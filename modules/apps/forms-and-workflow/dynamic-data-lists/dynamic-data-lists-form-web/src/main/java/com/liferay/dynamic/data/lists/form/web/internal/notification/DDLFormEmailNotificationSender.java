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

package com.liferay.dynamic.data.lists.form.web.internal.notification;

import com.liferay.dynamic.data.lists.form.web.internal.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PrefsPropsUtil;

import java.io.Writer;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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
			_log.error("Unable to send form email", e);
		}
	}

	protected MailMessage createMailMessage(
			PortletRequest portletRequest, DDLRecord record)
		throws Exception {

		DDLRecordSet recordSet = record.getRecordSet();

		String emailFromAddress = getEmailFromAddress(recordSet);
		String emailFromName = getEmailFromName(recordSet);

		InternetAddress fromInternetAddress = new InternetAddress(
			emailFromAddress, emailFromName);

		String subject = getEmailSubject(recordSet);

		String body = getEmailBody(portletRequest, recordSet, record);

		MailMessage mailMessage = new MailMessage(
			fromInternetAddress, subject, body, true);

		String emailToAddress = getEmailToAddress(recordSet);

		InternetAddress[] toAddresses = InternetAddress.parse(emailToAddress);

		mailMessage.setTo(toAddresses);

		return mailMessage;
	}

	protected Template createTemplate(
			PortletRequest portletRequest, DDLRecordSet recordSet,
			DDLRecord record)
		throws PortalException {

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY,
			getTemplateResource(_TEMPLATE_PATH), false);

		populateParameters(template, portletRequest, recordSet, record);

		return template;
	}

	protected DDMForm getDDMForm(DDLRecordSet recordSet)
		throws PortalException {

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return ddmStructure.getDDMForm();
	}

	protected Map<String, List<DDMFormFieldValue>> getDDMFormFieldValuesMap(
			DDLRecord record)
		throws PortalException {

		DDMFormValues ddmFormValues = record.getDDMFormValues();

		return ddmFormValues.getDDMFormFieldValuesMap();
	}

	protected DDMFormLayout getDDMFormLayout(DDLRecordSet recordSet)
		throws PortalException {

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return ddmStructure.getDDMFormLayout();
	}

	protected String getEmailBody(
			PortletRequest portletRequest, DDLRecordSet recordSet,
			DDLRecord record)
		throws PortalException {

		Template template = createTemplate(portletRequest, recordSet, record);

		return render(template);
	}

	protected String getEmailFromAddress(DDLRecordSet recordSet)
		throws PortalException {

		DDLRecordSetSettings recordSettings = recordSet.getSettingsModel();

		String defaultEmailFromAddress = PrefsPropsUtil.getString(
			recordSet.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

		return GetterUtil.getString(
			recordSettings.emailFromAddress(), defaultEmailFromAddress);
	}

	protected String getEmailFromName(DDLRecordSet recordSet)
		throws PortalException {

		DDLRecordSetSettings recordSettings = recordSet.getSettingsModel();

		String defaultEmailFromName = PrefsPropsUtil.getString(
			recordSet.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);

		return GetterUtil.getString(
			recordSettings.emailFromName(), defaultEmailFromName);
	}

	protected String getEmailSubject(DDLRecordSet recordSet)
		throws PortalException {

		DDLRecordSetSettings recordSettings = recordSet.getSettingsModel();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		Locale locale = ddmForm.getDefaultLocale();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String defaultEmailSubject = LanguageUtil.format(
			resourceBundle, "new-x-form-submitted", recordSet.getName(locale),
			false);

		return GetterUtil.getString(
			recordSettings.emailSubject(), defaultEmailSubject);
	}

	protected String getEmailToAddress(DDLRecordSet recordSet)
		throws PortalException {

		String defaultEmailToAddress = StringPool.BLANK;

		DDLRecordSetSettings recordSettings = recordSet.getSettingsModel();

		User user = _userLocalService.fetchUser(recordSet.getUserId());

		if (user != null) {
			defaultEmailToAddress = user.getEmailAddress();
		}

		return GetterUtil.getString(
			recordSettings.emailToAddress(), defaultEmailToAddress);
	}

	protected Map<String, Object> getField(
		List<DDMFormFieldValue> ddmFormFieldValues, Locale locale) {

		Map<String, Object> fieldMap = new HashMap<>();

		String labelString = null;
		StringBundler sb = new StringBundler(
			(ddmFormFieldValues.size() * 2) - 1);

		for (int i = 0; i < ddmFormFieldValues.size(); i++) {
			DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(i);

			DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

			if (labelString == null) {
				LocalizedValue label = ddmFormField.getLabel();

				labelString = label.getString(locale);

				if (ddmFormField.isRequired()) {
					labelString = labelString.concat("*");
				}
			}

			sb.append(renderDDMFormFieldValue(ddmFormFieldValue, locale));

			if (i < (ddmFormFieldValues.size() - 1)) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}
		}

		fieldMap.put("label", labelString);
		fieldMap.put("value", sb.toString());

		return fieldMap;
	}

	protected List<String> getFieldNames(DDMFormLayoutPage ddmFormLayoutPage) {
		List<String> fieldNames = new ArrayList<>();

		for (DDMFormLayoutRow ddmFormLayoutRow :
				ddmFormLayoutPage.getDDMFormLayoutRows()) {

			for (DDMFormLayoutColumn ddmFormLayoutColumn :
					ddmFormLayoutRow.getDDMFormLayoutColumns()) {

				fieldNames.addAll(ddmFormLayoutColumn.getDDMFormFieldNames());
			}
		}

		return fieldNames;
	}

	protected List<Object> getFields(
		List<String> fieldNames,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
		Locale locale) {

		List<Object> fields = new ArrayList<>();

		for (String fieldName : fieldNames) {
			List<DDMFormFieldValue> ddmFormFieldValues =
				ddmFormFieldValuesMap.get(fieldName);

			if (ddmFormFieldValues == null) {
				continue;
			}

			Map<String, Object> field = getField(ddmFormFieldValues, locale);

			fields.add(field);
		}

		return fields;
	}

	protected Locale getLocale(DDLRecordSet recordSet) throws PortalException {
		DDMForm ddmForm = getDDMForm(recordSet);

		return ddmForm.getDefaultLocale();
	}

	protected Map<String, Object> getPage(
		DDMFormLayoutPage ddmFormLayoutPage,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
		Locale locale) {

		Map<String, Object> pageMap = new HashMap<>();

		List<String> fieldNames = getFieldNames(ddmFormLayoutPage);

		pageMap.put(
			"fields", getFields(fieldNames, ddmFormFieldValuesMap, locale));

		LocalizedValue title = ddmFormLayoutPage.getTitle();

		pageMap.put("title", title.getString(locale));

		return pageMap;
	}

	protected List<Object> getPages(DDLRecordSet recordSet, DDLRecord record)
		throws PortalException {

		List<Object> pages = new ArrayList<>();

		DDMFormLayout ddmFormLayout = getDDMFormLayout(recordSet);

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			Map<String, Object> page = getPage(
				ddmFormLayoutPage, getDDMFormFieldValuesMap(record),
				getLocale(recordSet));

			pages.add(page);
		}

		return pages;
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	protected String getSiteName(long groupId, Locale locale) {
		Group siteGroup = _groupLocalService.fetchGroup(groupId);

		if (siteGroup != null) {
			return siteGroup.getName(locale);
		}

		return StringPool.BLANK;
	}

	protected TemplateResource getTemplateResource(String templatePath) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL templateURL = classLoader.getResource(templatePath);

		return new URLTemplateResource(templateURL.getPath(), templateURL);
	}

	protected ThemeDisplay getThemeDisplay(PortletRequest portletRequest) {
		return (ThemeDisplay)portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	protected String getUserName(DDLRecord record, Locale locale) {
		String userName = record.getUserName();

		if (Validator.isNotNull(userName)) {
			return userName;
		}

		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "someone");
	}

	protected String getViewFormEntriesURL(DDLRecordSet recordSet)
		throws PortalException {

		Map<String, String[]> params = new HashMap<>();

		String portletNamespace = _portal.getPortletNamespace(
			DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN);

		params.put(
			portletNamespace.concat("mvcPath"),
			new String[] {"/admin/view_records.jsp"});
		params.put(
			portletNamespace.concat("recordSetId"),
			new String[] {String.valueOf(recordSet.getRecordSetId())});

		return _portal.getControlPanelFullURL(
			recordSet.getGroupId(),
			DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN, params);
	}

	protected String getViewFormURL(DDLRecordSet recordSet, DDLRecord record)
		throws PortalException {

		Map<String, String[]> params = new HashMap<>();

		String portletNamespace = _portal.getPortletNamespace(
			DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN);

		params.put(
			portletNamespace.concat("mvcPath"),
			new String[] {"/admin/view_record.jsp"});
		params.put(
			portletNamespace.concat("recordId"),
			new String[] {String.valueOf(record.getRecordId())});
		params.put(
			portletNamespace.concat("recordSetId"),
			new String[] {String.valueOf(recordSet.getRecordSetId())});

		return _portal.getControlPanelFullURL(
			recordSet.getGroupId(),
			DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN, params);
	}

	protected void populateParameters(
			Template template, PortletRequest portletRequest,
			DDLRecordSet recordSet, DDLRecord record)
		throws PortalException {

		Locale locale = getLocale(recordSet);

		template.put("authorName", recordSet.getUserName());
		template.put("formName", recordSet.getName(locale));
		template.put("pages", getPages(recordSet, record));
		template.put("siteName", getSiteName(recordSet.getGroupId(), locale));
		template.put("userName", getUserName(record, locale));
		template.put("viewFormEntriesURL", getViewFormEntriesURL(recordSet));
		template.put("viewFormURL", getViewFormURL(recordSet, record));
	}

	protected String render(Template template) throws TemplateException {
		Writer writer = new UnsyncStringWriter();

		template.put(TemplateConstants.NAMESPACE, _NAMESPACE);

		template.processTemplate(writer);

		return writer.toString();
	}

	protected String renderDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		if (ddmFormFieldValue.getValue() == null) {
			return StringPool.BLANK;
		}

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueRenderer(
				ddmFormFieldValue.getType());

		return HtmlUtil.unescape(
			ddmFormFieldValueRenderer.render(ddmFormFieldValue, locale));
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	@Reference(unbind = "-")
	protected void setMailService(MailService mailService) {
		_mailService = mailService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final String _NAMESPACE = "form.form_entry";

	private static final String _TEMPLATE_PATH =
		"/META-INF/resources/notification/form_entry_add_body.soy";

	private static final Log _log = LogFactoryUtil.getLog(
		DDLFormEmailNotificationSender.class);

	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private GroupLocalService _groupLocalService;

	private MailService _mailService;

	@Reference
	private Portal _portal;

	private UserLocalService _userLocalService;

}