/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.persistence.impl;

import com.liferay.batch.engine.exception.NoSuchImportReportEntryException;
import com.liferay.batch.engine.model.BatchEngineImportReportEntry;
import com.liferay.batch.engine.model.BatchEngineImportReportEntryTable;
import com.liferay.batch.engine.model.impl.BatchEngineImportReportEntryImpl;
import com.liferay.batch.engine.model.impl.BatchEngineImportReportEntryModelImpl;
import com.liferay.batch.engine.service.persistence.BatchEngineImportReportEntryPersistence;
import com.liferay.batch.engine.service.persistence.BatchEngineImportReportEntryUtil;
import com.liferay.batch.engine.service.persistence.impl.constants.BatchEnginePersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the batch engine import report entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @generated
 */
@Component(service = BatchEngineImportReportEntryPersistence.class)
public class BatchEngineImportReportEntryPersistenceImpl
	extends BasePersistenceImpl<BatchEngineImportReportEntry>
	implements BatchEngineImportReportEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchEngineImportReportEntryUtil</code> to access the batch engine import report entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchEngineImportReportEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByC_C_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching batch engine import report entries
	 */
	@Override
	public List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK) {

		return findByC_C_C(
			companyId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @return the range of matching batch engine import report entries
	 */
	@Override
	public List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end) {

		return findByC_C_C(companyId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import report entries
	 */
	@Override
	public List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator) {

		return findByC_C_C(
			companyId, classNameId, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import report entries
	 */
	@Override
	public List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C_C;
				finderArgs = new Object[] {companyId, classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C_C;
			finderArgs = new Object[] {
				companyId, classNameId, classPK, start, end, orderByComparator
			};
		}

		List<BatchEngineImportReportEntry> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineImportReportEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchEngineImportReportEntry batchEngineImportReportEntry :
						list) {

					if ((companyId !=
							batchEngineImportReportEntry.getCompanyId()) ||
						(classNameId !=
							batchEngineImportReportEntry.getClassNameId()) ||
						(classPK !=
							batchEngineImportReportEntry.getClassPK())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_BATCHENGINEIMPORTREPORTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BatchEngineImportReportEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<BatchEngineImportReportEntry>)QueryUtil.list(
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
	 * Returns the first batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a matching batch engine import report entry could not be found
	 */
	@Override
	public BatchEngineImportReportEntry findByC_C_C_First(
			long companyId, long classNameId, long classPK,
			OrderByComparator<BatchEngineImportReportEntry> orderByComparator)
		throws NoSuchImportReportEntryException {

		BatchEngineImportReportEntry batchEngineImportReportEntry =
			fetchByC_C_C_First(
				companyId, classNameId, classPK, orderByComparator);

		if (batchEngineImportReportEntry != null) {
			return batchEngineImportReportEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchImportReportEntryException(sb.toString());
	}

	/**
	 * Returns the first batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import report entry, or <code>null</code> if a matching batch engine import report entry could not be found
	 */
	@Override
	public BatchEngineImportReportEntry fetchByC_C_C_First(
		long companyId, long classNameId, long classPK,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator) {

		List<BatchEngineImportReportEntry> list = findByC_C_C(
			companyId, classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a matching batch engine import report entry could not be found
	 */
	@Override
	public BatchEngineImportReportEntry findByC_C_C_Last(
			long companyId, long classNameId, long classPK,
			OrderByComparator<BatchEngineImportReportEntry> orderByComparator)
		throws NoSuchImportReportEntryException {

		BatchEngineImportReportEntry batchEngineImportReportEntry =
			fetchByC_C_C_Last(
				companyId, classNameId, classPK, orderByComparator);

		if (batchEngineImportReportEntry != null) {
			return batchEngineImportReportEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchImportReportEntryException(sb.toString());
	}

	/**
	 * Returns the last batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import report entry, or <code>null</code> if a matching batch engine import report entry could not be found
	 */
	@Override
	public BatchEngineImportReportEntry fetchByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator) {

		int count = countByC_C_C(companyId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<BatchEngineImportReportEntry> list = findByC_C_C(
			companyId, classNameId, classPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch engine import report entries before and after the current batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the current batch engine import report entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a batch engine import report entry with the primary key could not be found
	 */
	@Override
	public BatchEngineImportReportEntry[] findByC_C_C_PrevAndNext(
			long batchEngineImportReportEntryId, long companyId,
			long classNameId, long classPK,
			OrderByComparator<BatchEngineImportReportEntry> orderByComparator)
		throws NoSuchImportReportEntryException {

		BatchEngineImportReportEntry batchEngineImportReportEntry =
			findByPrimaryKey(batchEngineImportReportEntryId);

		Session session = null;

		try {
			session = openSession();

			BatchEngineImportReportEntry[] array =
				new BatchEngineImportReportEntryImpl[3];

			array[0] = getByC_C_C_PrevAndNext(
				session, batchEngineImportReportEntry, companyId, classNameId,
				classPK, orderByComparator, true);

			array[1] = batchEngineImportReportEntry;

			array[2] = getByC_C_C_PrevAndNext(
				session, batchEngineImportReportEntry, companyId, classNameId,
				classPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BatchEngineImportReportEntry getByC_C_C_PrevAndNext(
		Session session,
		BatchEngineImportReportEntry batchEngineImportReportEntry,
		long companyId, long classNameId, long classPK,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_BATCHENGINEIMPORTREPORTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

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
			sb.append(BatchEngineImportReportEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchEngineImportReportEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BatchEngineImportReportEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C_C(long companyId, long classNameId, long classPK) {
		for (BatchEngineImportReportEntry batchEngineImportReportEntry :
				findByC_C_C(
					companyId, classNameId, classPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(batchEngineImportReportEntry);
		}
	}

	/**
	 * Returns the number of batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching batch engine import report entries
	 */
	@Override
	public int countByC_C_C(long companyId, long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {companyId, classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_BATCHENGINEIMPORTREPORTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_C_COMPANYID_2 =
		"batchEngineImportReportEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 =
		"batchEngineImportReportEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"batchEngineImportReportEntry.classPK = ?";

	public BatchEngineImportReportEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(BatchEngineImportReportEntry.class);

		setModelImplClass(BatchEngineImportReportEntryImpl.class);
		setModelPKClass(long.class);

		setTable(BatchEngineImportReportEntryTable.INSTANCE);
	}

	/**
	 * Caches the batch engine import report entry in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportReportEntry the batch engine import report entry
	 */
	@Override
	public void cacheResult(
		BatchEngineImportReportEntry batchEngineImportReportEntry) {

		entityCache.putResult(
			BatchEngineImportReportEntryImpl.class,
			batchEngineImportReportEntry.getPrimaryKey(),
			batchEngineImportReportEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the batch engine import report entries in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportReportEntries the batch engine import report entries
	 */
	@Override
	public void cacheResult(
		List<BatchEngineImportReportEntry> batchEngineImportReportEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (batchEngineImportReportEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BatchEngineImportReportEntry batchEngineImportReportEntry :
				batchEngineImportReportEntries) {

			if (entityCache.getResult(
					BatchEngineImportReportEntryImpl.class,
					batchEngineImportReportEntry.getPrimaryKey()) == null) {

				cacheResult(batchEngineImportReportEntry);
			}
		}
	}

	/**
	 * Clears the cache for all batch engine import report entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BatchEngineImportReportEntryImpl.class);

		finderCache.clearCache(BatchEngineImportReportEntryImpl.class);
	}

	/**
	 * Clears the cache for the batch engine import report entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		BatchEngineImportReportEntry batchEngineImportReportEntry) {

		entityCache.removeResult(
			BatchEngineImportReportEntryImpl.class,
			batchEngineImportReportEntry);
	}

	@Override
	public void clearCache(
		List<BatchEngineImportReportEntry> batchEngineImportReportEntries) {

		for (BatchEngineImportReportEntry batchEngineImportReportEntry :
				batchEngineImportReportEntries) {

			entityCache.removeResult(
				BatchEngineImportReportEntryImpl.class,
				batchEngineImportReportEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(BatchEngineImportReportEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				BatchEngineImportReportEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new batch engine import report entry with the primary key. Does not add the batch engine import report entry to the database.
	 *
	 * @param batchEngineImportReportEntryId the primary key for the new batch engine import report entry
	 * @return the new batch engine import report entry
	 */
	@Override
	public BatchEngineImportReportEntry create(
		long batchEngineImportReportEntryId) {

		BatchEngineImportReportEntry batchEngineImportReportEntry =
			new BatchEngineImportReportEntryImpl();

		batchEngineImportReportEntry.setNew(true);
		batchEngineImportReportEntry.setPrimaryKey(
			batchEngineImportReportEntryId);

		batchEngineImportReportEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return batchEngineImportReportEntry;
	}

	/**
	 * Removes the batch engine import report entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the batch engine import report entry
	 * @return the batch engine import report entry that was removed
	 * @throws NoSuchImportReportEntryException if a batch engine import report entry with the primary key could not be found
	 */
	@Override
	public BatchEngineImportReportEntry remove(
			long batchEngineImportReportEntryId)
		throws NoSuchImportReportEntryException {

		return remove((Serializable)batchEngineImportReportEntryId);
	}

	/**
	 * Removes the batch engine import report entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the batch engine import report entry
	 * @return the batch engine import report entry that was removed
	 * @throws NoSuchImportReportEntryException if a batch engine import report entry with the primary key could not be found
	 */
	@Override
	public BatchEngineImportReportEntry remove(Serializable primaryKey)
		throws NoSuchImportReportEntryException {

		Session session = null;

		try {
			session = openSession();

			BatchEngineImportReportEntry batchEngineImportReportEntry =
				(BatchEngineImportReportEntry)session.get(
					BatchEngineImportReportEntryImpl.class, primaryKey);

			if (batchEngineImportReportEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchImportReportEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(batchEngineImportReportEntry);
		}
		catch (NoSuchImportReportEntryException noSuchEntityException) {
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
	protected BatchEngineImportReportEntry removeImpl(
		BatchEngineImportReportEntry batchEngineImportReportEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchEngineImportReportEntry)) {
				batchEngineImportReportEntry =
					(BatchEngineImportReportEntry)session.get(
						BatchEngineImportReportEntryImpl.class,
						batchEngineImportReportEntry.getPrimaryKeyObj());
			}

			if (batchEngineImportReportEntry != null) {
				session.delete(batchEngineImportReportEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (batchEngineImportReportEntry != null) {
			clearCache(batchEngineImportReportEntry);
		}

		return batchEngineImportReportEntry;
	}

	@Override
	public BatchEngineImportReportEntry updateImpl(
		BatchEngineImportReportEntry batchEngineImportReportEntry) {

		boolean isNew = batchEngineImportReportEntry.isNew();

		if (!(batchEngineImportReportEntry instanceof
				BatchEngineImportReportEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					batchEngineImportReportEntry.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					batchEngineImportReportEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchEngineImportReportEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchEngineImportReportEntry implementation " +
					batchEngineImportReportEntry.getClass());
		}

		BatchEngineImportReportEntryModelImpl
			batchEngineImportReportEntryModelImpl =
				(BatchEngineImportReportEntryModelImpl)
					batchEngineImportReportEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (batchEngineImportReportEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchEngineImportReportEntry.setCreateDate(date);
			}
			else {
				batchEngineImportReportEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!batchEngineImportReportEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchEngineImportReportEntry.setModifiedDate(date);
			}
			else {
				batchEngineImportReportEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(batchEngineImportReportEntry);
			}
			else {
				batchEngineImportReportEntry =
					(BatchEngineImportReportEntry)session.merge(
						batchEngineImportReportEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			BatchEngineImportReportEntryImpl.class,
			batchEngineImportReportEntryModelImpl, false, true);

		if (isNew) {
			batchEngineImportReportEntry.setNew(false);
		}

		batchEngineImportReportEntry.resetOriginalValues();

		return batchEngineImportReportEntry;
	}

	/**
	 * Returns the batch engine import report entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the batch engine import report entry
	 * @return the batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a batch engine import report entry with the primary key could not be found
	 */
	@Override
	public BatchEngineImportReportEntry findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchImportReportEntryException {

		BatchEngineImportReportEntry batchEngineImportReportEntry =
			fetchByPrimaryKey(primaryKey);

		if (batchEngineImportReportEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchImportReportEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return batchEngineImportReportEntry;
	}

	/**
	 * Returns the batch engine import report entry with the primary key or throws a <code>NoSuchImportReportEntryException</code> if it could not be found.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the batch engine import report entry
	 * @return the batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a batch engine import report entry with the primary key could not be found
	 */
	@Override
	public BatchEngineImportReportEntry findByPrimaryKey(
			long batchEngineImportReportEntryId)
		throws NoSuchImportReportEntryException {

		return findByPrimaryKey((Serializable)batchEngineImportReportEntryId);
	}

	/**
	 * Returns the batch engine import report entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the batch engine import report entry
	 * @return the batch engine import report entry, or <code>null</code> if a batch engine import report entry with the primary key could not be found
	 */
	@Override
	public BatchEngineImportReportEntry fetchByPrimaryKey(
		long batchEngineImportReportEntryId) {

		return fetchByPrimaryKey((Serializable)batchEngineImportReportEntryId);
	}

	/**
	 * Returns all the batch engine import report entries.
	 *
	 * @return the batch engine import report entries
	 */
	@Override
	public List<BatchEngineImportReportEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @return the range of batch engine import report entries
	 */
	@Override
	public List<BatchEngineImportReportEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch engine import report entries
	 */
	@Override
	public List<BatchEngineImportReportEntry> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch engine import report entries
	 */
	@Override
	public List<BatchEngineImportReportEntry> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator,
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

		List<BatchEngineImportReportEntry> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineImportReportEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BATCHENGINEIMPORTREPORTENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHENGINEIMPORTREPORTENTRY;

				sql = sql.concat(
					BatchEngineImportReportEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BatchEngineImportReportEntry>)QueryUtil.list(
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
	 * Removes all the batch engine import report entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchEngineImportReportEntry batchEngineImportReportEntry :
				findAll()) {

			remove(batchEngineImportReportEntry);
		}
	}

	/**
	 * Returns the number of batch engine import report entries.
	 *
	 * @return the number of batch engine import report entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_BATCHENGINEIMPORTREPORTENTRY);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "batchEngineImportReportEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHENGINEIMPORTREPORTENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchEngineImportReportEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch engine import report entry persistence.
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

		_finderPathWithPaginationFindByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"companyId", "classNameId", "classPK"}, true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"companyId", "classNameId", "classPK"}, false);

		BatchEngineImportReportEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		BatchEngineImportReportEntryUtil.setPersistence(null);

		entityCache.removeCache(
			BatchEngineImportReportEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = BatchEnginePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = BatchEnginePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BatchEnginePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_BATCHENGINEIMPORTREPORTENTRY =
		"SELECT batchEngineImportReportEntry FROM BatchEngineImportReportEntry batchEngineImportReportEntry";

	private static final String _SQL_SELECT_BATCHENGINEIMPORTREPORTENTRY_WHERE =
		"SELECT batchEngineImportReportEntry FROM BatchEngineImportReportEntry batchEngineImportReportEntry WHERE ";

	private static final String _SQL_COUNT_BATCHENGINEIMPORTREPORTENTRY =
		"SELECT COUNT(batchEngineImportReportEntry) FROM BatchEngineImportReportEntry batchEngineImportReportEntry";

	private static final String _SQL_COUNT_BATCHENGINEIMPORTREPORTENTRY_WHERE =
		"SELECT COUNT(batchEngineImportReportEntry) FROM BatchEngineImportReportEntry batchEngineImportReportEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"batchEngineImportReportEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BatchEngineImportReportEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchEngineImportReportEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineImportReportEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}