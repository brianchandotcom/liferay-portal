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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.exception.StructureLayoutException;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextDeserializer;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextDeserializerRequest;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
		"mvc.command.name=saveStructure"
	},
	service = MVCActionCommand.class
)
public class SaveStructureMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), actionRequest);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long structureId = ParamUtil.getLong(actionRequest, "structureId");
		String structureKey = ParamUtil.getString(
			actionRequest, "structureKey");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		DDMForm ddmForm = getDDMForm(actionRequest);
		DDMFormLayout ddmFormLayout = getDDMFormLayout(actionRequest);
		Map<Locale, String> nameMap =
			saveFormInstanceMVCCommandHelper.getNameMap(
				ddmForm, name, "untitled-element-set");
		Map<Locale, String> descriptionMap = getLocalizedMap(
			description, ddmForm.getAvailableLocales(),
			ddmForm.getDefaultLocale());

		DDMStructure ddmStructure = null;

		if (structureId == 0) {
			ddmStructure = _ddmStructureService.addStructure(
				groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				_portal.getClassNameId(DDMFormInstance.class), structureKey,
				nameMap, descriptionMap, ddmForm, ddmFormLayout,
				StorageType.JSON.toString(),
				DDMStructureConstants.TYPE_FRAGMENT, serviceContext);
		}
		else {
			ddmStructure = _ddmStructureService.updateStructure(
				groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				_portal.getClassNameId(DDMFormInstance.class), structureKey,
				nameMap, descriptionMap, ddmForm, ddmFormLayout,
				serviceContext);
		}

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, themeDisplay.getPpid(), PortletRequest.RENDER_PHASE);

		String mvcRenderCommandName = ParamUtil.getString(
			actionRequest, "mvcRenderCommandName");

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		portletURL.setParameter(
			"structureId", String.valueOf(ddmStructure.getStructureId()));

		portletURL.setParameter("redirect", redirect);

		actionRequest.setAttribute(WebKeys.REDIRECT, portletURL.toString());
	}

	protected DDMForm getDDMForm(PortletRequest portletRequest)
		throws PortalException {

		try {
			String serializedFormContext = ParamUtil.getString(
				portletRequest, "serializedFormBuilderContext");

			return ddlFormBuilderContextToDDMForm.deserialize(
				DDMFormContextDeserializerRequest.with(serializedFormContext));
		}
		catch (PortalException pe) {
			throw new StructureDefinitionException(pe);
		}
	}

	protected DDMFormLayout getDDMFormLayout(PortletRequest portletRequest)
		throws PortalException {

		try {
			String serializedFormContext = ParamUtil.getString(
				portletRequest, "serializedFormBuilderContext");

			return ddlFormBuilderContextToDDMFormLayout.deserialize(
				DDMFormContextDeserializerRequest.with(serializedFormContext));
		}
		catch (PortalException pe) {
			throw new StructureLayoutException(pe);
		}
	}

	protected Map<Locale, String> getLocalizedMap(
			String value, Set<Locale> availableLocales, Locale defaultLocale)
		throws PortalException {

		Map<Locale, String> localizedMap = new HashMap<>();

		JSONObject jsonObject = jsonFactory.createJSONObject(value);

		String defaultValueString = jsonObject.getString(
			LocaleUtil.toLanguageId(defaultLocale));

		for (Locale availableLocale : availableLocales) {
			String valueString = jsonObject.getString(
				LocaleUtil.toLanguageId(availableLocale), defaultValueString);

			localizedMap.put(availableLocale, valueString);
		}

		return localizedMap;
	}

	@Reference(
		target = "(dynamic.data.mapping.form.builder.context.deserializer.type=form)"
	)
	protected DDMFormContextDeserializer<DDMForm>
		ddlFormBuilderContextToDDMForm;

	@Reference(
		target = "(dynamic.data.mapping.form.builder.context.deserializer.type=formLayout)"
	)
	protected DDMFormContextDeserializer<DDMFormLayout>
		ddlFormBuilderContextToDDMFormLayout;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected SaveFormInstanceMVCCommandHelper saveFormInstanceMVCCommandHelper;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private Portal _portal;

}