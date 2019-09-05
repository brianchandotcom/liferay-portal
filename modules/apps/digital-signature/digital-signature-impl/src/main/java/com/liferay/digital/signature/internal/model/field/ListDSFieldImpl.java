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

package com.liferay.digital.signature.internal.model.field;

import com.liferay.digital.signature.model.field.DSListItem;
import com.liferay.digital.signature.model.field.ListDSField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class ListDSFieldImpl
	extends UserEntryDSFieldImpl<ListDSField> implements ListDSField {

	public ListDSFieldImpl(
		String documentKey, String fieldKey, Integer pageNumber) {

		super(documentKey, fieldKey, pageNumber);
	}

	public void addDSListItem(DSListItem dsListItem) {
		_dsListItems.add(dsListItem);
	}

	public void addDSListItems(List<DSListItem> dsListItems) {
		_dsListItems.addAll(dsListItems);
	}

	@Override
	public List<DSListItem> getDSListItems() {
		return _dsListItems;
	}

	@Override
	public Boolean getSenderRequired() {
		return _senderRequired;
	}

	public void setSenderRequired(Boolean senderRequired) {
		_senderRequired = senderRequired;
	}

	private List<DSListItem> _dsListItems = new ArrayList<>();
	private Boolean _senderRequired;

}