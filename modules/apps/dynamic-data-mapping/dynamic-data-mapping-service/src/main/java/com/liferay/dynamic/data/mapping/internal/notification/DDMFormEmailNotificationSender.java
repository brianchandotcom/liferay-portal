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

package com.liferay.dynamic.data.mapping.internal.notification;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.soy.data.SoyDataFactory;
import com.liferay.portal.util.PrefsPropsUtil;

import java.io.Writer;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.internet.InternetAddress;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = DDMFormEmailNotificationSender.class)
public class DDMFormEmailNotificationSender {

	public void sendEmailNotification(
		ServiceContext serviceContext,
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		try {
			MailMessage mailMessage = createMailMessage(
				serviceContext.getRequest(), ddmFormInstanceRecord);

			_mailService.sendEmail(mailMessage);
		}
		catch (Exception e) {
			_log.error("Unable to send form email", e);
		}
	}

	protected MailMessage createMailMessage(
			HttpServletRequest httpServletRequest,
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecord.getFormInstance();

		InternetAddress fromInternetAddress = new InternetAddress(
			getEmailFromAddress(ddmFormInstance),
			getEmailFromName(ddmFormInstance));

		String subject = getEmailSubject(ddmFormInstance);

		String body = getEmailBody(
			httpServletRequest, ddmFormInstance, ddmFormInstanceRecord);

		MailMessage mailMessage = new MailMessage(
			fromInternetAddress, subject, body, true);

		InternetAddress[] toAddresses = InternetAddress.parse(
			getEmailToAddress(ddmFormInstance));

		mailMessage.setTo(toAddresses);

		return mailMessage;
	}

	protected Template createTemplate(
			HttpServletRequest httpServletRequest,
			DDMFormInstance ddmFormInstance,
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY,
			getTemplateResource(_TEMPLATE_PATH), false);

		populateParameters(
			template, httpServletRequest, ddmFormInstance,
			ddmFormInstanceRecord);

