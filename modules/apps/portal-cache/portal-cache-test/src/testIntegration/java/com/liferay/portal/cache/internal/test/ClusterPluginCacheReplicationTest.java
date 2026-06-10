/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.log4j.Log4JUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.TomcatClusterTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.cluster.tomcat.TomcatCluster;
import com.liferay.portal.test.cluster.tomcat.TomcatNode;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import java.lang.reflect.Constructor;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Jiefeng Wu
 */
@RunWith(Arquillian.class)
public class ClusterPluginCacheReplicationTest implements Serializable {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@ClassRule
	public static final TomcatClusterTestRule tomcatClusterTestRule =
		new TomcatClusterTestRule();

	@Test
	public void testReplicatePutsViaCopy() throws Exception {
		TomcatCluster.Builder builder1 =
			tomcatClusterTestRule.buildTomcatNode();

		TomcatNode tomcatNode1 = builder1.build();

		tomcatNode1.start(true);

		TomcatCluster.Builder builder2 =
			tomcatClusterTestRule.buildTomcatNode();

		TomcatNode tomcatNode2 = builder2.build();

		tomcatNode2.start(true);

		String testCacheName = RandomTestUtil.randomString();
		String testKey = RandomTestUtil.randomString();
		String testValue = RandomTestUtil.randomString();

		_installSampleBundle(tomcatNode1);

		Assert.assertNull(
			tomcatNode1.syncExecute(
				() -> {
					PortalCache<Serializable, Serializable> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					_setFieldValue(portalCache, "_replicatePutsViaCopy", true);

					return portalCache.get(testKey);
				}));

		Assert.assertNull(
			tomcatNode2.syncExecute(
				() -> {
					PortalCache<Serializable, Serializable> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					_setFieldValue(portalCache, "_replicatePutsViaCopy", true);

					portalCache.registerPortalCacheListener(
						new TestPortalCacheListener());

					Log4JUtil.setLevel(
						"com.liferay.portal.cache.multiple.internal.cluster." +
							"link.ClusterLinkMessageUtil",
						"OFF", false);

					return portalCache.get(testKey);
				}));

		tomcatNode1.syncExecute(
			() -> {
				PortalCache<Serializable, Serializable> portalCache =
					PortalCacheHelperUtil.getPortalCache(
						PortalCacheManagerNames.MULTI_VM, testCacheName);

				portalCache.put(testKey, _newSampleValue(testValue));

				portalCache.remove(testKey);

				return null;
			});

		Assert.assertNull(
			tomcatNode2.syncExecute(
				() -> {
					PortalCache<Serializable, Serializable> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					TestPortalCacheListener testPortalCacheListener =
						(TestPortalCacheListener)_getPortalCacheListener(
							TestPortalCacheListener.class.getName(),
							portalCache);

					CountDownLatch putCountDownLatch =
						testPortalCacheListener.getPutCountDownLatch();

					CountDownLatch removeCountDownLatch =
						testPortalCacheListener.getRemoveCountDownLatch();

					removeCountDownLatch.await();

					Assert.assertEquals(1, putCountDownLatch.getCount());

					return portalCache.get(testKey);
				}));

		_installSampleBundle(tomcatNode2);

		tomcatNode1.syncExecute(
			() -> {
				PortalCache<Serializable, Serializable> portalCache =
					PortalCacheHelperUtil.getPortalCache(
						PortalCacheManagerNames.MULTI_VM, testCacheName);

				portalCache.put(testKey, _newSampleValue(testValue));

				return null;
			});

		Assert.assertEquals(
			testValue,
			tomcatNode2.syncExecute(
				() -> {
					PortalCache<Serializable, Serializable> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					TestPortalCacheListener testPortalCacheListener =
						(TestPortalCacheListener)_getPortalCacheListener(
							TestPortalCacheListener.class.getName(),
							portalCache);

					CountDownLatch putCountDownLatch =
						testPortalCacheListener.getPutCountDownLatch();

					putCountDownLatch.await();

					Serializable value = portalCache.get(testKey);

					return String.valueOf(value);
				}));
	}

