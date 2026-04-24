/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.reading.time.service.persistence.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.reading.time.exception.NoSuchEntryException;
import com.liferay.reading.time.model.ReadingTimeEntry;
import com.liferay.reading.time.model.ReadingTimeEntryTable;
import com.liferay.reading.time.model.impl.ReadingTimeEntryImpl;
import com.liferay.reading.time.model.impl.ReadingTimeEntryModelImpl;
import com.liferay.reading.time.service.persistence.ReadingTimeEntryPersistence;
import com.liferay.reading.time.service.persistence.ReadingTimeEntryUtil;
import com.liferay.reading.time.service.persistence.impl.constants.ReadingTimePersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the reading time entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ReadingTimeEntryPersistence.class)
public class ReadingTimeEntryPersistenceImpl
	extends BasePersistenceImpl<ReadingTimeEntry>
	implements ReadingTimeEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ReadingTimeEntryUtil</code> to access the reading time entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ReadingTimeEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<ReadingTimeEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the reading time entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the reading time entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @return the range of matching reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the reading time entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ReadingTimeEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the reading time entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ReadingTimeEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					ReadingTimeEntry.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first reading time entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching reading time entry
	 * @throws NoSuchEntryException if a matching reading time entry could not be found
	 */
	@Override
	public ReadingTimeEntry findByUuid_First(
			String uuid, OrderByComparator<ReadingTimeEntry> orderByComparator)
		throws NoSuchEntryException {

		ReadingTimeEntry readingTimeEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (readingTimeEntry != null) {
			return readingTimeEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first reading time entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	@Override
	public ReadingTimeEntry fetchByUuid_First(
		String uuid, OrderByComparator<ReadingTimeEntry> orderByComparator) {

		List<ReadingTimeEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the reading time entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ReadingTimeEntry readingTimeEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(readingTimeEntry);
		}
	}

	/**
	 * Returns the number of reading time entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching reading time entries
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					ReadingTimeEntry.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<ReadingTimeEntry>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the reading time entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching reading time entry
	 * @throws NoSuchEntryException if a matching reading time entry could not be found
	 */
	@Override
	public ReadingTimeEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		ReadingTimeEntry readingTimeEntry = fetchByUUID_G(uuid, groupId);

		if (readingTimeEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return readingTimeEntry;
	}

	/**
	 * Returns the reading time entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	@Override
	public ReadingTimeEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the reading time entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	@Override
	public ReadingTimeEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					ReadingTimeEntry.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the reading time entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the reading time entry that was removed
	 */
	@Override
	public ReadingTimeEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		ReadingTimeEntry readingTimeEntry = findByUUID_G(uuid, groupId);

		return remove(readingTimeEntry);
	}

	/**
	 * Returns the number of reading time entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching reading time entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<ReadingTimeEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the reading time entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the reading time entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @return the range of matching reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the reading time entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ReadingTimeEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the reading time entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ReadingTimeEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					ReadingTimeEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching reading time entry
	 * @throws NoSuchEntryException if a matching reading time entry could not be found
	 */
	@Override
	public ReadingTimeEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ReadingTimeEntry> orderByComparator)
		throws NoSuchEntryException {

		ReadingTimeEntry readingTimeEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (readingTimeEntry != null) {
			return readingTimeEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	@Override
	public ReadingTimeEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ReadingTimeEntry> orderByComparator) {

		List<ReadingTimeEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the reading time entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ReadingTimeEntry readingTimeEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(readingTimeEntry);
		}
	}

	/**
	 * Returns the number of reading time entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching reading time entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					ReadingTimeEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathFetchByG_C_C;
	private UniquePersistenceFinder<ReadingTimeEntry>
		_uniquePersistenceFinderByG_C_C;

	/**
	 * Returns the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching reading time entry
	 * @throws NoSuchEntryException if a matching reading time entry could not be found
	 */
	@Override
	public ReadingTimeEntry findByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchEntryException {

		ReadingTimeEntry readingTimeEntry = fetchByG_C_C(
			groupId, classNameId, classPK);

		if (readingTimeEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return readingTimeEntry;
	}

	/**
	 * Returns the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	@Override
	public ReadingTimeEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK) {

		return fetchByG_C_C(groupId, classNameId, classPK, true);
	}

	/**
	 * Returns the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	@Override
	public ReadingTimeEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					ReadingTimeEntry.class)) {

			return _uniquePersistenceFinderByG_C_C.fetch(
				finderCache, new Object[] {groupId, classNameId, classPK},
				useFinderCache);
		}
	}

	/**
	 * Removes the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the reading time entry that was removed
	 */
	@Override
	public ReadingTimeEntry removeByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchEntryException {

		ReadingTimeEntry readingTimeEntry = findByG_C_C(
			groupId, classNameId, classPK);

		return remove(readingTimeEntry);
	}

	/**
	 * Returns the number of reading time entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching reading time entries
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		return _uniquePersistenceFinderByG_C_C.count(
			finderCache, new Object[] {groupId, classNameId, classPK});
	}

	public ReadingTimeEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ReadingTimeEntry.class);

		setModelImplClass(ReadingTimeEntryImpl.class);
		setModelPKClass(long.class);

		setTable(ReadingTimeEntryTable.INSTANCE);
	}

	/**
	 * Caches the reading time entry in the entity cache if it is enabled.
	 *
	 * @param readingTimeEntry the reading time entry
	 */
	@Override
	public void cacheResult(ReadingTimeEntry readingTimeEntry) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					readingTimeEntry.getCtCollectionId())) {

			entityCache.putResult(
				ReadingTimeEntryImpl.class, readingTimeEntry.getPrimaryKey(),
				readingTimeEntry);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					readingTimeEntry.getUuid(), readingTimeEntry.getGroupId()
				},
				readingTimeEntry);

			finderCache.putResult(
				_finderPathFetchByG_C_C,
				new Object[] {
					readingTimeEntry.getGroupId(),
					readingTimeEntry.getClassNameId(),
					readingTimeEntry.getClassPK()
				},
				readingTimeEntry);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the reading time entries in the entity cache if it is enabled.
	 *
	 * @param readingTimeEntries the reading time entries
	 */
	@Override
	public void cacheResult(List<ReadingTimeEntry> readingTimeEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (readingTimeEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ReadingTimeEntry readingTimeEntry : readingTimeEntries) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						readingTimeEntry.getCtCollectionId())) {

				if (entityCache.getResult(
						ReadingTimeEntryImpl.class,
						readingTimeEntry.getPrimaryKey()) == null) {

					cacheResult(readingTimeEntry);
				}
			}
		}
	}

	/**
	 * Clears the cache for all reading time entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ReadingTimeEntryImpl.class);

		finderCache.clearCache(ReadingTimeEntryImpl.class);
	}

	/**
	 * Clears the cache for the reading time entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ReadingTimeEntry readingTimeEntry) {
		entityCache.removeResult(ReadingTimeEntryImpl.class, readingTimeEntry);
	}

	@Override
	public void clearCache(List<ReadingTimeEntry> readingTimeEntries) {
		for (ReadingTimeEntry readingTimeEntry : readingTimeEntries) {
			entityCache.removeResult(
				ReadingTimeEntryImpl.class, readingTimeEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ReadingTimeEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ReadingTimeEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ReadingTimeEntryModelImpl readingTimeEntryModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					readingTimeEntryModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				readingTimeEntryModelImpl.getUuid(),
				readingTimeEntryModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, readingTimeEntryModelImpl);

			args = new Object[] {
				readingTimeEntryModelImpl.getGroupId(),
				readingTimeEntryModelImpl.getClassNameId(),
				readingTimeEntryModelImpl.getClassPK()
			};

			finderCache.putResult(
				_finderPathFetchByG_C_C, args, readingTimeEntryModelImpl);
		}
	}

	/**
	 * Creates a new reading time entry with the primary key. Does not add the reading time entry to the database.
	 *
	 * @param readingTimeEntryId the primary key for the new reading time entry
	 * @return the new reading time entry
	 */
	@Override
	public ReadingTimeEntry create(long readingTimeEntryId) {
		ReadingTimeEntry readingTimeEntry = new ReadingTimeEntryImpl();

		readingTimeEntry.setNew(true);
		readingTimeEntry.setPrimaryKey(readingTimeEntryId);

		String uuid = PortalUUIDUtil.generate();

		readingTimeEntry.setUuid(uuid);

		readingTimeEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return readingTimeEntry;
	}

	/**
	 * Removes the reading time entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param readingTimeEntryId the primary key of the reading time entry
	 * @return the reading time entry that was removed
	 * @throws NoSuchEntryException if a reading time entry with the primary key could not be found
	 */
	@Override
	public ReadingTimeEntry remove(long readingTimeEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)readingTimeEntryId);
	}

	/**
	 * Removes the reading time entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the reading time entry
	 * @return the reading time entry that was removed
	 * @throws NoSuchEntryException if a reading time entry with the primary key could not be found
	 */
	@Override
	public ReadingTimeEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			ReadingTimeEntry readingTimeEntry = (ReadingTimeEntry)session.get(
				ReadingTimeEntryImpl.class, primaryKey);

			if (readingTimeEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(readingTimeEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected ReadingTimeEntry removeImpl(ReadingTimeEntry readingTimeEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(readingTimeEntry)) {
				readingTimeEntry = (ReadingTimeEntry)session.get(
					ReadingTimeEntryImpl.class,
					readingTimeEntry.getPrimaryKeyObj());
			}

			if ((readingTimeEntry != null) &&
				ctPersistenceHelper.isRemove(readingTimeEntry)) {

				session.delete(readingTimeEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (readingTimeEntry != null) {
			clearCache(readingTimeEntry);
		}

		return readingTimeEntry;
	}

	@Override
	public ReadingTimeEntry updateImpl(ReadingTimeEntry readingTimeEntry) {
		boolean isNew = readingTimeEntry.isNew();

		if (!(readingTimeEntry instanceof ReadingTimeEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(readingTimeEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					readingTimeEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in readingTimeEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ReadingTimeEntry implementation " +
					readingTimeEntry.getClass());
		}

		ReadingTimeEntryModelImpl readingTimeEntryModelImpl =
			(ReadingTimeEntryModelImpl)readingTimeEntry;

		if (Validator.isNull(readingTimeEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			readingTimeEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (readingTimeEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				readingTimeEntry.setCreateDate(date);
			}
			else {
				readingTimeEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!readingTimeEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				readingTimeEntry.setModifiedDate(date);
			}
			else {
				readingTimeEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(readingTimeEntry)) {
				if (!isNew) {
					session.evict(
						ReadingTimeEntryImpl.class,
						readingTimeEntry.getPrimaryKeyObj());
				}

				session.save(readingTimeEntry);
			}
			else {
				readingTimeEntry = (ReadingTimeEntry)session.merge(
					readingTimeEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ReadingTimeEntryImpl.class, readingTimeEntryModelImpl, false, true);

		cacheUniqueFindersCache(readingTimeEntryModelImpl);

		if (isNew) {
			readingTimeEntry.setNew(false);
		}

		readingTimeEntry.resetOriginalValues();

		return readingTimeEntry;
	}

	/**
	 * Returns the reading time entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the reading time entry
	 * @return the reading time entry
	 * @throws NoSuchEntryException if a reading time entry with the primary key could not be found
	 */
	@Override
	public ReadingTimeEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		ReadingTimeEntry readingTimeEntry = fetchByPrimaryKey(primaryKey);

		if (readingTimeEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return readingTimeEntry;
	}

	/**
	 * Returns the reading time entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param readingTimeEntryId the primary key of the reading time entry
	 * @return the reading time entry
	 * @throws NoSuchEntryException if a reading time entry with the primary key could not be found
	 */
	@Override
	public ReadingTimeEntry findByPrimaryKey(long readingTimeEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)readingTimeEntryId);
	}

	/**
	 * Returns the reading time entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the reading time entry
	 * @return the reading time entry, or <code>null</code> if a reading time entry with the primary key could not be found
	 */
	@Override
	public ReadingTimeEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				ReadingTimeEntry.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		ReadingTimeEntry readingTimeEntry =
			(ReadingTimeEntry)entityCache.getResult(
				ReadingTimeEntryImpl.class, primaryKey);

		if (readingTimeEntry != null) {
			return readingTimeEntry;
		}

		Session session = null;

		try {
			session = openSession();

			readingTimeEntry = (ReadingTimeEntry)session.get(
				ReadingTimeEntryImpl.class, primaryKey);

			if (readingTimeEntry != null) {
				cacheResult(readingTimeEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return readingTimeEntry;
	}

	/**
	 * Returns the reading time entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param readingTimeEntryId the primary key of the reading time entry
	 * @return the reading time entry, or <code>null</code> if a reading time entry with the primary key could not be found
	 */
	@Override
	public ReadingTimeEntry fetchByPrimaryKey(long readingTimeEntryId) {
		return fetchByPrimaryKey((Serializable)readingTimeEntryId);
	}

	@Override
	public Map<Serializable, ReadingTimeEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(ReadingTimeEntry.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ReadingTimeEntry> map =
			new HashMap<Serializable, ReadingTimeEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ReadingTimeEntry readingTimeEntry = fetchByPrimaryKey(primaryKey);

			if (readingTimeEntry != null) {
				map.put(primaryKey, readingTimeEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						ReadingTimeEntry.class, primaryKey)) {

				ReadingTimeEntry readingTimeEntry =
					(ReadingTimeEntry)entityCache.getResult(
						ReadingTimeEntryImpl.class, primaryKey);

				if (readingTimeEntry == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, readingTimeEntry);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (ReadingTimeEntry readingTimeEntry :
					(List<ReadingTimeEntry>)query.list()) {

				map.put(readingTimeEntry.getPrimaryKeyObj(), readingTimeEntry);

				cacheResult(readingTimeEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the reading time entries.
	 *
	 * @return the reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the reading time entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @return the range of reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the reading time entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findAll(
		int start, int end,
		OrderByComparator<ReadingTimeEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the reading time entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of reading time entries
	 */
	@Override
	public List<ReadingTimeEntry> findAll(
		int start, int end,
		OrderByComparator<ReadingTimeEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					ReadingTimeEntry.class)) {

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

			List<ReadingTimeEntry> list = null;

			if (useFinderCache) {
				list = (List<ReadingTimeEntry>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_READINGTIMEENTRY);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_READINGTIMEENTRY;

					sql = sql.concat(ReadingTimeEntryModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<ReadingTimeEntry>)QueryUtil.list(
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
	}

	/**
	 * Removes all the reading time entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ReadingTimeEntry readingTimeEntry : findAll()) {
			remove(readingTimeEntry);
		}
	}

	/**
	 * Returns the number of reading time entries.
	 *
	 * @return the number of reading time entries
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					ReadingTimeEntry.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_READINGTIMEENTRY);

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
		return "readingTimeEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_READINGTIMEENTRY;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return ReadingTimeEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "ReadingTimeEntry";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctMergeColumnNames.add("readingTime");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("readingTimeEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "classNameId", "classPK"});
	}

	/**
	 * Initializes the reading time entry persistence.
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

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_collectionPersistenceFinderByUuid = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByUuid,
			_finderPathWithoutPaginationFindByUuid, _finderPathCountByUuid,
			_SQL_SELECT_READINGTIMEENTRY_WHERE,
			_SQL_COUNT_READINGTIMEENTRY_WHERE,
			ReadingTimeEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"readingTimeEntry.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, ReadingTimeEntry::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_READINGTIMEENTRY_WHERE,
			new FinderColumn<>(
				"readingTimeEntry.", "uuid", FinderColumn.Type.STRING, "=",
				true, false, ReadingTimeEntry::getUuid),
			new FinderColumn<>(
				"readingTimeEntry.", "groupId", FinderColumn.Type.LONG, "=",
				true, true, ReadingTimeEntry::getGroupId));

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_collectionPersistenceFinderByUuid_C =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_C,
				_finderPathWithoutPaginationFindByUuid_C,
				_finderPathCountByUuid_C, _SQL_SELECT_READINGTIMEENTRY_WHERE,
				_SQL_COUNT_READINGTIMEENTRY_WHERE,
				ReadingTimeEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"readingTimeEntry.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, ReadingTimeEntry::getUuid),
				new FinderColumn<>(
					"readingTimeEntry.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, ReadingTimeEntry::getCompanyId));

		_finderPathFetchByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_uniquePersistenceFinderByG_C_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_C_C, _SQL_SELECT_READINGTIMEENTRY_WHERE,
			new FinderColumn<>(
				"readingTimeEntry.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, ReadingTimeEntry::getGroupId),
			new FinderColumn<>(
				"readingTimeEntry.", "classNameId", FinderColumn.Type.LONG, "=",
				true, false, ReadingTimeEntry::getClassNameId),
			new FinderColumn<>(
				"readingTimeEntry.", "classPK", FinderColumn.Type.LONG, "=",
				true, true, ReadingTimeEntry::getClassPK));

		ReadingTimeEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ReadingTimeEntryUtil.setPersistence(null);

		entityCache.removeCache(ReadingTimeEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = ReadingTimePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ReadingTimePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ReadingTimePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_READINGTIMEENTRY =
		"SELECT readingTimeEntry FROM ReadingTimeEntry readingTimeEntry";

	private static final String _SQL_SELECT_READINGTIMEENTRY_WHERE =
		"SELECT readingTimeEntry FROM ReadingTimeEntry readingTimeEntry WHERE ";

	private static final String _SQL_COUNT_READINGTIMEENTRY =
		"SELECT COUNT(readingTimeEntry) FROM ReadingTimeEntry readingTimeEntry";

	private static final String _SQL_COUNT_READINGTIMEENTRY_WHERE =
		"SELECT COUNT(readingTimeEntry) FROM ReadingTimeEntry readingTimeEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "readingTimeEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ReadingTimeEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ReadingTimeEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ReadingTimeEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-2092438079