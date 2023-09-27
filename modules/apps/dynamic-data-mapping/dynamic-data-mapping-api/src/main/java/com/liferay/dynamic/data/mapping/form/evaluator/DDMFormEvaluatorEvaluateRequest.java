/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;

import java.util.Locale;

/**
 * @author Leonardo Barros
 */
public final class DDMFormEvaluatorEvaluateRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public long getDDMFormInstanceId() {
		return _ddmFormInstanceId;
	}

	public DDMFormLayout getDDMFormLayout() {
		return _ddmFormLayout;
	}

	public DDMFormValues getDDMFormValues() {
		return _ddmFormValues;
	}

	public String getGooglePlacesAPIKey() {
		return _googlePlacesAPIKey;
	}

	public long getGroupId() {
		return _groupId;
	}

	public Locale getLocale() {
		return _locale;
	}

	public JSONArray getObjectFieldsJSONArray() {
		return _objectFieldsJSONArray;
	}

	public String getTimeZoneId() {
		return _timeZoneId;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isEditingFieldValue() {
		return _editingFieldValue;
	}

	public boolean isViewMode() {
		return _viewMode;
	}

	public static class Builder {

		public static Builder newBuilder(
			DDMForm ddmForm, DDMFormValues ddmFormValues, Locale locale) {

			return new Builder(ddmForm, ddmFormValues, locale);
		}

		public DDMFormEvaluatorEvaluateRequest build() {
			return _ddmFormEvaluatorEvaluateRequest;
		}

		public Builder withCompanyId(long companyId) {
			_ddmFormEvaluatorEvaluateRequest._companyId = companyId;

			return this;
		}

		public Builder withDDMFormInstanceId(long ddmFormInstanceId) {
			_ddmFormEvaluatorEvaluateRequest._ddmFormInstanceId =
				ddmFormInstanceId;

			return this;
		}

		public Builder withDDMFormLayout(DDMFormLayout ddmFormLayout) {
			_ddmFormEvaluatorEvaluateRequest._ddmFormLayout = ddmFormLayout;

			return this;
		}

		public Builder withEditingFieldValue(boolean editingFieldValue) {
			_ddmFormEvaluatorEvaluateRequest._editingFieldValue =
				editingFieldValue;

			return this;
		}

		public Builder withGooglePlacesAPIKey(String googlePlacesAPIKey) {
			_ddmFormEvaluatorEvaluateRequest._googlePlacesAPIKey =
				googlePlacesAPIKey;

			return this;
		}

		public Builder withGroupId(long groupId) {
			_ddmFormEvaluatorEvaluateRequest._groupId = groupId;

			return this;
		}

		public Builder withObjectFieldsJSONArray(
			JSONArray objectFieldsJSONArray) {

			_ddmFormEvaluatorEvaluateRequest._objectFieldsJSONArray =
				objectFieldsJSONArray;

			return this;
		}

		public Builder withTimeZoneId(String timeZoneId) {
			_ddmFormEvaluatorEvaluateRequest._timeZoneId = timeZoneId;

			return this;
		}

		public Builder withUserId(long userId) {
			_ddmFormEvaluatorEvaluateRequest._userId = userId;

			return this;
		}

		public Builder withViewMode(boolean viewMode) {
			_ddmFormEvaluatorEvaluateRequest._viewMode = viewMode;

			return this;
		}

		private Builder(
			DDMForm ddmForm, DDMFormValues ddmFormValues, Locale locale) {

			_ddmFormEvaluatorEvaluateRequest._ddmForm = ddmForm;
			_ddmFormEvaluatorEvaluateRequest._ddmFormValues = ddmFormValues;
			_ddmFormEvaluatorEvaluateRequest._locale = locale;
		}

		private final DDMFormEvaluatorEvaluateRequest
			_ddmFormEvaluatorEvaluateRequest =
				new DDMFormEvaluatorEvaluateRequest();

	}

	private DDMFormEvaluatorEvaluateRequest() {
	}

	private long _companyId;
	private DDMForm _ddmForm;
	private long _ddmFormInstanceId;
	private DDMFormLayout _ddmFormLayout;
	private DDMFormValues _ddmFormValues;
	private boolean _editingFieldValue;
	private String _googlePlacesAPIKey;
	private long _groupId;
	private Locale _locale;
	private JSONArray _objectFieldsJSONArray;
	private String _timeZoneId;
	private long _userId;
	private boolean _viewMode;

}