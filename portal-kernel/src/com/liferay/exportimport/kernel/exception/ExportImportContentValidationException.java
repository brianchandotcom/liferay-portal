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

import java.io.Serializable;

import java.util.Map;

/**
 * @author Gergely Mathe
 */
public class ExportImportContentValidationException extends PortalException {

	public static final int ARTICLE_NOT_FOUND = 6;

	public static final int DEFAULT = 1;

	public static final int FILE_ENTRY_NOT_FOUND = 5;

	public static final int LAYOUT_GROUP_NOT_FOUND = 2;

	public static final int LAYOUT_NOT_FOUND = 3;

	public static final int LAYOUT_WITH_URL_NOT_FOUND = 4;

	public ExportImportContentValidationException() {
	}

	public ExportImportContentValidationException(String className) {
		_className = className;
	}

	public ExportImportContentValidationException(
		String className, Throwable cause) {

		super(cause);

		_className = className;
	}

	public String getClassName() {
		return _className;
	}

	public Map<String, String[]> getDlReferenceParameters() {
		return _dlReferenceParameters;
	}

	public String getGroupFriendlyURL() {
		return _groupFriendlyURL;
	}

	public Map<String, String> getLayoutReferenceParameters() {
		return _layoutReferenceParameters;
	}

	public String getLayoutURL() {
		return _layoutURL;
	}

	public String getStagedModelClassName() {
		return _stagedModelClassName;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #getStagedModelPrimaryKeyObj()}
	 */
	@Deprecated
	public long getStagedModelClassPK() {
		return _stagedModelClassPK;
	}

	public Serializable getStagedModelPrimaryKeyObj() {
		return _stagedModelPrimaryKeyObj;
	}

	public int getType() {
		return _type;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setDlReferenceParameters(
		Map<String, String[]> dlReferenceParameters) {

		_dlReferenceParameters = dlReferenceParameters;
	}

	public void setGroupFriendlyURL(String groupFriendlyURL) {
		_groupFriendlyURL = groupFriendlyURL;
	}

	public void setLayoutReferenceParameters(
		Map<String, String> layoutReferenceParameters) {

		_layoutReferenceParameters = layoutReferenceParameters;
	}

	public void setLayoutURL(String layoutURL) {
		_layoutURL = layoutURL;
	}

	public void setStagedModelClassName(String stagedModelClassName) {
		_stagedModelClassName = stagedModelClassName;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #setStagedModelPrimaryKeyObj(Serializable)}
	 */
	@Deprecated
	public void setStagedModelClassPK(long stagedModelClassPK) {
		_stagedModelClassPK = stagedModelClassPK;
	}

	public void setStagedModelPrimaryKeyObj(
		Serializable stagedModelPrimaryKeyObj) {

		_stagedModelPrimaryKeyObj = stagedModelPrimaryKeyObj;
	}

	public void setType(int type) {
		_type = type;
	}

	private String _className;
	private Map<String, String[]> _dlReferenceParameters;
	private String _groupFriendlyURL;
	private Map<String, String> _layoutReferenceParameters;
	private String _layoutURL;
	private String _stagedModelClassName;
	private long _stagedModelClassPK;
	private Serializable _stagedModelPrimaryKeyObj;
	private int _type = DEFAULT;

}