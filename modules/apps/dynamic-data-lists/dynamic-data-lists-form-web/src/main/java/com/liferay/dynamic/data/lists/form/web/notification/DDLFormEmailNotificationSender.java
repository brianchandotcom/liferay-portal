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

package com.liferay.dynamic.data.lists.form.web.notification;

import com.liferay.dynamic.data.lists.form.web.configuration.DDLFormWebConfigurationValues;
import com.liferay.dynamic.data.lists.form.web.util.DDLFormEmailNotificationUtil;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.mail.service.MailService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.ContentUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = DDLFormEmailNotificationSender.class)
public class DDLFormEmailNotificationSender {

	public void sendEmailNotification(
		PortletRequest portletRequest, DDLRecord record) {

		try {
			MailMessage mailMessage = createMailMessage(portletRequest, record);

			_mailService.sendEmail(mailMessage);
		}
		catch (Exception e) {
			_log.error("The form email could not be sent", e);
		}
	}

	protected MailMessage createMailMessage(
			PortletRequest portletRequest, DDLRecord record)
		throws Exception {

		DDLRecordSet recordSet = record.getRecordSet();

		String emailFromAddress =
			DDLFormEmailNotificationUtil.getEmailFromAddress(recordSet);
		String emailFromName = DDLFormEmailNotificationUtil.getEmailFromName(
			recordSet);

		InternetAddress fromInternetAddress = new InternetAddress(
			emailFromAddress, emailFromName);

		String subject = DDLFormEmailNotificationUtil.getEmailSubject(
			recordSet);

		String body = getEmailBody(portletRequest, recordSet, record);

		MailMessage mailMessage = new MailMessage(
			fromInternetAddress, subject, body, true);

		String emailToAddress = DDLFormEmailNotificationUtil.getEmailToAddress(
			recordSet);

		InternetAddress[] toAddresses = InternetAddress.parse(emailToAddress);

		mailMessage.setTo(toAddresses);

		return mailMessage;
	}

	protected Map<String, Serializable> getContext(
			PortletRequest portletRequest, DDLRecordSet recordSet,
			DDLRecord record)
		throws PortalException {

		Map<String, Serializable> context = new HashMap<>();

		Locale locale = getDDMFormDefaultLocale(recordSet.getDDMStructure());

		context.put("formName", recordSet.getName(locale));

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group siteGroup = themeDisplay.getSiteGroup();

		context.put("siteName", siteGroup.getName(locale));
		context.put("formFieldsValues", getSerializedDDMFormValues(record));
		context.put(
			"fromName",
			DDLFormEmailNotificationUtil.getEmailFromName(recordSet));
		context.put(
			"fromAddress",
			DDLFormEmailNotificationUtil.getEmailFromAddress(recordSet));
		context.put("portalUrl", PortalUtil.getPortalURL(portletRequest));

		return context;
	}

	protected Locale getDDMFormDefaultLocale(DDMStructure ddmStructure) {
		DDMForm ddmForm = ddmStructure.getDDMForm();

		return ddmForm.getDefaultLocale();
	}

	protected String getEmailBody(
			PortletRequest portletRequest, DDLRecordSet recordSet,
			DDLRecord record)
		throws Exception {

		Class<?> clazz = getClass();

		String notificationTemplate = ContentUtil.get(
			clazz.getClassLoader(), DDLFormWebConfigurationValues.
			DDL_FORM_WEB_EMAIL_FORM_ENTRY_NOTIFICATION_BODY);

		Map<String, Serializable> context = getContext(
			portletRequest, recordSet, record);

		return replaceTokens(notificationTemplate, context);
	}

	protected String getSerializedDDMFormValues(DDLRecord record)
		throws PortalException {

		DDMFormValues ddmFormValues = record.getDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		StringBundler sb = new StringBundler(ddmFormFieldValues.size() * 4);

		Locale defaultLocale = ddmFormValues.getDefaultLocale();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

			LocalizedValue label = ddmFormField.getLabel();

			sb.append(label.getString(defaultLocale));
			sb.append(": ");
			sb.append(
				renderDDMFormFieldValue(ddmFormFieldValue, defaultLocale));
			sb.append("<br />");
		}

		return sb.toString();
	}

	protected String renderDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueRenderer(
				ddmFormFieldValue.getType());

		return ddmFormFieldValueRenderer.render(ddmFormFieldValue, locale);
	}

	protected String replaceTokens(
			String notificationTemplate, Map<String, Serializable> context)
		throws Exception {

		return StringUtil.replace(
			notificationTemplate,
			new String[] {
				"[$FORM_NAME$]", "[$SITE_NAME$]", "[$FORM_FIELDS_VALUES$]",
				"[$FROM_NAME$]", "[$FROM_ADDRESS$]", "[$PORTAL_URL$]"
			},
			new String[] {
				GetterUtil.getString(context.get("formName")),
				GetterUtil.getString(context.get("siteName")),
				GetterUtil.getString(context.get("formFieldsValues")),
				GetterUtil.getString(context.get("fromName")),
				GetterUtil.getString(context.get("fromAddress")),
				GetterUtil.getString(context.get("portalUrl"))
			});
	}

	@Reference
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	@Reference
	protected void setMailService(MailService mailService) {
		_mailService = mailService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLFormEmailNotificationSender.class);

	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private MailService _mailService;

}