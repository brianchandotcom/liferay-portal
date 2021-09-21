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

package com.liferay.layout.admin.web.internal.info.item.provider;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "info.item.identifier=com.liferay.info.item.ClassPKInfoItemIdentifier",
	service = InfoItemObjectProvider.class
)
public class LayoutSegmentsExperienceInfoItemObjectProvider
	implements InfoItemObjectProvider<SegmentsExperience> {

	@Override
	public SegmentsExperience getInfoItem(long classPK)
		throws NoSuchInfoItemException {

		try {
			return _segmentsExperienceLocalService.getSegmentsExperience(
				classPK);
		}
		catch (PortalException portalException) {
			throw new NoSuchInfoItemException(
				"No segments experience found with ID " + classPK,
				portalException);
		}
	}

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}