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

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.License;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.RemotePluginPackageRepository;
import com.liferay.portal.kernel.plugin.Screenshot;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.net.MalformedURLException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Sandeep Soni
 */
public class PluginPackageUtil {

	public static void endPluginPackageInstallation(String preliminaryContext) {
		_instance._endPluginPackageInstallation(preliminaryContext);
	}

	public static List<PluginPackage> getAllAvailablePluginPackages()
		throws PortalException {

		return _instance._getAllAvailablePluginPackages();
	}

	public static Collection<String> getAvailableTags() {
		return _instance._getAvailableTags();
	}

	public static PluginPackage getInstalledPluginPackage(String context) {
		return _instance._getInstalledPluginPackage(context);
	}

	public static List<PluginPackage> getInstalledPluginPackages() {
		return _instance._getInstalledPluginPackages();
	}

	public static Date getLastUpdateDate() {
		return _instance._getLastUpdateDate();
	}

	public static PluginPackage getLatestAvailablePluginPackage(
			String groupId, String artifactId)
		throws PortalException {

		return _instance._getLatestAvailablePluginPackage(groupId, artifactId);
	}

	public static PluginPackage getLatestInstalledPluginPackage(
		String groupId, String artifactId) {

		return _instance._getLatestInstalledPluginPackage(groupId, artifactId);
	}

	public static PluginPackage getPluginPackageByModuleId(
			String moduleId, String repositoryURL)
		throws PortalException {

		return _instance._getPluginPackageByModuleId(moduleId, repositoryURL);
	}

	public static PluginPackage getPluginPackageByURL(String url)
		throws PortalException {

		return _instance._getPluginPackageByURL(url);
	}

	public static RemotePluginPackageRepository getRepository(
			String repositoryURL)
		throws PortalException {

		return _instance._getRepository(repositoryURL);
	}

	public static String[] getRepositoryURLs() {
		return _instance._getRepositoryURLs();
	}

	public static String[] getStatusAndInstalledVersion(
		PluginPackage pluginPackage) {

		return _instance._getStatusAndInstalledVersion(pluginPackage);
	}

	public static String[] getSupportedTypes() {
		return _instance._getSupportedTypes();
	}

	public static boolean isCurrentVersionSupported(List<String> versions) {
		return _instance._isCurrentVersionSupported(versions);
	}

	public static boolean isIgnored(PluginPackage pluginPackage) {
		return _instance._isIgnored(pluginPackage);
	}

	public static boolean isInstallationInProcess(String context) {
		return _instance._isInstallationInProcess(context);
	}

	public static boolean isInstalled(String context) {
		return _instance._isInstalled(context);
	}

	public static boolean isTrusted(String repositoryURL) {
		return _instance._isTrusted(repositoryURL);
	}

	public static boolean isUpdateAvailable() {
		return _instance._isUpdateAvailable();
	}

	public static PluginPackage readPluginPackageProperties(
		String displayName, Properties properties) {

		return _instance._readPluginPackageProperties(displayName, properties);
	}

	public static PluginPackage readPluginPackageServletContext(
			ServletContext servletContext)
		throws DocumentException, IOException {

		return _instance._readPluginPackageServletContext(servletContext);
	}

	public static PluginPackage readPluginPackageXml(
		Element pluginPackageElement) {

		return _instance._readPluginPackageXml(pluginPackageElement);
	}

	public static PluginPackage readPluginPackageXml(String xml)
		throws DocumentException {

		return _instance._readPluginPackageXml(xml);
	}

	public static void refreshUpdatesAvailableCache() {
		_instance._refreshUpdatesAvailableCache();
	}

	public static void registerInstalledPluginPackage(
			PluginPackage pluginPackage)
		throws PortalException {

		_instance._registerInstalledPluginPackage(pluginPackage);
	}

	public static void registerPluginPackageInstallation(
		String preliminaryContext) {

		_instance._registerPluginPackageInstallation(preliminaryContext);
	}

	public static RepositoryReport reloadRepositories() throws PortalException {
		return _instance._reloadRepositories();
	}

	public static Hits search(
			String keywords, String type, String tag, String license,
			String repositoryURL, String status, int start, int end)
		throws PortalException {

		return _instance._search(
			keywords, type, tag, license, repositoryURL, status, start, end);
	}

	public static void unregisterInstalledPluginPackage(
			PluginPackage pluginPackage)
		throws PortalException {

		_instance._unregisterInstalledPluginPackage(pluginPackage);
	}

	public static void updateInstallingPluginPackage(
		String preliminaryContext, PluginPackage pluginPackage) {

		_instance._updateInstallingPluginPackage(
			preliminaryContext, pluginPackage);
	}

