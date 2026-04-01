/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log4j.Log4JUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.module.framework.ModuleFrameworkUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.util.LicenseUtil;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.ResettableClassFileTransformer;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import org.junit.Assert;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework;

/**
 * @author Tina Tian
 */
public abstract class BaseLicenseTestCase implements Serializable {

	public static SafeCloseable disableValidateWithSafeCloseable() {
		ResettableClassFileTransformer resettableClassFileTransformer =
			_transformMethod(ReflectionsHolder._validateMethod, true);

		return () -> resettableClassFileTransformer.reset(
			ReflectionsHolder._instrumentation,
			AgentBuilder.RedefinitionStrategy.RETRANSFORMATION);
	}

	public static boolean isReleaseBundle() {
		if (ReflectionsHolder._licenseManagerHelperClass != null) {
			return true;
		}

		return false;
	}

	public static SafeCloseable setVersionWithSafeCloseable(String version) {
		ResettableClassFileTransformer resettableClassFileTransformer =
			_transformMethod(ReflectionsHolder._versionMethod, version);

		return () -> resettableClassFileTransformer.reset(
			ReflectionsHolder._instrumentation,
			AgentBuilder.RedefinitionStrategy.RETRANSFORMATION);
	}

	public void assertBundlesExisted(String... bundleNames) {
		Set<String> bundleSymbolicNames = _getBundleSymbolicNames();

		Assert.assertTrue(
			bundleSymbolicNames.containsAll(SetUtil.fromArray(bundleNames)));
	}

	public void assertBundlesNotExisted(String... bundleNames) {
		Set<String> bundleSymbolicNames = _getBundleSymbolicNames();

		Assert.assertFalse(
			bundleSymbolicNames.containsAll(SetUtil.fromArray(bundleNames)));
	}

	public void assertLicensePropertiesExisted(String productId) {
		Map<String, String> licenseProperties =
			LicenseManagerUtil.getLicenseProperties(productId);

		Assert.assertFalse(
			licenseProperties.toString(), licenseProperties.isEmpty());
	}

	public void assertLicensePropertiesNotExisted(String productId) {
		Map<String, String> licenseProperties =
			LicenseManagerUtil.getLicenseProperties(productId);

		Assert.assertTrue(
			licenseProperties.toString(), licenseProperties.isEmpty());
	}

	public void assertPortalLicenseInvalid() throws Exception {
		String response = hitHomePage("localhost", getLocalPort());

		Assert.assertTrue(response.contains(_INVALID_LICENSE_KEY));
	}

	public void assertPortalLicenseNotRegistered() throws Exception {
		String response = hitHomePage("localhost", getLocalPort());

		Assert.assertTrue(response.contains(_NOT_REGISTERED_LICENSE_KEY));
	}

	public void assertPortalLicenseRegistered() throws Exception {
		String response = hitHomePage("localhost", getLocalPort());

		Assert.assertFalse(response.contains(_LICENSE_PAGE_KEY));
	}

	public File deployCMPLicense(long validityPeriod) throws Exception {
		long currentTimeMillis = System.currentTimeMillis();

		StringBundler sb = new StringBundler(19);

		sb.append("<?xml version=\"1.0\"?><license><product-id>");
		sb.append(getCMPProductId());
		sb.append("</product-id><product-name>");
		sb.append(_CMP_PRODUCT_NAME);
		sb.append("</product-name><product-version>2026.Q1</product-version>");
		sb.append("<license-type>");
		sb.append(_CMP_LICENSE_TYPE);
		sb.append("</license-type><license-version>3</license-version>");
		sb.append("<start-date>");
		sb.append(_DATE_FORMAT.format(new Date(currentTimeMillis)));
		sb.append("</start-date><expiration-date>");
		sb.append(
			_DATE_FORMAT.format(new Date(currentTimeMillis + validityPeriod)));
		sb.append("</expiration-date><host-names>");
		sb.append("<host-name>localhost</host-name>");
		sb.append("</host-names><ip-addresses>");

		for (String localIpAddress : LicenseUtil.getIpAddresses()) {
			sb.append("<ip-address>");
			sb.append(localIpAddress);
			sb.append("</ip-address>");
		}

		sb.append("</ip-addresses><mac-addresses>");

		for (String localMacAddress : LicenseUtil.getMacAddresses()) {
			sb.append("<mac-address>");
			sb.append(localMacAddress);
			sb.append("</mac-address>");
		}

		sb.append("</mac-addresses><key></key></license>");

		LicenseManagerUtil.registerLicense(
			JSONUtil.put("licenseXML", sb.toString()));

		return _buildBinaryFile(
			getCMPProductId(), StringPool.BLANK, _CMP_PRODUCT_NAME,
			_CMP_LICENSE_TYPE);
	}

