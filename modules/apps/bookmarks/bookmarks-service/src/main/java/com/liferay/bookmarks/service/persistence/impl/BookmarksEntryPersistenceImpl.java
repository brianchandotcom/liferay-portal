/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.service.persistence.impl;

import com.liferay.bookmarks.exception.NoSuchEntryException;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksEntryTable;
import com.liferay.bookmarks.model.impl.BookmarksEntryImpl;
import com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl;
import com.liferay.bookmarks.service.persistence.BookmarksEntryPersistence;
import com.liferay.bookmarks.service.persistence.BookmarksEntryUtil;
import com.liferay.bookmarks.service.persistence.impl.constants.BookmarksPersistenceConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the bookmarks entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = BookmarksEntryPersistence.class)
public class BookmarksEntryPersistenceImpl
	extends BasePersistenceImpl<BookmarksEntry, NoSuchEntryException>
	implements BookmarksEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BookmarksEntryUtil</code> to access the bookmarks entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BookmarksEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<BookmarksEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the bookmarks entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByUuid_First(
			String uuid, OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByUuid_First(
		String uuid, OrderByComparator<BookmarksEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the bookmarks entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of bookmarks entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<BookmarksEntry>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the bookmarks entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByUUID_G(uuid, groupId);

		if (bookmarksEntry == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryException(message);
		}

		return bookmarksEntry;
	}

	/**
	 * Returns the bookmarks entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the bookmarks entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the bookmarks entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the bookmarks entry that was removed
	 */
	@Override
	public BookmarksEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = findByUUID_G(uuid, groupId);

		return remove(bookmarksEntry);
	}

	/**
	 * Returns the number of bookmarks entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<BookmarksEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the bookmarks entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the bookmarks entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		_collectionPersistenceFinderByUuid_C.remove(
			finderCache, new Object[] {uuid, companyId});
	}

	/**
	 * Returns the number of bookmarks entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<BookmarksEntry>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the bookmarks entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				finderCache, new Object[] {companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByCompanyId_First(
			long companyId, OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<BookmarksEntry> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the bookmarks entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of bookmarks entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				finderCache, new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_F;
	private FinderPath _finderPathWithoutPaginationFindByG_F;
	private FinderPath _finderPathCountByG_F;
	private FinderPath _finderPathWithPaginationCountByG_F;

	/**
	 * Returns all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F(long groupId, long folderId) {
		return findByG_F(
			groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F(
		long groupId, long folderId, int start, int end) {

		return findByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_F(
			groupId, folderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_F;
					finderArgs = new Object[] {groupId, folderId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_F;
				finderArgs = new Object[] {
					groupId, folderId, start, end, orderByComparator
				};
			}

			List<BookmarksEntry> list = null;

			if (useFinderCache) {
				list = (List<BookmarksEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (BookmarksEntry bookmarksEntry : list) {
						if ((groupId != bookmarksEntry.getGroupId()) ||
							(folderId != bookmarksEntry.getFolderId())) {

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

				sb.append(_SQL_SELECT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_FOLDERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(BookmarksEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

					list = (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByG_F_First(
			long groupId, long folderId,
			OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByG_F_First(
			groupId, folderId, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", folderId=");
		sb.append(folderId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByG_F_First(
		long groupId, long folderId,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		List<BookmarksEntry> list = findByG_F(
			groupId, folderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F(long groupId, long folderId) {
		return filterFindByG_F(
			groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end) {

		return filterFindByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F(
					groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
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
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds) {

		return filterFindByG_F(
			groupId, folderIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end) {

		return filterFindByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderIds, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F(
					groupId, folderIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F(long groupId, long[] folderIds) {
		return findByG_F(
			groupId, folderIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end) {

		return findByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_F(
			groupId, folderIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		if (folderIds.length == 1) {
			return findByG_F(
				groupId, folderIds[0], start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						groupId, StringUtil.merge(folderIds)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(folderIds), start, end,
					orderByComparator
				};
			}

			List<BookmarksEntry> list = null;

			if (useFinderCache) {
				list = (List<BookmarksEntry>)finderCache.getResult(
					_finderPathWithPaginationFindByG_F, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (BookmarksEntry bookmarksEntry : list) {
						if ((groupId != bookmarksEntry.getGroupId()) ||
							!ArrayUtil.contains(
								folderIds, bookmarksEntry.getFolderId())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

				if (folderIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_F_FOLDERID_7);

					sb.append(StringUtil.merge(folderIds));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(BookmarksEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					list = (List<BookmarksEntry>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_F, finderArgs,
							list);
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
	 * Removes all the bookmarks entries where groupId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByG_F(long groupId, long folderId) {
		for (BookmarksEntry bookmarksEntry :
				findByG_F(
					groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(bookmarksEntry);
		}
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_F(long groupId, long folderId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			FinderPath finderPath = _finderPathCountByG_F;

			Object[] finderArgs = new Object[] {groupId, folderId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_FOLDERID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

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
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_F(long groupId, long[] folderIds) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(folderIds)
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_F, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

				if (folderIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_F_FOLDERID_7);

					sb.append(StringUtil.merge(folderIds));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_F, finderArgs, count);
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

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F(long groupId, long folderId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = findByG_F(
				groupId, folderId);

			bookmarksEntries = InlineSQLHelperUtil.filter(
				bookmarksEntries, groupId);

			return bookmarksEntries.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

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

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F(long groupId, long[] folderIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderIds);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = InlineSQLHelperUtil.filter(
				findByG_F(groupId, folderIds), groupId);

			return bookmarksEntries.size();
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 =
		"bookmarksEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 =
		"bookmarksEntry.folderId = ?";

	private static final String _FINDER_COLUMN_G_F_FOLDERID_7 =
		"bookmarksEntry.folderId IN (";

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;
	private CollectionPersistenceFinder<BookmarksEntry>
		_collectionPersistenceFinderByG_S;

	/**
	 * Returns all the bookmarks entries where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_S(long groupId, int status) {
		return findByG_S(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_S(
		long groupId, int status, int start, int end) {

		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_S(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByG_S.find(
				finderCache, new Object[] {groupId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByG_S_First(
			long groupId, int status,
			OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByG_S_First(
			groupId, status, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, status}));
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByG_S_First(
		long groupId, int status,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_S.fetchFirst(
			finderCache, new Object[] {groupId, status}, orderByComparator);
	}

	/**
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_S(long groupId, int status) {
		return filterFindByG_S(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_S(
		long groupId, int status, int start, int end) {

		return filterFindByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S(groupId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_S(
					groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
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
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Removes all the bookmarks entries where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_S(long groupId, int status) {
		_collectionPersistenceFinderByG_S.remove(
			finderCache, new Object[] {groupId, status});
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByG_S.count(
				finderCache, new Object[] {groupId, status});
		}
	}

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_S(long groupId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = findByG_S(groupId, status);

			bookmarksEntries = InlineSQLHelperUtil.filter(
				bookmarksEntries, groupId);

			return bookmarksEntries.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"bookmarksEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_STATUS_2 =
		"bookmarksEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_NotS;
	private FinderPath _finderPathWithPaginationCountByG_NotS;
	private CollectionPersistenceFinder<BookmarksEntry>
		_collectionPersistenceFinderByG_NotS;

	/**
	 * Returns all the bookmarks entries where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_NotS(long groupId, int status) {
		return findByG_NotS(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_NotS(
		long groupId, int status, int start, int end) {

		return findByG_NotS(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_NotS(
		long groupId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_NotS(
			groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_NotS(
		long groupId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByG_NotS.find(
				finderCache, new Object[] {groupId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByG_NotS_First(
			long groupId, int status,
			OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByG_NotS_First(
			groupId, status, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_NotS.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, status}));
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByG_NotS_First(
		long groupId, int status,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_NotS.fetchFirst(
			finderCache, new Object[] {groupId, status}, orderByComparator);
	}

	/**
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_NotS(long groupId, int status) {
		return filterFindByG_NotS(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_NotS(
		long groupId, int status, int start, int end) {

		return filterFindByG_NotS(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_NotS(
		long groupId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotS(groupId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_NotS(
					groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
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
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Removes all the bookmarks entries where groupId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_NotS(long groupId, int status) {
		_collectionPersistenceFinderByG_NotS.remove(
			finderCache, new Object[] {groupId, status});
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_NotS(long groupId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByG_NotS.count(
				finderCache, new Object[] {groupId, status});
		}
	}

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_NotS(long groupId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_NotS(groupId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = findByG_NotS(
				groupId, status);

			bookmarksEntries = InlineSQLHelperUtil.filter(
				bookmarksEntries, groupId);

			return bookmarksEntries.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_NOTS_GROUPID_2 =
		"bookmarksEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_NOTS_STATUS_2 =
		"bookmarksEntry.status != ?";

	private FinderPath _finderPathWithPaginationFindByC_NotS;
	private FinderPath _finderPathWithPaginationCountByC_NotS;
	private CollectionPersistenceFinder<BookmarksEntry>
		_collectionPersistenceFinderByC_NotS;

	/**
	 * Returns all the bookmarks entries where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByC_NotS(long companyId, int status) {
		return findByC_NotS(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByC_NotS(
		long companyId, int status, int start, int end) {

		return findByC_NotS(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByC_NotS(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByC_NotS.find(
				finderCache, new Object[] {companyId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByC_NotS_First(
			long companyId, int status,
			OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByC_NotS_First(
			companyId, status, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByC_NotS.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, status}));
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByC_NotS_First(
		long companyId, int status,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return _collectionPersistenceFinderByC_NotS.fetchFirst(
			finderCache, new Object[] {companyId, status}, orderByComparator);
	}

	/**
	 * Removes all the bookmarks entries where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_NotS(long companyId, int status) {
		_collectionPersistenceFinderByC_NotS.remove(
			finderCache, new Object[] {companyId, status});
	}

	/**
	 * Returns the number of bookmarks entries where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByC_NotS(long companyId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByC_NotS.count(
				finderCache, new Object[] {companyId, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_U_S;
	private FinderPath _finderPathWithoutPaginationFindByG_U_S;
	private FinderPath _finderPathCountByG_U_S;
	private CollectionPersistenceFinder<BookmarksEntry>
		_collectionPersistenceFinderByG_U_S;

	/**
	 * Returns all the bookmarks entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_S(
		long groupId, long userId, int status) {

		return findByG_U_S(
			groupId, userId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_S(
		long groupId, long userId, int status, int start, int end) {

		return findByG_U_S(groupId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_S(
		long groupId, long userId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_U_S(
			groupId, userId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_S(
		long groupId, long userId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByG_U_S.find(
				finderCache, new Object[] {groupId, userId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByG_U_S_First(
			long groupId, long userId, int status,
			OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByG_U_S_First(
			groupId, userId, status, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_U_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, userId, status}));
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByG_U_S_First(
		long groupId, long userId, int status,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_U_S.fetchFirst(
			finderCache, new Object[] {groupId, userId, status},
			orderByComparator);
	}

	/**
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_S(
		long groupId, long userId, int status) {

		return filterFindByG_U_S(
			groupId, userId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_S(
		long groupId, long userId, int status, int start, int end) {

		return filterFindByG_U_S(groupId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_S(
		long groupId, long userId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_S(
				groupId, userId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_U_S(
					groupId, userId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_S_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			queryPos.add(status);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Removes all the bookmarks entries where groupId = &#63; and userId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 */
	@Override
	public void removeByG_U_S(long groupId, long userId, int status) {
		_collectionPersistenceFinderByG_U_S.remove(
			finderCache, new Object[] {groupId, userId, status});
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_U_S(long groupId, long userId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByG_U_S.count(
				finderCache, new Object[] {groupId, userId, status});
		}
	}

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_S(long groupId, long userId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_S(groupId, userId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = findByG_U_S(
				groupId, userId, status);

			bookmarksEntries = InlineSQLHelperUtil.filter(
				bookmarksEntries, groupId);

			return bookmarksEntries.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_S_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_U_S_GROUPID_2 =
		"bookmarksEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_S_USERID_2 =
		"bookmarksEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_S_STATUS_2 =
		"bookmarksEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_U_NotS;
	private FinderPath _finderPathWithPaginationCountByG_U_NotS;
	private CollectionPersistenceFinder<BookmarksEntry>
		_collectionPersistenceFinderByG_U_NotS;

	/**
	 * Returns all the bookmarks entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_NotS(
		long groupId, long userId, int status) {

		return findByG_U_NotS(
			groupId, userId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_NotS(
		long groupId, long userId, int status, int start, int end) {

		return findByG_U_NotS(groupId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_NotS(
		long groupId, long userId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_U_NotS(
			groupId, userId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_NotS(
		long groupId, long userId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByG_U_NotS.find(
				finderCache, new Object[] {groupId, userId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByG_U_NotS_First(
			long groupId, long userId, int status,
			OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByG_U_NotS_First(
			groupId, userId, status, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByG_U_NotS.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, userId, status}));
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByG_U_NotS_First(
		long groupId, long userId, int status,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_U_NotS.fetchFirst(
			finderCache, new Object[] {groupId, userId, status},
			orderByComparator);
	}

	/**
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_NotS(
		long groupId, long userId, int status) {

		return filterFindByG_U_NotS(
			groupId, userId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_NotS(
		long groupId, long userId, int status, int start, int end) {

		return filterFindByG_U_NotS(groupId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_NotS(
		long groupId, long userId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_NotS(
				groupId, userId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_U_NotS(
					groupId, userId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_U_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_NOTS_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			queryPos.add(status);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Removes all the bookmarks entries where groupId = &#63; and userId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 */
	@Override
	public void removeByG_U_NotS(long groupId, long userId, int status) {
		_collectionPersistenceFinderByG_U_NotS.remove(
			finderCache, new Object[] {groupId, userId, status});
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_U_NotS(long groupId, long userId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			return _collectionPersistenceFinderByG_U_NotS.count(
				finderCache, new Object[] {groupId, userId, status});
		}
	}

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_NotS(long groupId, long userId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_NotS(groupId, userId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = findByG_U_NotS(
				groupId, userId, status);

			bookmarksEntries = InlineSQLHelperUtil.filter(
				bookmarksEntries, groupId);

			return bookmarksEntries.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_U_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_NOTS_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_U_NOTS_GROUPID_2 =
		"bookmarksEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_NOTS_USERID_2 =
		"bookmarksEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_NOTS_STATUS_2 =
		"bookmarksEntry.status != ?";

	private FinderPath _finderPathWithPaginationFindByG_F_S;
	private FinderPath _finderPathWithoutPaginationFindByG_F_S;
	private FinderPath _finderPathCountByG_F_S;
	private FinderPath _finderPathWithPaginationCountByG_F_S;

	/**
	 * Returns all the bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_S(
		long groupId, long folderId, int status) {

		return findByG_F_S(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_S(
		long groupId, long folderId, int status, int start, int end) {

		return findByG_F_S(groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_F_S(
			groupId, folderId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_F_S;
					finderArgs = new Object[] {groupId, folderId, status};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_F_S;
				finderArgs = new Object[] {
					groupId, folderId, status, start, end, orderByComparator
				};
			}

			List<BookmarksEntry> list = null;

			if (useFinderCache) {
				list = (List<BookmarksEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (BookmarksEntry bookmarksEntry : list) {
						if ((groupId != bookmarksEntry.getGroupId()) ||
							(folderId != bookmarksEntry.getFolderId()) ||
							(status != bookmarksEntry.getStatus())) {

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

				sb.append(_SQL_SELECT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

				sb.append(_FINDER_COLUMN_G_F_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(BookmarksEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

					queryPos.add(status);

					list = (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByG_F_S_First(
			long groupId, long folderId, int status,
			OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByG_F_S_First(
			groupId, folderId, status, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", folderId=");
		sb.append(folderId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByG_F_S_First(
		long groupId, long folderId, int status,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		List<BookmarksEntry> list = findByG_F_S(
			groupId, folderId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_S(
		long groupId, long folderId, int status) {

		return filterFindByG_F_S(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_S(
		long groupId, long folderId, int status, int start, int end) {

		return filterFindByG_F_S(groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_S(
				groupId, folderId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F_S(
					groupId, folderId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_F_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			queryPos.add(status);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_S(
		long groupId, long[] folderIds, int status) {

		return filterFindByG_F_S(
			groupId, folderIds, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_S(
		long groupId, long[] folderIds, int status, int start, int end) {

		return filterFindByG_F_S(groupId, folderIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_S(
		long groupId, long[] folderIds, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_S(
				groupId, folderIds, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F_S(
					groupId, folderIds, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_S_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_F_S_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_S(
		long groupId, long[] folderIds, int status) {

		return findByG_F_S(
			groupId, folderIds, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_S(
		long groupId, long[] folderIds, int status, int start, int end) {

		return findByG_F_S(groupId, folderIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_S(
		long groupId, long[] folderIds, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_F_S(
			groupId, folderIds, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_S(
		long groupId, long[] folderIds, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		if (folderIds.length == 1) {
			return findByG_F_S(
				groupId, folderIds[0], status, start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						groupId, StringUtil.merge(folderIds), status
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(folderIds), status, start, end,
					orderByComparator
				};
			}

			List<BookmarksEntry> list = null;

			if (useFinderCache) {
				list = (List<BookmarksEntry>)finderCache.getResult(
					_finderPathWithPaginationFindByG_F_S, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (BookmarksEntry bookmarksEntry : list) {
						if ((groupId != bookmarksEntry.getGroupId()) ||
							!ArrayUtil.contains(
								folderIds, bookmarksEntry.getFolderId()) ||
							(status != bookmarksEntry.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

				if (folderIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_F_S_FOLDERID_7);

					sb.append(StringUtil.merge(folderIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_F_S_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(BookmarksEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(status);

					list = (List<BookmarksEntry>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_F_S, finderArgs,
							list);
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
	 * Removes all the bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 */
	@Override
	public void removeByG_F_S(long groupId, long folderId, int status) {
		for (BookmarksEntry bookmarksEntry :
				findByG_F_S(
					groupId, folderId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(bookmarksEntry);
		}
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_F_S(long groupId, long folderId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			FinderPath finderPath = _finderPathCountByG_F_S;

			Object[] finderArgs = new Object[] {groupId, folderId, status};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

				sb.append(_FINDER_COLUMN_G_F_S_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

					queryPos.add(status);

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
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_F_S(long groupId, long[] folderIds, int status) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(folderIds), status
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_F_S, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

				if (folderIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_F_S_FOLDERID_7);

					sb.append(StringUtil.merge(folderIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_F_S_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(status);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_F_S, finderArgs,
						count);
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

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_S(long groupId, long folderId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_S(groupId, folderId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = findByG_F_S(
				groupId, folderId, status);

			bookmarksEntries = InlineSQLHelperUtil.filter(
				bookmarksEntries, groupId);

			return bookmarksEntries.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_F_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			queryPos.add(status);

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

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_S(long groupId, long[] folderIds, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_S(groupId, folderIds, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = InlineSQLHelperUtil.filter(
				findByG_F_S(groupId, folderIds, status), groupId);

			return bookmarksEntries.size();
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_S_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_F_S_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_F_S_GROUPID_2 =
		"bookmarksEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_S_FOLDERID_2 =
		"bookmarksEntry.folderId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_S_FOLDERID_7 =
		"bookmarksEntry.folderId IN (";

	private static final String _FINDER_COLUMN_G_F_S_STATUS_2 =
		"bookmarksEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_F_NotS;
	private FinderPath _finderPathWithPaginationCountByG_F_NotS;

	/**
	 * Returns all the bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_NotS(
		long groupId, long folderId, int status) {

		return findByG_F_NotS(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_NotS(
		long groupId, long folderId, int status, int start, int end) {

		return findByG_F_NotS(groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_NotS(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_F_NotS(
			groupId, folderId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_NotS(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			finderPath = _finderPathWithPaginationFindByG_F_NotS;
			finderArgs = new Object[] {
				groupId, folderId, status, start, end, orderByComparator
			};

			List<BookmarksEntry> list = null;

			if (useFinderCache) {
				list = (List<BookmarksEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (BookmarksEntry bookmarksEntry : list) {
						if ((groupId != bookmarksEntry.getGroupId()) ||
							(folderId != bookmarksEntry.getFolderId()) ||
							(status == bookmarksEntry.getStatus())) {

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

				sb.append(_SQL_SELECT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_NOTS_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_NOTS_FOLDERID_2);

				sb.append(_FINDER_COLUMN_G_F_NOTS_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(BookmarksEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

					queryPos.add(status);

					list = (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByG_F_NotS_First(
			long groupId, long folderId, int status,
			OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByG_F_NotS_First(
			groupId, folderId, status, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", folderId=");
		sb.append(folderId);

		sb.append(", status!=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByG_F_NotS_First(
		long groupId, long folderId, int status,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		List<BookmarksEntry> list = findByG_F_NotS(
			groupId, folderId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long folderId, int status) {

		return filterFindByG_F_NotS(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long folderId, int status, int start, int end) {

		return filterFindByG_F_NotS(
			groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_NotS(
				groupId, folderId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F_NotS(
					groupId, folderId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_NOTS_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_F_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			queryPos.add(status);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long[] folderIds, int status) {

		return filterFindByG_F_NotS(
			groupId, folderIds, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long[] folderIds, int status, int start, int end) {

		return filterFindByG_F_NotS(
			groupId, folderIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long[] folderIds, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_NotS(
				groupId, folderIds, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F_NotS(
					groupId, folderIds, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_NOTS_GROUPID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_NOTS_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_F_NOTS_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_NotS(
		long groupId, long[] folderIds, int status) {

		return findByG_F_NotS(
			groupId, folderIds, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_NotS(
		long groupId, long[] folderIds, int status, int start, int end) {

		return findByG_F_NotS(groupId, folderIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_NotS(
		long groupId, long[] folderIds, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_F_NotS(
			groupId, folderIds, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_F_NotS(
		long groupId, long[] folderIds, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		if (folderIds.length == 1) {
			return findByG_F_NotS(
				groupId, folderIds[0], status, start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						groupId, StringUtil.merge(folderIds), status
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(folderIds), status, start, end,
					orderByComparator
				};
			}

			List<BookmarksEntry> list = null;

			if (useFinderCache) {
				list = (List<BookmarksEntry>)finderCache.getResult(
					_finderPathWithPaginationFindByG_F_NotS, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (BookmarksEntry bookmarksEntry : list) {
						if ((groupId != bookmarksEntry.getGroupId()) ||
							!ArrayUtil.contains(
								folderIds, bookmarksEntry.getFolderId()) ||
							(status == bookmarksEntry.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_NOTS_GROUPID_2);

				if (folderIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_F_NOTS_FOLDERID_7);

					sb.append(StringUtil.merge(folderIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_F_NOTS_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(BookmarksEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(status);

					list = (List<BookmarksEntry>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_F_NotS, finderArgs,
							list);
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
	 * Removes all the bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 */
	@Override
	public void removeByG_F_NotS(long groupId, long folderId, int status) {
		for (BookmarksEntry bookmarksEntry :
				findByG_F_NotS(
					groupId, folderId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(bookmarksEntry);
		}
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_F_NotS(long groupId, long folderId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			FinderPath finderPath = _finderPathWithPaginationCountByG_F_NotS;

			Object[] finderArgs = new Object[] {groupId, folderId, status};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_NOTS_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_NOTS_FOLDERID_2);

				sb.append(_FINDER_COLUMN_G_F_NOTS_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

					queryPos.add(status);

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
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_F_NotS(long groupId, long[] folderIds, int status) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(folderIds), status
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_F_NotS, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_F_NOTS_GROUPID_2);

				if (folderIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_F_NOTS_FOLDERID_7);

					sb.append(StringUtil.merge(folderIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_F_NOTS_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(status);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_F_NotS, finderArgs,
						count);
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

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_NotS(long groupId, long folderId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_NotS(groupId, folderId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = findByG_F_NotS(
				groupId, folderId, status);

			bookmarksEntries = InlineSQLHelperUtil.filter(
				bookmarksEntries, groupId);

			return bookmarksEntries.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_F_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_NOTS_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_F_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			queryPos.add(status);

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

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_NotS(
		long groupId, long[] folderIds, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_NotS(groupId, folderIds, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = InlineSQLHelperUtil.filter(
				findByG_F_NotS(groupId, folderIds, status), groupId);

			return bookmarksEntries.size();
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_F_NOTS_GROUPID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_NOTS_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_F_NOTS_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_F_NOTS_GROUPID_2 =
		"bookmarksEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_NOTS_FOLDERID_2 =
		"bookmarksEntry.folderId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_NOTS_FOLDERID_7 =
		"bookmarksEntry.folderId IN (";

	private static final String _FINDER_COLUMN_G_F_NOTS_STATUS_2 =
		"bookmarksEntry.status != ?";

	private FinderPath _finderPathWithPaginationFindByG_U_F_S;
	private FinderPath _finderPathWithoutPaginationFindByG_U_F_S;
	private FinderPath _finderPathCountByG_U_F_S;
	private FinderPath _finderPathWithPaginationCountByG_U_F_S;

	/**
	 * Returns all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long folderId, int status) {

		return findByG_U_F_S(
			groupId, userId, folderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long folderId, int status, int start,
		int end) {

		return findByG_U_F_S(
			groupId, userId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long folderId, int status, int start,
		int end, OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_U_F_S(
			groupId, userId, folderId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long folderId, int status, int start,
		int end, OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_U_F_S;
					finderArgs = new Object[] {
						groupId, userId, folderId, status
					};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_U_F_S;
				finderArgs = new Object[] {
					groupId, userId, folderId, status, start, end,
					orderByComparator
				};
			}

			List<BookmarksEntry> list = null;

			if (useFinderCache) {
				list = (List<BookmarksEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (BookmarksEntry bookmarksEntry : list) {
						if ((groupId != bookmarksEntry.getGroupId()) ||
							(userId != bookmarksEntry.getUserId()) ||
							(folderId != bookmarksEntry.getFolderId()) ||
							(status != bookmarksEntry.getStatus())) {

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
						6 + (orderByComparator.getOrderByFields().length * 2));
				}
				else {
					sb = new StringBundler(6);
				}

				sb.append(_SQL_SELECT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_U_F_S_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_U_F_S_USERID_2);

				sb.append(_FINDER_COLUMN_G_U_F_S_FOLDERID_2);

				sb.append(_FINDER_COLUMN_G_U_F_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(BookmarksEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(userId);

					queryPos.add(folderId);

					queryPos.add(status);

					list = (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry
	 * @throws NoSuchEntryException if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry findByG_U_F_S_First(
			long groupId, long userId, long folderId, int status,
			OrderByComparator<BookmarksEntry> orderByComparator)
		throws NoSuchEntryException {

		BookmarksEntry bookmarksEntry = fetchByG_U_F_S_First(
			groupId, userId, folderId, status, orderByComparator);

		if (bookmarksEntry != null) {
			return bookmarksEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", folderId=");
		sb.append(folderId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	 */
	@Override
	public BookmarksEntry fetchByG_U_F_S_First(
		long groupId, long userId, long folderId, int status,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		List<BookmarksEntry> list = findByG_U_F_S(
			groupId, userId, folderId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long folderId, int status) {

		return filterFindByG_U_F_S(
			groupId, userId, folderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long folderId, int status, int start,
		int end) {

		return filterFindByG_U_F_S(
			groupId, userId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long folderId, int status, int start,
		int end, OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F_S(
				groupId, userId, folderId, status, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_U_F_S(
					groupId, userId, folderId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_U_F_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_F_S_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_F_S_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_U_F_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			queryPos.add(folderId);

			queryPos.add(status);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status) {

		return filterFindByG_U_F_S(
			groupId, userId, folderIds, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status, int start,
		int end) {

		return filterFindByG_U_F_S(
			groupId, userId, folderIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public List<BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status, int start,
		int end, OrderByComparator<BookmarksEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F_S(
				groupId, userId, folderIds, status, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_U_F_S(
					groupId, userId, folderIds, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_U_F_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_F_S_USERID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_U_F_S_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_U_F_S_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(BookmarksEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, BookmarksEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, BookmarksEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			queryPos.add(status);

			return (List<BookmarksEntry>)QueryUtil.list(
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
	 * Returns all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status) {

		return findByG_U_F_S(
			groupId, userId, folderIds, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @return the range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status, int start,
		int end) {

		return findByG_U_F_S(
			groupId, userId, folderIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status, int start,
		int end, OrderByComparator<BookmarksEntry> orderByComparator) {

		return findByG_U_F_S(
			groupId, userId, folderIds, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks entries
	 * @param end the upper bound of the range of bookmarks entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks entries
	 */
	@Override
	public List<BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status, int start,
		int end, OrderByComparator<BookmarksEntry> orderByComparator,
		boolean useFinderCache) {

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		if (folderIds.length == 1) {
			return findByG_U_F_S(
				groupId, userId, folderIds[0], status, start, end,
				orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						groupId, userId, StringUtil.merge(folderIds), status
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, userId, StringUtil.merge(folderIds), status, start,
					end, orderByComparator
				};
			}

			List<BookmarksEntry> list = null;

			if (useFinderCache) {
				list = (List<BookmarksEntry>)finderCache.getResult(
					_finderPathWithPaginationFindByG_U_F_S, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (BookmarksEntry bookmarksEntry : list) {
						if ((groupId != bookmarksEntry.getGroupId()) ||
							(userId != bookmarksEntry.getUserId()) ||
							!ArrayUtil.contains(
								folderIds, bookmarksEntry.getFolderId()) ||
							(status != bookmarksEntry.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_U_F_S_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_U_F_S_USERID_2);

				if (folderIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_U_F_S_FOLDERID_7);

					sb.append(StringUtil.merge(folderIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_U_F_S_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(BookmarksEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(userId);

					queryPos.add(status);

					list = (List<BookmarksEntry>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_U_F_S, finderArgs,
							list);
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
	 * Removes all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 */
	@Override
	public void removeByG_U_F_S(
		long groupId, long userId, long folderId, int status) {

		for (BookmarksEntry bookmarksEntry :
				findByG_U_F_S(
					groupId, userId, folderId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(bookmarksEntry);
		}
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_U_F_S(
		long groupId, long userId, long folderId, int status) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			FinderPath finderPath = _finderPathCountByG_U_F_S;

			Object[] finderArgs = new Object[] {
				groupId, userId, folderId, status
			};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(5);

				sb.append(_SQL_COUNT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_U_F_S_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_U_F_S_USERID_2);

				sb.append(_FINDER_COLUMN_G_U_F_S_FOLDERID_2);

				sb.append(_FINDER_COLUMN_G_U_F_S_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(userId);

					queryPos.add(folderId);

					queryPos.add(status);

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
	}

	/**
	 * Returns the number of bookmarks entries where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the number of matching bookmarks entries
	 */
	@Override
	public int countByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status) {

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					BookmarksEntry.class)) {

			Object[] finderArgs = new Object[] {
				groupId, userId, StringUtil.merge(folderIds), status
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_U_F_S, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_BOOKMARKSENTRY_WHERE);

				sb.append(_FINDER_COLUMN_G_U_F_S_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_U_F_S_USERID_2);

				if (folderIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_U_F_S_FOLDERID_7);

					sb.append(StringUtil.merge(folderIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_U_F_S_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(userId);

					queryPos.add(status);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_U_F_S, finderArgs,
						count);
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

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_F_S(
		long groupId, long userId, long folderId, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_F_S(groupId, userId, folderId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = findByG_U_F_S(
				groupId, userId, folderId, status);

			bookmarksEntries = InlineSQLHelperUtil.filter(
				bookmarksEntries, groupId);

			return bookmarksEntries.size();
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_U_F_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_F_S_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_F_S_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_U_F_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			queryPos.add(folderId);

			queryPos.add(status);

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

	/**
	 * Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param status the status
	 * @return the number of matching bookmarks entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_F_S(groupId, userId, folderIds, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<BookmarksEntry> bookmarksEntries = InlineSQLHelperUtil.filter(
				findByG_U_F_S(groupId, userId, folderIds, status), groupId);

			return bookmarksEntries.size();
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_U_F_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_F_S_USERID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_U_F_S_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_U_F_S_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), BookmarksEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_U_F_S_GROUPID_2 =
		"bookmarksEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_F_S_USERID_2 =
		"bookmarksEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_F_S_FOLDERID_2 =
		"bookmarksEntry.folderId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_F_S_FOLDERID_7 =
		"bookmarksEntry.folderId IN (";

	private static final String _FINDER_COLUMN_G_U_F_S_STATUS_2 =
		"bookmarksEntry.status = ?";

	public BookmarksEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(BookmarksEntry.class);

		setModelImplClass(BookmarksEntryImpl.class);
		setModelPKClass(long.class);

		setTable(BookmarksEntryTable.INSTANCE);
	}

	/**
	 * Creates a new bookmarks entry with the primary key. Does not add the bookmarks entry to the database.
	 *
	 * @param entryId the primary key for the new bookmarks entry
	 * @return the new bookmarks entry
	 */
	@Override
	public BookmarksEntry create(long entryId) {
		BookmarksEntry bookmarksEntry = new BookmarksEntryImpl();

		bookmarksEntry.setNew(true);
		bookmarksEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		bookmarksEntry.setUuid(uuid);

		bookmarksEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return bookmarksEntry;
	}

	/**
	 * Removes the bookmarks entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the bookmarks entry
	 * @return the bookmarks entry that was removed
	 * @throws NoSuchEntryException if a bookmarks entry with the primary key could not be found
	 */
	@Override
	public BookmarksEntry remove(long entryId) throws NoSuchEntryException {
		return remove((Serializable)entryId);
	}

	@Override
	protected BookmarksEntry removeImpl(BookmarksEntry bookmarksEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(bookmarksEntry)) {
				bookmarksEntry = (BookmarksEntry)session.get(
					BookmarksEntryImpl.class,
					bookmarksEntry.getPrimaryKeyObj());
			}

			if ((bookmarksEntry != null) &&
				ctPersistenceHelper.isRemove(bookmarksEntry)) {

				session.delete(bookmarksEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (bookmarksEntry != null) {
			clearCache(bookmarksEntry);
		}

		return bookmarksEntry;
	}

	@Override
	public BookmarksEntry updateImpl(BookmarksEntry bookmarksEntry) {
		boolean isNew = bookmarksEntry.isNew();

		if (!(bookmarksEntry instanceof BookmarksEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(bookmarksEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					bookmarksEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in bookmarksEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BookmarksEntry implementation " +
					bookmarksEntry.getClass());
		}

		BookmarksEntryModelImpl bookmarksEntryModelImpl =
			(BookmarksEntryModelImpl)bookmarksEntry;

		if (Validator.isNull(bookmarksEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			bookmarksEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (bookmarksEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				bookmarksEntry.setCreateDate(date);
			}
			else {
				bookmarksEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!bookmarksEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				bookmarksEntry.setModifiedDate(date);
			}
			else {
				bookmarksEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(bookmarksEntry)) {
				if (!isNew) {
					session.evict(
						BookmarksEntryImpl.class,
						bookmarksEntry.getPrimaryKeyObj());
				}

				session.save(bookmarksEntry);
			}
			else {
				bookmarksEntry = (BookmarksEntry)session.merge(bookmarksEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(bookmarksEntry, false);

		if (isNew) {
			bookmarksEntry.setNew(false);
		}

		bookmarksEntry.resetOriginalValues();

		return bookmarksEntry;
	}

	/**
	 * Returns the bookmarks entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the bookmarks entry
	 * @return the bookmarks entry
	 * @throws NoSuchEntryException if a bookmarks entry with the primary key could not be found
	 */
	@Override
	public BookmarksEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)entryId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the bookmarks entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the bookmarks entry
	 * @return the bookmarks entry, or <code>null</code> if a bookmarks entry with the primary key could not be found
	 */
	@Override
	public BookmarksEntry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
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
		return "entryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BOOKMARKSENTRY;
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
		return BookmarksEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "BookmarksEntry";
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
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("folderId");
		ctMergeColumnNames.add("treePath");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("url");
		ctMergeColumnNames.add("description");
		ctMergeColumnNames.add("priority");
		ctMergeColumnNames.add("lastPublishDate");
		ctMergeColumnNames.add("status");
		ctMergeColumnNames.add("statusByUserId");
		ctMergeColumnNames.add("statusByUserName");
		ctMergeColumnNames.add("statusDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("entryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the bookmarks entry persistence.
	 */
	@Activate
	public void activate() {
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
			_SQL_SELECT_BOOKMARKSENTRY_WHERE, _SQL_COUNT_BOOKMARKSENTRY_WHERE,
			BookmarksEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"bookmarksEntry.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, BookmarksEntry::getUuid));

		_finderPathFetchByUUID_G = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false,
			convertNullFunction(BookmarksEntry::getUuid),
			BookmarksEntry::getGroupId);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_BOOKMARKSENTRY_WHERE,
			"",
			new FinderColumn<>(
				"bookmarksEntry.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, BookmarksEntry::getUuid),
			new FinderColumn<>(
				"bookmarksEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, BookmarksEntry::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_BOOKMARKSENTRY_WHERE,
				_SQL_COUNT_BOOKMARKSENTRY_WHERE,
				BookmarksEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"bookmarksEntry.", "uuid", FinderColumn.Type.STRING, "=",
					true, true, BookmarksEntry::getUuid),
				new FinderColumn<>(
					"bookmarksEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, BookmarksEntry::getCompanyId));

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

		_collectionPersistenceFinderByCompanyId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCompanyId,
				_finderPathWithoutPaginationFindByCompanyId,
				_finderPathCountByCompanyId, _SQL_SELECT_BOOKMARKSENTRY_WHERE,
				_SQL_COUNT_BOOKMARKSENTRY_WHERE,
				BookmarksEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"bookmarksEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, BookmarksEntry::getCompanyId));

		_finderPathWithPaginationFindByG_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "folderId"}, true);

		_finderPathWithoutPaginationFindByG_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "folderId"}, true);

		_finderPathCountByG_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "folderId"}, false);

		_finderPathWithPaginationCountByG_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "folderId"}, false);

		_finderPathWithPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "status"}, true);

		_finderPathWithoutPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, true);

		_finderPathCountByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, false);

		_collectionPersistenceFinderByG_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_S,
			_finderPathWithoutPaginationFindByG_S, _finderPathCountByG_S,
			_SQL_SELECT_BOOKMARKSENTRY_WHERE, _SQL_COUNT_BOOKMARKSENTRY_WHERE,
			BookmarksEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"bookmarksEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, BookmarksEntry::getGroupId),
			new FinderColumn<>(
				"bookmarksEntry.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, BookmarksEntry::getStatus));

		_finderPathWithPaginationFindByG_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_NotS",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "status"}, true);

		_finderPathWithPaginationCountByG_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_NotS",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, false);

		_collectionPersistenceFinderByG_NotS =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_NotS, null,
				_finderPathWithPaginationCountByG_NotS,
				_SQL_SELECT_BOOKMARKSENTRY_WHERE,
				_SQL_COUNT_BOOKMARKSENTRY_WHERE,
				BookmarksEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"bookmarksEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, BookmarksEntry::getGroupId),
				new FinderColumn<>(
					"bookmarksEntry.", "status", FinderColumn.Type.INTEGER,
					"!=", true, true, BookmarksEntry::getStatus));

		_finderPathWithPaginationFindByC_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_NotS",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "status"}, true);

		_finderPathWithPaginationCountByC_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotS",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, false);

		_collectionPersistenceFinderByC_NotS =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_NotS, null,
				_finderPathWithPaginationCountByC_NotS,
				_SQL_SELECT_BOOKMARKSENTRY_WHERE,
				_SQL_COUNT_BOOKMARKSENTRY_WHERE,
				BookmarksEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"bookmarksEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, BookmarksEntry::getCompanyId),
				new FinderColumn<>(
					"bookmarksEntry.", "status", FinderColumn.Type.INTEGER,
					"!=", true, true, BookmarksEntry::getStatus));

		_finderPathWithPaginationFindByG_U_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId", "status"}, true);

		_finderPathWithoutPaginationFindByG_U_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "userId", "status"}, true);

		_finderPathCountByG_U_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "userId", "status"}, false);

		_collectionPersistenceFinderByG_U_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_U_S,
			_finderPathWithoutPaginationFindByG_U_S, _finderPathCountByG_U_S,
			_SQL_SELECT_BOOKMARKSENTRY_WHERE, _SQL_COUNT_BOOKMARKSENTRY_WHERE,
			BookmarksEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"bookmarksEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, BookmarksEntry::getGroupId),
			new FinderColumn<>(
				"bookmarksEntry.", "userId", FinderColumn.Type.LONG, "=", true,
				true, BookmarksEntry::getUserId),
			new FinderColumn<>(
				"bookmarksEntry.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, BookmarksEntry::getStatus));

		_finderPathWithPaginationFindByG_U_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId", "status"}, true);

		_finderPathWithPaginationCountByG_U_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_U_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "userId", "status"}, false);

		_collectionPersistenceFinderByG_U_NotS =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_U_NotS, null,
				_finderPathWithPaginationCountByG_U_NotS,
				_SQL_SELECT_BOOKMARKSENTRY_WHERE,
				_SQL_COUNT_BOOKMARKSENTRY_WHERE,
				BookmarksEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"bookmarksEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, BookmarksEntry::getGroupId),
				new FinderColumn<>(
					"bookmarksEntry.", "userId", FinderColumn.Type.LONG, "=",
					true, true, BookmarksEntry::getUserId),
				new FinderColumn<>(
					"bookmarksEntry.", "status", FinderColumn.Type.INTEGER,
					"!=", true, true, BookmarksEntry::getStatus));

		_finderPathWithPaginationFindByG_F_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "folderId", "status"}, true);

		_finderPathWithoutPaginationFindByG_F_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "folderId", "status"}, true);

		_finderPathCountByG_F_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "folderId", "status"}, false);

		_finderPathWithPaginationCountByG_F_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "folderId", "status"}, false);

		_finderPathWithPaginationFindByG_F_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "folderId", "status"}, true);

		_finderPathWithPaginationCountByG_F_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "folderId", "status"}, false);

		_finderPathWithPaginationFindByG_U_F_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId", "folderId", "status"}, true);

		_finderPathWithoutPaginationFindByG_U_F_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "userId", "folderId", "status"}, true);

		_finderPathCountByG_U_F_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "userId", "folderId", "status"}, false);

		_finderPathWithPaginationCountByG_U_F_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_U_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "userId", "folderId", "status"}, false);

		BookmarksEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		BookmarksEntryUtil.setPersistence(null);

		entityCache.removeCache(BookmarksEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = BookmarksPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = BookmarksPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BookmarksPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _ENTITY_ALIAS_PREFIX =
		BookmarksEntryModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_BOOKMARKSENTRY =
		"SELECT bookmarksEntry FROM BookmarksEntry bookmarksEntry";

	private static final String _SQL_SELECT_BOOKMARKSENTRY_WHERE =
		"SELECT bookmarksEntry FROM BookmarksEntry bookmarksEntry WHERE ";

	private static final String _SQL_COUNT_BOOKMARKSENTRY_WHERE =
		"SELECT COUNT(bookmarksEntry) FROM BookmarksEntry bookmarksEntry WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"bookmarksEntry.entryId";

	private static final String _FILTER_SQL_SELECT_BOOKMARKSENTRY_WHERE =
		"SELECT DISTINCT {bookmarksEntry.*} FROM BookmarksEntry bookmarksEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {BookmarksEntry.*} FROM (SELECT DISTINCT bookmarksEntry.entryId FROM BookmarksEntry bookmarksEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_BOOKMARKSENTRY_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN BookmarksEntry ON TEMP_TABLE.entryId = BookmarksEntry.entryId";

	private static final String _FILTER_SQL_COUNT_BOOKMARKSENTRY_WHERE =
		"SELECT COUNT(DISTINCT bookmarksEntry.entryId) AS COUNT_VALUE FROM BookmarksEntry bookmarksEntry WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "bookmarksEntry";

	private static final String _FILTER_ENTITY_TABLE = "BookmarksEntry";

	private static final String _ORDER_BY_ENTITY_TABLE = "BookmarksEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BookmarksEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BookmarksEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1106370562