	private PluginPackageUtil() {
		_installedPluginPackages = new LocalPluginPackageRepository();
		_repositoryCache = new HashMap<String, RemotePluginPackageRepository>();
		_availableTagsCache = new TreeSet<String>();
	}

	private void _checkRepositories(String repositoryURL)
		throws PortalException {

		String[] repositoryURLs = null;

		if (Validator.isNotNull(repositoryURL)) {
			repositoryURLs = new String[] {repositoryURL};
		}
		else {
			repositoryURLs = _getRepositoryURLs();
		}

		for (int i = 0; i < repositoryURLs.length; i++) {
			_getRepository(repositoryURLs[i]);
		}
	}

	private void _endPluginPackageInstallation(String preliminaryContext) {
		_installedPluginPackages.unregisterPluginPackageInstallation(
			preliminaryContext);
	}

	private PluginPackage _findLatestVersion(
		List<PluginPackage> pluginPackages) {

		PluginPackage latestPluginPackage = null;

		for (PluginPackage pluginPackage : pluginPackages) {
			if ((latestPluginPackage == null) ||
				pluginPackage.isLaterVersionThan(latestPluginPackage)) {

				latestPluginPackage = pluginPackage;
			}
		}

		return latestPluginPackage;
	}

	private List<PluginPackage> _getAllAvailablePluginPackages()
		throws PortalException {

		List<PluginPackage> pluginPackages = new ArrayList<PluginPackage>();

		String[] repositoryURLs = _getRepositoryURLs();

		for (int i = 0; i < repositoryURLs.length; i++) {
			try {
				RemotePluginPackageRepository repository = _getRepository(
					repositoryURLs[i]);

				pluginPackages.addAll(repository.getPluginPackages());
			}
			catch (PluginPackageException ppe) {
				String message = ppe.getMessage();

				if (message.startsWith("Unable to communicate")) {
					if (_log.isWarnEnabled()) {
						_log.warn(message);
					}
				}
				else {
					_log.error(message);
				}
			}
		}

		return pluginPackages;
	}

	private List<PluginPackage> _getAvailablePluginPackages(
			String groupId, String artifactId)
		throws PortalException {

		List<PluginPackage> pluginPackages = new ArrayList<PluginPackage>();

		String[] repositoryURLs = _getRepositoryURLs();

		for (int i = 0; i < repositoryURLs.length; i++) {
			RemotePluginPackageRepository repository = _getRepository(
				repositoryURLs[i]);

			List<PluginPackage> curPluginPackages =
				repository.findPluginsByGroupIdAndArtifactId(
					groupId, artifactId);

			if (curPluginPackages != null) {
				pluginPackages.addAll(curPluginPackages);
			}
		}

		return pluginPackages;
	}

	private Collection<String> _getAvailableTags() {
		return _availableTagsCache;
	}

	private PluginPackage _getInstalledPluginPackage(String context) {
		return _installedPluginPackages.getPluginPackage(context);
	}

	private List<PluginPackage> _getInstalledPluginPackages() {
		return _installedPluginPackages.getSortedPluginPackages();
	}

	private Date _getLastUpdateDate() {
		return _lastUpdateDate;
	}

	private PluginPackage _getLatestAvailablePluginPackage(
			String groupId, String artifactId)
		throws PortalException {

		List<PluginPackage> pluginPackages = _getAvailablePluginPackages(
			groupId, artifactId);

		return _findLatestVersion(pluginPackages);
	}

	private PluginPackage _getLatestInstalledPluginPackage(
		String groupId, String artifactId) {

		return _installedPluginPackages.getLatestPluginPackage(
			groupId, artifactId);
	}

	private PluginPackage _getPluginPackageByModuleId(
			String moduleId, String repositoryURL)
		throws PortalException {

		RemotePluginPackageRepository repository = _getRepository(
			repositoryURL);

		return repository.findPluginPackageByModuleId(moduleId);
	}

	private PluginPackage _getPluginPackageByURL(String url)
		throws PortalException {

		String[] repositoryURLs = _getRepositoryURLs();

		for (int i = 0; i < repositoryURLs.length; i++) {
			String repositoryURL = repositoryURLs[i];

			try {
				RemotePluginPackageRepository repository = _getRepository(
					repositoryURL);

				return repository.findPluginByArtifactURL(url);
			}
			catch (PluginPackageException ppe) {
				_log.error("Unable to load repository " + repositoryURL, ppe);
			}
		}

		return null;
	}

