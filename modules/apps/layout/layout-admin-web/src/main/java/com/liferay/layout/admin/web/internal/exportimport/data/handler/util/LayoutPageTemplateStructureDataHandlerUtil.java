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

package com.liferay.layout.admin.web.internal.exportimport.data.handler.util;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true, service = LayoutPageTemplateStructureDataHandlerUtil.class
)
public class LayoutPageTemplateStructureDataHandlerUtil {

	public void importLayoutPageTemplateStructure(
			PortletDataContext portletDataContext, long classNameId,
			long classPK, Element layoutPageTemplateStructureElement)
		throws Exception {

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, layoutPageTemplateStructureElement);

		Map<Long, Long> layoutPageTemplateStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				LayoutPageTemplateStructure.class);

		String path = layoutPageTemplateStructureElement.attributeValue("path");

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			(LayoutPageTemplateStructure)portletDataContext.getZipEntryAsObject(
				path);

		long layoutPageTemplateStructureId = MapUtil.getLong(
			layoutPageTemplateStructureIds,
			layoutPageTemplateStructure.getLayoutPageTemplateStructureId(),
			layoutPageTemplateStructure.getLayoutPageTemplateStructureId());

		LayoutPageTemplateStructure existingLayoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(layoutPageTemplateStructureId);

		if (existingLayoutPageTemplateStructure == null) {
			return;
		}

		existingLayoutPageTemplateStructure.setClassNameId(classNameId);
		existingLayoutPageTemplateStructure.setClassPK(classPK);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructure(
				existingLayoutPageTemplateStructure);

		List<LayoutPageTemplateStructureRel>
			existingLayoutPageTemplateStructureRels =
				_layoutPageTemplateStructureRelLocalService.
					getLayoutPageTemplateStructureRels(
						layoutPageTemplateStructureId);

		for (LayoutPageTemplateStructureRel
				existingLayoutPageTemplateStructureRel :
					existingLayoutPageTemplateStructureRels) {

			_importLayoutPageTemplateStructureRel(
				portletDataContext, existingLayoutPageTemplateStructureRel);

			_updateSegmentsExperiences(
				classNameId, classPK, existingLayoutPageTemplateStructureRel);
		}
	}

	private void _importLayoutPageTemplateStructureRel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateStructureRel
				existingLayoutPageTemplateStructureRel)
		throws Exception {

		String data = existingLayoutPageTemplateStructureRel.getData();

		if (Validator.isNull(data)) {
			return;
		}

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

		JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");

		if (structureJSONArray == null) {
			return;
		}

		Map<Long, Long> fragmentEntryLinkIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FragmentEntryLink.class);

		for (int i = 0; i < structureJSONArray.length(); i++) {
			JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

			JSONArray columnsJSONArray = rowJSONObject.getJSONArray("columns");

			for (int j = 0; j < columnsJSONArray.length(); j++) {
				JSONObject columnJSONObject = columnsJSONArray.getJSONObject(j);

				JSONArray fragmentEntryLinkIdsJSONArray =
					columnJSONObject.getJSONArray("fragmentEntryLinkIds");

				JSONArray newFragmentEntryLinkIdsJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (int k = 0; k < fragmentEntryLinkIdsJSONArray.length();
					 k++) {

					long fragmentEntryLinkId = MapUtil.getLong(
						fragmentEntryLinkIds,
						fragmentEntryLinkIdsJSONArray.getLong(k),
						fragmentEntryLinkIdsJSONArray.getLong(k));

					if (fragmentEntryLinkId <= 0) {
						continue;
					}

					newFragmentEntryLinkIdsJSONArray.put(fragmentEntryLinkId);
				}

				columnJSONObject.put(
					"fragmentEntryLinkIds", newFragmentEntryLinkIdsJSONArray);
			}
		}

		existingLayoutPageTemplateStructureRel.setData(
			dataJSONObject.toString());

		_layoutPageTemplateStructureRelLocalService.
			updateLayoutPageTemplateStructureRel(
				existingLayoutPageTemplateStructureRel);
	}

	private void _updateSegmentsExperiences(
		long classNameId, long classPK,
		LayoutPageTemplateStructureRel existingLayoutPageTemplateStructureRel) {

		if (existingLayoutPageTemplateStructureRel.getSegmentsExperienceId() ==
				SegmentsExperienceConstants.ID_DEFAULT) {

			return;
		}

		SegmentsExperience existingSegmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				existingLayoutPageTemplateStructureRel.
					getSegmentsExperienceId());

		if (existingSegmentsExperience == null) {
			return;
		}

		existingSegmentsExperience.setClassNameId(classNameId);
		existingSegmentsExperience.setClassPK(classPK);

		_segmentsExperienceLocalService.updateSegmentsExperience(
			existingSegmentsExperience);
	}

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}