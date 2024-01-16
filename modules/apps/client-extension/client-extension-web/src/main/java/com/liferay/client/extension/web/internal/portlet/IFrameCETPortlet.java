/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.portlet;

import com.liferay.client.extension.type.IFrameCET;
import com.liferay.frontend.js.loader.modules.extender.esm.ESImportUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.servlet.taglib.aui.JSFragment;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Map;
import java.util.Properties;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Iván Zaera Avellón
 */
public class IFrameCETPortlet extends BaseCETPortlet<IFrameCET> {

	public IFrameCETPortlet(
		IFrameCET iFrameCET,
		AbsolutePortalURLBuilderFactory absolutePortalURLBuilderFactory,
		String portletId, Portal portal) {

		super(iFrameCET);

		_absolutePortalURLBuilderFactory = absolutePortalURLBuilderFactory;
		_portletId = portletId;
		_portal = portal;
	}

	@Override
	public Dictionary<String, Object> getDictionary() {
		return HashMapDictionaryBuilder.<String, Object>put(
			"com.liferay.portlet.company", cet.getCompanyId()
		).put(
			"com.liferay.portlet.css-class-wrapper", "portlet-client-extension"
		).put(
			"com.liferay.portlet.display-category", cet.getPortletCategoryName()
		).put(
			"com.liferay.portlet.header-portlet-css", "/display/css/main.css"
		).put(
			"com.liferay.portlet.instanceable", cet.isInstanceable()
		).put(
			"javax.portlet.display-name", cet.getName(LocaleUtil.US)
		).put(
			"javax.portlet.name", _portletId
		).put(
			"javax.portlet.security-role-ref", "power-user,user"
		).put(
			"javax.portlet.version", "3.0"
		).build();
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		OutputData outputData = getOutputData(renderRequest);

		ScriptData scriptData = new ScriptData();

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				_portal.getHttpServletRequest(renderRequest));

		scriptData.append(
			null,
			new JSFragment(
				"remoteProtocol();",
				Arrays.asList(
					ESImportUtil.getESImport(
						absolutePortalURLBuilder,
						"{remoteProtocol} from client-extension-web"))));

		StringWriter stringWriter = new StringWriter();

		scriptData.writeTo(stringWriter);

		StringBuffer stringBuffer = stringWriter.getBuffer();

		outputData.setDataSB(
			IFrameCETPortlet.class.toString(), WebKeys.PAGE_TOP,
			new StringBundler(stringBuffer.toString()));

		PrintWriter printWriter = renderResponse.getWriter();

		printWriter.print("<iframe src=\"");

		String iFrameURL = cet.getURL();

		Properties properties = getProperties(renderRequest);

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			iFrameURL = HttpComponentsUtil.addParameter(
				iFrameURL, (String)entry.getKey(), (String)entry.getValue());
		}

		printWriter.print(iFrameURL);

		printWriter.print("\"></iframe>");

		printWriter.flush();
	}

	private final AbsolutePortalURLBuilderFactory
		_absolutePortalURLBuilderFactory;
	private final Portal _portal;
	private final String _portletId;

}