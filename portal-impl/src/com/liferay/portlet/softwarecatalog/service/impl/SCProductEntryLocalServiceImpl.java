/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.plugin.ModuleId;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.softwarecatalog.DuplicateProductEntryModuleIdException;
import com.liferay.portlet.softwarecatalog.ProductEntryAuthorException;
import com.liferay.portlet.softwarecatalog.ProductEntryLicenseException;
import com.liferay.portlet.softwarecatalog.ProductEntryNameException;
import com.liferay.portlet.softwarecatalog.ProductEntryPageURLException;
import com.liferay.portlet.softwarecatalog.ProductEntryScreenshotsException;
import com.liferay.portlet.softwarecatalog.ProductEntryShortDescriptionException;
import com.liferay.portlet.softwarecatalog.ProductEntryTypeException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.base.SCProductEntryLocalServiceBaseImpl;
import com.liferay.util.xml.DocUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class SCProductEntryLocalServiceImpl
	extends SCProductEntryLocalServiceBaseImpl {

	@Override
	@Indexable(type = IndexableType.REINDEX)
	public SCProductEntry addProductEntry(
			long userId, String name, String type, String tags,
			String shortDescription, String longDescription, String pageURL,
			String author, String repoGroupId, String repoArtifactId,
			long[] licenseIds, List<byte[]> thumbnails, List<byte[]> fullImages,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Product entry

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		tags = getTags(tags);
		repoGroupId = repoGroupId.trim().toLowerCase();
		repoArtifactId = repoArtifactId.trim().toLowerCase();
		Date now = new Date();

		validate(
			0, name, type, shortDescription, pageURL, author, repoGroupId,
			repoArtifactId, licenseIds, thumbnails, fullImages);

		long productEntryId = counterLocalService.increment();

		SCProductEntry productEntry = scProductEntryPersistence.create(
			productEntryId);

		productEntry.setGroupId(groupId);
		productEntry.setCompanyId(user.getCompanyId());
		productEntry.setUserId(user.getUserId());
		productEntry.setUserName(user.getFullName());
		productEntry.setCreateDate(now);
		productEntry.setModifiedDate(now);
		productEntry.setName(name);
		productEntry.setType(type);
		productEntry.setTags(tags);
		productEntry.setShortDescription(shortDescription);
		productEntry.setLongDescription(longDescription);
		productEntry.setPageURL(pageURL);
		productEntry.setAuthor(author);
		productEntry.setRepoGroupId(repoGroupId);
		productEntry.setRepoArtifactId(repoArtifactId);

		scProductEntryPersistence.update(productEntry);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addProductEntryResources(
				productEntry, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addProductEntryResources(
				productEntry, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Licenses

		scProductEntryPersistence.setSCLicenses(productEntryId, licenseIds);

		// Product screenshots

		saveProductScreenshots(productEntry, thumbnails, fullImages);

		// Message boards

		if (PropsValues.SC_PRODUCT_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, productEntry.getUserName(), groupId,
				SCProductEntry.class.getName(), productEntryId,
				WorkflowConstants.ACTION_PUBLISH);
		}

		return productEntry;
	}

	@Override
	public void addProductEntryResources(
			long productEntryId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(productEntryId);

		addProductEntryResources(
			productEntry, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addProductEntryResources(
			long productEntryId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(productEntryId);

		addProductEntryResources(
			productEntry, groupPermissions, guestPermissions);
	}

	@Override
	public void addProductEntryResources(
			SCProductEntry productEntry, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			productEntry.getCompanyId(), productEntry.getGroupId(),
			productEntry.getUserId(), SCProductEntry.class.getName(),
			productEntry.getProductEntryId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addProductEntryResources(
			SCProductEntry productEntry, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			productEntry.getCompanyId(), productEntry.getGroupId(),
			productEntry.getUserId(), SCProductEntry.class.getName(),
			productEntry.getProductEntryId(), groupPermissions,
			guestPermissions);
	}

	@Override
	public void deleteProductEntries(long groupId)
		throws PortalException, SystemException {

		List<SCProductEntry> productEntries =
			scProductEntryPersistence.findByGroupId(groupId);

		for (SCProductEntry productEntry : productEntries) {
			scProductEntryLocalService.deleteProductEntry(productEntry);
		}
	}

	@Override
	@Indexable(type = IndexableType.DELETE)
	public SCProductEntry deleteProductEntry(long productEntryId)
		throws PortalException, SystemException {

		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(productEntryId);

		return deleteProductEntry(productEntry);
	}

	@Override
	@Indexable(type = IndexableType.DELETE)
	public SCProductEntry deleteProductEntry(SCProductEntry productEntry)
		throws PortalException, SystemException {

		// Product entry

		scProductEntryPersistence.remove(productEntry);

		// Resources

		resourceLocalService.deleteResource(
			productEntry.getCompanyId(), SCProductEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			productEntry.getProductEntryId());

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			productEntry.getCompanyId(), SCProductEntry.class.getName(),
			productEntry.getProductEntryId());

		// Product screenshots

		scProductScreenshotLocalService.deleteProductScreenshots(
			productEntry.getProductEntryId());

		// Product versions

		scProductVersionLocalService.deleteProductVersions(
			productEntry.getProductEntryId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			SCProductEntry.class.getName(), productEntry.getProductEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			SCProductEntry.class.getName(), productEntry.getProductEntryId());

		return productEntry;
	}

	@Override
	public List<SCProductEntry> getCompanyProductEntries(
			long companyId, int start, int end)
		throws SystemException {

		return scProductEntryPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyProductEntriesCount(long companyId)
		throws SystemException {

		return scProductEntryPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<SCProductEntry> getProductEntries(
			long groupId, int start, int end)
		throws SystemException {

		return scProductEntryPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public List<SCProductEntry> getProductEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return scProductEntryPersistence.findByGroupId(
			groupId, start, end, obc);
	}

	@Override
	public List<SCProductEntry> getProductEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return scProductEntryPersistence.findByG_U(groupId, userId, start, end);
	}

	@Override
	public List<SCProductEntry> getProductEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return scProductEntryPersistence.findByG_U(
			groupId, userId, start, end, obc);
	}

	@Override
	public int getProductEntriesCount(long groupId) throws SystemException {
		return scProductEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getProductEntriesCount(long groupId, long userId)
		throws SystemException {

		return scProductEntryPersistence.countByG_U(groupId, userId);
	}

	@Override
	public SCProductEntry getProductEntry(long productEntryId)
		throws PortalException, SystemException {

		return scProductEntryPersistence.findByPrimaryKey(productEntryId);
	}

	@Override
	public String getRepositoryXML(
			long groupId, String baseImageURL, Date oldestDate,
			int maxNumOfVersions, Properties repoSettings)
		throws SystemException {

		return getRepositoryXML(
			groupId, null, baseImageURL, oldestDate, maxNumOfVersions,
			repoSettings);
	}

	@Override
	public String getRepositoryXML(
			long groupId, String version, String baseImageURL, Date oldestDate,
			int maxNumOfVersions, Properties repoSettings)
		throws SystemException {

		Document doc = SAXReaderUtil.createDocument();

		doc.setXMLEncoding(StringPool.UTF8);

		Element root = doc.addElement("plugin-repository");

		Element settingsEl = root.addElement("settings");

		populateSettingsElement(settingsEl, repoSettings);

		List<SCProductEntry> productEntries =
			scProductEntryPersistence.findByGroupId(groupId);

		for (SCProductEntry productEntry : productEntries) {
			if (Validator.isNull(productEntry.getRepoGroupId()) ||
				Validator.isNull(productEntry.getRepoArtifactId())) {

				continue;
			}

			List<SCProductVersion> productVersions =
				scProductVersionPersistence.findByProductEntryId(
					productEntry.getProductEntryId());

			for (int i = 0; i < productVersions.size(); i++) {
				SCProductVersion productVersion = productVersions.get(i);

				if ((maxNumOfVersions > 0) && (maxNumOfVersions < (i + 1))) {
					break;
				}

				if (!productVersion.isRepoStoreArtifact()) {
					continue;
				}

				if ((oldestDate != null) &&
					oldestDate.after(productVersion.getModifiedDate())) {

					continue;
				}

				if (Validator.isNotNull(version) &&
					!isVersionSupported(
						version, productVersion.getFrameworkVersions())) {

					continue;
				}

				Element el = root.addElement("plugin-package");

				populatePluginPackageElement(
					el, productEntry, productVersion, baseImageURL);
			}
		}

		return doc.asXML();
	}

	@Override
	@Indexable(type = IndexableType.REINDEX)
	public SCProductEntry updateProductEntry(
			long productEntryId, String name, String type, String tags,
			String shortDescription, String longDescription, String pageURL,
			String author, String repoGroupId, String repoArtifactId,
			long[] licenseIds, List<byte[]> thumbnails, List<byte[]> fullImages)
		throws PortalException, SystemException {

		// Product entry

		tags = getTags(tags);
		repoGroupId = repoGroupId.trim().toLowerCase();
		repoArtifactId = repoArtifactId.trim().toLowerCase();
		Date now = new Date();

		validate(
			productEntryId, name, type, shortDescription, pageURL, author,
			repoGroupId, repoArtifactId, licenseIds, thumbnails, fullImages);

		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(productEntryId);

		productEntry.setModifiedDate(now);
		productEntry.setName(name);
		productEntry.setType(type);
		productEntry.setTags(tags);
		productEntry.setShortDescription(shortDescription);
		productEntry.setLongDescription(longDescription);
		productEntry.setPageURL(pageURL);
		productEntry.setAuthor(author);
		productEntry.setRepoGroupId(repoGroupId);
		productEntry.setRepoArtifactId(repoArtifactId);

		scProductEntryPersistence.update(productEntry);

		// Licenses

		scProductEntryPersistence.setSCLicenses(productEntryId, licenseIds);

		// Product screenshots

		if (thumbnails.size() == 0) {
			scProductScreenshotLocalService.deleteProductScreenshots(
				productEntryId);
		}
		else {
			saveProductScreenshots(productEntry, thumbnails, fullImages);
		}

		return productEntry;
	}

	protected String getTags(String tags) {
		tags = tags.trim().toLowerCase();

		return StringUtil.merge(StringUtil.split(tags), ", ");
	}

	protected boolean isVersionSupported(
		String version, List<SCFrameworkVersion> frameworkVersions) {

		Version currentVersion = Version.getInstance(version);

		for (SCFrameworkVersion frameworkVersion : frameworkVersions) {
			Version supportedVersion = Version.getInstance(
				frameworkVersion.getName());

			if (supportedVersion.includes(currentVersion)) {
				return true;
			}
		}

		return false;
	}

	protected void populatePluginPackageElement(
			Element el, SCProductEntry productEntry,
			SCProductVersion productVersion, String baseImageURL)
		throws SystemException {

		DocUtil.add(el, "name", productEntry.getName());

		String moduleId = ModuleId.toString(
			productEntry.getRepoGroupId(), productEntry.getRepoArtifactId(),
			productVersion.getVersion(), "war");

		DocUtil.add(el, "module-id", moduleId);

		DocUtil.add(
			el, "modified-date",
			Time.getRFC822(productVersion.getModifiedDate()));

		Element typesEl = el.addElement("types");

		DocUtil.add(typesEl, "type", productEntry.getType());

		Element tagsEl = el.addElement("tags");

		String[] tags = StringUtil.split(productEntry.getTags());

		for (int i = 0; i < tags.length; i++) {
			DocUtil.add(tagsEl, "tag", tags[i]);
		}

		DocUtil.add(
			el, "short-description", productEntry.getShortDescription());

		if (Validator.isNotNull(productEntry.getLongDescription())) {
			DocUtil.add(
				el, "long-description", productEntry.getLongDescription());
		}

		if (Validator.isNotNull(productVersion.getChangeLog())) {
			DocUtil.add(el, "change-log", productVersion.getChangeLog());
		}

		if (Validator.isNotNull(productVersion.getDirectDownloadURL())) {
			DocUtil.add(
				el, "download-url", productVersion.getDirectDownloadURL());
		}

		DocUtil.add(el, "author", productEntry.getAuthor());

		Element screenshotsEl = el.addElement("screenshots");

		for (SCProductScreenshot screenshot : productEntry.getScreenshots()) {
			long thumbnailId = screenshot.getThumbnailId();
			long fullImageId = screenshot.getFullImageId();

			Element screenshotEl = screenshotsEl.addElement("screenshot");

			DocUtil.add(
				screenshotEl, "thumbnail-url",
				baseImageURL + "?img_id=" + thumbnailId + "&t=" +
					WebServerServletTokenUtil.getToken(thumbnailId));
			DocUtil.add(
				screenshotEl, "large-image-url",
				baseImageURL + "?img_id=" + fullImageId + "&t=" +
					WebServerServletTokenUtil.getToken(fullImageId));
		}

		Element licensesEl = el.addElement("licenses");

		for (SCLicense license : productEntry.getLicenses()) {
			Element licenseEl = licensesEl.addElement("license");

			licenseEl.addText(license.getName());
			licenseEl.addAttribute(
				"osi-approved", String.valueOf(license.isOpenSource()));
		}

		Element liferayVersionsEl = el.addElement("liferay-versions");

		for (SCFrameworkVersion frameworkVersion :
				productVersion.getFrameworkVersions()) {

			DocUtil.add(
				liferayVersionsEl, "liferay-version",
				frameworkVersion.getName());
		}
	}

	protected void populateSettingsElement(
		Element el, Properties repoSettings) {

		if (repoSettings == null) {
			return;
		}

		for (Map.Entry<Object, Object> entry : repoSettings.entrySet()) {
			String name = (String)entry.getKey();
			String value = (String)entry.getValue();

			Element settingEl = el.addElement("setting");

			settingEl.addAttribute("name", name);
			settingEl.addAttribute("value", value);
		}
	}

	protected void saveProductScreenshots(
			SCProductEntry productEntry, List<byte[]> thumbnails,
			List<byte[]> fullImages)
		throws PortalException, SystemException {

		long productEntryId = productEntry.getProductEntryId();

		List<SCProductScreenshot> productScreenshots =
			scProductScreenshotPersistence.findByProductEntryId(productEntryId);

		if (thumbnails.size() < productScreenshots.size()) {
			for (int i = thumbnails.size(); i < productScreenshots.size();
					i++) {

				SCProductScreenshot productScreenshot = productScreenshots.get(
					i);

				scProductScreenshotLocalService.deleteProductScreenshot(
					productScreenshot);
			}
		}

		for (int i = 0; i < thumbnails.size(); i++) {
			int priority = i;

			byte[] thumbnail = thumbnails.get(i);
			byte[] fullImage = fullImages.get(i);

			SCProductScreenshot productScreenshot =
				scProductScreenshotPersistence.fetchByP_P(
					productEntryId, priority);

			if (productScreenshot == null) {
				long productScreenshotId = counterLocalService.increment();

				productScreenshot = scProductScreenshotPersistence.create(
					productScreenshotId);

				productScreenshot.setCompanyId(productEntry.getCompanyId());
				productScreenshot.setGroupId(productEntry.getGroupId());
				productScreenshot.setProductEntryId(productEntryId);
				productScreenshot.setThumbnailId(
					counterLocalService.increment());
				productScreenshot.setFullImageId(
					counterLocalService.increment());
				productScreenshot.setPriority(priority);

				scProductScreenshotPersistence.update(productScreenshot);
			}

			imageLocalService.updateImage(
				productScreenshot.getThumbnailId(), thumbnail);
			imageLocalService.updateImage(
				productScreenshot.getFullImageId(), fullImage);
		}
	}

	protected void validate(
			long productEntryId, String name, String type,
			String shortDescription, String pageURL, String author,
			String repoGroupId, String repoArtifactId, long[] licenseIds,
			List<byte[]> thumbnails, List<byte[]> fullImages)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new ProductEntryNameException();
		}

		if (Validator.isNull(type)) {
			throw new ProductEntryTypeException();
		}

		if (Validator.isNull(shortDescription)) {
			throw new ProductEntryShortDescriptionException();
		}

		if (Validator.isNull(pageURL)) {
			throw new ProductEntryPageURLException();
		}
		else if (!Validator.isUrl(pageURL)) {
			throw new ProductEntryPageURLException();
		}

		if (Validator.isNull(author)) {
			throw new ProductEntryAuthorException();
		}

		SCProductEntry productEntry = scProductEntryPersistence.fetchByRG_RA(
			repoGroupId, repoArtifactId);

		if ((productEntry != null) &&
			(productEntry.getProductEntryId() != productEntryId)) {

			throw new DuplicateProductEntryModuleIdException();
		}

		if (licenseIds.length == 0) {
			throw new ProductEntryLicenseException();
		}

		if (thumbnails.size() != fullImages.size()) {
			throw new ProductEntryScreenshotsException();
		}
		else {
			for (byte[] thumbnail : thumbnails) {
				if (thumbnail == null) {
					throw new ProductEntryScreenshotsException();
				}
			}

			for (byte[] fullImage : fullImages) {
				if (fullImage == null) {
					throw new ProductEntryScreenshotsException();
				}
			}
		}
	}

}