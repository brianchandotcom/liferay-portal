/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public final class DDMDataProviderRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public String getDDMDataProviderId() {
		return _ddmDataProviderId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public Locale getLocale() {
		return _locale;
	}

	public <T> T getParameter(String name, Class<?> clazz) {
		Object value = _parameters.get(name);

		if (value == null) {
			return null;
		}

		Class<?> valueClass = value.getClass();

		if (clazz.isAssignableFrom(valueClass)) {
			return (T)value;
		}

		return null;
	}

	public Map<String, Object> getParameters() {
		return Collections.unmodifiableMap(_parameters);
	}

	public static class Builder {

		public static Builder newBuilder() {
			return new Builder();
		}

		public DDMDataProviderRequest build() {
			return _ddmDataProviderRequest;
		}

		public Builder withCompanyId(long companyId) {
			_ddmDataProviderRequest._companyId = companyId;

			return this;
		}

		public Builder withDDMDataProviderId(String ddmDataProviderId) {
			_ddmDataProviderRequest._ddmDataProviderId = ddmDataProviderId;

			return this;
		}

		public Builder withGroupId(long groupId) {
			_ddmDataProviderRequest._groupId = groupId;

			return this;
		}

		public Builder withLocale(Locale locale) {
			_ddmDataProviderRequest._locale = locale;

			return this;
		}

		public Builder withParameter(String name, Object value) {
			_ddmDataProviderRequest._parameters.put(name, value);

			return this;
		}

		private Builder() {
		}

		private final DDMDataProviderRequest _ddmDataProviderRequest =
			new DDMDataProviderRequest();

	}

	private DDMDataProviderRequest() {
	}

	private long _companyId;
	private String _ddmDataProviderId;
	private long _groupId;
	private Locale _locale;
	private final Map<String, Object> _parameters = new HashMap<>();

}