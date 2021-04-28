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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.MappingContentUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_collection_mapping_fields"
	},
	service = MVCResourceCommand.class
)
public class GetCollectionMappingFieldsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String itemSubtype = ParamUtil.getString(
			resourceRequest, "itemSubtype");

		String itemType = ParamUtil.getString(resourceRequest, "itemType");

		if (Objects.equals(DLFileEntryConstants.getClassName(), itemType)) {
			itemType = FileEntry.class.getName();
		}

		String itemSubtypeLabel = StringPool.BLANK;

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class, itemType);

		if (infoItemFormVariationsProvider != null) {
			Collection<InfoItemFormVariation> infoItemFormVariations =
				infoItemFormVariationsProvider.getInfoItemFormVariations(
					themeDisplay.getScopeGroupId());

			Stream<InfoItemFormVariation> stream =
				infoItemFormVariations.stream();

			Optional<InfoItemFormVariation> infoItemFormVariationOptional =
				stream.filter(
					infoItemFormVariation -> Objects.equals(
						itemSubtype, infoItemFormVariation.getKey())
				).findFirst();

			if (infoItemFormVariationOptional.isPresent()) {
				InfoItemFormVariation infoItemFormVariation =
					infoItemFormVariationOptional.get();

				itemSubtypeLabel = infoItemFormVariation.getLabel(
					themeDisplay.getLocale());
			}
		}

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, itemType);

		try {
			InfoForm infoForm = infoItemFormProvider.getInfoForm();

			String itemTypeLabel = infoForm.getLabel(themeDisplay.getLocale());

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"itemSubtypeLabel", itemSubtypeLabel
				).put(
					"itemTypeLabel", itemTypeLabel
				).put(
					"mappingFields",
					MappingContentUtil.getMappingFieldsJSONArray(
						itemSubtype, themeDisplay.getScopeGroupId(),
						_infoItemServiceTracker, itemType,
						themeDisplay.getLocale())
				));
		}
		catch (Exception exception) {
			_log.error("Unable to get collection mapping fields", exception);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					LanguageUtil.get(
						themeDisplay.getRequest(),
						"an-unexpected-error-occurred")));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetCollectionMappingFieldsMVCResourceCommand.class);

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}