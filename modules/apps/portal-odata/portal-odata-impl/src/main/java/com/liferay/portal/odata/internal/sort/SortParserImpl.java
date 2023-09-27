/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.sort;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.sort.InvalidSortException;
import com.liferay.portal.odata.sort.SortField;
import com.liferay.portal.odata.sort.SortParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Parses {@code Sort} strings. This class uses a model to create a {@code
 * SortField} list.
 *
 * @author Cristina González
 */
public class SortParserImpl implements SortParser {

	public SortParserImpl(EntityModel entityModel) {
		_entityModel = entityModel;
	}

	/**
	 * Returns a {@code SortField} list from a comma-separated list of field
	 * names and sort directions.
	 *
	 * <p>
	 * Sort directions {@code desc} and {@code asc} can be appended to each sort
	 * field, separated by the {@code :} character. If a sort direction isn't
	 * provided, ascending order is used by default.
	 * </p>
	 *
	 * <p>
	 * For example,
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * field1,field2,field3
	 * field1:asc,field2:desc,field3
	 * field1:asc,field2,field3:desc
	 * </code>
	 * </pre></p>
	 *
	 * @param  sortString the string to parse
	 * @return the sort field list
	 */
	@Override
	public List<SortField> parse(String sortString) {
		if (Validator.isNull(sortString)) {
			return Collections.emptyList();
		}

		List<SortField> sortFields = new ArrayList<>();

		for (String string : StringUtil.split(sortString)) {
			sortFields.add(getSortField(string));
		}

		return sortFields;
	}

	protected EntityField getEntityField(
		Map<String, EntityField> entityFieldsMap, String fieldName) {

		if (fieldName.contains(StringPool.FORWARD_SLASH)) {
			List<String> list = StringUtil.split(fieldName, '/');

			String complexTypeName = list.get(0);

			EntityField entityField = entityFieldsMap.get(complexTypeName);

			if ((entityField == null) ||
				!Objects.equals(
					EntityField.Type.COMPLEX, entityField.getType())) {

				throw new InvalidSortException(
					"Unable to sort because \"" + fieldName +
						"\" is not a complex property");
			}

			ComplexEntityField complexEntityField =
				(ComplexEntityField)entityField;

			return getEntityField(
				complexEntityField.getEntityFieldsMap(),
				fieldName.substring(complexTypeName.length() + 1));
		}

		return entityFieldsMap.get(fieldName);
	}

	protected SortField getSortField(String sortString) {
		List<String> list = StringUtil.split(sortString, ':');

		if (list.isEmpty()) {
			return null;
		}

		if (list.size() > 2) {
			throw new InvalidSortException(
				"Unable to parse sort string: " + sortString);
		}

		String fieldName = list.get(0);

		boolean ascending;

		if (list.size() > 1) {
			ascending = isAscending(list.get(1));
		}
		else {
			ascending = _ASC_DEFAULT;
		}

		if (_entityModel == null) {
			return new SortField(fieldName, ascending);
		}

		EntityField entityField = getEntityField(
			_entityModel.getEntityFieldsMap(), fieldName);

		if (entityField == null) {
			throw new InvalidSortException(
				"Unable to sort by property: " + fieldName);
		}

		return new SortField(entityField, ascending);
	}

	protected boolean isAscending(String orderBy) {
		if (orderBy == null) {
			return _ASC_DEFAULT;
		}

		if (_ORDER_BY_ASC.equals(
				com.liferay.portal.kernel.util.StringUtil.toLowerCase(
					orderBy))) {

			return true;
		}

		if (_ORDER_BY_DESC.equals(
				com.liferay.portal.kernel.util.StringUtil.toLowerCase(
					orderBy))) {

			return false;
		}

		return _ASC_DEFAULT;
	}

	private static final boolean _ASC_DEFAULT = true;

	private static final String _ORDER_BY_ASC = "asc";

	private static final String _ORDER_BY_DESC = "desc";

	private final EntityModel _entityModel;

}