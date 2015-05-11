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

package com.liferay.dynamic.data.mapping.web.portlet.action;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.dynamic.data.mapping.web.portlet.constants.DDMConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.StrictPortletPreferencesImpl;
import com.liferay.portlet.dynamicdatamapping.TemplateScriptException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=ddmUpdateTemplate",
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING
	},
	service = { ActionCommand.class }
)
public class DDMUpdateTemplateActionCommand extends DDMBaseActionCommand {

	@Override
	protected void doProcessCommand(PortletRequest portletRequest,
			PortletResponse portletResponse) throws Exception {
		
		DDMTemplate template = saveOrUpdateTemplate(portletRequest);
		
		String redirect = ParamUtil.getString(
			portletRequest, DDMConstants.REDIRECT);
		
		redirect = super.setRedirectAttribute(portletRequest, redirect);
		
		boolean saveAndContinue = ParamUtil.getBoolean(
			portletRequest, DDMConstants.SAVE_AND_CONTINUE);

		if (saveAndContinue) {
			redirect = getSaveAndContinueRedirect(
				portletRequest, template, redirect);
			
			portletRequest.setAttribute(WebKeys.REDIRECT,redirect);
		}

	}
	
	protected DDMTemplate saveOrUpdateTemplate(PortletRequest portletRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		long templateId = ParamUtil.getLong(
			uploadPortletRequest, DDMConstants.TEMPLATE_ID);

		long groupId = ParamUtil.getLong(
			uploadPortletRequest, DDMConstants.GROUP_ID);
		
		long classNameId = ParamUtil.getLong(
			uploadPortletRequest, DDMConstants.CLASS_NAME_ID);
		
		long classPK = ParamUtil.getLong(
			uploadPortletRequest, DDMConstants.CLASS_PK);
		
		long resourceClassNameId = ParamUtil.getLong(
			uploadPortletRequest, DDMConstants.RESOURCE_CLASS_NAME_ID);
		
		String templateKey = ParamUtil.getString(
			uploadPortletRequest, DDMConstants.TEMPLATE_KEY);
		
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			uploadPortletRequest, DDMConstants.NAME);
		
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				uploadPortletRequest, DDMConstants.DESCRIPTION);
		
		String type = ParamUtil.getString(
			uploadPortletRequest, DDMConstants.TYPE);
		
		String mode = ParamUtil.getString(
			uploadPortletRequest, DDMConstants.MODE);
		
		String language = ParamUtil.getString(
			uploadPortletRequest, DDMConstants.LANGUAGE, 
			TemplateConstants.LANG_TYPE_VM);

		String script = getScript(uploadPortletRequest);

		boolean cacheable = ParamUtil.getBoolean(
			uploadPortletRequest, DDMConstants.CACHEABLE);
		
		boolean smallImage = ParamUtil.getBoolean(
			uploadPortletRequest, DDMConstants.SMALL_IMAGE);
		
		String smallImageURL = ParamUtil.getString(
			uploadPortletRequest, DDMConstants.SMALL_IMAGE_URL);
		
		File smallImageFile = uploadPortletRequest.getFile(
			DDMConstants.SMALL_IMAGE_FILE);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMTemplate.class.getName(), uploadPortletRequest);

		DDMTemplate template = null;

		if (templateId <= 0) {
			template = DDMTemplateServiceUtil.addTemplate(
				groupId, classNameId, classPK, resourceClassNameId, templateKey,
				nameMap, descriptionMap, type, mode, language, script,
				cacheable, smallImage, smallImageURL, smallImageFile,
				serviceContext);
		}
		else {
			template = DDMTemplateServiceUtil.updateTemplate(
				templateId, classPK, nameMap, descriptionMap, type, mode,
				language, script, cacheable, smallImage, smallImageURL,
				smallImageFile, serviceContext);
		}

		PortletPreferences portletPreferences = getStrictPortletSetup(
			portletRequest);

		if (portletPreferences != null) {
			if (type.equals(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY)) {
				portletPreferences.setValue(
					DDMConstants.DISPLAY_DDM_TEMPLATE_ID,
					String.valueOf(template.getTemplateId()));
			}
			else {
				portletPreferences.setValue(
					DDMConstants.FORM_DDM_TEMPLATE_ID,
					String.valueOf(template.getTemplateId()));
			}

			portletPreferences.store();
		}

		return template;
	}
	
	protected String getSaveAndContinueRedirect(
		PortletRequest portletRequest, DDMTemplate template, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletResourceNamespace = ParamUtil.getString(
			portletRequest, DDMConstants.PORTLET_RESOURCE_NAMESPACE);
		
		long classNameId = ParamUtil.getLong(
			portletRequest, DDMConstants.CLASS_NAME_ID);
		
		long classPK = ParamUtil.getLong(
			portletRequest, DDMConstants.CLASS_PK);
		
		String structureAvailableFields = ParamUtil.getString(
			portletRequest, DDMConstants.STRUCTURE_AVAILABLE_FIELDS);

		PortletURLImpl portletURL = new PortletURLImpl(
			portletRequest, PortletKeys.DYNAMIC_DATA_MAPPING,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter(DDMConstants.MVC_PATH, "/edit_template.jsp");
		
		portletURL.setParameter(DDMConstants.REDIRECT, redirect, false);
		
		portletURL.setParameter(
			DDMConstants.PORTLET_RESOURCE_NAMESPACE, portletResourceNamespace, 
			false);
		
		portletURL.setParameter(
			DDMConstants.TEMPLATE_ID, String.valueOf(template.getTemplateId()), 
			false);
		
		portletURL.setParameter(
			DDMConstants.GROUP_ID, String.valueOf(template.getGroupId()), 
			false);
		
		portletURL.setParameter(
			DDMConstants.CLASS_NAME_ID, String.valueOf(classNameId), false);
		
		portletURL.setParameter(DDMConstants.CLASS_PK, String.valueOf(classPK), 
			false);
		
		portletURL.setParameter(DDMConstants.TYPE, template.getType(), false);
		
		portletURL.setParameter(
			DDMConstants.STRUCTURE_AVAILABLE_FIELDS, structureAvailableFields, 
			false);
		
		portletURL.setWindowState(portletRequest.getWindowState());

		return portletURL.toString();
	}
	
	protected String getScript(UploadPortletRequest uploadPortletRequest)
		throws Exception {

		String fileScriptContent = getFileScriptContent(uploadPortletRequest);

		if (Validator.isNotNull(fileScriptContent)) {
			return fileScriptContent;
		}

		return ParamUtil.getString(
			uploadPortletRequest, DDMConstants.SCRIPT_CONTENT);
	}
	
	protected String getFileScriptContent(
			UploadPortletRequest uploadPortletRequest)
		throws Exception {

		File file = uploadPortletRequest.getFile(DDMConstants.SCRIPT);

		if (file == null) {
			return null;
		}

		String fileScriptContent = FileUtil.read(file);

		if (Validator.isNotNull(fileScriptContent) && !isValidFile(file)) {
			throw new TemplateScriptException();
		}

		return fileScriptContent;
	}
	
	protected boolean isValidFile(File file) {
		String contentType = MimeTypesUtil.getContentType(file);

		if (contentType.equals(ContentTypes.APPLICATION_XSLT_XML) ||
			contentType.startsWith(ContentTypes.TEXT)) {

			return true;
		}

		return false;
	}
	
	protected PortletPreferences getStrictPortletSetup(
			PortletRequest portletRequest)
		throws PortalException {

		String portletResource = ParamUtil.getString(
			portletRequest, DDMConstants.PORTLET_RESOURCE);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getStrictPortletSetup(themeDisplay.getLayout(), portletResource);
	}
	
	protected PortletPreferences getStrictPortletSetup(
			Layout layout, String portletId)
		throws PortalException {

		if (Validator.isNull(portletId)) {
			return null;
		}

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, portletId);

		if (portletPreferences instanceof StrictPortletPreferencesImpl) {
			throw new PrincipalException();
		}

		return portletPreferences;
	}
}
