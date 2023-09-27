/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Gergely Mathe
 */
public class ExportImportContentProcessorException extends PortalException {

	public static final int ARTICLE_NOT_FOUND = 2;

	public static final int DEFAULT = 1;

	public ExportImportContentProcessorException() {
	}

	public ExportImportContentProcessorException(String className) {
		_className = className;
	}

	public ExportImportContentProcessorException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public ExportImportContentProcessorException(Throwable throwable) {
		super(throwable);
	}

	public String getClassName() {
		return _className;
	}

	public String getStagedModelClassName() {
		return _stagedModelClassName;
	}

	public long getStagedModelClassPK() {
		return _stagedModelClassPK;
	}

	public int getType() {
		return _type;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setStagedModelClassName(String stagedModelClassName) {
		_stagedModelClassName = stagedModelClassName;
	}

	public void setStagedModelClassPK(long stagedModelClassPK) {
		_stagedModelClassPK = stagedModelClassPK;
	}

	public void setType(int type) {
		_type = type;
	}

	private String _className;
	private String _stagedModelClassName;
	private long _stagedModelClassPK;
	private int _type = DEFAULT;

}