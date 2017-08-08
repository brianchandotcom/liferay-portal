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

package com.liferay.adaptive.media.image.service.impl;

import com.liferay.adaptive.media.exception.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.counter.AdaptiveMediaImageCounter;
import com.liferay.adaptive.media.image.exception.DuplicateAdaptiveMediaImageEntryException;
import com.liferay.adaptive.media.image.internal.storage.ImageStorage;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry;
import com.liferay.adaptive.media.image.service.base.AdaptiveMediaImageEntryLocalServiceBaseImpl;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.InputStream;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * Provides the local service for accessing, adding, and deleting adaptive media
 * image entries.
 *
 * <p>
 * This service adds adaptive media images to both the database as adaptive
 * media image entries and to the file store as bytes. Deleting adaptive media
 * images removes the adaptive media image entry from the database and the bytes
 * from the file store.
 * </p>
 *
 * <p>
 * This service is a low level API and, in general, should not be used by third
 * party developers as the entry point for adaptive media images.
 * </p>
 *
 * @author Sergio González
 */
public class AdaptiveMediaImageEntryLocalServiceImpl
	extends AdaptiveMediaImageEntryLocalServiceBaseImpl {

	/**
	 * Adds an adaptive media image entry in the database and stores the image
	 * bytes in the file store.
	 *
	 * @param  configurationEntry the configuration used to create the adaptive
	 *         media image
	 * @param  fileVersion the file version used to create the adaptive media
	 *         image
	 * @param  width the adaptive media image's width
	 * @param  height the adaptive media image's height
	 * @param  inputStream the adaptive media image's input stream to store in
	 *         the file store
	 * @param  size the adaptive media image's size
	 * @return the adaptive media image
	 * @throws PortalException if an adaptive media image already exists for the
	 *         file version and configuration
	 */
	@Override
	public AdaptiveMediaImageEntry addAdaptiveMediaImageEntry(
			AdaptiveMediaImageConfigurationEntry configurationEntry,
			FileVersion fileVersion, int width, int height,
			InputStream inputStream, int size)
		throws PortalException {

		_checkDuplicates(
			configurationEntry.getUUID(), fileVersion.getFileVersionId());

		long imageEntryId = counterLocalService.increment();

		AdaptiveMediaImageEntry adaptiveMediaImageEntry =
			adaptiveMediaImageEntryPersistence.create(imageEntryId);

		adaptiveMediaImageEntry.setCompanyId(fileVersion.getCompanyId());
		adaptiveMediaImageEntry.setGroupId(fileVersion.getGroupId());
		adaptiveMediaImageEntry.setCreateDate(new Date());
		adaptiveMediaImageEntry.setFileVersionId(
			fileVersion.getFileVersionId());
		adaptiveMediaImageEntry.setMimeType(fileVersion.getMimeType());
		adaptiveMediaImageEntry.setHeight(height);
		adaptiveMediaImageEntry.setWidth(width);
		adaptiveMediaImageEntry.setSize(size);
		adaptiveMediaImageEntry.setConfigurationUuid(
			configurationEntry.getUUID());

		imageStorage.save(
			fileVersion, configurationEntry.getUUID(), inputStream);

		return adaptiveMediaImageEntryPersistence.update(
			adaptiveMediaImageEntry);
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		Bundle bundle = FrameworkUtil.getBundle(
			AdaptiveMediaImageEntryLocalServiceImpl.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AdaptiveMediaImageCounter.class,
			"adaptive.media.key");
	}

	/**
	 * Deletes all the adaptive media images generated for the configuration in
	 * the company. This method deletes both the adaptive media image entry from
	 * the database and the bytes from the file store.
	 *
	 * @param companyId the primary key of the company
	 * @param configurationEntry the configuration used to create the adaptive
	 *        media image
	 */
	@Override
	public void deleteAdaptiveMediaImageEntries(
		long companyId,
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		adaptiveMediaImageEntryPersistence.removeByC_C(
			companyId, configurationEntry.getUUID());

		imageStorage.delete(companyId, configurationEntry.getUUID());
	}

	/**
	 * Deletes all the adaptive media images generated for a file version. This
	 * method deletes both the adaptive media image entry from the database and
	 * the bytes from the file store.
	 *
	 * @param  fileVersion the file version
	 * @throws PortalException if the file version was not found
	 */
	@Override
	public void deleteAdaptiveMediaImageEntryFileVersion(
			FileVersion fileVersion)
		throws PortalException {

		List<AdaptiveMediaImageEntry> adaptiveMediaImageEntries =
			adaptiveMediaImageEntryPersistence.findByFileVersionId(
				fileVersion.getFileVersionId());

		for (AdaptiveMediaImageEntry adaptiveMediaImageEntry :
				adaptiveMediaImageEntries) {

			try {
				adaptiveMediaImageEntryPersistence.remove(
					adaptiveMediaImageEntry);

				imageStorage.delete(
					fileVersion,
					adaptiveMediaImageEntry.getConfigurationUuid());
			}
			catch (AdaptiveMediaRuntimeException.IOException amreioe) {
				_log.error(amreioe);
			}
		}
	}

	/**
	 * Deletes adaptive media images generated for a file version under a given
	 * configuration. This method deletes both the adaptive media image entry
	 * from the database and the bytes from the file store.
	 *
	 * @param  configurationUuid the configuration UUID
	 * @param  fileVersionId the primary key of the file version
	 * @throws PortalException if the file version was not found
	 */
	@Override
	public void deleteAdaptiveMediaImageEntryFileVersion(
			String configurationUuid, long fileVersionId)
		throws PortalException {

		FileVersion fileVersion = dlAppLocalService.getFileVersion(
			fileVersionId);

		AdaptiveMediaImageEntry adaptiveMediaImageEntry =
			adaptiveMediaImageEntryPersistence.findByC_F(
				configurationUuid, fileVersionId);

		adaptiveMediaImageEntryPersistence.remove(adaptiveMediaImageEntry);

		imageStorage.delete(
			fileVersion, adaptiveMediaImageEntry.getConfigurationUuid());
	}

	@Override
	public void destroy() {
		super.destroy();

		_serviceTrackerMap.close();
	}

	/**
	 * Returns the adaptive media image entry generated for the configuration
	 * and file version.
	 *
	 * @param  configurationUuid the UUID of the configuration used to create
	 *         the adaptive media image
	 * @param  fileVersionId the primary key of the file version
	 * @return the matching adaptive media image entry, or <code>null</code> if
	 *         a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchAdaptiveMediaImageEntry(
		String configurationUuid, long fileVersionId) {

		return adaptiveMediaImageEntryPersistence.fetchByC_F(
			configurationUuid, fileVersionId);
	}

	/**
	 * Returns the number of adaptive media image entries generated for the
	 * configuration in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  configurationUuid the UUID of the configuration used to create
	 *         the adaptive media image
	 * @return the number of adaptive media image entries in the company for the
	 *         configuration
	 */
	@Override
	public int getAdaptiveMediaImageEntriesCount(
		long companyId, String configurationUuid) {

		return adaptiveMediaImageEntryPersistence.countByC_C(
			companyId, configurationUuid);
	}

	/**
	 * Returns the input stream of the adaptive media image generated for a file
	 * version and configuration.
	 *
	 * @param  configurationEntry the configuration used to create the adaptive
	 *         media image
	 * @param  fileVersion the file version used to create the adaptive media
	 *         image
	 * @return the input stream of the adaptive media image generated for a file
	 *         version and configuration
	 */
	@Override
	public InputStream getAdaptiveMediaImageEntryContentStream(
		AdaptiveMediaImageConfigurationEntry configurationEntry,
		FileVersion fileVersion) {

		return imageStorage.getContentStream(
			fileVersion, configurationEntry.getUUID());
	}

	/**
	 * Returns the total number of adaptive media images that are expected to be
	 * in a company once they are generated. The number of adaptive media images
	 * could be less if there are images that haven't generated the adaptive
	 * media image yet.
	 *
	 * @param  companyId the primary key of the company
	 * @return the number of expected adaptive media images for a company
	 */
	@Override
	public int getExpectedAdaptiveMediaImageEntriesCount(long companyId) {
		Collection<AdaptiveMediaImageCounter> adaptiveMediaImageCounters =
			_serviceTrackerMap.values();

		Stream<AdaptiveMediaImageCounter> adaptiveMediaImageCounterStream =
			adaptiveMediaImageCounters.stream();

		return adaptiveMediaImageCounterStream.mapToInt(
			adaptiveMediaImageCounter ->
				adaptiveMediaImageCounter.
					countExpectedAdaptiveMediaImageEntries(companyId)
		).sum();
	}

	/**
	 * Returns the percentage of images that have an adaptive media image
	 * generated based on the expected number of adaptive media images for a
	 * configuration in a company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  configurationUuid the UUID of the configuration used to create
	 *         the adaptive media image
	 * @return the percentage of images that have an adaptive media image out of
	 *         the expected adaptive media images
	 */
	@Override
	public int getPercentage(long companyId, String configurationUuid) {
		int expectedEntriesCount = getExpectedAdaptiveMediaImageEntriesCount(
			companyId);

		if (expectedEntriesCount == 0) {
			return 0;
		}

		int actualEntriesCount = getAdaptiveMediaImageEntriesCount(
			companyId, configurationUuid);

		int percentage = (actualEntriesCount * 100) / expectedEntriesCount;

		return Math.min(percentage, 100);
	}

	@ServiceReference(type = DLAppLocalService.class)
	protected DLAppLocalService dlAppLocalService;

	@ServiceReference(type = ImageStorage.class)
	protected ImageStorage imageStorage;

	private void _checkDuplicates(String configurationUuid, long fileVersionId)
		throws DuplicateAdaptiveMediaImageEntryException {

		AdaptiveMediaImageEntry adaptiveMediaImageEntry =
			adaptiveMediaImageEntryPersistence.fetchByC_F(
				configurationUuid, fileVersionId);

		if (adaptiveMediaImageEntry != null) {
			throw new DuplicateAdaptiveMediaImageEntryException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdaptiveMediaImageEntryLocalServiceImpl.class);

	private ServiceTrackerMap<String, AdaptiveMediaImageCounter>
		_serviceTrackerMap;

}