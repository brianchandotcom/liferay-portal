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
import java.util.Map;

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

	public DynamicQueryImpl(Class<?> clazz, String alias) {
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

		CriterionImpl criterionImpl = (CriterionImpl)criterion;

		CriterionType criterionType = criterionImpl.getCriterionType();
		String propertyName = criterionImpl.getPropertyName();
		Object[] values = criterionImpl.getValues();

		if (criterionType == CriterionType.ALL_EQ) {
			return Restrictions.allEq(criterionImpl.getPropertyNameValues());
		}
		else if (criterionType == CriterionType.AND) {
			List<Criterion> criterions = criterionImpl.getCriterions();

			return Restrictions.and(
				_toHibernateCriterion(criterions.get(0)),
				_toHibernateCriterion(criterions.get(1)));
		}
		else if (criterionType == CriterionType.BETWEEN) {
			return Restrictions.between(propertyName, values[0], values[1]);
		}
		else if (criterionType == CriterionType.CONJUNCTION) {
			Conjunction hibernateConjunction = Restrictions.conjunction();

			for (Criterion childCriterion : criterionImpl.getCriterions()) {
				hibernateConjunction.add(_toHibernateCriterion(childCriterion));
			}

			return hibernateConjunction;
		}
		else if (criterionType == CriterionType.DISJUNCTION) {
			Disjunction hibernateDisjunction = Restrictions.disjunction();

			for (Criterion childCriterion : criterionImpl.getCriterions()) {
				hibernateDisjunction.add(_toHibernateCriterion(childCriterion));
			}

			return hibernateDisjunction;
		}
		else if (criterionType == CriterionType.EQ) {
			return Restrictions.eq(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.EQ_PROPERTY) {
			return Restrictions.eqProperty(propertyName, (String)values[0]);
		}
		else if (criterionType == CriterionType.GE) {
			return Restrictions.ge(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.GE_PROPERTY) {
			return Restrictions.geProperty(propertyName, (String)values[0]);
		}
		else if (criterionType == CriterionType.GT) {
			return Restrictions.gt(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.GT_PROPERTY) {
			return Restrictions.gtProperty(propertyName, (String)values[0]);
		}
		else if (criterionType == CriterionType.ILIKE) {
			return Restrictions.ilike(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.IN) {
			return Restrictions.in(propertyName, criterionImpl.getValues());
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
		else if (criterionType == CriterionType.LE) {
			return Restrictions.le(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.LE_PROPERTY) {
			return Restrictions.leProperty(propertyName, (String)values[0]);
		}
		else if (criterionType == CriterionType.LIKE) {
			return Restrictions.like(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.LT) {
			return Restrictions.lt(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.LT_PROPERTY) {
			return Restrictions.ltProperty(propertyName, (String)values[0]);
		}
		else if (criterionType == CriterionType.NE) {
			return Restrictions.ne(propertyName, values[0]);
		}
		else if (criterionType == CriterionType.NE_PROPERTY) {
			return Restrictions.neProperty(propertyName, (String)values[0]);
		}
		else if (criterionType == CriterionType.NOT) {
			List<Criterion> criterions = criterionImpl.getCriterions();

			return Restrictions.not(_toHibernateCriterion(criterions.get(0)));
		}
		else if (criterionType == CriterionType.OR) {
			List<Criterion> criterions = criterionImpl.getCriterions();

			return Restrictions.or(
				_toHibernateCriterion(criterions.get(0)),
				_toHibernateCriterion(criterions.get(1)));
		}
		else if (criterionType == CriterionType.SIZE_EQ) {
			return Restrictions.sizeEq(propertyName, (Integer)values[0]);
		}
		else if (criterionType == CriterionType.SIZE_GE) {
			return Restrictions.sizeGe(propertyName, (Integer)values[0]);
		}
		else if (criterionType == CriterionType.SIZE_GT) {
			return Restrictions.sizeGt(propertyName, (Integer)values[0]);
		}
		else if (criterionType == CriterionType.SIZE_LE) {
			return Restrictions.sizeLe(propertyName, (Integer)values[0]);
		}
		else if (criterionType == CriterionType.SIZE_LT) {
			return Restrictions.sizeLt(propertyName, (Integer)values[0]);
		}
		else if (criterionType == CriterionType.SIZE_NE) {
			return Restrictions.sizeNe(propertyName, (Integer)values[0]);
		}
		else if (criterionType == CriterionType.SQL_RESTRICTION) {
			Object[] sqlValues = criterionImpl.getValues();

			if (sqlValues == null) {
				return Restrictions.sqlRestriction(criterionImpl.getSql());
			}

			return Restrictions.sqlRestriction(
				criterionImpl.getSql(), sqlValues,
				_toHibernateTypes(criterionImpl.getSqlTypes()));
		}
		else if (criterionType == CriterionType.SUBQUERY_EQ) {
			Property property = Property.forName(propertyName);

			return property.eq(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_EQ_ALL) {
			Property property = Property.forName(propertyName);

			return property.eqAll(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_GE) {
			Property property = Property.forName(propertyName);

			return property.ge(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_GE_ALL) {
			Property property = Property.forName(propertyName);

			return property.geAll(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_GE_SOME) {
			Property property = Property.forName(propertyName);

			return property.geSome(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_GT) {
			Property property = Property.forName(propertyName);

			return property.gt(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_GT_ALL) {
			Property property = Property.forName(propertyName);

			return property.gtAll(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_GT_SOME) {
			Property property = Property.forName(propertyName);

			return property.gtSome(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_IN) {
			Property property = Property.forName(propertyName);

			return property.in(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_LE) {
			Property property = Property.forName(propertyName);

			return property.le(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_LE_ALL) {
			Property property = Property.forName(propertyName);

			return property.leAll(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_LE_SOME) {
			Property property = Property.forName(propertyName);

			return property.leSome(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_LT) {
			Property property = Property.forName(propertyName);

			return property.lt(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_LT_ALL) {
			Property property = Property.forName(propertyName);

			return property.ltAll(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_LT_SOME) {
			Property property = Property.forName(propertyName);

			return property.ltSome(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_NE) {
			Property property = Property.forName(propertyName);

			return property.ne(_toHibernateDetachedCriteria(criterionImpl));
		}
		else if (criterionType == CriterionType.SUBQUERY_NOT_IN) {
			Property property = Property.forName(propertyName);

			return property.notIn(_toHibernateDetachedCriteria(criterionImpl));
		}

		throw new IllegalStateException(
			"Unexpected criterion type: " + criterionType);
	}

	private DetachedCriteria _toHibernateDetachedCriteria(
		CriterionImpl criterionImpl) {

		Object[] values = criterionImpl.getValues();

		DynamicQueryImpl subselectDynamicQueryImpl =
			(DynamicQueryImpl)values[0];

		return subselectDynamicQueryImpl.getDetachedCriteria();
	}

	private org.hibernate.criterion.Projection _toHibernateProjection(
		Projection projection) {

		if (projection instanceof ProjectionListImpl) {
			ProjectionListImpl projectionListImpl =
				(ProjectionListImpl)projection;

			List<Map.Entry<Projection, String>> projections =
				projectionListImpl.getProjections();

			ProjectionList projectionList = Projections.projectionList();

			for (Map.Entry<Projection, String> entry : projections) {
				String alias = entry.getValue();

				if (alias != null) {
					projectionList.add(
						_toHibernateProjection(entry.getKey()), alias);
				}
				else {
					projectionList.add(_toHibernateProjection(entry.getKey()));
				}
			}

			return projectionList;
		}

		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		ProjectionType projectionType = projectionImpl.getProjectionType();
		String propertyName = projectionImpl.getPropertyName();

		if (projectionType == ProjectionType.ALIAS) {
			return Projections.alias(
				_toHibernateProjection(projectionImpl.getProjection()),
				projectionImpl.getAlias());
		}
		else if (projectionType == ProjectionType.AVG) {
			return Projections.avg(propertyName);
		}
		else if (projectionType == ProjectionType.COUNT) {
			return Projections.count(propertyName);
		}
		else if (projectionType == ProjectionType.COUNT_DISTINCT) {
			return Projections.countDistinct(propertyName);
		}
		else if (projectionType == ProjectionType.DISTINCT) {
			return Projections.distinct(
				_toHibernateProjection(projectionImpl.getProjection()));
		}
		else if (projectionType == ProjectionType.GROUP_PROPERTY) {
			return Projections.groupProperty(propertyName);
		}
		else if (projectionType == ProjectionType.MAX) {
			return Projections.max(propertyName);
		}
		else if (projectionType == ProjectionType.MIN) {
			return Projections.min(propertyName);
		}
		else if (projectionType == ProjectionType.PROPERTY) {
			return Projections.property(propertyName);
		}
		else if (projectionType == ProjectionType.ROW_COUNT) {
			return Projections.rowCount();
		}
		else if (projectionType == ProjectionType.SQL_GROUP_PROJECTION) {
			return Projections.sqlGroupProjection(
				projectionImpl.getSql(), projectionImpl.getGroupBy(),
				projectionImpl.getColumnAliases(),
				_toHibernateTypes(projectionImpl.getTypes()));
		}
		else if (projectionType == ProjectionType.SQL_PROJECTION) {
			return Projections.sqlProjection(
				projectionImpl.getSql(), projectionImpl.getColumnAliases(),
				_toHibernateTypes(projectionImpl.getTypes()));
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