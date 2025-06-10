/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.persistence.impl;

import com.liferay.exportimport.report.exception.NoSuchImportReportEntryException;
import com.liferay.exportimport.report.model.ImportReportEntry;
import com.liferay.exportimport.report.model.ImportReportEntryTable;
import com.liferay.exportimport.report.model.impl.ImportReportEntryImpl;
import com.liferay.exportimport.report.model.impl.ImportReportEntryModelImpl;
import com.liferay.exportimport.report.service.persistence.ImportReportEntryPersistence;
import com.liferay.exportimport.report.service.persistence.ImportReportEntryUtil;
import com.liferay.exportimport.report.service.persistence.impl.constants.ExportImportReportPersistenceConstants;
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
 * The persistence implementation for the import report entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Carlos Correa
 * @generated
 */
@Component(service = ImportReportEntryPersistence.class)
public class ImportReportEntryPersistenceImpl
	extends BasePersistenceImpl<ImportReportEntry>
	implements ImportReportEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ImportReportEntryUtil</code> to access the import report entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ImportReportEntryImpl.class.getName();

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
	 * Returns all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching import report entries
	 */
	@Override
	public List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK) {

		return findByC_C_C(
			companyId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @return the range of matching import report entries
	 */
	@Override
	public List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end) {

		return findByC_C_C(companyId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching import report entries
	 */
	@Override
	public List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<ImportReportEntry> orderByComparator) {

		return findByC_C_C(
			companyId, classNameId, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching import report entries
	 */
	@Override
	public List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<ImportReportEntry> orderByComparator,
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

		List<ImportReportEntry> list = null;

		if (useFinderCache) {
			list = (List<ImportReportEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ImportReportEntry importReportEntry : list) {
					if ((companyId != importReportEntry.getCompanyId()) ||
						(classNameId != importReportEntry.getClassNameId()) ||
						(classPK != importReportEntry.getClassPK())) {

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

			sb.append(_SQL_SELECT_IMPORTREPORTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ImportReportEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<ImportReportEntry>)QueryUtil.list(
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
	 * Returns the first import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching import report entry
	 * @throws NoSuchImportReportEntryException if a matching import report entry could not be found
	 */
	@Override
	public ImportReportEntry findByC_C_C_First(
			long companyId, long classNameId, long classPK,
			OrderByComparator<ImportReportEntry> orderByComparator)
		throws NoSuchImportReportEntryException {

		ImportReportEntry importReportEntry = fetchByC_C_C_First(
			companyId, classNameId, classPK, orderByComparator);

		if (importReportEntry != null) {
			return importReportEntry;
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
	 * Returns the first import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching import report entry, or <code>null</code> if a matching import report entry could not be found
	 */
	@Override
	public ImportReportEntry fetchByC_C_C_First(
		long companyId, long classNameId, long classPK,
		OrderByComparator<ImportReportEntry> orderByComparator) {

		List<ImportReportEntry> list = findByC_C_C(
			companyId, classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching import report entry
	 * @throws NoSuchImportReportEntryException if a matching import report entry could not be found
	 */
	@Override
	public ImportReportEntry findByC_C_C_Last(
			long companyId, long classNameId, long classPK,
			OrderByComparator<ImportReportEntry> orderByComparator)
		throws NoSuchImportReportEntryException {

		ImportReportEntry importReportEntry = fetchByC_C_C_Last(
			companyId, classNameId, classPK, orderByComparator);

		if (importReportEntry != null) {
			return importReportEntry;
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
	 * Returns the last import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching import report entry, or <code>null</code> if a matching import report entry could not be found
	 */
	@Override
	public ImportReportEntry fetchByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		OrderByComparator<ImportReportEntry> orderByComparator) {

		int count = countByC_C_C(companyId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<ImportReportEntry> list = findByC_C_C(
			companyId, classNameId, classPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the import report entries before and after the current import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param importReportEntryId the primary key of the current import report entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next import report entry
	 * @throws NoSuchImportReportEntryException if a import report entry with the primary key could not be found
	 */
	@Override
	public ImportReportEntry[] findByC_C_C_PrevAndNext(
			long importReportEntryId, long companyId, long classNameId,
			long classPK,
			OrderByComparator<ImportReportEntry> orderByComparator)
		throws NoSuchImportReportEntryException {

		ImportReportEntry importReportEntry = findByPrimaryKey(
			importReportEntryId);

		Session session = null;

		try {
			session = openSession();

			ImportReportEntry[] array = new ImportReportEntryImpl[3];

			array[0] = getByC_C_C_PrevAndNext(
				session, importReportEntry, companyId, classNameId, classPK,
				orderByComparator, true);

			array[1] = importReportEntry;

			array[2] = getByC_C_C_PrevAndNext(
				session, importReportEntry, companyId, classNameId, classPK,
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

	protected ImportReportEntry getByC_C_C_PrevAndNext(
		Session session, ImportReportEntry importReportEntry, long companyId,
		long classNameId, long classPK,
		OrderByComparator<ImportReportEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_IMPORTREPORTENTRY_WHERE);

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
			sb.append(ImportReportEntryModelImpl.ORDER_BY_JPQL);
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
						importReportEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ImportReportEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C_C(long companyId, long classNameId, long classPK) {
		for (ImportReportEntry importReportEntry :
				findByC_C_C(
					companyId, classNameId, classPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(importReportEntry);
		}
	}

	/**
	 * Returns the number of import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching import report entries
	 */
	@Override
	public int countByC_C_C(long companyId, long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {companyId, classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_IMPORTREPORTENTRY_WHERE);

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
		"importReportEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 =
		"importReportEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"importReportEntry.classPK = ?";

	public ImportReportEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ImportReportEntry.class);

		setModelImplClass(ImportReportEntryImpl.class);
		setModelPKClass(long.class);

		setTable(ImportReportEntryTable.INSTANCE);
	}

	/**
	 * Caches the import report entry in the entity cache if it is enabled.
	 *
	 * @param importReportEntry the import report entry
	 */
	@Override
	public void cacheResult(ImportReportEntry importReportEntry) {
		entityCache.putResult(
			ImportReportEntryImpl.class, importReportEntry.getPrimaryKey(),
			importReportEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the import report entries in the entity cache if it is enabled.
	 *
	 * @param importReportEntries the import report entries
	 */
	@Override
	public void cacheResult(List<ImportReportEntry> importReportEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (importReportEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ImportReportEntry importReportEntry : importReportEntries) {
			if (entityCache.getResult(
					ImportReportEntryImpl.class,
					importReportEntry.getPrimaryKey()) == null) {

				cacheResult(importReportEntry);
			}
		}
	}

	/**
	 * Clears the cache for all import report entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ImportReportEntryImpl.class);

		finderCache.clearCache(ImportReportEntryImpl.class);
	}

	/**
	 * Clears the cache for the import report entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ImportReportEntry importReportEntry) {
		entityCache.removeResult(
			ImportReportEntryImpl.class, importReportEntry);
	}

	@Override
	public void clearCache(List<ImportReportEntry> importReportEntries) {
		for (ImportReportEntry importReportEntry : importReportEntries) {
			entityCache.removeResult(
				ImportReportEntryImpl.class, importReportEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ImportReportEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ImportReportEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new import report entry with the primary key. Does not add the import report entry to the database.
	 *
	 * @param importReportEntryId the primary key for the new import report entry
	 * @return the new import report entry
	 */
	@Override
	public ImportReportEntry create(long importReportEntryId) {
		ImportReportEntry importReportEntry = new ImportReportEntryImpl();

		importReportEntry.setNew(true);
		importReportEntry.setPrimaryKey(importReportEntryId);

		importReportEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return importReportEntry;
	}

	/**
	 * Removes the import report entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry that was removed
	 * @throws NoSuchImportReportEntryException if a import report entry with the primary key could not be found
	 */
	@Override
	public ImportReportEntry remove(long importReportEntryId)
		throws NoSuchImportReportEntryException {

		return remove((Serializable)importReportEntryId);
	}

	/**
	 * Removes the import report entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the import report entry
	 * @return the import report entry that was removed
	 * @throws NoSuchImportReportEntryException if a import report entry with the primary key could not be found
	 */
	@Override
	public ImportReportEntry remove(Serializable primaryKey)
		throws NoSuchImportReportEntryException {

		Session session = null;

		try {
			session = openSession();

			ImportReportEntry importReportEntry =
				(ImportReportEntry)session.get(
					ImportReportEntryImpl.class, primaryKey);

			if (importReportEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchImportReportEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(importReportEntry);
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
	protected ImportReportEntry removeImpl(
		ImportReportEntry importReportEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(importReportEntry)) {
				importReportEntry = (ImportReportEntry)session.get(
					ImportReportEntryImpl.class,
					importReportEntry.getPrimaryKeyObj());
			}

			if (importReportEntry != null) {
				session.delete(importReportEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (importReportEntry != null) {
			clearCache(importReportEntry);
		}

		return importReportEntry;
	}

	@Override
	public ImportReportEntry updateImpl(ImportReportEntry importReportEntry) {
		boolean isNew = importReportEntry.isNew();

		if (!(importReportEntry instanceof ImportReportEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(importReportEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					importReportEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in importReportEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ImportReportEntry implementation " +
					importReportEntry.getClass());
		}

		ImportReportEntryModelImpl importReportEntryModelImpl =
			(ImportReportEntryModelImpl)importReportEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (importReportEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				importReportEntry.setCreateDate(date);
			}
			else {
				importReportEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!importReportEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				importReportEntry.setModifiedDate(date);
			}
			else {
				importReportEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(importReportEntry);
			}
			else {
				importReportEntry = (ImportReportEntry)session.merge(
					importReportEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ImportReportEntryImpl.class, importReportEntryModelImpl, false,
			true);

		if (isNew) {
			importReportEntry.setNew(false);
		}

		importReportEntry.resetOriginalValues();

		return importReportEntry;
	}

	/**
	 * Returns the import report entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the import report entry
	 * @return the import report entry
	 * @throws NoSuchImportReportEntryException if a import report entry with the primary key could not be found
	 */
	@Override
	public ImportReportEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchImportReportEntryException {

		ImportReportEntry importReportEntry = fetchByPrimaryKey(primaryKey);

		if (importReportEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchImportReportEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return importReportEntry;
	}

	/**
	 * Returns the import report entry with the primary key or throws a <code>NoSuchImportReportEntryException</code> if it could not be found.
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry
	 * @throws NoSuchImportReportEntryException if a import report entry with the primary key could not be found
	 */
	@Override
	public ImportReportEntry findByPrimaryKey(long importReportEntryId)
		throws NoSuchImportReportEntryException {

		return findByPrimaryKey((Serializable)importReportEntryId);
	}

	/**
	 * Returns the import report entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry, or <code>null</code> if a import report entry with the primary key could not be found
	 */
	@Override
	public ImportReportEntry fetchByPrimaryKey(long importReportEntryId) {
		return fetchByPrimaryKey((Serializable)importReportEntryId);
	}

	/**
	 * Returns all the import report entries.
	 *
	 * @return the import report entries
	 */
	@Override
	public List<ImportReportEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @return the range of import report entries
	 */
	@Override
	public List<ImportReportEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of import report entries
	 */
	@Override
	public List<ImportReportEntry> findAll(
		int start, int end,
		OrderByComparator<ImportReportEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of import report entries
	 */
	@Override
	public List<ImportReportEntry> findAll(
		int start, int end,
		OrderByComparator<ImportReportEntry> orderByComparator,
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

		List<ImportReportEntry> list = null;

		if (useFinderCache) {
			list = (List<ImportReportEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_IMPORTREPORTENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_IMPORTREPORTENTRY;

				sql = sql.concat(ImportReportEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ImportReportEntry>)QueryUtil.list(
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
	 * Removes all the import report entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ImportReportEntry importReportEntry : findAll()) {
			remove(importReportEntry);
		}
	}

	/**
	 * Returns the number of import report entries.
	 *
	 * @return the number of import report entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_IMPORTREPORTENTRY);

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
		return "importReportEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_IMPORTREPORTENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ImportReportEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the import report entry persistence.
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

		ImportReportEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ImportReportEntryUtil.setPersistence(null);

		entityCache.removeCache(ImportReportEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = ExportImportReportPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ExportImportReportPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ExportImportReportPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_IMPORTREPORTENTRY =
		"SELECT importReportEntry FROM ImportReportEntry importReportEntry";

	private static final String _SQL_SELECT_IMPORTREPORTENTRY_WHERE =
		"SELECT importReportEntry FROM ImportReportEntry importReportEntry WHERE ";

	private static final String _SQL_COUNT_IMPORTREPORTENTRY =
		"SELECT COUNT(importReportEntry) FROM ImportReportEntry importReportEntry";

	private static final String _SQL_COUNT_IMPORTREPORTENTRY_WHERE =
		"SELECT COUNT(importReportEntry) FROM ImportReportEntry importReportEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "importReportEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ImportReportEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ImportReportEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ImportReportEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}