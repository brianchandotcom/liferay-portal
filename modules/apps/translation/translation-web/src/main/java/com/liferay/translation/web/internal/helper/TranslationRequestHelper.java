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

package com.liferay.translation.web.internal.helper;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;

import javax.portlet.PortletRequest;

/**
 * @author Adolfo Pérez
 */
public class TranslationRequestHelper {

	public TranslationRequestHelper(PortletRequest portletRequest) {
		_portletRequest = portletRequest;
	}

	public String getClassName(long segmentsExperienceId) {

		if (segmentsExperienceId != SegmentsExperienceConstants.ID_DEFAULT) {
			return SegmentsExperience.class.getName();
		}

		return getModelClassName();
	}

	public String getClassName(long[] segmentsExperienceIds) {
		if (ArrayUtil.isEmpty(segmentsExperienceIds) ||
			((segmentsExperienceIds.length == 1) &&
			 (segmentsExperienceIds[0] ==
				 SegmentsExperienceConstants.ID_DEFAULT))) {

			return getModelClassName();
		}

		return SegmentsExperience.class.getName();
	}

	public long getClassPK(long segmentsExperienceId) {

		if (segmentsExperienceId != SegmentsExperienceConstants.ID_DEFAULT) {
			return segmentsExperienceId;
		}

		return getModelClassPK();
	}

	public long[] getClassPKs(long[] segmentsExperienceIds) {

		if (ArrayUtil.isEmpty(segmentsExperienceIds) ||
			((segmentsExperienceIds.length == 1) &&
			 (segmentsExperienceIds[0] ==
				 SegmentsExperienceConstants.ID_DEFAULT))) {

			return new long[] {getModelClassPK()};
		}

		return segmentsExperienceIds;
	}

	public String getModelClassName() {
		return PortalUtil.getClassName(
			ParamUtil.getLong(_portletRequest, "classNameId"));
	}

	public long getModelClassPK() {
		return ParamUtil.getLong(_portletRequest, "classPK");
	}

	private final PortletRequest _portletRequest;

}