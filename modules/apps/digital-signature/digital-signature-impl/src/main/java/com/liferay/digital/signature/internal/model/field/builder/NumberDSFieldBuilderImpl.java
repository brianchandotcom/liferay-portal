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

import com.liferay.digital.signature.internal.model.field.NumberDSFieldImpl;
import com.liferay.digital.signature.model.field.DSField;
import com.liferay.digital.signature.model.field.NumberDSField;
import com.liferay.digital.signature.model.field.builder.NumberDSFieldBuilder;

/**
 * @author Michael C. Han
 */
public class NumberDSFieldBuilderImpl
	extends UserEntryDSFieldBuilderImpl<NumberDSField>
	implements NumberDSFieldBuilder {

	public NumberDSFieldBuilderImpl(
		String documentKey, String fieldKey, Integer pageNumber) {

		super(documentKey, fieldKey, pageNumber);
	}

	@Override
	public DSField<NumberDSField> getDSField() {
		NumberDSFieldImpl numberDSFieldImpl = new NumberDSFieldImpl(
			getDocumentKey(), getFieldKey(), getPageNumber()) {

			{
				setFormula(_formula);
				setSenderRequired(_senderRequired);
			}
		};

		populateFields(numberDSFieldImpl);

		return numberDSFieldImpl;
	}

	@Override
	public NumberDSFieldBuilder setFormula(String formula) {
		_formula = formula;

		return this;
	}

	@Override
	public NumberDSFieldBuilder setSenderRequired(Boolean senderRequired) {
		_senderRequired = senderRequired;

		return this;
	}

	private String _formula;
	private Boolean _senderRequired;

}