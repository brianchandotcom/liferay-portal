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

package com.liferay.remote.app.admin.web.internal.portlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.remote.app.model.RemoteAppEntry;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteAppEntryPortlet extends MVCPortlet {

	public RemoteAppEntryPortlet(
		RemoteAppEntry remoteAppEntry, String remoteProtocolBridgeModuleName) {

		_remoteProtocolBridgeModuleName = remoteProtocolBridgeModuleName;

		_url = remoteAppEntry.getUrl();
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		ScriptData scriptData = new ScriptData();

		scriptData.append(
			null, "RemoteProtocolBridge.default()",
			_remoteProtocolBridgeModuleName + " as RemoteProtocolBridge",
			ScriptData.ModulesType.ES6);

		StringWriter stringWriter = new StringWriter();

		scriptData.writeTo(stringWriter);

		StringBuffer stringBuffer = stringWriter.getBuffer();

		OutputData outputData = _getOutputData(renderRequest);

		outputData.setDataSB(
			RemoteAppEntryPortlet.class.toString(), WebKeys.PAGE_TOP,
			new StringBundler(stringBuffer.toString()));

		PrintWriter printWriter = renderResponse.getWriter();

		printWriter.print("<iframe src=\"");
		printWriter.print(_url);
		printWriter.print("\"></iframe>");

		printWriter.flush();
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

	private final String _remoteProtocolBridgeModuleName;
	private final String _url;

}