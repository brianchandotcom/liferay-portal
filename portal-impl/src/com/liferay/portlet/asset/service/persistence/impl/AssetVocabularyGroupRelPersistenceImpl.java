/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.persistence.impl;

import com.liferay.asset.kernel.exception.NoSuchVocabularyGroupRelException;
import com.liferay.asset.kernel.model.AssetVocabularyGroupRel;
import com.liferay.asset.kernel.model.AssetVocabularyGroupRelTable;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyGroupRelPersistence;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyGroupRelUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
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
import com.liferay.portlet.asset.model.impl.AssetVocabularyGroupRelImpl;
import com.liferay.portlet.asset.model.impl.AssetVocabularyGroupRelModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the asset vocabulary group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetVocabularyGroupRelPersistenceImpl
	extends BasePersistenceImpl<AssetVocabularyGroupRel>
	implements AssetVocabularyGroupRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetVocabularyGroupRelUtil</code> to access the asset vocabulary group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetVocabularyGroupRelImpl.class.getName();

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
	private CollectionPersistenceFinder<AssetVocabularyGroupRel>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the asset vocabulary group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset vocabulary group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @return the range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

			return _collectionPersistenceFinderByUuid.find(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws NoSuchVocabularyGroupRelException {

		AssetVocabularyGroupRel assetVocabularyGroupRel = fetchByUuid_First(
			uuid, orderByComparator);

		if (assetVocabularyGroupRel != null) {
			return assetVocabularyGroupRel;
		}

		throw new NoSuchVocabularyGroupRelException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid},
			orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary group rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid});
	}

	/**
	 * Returns the number of asset vocabulary group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset vocabulary group rels
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

			return _collectionPersistenceFinderByUuid.count(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<AssetVocabularyGroupRel>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the asset vocabulary group rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVocabularyGroupRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel findByUUID_G(String uuid, long groupId)
		throws NoSuchVocabularyGroupRelException {

		AssetVocabularyGroupRel assetVocabularyGroupRel = fetchByUUID_G(
			uuid, groupId);

		if (assetVocabularyGroupRel == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchVocabularyGroupRelException(message);
		}

		return assetVocabularyGroupRel;
	}

	/**
	 * Returns the asset vocabulary group rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset vocabulary group rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid, groupId},
				useFinderCache);
		}
	}

	/**
	 * Removes the asset vocabulary group rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset vocabulary group rel that was removed
	 */
	@Override
	public AssetVocabularyGroupRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchVocabularyGroupRelException {

		AssetVocabularyGroupRel assetVocabularyGroupRel = findByUUID_G(
			uuid, groupId);

		return remove(assetVocabularyGroupRel);
	}

	/**
	 * Returns the number of asset vocabulary group rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset vocabulary group rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<AssetVocabularyGroupRel>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the asset vocabulary group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset vocabulary group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @return the range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {uuid, companyId}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws NoSuchVocabularyGroupRelException {

		AssetVocabularyGroupRel assetVocabularyGroupRel = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (assetVocabularyGroupRel != null) {
			return assetVocabularyGroupRel;
		}

		throw new NoSuchVocabularyGroupRelException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid, companyId},
			orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary group rels where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of asset vocabulary group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset vocabulary group rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<AssetVocabularyGroupRel>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the asset vocabulary group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset vocabulary group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @return the range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {groupId}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel findByGroupId_First(
			long groupId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws NoSuchVocabularyGroupRelException {

		AssetVocabularyGroupRel assetVocabularyGroupRel = fetchByGroupId_First(
			groupId, orderByComparator);

		if (assetVocabularyGroupRel != null) {
			return assetVocabularyGroupRel;
		}

		throw new NoSuchVocabularyGroupRelException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel fetchByGroupId_First(
		long groupId,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId},
			orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary group rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId});
	}

	/**
	 * Returns the number of asset vocabulary group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset vocabulary group rels
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {groupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByVocabularyId;
	private FinderPath _finderPathWithoutPaginationFindByVocabularyId;
	private FinderPath _finderPathCountByVocabularyId;
	private CollectionPersistenceFinder<AssetVocabularyGroupRel>
		_collectionPersistenceFinderByVocabularyId;

	/**
	 * Returns all the asset vocabulary group rels where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByVocabularyId(long vocabularyId) {
		return findByVocabularyId(
			vocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset vocabulary group rels where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @return the range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByVocabularyId(
		long vocabularyId, int start, int end) {

		return findByVocabularyId(vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByVocabularyId(
		long vocabularyId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return findByVocabularyId(
			vocabularyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findByVocabularyId(
		long vocabularyId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

			return _collectionPersistenceFinderByVocabularyId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {vocabularyId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel findByVocabularyId_First(
			long vocabularyId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws NoSuchVocabularyGroupRelException {

		AssetVocabularyGroupRel assetVocabularyGroupRel =
			fetchByVocabularyId_First(vocabularyId, orderByComparator);

		if (assetVocabularyGroupRel != null) {
			return assetVocabularyGroupRel;
		}

		throw new NoSuchVocabularyGroupRelException(
			_collectionPersistenceFinderByVocabularyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {vocabularyId}));
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel fetchByVocabularyId_First(
		long vocabularyId,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return _collectionPersistenceFinderByVocabularyId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {vocabularyId},
			orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary group rels where vocabularyId = &#63; from the database.
	 *
	 * @param vocabularyId the vocabulary ID
	 */
	@Override
	public void removeByVocabularyId(long vocabularyId) {
		_collectionPersistenceFinderByVocabularyId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {vocabularyId});
	}

	/**
	 * Returns the number of asset vocabulary group rels where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset vocabulary group rels
	 */
	@Override
	public int countByVocabularyId(long vocabularyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

			return _collectionPersistenceFinderByVocabularyId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {vocabularyId});
		}
	}

	private FinderPath _finderPathFetchByG_V;
	private UniquePersistenceFinder<AssetVocabularyGroupRel>
		_uniquePersistenceFinderByG_V;

	/**
	 * Returns the asset vocabulary group rel where groupId = &#63; and vocabularyId = &#63; or throws a <code>NoSuchVocabularyGroupRelException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel findByG_V(long groupId, long vocabularyId)
		throws NoSuchVocabularyGroupRelException {

		AssetVocabularyGroupRel assetVocabularyGroupRel = fetchByG_V(
			groupId, vocabularyId);

		if (assetVocabularyGroupRel == null) {
			String message =
				_uniquePersistenceFinderByG_V.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, vocabularyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchVocabularyGroupRelException(message);
		}

		return assetVocabularyGroupRel;
	}

	/**
	 * Returns the asset vocabulary group rel where groupId = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel fetchByG_V(long groupId, long vocabularyId) {
		return fetchByG_V(groupId, vocabularyId, true);
	}

	/**
	 * Returns the asset vocabulary group rel where groupId = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	@Override
	public AssetVocabularyGroupRel fetchByG_V(
		long groupId, long vocabularyId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

			return _uniquePersistenceFinderByG_V.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, vocabularyId}, useFinderCache);
		}
	}

	/**
	 * Removes the asset vocabulary group rel where groupId = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the asset vocabulary group rel that was removed
	 */
	@Override
	public AssetVocabularyGroupRel removeByG_V(long groupId, long vocabularyId)
		throws NoSuchVocabularyGroupRelException {

		AssetVocabularyGroupRel assetVocabularyGroupRel = findByG_V(
			groupId, vocabularyId);

		return remove(assetVocabularyGroupRel);
	}

	/**
	 * Returns the number of asset vocabulary group rels where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset vocabulary group rels
	 */
	@Override
	public int countByG_V(long groupId, long vocabularyId) {
		return _uniquePersistenceFinderByG_V.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, vocabularyId});
	}

	public AssetVocabularyGroupRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetVocabularyGroupRel.class);

		setModelImplClass(AssetVocabularyGroupRelImpl.class);
		setModelPKClass(long.class);

		setTable(AssetVocabularyGroupRelTable.INSTANCE);
	}

	/**
	 * Caches the asset vocabulary group rel in the entity cache if it is enabled.
	 *
	 * @param assetVocabularyGroupRel the asset vocabulary group rel
	 */
	@Override
	public void cacheResult(AssetVocabularyGroupRel assetVocabularyGroupRel) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetVocabularyGroupRel.getCtCollectionId())) {

			EntityCacheUtil.putResult(
				AssetVocabularyGroupRelImpl.class,
				assetVocabularyGroupRel.getPrimaryKey(),
				assetVocabularyGroupRel);

			FinderCacheUtil.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					assetVocabularyGroupRel.getUuid(),
					assetVocabularyGroupRel.getGroupId()
				},
				assetVocabularyGroupRel);

			FinderCacheUtil.putResult(
				_finderPathFetchByG_V,
				new Object[] {
					assetVocabularyGroupRel.getGroupId(),
					assetVocabularyGroupRel.getVocabularyId()
				},
				assetVocabularyGroupRel);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the asset vocabulary group rels in the entity cache if it is enabled.
	 *
	 * @param assetVocabularyGroupRels the asset vocabulary group rels
	 */
	@Override
	public void cacheResult(
		List<AssetVocabularyGroupRel> assetVocabularyGroupRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (assetVocabularyGroupRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AssetVocabularyGroupRel assetVocabularyGroupRel :
				assetVocabularyGroupRels) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						assetVocabularyGroupRel.getCtCollectionId())) {

				if (EntityCacheUtil.getResult(
						AssetVocabularyGroupRelImpl.class,
						assetVocabularyGroupRel.getPrimaryKey()) == null) {

					cacheResult(assetVocabularyGroupRel);
				}
			}
		}
	}

	/**
	 * Clears the cache for all asset vocabulary group rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(AssetVocabularyGroupRelImpl.class);

		FinderCacheUtil.clearCache(AssetVocabularyGroupRelImpl.class);
	}

	/**
	 * Clears the cache for the asset vocabulary group rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetVocabularyGroupRel assetVocabularyGroupRel) {
		EntityCacheUtil.removeResult(
			AssetVocabularyGroupRelImpl.class, assetVocabularyGroupRel);
	}

	@Override
	public void clearCache(
		List<AssetVocabularyGroupRel> assetVocabularyGroupRels) {

		for (AssetVocabularyGroupRel assetVocabularyGroupRel :
				assetVocabularyGroupRels) {

			EntityCacheUtil.removeResult(
				AssetVocabularyGroupRelImpl.class, assetVocabularyGroupRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(AssetVocabularyGroupRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				AssetVocabularyGroupRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetVocabularyGroupRelModelImpl assetVocabularyGroupRelModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetVocabularyGroupRelModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				assetVocabularyGroupRelModelImpl.getUuid(),
				assetVocabularyGroupRelModelImpl.getGroupId()
			};

			FinderCacheUtil.putResult(
				_finderPathFetchByUUID_G, args,
				assetVocabularyGroupRelModelImpl);

			args = new Object[] {
				assetVocabularyGroupRelModelImpl.getGroupId(),
				assetVocabularyGroupRelModelImpl.getVocabularyId()
			};

			FinderCacheUtil.putResult(
				_finderPathFetchByG_V, args, assetVocabularyGroupRelModelImpl);
		}
	}

	/**
	 * Creates a new asset vocabulary group rel with the primary key. Does not add the asset vocabulary group rel to the database.
	 *
	 * @param assetVocabularyGroupRelId the primary key for the new asset vocabulary group rel
	 * @return the new asset vocabulary group rel
	 */
	@Override
	public AssetVocabularyGroupRel create(long assetVocabularyGroupRelId) {
		AssetVocabularyGroupRel assetVocabularyGroupRel =
			new AssetVocabularyGroupRelImpl();

		assetVocabularyGroupRel.setNew(true);
		assetVocabularyGroupRel.setPrimaryKey(assetVocabularyGroupRelId);

		String uuid = PortalUUIDUtil.generate();

		assetVocabularyGroupRel.setUuid(uuid);

		assetVocabularyGroupRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetVocabularyGroupRel;
	}

	/**
	 * Removes the asset vocabulary group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetVocabularyGroupRelId the primary key of the asset vocabulary group rel
	 * @return the asset vocabulary group rel that was removed
	 * @throws NoSuchVocabularyGroupRelException if a asset vocabulary group rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyGroupRel remove(long assetVocabularyGroupRelId)
		throws NoSuchVocabularyGroupRelException {

		return remove((Serializable)assetVocabularyGroupRelId);
	}

	/**
	 * Removes the asset vocabulary group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset vocabulary group rel
	 * @return the asset vocabulary group rel that was removed
	 * @throws NoSuchVocabularyGroupRelException if a asset vocabulary group rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyGroupRel remove(Serializable primaryKey)
		throws NoSuchVocabularyGroupRelException {

		Session session = null;

		try {
			session = openSession();

			AssetVocabularyGroupRel assetVocabularyGroupRel =
				(AssetVocabularyGroupRel)session.get(
					AssetVocabularyGroupRelImpl.class, primaryKey);

			if (assetVocabularyGroupRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVocabularyGroupRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetVocabularyGroupRel);
		}
		catch (NoSuchVocabularyGroupRelException noSuchEntityException) {
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
	protected AssetVocabularyGroupRel removeImpl(
		AssetVocabularyGroupRel assetVocabularyGroupRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetVocabularyGroupRel)) {
				assetVocabularyGroupRel = (AssetVocabularyGroupRel)session.get(
					AssetVocabularyGroupRelImpl.class,
					assetVocabularyGroupRel.getPrimaryKeyObj());
			}

			if ((assetVocabularyGroupRel != null) &&
				CTPersistenceHelperUtil.isRemove(assetVocabularyGroupRel)) {

				session.delete(assetVocabularyGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetVocabularyGroupRel != null) {
			clearCache(assetVocabularyGroupRel);
		}

		return assetVocabularyGroupRel;
	}

	@Override
	public AssetVocabularyGroupRel updateImpl(
		AssetVocabularyGroupRel assetVocabularyGroupRel) {

		boolean isNew = assetVocabularyGroupRel.isNew();

		if (!(assetVocabularyGroupRel instanceof
				AssetVocabularyGroupRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetVocabularyGroupRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetVocabularyGroupRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetVocabularyGroupRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetVocabularyGroupRel implementation " +
					assetVocabularyGroupRel.getClass());
		}

		AssetVocabularyGroupRelModelImpl assetVocabularyGroupRelModelImpl =
			(AssetVocabularyGroupRelModelImpl)assetVocabularyGroupRel;

		if (Validator.isNull(assetVocabularyGroupRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetVocabularyGroupRel.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(assetVocabularyGroupRel)) {
				if (!isNew) {
					session.evict(
						AssetVocabularyGroupRelImpl.class,
						assetVocabularyGroupRel.getPrimaryKeyObj());
				}

				session.save(assetVocabularyGroupRel);
			}
			else {
				assetVocabularyGroupRel =
					(AssetVocabularyGroupRel)session.merge(
						assetVocabularyGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			AssetVocabularyGroupRelImpl.class, assetVocabularyGroupRelModelImpl,
			false, true);

		cacheUniqueFindersCache(assetVocabularyGroupRelModelImpl);

		if (isNew) {
			assetVocabularyGroupRel.setNew(false);
		}

		assetVocabularyGroupRel.resetOriginalValues();

		return assetVocabularyGroupRel;
	}

	/**
	 * Returns the asset vocabulary group rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset vocabulary group rel
	 * @return the asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a asset vocabulary group rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyGroupRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVocabularyGroupRelException {

		AssetVocabularyGroupRel assetVocabularyGroupRel = fetchByPrimaryKey(
			primaryKey);

		if (assetVocabularyGroupRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVocabularyGroupRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetVocabularyGroupRel;
	}

	/**
	 * Returns the asset vocabulary group rel with the primary key or throws a <code>NoSuchVocabularyGroupRelException</code> if it could not be found.
	 *
	 * @param assetVocabularyGroupRelId the primary key of the asset vocabulary group rel
	 * @return the asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a asset vocabulary group rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyGroupRel findByPrimaryKey(
			long assetVocabularyGroupRelId)
		throws NoSuchVocabularyGroupRelException {

		return findByPrimaryKey((Serializable)assetVocabularyGroupRelId);
	}

	/**
	 * Returns the asset vocabulary group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset vocabulary group rel
	 * @return the asset vocabulary group rel, or <code>null</code> if a asset vocabulary group rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyGroupRel fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(
				AssetVocabularyGroupRel.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		AssetVocabularyGroupRel assetVocabularyGroupRel =
			(AssetVocabularyGroupRel)EntityCacheUtil.getResult(
				AssetVocabularyGroupRelImpl.class, primaryKey);

		if (assetVocabularyGroupRel != null) {
			return assetVocabularyGroupRel;
		}

		Session session = null;

		try {
			session = openSession();

			assetVocabularyGroupRel = (AssetVocabularyGroupRel)session.get(
				AssetVocabularyGroupRelImpl.class, primaryKey);

			if (assetVocabularyGroupRel != null) {
				cacheResult(assetVocabularyGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetVocabularyGroupRel;
	}

	/**
	 * Returns the asset vocabulary group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetVocabularyGroupRelId the primary key of the asset vocabulary group rel
	 * @return the asset vocabulary group rel, or <code>null</code> if a asset vocabulary group rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyGroupRel fetchByPrimaryKey(
		long assetVocabularyGroupRelId) {

		return fetchByPrimaryKey((Serializable)assetVocabularyGroupRelId);
	}

	@Override
	public Map<Serializable, AssetVocabularyGroupRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(
				AssetVocabularyGroupRel.class)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetVocabularyGroupRel> map =
			new HashMap<Serializable, AssetVocabularyGroupRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetVocabularyGroupRel assetVocabularyGroupRel = fetchByPrimaryKey(
				primaryKey);

			if (assetVocabularyGroupRel != null) {
				map.put(primaryKey, assetVocabularyGroupRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
						AssetVocabularyGroupRel.class, primaryKey)) {

				AssetVocabularyGroupRel assetVocabularyGroupRel =
					(AssetVocabularyGroupRel)EntityCacheUtil.getResult(
						AssetVocabularyGroupRelImpl.class, primaryKey);

				if (assetVocabularyGroupRel == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, assetVocabularyGroupRel);
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

			for (AssetVocabularyGroupRel assetVocabularyGroupRel :
					(List<AssetVocabularyGroupRel>)query.list()) {

				map.put(
					assetVocabularyGroupRel.getPrimaryKeyObj(),
					assetVocabularyGroupRel);

				cacheResult(assetVocabularyGroupRel);
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
	 * Returns all the asset vocabulary group rels.
	 *
	 * @return the asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset vocabulary group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @return the range of asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findAll(
		int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset vocabulary group rels
	 */
	@Override
	public List<AssetVocabularyGroupRel> findAll(
		int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

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

			List<AssetVocabularyGroupRel> list = null;

			if (useFinderCache) {
				list = (List<AssetVocabularyGroupRel>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_ASSETVOCABULARYGROUPREL);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_ASSETVOCABULARYGROUPREL;

					sql = sql.concat(
						AssetVocabularyGroupRelModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<AssetVocabularyGroupRel>)QueryUtil.list(
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
	 * Removes all the asset vocabulary group rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetVocabularyGroupRel assetVocabularyGroupRel : findAll()) {
			remove(assetVocabularyGroupRel);
		}
	}

	/**
	 * Returns the number of asset vocabulary group rels.
	 *
	 * @return the number of asset vocabulary group rels
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyGroupRel.class)) {

			Long count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_ASSETVOCABULARYGROUPREL);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "assetVocabularyGroupRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETVOCABULARYGROUPREL;
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
		return AssetVocabularyGroupRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetVocabularyGroupRel";
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
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctMergeColumnNames.add("vocabularyId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("assetVocabularyGroupRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the asset vocabulary group rel persistence.
	 */
	public void afterPropertiesSet() {
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
			_SQL_SELECT_ASSETVOCABULARYGROUPREL_WHERE,
			_SQL_COUNT_ASSETVOCABULARYGROUPREL_WHERE,
			AssetVocabularyGroupRelModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"assetVocabularyGroupRel.", "uuid", FinderColumn.Type.STRING,
				"=", true, true, AssetVocabularyGroupRel::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G,
			_SQL_SELECT_ASSETVOCABULARYGROUPREL_WHERE,
			new FinderColumn<>(
				"assetVocabularyGroupRel.", "uuid", FinderColumn.Type.STRING,
				"=", true, false, AssetVocabularyGroupRel::getUuid),
			new FinderColumn<>(
				"assetVocabularyGroupRel.", "groupId", FinderColumn.Type.LONG,
				"=", true, true, AssetVocabularyGroupRel::getGroupId));

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
				_SQL_SELECT_ASSETVOCABULARYGROUPREL_WHERE,
				_SQL_COUNT_ASSETVOCABULARYGROUPREL_WHERE,
				AssetVocabularyGroupRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"assetVocabularyGroupRel.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					AssetVocabularyGroupRel::getUuid),
				new FinderColumn<>(
					"assetVocabularyGroupRel.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					AssetVocabularyGroupRel::getCompanyId));

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
				_SQL_SELECT_ASSETVOCABULARYGROUPREL_WHERE,
				_SQL_COUNT_ASSETVOCABULARYGROUPREL_WHERE,
				AssetVocabularyGroupRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"assetVocabularyGroupRel.", "groupId",
					FinderColumn.Type.LONG, "=", true, true,
					AssetVocabularyGroupRel::getGroupId));

		_finderPathWithPaginationFindByVocabularyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByVocabularyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"vocabularyId"}, true);

		_finderPathWithoutPaginationFindByVocabularyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByVocabularyId",
			new String[] {Long.class.getName()}, new String[] {"vocabularyId"},
			true);

		_finderPathCountByVocabularyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByVocabularyId",
			new String[] {Long.class.getName()}, new String[] {"vocabularyId"},
			false);

		_collectionPersistenceFinderByVocabularyId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByVocabularyId,
				_finderPathWithoutPaginationFindByVocabularyId,
				_finderPathCountByVocabularyId,
				_SQL_SELECT_ASSETVOCABULARYGROUPREL_WHERE,
				_SQL_COUNT_ASSETVOCABULARYGROUPREL_WHERE,
				AssetVocabularyGroupRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"assetVocabularyGroupRel.", "vocabularyId",
					FinderColumn.Type.LONG, "=", true, true,
					AssetVocabularyGroupRel::getVocabularyId));

		_finderPathFetchByG_V = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_V",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "vocabularyId"}, true);

		_uniquePersistenceFinderByG_V = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_V,
			_SQL_SELECT_ASSETVOCABULARYGROUPREL_WHERE,
			new FinderColumn<>(
				"assetVocabularyGroupRel.", "groupId", FinderColumn.Type.LONG,
				"=", true, false, AssetVocabularyGroupRel::getGroupId),
			new FinderColumn<>(
				"assetVocabularyGroupRel.", "vocabularyId",
				FinderColumn.Type.LONG, "=", true, true,
				AssetVocabularyGroupRel::getVocabularyId));

		AssetVocabularyGroupRelUtil.setPersistence(this);
	}

	public void destroy() {
		AssetVocabularyGroupRelUtil.setPersistence(null);

		EntityCacheUtil.removeCache(
			AssetVocabularyGroupRelImpl.class.getName());
	}

	private static final String _SQL_SELECT_ASSETVOCABULARYGROUPREL =
		"SELECT assetVocabularyGroupRel FROM AssetVocabularyGroupRel assetVocabularyGroupRel";

	private static final String _SQL_SELECT_ASSETVOCABULARYGROUPREL_WHERE =
		"SELECT assetVocabularyGroupRel FROM AssetVocabularyGroupRel assetVocabularyGroupRel WHERE ";

	private static final String _SQL_COUNT_ASSETVOCABULARYGROUPREL =
		"SELECT COUNT(assetVocabularyGroupRel) FROM AssetVocabularyGroupRel assetVocabularyGroupRel";

	private static final String _SQL_COUNT_ASSETVOCABULARYGROUPREL_WHERE =
		"SELECT COUNT(assetVocabularyGroupRel) FROM AssetVocabularyGroupRel assetVocabularyGroupRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"assetVocabularyGroupRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetVocabularyGroupRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetVocabularyGroupRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabularyGroupRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:2103053750