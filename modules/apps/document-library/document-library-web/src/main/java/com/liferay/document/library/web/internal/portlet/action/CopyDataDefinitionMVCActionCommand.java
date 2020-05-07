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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Alicia García
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/ddm/copy_data_definition"
	},
	service = MVCActionCommand.class
)
public class CopyDataDefinitionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ddmStructureId = ParamUtil.getLong(
			actionRequest, "ddmStructureId");

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).user(
				themeDisplay.getUser()
			).build();

		DataDefinition dataDefinition =
			dataDefinitionResource.getDataDefinition(ddmStructureId);

		dataDefinition.setDataDefinitionKey(StringPool.BLANK);
		dataDefinition.setDescription(
			LocalizedValueUtil.toStringObjectMap(descriptionMap));
		dataDefinition.setName(LocalizedValueUtil.toStringObjectMap(nameMap));

		dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				themeDisplay.getScopeGroupId(), "document-library",
				dataDefinition);

		DDMStructure ddmStructure = _ddmStructureService.getStructure(
			themeDisplay.getScopeGroupId(),
			_portal.getClassNameId(DLFileEntryMetadata.class.getName()),
			dataDefinition.getDataDefinitionKey());

		boolean copyTemplates = ParamUtil.getBoolean(
			actionRequest, "copyTemplates");

		if (copyTemplates) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DDMStructure.class.getName(), actionRequest);

			_ddmTemplateService.copyTemplates(
				_portal.getClassNameId(DDMStructure.class), ddmStructureId,
				_portal.getClassNameId(DLFileEntryMetadata.class.getName()),
				ddmStructure.getStructureId(),
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, serviceContext);
		}
	}

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private DDMTemplateService _ddmTemplateService;

	@Reference
	private Portal _portal;

}