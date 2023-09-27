/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Gergely Mathe
 */
public class ExportImportDocumentException extends PortalException {

	public static final int DEFAULT = 1;

	public static final int PORTLET_DATA_IMPORT = 2;

	public static final int PORTLET_PREFERENCES_IMPORT = 3;

	public static final int PORTLET_SERVICE_IMPORT = 4;

	public ExportImportDocumentException() {
	}

	public ExportImportDocumentException(Throwable throwable) {
		super(throwable);
	}

	public String getPortletId() {
		return _portletId;
	}

	public int getType() {
		return _type;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setType(int type) {
		_type = type;
	}

	private String _portletId;
	private int _type;

}