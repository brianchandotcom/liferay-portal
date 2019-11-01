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

import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.adaptive.media.image.exception.DuplicateAMImageEntryException;
import com.liferay.adaptive.media.image.internal.storage.ImageStorage;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.service.base.AMImageEntryLocalServiceBaseImpl;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

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
@Component(
	property = "model.class.name=com.liferay.adaptive.media.image.model.AMImageEntry",
	service = AopService.class
)
public class AMImageEntryLocalServiceImpl
	extends AMImageEntryLocalServiceBaseImpl {

	/**
	 * Adds an adaptive media image entry in the database and stores the image
	 * bytes in the file store.
	 *
	 * @param  amImageConfigurationEntry the configuration used to create the
	 *         adaptive media image
	 * @param  fileVersion the file version used to create the adaptive media
	 *         image
	 * @param  height the adaptive media image's height
	 * @param  width the adaptive media image's width
	 * @param  inputStream the adaptive media image's input stream to store in
	 *         the file store
	 * @param  size the adaptive media image's size
	 * @return the adaptive media image
	 * @throws PortalException if an adaptive media image already exists for the
	 *         file version and configuration
	 */
	@Override
	public AMImageEntry addAMImageEntry(
			AMImageConfigurationEntry amImageConfigurationEntry,
			FileVersion fileVersion, int height, int width,
			InputStream inputStream, long size)
		throws PortalException {

		_checkDuplicateAMImageEntry(
			amImageConfigurationEntry.getUUID(),
			fileVersion.getFileVersionId());

		long imageEntryId = counterLocalService.increment();

		AMImageEntry amImageEntry = amImageEntryPersistence.create(
			imageEntryId);

		amImageEntry.setGroupId(fileVersion.getGroupId());
		amImageEntry.setCompanyId(fileVersion.getCompanyId());
		amImageEntry.setCreateDate(new Date());
		amImageEntry.setConfigurationUuid(amImageConfigurationEntry.getUUID());
		amImageEntry.setFileVersionId(fileVersion.getFileVersionId());
		amImageEntry.setMimeType(fileVersion.getMimeType());
		amImageEntry.setHeight(height);
		amImageEntry.setWidth(width);
		amImageEntry.setSize(size);

		_imageStorage.save(
			fileVersion, amImageConfigurationEntry.getUUID(), inputStream);

		return amImageEntryPersistence.update(amImageEntry);
	}

	/**
	 * Deletes all the adaptive media images generated for the configuration in
	 * the company. This method deletes both the adaptive media image entry from
	 * the database and the bytes from the file store.
	 *
	 * @param companyId the primary key of the company
	 * @param amImageConfigurationEntry the configuration used to create the
	 *        adaptive media image
	 */
	@Override
	public void deleteAMImageEntries(
		long companyId, AMImageConfigurationEntry amImageConfigurationEntry) {

		amImageEntryPersistence.removeByC_C(
			companyId, amImageConfigurationEntry.getUUID());

		_imageStorage.delete(companyId, amImageConfigurationEntry.getUUID());
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
	public void deleteAMImageEntryFileVersion(FileVersion fileVersion)
		throws PortalException {

		List<AMImageEntry> amImageEntries =
			amImageEntryPersistence.findByFileVersionId(
				fileVersion.getFileVersionId());

		for (AMImageEntry amImageEntry : amImageEntries) {
			try {
				amImageEntryPersistence.remove(amImageEntry);

				_imageStorage.delete(
					fileVersion, amImageEntry.getConfigurationUuid());
			}
			catch (AMRuntimeException.IOException amreioe) {
				_log.error(amreioe, amreioe);
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
	public void deleteAMImageEntryFileVersion(
			String configurationUuid, long fileVersionId)
		throws PortalException {

		FileVersion fileVersion = _dlAppLocalService.getFileVersion(
			fileVersionId);

		AMImageEntry amImageEntry = amImageEntryPersistence.findByC_F(
			configurationUuid, fileVersionId);

		amImageEntryPersistence.remove(amImageEntry);

		_imageStorage.delete(fileVersion, amImageEntry.getConfigurationUuid());
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
	public AMImageEntry fetchAMImageEntry(
		String configurationUuid, long fileVersionId) {

		return amImageEntryPersistence.fetchByC_F(
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
	public int getAMImageEntriesCount(
		long companyId, String configurationUuid) {

		return amImageEntryPersistence.countByC_C(companyId, configurationUuid);
	}

	/**
	 * Returns the input stream of the adaptive media image generated for a file
	 * version and configuration.
	 *
	 * @param  amImageConfigurationEntry the configuration used to create the
	 *         adaptive media image
	 * @param  fileVersion the file version used to create the adaptive media
	 *         image
	 * @return the input stream of the adaptive media image generated for a file
	 *         version and configuration
	 */
	@Override
	public InputStream getAMImageEntryContentStream(
		AMImageConfigurationEntry amImageConfigurationEntry,
		FileVersion fileVersion) {

		return _imageStorage.getContentStream(
			fileVersion, amImageConfigurationEntry.getUUID());
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
	public int getExpectedAMImageEntriesCount(long companyId) {
		Collection<AMImageCounter> amImageCounters =
			_serviceTrackerMap.values();

		Stream<AMImageCounter> amImageCounterStream = amImageCounters.stream();

		return amImageCounterStream.mapToInt(
			amImageCounter -> amImageCounter.countExpectedAMImageEntries(
				companyId)
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
		int expectedAMImageEntriesCount = getExpectedAMImageEntriesCount(
			companyId);

		if (expectedAMImageEntriesCount == 0) {
			return 0;
		}

		int actualAMImageEntriesCount = getAMImageEntriesCount(
			companyId, configurationUuid);

		int percentage =
			(actualAMImageEntriesCount * 100) / expectedAMImageEntriesCount;

		return Math.min(percentage, 100);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AMImageCounter.class, "adaptive.media.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _checkDuplicateAMImageEntry(
			String configurationUuid, long fileVersionId)
		throws DuplicateAMImageEntryException {

		AMImageEntry amImageEntry = amImageEntryPersistence.fetchByC_F(
			configurationUuid, fileVersionId);

		if (amImageEntry != null) {
			throw new DuplicateAMImageEntryException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMImageEntryLocalServiceImpl.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private ImageStorage _imageStorage;

	private ServiceTrackerMap<String, AMImageCounter> _serviceTrackerMap;

}