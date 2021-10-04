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
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

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
		"mvc.command.name=/template/add_template_entry"
	},
	service = MVCActionCommand.class
)
public class AddTemplateEntryMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String infoItemClassName = ParamUtil.getString(
			actionRequest, "infoItemClassName");
		String infoItemFormVariationKey = ParamUtil.getString(
			actionRequest, "infoItemFormVariationKey");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			new String[] {
				LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale())
			},
			new String[] {ParamUtil.getString(actionRequest, "name")});

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMTemplate.class.getName(), actionRequest);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		try {
			DDMTemplate ddmTemplate = _ddmTemplateLocalService.addTemplate(
				themeDisplay.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(TemplateEntry.class), 0,
				_portal.getClassNameId(TemplateEntry.class), nameMap,
				Collections.emptyMap(),
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, StringPool.BLANK,
				TemplateConstants.LANG_TYPE_FTL, _getScript(), serviceContext);

			serviceContext = ServiceContextFactory.getInstance(
				TemplateEntry.class.getName(), actionRequest);

			TemplateEntry templateEntry =
				_templateEntryLocalService.addTemplateEntry(
					themeDisplay.getUserId(), serviceContext.getScopeGroupId(),
					ddmTemplate.getTemplateId(), infoItemClassName,
					infoItemFormVariationKey, serviceContext);

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
						"ddmTemplateId", templateEntry.getDDMTemplateId()
					).setParameter(
						"templateEntryId", templateEntry.getTemplateEntryId()
					).buildString()));
		}
		catch (PortalException portalException) {
			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(
					"error",
					_getErrorJSONObject(portalException, themeDisplay)));
		}
	}

	private JSONObject _getErrorJSONObject(
		PortalException portalException, ThemeDisplay themeDisplay) {

		if (portalException instanceof TemplateNameException) {
			return JSONUtil.put(
				"name",
				LanguageUtil.get(
					themeDisplay.getLocale(), "please-enter-a-valid-name"));
		}
		else if (portalException instanceof TemplateScriptException) {
			return JSONUtil.put(
				"other",
				LanguageUtil.get(
					themeDisplay.getLocale(), "please-enter-a-valid-script"));
		}

		if (_log.isDebugEnabled()) {
			_log.debug(portalException.getMessage(), portalException);
		}

		return JSONUtil.put(
			"other",
			LanguageUtil.get(
				themeDisplay.getLocale(), "an-unexpected-error-occurred"));
	}

	private String _getScript() {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				_portal.getClassNameId(InfoItemFormProvider.class));

		if (templateHandler != null) {
			return templateHandler.getTemplatesHelpContent(
				TemplateConstants.LANG_TYPE_FTL);
		}

		return "<#-- Empty script -->";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddTemplateEntryMVCActionCommand.class);

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private TemplateEntryLocalService _templateEntryLocalService;

}