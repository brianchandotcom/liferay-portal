/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.exception.DuplicateGroupExternalReferenceCodeException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.GroupUtil;
import com.liferay.portal.kernel.service.persistence.OrganizationPersistence;
import com.liferay.portal.kernel.service.persistence.RolePersistence;
import com.liferay.portal.kernel.service.persistence.UserGroupPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.GroupModelImpl;

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
 * The persistence implementation for the group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class GroupPersistenceImpl
	extends BasePersistenceImpl<Group, NoSuchGroupException>
	implements GroupPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>GroupUtil</code> to access the group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		GroupImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the groups where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByUuid.find(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByUuid_First(
			String uuid, OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByUuid_First(uuid, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByUuid_First(
		String uuid, OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid},
			orderByComparator);
	}

	/**
	 * Removes all the groups where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid});
	}

	/**
	 * Returns the number of groups where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching groups
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByUuid.count(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<Group> _uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the group where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByUUID_G(String uuid, long groupId)
		throws NoSuchGroupException {

		Group group = fetchByUUID_G(uuid, groupId);

		if (group == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchGroupException(message);
		}

		return group;
	}

	/**
	 * Returns the group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				FinderCacheUtil.getFinderCache(), new Object[] {uuid, groupId},
				useFinderCache);
		}
	}

	/**
	 * Removes the group where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the group that was removed
	 */
	@Override
	public Group removeByUUID_G(String uuid, long groupId)
		throws NoSuchGroupException {

		Group group = findByUUID_G(uuid, groupId);

		return remove(group);
	}

	/**
	 * Returns the number of groups where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching groups
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the groups where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {uuid, companyId}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {uuid, companyId},
			orderByComparator);
	}

	/**
	 * Removes all the groups where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of groups where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching groups
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByCompanyId_First(
			long companyId, OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByCompanyId_First(companyId, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByCompanyId_First(
		long companyId, OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId},
			orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId});
	}

	/**
	 * Returns the number of groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching groups
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByLiveGroupId;
	private FinderPath _finderPathWithoutPaginationFindByLiveGroupId;
	private FinderPath _finderPathCountByLiveGroupId;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByLiveGroupId;

	/**
	 * Returns all the groups where liveGroupId = &#63;.
	 *
	 * @param liveGroupId the live group ID
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByLiveGroupId(long liveGroupId) {
		return findByLiveGroupId(
			liveGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where liveGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param liveGroupId the live group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByLiveGroupId(long liveGroupId, int start, int end) {
		return findByLiveGroupId(liveGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where liveGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param liveGroupId the live group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByLiveGroupId(
		long liveGroupId, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByLiveGroupId(
			liveGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where liveGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param liveGroupId the live group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByLiveGroupId(
		long liveGroupId, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByLiveGroupId.find(
				FinderCacheUtil.getFinderCache(), new Object[] {liveGroupId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where liveGroupId = &#63;.
	 *
	 * @param liveGroupId the live group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByLiveGroupId_First(
			long liveGroupId, OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByLiveGroupId_First(liveGroupId, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByLiveGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {liveGroupId}));
	}

	/**
	 * Returns the first group in the ordered set where liveGroupId = &#63;.
	 *
	 * @param liveGroupId the live group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByLiveGroupId_First(
		long liveGroupId, OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByLiveGroupId.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {liveGroupId},
			orderByComparator);
	}

	/**
	 * Removes all the groups where liveGroupId = &#63; from the database.
	 *
	 * @param liveGroupId the live group ID
	 */
	@Override
	public void removeByLiveGroupId(long liveGroupId) {
		_collectionPersistenceFinderByLiveGroupId.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {liveGroupId});
	}

	/**
	 * Returns the number of groups where liveGroupId = &#63;.
	 *
	 * @param liveGroupId the live group ID
	 * @return the number of matching groups
	 */
	@Override
	public int countByLiveGroupId(long liveGroupId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByLiveGroupId.count(
				FinderCacheUtil.getFinderCache(), new Object[] {liveGroupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_C;

	/**
	 * Returns all the groups where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_C(long companyId, long classNameId) {
		return findByC_C(
			companyId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_C(
		long companyId, long classNameId, int start, int end) {

		return findByC_C(companyId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_C(
		long companyId, long classNameId, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_C(
			companyId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_C(
		long companyId, long classNameId, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_C.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, classNameId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_C_First(
			long companyId, long classNameId,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_C_First(
			companyId, classNameId, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, classNameId}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_C_First(
		long companyId, long classNameId,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_C.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, classNameId}, orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByC_C(long companyId, long classNameId) {
		_collectionPersistenceFinderByC_C.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, classNameId});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_C(long companyId, long classNameId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_C.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, classNameId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_P;
	private FinderPath _finderPathWithoutPaginationFindByC_P;
	private FinderPath _finderPathCountByC_P;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_P;

	/**
	 * Returns all the groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_P(long companyId, long parentGroupId) {
		return findByC_P(
			companyId, parentGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_P(
		long companyId, long parentGroupId, int start, int end) {

		return findByC_P(companyId, parentGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_P(
		long companyId, long parentGroupId, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_P(
			companyId, parentGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_P(
		long companyId, long parentGroupId, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_P.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, parentGroupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_P_First(
			long companyId, long parentGroupId,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_P_First(
			companyId, parentGroupId, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, parentGroupId}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_P_First(
		long companyId, long parentGroupId,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_P.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, parentGroupId}, orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and parentGroupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 */
	@Override
	public void removeByC_P(long companyId, long parentGroupId) {
		_collectionPersistenceFinderByC_P.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, parentGroupId});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_P(long companyId, long parentGroupId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_P.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, parentGroupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_GK;
	private FinderPath _finderPathWithoutPaginationFindByC_GK;
	private FinderPath _finderPathFetchByC_GK;
	private FinderPath _finderPathCountByC_GK;
	private FinderPath _finderPathWithPaginationCountByC_GK;

	/**
	 * Returns all the groups where companyId = &#63; and groupKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupKeys the group keys
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_GK(long companyId, String[] groupKeys) {
		return findByC_GK(
			companyId, groupKeys, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and groupKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupKeys the group keys
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_GK(
		long companyId, String[] groupKeys, int start, int end) {

		return findByC_GK(companyId, groupKeys, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and groupKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupKeys the group keys
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_GK(
		long companyId, String[] groupKeys, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_GK(
			companyId, groupKeys, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and groupKey = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupKeys the group keys
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_GK(
		long companyId, String[] groupKeys, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		if (groupKeys == null) {
			groupKeys = new String[0];
		}
		else if (groupKeys.length > 1) {
			for (int i = 0; i < groupKeys.length; i++) {
				groupKeys[i] = Objects.toString(groupKeys[i], "");
			}

			groupKeys = ArrayUtil.sortedUnique(groupKeys);
		}

		if (groupKeys.length == 1) {
			Group group = fetchByC_GK(companyId, groupKeys[0]);

			if (group == null) {
				return Collections.emptyList();
			}
			else {
				List<Group> list = new ArrayList<Group>(1);

				list.add(group);

				return list;
			}
		}

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						companyId, StringUtil.merge(groupKeys)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, StringUtil.merge(groupKeys), start, end,
					orderByComparator
				};
			}

			List<Group> list = null;

			if (useFinderCache) {
				list = (List<Group>)FinderCacheUtil.getResult(
					_finderPathWithPaginationFindByC_GK, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (Group group : list) {
						if ((companyId != group.getCompanyId()) ||
							!ArrayUtil.contains(
								groupKeys, group.getGroupKey())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_GROUP__WHERE);

				sb.append(_FINDER_COLUMN_C_GK_COMPANYID_2);

				if (groupKeys.length > 0) {
					sb.append("(");

					for (int i = 0; i < groupKeys.length; i++) {
						String groupKey = groupKeys[i];

						if (groupKey.isEmpty()) {
							sb.append(_FINDER_COLUMN_C_GK_GROUPKEY_3);
						}
						else {
							sb.append(_FINDER_COLUMN_C_GK_GROUPKEY_2);
						}

						if ((i + 1) < groupKeys.length) {
							sb.append(WHERE_OR);
						}
					}

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
					sb.append(GroupModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					for (String groupKey : groupKeys) {
						if ((groupKey != null) && !groupKey.isEmpty()) {
							queryPos.add(groupKey);
						}
					}

					list = (List<Group>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathWithPaginationFindByC_GK, finderArgs,
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
	 * Returns the group where companyId = &#63; and groupKey = &#63; or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param groupKey the group key
	 * @return the matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_GK(long companyId, String groupKey)
		throws NoSuchGroupException {

		Group group = fetchByC_GK(companyId, groupKey);

		if (group == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", groupKey=");
			sb.append(groupKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchGroupException(sb.toString());
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupKey the group key
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_GK(long companyId, String groupKey) {
		return fetchByC_GK(companyId, groupKey, true);
	}

	/**
	 * Returns the group where companyId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupKey the group key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_GK(
		long companyId, String groupKey, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			groupKey = Objects.toString(groupKey, "");

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {companyId, groupKey};
			}

			Object result = null;

			if (useFinderCache) {
				result = FinderCacheUtil.getResult(
					_finderPathFetchByC_GK, finderArgs, this);
			}

			if (result instanceof Group) {
				Group group = (Group)result;

				if ((companyId != group.getCompanyId()) ||
					!Objects.equals(groupKey, group.getGroupKey())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_GROUP__WHERE);

				sb.append(_FINDER_COLUMN_C_GK_COMPANYID_2);

				boolean bindGroupKey = false;

				if (groupKey.isEmpty()) {
					sb.append(_FINDER_COLUMN_C_GK_GROUPKEY_3);
				}
				else {
					bindGroupKey = true;

					sb.append(_FINDER_COLUMN_C_GK_GROUPKEY_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					if (bindGroupKey) {
						queryPos.add(groupKey);
					}

					List<Group> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							FinderCacheUtil.putResult(
								_finderPathFetchByC_GK, finderArgs, list);
						}
					}
					else {
						Group group = list.get(0);

						result = group;

						cacheResult(group);
					}
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Group)result;
			}
		}
	}

	/**
	 * Removes the group where companyId = &#63; and groupKey = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupKey the group key
	 * @return the group that was removed
	 */
	@Override
	public Group removeByC_GK(long companyId, String groupKey)
		throws NoSuchGroupException {

		Group group = findByC_GK(companyId, groupKey);

		return remove(group);
	}

	/**
	 * Returns the number of groups where companyId = &#63; and groupKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupKey the group key
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_GK(long companyId, String groupKey) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			groupKey = Objects.toString(groupKey, "");

			FinderPath finderPath = _finderPathCountByC_GK;

			Object[] finderArgs = new Object[] {companyId, groupKey};

			Long count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_GROUP__WHERE);

				sb.append(_FINDER_COLUMN_C_GK_COMPANYID_2);

				boolean bindGroupKey = false;

				if (groupKey.isEmpty()) {
					sb.append(_FINDER_COLUMN_C_GK_GROUPKEY_3);
				}
				else {
					bindGroupKey = true;

					sb.append(_FINDER_COLUMN_C_GK_GROUPKEY_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					if (bindGroupKey) {
						queryPos.add(groupKey);
					}

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
	 * Returns the number of groups where companyId = &#63; and groupKey = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupKeys the group keys
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_GK(long companyId, String[] groupKeys) {
		if (groupKeys == null) {
			groupKeys = new String[0];
		}
		else if (groupKeys.length > 1) {
			for (int i = 0; i < groupKeys.length; i++) {
				groupKeys[i] = Objects.toString(groupKeys[i], "");
			}

			groupKeys = ArrayUtil.sortedUnique(groupKeys);
		}

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			Object[] finderArgs = new Object[] {
				companyId, StringUtil.merge(groupKeys)
			};

			Long count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByC_GK, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_GROUP__WHERE);

				sb.append(_FINDER_COLUMN_C_GK_COMPANYID_2);

				if (groupKeys.length > 0) {
					sb.append("(");

					for (int i = 0; i < groupKeys.length; i++) {
						String groupKey = groupKeys[i];

						if (groupKey.isEmpty()) {
							sb.append(_FINDER_COLUMN_C_GK_GROUPKEY_3);
						}
						else {
							sb.append(_FINDER_COLUMN_C_GK_GROUPKEY_2);
						}

						if ((i + 1) < groupKeys.length) {
							sb.append(WHERE_OR);
						}
					}

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

					for (String groupKey : groupKeys) {
						if ((groupKey != null) && !groupKey.isEmpty()) {
							queryPos.add(groupKey);
						}
					}

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByC_GK, finderArgs,
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

	private static final String _FINDER_COLUMN_C_GK_COMPANYID_2 =
		"group_.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_GK_GROUPKEY_2 =
		"group_.groupKey = ?";

	private static final String _FINDER_COLUMN_C_GK_GROUPKEY_3 =
		"(group_.groupKey IS NULL OR group_.groupKey = '')";

	private FinderPath _finderPathFetchByC_F;
	private UniquePersistenceFinder<Group> _uniquePersistenceFinderByC_F;

	/**
	 * Returns the group where companyId = &#63; and friendlyURL = &#63; or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_F(long companyId, String friendlyURL)
		throws NoSuchGroupException {

		Group group = fetchByC_F(companyId, friendlyURL);

		if (group == null) {
			String message =
				_uniquePersistenceFinderByC_F.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {companyId, friendlyURL});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchGroupException(message);
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_F(long companyId, String friendlyURL) {
		return fetchByC_F(companyId, friendlyURL, true);
	}

	/**
	 * Returns the group where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_F(
		long companyId, String friendlyURL, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _uniquePersistenceFinderByC_F.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, friendlyURL}, useFinderCache);
		}
	}

	/**
	 * Removes the group where companyId = &#63; and friendlyURL = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the group that was removed
	 */
	@Override
	public Group removeByC_F(long companyId, String friendlyURL)
		throws NoSuchGroupException {

		Group group = findByC_F(companyId, friendlyURL);

		return remove(group);
	}

	/**
	 * Returns the number of groups where companyId = &#63; and friendlyURL = &#63;.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_F(long companyId, String friendlyURL) {
		return _uniquePersistenceFinderByC_F.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, friendlyURL});
	}

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_S;

	/**
	 * Returns all the groups where companyId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_S(long companyId, boolean site) {
		return findByC_S(
			companyId, site, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_S(
		long companyId, boolean site, int start, int end) {

		return findByC_S(companyId, site, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_S(
		long companyId, boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_S(companyId, site, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_S(
		long companyId, boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, site}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_S_First(
			long companyId, boolean site,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_S_First(companyId, site, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, site}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_S_First(
		long companyId, boolean site,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_S.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId, site},
			orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and site = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 */
	@Override
	public void removeByC_S(long companyId, boolean site) {
		_collectionPersistenceFinderByC_S.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId, site});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_S(long companyId, boolean site) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, site});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A;
	private FinderPath _finderPathCountByC_A;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_A;

	/**
	 * Returns all the groups where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_A(long companyId, boolean active) {
		return findByC_A(
			companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_A(
		long companyId, boolean active, int start, int end) {

		return findByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_A(
			companyId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_A.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, active}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_A_First(companyId, active, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_A.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, active}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_A.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId, active},
			orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	@Override
	public void removeByC_A(long companyId, boolean active) {
		_collectionPersistenceFinderByC_A.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId, active});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_A(long companyId, boolean active) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_A.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, active});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_CPK;
	private FinderPath _finderPathWithoutPaginationFindByC_CPK;
	private FinderPath _finderPathCountByC_CPK;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_CPK;

	/**
	 * Returns all the groups where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_CPK(long classNameId, long classPK) {
		return findByC_CPK(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_CPK(
		long classNameId, long classPK, int start, int end) {

		return findByC_CPK(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_CPK(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_CPK.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {classNameId, classPK}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_CPK_First(
			long classNameId, long classPK,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_CPK_First(
			classNameId, classPK, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_CPK.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {classNameId, classPK}));
	}

	/**
	 * Returns the first group in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_CPK_First(
		long classNameId, long classPK,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_CPK.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {classNameId, classPK}, orderByComparator);
	}

	/**
	 * Removes all the groups where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_CPK(long classNameId, long classPK) {
		_collectionPersistenceFinderByC_CPK.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {classNameId, classPK});
	}

	/**
	 * Returns the number of groups where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_CPK(long classNameId, long classPK) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_CPK.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {classNameId, classPK});
		}
	}

	private FinderPath _finderPathWithPaginationFindByT_A;
	private FinderPath _finderPathWithoutPaginationFindByT_A;
	private FinderPath _finderPathCountByT_A;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByT_A;

	/**
	 * Returns all the groups where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByT_A(int type, boolean active) {
		return findByT_A(
			type, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByT_A(int type, boolean active, int start, int end) {
		return findByT_A(type, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByT_A(
		int type, boolean active, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByT_A(type, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByT_A(
		int type, boolean active, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByT_A.find(
				FinderCacheUtil.getFinderCache(), new Object[] {type, active},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByT_A_First(
			int type, boolean active,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByT_A_First(type, active, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByT_A.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {type, active}));
	}

	/**
	 * Returns the first group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByT_A_First(
		int type, boolean active, OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByT_A.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {type, active},
			orderByComparator);
	}

	/**
	 * Removes all the groups where type = &#63; and active = &#63; from the database.
	 *
	 * @param type the type
	 * @param active the active
	 */
	@Override
	public void removeByT_A(int type, boolean active) {
		_collectionPersistenceFinderByT_A.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {type, active});
	}

	/**
	 * Returns the number of groups where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @return the number of matching groups
	 */
	@Override
	public int countByT_A(int type, boolean active) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByT_A.count(
				FinderCacheUtil.getFinderCache(), new Object[] {type, active});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGtG_C_P;
	private FinderPath _finderPathWithPaginationCountByGtG_C_P;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByGtG_C_P;

	/**
	 * Returns all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByGtG_C_P(
		long groupId, long companyId, long parentGroupId) {

		return findByGtG_C_P(
			groupId, companyId, parentGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByGtG_C_P(
		long groupId, long companyId, long parentGroupId, int start, int end) {

		return findByGtG_C_P(
			groupId, companyId, parentGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByGtG_C_P(
		long groupId, long companyId, long parentGroupId, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByGtG_C_P(
			groupId, companyId, parentGroupId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByGtG_C_P(
		long groupId, long companyId, long parentGroupId, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByGtG_C_P.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, companyId, parentGroupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByGtG_C_P_First(
			long groupId, long companyId, long parentGroupId,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByGtG_C_P_First(
			groupId, companyId, parentGroupId, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByGtG_C_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, companyId, parentGroupId}));
	}

	/**
	 * Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByGtG_C_P_First(
		long groupId, long companyId, long parentGroupId,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByGtG_C_P.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, companyId, parentGroupId},
			orderByComparator);
	}

	/**
	 * Removes all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 */
	@Override
	public void removeByGtG_C_P(
		long groupId, long companyId, long parentGroupId) {

		_collectionPersistenceFinderByGtG_C_P.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, companyId, parentGroupId});
	}

	/**
	 * Returns the number of groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @return the number of matching groups
	 */
	@Override
	public int countByGtG_C_P(
		long groupId, long companyId, long parentGroupId) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByGtG_C_P.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, companyId, parentGroupId});
		}
	}

	private FinderPath _finderPathFetchByC_C_C;
	private UniquePersistenceFinder<Group> _uniquePersistenceFinderByC_C_C;

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchGroupException {

		Group group = fetchByC_C_C(companyId, classNameId, classPK);

		if (group == null) {
			String message =
				_uniquePersistenceFinderByC_C_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {companyId, classNameId, classPK});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchGroupException(message);
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_C_C(long companyId, long classNameId, long classPK) {
		return fetchByC_C_C(companyId, classNameId, classPK, true);
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_C_C(
		long companyId, long classNameId, long classPK,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _uniquePersistenceFinderByC_C_C.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, classNameId, classPK}, useFinderCache);
		}
	}

	/**
	 * Removes the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the group that was removed
	 */
	@Override
	public Group removeByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchGroupException {

		Group group = findByC_C_C(companyId, classNameId, classPK);

		return remove(group);
	}

	/**
	 * Returns the number of groups where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_C_C(long companyId, long classNameId, long classPK) {
		return _uniquePersistenceFinderByC_C_C.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, classNameId, classPK});
	}

	private FinderPath _finderPathWithPaginationFindByC_C_P;
	private FinderPath _finderPathWithoutPaginationFindByC_C_P;
	private FinderPath _finderPathCountByC_C_P;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_C_P;

	/**
	 * Returns all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_C_P(
		long companyId, long classNameId, long parentGroupId) {

		return findByC_C_P(
			companyId, classNameId, parentGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_C_P(
		long companyId, long classNameId, long parentGroupId, int start,
		int end) {

		return findByC_C_P(
			companyId, classNameId, parentGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_C_P(
		long companyId, long classNameId, long parentGroupId, int start,
		int end, OrderByComparator<Group> orderByComparator) {

		return findByC_C_P(
			companyId, classNameId, parentGroupId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_C_P(
		long companyId, long classNameId, long parentGroupId, int start,
		int end, OrderByComparator<Group> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_C_P.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, classNameId, parentGroupId}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_C_P_First(
			long companyId, long classNameId, long parentGroupId,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_C_P_First(
			companyId, classNameId, parentGroupId, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_C_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, classNameId, parentGroupId}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_C_P_First(
		long companyId, long classNameId, long parentGroupId,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_C_P.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, classNameId, parentGroupId},
			orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 */
	@Override
	public void removeByC_C_P(
		long companyId, long classNameId, long parentGroupId) {

		_collectionPersistenceFinderByC_C_P.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, classNameId, parentGroupId});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_C_P(
		long companyId, long classNameId, long parentGroupId) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_C_P.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, classNameId, parentGroupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_C_S;
	private FinderPath _finderPathWithoutPaginationFindByC_C_S;
	private FinderPath _finderPathCountByC_C_S;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_C_S;

	/**
	 * Returns all the groups where companyId = &#63; and classNameId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param site the site
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_C_S(
		long companyId, long classNameId, boolean site) {

		return findByC_C_S(
			companyId, classNameId, site, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and classNameId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_C_S(
		long companyId, long classNameId, boolean site, int start, int end) {

		return findByC_C_S(companyId, classNameId, site, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_C_S(
		long companyId, long classNameId, boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_C_S(
			companyId, classNameId, site, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_C_S(
		long companyId, long classNameId, boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_C_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, classNameId, site}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_C_S_First(
			long companyId, long classNameId, boolean site,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_C_S_First(
			companyId, classNameId, site, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_C_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, classNameId, site}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_C_S_First(
		long companyId, long classNameId, boolean site,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_C_S.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, classNameId, site}, orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and classNameId = &#63; and site = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param site the site
	 */
	@Override
	public void removeByC_C_S(long companyId, long classNameId, boolean site) {
		_collectionPersistenceFinderByC_C_S.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, classNameId, site});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and classNameId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param site the site
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_C_S(long companyId, long classNameId, boolean site) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_C_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, classNameId, site});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_P_S;
	private FinderPath _finderPathWithoutPaginationFindByC_P_S;
	private FinderPath _finderPathCountByC_P_S;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_P_S;

	/**
	 * Returns all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_P_S(
		long companyId, long parentGroupId, boolean site) {

		return findByC_P_S(
			companyId, parentGroupId, site, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_P_S(
		long companyId, long parentGroupId, boolean site, int start, int end) {

		return findByC_P_S(companyId, parentGroupId, site, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_P_S(
		long companyId, long parentGroupId, boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_P_S(
			companyId, parentGroupId, site, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_P_S(
		long companyId, long parentGroupId, boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_P_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, parentGroupId, site}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_P_S_First(
			long companyId, long parentGroupId, boolean site,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_P_S_First(
			companyId, parentGroupId, site, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_P_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, parentGroupId, site}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_P_S_First(
		long companyId, long parentGroupId, boolean site,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_P_S.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, parentGroupId, site}, orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 */
	@Override
	public void removeByC_P_S(
		long companyId, long parentGroupId, boolean site) {

		_collectionPersistenceFinderByC_P_S.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, parentGroupId, site});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_P_S(long companyId, long parentGroupId, boolean site) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_P_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, parentGroupId, site});
		}
	}

	private FinderPath _finderPathFetchByC_L_GK;
	private UniquePersistenceFinder<Group> _uniquePersistenceFinderByC_L_GK;

	/**
	 * Returns the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param groupKey the group key
	 * @return the matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_L_GK(long companyId, long liveGroupId, String groupKey)
		throws NoSuchGroupException {

		Group group = fetchByC_L_GK(companyId, liveGroupId, groupKey);

		if (group == null) {
			String message =
				_uniquePersistenceFinderByC_L_GK.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {companyId, liveGroupId, groupKey});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchGroupException(message);
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param groupKey the group key
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_L_GK(
		long companyId, long liveGroupId, String groupKey) {

		return fetchByC_L_GK(companyId, liveGroupId, groupKey, true);
	}

	/**
	 * Returns the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param groupKey the group key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_L_GK(
		long companyId, long liveGroupId, String groupKey,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _uniquePersistenceFinderByC_L_GK.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, liveGroupId, groupKey},
				useFinderCache);
		}
	}

	/**
	 * Removes the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param groupKey the group key
	 * @return the group that was removed
	 */
	@Override
	public Group removeByC_L_GK(
			long companyId, long liveGroupId, String groupKey)
		throws NoSuchGroupException {

		Group group = findByC_L_GK(companyId, liveGroupId, groupKey);

		return remove(group);
	}

	/**
	 * Returns the number of groups where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param groupKey the group key
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_L_GK(
		long companyId, long liveGroupId, String groupKey) {

		return _uniquePersistenceFinderByC_L_GK.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, liveGroupId, groupKey});
	}

	private FinderPath _finderPathWithPaginationFindByC_LikeT_S;
	private FinderPath _finderPathWithPaginationCountByC_LikeT_S;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_LikeT_S;

	/**
	 * Returns all the groups where companyId = &#63; and treePath LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param site the site
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_LikeT_S(
		long companyId, String treePath, boolean site) {

		return findByC_LikeT_S(
			companyId, treePath, site, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and treePath LIKE &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_LikeT_S(
		long companyId, String treePath, boolean site, int start, int end) {

		return findByC_LikeT_S(companyId, treePath, site, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and treePath LIKE &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_LikeT_S(
		long companyId, String treePath, boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_LikeT_S(
			companyId, treePath, site, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and treePath LIKE &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_LikeT_S(
		long companyId, String treePath, boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_LikeT_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, treePath, site}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and treePath LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_LikeT_S_First(
			long companyId, String treePath, boolean site,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_LikeT_S_First(
			companyId, treePath, site, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_LikeT_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, treePath, site}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and treePath LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_LikeT_S_First(
		long companyId, String treePath, boolean site,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_LikeT_S.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, treePath, site}, orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and treePath LIKE &#63; and site = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param site the site
	 */
	@Override
	public void removeByC_LikeT_S(
		long companyId, String treePath, boolean site) {

		_collectionPersistenceFinderByC_LikeT_S.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, treePath, site});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and treePath LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param site the site
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_LikeT_S(long companyId, String treePath, boolean site) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_LikeT_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, treePath, site});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_LikeN_S;
	private FinderPath _finderPathWithPaginationCountByC_LikeN_S;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_LikeN_S;

	/**
	 * Returns all the groups where companyId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param site the site
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_LikeN_S(
		long companyId, String name, boolean site) {

		return findByC_LikeN_S(
			companyId, name, site, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_LikeN_S(
		long companyId, String name, boolean site, int start, int end) {

		return findByC_LikeN_S(companyId, name, site, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_LikeN_S(
		long companyId, String name, boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_LikeN_S(
			companyId, name, site, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_LikeN_S(
		long companyId, String name, boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_LikeN_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, name, site}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_LikeN_S_First(
			long companyId, String name, boolean site,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_LikeN_S_First(
			companyId, name, site, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_LikeN_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, name, site}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_LikeN_S_First(
		long companyId, String name, boolean site,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_LikeN_S.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, name, site}, orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and name LIKE &#63; and site = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param site the site
	 */
	@Override
	public void removeByC_LikeN_S(long companyId, String name, boolean site) {
		_collectionPersistenceFinderByC_LikeN_S.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, name, site});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param site the site
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_LikeN_S(long companyId, String name, boolean site) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_LikeN_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, name, site});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_S_A;
	private FinderPath _finderPathWithoutPaginationFindByC_S_A;
	private FinderPath _finderPathCountByC_S_A;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_S_A;

	/**
	 * Returns all the groups where companyId = &#63; and site = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param active the active
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_S_A(
		long companyId, boolean site, boolean active) {

		return findByC_S_A(
			companyId, site, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and site = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_S_A(
		long companyId, boolean site, boolean active, int start, int end) {

		return findByC_S_A(companyId, site, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and site = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_S_A(
		long companyId, boolean site, boolean active, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_S_A(
			companyId, site, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and site = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_S_A(
		long companyId, boolean site, boolean active, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_S_A.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, site, active}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and site = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_S_A_First(
			long companyId, boolean site, boolean active,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_S_A_First(
			companyId, site, active, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_S_A.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, site, active}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and site = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_S_A_First(
		long companyId, boolean site, boolean active,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_S_A.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, site, active}, orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and site = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param active the active
	 */
	@Override
	public void removeByC_S_A(long companyId, boolean site, boolean active) {
		_collectionPersistenceFinderByC_S_A.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, site, active});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and site = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param active the active
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_S_A(long companyId, boolean site, boolean active) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_S_A.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, site, active});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGtG_C_C_P;
	private FinderPath _finderPathWithPaginationCountByGtG_C_C_P;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByGtG_C_C_P;

	/**
	 * Returns all the groups where groupId &gt; &#63; and companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByGtG_C_C_P(
		long groupId, long companyId, long classNameId, long parentGroupId) {

		return findByGtG_C_C_P(
			groupId, companyId, classNameId, parentGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where groupId &gt; &#63; and companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByGtG_C_C_P(
		long groupId, long companyId, long classNameId, long parentGroupId,
		int start, int end) {

		return findByGtG_C_C_P(
			groupId, companyId, classNameId, parentGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where groupId &gt; &#63; and companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByGtG_C_C_P(
		long groupId, long companyId, long classNameId, long parentGroupId,
		int start, int end, OrderByComparator<Group> orderByComparator) {

		return findByGtG_C_C_P(
			groupId, companyId, classNameId, parentGroupId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where groupId &gt; &#63; and companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByGtG_C_C_P(
		long groupId, long companyId, long classNameId, long parentGroupId,
		int start, int end, OrderByComparator<Group> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByGtG_C_C_P.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, companyId, classNameId, parentGroupId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByGtG_C_C_P_First(
			long groupId, long companyId, long classNameId, long parentGroupId,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByGtG_C_C_P_First(
			groupId, companyId, classNameId, parentGroupId, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByGtG_C_C_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, companyId, classNameId, parentGroupId}));
	}

	/**
	 * Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByGtG_C_C_P_First(
		long groupId, long companyId, long classNameId, long parentGroupId,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByGtG_C_C_P.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, companyId, classNameId, parentGroupId},
			orderByComparator);
	}

	/**
	 * Removes all the groups where groupId &gt; &#63; and companyId = &#63; and classNameId = &#63; and parentGroupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 */
	@Override
	public void removeByGtG_C_C_P(
		long groupId, long companyId, long classNameId, long parentGroupId) {

		_collectionPersistenceFinderByGtG_C_C_P.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, companyId, classNameId, parentGroupId});
	}

	/**
	 * Returns the number of groups where groupId &gt; &#63; and companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @return the number of matching groups
	 */
	@Override
	public int countByGtG_C_C_P(
		long groupId, long companyId, long classNameId, long parentGroupId) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByGtG_C_C_P.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, companyId, classNameId, parentGroupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGtG_C_P_S;
	private FinderPath _finderPathWithPaginationCountByGtG_C_P_S;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByGtG_C_P_S;

	/**
	 * Returns all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByGtG_C_P_S(
		long groupId, long companyId, long parentGroupId, boolean site) {

		return findByGtG_C_P_S(
			groupId, companyId, parentGroupId, site, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByGtG_C_P_S(
		long groupId, long companyId, long parentGroupId, boolean site,
		int start, int end) {

		return findByGtG_C_P_S(
			groupId, companyId, parentGroupId, site, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByGtG_C_P_S(
		long groupId, long companyId, long parentGroupId, boolean site,
		int start, int end, OrderByComparator<Group> orderByComparator) {

		return findByGtG_C_P_S(
			groupId, companyId, parentGroupId, site, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByGtG_C_P_S(
		long groupId, long companyId, long parentGroupId, boolean site,
		int start, int end, OrderByComparator<Group> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByGtG_C_P_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, companyId, parentGroupId, site}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByGtG_C_P_S_First(
			long groupId, long companyId, long parentGroupId, boolean site,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByGtG_C_P_S_First(
			groupId, companyId, parentGroupId, site, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByGtG_C_P_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, companyId, parentGroupId, site}));
	}

	/**
	 * Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByGtG_C_P_S_First(
		long groupId, long companyId, long parentGroupId, boolean site,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByGtG_C_P_S.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, companyId, parentGroupId, site},
			orderByComparator);
	}

	/**
	 * Removes all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; and site = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 */
	@Override
	public void removeByGtG_C_P_S(
		long groupId, long companyId, long parentGroupId, boolean site) {

		_collectionPersistenceFinderByGtG_C_P_S.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, companyId, parentGroupId, site});
	}

	/**
	 * Returns the number of groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @return the number of matching groups
	 */
	@Override
	public int countByGtG_C_P_S(
		long groupId, long companyId, long parentGroupId, boolean site) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByGtG_C_P_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {groupId, companyId, parentGroupId, site});
		}
	}

	private FinderPath _finderPathFetchByC_C_L_GK;
	private UniquePersistenceFinder<Group> _uniquePersistenceFinderByC_C_L_GK;

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param groupKey the group key
	 * @return the matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_C_L_GK(
			long companyId, long classNameId, long liveGroupId, String groupKey)
		throws NoSuchGroupException {

		Group group = fetchByC_C_L_GK(
			companyId, classNameId, liveGroupId, groupKey);

		if (group == null) {
			String message =
				_uniquePersistenceFinderByC_C_L_GK.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						companyId, classNameId, liveGroupId, groupKey
					});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchGroupException(message);
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param groupKey the group key
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_C_L_GK(
		long companyId, long classNameId, long liveGroupId, String groupKey) {

		return fetchByC_C_L_GK(
			companyId, classNameId, liveGroupId, groupKey, true);
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param groupKey the group key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_C_L_GK(
		long companyId, long classNameId, long liveGroupId, String groupKey,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _uniquePersistenceFinderByC_C_L_GK.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, classNameId, liveGroupId, groupKey},
				useFinderCache);
		}
	}

	/**
	 * Removes the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param groupKey the group key
	 * @return the group that was removed
	 */
	@Override
	public Group removeByC_C_L_GK(
			long companyId, long classNameId, long liveGroupId, String groupKey)
		throws NoSuchGroupException {

		Group group = findByC_C_L_GK(
			companyId, classNameId, liveGroupId, groupKey);

		return remove(group);
	}

	/**
	 * Returns the number of groups where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param groupKey the group key
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_C_L_GK(
		long companyId, long classNameId, long liveGroupId, String groupKey) {

		return _uniquePersistenceFinderByC_C_L_GK.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, classNameId, liveGroupId, groupKey});
	}

	private FinderPath _finderPathWithPaginationFindByC_P_LikeN_S;
	private FinderPath _finderPathWithPaginationCountByC_P_LikeN_S;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_P_LikeN_S;

	/**
	 * Returns all the groups where companyId = &#63; and parentGroupId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param name the name
	 * @param site the site
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_P_LikeN_S(
		long companyId, long parentGroupId, String name, boolean site) {

		return findByC_P_LikeN_S(
			companyId, parentGroupId, name, site, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param name the name
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_P_LikeN_S(
		long companyId, long parentGroupId, String name, boolean site,
		int start, int end) {

		return findByC_P_LikeN_S(
			companyId, parentGroupId, name, site, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param name the name
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_P_LikeN_S(
		long companyId, long parentGroupId, String name, boolean site,
		int start, int end, OrderByComparator<Group> orderByComparator) {

		return findByC_P_LikeN_S(
			companyId, parentGroupId, name, site, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param name the name
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_P_LikeN_S(
		long companyId, long parentGroupId, String name, boolean site,
		int start, int end, OrderByComparator<Group> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_P_LikeN_S.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, parentGroupId, name, site}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param name the name
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_P_LikeN_S_First(
			long companyId, long parentGroupId, String name, boolean site,
			OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_P_LikeN_S_First(
			companyId, parentGroupId, name, site, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_P_LikeN_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, parentGroupId, name, site}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param name the name
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_P_LikeN_S_First(
		long companyId, long parentGroupId, String name, boolean site,
		OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_P_LikeN_S.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, parentGroupId, name, site},
			orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and parentGroupId = &#63; and name LIKE &#63; and site = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param name the name
	 * @param site the site
	 */
	@Override
	public void removeByC_P_LikeN_S(
		long companyId, long parentGroupId, String name, boolean site) {

		_collectionPersistenceFinderByC_P_LikeN_S.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, parentGroupId, name, site});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and parentGroupId = &#63; and name LIKE &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param name the name
	 * @param site the site
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_P_LikeN_S(
		long companyId, long parentGroupId, String name, boolean site) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_P_LikeN_S.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, parentGroupId, name, site});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_P_S_I;
	private FinderPath _finderPathWithoutPaginationFindByC_P_S_I;
	private FinderPath _finderPathCountByC_P_S_I;
	private CollectionPersistenceFinder<Group>
		_collectionPersistenceFinderByC_P_S_I;

	/**
	 * Returns all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param inheritContent the inherit content
	 * @return the matching groups
	 */
	@Override
	public List<Group> findByC_P_S_I(
		long companyId, long parentGroupId, boolean site,
		boolean inheritContent) {

		return findByC_P_S_I(
			companyId, parentGroupId, site, inheritContent, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param inheritContent the inherit content
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 */
	@Override
	public List<Group> findByC_P_S_I(
		long companyId, long parentGroupId, boolean site,
		boolean inheritContent, int start, int end) {

		return findByC_P_S_I(
			companyId, parentGroupId, site, inheritContent, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param inheritContent the inherit content
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_P_S_I(
		long companyId, long parentGroupId, boolean site,
		boolean inheritContent, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return findByC_P_S_I(
			companyId, parentGroupId, site, inheritContent, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param inheritContent the inherit content
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching groups
	 */
	@Override
	public List<Group> findByC_P_S_I(
		long companyId, long parentGroupId, boolean site,
		boolean inheritContent, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_P_S_I.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, parentGroupId, site, inheritContent},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param inheritContent the inherit content
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByC_P_S_I_First(
			long companyId, long parentGroupId, boolean site,
			boolean inheritContent, OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException {

		Group group = fetchByC_P_S_I_First(
			companyId, parentGroupId, site, inheritContent, orderByComparator);

		if (group != null) {
			return group;
		}

		throw new NoSuchGroupException(
			_collectionPersistenceFinderByC_P_S_I.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, parentGroupId, site, inheritContent}));
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param inheritContent the inherit content
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByC_P_S_I_First(
		long companyId, long parentGroupId, boolean site,
		boolean inheritContent, OrderByComparator<Group> orderByComparator) {

		return _collectionPersistenceFinderByC_P_S_I.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, parentGroupId, site, inheritContent},
			orderByComparator);
	}

	/**
	 * Removes all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param inheritContent the inherit content
	 */
	@Override
	public void removeByC_P_S_I(
		long companyId, long parentGroupId, boolean site,
		boolean inheritContent) {

		_collectionPersistenceFinderByC_P_S_I.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, parentGroupId, site, inheritContent});
	}

	/**
	 * Returns the number of groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param inheritContent the inherit content
	 * @return the number of matching groups
	 */
	@Override
	public int countByC_P_S_I(
		long companyId, long parentGroupId, boolean site,
		boolean inheritContent) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _collectionPersistenceFinderByC_P_S_I.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, parentGroupId, site, inheritContent});
		}
	}

	private FinderPath _finderPathFetchByERC_C;
	private UniquePersistenceFinder<Group> _uniquePersistenceFinderByERC_C;

	/**
	 * Returns the group where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching group
	 * @throws NoSuchGroupException if a matching group could not be found
	 */
	@Override
	public Group findByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchGroupException {

		Group group = fetchByERC_C(externalReferenceCode, companyId);

		if (group == null) {
			String message =
				_uniquePersistenceFinderByERC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchGroupException(message);
		}

		return group;
	}

	/**
	 * Returns the group where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByERC_C(String externalReferenceCode, long companyId) {
		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the group where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 */
	@Override
	public Group fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					Group.class)) {

			return _uniquePersistenceFinderByERC_C.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {externalReferenceCode, companyId},
				useFinderCache);
		}
	}

	/**
	 * Removes the group where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the group that was removed
	 */
	@Override
	public Group removeByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchGroupException {

		Group group = findByERC_C(externalReferenceCode, companyId);

		return remove(group);
	}

	/**
	 * Returns the number of groups where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching groups
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {externalReferenceCode, companyId});
	}

	public GroupPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Group.class);

		setModelImplClass(GroupImpl.class);
		setModelPKClass(long.class);

		setTable(GroupTable.INSTANCE);
	}

	/**
	 * Creates a new group with the primary key. Does not add the group to the database.
	 *
	 * @param groupId the primary key for the new group
	 * @return the new group
	 */
	@Override
	public Group create(long groupId) {
		Group group = new GroupImpl();

		group.setNew(true);
		group.setPrimaryKey(groupId);

		String uuid = PortalUUIDUtil.generate();

		group.setUuid(uuid);

		group.setCompanyId(CompanyThreadLocal.getCompanyId());

		return group;
	}

	/**
	 * Removes the group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param groupId the primary key of the group
	 * @return the group that was removed
	 * @throws NoSuchGroupException if a group with the primary key could not be found
	 */
	@Override
	public Group remove(long groupId) throws NoSuchGroupException {
		return remove((Serializable)groupId);
	}

	@Override
	protected Group removeImpl(Group group) {
		groupToOrganizationTableMapper.deleteLeftPrimaryKeyTableMappings(
			group.getPrimaryKey());

		groupToRoleTableMapper.deleteLeftPrimaryKeyTableMappings(
			group.getPrimaryKey());

		groupToUserGroupTableMapper.deleteLeftPrimaryKeyTableMappings(
			group.getPrimaryKey());

		groupToUserTableMapper.deleteLeftPrimaryKeyTableMappings(
			group.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(group)) {
				group = (Group)session.get(
					GroupImpl.class, group.getPrimaryKeyObj());
			}

			if ((group != null) && CTPersistenceHelperUtil.isRemove(group)) {
				session.delete(group);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (group != null) {
			clearCache(group);
		}

		return group;
	}

	@Override
	public Group updateImpl(Group group) {
		boolean isNew = group.isNew();

		if (!(group instanceof GroupModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(group.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(group);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in group proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Group implementation " +
					group.getClass());
		}

		GroupModelImpl groupModelImpl = (GroupModelImpl)group;

		if (Validator.isNull(group.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			group.setUuid(uuid);
		}

		if (Validator.isNull(group.getExternalReferenceCode())) {
			group.setExternalReferenceCode(group.getUuid());
		}
		else {
			if (!Objects.equals(
					groupModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					group.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = group.getCompanyId();

					long groupId = group.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = group.getPrimaryKey();
					}

					try {
						group.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								Group.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								group.getExternalReferenceCode(), null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			Group ercGroup = fetchByERC_C(
				group.getExternalReferenceCode(), group.getCompanyId());

			if (isNew) {
				if (ercGroup != null) {
					throw new DuplicateGroupExternalReferenceCodeException(
						"Duplicate group with external reference code " +
							group.getExternalReferenceCode() + " and company " +
								group.getCompanyId());
				}
			}
			else {
				if ((ercGroup != null) &&
					(group.getGroupId() != ercGroup.getGroupId())) {

					throw new DuplicateGroupExternalReferenceCodeException(
						"Duplicate group with external reference code " +
							group.getExternalReferenceCode() + " and company " +
								group.getCompanyId());
				}
			}
		}

		if (!groupModelImpl.hasSetModifiedDate()) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				group.setModifiedDate(date);
			}
			else {
				group.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(group)) {
				if (!isNew) {
					session.evict(GroupImpl.class, group.getPrimaryKeyObj());
				}

				session.save(group);
			}
			else {
				group = (Group)session.merge(group);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(group, false);

		if (isNew) {
			group.setNew(false);
		}

		group.resetOriginalValues();

		return group;
	}

	/**
	 * Returns the group with the primary key or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param groupId the primary key of the group
	 * @return the group
	 * @throws NoSuchGroupException if a group with the primary key could not be found
	 */
	@Override
	public Group findByPrimaryKey(long groupId) throws NoSuchGroupException {
		return findByPrimaryKey((Serializable)groupId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return CTPersistenceHelperUtil.getCTPersistenceHelper();
	}

	/**
	 * Returns the group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param groupId the primary key of the group
	 * @return the group, or <code>null</code> if a group with the primary key could not be found
	 */
	@Override
	public Group fetchByPrimaryKey(long groupId) {
		return fetchByPrimaryKey((Serializable)groupId);
	}

	/**
	 * Returns the primaryKeys of organizations associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return long[] of the primaryKeys of organizations associated with the group
	 */
	@Override
	public long[] getOrganizationPrimaryKeys(long pk) {
		long[] pks = groupToOrganizationTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the organizations associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the organizations associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long pk) {

		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the organizations associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of organizations associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long pk, int start, int end) {

		return getOrganizations(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of organizations associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Organization>
			orderByComparator) {

		return groupToOrganizationTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of organizations associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of organizations associated with the group
	 */
	@Override
	public int getOrganizationsSize(long pk) {
		long[] pks = groupToOrganizationTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the organization is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPK the primary key of the organization
	 * @return <code>true</code> if the organization is associated with the group; <code>false</code> otherwise
	 */
	@Override
	public boolean containsOrganization(long pk, long organizationPK) {
		return groupToOrganizationTableMapper.containsTableMapping(
			pk, organizationPK);
	}

	/**
	 * Returns <code>true</code> if the group has any organizations associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with organizations
	 * @return <code>true</code> if the group has any organizations associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsOrganizations(long pk) {
		if (getOrganizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPK the primary key of the organization
	 * @return <code>true</code> if an association between the group and the organization was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addOrganization(long pk, long organizationPK) {
		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			return groupToOrganizationTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, organizationPK);
		}
		else {
			return groupToOrganizationTableMapper.addTableMapping(
				group.getCompanyId(), pk, organizationPK);
		}
	}

	/**
	 * Adds an association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organization the organization
	 * @return <code>true</code> if an association between the group and the organization was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addOrganization(
		long pk, com.liferay.portal.kernel.model.Organization organization) {

		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			return groupToOrganizationTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				organization.getPrimaryKey());
		}
		else {
			return groupToOrganizationTableMapper.addTableMapping(
				group.getCompanyId(), pk, organization.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPKs the primary keys of the organizations
	 * @return <code>true</code> if at least one association between the group and the organizations was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addOrganizations(long pk, long[] organizationPKs) {
		long companyId = 0;

		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = group.getCompanyId();
		}

		long[] addedKeys = groupToOrganizationTableMapper.addTableMappings(
			companyId, pk, organizationPKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizations the organizations
	 * @return <code>true</code> if at least one association between the group and the organizations was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addOrganizations(
		long pk,
		List<com.liferay.portal.kernel.model.Organization> organizations) {

		return addOrganizations(
			pk,
			ListUtil.toLongArray(
				organizations,
				com.liferay.portal.kernel.model.Organization.
					ORGANIZATION_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the group and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated organizations from
	 */
	@Override
	public void clearOrganizations(long pk) {
		groupToOrganizationTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPK the primary key of the organization
	 */
	@Override
	public void removeOrganization(long pk, long organizationPK) {
		groupToOrganizationTableMapper.deleteTableMapping(pk, organizationPK);
	}

	/**
	 * Removes the association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organization the organization
	 */
	@Override
	public void removeOrganization(
		long pk, com.liferay.portal.kernel.model.Organization organization) {

		groupToOrganizationTableMapper.deleteTableMapping(
			pk, organization.getPrimaryKey());
	}

	/**
	 * Removes the association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPKs the primary keys of the organizations
	 */
	@Override
	public void removeOrganizations(long pk, long[] organizationPKs) {
		groupToOrganizationTableMapper.deleteTableMappings(pk, organizationPKs);
	}

	/**
	 * Removes the association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizations the organizations
	 */
	@Override
	public void removeOrganizations(
		long pk,
		List<com.liferay.portal.kernel.model.Organization> organizations) {

		removeOrganizations(
			pk,
			ListUtil.toLongArray(
				organizations,
				com.liferay.portal.kernel.model.Organization.
					ORGANIZATION_ID_ACCESSOR));
	}

	/**
	 * Sets the organizations associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPKs the primary keys of the organizations to be associated with the group
	 */
	@Override
	public void setOrganizations(long pk, long[] organizationPKs) {
		Set<Long> newOrganizationPKsSet = SetUtil.fromArray(organizationPKs);
		Set<Long> oldOrganizationPKsSet = SetUtil.fromArray(
			groupToOrganizationTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeOrganizationPKsSet = new HashSet<Long>(
			oldOrganizationPKsSet);

		removeOrganizationPKsSet.removeAll(newOrganizationPKsSet);

		groupToOrganizationTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeOrganizationPKsSet));

		newOrganizationPKsSet.removeAll(oldOrganizationPKsSet);

		long companyId = 0;

		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = group.getCompanyId();
		}

		groupToOrganizationTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newOrganizationPKsSet));
	}

	/**
	 * Sets the organizations associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizations the organizations to be associated with the group
	 */
	@Override
	public void setOrganizations(
		long pk,
		List<com.liferay.portal.kernel.model.Organization> organizations) {

		try {
			long[] organizationPKs = new long[organizations.size()];

			for (int i = 0; i < organizations.size(); i++) {
				com.liferay.portal.kernel.model.Organization organization =
					organizations.get(i);

				organizationPKs[i] = organization.getPrimaryKey();
			}

			setOrganizations(pk, organizationPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of roles associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return long[] of the primaryKeys of roles associated with the group
	 */
	@Override
	public long[] getRolePrimaryKeys(long pk) {
		long[] pks = groupToRoleTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the roles associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the roles associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Role> getRoles(long pk) {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the roles associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of roles associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Role> getRoles(
		long pk, int start, int end) {

		return getRoles(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of roles associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Role> getRoles(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Role>
			orderByComparator) {

		return groupToRoleTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of roles associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of roles associated with the group
	 */
	@Override
	public int getRolesSize(long pk) {
		long[] pks = groupToRoleTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the role is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param rolePK the primary key of the role
	 * @return <code>true</code> if the role is associated with the group; <code>false</code> otherwise
	 */
	@Override
	public boolean containsRole(long pk, long rolePK) {
		return groupToRoleTableMapper.containsTableMapping(pk, rolePK);
	}

	/**
	 * Returns <code>true</code> if the group has any roles associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with roles
	 * @return <code>true</code> if the group has any roles associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsRoles(long pk) {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePK the primary key of the role
	 * @return <code>true</code> if an association between the group and the role was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addRole(long pk, long rolePK) {
		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			return groupToRoleTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, rolePK);
		}
		else {
			return groupToRoleTableMapper.addTableMapping(
				group.getCompanyId(), pk, rolePK);
		}
	}

	/**
	 * Adds an association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param role the role
	 * @return <code>true</code> if an association between the group and the role was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addRole(long pk, com.liferay.portal.kernel.model.Role role) {
		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			return groupToRoleTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, role.getPrimaryKey());
		}
		else {
			return groupToRoleTableMapper.addTableMapping(
				group.getCompanyId(), pk, role.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePKs the primary keys of the roles
	 * @return <code>true</code> if at least one association between the group and the roles was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addRoles(long pk, long[] rolePKs) {
		long companyId = 0;

		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = group.getCompanyId();
		}

		long[] addedKeys = groupToRoleTableMapper.addTableMappings(
			companyId, pk, rolePKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param roles the roles
	 * @return <code>true</code> if at least one association between the group and the roles was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addRoles(
		long pk, List<com.liferay.portal.kernel.model.Role> roles) {

		return addRoles(
			pk,
			ListUtil.toLongArray(
				roles, com.liferay.portal.kernel.model.Role.ROLE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the group and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated roles from
	 */
	@Override
	public void clearRoles(long pk) {
		groupToRoleTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePK the primary key of the role
	 */
	@Override
	public void removeRole(long pk, long rolePK) {
		groupToRoleTableMapper.deleteTableMapping(pk, rolePK);
	}

	/**
	 * Removes the association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param role the role
	 */
	@Override
	public void removeRole(long pk, com.liferay.portal.kernel.model.Role role) {
		groupToRoleTableMapper.deleteTableMapping(pk, role.getPrimaryKey());
	}

	/**
	 * Removes the association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePKs the primary keys of the roles
	 */
	@Override
	public void removeRoles(long pk, long[] rolePKs) {
		groupToRoleTableMapper.deleteTableMappings(pk, rolePKs);
	}

	/**
	 * Removes the association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param roles the roles
	 */
	@Override
	public void removeRoles(
		long pk, List<com.liferay.portal.kernel.model.Role> roles) {

		removeRoles(
			pk,
			ListUtil.toLongArray(
				roles, com.liferay.portal.kernel.model.Role.ROLE_ID_ACCESSOR));
	}

	/**
	 * Sets the roles associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePKs the primary keys of the roles to be associated with the group
	 */
	@Override
	public void setRoles(long pk, long[] rolePKs) {
		Set<Long> newRolePKsSet = SetUtil.fromArray(rolePKs);
		Set<Long> oldRolePKsSet = SetUtil.fromArray(
			groupToRoleTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeRolePKsSet = new HashSet<Long>(oldRolePKsSet);

		removeRolePKsSet.removeAll(newRolePKsSet);

		groupToRoleTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeRolePKsSet));

		newRolePKsSet.removeAll(oldRolePKsSet);

		long companyId = 0;

		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = group.getCompanyId();
		}

		groupToRoleTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newRolePKsSet));
	}

	/**
	 * Sets the roles associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param roles the roles to be associated with the group
	 */
	@Override
	public void setRoles(
		long pk, List<com.liferay.portal.kernel.model.Role> roles) {

		try {
			long[] rolePKs = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.kernel.model.Role role = roles.get(i);

				rolePKs[i] = role.getPrimaryKey();
			}

			setRoles(pk, rolePKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of user groups associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return long[] of the primaryKeys of user groups associated with the group
	 */
	@Override
	public long[] getUserGroupPrimaryKeys(long pk) {
		long[] pks = groupToUserGroupTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the user groups associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the user groups associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk) {

		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the user groups associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of user groups associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk, int start, int end) {

		return getUserGroups(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user groups associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user groups associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.UserGroup>
			orderByComparator) {

		return groupToUserGroupTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of user groups associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of user groups associated with the group
	 */
	@Override
	public int getUserGroupsSize(long pk) {
		long[] pks = groupToUserGroupTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the user group is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPK the primary key of the user group
	 * @return <code>true</code> if the user group is associated with the group; <code>false</code> otherwise
	 */
	@Override
	public boolean containsUserGroup(long pk, long userGroupPK) {
		return groupToUserGroupTableMapper.containsTableMapping(
			pk, userGroupPK);
	}

	/**
	 * Returns <code>true</code> if the group has any user groups associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with user groups
	 * @return <code>true</code> if the group has any user groups associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsUserGroups(long pk) {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPK the primary key of the user group
	 * @return <code>true</code> if an association between the group and the user group was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addUserGroup(long pk, long userGroupPK) {
		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			return groupToUserGroupTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, userGroupPK);
		}
		else {
			return groupToUserGroupTableMapper.addTableMapping(
				group.getCompanyId(), pk, userGroupPK);
		}
	}

	/**
	 * Adds an association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroup the user group
	 * @return <code>true</code> if an association between the group and the user group was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addUserGroup(
		long pk, com.liferay.portal.kernel.model.UserGroup userGroup) {

		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			return groupToUserGroupTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				userGroup.getPrimaryKey());
		}
		else {
			return groupToUserGroupTableMapper.addTableMapping(
				group.getCompanyId(), pk, userGroup.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPKs the primary keys of the user groups
	 * @return <code>true</code> if at least one association between the group and the user groups was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addUserGroups(long pk, long[] userGroupPKs) {
		long companyId = 0;

		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = group.getCompanyId();
		}

		long[] addedKeys = groupToUserGroupTableMapper.addTableMappings(
			companyId, pk, userGroupPKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroups the user groups
	 * @return <code>true</code> if at least one association between the group and the user groups was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addUserGroups(
		long pk, List<com.liferay.portal.kernel.model.UserGroup> userGroups) {

		return addUserGroups(
			pk,
			ListUtil.toLongArray(
				userGroups,
				com.liferay.portal.kernel.model.UserGroup.
					USER_GROUP_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the group and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated user groups from
	 */
	@Override
	public void clearUserGroups(long pk) {
		groupToUserGroupTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPK the primary key of the user group
	 */
	@Override
	public void removeUserGroup(long pk, long userGroupPK) {
		groupToUserGroupTableMapper.deleteTableMapping(pk, userGroupPK);
	}

	/**
	 * Removes the association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroup the user group
	 */
	@Override
	public void removeUserGroup(
		long pk, com.liferay.portal.kernel.model.UserGroup userGroup) {

		groupToUserGroupTableMapper.deleteTableMapping(
			pk, userGroup.getPrimaryKey());
	}

	/**
	 * Removes the association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPKs the primary keys of the user groups
	 */
	@Override
	public void removeUserGroups(long pk, long[] userGroupPKs) {
		groupToUserGroupTableMapper.deleteTableMappings(pk, userGroupPKs);
	}

	/**
	 * Removes the association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroups the user groups
	 */
	@Override
	public void removeUserGroups(
		long pk, List<com.liferay.portal.kernel.model.UserGroup> userGroups) {

		removeUserGroups(
			pk,
			ListUtil.toLongArray(
				userGroups,
				com.liferay.portal.kernel.model.UserGroup.
					USER_GROUP_ID_ACCESSOR));
	}

	/**
	 * Sets the user groups associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPKs the primary keys of the user groups to be associated with the group
	 */
	@Override
	public void setUserGroups(long pk, long[] userGroupPKs) {
		Set<Long> newUserGroupPKsSet = SetUtil.fromArray(userGroupPKs);
		Set<Long> oldUserGroupPKsSet = SetUtil.fromArray(
			groupToUserGroupTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeUserGroupPKsSet = new HashSet<Long>(oldUserGroupPKsSet);

		removeUserGroupPKsSet.removeAll(newUserGroupPKsSet);

		groupToUserGroupTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeUserGroupPKsSet));

		newUserGroupPKsSet.removeAll(oldUserGroupPKsSet);

		long companyId = 0;

		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = group.getCompanyId();
		}

		groupToUserGroupTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newUserGroupPKsSet));
	}

	/**
	 * Sets the user groups associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroups the user groups to be associated with the group
	 */
	@Override
	public void setUserGroups(
		long pk, List<com.liferay.portal.kernel.model.UserGroup> userGroups) {

		try {
			long[] userGroupPKs = new long[userGroups.size()];

			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.kernel.model.UserGroup userGroup =
					userGroups.get(i);

				userGroupPKs[i] = userGroup.getPrimaryKey();
			}

			setUserGroups(pk, userGroupPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of users associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return long[] of the primaryKeys of users associated with the group
	 */
	@Override
	public long[] getUserPrimaryKeys(long pk) {
		long[] pks = groupToUserTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the users associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the users associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.User> getUsers(long pk) {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the users associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of users associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.User> getUsers(
		long pk, int start, int end) {

		return getUsers(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users associated with the group
	 */
	@Override
	public List<com.liferay.portal.kernel.model.User> getUsers(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.User>
			orderByComparator) {

		return groupToUserTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of users associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of users associated with the group
	 */
	@Override
	public int getUsersSize(long pk) {
		long[] pks = groupToUserTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the user is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param userPK the primary key of the user
	 * @return <code>true</code> if the user is associated with the group; <code>false</code> otherwise
	 */
	@Override
	public boolean containsUser(long pk, long userPK) {
		return groupToUserTableMapper.containsTableMapping(pk, userPK);
	}

	/**
	 * Returns <code>true</code> if the group has any users associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with users
	 * @return <code>true</code> if the group has any users associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsUsers(long pk) {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPK the primary key of the user
	 * @return <code>true</code> if an association between the group and the user was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addUser(long pk, long userPK) {
		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			return groupToUserTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, userPK);
		}
		else {
			return groupToUserTableMapper.addTableMapping(
				group.getCompanyId(), pk, userPK);
		}
	}

	/**
	 * Adds an association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param user the user
	 * @return <code>true</code> if an association between the group and the user was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addUser(long pk, com.liferay.portal.kernel.model.User user) {
		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			return groupToUserTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, user.getPrimaryKey());
		}
		else {
			return groupToUserTableMapper.addTableMapping(
				group.getCompanyId(), pk, user.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPKs the primary keys of the users
	 * @return <code>true</code> if at least one association between the group and the users was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addUsers(long pk, long[] userPKs) {
		long companyId = 0;

		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = group.getCompanyId();
		}

		long[] addedKeys = groupToUserTableMapper.addTableMappings(
			companyId, pk, userPKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param users the users
	 * @return <code>true</code> if at least one association between the group and the users was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addUsers(
		long pk, List<com.liferay.portal.kernel.model.User> users) {

		return addUsers(
			pk,
			ListUtil.toLongArray(
				users, com.liferay.portal.kernel.model.User.USER_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the group and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated users from
	 */
	@Override
	public void clearUsers(long pk) {
		groupToUserTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPK the primary key of the user
	 */
	@Override
	public void removeUser(long pk, long userPK) {
		groupToUserTableMapper.deleteTableMapping(pk, userPK);
	}

	/**
	 * Removes the association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param user the user
	 */
	@Override
	public void removeUser(long pk, com.liferay.portal.kernel.model.User user) {
		groupToUserTableMapper.deleteTableMapping(pk, user.getPrimaryKey());
	}

	/**
	 * Removes the association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPKs the primary keys of the users
	 */
	@Override
	public void removeUsers(long pk, long[] userPKs) {
		groupToUserTableMapper.deleteTableMappings(pk, userPKs);
	}

	/**
	 * Removes the association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param users the users
	 */
	@Override
	public void removeUsers(
		long pk, List<com.liferay.portal.kernel.model.User> users) {

		removeUsers(
			pk,
			ListUtil.toLongArray(
				users, com.liferay.portal.kernel.model.User.USER_ID_ACCESSOR));
	}

	/**
	 * Sets the users associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPKs the primary keys of the users to be associated with the group
	 */
	@Override
	public void setUsers(long pk, long[] userPKs) {
		Set<Long> newUserPKsSet = SetUtil.fromArray(userPKs);
		Set<Long> oldUserPKsSet = SetUtil.fromArray(
			groupToUserTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeUserPKsSet = new HashSet<Long>(oldUserPKsSet);

		removeUserPKsSet.removeAll(newUserPKsSet);

		groupToUserTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeUserPKsSet));

		newUserPKsSet.removeAll(oldUserPKsSet);

		long companyId = 0;

		Group group = fetchByPrimaryKey(pk);

		if (group == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = group.getCompanyId();
		}

		groupToUserTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newUserPKsSet));
	}

	/**
	 * Sets the users associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param users the users to be associated with the group
	 */
	@Override
	public void setUsers(
		long pk, List<com.liferay.portal.kernel.model.User> users) {

		try {
			long[] userPKs = new long[users.size()];

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.kernel.model.User user = users.get(i);

				userPKs[i] = user.getPrimaryKey();
			}

			setUsers(pk, userPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
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
		return "groupId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_GROUP_;
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
		return GroupModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "Group_";
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
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("creatorUserId");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctMergeColumnNames.add("parentGroupId");
		ctMergeColumnNames.add("liveGroupId");
		ctMergeColumnNames.add("treePath");
		ctMergeColumnNames.add("groupKey");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("description");
		ctMergeColumnNames.add("type_");
		ctMergeColumnNames.add("typeSettings");
		ctMergeColumnNames.add("manualMembership");
		ctMergeColumnNames.add("membershipRestriction");
		ctMergeColumnNames.add("friendlyURL");
		ctMergeColumnNames.add("site");
		ctMergeColumnNames.add("remoteStagingGroupCount");
		ctMergeColumnNames.add("inheritContent");
		ctMergeColumnNames.add("active_");
		ctMergeColumnNames.add("orgs");
		ctMergeColumnNames.add("roles");
		ctMergeColumnNames.add("userGroups");
		ctMergeColumnNames.add("users");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("groupId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_mappingTableNames.add("Groups_Orgs");
		_mappingTableNames.add("Groups_Roles");
		_mappingTableNames.add("Groups_UserGroups");
		_mappingTableNames.add("Users_Groups");

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"companyId", "groupKey"});

		_uniqueIndexColumnNames.add(new String[] {"companyId", "friendlyURL"});

		_uniqueIndexColumnNames.add(
			new String[] {"companyId", "classNameId", "classPK"});

		_uniqueIndexColumnNames.add(
			new String[] {"companyId", "liveGroupId", "groupKey"});

		_uniqueIndexColumnNames.add(
			new String[] {
				"companyId", "classNameId", "liveGroupId", "groupKey"
			});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "companyId"});
	}

	/**
	 * Initializes the group persistence.
	 */
	public void afterPropertiesSet() {
		groupToOrganizationTableMapper = TableMapperFactory.getTableMapper(
			"Groups_Orgs", "companyId", "groupId", "organizationId", this,
			organizationPersistence);

		groupToRoleTableMapper = TableMapperFactory.getTableMapper(
			"Groups_Roles", "companyId", "groupId", "roleId", this,
			rolePersistence);

		groupToUserGroupTableMapper = TableMapperFactory.getTableMapper(
			"Groups_UserGroups", "companyId", "groupId", "userGroupId", this,
			userGroupPersistence);

		groupToUserTableMapper = TableMapperFactory.getTableMapper(
			"Users_Groups", "companyId", "groupId", "userId", this,
			userPersistence);

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
			_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
			GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"group_.", "uuid", FinderColumn.Type.STRING, "=", true, true,
				Group::getUuid));

		_finderPathFetchByUUID_G = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false,
			convertNullFunction(Group::getUuid), Group::getGroupId);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_GROUP__WHERE, "",
			new FinderColumn<>(
				"group_.", "uuid", FinderColumn.Type.STRING, "=", true, true,
				Group::getUuid),
			new FinderColumn<>(
				"group_.", "groupId", FinderColumn.Type.LONG, "=", true, true,
				Group::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_GROUP__WHERE,
				_SQL_COUNT_GROUP__WHERE, GroupModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"group_.", "uuid", FinderColumn.Type.STRING, "=", true,
					true, Group::getUuid),
				new FinderColumn<>(
					"group_.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Group::getCompanyId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_GROUP__WHERE,
				_SQL_COUNT_GROUP__WHERE, GroupModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"group_.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Group::getCompanyId));

		_finderPathWithPaginationFindByLiveGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLiveGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"liveGroupId"}, true);

		_finderPathWithoutPaginationFindByLiveGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLiveGroupId",
			new String[] {Long.class.getName()}, new String[] {"liveGroupId"},
			true);

		_finderPathCountByLiveGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLiveGroupId",
			new String[] {Long.class.getName()}, new String[] {"liveGroupId"},
			false);

		_collectionPersistenceFinderByLiveGroupId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByLiveGroupId,
				_finderPathWithoutPaginationFindByLiveGroupId,
				_finderPathCountByLiveGroupId, _SQL_SELECT_GROUP__WHERE,
				_SQL_COUNT_GROUP__WHERE, GroupModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"group_.", "liveGroupId", FinderColumn.Type.LONG, "=", true,
					true, Group::getLiveGroupId));

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "classNameId"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "classNameId"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "classNameId"}, false);

		_collectionPersistenceFinderByC_C = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_C,
			_finderPathWithoutPaginationFindByC_C, _finderPathCountByC_C,
			_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
			GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "classNameId", FinderColumn.Type.LONG, "=", true,
				true, Group::getClassNameId));

		_finderPathWithPaginationFindByC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "parentGroupId"}, true);

		_finderPathWithoutPaginationFindByC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "parentGroupId"}, true);

		_finderPathCountByC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "parentGroupId"}, false);

		_collectionPersistenceFinderByC_P = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_P,
			_finderPathWithoutPaginationFindByC_P, _finderPathCountByC_P,
			_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
			GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "parentGroupId", FinderColumn.Type.LONG, "=", true,
				true, Group::getParentGroupId));

		_finderPathWithPaginationFindByC_GK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_GK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "groupKey"}, true);

		_finderPathWithoutPaginationFindByC_GK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_GK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "groupKey"}, true);

		_finderPathFetchByC_GK = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_GK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "groupKey"}, false, Group::getCompanyId,
			convertNullFunction(Group::getGroupKey));

		_finderPathCountByC_GK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_GK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "groupKey"}, false);

		_finderPathWithPaginationCountByC_GK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_GK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "groupKey"}, false);

		_finderPathFetchByC_F = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_F",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "friendlyURL"}, false,
			Group::getCompanyId, convertNullFunction(Group::getFriendlyURL));

		_uniquePersistenceFinderByC_F = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_F, _SQL_SELECT_GROUP__WHERE, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "friendlyURL", FinderColumn.Type.STRING, "=", true,
				true, Group::getFriendlyURL));

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "site"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "site"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "site"}, false);

		_collectionPersistenceFinderByC_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_S,
			_finderPathWithoutPaginationFindByC_S, _finderPathCountByC_S,
			_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
			GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "site", FinderColumn.Type.BOOLEAN, "=", true, true,
				Group::isSite));

		_finderPathWithPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_"}, true);

		_finderPathWithoutPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "active_"}, true);

		_finderPathCountByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "active_"}, false);

		_collectionPersistenceFinderByC_A = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_A,
			_finderPathWithoutPaginationFindByC_A, _finderPathCountByC_A,
			_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
			GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "active", FinderColumn.Type.BOOLEAN, "=", true, true,
				Group::isActive));

		_finderPathWithPaginationFindByC_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_collectionPersistenceFinderByC_CPK = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_CPK,
			_finderPathWithoutPaginationFindByC_CPK, _finderPathCountByC_CPK,
			_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
			GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"group_.", "classNameId", FinderColumn.Type.LONG, "=", true,
				true, Group::getClassNameId),
			new FinderColumn<>(
				"group_.", "classPK", FinderColumn.Type.LONG, "=", true, true,
				Group::getClassPK));

		_finderPathWithPaginationFindByT_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_A",
			new String[] {
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"type_", "active_"}, true);

		_finderPathWithoutPaginationFindByT_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_A",
			new String[] {Integer.class.getName(), Boolean.class.getName()},
			new String[] {"type_", "active_"}, true);

		_finderPathCountByT_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_A",
			new String[] {Integer.class.getName(), Boolean.class.getName()},
			new String[] {"type_", "active_"}, false);

		_collectionPersistenceFinderByT_A = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByT_A,
			_finderPathWithoutPaginationFindByT_A, _finderPathCountByT_A,
			_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
			GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"group_.", "type", FinderColumn.Type.INTEGER, "=", true, true,
				Group::getType),
			new FinderColumn<>(
				"group_.", "active", FinderColumn.Type.BOOLEAN, "=", true, true,
				Group::isActive));

		_finderPathWithPaginationFindByGtG_C_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGtG_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId", "parentGroupId"}, true);

		_finderPathWithPaginationCountByGtG_C_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGtG_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "companyId", "parentGroupId"}, false);

		_collectionPersistenceFinderByGtG_C_P =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByGtG_C_P, null,
				_finderPathWithPaginationCountByGtG_C_P,
				_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
				GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"group_.", "groupId", FinderColumn.Type.LONG, ">", true,
					true, Group::getGroupId),
				new FinderColumn<>(
					"group_.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Group::getCompanyId),
				new FinderColumn<>(
					"group_.", "parentGroupId", FinderColumn.Type.LONG, "=",
					true, true, Group::getParentGroupId));

		_finderPathFetchByC_C_C = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"companyId", "classNameId", "classPK"}, false,
			Group::getCompanyId, Group::getClassNameId, Group::getClassPK);

		_uniquePersistenceFinderByC_C_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_C_C, _SQL_SELECT_GROUP__WHERE, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "classNameId", FinderColumn.Type.LONG, "=", true,
				true, Group::getClassNameId),
			new FinderColumn<>(
				"group_.", "classPK", FinderColumn.Type.LONG, "=", true, true,
				Group::getClassPK));

		_finderPathWithPaginationFindByC_C_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "classNameId", "parentGroupId"}, true);

		_finderPathWithoutPaginationFindByC_C_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"companyId", "classNameId", "parentGroupId"}, true);

		_finderPathCountByC_C_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"companyId", "classNameId", "parentGroupId"}, false);

		_collectionPersistenceFinderByC_C_P = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_C_P,
			_finderPathWithoutPaginationFindByC_C_P, _finderPathCountByC_C_P,
			_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
			GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "classNameId", FinderColumn.Type.LONG, "=", true,
				true, Group::getClassNameId),
			new FinderColumn<>(
				"group_.", "parentGroupId", FinderColumn.Type.LONG, "=", true,
				true, Group::getParentGroupId));

		_finderPathWithPaginationFindByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "classNameId", "site"}, true);

		_finderPathWithoutPaginationFindByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "classNameId", "site"}, true);

		_finderPathCountByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "classNameId", "site"}, false);

		_collectionPersistenceFinderByC_C_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_C_S,
			_finderPathWithoutPaginationFindByC_C_S, _finderPathCountByC_C_S,
			_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
			GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "classNameId", FinderColumn.Type.LONG, "=", true,
				true, Group::getClassNameId),
			new FinderColumn<>(
				"group_.", "site", FinderColumn.Type.BOOLEAN, "=", true, true,
				Group::isSite));

		_finderPathWithPaginationFindByC_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "parentGroupId", "site"}, true);

		_finderPathWithoutPaginationFindByC_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "parentGroupId", "site"}, true);

		_finderPathCountByC_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "parentGroupId", "site"}, false);

		_collectionPersistenceFinderByC_P_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_P_S,
			_finderPathWithoutPaginationFindByC_P_S, _finderPathCountByC_P_S,
			_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
			GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "parentGroupId", FinderColumn.Type.LONG, "=", true,
				true, Group::getParentGroupId),
			new FinderColumn<>(
				"group_.", "site", FinderColumn.Type.BOOLEAN, "=", true, true,
				Group::isSite));

		_finderPathFetchByC_L_GK = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_L_GK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "liveGroupId", "groupKey"}, false,
			Group::getCompanyId, Group::getLiveGroupId,
			convertNullFunction(Group::getGroupKey));

		_uniquePersistenceFinderByC_L_GK = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_L_GK, _SQL_SELECT_GROUP__WHERE, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "liveGroupId", FinderColumn.Type.LONG, "=", true,
				true, Group::getLiveGroupId),
			new FinderColumn<>(
				"group_.", "groupKey", FinderColumn.Type.STRING, "=", true,
				true, Group::getGroupKey));

		_finderPathWithPaginationFindByC_LikeT_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LikeT_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "treePath", "site"}, true);

		_finderPathWithPaginationCountByC_LikeT_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LikeT_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "treePath", "site"}, false);

		_collectionPersistenceFinderByC_LikeT_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_LikeT_S, null,
				_finderPathWithPaginationCountByC_LikeT_S,
				_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
				GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"group_.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Group::getCompanyId),
				new FinderColumn<>(
					"group_.", "treePath", FinderColumn.Type.STRING, "LIKE",
					true, true, Group::getTreePath),
				new FinderColumn<>(
					"group_.", "site", FinderColumn.Type.BOOLEAN, "=", true,
					true, Group::isSite));

		_finderPathWithPaginationFindByC_LikeN_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LikeN_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "name", "site"}, true);

		_finderPathWithPaginationCountByC_LikeN_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LikeN_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "name", "site"}, false);

		_collectionPersistenceFinderByC_LikeN_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_LikeN_S, null,
				_finderPathWithPaginationCountByC_LikeN_S,
				_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
				GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"group_.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Group::getCompanyId),
				new FinderColumn<>(
					"group_.", "name", FinderColumn.Type.STRING, "LIKE", false,
					true, Group::getName),
				new FinderColumn<>(
					"group_.", "site", FinderColumn.Type.BOOLEAN, "=", true,
					true, Group::isSite));

		_finderPathWithPaginationFindByC_S_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "site", "active_"}, true);

		_finderPathWithoutPaginationFindByC_S_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "site", "active_"}, true);

		_finderPathCountByC_S_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "site", "active_"}, false);

		_collectionPersistenceFinderByC_S_A = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_S_A,
			_finderPathWithoutPaginationFindByC_S_A, _finderPathCountByC_S_A,
			_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
			GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "site", FinderColumn.Type.BOOLEAN, "=", true, true,
				Group::isSite),
			new FinderColumn<>(
				"group_.", "active", FinderColumn.Type.BOOLEAN, "=", true, true,
				Group::isActive));

		_finderPathWithPaginationFindByGtG_C_C_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGtG_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "companyId", "classNameId", "parentGroupId"
			},
			true);

		_finderPathWithPaginationCountByGtG_C_C_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGtG_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"groupId", "companyId", "classNameId", "parentGroupId"
			},
			false);

		_collectionPersistenceFinderByGtG_C_C_P =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByGtG_C_C_P, null,
				_finderPathWithPaginationCountByGtG_C_C_P,
				_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
				GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"group_.", "groupId", FinderColumn.Type.LONG, ">", true,
					true, Group::getGroupId),
				new FinderColumn<>(
					"group_.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Group::getCompanyId),
				new FinderColumn<>(
					"group_.", "classNameId", FinderColumn.Type.LONG, "=", true,
					true, Group::getClassNameId),
				new FinderColumn<>(
					"group_.", "parentGroupId", FinderColumn.Type.LONG, "=",
					true, true, Group::getParentGroupId));

		_finderPathWithPaginationFindByGtG_C_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGtG_C_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId", "parentGroupId", "site"},
			true);

		_finderPathWithPaginationCountByGtG_C_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGtG_C_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "companyId", "parentGroupId", "site"},
			false);

		_collectionPersistenceFinderByGtG_C_P_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByGtG_C_P_S, null,
				_finderPathWithPaginationCountByGtG_C_P_S,
				_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
				GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"group_.", "groupId", FinderColumn.Type.LONG, ">", true,
					true, Group::getGroupId),
				new FinderColumn<>(
					"group_.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Group::getCompanyId),
				new FinderColumn<>(
					"group_.", "parentGroupId", FinderColumn.Type.LONG, "=",
					true, true, Group::getParentGroupId),
				new FinderColumn<>(
					"group_.", "site", FinderColumn.Type.BOOLEAN, "=", true,
					true, Group::isSite));

		_finderPathFetchByC_C_L_GK = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_L_GK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {
				"companyId", "classNameId", "liveGroupId", "groupKey"
			},
			false, Group::getCompanyId, Group::getClassNameId,
			Group::getLiveGroupId, convertNullFunction(Group::getGroupKey));

		_uniquePersistenceFinderByC_C_L_GK = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_C_L_GK, _SQL_SELECT_GROUP__WHERE, "",
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId),
			new FinderColumn<>(
				"group_.", "classNameId", FinderColumn.Type.LONG, "=", true,
				true, Group::getClassNameId),
			new FinderColumn<>(
				"group_.", "liveGroupId", FinderColumn.Type.LONG, "=", true,
				true, Group::getLiveGroupId),
			new FinderColumn<>(
				"group_.", "groupKey", FinderColumn.Type.STRING, "=", true,
				true, Group::getGroupKey));

		_finderPathWithPaginationFindByC_P_LikeN_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "parentGroupId", "name", "site"}, true);

		_finderPathWithPaginationCountByC_P_LikeN_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_P_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			new String[] {"companyId", "parentGroupId", "name", "site"}, false);

		_collectionPersistenceFinderByC_P_LikeN_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_P_LikeN_S, null,
				_finderPathWithPaginationCountByC_P_LikeN_S,
				_SQL_SELECT_GROUP__WHERE, _SQL_COUNT_GROUP__WHERE,
				GroupModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"group_.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Group::getCompanyId),
				new FinderColumn<>(
					"group_.", "parentGroupId", FinderColumn.Type.LONG, "=",
					true, true, Group::getParentGroupId),
				new FinderColumn<>(
					"group_.", "name", FinderColumn.Type.STRING, "LIKE", false,
					true, Group::getName),
				new FinderColumn<>(
					"group_.", "site", FinderColumn.Type.BOOLEAN, "=", true,
					true, Group::isSite));

		_finderPathWithPaginationFindByC_P_S_I = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P_S_I",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "parentGroupId", "site", "inheritContent"
			},
			true);

		_finderPathWithoutPaginationFindByC_P_S_I = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P_S_I",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"companyId", "parentGroupId", "site", "inheritContent"
			},
			true);

		_finderPathCountByC_P_S_I = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P_S_I",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"companyId", "parentGroupId", "site", "inheritContent"
			},
			false);

		_collectionPersistenceFinderByC_P_S_I =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_P_S_I,
				_finderPathWithoutPaginationFindByC_P_S_I,
				_finderPathCountByC_P_S_I, _SQL_SELECT_GROUP__WHERE,
				_SQL_COUNT_GROUP__WHERE, GroupModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"group_.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Group::getCompanyId),
				new FinderColumn<>(
					"group_.", "parentGroupId", FinderColumn.Type.LONG, "=",
					true, true, Group::getParentGroupId),
				new FinderColumn<>(
					"group_.", "site", FinderColumn.Type.BOOLEAN, "=", true,
					true, Group::isSite),
				new FinderColumn<>(
					"group_.", "inheritContent", FinderColumn.Type.BOOLEAN, "=",
					true, true, Group::isInheritContent));

		_finderPathFetchByERC_C = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, false,
			convertNullFunction(Group::getExternalReferenceCode),
			Group::getCompanyId);

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_C, _SQL_SELECT_GROUP__WHERE, "",
			new FinderColumn<>(
				"group_.", "externalReferenceCode", FinderColumn.Type.STRING,
				"=", true, true, Group::getExternalReferenceCode),
			new FinderColumn<>(
				"group_.", "companyId", FinderColumn.Type.LONG, "=", true, true,
				Group::getCompanyId));

		GroupUtil.setPersistence(this);
	}

	public void destroy() {
		GroupUtil.setPersistence(null);

		EntityCacheUtil.removeCache(GroupImpl.class.getName());

		TableMapperFactory.removeTableMapper("Groups_Orgs");
		TableMapperFactory.removeTableMapper("Groups_Roles");
		TableMapperFactory.removeTableMapper("Groups_UserGroups");
		TableMapperFactory.removeTableMapper("Users_Groups");
	}

	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;

	protected TableMapper<Group, com.liferay.portal.kernel.model.Organization>
		groupToOrganizationTableMapper;

	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;

	protected TableMapper<Group, com.liferay.portal.kernel.model.Role>
		groupToRoleTableMapper;

	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;

	protected TableMapper<Group, com.liferay.portal.kernel.model.UserGroup>
		groupToUserGroupTableMapper;

	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

	protected TableMapper<Group, com.liferay.portal.kernel.model.User>
		groupToUserTableMapper;

	private static final String _ENTITY_ALIAS_PREFIX =
		GroupModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_GROUP_ =
		"SELECT group_ FROM Group group_";

	private static final String _SQL_SELECT_GROUP__WHERE =
		"SELECT group_ FROM Group group_ WHERE ";

	private static final String _SQL_COUNT_GROUP__WHERE =
		"SELECT COUNT(group_) FROM Group group_ WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Group exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		GroupPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type", "active"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1779225640