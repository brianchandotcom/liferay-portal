/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

/**
 * @author Adolfo Pérez
 */
public class ClassTypeField {

	public ClassTypeField(
		long classTypeId, String fieldReference, String label, String name,
		String type) {

		_classTypeId = classTypeId;
		_fieldReference = fieldReference;
		_label = label;
		_name = name;
		_type = type;
	}

	public ClassTypeField(
		String label, String name, String type, long classTypeId) {

		_label = label;
		_name = name;
		_type = type;
		_classTypeId = classTypeId;
	}

	public long getClassTypeId() {
		return _classTypeId;
	}

	public String getFieldReference() {
		return _fieldReference;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	private final long _classTypeId;
	private String _fieldReference;
	private final String _label;
	private final String _name;
	private final String _type;

}