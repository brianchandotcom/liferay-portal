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

package com.liferay.portal.workflow.kaleo.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskFormTable;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskFormPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the kaleo task form service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {KaleoTaskFormPersistence.class, BasePersistence.class})
public class KaleoTaskFormPersistenceImpl
	extends BasePersistenceImpl<KaleoTaskForm>
	implements KaleoTaskFormPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoTaskFormUtil</code> to access the kaleo task form persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoTaskFormImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the kaleo task forms where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task forms where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @return the range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task forms where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task forms where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<KaleoTaskForm> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskForm>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskForm kaleoTaskForm : list) {
					if (companyId != kaleoTaskForm.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_KALEOTASKFORM_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskFormModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<KaleoTaskForm>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first kaleo task form in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form
	 * @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm findByCompanyId_First(
			long companyId, OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (kaleoTaskForm != null) {
			return kaleoTaskForm;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTaskFormException(sb.toString());
	}

	/**
	 * Returns the first kaleo task form in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm fetchByCompanyId_First(
		long companyId, OrderByComparator<KaleoTaskForm> orderByComparator) {

		List<KaleoTaskForm> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task form in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form
	 * @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm findByCompanyId_Last(
			long companyId, OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (kaleoTaskForm != null) {
			return kaleoTaskForm;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTaskFormException(sb.toString());
	}

	/**
	 * Returns the last kaleo task form in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm fetchByCompanyId_Last(
		long companyId, OrderByComparator<KaleoTaskForm> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskForm> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task forms before and after the current kaleo task form in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoTaskFormId the primary key of the current kaleo task form
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task form
	 * @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	 */
	@Override
	public KaleoTaskForm[] findByCompanyId_PrevAndNext(
			long kaleoTaskFormId, long companyId,
			OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = findByPrimaryKey(kaleoTaskFormId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskForm[] array = new KaleoTaskFormImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, kaleoTaskForm, companyId, orderByComparator, true);

			array[1] = kaleoTaskForm;

			array[2] = getByCompanyId_PrevAndNext(
				session, kaleoTaskForm, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoTaskForm getByCompanyId_PrevAndNext(
		Session session, KaleoTaskForm kaleoTaskForm, long companyId,
		OrderByComparator<KaleoTaskForm> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEOTASKFORM_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(KaleoTaskFormModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskForm)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskForm> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task forms where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoTaskForm kaleoTaskForm :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskForm);
		}
	}

	/**
	 * Returns the number of kaleo task forms where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo task forms
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKFORM_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"kaleoTaskForm.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoDefinitionVersionId;
	private FinderPath
		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
	private FinderPath _finderPathCountByKaleoDefinitionVersionId;

	/**
	 * Returns all the kaleo task forms where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo task forms where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @return the range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task forms where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task forms where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
				finderArgs = new Object[] {kaleoDefinitionVersionId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByKaleoDefinitionVersionId;
			finderArgs = new Object[] {
				kaleoDefinitionVersionId, start, end, orderByComparator
			};
		}

		List<KaleoTaskForm> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskForm>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskForm kaleoTaskForm : list) {
					if (kaleoDefinitionVersionId !=
							kaleoTaskForm.getKaleoDefinitionVersionId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_KALEOTASKFORM_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskFormModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				list = (List<KaleoTaskForm>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first kaleo task form in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form
	 * @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = fetchByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);

		if (kaleoTaskForm != null) {
			return kaleoTaskForm;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchTaskFormException(sb.toString());
	}

	/**
	 * Returns the first kaleo task form in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTaskForm> orderByComparator) {

		List<KaleoTaskForm> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task form in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form
	 * @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = fetchByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);

		if (kaleoTaskForm != null) {
			return kaleoTaskForm;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchTaskFormException(sb.toString());
	}

	/**
	 * Returns the last kaleo task form in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTaskForm> orderByComparator) {

		int count = countByKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskForm> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task forms before and after the current kaleo task form in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoTaskFormId the primary key of the current kaleo task form
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task form
	 * @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	 */
	@Override
	public KaleoTaskForm[] findByKaleoDefinitionVersionId_PrevAndNext(
			long kaleoTaskFormId, long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = findByPrimaryKey(kaleoTaskFormId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskForm[] array = new KaleoTaskFormImpl[3];

			array[0] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoTaskForm, kaleoDefinitionVersionId,
				orderByComparator, true);

			array[1] = kaleoTaskForm;

			array[2] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoTaskForm, kaleoDefinitionVersionId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoTaskForm getByKaleoDefinitionVersionId_PrevAndNext(
		Session session, KaleoTaskForm kaleoTaskForm,
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTaskForm> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEOTASKFORM_WHERE);

		sb.append(
			_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(KaleoTaskFormModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoDefinitionVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskForm)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskForm> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task forms where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		for (KaleoTaskForm kaleoTaskForm :
				findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskForm);
		}
	}

	/**
	 * Returns the number of kaleo task forms where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo task forms
	 */
	@Override
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		FinderPath finderPath = _finderPathCountByKaleoDefinitionVersionId;

		Object[] finderArgs = new Object[] {kaleoDefinitionVersionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKFORM_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2 =
			"kaleoTaskForm.kaleoDefinitionVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoNodeId;
	private FinderPath _finderPathWithoutPaginationFindByKaleoNodeId;
	private FinderPath _finderPathCountByKaleoNodeId;

	/**
	 * Returns all the kaleo task forms where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoNodeId(long kaleoNodeId) {
		return findByKaleoNodeId(
			kaleoNodeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task forms where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @return the range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoNodeId(
		long kaleoNodeId, int start, int end) {

		return findByKaleoNodeId(kaleoNodeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task forms where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoNodeId(
		long kaleoNodeId, int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator) {

		return findByKaleoNodeId(
			kaleoNodeId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task forms where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoNodeId(
		long kaleoNodeId, int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByKaleoNodeId;
				finderArgs = new Object[] {kaleoNodeId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKaleoNodeId;
			finderArgs = new Object[] {
				kaleoNodeId, start, end, orderByComparator
			};
		}

		List<KaleoTaskForm> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskForm>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskForm kaleoTaskForm : list) {
					if (kaleoNodeId != kaleoTaskForm.getKaleoNodeId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_KALEOTASKFORM_WHERE);

			sb.append(_FINDER_COLUMN_KALEONODEID_KALEONODEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskFormModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoNodeId);

				list = (List<KaleoTaskForm>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first kaleo task form in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form
	 * @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm findByKaleoNodeId_First(
			long kaleoNodeId,
			OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = fetchByKaleoNodeId_First(
			kaleoNodeId, orderByComparator);

		if (kaleoTaskForm != null) {
			return kaleoTaskForm;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoNodeId=");
		sb.append(kaleoNodeId);

		sb.append("}");

		throw new NoSuchTaskFormException(sb.toString());
	}

	/**
	 * Returns the first kaleo task form in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm fetchByKaleoNodeId_First(
		long kaleoNodeId, OrderByComparator<KaleoTaskForm> orderByComparator) {

		List<KaleoTaskForm> list = findByKaleoNodeId(
			kaleoNodeId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task form in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form
	 * @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm findByKaleoNodeId_Last(
			long kaleoNodeId,
			OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = fetchByKaleoNodeId_Last(
			kaleoNodeId, orderByComparator);

		if (kaleoTaskForm != null) {
			return kaleoTaskForm;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoNodeId=");
		sb.append(kaleoNodeId);

		sb.append("}");

		throw new NoSuchTaskFormException(sb.toString());
	}

	/**
	 * Returns the last kaleo task form in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm fetchByKaleoNodeId_Last(
		long kaleoNodeId, OrderByComparator<KaleoTaskForm> orderByComparator) {

		int count = countByKaleoNodeId(kaleoNodeId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskForm> list = findByKaleoNodeId(
			kaleoNodeId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task forms before and after the current kaleo task form in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoTaskFormId the primary key of the current kaleo task form
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task form
	 * @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	 */
	@Override
	public KaleoTaskForm[] findByKaleoNodeId_PrevAndNext(
			long kaleoTaskFormId, long kaleoNodeId,
			OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = findByPrimaryKey(kaleoTaskFormId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskForm[] array = new KaleoTaskFormImpl[3];

			array[0] = getByKaleoNodeId_PrevAndNext(
				session, kaleoTaskForm, kaleoNodeId, orderByComparator, true);

			array[1] = kaleoTaskForm;

			array[2] = getByKaleoNodeId_PrevAndNext(
				session, kaleoTaskForm, kaleoNodeId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoTaskForm getByKaleoNodeId_PrevAndNext(
		Session session, KaleoTaskForm kaleoTaskForm, long kaleoNodeId,
		OrderByComparator<KaleoTaskForm> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEOTASKFORM_WHERE);

		sb.append(_FINDER_COLUMN_KALEONODEID_KALEONODEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(KaleoTaskFormModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoNodeId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskForm)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskForm> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task forms where kaleoNodeId = &#63; from the database.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 */
	@Override
	public void removeByKaleoNodeId(long kaleoNodeId) {
		for (KaleoTaskForm kaleoTaskForm :
				findByKaleoNodeId(
					kaleoNodeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskForm);
		}
	}

	/**
	 * Returns the number of kaleo task forms where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the number of matching kaleo task forms
	 */
	@Override
	public int countByKaleoNodeId(long kaleoNodeId) {
		FinderPath finderPath = _finderPathCountByKaleoNodeId;

		Object[] finderArgs = new Object[] {kaleoNodeId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKFORM_WHERE);

			sb.append(_FINDER_COLUMN_KALEONODEID_KALEONODEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoNodeId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_KALEONODEID_KALEONODEID_2 =
		"kaleoTaskForm.kaleoNodeId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoTaskId;
	private FinderPath _finderPathWithoutPaginationFindByKaleoTaskId;
	private FinderPath _finderPathCountByKaleoTaskId;

	/**
	 * Returns all the kaleo task forms where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @return the matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoTaskId(long kaleoTaskId) {
		return findByKaleoTaskId(
			kaleoTaskId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task forms where kaleoTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @return the range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoTaskId(
		long kaleoTaskId, int start, int end) {

		return findByKaleoTaskId(kaleoTaskId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task forms where kaleoTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoTaskId(
		long kaleoTaskId, int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator) {

		return findByKaleoTaskId(
			kaleoTaskId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task forms where kaleoTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findByKaleoTaskId(
		long kaleoTaskId, int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByKaleoTaskId;
				finderArgs = new Object[] {kaleoTaskId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKaleoTaskId;
			finderArgs = new Object[] {
				kaleoTaskId, start, end, orderByComparator
			};
		}

		List<KaleoTaskForm> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskForm>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskForm kaleoTaskForm : list) {
					if (kaleoTaskId != kaleoTaskForm.getKaleoTaskId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_KALEOTASKFORM_WHERE);

			sb.append(_FINDER_COLUMN_KALEOTASKID_KALEOTASKID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskFormModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoTaskId);

				list = (List<KaleoTaskForm>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first kaleo task form in the ordered set where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form
	 * @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm findByKaleoTaskId_First(
			long kaleoTaskId,
			OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = fetchByKaleoTaskId_First(
			kaleoTaskId, orderByComparator);

		if (kaleoTaskForm != null) {
			return kaleoTaskForm;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoTaskId=");
		sb.append(kaleoTaskId);

		sb.append("}");

		throw new NoSuchTaskFormException(sb.toString());
	}

	/**
	 * Returns the first kaleo task form in the ordered set where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm fetchByKaleoTaskId_First(
		long kaleoTaskId, OrderByComparator<KaleoTaskForm> orderByComparator) {

		List<KaleoTaskForm> list = findByKaleoTaskId(
			kaleoTaskId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task form in the ordered set where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form
	 * @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm findByKaleoTaskId_Last(
			long kaleoTaskId,
			OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = fetchByKaleoTaskId_Last(
			kaleoTaskId, orderByComparator);

		if (kaleoTaskForm != null) {
			return kaleoTaskForm;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoTaskId=");
		sb.append(kaleoTaskId);

		sb.append("}");

		throw new NoSuchTaskFormException(sb.toString());
	}

	/**
	 * Returns the last kaleo task form in the ordered set where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm fetchByKaleoTaskId_Last(
		long kaleoTaskId, OrderByComparator<KaleoTaskForm> orderByComparator) {

		int count = countByKaleoTaskId(kaleoTaskId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskForm> list = findByKaleoTaskId(
			kaleoTaskId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task forms before and after the current kaleo task form in the ordered set where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskFormId the primary key of the current kaleo task form
	 * @param kaleoTaskId the kaleo task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task form
	 * @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	 */
	@Override
	public KaleoTaskForm[] findByKaleoTaskId_PrevAndNext(
			long kaleoTaskFormId, long kaleoTaskId,
			OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = findByPrimaryKey(kaleoTaskFormId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskForm[] array = new KaleoTaskFormImpl[3];

			array[0] = getByKaleoTaskId_PrevAndNext(
				session, kaleoTaskForm, kaleoTaskId, orderByComparator, true);

			array[1] = kaleoTaskForm;

			array[2] = getByKaleoTaskId_PrevAndNext(
				session, kaleoTaskForm, kaleoTaskId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoTaskForm getByKaleoTaskId_PrevAndNext(
		Session session, KaleoTaskForm kaleoTaskForm, long kaleoTaskId,
		OrderByComparator<KaleoTaskForm> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEOTASKFORM_WHERE);

		sb.append(_FINDER_COLUMN_KALEOTASKID_KALEOTASKID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(KaleoTaskFormModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoTaskId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskForm)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskForm> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task forms where kaleoTaskId = &#63; from the database.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 */
	@Override
	public void removeByKaleoTaskId(long kaleoTaskId) {
		for (KaleoTaskForm kaleoTaskForm :
				findByKaleoTaskId(
					kaleoTaskId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskForm);
		}
	}

	/**
	 * Returns the number of kaleo task forms where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @return the number of matching kaleo task forms
	 */
	@Override
	public int countByKaleoTaskId(long kaleoTaskId) {
		FinderPath finderPath = _finderPathCountByKaleoTaskId;

		Object[] finderArgs = new Object[] {kaleoTaskId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKFORM_WHERE);

			sb.append(_FINDER_COLUMN_KALEOTASKID_KALEOTASKID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoTaskId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_KALEOTASKID_KALEOTASKID_2 =
		"kaleoTaskForm.kaleoTaskId = ?";

	private FinderPath _finderPathFetchByFormUuid_KTI;
	private FinderPath _finderPathCountByFormUuid_KTI;

	/**
	 * Returns the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; or throws a <code>NoSuchTaskFormException</code> if it could not be found.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param formUuid the form uuid
	 * @return the matching kaleo task form
	 * @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm findByFormUuid_KTI(long kaleoTaskId, String formUuid)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = fetchByFormUuid_KTI(
			kaleoTaskId, formUuid);

		if (kaleoTaskForm == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("kaleoTaskId=");
			sb.append(kaleoTaskId);

			sb.append(", formUuid=");
			sb.append(formUuid);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTaskFormException(sb.toString());
		}

		return kaleoTaskForm;
	}

	/**
	 * Returns the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param formUuid the form uuid
	 * @return the matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm fetchByFormUuid_KTI(
		long kaleoTaskId, String formUuid) {

		return fetchByFormUuid_KTI(kaleoTaskId, formUuid, true);
	}

	/**
	 * Returns the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param formUuid the form uuid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	 */
	@Override
	public KaleoTaskForm fetchByFormUuid_KTI(
		long kaleoTaskId, String formUuid, boolean useFinderCache) {

		formUuid = Objects.toString(formUuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {kaleoTaskId, formUuid};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByFormUuid_KTI, finderArgs);
		}

		if (result instanceof KaleoTaskForm) {
			KaleoTaskForm kaleoTaskForm = (KaleoTaskForm)result;

			if ((kaleoTaskId != kaleoTaskForm.getKaleoTaskId()) ||
				!Objects.equals(formUuid, kaleoTaskForm.getFormUuid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_KALEOTASKFORM_WHERE);

			sb.append(_FINDER_COLUMN_FORMUUID_KTI_KALEOTASKID_2);

			boolean bindFormUuid = false;

			if (formUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_FORMUUID_KTI_FORMUUID_3);
			}
			else {
				bindFormUuid = true;

				sb.append(_FINDER_COLUMN_FORMUUID_KTI_FORMUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoTaskId);

				if (bindFormUuid) {
					queryPos.add(formUuid);
				}

				List<KaleoTaskForm> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByFormUuid_KTI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									kaleoTaskId, formUuid
								};
							}

							_log.warn(
								"KaleoTaskFormPersistenceImpl.fetchByFormUuid_KTI(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					KaleoTaskForm kaleoTaskForm = list.get(0);

					result = kaleoTaskForm;

					cacheResult(kaleoTaskForm);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (KaleoTaskForm)result;
		}
	}

	/**
	 * Removes the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; from the database.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param formUuid the form uuid
	 * @return the kaleo task form that was removed
	 */
	@Override
	public KaleoTaskForm removeByFormUuid_KTI(long kaleoTaskId, String formUuid)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = findByFormUuid_KTI(kaleoTaskId, formUuid);

		return remove(kaleoTaskForm);
	}

	/**
	 * Returns the number of kaleo task forms where kaleoTaskId = &#63; and formUuid = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param formUuid the form uuid
	 * @return the number of matching kaleo task forms
	 */
	@Override
	public int countByFormUuid_KTI(long kaleoTaskId, String formUuid) {
		formUuid = Objects.toString(formUuid, "");

		FinderPath finderPath = _finderPathCountByFormUuid_KTI;

		Object[] finderArgs = new Object[] {kaleoTaskId, formUuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOTASKFORM_WHERE);

			sb.append(_FINDER_COLUMN_FORMUUID_KTI_KALEOTASKID_2);

			boolean bindFormUuid = false;

			if (formUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_FORMUUID_KTI_FORMUUID_3);
			}
			else {
				bindFormUuid = true;

				sb.append(_FINDER_COLUMN_FORMUUID_KTI_FORMUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoTaskId);

				if (bindFormUuid) {
					queryPos.add(formUuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_FORMUUID_KTI_KALEOTASKID_2 =
		"kaleoTaskForm.kaleoTaskId = ? AND ";

	private static final String _FINDER_COLUMN_FORMUUID_KTI_FORMUUID_2 =
		"kaleoTaskForm.formUuid = ?";

	private static final String _FINDER_COLUMN_FORMUUID_KTI_FORMUUID_3 =
		"(kaleoTaskForm.formUuid IS NULL OR kaleoTaskForm.formUuid = '')";

	public KaleoTaskFormPersistenceImpl() {
		setModelClass(KaleoTaskForm.class);

		setModelImplClass(KaleoTaskFormImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoTaskFormTable.INSTANCE);
	}

	/**
	 * Caches the kaleo task form in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskForm the kaleo task form
	 */
	@Override
	public void cacheResult(KaleoTaskForm kaleoTaskForm) {
		entityCache.putResult(
			KaleoTaskFormImpl.class, kaleoTaskForm.getPrimaryKey(),
			kaleoTaskForm);

		finderCache.putResult(
			_finderPathFetchByFormUuid_KTI,
			new Object[] {
				kaleoTaskForm.getKaleoTaskId(), kaleoTaskForm.getFormUuid()
			},
			kaleoTaskForm);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo task forms in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskForms the kaleo task forms
	 */
	@Override
	public void cacheResult(List<KaleoTaskForm> kaleoTaskForms) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoTaskForms.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoTaskForm kaleoTaskForm : kaleoTaskForms) {
			if (entityCache.getResult(
					KaleoTaskFormImpl.class, kaleoTaskForm.getPrimaryKey()) ==
						null) {

				cacheResult(kaleoTaskForm);
			}
		}
	}

	/**
	 * Clears the cache for all kaleo task forms.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoTaskFormImpl.class);

		finderCache.clearCache(KaleoTaskFormImpl.class);
	}

	/**
	 * Clears the cache for the kaleo task form.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoTaskForm kaleoTaskForm) {
		entityCache.removeResult(KaleoTaskFormImpl.class, kaleoTaskForm);
	}

	@Override
	public void clearCache(List<KaleoTaskForm> kaleoTaskForms) {
		for (KaleoTaskForm kaleoTaskForm : kaleoTaskForms) {
			entityCache.removeResult(KaleoTaskFormImpl.class, kaleoTaskForm);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KaleoTaskFormImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(KaleoTaskFormImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		KaleoTaskFormModelImpl kaleoTaskFormModelImpl) {

		Object[] args = new Object[] {
			kaleoTaskFormModelImpl.getKaleoTaskId(),
			kaleoTaskFormModelImpl.getFormUuid()
		};

		finderCache.putResult(
			_finderPathCountByFormUuid_KTI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByFormUuid_KTI, args, kaleoTaskFormModelImpl);
	}

	/**
	 * Creates a new kaleo task form with the primary key. Does not add the kaleo task form to the database.
	 *
	 * @param kaleoTaskFormId the primary key for the new kaleo task form
	 * @return the new kaleo task form
	 */
	@Override
	public KaleoTaskForm create(long kaleoTaskFormId) {
		KaleoTaskForm kaleoTaskForm = new KaleoTaskFormImpl();

		kaleoTaskForm.setNew(true);
		kaleoTaskForm.setPrimaryKey(kaleoTaskFormId);

		kaleoTaskForm.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kaleoTaskForm;
	}

	/**
	 * Removes the kaleo task form with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskFormId the primary key of the kaleo task form
	 * @return the kaleo task form that was removed
	 * @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	 */
	@Override
	public KaleoTaskForm remove(long kaleoTaskFormId)
		throws NoSuchTaskFormException {

		return remove((Serializable)kaleoTaskFormId);
	}

	/**
	 * Removes the kaleo task form with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo task form
	 * @return the kaleo task form that was removed
	 * @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	 */
	@Override
	public KaleoTaskForm remove(Serializable primaryKey)
		throws NoSuchTaskFormException {

		Session session = null;

		try {
			session = openSession();

			KaleoTaskForm kaleoTaskForm = (KaleoTaskForm)session.get(
				KaleoTaskFormImpl.class, primaryKey);

			if (kaleoTaskForm == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaskFormException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoTaskForm);
		}
		catch (NoSuchTaskFormException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected KaleoTaskForm removeImpl(KaleoTaskForm kaleoTaskForm) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoTaskForm)) {
				kaleoTaskForm = (KaleoTaskForm)session.get(
					KaleoTaskFormImpl.class, kaleoTaskForm.getPrimaryKeyObj());
			}

			if (kaleoTaskForm != null) {
				session.delete(kaleoTaskForm);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoTaskForm != null) {
			clearCache(kaleoTaskForm);
		}

		return kaleoTaskForm;
	}

	@Override
	public KaleoTaskForm updateImpl(KaleoTaskForm kaleoTaskForm) {
		boolean isNew = kaleoTaskForm.isNew();

		if (!(kaleoTaskForm instanceof KaleoTaskFormModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoTaskForm.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					kaleoTaskForm);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoTaskForm proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoTaskForm implementation " +
					kaleoTaskForm.getClass());
		}

		KaleoTaskFormModelImpl kaleoTaskFormModelImpl =
			(KaleoTaskFormModelImpl)kaleoTaskForm;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (kaleoTaskForm.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoTaskForm.setCreateDate(date);
			}
			else {
				kaleoTaskForm.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!kaleoTaskFormModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoTaskForm.setModifiedDate(date);
			}
			else {
				kaleoTaskForm.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(kaleoTaskForm);
			}
			else {
				kaleoTaskForm = (KaleoTaskForm)session.merge(kaleoTaskForm);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			KaleoTaskFormImpl.class, kaleoTaskFormModelImpl, false, true);

		cacheUniqueFindersCache(kaleoTaskFormModelImpl);

		if (isNew) {
			kaleoTaskForm.setNew(false);
		}

		kaleoTaskForm.resetOriginalValues();

		return kaleoTaskForm;
	}

	/**
	 * Returns the kaleo task form with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo task form
	 * @return the kaleo task form
	 * @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	 */
	@Override
	public KaleoTaskForm findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaskFormException {

		KaleoTaskForm kaleoTaskForm = fetchByPrimaryKey(primaryKey);

		if (kaleoTaskForm == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaskFormException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoTaskForm;
	}

	/**
	 * Returns the kaleo task form with the primary key or throws a <code>NoSuchTaskFormException</code> if it could not be found.
	 *
	 * @param kaleoTaskFormId the primary key of the kaleo task form
	 * @return the kaleo task form
	 * @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	 */
	@Override
	public KaleoTaskForm findByPrimaryKey(long kaleoTaskFormId)
		throws NoSuchTaskFormException {

		return findByPrimaryKey((Serializable)kaleoTaskFormId);
	}

	/**
	 * Returns the kaleo task form with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoTaskFormId the primary key of the kaleo task form
	 * @return the kaleo task form, or <code>null</code> if a kaleo task form with the primary key could not be found
	 */
	@Override
	public KaleoTaskForm fetchByPrimaryKey(long kaleoTaskFormId) {
		return fetchByPrimaryKey((Serializable)kaleoTaskFormId);
	}

	/**
	 * Returns all the kaleo task forms.
	 *
	 * @return the kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task forms.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @return the range of kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task forms.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findAll(
		int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task forms.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo task forms
	 */
	@Override
	public List<KaleoTaskForm> findAll(
		int start, int end, OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<KaleoTaskForm> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskForm>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KALEOTASKFORM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KALEOTASKFORM;

				sql = sql.concat(KaleoTaskFormModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KaleoTaskForm>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the kaleo task forms from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoTaskForm kaleoTaskForm : findAll()) {
			remove(kaleoTaskForm);
		}
	}

	/**
	 * Returns the number of kaleo task forms.
	 *
	 * @return the number of kaleo task forms
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_KALEOTASKFORM);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "kaleoTaskFormId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEOTASKFORM;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoTaskFormModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo task form persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"kaleoDefinitionVersionId"}, true);

		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {Long.class.getName()},
				new String[] {"kaleoDefinitionVersionId"}, true);

		_finderPathCountByKaleoDefinitionVersionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoDefinitionVersionId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoDefinitionVersionId"}, false);

		_finderPathWithPaginationFindByKaleoNodeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKaleoNodeId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"kaleoNodeId"}, true);

		_finderPathWithoutPaginationFindByKaleoNodeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKaleoNodeId",
			new String[] {Long.class.getName()}, new String[] {"kaleoNodeId"},
			true);

		_finderPathCountByKaleoNodeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKaleoNodeId",
			new String[] {Long.class.getName()}, new String[] {"kaleoNodeId"},
			false);

		_finderPathWithPaginationFindByKaleoTaskId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKaleoTaskId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"kaleoTaskId"}, true);

		_finderPathWithoutPaginationFindByKaleoTaskId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKaleoTaskId",
			new String[] {Long.class.getName()}, new String[] {"kaleoTaskId"},
			true);

		_finderPathCountByKaleoTaskId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKaleoTaskId",
			new String[] {Long.class.getName()}, new String[] {"kaleoTaskId"},
			false);

		_finderPathFetchByFormUuid_KTI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByFormUuid_KTI",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"kaleoTaskId", "formUuid"}, true);

		_finderPathCountByFormUuid_KTI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFormUuid_KTI",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"kaleoTaskId", "formUuid"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(KaleoTaskFormImpl.class.getName());
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_KALEOTASKFORM =
		"SELECT kaleoTaskForm FROM KaleoTaskForm kaleoTaskForm";

	private static final String _SQL_SELECT_KALEOTASKFORM_WHERE =
		"SELECT kaleoTaskForm FROM KaleoTaskForm kaleoTaskForm WHERE ";

	private static final String _SQL_COUNT_KALEOTASKFORM =
		"SELECT COUNT(kaleoTaskForm) FROM KaleoTaskForm kaleoTaskForm";

	private static final String _SQL_COUNT_KALEOTASKFORM_WHERE =
		"SELECT COUNT(kaleoTaskForm) FROM KaleoTaskForm kaleoTaskForm WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoTaskForm.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoTaskForm exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoTaskForm exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTaskFormPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private KaleoTaskFormModelArgumentsResolver
		_kaleoTaskFormModelArgumentsResolver;

}