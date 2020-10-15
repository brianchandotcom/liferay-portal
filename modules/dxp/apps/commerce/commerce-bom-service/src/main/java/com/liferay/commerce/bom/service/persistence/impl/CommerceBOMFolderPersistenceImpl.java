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

package com.liferay.commerce.bom.service.persistence.impl;

import com.liferay.commerce.bom.exception.NoSuchBOMFolderException;
import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.model.CommerceBOMFolderTable;
import com.liferay.commerce.bom.model.impl.CommerceBOMFolderImpl;
import com.liferay.commerce.bom.model.impl.CommerceBOMFolderModelImpl;
import com.liferay.commerce.bom.service.persistence.CommerceBOMFolderPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce bom folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceBOMFolderPersistenceImpl
	extends BasePersistenceImpl<CommerceBOMFolder>
	implements CommerceBOMFolderPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceBOMFolderUtil</code> to access the commerce bom folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceBOMFolderImpl.class.getName();

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
	 * Returns all the commerce bom folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce bom folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @return the range of matching commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce bom folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceBOMFolder> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce bom folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceBOMFolder> orderByComparator,
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

		List<CommerceBOMFolder> list = null;

		if (useFinderCache) {
			list = (List<CommerceBOMFolder>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceBOMFolder commerceBOMFolder : list) {
					if (companyId != commerceBOMFolder.getCompanyId()) {
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

			sb.append(_SQL_SELECT_COMMERCEBOMFOLDER_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceBOMFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommerceBOMFolder>)QueryUtil.list(
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
	 * Returns the first commerce bom folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder
	 * @throws NoSuchBOMFolderException if a matching commerce bom folder could not be found
	 */
	@Override
	public CommerceBOMFolder findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceBOMFolder> orderByComparator)
		throws NoSuchBOMFolderException {

		CommerceBOMFolder commerceBOMFolder = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (commerceBOMFolder != null) {
			return commerceBOMFolder;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchBOMFolderException(sb.toString());
	}

	/**
	 * Returns the first commerce bom folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder, or <code>null</code> if a matching commerce bom folder could not be found
	 */
	@Override
	public CommerceBOMFolder fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceBOMFolder> orderByComparator) {

		List<CommerceBOMFolder> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce bom folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder
	 * @throws NoSuchBOMFolderException if a matching commerce bom folder could not be found
	 */
	@Override
	public CommerceBOMFolder findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceBOMFolder> orderByComparator)
		throws NoSuchBOMFolderException {

		CommerceBOMFolder commerceBOMFolder = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (commerceBOMFolder != null) {
			return commerceBOMFolder;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchBOMFolderException(sb.toString());
	}

	/**
	 * Returns the last commerce bom folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder, or <code>null</code> if a matching commerce bom folder could not be found
	 */
	@Override
	public CommerceBOMFolder fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceBOMFolder> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceBOMFolder> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce bom folders before and after the current commerce bom folder in the ordered set where companyId = &#63;.
	 *
	 * @param commerceBOMFolderId the primary key of the current commerce bom folder
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom folder
	 * @throws NoSuchBOMFolderException if a commerce bom folder with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolder[] findByCompanyId_PrevAndNext(
			long commerceBOMFolderId, long companyId,
			OrderByComparator<CommerceBOMFolder> orderByComparator)
		throws NoSuchBOMFolderException {

		CommerceBOMFolder commerceBOMFolder = findByPrimaryKey(
			commerceBOMFolderId);

		Session session = null;

		try {
			session = openSession();

			CommerceBOMFolder[] array = new CommerceBOMFolderImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commerceBOMFolder, companyId, orderByComparator, true);

			array[1] = commerceBOMFolder;

			array[2] = getByCompanyId_PrevAndNext(
				session, commerceBOMFolder, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceBOMFolder getByCompanyId_PrevAndNext(
		Session session, CommerceBOMFolder commerceBOMFolder, long companyId,
		OrderByComparator<CommerceBOMFolder> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMERCEBOMFOLDER_WHERE);

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
			sb.append(CommerceBOMFolderModelImpl.ORDER_BY_JPQL);
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
						commerceBOMFolder)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceBOMFolder> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce bom folders that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce bom folders that the user has permission to view
	 */
	@Override
	public List<CommerceBOMFolder> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce bom folders that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @return the range of matching commerce bom folders that the user has permission to view
	 */
	@Override
	public List<CommerceBOMFolder> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce bom folders that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom folders that the user has permission to view
	 */
	@Override
	public List<CommerceBOMFolder> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceBOMFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceBOMFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceBOMFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceBOMFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceBOMFolderImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceBOMFolderImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<CommerceBOMFolder>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce bom folders before and after the current commerce bom folder in the ordered set of commerce bom folders that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceBOMFolderId the primary key of the current commerce bom folder
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom folder
	 * @throws NoSuchBOMFolderException if a commerce bom folder with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolder[] filterFindByCompanyId_PrevAndNext(
			long commerceBOMFolderId, long companyId,
			OrderByComparator<CommerceBOMFolder> orderByComparator)
		throws NoSuchBOMFolderException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				commerceBOMFolderId, companyId, orderByComparator);
		}

		CommerceBOMFolder commerceBOMFolder = findByPrimaryKey(
			commerceBOMFolderId);

		Session session = null;

		try {
			session = openSession();

			CommerceBOMFolder[] array = new CommerceBOMFolderImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, commerceBOMFolder, companyId, orderByComparator, true);

			array[1] = commerceBOMFolder;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, commerceBOMFolder, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceBOMFolder filterGetByCompanyId_PrevAndNext(
		Session session, CommerceBOMFolder commerceBOMFolder, long companyId,
		OrderByComparator<CommerceBOMFolder> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceBOMFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceBOMFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceBOMFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceBOMFolderImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceBOMFolderImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceBOMFolder)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceBOMFolder> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce bom folders where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceBOMFolder commerceBOMFolder :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceBOMFolder);
		}
	}

	/**
	 * Returns the number of commerce bom folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce bom folders
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEBOMFOLDER_WHERE);

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

	/**
	 * Returns the number of commerce bom folders that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce bom folders that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCEBOMFOLDER_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceBOMFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"commerceBOMFolder.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_P;
	private FinderPath _finderPathWithoutPaginationFindByC_P;
	private FinderPath _finderPathCountByC_P;

	/**
	 * Returns all the commerce bom folders where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @return the matching commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findByC_P(
		long companyId, long parentCommerceBOMFolderId) {

		return findByC_P(
			companyId, parentCommerceBOMFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce bom folders where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @return the range of matching commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findByC_P(
		long companyId, long parentCommerceBOMFolderId, int start, int end) {

		return findByC_P(
			companyId, parentCommerceBOMFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce bom folders where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findByC_P(
		long companyId, long parentCommerceBOMFolderId, int start, int end,
		OrderByComparator<CommerceBOMFolder> orderByComparator) {

		return findByC_P(
			companyId, parentCommerceBOMFolderId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce bom folders where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findByC_P(
		long companyId, long parentCommerceBOMFolderId, int start, int end,
		OrderByComparator<CommerceBOMFolder> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_P;
				finderArgs = new Object[] {
					companyId, parentCommerceBOMFolderId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_P;
			finderArgs = new Object[] {
				companyId, parentCommerceBOMFolderId, start, end,
				orderByComparator
			};
		}

		List<CommerceBOMFolder> list = null;

		if (useFinderCache) {
			list = (List<CommerceBOMFolder>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceBOMFolder commerceBOMFolder : list) {
					if ((companyId != commerceBOMFolder.getCompanyId()) ||
						(parentCommerceBOMFolderId !=
							commerceBOMFolder.getParentCommerceBOMFolderId())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_COMMERCEBOMFOLDER_WHERE);

			sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_P_PARENTCOMMERCEBOMFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceBOMFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(parentCommerceBOMFolderId);

				list = (List<CommerceBOMFolder>)QueryUtil.list(
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
	 * Returns the first commerce bom folder in the ordered set where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder
	 * @throws NoSuchBOMFolderException if a matching commerce bom folder could not be found
	 */
	@Override
	public CommerceBOMFolder findByC_P_First(
			long companyId, long parentCommerceBOMFolderId,
			OrderByComparator<CommerceBOMFolder> orderByComparator)
		throws NoSuchBOMFolderException {

		CommerceBOMFolder commerceBOMFolder = fetchByC_P_First(
			companyId, parentCommerceBOMFolderId, orderByComparator);

		if (commerceBOMFolder != null) {
			return commerceBOMFolder;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentCommerceBOMFolderId=");
		sb.append(parentCommerceBOMFolderId);

		sb.append("}");

		throw new NoSuchBOMFolderException(sb.toString());
	}

	/**
	 * Returns the first commerce bom folder in the ordered set where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder, or <code>null</code> if a matching commerce bom folder could not be found
	 */
	@Override
	public CommerceBOMFolder fetchByC_P_First(
		long companyId, long parentCommerceBOMFolderId,
		OrderByComparator<CommerceBOMFolder> orderByComparator) {

		List<CommerceBOMFolder> list = findByC_P(
			companyId, parentCommerceBOMFolderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce bom folder in the ordered set where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder
	 * @throws NoSuchBOMFolderException if a matching commerce bom folder could not be found
	 */
	@Override
	public CommerceBOMFolder findByC_P_Last(
			long companyId, long parentCommerceBOMFolderId,
			OrderByComparator<CommerceBOMFolder> orderByComparator)
		throws NoSuchBOMFolderException {

		CommerceBOMFolder commerceBOMFolder = fetchByC_P_Last(
			companyId, parentCommerceBOMFolderId, orderByComparator);

		if (commerceBOMFolder != null) {
			return commerceBOMFolder;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentCommerceBOMFolderId=");
		sb.append(parentCommerceBOMFolderId);

		sb.append("}");

		throw new NoSuchBOMFolderException(sb.toString());
	}

	/**
	 * Returns the last commerce bom folder in the ordered set where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder, or <code>null</code> if a matching commerce bom folder could not be found
	 */
	@Override
	public CommerceBOMFolder fetchByC_P_Last(
		long companyId, long parentCommerceBOMFolderId,
		OrderByComparator<CommerceBOMFolder> orderByComparator) {

		int count = countByC_P(companyId, parentCommerceBOMFolderId);

		if (count == 0) {
			return null;
		}

		List<CommerceBOMFolder> list = findByC_P(
			companyId, parentCommerceBOMFolderId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce bom folders before and after the current commerce bom folder in the ordered set where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the primary key of the current commerce bom folder
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom folder
	 * @throws NoSuchBOMFolderException if a commerce bom folder with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolder[] findByC_P_PrevAndNext(
			long commerceBOMFolderId, long companyId,
			long parentCommerceBOMFolderId,
			OrderByComparator<CommerceBOMFolder> orderByComparator)
		throws NoSuchBOMFolderException {

		CommerceBOMFolder commerceBOMFolder = findByPrimaryKey(
			commerceBOMFolderId);

		Session session = null;

		try {
			session = openSession();

			CommerceBOMFolder[] array = new CommerceBOMFolderImpl[3];

			array[0] = getByC_P_PrevAndNext(
				session, commerceBOMFolder, companyId,
				parentCommerceBOMFolderId, orderByComparator, true);

			array[1] = commerceBOMFolder;

			array[2] = getByC_P_PrevAndNext(
				session, commerceBOMFolder, companyId,
				parentCommerceBOMFolderId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceBOMFolder getByC_P_PrevAndNext(
		Session session, CommerceBOMFolder commerceBOMFolder, long companyId,
		long parentCommerceBOMFolderId,
		OrderByComparator<CommerceBOMFolder> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_COMMERCEBOMFOLDER_WHERE);

		sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_PARENTCOMMERCEBOMFOLDERID_2);

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
			sb.append(CommerceBOMFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(parentCommerceBOMFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceBOMFolder)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceBOMFolder> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce bom folders that the user has permission to view where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @return the matching commerce bom folders that the user has permission to view
	 */
	@Override
	public List<CommerceBOMFolder> filterFindByC_P(
		long companyId, long parentCommerceBOMFolderId) {

		return filterFindByC_P(
			companyId, parentCommerceBOMFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce bom folders that the user has permission to view where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @return the range of matching commerce bom folders that the user has permission to view
	 */
	@Override
	public List<CommerceBOMFolder> filterFindByC_P(
		long companyId, long parentCommerceBOMFolderId, int start, int end) {

		return filterFindByC_P(
			companyId, parentCommerceBOMFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce bom folders that the user has permissions to view where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom folders that the user has permission to view
	 */
	@Override
	public List<CommerceBOMFolder> filterFindByC_P(
		long companyId, long parentCommerceBOMFolderId, int start, int end,
		OrderByComparator<CommerceBOMFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_P(
				companyId, parentCommerceBOMFolderId, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_PARENTCOMMERCEBOMFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceBOMFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceBOMFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceBOMFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceBOMFolderImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceBOMFolderImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(parentCommerceBOMFolderId);

			return (List<CommerceBOMFolder>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce bom folders before and after the current commerce bom folder in the ordered set of commerce bom folders that the user has permission to view where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the primary key of the current commerce bom folder
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom folder
	 * @throws NoSuchBOMFolderException if a commerce bom folder with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolder[] filterFindByC_P_PrevAndNext(
			long commerceBOMFolderId, long companyId,
			long parentCommerceBOMFolderId,
			OrderByComparator<CommerceBOMFolder> orderByComparator)
		throws NoSuchBOMFolderException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_P_PrevAndNext(
				commerceBOMFolderId, companyId, parentCommerceBOMFolderId,
				orderByComparator);
		}

		CommerceBOMFolder commerceBOMFolder = findByPrimaryKey(
			commerceBOMFolderId);

		Session session = null;

		try {
			session = openSession();

			CommerceBOMFolder[] array = new CommerceBOMFolderImpl[3];

			array[0] = filterGetByC_P_PrevAndNext(
				session, commerceBOMFolder, companyId,
				parentCommerceBOMFolderId, orderByComparator, true);

			array[1] = commerceBOMFolder;

			array[2] = filterGetByC_P_PrevAndNext(
				session, commerceBOMFolder, companyId,
				parentCommerceBOMFolderId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceBOMFolder filterGetByC_P_PrevAndNext(
		Session session, CommerceBOMFolder commerceBOMFolder, long companyId,
		long parentCommerceBOMFolderId,
		OrderByComparator<CommerceBOMFolder> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_PARENTCOMMERCEBOMFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceBOMFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceBOMFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceBOMFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceBOMFolderImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceBOMFolderImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		queryPos.add(parentCommerceBOMFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceBOMFolder)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceBOMFolder> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce bom folders where companyId = &#63; and parentCommerceBOMFolderId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 */
	@Override
	public void removeByC_P(long companyId, long parentCommerceBOMFolderId) {
		for (CommerceBOMFolder commerceBOMFolder :
				findByC_P(
					companyId, parentCommerceBOMFolderId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceBOMFolder);
		}
	}

	/**
	 * Returns the number of commerce bom folders where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @return the number of matching commerce bom folders
	 */
	@Override
	public int countByC_P(long companyId, long parentCommerceBOMFolderId) {
		FinderPath finderPath = _finderPathCountByC_P;

		Object[] finderArgs = new Object[] {
			companyId, parentCommerceBOMFolderId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEBOMFOLDER_WHERE);

			sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_P_PARENTCOMMERCEBOMFOLDERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(parentCommerceBOMFolderId);

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

	/**
	 * Returns the number of commerce bom folders that the user has permission to view where companyId = &#63; and parentCommerceBOMFolderId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID
	 * @return the number of matching commerce bom folders that the user has permission to view
	 */
	@Override
	public int filterCountByC_P(
		long companyId, long parentCommerceBOMFolderId) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_P(companyId, parentCommerceBOMFolderId);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCEBOMFOLDER_WHERE);

		sb.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_P_PARENTCOMMERCEBOMFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceBOMFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(parentCommerceBOMFolderId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 =
		"commerceBOMFolder.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_PARENTCOMMERCEBOMFOLDERID_2 =
		"commerceBOMFolder.parentCommerceBOMFolderId = ?";

	public CommerceBOMFolderPersistenceImpl() {
		setModelClass(CommerceBOMFolder.class);

		setModelImplClass(CommerceBOMFolderImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceBOMFolderTable.INSTANCE);
	}

	/**
	 * Caches the commerce bom folder in the entity cache if it is enabled.
	 *
	 * @param commerceBOMFolder the commerce bom folder
	 */
	@Override
	public void cacheResult(CommerceBOMFolder commerceBOMFolder) {
		entityCache.putResult(
			CommerceBOMFolderImpl.class, commerceBOMFolder.getPrimaryKey(),
			commerceBOMFolder);
	}

	/**
	 * Caches the commerce bom folders in the entity cache if it is enabled.
	 *
	 * @param commerceBOMFolders the commerce bom folders
	 */
	@Override
	public void cacheResult(List<CommerceBOMFolder> commerceBOMFolders) {
		for (CommerceBOMFolder commerceBOMFolder : commerceBOMFolders) {
			if (entityCache.getResult(
					CommerceBOMFolderImpl.class,
					commerceBOMFolder.getPrimaryKey()) == null) {

				cacheResult(commerceBOMFolder);
			}
		}
	}

	/**
	 * Clears the cache for all commerce bom folders.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceBOMFolderImpl.class);

		finderCache.clearCache(CommerceBOMFolderImpl.class);
	}

	/**
	 * Clears the cache for the commerce bom folder.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceBOMFolder commerceBOMFolder) {
		entityCache.removeResult(
			CommerceBOMFolderImpl.class, commerceBOMFolder);
	}

	@Override
	public void clearCache(List<CommerceBOMFolder> commerceBOMFolders) {
		for (CommerceBOMFolder commerceBOMFolder : commerceBOMFolders) {
			entityCache.removeResult(
				CommerceBOMFolderImpl.class, commerceBOMFolder);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceBOMFolderImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommerceBOMFolderImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce bom folder with the primary key. Does not add the commerce bom folder to the database.
	 *
	 * @param commerceBOMFolderId the primary key for the new commerce bom folder
	 * @return the new commerce bom folder
	 */
	@Override
	public CommerceBOMFolder create(long commerceBOMFolderId) {
		CommerceBOMFolder commerceBOMFolder = new CommerceBOMFolderImpl();

		commerceBOMFolder.setNew(true);
		commerceBOMFolder.setPrimaryKey(commerceBOMFolderId);

		commerceBOMFolder.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceBOMFolder;
	}

	/**
	 * Removes the commerce bom folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceBOMFolderId the primary key of the commerce bom folder
	 * @return the commerce bom folder that was removed
	 * @throws NoSuchBOMFolderException if a commerce bom folder with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolder remove(long commerceBOMFolderId)
		throws NoSuchBOMFolderException {

		return remove((Serializable)commerceBOMFolderId);
	}

	/**
	 * Removes the commerce bom folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce bom folder
	 * @return the commerce bom folder that was removed
	 * @throws NoSuchBOMFolderException if a commerce bom folder with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolder remove(Serializable primaryKey)
		throws NoSuchBOMFolderException {

		Session session = null;

		try {
			session = openSession();

			CommerceBOMFolder commerceBOMFolder =
				(CommerceBOMFolder)session.get(
					CommerceBOMFolderImpl.class, primaryKey);

			if (commerceBOMFolder == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBOMFolderException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceBOMFolder);
		}
		catch (NoSuchBOMFolderException noSuchEntityException) {
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
	protected CommerceBOMFolder removeImpl(
		CommerceBOMFolder commerceBOMFolder) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceBOMFolder)) {
				commerceBOMFolder = (CommerceBOMFolder)session.get(
					CommerceBOMFolderImpl.class,
					commerceBOMFolder.getPrimaryKeyObj());
			}

			if (commerceBOMFolder != null) {
				session.delete(commerceBOMFolder);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceBOMFolder != null) {
			clearCache(commerceBOMFolder);
		}

		return commerceBOMFolder;
	}

	@Override
	public CommerceBOMFolder updateImpl(CommerceBOMFolder commerceBOMFolder) {
		boolean isNew = commerceBOMFolder.isNew();

		if (!(commerceBOMFolder instanceof CommerceBOMFolderModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceBOMFolder.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceBOMFolder);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceBOMFolder proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceBOMFolder implementation " +
					commerceBOMFolder.getClass());
		}

		CommerceBOMFolderModelImpl commerceBOMFolderModelImpl =
			(CommerceBOMFolderModelImpl)commerceBOMFolder;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceBOMFolder.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceBOMFolder.setCreateDate(now);
			}
			else {
				commerceBOMFolder.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceBOMFolderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceBOMFolder.setModifiedDate(now);
			}
			else {
				commerceBOMFolder.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceBOMFolder);
			}
			else {
				commerceBOMFolder = (CommerceBOMFolder)session.merge(
					commerceBOMFolder);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceBOMFolderImpl.class, commerceBOMFolderModelImpl, false,
			true);

		if (isNew) {
			commerceBOMFolder.setNew(false);
		}

		commerceBOMFolder.resetOriginalValues();

		return commerceBOMFolder;
	}

	/**
	 * Returns the commerce bom folder with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce bom folder
	 * @return the commerce bom folder
	 * @throws NoSuchBOMFolderException if a commerce bom folder with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBOMFolderException {

		CommerceBOMFolder commerceBOMFolder = fetchByPrimaryKey(primaryKey);

		if (commerceBOMFolder == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBOMFolderException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceBOMFolder;
	}

	/**
	 * Returns the commerce bom folder with the primary key or throws a <code>NoSuchBOMFolderException</code> if it could not be found.
	 *
	 * @param commerceBOMFolderId the primary key of the commerce bom folder
	 * @return the commerce bom folder
	 * @throws NoSuchBOMFolderException if a commerce bom folder with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolder findByPrimaryKey(long commerceBOMFolderId)
		throws NoSuchBOMFolderException {

		return findByPrimaryKey((Serializable)commerceBOMFolderId);
	}

	/**
	 * Returns the commerce bom folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceBOMFolderId the primary key of the commerce bom folder
	 * @return the commerce bom folder, or <code>null</code> if a commerce bom folder with the primary key could not be found
	 */
	@Override
	public CommerceBOMFolder fetchByPrimaryKey(long commerceBOMFolderId) {
		return fetchByPrimaryKey((Serializable)commerceBOMFolderId);
	}

	/**
	 * Returns all the commerce bom folders.
	 *
	 * @return the commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce bom folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @return the range of commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce bom folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findAll(
		int start, int end,
		OrderByComparator<CommerceBOMFolder> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce bom folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom folders
	 * @param end the upper bound of the range of commerce bom folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce bom folders
	 */
	@Override
	public List<CommerceBOMFolder> findAll(
		int start, int end,
		OrderByComparator<CommerceBOMFolder> orderByComparator,
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

		List<CommerceBOMFolder> list = null;

		if (useFinderCache) {
			list = (List<CommerceBOMFolder>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEBOMFOLDER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEBOMFOLDER;

				sql = sql.concat(CommerceBOMFolderModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceBOMFolder>)QueryUtil.list(
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
	 * Removes all the commerce bom folders from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceBOMFolder commerceBOMFolder : findAll()) {
			remove(commerceBOMFolder);
		}
	}

	/**
	 * Returns the number of commerce bom folders.
	 *
	 * @return the number of commerce bom folders
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMERCEBOMFOLDER);

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
		return "commerceBOMFolderId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEBOMFOLDER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceBOMFolderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce bom folder persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceBOMFolderPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceBOMFolderModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", CommerceBOMFolder.class.getName()));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByC_P = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "parentCommerceBOMFolderId"}, true);

		_finderPathWithoutPaginationFindByC_P = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "parentCommerceBOMFolderId"}, true);

		_finderPathCountByC_P = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "parentCommerceBOMFolderId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceBOMFolderImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEBOMFOLDER =
		"SELECT commerceBOMFolder FROM CommerceBOMFolder commerceBOMFolder";

	private static final String _SQL_SELECT_COMMERCEBOMFOLDER_WHERE =
		"SELECT commerceBOMFolder FROM CommerceBOMFolder commerceBOMFolder WHERE ";

	private static final String _SQL_COUNT_COMMERCEBOMFOLDER =
		"SELECT COUNT(commerceBOMFolder) FROM CommerceBOMFolder commerceBOMFolder";

	private static final String _SQL_COUNT_COMMERCEBOMFOLDER_WHERE =
		"SELECT COUNT(commerceBOMFolder) FROM CommerceBOMFolder commerceBOMFolder WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"commerceBOMFolder.commerceBOMFolderId";

	private static final String _FILTER_SQL_SELECT_COMMERCEBOMFOLDER_WHERE =
		"SELECT DISTINCT {commerceBOMFolder.*} FROM CommerceBOMFolder commerceBOMFolder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CommerceBOMFolder.*} FROM (SELECT DISTINCT commerceBOMFolder.commerceBOMFolderId FROM CommerceBOMFolder commerceBOMFolder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEBOMFOLDER_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CommerceBOMFolder ON TEMP_TABLE.commerceBOMFolderId = CommerceBOMFolder.commerceBOMFolderId";

	private static final String _FILTER_SQL_COUNT_COMMERCEBOMFOLDER_WHERE =
		"SELECT COUNT(DISTINCT commerceBOMFolder.commerceBOMFolderId) AS COUNT_VALUE FROM CommerceBOMFolder commerceBOMFolder WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "commerceBOMFolder";

	private static final String _FILTER_ENTITY_TABLE = "CommerceBOMFolder";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceBOMFolder.";

	private static final String _ORDER_BY_ENTITY_TABLE = "CommerceBOMFolder.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceBOMFolder exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceBOMFolder exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceBOMFolderPersistenceImpl.class);

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;
	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();

	private static class CommerceBOMFolderModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CommerceBOMFolderModelImpl commerceBOMFolderModelImpl =
				(CommerceBOMFolderModelImpl)baseModel;

			long columnBitmask = commerceBOMFolderModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceBOMFolderModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceBOMFolderModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceBOMFolderModelImpl, columnNames, original);
			}

			return null;
		}

		private Object[] _getValue(
			CommerceBOMFolderModelImpl commerceBOMFolderModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceBOMFolderModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = commerceBOMFolderModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}