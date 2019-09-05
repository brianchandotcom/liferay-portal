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

package com.liferay.layout.page.template.model.impl;

import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.Arrays;
import java.util.stream.LongStream;

/**
 * @author Eduardo García
 */
public class LayoutPageTemplateStructureImpl
	extends LayoutPageTemplateStructureBaseImpl {

	public LayoutPageTemplateStructureImpl() {
	}

	public String getData(long segmentsExperienceId) {
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			LayoutPageTemplateStructureRelLocalServiceUtil.
				fetchLayoutPageTemplateStructureRel(
					getLayoutPageTemplateStructureId(), segmentsExperienceId);

		if (layoutPageTemplateStructureRel != null) {
			return layoutPageTemplateStructureRel.getData();
		}

		return StringPool.BLANK;
	}

	public String getData(long[] segmentsExperienceIds) throws PortalException {
		long segmentsExperienceId = _getFirstSegmentsExperienceId(
			segmentsExperienceIds);

		return getData(segmentsExperienceId);
	}

	private long _getFirstSegmentsExperienceId(long[] segmentsExperienceIds) {
		LongStream stream = Arrays.stream(segmentsExperienceIds);

		return stream.filter(
			segmentsExperienceId -> {
				LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
					LayoutPageTemplateStructureRelLocalServiceUtil.
						fetchLayoutPageTemplateStructureRel(
							getLayoutPageTemplateStructureId(),
							segmentsExperienceId);

				return layoutPageTemplateStructureRel != null;
			}
		).findFirst(
		).orElse(
			SegmentsExperienceConstants.ID_DEFAULT
		);
	}

}