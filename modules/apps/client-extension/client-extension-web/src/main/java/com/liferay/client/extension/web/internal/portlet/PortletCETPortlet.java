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

package com.liferay.client.extension.web.internal.portlet;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.CustomElementCET;
import com.liferay.client.extension.type.IFrameCET;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Iván Zaera Avellón
 */
public class PortletCETPortlet extends MVCPortlet {

	public PortletCETPortlet(
		CET cet, CustomElementCET customElementCET, IFrameCET iFrameCET,
		NPMResolver npmResolver) {

		_cet = cet;
		_customElementCET = customElementCET;
		_iFrameCET = iFrameCET;
		_npmResolver = npmResolver;
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		if (_customElementCET != null) {
			_renderCustomElement(renderRequest, renderResponse);
		}
		else if (_iFrameCET != null) {
			_renderIFrame(renderRequest, renderResponse);
		}
		else {
			throw new IOException(
				"Invalid remote app entry type: " + _cet.getType());
		}
	}

	private OutputData _getOutputData(RenderRequest renderRequest) {
		OutputData outputData = (OutputData)renderRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		if (outputData == null) {
			outputData = new OutputData();

			renderRequest.setAttribute(WebKeys.OUTPUT_DATA, outputData);
		}

		return outputData;
	}

	private Properties _getProperties(RenderRequest renderRequest)
		throws IOException {

		Properties properties = _cet.getProperties();

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		PropertiesUtil.load(
			properties,
			portletPreferences.getValue("properties", StringPool.BLANK));

		return properties;
	}

	private void _renderCustomElement(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		PrintWriter printWriter = renderResponse.getWriter();

		printWriter.print(StringPool.LESS_THAN);
		printWriter.print(_customElementCET.getHTMLElementName());

		Properties properties = _getProperties(renderRequest);

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			Group group = GroupLocalServiceUtil.getGroup(
				themeDisplay.getScopeGroupId());

			StringBundler webDavURLSB = new StringBundler(4);

			webDavURLSB.append(themeDisplay.getPortalURL());
			webDavURLSB.append("/webdav");
			webDavURLSB.append(group.getFriendlyURL());
			webDavURLSB.append("/document_library");

			properties.put("liferaywebdavurl", webDavURLSB.toString());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			printWriter.print(StringPool.SPACE);
			printWriter.print(entry.getKey());
			printWriter.print("=\"");
			printWriter.print(
				StringUtil.replace(
					(String)entry.getValue(), CharPool.QUOTE, "&quot;"));
			printWriter.print(StringPool.QUOTE);
		}

		printWriter.print("></");
		printWriter.print(_customElementCET.getHTMLElementName());
		printWriter.print(StringPool.GREATER_THAN);

		printWriter.flush();
	}

	private void _renderIFrame(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		OutputData outputData = _getOutputData(renderRequest);

		ScriptData scriptData = new ScriptData();

		String moduleName = _npmResolver.resolveModuleName(
			"@liferay/client-extension-web/remote_protocol/bridge");

		scriptData.append(
			null, "RemoteProtocolBridge.default()",
			moduleName + " as RemoteProtocolBridge",
			ScriptData.ModulesType.ES6);

		StringWriter stringWriter = new StringWriter();

		scriptData.writeTo(stringWriter);

		StringBuffer stringBuffer = stringWriter.getBuffer();

		outputData.setDataSB(
			PortletCETPortlet.class.toString(), WebKeys.PAGE_TOP,
			new StringBundler(stringBuffer.toString()));

		PrintWriter printWriter = renderResponse.getWriter();

		printWriter.print("<iframe src=\"");

		String iFrameURL = _iFrameCET.getURL();

		Properties properties = _getProperties(renderRequest);

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			iFrameURL = HttpComponentsUtil.addParameter(
				iFrameURL, (String)entry.getKey(), (String)entry.getValue());
		}

		printWriter.print(iFrameURL);

		printWriter.print("\"></iframe>");

		printWriter.flush();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletCETPortlet.class);

	private final CET _cet;
	private final CustomElementCET _customElementCET;
	private final IFrameCET _iFrameCET;
	private final NPMResolver _npmResolver;

}