/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Type;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
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
public class HibernateCriteriaUtil {

	public static Criteria buildCriteria(
		DynamicQueryImpl dynamicQueryImpl, Session session) {

		DetachedCriteria detachedCriteria = _buildDetachedCriteria(
			dynamicQueryImpl);

		return detachedCriteria.getExecutableCriteria(session);
	}

	private static DetachedCriteria _buildDetachedCriteria(
		DynamicQueryImpl dynamicQueryImpl) {

		DetachedCriteria detachedCriteria = null;

		if (dynamicQueryImpl.getAlias() != null) {
			detachedCriteria = DetachedCriteria.forClass(
				dynamicQueryImpl.getClazz(), dynamicQueryImpl.getAlias());
		}
		else {
			detachedCriteria = DetachedCriteria.forClass(
				dynamicQueryImpl.getClazz());
		}

		for (Criterion criterion : dynamicQueryImpl.getCriterions()) {
			detachedCriteria.add(_toCriterion(criterion));
		}

		for (Order order : dynamicQueryImpl.getOrders()) {
			OrderImpl orderImpl = (OrderImpl)order;

			if (orderImpl.isAscending()) {
				detachedCriteria.addOrder(
					org.hibernate.criterion.Order.asc(
						orderImpl.getPropertyName()));
			}
			else {
				detachedCriteria.addOrder(
					org.hibernate.criterion.Order.desc(
						orderImpl.getPropertyName()));
			}
		}

		if (dynamicQueryImpl.getProjection() != null) {
			detachedCriteria.setProjection(
				_toProjection(dynamicQueryImpl.getProjection()));
		}

		return detachedCriteria;
	}

	private static org.hibernate.criterion.Criterion _toCriterion(
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
				_toCriterion(criterions.get(0)),
				_toCriterion(criterions.get(1)));
		}
		else if (criterionType == CriterionType.BETWEEN) {
			return Restrictions.between(propertyName, values[0], values[1]);
		}
		else if (criterionType == CriterionType.CONJUNCTION) {
			Conjunction hibernateConjunction = Restrictions.conjunction();

			for (Criterion childCriterion : criterionImpl.getCriterions()) {
				hibernateConjunction.add(_toCriterion(childCriterion));
			}

			return hibernateConjunction;
		}
		else if (criterionType == CriterionType.DISJUNCTION) {
			Disjunction hibernateDisjunction = Restrictions.disjunction();

			for (Criterion childCriterion : criterionImpl.getCriterions()) {
				hibernateDisjunction.add(_toCriterion(childCriterion));
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
			return Restrictions.in(propertyName, values);
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

			return Restrictions.not(_toCriterion(criterions.get(0)));
		}
		else if (criterionType == CriterionType.OR) {
			List<Criterion> criterions = criterionImpl.getCriterions();

			return Restrictions.or(
				_toCriterion(criterions.get(0)),
				_toCriterion(criterions.get(1)));
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
			if (values == null) {
				return Restrictions.sqlRestriction(criterionImpl.getSQL());
			}

			return Restrictions.sqlRestriction(
				criterionImpl.getSQL(), values,
				_toHibernateTypes(criterionImpl.getSQLTypes()));
		}
		else if (criterionType == CriterionType.SUBQUERY_EQ) {
			Property property = Property.forName(propertyName);

			return property.eq(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_EQ_ALL) {
			Property property = Property.forName(propertyName);

			return property.eqAll(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_GE) {
			Property property = Property.forName(propertyName);

			return property.ge(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_GE_ALL) {
			Property property = Property.forName(propertyName);

			return property.geAll(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_GE_SOME) {
			Property property = Property.forName(propertyName);

			return property.geSome(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_GT) {
			Property property = Property.forName(propertyName);

			return property.gt(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_GT_ALL) {
			Property property = Property.forName(propertyName);

			return property.gtAll(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_GT_SOME) {
			Property property = Property.forName(propertyName);

			return property.gtSome(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_IN) {
			Property property = Property.forName(propertyName);

			return property.in(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_LE) {
			Property property = Property.forName(propertyName);

			return property.le(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_LE_ALL) {
			Property property = Property.forName(propertyName);

			return property.leAll(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_LE_SOME) {
			Property property = Property.forName(propertyName);

			return property.leSome(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_LT) {
			Property property = Property.forName(propertyName);

			return property.lt(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_LT_ALL) {
			Property property = Property.forName(propertyName);

			return property.ltAll(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_LT_SOME) {
			Property property = Property.forName(propertyName);

			return property.ltSome(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_NE) {
			Property property = Property.forName(propertyName);

			return property.ne(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}
		else if (criterionType == CriterionType.SUBQUERY_NOT_IN) {
			Property property = Property.forName(propertyName);

			return property.notIn(
				_buildDetachedCriteria((DynamicQueryImpl)values[0]));
		}

		throw new IllegalStateException(
			"Unexpected criterion type: " + criterionType);
	}

	private static org.hibernate.type.Type[] _toHibernateTypes(Type[] types) {
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

	private static org.hibernate.criterion.Projection _toProjection(
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
					projectionList.add(_toProjection(entry.getKey()), alias);
				}
				else {
					projectionList.add(_toProjection(entry.getKey()));
				}
			}

			return projectionList;
		}

		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		ProjectionType projectionType = projectionImpl.getProjectionType();
		String propertyName = projectionImpl.getPropertyName();

		if (projectionType == ProjectionType.ALIAS) {
			return Projections.alias(
				_toProjection(projectionImpl.getProjection()),
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
				_toProjection(projectionImpl.getProjection()));
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
				projectionImpl.getSQL(), projectionImpl.getGroupBy(),
				projectionImpl.getColumnAliases(),
				_toHibernateTypes(projectionImpl.getTypes()));
		}
		else if (projectionType == ProjectionType.SQL_PROJECTION) {
			return Projections.sqlProjection(
				projectionImpl.getSQL(), projectionImpl.getColumnAliases(),
				_toHibernateTypes(projectionImpl.getTypes()));
		}
		else if (projectionType == ProjectionType.SUM) {
			return Projections.sum(propertyName);
		}

		throw new IllegalStateException(
			"Unexpected projection type: " + projectionType);
	}

}