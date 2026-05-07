/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFileVersionTable;
import com.liferay.document.library.kernel.service.persistence.DLFileVersionPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFileVersionUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
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
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

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
 * The persistence implementation for the document library file version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileVersionPersistenceImpl
	extends BasePersistenceImpl<DLFileVersion, NoSuchFileVersionException>
	implements DLFileVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DLFileVersionUtil</code> to access the document library file version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DLFileVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<DLFileVersion>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the document library file versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByUuid.find(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUuid_First(
			String uuid, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByUuid_First(
			uuid, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		throw new NoSuchFileVersionException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUuid_First(
		String uuid, OrderByComparator<DLFileVersion> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid},
			orderByComparator);
	}

	/**
	 * Removes all the document library file versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid});
	}

	/**
	 * Returns the number of document library file versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByUuid.count(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<DLFileVersion>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFileVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUUID_G(String uuid, long groupId)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByUUID_G(uuid, groupId);

		if (dlFileVersion == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFileVersionException(message);
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid, groupId},
				useFinderCache);
		}
	}

	/**
	 * Removes the document library file version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library file version that was removed
	 */
	@Override
	public DLFileVersion removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = findByUUID_G(uuid, groupId);

		return remove(dlFileVersion);
	}

	/**
	 * Returns the number of document library file versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<DLFileVersion>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {uuid, companyId}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		throw new NoSuchFileVersionException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid, companyId},
			orderByComparator);
	}

	/**
	 * Removes all the document library file versions where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<DLFileVersion>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the document library file versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByCompanyId_First(
			long companyId, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		throw new NoSuchFileVersionException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByCompanyId_First(
		long companyId, OrderByComparator<DLFileVersion> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId},
			orderByComparator);
	}

	/**
	 * Removes all the document library file versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId});
	}

	/**
	 * Returns the number of document library file versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByFileEntryId;
	private FinderPath _finderPathWithoutPaginationFindByFileEntryId;
	private FinderPath _finderPathCountByFileEntryId;
	private CollectionPersistenceFinder<DLFileVersion>
		_collectionPersistenceFinderByFileEntryId;

	/**
	 * Returns all the document library file versions where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(long fileEntryId) {
		return findByFileEntryId(
			fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(
		long fileEntryId, int start, int end) {

		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByFileEntryId(
			fileEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByFileEntryId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {fileEntryId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByFileEntryId_First(
			long fileEntryId,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByFileEntryId_First(
			fileEntryId, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		throw new NoSuchFileVersionException(
			_collectionPersistenceFinderByFileEntryId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {fileEntryId}));
	}

	/**
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByFileEntryId_First(
		long fileEntryId, OrderByComparator<DLFileVersion> orderByComparator) {

		return _collectionPersistenceFinderByFileEntryId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {fileEntryId},
			orderByComparator);
	}

	/**
	 * Removes all the document library file versions where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 */
	@Override
	public void removeByFileEntryId(long fileEntryId) {
		_collectionPersistenceFinderByFileEntryId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {fileEntryId});
	}

	/**
	 * Returns the number of document library file versions where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByFileEntryId(long fileEntryId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByFileEntryId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {fileEntryId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByMimeType;
	private FinderPath _finderPathWithoutPaginationFindByMimeType;
	private FinderPath _finderPathCountByMimeType;
	private CollectionPersistenceFinder<DLFileVersion>
		_collectionPersistenceFinderByMimeType;

	/**
	 * Returns all the document library file versions where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(String mimeType) {
		return findByMimeType(
			mimeType, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(
		String mimeType, int start, int end) {

		return findByMimeType(mimeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(
		String mimeType, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByMimeType(mimeType, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(
		String mimeType, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByMimeType.find(
				FinderCacheUtil.getFinderCache(), new Object[] {mimeType},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByMimeType_First(
			String mimeType, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByMimeType_First(
			mimeType, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		throw new NoSuchFileVersionException(
			_collectionPersistenceFinderByMimeType.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {mimeType}));
	}

	/**
	 * Returns the first document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByMimeType_First(
		String mimeType, OrderByComparator<DLFileVersion> orderByComparator) {

		return _collectionPersistenceFinderByMimeType.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {mimeType},
			orderByComparator);
	}

	/**
	 * Removes all the document library file versions where mimeType = &#63; from the database.
	 *
	 * @param mimeType the mime type
	 */
	@Override
	public void removeByMimeType(String mimeType) {
		_collectionPersistenceFinderByMimeType.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {mimeType});
	}

	/**
	 * Returns the number of document library file versions where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByMimeType(String mimeType) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByMimeType.count(
				FinderCacheUtil.getFinderCache(), new Object[] {mimeType});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_SU;
	private FinderPath _finderPathWithoutPaginationFindByC_SU;
	private FinderPath _finderPathCountByC_SU;
	private CollectionPersistenceFinder<DLFileVersion>
		_collectionPersistenceFinderByC_SU;

	/**
	 * Returns all the document library file versions where companyId = &#63; and storeUUID = &#63;.
	 *
	 * @param companyId the company ID
	 * @param storeUUID the store uuid
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_SU(long companyId, String storeUUID) {
		return findByC_SU(
			companyId, storeUUID, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where companyId = &#63; and storeUUID = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param storeUUID the store uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_SU(
		long companyId, String storeUUID, int start, int end) {

		return findByC_SU(companyId, storeUUID, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and storeUUID = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param storeUUID the store uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_SU(
		long companyId, String storeUUID, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByC_SU(
			companyId, storeUUID, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and storeUUID = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param storeUUID the store uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_SU(
		long companyId, String storeUUID, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByC_SU.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, storeUUID}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63; and storeUUID = &#63;.
	 *
	 * @param companyId the company ID
	 * @param storeUUID the store uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByC_SU_First(
			long companyId, String storeUUID,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByC_SU_First(
			companyId, storeUUID, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		throw new NoSuchFileVersionException(
			_collectionPersistenceFinderByC_SU.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, storeUUID}));
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63; and storeUUID = &#63;.
	 *
	 * @param companyId the company ID
	 * @param storeUUID the store uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByC_SU_First(
		long companyId, String storeUUID,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return _collectionPersistenceFinderByC_SU.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, storeUUID}, orderByComparator);
	}

	/**
	 * Removes all the document library file versions where companyId = &#63; and storeUUID = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param storeUUID the store uuid
	 */
	@Override
	public void removeByC_SU(long companyId, String storeUUID) {
		_collectionPersistenceFinderByC_SU.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, storeUUID});
	}

	/**
	 * Returns the number of document library file versions where companyId = &#63; and storeUUID = &#63;.
	 *
	 * @param companyId the company ID
	 * @param storeUUID the store uuid
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByC_SU(long companyId, String storeUUID) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByC_SU.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, storeUUID});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_NotS;
	private FinderPath _finderPathWithPaginationCountByC_NotS;
	private CollectionPersistenceFinder<DLFileVersion>
		_collectionPersistenceFinderByC_NotS;

	/**
	 * Returns all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(long companyId, int status) {
		return findByC_NotS(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(
		long companyId, int status, int start, int end) {

		return findByC_NotS(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByC_NotS(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByC_NotS.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, status}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByC_NotS_First(
			long companyId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByC_NotS_First(
			companyId, status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		throw new NoSuchFileVersionException(
			_collectionPersistenceFinderByC_NotS.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, status}));
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByC_NotS_First(
		long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return _collectionPersistenceFinderByC_NotS.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId, status},
			orderByComparator);
	}

	/**
	 * Removes all the document library file versions where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_NotS(long companyId, int status) {
		_collectionPersistenceFinderByC_NotS.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId, status});
	}

	/**
	 * Returns the number of document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByC_NotS(long companyId, int status) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByC_NotS.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, status});
		}
	}

	private FinderPath _finderPathFetchByF_V;
	private UniquePersistenceFinder<DLFileVersion>
		_uniquePersistenceFinderByF_V;

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or throws a <code>NoSuchFileVersionException</code> if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByF_V(long fileEntryId, String version)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByF_V(fileEntryId, version);

		if (dlFileVersion == null) {
			String message =
				_uniquePersistenceFinderByF_V.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {fileEntryId, version});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFileVersionException(message);
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByF_V(long fileEntryId, String version) {
		return fetchByF_V(fileEntryId, version, true);
	}

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByF_V(
		long fileEntryId, String version, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _uniquePersistenceFinderByF_V.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {fileEntryId, version}, useFinderCache);
		}
	}

	/**
	 * Removes the document library file version where fileEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the document library file version that was removed
	 */
	@Override
	public DLFileVersion removeByF_V(long fileEntryId, String version)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = findByF_V(fileEntryId, version);

		return remove(dlFileVersion);
	}

	/**
	 * Returns the number of document library file versions where fileEntryId = &#63; and version = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByF_V(long fileEntryId, String version) {
		return _uniquePersistenceFinderByF_V.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {fileEntryId, version});
	}

	private FinderPath _finderPathWithPaginationFindByF_S;
	private FinderPath _finderPathWithoutPaginationFindByF_S;
	private FinderPath _finderPathCountByF_S;
	private FinderPath _finderPathWithPaginationCountByF_S;

	/**
	 * Returns all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(long fileEntryId, int status) {
		return findByF_S(
			fileEntryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(
		long fileEntryId, int status, int start, int end) {

		return findByF_S(fileEntryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(
		long fileEntryId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByF_S(
			fileEntryId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(
		long fileEntryId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByF_S;
					finderArgs = new Object[] {fileEntryId, status};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByF_S;
				finderArgs = new Object[] {
					fileEntryId, status, start, end, orderByComparator
				};
			}

			List<DLFileVersion> list = null;

			if (useFinderCache) {
				list = (List<DLFileVersion>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (DLFileVersion dlFileVersion : list) {
						if ((fileEntryId != dlFileVersion.getFileEntryId()) ||
							(status != dlFileVersion.getStatus())) {

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

				sb.append(_SQL_SELECT_DLFILEVERSION_WHERE);

				sb.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

				sb.append(_FINDER_COLUMN_F_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(fileEntryId);

					queryPos.add(status);

					list = (List<DLFileVersion>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByF_S_First(
			long fileEntryId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByF_S_First(
			fileEntryId, status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileEntryId=");
		sb.append(fileEntryId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFileVersionException(sb.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByF_S_First(
		long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {

		List<DLFileVersion> list = findByF_S(
			fileEntryId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the document library file versions where fileEntryId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param statuses the statuses
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(long fileEntryId, int[] statuses) {
		return findByF_S(
			fileEntryId, statuses, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where fileEntryId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(
		long fileEntryId, int[] statuses, int start, int end) {

		return findByF_S(fileEntryId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(
		long fileEntryId, int[] statuses, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByF_S(
			fileEntryId, statuses, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(
		long fileEntryId, int[] statuses, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		if (statuses.length == 1) {
			return findByF_S(
				fileEntryId, statuses[0], start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						fileEntryId, StringUtil.merge(statuses)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					fileEntryId, StringUtil.merge(statuses), start, end,
					orderByComparator
				};
			}

			List<DLFileVersion> list = null;

			if (useFinderCache) {
				list = (List<DLFileVersion>)FinderCacheUtil.getResult(
					_finderPathWithPaginationFindByF_S, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (DLFileVersion dlFileVersion : list) {
						if ((fileEntryId != dlFileVersion.getFileEntryId()) ||
							!ArrayUtil.contains(
								statuses, dlFileVersion.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_DLFILEVERSION_WHERE);

				sb.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_F_S_STATUS_7);

					sb.append(StringUtil.merge(statuses));

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
					sb.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(fileEntryId);

					list = (List<DLFileVersion>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathWithPaginationFindByF_S, finderArgs,
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
	 * Removes all the document library file versions where fileEntryId = &#63; and status = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 */
	@Override
	public void removeByF_S(long fileEntryId, int status) {
		for (DLFileVersion dlFileVersion :
				findByF_S(
					fileEntryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByF_S(long fileEntryId, int status) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			FinderPath finderPath = _finderPathCountByF_S;

			Object[] finderArgs = new Object[] {fileEntryId, status};

			Long count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_DLFILEVERSION_WHERE);

				sb.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

				sb.append(_FINDER_COLUMN_F_S_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(fileEntryId);

					queryPos.add(status);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of document library file versions where fileEntryId = &#63; and status = any &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param statuses the statuses
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByF_S(long fileEntryId, int[] statuses) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			Object[] finderArgs = new Object[] {
				fileEntryId, StringUtil.merge(statuses)
			};

			Long count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByF_S, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_DLFILEVERSION_WHERE);

				sb.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_F_S_STATUS_7);

					sb.append(StringUtil.merge(statuses));

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

					queryPos.add(fileEntryId);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByF_S, finderArgs, count);
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

	private static final String _FINDER_COLUMN_F_S_FILEENTRYID_2 =
		"dlFileVersion.fileEntryId = ? AND ";

	private static final String _FINDER_COLUMN_F_S_STATUS_2 =
		"dlFileVersion.status = ?";

	private static final String _FINDER_COLUMN_F_S_STATUS_7 =
		"dlFileVersion.status IN (";

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;
	private CollectionPersistenceFinder<DLFileVersion>
		_collectionPersistenceFinderByLtD_S;

	/**
	 * Returns all the document library file versions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByLtD_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {displayDate, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file version in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		throw new NoSuchFileVersionException(
			_collectionPersistenceFinderByLtD_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {displayDate, status}));
	}

	/**
	 * Returns the first document library file version in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return _collectionPersistenceFinderByLtD_S.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {displayDate, status}, orderByComparator);
	}

	/**
	 * Removes all the document library file versions where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		_collectionPersistenceFinderByLtD_S.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {displayDate, status});
	}

	/**
	 * Returns the number of document library file versions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByLtD_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {displayDate, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_F_S;
	private FinderPath _finderPathWithoutPaginationFindByG_F_S;
	private FinderPath _finderPathCountByG_F_S;
	private CollectionPersistenceFinder<DLFileVersion>
		_collectionPersistenceFinderByG_F_S;

	/**
	 * Returns all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(
		long groupId, long folderId, int status) {

		return findByG_F_S(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(
		long groupId, long folderId, int status, int start, int end) {

		return findByG_F_S(groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByG_F_S(
			groupId, folderId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByG_F_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, folderId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByG_F_S_First(
			long groupId, long folderId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByG_F_S_First(
			groupId, folderId, status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		throw new NoSuchFileVersionException(
			_collectionPersistenceFinderByG_F_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, folderId, status}));
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByG_F_S_First(
		long groupId, long folderId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_F_S.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, folderId, status}, orderByComparator);
	}

	/**
	 * Removes all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 */
	@Override
	public void removeByG_F_S(long groupId, long folderId, int status) {
		_collectionPersistenceFinderByG_F_S.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, folderId, status});
	}

	/**
	 * Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByG_F_S(long groupId, long folderId, int status) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByG_F_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, folderId, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_E_S;
	private FinderPath _finderPathWithoutPaginationFindByC_E_S;
	private FinderPath _finderPathCountByC_E_S;
	private FinderPath _finderPathWithPaginationCountByC_E_S;

	/**
	 * Returns all the document library file versions where companyId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_E_S(
		long companyId, Date expirationDate, int status) {

		return findByC_E_S(
			companyId, expirationDate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where companyId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_E_S(
		long companyId, Date expirationDate, int status, int start, int end) {

		return findByC_E_S(companyId, expirationDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_E_S(
		long companyId, Date expirationDate, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByC_E_S(
			companyId, expirationDate, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_E_S(
		long companyId, Date expirationDate, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByC_E_S;
					finderArgs = new Object[] {
						companyId, _getTime(expirationDate), status
					};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByC_E_S;
				finderArgs = new Object[] {
					companyId, _getTime(expirationDate), status, start, end,
					orderByComparator
				};
			}

			List<DLFileVersion> list = null;

			if (useFinderCache) {
				list = (List<DLFileVersion>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (DLFileVersion dlFileVersion : list) {
						if ((companyId != dlFileVersion.getCompanyId()) ||
							!Objects.equals(
								expirationDate,
								dlFileVersion.getExpirationDate()) ||
							(status != dlFileVersion.getStatus())) {

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

				sb.append(_SQL_SELECT_DLFILEVERSION_WHERE);

				sb.append(_FINDER_COLUMN_C_E_S_COMPANYID_2);

				boolean bindExpirationDate = false;

				if (expirationDate == null) {
					sb.append(_FINDER_COLUMN_C_E_S_EXPIRATIONDATE_1);
				}
				else {
					bindExpirationDate = true;

					sb.append(_FINDER_COLUMN_C_E_S_EXPIRATIONDATE_2);
				}

				sb.append(_FINDER_COLUMN_C_E_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					if (bindExpirationDate) {
						queryPos.add(new Timestamp(expirationDate.getTime()));
					}

					queryPos.add(status);

					list = (List<DLFileVersion>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first document library file version in the ordered set where companyId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByC_E_S_First(
			long companyId, Date expirationDate, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByC_E_S_First(
			companyId, expirationDate, status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", expirationDate=");
		sb.append(expirationDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFileVersionException(sb.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByC_E_S_First(
		long companyId, Date expirationDate, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {

		List<DLFileVersion> list = findByC_E_S(
			companyId, expirationDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the document library file versions where companyId = &#63; and expirationDate = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param statuses the statuses
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_E_S(
		long companyId, Date expirationDate, int[] statuses) {

		return findByC_E_S(
			companyId, expirationDate, statuses, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where companyId = &#63; and expirationDate = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param statuses the statuses
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_E_S(
		long companyId, Date expirationDate, int[] statuses, int start,
		int end) {

		return findByC_E_S(
			companyId, expirationDate, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and expirationDate = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param statuses the statuses
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_E_S(
		long companyId, Date expirationDate, int[] statuses, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByC_E_S(
			companyId, expirationDate, statuses, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and expirationDate = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param statuses the statuses
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_E_S(
		long companyId, Date expirationDate, int[] statuses, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		if (statuses.length == 1) {
			return findByC_E_S(
				companyId, expirationDate, statuses[0], start, end,
				orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						companyId, _getTime(expirationDate),
						StringUtil.merge(statuses)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, _getTime(expirationDate),
					StringUtil.merge(statuses), start, end, orderByComparator
				};
			}

			List<DLFileVersion> list = null;

			if (useFinderCache) {
				list = (List<DLFileVersion>)FinderCacheUtil.getResult(
					_finderPathWithPaginationFindByC_E_S, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (DLFileVersion dlFileVersion : list) {
						if ((companyId != dlFileVersion.getCompanyId()) ||
							!Objects.equals(
								expirationDate,
								dlFileVersion.getExpirationDate()) ||
							!ArrayUtil.contains(
								statuses, dlFileVersion.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_DLFILEVERSION_WHERE);

				sb.append(_FINDER_COLUMN_C_E_S_COMPANYID_2);

				boolean bindExpirationDate = false;

				if (expirationDate == null) {
					sb.append(_FINDER_COLUMN_C_E_S_EXPIRATIONDATE_1);
				}
				else {
					bindExpirationDate = true;

					sb.append(_FINDER_COLUMN_C_E_S_EXPIRATIONDATE_2);
				}

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_C_E_S_STATUS_7);

					sb.append(StringUtil.merge(statuses));

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
					sb.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					if (bindExpirationDate) {
						queryPos.add(new Timestamp(expirationDate.getTime()));
					}

					list = (List<DLFileVersion>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathWithPaginationFindByC_E_S, finderArgs,
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
	 * Removes all the document library file versions where companyId = &#63; and expirationDate = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	@Override
	public void removeByC_E_S(long companyId, Date expirationDate, int status) {
		for (DLFileVersion dlFileVersion :
				findByC_E_S(
					companyId, expirationDate, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where companyId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByC_E_S(long companyId, Date expirationDate, int status) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			FinderPath finderPath = _finderPathCountByC_E_S;

			Object[] finderArgs = new Object[] {
				companyId, _getTime(expirationDate), status
			};

			Long count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_DLFILEVERSION_WHERE);

				sb.append(_FINDER_COLUMN_C_E_S_COMPANYID_2);

				boolean bindExpirationDate = false;

				if (expirationDate == null) {
					sb.append(_FINDER_COLUMN_C_E_S_EXPIRATIONDATE_1);
				}
				else {
					bindExpirationDate = true;

					sb.append(_FINDER_COLUMN_C_E_S_EXPIRATIONDATE_2);
				}

				sb.append(_FINDER_COLUMN_C_E_S_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					if (bindExpirationDate) {
						queryPos.add(new Timestamp(expirationDate.getTime()));
					}

					queryPos.add(status);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of document library file versions where companyId = &#63; and expirationDate = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param expirationDate the expiration date
	 * @param statuses the statuses
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByC_E_S(
		long companyId, Date expirationDate, int[] statuses) {

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			Object[] finderArgs = new Object[] {
				companyId, _getTime(expirationDate), StringUtil.merge(statuses)
			};

			Long count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByC_E_S, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_DLFILEVERSION_WHERE);

				sb.append(_FINDER_COLUMN_C_E_S_COMPANYID_2);

				boolean bindExpirationDate = false;

				if (expirationDate == null) {
					sb.append(_FINDER_COLUMN_C_E_S_EXPIRATIONDATE_1);
				}
				else {
					bindExpirationDate = true;

					sb.append(_FINDER_COLUMN_C_E_S_EXPIRATIONDATE_2);
				}

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_C_E_S_STATUS_7);

					sb.append(StringUtil.merge(statuses));

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

					queryPos.add(companyId);

					if (bindExpirationDate) {
						queryPos.add(new Timestamp(expirationDate.getTime()));
					}

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByC_E_S, finderArgs,
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

	private static final String _FINDER_COLUMN_C_E_S_COMPANYID_2 =
		"dlFileVersion.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_E_S_EXPIRATIONDATE_1 =
		"dlFileVersion.expirationDate IS NULL AND ";

	private static final String _FINDER_COLUMN_C_E_S_EXPIRATIONDATE_2 =
		"dlFileVersion.expirationDate = ? AND ";

	private static final String _FINDER_COLUMN_C_E_S_STATUS_2 =
		"dlFileVersion.status = ?";

	private static final String _FINDER_COLUMN_C_E_S_STATUS_7 =
		"dlFileVersion.status IN (";

	private FinderPath _finderPathWithPaginationFindByG_F_T_V;
	private FinderPath _finderPathWithoutPaginationFindByG_F_T_V;
	private FinderPath _finderPathCountByG_F_T_V;
	private CollectionPersistenceFinder<DLFileVersion>
		_collectionPersistenceFinderByG_F_T_V;

	/**
	 * Returns all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(
		long groupId, long folderId, String title, String version) {

		return findByG_F_T_V(
			groupId, folderId, title, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(
		long groupId, long folderId, String title, String version, int start,
		int end) {

		return findByG_F_T_V(
			groupId, folderId, title, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(
		long groupId, long folderId, String title, String version, int start,
		int end, OrderByComparator<DLFileVersion> orderByComparator) {

		return findByG_F_T_V(
			groupId, folderId, title, version, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(
		long groupId, long folderId, String title, String version, int start,
		int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByG_F_T_V.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, folderId, title, version}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByG_F_T_V_First(
			long groupId, long folderId, String title, String version,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByG_F_T_V_First(
			groupId, folderId, title, version, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		throw new NoSuchFileVersionException(
			_collectionPersistenceFinderByG_F_T_V.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, folderId, title, version}));
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByG_F_T_V_First(
		long groupId, long folderId, String title, String version,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_F_T_V.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, folderId, title, version},
			orderByComparator);
	}

	/**
	 * Removes all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 */
	@Override
	public void removeByG_F_T_V(
		long groupId, long folderId, String title, String version) {

		_collectionPersistenceFinderByG_F_T_V.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, folderId, title, version});
	}

	/**
	 * Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByG_F_T_V(
		long groupId, long folderId, String title, String version) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					DLFileVersion.class)) {

			return _collectionPersistenceFinderByG_F_T_V.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, folderId, title, version});
		}
	}

	public DLFileVersionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DLFileVersion.class);

		setModelImplClass(DLFileVersionImpl.class);
		setModelPKClass(long.class);

		setTable(DLFileVersionTable.INSTANCE);
	}

	/**
	 * Creates a new document library file version with the primary key. Does not add the document library file version to the database.
	 *
	 * @param fileVersionId the primary key for the new document library file version
	 * @return the new document library file version
	 */
	@Override
	public DLFileVersion create(long fileVersionId) {
		DLFileVersion dlFileVersion = new DLFileVersionImpl();

		dlFileVersion.setNew(true);
		dlFileVersion.setPrimaryKey(fileVersionId);

		String uuid = PortalUUIDUtil.generate();

		dlFileVersion.setUuid(uuid);

		dlFileVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dlFileVersion;
	}

	/**
	 * Removes the document library file version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version that was removed
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion remove(long fileVersionId)
		throws NoSuchFileVersionException {

		return remove((Serializable)fileVersionId);
	}

	@Override
	protected DLFileVersion removeImpl(DLFileVersion dlFileVersion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileVersion)) {
				dlFileVersion = (DLFileVersion)session.get(
					DLFileVersionImpl.class, dlFileVersion.getPrimaryKeyObj());
			}

			if ((dlFileVersion != null) &&
				CTPersistenceHelperUtil.isRemove(dlFileVersion)) {

				session.delete(dlFileVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dlFileVersion != null) {
			clearCache(dlFileVersion);
		}

		return dlFileVersion;
	}

	@Override
	public DLFileVersion updateImpl(DLFileVersion dlFileVersion) {
		boolean isNew = dlFileVersion.isNew();

		if (!(dlFileVersion instanceof DLFileVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlFileVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					dlFileVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlFileVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLFileVersion implementation " +
					dlFileVersion.getClass());
		}

		DLFileVersionModelImpl dlFileVersionModelImpl =
			(DLFileVersionModelImpl)dlFileVersion;

		if (Validator.isNull(dlFileVersion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileVersion.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (dlFileVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				dlFileVersion.setCreateDate(date);
			}
			else {
				dlFileVersion.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!dlFileVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dlFileVersion.setModifiedDate(date);
			}
			else {
				dlFileVersion.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(dlFileVersion)) {
				if (!isNew) {
					session.evict(
						DLFileVersionImpl.class,
						dlFileVersion.getPrimaryKeyObj());
				}

				session.save(dlFileVersion);
			}
			else {
				dlFileVersion = (DLFileVersion)session.merge(dlFileVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(dlFileVersion, false);

		if (isNew) {
			dlFileVersion.setNew(false);
		}

		dlFileVersion.resetOriginalValues();

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version with the primary key or throws a <code>NoSuchFileVersionException</code> if it could not be found.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion findByPrimaryKey(long fileVersionId)
		throws NoSuchFileVersionException {

		return findByPrimaryKey((Serializable)fileVersionId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return CTPersistenceHelperUtil.getCTPersistenceHelper();
	}

	/**
	 * Returns the document library file version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version, or <code>null</code> if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion fetchByPrimaryKey(long fileVersionId) {
		return fetchByPrimaryKey((Serializable)fileVersionId);
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
		return "fileVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLFILEVERSION;
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
		return DLFileVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DLFileVersion";
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
		ctMergeColumnNames.add("repositoryId");
		ctMergeColumnNames.add("folderId");
		ctMergeColumnNames.add("fileEntryId");
		ctMergeColumnNames.add("treePath");
		ctMergeColumnNames.add("fileName");
		ctMergeColumnNames.add("extension");
		ctMergeColumnNames.add("mimeType");
		ctMergeColumnNames.add("title");
		ctMergeColumnNames.add("description");
		ctMergeColumnNames.add("changeLog");
		ctMergeColumnNames.add("extraSettings");
		ctMergeColumnNames.add("fileEntryTypeId");
		ctMergeColumnNames.add("version");
		ctMergeColumnNames.add("size_");
		ctMergeColumnNames.add("checksum");
		ctMergeColumnNames.add("storeUUID");
		ctMergeColumnNames.add("displayDate");
		ctMergeColumnNames.add("expirationDate");
		ctMergeColumnNames.add("reviewDate");
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
			CTColumnResolutionType.PK, Collections.singleton("fileVersionId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"fileEntryId", "version"});
	}

	/**
	 * Initializes the document library file version persistence.
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
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_collectionPersistenceFinderByUuid = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByUuid,
			_finderPathWithoutPaginationFindByUuid, _finderPathCountByUuid,
			_SQL_SELECT_DLFILEVERSION_WHERE, _SQL_COUNT_DLFILEVERSION_WHERE,
			DLFileVersionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileVersion.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, DLFileVersion::getUuid));

		_finderPathFetchByUUID_G = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false,
			convertNullFunction(DLFileVersion::getUuid),
			DLFileVersion::getGroupId);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_DLFILEVERSION_WHERE, "",
			new FinderColumn<>(
				"dlFileVersion.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, DLFileVersion::getUuid),
			new FinderColumn<>(
				"dlFileVersion.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DLFileVersion::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_DLFILEVERSION_WHERE,
				_SQL_COUNT_DLFILEVERSION_WHERE,
				DLFileVersionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileVersion.", "uuid", FinderColumn.Type.STRING, "=",
					true, true, DLFileVersion::getUuid),
				new FinderColumn<>(
					"dlFileVersion.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, DLFileVersion::getCompanyId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_DLFILEVERSION_WHERE,
				_SQL_COUNT_DLFILEVERSION_WHERE,
				DLFileVersionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileVersion.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, DLFileVersion::getCompanyId));

		_finderPathWithPaginationFindByFileEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"fileEntryId"}, true);

		_finderPathWithoutPaginationFindByFileEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryId",
			new String[] {Long.class.getName()}, new String[] {"fileEntryId"},
			true);

		_finderPathCountByFileEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryId",
			new String[] {Long.class.getName()}, new String[] {"fileEntryId"},
			false);

		_collectionPersistenceFinderByFileEntryId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByFileEntryId,
				_finderPathWithoutPaginationFindByFileEntryId,
				_finderPathCountByFileEntryId, _SQL_SELECT_DLFILEVERSION_WHERE,
				_SQL_COUNT_DLFILEVERSION_WHERE,
				DLFileVersionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileVersion.", "fileEntryId", FinderColumn.Type.LONG,
					"=", true, true, DLFileVersion::getFileEntryId));

		_finderPathWithPaginationFindByMimeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByMimeType",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"mimeType"}, true);

		_finderPathWithoutPaginationFindByMimeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByMimeType",
			new String[] {String.class.getName()}, new String[] {"mimeType"},
			true);

		_finderPathCountByMimeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMimeType",
			new String[] {String.class.getName()}, new String[] {"mimeType"},
			false);

		_collectionPersistenceFinderByMimeType =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByMimeType,
				_finderPathWithoutPaginationFindByMimeType,
				_finderPathCountByMimeType, _SQL_SELECT_DLFILEVERSION_WHERE,
				_SQL_COUNT_DLFILEVERSION_WHERE,
				DLFileVersionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileVersion.", "mimeType", FinderColumn.Type.STRING, "=",
					true, true, DLFileVersion::getMimeType));

		_finderPathWithPaginationFindByC_SU = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_SU",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "storeUUID"}, true);

		_finderPathWithoutPaginationFindByC_SU = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_SU",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "storeUUID"}, true);

		_finderPathCountByC_SU = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_SU",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "storeUUID"}, false);

		_collectionPersistenceFinderByC_SU = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_SU,
			_finderPathWithoutPaginationFindByC_SU, _finderPathCountByC_SU,
			_SQL_SELECT_DLFILEVERSION_WHERE, _SQL_COUNT_DLFILEVERSION_WHERE,
			DLFileVersionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileVersion.", "companyId", FinderColumn.Type.LONG, "=",
				true, true, DLFileVersion::getCompanyId),
			new FinderColumn<>(
				"dlFileVersion.", "storeUUID", FinderColumn.Type.STRING, "=",
				true, true, DLFileVersion::getStoreUUID));

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
				_SQL_SELECT_DLFILEVERSION_WHERE, _SQL_COUNT_DLFILEVERSION_WHERE,
				DLFileVersionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileVersion.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, DLFileVersion::getCompanyId),
				new FinderColumn<>(
					"dlFileVersion.", "status", FinderColumn.Type.INTEGER, "!=",
					true, true, DLFileVersion::getStatus));

		_finderPathFetchByF_V = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByF_V",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"fileEntryId", "version"}, false,
			DLFileVersion::getFileEntryId,
			convertNullFunction(DLFileVersion::getVersion));

		_uniquePersistenceFinderByF_V = new UniquePersistenceFinder<>(
			this, _finderPathFetchByF_V, _SQL_SELECT_DLFILEVERSION_WHERE, "",
			new FinderColumn<>(
				"dlFileVersion.", "fileEntryId", FinderColumn.Type.LONG, "=",
				true, true, DLFileVersion::getFileEntryId),
			new FinderColumn<>(
				"dlFileVersion.", "version", FinderColumn.Type.STRING, "=",
				true, true, DLFileVersion::getVersion));

		_finderPathWithPaginationFindByF_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"fileEntryId", "status"}, true);

		_finderPathWithoutPaginationFindByF_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByF_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"fileEntryId", "status"}, true);

		_finderPathCountByF_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"fileEntryId", "status"}, false);

		_finderPathWithPaginationCountByF_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByF_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"fileEntryId", "status"}, false);

		_finderPathWithPaginationFindByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"displayDate", "status"}, true);

		_finderPathWithPaginationCountByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"displayDate", "status"}, false);

		_collectionPersistenceFinderByLtD_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByLtD_S, null,
			_finderPathWithPaginationCountByLtD_S,
			_SQL_SELECT_DLFILEVERSION_WHERE, _SQL_COUNT_DLFILEVERSION_WHERE,
			DLFileVersionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileVersion.", "displayDate", FinderColumn.Type.DATE, "<",
				true, true, DLFileVersion::getDisplayDate),
			new FinderColumn<>(
				"dlFileVersion.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, DLFileVersion::getStatus));

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

		_collectionPersistenceFinderByG_F_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_F_S,
			_finderPathWithoutPaginationFindByG_F_S, _finderPathCountByG_F_S,
			_SQL_SELECT_DLFILEVERSION_WHERE, _SQL_COUNT_DLFILEVERSION_WHERE,
			DLFileVersionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"dlFileVersion.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DLFileVersion::getGroupId),
			new FinderColumn<>(
				"dlFileVersion.", "folderId", FinderColumn.Type.LONG, "=", true,
				true, DLFileVersion::getFolderId),
			new FinderColumn<>(
				"dlFileVersion.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, DLFileVersion::getStatus));

		_finderPathWithPaginationFindByC_E_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_E_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "expirationDate", "status"}, true);

		_finderPathWithoutPaginationFindByC_E_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_E_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "expirationDate", "status"}, true);

		_finderPathCountByC_E_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_E_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "expirationDate", "status"}, false);

		_finderPathWithPaginationCountByC_E_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_E_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "expirationDate", "status"}, false);

		_finderPathWithPaginationFindByG_F_T_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "folderId", "title", "version"}, true);

		_finderPathWithoutPaginationFindByG_F_T_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {"groupId", "folderId", "title", "version"}, true);

		_finderPathCountByG_F_T_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {"groupId", "folderId", "title", "version"}, false);

		_collectionPersistenceFinderByG_F_T_V =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_F_T_V,
				_finderPathWithoutPaginationFindByG_F_T_V,
				_finderPathCountByG_F_T_V, _SQL_SELECT_DLFILEVERSION_WHERE,
				_SQL_COUNT_DLFILEVERSION_WHERE,
				DLFileVersionModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"dlFileVersion.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, DLFileVersion::getGroupId),
				new FinderColumn<>(
					"dlFileVersion.", "folderId", FinderColumn.Type.LONG, "=",
					true, true, DLFileVersion::getFolderId),
				new FinderColumn<>(
					"dlFileVersion.", "title", FinderColumn.Type.STRING, "=",
					true, true, DLFileVersion::getTitle),
				new FinderColumn<>(
					"dlFileVersion.", "version", FinderColumn.Type.STRING, "=",
					true, true, DLFileVersion::getVersion));

		DLFileVersionUtil.setPersistence(this);
	}

	public void destroy() {
		DLFileVersionUtil.setPersistence(null);

		EntityCacheUtil.removeCache(DLFileVersionImpl.class.getName());
	}

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _ENTITY_ALIAS_PREFIX =
		DLFileVersionModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_DLFILEVERSION =
		"SELECT dlFileVersion FROM DLFileVersion dlFileVersion";

	private static final String _SQL_SELECT_DLFILEVERSION_WHERE =
		"SELECT dlFileVersion FROM DLFileVersion dlFileVersion WHERE ";

	private static final String _SQL_COUNT_DLFILEVERSION_WHERE =
		"SELECT COUNT(dlFileVersion) FROM DLFileVersion dlFileVersion WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DLFileVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "size"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1843715069