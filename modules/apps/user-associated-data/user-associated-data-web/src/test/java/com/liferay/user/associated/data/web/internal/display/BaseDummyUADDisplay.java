/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.display;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.user.associated.data.display.UADDisplay;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Drew Brokke
 */
public abstract class BaseDummyUADDisplay<T extends UserAssociatedEntity>
	implements UADDisplay<T> {

	@Override
	public long count(long userId) {
		DummyService<T> dummyService = getDummyService();

		return dummyService.count(userId);
	}

	@Override
	public T get(Serializable primaryKey) {
		DummyService<T> dummyService = getDummyService();

		return dummyService.getEntity((long)primaryKey);
	}

	@Override
	public String[] getDisplayFieldNames() {
		return new String[0];
	}

	@Override
	public Map<String, Object> getFieldValues(
		T t, String[] fieldNames, Locale locale) {

		Map<String, Object> fieldValues = new HashMap<>();

		List<String> fieldNamesList = ListUtil.fromArray(fieldNames);

		if (fieldNamesList.indexOf("uuid") != -1) {
			fieldValues.put("uuid", t.getUuid());
		}

		return fieldValues;
	}

	@Override
	public String getName(T t, Locale locale) {
		return t.getName();
	}

	@Override
	public Serializable getParentContainerId(T t) {
		return t.getContainerId();
	}

	@Override
	public Serializable getPrimaryKey(T t) {
		return t.getId();
	}

	@Override
	public List<T> getRange(long userId, int start, int end) {
		return search(userId, null, null, null, null, start, end);
	}

	@Override
	public String[] getSortingFieldNames() {
		return new String[0];
	}

	@Override
	public String getTypeName(Locale locale) {
		return null;
	}

	@Override
	public List<T> search(
		long userId, long[] groupIds, String keywords, String orderByField,
		String orderByType, int start, int end) {

		DummyService<T> dummyService = getDummyService();

		return dummyService.getEntities(userId);
	}

	@Override
	public long searchCount(long userId, long[] groupIds, String keywords) {
		List<T> results = search(
			userId, groupIds, keywords, null, null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		return results.size();
	}

	protected abstract DummyService<T> getDummyService();

}