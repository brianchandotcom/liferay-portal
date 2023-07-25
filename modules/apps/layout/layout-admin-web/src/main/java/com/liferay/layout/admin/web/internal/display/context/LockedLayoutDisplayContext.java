/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.RenderRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class LockedLayoutDisplayContext {

	public LockedLayoutDisplayContext(
		Portal portal, RenderRequest renderRequest) {

		_portal = portal;
		_renderRequest = renderRequest;
	}

	public String getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		_backURL = ParamUtil.getString(_renderRequest, "backURL");

		return _backURL;
	}

	public String getImagesPath() {
		return _portal.getPathContext(_renderRequest) + "/images";
	}

	public boolean isShowGoBackButton() {
		if (Validator.isNotNull(getBackURL())) {
			return true;
		}

		return false;
	}

	private String _backURL;
	private final Portal _portal;
	private final RenderRequest _renderRequest;

}