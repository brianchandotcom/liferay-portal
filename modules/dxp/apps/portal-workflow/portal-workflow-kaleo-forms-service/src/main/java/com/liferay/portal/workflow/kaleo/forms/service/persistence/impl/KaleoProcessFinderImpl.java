/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.service.persistence.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessImpl;
import com.liferay.portal.workflow.kaleo.forms.service.persistence.KaleoProcessFinder;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implements custom finders for Kaleo processes.
 *
 * @author Inácio Nery
 */
@Component(service = KaleoProcessFinder.class)
public class KaleoProcessFinderImpl
	extends KaleoProcessFinderBaseImpl implements KaleoProcessFinder {

	public static final String COUNT_BY_G_N_D =
		KaleoProcessFinder.class.getName() + ".countByG_N_D";

	public static final String FIND_BY_G_N_D =
		KaleoProcessFinder.class.getName() + ".findByG_N_D";

	/**
	 * Returns the number of Kaleo processes in a group, matching the String
	 * value of the keywords parameter to the processes' titles or descriptions.
	 *
	 * @param  groupId the primary key of the Kaleo process's group
	 * @param  keywords the keywords (space separated) to match in the Kaleo
	 *         process name or description
	 * @return the number of matching Kaleo processes
	 */
	@Override
	public int countByKeywords(long groupId, String keywords) {
		return doCountByKeywords(groupId, keywords, false);
	}

	/**
	 * Returns the number of Kaleo processes in the group, matching the name
	 * and/or description parameters. Whether the name and description should
	 * both match depends on the <code>andOperator</code> parameter.
	 *
	 * @param  groupId the primary key of the Kaleo process's group
	 * @param  name the name to match in the Kaleo process name
	 * @param  description the description to match in the Kaleo process
	 *         description
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @return the number of matching Kaleo processes
	 */
	@Override
	public int countByG_N_D(
		long groupId, String name, String description, boolean andOperator) {

		String[] names = _customSQL.keywords(name);
		String[] descriptions = _customSQL.keywords(description, false);

		return doCountByG_N_D(groupId, names, descriptions, andOperator, false);
	}

	/**
	 * Returns the number of Kaleo processes in the group matching the keywords
	 * parameter. The keywords parameter is for matching String values to the
	 * Kaleo processes' names or descriptions. Only Kaleo processes the user has
	 * permission to view are counted.
	 *
	 * @param  groupId the primary key of the Kaleo processes' group
	 * @param  keywords the keywords (space separated) to match with each Kaleo
	 *         process's name or description
	 * @return the number of matching Kaleo processes
	 */
	@Override
	public int filterCountByKeywords(long groupId, String keywords) {
		return doCountByKeywords(groupId, keywords, true);
	}

	/**
	 * Returns the number of Kaleo processes in the group, matching the name or
	 * description parameters. Whether the name and description should both
	 * match depends on the <code>andOperator</code> parameter. Only Kaleo
	 * processes the user has permission to view are counted.
	 *
	 * @param  groupId the primary key of the Kaleo processes' group
	 * @param  name the name to match in the Kaleo process name
	 * @param  description the description to match in the Kaleo process
	 *         description
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @return the number of matching Kaleo processes
	 */
	@Override
	public int filterCountByG_N_D(
		long groupId, String name, String description, boolean andOperator) {

		String[] names = _customSQL.keywords(name);
		String[] descriptions = _customSQL.keywords(description, false);

		return doCountByG_N_D(groupId, names, descriptions, andOperator, true);
	}

	/**
	 * Returns an ordered range of all Kaleo processes in the group whose name
	 * or description matches the String values of the keywords parameter. Only
	 * Kaleo processes the user has permission to view are returned.
	 *
	 * @param  groupId the primary key of the Kaleo processes' group
	 * @param  keywords the keywords (space separated) to match in each Kaleo
	 *         process's name or description
	 * @param  start the lower bound of the range of Kaleo processes to return
	 * @param  end the upper bound of the range of Kaleo processes to return
	 *         (not inclusive)
	 * @param  orderByComparator the comparator to order the Kaleo processes
	 * @return the range of matching Kaleo processes ordered by the comparator
	 */
	@Override
	public List<KaleoProcess> filterFindByKeywords(
		long groupId, String keywords, int start, int end,
		OrderByComparator<KaleoProcess> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = _customSQL.keywords(keywords);
			descriptions = _customSQL.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return filterFindByG_N_D(
			groupId, names, descriptions, andOperator, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all Kaleo processes in the group, matching
	 * the Kaleo process's name and/or description. Whether the name and
	 * description should both match depends on the <code>andOperator</code>
	 * parameter. Only Kaleo processes the user has permission to view are
	 * returned.
	 *
	 * @param  groupId the primary key of the Kaleo process's group
	 * @param  name the name to match in the Kaleo process name
	 * @param  description the description to match in the Kaleo process
	 *         description
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @param  start the lower bound of the range of Kaleo processes to return
	 * @param  end the upper bound of the range of Kaleo processes to return
	 *         (not inclusive)
	 * @param  orderByComparator the comparator to order the Kaleo processes
	 * @return the range of matching Kaleo processes ordered by the comparator
	 */
	@Override
	public List<KaleoProcess> filterFindByG_N_D(
		long groupId, String name, String description, boolean andOperator,
		int start, int end, OrderByComparator<KaleoProcess> orderByComparator) {

		String[] names = _customSQL.keywords(name);
		String[] descriptions = _customSQL.keywords(description, false);

		return filterFindByG_N_D(
			groupId, names, descriptions, andOperator, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all Kaleo processes matching the name and/or
	 * description parameter. Whether the name and description should both match
	 * depends on the <code>andOperator</code> parameter. Only Kaleo processes
	 * the user has permission to view are returned.
	 *
	 * @param  groupId the primary key of the Kaleo process's group
	 * @param  names the names to match in the Kaleo process name
	 * @param  descriptions the descriptions to match in the Kaleo process
	 *         description
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @param  start the lower bound of the range of Kaleo processes to return
	 * @param  end the upper bound of the range of Kaleo processes to return
	 *         (not inclusive)
	 * @param  orderByComparator the comparator to order the Kaleo processes
	 * @return the range of matching Kaleo processes ordered by the comparator
	 */
	@Override
	public List<KaleoProcess> filterFindByG_N_D(
		long groupId, String[] names, String[] descriptions,
		boolean andOperator, int start, int end,
		OrderByComparator<KaleoProcess> orderByComparator) {

		return doFindByG_N_D(
			groupId, names, descriptions, andOperator, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all Kaleo processes in the group matching the
	 * parameters. The keywords parameter is used for matching String values to
	 * the Kaleo processes' names or descriptions.
	 *
	 * @param  groupId the primary key of the Kaleo processes' group
	 * @param  keywords the keywords (space separated) to match in each Kaleo
	 *         process's name or description
	 * @param  start the lower bound of the range of Kaleo processes to return
	 * @param  end the upper bound of the range of Kaleo processes to return
	 *         (not inclusive)
	 * @param  orderByComparator the comparator to order the Kaleo processes
	 * @return the range of matching Kaleo processes ordered by the comparator
	 */
	@Override
	public List<KaleoProcess> findByKeywords(
		long groupId, String keywords, int start, int end,
		OrderByComparator<KaleoProcess> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = _customSQL.keywords(keywords);
			descriptions = _customSQL.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return findByG_N_D(
			groupId, names, descriptions, andOperator, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all Kaleo processes in the group, matching
	 * the name and/or description parameter. Whether the name and description
	 * should both match depends on the <code>andOperator</code> parameter.
	 *
	 * @param  groupId the primary key of the Kaleo process's group
	 * @param  name the name to match in the Kaleo process name
	 * @param  description the description match in the Kaleo process
	 *         description
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @param  start the lower bound of the range of Kaleo processes to return
	 * @param  end the upper bound of the range of Kaleo processes to return
	 *         (not inclusive)
	 * @param  orderByComparator the comparator to order the Kaleo processes
	 * @return the range of matching Kaleo processes ordered by the comparator
	 */
	@Override
	public List<KaleoProcess> findByG_N_D(
		long groupId, String name, String description, boolean andOperator,
		int start, int end, OrderByComparator<KaleoProcess> orderByComparator) {

		String[] names = _customSQL.keywords(name);
		String[] descriptions = _customSQL.keywords(description, false);

		return findByG_N_D(
			groupId, names, descriptions, andOperator, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all Kaleo processes in the group, matching
	 * the names and/or descriptions. Whether the name and description should
	 * both match depends on the <code>andOperator</code> parameter.
	 *
	 * @param  groupId the primary key of the Kaleo process's group
	 * @param  names the names to match in the Kaleo process name
	 * @param  descriptions the descriptions to match in the Kaleo process
	 *         description
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @param  start the lower bound of the range of Kaleo processes to return
	 * @param  end the upper bound of the range of Kaleo processes to return
	 *         (not inclusive)
	 * @param  orderByComparator the comparator to order the Kaleo processes
	 * @return the range of matching Kaleo processes ordered by the comparator
	 */
	@Override
	public List<KaleoProcess> findByG_N_D(
		long groupId, String[] names, String[] descriptions,
		boolean andOperator, int start, int end,
		OrderByComparator<KaleoProcess> orderByComparator) {

		return doFindByG_N_D(
			groupId, names, descriptions, andOperator, start, end,
			orderByComparator, false);
	}

	/**
	 * Returns the number of Kaleo processes in the group matching the
	 * parameters, including a keywords parameter for matching String values to
	 * the Kaleo processes' names or descriptions.
	 *
	 * @param  groupId the primary key of the Kaleo process's group
	 * @param  keywords the keywords (space separated) to match in the Kaleo
	 *         process name or description
	 * @param  inlineSQLHelper whether to add a query criterion that checks if
	 *         the user has view permissions for the Kaleo processes
	 * @return the number of matching Kaleo processes
	 */
	protected int doCountByKeywords(
		long groupId, String keywords, boolean inlineSQLHelper) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = true;

		if (Validator.isNotNull(keywords)) {
			names = _customSQL.keywords(keywords);
			descriptions = _customSQL.keywords(keywords, false);
			andOperator = false;
		}

		return doCountByG_N_D(
			groupId, names, descriptions, andOperator, inlineSQLHelper);
	}

	/**
	 * Returns the number of Kaleo processes in the group, matching the names or
	 * descriptions. Whether the name and description should both match depends
	 * on the <code>andOperator</code> parameter.
	 *
	 * @param  groupId the primary key of the Kaleo process's group
	 * @param  names the names to match in the Kaleo process name
	 * @param  descriptions the descriptions to match in the Kaleo process
	 *         description
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @param  inlineSQLHelper whether to add a query criterion that checks if
	 *         the user has view permissions for the Kaleo processes
	 * @return the number of matching Kaleo processes
	 */
	protected int doCountByG_N_D(
		long groupId, String[] names, String[] descriptions,
		boolean andOperator, boolean inlineSQLHelper) {

		names = _customSQL.keywords(names);
		descriptions = _customSQL.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_N_D);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, KaleoProcess.class.getName(),
					"KaleoProcess.kaleoProcessId", groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(KaleoProcess.groupId = ?) AND");
			}

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(DDLRecordSet.name)", StringPool.LIKE, false, names);
			sql = _customSQL.replaceKeywords(
				sql, "DDLRecordSet.description", StringPool.LIKE, true,
				descriptions);
			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			qPos.add(names, 2);
			qPos.add(descriptions, 2);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns an ordered range of all Kaleo processes in the group, matching
	 * the names or descriptions. Whether the name and description should both
	 * match depends on the <code>andOperator</code> parameter.
	 *
	 * @param  groupId the primary key of the Kaleo process's group
	 * @param  names the names to match in the Kaleo process name
	 * @param  descriptions the descriptions to match in the Kaleo process
	 *         description
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @param  start the lower bound of the range of Kaleo processes to return
	 * @param  end the upper bound of the range of Kaleo processes to return
	 *         (not inclusive)
	 * @param  orderByComparator the comparator to order the Kaleo processes
	 * @param  inlineSQLHelper whether to add a query criterion that checks if
	 *         the user has view permissions for the Kaleo processes
	 * @return the range of matching Kaleo processes ordered by the comparator
	 */
	protected List<KaleoProcess> doFindByG_N_D(
		long groupId, String[] names, String[] descriptions,
		boolean andOperator, int start, int end,
		OrderByComparator<KaleoProcess> orderByComparator,
		boolean inlineSQLHelper) {

		names = _customSQL.keywords(names);
		descriptions = _customSQL.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_N_D);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, KaleoProcess.class.getName(),
					"KaleoProcess.kaleoProcessId", groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(KaleoProcess.groupId = ?) AND");
			}

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(DDLRecordSet.name)", StringPool.LIKE, false, names);
			sql = _customSQL.replaceKeywords(
				sql, "DDLRecordSet.description", StringPool.LIKE, true,
				descriptions);
			sql = _customSQL.replaceAndOperator(sql, andOperator);
			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("KaleoProcess", KaleoProcessImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			qPos.add(names, 2);
			qPos.add(descriptions, 2);

			return (List<KaleoProcess>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}