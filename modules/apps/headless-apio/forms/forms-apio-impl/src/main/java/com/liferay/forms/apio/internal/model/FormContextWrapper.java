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

package com.liferay.forms.apio.internal.model;

import java.util.List;
import java.util.Map;

/**
 * @author Paulo Cruz
 */
public class FormContextWrapper extends BaseFormContextWrapper {

	public FormContextWrapper(Map<String, Object> wrappedMap) {
		super(wrappedMap);
	}

	public String getIdentifier() {
		return getValue("containerId", String.class);
	}

	public List<FormPageContextWrapper> getPageContexts() {
		return getWrappedList("pages", FormPageContextWrapper::new);
	}

	public boolean isReadOnly() {
		return getValue("readOnly", Boolean.class);
	}

	public boolean isShowRequiredFieldsWarning() {
		return getValue("showRequiredFieldsWarning", Boolean.class);
	}

	public boolean isShowSubmitButton() {
		return getValue("showSubmitButton", Boolean.class);
	}

}