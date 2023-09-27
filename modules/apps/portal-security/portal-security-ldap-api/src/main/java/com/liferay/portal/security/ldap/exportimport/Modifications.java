/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.exportimport;

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

/**
 * @author Amos Fong
 * @author Brian Wing Shun Chan
 */
public class Modifications {

	public static Modifications getInstance() {
		return new Modifications();
	}

	public ModificationItem addItem(BasicAttribute basicAttribute) {
		return addItem(DirContext.REPLACE_ATTRIBUTE, basicAttribute);
	}

	public ModificationItem addItem(
		int modificationOp, BasicAttribute basicAttribute) {

		ModificationItem modificationItem = new ModificationItem(
			modificationOp, basicAttribute);

		_modificationItems.add(modificationItem);

		return modificationItem;
	}

	public ModificationItem addItem(
		int modificationOp, String id, Object value) {

		BasicAttribute basicAttribute = new BasicAttribute(id);

		if (value != null) {
			if (!(value instanceof byte[]) && !(value instanceof String)) {
				value = value.toString();
			}

			basicAttribute.add(value);
		}

		return addItem(modificationOp, basicAttribute);
	}

	public ModificationItem addItem(
		int modificationOp, String id, String value) {

		BasicAttribute basicAttribute = new BasicAttribute(id);

		if (Validator.isNotNull(value)) {
			basicAttribute.add(value);
		}

		return addItem(modificationOp, basicAttribute);
	}

	public ModificationItem addItem(String id, Object value) {
		return addItem(DirContext.REPLACE_ATTRIBUTE, id, value);
	}

	public ModificationItem addItem(String id, String value) {
		return addItem(DirContext.REPLACE_ATTRIBUTE, id, value);
	}

	public ModificationItem[] getItems() {
		return _modificationItems.toArray(new ModificationItem[0]);
	}

	private Modifications() {
	}

	private final List<ModificationItem> _modificationItems = new ArrayList<>();

}