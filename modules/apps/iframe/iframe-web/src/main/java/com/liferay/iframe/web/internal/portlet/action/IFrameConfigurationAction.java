/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.iframe.web.internal.portlet.action;

import com.liferay.iframe.web.internal.constants.IFramePortletKeys;
import com.liferay.iframe.web.internal.util.IFrameUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.KeyReferenceUtil;
import com.liferay.portal.security.key.secret.Secret;
import com.liferay.portal.security.key.secret.SecretManager;
import com.liferay.portal.security.key.secret.exception.SecretException;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletConfig;
import jakarta.portlet.PortletPreferences;
import jakarta.portlet.PortletRequest;
import jakarta.portlet.ReadOnlyException;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "jakarta.portlet.name=" + IFramePortletKeys.IFRAME,
	service = ConfigurationAction.class
)
public class IFrameConfigurationAction extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/configuration.jsp";
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String src = getParameter(actionRequest, "src");

		if (!src.startsWith("/") && !StringUtil.startsWith(src, "http://") &&
			!StringUtil.startsWith(src, "https://") &&
			!StringUtil.startsWith(src, "mhtml://")) {

			src = HttpComponentsUtil.getProtocol(actionRequest) + "://" + src;

			setPreference(actionRequest, "src", src);
		}

		String[] htmlAttributes = StringUtil.splitLines(
			getParameter(actionRequest, "htmlAttributes"));

		for (String htmlAttribute : htmlAttributes) {
			int pos = htmlAttribute.indexOf(CharPool.EQUAL);

			if (pos == -1) {
				continue;
			}

			String key = htmlAttribute.substring(0, pos);
			String value = htmlAttribute.substring(pos + 1);

			setPreference(actionRequest, key, value);
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	protected void postProcess(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws PortalException {

		String formPassword = portletPreferences.getValue(
			"formPassword", StringPool.BLANK);

		if (Validator.isNotNull(formPassword) &&
			formPassword.contains("@password@") &&
			!IFrameUtil.isPasswordTokenEnabled(portletRequest)) {

			formPassword = formPassword.replaceAll("@password@", "");

			try {
				portletPreferences.setValue("formPassword", formPassword);
			}
			catch (ReadOnlyException readOnlyException) {
				throw new PortalException(readOnlyException);
			}
		}

		_vault(companyId, "basicPassword", portletPreferences, portletRequest);
		_vault(companyId, "formPassword", portletPreferences, portletRequest);
	}

	private String _getIdentifier(
		String name, PortletRequest portletRequest, ThemeDisplay themeDisplay) {

		return StringBundler.concat(
			_IDENTIFIER_PREFIX, "portlet/", themeDisplay.getPlid(),
			StringPool.SLASH,
			ParamUtil.getString(portletRequest, "portletResource"),
			StringPool.SLASH, name);
	}

	private void _vault(
			long companyId, String name, PortletPreferences portletPreferences,
			PortletRequest portletRequest)
		throws PortalException {

		String value = portletPreferences.getValue(name, StringPool.BLANK);

		if (!PropsValues.FIPS_ENABLED || Validator.isNull(value)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String identifier = _getIdentifier(name, portletRequest, themeDisplay);

		if (KeyReferenceUtil.isKeyReference(value)) {
			KeyReference keyReference = KeyReferenceUtil.parseKeyReference(
				value);

			if (keyReference == null) {
				throw new SecretException("Unable to parse the key reference");
			}

			String valueIdentifier = keyReference.getIdentifier();

			if (valueIdentifier.startsWith(_IDENTIFIER_PREFIX) &&
				!valueIdentifier.equals(identifier)) {

				throw new SecretException(
					StringBundler.concat(
						"Identifier \"", identifier,
						"\" cannot reference a value belonging to \"",
						valueIdentifier, "\""));
			}

			return;
		}

		SecretManager secretManager = _secretManagerSnapshot.get();

		if (secretManager == null) {
			throw new IllegalStateException("Secret manager is unavailable");
		}

		try (Secret secret = new Secret(
				new KeyReference(
					identifier, StringPool.STAR, KeyReference.Type.SECRET),
				value)) {

			portletPreferences.setValue(
				name,
				KeyReferenceUtil.toKeyReferenceString(
					secretManager.putSecret(companyId, secret)));
		}
		catch (ReadOnlyException readOnlyException) {
			throw new PortalException(readOnlyException);
		}
	}

	private static final String _IDENTIFIER_PREFIX = "preference/";

	private static final Snapshot<SecretManager> _secretManagerSnapshot =
		new Snapshot<>(
			IFrameConfigurationAction.class, SecretManager.class, null, true);

}