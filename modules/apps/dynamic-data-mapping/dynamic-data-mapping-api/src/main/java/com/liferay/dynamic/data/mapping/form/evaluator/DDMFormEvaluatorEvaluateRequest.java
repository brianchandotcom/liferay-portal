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

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

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

	public long getGroupId() {
		return _groupId;
	}

	public Locale getLocale() {
		return _locale;
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

		public Builder withGroupId(long groupId) {
			_ddmFormEvaluatorEvaluateRequest._groupId = groupId;

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
	private long _groupId;
	private Locale _locale;
	private long _userId;
	private boolean _viewMode;

}