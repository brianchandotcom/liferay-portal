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
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.service.base.SCProductScreenshotLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SCProductScreenshotLocalServiceImpl
	extends SCProductScreenshotLocalServiceBaseImpl {

	@Override
	public void deleteProductScreenshot(SCProductScreenshot productScreenshot)
		throws PortalException, SystemException {

		// Product screenshot

		scProductScreenshotPersistence.remove(productScreenshot);

		// Images

		imageLocalService.deleteImage(productScreenshot.getThumbnailId());
		imageLocalService.deleteImage(productScreenshot.getFullImageId());
	}

	@Override
	public void deleteProductScreenshots(long productEntryId)
		throws PortalException, SystemException {

		List<SCProductScreenshot> productScreenshots =
			scProductScreenshotPersistence.findByProductEntryId(productEntryId);

		for (SCProductScreenshot productScreenshot : productScreenshots) {
			deleteProductScreenshot(productScreenshot);
		}
	}

	@Override
	public SCProductScreenshot getProductScreenshot(
			long productEntryId, int priority)
		throws PortalException, SystemException {

		return scProductScreenshotPersistence.findByP_P(
			productEntryId, priority);
	}

	@Override
	public SCProductScreenshot getProductScreenshotByFullImageId(
			long fullImageId)
		throws PortalException, SystemException {

		return scProductScreenshotPersistence.findByFullImageId(fullImageId);
	}

	@Override
	public SCProductScreenshot getProductScreenshotByThumbnailId(
			long thumbnailId)
		throws PortalException, SystemException {

		return scProductScreenshotPersistence.findByThumbnailId(thumbnailId);
	}

	@Override
	public List<SCProductScreenshot> getProductScreenshots(long productEntryId)
		throws SystemException {

		return scProductScreenshotPersistence.findByProductEntryId(
			productEntryId);
	}

}