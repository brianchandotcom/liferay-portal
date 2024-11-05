/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.helper;

import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;

/**
 * @author Eudaldo Alonso
 */
public class InfoItemFieldMapped {

	public InfoItemFieldMapped(
		String fieldName, InfoItemReference infoItemReference, Object object) {

		_fieldName = fieldName;
		_infoItemReference = infoItemReference;
		_object = object;
	}

	public String getClassName() {
		return _infoItemReference.getClassName();
	}

	public String getFieldName() {
		return _fieldName;
	}

	public InfoItemIdentifier getInfoItemIdentifier() {
		return _infoItemReference.getInfoItemIdentifier();
	}

	public InfoItemReference getInfoItemReference() {
		return _infoItemReference;
	}

	public Object getObject() {
		return _object;
	}

	private final String _fieldName;
	private final InfoItemReference _infoItemReference;
	private final Object _object;

}