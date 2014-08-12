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

package com.liferay.portal.security.ldap;

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
		return _modificationItems.toArray(
			new ModificationItem[_modificationItems.size()]);
	}

	private Modifications() {
	}

	private final List<ModificationItem> _modificationItems =
		new ArrayList<ModificationItem>();

}