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

package com.liferay.dynamic.data.lists.form.web.util;

import com.liferay.dynamic.data.lists.form.web.configuration.DDLFormWebConfigurationValues;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistryUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.mail.service.MailServiceUtil;
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

/**
 * @author Rafael Praxedes
 */
public class DDLFormEmailNotificationSenderUtil {

	public static void sendEmailNotification(
		PortletRequest portletRequest, DDLRecord record) {

		try {
			DDLRecordSet recordSet = record.getRecordSet();

			long companyId = recordSet.getCompanyId();

			String emailFromName = recordSet.getSettingsProperty(
				"emailFromName",
				DDLFormEmailNotificationUtil.getDefaultEmailFromName(
					companyId));

			String emailFromAddress = recordSet.getSettingsProperty(
				"emailFromAddress",
				DDLFormEmailNotificationUtil.getDefaultEmailFromAddress(
					companyId));

			String emailToAddress = recordSet.getSettingsProperty(
				"emailToAddress",
				DDLFormEmailNotificationUtil.getDefaultEmailToAddress(
					recordSet));

			String subject = recordSet.getSettingsProperty(
				"emailSubject",
				DDLFormEmailNotificationUtil.getDefaultSubject(recordSet));

			InternetAddress fromInternetAddress = new InternetAddress(
				emailFromAddress, emailFromName);

			String body = getMailBody(portletRequest, recordSet, record);

			MailMessage mailMessage = new MailMessage(
				fromInternetAddress, subject, body, true);

			InternetAddress[] toAddresses = InternetAddress.parse(
				emailToAddress);

			mailMessage.setTo(toAddresses);

			MailServiceUtil.sendEmail(mailMessage);
		}
		catch (Exception e) {
			_log.error("The form email could not be sent", e);
		}
	}

	private static Map<String, Serializable> getContext(
		PortletRequest portletRequest, DDLRecordSet recordSet,
		DDLRecord record) throws PortalException {

		long companyId = recordSet.getCompanyId();

		Map<String, Serializable> context = new HashMap<>();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		String emailFromName = recordSet.getSettingsProperty(
			"emailFromName",
			DDLFormEmailNotificationUtil.getDefaultEmailFromName(companyId));

		String emailFromAddress = recordSet.getSettingsProperty(
			"emailFromAddress",
			DDLFormEmailNotificationUtil.getDefaultEmailFromAddress(companyId));

		Locale locale = ddmStructure.getDDMForm().getDefaultLocale();

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group siteGroup = themeDisplay.getSiteGroup();

		String portalUrl = PortalUtil.getPortalURL(portletRequest);

		context.put("formName", recordSet.getName(locale));
		context.put("siteName", siteGroup.getName(locale));
		context.put("formFieldsValues", getSerializedDDMFormValues(record));
		context.put("fromName", emailFromName);
		context.put("fromAddress", emailFromAddress);
		context.put("portalUrl", portalUrl);

		return context;
	}

	private static String getMailBody(
		PortletRequest portletRequest, DDLRecordSet recordSet,
		DDLRecord record) throws Exception {

		Class<?> clazz = DDLFormEmailNotificationSenderUtil.class;

		String notificationTemplate = ContentUtil.get(
			clazz.getClassLoader(), DDLFormWebConfigurationValues.
			DDL_FORM_WEB_EMAIL_FORM_ENTRY_NOTIFICATION_BODY);

		Map<String, Serializable> context = getContext(
			portletRequest, recordSet, record);

		return replaceTokens(notificationTemplate, context);
	}

	private static String getRenderedValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			DDMFormFieldValueRendererRegistryUtil.getDDMFormFieldValueRenderer(
				ddmFormFieldValue.getType());

		return ddmFormFieldValueRenderer.render(ddmFormFieldValue, locale);
	}

	private static String getSerializedDDMFormValues(DDLRecord record)
		throws PortalException {

		DDMFormValues ddmFormValues = record.getDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		StringBundler sb = new StringBundler();

		Locale defaultLocale = ddmFormValues.getDefaultLocale();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

			sb.append(ddmFormField.getLabel().getString(defaultLocale));
			sb.append(" : ");
			sb.append(getRenderedValue(ddmFormFieldValue, defaultLocale));
			sb.append("<br />");
		}

		return sb.toString();
	}

	private static String replaceTokens(
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

	private static final Log _log = LogFactoryUtil.getLog(
		DDLFormEmailNotificationSenderUtil.class);

}