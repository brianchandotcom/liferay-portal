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

package com.liferay.message.boards.internal.messaging;

import java.io.Serializable;

/**
 * @author Thiago Moreira
 */
public class MailingListRequest implements Serializable {

	public long getCategoryId() {
		return _categoryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getInPassword() {
		return _inPassword;
	}

	public String getInProtocol() {
		return _inProtocol;
	}

	public String getInServerName() {
		return _inServerName;
	}

	public int getInServerPort() {
		return _inServerPort;
	}

	public String getInUserName() {
		return _inUserName;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isAllowAnonymous() {
		return _allowAnonymous;
	}

	public boolean isInUseSSL() {
		return _inUseSSL;
	}

	public void setAllowAnonymous(boolean allowAnonymous) {
		_allowAnonymous = allowAnonymous;
	}

	public void setCategoryId(long id) {
		_categoryId = id;
	}

	public void setCompanyId(long id) {
		_companyId = id;
	}

	public void setGroupId(long id) {
		_groupId = id;
	}

	public void setInPassword(String inPassword) {
		_inPassword = inPassword;
	}

	public void setInProtocol(String inProtocol) {
		_inProtocol = inProtocol;
	}

	public void setInServerName(String inServerName) {
		_inServerName = inServerName;
	}

	public void setInServerPort(int inServerPort) {
		_inServerPort = inServerPort;
	}

	public void setInUserName(String inUserName) {
		_inUserName = inUserName;
	}

	public void setInUseSSL(boolean inUseSSL) {
		_inUseSSL = inUseSSL;
	}

	public void setUserId(long id) {
		_userId = id;
	}

	private static final long serialVersionUID = 8983934222717334170L;

	private boolean _allowAnonymous;
	private long _categoryId;
	private long _companyId;
	private long _groupId;
	private String _inPassword;
	private String _inProtocol;
	private String _inServerName;
	private int _inServerPort;
	private String _inUserName;
	private boolean _inUseSSL;
	private long _userId;

}