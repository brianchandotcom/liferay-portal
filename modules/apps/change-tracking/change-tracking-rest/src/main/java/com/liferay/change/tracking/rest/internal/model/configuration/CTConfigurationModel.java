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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author Máté Thurzó
 */
@JsonRootName("configuration")
public class CTConfigurationModel {

	public static CTConfigurationModel.Builder forCompany(long companyId) {
		return new Builder(companyId);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public boolean isChangeTrackingEnabled() {
		return _changeTrackingEnabled;
	}

	public static class Builder {

		public CTConfigurationModel build() {
			return _ctConfigurationModel;
		}

		public Builder setChangeTrackingEnabled(boolean changeTrackingEnabled) {
			_ctConfigurationModel._changeTrackingEnabled =
				changeTrackingEnabled;

			return this;
		}

		private Builder(long companyId) {
			_ctConfigurationModel = new CTConfigurationModel();

			_ctConfigurationModel._companyId = companyId;
		}

		private final CTConfigurationModel _ctConfigurationModel;

	}

	private CTConfigurationModel() {
	}

	@JsonProperty("changeTrackingEnabled")
	private boolean _changeTrackingEnabled;

	@JsonProperty("companyId")
	private long _companyId;

}