	public File deployEnterprisePortalLicense(long validityPeriod)
		throws Exception {

		long currentTimeMillis = System.currentTimeMillis();

		StringBundler sb = new StringBundler(19);

		sb.append("<?xml version=\"1.0\"?><license><account-name>");
		sb.append(_ENTERPRISE_ACCOUNT_NAME);
		sb.append("</account-name><product-id>");
		sb.append(getPortalProductId());
		sb.append("</product-id><product-name>");
		sb.append(_ENTERPRISE_PRODUCT_NAME);
		sb.append("</product-name><product-version>2026.Q1</product-version>");
		sb.append("<license-type>");
		sb.append(_ENTERPRISE_LICENSE_TYPE);
		sb.append("</license-type><license-version>6</license-version>");
		sb.append("<start-date>");
		sb.append(_DATE_FORMAT.format(new Date(currentTimeMillis)));
		sb.append("</start-date><expiration-date>");
		sb.append(
			_DATE_FORMAT.format(new Date(currentTimeMillis + validityPeriod)));
		sb.append("</expiration-date>");
		sb.append("<domains><domain>");
		sb.append(_ENTERPRISE_DOMAIN);
		sb.append("</domain><domain>localhost</domain></domains>");
		sb.append("<key></key></license>");

		LicenseManagerUtil.registerLicense(
			JSONUtil.put("licenseXML", sb.toString()));

		return _buildBinaryFile(
			getPortalProductId(), _ENTERPRISE_ACCOUNT_NAME,
			_ENTERPRISE_PRODUCT_NAME, _ENTERPRISE_LICENSE_TYPE);
	}

	public File deployFreeTierPortalLicense(long validityPeriod)
		throws Exception {

		return deployFreeTierPortalLicense(validityPeriod, _FREE_TIER_DOMAIN);
	}

	public File deployFreeTierPortalLicense(long validityPeriod, String domain)
		throws Exception {

		StringBundler sb = new StringBundler(19);

		sb.append("<?xml version=\"1.0\"?><license><account-name>");
		sb.append(_FREE_TIER_ACCOUNT_NAME);
		sb.append("</account-name><product-id>");
		sb.append(getPortalProductId());
		sb.append("</product-id><product-name>");
		sb.append(_FREE_TIER_PRODUCT_NAME);
		sb.append("</product-name><product-version>2026.Q1</product-version>");
		sb.append("<license-type>");
		sb.append(_FREE_TIER_LICENSE_TYPE);
		sb.append("</license-type><license-version>6</license-version>");
		sb.append("<start-date>");

		long currentTimeMillis = System.currentTimeMillis();

		sb.append(_DATE_FORMAT.format(new Date(currentTimeMillis)));

		sb.append("</start-date><expiration-date>");
		sb.append(
			_DATE_FORMAT.format(new Date(currentTimeMillis + validityPeriod)));
		sb.append("</expiration-date>");
		sb.append("<max-cluster-nodes>3</max-cluster-nodes><domains><domain>");
		sb.append(domain);
		sb.append("</domain><domain>localhost</domain></domains><key></key>");
		sb.append("</license>");

		LicenseManagerUtil.registerLicense(
			JSONUtil.put("licenseXML", sb.toString()));

		return _buildBinaryFile(
			getPortalProductId(), _FREE_TIER_ACCOUNT_NAME,
			_FREE_TIER_PRODUCT_NAME, _FREE_TIER_LICENSE_TYPE);
	}

