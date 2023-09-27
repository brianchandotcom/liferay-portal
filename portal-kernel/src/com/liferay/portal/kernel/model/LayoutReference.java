/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutReference implements Serializable {

	public LayoutReference() {
	}

	public LayoutReference(LayoutModel layoutModel, String portletId) {
		_layoutModel = layoutModel;
		_portletId = portletId;
	}

	public LayoutModel getLayoutModel() {
		return _layoutModel;
	}

	public String getPortletId() {
		return _portletId;
	}

	public void setLayoutSoap(LayoutModel layoutModel) {
		_layoutModel = layoutModel;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	private LayoutModel _layoutModel;
	private String _portletId;

}