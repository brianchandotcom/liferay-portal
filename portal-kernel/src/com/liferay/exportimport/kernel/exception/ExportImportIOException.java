/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public ExportImportIOException(String className, Throwable throwable) {
		super(throwable);

		_className = className;
	}

	public ExportImportIOException(Throwable throwable) {
		super(throwable);
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