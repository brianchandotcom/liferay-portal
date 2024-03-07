/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.service.taglib;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.internal.service.taglib.util.ClientExtensionDynamicIncludeUtil;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.type.GlobalJSCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyNonceProviderUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Thiago Buarque
 */
public class ClientExtensionGlobalJSScriptInclude {

	public ClientExtensionGlobalJSScriptInclude(
		CETManager cetManager, JSONFactory jsonFactory) {

		_cetManager = cetManager;
		_jsonFactory = jsonFactory;
	}

	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String scriptLocation)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PrintWriter printWriter = httpServletResponse.getWriter();

		List<ClientExtensionEntryRel> clientExtensionEntryRels =
			ClientExtensionDynamicIncludeUtil.getClientExtensionEntryRels(
				themeDisplay.getLayout(),
				ClientExtensionEntryConstants.TYPE_GLOBAL_JS);

		for (ClientExtensionEntryRel clientExtensionEntryRel :
				clientExtensionEntryRels) {

			GlobalJSCET globalJSCET = (GlobalJSCET)_cetManager.getCET(
				clientExtensionEntryRel.getCompanyId(),
				clientExtensionEntryRel.getCETExternalReferenceCode());

			if (globalJSCET == null) {
				continue;
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				UnicodePropertiesBuilder.create(
					true
				).fastLoad(
					clientExtensionEntryRel.getTypeSettings()
				).build();

			if (!Objects.equals(
					typeSettingsUnicodeProperties.getProperty(
						"scriptLocation", StringPool.BLANK),
					scriptLocation)) {

				continue;
			}

			printWriter.print("<script");
			printWriter.print(
				ContentSecurityPolicyNonceProviderUtil.getNonceAttribute(
					httpServletRequest));

			String loadType = typeSettingsUnicodeProperties.getProperty(
				"loadType", StringPool.BLANK);

			if (Validator.isNotNull(loadType) &&
				!Objects.equals(loadType, "default")) {

				printWriter.print(StringPool.SPACE);
				printWriter.print(loadType);
				printWriter.print(StringPool.SPACE);
			}

			if (FeatureFlagManagerUtil.isEnabled("LPD-10981")) {
				printWriter.print(StringPool.SPACE);
				printWriter.print(
					_getMappedScriptElementAttributes(
						globalJSCET.getScriptElementAttributes()));
			}

			printWriter.print(" data-senna-track=\"temporary\" src=\"");
			printWriter.print(globalJSCET.getURL());
			printWriter.print("\" type=\"text/javascript\"></script>");
		}
	}

	private String _getMappedScriptElementAttributes(
		String scriptElementAttributes) {

		StringBuilder stringBuilder = new StringBuilder();

		try {
			JSONObject scriptElementAttributesJSONObject =
				_jsonFactory.createJSONObject(scriptElementAttributes);

			Set<String> keySet = scriptElementAttributesJSONObject.keySet();

			int i = 0;

			for (String key : keySet) {
				Object value = scriptElementAttributesJSONObject.get(key);

				if (value instanceof Boolean) {
					if (!(boolean)value) {
						continue;
					}

					stringBuilder.append(key);
				}
				else {
					stringBuilder.append(key);
					stringBuilder.append(StringPool.EQUAL);
					stringBuilder.append(StringPool.QUOTE);
					stringBuilder.append(
						HtmlUtil.escapeAttribute((String)value));
					stringBuilder.append(StringPool.QUOTE);
				}

				if (i != (keySet.size() - 1)) {
					stringBuilder.append(StringPool.SPACE);
				}

				i++;
			}
		}
		catch (JSONException jsonException) {
			_log.error(
				"Unable to parse script element attributes", jsonException);
		}

		return stringBuilder.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClientExtensionGlobalJSScriptInclude.class);

	private final CETManager _cetManager;
	private final JSONFactory _jsonFactory;

}