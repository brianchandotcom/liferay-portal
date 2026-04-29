/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.service.persistence.impl;

import com.liferay.adaptive.media.image.exception.NoSuchAMImageEntryException;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.model.AMImageEntryTable;
import com.liferay.adaptive.media.image.model.impl.AMImageEntryImpl;
import com.liferay.adaptive.media.image.model.impl.AMImageEntryModelImpl;
import com.liferay.adaptive.media.image.service.persistence.AMImageEntryPersistence;
import com.liferay.adaptive.media.image.service.persistence.AMImageEntryUtil;
import com.liferay.adaptive.media.image.service.persistence.impl.constants.AMImageEntryPersistenceConstants;
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
 * The persistence implementation for the am image entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AMImageEntryPersistence.class)
public class AMImageEntryPersistenceImpl
	extends BasePersistenceImpl<AMImageEntry, NoSuchAMImageEntryException>
	implements AMImageEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AMImageEntryUtil</code> to access the am image entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AMImageEntryImpl.class.getName();

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
	private CollectionPersistenceFinder<AMImageEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the am image entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByUuid_First(
			String uuid, OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {

		AMImageEntry amImageEntry = fetchByUuid_First(uuid, orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		throw new NoSuchAMImageEntryException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByUuid_First(
		String uuid, OrderByComparator<AMImageEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the am image entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of am image entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<AMImageEntry>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the am image entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchAMImageEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchAMImageEntryException {

		AMImageEntry amImageEntry = fetchByUUID_G(uuid, groupId);

		if (amImageEntry == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchAMImageEntryException(message);
		}

		return amImageEntry;
	}

	/**
	 * Returns the am image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the am image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the am image entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the am image entry that was removed
	 */
	@Override
	public AMImageEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchAMImageEntryException {

		AMImageEntry amImageEntry = findByUUID_G(uuid, groupId);

		return remove(amImageEntry);
	}

	/**
	 * Returns the number of am image entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<AMImageEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {

		AMImageEntry amImageEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		throw new NoSuchAMImageEntryException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the am image entries where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<AMImageEntry>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the am image entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				finderCache, new Object[] {groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByGroupId_First(
			long groupId, OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {

		AMImageEntry amImageEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		throw new NoSuchAMImageEntryException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByGroupId_First(
		long groupId, OrderByComparator<AMImageEntry> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Removes all the am image entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of am image entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				finderCache, new Object[] {groupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<AMImageEntry>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the am image entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				finderCache, new Object[] {companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByCompanyId_First(
			long companyId, OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {

		AMImageEntry amImageEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		throw new NoSuchAMImageEntryException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<AMImageEntry> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the am image entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of am image entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				finderCache, new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByConfigurationUuid;
	private FinderPath _finderPathWithoutPaginationFindByConfigurationUuid;
	private FinderPath _finderPathCountByConfigurationUuid;
	private CollectionPersistenceFinder<AMImageEntry>
		_collectionPersistenceFinderByConfigurationUuid;

	/**
	 * Returns all the am image entries where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid) {

		return findByConfigurationUuid(
			configurationUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end) {

		return findByConfigurationUuid(configurationUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return findByConfigurationUuid(
			configurationUuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByConfigurationUuid.find(
				finderCache, new Object[] {configurationUuid}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByConfigurationUuid_First(
			String configurationUuid,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {

		AMImageEntry amImageEntry = fetchByConfigurationUuid_First(
			configurationUuid, orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		throw new NoSuchAMImageEntryException(
			_collectionPersistenceFinderByConfigurationUuid.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {configurationUuid}));
	}

	/**
	 * Returns the first am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByConfigurationUuid_First(
		String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return _collectionPersistenceFinderByConfigurationUuid.fetchFirst(
			finderCache, new Object[] {configurationUuid}, orderByComparator);
	}

	/**
	 * Removes all the am image entries where configurationUuid = &#63; from the database.
	 *
	 * @param configurationUuid the configuration uuid
	 */
	@Override
	public void removeByConfigurationUuid(String configurationUuid) {
		_collectionPersistenceFinderByConfigurationUuid.remove(
			finderCache, new Object[] {configurationUuid});
	}

	/**
	 * Returns the number of am image entries where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByConfigurationUuid(String configurationUuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByConfigurationUuid.count(
				finderCache, new Object[] {configurationUuid});
		}
	}

	private FinderPath _finderPathWithPaginationFindByFileVersionId;
	private FinderPath _finderPathWithoutPaginationFindByFileVersionId;
	private FinderPath _finderPathCountByFileVersionId;
	private CollectionPersistenceFinder<AMImageEntry>
		_collectionPersistenceFinderByFileVersionId;

	/**
	 * Returns all the am image entries where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByFileVersionId(long fileVersionId) {
		return findByFileVersionId(
			fileVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end) {

		return findByFileVersionId(fileVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return findByFileVersionId(
			fileVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByFileVersionId.find(
				finderCache, new Object[] {fileVersionId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByFileVersionId_First(
			long fileVersionId,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {

		AMImageEntry amImageEntry = fetchByFileVersionId_First(
			fileVersionId, orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		throw new NoSuchAMImageEntryException(
			_collectionPersistenceFinderByFileVersionId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {fileVersionId}));
	}

	/**
	 * Returns the first am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByFileVersionId_First(
		long fileVersionId, OrderByComparator<AMImageEntry> orderByComparator) {

		return _collectionPersistenceFinderByFileVersionId.fetchFirst(
			finderCache, new Object[] {fileVersionId}, orderByComparator);
	}

	/**
	 * Removes all the am image entries where fileVersionId = &#63; from the database.
	 *
	 * @param fileVersionId the file version ID
	 */
	@Override
	public void removeByFileVersionId(long fileVersionId) {
		_collectionPersistenceFinderByFileVersionId.remove(
			finderCache, new Object[] {fileVersionId});
	}

	/**
	 * Returns the number of am image entries where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByFileVersionId(long fileVersionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByFileVersionId.count(
				finderCache, new Object[] {fileVersionId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;
	private CollectionPersistenceFinder<AMImageEntry>
		_collectionPersistenceFinderByC_C;

	/**
	 * Returns all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByC_C(
		long companyId, String configurationUuid) {

		return findByC_C(
			companyId, configurationUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByC_C(
		long companyId, String configurationUuid, int start, int end) {

		return findByC_C(companyId, configurationUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByC_C(
		long companyId, String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return findByC_C(
			companyId, configurationUuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByC_C(
		long companyId, String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByC_C.find(
				finderCache, new Object[] {companyId, configurationUuid}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByC_C_First(
			long companyId, String configurationUuid,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {

		AMImageEntry amImageEntry = fetchByC_C_First(
			companyId, configurationUuid, orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		throw new NoSuchAMImageEntryException(
			_collectionPersistenceFinderByC_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, configurationUuid}));
	}

	/**
	 * Returns the first am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByC_C_First(
		long companyId, String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return _collectionPersistenceFinderByC_C.fetchFirst(
			finderCache, new Object[] {companyId, configurationUuid},
			orderByComparator);
	}

	/**
	 * Removes all the am image entries where companyId = &#63; and configurationUuid = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 */
	@Override
	public void removeByC_C(long companyId, String configurationUuid) {
		_collectionPersistenceFinderByC_C.remove(
			finderCache, new Object[] {companyId, configurationUuid});
	}

	/**
	 * Returns the number of am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByC_C(long companyId, String configurationUuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _collectionPersistenceFinderByC_C.count(
				finderCache, new Object[] {companyId, configurationUuid});
		}
	}

	private FinderPath _finderPathFetchByC_F;
	private UniquePersistenceFinder<AMImageEntry> _uniquePersistenceFinderByC_F;

	/**
	 * Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or throws a <code>NoSuchAMImageEntryException</code> if it could not be found.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByC_F(String configurationUuid, long fileVersionId)
		throws NoSuchAMImageEntryException {

		AMImageEntry amImageEntry = fetchByC_F(
			configurationUuid, fileVersionId);

		if (amImageEntry == null) {
			String message =
				_uniquePersistenceFinderByC_F.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {configurationUuid, fileVersionId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchAMImageEntryException(message);
		}

		return amImageEntry;
	}

	/**
	 * Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByC_F(
		String configurationUuid, long fileVersionId) {

		return fetchByC_F(configurationUuid, fileVersionId, true);
	}

	/**
	 * Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByC_F(
		String configurationUuid, long fileVersionId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			return _uniquePersistenceFinderByC_F.fetch(
				finderCache, new Object[] {configurationUuid, fileVersionId},
				useFinderCache);
		}
	}

	/**
	 * Removes the am image entry where configurationUuid = &#63; and fileVersionId = &#63; from the database.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the am image entry that was removed
	 */
	@Override
	public AMImageEntry removeByC_F(
			String configurationUuid, long fileVersionId)
		throws NoSuchAMImageEntryException {

		AMImageEntry amImageEntry = findByC_F(configurationUuid, fileVersionId);

		return remove(amImageEntry);
	}

	/**
	 * Returns the number of am image entries where configurationUuid = &#63; and fileVersionId = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByC_F(String configurationUuid, long fileVersionId) {
		return _uniquePersistenceFinderByC_F.count(
			finderCache, new Object[] {configurationUuid, fileVersionId});
	}

	public AMImageEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AMImageEntry.class);

		setModelImplClass(AMImageEntryImpl.class);
		setModelPKClass(long.class);

		setTable(AMImageEntryTable.INSTANCE);
	}

	/**
	 * Caches the am image entry in the entity cache if it is enabled.
	 *
	 * @param amImageEntry the am image entry
	 */
	@Override
	public void cacheResult(AMImageEntry amImageEntry) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					amImageEntry.getCtCollectionId())) {

			entityCache.putResult(
				AMImageEntryImpl.class, amImageEntry.getPrimaryKey(),
				amImageEntry);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					amImageEntry.getUuid(), amImageEntry.getGroupId()
				},
				amImageEntry);

			finderCache.putResult(
				_finderPathFetchByC_F,
				new Object[] {
					amImageEntry.getConfigurationUuid(),
					amImageEntry.getFileVersionId()
				},
				amImageEntry);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the am image entries in the entity cache if it is enabled.
	 *
	 * @param amImageEntries the am image entries
	 */
	@Override
	public void cacheResult(List<AMImageEntry> amImageEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (amImageEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AMImageEntry amImageEntry : amImageEntries) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						amImageEntry.getCtCollectionId())) {

				if (entityCache.getResult(
						AMImageEntryImpl.class, amImageEntry.getPrimaryKey()) ==
							null) {

					cacheResult(amImageEntry);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		AMImageEntryModelImpl amImageEntryModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					amImageEntryModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				amImageEntryModelImpl.getUuid(),
				amImageEntryModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, amImageEntryModelImpl);

			args = new Object[] {
				amImageEntryModelImpl.getConfigurationUuid(),
				amImageEntryModelImpl.getFileVersionId()
			};

			finderCache.putResult(
				_finderPathFetchByC_F, args, amImageEntryModelImpl);
		}
	}

	/**
	 * Creates a new am image entry with the primary key. Does not add the am image entry to the database.
	 *
	 * @param amImageEntryId the primary key for the new am image entry
	 * @return the new am image entry
	 */
	@Override
	public AMImageEntry create(long amImageEntryId) {
		AMImageEntry amImageEntry = new AMImageEntryImpl();

		amImageEntry.setNew(true);
		amImageEntry.setPrimaryKey(amImageEntryId);

		String uuid = PortalUUIDUtil.generate();

		amImageEntry.setUuid(uuid);

		amImageEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return amImageEntry;
	}

	/**
	 * Removes the am image entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param amImageEntryId the primary key of the am image entry
	 * @return the am image entry that was removed
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry remove(long amImageEntryId)
		throws NoSuchAMImageEntryException {

		return remove((Serializable)amImageEntryId);
	}

	@Override
	protected AMImageEntry removeImpl(AMImageEntry amImageEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(amImageEntry)) {
				amImageEntry = (AMImageEntry)session.get(
					AMImageEntryImpl.class, amImageEntry.getPrimaryKeyObj());
			}

			if ((amImageEntry != null) &&
				ctPersistenceHelper.isRemove(amImageEntry)) {

				session.delete(amImageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (amImageEntry != null) {
			clearCache(amImageEntry);
		}

		return amImageEntry;
	}

	@Override
	public AMImageEntry updateImpl(AMImageEntry amImageEntry) {
		boolean isNew = amImageEntry.isNew();

		if (!(amImageEntry instanceof AMImageEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(amImageEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					amImageEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in amImageEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AMImageEntry implementation " +
					amImageEntry.getClass());
		}

		AMImageEntryModelImpl amImageEntryModelImpl =
			(AMImageEntryModelImpl)amImageEntry;

		if (Validator.isNull(amImageEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			amImageEntry.setUuid(uuid);
		}

		if (isNew && (amImageEntry.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				amImageEntry.setCreateDate(date);
			}
			else {
				amImageEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(amImageEntry)) {
				if (!isNew) {
					session.evict(
						AMImageEntryImpl.class,
						amImageEntry.getPrimaryKeyObj());
				}

				session.save(amImageEntry);
			}
			else {
				amImageEntry = (AMImageEntry)session.merge(amImageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AMImageEntryImpl.class, amImageEntryModelImpl, false, true);

		cacheUniqueFindersCache(amImageEntryModelImpl);

		if (isNew) {
			amImageEntry.setNew(false);
		}

		amImageEntry.resetOriginalValues();

		return amImageEntry;
	}

	/**
	 * Returns the am image entry with the primary key or throws a <code>NoSuchAMImageEntryException</code> if it could not be found.
	 *
	 * @param amImageEntryId the primary key of the am image entry
	 * @return the am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry findByPrimaryKey(long amImageEntryId)
		throws NoSuchAMImageEntryException {

		return findByPrimaryKey((Serializable)amImageEntryId);
	}

	/**
	 * Returns the am image entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the am image entry
	 * @return the am image entry, or <code>null</code> if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				AMImageEntry.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		AMImageEntry amImageEntry = (AMImageEntry)entityCache.getResult(
			AMImageEntryImpl.class, primaryKey);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		Session session = null;

		try {
			session = openSession();

			amImageEntry = (AMImageEntry)session.get(
				AMImageEntryImpl.class, primaryKey);

			if (amImageEntry != null) {
				cacheResult(amImageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return amImageEntry;
	}

	/**
	 * Returns the am image entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param amImageEntryId the primary key of the am image entry
	 * @return the am image entry, or <code>null</code> if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry fetchByPrimaryKey(long amImageEntryId) {
		return fetchByPrimaryKey((Serializable)amImageEntryId);
	}

	@Override
	public Map<Serializable, AMImageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(AMImageEntry.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AMImageEntry> map =
			new HashMap<Serializable, AMImageEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AMImageEntry amImageEntry = fetchByPrimaryKey(primaryKey);

			if (amImageEntry != null) {
				map.put(primaryKey, amImageEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						AMImageEntry.class, primaryKey)) {

				AMImageEntry amImageEntry = (AMImageEntry)entityCache.getResult(
					AMImageEntryImpl.class, primaryKey);

				if (amImageEntry == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, amImageEntry);
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

			for (AMImageEntry amImageEntry : (List<AMImageEntry>)query.list()) {
				map.put(amImageEntry.getPrimaryKeyObj(), amImageEntry);

				cacheResult(amImageEntry);
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
	 * Returns all the am image entries.
	 *
	 * @return the am image entries
	 */
	@Override
	public List<AMImageEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of am image entries
	 */
	@Override
	public List<AMImageEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of am image entries
	 */
	@Override
	public List<AMImageEntry> findAll(
		int start, int end, OrderByComparator<AMImageEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of am image entries
	 */
	@Override
	public List<AMImageEntry> findAll(
		int start, int end, OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

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

			List<AMImageEntry> list = null;

			if (useFinderCache) {
				list = (List<AMImageEntry>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_AMIMAGEENTRY);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_AMIMAGEENTRY;

					sql = sql.concat(AMImageEntryModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<AMImageEntry>)QueryUtil.list(
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
	 * Removes all the am image entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AMImageEntry amImageEntry : findAll()) {
			remove(amImageEntry);
		}
	}

	/**
	 * Returns the number of am image entries.
	 *
	 * @return the number of am image entries
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AMImageEntry.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_AMIMAGEENTRY);

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
		return "amImageEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_AMIMAGEENTRY;
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
		return AMImageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AMImageEntry";
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
		ctStrictColumnNames.add("createDate");
		ctMergeColumnNames.add("configurationUuid");
		ctMergeColumnNames.add("fileVersionId");
		ctMergeColumnNames.add("mimeType");
		ctMergeColumnNames.add("height");
		ctMergeColumnNames.add("width");
		ctMergeColumnNames.add("size_");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("amImageEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"configurationUuid", "fileVersionId"});
	}

	/**
	 * Initializes the am image entry persistence.
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
			_SQL_SELECT_AMIMAGEENTRY_WHERE, _SQL_COUNT_AMIMAGEENTRY_WHERE,
			AMImageEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"amImageEntry.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, AMImageEntry::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_AMIMAGEENTRY_WHERE,
			new FinderColumn<>(
				"amImageEntry.", "uuid", FinderColumn.Type.STRING, "=", true,
				false, AMImageEntry::getUuid),
			new FinderColumn<>(
				"amImageEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, AMImageEntry::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_AMIMAGEENTRY_WHERE,
				_SQL_COUNT_AMIMAGEENTRY_WHERE,
				AMImageEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"amImageEntry.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, AMImageEntry::getUuid),
				new FinderColumn<>(
					"amImageEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, AMImageEntry::getCompanyId));

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
				_finderPathCountByGroupId, _SQL_SELECT_AMIMAGEENTRY_WHERE,
				_SQL_COUNT_AMIMAGEENTRY_WHERE,
				AMImageEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"amImageEntry.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, AMImageEntry::getGroupId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_AMIMAGEENTRY_WHERE,
				_SQL_COUNT_AMIMAGEENTRY_WHERE,
				AMImageEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"amImageEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, AMImageEntry::getCompanyId));

		_finderPathWithPaginationFindByConfigurationUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByConfigurationUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"configurationUuid"}, true);

		_finderPathWithoutPaginationFindByConfigurationUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByConfigurationUuid", new String[] {String.class.getName()},
			new String[] {"configurationUuid"}, true);

		_finderPathCountByConfigurationUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByConfigurationUuid", new String[] {String.class.getName()},
			new String[] {"configurationUuid"}, false);

		_collectionPersistenceFinderByConfigurationUuid =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByConfigurationUuid,
				_finderPathWithoutPaginationFindByConfigurationUuid,
				_finderPathCountByConfigurationUuid,
				_SQL_SELECT_AMIMAGEENTRY_WHERE, _SQL_COUNT_AMIMAGEENTRY_WHERE,
				AMImageEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"amImageEntry.", "configurationUuid",
					FinderColumn.Type.STRING, "=", true, true,
					AMImageEntry::getConfigurationUuid));

		_finderPathWithPaginationFindByFileVersionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileVersionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"fileVersionId"}, true);

		_finderPathWithoutPaginationFindByFileVersionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileVersionId",
			new String[] {Long.class.getName()}, new String[] {"fileVersionId"},
			true);

		_finderPathCountByFileVersionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileVersionId",
			new String[] {Long.class.getName()}, new String[] {"fileVersionId"},
			false);

		_collectionPersistenceFinderByFileVersionId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByFileVersionId,
				_finderPathWithoutPaginationFindByFileVersionId,
				_finderPathCountByFileVersionId, _SQL_SELECT_AMIMAGEENTRY_WHERE,
				_SQL_COUNT_AMIMAGEENTRY_WHERE,
				AMImageEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"amImageEntry.", "fileVersionId", FinderColumn.Type.LONG,
					"=", true, true, AMImageEntry::getFileVersionId));

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "configurationUuid"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "configurationUuid"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "configurationUuid"}, false);

		_collectionPersistenceFinderByC_C = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_C,
			_finderPathWithoutPaginationFindByC_C, _finderPathCountByC_C,
			_SQL_SELECT_AMIMAGEENTRY_WHERE, _SQL_COUNT_AMIMAGEENTRY_WHERE,
			AMImageEntryModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"amImageEntry.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, AMImageEntry::getCompanyId),
			new FinderColumn<>(
				"amImageEntry.", "configurationUuid", FinderColumn.Type.STRING,
				"=", true, true, AMImageEntry::getConfigurationUuid));

		_finderPathFetchByC_F = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_F",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"configurationUuid", "fileVersionId"}, true);

		_uniquePersistenceFinderByC_F = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_F, _SQL_SELECT_AMIMAGEENTRY_WHERE,
			new FinderColumn<>(
				"amImageEntry.", "configurationUuid", FinderColumn.Type.STRING,
				"=", true, false, AMImageEntry::getConfigurationUuid),
			new FinderColumn<>(
				"amImageEntry.", "fileVersionId", FinderColumn.Type.LONG, "=",
				true, true, AMImageEntry::getFileVersionId));

		AMImageEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		AMImageEntryUtil.setPersistence(null);

		entityCache.removeCache(AMImageEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = AMImageEntryPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AMImageEntryPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AMImageEntryPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_AMIMAGEENTRY =
		"SELECT amImageEntry FROM AMImageEntry amImageEntry";

	private static final String _SQL_SELECT_AMIMAGEENTRY_WHERE =
		"SELECT amImageEntry FROM AMImageEntry amImageEntry WHERE ";

	private static final String _SQL_COUNT_AMIMAGEENTRY =
		"SELECT COUNT(amImageEntry) FROM AMImageEntry amImageEntry";

	private static final String _SQL_COUNT_AMIMAGEENTRY_WHERE =
		"SELECT COUNT(amImageEntry) FROM AMImageEntry amImageEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "amImageEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AMImageEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AMImageEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "size"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:432127838