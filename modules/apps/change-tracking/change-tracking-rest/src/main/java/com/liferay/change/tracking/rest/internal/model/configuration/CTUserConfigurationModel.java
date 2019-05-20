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

package com.liferay.change.tracking.rest.internal.model.configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Gergely Mathe
 */
@XmlRootElement
public class CTUserConfigurationModel {

	public static CTUserConfigurationModel.Builder forCompanyAndUser(
		long companyId, long userId) {

		return new Builder(companyId, userId);
	}

	@XmlElement
	public long getCompanyId() {
		return _companyId;
	}

	@XmlElement
	public long getUserId() {
		return _userId;
	}

	@XmlElement
	public boolean isCheckoutCTCollectionConfirmationEnabled() {
		return _checkoutCTCollectionConfirmationEnabled;
	}

	public static class Builder {

		public CTUserConfigurationModel build() {
			return _ctUserConfigurationModel;
		}

		public Builder setCheckoutCTCollectionConfirmationEnabled(
			boolean checkoutCTCollectionConfirmationEnabled) {

			_ctUserConfigurationModel._checkoutCTCollectionConfirmationEnabled =
				checkoutCTCollectionConfirmationEnabled;

			return this;
		}

		private Builder(long companyId, long userId) {
			_ctUserConfigurationModel = new CTUserConfigurationModel();

			_ctUserConfigurationModel._companyId = companyId;
			_ctUserConfigurationModel._userId = userId;
		}

		private final CTUserConfigurationModel _ctUserConfigurationModel;

	}

	private CTUserConfigurationModel() {
	}

	private boolean _checkoutCTCollectionConfirmationEnabled;
	private long _companyId;
	private long _userId;

}