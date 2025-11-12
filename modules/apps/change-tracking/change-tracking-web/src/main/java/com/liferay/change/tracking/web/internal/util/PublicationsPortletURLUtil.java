/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.util;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.PortletURL;
import jakarta.portlet.RenderResponse;
import jakarta.portlet.WindowState;
import jakarta.portlet.WindowStateException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class PublicationsPortletURLUtil {

	public static String getDeleteHref(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse,
		String backURL, long ctCollectionId, Language language) {

		return getDeleteHref(
			language.get(
				httpServletRequest,
				"are-you-sure-you-want-to-delete-this-publication"),
			getHref(
				renderResponse.createActionURL(), ActionRequest.ACTION_NAME,
				"/change_tracking/delete_ct_collection", "redirect", backURL,
				"ctCollectionId", String.valueOf(ctCollectionId)));
	}

	public static String getDeleteHref(
		String confirmationMessage, String deleteURL) {

		return StringBundler.concat(
			"javascript:Liferay.Util.openConfirmModal({message: '",
			confirmationMessage,
			"', onConfirm: (isConfirmed) => {if (isConfirmed) {",
			"submitForm(document.hrefFm, '", HtmlUtil.escapeJS(deleteURL),
			"');} else {self.focus();}}});");
	}

	public static String getHref(PortletURL portletURL, Object... parameters) {
		if (parameters != null) {
			if ((parameters.length % 2) != 0) {
				throw new IllegalArgumentException(
					"Parameters length is not an even number");
			}

			for (int i = 0; i < parameters.length; i += 2) {
				String parameterName = String.valueOf(parameters[i]);
				String parameterValue = String.valueOf(parameters[i + 1]);

				portletURL.setParameter(parameterName, parameterValue);
			}
		}

		return portletURL.toString();
	}

	public static String getPermissionsHref(
			HttpServletRequest httpServletRequest, CTCollection ctCollection,
			Language language)
		throws Exception {

		return _getPermissionsHref(
			httpServletRequest, CTCollection.class.getName(),
			ctCollection.getName(),
			String.valueOf(ctCollection.getCtCollectionId()), language);
	}

	public static String getPermissionsHref(
			HttpServletRequest httpServletRequest,
			CTCollectionTemplate ctCollectionTemplate, Language language)
		throws Exception {

		return _getPermissionsHref(
			httpServletRequest, CTCollectionTemplate.class.getName(),
			ctCollectionTemplate.getName(),
			String.valueOf(ctCollectionTemplate.getCtCollectionTemplateId()),
			language);
	}

	public static void setWindowState(
		PortletURL portletURL, WindowState windowState) {

		try {
			portletURL.setWindowState(windowState);
		}
		catch (WindowStateException windowStateException) {
			ReflectionUtil.throwException(windowStateException);
		}
	}

	private static String _getPermissionsHref(
			HttpServletRequest httpServletRequest, String modelResource,
			String resourceName, String resourcePrimKey, Language language)
		throws Exception {

		return StringBundler.concat(
			"javascript:Liferay.Util.openModal({containerProps: {}, ",
			"iframeBodyCssClass: 'dialog-with-footer', title:'",
			language.get(httpServletRequest, "permissions"), "', url:'",
			PermissionsURLTag.doTag(
				StringPool.BLANK, modelResource, HtmlUtil.escape(resourceName),
				null, String.valueOf(resourcePrimKey),
				LiferayWindowState.POP_UP.toString(), null, httpServletRequest),
			"',});");
	}

	private PublicationsPortletURLUtil() {
	}

}