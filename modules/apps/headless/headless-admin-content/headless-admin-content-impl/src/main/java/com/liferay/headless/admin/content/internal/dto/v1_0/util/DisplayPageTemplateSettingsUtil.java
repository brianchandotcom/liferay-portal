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

package com.liferay.headless.admin.content.internal.dto.v1_0.util;

import com.liferay.headless.admin.content.dto.v1_0.ContentAssociation;
import com.liferay.headless.admin.content.dto.v1_0.DisplayPageTemplateSettings;
import com.liferay.headless.admin.content.dto.v1_0.OpenGraphSettingsMapping;
import com.liferay.headless.admin.content.dto.v1_0.SEOSettingsMapping;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class DisplayPageTemplateSettingsUtil {

	public static DisplayPageTemplateSettings getDisplayPageTemplateSettings(
		DTOConverterContext dtoConverterContext,
		InfoItemServiceTracker infoItemServiceTracker, Layout layout,
		LayoutPageTemplateEntry layoutPageTemplateEntry, Portal portal) {

		return new DisplayPageTemplateSettings() {
			{
				contentAssociation = _getContentAssociation(
					dtoConverterContext, infoItemServiceTracker,
					layoutPageTemplateEntry, portal);
				openGraphSettingsMapping = _getOpenGraphSettingsMapping(layout);
				seoSettingsMapping = _getSEOSettingsMapping(
					dtoConverterContext, layout);
			}
		};
	}

	private static ContentAssociation _getContentAssociation(
		DTOConverterContext dtoConverterContext,
		InfoItemServiceTracker infoItemServiceTracker,
		LayoutPageTemplateEntry layoutPageTemplateEntry, Portal portal) {

		String className = portal.getClassName(
			layoutPageTemplateEntry.getClassNameId());

		return new ContentAssociation() {
			{
				contentSubtype = _getContentSubtype(
					dtoConverterContext, infoItemServiceTracker,
					layoutPageTemplateEntry);
				contentType = _contentTypes.getOrDefault(className, className);
			}
		};
	}

	private static String _getContentSubtype(
		DTOConverterContext dtoConverterContext,
		InfoItemServiceTracker infoItemServiceTracker,
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				layoutPageTemplateEntry.getClassName());

		if (infoItemFormVariationsProvider == null) {
			return null;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				layoutPageTemplateEntry.getGroupId(),
				String.valueOf(layoutPageTemplateEntry.getClassTypeId()));

		if (infoItemFormVariation != null) {
			return infoItemFormVariation.getLabel(
				dtoConverterContext.getLocale());
		}

		return null;
	}

	private static OpenGraphSettingsMapping _getOpenGraphSettingsMapping(
		Layout layout) {

		return new OpenGraphSettingsMapping() {
			{
				descriptionMappingFieldKey = layout.getTypeSettingsProperty(
					"mapped-openGraphDescription", "description");
				imageAltMappingFieldKey = layout.getTypeSettingsProperty(
					"mapped-openGraphImageAlt", null);
				imageMappingFieldKey = layout.getTypeSettingsProperty(
					"mapped-openGraphImage", null);
				titleMappingFieldKey = layout.getTypeSettingsProperty(
					"mapped-openGraphTitle", "title");
			}
		};
	}

	private static SEOSettingsMapping _getSEOSettingsMapping(
		DTOConverterContext dtoConverterContext, Layout layout) {

		return new SEOSettingsMapping() {
			{
				descriptionMappingFieldKey = layout.getTypeSettingsProperty(
					"mapped-description", "description");
				htmlTitleMappingFieldKey = layout.getTypeSettingsProperty(
					"mapped-title", "title");
				robots = layout.getRobots(dtoConverterContext.getLocale());
				robots_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					layout.getRobotsMap());
			}
		};
	}

	private static final Map<String, String> _contentTypes = HashMapBuilder.put(
		"com.liferay.blogs.model.BlogsEntry", "BlogPosting"
	).put(
		"com.liferay.document.library.kernel.model.DLFileEntry", "Document"
	).put(
		"com.liferay.journal.model.JournalArticle", "StructuredContent"
	).put(
		"com.liferay.portal.kernel.repository.model.FileEntry", "Document"
	).build();

}