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

	public ExportImportDocumentException(Throwable cause) {
		super(cause);
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