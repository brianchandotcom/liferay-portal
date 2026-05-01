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
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicQueryImpl implements DynamicQuery {

	public DynamicQueryImpl(String alias, Class<?> clazz) {
		_alias = alias;
		_clazz = clazz;
	}

	@Override
	public DynamicQuery add(Criterion criterion) {
		_criterions.add(criterion);

		return this;
	}

	@Override
	public DynamicQuery addOrder(Order order) {
		_orders.add(order);

		return this;
	}

	@Override
	public void compile(Session session) {
		_criteria = HibernateCriteriaUtil.buildCriteria(
			this, (org.hibernate.Session)session.getWrappedSession());

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

	public String getAlias() {
		return _alias;
	}

	public Class<?> getClazz() {
		return _clazz;
	}

	public List<Criterion> getCriterions() {
		return _criterions;
	}

	public List<Order> getOrders() {
		return _orders;
	}

	public Projection getProjection() {
		return _projection;
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
		_projection = projection;

		return this;
	}

	@Override
	public String toString() {
		if (_criteria != null) {
			return _criteria.toString();
		}

		return super.toString();
	}

	private final String _alias;
	private final Class<?> _clazz;
	private Criteria _criteria;
	private final List<Criterion> _criterions = new ArrayList<>();
	private Integer _end;
	private final List<Order> _orders = new ArrayList<>();
	private Projection _projection;
	private boolean _requiresProcessing = true;
	private Integer _start;

}