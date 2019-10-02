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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.service.persistence.ServiceComponentPersistence;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.DBAssertionUtil;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.aop.AopInvocationHandler;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.test.model.impl.BuildAutoUpgradeTestEntityModelImpl;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

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
import org.objectweb.asm.Type;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public abstract class BaseBuildAutoUpgradeTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"drop table BuildAutoUpgradeTestEntity")) {

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
		}

		_previousSchemaModuleBuildAutoUpgrade =
			PropsValues.SCHEMA_MODULE_BUILD_AUTO_UPGRADE;

		PropsValues.SCHEMA_MODULE_BUILD_AUTO_UPGRADE = true;
	}

	@After
	public void tearDown() throws Throwable {
		PropsValues.SCHEMA_MODULE_BUILD_AUTO_UPGRADE =
			_previousSchemaModuleBuildAutoUpgrade;

		if (_bundle != null) {
			_bundle.uninstall();
		}

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"drop table BuildAutoUpgradeTestEntity")) {

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
		}

		Release release = _releaseLocalService.fetchRelease(
			BUNDLE_SYMBOLICNAME);

		if (release != null) {
			_releaseLocalService.deleteRelease(release);
		}

		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		TransactionInvokerUtil.invoke(
			builder.build(),
			() -> {
				_serviceComponentPersistence.removeByBuildNamespace(
					"BuildAutoUpgradeTest");

				return null;
			});

		AopInvocationHandler aopInvocationHandler =
			(AopInvocationHandler)ProxyUtil.getInvocationHandler(
				_serviceComponentLocalService);

		Object serviceComponentLocalServiceImpl =
			aopInvocationHandler.getTarget();

		Class<?> clazz = serviceComponentLocalServiceImpl.getClass();

		Field field = clazz.getDeclaredField("_serviceComponents");

		field.setAccessible(true);

		field.set(serviceComponentLocalServiceImpl, null);
	}

	@Test
	public void testBuildAutoUpgrade() throws Exception {
		Bundle testBundle = FrameworkUtil.getBundle(
			BaseBuildAutoUpgradeTestCase.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		_bundle = bundleContext.installBundle(
			BaseBuildAutoUpgradeTestCase.class.getName(),
			getBundleInputStream(1));

		_bundle.start();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"insert into BuildAutoUpgradeTestEntity values (1, 'data')")) {

			Assert.assertEquals(1, ps.executeUpdate());
		}

		// Initial columns

		DBAssertionUtil.assertColumns(
			"BuildAutoUpgradeTestEntity", "id_", "data_");

		// Add "data2" column

		_updateBundle(getBundleInputStream(2));

		DBAssertionUtil.assertColumns(
			"BuildAutoUpgradeTestEntity", "id_", "data_", "data2");

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select id_, data_, data2 from BuildAutoUpgradeTestEntity");
			ResultSet rs = ps.executeQuery()) {

			Assert.assertTrue(rs.next());

			Assert.assertEquals(1, rs.getLong("id_"));
			Assert.assertEquals("data", rs.getString("data_"));

			String data2 = rs.getString("data2");

			Assert.assertTrue(data2, Validator.isNull(data2));

			Assert.assertFalse(rs.next());
		}

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"update BuildAutoUpgradeTestEntity set data2 = 'data2' where " +
					"id_ = 1")) {

			Assert.assertEquals(1, ps.executeUpdate());
		}

		// Remove "data_" column

		_updateBundle(getBundleInputStream(3));

		DBAssertionUtil.assertColumns(
			"BuildAutoUpgradeTestEntity", "id_", "data2");

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select id_, data2 from BuildAutoUpgradeTestEntity");
			ResultSet rs = ps.executeQuery()) {

			Assert.assertTrue(rs.next());

			Assert.assertEquals(1, rs.getLong("id_"));
			Assert.assertEquals("data2", rs.getString("data2"));

			Assert.assertFalse(rs.next());
		}

		// Remove "data2" column and add "data_" column

		_updateBundle(getBundleInputStream(4));

		DBAssertionUtil.assertColumns(
			"BuildAutoUpgradeTestEntity", "id_", "data_");

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select id_, data_ from BuildAutoUpgradeTestEntity");
			ResultSet rs = ps.executeQuery()) {

			Assert.assertTrue(rs.next());

			Assert.assertEquals(1, rs.getLong("id_"));

			String data = rs.getString("data_");

			Assert.assertTrue(data, Validator.isNull(data));

			Assert.assertFalse(rs.next());
		}
	}

	protected void addClass(
			String path, JarOutputStream jarOutputStream,
			Object[][] tableColumns, String createSQL)
		throws IOException {

		jarOutputStream.putNextEntry(new JarEntry(path));

		ClassLoader classLoader =
			BaseBuildAutoUpgradeTestCase.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				ENTITY_PATH)) {

			ClassReader classReader = new ClassReader(inputStream);

			ClassWriter classWriter = new ClassWriter(
				classReader, ClassWriter.COMPUTE_MAXS);

			ClassVisitor classVisitor = new ClassVisitor(
				Opcodes.ASM5, classWriter) {

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

					if (name.equals("<clinit>")) {
						_initTableColumns(methodVisitor, tableColumns);

						return null;
					}

					return methodVisitor;
				}

			};

			classReader.accept(classVisitor, 0);

			jarOutputStream.write(classWriter.toByteArray());
		}

		jarOutputStream.closeEntry();
	}

	protected void addEmptyResource(
			String path, JarOutputStream jarOutputStream)
		throws IOException {

		jarOutputStream.putNextEntry(new JarEntry(path));

		jarOutputStream.closeEntry();
	}

	protected void addResource(
			String path, byte[] data, JarOutputStream jarOutputStream)
		throws IOException {

		jarOutputStream.putNextEntry(new JarEntry(path));

		jarOutputStream.write(data);

		jarOutputStream.closeEntry();
	}

	protected void addResource(String path, JarOutputStream jarOutputStream)
		throws IOException {

		addResource("dependencies/service/" + path, path, jarOutputStream);
	}

	protected void addResource(
			String resourcePath, String path, JarOutputStream jarOutputStream)
		throws IOException {

		jarOutputStream.putNextEntry(new JarEntry(path));

		try (InputStream inputStream =
				BaseBuildAutoUpgradeTestCase.class.getResourceAsStream(
					resourcePath)) {

			StreamUtil.transfer(inputStream, jarOutputStream, false);
		}

		jarOutputStream.closeEntry();
	}

	protected void addServiceProperties(
			int version, String path, JarOutputStream jarOutputStream)
		throws IOException {

		long time = System.currentTimeMillis();

		Properties serviceProperties = new Properties();

		serviceProperties.setProperty(
			"build.namespace", "BuildAutoUpgradeTest");
		serviceProperties.setProperty("build.number", String.valueOf(version));
		serviceProperties.setProperty(
			"build.date", String.valueOf(time + version));

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			serviceProperties.store(unsyncByteArrayOutputStream, null);

			addResource(
				path, unsyncByteArrayOutputStream.toByteArray(),
				jarOutputStream);
		}
	}

	protected abstract InputStream getBundleInputStream(int version)
		throws IOException;

	protected String toCreateSQL(Object[][] tableColumns) {
		StringBundler sb = new StringBundler(tableColumns.length * 5 + 1);

		sb.append("create table BuildAutoUpgradeTestEntity (");

		boolean first = true;

		for (Object[] tableColumn : tableColumns) {
			sb.append(tableColumn[0]);
			sb.append(StringPool.SPACE);

			int type = (Integer)tableColumn[1];

			if (Types.BIGINT == type) {
				sb.append("LONG");
			}
			else if (Types.VARCHAR == type) {
				sb.append("VARCHAR(75)");
			}
			else {
				throw new IllegalArgumentException("Unknown data type " + type);
			}

			if (first) {
				first = false;

				sb.append(" not null primary key");
			}
			else {
				sb.append(" null");
			}

			sb.append(StringPool.COMMA);
		}

		sb.setStringAt(");", sb.index() - 1);

		return sb.toString();
	}

	protected static final String BUNDLE_SYMBOLICNAME =
		"build.auto.upgrade.test";

	protected static final String ENTITY_PATH;

	static {
		String path = BuildAutoUpgradeTestEntityModelImpl.class.getName();

		path = StringUtil.replace(path, '.', '/');

		ENTITY_PATH = path.concat(".class");
	}

	private String _assertAndGetFirstLogRecordMessage(
		CaptureAppender captureAppender) {

		List<LoggingEvent> loggingEvents = captureAppender.getLoggingEvents();

		Assert.assertEquals(loggingEvents.toString(), 1, loggingEvents.size());

		LoggingEvent loggingEvent = loggingEvents.get(0);

		return loggingEvent.getRenderedMessage();
	}

	private void _initTableColumns(
		MethodVisitor methodVisitor, Object[][] tableColumns) {

		methodVisitor.visitCode();

		methodVisitor.visitInsn(Opcodes.ICONST_0 + tableColumns.length);
		methodVisitor.visitTypeInsn(
			Opcodes.ANEWARRAY, Type.getDescriptor(Object[].class));
		methodVisitor.visitInsn(Opcodes.DUP);

		for (int i = 0; i < tableColumns.length; i++) {
			Object[] tableColumn = tableColumns[i];

			methodVisitor.visitInsn(Opcodes.ICONST_0 + i);
			methodVisitor.visitInsn(Opcodes.ICONST_2);
			methodVisitor.visitTypeInsn(
				Opcodes.ANEWARRAY, Type.getInternalName(Object.class));
			methodVisitor.visitInsn(Opcodes.DUP);
			methodVisitor.visitInsn(Opcodes.ICONST_0);
			methodVisitor.visitLdcInsn(tableColumn[0]);
			methodVisitor.visitInsn(Opcodes.AASTORE);
			methodVisitor.visitInsn(Opcodes.DUP);
			methodVisitor.visitInsn(Opcodes.ICONST_1);
			methodVisitor.visitIntInsn(Opcodes.BIPUSH, (Integer)tableColumn[1]);
			methodVisitor.visitMethodInsn(
				Opcodes.INVOKESTATIC, Type.getInternalName(Integer.class),
				"valueOf",
				Type.getMethodDescriptor(
					Type.getType(Integer.class), Type.INT_TYPE),
				false);

			methodVisitor.visitInsn(Opcodes.AASTORE);
			methodVisitor.visitInsn(Opcodes.AASTORE);

			if (i < (tableColumns.length - 1)) {
				methodVisitor.visitInsn(Opcodes.DUP);
			}
		}

		methodVisitor.visitFieldInsn(
			Opcodes.PUTSTATIC,
			Type.getInternalName(BuildAutoUpgradeTestEntityModelImpl.class),
			"TABLE_COLUMNS", Type.getDescriptor(Object[][].class));
		methodVisitor.visitInsn(Opcodes.RETURN);
		methodVisitor.visitMaxs(0, 0);
		methodVisitor.visitEnd();
	}

	private void _updateBundle(InputStream inputStream) throws Exception {
		try (CaptureAppender serviceComponentCaptureHandler =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.service.impl." +
						"ServiceComponentLocalServiceImpl",
					Level.WARN);
			CaptureAppender baseDBCaptureHandler =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.dao.db.BaseDB", Level.WARN)) {

			_bundle.update(inputStream);

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

	private Bundle _bundle;
	private boolean _previousSchemaModuleBuildAutoUpgrade;

	@Inject
	private ReleaseLocalService _releaseLocalService;

	@Inject
	private ServiceComponentLocalService _serviceComponentLocalService;

	@Inject
	private ServiceComponentPersistence _serviceComponentPersistence;

}