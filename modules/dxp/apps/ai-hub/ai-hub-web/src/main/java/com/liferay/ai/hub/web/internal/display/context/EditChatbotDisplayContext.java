/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.ai.hub.web.internal.util.ActionUtil;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.attachment.AttachmentManager;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author José Abelenda
 */
public class EditChatbotDisplayContext {

	public EditChatbotDisplayContext(
		AttachmentManager attachmentManager,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectFieldLocalService objectFieldLocalService) {

		_attachmentManager = attachmentManager;
		_httpServletRequest = httpServletRequest;
		_language = language;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectFieldLocalService = objectFieldLocalService;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getReactData() throws Exception {
		ObjectField objectField = _getCompanyLogoObjectField();

		String acceptedFileExtensions = "";
		long maximumFileSize = 0;

		if (objectField != null) {
			acceptedFileExtensions = ObjectFieldSettingUtil.getValue(
				ObjectFieldSettingConstants.NAME_ACCEPTED_FILE_EXTENSIONS,
				objectField);

			maximumFileSize = _attachmentManager.getMaximumFileSize(
				objectField.getObjectFieldId(), _themeDisplay.isSignedIn());
		}

		return HashMapBuilder.<String, Object>put(
			"accountEntryExternalReferenceCode",
			() -> {
				AccountEntry accountEntry =
					AccountEntryUtil.getUserAccountEntry(
						_themeDisplay.getUserId());

				if (accountEntry == null) {
					return null;
				}

				return accountEntry.getExternalReferenceCode();
			}
		).put(
			"backURL", ActionUtil.getAIHubURL(_themeDisplay) + "/chatbots"
		).put(
			"companyLogoAcceptedFileExtensions", acceptedFileExtensions
		).put(
			"companyLogoMaximumFileSize", maximumFileSize
		).put(
			"companyLogoMaximumFileSizeLabel",
			_language.formatStorageSize(
				maximumFileSize, _themeDisplay.getLocale())
		).put(
			"companyLogoUploadTip",
			_language.format(
				_themeDisplay.getLocale(), "upload-a-x-no-larger-than-x",
				new Object[] {
					acceptedFileExtensions,
					_language.formatStorageSize(
						maximumFileSize, _themeDisplay.getLocale())
				})
		).put(
			"externalReferenceCode",
			_httpServletRequest.getParameter("externalReferenceCode")
		).put(
			"portalURL", _themeDisplay.getPortalURL()
		).build();
	}

	private ObjectField _getCompanyLogoObjectField() {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_AI_HUB_CHATBOT", _themeDisplay.getCompanyId());

		if (objectDefinition == null) {
			return null;
		}

		return _objectFieldLocalService.fetchObjectField(
			objectDefinition.getObjectDefinitionId(), "companyLogo");
	}

	private final AttachmentManager _attachmentManager;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ThemeDisplay _themeDisplay;

}