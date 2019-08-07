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

package com.liferay.segments.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model implementation for the SegmentsExperimentRel service. Represents a row in the &quot;SegmentsExperimentRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.segments.model.SegmentsExperimentRel</code> interface.
 * </p>
 *
 * @author Eduardo García
 */
@ProviderType
public class SegmentsExperimentRelImpl extends SegmentsExperimentRelBaseImpl {

	public SegmentsExperimentRelImpl() {
	}

	@Override
	public String getSegmentsExperienceName(Locale locale)
		throws PortalException {

		if (getSegmentsExperienceId() ==
				SegmentsExperienceConstants.ID_DEFAULT) {

			return SegmentsExperienceConstants.getDefaultSegmentsExperienceName(
				locale);
		}

		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.getSegmentsExperience(
				getSegmentsExperienceId());

		return segmentsExperience.getName(locale);
	}

}