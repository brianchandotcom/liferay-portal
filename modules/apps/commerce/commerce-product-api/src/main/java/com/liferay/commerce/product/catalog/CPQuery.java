/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.catalog;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPQuery {

	public static final String[] ORDER_BY_COLUMNS = {
		"name", "createDate", "modifiedDate", "publishDate", "expirationDate",
		"priority"
	};

	public static String checkOrderByCol(String orderByCol) {
		if (ArrayUtil.contains(ORDER_BY_COLUMNS, orderByCol) ||
			(orderByCol != null)) {

			return orderByCol;
		}

		return ORDER_BY_COLUMNS[2];
	}

	public static String checkOrderByType(String orderByType) {
		if ((orderByType == null) ||
			StringUtil.equalsIgnoreCase(orderByType, "DESC")) {

			return "DESC";
		}

		return "ASC";
	}

	public void addAllTagIdsArray(long[] allTagsIds) {
		if (allTagsIds.length == 0) {
			return;
		}

		_allTagIdsArray = ArrayUtil.append(_allTagIdsArray, allTagsIds);

		_allTagIds = _flattenTagIds(_allTagIdsArray);
	}

	public void addNotAllTagIdsArray(long[] notAllTagsIds) {
		if (notAllTagsIds.length == 0) {
			return;
		}

		_notAllTagIdsArray = ArrayUtil.append(
			_notAllTagIdsArray, notAllTagsIds);

		_notAllTagIds = _flattenTagIds(_notAllTagIdsArray);
	}

	public long[] getAllCategoryIds() {
		return _allCategoryIds.clone();
	}

	public long[] getAllTagIds() {
		return _allTagIds.clone();
	}

	public long[][] getAllTagIdsArray() {
		return _allTagIdsArray.clone();
	}

	public long[] getAnyCategoryIds() {
		return _anyCategoryIds.clone();
	}

	public long[] getAnyTagIds() {
		return _anyTagIds.clone();
	}

	public Serializable getAttribute(String name) {
		return _attributes.get(name);
	}

	public Map<String, Serializable> getAttributes() {
		return _attributes;
	}

	public long[] getNotAllCategoryIds() {
		return _notAllCategoryIds.clone();
	}

	public long[] getNotAllTagIds() {
		return _notAllTagIds.clone();
	}

	public long[][] getNotAllTagIdsArray() {
		return _notAllTagIdsArray.clone();
	}

	public long[] getNotAnyCategoryIds() {
		return _notAnyCategoryIds.clone();
	}

	public long[] getNotAnyTagIds() {
		return _notAnyTagIds.clone();
	}

	public String getOrderByCol1() {
		return checkOrderByCol(_orderByCol1);
	}

	public String getOrderByCol2() {
		return checkOrderByCol(_orderByCol2);
	}

	public String getOrderByType1() {
		return checkOrderByType(_orderByType1);
	}

	public String getOrderByType2() {
		return checkOrderByType(_orderByType2);
	}

	public void setAllCategoryIds(long[] allCategoryIds) {
		_allCategoryIds = allCategoryIds.clone();

		_toString = null;
	}

	public void setAllTagIds(long[] allTagIds) {
		_allTagIds = allTagIds.clone();

		_allTagIdsArray = _expandTagIds(allTagIds);

		_toString = null;
	}

	public void setAllTagIdsArray(long[][] allTagIdsArray) {
		_allTagIdsArray = allTagIdsArray.clone();

		_allTagIds = _flattenTagIds(allTagIdsArray.clone());

		_toString = null;
	}

	public void setAndOperator(boolean andOperator) {
		_andOperator = andOperator;
	}

	public void setAnyCategoryIds(long[] anyCategoryIds) {
		_anyCategoryIds = anyCategoryIds.clone();

		_toString = null;
	}

	public void setAnyTagIds(long[] anyTagIds) {
		_anyTagIds = anyTagIds.clone();

		_toString = null;
	}

	public void setAttribute(String name, Serializable value) {
		_attributes.put(name, value);
	}

	public void setAttributes(Map<String, Serializable> attributes) {
		if (_attributes == null) {
			_attributes = new HashMap<>();
		}
		else {
			_attributes = attributes;
		}
	}

	public void setNotAllCategoryIds(long[] notAllCategoryIds) {
		_notAllCategoryIds = notAllCategoryIds.clone();

		_toString = null;
	}

	public void setNotAllTagIds(long[] notAllTagIds) {
		_notAllTagIds = notAllTagIds.clone();

		_notAllTagIdsArray = _expandTagIds(notAllTagIds.clone());

		_toString = null;
	}

	public void setNotAllTagIdsArray(long[][] notAllTagIdsArray) {
		_notAllTagIdsArray = notAllTagIdsArray.clone();

		_notAllTagIds = _flattenTagIds(notAllTagIdsArray.clone());

		_toString = null;
	}

	public void setNotAnyCategoryIds(long[] notAnyCategoryIds) {
		_notAnyCategoryIds = notAnyCategoryIds.clone();

		_toString = null;
	}

	public void setNotAnyTagIds(long[] notAnyTagIds) {
		_notAnyTagIds = notAnyTagIds.clone();

		_toString = null;
	}

	public void setOrderByCol1(String orderByCol1) {
		_orderByCol1 = orderByCol1;

		_toString = null;
	}

	public void setOrderByCol2(String orderByCol2) {
		_orderByCol2 = orderByCol2;

		_toString = null;
	}

	public void setOrderByType1(String orderByType1) {
		_orderByType1 = orderByType1;

		_toString = null;
	}

	public void setOrderByType2(String orderByType2) {
		_orderByType2 = orderByType2;

		_toString = null;
	}

	@Override
	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		_toString = StringBundler.concat(
			"{allCategoryIds=", StringUtil.merge(_allCategoryIds),
			", allTagIds=", StringUtil.merge(_allTagIds), ", andOperator=",
			_andOperator, ", anyCategoryIds=",
			StringUtil.merge(_anyCategoryIds), ", anyTagIds=",
			StringUtil.merge(_anyTagIds), ", notAllCategoryIds=",
			StringUtil.merge(_notAllCategoryIds), ", notAllTagIds=",
			StringUtil.merge(_notAllTagIds), ", notAnyCategoryIds=",
			StringUtil.merge(_notAnyCategoryIds), ", notAnyTagIds=",
			StringUtil.merge(_notAnyTagIds), ", orderByCol1=", _orderByCol1,
			", orderByCol2=", _orderByCol2, ", orderByType1=", _orderByType1,
			", orderByType2=", _orderByType2, "}");

		return _toString;
	}

	private long[][] _expandTagIds(long[] tagIds) {
		long[][] tagIdsArray = new long[tagIds.length][1];

		for (int i = 0; i < tagIds.length; i++) {
			tagIdsArray[i][0] = tagIds[i];
		}

		return tagIdsArray;
	}

	private long[] _flattenTagIds(long[][] tagIdsArray) {
		List<Long> tagIdsList = new ArrayList<>();

		for (long[] tagIds : tagIdsArray) {
			for (long tagId : tagIds) {
				tagIdsList.add(tagId);
			}
		}

		return ArrayUtil.toArray(tagIdsList.toArray(new Long[0]));
	}

	private long[] _allCategoryIds = new long[0];
	private long[] _allTagIds = new long[0];
	private long[][] _allTagIdsArray = new long[0][];
	private boolean _andOperator;
	private long[] _anyCategoryIds = new long[0];
	private long[] _anyTagIds = new long[0];
	private Map<String, Serializable> _attributes = new HashMap<>();
	private long[] _notAllCategoryIds = new long[0];
	private long[] _notAllTagIds = new long[0];
	private long[][] _notAllTagIdsArray = new long[0][];
	private long[] _notAnyCategoryIds = new long[0];
	private long[] _notAnyTagIds = new long[0];
	private String _orderByCol1;
	private String _orderByCol2;
	private String _orderByType1;
	private String _orderByType2;
	private String _toString;

}