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

package com.liferay.exportimport.kernel.lar;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;

/**
 * @author Raymond Augé
 */
@ProviderType
public class PortletDataException extends PortalException {

	public static final int COMPANY_BEING_DELETED = 14;

	public static final int DEFAULT = 1;

	public static final int DELETE_PORTLET_DATA = 10;

	public static final int END_DATE_IS_MISSING_START_DATE = 1;

	public static final int EXPORT_DATA_GROUP_ELEMENT = 15;

	public static final int EXPORT_PORTLET_DATA = 11;

	public static final int FUTURE_END_DATE = 2;

	public static final int FUTURE_START_DATE = 3;

	public static final int IMPORT_DATA_GROUP_ELEMENT = 16;

	public static final int IMPORT_PORTLET_DATA = 12;

	public static final int INVALID_GROUP = 4;

	public static final int MISSING_DEPENDENCY = 5;

	public static final int PREPARE_MANIFEST_SUMMARY = 13;

	public static final int START_DATE_AFTER_END_DATE = 6;

	public static final int START_DATE_IS_MISSING_END_DATE = 7;

	public static final int STATUS_IN_TRASH = 8;

	public static final int STATUS_UNAVAILABLE = 9;

	public PortletDataException() {
	}

	public PortletDataException(int type) {
		_type = type;
	}

	public PortletDataException(int type, Throwable cause) {
		super(cause);

		_type = type;
	}

	public PortletDataException(String msg) {
		super(msg);
	}

	public PortletDataException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PortletDataException(Throwable cause) {
		super(cause);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getPortletId() {
		return _portletId;
	}

	public StagedModel getStagedModel() {
		return _stagedModel;
	}

	public String getStagedModelClassName() {
		return _stagedModelClassName;
	}

	public String getStagedModelClassPK() {
		return _stagedModelClassPK;
	}

	public String getStagedModelDisplayName() {
		return _stagedModelDisplayName;
	}

	public int getType() {
		return _type;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setStagedModel(StagedModel stagedModel) {
		_stagedModel = stagedModel;
	}

	public void setStagedModelClassName(String stagedModelClassName) {
		_stagedModelClassName = stagedModelClassName;
	}

	public void setStagedModelClassPK(String stagedModelClassPK) {
		_stagedModelClassPK = stagedModelClassPK;
	}

	public void setStagedModelDisplayName(String stagedModelDisplayName) {
		_stagedModelDisplayName = stagedModelDisplayName;
	}

	public void setType(int type) {
		_type = type;
	}

	private long _companyId;
	private String _portletId = StringPool.BLANK;
	private StagedModel _stagedModel;
	private String _stagedModelClassName = StringPool.BLANK;
	private String _stagedModelClassPK = StringPool.BLANK;
	private String _stagedModelDisplayName = StringPool.BLANK;
	private int _type = DEFAULT;

}