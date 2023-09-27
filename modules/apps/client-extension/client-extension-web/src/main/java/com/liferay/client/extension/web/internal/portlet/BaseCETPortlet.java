/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.portlet;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.web.internal.type.deployer.Registrable;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Properties;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

/**
 * @author Iván Zaera Avellón
 */
public abstract class BaseCETPortlet<T extends CET>
	extends MVCPortlet implements Registrable {

	public BaseCETPortlet(T cet, NPMResolver npmResolver) {
		this.cet = cet;
		this.npmResolver = npmResolver;
	}

	public T getCET() {
		return cet;
	}

	protected OutputData getOutputData(RenderRequest renderRequest) {
		OutputData outputData = (OutputData)renderRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		if (outputData == null) {
			outputData = new OutputData();

			renderRequest.setAttribute(WebKeys.OUTPUT_DATA, outputData);
		}

		return outputData;
	}

	protected Properties getProperties(RenderRequest renderRequest)
		throws IOException {

		Properties properties = cet.getProperties();

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		PropertiesUtil.load(
			properties,
			portletPreferences.getValue("properties", StringPool.BLANK));

		return properties;
	}

	protected final T cet;
	protected final NPMResolver npmResolver;

}