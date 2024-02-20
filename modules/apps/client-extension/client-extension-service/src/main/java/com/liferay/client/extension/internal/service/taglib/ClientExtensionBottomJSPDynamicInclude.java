/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.service.taglib;

import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = DynamicInclude.class)
public class ClientExtensionBottomJSPDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		_clientExtensionJSInclude.include(
			httpServletRequest, httpServletResponse, "bottom");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	@Activate
	protected void activate() {
		_clientExtensionJSInclude = new ClientExtensionJSInclude(
			_cetManager, _jsonFactory);
	}

	@Reference
	private CETManager _cetManager;

	private ClientExtensionJSInclude _clientExtensionJSInclude;

	@Reference
	private JSONFactory _jsonFactory;

}