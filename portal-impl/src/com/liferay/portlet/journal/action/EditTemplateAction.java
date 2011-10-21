/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.journal.DuplicateTemplateIdException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.RequiredTemplateException;
import com.liferay.portlet.journal.TemplateIdException;
import com.liferay.portlet.journal.TemplateNameException;
import com.liferay.portlet.journal.TemplateSmallImageNameException;
import com.liferay.portlet.journal.TemplateSmallImageSizeException;
import com.liferay.portlet.journal.TemplateXslException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.JournalTemplateConstants;
import com.liferay.portlet.journal.service.JournalTemplateServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.JS;

import java.io.File;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class EditTemplateAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		JournalTemplate template = null;

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				template = updateTemplate(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteTemplates(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (template != null) {
				boolean saveAndContinue = ParamUtil.getBoolean(
					actionRequest, "saveAndContinue");

				if (saveAndContinue) {
					redirect = getSaveAndContinueRedirect(
						portletConfig, actionRequest, template, redirect);
				}
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchTemplateException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.journal.error");
			}
			else if (e instanceof DuplicateTemplateIdException ||
					 e instanceof RequiredTemplateException ||
					 e instanceof TemplateIdException ||
					 e instanceof TemplateNameException ||
					 e instanceof TemplateSmallImageNameException ||
					 e instanceof TemplateSmallImageSizeException ||
					 e instanceof TemplateXslException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				if (e instanceof RequiredTemplateException) {
					String redirect = PortalUtil.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (Validator.isNotNull(redirect)) {
						actionResponse.sendRedirect(redirect);
					}
				}
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(renderRequest, Constants.CMD);

			if (!cmd.equals(Constants.ADD)) {
				ActionUtil.getTemplate(renderRequest);
			}
		}
		catch (NoSuchTemplateException nsse) {

			// Let this slide because the user can manually input a template id
			// for a new template that does not yet exist.

		}
		catch (Exception e) {
			if (//e instanceof NoSuchTemplateException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.journal.edit_template"));
	}

	protected void deleteTemplates(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String[] deleteTemplateIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteTemplateIds"));

		for (int i = 0; i < deleteTemplateIds.length; i++) {
			JournalTemplateServiceUtil.deleteTemplate(
				groupId, deleteTemplateIds[i]);

			JournalUtil.removeRecentTemplate(
				actionRequest, deleteTemplateIds[i]);
		}
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			JournalTemplate template, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String originalRedirect = ParamUtil.getString(
			actionRequest, "originalRedirect");

		PortletURLImpl portletURL = new PortletURLImpl(
			actionRequest, portletConfig.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(actionRequest.getWindowState());

		portletURL.setParameter("struts_action", "/journal/edit_template");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("originalRedirect", originalRedirect, false);
		portletURL.setParameter(
			"groupId", String.valueOf(template.getGroupId()), false);
		portletURL.setParameter("templateId", template.getTemplateId(), false);

		return portletURL.toString();
	}

	protected JournalTemplate updateTemplate(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		String cmd = ParamUtil.getString(uploadPortletRequest, Constants.CMD);

		long groupId = ParamUtil.getLong(uploadPortletRequest, "groupId");

		String templateId = ParamUtil.getString(
			uploadPortletRequest, "templateId");
		boolean autoTemplateId = ParamUtil.getBoolean(
			uploadPortletRequest, "autoTemplateId");

		String structureId = ParamUtil.getString(
			uploadPortletRequest, "structureId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		String xsl = ParamUtil.getString(uploadPortletRequest, "xsl");
		String xslContent = JS.decodeURIComponent(
			ParamUtil.getString(uploadPortletRequest, "xslContent"));
		boolean formatXsl = ParamUtil.getBoolean(
			uploadPortletRequest, "formatXsl");

		if (Validator.isNull(xsl)) {
			xsl = xslContent;
		}

		String langType = ParamUtil.getString(
			uploadPortletRequest, "langType",
			JournalTemplateConstants.LANG_TYPE_XSL);

		boolean cacheable = ParamUtil.getBoolean(
			uploadPortletRequest, "cacheable");

		boolean smallImage = ParamUtil.getBoolean(
			uploadPortletRequest, "smallImage");
		String smallImageURL = ParamUtil.getString(
			uploadPortletRequest, "smallImageURL");
		File smallFile = uploadPortletRequest.getFile("smallFile");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalTemplate.class.getName(), actionRequest);

		JournalTemplate template = null;

		if (cmd.equals(Constants.ADD)) {

			// Add template

			template = JournalTemplateServiceUtil.addTemplate(
				groupId, templateId, autoTemplateId, structureId, nameMap,
				descriptionMap, xsl, formatXsl, langType, cacheable, smallImage,
				smallImageURL, smallFile, serviceContext);
		}
		else {

			// Update template

			template = JournalTemplateServiceUtil.updateTemplate(
				groupId, templateId, structureId, nameMap, descriptionMap, xsl,
				formatXsl, langType, cacheable, smallImage, smallImageURL,
				smallFile, serviceContext);
		}

		// Recent templates

		JournalUtil.addRecentTemplate(actionRequest, template);

		return template;
	}

}