	public void resetCheckInterval() throws Exception {
		Object lifecycleAction = ReflectionsHolder._lifecycleActionField.get(
			null);

		Class<?> lifecycleActionClass = lifecycleAction.getClass();

		for (Field field : lifecycleActionClass.getDeclaredFields()) {
			if (!Modifier.isFinal(field.getModifiers()) &&
				Objects.equals(field.getType(), long.class)) {

				field.setAccessible(true);

				field.set(lifecycleAction, 0L);
			}
		}
	}

	public void resetLicenseData() throws Exception {
		File dir = new File(LicenseUtil.LICENSE_REPOSITORY_DIR);

		if (dir.exists()) {
			FileUtil.deltree(dir);
		}

		LicenseManagerUtil.checkLicense(getPortalProductId());
		LicenseManagerUtil.checkLicense(getCMPProductId());
	}

	public void resetLifecycleAction() throws Exception {
		String originalPriority = Log4JUtil.getPriority(
			_BUNDLE_START_STOP_LOGGER);

		if (!Objects.equals(originalPriority, "OFF")) {
			Log4JUtil.setLevel(_BUNDLE_START_STOP_LOGGER, "OFF", false);
		}

		try {
			Object lifecycleAction =
				ReflectionsHolder._lifecycleActionField.get(null);

			Class<?> lifecycleActionClass = lifecycleAction.getClass();

			for (Method method : lifecycleActionClass.getDeclaredMethods()) {
				if (Arrays.equals(
						method.getParameterTypes(),
						new Class<?>[] {
							BundleContext.class, Map.class, Framework.class
						})) {

					method.setAccessible(true);

					for (Field field :
							lifecycleActionClass.getDeclaredFields()) {

						if (Map.class.isAssignableFrom(field.getType())) {
							field.setAccessible(true);

							Object bundleData = field.get(lifecycleAction);

							if (bundleData != null) {
								method.invoke(
									lifecycleAction,
									SystemBundleUtil.getBundleContext(),
									bundleData,
									ModuleFrameworkUtil.getFramework());
							}
						}
					}

					break;
				}
			}

			for (Field field : lifecycleActionClass.getDeclaredFields()) {
				if (!Modifier.isFinal(field.getModifiers())) {
					field.setAccessible(true);

					if (Objects.equals(field.getType(), long.class)) {
						field.set(lifecycleAction, 0L);
					}
					else if (Objects.equals(field.getType(), boolean.class)) {
						field.set(lifecycleAction, false);
					}
					else {
						field.set(lifecycleAction, null);
					}
				}
			}
		}
		finally {
			if (!Objects.equals(
					originalPriority,
					Log4JUtil.getPriority(_BUNDLE_START_STOP_LOGGER))) {

				Log4JUtil.setLevel(
					_BUNDLE_START_STOP_LOGGER, originalPriority, false);
			}
		}
	}

	protected static Field findField(
			ClassLoader classLoader, String fieldString)
		throws Exception {

		return ReflectionTestUtil.getField(
			classLoader.loadClass(
				fieldString.substring(
					0, fieldString.lastIndexOf(StringPool.PERIOD))),
			fieldString.substring(
				fieldString.lastIndexOf(StringPool.PERIOD) + 1));
	}

