/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.web.internal.helper;

import com.liferay.multi.factor.authentication.web.internal.constants.MFAPortletKeys;
import com.liferay.multi.factor.authentication.web.internal.constants.MFAWebKeys;
import com.liferay.portal.kernel.encryptor.Encryptor;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletRequest;
import jakarta.portlet.WindowState;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.security.Key;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 */
@Component(service = MFABrowserVerificationHelper.class)
public class MFABrowserVerificationHelper {

	public String getVerificationRedirectURL(
			HttpServletRequest httpServletRequest,
			Map<String, String[]> parameterMap, String redirect, long userId,
			WindowState windowState)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long plid = (themeDisplay == null) ? 0 : themeDisplay.getPlid();

		LiferayPortletURL liferayPortletURL = _portletURLFactory.create(
			httpServletRequest, MFAPortletKeys.MFA_VERIFY, plid,
			PortletRequest.RENDER_PHASE);

		liferayPortletURL.setParameter(
			"mvcRenderCommandName", "/mfa_verify/view");
		liferayPortletURL.setParameter("redirect", redirect);
		liferayPortletURL.setParameter(
			"saveLastPath", Boolean.FALSE.toString());

		Key key = _encryptor.generateKey();

		String encryptedStateMapJSON = _encryptor.encrypt(
			key,
			_jsonFactory.looseSerializeDeep(
				HashMapBuilder.<String, Object>put(
					"requestParameters",
					() -> HashMapBuilder.putAll(
						parameterMap
					).remove(
						"redirect"
					).build()
				).build()));

		liferayPortletURL.setParameter("state", encryptedStateMapJSON);

		liferayPortletURL.setWindowState(windowState);

		HttpSession httpSession = httpServletRequest.getSession();

		httpSession.setAttribute(MFAWebKeys.MFA_USER_ID, userId);
		httpSession.setAttribute(
			MFAWebKeys.MFA_WEB_DIGEST,
			DigesterUtil.digest(encryptedStateMapJSON));
		httpSession.setAttribute(
			MFAWebKeys.MFA_WEB_KEY, _encryptor.serializeKey(key));

		return liferayPortletURL.toString();
	}

	@Reference
	private Encryptor _encryptor;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PortletURLFactory _portletURLFactory;

}