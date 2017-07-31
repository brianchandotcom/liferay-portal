/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.ServiceComponentUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.BuildAutoUpgradeTestEntityModelImpl;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class BuildAutoUpgradeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		try (Connection con = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement ps = con.prepareStatement(
				"drop table BuildAutoUpgradeTestEntity")) {

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
		}

		_jarBytesV1 = _createBundleBytes("serviceV1", null, null);
		_jarBytesV2 = _createBundleBytes(
			"serviceV2", this::_addColumn,
			"create table BuildAutoUpgradeTestEntity (id_ LONG not null " +
				"primary key, data_ VARCHAR(75) null, data2 VARCHAR(75) " +
					"null);");
		_jarBytesV3 = _createBundleBytes(
			"serviceV3", this::_removeColumn,
			"create table BuildAutoUpgradeTestEntity (id_ LONG not null " +
				"primary key, data2 VARCHAR(75) null);");
		_jarBytesV4 = _createBundleBytes("serviceV4", null, null);

		Bundle testBundle = FrameworkUtil.getBundle(BuildAutoUpgradeTest.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		try (InputStream is = new UnsyncByteArrayInputStream(_jarBytesV1)) {
			_bundle = bundleContext.installBundle(
				BuildAutoUpgradeTest.class.getName(), is);
		}

		_bundle.start();
	}

	@After
	public void tearDown() throws Throwable {
		if (_bundle != null) {
			_bundle.uninstall();
		}

		try (Connection con = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement ps = con.prepareStatement(
				"drop table BuildAutoUpgradeTestEntity")) {

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
		}

		Release release = ReleaseLocalServiceUtil.fetchRelease(
			_BUNDLE_SYMBOLICNAME);

		if (release != null) {
			ReleaseLocalServiceUtil.deleteRelease(release);
		}

		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		TransactionInvokerUtil.invoke(
			builder.build(),
			() -> {
				ServiceComponentUtil.removeByBuildNamespace(
					"BuildAutoUpgradeTest");

				return null;
			});
	}

	@Test
	public void testBuildAutoUpgrade() throws Exception {

		// Initial columns

		_assertColumns("id_", "data_");

		// Add "data2" column

		_updateBundle(_jarBytesV2);

		_assertColumns("id_", "data_", "data2");

		// Remove "data_" column

		_updateBundle(_jarBytesV3);

		_assertColumns("id_", "data2");

		// Remove "data2" column and add "data_" column

		_updateBundle(_jarBytesV4);

		_assertColumns("id_", "data_");
	}

	private void _addClass(
			JarOutputStream jarOutputStream,
			Consumer<MethodVisitor> methodVisitorConsumer, String createSQL)
		throws Exception {

		jarOutputStream.putNextEntry(new JarEntry(_ENTITY_PATH));

		ClassLoader classLoader = BuildAutoUpgradeTest.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				_ENTITY_PATH)) {

			ClassReader classReader = new ClassReader(inputStream);

			ClassWriter classWriter = new ClassWriter(
				classReader, ClassWriter.COMPUTE_MAXS);

			ClassVisitor classVisitor =
				new ClassVisitor(Opcodes.ASM5, classWriter) {

					@Override
					public FieldVisitor visitField(
						int access, String name, String desc, String signature,
						Object value) {

						if ((createSQL != null) &&
							name.equals("TABLE_SQL_CREATE")) {

							value = createSQL;
						}

						return super.visitField(
							access, name, desc, signature, value);
					}

					@Override
					public MethodVisitor visitMethod(
						int access, String name, String desc, String signature,
						String[] exceptions) {

						MethodVisitor methodVisitor = super.visitMethod(
							access, name, desc, signature, exceptions);

						if ((methodVisitorConsumer != null) &&
							name.equals("<clinit>")) {

							methodVisitorConsumer.accept(methodVisitor);

							return null;
						}

						return methodVisitor;
					}

				};

			classReader.accept(classVisitor, 0);

			StreamUtil.transfer(
				new UnsyncByteArrayInputStream(classWriter.toByteArray()),
				jarOutputStream, false);
		}

		jarOutputStream.closeEntry();
	}

	private void _addColumn(MethodVisitor methodVisitor) {
		methodVisitor.visitCode();
		methodVisitor.visitInsn(Opcodes.ICONST_3);
		methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "[Ljava/lang/Object;");
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_0);
		methodVisitor.visitInsn(Opcodes.ICONST_2);
		methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_0);
		methodVisitor.visitLdcInsn("id_");
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_1);
		methodVisitor.visitIntInsn(Opcodes.BIPUSH, Types.BIGINT);
		methodVisitor.visitMethodInsn(
			Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf",
			"(I)Ljava/lang/Integer;", false);
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_1);
		methodVisitor.visitInsn(Opcodes.ICONST_2);
		methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_0);
		methodVisitor.visitLdcInsn("data_");
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_1);
		methodVisitor.visitIntInsn(Opcodes.BIPUSH, Types.VARCHAR);
		methodVisitor.visitMethodInsn(
			Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf",
			"(I)Ljava/lang/Integer;", false);
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_2);
		methodVisitor.visitInsn(Opcodes.ICONST_2);
		methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_0);
		methodVisitor.visitLdcInsn("data2");
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_1);
		methodVisitor.visitIntInsn(Opcodes.BIPUSH, Types.VARCHAR);
		methodVisitor.visitMethodInsn(
			Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf",
			"(I)Ljava/lang/Integer;", false);
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitFieldInsn(
			Opcodes.PUTSTATIC,
			"com/liferay/portal/model/impl/BuildAutoUpgradeTestEntityModelImpl",
			"TABLE_COLUMNS", "[[Ljava/lang/Object;");
		methodVisitor.visitInsn(Opcodes.RETURN);
		methodVisitor.visitMaxs(7, 0);
		methodVisitor.visitEnd();
	}

	private void _addResource(
			String resourcePath, String path, JarOutputStream jarOutputStream)
		throws Exception {

		jarOutputStream.putNextEntry(new JarEntry(path));

		try (InputStream inputStream =
				BuildAutoUpgradeTest.class.getResourceAsStream(
					resourcePath + path)) {

			StreamUtil.transfer(inputStream, jarOutputStream, false);
		}

		jarOutputStream.closeEntry();
	}

	private String _assertAndGetFirstLogRecordMessage(
		CaptureAppender captureAppender) {

		List<LoggingEvent> loggingEvents = captureAppender.getLoggingEvents();

		Assert.assertEquals(loggingEvents.toString(), 1, loggingEvents.size());

		LoggingEvent loggingEvent = loggingEvents.get(0);

		return loggingEvent.getRenderedMessage();
	}

	private void _assertColumns(String... columnNames) throws Exception {
		Set<String> names = SetUtil.fromArray(columnNames);

		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			DBInspector dbInspector = new DBInspector(con);

			DatabaseMetaData metaData = con.getMetaData();

			try (ResultSet rs = metaData.getColumns(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					dbInspector.normalizeName("BuildAutoUpgradeTestEntity"),
					null)) {

				while (rs.next()) {
					String columnName = StringUtil.toLowerCase(
						rs.getString("COLUMN_NAME"));

					Assert.assertTrue(
						columnName + " should not exist",
						names.remove(columnName));
				}
			}
		}

		Assert.assertEquals(names.toString(), 0, names.size());
	}

	private byte[] _createBundleBytes(
			String resourcePath, Consumer<MethodVisitor> methodVisitorConsumer,
			String createSQL)
		throws Exception {

		try (UnsyncByteArrayOutputStream byteArrayOutputStream =
				new UnsyncByteArrayOutputStream();
			JarOutputStream jarOutputStream =
				new JarOutputStream(byteArrayOutputStream)) {

			Manifest manifest = new Manifest();

			Attributes attributes = manifest.getMainAttributes();

			attributes.putValue("Manifest-Version", "1.0");
			attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
			attributes.putValue(
				Constants.BUNDLE_NAME, "Build Auto Upgrade Test");
			attributes.putValue(
				Constants.BUNDLE_SYMBOLICNAME, _BUNDLE_SYMBOLICNAME);
			attributes.putValue(Constants.BUNDLE_VERSION, "1.0.0");
			attributes.putValue("Liferay-Require-SchemaVersion", "1.0.0");
			attributes.putValue("Liferay-Service", Boolean.TRUE.toString());
			attributes.putValue("Liferay-Spring-Context", "META-INF/spring");

			jarOutputStream.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME));

			manifest.write(jarOutputStream);

			jarOutputStream.closeEntry();

			_addClass(jarOutputStream, methodVisitorConsumer, createSQL);

			_addResource(
				"dependencies/service/", "META-INF/portlet-model-hints.xml",
				jarOutputStream);
			_addResource(
				"dependencies/service/", "META-INF/spring/module-spring.xml",
				jarOutputStream);
			_addResource(
				"dependencies/service/", "META-INF/sql/indexes.sql",
				jarOutputStream);
			_addResource(
				"dependencies/service/", "META-INF/sql/sequences.sql",
				jarOutputStream);

			_addResource(
				"dependencies/" + resourcePath + "/", "service.properties",
				jarOutputStream);
			_addResource(
				"dependencies/" + resourcePath + "/", "META-INF/sql/tables.sql",
				jarOutputStream);

			jarOutputStream.finish();

			return byteArrayOutputStream.toByteArray();
		}
	}

	private void _removeColumn(MethodVisitor methodVisitor) {
		methodVisitor.visitCode();
		methodVisitor.visitInsn(Opcodes.ICONST_2);
		methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "[Ljava/lang/Object;");
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_0);
		methodVisitor.visitInsn(Opcodes.ICONST_2);
		methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_0);
		methodVisitor.visitLdcInsn("id_");
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_1);
		methodVisitor.visitIntInsn(Opcodes.BIPUSH, Types.BIGINT);
		methodVisitor.visitMethodInsn(
			Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf",
			"(I)Ljava/lang/Integer;", false);
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_1);
		methodVisitor.visitInsn(Opcodes.ICONST_2);
		methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_0);
		methodVisitor.visitLdcInsn("data2");
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.DUP);
		methodVisitor.visitInsn(Opcodes.ICONST_1);
		methodVisitor.visitIntInsn(Opcodes.BIPUSH, Types.VARCHAR);
		methodVisitor.visitMethodInsn(
			Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf",
			"(I)Ljava/lang/Integer;", false);
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitInsn(Opcodes.AASTORE);
		methodVisitor.visitFieldInsn(
			Opcodes.PUTSTATIC,
			"com/liferay/portal/model/impl/BuildAutoUpgradeTestEntityModelImpl",
			"TABLE_COLUMNS", "[[Ljava/lang/Object;");
		methodVisitor.visitInsn(Opcodes.RETURN);
		methodVisitor.visitMaxs(7, 0);
		methodVisitor.visitEnd();
	}

	private void _updateBundle(byte[] jarBytes) throws Exception {
		try (CaptureAppender serviceComponentCaptureHandler =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.service.impl." +
						"ServiceComponentLocalServiceImpl",
					Level.WARN);
			CaptureAppender baseDBCaptureHandler =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.dao.db.BaseDB", Level.WARN)) {

			_bundle.update(new UnsyncByteArrayInputStream(jarBytes));

			String message = _assertAndGetFirstLogRecordMessage(
				serviceComponentCaptureHandler);

			Assert.assertTrue(
				message,
				message.startsWith("Auto upgrading BuildAutoUpgradeTest"));

			message = _assertAndGetFirstLogRecordMessage(baseDBCaptureHandler);

			Assert.assertTrue(
				message,
				message.contains("create table BuildAutoUpgradeTestEntity"));
		}
	}

	private static final String _BUNDLE_SYMBOLICNAME =
		"build.auto.upgrade.test";

	private static final String _ENTITY_PATH;

	static {
		String path = BuildAutoUpgradeTestEntityModelImpl.class.getName();

		path = path.replace('.', '/');

		_ENTITY_PATH = path.concat(".class");
	}

	private Bundle _bundle;
	private byte[] _jarBytesV1;
	private byte[] _jarBytesV2;
	private byte[] _jarBytesV3;
	private byte[] _jarBytesV4;

}