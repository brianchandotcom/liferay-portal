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

package com.liferay.change.tracking.rest.internal.model.process;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Máté Thurzó
 */
public class CTProcessUserModel {

	public static final CTProcessUserModel EMPTY_CT_PROCESS_USER_MODEL =
		new CTProcessUserModel();

	public static Builder forUserId(long userId) {
		return new Builder(userId);
	}

	@XmlElement
	public long getUserId() {
		return _userId;
	}

	@XmlElement
	public String getUserName() {
		return _userName;
	}

	public static class Builder {

		public Builder(long userId) {
			_ctProcessUserModel = new CTProcessUserModel();

			_ctProcessUserModel._userId = userId;
		}

		public CTProcessUserModel build() {
			return _ctProcessUserModel;
		}

		public Builder setUserName(String userName) {
			_ctProcessUserModel._userName = userName;

			return this;
		}

		private final CTProcessUserModel _ctProcessUserModel;

	}

	private CTProcessUserModel() {
	}

	private long _userId;
	private String _userName;

}