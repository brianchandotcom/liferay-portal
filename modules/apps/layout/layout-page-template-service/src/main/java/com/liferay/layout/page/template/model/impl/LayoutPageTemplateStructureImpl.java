/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model.impl;

import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalServiceUtil;
import com.liferay.petra.string.StringPool;
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
				getGroupId(), segmentsExperienceKey, getPlid());

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
	public String getDefaultSegmentsExperienceData() {
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			LayoutPageTemplateStructureRelLocalServiceUtil.
				fetchLayoutPageTemplateStructureRel(
					getLayoutPageTemplateStructureId(),
					SegmentsExperienceLocalServiceUtil.
						fetchDefaultSegmentsExperienceId(getPlid()));

		if (layoutPageTemplateStructureRel != null) {
			return layoutPageTemplateStructureRel.getData();
		}

		return StringPool.BLANK;
	}

}