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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.CompatClassImportsCheck;
import com.liferay.source.formatter.checks.CopyrightCheck;
import com.liferay.source.formatter.checks.EmptyArrayCheck;
import com.liferay.source.formatter.checks.EmptyCollectionCheck;
import com.liferay.source.formatter.checks.GetterUtilCheck;
import com.liferay.source.formatter.checks.Java2HTMLCheck;
import com.liferay.source.formatter.checks.JavaAnnotationsCheck;
import com.liferay.source.formatter.checks.JavaAssertEqualsCheck;
import com.liferay.source.formatter.checks.JavaBooleanStatementCheck;
import com.liferay.source.formatter.checks.JavaBooleanUsageCheck;
import com.liferay.source.formatter.checks.JavaCleanUpMethodVariablesCheck;
import com.liferay.source.formatter.checks.JavaCombineLinesCheck;
import com.liferay.source.formatter.checks.JavaConstructorParameterOrder;
import com.liferay.source.formatter.checks.JavaConstructorSuperCallCheck;
import com.liferay.source.formatter.checks.JavaDataAccessConnectionCheck;
import com.liferay.source.formatter.checks.JavaDeprecatedJavadocCheck;
import com.liferay.source.formatter.checks.JavaDeserializationSecurityCheck;
import com.liferay.source.formatter.checks.JavaDiamondOperatorCheck;
import com.liferay.source.formatter.checks.JavaEmptyLinesCheck;
import com.liferay.source.formatter.checks.JavaExceptionCheck;
import com.liferay.source.formatter.checks.JavaFinderCacheCheck;
import com.liferay.source.formatter.checks.JavaHibernateSQLCheck;
import com.liferay.source.formatter.checks.JavaIOExceptionCheck;
import com.liferay.source.formatter.checks.JavaIfStatementCheck;
import com.liferay.source.formatter.checks.JavaIllegalImportsCheck;
import com.liferay.source.formatter.checks.JavaImportsCheck;
import com.liferay.source.formatter.checks.JavaIndexableCheck;
import com.liferay.source.formatter.checks.JavaInterfaceCheck;
import com.liferay.source.formatter.checks.JavaLineBreakCheck;
import com.liferay.source.formatter.checks.JavaLocalSensitiveComparisonCheck;
import com.liferay.source.formatter.checks.JavaLogClassNameCheck;
import com.liferay.source.formatter.checks.JavaLogLevelCheck;
import com.liferay.source.formatter.checks.JavaLongLinesCheck;
import com.liferay.source.formatter.checks.JavaModuleExtendedObjectClassDefinitionCheck;
import com.liferay.source.formatter.checks.JavaModuleIllegalImportsCheck;
import com.liferay.source.formatter.checks.JavaModuleInternalImportsCheck;
import com.liferay.source.formatter.checks.JavaModuleServiceProxyFactoryCheck;
import com.liferay.source.formatter.checks.JavaModuleTestCheck;
import com.liferay.source.formatter.checks.JavaOSGiReferenceCheck;
import com.liferay.source.formatter.checks.JavaPackagePathCheck;
import com.liferay.source.formatter.checks.JavaProcessCallableCheck;
import com.liferay.source.formatter.checks.JavaRedundantConstructorCheck;
import com.liferay.source.formatter.checks.JavaResultSetCheck;
import com.liferay.source.formatter.checks.JavaReturnStatementCheck;
import com.liferay.source.formatter.checks.JavaSeeAnnotationCheck;
import com.liferay.source.formatter.checks.JavaServiceImplCheck;
import com.liferay.source.formatter.checks.JavaServiceUtilCheck;
import com.liferay.source.formatter.checks.JavaSignatureStylingCheck;
import com.liferay.source.formatter.checks.JavaStaticBlockCheck;
import com.liferay.source.formatter.checks.JavaStopWatchCheck;
import com.liferay.source.formatter.checks.JavaStylingCheck;
import com.liferay.source.formatter.checks.JavaSystemEventAnnotationCheck;
import com.liferay.source.formatter.checks.JavaSystemExceptionCheck;
import com.liferay.source.formatter.checks.JavaTermDividersCheck;
import com.liferay.source.formatter.checks.JavaTermOrderCheck;
import com.liferay.source.formatter.checks.JavaTermStylingCheck;
import com.liferay.source.formatter.checks.JavaTestMethodAnnotationsCheck;
import com.liferay.source.formatter.checks.JavaUpgradeClassCheck;
import com.liferay.source.formatter.checks.JavaVariableTypeCheck;
import com.liferay.source.formatter.checks.JavaVerifyUpgradeConnectionCheck;
import com.liferay.source.formatter.checks.JavaWhitespaceCheck;
import com.liferay.source.formatter.checks.JavaXMLSecurityCheck;
import com.liferay.source.formatter.checks.LanguageKeysCheck;
import com.liferay.source.formatter.checks.MethodCallsOrderCheck;
import com.liferay.source.formatter.checks.PrimitiveWrapperInstantiationCheck;
import com.liferay.source.formatter.checks.PrincipalExceptionCheck;
import com.liferay.source.formatter.checks.ResourceBundleCheck;
import com.liferay.source.formatter.checks.SessionKeysCheck;
import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.checks.StringBundlerCheck;
import com.liferay.source.formatter.checks.StringMethodsCheck;
import com.liferay.source.formatter.checks.StringUtilCheck;
import com.liferay.source.formatter.checks.UnparameterizedClassCheck;
import com.liferay.source.formatter.checks.ValidatorEqualsCheck;
import com.liferay.source.formatter.checkstyle.util.CheckStyleUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] includes = getIncludes();

		if (ArrayUtil.isEmpty(includes)) {
			return new ArrayList<>();
		}

		Collection<String> fileNames = null;

		if (portalSource || subrepository) {
			fileNames = _getPortalJavaFiles(includes);
		}
		else {
			fileNames = _getPluginJavaFiles(includes);
		}

		return new ArrayList<>(fileNames);
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<SourceCheck> getModuleSourceChecks() {
		return _moduleSourceChecks;
	}

	@Override
	protected List<SourceCheck> getSourceChecks() {
		return _sourceChecks;
	}

	@Override
	protected void populateModuleSourceChecks() throws Exception {
		_moduleSourceChecks.add(
			new JavaModuleExtendedObjectClassDefinitionCheck());

		boolean checkRegistryInTestClasses = GetterUtil.getBoolean(
			System.getProperty(
				"source.formatter.check.registry.in.test.classes"));

		_moduleSourceChecks.add(
			new JavaModuleIllegalImportsCheck(checkRegistryInTestClasses));

		_moduleSourceChecks.add(new JavaModuleInternalImportsCheck());
		_moduleSourceChecks.add(new JavaModuleServiceProxyFactoryCheck());
		_moduleSourceChecks.add(new JavaModuleTestCheck());
		_moduleSourceChecks.add(
			new JavaOSGiReferenceCheck(
				_getModuleFileNamesMap(),
				getPropertyList("service.reference.util.class.names")));
	}

	@Override
	protected void populateSourceChecks() throws Exception {
		_populateFileChecks();
		_populateJavaTermChecks();
	}

	@Override
	protected void postFormat() throws Exception {
		_processCheckStyle();
	}

	@Override
	protected String processSourceChecks(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		if (_hasGeneratedTag(content)) {
			return content;
		}

		_ungeneratedFiles.add(file);

		return super.processSourceChecks(file, fileName, absolutePath, content);
	}

	private List<String> _getAnnotationsExclusions() {
		return ListUtil.fromArray(
			new String[] {
				"ArquillianResource", "Autowired", "BeanReference", "Captor",
				"Inject", "Mock", "Parameter", "Reference", "ServiceReference",
				"SuppressWarnings", "Value"
			});
	}

	private Map<String, String> _getDefaultPrimitiveValues() {
		return MapUtil.fromArray(
			new String[] {
				"boolean", "false", "char", "'\\\\0'", "byte", "0", "double",
				"0\\.0", "float", "0\\.0", "int", "0", "long", "0", "short", "0"
			});
	}

	private Set<String> _getImmutableFieldTypes() {
		Set<String> immutableFieldTypes = SetUtil.fromArray(
			new String[] {
				"boolean", "byte", "char", "double", "float", "int", "long",
				"short", "Boolean", "Byte", "Character", "Class", "Double",
				"Float", "Int", "Long", "Number", "Short", "String"
			});

		immutableFieldTypes.addAll(getPropertyList("immutable.field.types"));

		return immutableFieldTypes;
	}

	private Map<String, String> _getModuleFileNamesMap() throws Exception {
		Map<String, String> moduleFileNamesMap = new HashMap<>();

		List<String> fileNames = new ArrayList<>();

		String moduleRootDirLocation = "modules/";

		for (int i = 0; i < 6; i++) {
			File file = new File(
				sourceFormatterArgs.getBaseDirName() + moduleRootDirLocation);

			if (file.exists()) {
				fileNames = getFileNames(
					sourceFormatterArgs.getBaseDirName() +
						moduleRootDirLocation,
					new String[0], getIncludes());

				break;
			}

			moduleRootDirLocation = "../" + moduleRootDirLocation;
		}

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			String className = StringUtil.replace(
				fileName, CharPool.SLASH, CharPool.PERIOD);

			int pos = className.lastIndexOf(".com.liferay.");

			className = className.substring(pos + 1, fileName.length() - 5);

			moduleFileNamesMap.put(className, fileName);
		}

		return moduleFileNamesMap;
	}

	private String[] _getPluginExcludes(String pluginDirectoryName) {
		return new String[] {
			pluginDirectoryName + "**/model/*Clp.java",
			pluginDirectoryName + "**/model/impl/*BaseImpl.java",
			pluginDirectoryName + "**/model/impl/*Model.java",
			pluginDirectoryName + "**/model/impl/*ModelImpl.java",
			pluginDirectoryName + "**/service/**/service/*Service.java",
			pluginDirectoryName + "**/service/**/service/*ServiceClp.java",
			pluginDirectoryName + "**/service/**/service/*ServiceFactory.java",
			pluginDirectoryName + "**/service/**/service/*ServiceUtil.java",
			pluginDirectoryName + "**/service/**/service/*ServiceWrapper.java",
			pluginDirectoryName + "**/service/**/service/ClpSerializer.java",
			pluginDirectoryName +
				"**/service/**/service/messaging/*ClpMessageListener.java",
			pluginDirectoryName +
				"**/service/**/service/persistence/*Finder.java",
			pluginDirectoryName +
				"**/service/**/service/persistence/*Util.java",
			pluginDirectoryName + "**/service/base/*ServiceBaseImpl.java",
			pluginDirectoryName + "**/service/base/*ServiceClpInvoker.java",
			pluginDirectoryName + "**/service/http/*JSONSerializer.java",
			pluginDirectoryName + "**/service/http/*ServiceHttp.java",
			pluginDirectoryName + "**/service/http/*ServiceJSON.java",
			pluginDirectoryName + "**/service/http/*ServiceSoap.java",
			pluginDirectoryName + "**/tools/templates/**"
		};
	}

	private Collection<String> _getPluginJavaFiles(String[] includes)
		throws Exception {

		Collection<String> fileNames = new TreeSet<>();

		String[] excludes = _getPluginExcludes(StringPool.BLANK);

		fileNames.addAll(getFileNames(excludes, includes));

		return fileNames;
	}

	private String _getPortalCustomSQLContent() throws Exception {
		if (!portalSource) {
			return null;
		}

		if (_portalCustomSQLContent != null) {
			return _portalCustomSQLContent;
		}

		File portalCustomSQLFile = getFile(
			"portal-impl/src/custom-sql/default.xml", PORTAL_MAX_DIR_LEVEL);

		String portalCustomSQLContent = FileUtil.read(portalCustomSQLFile);

		Matcher matcher = _customSQLFilePattern.matcher(portalCustomSQLContent);

		while (matcher.find()) {
			File customSQLFile = getFile(
				"portal-impl/src/" + matcher.group(1), PORTAL_MAX_DIR_LEVEL);

			if (customSQLFile != null) {
				portalCustomSQLContent += FileUtil.read(customSQLFile);
			}
		}

		_portalCustomSQLContent = portalCustomSQLContent;

		return _portalCustomSQLContent;
	}

	private Collection<String> _getPortalJavaFiles(String[] includes)
		throws Exception {

		Collection<String> fileNames = new TreeSet<>();

		String[] excludes = new String[] {
			"**/*_IW.java", "**/counter/service/**", "**/jsp/*",
			"**/model/impl/*Model.java", "**/model/impl/*ModelImpl.java",
			"**/portal/service/**", "**/portal-client/**",
			"**/portal-web/test/**/*Test.java", "**/test/*-generated/**"
		};

		for (String directoryName : getPluginsInsideModulesDirectoryNames()) {
			excludes = ArrayUtil.append(
				excludes, _getPluginExcludes("**" + directoryName));
		}

		fileNames.addAll(getFileNames(excludes, includes));

		excludes = new String[] {
			"**/portal-client/**", "**/tools/ext_tmpl/**", "**/*_IW.java",
			"**/test/**/*PersistenceTest.java"
		};
		includes = new String[] {
			"**/com/liferay/portal/kernel/service/ServiceContext*.java",
			"**/model/BaseModel.java", "**/model/impl/BaseModelImpl.java",
			"**/portal-test/**/portal/service/**/*.java",
			"**/portal-test-integration/**/portal/service/**/*.java",
			"**/service/Base*.java",
			"**/service/PersistedModelLocalService*.java",
			"**/service/configuration/**/*.java",
			"**/service/http/*HttpTest.java", "**/service/http/*SoapTest.java",
			"**/service/http/TunnelUtil.java", "**/service/impl/*.java",
			"**/service/jms/*.java", "**/service/permission/*.java",
			"**/service/persistence/BasePersistence.java",
			"**/service/persistence/BatchSession*.java",
			"**/service/persistence/*FinderImpl.java",
			"**/service/persistence/*Query.java",
			"**/service/persistence/impl/*.java",
			"**/portal-impl/test/**/*.java", "**/util-bridges/**/*.java"
		};

		fileNames.addAll(getFileNames(excludes, includes));

		return fileNames;
	}

	private List<File> _getSuppressionsFiles() throws Exception {
		String fileName = "checkstyle-suppressions.xml";

		List<File> suppressionsFiles = new ArrayList<>();

		// Find suppressions file in portal-impl/src/

		if (portalSource) {
			File suppressionsFile = getFile(
				"portal-impl/src/" + fileName, PORTAL_MAX_DIR_LEVEL);

			if (suppressionsFile != null) {
				suppressionsFiles.add(suppressionsFile);
			}
		}

		// Find suppressions files in any parent directory

		int maxDirLevel = PLUGINS_MAX_DIR_LEVEL;
		String parentDirName = sourceFormatterArgs.getBaseDirName();

		if (portalSource || subrepository) {
			maxDirLevel = PORTAL_MAX_DIR_LEVEL - 1;
			parentDirName += "../";
		}

		for (int i = 0; i < maxDirLevel; i++) {
			File suppressionsFile = new File(parentDirName + fileName);

			if (suppressionsFile.exists()) {
				suppressionsFiles.add(suppressionsFile);
			}

			parentDirName += "../";
		}

		if (!portalSource && !subrepository) {
			return suppressionsFiles;
		}

		// Find suppressions files in any child directory

		List<String> moduleSuppressionsFileNames = getFileNames(
			new String[0], new String[] {"**/modules/**/" + fileName}, true);

		for (String moduleSuppressionsFileName : moduleSuppressionsFileNames) {
			moduleSuppressionsFileName = StringUtil.replace(
				moduleSuppressionsFileName, CharPool.BACK_SLASH,
				CharPool.SLASH);

			suppressionsFiles.add(new File(moduleSuppressionsFileName));
		}

		return suppressionsFiles;
	}

	private boolean _hasGeneratedTag(String content) {
		if ((content.contains("* @generated") || content.contains("$ANTLR")) &&
			!content.contains("hasGeneratedTag")) {

			return true;
		}
		else {
			return false;
		}
	}

	private void _populateFileChecks() throws Exception {
		_sourceChecks.add(new JavaWhitespaceCheck());

		_sourceChecks.add(new CopyrightCheck(getCopyright()));
		_sourceChecks.add(new EmptyArrayCheck());
		_sourceChecks.add(new EmptyCollectionCheck());
		_sourceChecks.add(new GetterUtilCheck());
		_sourceChecks.add(new Java2HTMLCheck());
		_sourceChecks.add(new JavaAnnotationsCheck());
		_sourceChecks.add(new JavaAssertEqualsCheck());
		_sourceChecks.add(new JavaBooleanUsageCheck());
		_sourceChecks.add(
			new JavaCombineLinesCheck(
				getExcludes(_FIT_ON_SINGLE_LINE_EXCLUDES),
				sourceFormatterArgs.getMaxLineLength()));
		_sourceChecks.add(new JavaDataAccessConnectionCheck());
		_sourceChecks.add(
			new JavaDiamondOperatorCheck(
				getExcludes(_DIAMOND_OPERATOR_EXCLUDES)));
		_sourceChecks.add(
			new JavaDeserializationSecurityCheck(
				getExcludes(_SECURE_DESERIALIZATION_EXCLUDES),
				getExcludes(RUN_OUTSIDE_PORTAL_EXCLUDES)));
		_sourceChecks.add(new JavaEmptyLinesCheck());
		_sourceChecks.add(new JavaExceptionCheck());
		_sourceChecks.add(
			new JavaHibernateSQLCheck(
				getExcludes(_HIBERNATE_SQL_QUERY_EXCLUDES)));
		_sourceChecks.add(
			new JavaIfStatementCheck(sourceFormatterArgs.getMaxLineLength()));
		_sourceChecks.add(
			new JavaIllegalImportsCheck(
				portalSource, getExcludes(_PROXY_EXCLUDES),
				getExcludes(RUN_OUTSIDE_PORTAL_EXCLUDES),
				getExcludes(_SECURE_RANDOM_EXCLUDES)));
		_sourceChecks.add(new JavaImportsCheck());
		_sourceChecks.add(new JavaInterfaceCheck());
		_sourceChecks.add(new JavaIOExceptionCheck());
		_sourceChecks.add(
			new JavaLineBreakCheck(sourceFormatterArgs.getMaxLineLength()));
		_sourceChecks.add(new JavaLogClassNameCheck());
		_sourceChecks.add(new JavaLogLevelCheck());
		_sourceChecks.add(
			new JavaLongLinesCheck(
				getExcludes(_LINE_LENGTH_EXCLUDES),
				sourceFormatterArgs.getMaxLineLength()));
		_sourceChecks.add(new JavaPackagePathCheck());
		_sourceChecks.add(new JavaProcessCallableCheck());
		_sourceChecks.add(new JavaResultSetCheck());
		_sourceChecks.add(new JavaSeeAnnotationCheck());
		_sourceChecks.add(new JavaStopWatchCheck());
		_sourceChecks.add(new JavaStylingCheck());
		_sourceChecks.add(new JavaSystemExceptionCheck());
		_sourceChecks.add(
			new MethodCallsOrderCheck(getExcludes(METHOD_CALL_SORT_EXCLUDES)));
		_sourceChecks.add(new PrimitiveWrapperInstantiationCheck());
		_sourceChecks.add(new PrincipalExceptionCheck());
		_sourceChecks.add(new SessionKeysCheck());
		_sourceChecks.add(
			new StringBundlerCheck(sourceFormatterArgs.getMaxLineLength()));
		_sourceChecks.add(new StringUtilCheck());
		_sourceChecks.add(new UnparameterizedClassCheck());
		_sourceChecks.add(new ValidatorEqualsCheck());

		if (portalSource || subrepository) {
			_sourceChecks.add(new JavaFinderCacheCheck());
			_sourceChecks.add(new JavaSystemEventAnnotationCheck());
			_sourceChecks.add(
				new JavaVerifyUpgradeConnectionCheck(
					getExcludes(_UPGRADE_DATA_ACCESS_CONNECTION_EXCLUDES)));
			_sourceChecks.add(
				new JavaUpgradeClassCheck(
					getExcludes(_UPGRADE_SERVICE_UTIL_EXCLUDES)));
			_sourceChecks.add(
				new JavaXMLSecurityCheck(
					getExcludes(RUN_OUTSIDE_PORTAL_EXCLUDES),
					getExcludes(_SECURE_XML_EXCLUDES)));
			_sourceChecks.add(
				new ResourceBundleCheck(
					getExcludes(RUN_OUTSIDE_PORTAL_EXCLUDES)));
			_sourceChecks.add(
				new StringMethodsCheck(
					getExcludes(RUN_OUTSIDE_PORTAL_EXCLUDES)));

			if (!GetterUtil.getBoolean(
					getProperty("allow.use.service.util.in.service.impl"))) {

				_sourceChecks.add(new JavaServiceUtilCheck());
			}
		}
		else {
			if (GetterUtil.getBoolean(
					getProperty("use.portal.compat.import"))) {

				_sourceChecks.add(
					new CompatClassImportsCheck(getCompatClassNamesMap()));
			}
		}

		if (portalSource) {
			_sourceChecks.add(
				new LanguageKeysCheck(
					getExcludes(LANGUAGE_KEYS_CHECK_EXCLUDES),
					getPortalLanguageProperties()));
		}

		if (GetterUtil.getBoolean(
				getProperty("add.missing.deprecation.release.version"))) {

			_sourceChecks.add(
				new JavaDeprecatedJavadocCheck(portalSource, subrepository));
		}
	}

	private void _populateJavaTermChecks() throws Exception {
		_sourceChecks.add(
			new JavaTermOrderCheck(
				getExcludes(_JAVATERM_SORT_EXCLUDES), portalSource,
				subrepository, _getPortalCustomSQLContent()));

		_sourceChecks.add(new JavaTermDividersCheck());

		_sourceChecks.add(
			new JavaStaticBlockCheck(getExcludes(_JAVATERM_SORT_EXCLUDES)));

		_sourceChecks.add(new JavaBooleanStatementCheck());
		_sourceChecks.add(new JavaConstructorParameterOrder());
		_sourceChecks.add(new JavaConstructorSuperCallCheck());
		_sourceChecks.add(new JavaIndexableCheck());
		_sourceChecks.add(new JavaLocalSensitiveComparisonCheck());
		_sourceChecks.add(new JavaRedundantConstructorCheck());
		_sourceChecks.add(new JavaReturnStatementCheck());
		_sourceChecks.add(new JavaServiceImplCheck());
		_sourceChecks.add(new JavaSignatureStylingCheck());
		_sourceChecks.add(new JavaTermStylingCheck());

		if (portalSource || subrepository) {
			_sourceChecks.add(new JavaCleanUpMethodVariablesCheck());
			_sourceChecks.add(
				new JavaTestMethodAnnotationsCheck(
					getExcludes(_TEST_ANNOTATIONS_EXCLUDES)));
			_sourceChecks.add(
				new JavaVariableTypeCheck(
					getExcludes(_CHECK_JAVA_FIELD_TYPES_EXCLUDES),
					getExcludes(_STATIC_LOG_EXCLUDES),
					_getAnnotationsExclusions(), _getDefaultPrimitiveValues(),
					_getImmutableFieldTypes()));
		}
	}

	private void _processCheckStyle() throws Exception {
		if (_ungeneratedFiles.isEmpty()) {
			return;
		}

		Set<SourceFormatterMessage> sourceFormatterMessages =
			CheckStyleUtil.process(
				_ungeneratedFiles, _getSuppressionsFiles(),
				sourceFormatterArgs.getBaseDirName());

		for (SourceFormatterMessage sourceFormatterMessage :
				sourceFormatterMessages) {

			processMessage(
				sourceFormatterMessage.getFileName(), sourceFormatterMessage);

			printError(
				sourceFormatterMessage.getFileName(),
				sourceFormatterMessage.toString());
		}
	}

	private static final String _CHECK_JAVA_FIELD_TYPES_EXCLUDES =
		"check.java.field.types.excludes";

	private static final String _DIAMOND_OPERATOR_EXCLUDES =
		"diamond.operator.excludes";

	private static final String _FIT_ON_SINGLE_LINE_EXCLUDES =
		"fit.on.single.line.excludes";

	private static final String _HIBERNATE_SQL_QUERY_EXCLUDES =
		"hibernate.sql.query.excludes";

	private static final String[] _INCLUDES = new String[] {"**/*.java"};

	private static final String _JAVATERM_SORT_EXCLUDES =
		"javaterm.sort.excludes";

	private static final String _LINE_LENGTH_EXCLUDES = "line.length.excludes";

	private static final String _PROXY_EXCLUDES = "proxy.excludes";

	private static final String _SECURE_DESERIALIZATION_EXCLUDES =
		"secure.deserialization.excludes";

	private static final String _SECURE_RANDOM_EXCLUDES =
		"secure.random.excludes";

	private static final String _SECURE_XML_EXCLUDES = "secure.xml.excludes";

	private static final String _STATIC_LOG_EXCLUDES = "static.log.excludes";

	private static final String _TEST_ANNOTATIONS_EXCLUDES =
		"test.annotations.excludes";

	private static final String _UPGRADE_DATA_ACCESS_CONNECTION_EXCLUDES =
		"upgrade.data.access.connection.excludes";

	private static final String _UPGRADE_SERVICE_UTIL_EXCLUDES =
		"upgrade.service.util.excludes";

	private final Pattern _customSQLFilePattern = Pattern.compile(
		"<sql file=\"(.*)\" \\/>");
	private final List<SourceCheck> _moduleSourceChecks = new ArrayList<>();
	private String _portalCustomSQLContent;
	private final List<SourceCheck> _sourceChecks = new ArrayList<>();
	private final Set<File> _ungeneratedFiles = new CopyOnWriteArraySet<>();

}