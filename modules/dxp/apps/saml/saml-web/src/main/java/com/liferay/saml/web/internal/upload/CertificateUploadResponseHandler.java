/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.upload;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadResponseHandler;

import java.security.KeyStoreException;
import java.security.cert.CertificateException;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(service = CertificateUploadResponseHandler.class)
public class CertificateUploadResponseHandler implements UploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException portalException)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		String errorMessage = StringPool.BLANK;

		if (portalException instanceof PrincipalException) {
			errorMessage = _language.format(
				resourceBundle, "you-must-be-an-admin-to-complete-this-action",
				null);
		}
		else if (portalException.getCause() instanceof CertificateException) {
			errorMessage = _language.format(
				resourceBundle,
				"there-was-a-problem-reading-one-or-more-certificates-in-the-" +
					"keystore",
				null);
		}
		else if (portalException.getCause() instanceof KeyStoreException) {
			errorMessage = _language.format(
				resourceBundle, "the-file-is-not-a-pkcs12-formatted-keystore",
				null);
		}
		else {
			errorMessage = _language.format(
				resourceBundle, "an-unexpected-error-occurred", null);
		}

		JSONObject exceptionMessagesJSONObject = JSONUtil.put(
			"message", errorMessage);

		return exceptionMessagesJSONObject.put("status", 499);
	}

	@Override
	public JSONObject onSuccess(
			UploadPortletRequest uploadPortletRequest, FileEntry fileEntry)
		throws PortalException {

		return JSONUtil.put(
			"groupId", fileEntry.getGroupId()
		).put(
			"name", fileEntry.getTitle()
		).put(
			"title", uploadPortletRequest.getFileName("file")
		).put(
			"uuid", fileEntry.getUuid()
		);
	}

	@Reference
	private Language _language;

}