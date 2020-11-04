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

package com.liferay.change.tracking.web.internal.util;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class PublicationsPortletURLUtil {

	public static String getDeleteHref(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse,
		String backURL, long ctCollectionId, Language language) {

		return StringBundler.concat(
			"javascript:if(confirm('",
			language.get(
				httpServletRequest,
				"are-you-sure-you-want-to-delete-this-publication"),
			"')){ submitForm(document.hrefFm,'",
			getHref(
				renderResponse.createActionURL(), ActionRequest.ACTION_NAME,
				"/change_tracking/delete_ct_collection", "redirect", backURL,
				"ctCollectionId", String.valueOf(ctCollectionId)),
			"');} else{self.focus();}");
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

		return StringBundler.concat(
			"javascript: Liferay.Util.openWindow({dialog: {destroyOnHide: ",
			"true,},dialogIframe: {bodyCssClass: 'dialog-with-footer'},",
			"title:'", language.get(httpServletRequest, "permissions"),
			"',uri:'",
			PermissionsURLTag.doTag(
				StringPool.BLANK, CTCollection.class.getName(),
				HtmlUtil.escape(ctCollection.getName()), null,
				String.valueOf(ctCollection.getCtCollectionId()),
				LiferayWindowState.POP_UP.toString(), null, httpServletRequest),
			"',});");
	}

	private PublicationsPortletURLUtil() {
	}

}