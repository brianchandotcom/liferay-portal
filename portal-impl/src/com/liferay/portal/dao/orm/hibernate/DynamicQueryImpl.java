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
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

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
		_detachedCriteria.add(_toHibernateCriterion(criterion));

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

	private org.hibernate.criterion.Criterion _toHibernateCriterion(
		Criterion criterion) {

		if (criterion instanceof ConjunctionImpl) {
			ConjunctionImpl conjunctionImpl = (ConjunctionImpl)criterion;

			Conjunction conjunction = Restrictions.conjunction();

			for (Criterion curCriterion : conjunctionImpl.getCriterions()) {
				conjunction.add(_toHibernateCriterion(curCriterion));
			}

			return conjunction;
		}

		if (criterion instanceof DisjunctionImpl) {
			DisjunctionImpl disjunctionImpl = (DisjunctionImpl)criterion;

			Disjunction disjunction = Restrictions.disjunction();

			for (Criterion curCriterion : disjunctionImpl.getCriterions()) {
				disjunction.add(_toHibernateCriterion(curCriterion));
			}

			return disjunction;
		}

		CriterionImpl criterionImpl = (CriterionImpl)criterion;

		List<Criterion> criterions = criterionImpl.getCriterions();

		if (criterions != null) {
			return _toHibernateCriterion(
				criterionImpl.getCriterionType(), criterions);
		}

		DynamicQuery dynamicQuery = criterionImpl.getDynamicQuery();

		if (dynamicQuery != null) {
			return _toHibernateCriterion(
				criterionImpl.getCriterionType(), dynamicQuery,
				criterionImpl.getPropertyName());
		}

		Integer size = criterionImpl.getSize();

		if (size != null) {
			return _toHibernateCriterion(
				criterionImpl.getCriterionType(),
				criterionImpl.getPropertyName(), size);
		}

		String targetPropertyName = criterionImpl.getTargetPropertyName();

		if (targetPropertyName != null) {
			return _toHibernateCriterion(
				criterionImpl.getCriterionType(),
				criterionImpl.getPropertyName(), targetPropertyName);
		}

		Object[] values = criterionImpl.getValues();

		if (values != null) {
			return _toHibernateCriterion(
				criterionImpl, criterionImpl.getCriterionType(),
				criterionImpl.getPropertyName(), values);
		}

		return _toHibernateCriterion(
			criterionImpl, criterionImpl.getCriterionType(),
			criterionImpl.getPropertyName());
	}

	private org.hibernate.criterion.Criterion _toHibernateCriterion(
		CriterionImpl criterionImpl, CriterionType criterionType,
		String propertyName) {

		if (criterionType == CriterionType.ALL_EQ) {
			return Restrictions.allEq(criterionImpl.getPropertyNameValues());
		}
		else if (criterionType == CriterionType.IS_EMPTY) {
			return Restrictions.isEmpty(propertyName);
		}
		else if (criterionType == CriterionType.IS_NOT_EMPTY) {
			return Restrictions.isNotEmpty(propertyName);
		}
		else if (criterionType == CriterionType.IS_NOT_NULL) {
			return Restrictions.isNotNull(propertyName);
		}
		else if (criterionType == CriterionType.IS_NULL) {
			return Restrictions.isNull(propertyName);
		}
		else if (criterionType == CriterionType.SQL_RESTRICTION) {
			return Restrictions.sqlRestriction(criterionImpl.getSQL());
		}

		throw new IllegalStateException(
			"Unexpected criterion type: " + criterionType);
	}

	private org.hibernate.criterion.Criterion _toHibernateCriterion(
		CriterionImpl criterionImpl, CriterionType criterionType,
		String propertyName, Object[] values) {

		if (criterionType == CriterionType.BETWEEN) {
			return Restrictions.between(propertyName, values[0], values[1]);
		}
		else if (criterionType == CriterionType.EQ) {
			return Restrictions.eq(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.GE) {
			return Restrictions.ge(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.GT) {
			return Restrictions.gt(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.ILIKE) {
			return Restrictions.ilike(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.IN) {
			return Restrictions.in(propertyName, values);
		}
		else if (criterionType == CriterionType.LE) {
			return Restrictions.le(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.LIKE) {
			return Restrictions.like(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.LT) {
			return Restrictions.lt(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.NE) {
			return Restrictions.ne(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.SQL_RESTRICTION) {
			return Restrictions.sqlRestriction(
				criterionImpl.getSQL(), values,
				_toHibernateTypes(criterionImpl.getSQLTypes()));
		}

		throw new IllegalStateException(
			"Unexpected criterion type: " + criterionType);
	}

	private org.hibernate.criterion.Criterion _toHibernateCriterion(
		CriterionType criterionType, DynamicQuery dynamicQuery,
		String propertyName) {

		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)dynamicQuery;

		DetachedCriteria detachedCriteria =
			dynamicQueryImpl.getDetachedCriteria();

		Property property = Property.forName(propertyName);

		if (criterionType == CriterionType.EQ) {
			return property.eq(detachedCriteria);
		}
		else if (criterionType == CriterionType.EQ_ALL) {
			return property.eqAll(detachedCriteria);
		}
		else if (criterionType == CriterionType.GE) {
			return property.ge(detachedCriteria);
		}
		else if (criterionType == CriterionType.GE_ALL) {
			return property.geAll(detachedCriteria);
		}
		else if (criterionType == CriterionType.GE_SOME) {
			return property.geSome(detachedCriteria);
		}
		else if (criterionType == CriterionType.GT) {
			return property.gt(detachedCriteria);
		}
		else if (criterionType == CriterionType.GT_ALL) {
			return property.gtAll(detachedCriteria);
		}
		else if (criterionType == CriterionType.GT_SOME) {
			return property.gtSome(detachedCriteria);
		}
		else if (criterionType == CriterionType.IN) {
			return property.in(detachedCriteria);
		}
		else if (criterionType == CriterionType.LE) {
			return property.le(detachedCriteria);
		}
		else if (criterionType == CriterionType.LE_ALL) {
			return property.leAll(detachedCriteria);
		}
		else if (criterionType == CriterionType.LE_SOME) {
			return property.leSome(detachedCriteria);
		}
		else if (criterionType == CriterionType.LT) {
			return property.lt(detachedCriteria);
		}
		else if (criterionType == CriterionType.LT_ALL) {
			return property.ltAll(detachedCriteria);
		}
		else if (criterionType == CriterionType.LT_SOME) {
			return property.ltSome(detachedCriteria);
		}
		else if (criterionType == CriterionType.NE) {
			return property.ne(detachedCriteria);
		}
		else if (criterionType == CriterionType.NOT_IN) {
			return property.notIn(detachedCriteria);
		}

		throw new IllegalStateException(
			"Unexpected criterion type: " + criterionType);
	}

	private org.hibernate.criterion.Criterion _toHibernateCriterion(
		CriterionType criterionType, List<Criterion> criterions) {

		if (criterions.size() == 1) {
			return Restrictions.not(_toHibernateCriterion(criterions.get(0)));
		}

		if (criterions.size() == 2) {
			if (criterionType == CriterionType.OR) {
				return Restrictions.or(
					_toHibernateCriterion(criterions.get(0)),
					_toHibernateCriterion(criterions.get(1)));
			}

			if (criterionType == CriterionType.AND) {
				return Restrictions.and(
					_toHibernateCriterion(criterions.get(0)),
					_toHibernateCriterion(criterions.get(1)));
			}
		}

		throw new IllegalStateException(
			"Unexpected criterion type: " + criterionType);
	}

	private org.hibernate.criterion.Criterion _toHibernateCriterion(
		CriterionType criterionType, String propertyName, int size) {

		if (criterionType == CriterionType.EQ) {
			return Restrictions.sizeEq(propertyName, size);
		}
		else if (criterionType == CriterionType.GE) {
			return Restrictions.sizeGe(propertyName, size);
		}
		else if (criterionType == CriterionType.GT) {
			return Restrictions.sizeGt(propertyName, size);
		}
		else if (criterionType == CriterionType.LE) {
			return Restrictions.sizeLe(propertyName, size);
		}
		else if (criterionType == CriterionType.LT) {
			return Restrictions.sizeLt(propertyName, size);
		}
		else if (criterionType == CriterionType.NE) {
			return Restrictions.sizeNe(propertyName, size);
		}

		throw new IllegalStateException(
			"Unexpected criterion type: " + criterionType);
	}

	private org.hibernate.criterion.Criterion _toHibernateCriterion(
		CriterionType criterionType, String propertyName,
		String targetPropertyName) {

		if (criterionType == CriterionType.EQ) {
			return Restrictions.eqProperty(propertyName, targetPropertyName);
		}
		else if (criterionType == CriterionType.GE) {
			return Restrictions.geProperty(propertyName, targetPropertyName);
		}
		else if (criterionType == CriterionType.GT) {
			return Restrictions.gtProperty(propertyName, targetPropertyName);
		}
		else if (criterionType == CriterionType.LE) {
			return Restrictions.leProperty(propertyName, targetPropertyName);
		}
		else if (criterionType == CriterionType.LT) {
			return Restrictions.ltProperty(propertyName, targetPropertyName);
		}
		else if (criterionType == CriterionType.NE) {
			return Restrictions.neProperty(propertyName, targetPropertyName);
		}

		throw new IllegalStateException(
			"Unexpected criterion type: " + criterionType);
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
		if (types == null) {
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