/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.internal.configuration;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.file.install.constants.FileInstallConstants;
import com.liferay.portal.file.install.internal.Util;
import com.liferay.portal.file.install.properties.ConfigurationProperties;
import com.liferay.portal.file.install.properties.ConfigurationPropertiesFactory;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Matthew Tambara
 */
public class ConfigurationFileInstaller implements FileInstaller {

	public ConfigurationFileInstaller(
		ConfigurationAdmin configurationAdmin, String encoding) {

		_configurationAdmin = configurationAdmin;
		_encoding = encoding;

		_configsDirPath = Util.getFilePath(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR);
	}

	@Override
	public boolean canTransformURL(File file) {
		if (!Objects.equals(
				_configsDirPath, Util.getFilePath(file.getParent()))) {

			return false;
		}

		String name = file.getName();

		if (name.endsWith(".config")) {
			return true;
		}
		else if (name.endsWith(".cfg")) {
			if (PropsValues.MODULE_FRAMEWORK_FILE_INSTALL_CFG_ENABLED) {
				return true;
			}

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to install .cfg file " + file +
						", please use .config file instead.");
			}
		}

		return false;
	}

	@Override
	public URL transformURL(File file) throws Exception {
		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		ConfigurationProperties configurationProperties =
			ConfigurationPropertiesFactory.create(file, _encoding);

		for (String key : configurationProperties.keySet()) {
			dictionary.put(key, configurationProperties.get(key));
		}

		String[] pid = _parsePid(file.getName());

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					_getCompanyId(
						_getScope(dictionary), dictionary, file.getName()))) {

			Configuration configuration = _getConfiguration(
				file.getName(), pid[0], pid[1]);

			Set<Configuration.ConfigurationAttribute> configurationAttributes =
				configuration.getAttributes();

			if (configurationAttributes.contains(
					Configuration.ConfigurationAttribute.READ_ONLY)) {

				configuration.removeAttributes(
					Configuration.ConfigurationAttribute.READ_ONLY);
			}

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Dictionary<String, Object> old = null;

			if (properties != null) {
				old = new HashMapDictionary<>();

				Enumeration<String> enumeration = properties.keys();

				while (enumeration.hasMoreElements()) {
					String key = enumeration.nextElement();

					old.put(key, properties.get(key));
				}
			}

			String oldFileName = null;

			if (old != null) {
				oldFileName = (String)old.remove(
					FileInstallConstants.FELIX_FILE_INSTALL_FILENAME);

				old.remove(Constants.SERVICE_PID);
				old.remove(ConfigurationAdmin.SERVICE_FACTORYPID);

				Object bundleLocation = dictionary.get(
					ConfigurationAdmin.SERVICE_BUNDLELOCATION);

				if ((bundleLocation == null) &&
					Objects.equals(
						StringPool.QUESTION,
						old.get(ConfigurationAdmin.SERVICE_BUNDLELOCATION))) {

					old.remove(ConfigurationAdmin.SERVICE_BUNDLELOCATION);
				}
			}

			String currentFileName = file.getName();

			if (!_equals(dictionary, old) ||
				!Objects.equals(oldFileName, currentFileName) ||
				configurationAttributes.contains(
					Configuration.ConfigurationAttribute.READ_ONLY) ||
				!file.canWrite()) {

				dictionary.put(
					FileInstallConstants.FELIX_FILE_INSTALL_FILENAME,
					currentFileName);

				String logString = StringPool.BLANK;

				if (pid[1] != null) {
					logString = StringPool.TILDE + pid[1];
				}

				if (old == null) {
					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Creating configuration from ", pid[0],
								logString, ".config"));
					}
				}
				else {
					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Updating configuration from ", pid[0],
								logString, ".config"));
					}
				}

				configuration.updateIfDifferent(dictionary);

				if (!file.canWrite()) {
					try {
						configuration.addAttributes(
							Configuration.ConfigurationAttribute.READ_ONLY);
					}
					catch (Throwable throwable) {
						_log.error(throwable);
					}
				}
			}
		}

		return null;
	}

	@Override
	public void uninstall(File file) throws Exception {
		String[] pid = _parsePid(file.getName());

		String logString = StringPool.BLANK;

		if (pid[1] != null) {
			logString = StringPool.TILDE + pid[1];
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Deleting configuration from ", pid[0], logString,
					".config"));
		}

		Configuration configuration = _getConfiguration(
			file.getName(), pid[0], pid[1]);

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					_getCompanyId(
						ExtendedObjectClassDefinition.Scope.COMPANY,
						configuration.getProperties(), file.getName()))) {

			configuration.delete();
		}
	}

	private boolean _equals(
		Dictionary<String, Object> newDictionary,
		Dictionary<String, Object> oldDictionary) {

		if (oldDictionary == null) {
			return false;
		}

		Enumeration<String> enumeration = newDictionary.keys();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			Object newValue = newDictionary.get(key);
			Object oldValue = oldDictionary.remove(key);

			if (!Objects.equals(newValue, oldValue) &&
				!Objects.deepEquals(newValue, oldValue)) {

				return false;
			}
		}

		return oldDictionary.isEmpty();
	}

	private String _escapeFilterValue(String string) {
		string = StringUtil.replace(string, "[(]", "\\\\(");
		string = StringUtil.replace(string, "[)]", "\\\\)");
		string = StringUtil.replace(string, "[=]", "\\\\=");

		return StringUtil.replace(string, "[\\*]", "\\\\*");
	}

	private Configuration _findExistingConfiguration(String fileName)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				StringPool.OPEN_PARENTHESIS,
				FileInstallConstants.FELIX_FILE_INSTALL_FILENAME,
				StringPool.EQUAL, _escapeFilterValue(fileName),
				StringPool.CLOSE_PARENTHESIS));

		if ((configurations != null) && (configurations.length > 0)) {
			return configurations[0];
		}

		return null;
	}

	private long _getCompanyId(
		Dictionary<String, Object> dictionary, String fileName) {

		ExtendedObjectClassDefinition.Scope scope =
			ExtendedObjectClassDefinition.Scope.COMPANY;

		long companyId = GetterUtil.getLong(
			dictionary.get(scope.getPropertyKey()));

		if (companyId != 0) {
			if (!ArrayUtil.contains(
					PortalInstancePool.getCompanyIds(), companyId)) {

				throw new IllegalArgumentException(
					StringBundler.concat(
						"Unable to process ", fileName, " because company ID ",
						companyId, " does not exist"));
			}

			return companyId;
		}

		String webId = (String)dictionary.get(scope.getPortablePropertyKey());

		if (webId != null) {
			try {
				companyId = PortalInstancePool.getCompanyId(webId);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"Unable to process ", fileName, ": ",
						illegalArgumentException.getMessage()));
			}

			return companyId;
		}

		return 0L;
	}

	private Long _getCompanyId(
		ExtendedObjectClassDefinition.Scope scope,
		Dictionary<String, Object> dictionary, String fileName) {

		if (scope.equals(ExtendedObjectClassDefinition.Scope.SYSTEM) ||
			(dictionary == null)) {

			return 0L;
		}

		if (scope.equals(ExtendedObjectClassDefinition.Scope.COMPANY)) {
			return _getCompanyId(dictionary, fileName);
		}
		else if (scope.equals(ExtendedObjectClassDefinition.Scope.GROUP)) {
			return _getGroupCompanyId(dictionary, fileName);
		}

		return 0L;
	}

	private Configuration _getConfiguration(
			String fileName, String pid, String name)
		throws Exception {

		Configuration configuration = _findExistingConfiguration(fileName);

		if (configuration != null) {
			return configuration;
		}

		if (name != null) {
			return _configurationAdmin.getFactoryConfiguration(
				pid, name, StringPool.QUESTION);
		}

		return _configurationAdmin.getConfiguration(pid, StringPool.QUESTION);
	}

	private long _getGroupCompanyId(
		Dictionary<String, Object> dictionary, String fileName) {

		ExtendedObjectClassDefinition.Scope scope =
			ExtendedObjectClassDefinition.Scope.GROUP;

		long groupId = GetterUtil.getLong(
			dictionary.get(scope.getPropertyKey()));

		if (groupId != 0) {
			long companyId = _getCompanyId(dictionary, fileName);

			if (companyId == 0L) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"Unable to process group scoped configuration ",
						fileName,
						" because required property \"companyId\" is missing"));
			}

			return companyId;
		}

		String groupKey = (String)dictionary.get(
			scope.getPortablePropertyKey());

		if (groupKey != null) {
			try {
				String[] parts = StringUtil.split(groupKey, _SEPARATOR);

				String webId = parts[0];

				return PortalInstancePool.getCompanyId(webId);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"Unable to process ", fileName, ": ",
						illegalArgumentException.getMessage()));
			}
		}

		return 0L;
	}

	private ExtendedObjectClassDefinition.Scope _getScope(
		Dictionary<String, Object> dictionary) {

		for (ExtendedObjectClassDefinition.Scope scope :
				ExtendedObjectClassDefinition.Scope.values()) {

			for (String key :
					new String[] {
						scope.getPropertyKey(), scope.getPortablePropertyKey()
					}) {

				if ((key == null) || (dictionary.get(key) == null)) {
					continue;
				}

				if (!scope.equals(
						ExtendedObjectClassDefinition.Scope.COMPANY)) {

					return scope;
				}

				Object groupId = dictionary.get(
					ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey());

				if (groupId != null) {
					return ExtendedObjectClassDefinition.Scope.GROUP;
				}

				return scope;
			}
		}

		return ExtendedObjectClassDefinition.Scope.SYSTEM;
	}

	private String[] _parsePid(String path) {
		String pid = path.substring(0, path.lastIndexOf(CharPool.PERIOD));

		int index = pid.indexOf(CharPool.TILDE);

		if (index <= 0) {
			index = pid.indexOf(CharPool.UNDERLINE);

			if (index <= 0) {
				index = pid.indexOf(CharPool.DASH);
			}
		}

		if (index > 0) {
			String name = pid.substring(index + 1);

			pid = pid.substring(0, index);

			return new String[] {pid, name};
		}

		return new String[] {pid, null};
	}

	private static final String _SEPARATOR = "--";

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationFileInstaller.class);

	private final String _configsDirPath;
	private final ConfigurationAdmin _configurationAdmin;
	private final String _encoding;

}