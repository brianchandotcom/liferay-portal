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

package com.liferay.template.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.exception.TemplateNameException;
import com.liferay.dynamic.data.mapping.exception.TemplateScriptException;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.template.constants.TemplatePortletKeys;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TemplatePortletKeys.TEMPLATE,
		"mvc.command.name=/template/add_ddm_template"
	},
	service = MVCActionCommand.class
)
public class AddDDMTemplateMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		long resourceClassNameId = ParamUtil.getLong(
			actionRequest, "resourceClassNameId");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			new String[] {LocaleUtil.toLanguageId(themeDisplay.getLocale())},
			new String[] {ParamUtil.getString(actionRequest, "name")});

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMTemplate.class.getName(), actionRequest);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		try {
			DDMTemplate ddmTemplate = _ddmTemplateService.addTemplate(
				themeDisplay.getScopeGroupId(), classNameId, classPK,
				resourceClassNameId, nameMap, Collections.emptyMap(),
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, StringPool.BLANK,
				TemplateConstants.LANG_TYPE_FTL,
				_getScript(classNameId, resourceClassNameId), serviceContext);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(
					"redirectURL",
					PortletURLBuilder.createRenderURL(
						_portal.getLiferayPortletResponse(actionResponse)
					).setMVCRenderCommandName(
						"/template/edit_ddm_template"
					).setRedirect(
						ParamUtil.getString(actionRequest, "redirect")
					).setTabs1(
						"information-templates"
					).setParameter(
						"ddmTemplateId", ddmTemplate.getTemplateId()
					).buildString()));
		}
		catch (PortalException portalException) {
			JSONObject errorJSONObject = JSONFactoryUtil.createJSONObject();

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				themeDisplay.getLocale(), AddDDMTemplateMVCActionCommand.class);

			if (portalException instanceof TemplateNameException) {
				errorJSONObject.put(
					"name",
					ResourceBundleUtil.getString(
						resourceBundle, "please-enter-a-valid-name"));
			}
			else if (portalException instanceof TemplateScriptException) {
				errorJSONObject.put(
					"other",
					ResourceBundleUtil.getString(
						resourceBundle, "please-enter-a-valid-script"));
			}
			else {
				errorJSONObject.put(
					"other",
					ResourceBundleUtil.getString(
						resourceBundle, "an-unexpected-error-occurred"));

				if (_log.isDebugEnabled()) {
					_log.debug(portalException.getMessage(), portalException);
				}
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("error", errorJSONObject));
		}
	}

	private String _getScript(long classNameId, long resourceClassNameId) {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(resourceClassNameId);

		if (templateHandler == null) {
			templateHandler = TemplateHandlerRegistryUtil.getTemplateHandler(
				classNameId);
		}

		if (templateHandler != null) {
			return templateHandler.getTemplatesHelpContent(
				TemplateConstants.LANG_TYPE_FTL);
		}

		return "<#-- Empty script -->";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddDDMTemplateMVCActionCommand.class);

	@Reference
	private DDMTemplateService _ddmTemplateService;

	@Reference
	private Portal _portal;

}