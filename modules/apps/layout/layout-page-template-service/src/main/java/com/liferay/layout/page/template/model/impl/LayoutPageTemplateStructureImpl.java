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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

/**
 * @author Eduardo García
 */
public class LayoutPageTemplateStructureImpl
	extends LayoutPageTemplateStructureBaseImpl {

	@Override
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

	@Override
	public String getData(String segmentsExperienceKey) {
		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
				getGroupId(), segmentsExperienceKey,
				PortalUtil.getClassNameId(Layout.class), getPlid());

		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			LayoutPageTemplateStructureRelLocalServiceUtil.
				fetchLayoutPageTemplateStructureRel(
					getLayoutPageTemplateStructureId(),
					segmentsExperience.getSegmentsExperienceId());

		if (layoutPageTemplateStructureRel != null) {
			return layoutPageTemplateStructureRel.getData();
		}

		return StringPool.BLANK;
	}

	@Override
	public long getPlid() {
		return getClassPK();
	}

}