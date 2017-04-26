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

import java.io.File;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

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
	protected List<SourceCheck> getModuleSourceChecks() throws Exception {
		List<SourceCheck> moduleSourceChecks = new ArrayList<>();

		moduleSourceChecks.add(
			new JavaModuleExtendedObjectClassDefinitionCheck());

		boolean checkRegistryInTestClasses = GetterUtil.getBoolean(
			System.getProperty(
				"source.formatter.check.registry.in.test.classes"));

		moduleSourceChecks.add(
			new JavaModuleIllegalImportsCheck(checkRegistryInTestClasses));

		moduleSourceChecks.add(new JavaModuleInternalImportsCheck());
		moduleSourceChecks.add(new JavaModuleServiceProxyFactoryCheck());
		moduleSourceChecks.add(new JavaModuleTestCheck());
		moduleSourceChecks.add(new JavaOSGiReferenceCheck());

		return moduleSourceChecks;
	}

	@Override
	protected List<SourceCheck> getSourceChecks() throws Exception {
		List<SourceCheck> sourceChecks = _getFileChecks();

		sourceChecks.addAll(_getJavaTermChecks());

		return sourceChecks;
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

	private List<SourceCheck> _getFileChecks() throws Exception {
		List<SourceCheck> fileChecks = new ArrayList<>();

		fileChecks.add(new JavaWhitespaceCheck());

		fileChecks.add(new CopyrightCheck(getCopyright()));
		fileChecks.add(new EmptyArrayCheck());
		fileChecks.add(new EmptyCollectionCheck());
		fileChecks.add(new GetterUtilCheck());
		fileChecks.add(new Java2HTMLCheck());
		fileChecks.add(new JavaAnnotationsCheck());
		fileChecks.add(new JavaAssertEqualsCheck());
		fileChecks.add(new JavaBooleanUsageCheck());
		fileChecks.add(new JavaCombineLinesCheck());
		fileChecks.add(new JavaDataAccessConnectionCheck());
		fileChecks.add(new JavaDiamondOperatorCheck());
		fileChecks.add(new JavaDeserializationSecurityCheck());
		fileChecks.add(new JavaEmptyLinesCheck());
		fileChecks.add(new JavaExceptionCheck());
		fileChecks.add(new JavaHibernateSQLCheck());
		fileChecks.add(new JavaIfStatementCheck());
		fileChecks.add(new JavaIllegalImportsCheck());
		fileChecks.add(new JavaImportsCheck());
		fileChecks.add(new JavaInterfaceCheck());
		fileChecks.add(new JavaIOExceptionCheck());
		fileChecks.add(new JavaLineBreakCheck());
		fileChecks.add(new JavaLogClassNameCheck());
		fileChecks.add(new JavaLogLevelCheck());
		fileChecks.add(new JavaLongLinesCheck());
		fileChecks.add(new JavaPackagePathCheck());
		fileChecks.add(new JavaProcessCallableCheck());
		fileChecks.add(new JavaResultSetCheck());
		fileChecks.add(new JavaSeeAnnotationCheck());
		fileChecks.add(new JavaStopWatchCheck());
		fileChecks.add(new JavaStylingCheck());
		fileChecks.add(new JavaSystemExceptionCheck());
		fileChecks.add(new MethodCallsOrderCheck());
		fileChecks.add(new PrimitiveWrapperInstantiationCheck());
		fileChecks.add(new PrincipalExceptionCheck());
		fileChecks.add(new SessionKeysCheck());
		fileChecks.add(new StringBundlerCheck());
		fileChecks.add(new StringUtilCheck());
		fileChecks.add(new UnparameterizedClassCheck());
		fileChecks.add(new ValidatorEqualsCheck());

		if (portalSource || subrepository) {
			fileChecks.add(new JavaFinderCacheCheck());
			fileChecks.add(new JavaSystemEventAnnotationCheck());
			fileChecks.add(new JavaVerifyUpgradeConnectionCheck());
			fileChecks.add(new JavaUpgradeClassCheck());
			fileChecks.add(new JavaXMLSecurityCheck());
			fileChecks.add(new ResourceBundleCheck());
			fileChecks.add(new StringMethodsCheck());

			if (!GetterUtil.getBoolean(
					getProperty("allow.use.service.util.in.service.impl"))) {

				fileChecks.add(new JavaServiceUtilCheck());
			}
		}
		else {
			if (GetterUtil.getBoolean(
					getProperty("use.portal.compat.import"))) {

				fileChecks.add(new CompatClassImportsCheck());
			}
		}

		if (portalSource) {
			fileChecks.add(new LanguageKeysCheck());
		}

		if (GetterUtil.getBoolean(
				getProperty("add.missing.deprecation.release.version"))) {

			fileChecks.add(new JavaDeprecatedJavadocCheck());
		}

		return fileChecks;
	}

	private List<SourceCheck> _getJavaTermChecks() throws Exception {
		List<SourceCheck> javaTermChecks = new ArrayList<>();

		javaTermChecks.add(new JavaTermOrderCheck());

		javaTermChecks.add(new JavaTermDividersCheck());

		javaTermChecks.add(new JavaStaticBlockCheck());

		javaTermChecks.add(new JavaBooleanStatementCheck());
		javaTermChecks.add(new JavaConstructorParameterOrder());
		javaTermChecks.add(new JavaConstructorSuperCallCheck());
		javaTermChecks.add(new JavaIndexableCheck());
		javaTermChecks.add(new JavaLocalSensitiveComparisonCheck());
		javaTermChecks.add(new JavaRedundantConstructorCheck());
		javaTermChecks.add(new JavaReturnStatementCheck());
		javaTermChecks.add(new JavaServiceImplCheck());
		javaTermChecks.add(new JavaSignatureStylingCheck());
		javaTermChecks.add(new JavaTermStylingCheck());

		if (portalSource || subrepository) {
			javaTermChecks.add(new JavaCleanUpMethodVariablesCheck());
			javaTermChecks.add(new JavaTestMethodAnnotationsCheck());
			javaTermChecks.add(new JavaVariableTypeCheck());
		}

		return javaTermChecks;
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

	private static final String[] _INCLUDES = new String[] {"**/*.java"};

	private final Set<File> _ungeneratedFiles = new CopyOnWriteArraySet<>();

}