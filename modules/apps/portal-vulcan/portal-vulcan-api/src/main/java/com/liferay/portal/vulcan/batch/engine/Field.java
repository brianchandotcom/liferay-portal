/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.batch.engine;

/**
 * @author Javier de Arcos
 */
public class Field {

	public static Field of(
		String anyOfGroup, String description, String name, boolean readOnly,
		String ref, boolean required, String type, String[] unsupportedFormats,
		boolean writeOnly) {

		return new Field(
			_toAccessType(readOnly, writeOnly), anyOfGroup, description, name,
			ref, required, type, unsupportedFormats);
	}

	public AccessType getAccessType() {
		return _accessType;
	}

	public String getAnyOfGroup() {
		return _anyOfGroup;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public String getRef() {
		return _ref;
	}

	public String getType() {
		return _type;
	}

	public String[] getUnsupportedFormats() {
		return _unsupportedFormats;
	}

	public boolean isRequired() {
		return _required;
	}

	public enum AccessType {

		READ, READWRITE, WRITE

	}

	private static AccessType _toAccessType(
		boolean readOnly, boolean writeOnly) {

		if (readOnly) {
			return AccessType.READ;
		}
		else if (writeOnly) {
			return AccessType.WRITE;
		}

		return AccessType.READWRITE;
	}

	private Field(
		AccessType accessType, String anyOfGroup, String description,
		String name, String ref, boolean required, String type,
		String[] unsupportedFormats) {

		_accessType = accessType;
		_anyOfGroup = anyOfGroup;
		_description = description;
		_name = name;
		_ref = ref;
		_required = required;
		_type = type;
		_unsupportedFormats = unsupportedFormats;
	}

	private final AccessType _accessType;
	private final String _anyOfGroup;
	private final String _description;
	private final String _name;
	private final String _ref;
	private final boolean _required;
	private final String _type;
	private final String[] _unsupportedFormats;

}