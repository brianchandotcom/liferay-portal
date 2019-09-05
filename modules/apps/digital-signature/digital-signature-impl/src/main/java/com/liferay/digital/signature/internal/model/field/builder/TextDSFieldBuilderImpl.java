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

package com.liferay.digital.signature.internal.model.field.builder;

import com.liferay.digital.signature.internal.model.field.TextDSFieldImpl;
import com.liferay.digital.signature.model.field.DSField;
import com.liferay.digital.signature.model.field.TextDSField;
import com.liferay.digital.signature.model.field.builder.TextDSFieldBuilder;

/**
 * @author Michael C. Han
 */
public class TextDSFieldBuilderImpl
	extends UserEntryDSFieldBuilderImpl<TextDSField>
	implements TextDSFieldBuilder {

	public TextDSFieldBuilderImpl(
		String documentKey, String fieldKey, Integer pageText) {

		super(documentKey, fieldKey, pageText);
	}

	@Override
	public DSField<TextDSField> getDSField() {
		TextDSFieldImpl textDSFieldImpl = new TextDSFieldImpl(
			getDocumentKey(), getFieldKey(), getPageNumber()) {

			{
				setFormula(_formula);
				setSenderRequired(_senderRequired);
			}
		};

		populateFields(textDSFieldImpl);

		return textDSFieldImpl;
	}

	@Override
	public TextDSFieldBuilder setFormula(String formula) {
		_formula = formula;

		return this;
	}

	@Override
	public TextDSFieldBuilder setSenderRequired(Boolean senderRequired) {
		_senderRequired = senderRequired;

		return this;
	}

	private String _formula;
	private Boolean _senderRequired;

}