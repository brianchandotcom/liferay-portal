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
public abstract class CETPortlet<T extends CET>
	extends MVCPortlet implements Registrable {

	public CETPortlet(T cet, NPMResolver npmResolver) {
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