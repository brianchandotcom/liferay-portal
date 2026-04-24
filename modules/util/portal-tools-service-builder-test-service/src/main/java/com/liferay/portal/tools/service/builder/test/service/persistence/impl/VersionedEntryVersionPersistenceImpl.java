/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryVersionException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryVersion;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryVersionTable;
import com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryVersionImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryVersionModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryVersionPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryVersionUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the versioned entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class VersionedEntryVersionPersistenceImpl
	extends BasePersistenceImpl<VersionedEntryVersion>
	implements VersionedEntryVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>VersionedEntryVersionUtil</code> to access the versioned entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		VersionedEntryVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByVersionedEntryId;
	private FinderPath _finderPathWithoutPaginationFindByVersionedEntryId;
	private FinderPath _finderPathCountByVersionedEntryId;
	private CollectionPersistenceFinder<VersionedEntryVersion>
		_collectionPersistenceFinderByVersionedEntryId;

	/**
	 * Returns all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @return the matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId) {

		return findByVersionedEntryId(
			versionedEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end) {

		return findByVersionedEntryId(versionedEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return findByVersionedEntryId(
			versionedEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByVersionedEntryId.find(
			finderCache, new Object[] {versionedEntryId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	@Override
	public VersionedEntryVersion findByVersionedEntryId_First(
			long versionedEntryId,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException {

		VersionedEntryVersion versionedEntryVersion =
			fetchByVersionedEntryId_First(versionedEntryId, orderByComparator);

		if (versionedEntryVersion != null) {
			return versionedEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("versionedEntryId=");
		sb.append(versionedEntryId);

		sb.append("}");

		throw new NoSuchVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	@Override
	public VersionedEntryVersion fetchByVersionedEntryId_First(
		long versionedEntryId,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		List<VersionedEntryVersion> list = findByVersionedEntryId(
			versionedEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the versioned entry versions where versionedEntryId = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 */
	@Override
	public void removeByVersionedEntryId(long versionedEntryId) {
		for (VersionedEntryVersion versionedEntryVersion :
				findByVersionedEntryId(
					versionedEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(versionedEntryVersion);
		}
	}

	/**
	 * Returns the number of versioned entry versions where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @return the number of matching versioned entry versions
	 */
	@Override
	public int countByVersionedEntryId(long versionedEntryId) {
		return _collectionPersistenceFinderByVersionedEntryId.count(
			finderCache, new Object[] {versionedEntryId});
	}

	private FinderPath _finderPathFetchByVersionedEntryId_Version;
	private UniquePersistenceFinder<VersionedEntryVersion>
		_uniquePersistenceFinderByVersionedEntryId_Version;

	/**
	 * Returns the versioned entry version where versionedEntryId = &#63; and version = &#63; or throws a <code>NoSuchVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	@Override
	public VersionedEntryVersion findByVersionedEntryId_Version(
			long versionedEntryId, int version)
		throws NoSuchVersionedEntryVersionException {

		VersionedEntryVersion versionedEntryVersion =
			fetchByVersionedEntryId_Version(versionedEntryId, version);

		if (versionedEntryVersion == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("versionedEntryId=");
			sb.append(versionedEntryId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchVersionedEntryVersionException(sb.toString());
		}

		return versionedEntryVersion;
	}

	/**
	 * Returns the versioned entry version where versionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	@Override
	public VersionedEntryVersion fetchByVersionedEntryId_Version(
		long versionedEntryId, int version) {

		return fetchByVersionedEntryId_Version(versionedEntryId, version, true);
	}

	/**
	 * Returns the versioned entry version where versionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	@Override
	public VersionedEntryVersion fetchByVersionedEntryId_Version(
		long versionedEntryId, int version, boolean useFinderCache) {

		return _uniquePersistenceFinderByVersionedEntryId_Version.fetch(
			finderCache, new Object[] {versionedEntryId, version},
			useFinderCache);
	}

	/**
	 * Removes the versioned entry version where versionedEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the versioned entry version that was removed
	 */
	@Override
	public VersionedEntryVersion removeByVersionedEntryId_Version(
			long versionedEntryId, int version)
		throws NoSuchVersionedEntryVersionException {

		VersionedEntryVersion versionedEntryVersion =
			findByVersionedEntryId_Version(versionedEntryId, version);

		return remove(versionedEntryVersion);
	}

	/**
	 * Returns the number of versioned entry versions where versionedEntryId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the number of matching versioned entry versions
	 */
	@Override
	public int countByVersionedEntryId_Version(
		long versionedEntryId, int version) {

		return _uniquePersistenceFinderByVersionedEntryId_Version.count(
			finderCache, new Object[] {versionedEntryId, version});
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<VersionedEntryVersion>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the versioned entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the versioned entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByGroupId.find(
			finderCache, new Object[] {groupId}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	@Override
	public VersionedEntryVersion findByGroupId_First(
			long groupId,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException {

		VersionedEntryVersion versionedEntryVersion = fetchByGroupId_First(
			groupId, orderByComparator);

		if (versionedEntryVersion != null) {
			return versionedEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	@Override
	public VersionedEntryVersion fetchByGroupId_First(
		long groupId,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		List<VersionedEntryVersion> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the versioned entry versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (VersionedEntryVersion versionedEntryVersion :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(versionedEntryVersion);
		}
	}

	/**
	 * Returns the number of versioned entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching versioned entry versions
	 */
	@Override
	public int countByGroupId(long groupId) {
		return _collectionPersistenceFinderByGroupId.count(
			finderCache, new Object[] {groupId});
	}

	private FinderPath _finderPathWithPaginationFindByGroupId_Version;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Version;
	private FinderPath _finderPathCountByGroupId_Version;
	private CollectionPersistenceFinder<VersionedEntryVersion>
		_collectionPersistenceFinderByGroupId_Version;

	/**
	 * Returns all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version) {

		return findByGroupId_Version(
			groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end) {

		return findByGroupId_Version(groupId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return findByGroupId_Version(
			groupId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByGroupId_Version.find(
			finderCache, new Object[] {groupId, version}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	@Override
	public VersionedEntryVersion findByGroupId_Version_First(
			long groupId, int version,
			OrderByComparator<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException {

		VersionedEntryVersion versionedEntryVersion =
			fetchByGroupId_Version_First(groupId, version, orderByComparator);

		if (versionedEntryVersion != null) {
			return versionedEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchVersionedEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	@Override
	public VersionedEntryVersion fetchByGroupId_Version_First(
		long groupId, int version,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		List<VersionedEntryVersion> list = findByGroupId_Version(
			groupId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the versioned entry versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	@Override
	public void removeByGroupId_Version(long groupId, int version) {
		for (VersionedEntryVersion versionedEntryVersion :
				findByGroupId_Version(
					groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(versionedEntryVersion);
		}
	}

	/**
	 * Returns the number of versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching versioned entry versions
	 */
	@Override
	public int countByGroupId_Version(long groupId, int version) {
		return _collectionPersistenceFinderByGroupId_Version.count(
			finderCache, new Object[] {groupId, version});
	}

	public VersionedEntryVersionPersistenceImpl() {
		setModelClass(VersionedEntryVersion.class);

		setModelImplClass(VersionedEntryVersionImpl.class);
		setModelPKClass(long.class);

		setTable(VersionedEntryVersionTable.INSTANCE);
	}

	/**
	 * Caches the versioned entry version in the entity cache if it is enabled.
	 *
	 * @param versionedEntryVersion the versioned entry version
	 */
	@Override
	public void cacheResult(VersionedEntryVersion versionedEntryVersion) {
		entityCache.putResult(
			VersionedEntryVersionImpl.class,
			versionedEntryVersion.getPrimaryKey(), versionedEntryVersion);

		finderCache.putResult(
			_finderPathFetchByVersionedEntryId_Version,
			new Object[] {
				versionedEntryVersion.getVersionedEntryId(),
				versionedEntryVersion.getVersion()
			},
			versionedEntryVersion);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the versioned entry versions in the entity cache if it is enabled.
	 *
	 * @param versionedEntryVersions the versioned entry versions
	 */
	@Override
	public void cacheResult(
		List<VersionedEntryVersion> versionedEntryVersions) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (versionedEntryVersions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (VersionedEntryVersion versionedEntryVersion :
				versionedEntryVersions) {

			if (entityCache.getResult(
					VersionedEntryVersionImpl.class,
					versionedEntryVersion.getPrimaryKey()) == null) {

				cacheResult(versionedEntryVersion);
			}
		}
	}

	/**
	 * Clears the cache for all versioned entry versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(VersionedEntryVersionImpl.class);

		finderCache.clearCache(VersionedEntryVersionImpl.class);
	}

	/**
	 * Clears the cache for the versioned entry version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(VersionedEntryVersion versionedEntryVersion) {
		entityCache.removeResult(
			VersionedEntryVersionImpl.class, versionedEntryVersion);
	}

	@Override
	public void clearCache(List<VersionedEntryVersion> versionedEntryVersions) {
		for (VersionedEntryVersion versionedEntryVersion :
				versionedEntryVersions) {

			entityCache.removeResult(
				VersionedEntryVersionImpl.class, versionedEntryVersion);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(VersionedEntryVersionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				VersionedEntryVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		VersionedEntryVersionModelImpl versionedEntryVersionModelImpl) {

		Object[] args = new Object[] {
			versionedEntryVersionModelImpl.getVersionedEntryId(),
			versionedEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathFetchByVersionedEntryId_Version, args,
			versionedEntryVersionModelImpl);
	}

	/**
	 * Creates a new versioned entry version with the primary key. Does not add the versioned entry version to the database.
	 *
	 * @param versionedEntryVersionId the primary key for the new versioned entry version
	 * @return the new versioned entry version
	 */
	@Override
	public VersionedEntryVersion create(long versionedEntryVersionId) {
		VersionedEntryVersion versionedEntryVersion =
			new VersionedEntryVersionImpl();

		versionedEntryVersion.setNew(true);
		versionedEntryVersion.setPrimaryKey(versionedEntryVersionId);

		return versionedEntryVersion;
	}

	/**
	 * Removes the versioned entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntryVersionId the primary key of the versioned entry version
	 * @return the versioned entry version that was removed
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	@Override
	public VersionedEntryVersion remove(long versionedEntryVersionId)
		throws NoSuchVersionedEntryVersionException {

		return remove((Serializable)versionedEntryVersionId);
	}

	/**
	 * Removes the versioned entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the versioned entry version
	 * @return the versioned entry version that was removed
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	@Override
	public VersionedEntryVersion remove(Serializable primaryKey)
		throws NoSuchVersionedEntryVersionException {

		Session session = null;

		try {
			session = openSession();

			VersionedEntryVersion versionedEntryVersion =
				(VersionedEntryVersion)session.get(
					VersionedEntryVersionImpl.class, primaryKey);

			if (versionedEntryVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVersionedEntryVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(versionedEntryVersion);
		}
		catch (NoSuchVersionedEntryVersionException noSuchEntityException) {
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
	protected VersionedEntryVersion removeImpl(
		VersionedEntryVersion versionedEntryVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(versionedEntryVersion)) {
				versionedEntryVersion = (VersionedEntryVersion)session.get(
					VersionedEntryVersionImpl.class,
					versionedEntryVersion.getPrimaryKeyObj());
			}

			if (versionedEntryVersion != null) {
				session.delete(versionedEntryVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (versionedEntryVersion != null) {
			clearCache(versionedEntryVersion);
		}

		return versionedEntryVersion;
	}

	@Override
	public VersionedEntryVersion updateImpl(
		VersionedEntryVersion versionedEntryVersion) {

		boolean isNew = versionedEntryVersion.isNew();

		if (!(versionedEntryVersion instanceof
				VersionedEntryVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(versionedEntryVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					versionedEntryVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in versionedEntryVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom VersionedEntryVersion implementation " +
					versionedEntryVersion.getClass());
		}

		VersionedEntryVersionModelImpl versionedEntryVersionModelImpl =
			(VersionedEntryVersionModelImpl)versionedEntryVersion;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(versionedEntryVersion);
			}
			else {
				throw new IllegalArgumentException(
					"VersionedEntryVersion is read only, create a new version instead");
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			VersionedEntryVersionImpl.class, versionedEntryVersionModelImpl,
			false, true);

		cacheUniqueFindersCache(versionedEntryVersionModelImpl);

		if (isNew) {
			versionedEntryVersion.setNew(false);
		}

		versionedEntryVersion.resetOriginalValues();

		return versionedEntryVersion;
	}

	/**
	 * Returns the versioned entry version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the versioned entry version
	 * @return the versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	@Override
	public VersionedEntryVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVersionedEntryVersionException {

		VersionedEntryVersion versionedEntryVersion = fetchByPrimaryKey(
			primaryKey);

		if (versionedEntryVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVersionedEntryVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return versionedEntryVersion;
	}

	/**
	 * Returns the versioned entry version with the primary key or throws a <code>NoSuchVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param versionedEntryVersionId the primary key of the versioned entry version
	 * @return the versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	@Override
	public VersionedEntryVersion findByPrimaryKey(long versionedEntryVersionId)
		throws NoSuchVersionedEntryVersionException {

		return findByPrimaryKey((Serializable)versionedEntryVersionId);
	}

	/**
	 * Returns the versioned entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param versionedEntryVersionId the primary key of the versioned entry version
	 * @return the versioned entry version, or <code>null</code> if a versioned entry version with the primary key could not be found
	 */
	@Override
	public VersionedEntryVersion fetchByPrimaryKey(
		long versionedEntryVersionId) {

		return fetchByPrimaryKey((Serializable)versionedEntryVersionId);
	}

	/**
	 * Returns all the versioned entry versions.
	 *
	 * @return the versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findAll(
		int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of versioned entry versions
	 */
	@Override
	public List<VersionedEntryVersion> findAll(
		int start, int end,
		OrderByComparator<VersionedEntryVersion> orderByComparator,
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

		List<VersionedEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<VersionedEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_VERSIONEDENTRYVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_VERSIONEDENTRYVERSION;

				sql = sql.concat(VersionedEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<VersionedEntryVersion>)QueryUtil.list(
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
	 * Removes all the versioned entry versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (VersionedEntryVersion versionedEntryVersion : findAll()) {
			remove(versionedEntryVersion);
		}
	}

	/**
	 * Returns the number of versioned entry versions.
	 *
	 * @return the number of versioned entry versions
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
					_SQL_COUNT_VERSIONEDENTRYVERSION);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "versionedEntryVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_VERSIONEDENTRYVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return VersionedEntryVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the versioned entry version persistence.
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

		_finderPathWithPaginationFindByVersionedEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByVersionedEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"versionedEntryId"}, true);

		_finderPathWithoutPaginationFindByVersionedEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByVersionedEntryId",
			new String[] {Long.class.getName()},
			new String[] {"versionedEntryId"}, true);

		_finderPathCountByVersionedEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByVersionedEntryId", new String[] {Long.class.getName()},
			new String[] {"versionedEntryId"}, false);

		_collectionPersistenceFinderByVersionedEntryId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByVersionedEntryId,
				_finderPathWithoutPaginationFindByVersionedEntryId,
				_finderPathCountByVersionedEntryId,
				_SQL_SELECT_VERSIONEDENTRYVERSION_WHERE,
				_SQL_COUNT_VERSIONEDENTRYVERSION_WHERE,
				VersionedEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"versionedEntryVersion.", "versionedEntryId",
					FinderColumn.Type.LONG, "=", true, true,
					VersionedEntryVersion::getVersionedEntryId));

		_finderPathFetchByVersionedEntryId_Version = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByVersionedEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"versionedEntryId", "version"}, true);

		_uniquePersistenceFinderByVersionedEntryId_Version =
			new UniquePersistenceFinder<>(
				this, _finderPathFetchByVersionedEntryId_Version,
				_SQL_SELECT_VERSIONEDENTRYVERSION_WHERE,
				new FinderColumn<>(
					"versionedEntryVersion.", "versionedEntryId",
					FinderColumn.Type.LONG, "=", true, false,
					VersionedEntryVersion::getVersionedEntryId),
				new FinderColumn<>(
					"versionedEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					VersionedEntryVersion::getVersion));

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
				_SQL_SELECT_VERSIONEDENTRYVERSION_WHERE,
				_SQL_COUNT_VERSIONEDENTRYVERSION_WHERE,
				VersionedEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"versionedEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, true, VersionedEntryVersion::getGroupId));

		_finderPathWithPaginationFindByGroupId_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "version"}, true);

		_finderPathWithoutPaginationFindByGroupId_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "version"}, true);

		_finderPathCountByGroupId_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "version"}, false);

		_collectionPersistenceFinderByGroupId_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByGroupId_Version,
				_finderPathWithoutPaginationFindByGroupId_Version,
				_finderPathCountByGroupId_Version,
				_SQL_SELECT_VERSIONEDENTRYVERSION_WHERE,
				_SQL_COUNT_VERSIONEDENTRYVERSION_WHERE,
				VersionedEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"versionedEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, VersionedEntryVersion::getGroupId),
				new FinderColumn<>(
					"versionedEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					VersionedEntryVersion::getVersion));

		VersionedEntryVersionUtil.setPersistence(this);
	}

	public void destroy() {
		VersionedEntryVersionUtil.setPersistence(null);

		entityCache.removeCache(VersionedEntryVersionImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_VERSIONEDENTRYVERSION =
		"SELECT versionedEntryVersion FROM VersionedEntryVersion versionedEntryVersion";

	private static final String _SQL_SELECT_VERSIONEDENTRYVERSION_WHERE =
		"SELECT versionedEntryVersion FROM VersionedEntryVersion versionedEntryVersion WHERE ";

	private static final String _SQL_COUNT_VERSIONEDENTRYVERSION =
		"SELECT COUNT(versionedEntryVersion) FROM VersionedEntryVersion versionedEntryVersion";

	private static final String _SQL_COUNT_VERSIONEDENTRYVERSION_WHERE =
		"SELECT COUNT(versionedEntryVersion) FROM VersionedEntryVersion versionedEntryVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"versionedEntryVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No VersionedEntryVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No VersionedEntryVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		VersionedEntryVersionPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-881642729