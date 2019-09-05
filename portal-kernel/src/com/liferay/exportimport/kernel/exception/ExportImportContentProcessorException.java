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
public class ExportImportContentProcessorException extends PortalException {

	public static final int ARTICLE_NOT_FOUND = 2;

	public static final int DEFAULT = 1;

	public ExportImportContentProcessorException() {
	}

	public ExportImportContentProcessorException(String className) {
		_className = className;
	}

	public ExportImportContentProcessorException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ExportImportContentProcessorException(Throwable cause) {
		super(cause);
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