	public static class TestPortalCacheListener
		implements PortalCacheListener<Serializable, Serializable>,
				   Serializable {

		public TestPortalCacheListener() {
			_putCountDownLatch = new CountDownLatch(1);
			_removeCountDownLatch = new CountDownLatch(1);
		}

		@Override
		public void dispose() {
		}

		public CountDownLatch getPutCountDownLatch() {
			return _putCountDownLatch;
		}

		public CountDownLatch getRemoveCountDownLatch() {
			return _removeCountDownLatch;
		}

		@Override
		public void notifyEntryEvicted(
				PortalCache<Serializable, Serializable> portalCache,
				Serializable key, Serializable value, int timeToLive)
			throws PortalCacheException {
		}

		@Override
		public void notifyEntryExpired(
				PortalCache<Serializable, Serializable> portalCache,
				Serializable key, Serializable value, int timeToLive)
			throws PortalCacheException {
		}

		@Override
		public void notifyEntryPut(
				PortalCache<Serializable, Serializable> portalCache,
				Serializable key, Serializable value, int timeToLive)
			throws PortalCacheException {

			_putCountDownLatch.countDown();
		}

		@Override
		public void notifyEntryRemoved(
				PortalCache<Serializable, Serializable> portalCache,
				Serializable key, Serializable value, int timeToLive)
			throws PortalCacheException {

			_removeCountDownLatch.countDown();
		}

		@Override
		public void notifyEntryUpdated(
				PortalCache<Serializable, Serializable> portalCache,
				Serializable key, Serializable value, int timeToLive)
			throws PortalCacheException {
		}

		@Override
		public void notifyRemoveAll(
				PortalCache<Serializable, Serializable> portalCache)
			throws PortalCacheException {
		}

		private final CountDownLatch _putCountDownLatch;
		private final CountDownLatch _removeCountDownLatch;

	}