	private RemotePluginPackageRepository _getRepository(String repositoryURL)
		throws PortalException {

		RemotePluginPackageRepository repository = _repositoryCache.get(
			repositoryURL);

		if (repository != null) {
			return repository;
		}

		return _loadRepository(repositoryURL);
	}

	private String[] _getRepositoryURLs() throws PluginPackageException {
		try {
			String[] trusted = PrefsPropsUtil.getStringArray(
				PropsKeys.PLUGIN_REPOSITORIES_TRUSTED, StringPool.NEW_LINE,
				PropsValues.PLUGIN_REPOSITORIES_TRUSTED);
			String[] untrusted = PrefsPropsUtil.getStringArray(
				PropsKeys.PLUGIN_REPOSITORIES_UNTRUSTED, StringPool.NEW_LINE,
				PropsValues.PLUGIN_REPOSITORIES_UNTRUSTED);

			return ArrayUtil.append(trusted, untrusted);
		}
		catch (Exception e) {
			throw new PluginPackageException(
				"Unable to read repository list", e);
		}
	}

	private String[] _getStatusAndInstalledVersion(
		PluginPackage pluginPackage) {

		PluginPackage installedPluginPackage =
			_installedPluginPackages.getLatestPluginPackage(
				pluginPackage.getGroupId(), pluginPackage.getArtifactId());

		String status = null;
		String installedVersion = null;

		if (installedPluginPackage == null) {
			status = PluginPackageImpl.STATUS_NOT_INSTALLED;
		}
		else {
			installedVersion = installedPluginPackage.getVersion();

			if (installedPluginPackage.isLaterVersionThan(pluginPackage)) {
				status = PluginPackageImpl.STATUS_NEWER_VERSION_INSTALLED;
			}
			else if (installedPluginPackage.isPreviousVersionThan(
						pluginPackage)) {

				status = PluginPackageImpl.STATUS_OLDER_VERSION_INSTALLED;
			}
			else {
				status = PluginPackageImpl.STATUS_SAME_VERSION_INSTALLED;
			}
		}

		return new String[] {status, installedVersion};
	}

	private String[] _getSupportedTypes() {
		return PropsValues.PLUGIN_TYPES;
	}

	private void _indexPluginPackage(PluginPackage pluginPackage)
		throws PortalException {

		Indexer indexer = IndexerRegistryUtil.getIndexer(PluginPackage.class);

		indexer.reindex(pluginPackage);
	}

