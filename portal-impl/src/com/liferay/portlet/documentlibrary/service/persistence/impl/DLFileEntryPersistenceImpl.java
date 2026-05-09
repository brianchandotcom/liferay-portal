/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import com.liferay.document.library.kernel.exception.DuplicateDLFileEntryExternalReferenceCodeException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.ArrayableFinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl;

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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the document library file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryPersistenceImpl
	extends BasePersistenceImpl<DLFileEntry, NoSuchFileEntryException>
	implements DLFileEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DLFileEntryUtil</code> to access the document library file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DLFileEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the document library file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByUuid.find(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByUuid_First(
			String uuid, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByUuid_First(uuid, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByUuid_First(
		String uuid, OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid});
	}

	/**
	 * Returns the number of document library file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByUuid.count(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<DLFileEntry>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the document library file entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFileEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByUUID_G(uuid, groupId);

		if (dlFileEntry == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFileEntryException(message);
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid, groupId},
				useFinderCache);
		}
	}

	/**
	 * Removes the document library file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library file entry that was removed
	 */
	@Override
	public DLFileEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = findByUUID_G(uuid, groupId);

		return remove(dlFileEntry);
	}

	/**
	 * Returns the number of document library file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {uuid, companyId}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid, companyId},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		_collectionPersistenceFinderByUuid_C.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid, companyId});
	}

	/**
	 * Returns the number of document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the document library file entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {groupId}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByGroupId_First(
			long groupId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByGroupId_First(
		long groupId, OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId},
			orderByComparator);
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
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
			sb.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<DLFileEntry>)QueryUtil.list(
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
	 * Removes all the document library file entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId});
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {groupId});
		}
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DLFileEntry> dlFileEntries = findByGroupId(groupId);

			dlFileEntries = InlineSQLHelperUtil.filter(dlFileEntries, groupId);

			return dlFileEntries.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"dlFileEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the document library file entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByCompanyId_First(
			long companyId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId});
	}

	/**
	 * Returns the number of document library file entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByRepositoryId;
	private FinderPath _finderPathWithoutPaginationFindByRepositoryId;
	private FinderPath _finderPathCountByRepositoryId;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByRepositoryId;

	/**
	 * Returns all the document library file entries where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByRepositoryId(long repositoryId) {
		return findByRepositoryId(
			repositoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByRepositoryId(
		long repositoryId, int start, int end) {

		return findByRepositoryId(repositoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByRepositoryId(
		long repositoryId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByRepositoryId(
			repositoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByRepositoryId(
		long repositoryId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByRepositoryId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {repositoryId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByRepositoryId_First(
			long repositoryId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByRepositoryId_First(
			repositoryId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByRepositoryId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {repositoryId}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByRepositoryId_First(
		long repositoryId, OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByRepositoryId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {repositoryId},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where repositoryId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 */
	@Override
	public void removeByRepositoryId(long repositoryId) {
		_collectionPersistenceFinderByRepositoryId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {repositoryId});
	}

	/**
	 * Returns the number of document library file entries where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByRepositoryId(long repositoryId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByRepositoryId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {repositoryId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByMimeType;
	private FinderPath _finderPathWithoutPaginationFindByMimeType;
	private FinderPath _finderPathCountByMimeType;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByMimeType;

	/**
	 * Returns all the document library file entries where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByMimeType(String mimeType) {
		return findByMimeType(
			mimeType, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByMimeType(
		String mimeType, int start, int end) {

		return findByMimeType(mimeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByMimeType(
		String mimeType, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByMimeType(mimeType, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByMimeType(
		String mimeType, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByMimeType.find(
				FinderCacheUtil.getFinderCache(), new Object[] {mimeType},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByMimeType_First(
			String mimeType, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByMimeType_First(
			mimeType, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByMimeType.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {mimeType}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByMimeType_First(
		String mimeType, OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByMimeType.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {mimeType},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where mimeType = &#63; from the database.
	 *
	 * @param mimeType the mime type
	 */
	@Override
	public void removeByMimeType(String mimeType) {
		_collectionPersistenceFinderByMimeType.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {mimeType});
	}

	/**
	 * Returns the number of document library file entries where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByMimeType(String mimeType) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByMimeType.count(
				FinderCacheUtil.getFinderCache(), new Object[] {mimeType});
		}
	}

	private FinderPath _finderPathWithPaginationFindByFileEntryTypeId;
	private FinderPath _finderPathWithoutPaginationFindByFileEntryTypeId;
	private FinderPath _finderPathCountByFileEntryTypeId;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByFileEntryTypeId;

	/**
	 * Returns all the document library file entries where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByFileEntryTypeId(long fileEntryTypeId) {
		return findByFileEntryTypeId(
			fileEntryTypeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByFileEntryTypeId(
		long fileEntryTypeId, int start, int end) {

		return findByFileEntryTypeId(fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByFileEntryTypeId(
		long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByFileEntryTypeId(
			fileEntryTypeId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByFileEntryTypeId(
		long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByFileEntryTypeId.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {fileEntryTypeId}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByFileEntryTypeId_First(
			long fileEntryTypeId,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByFileEntryTypeId_First(
			fileEntryTypeId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByFileEntryTypeId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {fileEntryTypeId}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByFileEntryTypeId_First(
		long fileEntryTypeId,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByFileEntryTypeId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {fileEntryTypeId},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where fileEntryTypeId = &#63; from the database.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 */
	@Override
	public void removeByFileEntryTypeId(long fileEntryTypeId) {
		_collectionPersistenceFinderByFileEntryTypeId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {fileEntryTypeId});
	}

	/**
	 * Returns the number of document library file entries where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByFileEntryTypeId(long fileEntryTypeId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByFileEntryTypeId.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {fileEntryTypeId});
		}
	}

	private FinderPath _finderPathWithPaginationFindBySmallImageId;
	private FinderPath _finderPathWithoutPaginationFindBySmallImageId;
	private FinderPath _finderPathCountBySmallImageId;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderBySmallImageId;

	/**
	 * Returns all the document library file entries where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findBySmallImageId(long smallImageId) {
		return findBySmallImageId(
			smallImageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findBySmallImageId(
		long smallImageId, int start, int end) {

		return findBySmallImageId(smallImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findBySmallImageId(
		long smallImageId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findBySmallImageId(
			smallImageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findBySmallImageId(
		long smallImageId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderBySmallImageId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {smallImageId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findBySmallImageId_First(
			long smallImageId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchBySmallImageId_First(
			smallImageId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderBySmallImageId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {smallImageId}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchBySmallImageId_First(
		long smallImageId, OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderBySmallImageId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {smallImageId},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where smallImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 */
	@Override
	public void removeBySmallImageId(long smallImageId) {
		_collectionPersistenceFinderBySmallImageId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {smallImageId});
	}

	/**
	 * Returns the number of document library file entries where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countBySmallImageId(long smallImageId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderBySmallImageId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {smallImageId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByLargeImageId;
	private FinderPath _finderPathWithoutPaginationFindByLargeImageId;
	private FinderPath _finderPathCountByLargeImageId;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByLargeImageId;

	/**
	 * Returns all the document library file entries where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByLargeImageId(long largeImageId) {
		return findByLargeImageId(
			largeImageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where largeImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param largeImageId the large image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByLargeImageId(
		long largeImageId, int start, int end) {

		return findByLargeImageId(largeImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where largeImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param largeImageId the large image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByLargeImageId(
		long largeImageId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByLargeImageId(
			largeImageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where largeImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param largeImageId the large image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByLargeImageId(
		long largeImageId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByLargeImageId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {largeImageId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByLargeImageId_First(
			long largeImageId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByLargeImageId_First(
			largeImageId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByLargeImageId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {largeImageId}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByLargeImageId_First(
		long largeImageId, OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByLargeImageId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {largeImageId},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where largeImageId = &#63; from the database.
	 *
	 * @param largeImageId the large image ID
	 */
	@Override
	public void removeByLargeImageId(long largeImageId) {
		_collectionPersistenceFinderByLargeImageId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {largeImageId});
	}

	/**
	 * Returns the number of document library file entries where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByLargeImageId(long largeImageId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByLargeImageId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {largeImageId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCustom1ImageId;
	private FinderPath _finderPathWithoutPaginationFindByCustom1ImageId;
	private FinderPath _finderPathCountByCustom1ImageId;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByCustom1ImageId;

	/**
	 * Returns all the document library file entries where custom1ImageId = &#63;.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom1ImageId(long custom1ImageId) {
		return findByCustom1ImageId(
			custom1ImageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where custom1ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom1ImageId(
		long custom1ImageId, int start, int end) {

		return findByCustom1ImageId(custom1ImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where custom1ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom1ImageId(
		long custom1ImageId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByCustom1ImageId(
			custom1ImageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where custom1ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom1ImageId(
		long custom1ImageId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByCustom1ImageId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {custom1ImageId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where custom1ImageId = &#63;.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByCustom1ImageId_First(
			long custom1ImageId,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByCustom1ImageId_First(
			custom1ImageId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByCustom1ImageId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {custom1ImageId}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where custom1ImageId = &#63;.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByCustom1ImageId_First(
		long custom1ImageId, OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByCustom1ImageId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {custom1ImageId},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where custom1ImageId = &#63; from the database.
	 *
	 * @param custom1ImageId the custom1 image ID
	 */
	@Override
	public void removeByCustom1ImageId(long custom1ImageId) {
		_collectionPersistenceFinderByCustom1ImageId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {custom1ImageId});
	}

	/**
	 * Returns the number of document library file entries where custom1ImageId = &#63;.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByCustom1ImageId(long custom1ImageId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByCustom1ImageId.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {custom1ImageId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCustom2ImageId;
	private FinderPath _finderPathWithoutPaginationFindByCustom2ImageId;
	private FinderPath _finderPathCountByCustom2ImageId;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByCustom2ImageId;

	/**
	 * Returns all the document library file entries where custom2ImageId = &#63;.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom2ImageId(long custom2ImageId) {
		return findByCustom2ImageId(
			custom2ImageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom2ImageId(
		long custom2ImageId, int start, int end) {

		return findByCustom2ImageId(custom2ImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom2ImageId(
		long custom2ImageId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByCustom2ImageId(
			custom2ImageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom2ImageId(
		long custom2ImageId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByCustom2ImageId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {custom2ImageId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where custom2ImageId = &#63;.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByCustom2ImageId_First(
			long custom2ImageId,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByCustom2ImageId_First(
			custom2ImageId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByCustom2ImageId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {custom2ImageId}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where custom2ImageId = &#63;.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByCustom2ImageId_First(
		long custom2ImageId, OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByCustom2ImageId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {custom2ImageId},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where custom2ImageId = &#63; from the database.
	 *
	 * @param custom2ImageId the custom2 image ID
	 */
	@Override
	public void removeByCustom2ImageId(long custom2ImageId) {
		_collectionPersistenceFinderByCustom2ImageId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {custom2ImageId});
	}

	/**
	 * Returns the number of document library file entries where custom2ImageId = &#63;.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByCustom2ImageId(long custom2ImageId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByCustom2ImageId.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {custom2ImageId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_U;
	private FinderPath _finderPathWithoutPaginationFindByG_U;
	private FinderPath _finderPathCountByG_U;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByG_U;

	/**
	 * Returns all the document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U(long groupId, long userId) {
		return findByG_U(
			groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U(
		long groupId, long userId, int start, int end) {

		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByG_U(groupId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_U.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, userId}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_U_First(
			long groupId, long userId,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByG_U_First(
			groupId, userId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByG_U.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, userId}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_U_First(
		long groupId, long userId,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_U.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId, userId},
			orderByComparator);
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U(long groupId, long userId) {
		return filterFindByG_U(
			groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U(
		long groupId, long userId, int start, int end) {

		return filterFindByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U(groupId, userId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_U(
					groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			return (List<DLFileEntry>)QueryUtil.list(
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
	 * Removes all the document library file entries where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByG_U(long groupId, long userId) {
		_collectionPersistenceFinderByG_U.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId, userId});
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_U.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, userId});
		}
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U(long groupId, long userId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U(groupId, userId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DLFileEntry> dlFileEntries = findByG_U(groupId, userId);

			dlFileEntries = InlineSQLHelperUtil.filter(dlFileEntries, groupId);

			return dlFileEntries.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
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

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 =
		"dlFileEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_USERID_2 =
		"dlFileEntry.userId = ?";

	private FinderPath _finderPathWithPaginationFindByG_F;
	private FinderPath _finderPathWithoutPaginationFindByG_F;
	private FinderPath _finderPathCountByG_F;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByG_F;

	/**
	 * Returns all the document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(long groupId, long folderId) {
		return findByG_F(
			groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(
		long groupId, long folderId, int start, int end) {

		return findByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByG_F(
			groupId, folderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_F.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, new long[] {folderId}}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_First(
			long groupId, long folderId,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByG_F_First(
			groupId, folderId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", folderId=");
		sb.append(folderId);

		sb.append("}");

		throw new NoSuchFileEntryException(sb.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_First(
		long groupId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_F.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, new long[] {folderId}}, orderByComparator);
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(long groupId, long folderId) {
		return filterFindByG_F(
			groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end) {

		return filterFindByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			return (List<DLFileEntry>)QueryUtil.list(
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
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(long groupId, long[] folderIds) {
		return filterFindByG_F(
			groupId, folderIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end) {

		return filterFindByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
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
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<DLFileEntry>)QueryUtil.list(
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
	 * Returns all the document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(long groupId, long[] folderIds) {
		return findByG_F(
			groupId, folderIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end) {

		return findByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByG_F(
			groupId, folderIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_F.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, ArrayUtil.sortedUnique(folderIds)},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByG_F(long groupId, long folderId) {
		_collectionPersistenceFinderByG_F.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, new long[] {folderId}});
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F(long groupId, long folderId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_F.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, new long[] {folderId}});
		}
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F(long groupId, long[] folderIds) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_F.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, ArrayUtil.sortedUnique(folderIds)});
		}
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F(long groupId, long folderId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DLFileEntry> dlFileEntries = findByG_F(groupId, folderId);

			dlFileEntries = InlineSQLHelperUtil.filter(dlFileEntries, groupId);

			return dlFileEntries.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
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
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F(long groupId, long[] folderIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderIds);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DLFileEntry> dlFileEntries = InlineSQLHelperUtil.filter(
				findByG_F(groupId, folderIds), groupId);

			return dlFileEntries.size();
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

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
			sb.toString(), DLFileEntry.class.getName(),
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
		"dlFileEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 =
		"dlFileEntry.folderId = ?";

	private static final String _FINDER_COLUMN_G_F_FOLDERID_7 =
		"dlFileEntry.folderId IN (";

	private FinderPath _finderPathWithPaginationFindByR_F;
	private FinderPath _finderPathWithoutPaginationFindByR_F;
	private FinderPath _finderPathCountByR_F;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByR_F;

	/**
	 * Returns all the document library file entries where repositoryId = &#63; and folderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByR_F(long repositoryId, long folderId) {
		return findByR_F(
			repositoryId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where repositoryId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByR_F(
		long repositoryId, long folderId, int start, int end) {

		return findByR_F(repositoryId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where repositoryId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByR_F(
		long repositoryId, long folderId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByR_F(
			repositoryId, folderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where repositoryId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByR_F(
		long repositoryId, long folderId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByR_F.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {repositoryId, folderId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByR_F_First(
			long repositoryId, long folderId,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByR_F_First(
			repositoryId, folderId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByR_F.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {repositoryId, folderId}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByR_F_First(
		long repositoryId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByR_F.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {repositoryId, folderId}, orderByComparator);
	}

	/**
	 * Removes all the document library file entries where repositoryId = &#63; and folderId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByR_F(long repositoryId, long folderId) {
		_collectionPersistenceFinderByR_F.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {repositoryId, folderId});
	}

	/**
	 * Returns the number of document library file entries where repositoryId = &#63; and folderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByR_F(long repositoryId, long folderId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByR_F.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {repositoryId, folderId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByF_N;
	private FinderPath _finderPathWithoutPaginationFindByF_N;
	private FinderPath _finderPathCountByF_N;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByF_N;

	/**
	 * Returns all the document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByF_N(long folderId, String name) {
		return findByF_N(
			folderId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByF_N(
		long folderId, String name, int start, int end) {

		return findByF_N(folderId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByF_N(
		long folderId, String name, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByF_N(folderId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByF_N(
		long folderId, String name, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByF_N.find(
				FinderCacheUtil.getFinderCache(), new Object[] {folderId, name},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByF_N_First(
			long folderId, String name,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByF_N_First(
			folderId, name, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByF_N.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {folderId, name}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByF_N_First(
		long folderId, String name,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByF_N.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {folderId, name},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where folderId = &#63; and name = &#63; from the database.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 */
	@Override
	public void removeByF_N(long folderId, String name) {
		_collectionPersistenceFinderByF_N.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {folderId, name});
	}

	/**
	 * Returns the number of document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByF_N(long folderId, String name) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByF_N.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {folderId, name});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_U_F;
	private FinderPath _finderPathWithoutPaginationFindByG_U_F;
	private FinderPath _finderPathCountByG_U_F;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByG_U_F;

	/**
	 * Returns all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(
		long groupId, long userId, long folderId) {

		return findByG_U_F(
			groupId, userId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(
		long groupId, long userId, long folderId, int start, int end) {

		return findByG_U_F(groupId, userId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(
		long groupId, long userId, long folderId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByG_U_F(
			groupId, userId, folderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(
		long groupId, long userId, long folderId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_U_F.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, userId, new long[] {folderId}}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_U_F_First(
			long groupId, long userId, long folderId,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByG_U_F_First(
			groupId, userId, folderId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", folderId=");
		sb.append(folderId);

		sb.append("}");

		throw new NoSuchFileEntryException(sb.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_U_F_First(
		long groupId, long userId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_U_F.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, userId, new long[] {folderId}},
			orderByComparator);
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long folderId) {

		return filterFindByG_U_F(
			groupId, userId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long folderId, int start, int end) {

		return filterFindByG_U_F(groupId, userId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long folderId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F(
				groupId, userId, folderId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_U_F(
					groupId, userId, folderId, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_F_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			queryPos.add(folderId);

			return (List<DLFileEntry>)QueryUtil.list(
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
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long[] folderIds) {

		return filterFindByG_U_F(
			groupId, userId, folderIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long[] folderIds, int start, int end) {

		return filterFindByG_U_F(groupId, userId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long[] folderIds, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F(
				groupId, userId, folderIds, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_U_F(
					groupId, userId, folderIds, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_F_USERID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_U_F_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			return (List<DLFileEntry>)QueryUtil.list(
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
	 * Returns all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(
		long groupId, long userId, long[] folderIds) {

		return findByG_U_F(
			groupId, userId, folderIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(
		long groupId, long userId, long[] folderIds, int start, int end) {

		return findByG_U_F(groupId, userId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(
		long groupId, long userId, long[] folderIds, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByG_U_F(
			groupId, userId, folderIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(
		long groupId, long userId, long[] folderIds, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_U_F.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					groupId, userId, ArrayUtil.sortedUnique(folderIds)
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByG_U_F(long groupId, long userId, long folderId) {
		_collectionPersistenceFinderByG_U_F.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, userId, new long[] {folderId}});
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_U_F(long groupId, long userId, long folderId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_U_F.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, userId, new long[] {folderId}});
		}
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_U_F(long groupId, long userId, long[] folderIds) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_U_F.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					groupId, userId, ArrayUtil.sortedUnique(folderIds)
				});
		}
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_F(long groupId, long userId, long folderId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_F(groupId, userId, folderId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DLFileEntry> dlFileEntries = findByG_U_F(
				groupId, userId, folderId);

			dlFileEntries = InlineSQLHelperUtil.filter(dlFileEntries, groupId);

			return dlFileEntries.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_F_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
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
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_F(long groupId, long userId, long[] folderIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_F(groupId, userId, folderIds);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DLFileEntry> dlFileEntries = InlineSQLHelperUtil.filter(
				findByG_U_F(groupId, userId, folderIds), groupId);

			return dlFileEntries.size();
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_F_USERID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_U_F_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
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

	private static final String _FINDER_COLUMN_G_U_F_GROUPID_2 =
		"dlFileEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_F_USERID_2 =
		"dlFileEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_F_FOLDERID_2 =
		"dlFileEntry.folderId = ?";

	private static final String _FINDER_COLUMN_G_U_F_FOLDERID_7 =
		"dlFileEntry.folderId IN (";

	private FinderPath _finderPathFetchByG_F_N;
	private UniquePersistenceFinder<DLFileEntry>
		_uniquePersistenceFinderByG_F_N;

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or throws a <code>NoSuchFileEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByG_F_N(groupId, folderId, name);

		if (dlFileEntry == null) {
			String message =
				_uniquePersistenceFinderByG_F_N.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, folderId, name});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFileEntryException(message);
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_N(long groupId, long folderId, String name) {
		return fetchByG_F_N(groupId, folderId, name, true);
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_N(
		long groupId, long folderId, String name, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _uniquePersistenceFinderByG_F_N.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, folderId, name}, useFinderCache);
		}
	}

	/**
	 * Removes the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the document library file entry that was removed
	 */
	@Override
	public DLFileEntry removeByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = findByG_F_N(groupId, folderId, name);

		return remove(dlFileEntry);
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F_N(long groupId, long folderId, String name) {
		return _uniquePersistenceFinderByG_F_N.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, folderId, name});
	}

	private FinderPath _finderPathFetchByG_F_FN;
	private UniquePersistenceFinder<DLFileEntry>
		_uniquePersistenceFinderByG_F_FN;

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; or throws a <code>NoSuchFileEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileName the file name
	 * @return the matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_FN(
			long groupId, long folderId, String fileName)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByG_F_FN(groupId, folderId, fileName);

		if (dlFileEntry == null) {
			String message =
				_uniquePersistenceFinderByG_F_FN.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, folderId, fileName});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFileEntryException(message);
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileName the file name
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_FN(
		long groupId, long folderId, String fileName) {

		return fetchByG_F_FN(groupId, folderId, fileName, true);
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileName the file name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_FN(
		long groupId, long folderId, String fileName, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _uniquePersistenceFinderByG_F_FN.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, folderId, fileName}, useFinderCache);
		}
	}

	/**
	 * Removes the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileName the file name
	 * @return the document library file entry that was removed
	 */
	@Override
	public DLFileEntry removeByG_F_FN(
			long groupId, long folderId, String fileName)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = findByG_F_FN(groupId, folderId, fileName);

		return remove(dlFileEntry);
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and fileName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileName the file name
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F_FN(long groupId, long folderId, String fileName) {
		return _uniquePersistenceFinderByG_F_FN.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, folderId, fileName});
	}

	private FinderPath _finderPathFetchByG_F_T;
	private UniquePersistenceFinder<DLFileEntry>
		_uniquePersistenceFinderByG_F_T;

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or throws a <code>NoSuchFileEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_T(long groupId, long folderId, String title)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByG_F_T(groupId, folderId, title);

		if (dlFileEntry == null) {
			String message =
				_uniquePersistenceFinderByG_F_T.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, folderId, title});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFileEntryException(message);
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_T(long groupId, long folderId, String title) {
		return fetchByG_F_T(groupId, folderId, title, true);
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_T(
		long groupId, long folderId, String title, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _uniquePersistenceFinderByG_F_T.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, folderId, title}, useFinderCache);
		}
	}

	/**
	 * Removes the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the document library file entry that was removed
	 */
	@Override
	public DLFileEntry removeByG_F_T(long groupId, long folderId, String title)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = findByG_F_T(groupId, folderId, title);

		return remove(dlFileEntry);
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and title = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F_T(long groupId, long folderId, String title) {
		return _uniquePersistenceFinderByG_F_T.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, folderId, title});
	}

	private FinderPath _finderPathWithPaginationFindByG_F_F;
	private FinderPath _finderPathWithoutPaginationFindByG_F_F;
	private FinderPath _finderPathCountByG_F_F;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByG_F_F;

	/**
	 * Returns all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(
		long groupId, long folderId, long fileEntryTypeId) {

		return findByG_F_F(
			groupId, folderId, fileEntryTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(
		long groupId, long folderId, long fileEntryTypeId, int start, int end) {

		return findByG_F_F(
			groupId, folderId, fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(
		long groupId, long folderId, long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByG_F_F(
			groupId, folderId, fileEntryTypeId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(
		long groupId, long folderId, long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_F_F.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, new long[] {folderId}, fileEntryTypeId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_F_First(
			long groupId, long folderId, long fileEntryTypeId,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByG_F_F_First(
			groupId, folderId, fileEntryTypeId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", folderId=");
		sb.append(folderId);

		sb.append(", fileEntryTypeId=");
		sb.append(fileEntryTypeId);

		sb.append("}");

		throw new NoSuchFileEntryException(sb.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_F_First(
		long groupId, long folderId, long fileEntryTypeId,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_F_F.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, new long[] {folderId}, fileEntryTypeId},
			orderByComparator);
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(
		long groupId, long folderId, long fileEntryTypeId) {

		return filterFindByG_F_F(
			groupId, folderId, fileEntryTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(
		long groupId, long folderId, long fileEntryTypeId, int start, int end) {

		return filterFindByG_F_F(
			groupId, folderId, fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(
		long groupId, long folderId, long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_F(
				groupId, folderId, fileEntryTypeId, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F_F(
					groupId, folderId, fileEntryTypeId, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			queryPos.add(fileEntryTypeId);

			return (List<DLFileEntry>)QueryUtil.list(
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
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId) {

		return filterFindByG_F_F(
			groupId, folderIds, fileEntryTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId, int start,
		int end) {

		return filterFindByG_F_F(
			groupId, folderIds, fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_F(
				groupId, folderIds, fileEntryTypeId, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F_F(
					groupId, folderIds, fileEntryTypeId, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_F_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(fileEntryTypeId);

			return (List<DLFileEntry>)QueryUtil.list(
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
	 * Returns all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId) {

		return findByG_F_F(
			groupId, folderIds, fileEntryTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId, int start,
		int end) {

		return findByG_F_F(
			groupId, folderIds, fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {

		return findByG_F_F(
			groupId, folderIds, fileEntryTypeId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_F_F.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					groupId, ArrayUtil.sortedUnique(folderIds), fileEntryTypeId
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 */
	@Override
	public void removeByG_F_F(
		long groupId, long folderId, long fileEntryTypeId) {

		_collectionPersistenceFinderByG_F_F.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, new long[] {folderId}, fileEntryTypeId});
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F_F(long groupId, long folderId, long fileEntryTypeId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_F_F.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, new long[] {folderId}, fileEntryTypeId});
		}
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByG_F_F.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					groupId, ArrayUtil.sortedUnique(folderIds), fileEntryTypeId
				});
		}
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_F(
		long groupId, long folderId, long fileEntryTypeId) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_F(groupId, folderId, fileEntryTypeId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DLFileEntry> dlFileEntries = findByG_F_F(
				groupId, folderId, fileEntryTypeId);

			dlFileEntries = InlineSQLHelperUtil.filter(dlFileEntries, groupId);

			return dlFileEntries.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
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

			queryPos.add(fileEntryTypeId);

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
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_F(groupId, folderIds, fileEntryTypeId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<DLFileEntry> dlFileEntries = InlineSQLHelperUtil.filter(
				findByG_F_F(groupId, folderIds, fileEntryTypeId), groupId);

			return dlFileEntries.size();
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_F_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(fileEntryTypeId);

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

	private static final String _FINDER_COLUMN_G_F_F_GROUPID_2 =
		"dlFileEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_F_FOLDERID_2 =
		"dlFileEntry.folderId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_F_FOLDERID_7 =
		"dlFileEntry.folderId IN (";

	private static final String _FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2 =
		"dlFileEntry.fileEntryTypeId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C_C;
	private FinderPath _finderPathCountByC_C_C;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByC_C_C;

	/**
	 * Returns all the document library file entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByC_C_C(
		long companyId, long classNameId, long classPK) {

		return findByC_C_C(
			companyId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end) {

		return findByC_C_C(companyId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByC_C_C(
			companyId, classNameId, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByC_C_C.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, classNameId, classPK}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByC_C_C_First(
			long companyId, long classNameId, long classPK,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByC_C_C_First(
			companyId, classNameId, classPK, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByC_C_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, classNameId, classPK}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByC_C_C_First(
		long companyId, long classNameId, long classPK,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByC_C_C.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, classNameId, classPK}, orderByComparator);
	}

	/**
	 * Removes all the document library file entries where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C_C(long companyId, long classNameId, long classPK) {
		_collectionPersistenceFinderByC_C_C.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, classNameId, classPK});
	}

	/**
	 * Returns the number of document library file entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByC_C_C(long companyId, long classNameId, long classPK) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByC_C_C.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, classNameId, classPK});
		}
	}

	private FinderPath _finderPathWithPaginationFindByS_L_C1_C2;
	private FinderPath _finderPathWithoutPaginationFindByS_L_C1_C2;
	private FinderPath _finderPathCountByS_L_C1_C2;
	private CollectionPersistenceFinder<DLFileEntry>
		_collectionPersistenceFinderByS_L_C1_C2;

	/**
	 * Returns all the document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByS_L_C1_C2(
		long smallImageId, long largeImageId, long custom1ImageId,
		long custom2ImageId) {

		return findByS_L_C1_C2(
			smallImageId, largeImageId, custom1ImageId, custom2ImageId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByS_L_C1_C2(
		long smallImageId, long largeImageId, long custom1ImageId,
		long custom2ImageId, int start, int end) {

		return findByS_L_C1_C2(
			smallImageId, largeImageId, custom1ImageId, custom2ImageId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByS_L_C1_C2(
		long smallImageId, long largeImageId, long custom1ImageId,
		long custom2ImageId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {

		return findByS_L_C1_C2(
			smallImageId, largeImageId, custom1ImageId, custom2ImageId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByS_L_C1_C2(
		long smallImageId, long largeImageId, long custom1ImageId,
		long custom2ImageId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByS_L_C1_C2.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					smallImageId, largeImageId, custom1ImageId, custom2ImageId
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file entry in the ordered set where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByS_L_C1_C2_First(
			long smallImageId, long largeImageId, long custom1ImageId,
			long custom2ImageId,
			OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByS_L_C1_C2_First(
			smallImageId, largeImageId, custom1ImageId, custom2ImageId,
			orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			_collectionPersistenceFinderByS_L_C1_C2.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {
					smallImageId, largeImageId, custom1ImageId, custom2ImageId
				}));
	}

	/**
	 * Returns the first document library file entry in the ordered set where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByS_L_C1_C2_First(
		long smallImageId, long largeImageId, long custom1ImageId,
		long custom2ImageId, OrderByComparator<DLFileEntry> orderByComparator) {

		return _collectionPersistenceFinderByS_L_C1_C2.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {
				smallImageId, largeImageId, custom1ImageId, custom2ImageId
			},
			orderByComparator);
	}

	/**
	 * Removes all the document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 */
	@Override
	public void removeByS_L_C1_C2(
		long smallImageId, long largeImageId, long custom1ImageId,
		long custom2ImageId) {

		_collectionPersistenceFinderByS_L_C1_C2.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {
				smallImageId, largeImageId, custom1ImageId, custom2ImageId
			});
	}

	/**
	 * Returns the number of document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByS_L_C1_C2(
		long smallImageId, long largeImageId, long custom1ImageId,
		long custom2ImageId) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _collectionPersistenceFinderByS_L_C1_C2.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {
					smallImageId, largeImageId, custom1ImageId, custom2ImageId
				});
		}
	}

	private FinderPath _finderPathFetchByERC_G;
	private UniquePersistenceFinder<DLFileEntry>
		_uniquePersistenceFinderByERC_G;

	/**
	 * Returns the document library file entry where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchFileEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByERC_G(String externalReferenceCode, long groupId)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = fetchByERC_G(externalReferenceCode, groupId);

		if (dlFileEntry == null) {
			String message =
				_uniquePersistenceFinderByERC_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFileEntryException(message);
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByERC_G(
		String externalReferenceCode, long groupId) {

		return fetchByERC_G(externalReferenceCode, groupId, true);
	}

	/**
	 * Returns the document library file entry where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileEntry.class)) {

			return _uniquePersistenceFinderByERC_G.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {externalReferenceCode, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the document library file entry where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the document library file entry that was removed
	 */
	@Override
	public DLFileEntry removeByERC_G(String externalReferenceCode, long groupId)
		throws NoSuchFileEntryException {

		DLFileEntry dlFileEntry = findByERC_G(externalReferenceCode, groupId);

		return remove(dlFileEntry);
	}

	/**
	 * Returns the number of document library file entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByERC_G(String externalReferenceCode, long groupId) {
		return _uniquePersistenceFinderByERC_G.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {externalReferenceCode, groupId});
	}

	public DLFileEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DLFileEntry.class);

		setModelImplClass(DLFileEntryImpl.class);
		setModelPKClass(long.class);

		setTable(DLFileEntryTable.INSTANCE);
	}

	/**
	 * Creates a new document library file entry with the primary key. Does not add the document library file entry to the database.
	 *
	 * @param fileEntryId the primary key for the new document library file entry
	 * @return the new document library file entry
	 */
	@Override
	public DLFileEntry create(long fileEntryId) {
		DLFileEntry dlFileEntry = new DLFileEntryImpl();

		dlFileEntry.setNew(true);
		dlFileEntry.setPrimaryKey(fileEntryId);

		String uuid = PortalUUIDUtil.generate();

		dlFileEntry.setUuid(uuid);

		dlFileEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dlFileEntry;
	}

	/**
	 * Removes the document library file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileEntryId the primary key of the document library file entry
	 * @return the document library file entry that was removed
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry remove(long fileEntryId)
		throws NoSuchFileEntryException {

		return remove((Serializable)fileEntryId);
	}

	@Override
	protected DLFileEntry removeImpl(DLFileEntry dlFileEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileEntry)) {
				dlFileEntry = (DLFileEntry)session.get(
					DLFileEntryImpl.class, dlFileEntry.getPrimaryKeyObj());
			}

			if ((dlFileEntry != null) &&
				CTPersistenceHelperUtil.isRemove(dlFileEntry)) {

				session.delete(dlFileEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dlFileEntry != null) {
			clearCache(dlFileEntry);
		}

		return dlFileEntry;
	}

	@Override
	public DLFileEntry updateImpl(DLFileEntry dlFileEntry) {
		boolean isNew = dlFileEntry.isNew();

		if (!(dlFileEntry instanceof DLFileEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlFileEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(dlFileEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlFileEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLFileEntry implementation " +
					dlFileEntry.getClass());
		}

		DLFileEntryModelImpl dlFileEntryModelImpl =
			(DLFileEntryModelImpl)dlFileEntry;

		if (Validator.isNull(dlFileEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileEntry.setUuid(uuid);
		}

		if (Validator.isNull(dlFileEntry.getExternalReferenceCode())) {
			dlFileEntry.setExternalReferenceCode(dlFileEntry.getUuid());
		}
		else {
			if (!Objects.equals(
					dlFileEntryModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					dlFileEntry.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = dlFileEntry.getCompanyId();

					long groupId = dlFileEntry.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = dlFileEntry.getPrimaryKey();
					}

					try {
						dlFileEntry.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								DLFileEntry.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								dlFileEntry.getExternalReferenceCode(), null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			DLFileEntry ercDLFileEntry = fetchByERC_G(
				dlFileEntry.getExternalReferenceCode(),
				dlFileEntry.getGroupId());

			if (isNew) {
				if (ercDLFileEntry != null) {
					throw new DuplicateDLFileEntryExternalReferenceCodeException(
						"Duplicate document library file entry with external reference code " +
							dlFileEntry.getExternalReferenceCode() +
								" and group " + dlFileEntry.getGroupId());
				}
			}
			else {
				if ((ercDLFileEntry != null) &&
					(dlFileEntry.getFileEntryId() !=
						ercDLFileEntry.getFileEntryId())) {

					throw new DuplicateDLFileEntryExternalReferenceCodeException(
						"Duplicate document library file entry with external reference code " +
							dlFileEntry.getExternalReferenceCode() +
								" and group " + dlFileEntry.getGroupId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (dlFileEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				dlFileEntry.setCreateDate(date);
			}
			else {
				dlFileEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!dlFileEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dlFileEntry.setModifiedDate(date);
			}
			else {
				dlFileEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(dlFileEntry)) {
				if (!isNew) {
					session.evict(
						DLFileEntryImpl.class, dlFileEntry.getPrimaryKeyObj());
				}

				session.save(dlFileEntry);
			}
			else {
				dlFileEntry = (DLFileEntry)session.merge(dlFileEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(dlFileEntry, false);

		if (isNew) {
			dlFileEntry.setNew(false);
		}

		dlFileEntry.resetOriginalValues();

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry with the primary key or throws a <code>NoSuchFileEntryException</code> if it could not be found.
	 *
	 * @param fileEntryId the primary key of the document library file entry
	 * @return the document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry findByPrimaryKey(long fileEntryId)
		throws NoSuchFileEntryException {

		return findByPrimaryKey((Serializable)fileEntryId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return CTPersistenceHelperUtil.getCTPersistenceHelper();
	}

	/**
	 * Returns the document library file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileEntryId the primary key of the document library file entry
	 * @return the document library file entry, or <code>null</code> if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry fetchByPrimaryKey(long fileEntryId) {
		return fetchByPrimaryKey((Serializable)fileEntryId);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "fileEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLFILEENTRY;
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
		return DLFileEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DLFileEntry";
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
		ctStrictColumnNames.add("externalReferenceCode");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctMergeColumnNames.add("repositoryId");
		ctMergeColumnNames.add("folderId");
		ctMergeColumnNames.add("treePath");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("fileName");
		ctMergeColumnNames.add("extension");
		ctMergeColumnNames.add("mimeType");
		ctMergeColumnNames.add("title");
		ctMergeColumnNames.add("description");
		ctMergeColumnNames.add("extraSettings");
		ctMergeColumnNames.add("fileEntryTypeId");
		ctMergeColumnNames.add("version");
		ctMergeColumnNames.add("size_");
		ctMergeColumnNames.add("smallImageId");
		ctMergeColumnNames.add("largeImageId");
		ctMergeColumnNames.add("custom1ImageId");
		ctMergeColumnNames.add("custom2ImageId");
		ctMergeColumnNames.add("manualCheckInRequired");
		ctMergeColumnNames.add("displayDate");
		ctMergeColumnNames.add("expirationDate");
		ctMergeColumnNames.add("reviewDate");
		ctMergeColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("fileEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "folderId", "name"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "folderId", "fileName"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "folderId", "title"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "groupId"});
	}

	/**
	 * Initializes the document library file entry persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"}, 0, 1,
			true, null);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"}, 0, 1,
			false, null);

		_collectionPersistenceFinderByUuid = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByUuid,
			_finderPathWithoutPaginationFindByUuid, _finderPathCountByUuid,
			_SQL_SELECT_DLFILEENTRY_WHERE, _SQL_COUNT_DLFILEENTRY_WHERE,
			DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileEntry.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, DLFileEntry::getUuid));

		_finderPathFetchByUUID_G = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, 0, 1, false,
			convertNullFunction(DLFileEntry::getUuid), DLFileEntry::getGroupId);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_DLFILEENTRY_WHERE, "",
			new FinderColumn<>(
				"dlFileEntry.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, DLFileEntry::getUuid),
			new FinderColumn<>(
				"dlFileEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getGroupId));

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
			new String[] {"uuid_", "companyId"}, 0, 1, true, null);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, 0, 1, false, null);

		_collectionPersistenceFinderByUuid_C =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_C,
				_finderPathWithoutPaginationFindByUuid_C,
				_finderPathCountByUuid_C, _SQL_SELECT_DLFILEENTRY_WHERE,
				_SQL_COUNT_DLFILEENTRY_WHERE,
				DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileEntry.", "uuid", FinderColumn.Type.STRING, "=", true,
					true, DLFileEntry::getUuid),
				new FinderColumn<>(
					"dlFileEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, DLFileEntry::getCompanyId));

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_collectionPersistenceFinderByGroupId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByGroupId,
				_finderPathWithoutPaginationFindByGroupId,
				_finderPathCountByGroupId, _SQL_SELECT_DLFILEENTRY_WHERE,
				_SQL_COUNT_DLFILEENTRY_WHERE,
				DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, DLFileEntry::getGroupId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_DLFILEENTRY_WHERE,
				_SQL_COUNT_DLFILEENTRY_WHERE,
				DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, DLFileEntry::getCompanyId));

		_finderPathWithPaginationFindByRepositoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRepositoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"repositoryId"}, true);

		_finderPathWithoutPaginationFindByRepositoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRepositoryId",
			new String[] {Long.class.getName()}, new String[] {"repositoryId"},
			true);

		_finderPathCountByRepositoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRepositoryId",
			new String[] {Long.class.getName()}, new String[] {"repositoryId"},
			false);

		_collectionPersistenceFinderByRepositoryId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByRepositoryId,
				_finderPathWithoutPaginationFindByRepositoryId,
				_finderPathCountByRepositoryId, _SQL_SELECT_DLFILEENTRY_WHERE,
				_SQL_COUNT_DLFILEENTRY_WHERE,
				DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileEntry.", "repositoryId", FinderColumn.Type.LONG, "=",
					true, true, DLFileEntry::getRepositoryId));

		_finderPathWithPaginationFindByMimeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByMimeType",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"mimeType"}, true);

		_finderPathWithoutPaginationFindByMimeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByMimeType",
			new String[] {String.class.getName()}, new String[] {"mimeType"}, 0,
			1, true, null);

		_finderPathCountByMimeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMimeType",
			new String[] {String.class.getName()}, new String[] {"mimeType"}, 0,
			1, false, null);

		_collectionPersistenceFinderByMimeType =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByMimeType,
				_finderPathWithoutPaginationFindByMimeType,
				_finderPathCountByMimeType, _SQL_SELECT_DLFILEENTRY_WHERE,
				_SQL_COUNT_DLFILEENTRY_WHERE,
				DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileEntry.", "mimeType", FinderColumn.Type.STRING, "=",
					true, true, DLFileEntry::getMimeType));

		_finderPathWithPaginationFindByFileEntryTypeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryTypeId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"fileEntryTypeId"}, true);

		_finderPathWithoutPaginationFindByFileEntryTypeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryTypeId",
			new String[] {Long.class.getName()},
			new String[] {"fileEntryTypeId"}, true);

		_finderPathCountByFileEntryTypeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryTypeId",
			new String[] {Long.class.getName()},
			new String[] {"fileEntryTypeId"}, false);

		_collectionPersistenceFinderByFileEntryTypeId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByFileEntryTypeId,
				_finderPathWithoutPaginationFindByFileEntryTypeId,
				_finderPathCountByFileEntryTypeId,
				_SQL_SELECT_DLFILEENTRY_WHERE, _SQL_COUNT_DLFILEENTRY_WHERE,
				DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileEntry.", "fileEntryTypeId", FinderColumn.Type.LONG,
					"=", true, true, DLFileEntry::getFileEntryTypeId));

		_finderPathWithPaginationFindBySmallImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySmallImageId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"smallImageId"}, true);

		_finderPathWithoutPaginationFindBySmallImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySmallImageId",
			new String[] {Long.class.getName()}, new String[] {"smallImageId"},
			true);

		_finderPathCountBySmallImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySmallImageId",
			new String[] {Long.class.getName()}, new String[] {"smallImageId"},
			false);

		_collectionPersistenceFinderBySmallImageId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindBySmallImageId,
				_finderPathWithoutPaginationFindBySmallImageId,
				_finderPathCountBySmallImageId, _SQL_SELECT_DLFILEENTRY_WHERE,
				_SQL_COUNT_DLFILEENTRY_WHERE,
				DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileEntry.", "smallImageId", FinderColumn.Type.LONG, "=",
					true, true, DLFileEntry::getSmallImageId));

		_finderPathWithPaginationFindByLargeImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLargeImageId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"largeImageId"}, true);

		_finderPathWithoutPaginationFindByLargeImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLargeImageId",
			new String[] {Long.class.getName()}, new String[] {"largeImageId"},
			true);

		_finderPathCountByLargeImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLargeImageId",
			new String[] {Long.class.getName()}, new String[] {"largeImageId"},
			false);

		_collectionPersistenceFinderByLargeImageId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByLargeImageId,
				_finderPathWithoutPaginationFindByLargeImageId,
				_finderPathCountByLargeImageId, _SQL_SELECT_DLFILEENTRY_WHERE,
				_SQL_COUNT_DLFILEENTRY_WHERE,
				DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileEntry.", "largeImageId", FinderColumn.Type.LONG, "=",
					true, true, DLFileEntry::getLargeImageId));

		_finderPathWithPaginationFindByCustom1ImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCustom1ImageId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"custom1ImageId"}, true);

		_finderPathWithoutPaginationFindByCustom1ImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCustom1ImageId",
			new String[] {Long.class.getName()},
			new String[] {"custom1ImageId"}, true);

		_finderPathCountByCustom1ImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCustom1ImageId",
			new String[] {Long.class.getName()},
			new String[] {"custom1ImageId"}, false);

		_collectionPersistenceFinderByCustom1ImageId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCustom1ImageId,
				_finderPathWithoutPaginationFindByCustom1ImageId,
				_finderPathCountByCustom1ImageId, _SQL_SELECT_DLFILEENTRY_WHERE,
				_SQL_COUNT_DLFILEENTRY_WHERE,
				DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileEntry.", "custom1ImageId", FinderColumn.Type.LONG,
					"=", true, true, DLFileEntry::getCustom1ImageId));

		_finderPathWithPaginationFindByCustom2ImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCustom2ImageId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"custom2ImageId"}, true);

		_finderPathWithoutPaginationFindByCustom2ImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCustom2ImageId",
			new String[] {Long.class.getName()},
			new String[] {"custom2ImageId"}, true);

		_finderPathCountByCustom2ImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCustom2ImageId",
			new String[] {Long.class.getName()},
			new String[] {"custom2ImageId"}, false);

		_collectionPersistenceFinderByCustom2ImageId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCustom2ImageId,
				_finderPathWithoutPaginationFindByCustom2ImageId,
				_finderPathCountByCustom2ImageId, _SQL_SELECT_DLFILEENTRY_WHERE,
				_SQL_COUNT_DLFILEENTRY_WHERE,
				DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileEntry.", "custom2ImageId", FinderColumn.Type.LONG,
					"=", true, true, DLFileEntry::getCustom2ImageId));

		_finderPathWithPaginationFindByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId"}, true);

		_finderPathWithoutPaginationFindByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, true);

		_finderPathCountByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, false);

		_collectionPersistenceFinderByG_U = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_U,
			_finderPathWithoutPaginationFindByG_U, _finderPathCountByG_U,
			_SQL_SELECT_DLFILEENTRY_WHERE, _SQL_COUNT_DLFILEENTRY_WHERE,
			DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getGroupId),
			new FinderColumn<>(
				"dlFileEntry.", "userId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getUserId));

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
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "folderId"}, false);

		_collectionPersistenceFinderByG_F = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_F,
			_finderPathWithoutPaginationFindByG_F, _finderPathCountByG_F,
			_SQL_SELECT_DLFILEENTRY_WHERE, _SQL_COUNT_DLFILEENTRY_WHERE,
			DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getGroupId),
			new ArrayableFinderColumn<>(
				"dlFileEntry.", "folderId", FinderColumn.Type.LONG, "=", false,
				true, true, DLFileEntry::getFolderId));

		_finderPathWithPaginationFindByR_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"repositoryId", "folderId"}, true);

		_finderPathWithoutPaginationFindByR_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"repositoryId", "folderId"}, true);

		_finderPathCountByR_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"repositoryId", "folderId"}, false);

		_collectionPersistenceFinderByR_F = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByR_F,
			_finderPathWithoutPaginationFindByR_F, _finderPathCountByR_F,
			_SQL_SELECT_DLFILEENTRY_WHERE, _SQL_COUNT_DLFILEENTRY_WHERE,
			DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileEntry.", "repositoryId", FinderColumn.Type.LONG, "=",
				true, true, DLFileEntry::getRepositoryId),
			new FinderColumn<>(
				"dlFileEntry.", "folderId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getFolderId));

		_finderPathWithPaginationFindByF_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"folderId", "name"}, true);

		_finderPathWithoutPaginationFindByF_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByF_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"folderId", "name"}, 0, 2, true, null);

		_finderPathCountByF_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"folderId", "name"}, 0, 2, false, null);

		_collectionPersistenceFinderByF_N = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByF_N,
			_finderPathWithoutPaginationFindByF_N, _finderPathCountByF_N,
			_SQL_SELECT_DLFILEENTRY_WHERE, _SQL_COUNT_DLFILEENTRY_WHERE,
			DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileEntry.", "folderId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getFolderId),
			new FinderColumn<>(
				"dlFileEntry.", "name", FinderColumn.Type.STRING, "=", true,
				true, DLFileEntry::getName));

		_finderPathWithPaginationFindByG_U_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId", "folderId"}, true);

		_finderPathWithoutPaginationFindByG_U_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "userId", "folderId"}, true);

		_finderPathCountByG_U_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "userId", "folderId"}, false);

		_collectionPersistenceFinderByG_U_F = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_U_F,
			_finderPathWithoutPaginationFindByG_U_F, _finderPathCountByG_U_F,
			_SQL_SELECT_DLFILEENTRY_WHERE, _SQL_COUNT_DLFILEENTRY_WHERE,
			DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getGroupId),
			new FinderColumn<>(
				"dlFileEntry.", "userId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getUserId),
			new ArrayableFinderColumn<>(
				"dlFileEntry.", "folderId", FinderColumn.Type.LONG, "=", false,
				true, true, DLFileEntry::getFolderId));

		_finderPathFetchByG_F_N = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "folderId", "name"}, 0, 4, false,
			DLFileEntry::getGroupId, DLFileEntry::getFolderId,
			convertNullFunction(DLFileEntry::getName));

		_uniquePersistenceFinderByG_F_N = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_F_N, _SQL_SELECT_DLFILEENTRY_WHERE, "",
			new FinderColumn<>(
				"dlFileEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getGroupId),
			new FinderColumn<>(
				"dlFileEntry.", "folderId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getFolderId),
			new FinderColumn<>(
				"dlFileEntry.", "name", FinderColumn.Type.STRING, "=", true,
				true, DLFileEntry::getName));

		_finderPathFetchByG_F_FN = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_FN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "folderId", "fileName"}, 0, 4, false,
			DLFileEntry::getGroupId, DLFileEntry::getFolderId,
			convertNullFunction(DLFileEntry::getFileName));

		_uniquePersistenceFinderByG_F_FN = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_F_FN, _SQL_SELECT_DLFILEENTRY_WHERE, "",
			new FinderColumn<>(
				"dlFileEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getGroupId),
			new FinderColumn<>(
				"dlFileEntry.", "folderId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getFolderId),
			new FinderColumn<>(
				"dlFileEntry.", "fileName", FinderColumn.Type.STRING, "=", true,
				true, DLFileEntry::getFileName));

		_finderPathFetchByG_F_T = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "folderId", "title"}, 0, 4, false,
			DLFileEntry::getGroupId, DLFileEntry::getFolderId,
			convertNullFunction(DLFileEntry::getTitle));

		_uniquePersistenceFinderByG_F_T = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_F_T, _SQL_SELECT_DLFILEENTRY_WHERE, "",
			new FinderColumn<>(
				"dlFileEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getGroupId),
			new FinderColumn<>(
				"dlFileEntry.", "folderId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getFolderId),
			new FinderColumn<>(
				"dlFileEntry.", "title", FinderColumn.Type.STRING, "=", true,
				true, DLFileEntry::getTitle));

		_finderPathWithPaginationFindByG_F_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "folderId", "fileEntryTypeId"}, true);

		_finderPathWithoutPaginationFindByG_F_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "folderId", "fileEntryTypeId"}, true);

		_finderPathCountByG_F_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "folderId", "fileEntryTypeId"}, false);

		_collectionPersistenceFinderByG_F_F = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_F_F,
			_finderPathWithoutPaginationFindByG_F_F, _finderPathCountByG_F_F,
			_SQL_SELECT_DLFILEENTRY_WHERE, _SQL_COUNT_DLFILEENTRY_WHERE,
			DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getGroupId),
			new ArrayableFinderColumn<>(
				"dlFileEntry.", "folderId", FinderColumn.Type.LONG, "=", false,
				true, true, DLFileEntry::getFolderId),
			new FinderColumn<>(
				"dlFileEntry.", "fileEntryTypeId", FinderColumn.Type.LONG, "=",
				true, true, DLFileEntry::getFileEntryTypeId));

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

		_collectionPersistenceFinderByC_C_C = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_C_C,
			_finderPathWithoutPaginationFindByC_C_C, _finderPathCountByC_C_C,
			_SQL_SELECT_DLFILEENTRY_WHERE, _SQL_COUNT_DLFILEENTRY_WHERE,
			DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileEntry.", "companyId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getCompanyId),
			new FinderColumn<>(
				"dlFileEntry.", "classNameId", FinderColumn.Type.LONG, "=",
				true, true, DLFileEntry::getClassNameId),
			new FinderColumn<>(
				"dlFileEntry.", "classPK", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getClassPK));

		_finderPathWithPaginationFindByS_L_C1_C2 = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_L_C1_C2",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"smallImageId", "largeImageId", "custom1ImageId",
				"custom2ImageId"
			},
			true);

		_finderPathWithoutPaginationFindByS_L_C1_C2 = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_L_C1_C2",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"smallImageId", "largeImageId", "custom1ImageId",
				"custom2ImageId"
			},
			true);

		_finderPathCountByS_L_C1_C2 = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_L_C1_C2",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"smallImageId", "largeImageId", "custom1ImageId",
				"custom2ImageId"
			},
			false);

		_collectionPersistenceFinderByS_L_C1_C2 =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByS_L_C1_C2,
				_finderPathWithoutPaginationFindByS_L_C1_C2,
				_finderPathCountByS_L_C1_C2, _SQL_SELECT_DLFILEENTRY_WHERE,
				_SQL_COUNT_DLFILEENTRY_WHERE,
				DLFileEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileEntry.", "smallImageId", FinderColumn.Type.LONG, "=",
					true, true, DLFileEntry::getSmallImageId),
				new FinderColumn<>(
					"dlFileEntry.", "largeImageId", FinderColumn.Type.LONG, "=",
					true, true, DLFileEntry::getLargeImageId),
				new FinderColumn<>(
					"dlFileEntry.", "custom1ImageId", FinderColumn.Type.LONG,
					"=", true, true, DLFileEntry::getCustom1ImageId),
				new FinderColumn<>(
					"dlFileEntry.", "custom2ImageId", FinderColumn.Type.LONG,
					"=", true, true, DLFileEntry::getCustom2ImageId));

		_finderPathFetchByERC_G = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "groupId"}, 0, 1, false,
			convertNullFunction(DLFileEntry::getExternalReferenceCode),
			DLFileEntry::getGroupId);

		_uniquePersistenceFinderByERC_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_G, _SQL_SELECT_DLFILEENTRY_WHERE, "",
			new FinderColumn<>(
				"dlFileEntry.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, true,
				DLFileEntry::getExternalReferenceCode),
			new FinderColumn<>(
				"dlFileEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DLFileEntry::getGroupId));

		DLFileEntryUtil.setPersistence(this);
	}

	public void destroy() {
		DLFileEntryUtil.setPersistence(null);

		EntityCacheUtil.removeCache(DLFileEntryImpl.class.getName());
	}

	private static final String _ENTITY_ALIAS_PREFIX =
		DLFileEntryModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_DLFILEENTRY =
		"SELECT dlFileEntry FROM DLFileEntry dlFileEntry";

	private static final String _SQL_SELECT_DLFILEENTRY_WHERE =
		"SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ";

	private static final String _SQL_COUNT_DLFILEENTRY_WHERE =
		"SELECT COUNT(dlFileEntry) FROM DLFileEntry dlFileEntry WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"dlFileEntry.fileEntryId";

	private static final String _FILTER_SQL_SELECT_DLFILEENTRY_WHERE =
		"SELECT DISTINCT {dlFileEntry.*} FROM DLFileEntry dlFileEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {DLFileEntry.*} FROM (SELECT DISTINCT dlFileEntry.fileEntryId FROM DLFileEntry dlFileEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN DLFileEntry ON TEMP_TABLE.fileEntryId = DLFileEntry.fileEntryId";

	private static final String _FILTER_SQL_COUNT_DLFILEENTRY_WHERE =
		"SELECT COUNT(DISTINCT dlFileEntry.fileEntryId) AS COUNT_VALUE FROM DLFileEntry dlFileEntry WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "dlFileEntry";

	private static final String _FILTER_ENTITY_TABLE = "DLFileEntry";

	private static final String _ORDER_BY_ENTITY_TABLE = "DLFileEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DLFileEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "size"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:727894104