	private byte[] _generateSampleClassBytes() {
		String internalName = StringUtil.replace(_SAMPLE_CLASS_NAME, '.', '/');

		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

		classWriter.visit(
			Opcodes.V1_8, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER, internalName,
			null, "java/lang/Object", new String[] {"java/io/Serializable"});

		FieldVisitor fieldVisitor = classWriter.visitField(
			Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL, "_name",
			"Ljava/lang/String;", null, null);

		fieldVisitor.visitEnd();

		MethodVisitor constructorMethodVisitor = classWriter.visitMethod(
			Opcodes.ACC_PUBLIC, "<init>", "(Ljava/lang/String;)V", null, null);

		constructorMethodVisitor.visitCode();
		constructorMethodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		constructorMethodVisitor.visitMethodInsn(
			Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		constructorMethodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		constructorMethodVisitor.visitVarInsn(Opcodes.ALOAD, 1);
		constructorMethodVisitor.visitFieldInsn(
			Opcodes.PUTFIELD, internalName, "_name", "Ljava/lang/String;");
		constructorMethodVisitor.visitInsn(Opcodes.RETURN);
		constructorMethodVisitor.visitMaxs(0, 0);
		constructorMethodVisitor.visitEnd();

		MethodVisitor toStringMethodVisitor = classWriter.visitMethod(
			Opcodes.ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);

		toStringMethodVisitor.visitCode();
		toStringMethodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		toStringMethodVisitor.visitFieldInsn(
			Opcodes.GETFIELD, internalName, "_name", "Ljava/lang/String;");
		toStringMethodVisitor.visitInsn(Opcodes.ARETURN);
		toStringMethodVisitor.visitMaxs(0, 0);
		toStringMethodVisitor.visitEnd();

		classWriter.visitEnd();

		return classWriter.toByteArray();
	}

	private PortalCacheListener<?, ?> _getPortalCacheListener(
		String className, PortalCache<?, ?> portalCache) {

		portalCache = ReflectionTestUtil.getFieldValue(
			portalCache, "_portalCache");

		Object aggregatedPortalCacheListener = ReflectionTestUtil.getFieldValue(
			portalCache, "aggregatedPortalCacheListener");

		ConcurrentMap<PortalCacheListener<?, ?>, PortalCacheListenerScope>
			portalCacheListeners = ReflectionTestUtil.getFieldValue(
				aggregatedPortalCacheListener, "_portalCacheListeners");

		for (PortalCacheListener<?, ?> portalCacheListener :
				portalCacheListeners.keySet()) {

			Class<?> clazz = portalCacheListener.getClass();

			if (Objects.equals(clazz.getName(), className)) {
				return portalCacheListener;
			}
		}

		throw new IllegalStateException(className + " does not exist");
	}

	private void _installSampleBundle(TomcatNode tomcatNode) throws Exception {
		Manifest manifest = new Manifest();

		Attributes mainAttributes = manifest.getMainAttributes();

		mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
		mainAttributes.putValue("Bundle-ManifestVersion", "2");
		mainAttributes.putValue(
			"Bundle-SymbolicName", _SAMPLE_BUNDLE_SYMBOLIC_NAME);
		mainAttributes.putValue("Bundle-Version", _SAMPLE_BUNDLE_VERSION);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		byte[] classBytes = _generateSampleClassBytes();

		try (JarOutputStream jarOutputStream = new JarOutputStream(
				byteArrayOutputStream, manifest)) {

			jarOutputStream.putNextEntry(
				new JarEntry(
					StringUtil.replace(_SAMPLE_CLASS_NAME, '.', '/') +
						".class"));

			jarOutputStream.write(classBytes);

			jarOutputStream.closeEntry();
		}

		byte[] bundleBytes = byteArrayOutputStream.toByteArray();

		tomcatNode.syncExecute(
			() -> {
				BundleContext bundleContext =
					SystemBundleUtil.getBundleContext();

				Bundle bundle = bundleContext.installBundle(
					_SAMPLE_BUNDLE_SYMBOLIC_NAME,
					new ByteArrayInputStream(bundleBytes));

				bundle.start();

				return null;
			});
	}

	private Serializable _newSampleValue(String value) throws Exception {
		ClassLoader sampleClassLoader = ClassLoaderPool.getClassLoader(
			_SAMPLE_BUNDLE_SYMBOLIC_NAME + "_" + _SAMPLE_BUNDLE_VERSION);

		Class<?> sampleClass = sampleClassLoader.loadClass(_SAMPLE_CLASS_NAME);

		Constructor<?> constructor = sampleClass.getConstructor(String.class);

		return (Serializable)constructor.newInstance(value);
	}

	private void _setFieldValue(
		PortalCache<?, ?> portalCache, String fieldName, boolean fieldValue) {

		ReflectionTestUtil.setFieldValue(
			(Object)ReflectionTestUtil.getFieldValue(
				_getPortalCacheListener(
					_CLASS_NAME_EHCACHE_PORTAL_CACHE_REPLICATOR, portalCache),
				"_portalCacheReplicator"),
			fieldName, fieldValue);
	}

	private static final String _CLASS_NAME_EHCACHE_PORTAL_CACHE_REPLICATOR =
		"com.liferay.portal.cache.ehcache.internal.events." +
			"EhcachePortalCacheReplicatorUtil$EhcachePortalCacheReplicator";

	private static final String _SAMPLE_BUNDLE_SYMBOLIC_NAME =
		"com.liferay.portal.cluster.multiple.sample.web";

	private static final String _SAMPLE_BUNDLE_VERSION = "1.0.0";

	private static final String _SAMPLE_CLASS_NAME =
		"com.liferay.portal.cluster.multiple.sample.web.internal." +
			"ClusterSampleClass";

}