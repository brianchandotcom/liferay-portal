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

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rodrigo Paulino
 */
public class DDMFormTemplateContext {

	public static class Builder {

		public static Builder newBuilder(
			DDMFormTemplateContextFactory ddmFormTemplateContextFactory) {

			return new Builder(ddmFormTemplateContextFactory);
		}

		public Map<String, Object> build() throws PortalException {
			if (_paginationMode == null) {
				return _ddmFormTemplateContextFactory.create(
					DDMFormTestUtil.createDDMForm(), _ddmFormRenderingContext);
			}

			DDMFormLayout ddmFormLayout = new DDMFormLayout();

			ddmFormLayout.setPaginationMode(_paginationMode);

			return _ddmFormTemplateContextFactory.create(
				DDMFormTestUtil.createDDMForm(), ddmFormLayout,
				_ddmFormRenderingContext);
		}

		public Builder withContainerId(String containerId) {
			_ddmFormRenderingContext.setContainerId(containerId);

			return this;
		}

		public Builder withHttpServletRequest(
			HttpServletRequest httpServletRequest) {

			_ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);

			return this;
		}

		public Builder withLocale(Locale locale) {
			_ddmFormRenderingContext.setLocale(locale);

			return this;
		}

		public Builder withPaginationMode(String paginationMode) {
			_paginationMode = paginationMode;

			return this;
		}

		public Builder withPortletNamespace(String portletNamespace) {
			_ddmFormRenderingContext.setPortletNamespace(portletNamespace);

			return this;
		}

		public Builder withProperty(String name, Object value) {
			_ddmFormRenderingContext.addProperty(name, value);

			return this;
		}

		public Builder withReadOnly(boolean readOnly) {
			_ddmFormRenderingContext.setReadOnly(readOnly);

			return this;
		}

		public Builder withShowRequiredFieldsWarning(
			boolean showRequiredFieldsWarning) {

			_ddmFormRenderingContext.setShowRequiredFieldsWarning(
				showRequiredFieldsWarning);

			return this;
		}

		public Builder withShowSubmitButton(boolean showSubmitButton) {
			_ddmFormRenderingContext.setShowSubmitButton(showSubmitButton);

			return this;
		}

		public Builder withSubmitLabel(String submitLabel) {
			_ddmFormRenderingContext.setSubmitLabel(submitLabel);

			return this;
		}

		public Builder withViewMode(boolean viewMode) {
			_ddmFormRenderingContext.setViewMode(viewMode);

			return this;
		}

		private Builder(
			DDMFormTemplateContextFactory ddmFormTemplateContextFactory) {

			_ddmFormTemplateContextFactory = ddmFormTemplateContextFactory;
		}

		private final DDMFormRenderingContext _ddmFormRenderingContext =
			new DDMFormRenderingContext();
		private final DDMFormTemplateContextFactory
			_ddmFormTemplateContextFactory;
		private String _paginationMode;

	}

}