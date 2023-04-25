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

package com.liferay.client.extension.web.internal.portlet.action;

import com.liferay.client.extension.web.internal.type.deployer.Registrable;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class CETPortletConfigurationAction
	extends DefaultConfigurationAction implements Registrable {

	public CETPortletConfigurationAction(String jspPath, String portletId) {
		_jspPath = jspPath;
		_portletId = portletId;
	}

	@Override
	public Dictionary<String, Object> getDictionary() {
		return HashMapDictionaryBuilder.<String, Object>put(
			"javax.portlet.name", _portletId
		).build();
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return _jspPath;
	}

	private final String _jspPath;
	private final String _portletId;

}