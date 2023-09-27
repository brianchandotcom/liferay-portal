/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.service.builder;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.portal.tools.service.builder.ServiceBuilderArgs;

import java.io.File;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.util.CollectionUtils;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class BuildServiceTask extends JavaExec {

	public BuildServiceTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set(
			"com.liferay.portal.tools.service.builder.ServiceBuilder");

		modelHintsConfigs((Object[])ServiceBuilderArgs.MODEL_HINTS_CONFIGS);
		readOnlyPrefixes((Object[])ServiceBuilderArgs.READ_ONLY_PREFIXES);
		resourceActionsConfigs(
			(Object[])ServiceBuilderArgs.RESOURCE_ACTION_CONFIGS);
		springNamespaces("beans");
		systemProperty("file.encoding", StandardCharsets.UTF_8.name());
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getApiDir() {
		File file = GradleUtil.toFile(getProject(), _apiDir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	@Input
	public String getBeanLocatorUtil() {
		return GradleUtil.toString(_beanLocatorUtil);
	}

	@Input
	public long getBuildNumber() {
		return _buildNumber;
	}

	@Input
	public int getDatabaseNameMaxLength() {
		return _databaseNameMaxLength;
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getHbmFile() {
		File file = GradleUtil.toFile(getProject(), _hbmFile);

		if (!file.exists()) {
			return null;
		}

		return file;
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getImplDir() {
		File file = GradleUtil.toFile(getProject(), _implDir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	@Input
	@Optional
	public Set<String> getIncubationFeatures() {
		return _incubationFeatures;
	}

	@InputFile
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getInputFile() {
		return GradleUtil.toFile(getProject(), _inputFile);
	}

	@Override
	public List<String> getJvmArgs() {
		List<String> jvmArgs = new ArrayList<>();

		JavaVersion javaVersion = getJavaVersion();

		if (javaVersion.isJava9Compatible()) {
			jvmArgs.add("--illegal-access=permit");
		}

		return jvmArgs;
	}

	@Input
	public List<String> getModelHintsConfigs() {
		return GradleUtil.toStringList(_modelHintsConfigs);
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getModelHintsFile() {
		File file = GradleUtil.toFile(getProject(), _modelHintsFile);

		if (!file.exists()) {
			return null;
		}

		return file;
	}

	@Input
	public String getPluginName() {
		return GradleUtil.toString(_pluginName);
	}

	@Input
	public String getPropsUtil() {
		return GradleUtil.toString(_propsUtil);
	}

	@Input
	public List<String> getReadOnlyPrefixes() {
		return GradleUtil.toStringList(_readOnlyPrefixes);
	}

	@Input
	public List<String> getResourceActionsConfigs() {
		return GradleUtil.toStringList(_resourceActionsConfigs);
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getResourcesDir() {
		File file = GradleUtil.toFile(getProject(), _resourcesDir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getSpringFile() {
		File file = GradleUtil.toFile(getProject(), _springFile);

		if (!file.exists()) {
			return null;
		}

		return file;
	}

	@Input
	public List<String> getSpringNamespaces() {
		return GradleUtil.toStringList(_springNamespaces);
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getSqlDir() {
		File file = GradleUtil.toFile(getProject(), _sqlDir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	@Input
	public String getSqlFileName() {
		return GradleUtil.toString(_sqlFileName);
	}

	@Input
	public String getSqlIndexesFileName() {
		return GradleUtil.toString(_sqlIndexesFileName);
	}

	@Input
	public String getSqlSequencesFileName() {
		return GradleUtil.toString(_sqlSequencesFileName);
	}

	@Input
	@Optional
	public String getTargetEntityName() {
		return GradleUtil.toString(_targetEntityName);
	}

	@InputDirectory
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getTestDir() {
		if (_testDir == null) {
			return null;
		}

		File file = GradleUtil.toFile(getProject(), _testDir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	@InputDirectory
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getUADDir() {
		if (_uadDir == null) {
			return null;
		}

		File file = GradleUtil.toFile(getProject(), _uadDir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	@InputDirectory
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getUADTestIntegrationDir() {
		if (_uadTestIntegrationDir == null) {
			return null;
		}

		File file = GradleUtil.toFile(getProject(), _uadTestIntegrationDir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	public BuildServiceTask incubationFeatures(
		Iterable<String> incubationFeatures) {

		GUtil.addToCollection(_incubationFeatures, incubationFeatures);

		return this;
	}

	public BuildServiceTask incubationFeatures(String... incubationFeatures) {
		return incubationFeatures(Arrays.asList(incubationFeatures));
	}

	@Input
	public boolean isAutoImportDefaultReferences() {
		return _autoImportDefaultReferences;
	}

	@Input
	public boolean isAutoNamespaceTables() {
		return _autoNamespaceTables;
	}

	@Input
	public boolean isBuildNumberIncrement() {
		return _buildNumberIncrement;
	}

	@Input
	public boolean isOsgiModule() {
		return _osgiModule;
	}

	public BuildServiceTask modelHintsConfigs(
		Iterable<Object> modelHintsConfigs) {

		GUtil.addToCollection(_modelHintsConfigs, modelHintsConfigs);

		return this;
	}

	public BuildServiceTask modelHintsConfigs(Object... modelHintsConfigs) {
		return modelHintsConfigs(Arrays.asList(modelHintsConfigs));
	}

	public BuildServiceTask readOnlyPrefixes(
		Iterable<Object> readOnlyPrefixes) {

		GUtil.addToCollection(_readOnlyPrefixes, readOnlyPrefixes);

		return this;
	}

	public BuildServiceTask readOnlyPrefixes(Object... readOnlyPrefixes) {
		return readOnlyPrefixes(Arrays.asList(readOnlyPrefixes));
	}

	public BuildServiceTask resourceActionsConfigs(
		Iterable<Object> resourceActionsConfigs) {

		GUtil.addToCollection(_resourceActionsConfigs, resourceActionsConfigs);

		return this;
	}

	public BuildServiceTask resourceActionsConfigs(
		Object... resourceActionsConfigs) {

		return resourceActionsConfigs(Arrays.asList(resourceActionsConfigs));
	}

	public void setApiDir(Object apiDir) {
		_apiDir = apiDir;
	}

	public void setAutoImportDefaultReferences(
		boolean autoImportDefaultReferences) {

		_autoImportDefaultReferences = autoImportDefaultReferences;
	}

	public void setAutoNamespaceTables(boolean autoNamespaceTables) {
		_autoNamespaceTables = autoNamespaceTables;
	}

	public void setBeanLocatorUtil(Object beanLocatorUtil) {
		_beanLocatorUtil = beanLocatorUtil;
	}

	public void setBuildNumber(long buildNumber) {
		_buildNumber = buildNumber;
	}

	public void setBuildNumberIncrement(boolean buildNumberIncrement) {
		_buildNumberIncrement = buildNumberIncrement;
	}

	public void setDatabaseNameMaxLength(int databaseNameMaxLength) {
		_databaseNameMaxLength = databaseNameMaxLength;
	}

	public void setHbmFile(Object hbmFile) {
		_hbmFile = hbmFile;
	}

	public void setImplDir(Object implDir) {
		_implDir = implDir;
	}

	public void setIncubationFeatures(Iterable<String> incubationFeatures) {
		_incubationFeatures.clear();

		incubationFeatures(incubationFeatures);
	}

	public void setIncubationFeatures(String... incubationFeatures) {
		setIncubationFeatures(Arrays.asList(incubationFeatures));
	}

	public void setInputFile(Object inputFile) {
		_inputFile = inputFile;
	}

	public void setModelHintsConfigs(Iterable<Object> modelHintsConfigs) {
		_modelHintsConfigs.clear();

		modelHintsConfigs(modelHintsConfigs);
	}

	public void setModelHintsConfigs(Object... modelHintsConfigs) {
		setModelHintsConfigs(Arrays.asList(modelHintsConfigs));
	}

	public void setModelHintsFile(Object modelHintsFile) {
		_modelHintsFile = modelHintsFile;
	}

	public void setOsgiModule(boolean osgiModule) {
		_osgiModule = osgiModule;
	}

	public void setPluginName(Object pluginName) {
		_pluginName = pluginName;
	}

	public void setPropsUtil(Object propsUtil) {
		_propsUtil = propsUtil;
	}

	public void setReadOnlyPrefixes(Iterable<Object> readOnlyPrefixes) {
		_readOnlyPrefixes.clear();

		readOnlyPrefixes(readOnlyPrefixes);
	}

	public void setReadOnlyPrefixes(Object... readOnlyPrefixes) {
		setReadOnlyPrefixes(Arrays.asList(readOnlyPrefixes));
	}

	public void setResourceActionsConfigs(
		Iterable<Object> resourceActionsConfigs) {

		_resourceActionsConfigs.clear();

		resourceActionsConfigs(resourceActionsConfigs);
	}

	public void setResourceActionsConfigs(Object... resourceActionsConfigs) {
		setResourceActionsConfigs(Arrays.asList(resourceActionsConfigs));
	}

	public void setResourcesDir(Object resourcesDir) {
		_resourcesDir = resourcesDir;
	}

	public void setSpringFile(Object springFile) {
		_springFile = springFile;
	}

	public void setSpringNamespaces(Iterable<Object> springNamespaces) {
		_springNamespaces.clear();

		springNamespaces(springNamespaces);
	}

	public void setSpringNamespaces(Object... springNamespaces) {
		setSpringNamespaces(Arrays.asList(springNamespaces));
	}

	public void setSqlDir(Object sqlDir) {
		_sqlDir = sqlDir;
	}

	public void setSqlFileName(Object sqlFileName) {
		_sqlFileName = sqlFileName;
	}

	public void setSqlIndexesFileName(Object sqlIndexesFileName) {
		_sqlIndexesFileName = sqlIndexesFileName;
	}

	public void setSqlSequencesFileName(Object sqlSequencesFileName) {
		_sqlSequencesFileName = sqlSequencesFileName;
	}

	public void setTargetEntityName(Object targetEntityName) {
		_targetEntityName = targetEntityName;
	}

	public void setTestDir(Object testDir) {
		_testDir = testDir;
	}

	public void setUADDir(Object uadDir) {
		_uadDir = uadDir;
	}

	public void setUADTestIntegrationDir(Object uadTestIntegrationDir) {
		_uadTestIntegrationDir = uadTestIntegrationDir;
	}

	public BuildServiceTask springNamespaces(
		Iterable<Object> springNamespaces) {

		GUtil.addToCollection(_springNamespaces, springNamespaces);

		return this;
	}

	public BuildServiceTask springNamespaces(Object... springNamespaces) {
		return springNamespaces(Arrays.asList(springNamespaces));
	}

	@Internal
	protected List<String> getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("service.api.dir=" + _relativize(getApiDir()));
		args.add(
			"service.auto.import.default.references=" +
				isAutoImportDefaultReferences());
		args.add("service.auto.namespace.tables=" + isAutoNamespaceTables());
		args.add("service.bean.locator.util=" + getBeanLocatorUtil());
		args.add("service.build.number.increment=" + isBuildNumberIncrement());
		args.add("service.build.number=" + getBuildNumber());
		args.add(
			"service.database.name.max.length=" + getDatabaseNameMaxLength());
		args.add(
			"service.hbm.file=" +
				_relativize(_getOptionalFile(getHbmFile(), _hbmFile)));
		args.add("service.impl.dir=" + _relativize(getImplDir()));
		args.add(
			"service.incubation.features=" +
				CollectionUtils.join(",", getIncubationFeatures()));
		args.add("service.input.file=" + _relativize(getInputFile()));
		args.add(
			"service.model.hints.configs=" +
				CollectionUtils.join(",", getCompleteModelHintsConfigs()));

		File modelHintsFile = _getOptionalFile(
			getModelHintsFile(), _modelHintsFile);

		args.add("service.model.hints.file=" + _relativize(modelHintsFile));

		args.add("service.osgi.module=" + isOsgiModule());
		args.add("service.plugin.name=" + getPluginName());
		args.add("service.props.util=" + getPropsUtil());
		args.add(
			"service.read.only.prefixes=" +
				CollectionUtils.join(",", getReadOnlyPrefixes()));
		args.add(
			"service.resource.actions.configs=" +
				CollectionUtils.join(",", getResourceActionsConfigs()));
		args.add("service.resources.dir=" + _relativize(getResourcesDir()));
		args.add(
			"service.spring.file=" +
				_relativize(_getOptionalFile(getSpringFile(), _springFile)));
		args.add(
			"service.spring.namespaces=" +
				CollectionUtils.join(",", getSpringNamespaces()));
		args.add("service.sql.dir=" + _relativize(getSqlDir()));
		args.add("service.sql.file=" + getSqlFileName());
		args.add("service.sql.indexes.file=" + getSqlIndexesFileName());
		args.add("service.sql.sequences.file=" + getSqlSequencesFileName());

		String targetEntityName = getTargetEntityName();

		if (Validator.isNull(targetEntityName)) {
			targetEntityName = "${service.target.entity.name}";
		}

		args.add("service.target.entity.name=" + targetEntityName);

		File testDir = getTestDir();

		if (testDir != null) {
			args.add("service.test.dir=" + _relativize(testDir));
		}

		File uadDir = getUADDir();

		if (uadDir != null) {
			args.add("service.uad.dir=" + _relativize(uadDir));
		}

		File uadTestIntegrationDir = getUADTestIntegrationDir();

		if (uadTestIntegrationDir != null) {
			args.add(
				"service.uad.test.integration.dir=" +
					_relativize(uadTestIntegrationDir));
		}

		return args;
	}

	@Input
	protected List<String> getCompleteModelHintsConfigs() {
		List<String> modelHintsConfigs = getModelHintsConfigs();
		File modelHintsFile = _getOptionalFile(
			getModelHintsFile(), _modelHintsFile);
		Project project = getProject();

		boolean found = false;

		for (String config : modelHintsConfigs) {
			if (config.startsWith("classpath*:")) {
				continue;
			}

			File configFile = project.file(config);

			if (configFile.equals(modelHintsFile)) {
				found = true;

				break;
			}
		}

		if (!found) {
			modelHintsConfigs.add(_relativize(modelHintsFile));
		}

		return modelHintsConfigs;
	}

	private File _getOptionalFile(File file, Object defaultFile) {
		if (file != null) {
			return file;
		}

		return GradleUtil.toFile(getProject(), defaultFile);
	}

	private String _relativize(File file) {
		String relativePath = FileUtil.relativize(file, getWorkingDir());

		return relativePath.replace('\\', '/');
	}

	private Object _apiDir;
	private boolean _autoImportDefaultReferences = true;
	private boolean _autoNamespaceTables = true;
	private Object _beanLocatorUtil =
		"com.liferay.util.bean.PortletBeanLocatorUtil";
	private long _buildNumber = 1;
	private boolean _buildNumberIncrement = true;
	private int _databaseNameMaxLength = 30;
	private Object _hbmFile;
	private Object _implDir;
	private final Set<String> _incubationFeatures = new HashSet<>();
	private Object _inputFile;
	private final Set<Object> _modelHintsConfigs = new LinkedHashSet<>();
	private Object _modelHintsFile;
	private boolean _osgiModule;
	private Object _pluginName;
	private Object _propsUtil;
	private final Set<Object> _readOnlyPrefixes = new HashSet<>();
	private final Set<Object> _resourceActionsConfigs = new LinkedHashSet<>();
	private Object _resourcesDir;
	private Object _springFile;
	private final Set<Object> _springNamespaces = new LinkedHashSet<>();
	private Object _sqlDir;
	private Object _sqlFileName = "tables.sql";
	private Object _sqlIndexesFileName = "indexes.sql";
	private Object _sqlSequencesFileName = "sequences.sql";
	private Object _targetEntityName;
	private Object _testDir;
	private Object _uadDir;
	private Object _uadTestIntegrationDir;

}