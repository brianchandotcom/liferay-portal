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
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Writer;

import java.net.URL;

import javax.mail.internet.InternetAddress;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
		"mvc.command.name=/admin/share_form_instance"
	},
	service = MVCActionCommand.class
)
public class ShareFormInstanceMVCActionCommand extends BaseMVCActionCommand {

	protected MailMessage createMailMessage(
			ActionRequest actionRequest, Template template)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		InternetAddress fromInternetAddress = new InternetAddress(
			user.getEmailAddress(), user.getFullName());

		MailMessage mailMessage = new MailMessage(
			fromInternetAddress, ParamUtil.getString(actionRequest, "subject"),
			render(template), true);

		String addresses = ParamUtil.getString(actionRequest, "addresses");

		InternetAddress[] toAddresses = InternetAddress.parse(addresses);

		mailMessage.setTo(toAddresses);

		return mailMessage;
	}

	protected Template createTemplate(ActionRequest actionRequest)
		throws Exception {

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY,
			getTemplateResource(_TEMPLATE_PATH), false);

		template.put("message", ParamUtil.getString(actionRequest, "message"));
		template.put("url", ParamUtil.getString(actionRequest, "url"));

		return template;
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		try {
			Template template = createTemplate(actionRequest);

			MailMessage mailMessage = createMailMessage(
				actionRequest, template);

			_mailService.sendEmail(mailMessage);

			JSONObject jsonObject = JSONUtil.put(
				"successMessage",
				LanguageUtil.get(
					httpServletRequest, "your-request-completed-successfully"));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (Exception exception) {
			_log.error("Unable to send form email", exception);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			JSONObject jsonObject = JSONUtil.put(
				"errorMessage",
				LanguageUtil.get(
					httpServletRequest, "your-request-failed-to-complete"));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
	}

	protected TemplateResource getTemplateResource(String templatePath) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL templateURL = classLoader.getResource(templatePath);

		return new URLTemplateResource(templateURL.getPath(), templateURL);
	}

	protected String render(Template template) throws TemplateException {
		Writer writer = new UnsyncStringWriter();

		template.put(TemplateConstants.NAMESPACE, _NAMESPACE);

		template.processTemplate(writer);

		return writer.toString();
	}

	private static final String _NAMESPACE = "form.share_link";

	private static final String _TEMPLATE_PATH =
		"/META-INF/resources/email/share_form_link.soy";

	private static final Log _log = LogFactoryUtil.getLog(
		ShareFormInstanceMVCActionCommand.class);

	@Reference
	private MailService _mailService;

	@Reference
	private Portal _portal;

}