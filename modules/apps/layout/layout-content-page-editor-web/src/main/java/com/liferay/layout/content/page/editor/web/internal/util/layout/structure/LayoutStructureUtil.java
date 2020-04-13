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

package com.liferay.layout.content.page.editor.web.internal.util.layout.structure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.PageDefinitionConverterUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureServiceUtil;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Víctor Galán
 */
public class LayoutStructureUtil {

	public static long[] getFragmentEntryLinkIds(
		List<LayoutStructureItem> layoutStructureItems) {

		List<Long> fragmentEntryLinkIds = new ArrayList<>();

		for (LayoutStructureItem layoutStructureItem : layoutStructureItems) {
			if (!(layoutStructureItem instanceof FragmentLayoutStructureItem)) {
				continue;
			}

			FragmentLayoutStructureItem fragmentLayoutStructureItem =
				(FragmentLayoutStructureItem)layoutStructureItem;

			if (fragmentLayoutStructureItem.getFragmentEntryLinkId() <= 0) {
				continue;
			}

			fragmentEntryLinkIds.add(
				fragmentLayoutStructureItem.getFragmentEntryLinkId());
		}

		return ArrayUtil.toLongArray(fragmentEntryLinkIds);
	}

	public static LayoutStructure getLayoutStructure(
			long groupId, long plid, long segmentsExperienceId)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					groupId, PortalUtil.getClassNameId(Layout.class.getName()),
					plid, true);

		return LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));
	}

	public static String getLayoutStructureItemJSON(
			FragmentCollectionContributorTracker
				fragmentCollectionContributorTracker,
			FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
			FragmentRendererTracker fragmentRendererTracker, long groupId,
			InfoDisplayContributorTracker infoDisplayContributorTracker,
			String itemId, long plid, boolean saveInlineContent,
			boolean saveMappingConfiguration, long segmentsExperienceId)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					groupId, PortalUtil.getClassNameId(Layout.class.getName()),
					plid, true);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		PageElement pageElement = PageDefinitionConverterUtil.toPageElement(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererTracker, groupId,
			infoDisplayContributorTracker, layoutStructure,
			layoutStructure.getLayoutStructureItem(itemId), saveInlineContent,
			saveMappingConfiguration, segmentsExperienceId);

		try {
			SimpleFilterProvider simpleFilterProvider =
				new SimpleFilterProvider();

			FilterProvider filterProvider = simpleFilterProvider.addFilter(
				"Liferay.Vulcan", SimpleBeanPropertyFilter.serializeAll());

			ObjectWriter objectWriter = _objectMapper.writer(filterProvider);

			return objectWriter.writeValueAsString(pageElement);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	public static JSONObject updateLayoutPageTemplateData(
			long groupId, long segmentsExperienceId, long plid,
			UnsafeConsumer<LayoutStructure, PortalException> unsafeConsumer)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					groupId, PortalUtil.getClassNameId(Layout.class.getName()),
					plid, true);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		unsafeConsumer.accept(layoutStructure);

		JSONObject dataJSONObject = layoutStructure.toJSONObject();

		LayoutPageTemplateStructureServiceUtil.
			updateLayoutPageTemplateStructure(
				groupId, PortalUtil.getClassNameId(Layout.class.getName()),
				plid, segmentsExperienceId, dataJSONObject.toString());

		return dataJSONObject;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
			setDateFormat(new ISO8601DateFormat());
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
			setVisibility(
				PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
			setVisibility(
				PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
		}
	};

}