		return template;
	}

	protected DDMForm getDDMForm(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		return ddmStructure.getDDMForm();
	}

	protected Map<String, List<DDMFormFieldValue>> getDDMFormFieldValuesMap(
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		DDMFormValues ddmFormValues = ddmFormInstanceRecord.getDDMFormValues();

		return ddmFormValues.getDDMFormFieldValuesMap();
	}

	protected DDMFormLayout getDDMFormLayout(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		return ddmStructure.getDDMFormLayout();
	}

	protected String getEmailBody(
			HttpServletRequest httpServletRequest,
			DDMFormInstance ddmFormInstance,
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		Template template = createTemplate(
			httpServletRequest, ddmFormInstance, ddmFormInstanceRecord);

		return render(template);
	}

	protected String getEmailFromAddress(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMFormInstanceSettings formInstancetings =
			ddmFormInstance.getSettingsModel();

		String defaultEmailFromAddress = PrefsPropsUtil.getString(
			ddmFormInstance.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

		return GetterUtil.getString(
			formInstancetings.emailFromAddress(), defaultEmailFromAddress);
	}

	protected String getEmailFromName(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMFormInstanceSettings formInstancetings =
			ddmFormInstance.getSettingsModel();

		String defaultEmailFromName = PrefsPropsUtil.getString(
			ddmFormInstance.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);

		return GetterUtil.getString(
			formInstancetings.emailFromName(), defaultEmailFromName);
	}

	protected String getEmailSubject(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMFormInstanceSettings formInstancetings =
			ddmFormInstance.getSettingsModel();

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		Locale locale = ddmForm.getDefaultLocale();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String defaultEmailSubject = LanguageUtil.format(
			resourceBundle, "new-x-form-submitted",
			ddmFormInstance.getName(locale), false);

		return GetterUtil.getString(
			formInstancetings.emailSubject(), defaultEmailSubject);
	}

	protected String getEmailToAddress(DDMFormInstance ddmFormInstance)
		throws PortalException {

		String defaultEmailToAddress = StringPool.BLANK;

		DDMFormInstanceSettings formInstancetings =
			ddmFormInstance.getSettingsModel();

		User user = _userLocalService.fetchUser(ddmFormInstance.getUserId());

		if (user != null) {
			defaultEmailToAddress = user.getEmailAddress();
		}

		return GetterUtil.getString(
			formInstancetings.emailToAddress(), defaultEmailToAddress);
	}

	protected Map<String, Object> getField(
		List<DDMFormFieldValue> ddmFormFieldValues, Locale locale) {

		String labelString = null;
		StringBundler sb = new StringBundler(
			(ddmFormFieldValues.size() * 2) - 1);

		for (int i = 0; i < ddmFormFieldValues.size(); i++) {
			DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(i);

			if (labelString == null) {
				DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

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

		return HashMapBuilder.<String, Object>put(
			"label", labelString
		).put(
			"value", _soyDataFactory.createSoyRawData(sb.toString())
		).build();
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

	protected Locale getLocale(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMForm ddmForm = getDDMForm(ddmFormInstance);

		return ddmForm.getDefaultLocale();
	}

	protected Map<String, Object> getPage(
		DDMFormLayoutPage ddmFormLayoutPage,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
		Locale locale) {

		LocalizedValue title = ddmFormLayoutPage.getTitle();

		Map<String, Object> pageMap = HashMapBuilder.<String, Object>put(
			"fields",
			getFields(
				getFieldNames(ddmFormLayoutPage), ddmFormFieldValuesMap, locale)
		).put(
			"title", title.getString(locale)
		).build();

		return pageMap;
	}

	protected List<Object> getPages(
			DDMFormInstance ddmFormInstance,
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		List<Object> pages = new ArrayList<>();

		DDMFormLayout ddmFormLayout = getDDMFormLayout(ddmFormInstance);

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			Map<String, Object> page = getPage(
				ddmFormLayoutPage,
				getDDMFormFieldValuesMap(ddmFormInstanceRecord),
				getLocale(ddmFormInstance));

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

	protected ThemeDisplay getThemeDisplay(
		HttpServletRequest httpServletRequest) {

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	protected String getUserName(
		DDMFormInstanceRecord ddmFormInstanceRecord, Locale locale) {

		String userName = ddmFormInstanceRecord.getUserName();

		if (Validator.isNotNull(userName)) {
			return userName;
		}

		return LanguageUtil.get(getResourceBundle(locale), "someone");
	}

	protected String getViewFormEntriesURL(
			DDMFormInstance ddmFormInstance, ThemeDisplay themeDisplay)
		throws PortalException {

		String portletNamespace = _portal.getPortletNamespace(
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN);

		Map<String, String[]> params = HashMapBuilder.put(
			portletNamespace.concat("mvcPath"),
			new String[] {"/admin/view_form_instance_records.jsp"}
		).put(
			portletNamespace.concat("formInstanceId"),
			new String[] {String.valueOf(ddmFormInstance.getFormInstanceId())}
		).build();

		return _portal.getSiteAdminURL(
			themeDisplay, DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
			params);
	}

	protected String getViewFormURL(
			DDMFormInstance ddmFormInstance,
			DDMFormInstanceRecord ddmFormInstanceRecord,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String portletNamespace = _portal.getPortletNamespace(
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN);

		Map<String, String[]> params = HashMapBuilder.put(
			portletNamespace.concat("mvcPath"),
			new String[] {"/admin/view_form_instance_record.jsp"}
		).put(
			portletNamespace.concat("formInstanceRecordId"),
			new String[] {
				String.valueOf(ddmFormInstanceRecord.getFormInstanceRecordId())
			}
		).put(
			portletNamespace.concat("formInstanceId"),
			new String[] {String.valueOf(ddmFormInstance.getFormInstanceId())}
		).build();

		return _portal.getSiteAdminURL(
			themeDisplay, DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
			params);
	}

	protected void populateParameters(
			Template template, HttpServletRequest httpServletRequest,
			DDMFormInstance ddmFormInstance,
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		Locale locale = getLocale(ddmFormInstance);

		template.put("authorName", ddmFormInstance.getUserName());
		template.put("formName", ddmFormInstance.getName(locale));
		template.put("pages", getPages(ddmFormInstance, ddmFormInstanceRecord));
		template.put(
			"siteName", getSiteName(ddmFormInstance.getGroupId(), locale));
		template.put("userName", getUserName(ddmFormInstanceRecord, locale));

		ThemeDisplay themeDisplay = getThemeDisplay(httpServletRequest);

		template.put(
			"viewFormEntriesURL",
			getViewFormEntriesURL(ddmFormInstance, themeDisplay));
		template.put(
			"viewFormURL",
			getViewFormURL(
				ddmFormInstance, ddmFormInstanceRecord, themeDisplay));
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
		DDMFormEmailNotificationSender.class);

	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private GroupLocalService _groupLocalService;

	private MailService _mailService;

	@Reference
	private Portal _portal;

	@Reference
	private SoyDataFactory _soyDataFactory;

	private UserLocalService _userLocalService;

}