	protected static Method findMethod(
			ClassLoader classLoader, String methodString)
		throws Exception {

		String methodName = methodString.substring(
			0, methodString.indexOf(StringPool.OPEN_PARENTHESIS));

		String className = methodName.substring(
			0, methodName.lastIndexOf(StringPool.PERIOD));
		String methodSimpleName = methodName.substring(
			methodName.lastIndexOf(StringPool.PERIOD) + 1);

		String parameterString = methodString.substring(
			methodString.indexOf(StringPool.OPEN_PARENTHESIS) + 1,
			methodString.length() - 1);

		if (Validator.isNull(parameterString)) {
			return ReflectionTestUtil.getMethod(
				classLoader.loadClass(className), methodSimpleName);
		}

		String[] parameterNames = parameterString.split(StringPool.COMMA);

		Class<?>[] parameterTypes = new Class<?>[parameterNames.length];

		for (int i = 0; i < parameterNames.length; i++) {
			parameterTypes[i] = classLoader.loadClass(parameterNames[i]);
		}

		return ReflectionTestUtil.getMethod(
			classLoader.loadClass(className), methodSimpleName, parameterTypes);
	}

	protected static String getProperty(String propertyKey) {
		String value = _licenseTestProperties.getProperty(propertyKey);

		if (Validator.isNull(value)) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Property ", _PROPERTY_PREFIX, propertyKey, " is not set"));
		}

