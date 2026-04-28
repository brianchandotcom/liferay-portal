/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicQueryImpl implements DynamicQuery {

	public DynamicQueryImpl(String alias, Class<?> clazz) {
		if (alias != null) {
			_detachedCriteria = DetachedCriteria.forClass(clazz, alias);
		}
		else {
			_detachedCriteria = DetachedCriteria.forClass(clazz);
		}
	}

	@Override
	public DynamicQuery add(Criterion criterion) {
		CriterionImpl criterionImpl = (CriterionImpl)criterion;

		_detachedCriteria.add(criterionImpl.getWrappedCriterion());

		return this;
	}

	@Override
	public DynamicQuery addOrder(Order order) {
		OrderImpl orderImpl = (OrderImpl)order;

		if (orderImpl.isAscending()) {
			_detachedCriteria.addOrder(
				org.hibernate.criterion.Order.asc(orderImpl.getPropertyName()));
		}
		else {
			_detachedCriteria.addOrder(
				org.hibernate.criterion.Order.desc(
					orderImpl.getPropertyName()));
		}

		return this;
	}

	@Override
	public void compile(Session session) {
		org.hibernate.Session hibernateSession =
			(org.hibernate.Session)session.getWrappedSession();

		_criteria = _detachedCriteria.getExecutableCriteria(hibernateSession);

		if ((_start == null) && (_end == null)) {
			return;
		}

		int start = QueryUtil.ALL_POS;

		if (_start != null) {
			start = _start.intValue();
		}

		int end = QueryUtil.ALL_POS;

		if (_end != null) {
			end = _end.intValue();
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)) {
			return;
		}
		else if ((start < QueryUtil.ALL_POS) && (end < QueryUtil.ALL_POS)) {
			_criteria = _criteria.setFirstResult(0);
			_criteria = _criteria.setMaxResults(0);

			_requiresProcessing = false;

			return;
		}

		if (start < 0) {
			start = 0;
		}

		_criteria = _criteria.setFirstResult(start);

		if (end == QueryUtil.ALL_POS) {
			return;
		}

		if (start <= end) {
			end = end - start;
		}
		else {
			end = 0;
		}

		_criteria = _criteria.setMaxResults(end);

		if (end == 0) {
			_requiresProcessing = false;
		}
	}

	public DetachedCriteria getDetachedCriteria() {
		return _detachedCriteria;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List list() {
		return list(true);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List list(boolean unmodifiable) {
		if (!_requiresProcessing) {
			if (unmodifiable) {
				return Collections.emptyList();
			}

			return new ArrayList<>();
		}

		List list = _criteria.list();

		if (unmodifiable) {
			return Collections.unmodifiableList(list);
		}

		return ListUtil.copy(list);
	}

	@Override
	public void setLimit(int start, int end) {
		_start = Integer.valueOf(start);
		_end = Integer.valueOf(end);
	}

	@Override
	public DynamicQuery setProjection(Projection projection) {
		_detachedCriteria.setProjection(_toHibernateProjection(projection));

		return this;
	}

	@Override
	public String toString() {
		if (_criteria != null) {
			return _criteria.toString();
		}

		return super.toString();
	}

	private org.hibernate.criterion.Projection _toHibernateProjection(
		Projection projection) {

		if (projection instanceof ProjectionListImpl) {
			ProjectionListImpl projectionListImpl =
				(ProjectionListImpl)projection;

			ProjectionList projectionList = Projections.projectionList();

			for (Projection curProjection :
					projectionListImpl.getProjections()) {

				projectionList.add(_toHibernateProjection(curProjection));
			}

			return projectionList;
		}

		if (projection instanceof PropertyImpl) {
			PropertyImpl propertyImpl = (PropertyImpl)projection;

			if (propertyImpl.isGroup()) {
				return Projections.groupProperty(
					propertyImpl.getPropertyName());
			}

			return Projections.property(propertyImpl.getPropertyName());
		}

		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		if (projectionImpl.getAlias() != null) {
			return Projections.alias(
				_toHibernateProjection(projectionImpl.getProjection()),
				projectionImpl.getAlias());
		}

		if (projectionImpl.getProjection() != null) {
			return Projections.distinct(
				_toHibernateProjection(projectionImpl.getProjection()));
		}

		if (projectionImpl.getSQL() != null) {
			if (projectionImpl.getGroupBy() != null) {
				return Projections.sqlGroupProjection(
					projectionImpl.getSQL(), projectionImpl.getGroupBy(),
					projectionImpl.getColumnAliases(),
					_toHibernateTypes(projectionImpl.getTypes()));
			}

			return Projections.sqlProjection(
				projectionImpl.getSQL(), projectionImpl.getColumnAliases(),
				_toHibernateTypes(projectionImpl.getTypes()));
		}

		return _toHibernateProjection(
			projectionImpl.getProjectionType(),
			projectionImpl.getPropertyName());
	}

	private org.hibernate.criterion.Projection _toHibernateProjection(
		ProjectionType projectionType, String propertyName) {

		if (projectionType == ProjectionType.AVG) {
			return Projections.avg(propertyName);
		}
		else if (projectionType == ProjectionType.COUNT) {
			return Projections.count(propertyName);
		}
		else if (projectionType == ProjectionType.COUNT_DISTINCT) {
			return Projections.countDistinct(propertyName);
		}
		else if (projectionType == ProjectionType.MAX) {
			return Projections.max(propertyName);
		}
		else if (projectionType == ProjectionType.MIN) {
			return Projections.min(propertyName);
		}
		else if (projectionType == ProjectionType.ROW_COUNT) {
			return Projections.rowCount();
		}
		else if (projectionType == ProjectionType.SUM) {
			return Projections.sum(propertyName);
		}

		throw new IllegalStateException(
			"Unexpected projection type: " + projectionType);
	}

	private org.hibernate.type.Type[] _toHibernateTypes(Type[] types) {
		if (ArrayUtil.isEmpty(types)) {
			return null;
		}

		org.hibernate.type.Type[] hibernateTypes =
			new org.hibernate.type.Type[types.length];

		for (int i = 0; i < types.length; i++) {
			hibernateTypes[i] = TypeTranslator.translate(types[i]);
		}

		return hibernateTypes;
	}

	private Criteria _criteria;
	private final DetachedCriteria _detachedCriteria;
	private Integer _end;
	private boolean _requiresProcessing = true;
	private Integer _start;

}