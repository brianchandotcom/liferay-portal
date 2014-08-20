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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicQueryImpl implements DynamicQuery {

	public DynamicQueryImpl(DetachedCriteria detachedCriteria) {
		_detachedCriteria = detachedCriteria;
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

		_detachedCriteria.addOrder(orderImpl.getWrappedOrder());

		return this;
	}

	@Override
	public void compile(Session session) {
		org.hibernate.Session hibernateSession =
			(org.hibernate.Session)session.getWrappedSession();

		_criteria = _detachedCriteria.getExecutableCriteria(hibernateSession);

		if ((_start == null) || (_end == null)) {
			return;
		}

		int start = _start.intValue();
		int end = _end.intValue();

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)) {
			return;
		}

		_criteria = _criteria.setFirstResult(start);
		_criteria = _criteria.setMaxResults(end - start);
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
		List list = _criteria.list();

		if (unmodifiable) {
			return Collections.unmodifiableList(list);
		}
		else {
			return ListUtil.copy(list);
		}
	}

	@Override
	public void setLimit(int start, int end) {
		_start = Integer.valueOf(start);
		_end = Integer.valueOf(end);
	}

	@Override
	public DynamicQuery setProjection(Projection projection) {
		return setProjection(projection, true);
	}

	@Override
	public DynamicQuery setProjection(
		Projection projection, boolean useColumnAlias) {

		if (!useColumnAlias) {
			projection = ProjectionFactoryUtil.sqlProjection(
				_detachedCriteria.getAlias() + "_." + projection.toString(),
				null, null);
		}

		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		_detachedCriteria.setProjection(projectionImpl.getWrappedProjection());

		return this;
	}

	@Override
	public String toString() {
		if (_criteria != null) {
			return _criteria.toString();
		}

		return super.toString();
	}

	private Criteria _criteria;
	private final DetachedCriteria _detachedCriteria;
	private Integer _end;
	private Integer _start;

}