/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class Fields implements Iterable<Field>, Serializable {

	public boolean contains(String name) {
		return _fieldsMap.containsKey(name);
	}

	@Override
	public boolean equals(Object object) {
		return equals(object, true);
	}

	public boolean equals(Object object, boolean includePrivateFields) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Fields)) {
			return false;
		}

		Fields fields = (Fields)object;

		if (includePrivateFields) {
			return Objects.equals(_fieldsMap, fields._fieldsMap);
		}

		List<Field> fieldsList1 = getFieldsList(includePrivateFields);
		List<Field> fieldsList2 = fields.getFieldsList(includePrivateFields);

		if (fieldsList1.size() != fieldsList2.size()) {
			return false;
		}

		if (fieldsList1.containsAll(fieldsList2)) {
			return true;
		}

		return false;
	}

	public Field get(String name) {
		return _fieldsMap.get(name);
	}

	public Set<Locale> getAvailableLocales() {
		Set<Locale> availableLocales = new HashSet<>();

		for (Field field : _fieldsMap.values()) {
			if (field.isPrivate()) {
				continue;
			}

			for (Locale availableLocale : field.getAvailableLocales()) {
				availableLocales.add(availableLocale);
			}
		}

		return availableLocales;
	}

	public long getDDMStructureId() {
		long ddmStructureId = 0;

		Iterator<Field> iterator = iterator();

		if (iterator.hasNext()) {
			Field field = iterator.next();

			ddmStructureId = field.getDDMStructureId();
		}

		return ddmStructureId;
	}

	public Locale getDefaultLocale() {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		Iterator<Field> iterator = iterator();

		if (iterator.hasNext()) {
			Field field = iterator.next();

			defaultLocale = field.getDefaultLocale();
		}

		return defaultLocale;
	}

	public Set<String> getNames() {
		return _fieldsMap.keySet();
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _fieldsMap);

		return HashUtil.hash(hash, getFieldsList(true));
	}

	@Override
	public Iterator<Field> iterator() {
		return iterator(false);
	}

	public Iterator<Field> iterator(boolean includePrivateFields) {
		return iterator(null, includePrivateFields);
	}

	public Iterator<Field> iterator(
		Comparator<Field> comparator, boolean includePrivateFields) {

		List<Field> fieldsList = getFieldsList(includePrivateFields);

		if (comparator != null) {
			Collections.sort(fieldsList, comparator);
		}

		return fieldsList.iterator();
	}

	public void put(Field field) {
		_fieldsMap.put(field.getName(), field);
	}

	public Field remove(String name) {
		return _fieldsMap.remove(name);
	}

	protected List<Field> getFieldsList(boolean includePrivateFields) {
		List<Field> fieldsList = new ArrayList<>();

		for (Field field : _fieldsMap.values()) {
			if (!includePrivateFields && field.isPrivate()) {
				continue;
			}

			fieldsList.add(field);
		}

		return fieldsList;
	}

	private final Map<String, Field> _fieldsMap = new HashMap<>();

}