		return value;
	}

	protected void assertLicenseValidationFailedLog(
		LogCapture logCapture, String message) {

		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

		LogEntry logEntry = logEntries.get(0);

		Assert.assertEquals(LoggerTestUtil.ERROR, logEntry.getPriority());

		Assert.assertEquals(
			"DXP Production license validation failed", logEntry.getMessage());

		Throwable throwable = logEntry.getThrowable();

		Assert.assertEquals(message, throwable.getMessage());
	}

	protected void assertPortalInvalidatedWithBrokenFile(String filePath)
		throws Exception {

		_assertPortalInvalidatedWithBrokenFile(filePath, null);

		_assertPortalInvalidatedWithBrokenFile(
			filePath, InputStream.nullInputStream());
	}

	protected String getCMPProductId() {
		return getProperty("product.id.cmp");
	}

	protected String getCurrentVersion() throws Exception {
		Method versionMethod = ReflectionsHolder._versionMethod;

		return (String)versionMethod.invoke(null);
	}

	protected String getLicenseManagerClassName() {
		return getProperty("license.manager.class.name");
	}

	protected int getLocalPort() {
		int localPort = PortalUtil.getPortalLocalPort(false);

		if (localPort == -1) {
			localPort = 8080;
		}

		return localPort;
	}

	protected String getPortalProductId() {
		return getProperty("product.id.portal");
	}

	protected String hitHomePage(String host, int port) throws Exception {
		Http.Options options = new Http.Options();

		options.setCookieSpec(Http.CookieSpec.IGNORE_COOKIES);
		options.setLocation(String.format("http://%s:%d/", host, port));
		options.setMethod(Http.Method.GET);

		return HttpUtil.URLtoString(options);
	}

	private static ResettableClassFileTransformer _transformMethod(
		Method method, Object returnValue) {

		return new AgentBuilder.Default(
		).disableClassFormatChanges(
		).with(
			AgentBuilder.RedefinitionStrategy.RETRANSFORMATION
		).type(
			ElementMatchers.is(method.getDeclaringClass())
		).transform(
			(builder, typeDescription, classLoader, module, protectionDomain) ->
				builder.method(
					ElementMatchers.is(method)
				).intercept(
					FixedValue.value(returnValue)
				)
		).installOn(
			ReflectionsHolder._instrumentation
		);
	}

	private void _assertPortalInvalidatedWithBrokenFile(
			String filePath, InputStream inputStream)
		throws Exception {

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		PortalClassLoaderUtil.setClassLoader(
			new ClassLoader(classLoader) {

				@Override
				public boolean equals(Object object) {
					return classLoader.equals(object);
				}

				@Override
				public InputStream getResourceAsStream(String name) {
					if (name.equals(filePath)) {
						return inputStream;
					}

					return classLoader.getResourceAsStream(name);
				}

				@Override
				public int hashCode() {
					return classLoader.hashCode();
				}

			});

		try {
			assertPortalLicenseNotRegistered();

			deployFreeTierPortalLicense(Time.HOUR);

			assertLicensePropertiesExisted(getPortalProductId());

			assertPortalLicenseInvalid();
		}
		finally {
			PortalClassLoaderUtil.setClassLoader(classLoader);

			resetLicenseData();
			resetLifecycleAction();
		}
	}

	private File _buildBinaryFile(
		String productId, String accountName, String productEntryName,
		String licenseType) {

		StringBundler sb = new StringBundler(6);

		if (productId.equals(getPortalProductId())) {
			sb.append(StringUtil.extractChars(accountName));
			sb.append("_");
		}

		sb.append(StringUtil.extractChars(productEntryName));
		sb.append("_");
		sb.append(StringUtil.extractChars(licenseType));
		sb.append(".li");

		return new File(LicenseUtil.LICENSE_REPOSITORY_DIR, sb.toString());
	}

	private Set<String> _getBundleSymbolicNames() {
		Set<String> bundleSymbolicNames = new HashSet<>();

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			bundleSymbolicNames.add(bundle.getSymbolicName());
		}

		return bundleSymbolicNames;
	}

	private static final String _BUNDLE_START_STOP_LOGGER =
		"com.liferay.portal.bootstrap.log.BundleStartStopLogger";

	private static final String _CMP_LICENSE_TYPE = "production";

	private static final String _CMP_PRODUCT_NAME =
		"Liferay Content Marketing Platform";

	private static final DateFormat _DATE_FORMAT = new SimpleDateFormat(
		"EEEE, MMMM d, yyyy hh:mm:ss a z", LocaleUtil.US);

	private static final String _ENTERPRISE_ACCOUNT_NAME = "Enterprise Account";

	private static final String _ENTERPRISE_DOMAIN = "enterprise.com";

	private static final String _ENTERPRISE_LICENSE_TYPE = "enterprise";

	private static final String _ENTERPRISE_PRODUCT_NAME = "DXP Enterprise";

	private static final String _FREE_TIER_ACCOUNT_NAME = "Free Account";

	private static final String _FREE_TIER_DOMAIN = "free.tier.com";

	private static final String _FREE_TIER_LICENSE_TYPE = "free";

	private static final String _FREE_TIER_PRODUCT_NAME = "DXP Production";

	private static final String _INVALID_LICENSE_KEY =
		"This instance is invalid.";

	private static final String _LICENSE_PAGE_KEY =
		"<strong class=\"lead\">Error:</strong>";

	private static final String _NOT_REGISTERED_LICENSE_KEY =
		"This instance is not registered.";

	private static final String _PROPERTY_PREFIX = "license.test.";

	private static Properties _licenseTestProperties;

	private static class ReflectionsHolder {

		private static final Log _log = LogFactoryUtil.getLog(
			BaseLicenseTestCase.class);

		private static Instrumentation _instrumentation;
		private static Class<?> _licenseManagerHelperClass;
		private static Field _lifecycleActionField;
		private static Method _validateMethod;
		private static Method _versionMethod;

		static {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			try {
				_licenseManagerHelperClass = classLoader.loadClass(
					"com.liferay.portal.ee.license.util.LicenseManagerHelper");
			}
			catch (ClassNotFoundException classNotFoundException) {
				if (_log.isDebugEnabled()) {
					_log.debug(classNotFoundException);
				}
			}

			if (_licenseManagerHelperClass != null) {
				_licenseTestProperties = PropsUtil.getProperties(
					_PROPERTY_PREFIX, true);

				if (_licenseTestProperties.isEmpty()) {
					throw new IllegalArgumentException(
						"Missing license test properties");
				}

				try {
					_lifecycleActionField = findField(
						classLoader, getProperty("lifecycle.action.field"));
					_validateMethod = findMethod(
						classLoader, getProperty("validate.method"));
					_versionMethod = findMethod(
						classLoader, getProperty("version.method"));

					ByteBuddyAgent.install();

					_instrumentation = ByteBuddyAgent.getInstrumentation();
				}
				catch (Exception exception) {
					throw new ExceptionInInitializerError(exception);
				}
			}
		}

	}

}