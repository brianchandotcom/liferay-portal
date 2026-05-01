/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.service.persistence.impl;

import com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.model.AssetDisplayPageEntryTable;
import com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryImpl;
import com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryPersistence;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryUtil;
import com.liferay.asset.display.page.service.persistence.impl.constants.AssetPersistenceConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
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
 * The persistence implementation for the asset display page entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AssetDisplayPageEntryPersistence.class)
public class AssetDisplayPageEntryPersistenceImpl
	extends BasePersistenceImpl
		<AssetDisplayPageEntry, NoSuchDisplayPageEntryException>
	implements AssetDisplayPageEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetDisplayPageEntryUtil</code> to access the asset display page entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetDisplayPageEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<AssetDisplayPageEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the asset display page entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first asset display page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByUuid_First(
			String uuid,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		throw new NoSuchDisplayPageEntryException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first asset display page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the asset display page entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of asset display page entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<AssetDisplayPageEntry>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the asset display page entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchDisplayPageEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByUUID_G(
			uuid, groupId);

		if (assetDisplayPageEntry == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchDisplayPageEntryException(message);
		}

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset display page entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the asset display page entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset display page entry that was removed
	 */
	@Override
	public AssetDisplayPageEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = findByUUID_G(
			uuid, groupId);

		return remove(assetDisplayPageEntry);
	}

	/**
	 * Returns the number of asset display page entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<AssetDisplayPageEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the asset display page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first asset display page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		throw new NoSuchDisplayPageEntryException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first asset display page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the asset display page entries where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of asset display page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<AssetDisplayPageEntry>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the asset display page entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				finderCache, new Object[] {groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first asset display page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByGroupId_First(
			long groupId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		throw new NoSuchDisplayPageEntryException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first asset display page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Removes all the asset display page entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of asset display page entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				finderCache, new Object[] {groupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByLayoutPageTemplateEntryId;
	private FinderPath
		_finderPathWithoutPaginationFindByLayoutPageTemplateEntryId;
	private FinderPath _finderPathCountByLayoutPageTemplateEntryId;
	private CollectionPersistenceFinder<AssetDisplayPageEntry>
		_collectionPersistenceFinderByLayoutPageTemplateEntryId;

	/**
	 * Returns all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		return findByLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end) {

		return findByLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return findByLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _collectionPersistenceFinderByLayoutPageTemplateEntryId.find(
				finderCache, new Object[] {layoutPageTemplateEntryId}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByLayoutPageTemplateEntryId_First(
			long layoutPageTemplateEntryId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry =
			fetchByLayoutPageTemplateEntryId_First(
				layoutPageTemplateEntryId, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		throw new NoSuchDisplayPageEntryException(
			_collectionPersistenceFinderByLayoutPageTemplateEntryId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {layoutPageTemplateEntryId}));
	}

	/**
	 * Returns the first asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByLayoutPageTemplateEntryId_First(
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return _collectionPersistenceFinderByLayoutPageTemplateEntryId.
			fetchFirst(
				finderCache, new Object[] {layoutPageTemplateEntryId},
				orderByComparator);
	}

	/**
	 * Removes all the asset display page entries where layoutPageTemplateEntryId = &#63; from the database.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 */
	@Override
	public void removeByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		_collectionPersistenceFinderByLayoutPageTemplateEntryId.remove(
			finderCache, new Object[] {layoutPageTemplateEntryId});
	}

	/**
	 * Returns the number of asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _collectionPersistenceFinderByLayoutPageTemplateEntryId.
				count(finderCache, new Object[] {layoutPageTemplateEntryId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_CN;
	private FinderPath _finderPathWithoutPaginationFindByG_CN;
	private FinderPath _finderPathCountByG_CN;
	private CollectionPersistenceFinder<AssetDisplayPageEntry>
		_collectionPersistenceFinderByG_CN;

	/**
	 * Returns all the asset display page entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByG_CN(
		long groupId, long classNameId) {

		return findByG_CN(
			groupId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display page entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByG_CN(
		long groupId, long classNameId, int start, int end) {

		return findByG_CN(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByG_CN(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return findByG_CN(
			groupId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByG_CN(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _collectionPersistenceFinderByG_CN.find(
				finderCache, new Object[] {groupId, classNameId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first asset display page entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByG_CN_First(
			long groupId, long classNameId,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByG_CN_First(
			groupId, classNameId, orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		throw new NoSuchDisplayPageEntryException(
			_collectionPersistenceFinderByG_CN.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, classNameId}));
	}

	/**
	 * Returns the first asset display page entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByG_CN_First(
		long groupId, long classNameId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_CN.fetchFirst(
			finderCache, new Object[] {groupId, classNameId},
			orderByComparator);
	}

	/**
	 * Removes all the asset display page entries where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_CN(long groupId, long classNameId) {
		_collectionPersistenceFinderByG_CN.remove(
			finderCache, new Object[] {groupId, classNameId});
	}

	/**
	 * Returns the number of asset display page entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByG_CN(long groupId, long classNameId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _collectionPersistenceFinderByG_CN.count(
				finderCache, new Object[] {groupId, classNameId});
		}
	}

	private FinderPath _finderPathFetchByG_C_C;
	private UniquePersistenceFinder<AssetDisplayPageEntry>
		_uniquePersistenceFinderByG_C_C;

	/**
	 * Returns the asset display page entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchDisplayPageEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = fetchByG_C_C(
			groupId, classNameId, classPK);

		if (assetDisplayPageEntry == null) {
			String message =
				_uniquePersistenceFinderByG_C_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, classNameId, classPK});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchDisplayPageEntryException(message);
		}

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK) {

		return fetchByG_C_C(groupId, classNameId, classPK, true);
	}

	/**
	 * Returns the asset display page entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetDisplayPageEntry.class)) {

			return _uniquePersistenceFinderByG_C_C.fetch(
				finderCache, new Object[] {groupId, classNameId, classPK},
				useFinderCache);
		}
	}

	/**
	 * Removes the asset display page entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the asset display page entry that was removed
	 */
	@Override
	public AssetDisplayPageEntry removeByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchDisplayPageEntryException {

		AssetDisplayPageEntry assetDisplayPageEntry = findByG_C_C(
			groupId, classNameId, classPK);

		return remove(assetDisplayPageEntry);
	}

	/**
	 * Returns the number of asset display page entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		return _uniquePersistenceFinderByG_C_C.count(
			finderCache, new Object[] {groupId, classNameId, classPK});
	}

	public AssetDisplayPageEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetDisplayPageEntry.class);

		setModelImplClass(AssetDisplayPageEntryImpl.class);
		setModelPKClass(long.class);

		setTable(AssetDisplayPageEntryTable.INSTANCE);
	}

	/**
	 * Caches the asset display page entry in the entity cache if it is enabled.
	 *
	 * @param assetDisplayPageEntry the asset display page entry
	 */
	@Override
	public void cacheResult(AssetDisplayPageEntry assetDisplayPageEntry) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetDisplayPageEntry.getCtCollectionId())) {

			entityCache.putResult(
				AssetDisplayPageEntryImpl.class,
				assetDisplayPageEntry.getPrimaryKey(), assetDisplayPageEntry);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					assetDisplayPageEntry.getUuid(),
					assetDisplayPageEntry.getGroupId()
				},
				assetDisplayPageEntry);

			finderCache.putResult(
				_finderPathFetchByG_C_C,
				new Object[] {
					assetDisplayPageEntry.getGroupId(),
					assetDisplayPageEntry.getClassNameId(),
					assetDisplayPageEntry.getClassPK()
				},
				assetDisplayPageEntry);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the asset display page entries in the entity cache if it is enabled.
	 *
	 * @param assetDisplayPageEntries the asset display page entries
	 */
	@Override
	public void cacheResult(
		List<AssetDisplayPageEntry> assetDisplayPageEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (assetDisplayPageEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AssetDisplayPageEntry assetDisplayPageEntry :
				assetDisplayPageEntries) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						assetDisplayPageEntry.getCtCollectionId())) {

				if (entityCache.getResult(
						AssetDisplayPageEntryImpl.class,
						assetDisplayPageEntry.getPrimaryKey()) == null) {

					cacheResult(assetDisplayPageEntry);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		AssetDisplayPageEntryModelImpl assetDisplayPageEntryModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetDisplayPageEntryModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				assetDisplayPageEntryModelImpl.getUuid(),
				assetDisplayPageEntryModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, assetDisplayPageEntryModelImpl);

			args = new Object[] {
				assetDisplayPageEntryModelImpl.getGroupId(),
				assetDisplayPageEntryModelImpl.getClassNameId(),
				assetDisplayPageEntryModelImpl.getClassPK()
			};

			finderCache.putResult(
				_finderPathFetchByG_C_C, args, assetDisplayPageEntryModelImpl);
		}
	}

	/**
	 * Creates a new asset display page entry with the primary key. Does not add the asset display page entry to the database.
	 *
	 * @param assetDisplayPageEntryId the primary key for the new asset display page entry
	 * @return the new asset display page entry
	 */
	@Override
	public AssetDisplayPageEntry create(long assetDisplayPageEntryId) {
		AssetDisplayPageEntry assetDisplayPageEntry =
			new AssetDisplayPageEntryImpl();

		assetDisplayPageEntry.setNew(true);
		assetDisplayPageEntry.setPrimaryKey(assetDisplayPageEntryId);

		String uuid = PortalUUIDUtil.generate();

		assetDisplayPageEntry.setUuid(uuid);

		assetDisplayPageEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetDisplayPageEntry;
	}

	/**
	 * Removes the asset display page entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry that was removed
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry remove(long assetDisplayPageEntryId)
		throws NoSuchDisplayPageEntryException {

		return remove((Serializable)assetDisplayPageEntryId);
	}

	@Override
	protected AssetDisplayPageEntry removeImpl(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetDisplayPageEntry)) {
				assetDisplayPageEntry = (AssetDisplayPageEntry)session.get(
					AssetDisplayPageEntryImpl.class,
					assetDisplayPageEntry.getPrimaryKeyObj());
			}

			if ((assetDisplayPageEntry != null) &&
				ctPersistenceHelper.isRemove(assetDisplayPageEntry)) {

				session.delete(assetDisplayPageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetDisplayPageEntry != null) {
			clearCache(assetDisplayPageEntry);
		}

		return assetDisplayPageEntry;
	}

	@Override
	public AssetDisplayPageEntry updateImpl(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		boolean isNew = assetDisplayPageEntry.isNew();

		if (!(assetDisplayPageEntry instanceof
				AssetDisplayPageEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetDisplayPageEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetDisplayPageEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetDisplayPageEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetDisplayPageEntry implementation " +
					assetDisplayPageEntry.getClass());
		}

		AssetDisplayPageEntryModelImpl assetDisplayPageEntryModelImpl =
			(AssetDisplayPageEntryModelImpl)assetDisplayPageEntry;

		if (Validator.isNull(assetDisplayPageEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetDisplayPageEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (assetDisplayPageEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetDisplayPageEntry.setCreateDate(date);
			}
			else {
				assetDisplayPageEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!assetDisplayPageEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetDisplayPageEntry.setModifiedDate(date);
			}
			else {
				assetDisplayPageEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(assetDisplayPageEntry)) {
				if (!isNew) {
					session.evict(
						AssetDisplayPageEntryImpl.class,
						assetDisplayPageEntry.getPrimaryKeyObj());
				}

				session.save(assetDisplayPageEntry);
			}
			else {
				assetDisplayPageEntry = (AssetDisplayPageEntry)session.merge(
					assetDisplayPageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AssetDisplayPageEntryImpl.class, assetDisplayPageEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(assetDisplayPageEntryModelImpl);

		if (isNew) {
			assetDisplayPageEntry.setNew(false);
		}

		assetDisplayPageEntry.resetOriginalValues();

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry with the primary key or throws a <code>NoSuchDisplayPageEntryException</code> if it could not be found.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByPrimaryKey(long assetDisplayPageEntryId)
		throws NoSuchDisplayPageEntryException {

		return findByPrimaryKey((Serializable)assetDisplayPageEntryId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the asset display page entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry, or <code>null</code> if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByPrimaryKey(
		long assetDisplayPageEntryId) {

		return fetchByPrimaryKey((Serializable)assetDisplayPageEntryId);
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
		return "assetDisplayPageEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETDISPLAYPAGEENTRY;
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
		return AssetDisplayPageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetDisplayPageEntry";
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
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctMergeColumnNames.add("layoutPageTemplateEntryId");
		ctMergeColumnNames.add("type_");
		ctMergeColumnNames.add("plid");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("assetDisplayPageEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "classNameId", "classPK"});
	}

	/**
	 * Initializes the asset display page entry persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

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
			_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE,
			_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE,
			AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"assetDisplayPageEntry.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, AssetDisplayPageEntry::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G,
			_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE,
			new FinderColumn<>(
				"assetDisplayPageEntry.", "uuid", FinderColumn.Type.STRING, "=",
				true, false, AssetDisplayPageEntry::getUuid),
			new FinderColumn<>(
				"assetDisplayPageEntry.", "groupId", FinderColumn.Type.LONG,
				"=", true, true, AssetDisplayPageEntry::getGroupId));

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
				_finderPathCountByUuid_C,
				_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE,
				_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE,
				AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"assetDisplayPageEntry.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, AssetDisplayPageEntry::getUuid),
				new FinderColumn<>(
					"assetDisplayPageEntry.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					AssetDisplayPageEntry::getCompanyId));

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
				_finderPathCountByGroupId,
				_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE,
				_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE,
				AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"assetDisplayPageEntry.", "groupId", FinderColumn.Type.LONG,
					"=", true, true, AssetDisplayPageEntry::getGroupId));

		_finderPathWithPaginationFindByLayoutPageTemplateEntryId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByLayoutPageTemplateEntryId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"layoutPageTemplateEntryId"}, true);

		_finderPathWithoutPaginationFindByLayoutPageTemplateEntryId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByLayoutPageTemplateEntryId",
				new String[] {Long.class.getName()},
				new String[] {"layoutPageTemplateEntryId"}, true);

		_finderPathCountByLayoutPageTemplateEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutPageTemplateEntryId",
			new String[] {Long.class.getName()},
			new String[] {"layoutPageTemplateEntryId"}, false);

		_collectionPersistenceFinderByLayoutPageTemplateEntryId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByLayoutPageTemplateEntryId,
				_finderPathWithoutPaginationFindByLayoutPageTemplateEntryId,
				_finderPathCountByLayoutPageTemplateEntryId,
				_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE,
				_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE,
				AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"assetDisplayPageEntry.", "layoutPageTemplateEntryId",
					FinderColumn.Type.LONG, "=", true, true,
					AssetDisplayPageEntry::getLayoutPageTemplateEntryId));

		_finderPathWithPaginationFindByG_CN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_CN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId"}, true);

		_finderPathWithoutPaginationFindByG_CN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_CN",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "classNameId"}, true);

		_finderPathCountByG_CN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_CN",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "classNameId"}, false);

		_collectionPersistenceFinderByG_CN = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_CN,
			_finderPathWithoutPaginationFindByG_CN, _finderPathCountByG_CN,
			_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE,
			_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE,
			AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"assetDisplayPageEntry.", "groupId", FinderColumn.Type.LONG,
				"=", true, false, AssetDisplayPageEntry::getGroupId),
			new FinderColumn<>(
				"assetDisplayPageEntry.", "classNameId", FinderColumn.Type.LONG,
				"=", true, true, AssetDisplayPageEntry::getClassNameId));

		_finderPathFetchByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_uniquePersistenceFinderByG_C_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_C_C,
			_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE,
			new FinderColumn<>(
				"assetDisplayPageEntry.", "groupId", FinderColumn.Type.LONG,
				"=", true, false, AssetDisplayPageEntry::getGroupId),
			new FinderColumn<>(
				"assetDisplayPageEntry.", "classNameId", FinderColumn.Type.LONG,
				"=", true, false, AssetDisplayPageEntry::getClassNameId),
			new FinderColumn<>(
				"assetDisplayPageEntry.", "classPK", FinderColumn.Type.LONG,
				"=", true, true, AssetDisplayPageEntry::getClassPK));

		AssetDisplayPageEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		AssetDisplayPageEntryUtil.setPersistence(null);

		entityCache.removeCache(AssetDisplayPageEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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
		AssetDisplayPageEntryModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_ASSETDISPLAYPAGEENTRY =
		"SELECT assetDisplayPageEntry FROM AssetDisplayPageEntry assetDisplayPageEntry";

	private static final String _SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE =
		"SELECT assetDisplayPageEntry FROM AssetDisplayPageEntry assetDisplayPageEntry WHERE ";

	private static final String _SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE =
		"SELECT COUNT(assetDisplayPageEntry) FROM AssetDisplayPageEntry assetDisplayPageEntry WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetDisplayPageEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetDisplayPageEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-728750206