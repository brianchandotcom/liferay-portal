/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.production.readiness;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.search.elasticsearch8.configuration.ElasticsearchConfiguration;

import java.io.File;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

/**
 * @author Lily Chi
 */
public class ProductionReadinessRuleUtil {

	public static Collection<ProductionReadinessResult> check() {
		return TransformUtil.transform(
			_productionReadinessResultSuppliers, Supplier::get);
	}

	private static ProductionReadinessResult _checkBetaLanguages() {
		List<String> betaLocales = List.of(PropsValues.LOCALES_BETA);

		return _getLanguagesProductionReadinessResult(
			"languages-beta",
			ListUtil.filter(
				List.of(PropsValues.LOCALES_ENABLED), betaLocales::contains));
	}

	private static ProductionReadinessResult _checkCounterIncrement() {
		int counterIncrement = GetterUtil.getInteger(
			PropsUtil.get("counter.increment"));

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION, "counter-increment"
			).currentValue(
				String.valueOf(counterIncrement)
			);

		if (counterIncrement < 2000) {
			return builder.fail();
		}

		return builder.pass();
	}

	private static ProductionReadinessResult _checkDatabaseConfiguration() {
		int jdbcMaxPoolSize = GetterUtil.getInteger(
			PropsUtil.get("jdbc.default.maximumPoolSize"));

		int tomcatMaxThreads = _getMaxThreads();

		if ((jdbcMaxPoolSize <= 0) || (tomcatMaxThreads <= 0)) {
			return null;
		}

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				"database-configuration", "pool-vs-thread-size"
			).currentValue(
				StringBundler.concat(
					"DB Pool Size=", jdbcMaxPoolSize, ", Tomcat Threads=",
					tomcatMaxThreads)
			).recommendedValue(
				"DB Pool Size >= Tomcat Threads"
			);

		if (jdbcMaxPoolSize >= tomcatMaxThreads) {
			return builder.pass();
		}

		return builder.fail();
	}

	private static ProductionReadinessResult _checkDLImagePreviewDPI() {
		int dpi = PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI;

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION,
				"dl-image-preview-dpi"
			).currentValue(
				String.valueOf(dpi)
			);

		if (dpi > _MAX_DL_IMAGE_PREVIEW_DPI) {
			return builder.recommendedValue(
				String.valueOf(_MAX_DL_IMAGE_PREVIEW_DPI)
			).fail();
		}

		return builder.pass();
	}

	private static ProductionReadinessResult _checkDLPreviewForking() {
		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION, "dl-preview-forking"
			).currentValue(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED + "=" +
					PropsValues.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED
			).messageParameters(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED
			).recommendedValue(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED + "=true"
			);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED) {
			return builder.pass();
		}

		return builder.fail();
	}

	private static ProductionReadinessResult _checkExplicitGCDisabled() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		List<String> inputArguments = runtimeMXBean.getInputArguments();

		boolean disabled = inputArguments.contains("-XX:+DisableExplicitGC");

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_JVM_AND_INFRASTRUCTURE_VALIDATION,
				"explicit-gc-disabled");

		if (disabled) {
			return builder.currentValue(
				"-XX:+DisableExplicitGC"
			).pass();
		}

		return builder.recommendedValue(
			"-XX:+DisableExplicitGC"
		).fail();
	}

	private static ProductionReadinessResult _checkFileStoreImplementation() {
		String dlStoreImpl = PropsValues.DL_STORE_IMPL;

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION,
				"file-store-implementation"
			).currentValue(
				dlStoreImpl
			);

		if (_recommendedDLStoreImplClassNames.contains(dlStoreImpl)) {
			return builder.pass();
		}

		return builder.recommendedValue(
			StringUtil.merge(_recommendedDLStoreImplClassNames)
		).fail();
	}

	private static ProductionReadinessResult _checkGarbageCollectorType() {
		List<GarbageCollectorMXBean> garbageCollectorMXBeans =
			ManagementFactory.getGarbageCollectorMXBeans();

		List<String> gcNames = new ArrayList<>();

		boolean pass = false;

		for (GarbageCollectorMXBean garbageCollectorMXBean :
				garbageCollectorMXBeans) {

			String name = garbageCollectorMXBean.getName();

			gcNames.add(name);

			if (name.contains("G1") || name.contains("Shenandoah") ||
				name.contains("ZGC")) {

				pass = true;
			}
		}

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_JVM_AND_INFRASTRUCTURE_VALIDATION,
				"garbage-collector-type"
			).currentValue(
				StringUtil.merge(gcNames, ", ")
			).recommendedValue(
				"G1, Shenandoah, or ZGC"
			);

		if (pass) {
			return builder.pass();
		}

		return builder.fail();
	}

	private static ProductionReadinessResult _checkHeapAllocationConsistency() {
		MemoryUsage heapMemoryUsage = _getHeapMemoryUsage();

		long xmsBytes = heapMemoryUsage.getInit();
		long xmxBytes = heapMemoryUsage.getMax();

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_JVM_AND_INFRASTRUCTURE_VALIDATION,
				"heap-allocation-consistency"
			).currentValue(
				StringBundler.concat(
					"Xms=", xmsBytes / 1024 / 1024, "MB, Xmx=",
					xmxBytes / 1024 / 1024, "MB")
			);

		if ((xmsBytes > 0) && (xmsBytes == xmxBytes)) {
			return builder.pass();
		}

		return builder.fail();
	}

	private static ProductionReadinessResult _checkHeapSizeUpperLimit() {
		MemoryUsage heapMemoryUsage = _getHeapMemoryUsage();

		long xmxBytes = heapMemoryUsage.getMax();

		double maxMemoryGB = xmxBytes / (1024.0 * 1024.0 * 1024.0);

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_JVM_AND_INFRASTRUCTURE_VALIDATION,
				"heap-size-upper-limit"
			).currentValue(
				maxMemoryGB + "GB"
			);

		if (maxMemoryGB <= 32.0) {
			return builder.pass();
		}

		return builder.fail();
	}

	private static ProductionReadinessResult _checkHugePagesConfiguration() {
		MemoryUsage heapMemoryUsage = _getHeapMemoryUsage();

		long xmxBytes = heapMemoryUsage.getMax();

		double maxMemoryGB = xmxBytes / (1024.0 * 1024.0 * 1024.0);

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_JVM_AND_INFRASTRUCTURE_VALIDATION,
				"huge-pages-configuration");

		if (maxMemoryGB <= 4.0) {
			return builder.messageKeySuffix(
				"heap-under-4gb"
			).pass();
		}

		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		List<String> inputArguments = runtimeMXBean.getInputArguments();

		boolean useLargePages = false;
		String largePageSizeArg = null;

		for (String inputArgument : inputArguments) {
			if (inputArgument.equals("-XX:+UseLargePages")) {
				useLargePages = true;
			}
			else if (inputArgument.startsWith(
						_PREFIX_LARGE_PAGE_SIZE_IN_BYTES)) {

				largePageSizeArg = inputArgument.substring(
					_PREFIX_LARGE_PAGE_SIZE_IN_BYTES.length());
			}
		}

		if (!useLargePages) {
			return builder.messageKeySuffix(
				"no-large-pages"
			).recommendedValue(
				"-XX:+UseLargePages"
			).fail();
		}

		if (largePageSizeArg == null) {
			return builder.messageKeySuffix(
				"missing-large-page-size"
			).fail();
		}

		long osHugePageSize = _getOSHugePageSize();

		if (osHugePageSize > 0) {
			long configLargePageSize = _parseSize(largePageSizeArg);

			if (configLargePageSize != osHugePageSize) {
				return builder.currentValue(
					StringBundler.concat(
						"-XX:LargePageSizeInBytes = ", largePageSizeArg,
						", OS's huge page size = ", osHugePageSize / 1024, "kB")
				).messageKeySuffix(
					"size-mismatch"
				).fail();
			}
		}

		return builder.messageKeySuffix(
			"configured"
		).pass();
	}

	private static ProductionReadinessResult _checkJMXConfigurationDisabled() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		List<String> inputArguments = runtimeMXBean.getInputArguments();

		boolean jmxEnabled = false;
		String enabledArg = null;

		for (String inputArgument : inputArguments) {
			if (inputArgument.startsWith("-Dcom.sun.management.jmxremote")) {
				jmxEnabled = true;
				enabledArg = inputArgument;

				break;
			}
		}

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_JVM_AND_INFRASTRUCTURE_VALIDATION,
				"jmx-configuration-disabled");

		if (jmxEnabled) {
			return builder.currentValue(
				"JMX Configuration has been enabled (" + enabledArg + ")"
			).fail();
		}

		return builder.pass();
	}

	private static ProductionReadinessResult _checkJSPEngineSettings() {
		if (!ServerDetector.isTomcat()) {
			return null;
		}

		String catalinaBase = System.getProperty("catalina.base");

		if (Validator.isNull(catalinaBase)) {
			catalinaBase = System.getProperty("catalina.home");
		}

		if (Validator.isNull(catalinaBase)) {
			return null;
		}

		File webXmlFile = new File(catalinaBase, "conf/web.xml");

		if (!webXmlFile.exists()) {
			return null;
		}

		try {
			String content = FileUtil.read(webXmlFile);

			Document document = SAXReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			Boolean development = null;
			Boolean mappedFile = null;

			for (Element element : rootElement.elements("servlet")) {
				String servletName = element.elementText("servlet-name");

				if (!servletName.equals("jsp")) {
					continue;
				}

				for (Element initParamElement :
						element.elements("init-param")) {

					String paramName = initParamElement.elementText(
						"param-name");
					String paramValue = initParamElement.elementText(
						"param-value");

					if (paramName.equals("development")) {
						development = GetterUtil.getBoolean(paramValue);
					}
					else if (paramName.equals("mappedFile")) {
						mappedFile = GetterUtil.getBoolean(paramValue);
					}
				}
			}

			if ((development == null) || (mappedFile == null)) {
				return ProductionReadinessResult.builder(
					_CATEGORY_JVM_AND_INFRASTRUCTURE_VALIDATION,
					"jsp-engine-settings"
				).currentValue(
					"development or mappedfile is not set, Tomcat will use " +
						"the default value development=true or mappedfile=true"
				).recommendedValue(
					"development=false, mappedfile=false"
				).fail();
			}

			ProductionReadinessResult.Builder builder =
				ProductionReadinessResult.builder(
					_CATEGORY_JVM_AND_INFRASTRUCTURE_VALIDATION,
					"jsp-engine-settings"
				).currentValue(
					StringBundler.concat(
						"development=", development, ", mappedfile=",
						mappedFile)
				).recommendedValue(
					"development=false, mappedfile=false"
				);

			if (!development && !mappedFile) {
				return builder.pass();
			}

			return builder.fail();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return null;
	}

	private static ProductionReadinessResult _checkJSPReloading() {
		boolean directServletContextReload = GetterUtil.getBoolean(
			PropsUtil.get("direct.servlet.context.reload"));

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION, "jsp-reloading"
			).currentValue(
				"direct.servlet.context.reload=" + directServletContextReload
			).recommendedValue(
				"direct.servlet.context.reload=false"
			);

		if (directServletContextReload) {
			return builder.fail();
		}

		return builder.pass();
	}

	private static ProductionReadinessResult _checkPasswordEncryption() {
		String algorithm = PropsUtil.get("passwords.encryption.algorithm");

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION, "password-encryption"
			).currentValue(
				algorithm
			).recommendedValue(
				"PBKDF2WithHmacSHA1/160/1300000 (or stronger)"
			);

		if (_isStrongerThanPBKDF2(algorithm)) {
			return builder.pass();
		}

		return builder.fail();
	}

	private static ProductionReadinessResult _checkPortalDeveloperProperties() {
		boolean hasDeveloperProperties = ArrayUtil.contains(
			PropsUtil.getArray("include-and-override"),
			"portal-developer.properties");

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION,
				"portal-developer-properties");

		if (hasDeveloperProperties) {
			return builder.currentValue(
				"portal-developer.properties included"
			).fail();
		}

		return builder.currentValue(
			"portal-developer.properties is not included"
		).pass();
	}

	private static ProductionReadinessResult _checkPreventDiagnosticOverhead() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		List<String> inputArguments = runtimeMXBean.getInputArguments();

		boolean unlocked = inputArguments.contains(
			"-XX:+UnlockDiagnosticVMOptions");

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_JVM_AND_INFRASTRUCTURE_VALIDATION,
				"prevent-diagnostic-overhead");

		if (unlocked) {
			return builder.currentValue(
				"-XX:+UnlockDiagnosticVMOptions"
			).fail();
		}

		return builder.pass();
	}

	private static ProductionReadinessResult _checkSidecarDetection() {
		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				"search-engine-connectivity-validation", "sidecar-detection");

		try {
			ElasticsearchConfiguration elasticsearchConfiguration =
				ConfigurationProviderUtil.getSystemConfiguration(
					ElasticsearchConfiguration.class);

			if (elasticsearchConfiguration.productionModeEnabled()) {
				return builder.pass();
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return builder.fail();
	}

	private static ProductionReadinessResult _checkUnusedLanguages() {
		List<String> enabledLocales = List.of(PropsValues.LOCALES_ENABLED);

		return _getLanguagesProductionReadinessResult(
			"languages-unused",
			ListUtil.filter(
				List.of(PropsValues.LOCALES),
				locale -> !enabledLocales.contains(locale)));
	}

	private static MemoryUsage _getHeapMemoryUsage() {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

		return memoryMXBean.getHeapMemoryUsage();
	}

	private static ProductionReadinessResult
		_getLanguagesProductionReadinessResult(
			String key, List<String> locales) {

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION, key);

		if (locales.isEmpty()) {
			return builder.pass();
		}

		String localesString = StringUtil.merge(locales);

		return builder.currentValue(
			localesString
		).messageParameters(
			localesString
		).fail();
	}

	private static int _getMaxThreads() {
		try {
			MBeanServer mBeanServer =
				ManagementFactory.getPlatformMBeanServer();

			ObjectName objectName = new ObjectName(
				"Catalina:type=ThreadPool,name=*");

			Set<ObjectName> objectNames = mBeanServer.queryNames(
				objectName, null);

			int maxThreads = 0;

			for (ObjectName name : objectNames) {
				int threads = GetterUtil.getInteger(
					mBeanServer.getAttribute(name, "maxThreads"));

				if (threads > maxThreads) {
					maxThreads = threads;
				}
			}

			return maxThreads;
		}
		catch (AttributeNotFoundException | InstanceNotFoundException |
			   MalformedObjectNameException | MBeanException |
			   ReflectionException exception) {

			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return 200;
		}
	}

	private static long _getOSHugePageSize() {
		File file = new File("/proc/meminfo");

		if (!file.exists()) {
			return -1;
		}

		try {
			String content = FileUtil.read(file);

			for (String line : StringUtil.splitLines(content)) {
				if (line.startsWith(_PREFIX_HUGEPAGESIZE)) {
					String sizeStr = line.substring(
						_PREFIX_HUGEPAGESIZE.length()
					).trim();

					return _parseSize(StringUtil.removeSubstring(sizeStr, " "));
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return -1;
	}

	private static boolean _isStrongerThanPBKDF2(String algorithm) {
		if (algorithm == null) {
			return false;
		}

		if (algorithm.equals("BCRYPT") || algorithm.startsWith("BCRYPT/") ||
			algorithm.equals("SCRYPT")) {

			return true;
		}

		if (algorithm.startsWith("PBKDF2WithHmacSHA1/")) {
			String[] parts = algorithm.split("/");

			if (parts.length >= 3) {
				int rounds = GetterUtil.getInteger(parts[2]);

				if (rounds >= 1300000) {
					return true;
				}
			}
		}

		return false;
	}

	private static long _parseSize(String sizeStr) {
		if (sizeStr == null) {
			return -1;
		}

		sizeStr = StringUtil.toLowerCase(sizeStr.trim());

		long multiplier = 1;

		if (sizeStr.endsWith("k") || sizeStr.endsWith("kb")) {
			multiplier = 1024;
		}
		else if (sizeStr.endsWith("m") || sizeStr.endsWith("mb")) {
			multiplier = 1024 * 1024;
		}
		else if (sizeStr.endsWith("g") || sizeStr.endsWith("gb")) {
			multiplier = 1024 * 1024 * 1024;
		}

		if (multiplier > 1) {
			sizeStr = StringUtil.extractDigits(sizeStr);
		}

		return GetterUtil.getLong(sizeStr) * multiplier;
	}

	private static final String _CATEGORY_JVM_AND_INFRASTRUCTURE_VALIDATION =
		"jvm-and-infrastructure-validation";

	private static final String _CATEGORY_PORTAL_PROPERTIES_CONFIGURATION =
		"portal-properties-configuration";

	private static final int _MAX_DL_IMAGE_PREVIEW_DPI = 75;

	private static final String _PREFIX_HUGEPAGESIZE = "Hugepagesize:";

	private static final String _PREFIX_LARGE_PAGE_SIZE_IN_BYTES =
		"-XX:LargePageSizeInBytes=";

	private static final Log _log = LogFactoryUtil.getLog(
		ProductionReadinessRuleUtil.class);

	private static final List<Supplier<ProductionReadinessResult>>
		_productionReadinessResultSuppliers = List.of(
			ProductionReadinessRuleUtil::_checkBetaLanguages,
			ProductionReadinessRuleUtil::_checkCounterIncrement,
			ProductionReadinessRuleUtil::_checkDatabaseConfiguration,
			ProductionReadinessRuleUtil::_checkDLImagePreviewDPI,
			ProductionReadinessRuleUtil::_checkDLPreviewForking,
			ProductionReadinessRuleUtil::_checkExplicitGCDisabled,
			ProductionReadinessRuleUtil::_checkFileStoreImplementation,
			ProductionReadinessRuleUtil::_checkGarbageCollectorType,
			ProductionReadinessRuleUtil::_checkHeapAllocationConsistency,
			ProductionReadinessRuleUtil::_checkHeapSizeUpperLimit,
			ProductionReadinessRuleUtil::_checkHugePagesConfiguration,
			ProductionReadinessRuleUtil::_checkJMXConfigurationDisabled,
			ProductionReadinessRuleUtil::_checkJSPEngineSettings,
			ProductionReadinessRuleUtil::_checkJSPReloading,
			ProductionReadinessRuleUtil::_checkPasswordEncryption,
			ProductionReadinessRuleUtil::_checkPortalDeveloperProperties,
			ProductionReadinessRuleUtil::_checkPreventDiagnosticOverhead,
			ProductionReadinessRuleUtil::_checkSidecarDetection,
			ProductionReadinessRuleUtil::_checkUnusedLanguages);
	private static final List<String> _recommendedDLStoreImplClassNames =
		List.of(
			"com.liferay.portal.store.azure.AzureStore",
			"com.liferay.portal.store.file.system.AdvancedFileSystemStore",
			"com.liferay.portal.store.gcs.GCSStore",
			"com.liferay.portal.store.s3.IBMS3Store",
			"com.liferay.portal.store.s3.S3Store");

}