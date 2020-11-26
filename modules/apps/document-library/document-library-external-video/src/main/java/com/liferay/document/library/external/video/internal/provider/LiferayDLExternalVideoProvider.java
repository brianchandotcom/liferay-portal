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

package com.liferay.document.library.external.video.internal.provider;

import com.liferay.document.library.external.video.DLExternalVideo;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoPortletKeys;
import com.liferay.document.library.external.video.provider.DLExternalVideoProvider;
import com.liferay.frontend.editor.embed.EditorEmbedProvider;
import com.liferay.frontend.editor.embed.constants.EditorEmbedProviderTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
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
	service = {DLExternalVideoProvider.class, EditorEmbedProvider.class}
)
public class LiferayDLExternalVideoProvider
	implements DLExternalVideoProvider, EditorEmbedProvider {

	@Override
	public DLExternalVideo getDLExternalVideo(String url) {
		Matcher matcher = _urlPattern.matcher(url);

		if (!matcher.matches()) {
			return null;
		}

		return new DLExternalVideo() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public String getEmbeddableHTML() {
				return StringUtil.replace(
					getTpl(), "{embedId}", matcher.group(1));
			}

			@Override
			public String getIconURL() {
				return null;
			}

			@Override
			public String getTitle() {
				return null;
			}

			@Override
			public String getURL() {
				return url;
			}

		};
	}

	@Override
	public String getId() {
		return "liferay";
	}

	@Override
	public String getTpl() {
		return StringBundler.concat(
			"<iframe height=\"315\" frameborder=\"0\" src=\"",
			_getDLExternalVideoFieldsURL(), "&",
			_portal.getPortletNamespace(
				DLExternalVideoPortletKeys.DL_EXTERNAL_VIDEO),
			"url={embedId}\" width=\"560\"></iframe>");
	}

	@Override
	public String[] getURLSchemes() {
		return new String[] {_urlPattern.pattern()};
	}

	private String _getDLExternalVideoFieldsURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_getHttpServletRequest());

		PortletURL getDLExternalVideoFieldsURL =
			requestBackedPortletURLFactory.createRenderURL(
				DLExternalVideoPortletKeys.DL_EXTERNAL_VIDEO);

		try {
			getDLExternalVideoFieldsURL.setWindowState(
				LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
		}

		getDLExternalVideoFieldsURL.setParameter(
			"mvcRenderCommandName",
			"/document_library_external_video/embed_video");

		return getDLExternalVideoFieldsURL.toString();
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