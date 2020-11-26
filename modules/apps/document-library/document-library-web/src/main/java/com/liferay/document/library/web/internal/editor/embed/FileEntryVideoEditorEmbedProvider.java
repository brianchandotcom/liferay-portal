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

package com.liferay.document.library.web.internal.editor.embed;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.frontend.editor.embed.EditorEmbedProvider;
import com.liferay.frontend.editor.embed.constants.EditorEmbedProviderTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import java.util.regex.Pattern;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "type=" + EditorEmbedProviderTypeConstants.VIDEO,
	service = EditorEmbedProvider.class
)
public class FileEntryVideoEditorEmbedProvider implements EditorEmbedProvider {

	@Override
	public String getId() {
		return "liferay";
	}

	@Override
	public String getTpl() {
		return StringBundler.concat(
			"<iframe height=\"315\" frameborder=\"0\" src=\"",
			_getEmbedVideoURL(), "&",
			_portal.getPortletNamespace(DLPortletKeys.DOCUMENT_LIBRARY),
			"url={embedId}\" width=\"560\"></iframe>");
	}

	@Override
	public String[] getURLSchemes() {
		return new String[] {_urlPattern.pattern()};
	}

	private String _getEmbedVideoURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_getHttpServletRequest());

		PortletURL getEmbedVideoURL =
			requestBackedPortletURLFactory.createRenderURL(
				DLPortletKeys.DOCUMENT_LIBRARY);

		try {
			getEmbedVideoURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
		}

		getEmbedVideoURL.setParameter(
			"mvcRenderCommandName", "/document_library/embed_video");

		return getEmbedVideoURL.toString();
	}

	private HttpServletRequest _getHttpServletRequest() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getRequest();
		}

		return null;
	}

	private static final Pattern _urlPattern = Pattern.compile(
		"(.*\\/documents\\/.*)");

	@Reference
	private Portal _portal;

}