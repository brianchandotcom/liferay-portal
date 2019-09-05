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
public class ExportImportIOException extends PortalException {

	public static final int ADD_ZIP_ENTRY_BYTES = 2;

	public static final int ADD_ZIP_ENTRY_STREAM = 3;

	public static final int ADD_ZIP_ENTRY_STRING = 4;

	public static final int DEFAULT = 1;

	public static final int LAYOUT_IMPORT = 5;

	public static final int LAYOUT_IMPORT_FILE = 6;

	public static final int LAYOUT_VALIDATE = 7;

	public static final int LAYOUT_VALIDATE_FILE = 8;

	public static final int PORTLET_EXPORT = 9;

	public static final int PORTLET_IMPORT = 10;

	public static final int PORTLET_IMPORT_FILE = 11;

	public static final int PORTLET_VALIDATE = 12;

	public static final int PORTLET_VALIDATE_FILE = 13;

	public static final int PUBLISH_STAGING_REQUEST = 14;

	public static final int STAGING_REQUEST_CHECKSUM = 15;

	public static final int STAGING_REQUEST_REASSEMBLE_FILE = 16;

	public ExportImportIOException() {
	}

	public ExportImportIOException(String className) {
		_className = className;
	}

	public ExportImportIOException(String className, Throwable cause) {
		super(cause);

		_className = className;
	}

	public ExportImportIOException(Throwable cause) {
		super(cause);
	}

	public String getChecksum() {
		return _checksum;
	}

	public String getClassName() {
		return _className;
	}

	public long getExportImportConfigurationId() {
		return _exportImportConfigurationId;
	}

	public String getFileName() {
		return _fileName;
	}

	public String getPortletId() {
		return _portletId;
	}

	public long getStagingRequestId() {
		return _stagingRequestId;
	}

	public int getType() {
		return _type;
	}

	public void setChecksum(String checksum) {
		_checksum = checksum;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setExportImportConfigurationId(
		long exportImportConfigurationId) {

		_exportImportConfigurationId = exportImportConfigurationId;
	}

	public void setFileName(String fileName) {
		_fileName = fileName;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setStagingRequestId(long stagingRequestId) {
		_stagingRequestId = stagingRequestId;
	}

	public void setType(int type) {
		_type = type;
	}

	private String _checksum;
	private String _className;
	private long _exportImportConfigurationId;
	private String _fileName;
	private String _portletId;
	private long _stagingRequestId;
	private int _type = DEFAULT;

}