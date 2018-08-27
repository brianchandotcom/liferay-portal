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

package com.liferay.forms.apio.internal.architect.representable;

import static com.liferay.forms.apio.internal.util.LocalizedValueUtil.getLocalizedString;

import com.liferay.apio.architect.representor.NestedRepresentor;
import com.liferay.apio.architect.representor.NestedRepresentor.Builder;
import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.forms.apio.architect.identifier.FormContextIdentifier;
import com.liferay.forms.apio.internal.model.FormContextWrapper;
import com.liferay.forms.apio.internal.model.FormFieldContextWrapper;
import com.liferay.forms.apio.internal.model.FormPageContextWrapper;
import com.liferay.portal.kernel.util.KeyValuePair;

import org.osgi.service.component.annotations.Component;

/**
 * @author Paulo Cruz
 */
@Component(immediate = true)
public class FormContextRepresentable
	implements Representable
		<FormContextWrapper, String, FormContextIdentifier> {

	@Override
	public String getName() {
		return "form-contexts";
	}

	@Override
	public Representor<FormContextWrapper> representor(
		Representor.Builder<FormContextWrapper, String> builder) {

		return builder.types(
			"FormContext"
		).identifier(
			FormContextWrapper::getIdentifier
		).addBoolean(
			"isReadOnly", FormContextWrapper::isReadOnly
		).addBoolean(
			"isShowRequiredFieldsWarning",
			FormContextWrapper::isShowRequiredFieldsWarning
		).addBoolean(
			"isShowSubmitButton", FormContextWrapper::isShowSubmitButton
		).addNestedList(
			"pages", FormContextWrapper::getPageContexts,
			this::_buildFormContextPages
		).build();
	}

	private NestedRepresentor<FormFieldContextWrapper> _buildFormContextFields(
		Builder<FormFieldContextWrapper> builder) {

		return builder.types(
			"FormFieldContext"
		).addBoolean(
			"isEvaluable", FormFieldContextWrapper::isEvaluable
		).addBoolean(
			"isReadOnly", FormFieldContextWrapper::isReadOnly
		).addBoolean(
			"isRequired", FormFieldContextWrapper::isRequired
		).addBoolean(
			"isValid", FormFieldContextWrapper::isValid
		).addBoolean(
			"isValueChanged", FormFieldContextWrapper::isValueChanged
		).addBoolean(
			"isVisible", FormFieldContextWrapper::isVisible
		).addNestedList(
			"options", FormFieldContextWrapper::getOptions,
			optionsBuilder -> optionsBuilder.types(
				"FormFieldOptions"
			).addString(
				"label", KeyValuePair::getValue
			).addString(
				"value", KeyValuePair::getKey
			).build()
		).addString(
			"errorMessage", FormFieldContextWrapper::getErrorMessage
		).addString(
			"name", FormFieldContextWrapper::getName
		).addString(
			"value", FormFieldContextWrapper::getValue
		).build();
	}

	private NestedRepresentor<FormPageContextWrapper> _buildFormContextPages(
		Builder<FormPageContextWrapper> builder) {

		return builder.types(
			"FormPageContext"
		).addBoolean(
			"isEnabled", FormPageContextWrapper::isEnabled
		).addBoolean(
			"isShowRequiredFieldsWarning",
			FormPageContextWrapper::isShowRequiredFieldsWarning
		).addLocalizedStringByLocale(
			"headline", getLocalizedString(FormPageContextWrapper::getTitle)
		).addLocalizedStringByLocale(
			"text", getLocalizedString(FormPageContextWrapper::getDescription)
		).addNestedList(
			"fields", FormPageContextWrapper::getFormFieldContexts,
			this::_buildFormContextFields
		).build();
	}

}