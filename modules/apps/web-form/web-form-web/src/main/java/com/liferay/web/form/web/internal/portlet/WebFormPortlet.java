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

package com.liferay.web.form.web.internal.portlet;

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.web.form.web.configuration.WebFormServiceConfiguration;
import com.liferay.web.form.web.internal.constants.WebFormPortletKeys;
import com.liferay.web.form.web.internal.util.WebFormUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Weisser
 * @author Jorge Ferrer
 * @author Alberto Montero
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=web-form-portlet",
		"com.liferay.portlet.display-category=category.tools",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/web_form.png",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Web Form",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + WebFormPortletKeys.WEB_FORM,
		"javax.portlet.portlet-info.keywords=Web Form",
		"javax.portlet.portlet-info.short-title=Web Form",
		"javax.portlet.portlet-info.title=Web Form",
		"javax.portlet.preferences=classpath:/META-INF/portlet-preferences/default-portlet-preferences.xml",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class WebFormPortlet extends MVCPortlet {

	public void deleteData(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletId = _portal.getPortletId(actionRequest);

		PortletPermissionUtil.check(
			themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
			portletId, ActionKeys.CONFIGURATION);

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(actionRequest);

		String databaseTableName = preferences.getValue(
			"databaseTableName", StringPool.BLANK);

		if (Validator.isNotNull(databaseTableName)) {
			_expandoTableLocalService.deleteTable(
				themeDisplay.getCompanyId(), WebFormUtil.class.getName(),
				databaseTableName);
		}
	}

	public void saveData(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletId = _portal.getPortletId(actionRequest);

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletId);

		boolean requireCaptcha = GetterUtil.getBoolean(
			preferences.getValue("requireCaptcha", StringPool.BLANK));
		String successURL = GetterUtil.getString(
			preferences.getValue("successURL", StringPool.BLANK));
		boolean sendAsEmail = GetterUtil.getBoolean(
			preferences.getValue("sendAsEmail", StringPool.BLANK));
		boolean saveToDatabase = GetterUtil.getBoolean(
			preferences.getValue("saveToDatabase", StringPool.BLANK));
		String databaseTableName = GetterUtil.getString(
			preferences.getValue("databaseTableName", StringPool.BLANK));
		boolean saveToFile = GetterUtil.getBoolean(
			preferences.getValue("saveToFile", StringPool.BLANK));

		if (requireCaptcha) {
			try {
				CaptchaUtil.check(actionRequest);
			}
			catch (CaptchaTextException cte) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(cte, cte);
				}

				SessionErrors.add(
					actionRequest, CaptchaTextException.class.getName());

				return;
			}
		}

		Map<String, String> fieldsMap = new LinkedHashMap<>();

		for (int i = 1; true; i++) {
			String fieldLabel = preferences.getValue(
				"fieldLabel" + i, StringPool.BLANK);

			String fieldType = preferences.getValue(
				"fieldType" + i, StringPool.BLANK);

			if (Validator.isNull(fieldLabel)) {
				break;
			}

			if (StringUtil.equalsIgnoreCase(fieldType, "paragraph")) {
				continue;
			}

			fieldsMap.put(fieldLabel, actionRequest.getParameter("field" + i));
		}

		Set<String> validationErrors = null;

		WebFormServiceConfiguration webFormServiceConfiguration =
			getWebFormServiceConfiguration(themeDisplay.getCompanyId());

		try {
			validationErrors = validate(
				fieldsMap, webFormServiceConfiguration.validationScriptEnable(),
				preferences);
		}
		catch (Exception e) {
			SessionErrors.add(
				actionRequest, "validationScriptError", e.getMessage().trim());

			return;
		}

		if (validationErrors.isEmpty()) {
			boolean emailSuccess = true;
			boolean databaseSuccess = true;
			boolean fileSuccess = true;

			if (sendAsEmail) {
				emailSuccess = sendEmail(
					fieldsMap, preferences, webFormServiceConfiguration);
			}

			if (saveToDatabase) {
				if (Validator.isNull(databaseTableName)) {
					databaseTableName = WebFormUtil.getNewDatabaseTableName(
						portletId);

					preferences.setValue(
						"databaseTableName", databaseTableName);

					preferences.store();
				}

				databaseSuccess = saveDatabase(
					themeDisplay.getCompanyId(), fieldsMap, preferences,
					databaseTableName);
			}

			if (saveToFile) {
				String fileName = WebFormUtil.getFileName(
					themeDisplay, portletId,
					webFormServiceConfiguration.dataRootDir());

				fileSuccess = saveFile(
					fieldsMap, webFormServiceConfiguration.csvSeparator(),
					fileName);
			}

			if (emailSuccess && databaseSuccess && fileSuccess) {
				if (Validator.isNull(successURL)) {
					SessionMessages.add(actionRequest, "success");
				}
				else {
					SessionMessages.add(
						actionRequest,
						portletId +
							SessionMessages.
								KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
				}
			}
			else {
				SessionErrors.add(actionRequest, "error");
			}
		}
		else {
			for (String badField : validationErrors) {
				SessionErrors.add(actionRequest, "error" + badField);
			}
		}

		if (SessionErrors.isEmpty(actionRequest) &&
			Validator.isNotNull(successURL)) {

			actionResponse.sendRedirect(successURL);
		}
	}

	@Override
	public void serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		try {
			if (cmd.equals("captcha")) {
				serveCaptcha(resourceRequest, resourceResponse);
			}
			else if (cmd.equals("export")) {
				exportData(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void appendFieldLabels(
		Map<String, String> fieldsMap, String csvSeparator, StringBundler sb) {

		for (String fieldLabel : fieldsMap.keySet()) {
			sb.append(getCSVFormattedValue(fieldLabel));
			sb.append(csvSeparator);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(CharPool.NEW_LINE);
	}

	protected void appendFieldValues(
		Map<String, String> fieldsMap, String csvSeparator, StringBundler sb) {

		for (String fieldLabel : fieldsMap.keySet()) {
			String fieldValue = fieldsMap.get(fieldLabel);

			sb.append(getCSVFormattedValue(fieldValue));

			sb.append(csvSeparator);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(CharPool.NEW_LINE);
	}

	protected void exportData(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletId = _portal.getPortletId(resourceRequest);

		PortletPermissionUtil.check(
			themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
			portletId, ActionKeys.CONFIGURATION);

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(resourceRequest);

		String databaseTableName = preferences.getValue(
			"databaseTableName", StringPool.BLANK);
		String title = preferences.getValue("title", "no-title");

		StringBundler sb = new StringBundler();

		List<String> fieldLabels = new ArrayList<>();

		WebFormServiceConfiguration webFormServiceConfiguration =
			getWebFormServiceConfiguration(themeDisplay.getCompanyId());

		String csvSeparator = webFormServiceConfiguration.csvSeparator();

		for (int i = 1; true; i++) {
			String fieldLabel = preferences.getValue(
				"fieldLabel" + i, StringPool.BLANK);

			String localizedfieldLabel = LocalizationUtil.getPreferencesValue(
				preferences, "fieldLabel" + i, themeDisplay.getLanguageId());

			if (Validator.isNull(fieldLabel)) {
				break;
			}

			fieldLabels.add(fieldLabel);

			sb.append(getCSVFormattedValue(localizedfieldLabel));
			sb.append(csvSeparator);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(CharPool.NEW_LINE);

		if (Validator.isNotNull(databaseTableName)) {
			List<ExpandoRow> rows = _expandoRowLocalService.getRows(
				themeDisplay.getCompanyId(), WebFormUtil.class.getName(),
				databaseTableName, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (ExpandoRow row : rows) {
				for (String fieldName : fieldLabels) {
					String data = _expandoValueLocalService.getData(
						themeDisplay.getCompanyId(),
						WebFormUtil.class.getName(), databaseTableName,
						fieldName, row.getClassPK(), StringPool.BLANK);

					sb.append(getCSVFormattedValue(data));

					sb.append(csvSeparator);
				}

				sb.setIndex(sb.index() - 1);

				sb.append(CharPool.NEW_LINE);
			}
		}

		String fileName = title + ".csv";
		byte[] bytes = sb.toString().getBytes();
		String contentType = ContentTypes.APPLICATION_TEXT;

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, fileName, bytes, contentType);
	}

	protected String getCSVFormattedValue(String value) {
		StringBundler sb = new StringBundler(3);

		sb.append(CharPool.QUOTE);
		sb.append(
			StringUtil.replace(value, CharPool.QUOTE, StringPool.DOUBLE_QUOTE));
		sb.append(CharPool.QUOTE);

		return sb.toString();
	}

	protected String getMailBody(Map<String, String> fieldsMap) {
		StringBundler sb = new StringBundler();

		for (String fieldLabel : fieldsMap.keySet()) {
			String fieldValue = fieldsMap.get(fieldLabel);

			sb.append(fieldLabel);
			sb.append(" : ");
			sb.append(fieldValue);
			sb.append(CharPool.NEW_LINE);
		}

		return sb.toString();
	}

	protected WebFormServiceConfiguration getWebFormServiceConfiguration(
			long companyId)
		throws PortalException {

		return _configurationProvider.getCompanyConfiguration(
			WebFormServiceConfiguration.class, companyId);
	}

	protected boolean saveDatabase(
			long companyId, Map<String, String> fieldsMap,
			PortletPreferences preferences, String databaseTableName)
		throws Exception {

		WebFormUtil.checkTable(companyId, databaseTableName, preferences);

		long classPK = _counterLocalService.increment(
			WebFormUtil.class.getName());

		try {
			for (String fieldLabel : fieldsMap.keySet()) {
				String fieldValue = fieldsMap.get(fieldLabel);

				_expandoValueLocalService.addValue(
					companyId, WebFormUtil.class.getName(), databaseTableName,
					fieldLabel, classPK, fieldValue);
			}

			return true;
		}
		catch (Exception e) {
			_log.error(
				"The web form data could not be saved to the database", e);

			return false;
		}
	}

	protected boolean saveFile(
			Map<String, String> fieldsMap, String csvSeparator, String fileName)
		throws PortalException {

		StringBundler sb = new StringBundler();

		if (!FileUtil.exists(fileName)) {
			appendFieldLabels(fieldsMap, csvSeparator, sb);
		}

		appendFieldValues(fieldsMap, csvSeparator, sb);

		try {
			FileUtil.write(fileName, sb.toString(), false, true);

			return true;
		}
		catch (Exception e) {
			_log.error("The web form data could not be saved to a file", e);

			return false;
		}
	}

	protected boolean sendEmail(
		Map<String, String> fieldsMap, PortletPreferences preferences,
		WebFormServiceConfiguration webFormServiceConfiguration) {

		try {
			String emailAddresses = preferences.getValue(
				"emailAddress", StringPool.BLANK);

			if (Validator.isNull(emailAddresses)) {
				_log.error(
					"The web form email cannot be sent because no email " +
						"address is configured");

				return false;
			}

			String emailFromAddress = preferences.getValue(
				"emailFromAddress",
				webFormServiceConfiguration.emailFromAddress());

			String emailFromName = preferences.getValue(
				"emailFromName", webFormServiceConfiguration.emailFromName());

			InternetAddress fromAddress = new InternetAddress(
				emailFromAddress, emailFromName);

			String subject = preferences.getValue("subject", StringPool.BLANK);
			String body = getMailBody(fieldsMap);

			MailMessage mailMessage = new MailMessage(
				fromAddress, subject, body, false);

			InternetAddress[] toAddresses = InternetAddress.parse(
				emailAddresses);

			mailMessage.setTo(toAddresses);

			_mailService.sendEmail(mailMessage);

			return true;
		}
		catch (Exception e) {
			_log.error("The web form email could not be sent", e);

			return false;
		}
	}

	protected void serveCaptcha(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		CaptchaUtil.serveImage(resourceRequest, resourceResponse);
	}

	@Reference(unbind = "-")
	protected void setCounterLocalService(
		CounterLocalService counterLocalService) {

		_counterLocalService = counterLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoRowLocalService(
		ExpandoRowLocalService expandoRowLocalService) {

		_expandoRowLocalService = expandoRowLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoTableLocalService(
		ExpandoTableLocalService expandoTableLocalService) {

		_expandoTableLocalService = expandoTableLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoValueLocalService(
		ExpandoValueLocalService expandoValueLocalService) {

		_expandoValueLocalService = expandoValueLocalService;
	}

	@Reference(unbind = "-")
	protected void setMailService(MailService mailService) {
		_mailService = mailService;
	}

	protected Set<String> validate(
			Map<String, String> fieldsMap, boolean validationScriptEnable,
			PortletPreferences preferences)
		throws Exception {

		Set<String> validationErrors = new HashSet<>();

		for (int i = 0; i < fieldsMap.size(); i++) {
			String fieldType = preferences.getValue(
				"fieldType" + (i + 1), StringPool.BLANK);

			String fieldLabel = preferences.getValue(
				"fieldLabel" + (i + 1), StringPool.BLANK);

			String fieldValue = fieldsMap.get(fieldLabel);

			boolean fieldOptional = GetterUtil.getBoolean(
				preferences.getValue(
					"fieldOptional" + (i + 1), StringPool.BLANK));

			if (Objects.equals(fieldType, "paragraph")) {
				continue;
			}

			if (!fieldOptional && Validator.isNotNull(fieldLabel) &&
				Validator.isNull(fieldValue)) {

				validationErrors.add(fieldLabel);

				continue;
			}

			if (!validationScriptEnable) {
				continue;
			}

			String validationScript = GetterUtil.getString(
				preferences.getValue(
					"fieldValidationScript" + (i + 1), StringPool.BLANK));

			if (Validator.isNotNull(validationScript) &&
				!WebFormUtil.validate(
					fieldValue, fieldsMap, validationScript)) {

				validationErrors.add(fieldLabel);

				continue;
			}
		}

		return validationErrors;
	}

	private static final Log _log = LogFactoryUtil.getLog(WebFormPortlet.class);

	private static CounterLocalService _counterLocalService;
	private static ExpandoRowLocalService _expandoRowLocalService;
	private static ExpandoTableLocalService _expandoTableLocalService;
	private static ExpandoValueLocalService _expandoValueLocalService;
	private static MailService _mailService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

}