	private boolean _isCurrentVersionSupported(List<String> versions) {
		Version currentVersion = Version.getInstance(ReleaseInfo.getVersion());

		for (String version : versions) {
			Version supportedVersion = Version.getInstance(version);

			if (supportedVersion.includes(currentVersion)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isIgnored(PluginPackage pluginPackage) {
		String packageId = pluginPackage.getPackageId();

		String[] pluginPackagesIgnored = PrefsPropsUtil.getStringArray(
			PropsKeys.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED,
			StringPool.NEW_LINE,
			PropsValues.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED);

		for (int i = 0; i < pluginPackagesIgnored.length; i++) {
			String curPluginPackagesIgnored = pluginPackagesIgnored[i];

			if (curPluginPackagesIgnored.endsWith(StringPool.STAR)) {
				String prefix = curPluginPackagesIgnored.substring(
					0, curPluginPackagesIgnored.length() - 2);

				if (packageId.startsWith(prefix)) {
					return true;
				}
			}
			else {
				if (packageId.equals(curPluginPackagesIgnored)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean _isInstallationInProcess(String context) {
		if (_installedPluginPackages.getInstallingPluginPackage(
				context) != null) {

			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isInstalled(String context) {
		PluginPackage pluginPackage = _installedPluginPackages.getPluginPackage(
			context);

		if (pluginPackage != null) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isTrusted(String repositoryURL)
		throws PluginPackageException {

		try {
			String[] trusted = PrefsPropsUtil.getStringArray(
				PropsKeys.PLUGIN_REPOSITORIES_TRUSTED, StringPool.NEW_LINE,
				PropsValues.PLUGIN_REPOSITORIES_TRUSTED);

			if (ArrayUtil.contains(trusted, repositoryURL)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			throw new PluginPackageException(
				"Unable to read repository list", e);
		}
	}

	private boolean _isUpdateAvailable() {
		if (!PrefsPropsUtil.getBoolean(
				PropsKeys.PLUGIN_NOTIFICATIONS_ENABLED,
				PropsValues.PLUGIN_NOTIFICATIONS_ENABLED)) {

			return false;
		}

		if (_updateAvailable != null) {
			return _updateAvailable.booleanValue();
		}
		else if (!_settingUpdateAvailable) {
			_settingUpdateAvailable = true;

			Thread indexerThread = new Thread(
				new UpdateAvailableRunner(), PluginPackageUtil.class.getName());

			indexerThread.setPriority(Thread.MIN_PRIORITY);

			indexerThread.start();
		}

		return false;
	}

	private RemotePluginPackageRepository _loadRepository(String repositoryURL)
		throws PluginPackageException, PortalException {

		RemotePluginPackageRepository repository = null;

		StringBundler sb = new StringBundler(8);

		if (!repositoryURL.startsWith(Http.HTTP_WITH_SLASH) &&
			!repositoryURL.startsWith(Http.HTTPS_WITH_SLASH)) {

			sb.append(Http.HTTP_WITH_SLASH);
		}

		sb.append(repositoryURL);
		sb.append(StringPool.SLASH);
		sb.append(PluginPackage.REPOSITORY_XML_FILENAME_PREFIX);
		sb.append(StringPool.DASH);
		sb.append(ReleaseInfo.getVersion());
		sb.append(StringPool.PERIOD);
		sb.append(PluginPackage.REPOSITORY_XML_FILENAME_EXTENSION);

		String pluginsXmlURL = sb.toString();

		try {
			Http.Options options = new Http.Options();

			options.setLocation(pluginsXmlURL);
			options.setPost(false);

			byte[] bytes = HttpUtil.URLtoByteArray(options);

			Http.Response response = options.getResponse();

			int responseCode = response.getResponseCode();

			if (responseCode != HttpServletResponse.SC_OK) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"A repository for version " +
							ReleaseInfo.getVersion() + " was not found. " +
								"Checking general repository");
				}

				sb.setIndex(0);

				sb.append(repositoryURL);
				sb.append(StringPool.SLASH);
				sb.append(PluginPackage.REPOSITORY_XML_FILENAME_PREFIX);
				sb.append(StringPool.PERIOD);
				sb.append(PluginPackage.REPOSITORY_XML_FILENAME_EXTENSION);

				pluginsXmlURL = sb.toString();

				options = new Http.Options();

				options.setLocation(pluginsXmlURL);
				options.setPost(false);

				bytes = HttpUtil.URLtoByteArray(options);

				response = options.getResponse();

				if (responseCode != HttpServletResponse.SC_OK) {
					throw new PluginPackageException(
						"Unable to download file " + pluginsXmlURL +
							" because of response code " + responseCode);
				}
			}

			if (ArrayUtil.isNotEmpty(bytes)) {
				repository = _parseRepositoryXml(
					new String(bytes), repositoryURL);

				_repositoryCache.put(repositoryURL, repository);
				_availableTagsCache.addAll(repository.getTags());
				_lastUpdateDate = new Date();
				_updateAvailable = null;

				return repository;
			}

			_lastUpdateDate = new Date();

			throw new PluginPackageException("Download returned 0 bytes");
		}
		catch (MalformedURLException murle) {
			_repositoryCache.remove(repositoryURL);

			throw new PluginPackageException(
				"Invalid URL " + pluginsXmlURL, murle);
		}
		catch (IOException ioe) {
			_repositoryCache.remove(repositoryURL);

			throw new PluginPackageException(
				"Unable to communicate with repository " + repositoryURL, ioe);
		}
		catch (DocumentException de) {
			_repositoryCache.remove(repositoryURL);

			throw new PluginPackageException(
				"Unable to parse plugin list for repository " + repositoryURL,
				de);
		}
	}

	private RemotePluginPackageRepository _parseRepositoryXml(
			String xml, String repositoryURL)
		throws DocumentException, PortalException {

		List<String> supportedPluginTypes = Arrays.asList(getSupportedTypes());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Loading plugin repository " + repositoryURL + ":\n" + xml);
		}

		RemotePluginPackageRepository pluginPackageRepository =
			new RemotePluginPackageRepository(repositoryURL);

		if (xml == null) {
			return pluginPackageRepository;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		Properties settings = _readProperties(
			rootElement.element("settings"), "setting");

		pluginPackageRepository.setSettings(settings);

		List<Element> pluginPackageElements = rootElement.elements(
			"plugin-package");

		for (Element pluginPackageElement : pluginPackageElements) {
			PluginPackage pluginPackage = _readPluginPackageXml(
				pluginPackageElement);

			if (!_isCurrentVersionSupported(
					pluginPackage.getLiferayVersions())) {

				continue;
			}

			boolean containsSupportedTypes = false;

			List<String> pluginTypes = pluginPackage.getTypes();

			for (String pluginType : pluginTypes) {
				if (supportedPluginTypes.contains(pluginType)) {
					containsSupportedTypes = true;

					break;
				}
			}

			if (!containsSupportedTypes) {
				continue;
			}

			pluginPackage.setRepository(pluginPackageRepository);

			pluginPackageRepository.addPluginPackage(pluginPackage);

			_indexPluginPackage(pluginPackage);
		}

		return pluginPackageRepository;
	}

	private Date _readDate(String text) {
		if (Validator.isNotNull(text)) {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				Time.RFC822_FORMAT, LocaleUtil.US);

			try {
				return dateFormat.parse(text);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to parse date " + text);
				}
			}
		}

		return new Date();
	}

	private String _readHtml(String text) {
		return GetterUtil.getString(text);
	}

	private List<License> _readLicenseList(Element parentElement, String name) {
		List<License> licenses = new ArrayList<License>();

		for (Element licenseElement : parentElement.elements(name)) {
			License license = new License();

			license.setName(licenseElement.getText());

			Attribute osiApproved = licenseElement.attribute("osi-approved");

			if (osiApproved != null) {
				license.setOsiApproved(
					GetterUtil.getBoolean(osiApproved.getText()));
			}

			Attribute url = licenseElement.attribute("url");

			if (url != null) {
				license.setUrl(url.getText());
			}

			licenses.add(license);
		}

		return licenses;
	}

	private List<String> _readList(Element parentElement, String name) {
		List<String> list = new ArrayList<String>();

		if (parentElement == null) {
			return list;
		}

		for (Element element : parentElement.elements(name)) {
			String text = StringUtil.toLowerCase(element.getText().trim());

			list.add(text);
		}

		return list;
	}

	private PluginPackage _readPluginPackageProperties(
		String displayName, Properties properties) {

		int pos = displayName.indexOf("-portlet");

		String pluginType = Plugin.TYPE_PORTLET;

		if (pos == -1) {
			pos = displayName.indexOf("-ext");

			pluginType = Plugin.TYPE_EXT;
		}

		if (pos == -1) {
			pos = displayName.indexOf("-hook");

			pluginType = Plugin.TYPE_HOOK;
		}

		if (pos == -1) {
			pos = displayName.indexOf("-layouttpl");

			pluginType = Plugin.TYPE_LAYOUT_TEMPLATE;
		}

		if (pos == -1) {
			pos = displayName.indexOf("-theme");

			pluginType = Plugin.TYPE_THEME;
		}

		if (pos == -1) {
			pos = displayName.indexOf("-web");

			pluginType = Plugin.TYPE_WEB;
		}

		if (pos == -1) {
			return null;
		}

		String displayPrefix = displayName.substring(0, pos);

		String moduleGroupId = GetterUtil.getString(
			properties.getProperty("module-group-id"));
		String moduleArtifactId = displayPrefix + "-" + pluginType;

		String moduleVersion = GetterUtil.getString(
			properties.getProperty("module-version"));

		if (Validator.isNull(moduleVersion)) {
			int moduleVersionPos = pos + pluginType.length() + 2;

			if (displayName.length() > moduleVersionPos) {
				moduleVersion = displayName.substring(moduleVersionPos);
			}
			else {
				moduleVersion = ReleaseInfo.getVersion();
			}
		}

		String moduleId =
			moduleGroupId + "/" + moduleArtifactId + "/" + moduleVersion +
				"/war";

		String pluginName = GetterUtil.getString(
			properties.getProperty("name"));

		String deploymentContext = GetterUtil.getString(
			properties.getProperty("recommended-deployment-context"),
			moduleArtifactId);

		String author = GetterUtil.getString(properties.getProperty("author"));

		List<String> types = new ArrayList<String>();

		types.add(pluginType);

		List<License> licenses = new ArrayList<License>();

		String[] licensesArray = StringUtil.split(
			properties.getProperty("licenses"));

		for (int i = 0; i < licensesArray.length; i++) {
			License license = new License();

			license.setName(licensesArray[i].trim());
			license.setOsiApproved(true);

			licenses.add(license);
		}

		List<String> liferayVersions = new ArrayList<String>();

		String[] liferayVersionsArray = StringUtil.split(
			properties.getProperty("liferay-versions"));

		for (String liferayVersion : liferayVersionsArray) {
			liferayVersions.add(liferayVersion.trim());
		}

		if (liferayVersions.isEmpty()) {
			liferayVersions.add(ReleaseInfo.getVersion() + "+");
		}

		List<String> tags = new ArrayList<String>();

		String[] tagsArray = StringUtil.split(properties.getProperty("tags"));

		for (String tag : tagsArray) {
			tags.add(tag.trim());
		}

		String shortDescription = GetterUtil.getString(
			properties.getProperty("short-description"));
		String longDescription = GetterUtil.getString(
			properties.getProperty("long-description"));
		String changeLog = GetterUtil.getString(
			properties.getProperty("change-log"));
		String pageURL = GetterUtil.getString(
			properties.getProperty("page-url"));
		String downloadURL = GetterUtil.getString(
			properties.getProperty("download-url"));
		List<String> requiredDeploymentContexts = ListUtil.fromArray(
			StringUtil.split(
				properties.getProperty("required-deployment-contexts")));

		PluginPackage pluginPackage = new PluginPackageImpl(moduleId);

		pluginPackage.setName(pluginName);
		pluginPackage.setRecommendedDeploymentContext(deploymentContext);
		//pluginPackage.setModifiedDate(null);
		pluginPackage.setAuthor(author);
		pluginPackage.setTypes(types);
		pluginPackage.setLicenses(licenses);
		pluginPackage.setLiferayVersions(liferayVersions);
		pluginPackage.setTags(tags);
		pluginPackage.setShortDescription(shortDescription);
		pluginPackage.setLongDescription(longDescription);
		pluginPackage.setChangeLog(changeLog);
		//pluginPackage.setScreenshots(null);
		pluginPackage.setPageURL(pageURL);
		pluginPackage.setDownloadURL(downloadURL);
		//pluginPackage.setDeploymentSettings(null);
		pluginPackage.setRequiredDeploymentContexts(requiredDeploymentContexts);

		return pluginPackage;
	}

	/**
	 * @see com.liferay.portal.tools.deploy.BaseDeployer#readPluginPackage(
	 *      java.io.File)
	 */
	private PluginPackage _readPluginPackageServletContext(
			ServletContext servletContext)
		throws DocumentException, IOException {

		String servletContextName = servletContext.getServletContextName();

		if (_log.isInfoEnabled()) {
			if (servletContextName == null) {
				_log.info("Reading plugin package for the root context");
			}
			else {
				_log.info("Reading plugin package for " + servletContextName);
			}
		}

		PluginPackage pluginPackage = null;

		String xml = HttpUtil.URLtoString(
			servletContext.getResource("/WEB-INF/liferay-plugin-package.xml"));

		if (xml != null) {
			pluginPackage = _readPluginPackageXml(xml);
		}
		else {
			String propertiesString = HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/liferay-plugin-package.properties"));

			if (propertiesString != null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Reading plugin package from " +
							"liferay-plugin-package.properties");
				}

				Properties properties = PropertiesUtil.load(propertiesString);

				String displayName = servletContextName;

				if (displayName.startsWith(StringPool.SLASH)) {
					displayName = displayName.substring(1);
				}

				pluginPackage = _readPluginPackageProperties(
					displayName, properties);
			}

			if (pluginPackage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Reading plugin package from MANIFEST.MF");
				}

				pluginPackage =_readPluginPackageServletManifest(
					servletContext);
			}
		}

		pluginPackage.setContext(servletContextName);

		return pluginPackage;
	}

	private PluginPackage _readPluginPackageServletManifest(
			ServletContext servletContext)
		throws IOException {

		Attributes attributes = null;

		String servletContextName = servletContext.getServletContextName();

		InputStream inputStream = servletContext.getResourceAsStream(
			"/META-INF/MANIFEST.MF");

		if (inputStream != null) {
			Manifest manifest = new Manifest(inputStream);

			attributes = manifest.getMainAttributes();
		}
		else {
			attributes = new Attributes();
		}

		String artifactGroupId = attributes.getValue(
			"Implementation-Vendor-Id");

		if (Validator.isNull(artifactGroupId)) {
			artifactGroupId = attributes.getValue("Implementation-Vendor");
		}

		if (Validator.isNull(artifactGroupId)) {
			artifactGroupId = GetterUtil.getString(
				attributes.getValue("Bundle-Vendor"), servletContextName);
		}

		String artifactId = attributes.getValue("Implementation-Title");

		if (Validator.isNull(artifactId)) {
			artifactId = GetterUtil.getString(
				attributes.getValue("Bundle-Name"), servletContextName);
		}

		String version = attributes.getValue("Implementation-Version");

		if (Validator.isNull(version)) {
			version = GetterUtil.getString(
				attributes.getValue("Bundle-Version"), Version.UNKNOWN);
		}

		if (version.equals(Version.UNKNOWN) && _log.isWarnEnabled()) {
			_log.warn(
				"Plugin package on context " + servletContextName +
					" cannot be tracked because this WAR does not contain a " +
						"liferay-plugin-package.xml file");
		}

		PluginPackage pluginPackage = new PluginPackageImpl(
			artifactGroupId + StringPool.SLASH + artifactId + StringPool.SLASH +
				version + StringPool.SLASH + "war");

		pluginPackage.setName(artifactId);

		String shortDescription = attributes.getValue("Bundle-Description");

		if (Validator.isNotNull(shortDescription)) {
			pluginPackage.setShortDescription(shortDescription);
		}

		String pageURL = attributes.getValue("Bundle-DocURL");

		if (Validator.isNotNull(pageURL)) {
			pluginPackage.setPageURL(pageURL);
		}

		return pluginPackage;
	}

	private PluginPackage _readPluginPackageXml(Element pluginPackageElement) {
		String name = pluginPackageElement.elementText("name");

		if (_log.isDebugEnabled()) {
			_log.debug("Reading pluginPackage definition " + name);
		}

		PluginPackage pluginPackage = new PluginPackageImpl(
			GetterUtil.getString(
				pluginPackageElement.elementText("module-id")));

		List<String> liferayVersions = _readList(
			pluginPackageElement.element("liferay-versions"),
			"liferay-version");

		List<String> types = _readList(
			pluginPackageElement.element("types"), "type");

		if (types.contains("layout-template")) {
			types.remove("layout-template");

			types.add(Plugin.TYPE_LAYOUT_TEMPLATE);
		}

		pluginPackage.setName(_readText(name));
		pluginPackage.setRecommendedDeploymentContext(
			_readText(
				pluginPackageElement.elementText(
					"recommended-deployment-context")));
		pluginPackage.setRequiredDeploymentContexts(
			_readList(
				pluginPackageElement.element("required-deployment-contexts"),
				"required-deployment-context"));
		pluginPackage.setModifiedDate(
			_readDate(pluginPackageElement.elementText("modified-date")));
		pluginPackage.setAuthor(
			_readText(pluginPackageElement.elementText("author")));
		pluginPackage.setTypes(types);
		pluginPackage.setLicenses(
			_readLicenseList(
				pluginPackageElement.element("licenses"), "license"));
		pluginPackage.setLiferayVersions(liferayVersions);
		pluginPackage.setTags(
			_readList(pluginPackageElement.element("tags"), "tag"));
		pluginPackage.setShortDescription(
			_readText(pluginPackageElement.elementText("short-description")));
		pluginPackage.setLongDescription(
			_readHtml(pluginPackageElement.elementText("long-description")));
		pluginPackage.setChangeLog(
			_readHtml(pluginPackageElement.elementText("change-log")));
		pluginPackage.setScreenshots(
			_readScreenshots(pluginPackageElement.element("screenshots")));
		pluginPackage.setPageURL(
			_readText(pluginPackageElement.elementText("page-url")));
		pluginPackage.setDownloadURL(
			_readText(pluginPackageElement.elementText("download-url")));
		pluginPackage.setDeploymentSettings(
			_readProperties(
				pluginPackageElement.element("deployment-settings"),
				"setting"));

		return pluginPackage;
	}

	private PluginPackage _readPluginPackageXml(String xml)
		throws DocumentException {

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		return _readPluginPackageXml(rootElement);
	}

	private Properties _readProperties(Element parentElement, String name) {
		Properties properties = new Properties();

		if (parentElement == null) {
			return properties;
		}

		for (Element element : parentElement.elements(name)) {
			properties.setProperty(
				element.attributeValue("name"),
				element.attributeValue("value"));
		}

		return properties;
	}

	private List<Screenshot> _readScreenshots(Element parentElement) {
		List<Screenshot> screenshots = new ArrayList<Screenshot>();

		if (parentElement == null) {
			return screenshots;
		}

		for (Element screenshotElement : parentElement.elements("screenshot")) {
			Screenshot screenshot = new Screenshot();

			screenshot.setThumbnailURL(
				screenshotElement.elementText("thumbnail-url"));
			screenshot.setLargeImageURL(
				screenshotElement.elementText("large-image-url"));

			screenshots.add(screenshot);
		}

		return screenshots;
	}

	private String _readText(String text) {
		return HtmlUtil.extractText(GetterUtil.getString(text));
	}

	private void _refreshUpdatesAvailableCache() {
		_updateAvailable = null;
	}

	private void _registerInstalledPluginPackage(PluginPackage pluginPackage)
		throws PortalException {

		_installedPluginPackages.addPluginPackage(pluginPackage);

		_updateAvailable = null;

		_indexPluginPackage(pluginPackage);
	}

	private void _registerPluginPackageInstallation(String preliminaryContext) {
		_installedPluginPackages.registerPluginPackageInstallation(
			preliminaryContext);
	}

	private RepositoryReport _reloadRepositories() throws PortalException {
		if (_log.isInfoEnabled()) {
			_log.info("Reloading repositories");
		}

		RepositoryReport repositoryReport = new RepositoryReport();

		String[] repositoryURLs = _getRepositoryURLs();

		for (int i = 0; i < repositoryURLs.length; i++) {
			String repositoryURL = repositoryURLs[i];

			try {
				_loadRepository(repositoryURL);

				repositoryReport.addSuccess(repositoryURL);
			}
			catch (PluginPackageException ppe) {
				repositoryReport.addError(repositoryURL, ppe);

				_log.error(
					"Unable to load repository " + repositoryURL + " " +
						ppe.toString());
			}
		}

		Indexer indexer = IndexerRegistryUtil.getIndexer(PluginPackage.class);

		indexer.reindex(new String[0]);

		return repositoryReport;
	}

	private Hits _search(
			String keywords, String type, String tag, String license,
			String repositoryURL, String status, int start, int end)
		throws PortalException {

		_checkRepositories(repositoryURL);

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		attributes.put("license", license);
		attributes.put("repositoryURL", repositoryURL);
		attributes.put("status", status);
		attributes.put("tag", tag);
		attributes.put("type", type);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(CompanyConstants.SYSTEM);
		searchContext.setEnd(end);
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		searchContext.setStart(start);

		Indexer indexer = IndexerRegistryUtil.getIndexer(PluginPackage.class);

		return indexer.search(searchContext);
	}

	private void _unregisterInstalledPluginPackage(PluginPackage pluginPackage)
		throws PortalException {

		_installedPluginPackages.removePluginPackage(pluginPackage);

		try {
			List<PluginPackage> pluginPackages = _getAvailablePluginPackages(
				pluginPackage.getGroupId(), pluginPackage.getArtifactId());

			for (PluginPackage availablePackage : pluginPackages) {
				_indexPluginPackage(availablePackage);
			}
		}
		catch (PluginPackageException ppe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to reindex unistalled package " +
						pluginPackage.getContext() + ": " + ppe.getMessage());
			}
		}
	}

	private void _updateInstallingPluginPackage(
		String preliminaryContext, PluginPackage pluginPackage) {

		_installedPluginPackages.unregisterPluginPackageInstallation(
			preliminaryContext);
		_installedPluginPackages.registerPluginPackageInstallation(
			pluginPackage);
	}

	private static Log _log = LogFactoryUtil.getLog(PluginPackageUtil.class);

	private static PluginPackageUtil _instance = new PluginPackageUtil();

	private Set<String> _availableTagsCache;
	private LocalPluginPackageRepository _installedPluginPackages;
	private Date _lastUpdateDate;
	private Map<String, RemotePluginPackageRepository> _repositoryCache;
	private boolean _settingUpdateAvailable;
	private Boolean _updateAvailable;

	private class UpdateAvailableRunner implements Runnable {

		@Override
		public void run() {
			try {
				setUpdateAvailable();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}
		}

		protected void setUpdateAvailable() throws Exception {
			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			if (_log.isInfoEnabled()) {
				_log.info("Checking for available updates");
			}

			for (PluginPackage pluginPackage :
					_installedPluginPackages.getPluginPackages()) {

				PluginPackage availablePluginPackage = null;

				if (_isIgnored(pluginPackage)) {
					continue;
				}

				availablePluginPackage =
					PluginPackageUtil.getLatestAvailablePluginPackage(
						pluginPackage.getGroupId(),
						pluginPackage.getArtifactId());

				if (availablePluginPackage == null) {
					continue;
				}

				Version availablePluginPackageVersion = Version.getInstance(
					availablePluginPackage.getVersion());

				if (availablePluginPackageVersion.isLaterVersionThan(
						pluginPackage.getVersion())) {

					_updateAvailable = Boolean.TRUE;

					break;
				}
			}

			if (_updateAvailable == null) {
				_updateAvailable = Boolean.FALSE;
			}

			_settingUpdateAvailable = false;

			if (_log.isInfoEnabled()) {
				_log.info(
					"Finished checking for available updates in " +
						stopWatch.getTime() + " ms");
			}
		}
	}

}