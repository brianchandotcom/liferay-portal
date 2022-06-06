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

package com.liferay.layout.taglib.internal.util;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.manager.SegmentsExperienceManager;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class LayoutStructureUtil {

	public static LayoutStructure getLayoutStructure(
		HttpServletRequest httpServletRequest, long plid) {

		try {
			Layout layout = _getLayout(plid);

			LayoutPageTemplateStructure layoutPageTemplateStructure =
				LayoutPageTemplateStructureLocalServiceUtil.
					fetchLayoutPageTemplateStructure(
						layout.getGroupId(), layout.getPlid(), true);

			String data = layoutPageTemplateStructure.getData(
				getSegmentsExperienceId(httpServletRequest));

			if (Validator.isNull(data)) {
				return null;
			}

			String masterLayoutData = _getMasterLayoutData(
				layout.getMasterLayoutPlid());

			if (Validator.isNull(masterLayoutData)) {
				return LayoutStructure.of(data);
			}

			return _mergeLayoutStructure(data, masterLayoutData);
		}
		catch (Exception exception) {
			_log.error("Unable to get layout structure", exception);

			return null;
		}
	}

	public static long getSegmentsExperienceId(
		HttpServletRequest httpServletRequest) {

		long selectedSegmentsExperienceId = ParamUtil.getLong(
			httpServletRequest, "segmentsExperienceId", -1);

		if (selectedSegmentsExperienceId != -1) {
			return selectedSegmentsExperienceId;
		}

		SegmentsExperienceManager segmentsExperienceManager =
			new SegmentsExperienceManager(
				ServletContextUtil.getSegmentsExperienceLocalService());

		return segmentsExperienceManager.getSegmentsExperienceId(
			httpServletRequest);
	}

	private static Layout _getLayout(long plid) {
		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		if (layout instanceof VirtualLayout) {
			VirtualLayout virtualLayout = (VirtualLayout)layout;

			layout = virtualLayout.getSourceLayout();
		}

		return layout;
	}

	private static String _getMasterLayoutData(long masterLayoutPlid) {
		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(masterLayoutPlid);

		if (masterLayoutPageTemplateEntry == null) {
			return null;
		}

		LayoutPageTemplateStructure masterLayoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					masterLayoutPageTemplateEntry.getGroupId(),
					masterLayoutPageTemplateEntry.getPlid());

		if (masterLayoutPageTemplateStructure == null) {
			return null;
		}

		return masterLayoutPageTemplateStructure.
			getDefaultSegmentsExperienceData();
	}

	private static LayoutStructure _mergeLayoutStructure(
		String data, String masterLayoutData) {

		LayoutStructure masterLayoutStructure = LayoutStructure.of(
			masterLayoutData);

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			masterLayoutStructure.addLayoutStructureItem(layoutStructureItem);
		}

		DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
			(DropZoneLayoutStructureItem)
				masterLayoutStructure.getDropZoneLayoutStructureItem();

		dropZoneLayoutStructureItem.addChildrenItem(
			layoutStructure.getMainItemId());

		LayoutStructureItem rootStructureItem =
			masterLayoutStructure.getLayoutStructureItem(
				layoutStructure.getMainItemId());

		rootStructureItem.setParentItemId(
			dropZoneLayoutStructureItem.getItemId());

		return masterLayoutStructure;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStructureUtil.class);

}