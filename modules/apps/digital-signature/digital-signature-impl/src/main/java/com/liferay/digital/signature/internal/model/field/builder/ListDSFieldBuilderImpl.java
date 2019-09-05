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

import com.liferay.digital.signature.internal.model.field.DSListItemImpl;
import com.liferay.digital.signature.internal.model.field.ListDSFieldImpl;
import com.liferay.digital.signature.model.field.DSField;
import com.liferay.digital.signature.model.field.DSListItem;
import com.liferay.digital.signature.model.field.ListDSField;
import com.liferay.digital.signature.model.field.builder.ListDSFieldBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class ListDSFieldBuilderImpl
	extends UserEntryDSFieldBuilderImpl<ListDSField>
	implements ListDSFieldBuilder {

	public ListDSFieldBuilderImpl(
		String documentKey, String fieldKey, Integer pageNumber) {

		super(documentKey, fieldKey, pageNumber);
	}

	@Override
	public DSListItem addDSListItem(
		boolean selected, String text, String value) {

		DSListItem dsListItem = new DSListItemImpl(selected, text, value);

		_dsListItems.add(dsListItem);

		return dsListItem;
	}

	@Override
	public DSListItem addDSListItem(String text, String value) {
		return addDSListItem(false, text, value);
	}

	@Override
	public DSField<ListDSField> getDSField() {
		ListDSFieldImpl listDSFieldImpl = new ListDSFieldImpl(
			getDocumentKey(), getFieldKey(), getPageNumber()) {

			{
				addDSListItems(_dsListItems);
				setSenderRequired(_senderRequired);
			}
		};

		populateFields(listDSFieldImpl);

		return listDSFieldImpl;
	}

	@Override
	public ListDSFieldBuilder setSenderRequired(Boolean senderRequired) {
		_senderRequired = senderRequired;

		return this;
	}

	private List<DSListItem> _dsListItems = new ArrayList<>();
	private Boolean _senderRequired;

}