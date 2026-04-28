/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CriterionImpl implements Criterion {

	public CriterionImpl(CriterionType criterionType, Criterion... criterions) {
		_criterionType = criterionType;
		_criterions = ListUtil.fromArray(criterions);
	}

	public CriterionImpl(
		CriterionType criterionType,
		Map<String, Criterion> propertyNameValues) {

		_criterionType = criterionType;
		_propertyNameValues = propertyNameValues;
	}

	public CriterionImpl(CriterionType criterionType, String propertyName) {
		_criterionType = criterionType;
		_propertyName = propertyName;
	}

	public CriterionImpl(
		CriterionType criterionType, String propertyName, Object... values) {

		_criterionType = criterionType;
		_propertyName = propertyName;
		_values = values;
	}

	public CriterionImpl(
		CriterionType criterionType, String sql, Type[] sqlTypes,
		Object[] values) {

		_criterionType = criterionType;
		_sql = sql;
		_sqlTypes = sqlTypes;
		_values = values;
	}

	public List<Criterion> getCriterions() {
		return _criterions;
	}

	public CriterionType getCriterionType() {
		return _criterionType;
	}

	public String getPropertyName() {
		return _propertyName;
	}

	public Map<String, Criterion> getPropertyNameValues() {
		return _propertyNameValues;
	}

	public String getSQL() {
		return _sql;
	}

	public Type[] getSQLTypes() {
		return _sqlTypes;
	}

	public Object[] getValues() {
		return _values;
	}

	@Override
	public String toString() {
		if (_criterionType == CriterionType.ALL_EQ) {
			return String.valueOf(_propertyNameValues);
		}
		else if (_criterionType == CriterionType.AND) {
			return StringBundler.concat(
				_criterions.get(0), " and ", _criterions.get(1));
		}
		else if (_criterionType == CriterionType.BETWEEN) {
			return StringBundler.concat(
				_propertyName, " between ", _values[0], " and ", _values[1]);
		}
		else if ((_criterionType == CriterionType.CONJUNCTION) ||
				 (_criterionType == CriterionType.DISJUNCTION)) {

			StringBundler sb = new StringBundler((_criterions.size() * 2) + 3);

			sb.append("( ");
			sb.append(
				(_criterionType == CriterionType.CONJUNCTION) ? "and" : "or");

			for (Criterion criterion : _criterions) {
				sb.append(" ");
				sb.append(criterion.toString());
			}

			sb.append(" )");

			return sb.toString();
		}
		else if ((_criterionType == CriterionType.EQ) ||
				 (_criterionType == CriterionType.EQ_PROPERTY)) {

			return StringBundler.concat(_propertyName, "=", _values[0]);
		}
		else if ((_criterionType == CriterionType.GE) ||
				 (_criterionType == CriterionType.GE_PROPERTY)) {

			return StringBundler.concat(_propertyName, ">=", _values[0]);
		}
		else if ((_criterionType == CriterionType.GT) ||
				 (_criterionType == CriterionType.GT_PROPERTY)) {

			return StringBundler.concat(_propertyName, ">", _values[0]);
		}
		else if (_criterionType == CriterionType.ILIKE) {
			return StringBundler.concat(_propertyName, " ilike ", _values[0]);
		}
		else if (_criterionType == CriterionType.IN) {
			return StringBundler.concat(
				_propertyName, " in (", StringUtil.merge(_values, ", "), ")");
		}
		else if (_criterionType == CriterionType.IS_EMPTY) {
			return _propertyName + " is empty";
		}
		else if (_criterionType == CriterionType.IS_NOT_EMPTY) {
			return _propertyName + " is not empty";
		}
		else if (_criterionType == CriterionType.IS_NOT_NULL) {
			return _propertyName + " is not null";
		}
		else if (_criterionType == CriterionType.IS_NULL) {
			return _propertyName + " is null";
		}
		else if ((_criterionType == CriterionType.LE) ||
				 (_criterionType == CriterionType.LE_PROPERTY)) {

			return StringBundler.concat(_propertyName, "<=", _values[0]);
		}
		else if (_criterionType == CriterionType.LIKE) {
			return StringBundler.concat(_propertyName, " like ", _values[0]);
		}
		else if ((_criterionType == CriterionType.LT) ||
				 (_criterionType == CriterionType.LT_PROPERTY)) {

			return StringBundler.concat(_propertyName, "<", _values[0]);
		}
		else if ((_criterionType == CriterionType.NE) ||
				 (_criterionType == CriterionType.NE_PROPERTY)) {

			return StringBundler.concat(_propertyName, "<>", _values[0]);
		}
		else if (_criterionType == CriterionType.NOT) {
			return "not " + _criterions.get(0);
		}
		else if (_criterionType == CriterionType.OR) {
			return StringBundler.concat(
				_criterions.get(0), " or ", _criterions.get(1));
		}
		else if (_criterionType == CriterionType.SIZE_EQ) {
			return StringBundler.concat(_propertyName, ".size=", _values[0]);
		}
		else if (_criterionType == CriterionType.SIZE_GE) {
			return StringBundler.concat(_propertyName, ".size>=", _values[0]);
		}
		else if (_criterionType == CriterionType.SIZE_GT) {
			return StringBundler.concat(_propertyName, ".size>", _values[0]);
		}
		else if (_criterionType == CriterionType.SIZE_LE) {
			return StringBundler.concat(_propertyName, ".size<=", _values[0]);
		}
		else if (_criterionType == CriterionType.SIZE_LT) {
			return StringBundler.concat(_propertyName, ".size<", _values[0]);
		}
		else if (_criterionType == CriterionType.SIZE_NE) {
			return StringBundler.concat(_propertyName, ".size<>", _values[0]);
		}
		else if (_criterionType == CriterionType.SQL_RESTRICTION) {
			return _sql;
		}
		else if (_criterionType == CriterionType.SUBQUERY_EQ) {
			return StringBundler.concat(_propertyName, " = (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_EQ_ALL) {
			return StringBundler.concat(
				_propertyName, " =ALL (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_GE) {
			return StringBundler.concat(
				_propertyName, " >= (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_GE_ALL) {
			return StringBundler.concat(
				_propertyName, " >=ALL (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_GE_SOME) {
			return StringBundler.concat(
				_propertyName, " >=SOME (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_GT) {
			return StringBundler.concat(_propertyName, " > (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_GT_ALL) {
			return StringBundler.concat(
				_propertyName, " >ALL (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_GT_SOME) {
			return StringBundler.concat(
				_propertyName, " >SOME (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_IN) {
			return StringBundler.concat(
				_propertyName, " in (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_LE) {
			return StringBundler.concat(
				_propertyName, " <= (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_LE_ALL) {
			return StringBundler.concat(
				_propertyName, " <=ALL (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_LE_SOME) {
			return StringBundler.concat(
				_propertyName, " <=SOME (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_LT) {
			return StringBundler.concat(_propertyName, " < (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_LT_ALL) {
			return StringBundler.concat(
				_propertyName, " <ALL (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_LT_SOME) {
			return StringBundler.concat(
				_propertyName, " <SOME (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_NE) {
			return StringBundler.concat(
				_propertyName, " <> (", _values[0], ")");
		}
		else if (_criterionType == CriterionType.SUBQUERY_NOT_IN) {
			return StringBundler.concat(
				_propertyName, " not in (", _values[0], ")");
		}

		return "{criterionType=" + _criterionType + "}";
	}

	private List<Criterion> _criterions;
	private final CriterionType _criterionType;
	private String _propertyName;
	private Map<String, Criterion> _propertyNameValues;
	private String _sql;
	private Type[] _sqlTypes;
	private Object[] _values;

}