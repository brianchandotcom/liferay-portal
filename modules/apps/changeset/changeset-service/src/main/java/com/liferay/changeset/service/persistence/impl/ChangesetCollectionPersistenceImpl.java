/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.changeset.service.persistence.impl;

import com.liferay.changeset.exception.NoSuchCollectionException;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetCollectionTable;
import com.liferay.changeset.model.impl.ChangesetCollectionImpl;
import com.liferay.changeset.model.impl.ChangesetCollectionModelImpl;
import com.liferay.changeset.service.persistence.ChangesetCollectionPersistence;
import com.liferay.changeset.service.persistence.ChangesetCollectionUtil;
import com.liferay.changeset.service.persistence.impl.constants.ChangesetPersistenceConstants;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the changeset collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ChangesetCollectionPersistence.class)
public class ChangesetCollectionPersistenceImpl
	extends BasePersistenceImpl<ChangesetCollection>
	implements ChangesetCollectionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ChangesetCollectionUtil</code> to access the changeset collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ChangesetCollectionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<ChangesetCollection>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the changeset collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByGroupId.find(
			finderCache, new Object[] {groupId}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByGroupId_First(
			long groupId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {

		ChangesetCollection changesetCollection = fetchByGroupId_First(
			groupId, orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		throw new NoSuchCollectionException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByGroupId_First(
		long groupId,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Removes all the changeset collections where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of changeset collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching changeset collections
	 */
	@Override
	public int countByGroupId(long groupId) {
		return _collectionPersistenceFinderByGroupId.count(
			finderCache, new Object[] {groupId});
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<ChangesetCollection>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the changeset collections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByCompanyId_First(
			long companyId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {

		ChangesetCollection changesetCollection = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		throw new NoSuchCollectionException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByCompanyId_First(
		long companyId,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the changeset collections where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of changeset collections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching changeset collections
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	private FinderPath _finderPathWithPaginationFindByG_U;
	private FinderPath _finderPathWithoutPaginationFindByG_U;
	private FinderPath _finderPathCountByG_U;
	private CollectionPersistenceFinder<ChangesetCollection>
		_collectionPersistenceFinderByG_U;

	/**
	 * Returns all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByG_U(long groupId, long userId) {
		return findByG_U(
			groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByG_U(
		long groupId, long userId, int start, int end) {

		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return findByG_U(groupId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_U.find(
			finderCache, new Object[] {groupId, userId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByG_U_First(
			long groupId, long userId,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {

		ChangesetCollection changesetCollection = fetchByG_U_First(
			groupId, userId, orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		throw new NoSuchCollectionException(
			_collectionPersistenceFinderByG_U.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, userId}));
	}

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByG_U_First(
		long groupId, long userId,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return _collectionPersistenceFinderByG_U.fetchFirst(
			finderCache, new Object[] {groupId, userId}, orderByComparator);
	}

	/**
	 * Removes all the changeset collections where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByG_U(long groupId, long userId) {
		_collectionPersistenceFinderByG_U.remove(
			finderCache, new Object[] {groupId, userId});
	}

	/**
	 * Returns the number of changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching changeset collections
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		return _collectionPersistenceFinderByG_U.count(
			finderCache, new Object[] {groupId, userId});
	}

	private FinderPath _finderPathFetchByG_N;
	private UniquePersistenceFinder<ChangesetCollection>
		_uniquePersistenceFinderByG_N;

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or throws a <code>NoSuchCollectionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByG_N(long groupId, String name)
		throws NoSuchCollectionException {

		ChangesetCollection changesetCollection = fetchByG_N(groupId, name);

		if (changesetCollection == null) {
			String message =
				_uniquePersistenceFinderByG_N.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, name});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCollectionException(message);
		}

		return changesetCollection;
	}

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByG_N(
		long groupId, String name, boolean useFinderCache) {

		return _uniquePersistenceFinderByG_N.fetch(
			finderCache, new Object[] {groupId, name}, useFinderCache);
	}

	/**
	 * Removes the changeset collection where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the changeset collection that was removed
	 */
	@Override
	public ChangesetCollection removeByG_N(long groupId, String name)
		throws NoSuchCollectionException {

		ChangesetCollection changesetCollection = findByG_N(groupId, name);

		return remove(changesetCollection);
	}

	/**
	 * Returns the number of changeset collections where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching changeset collections
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		return _uniquePersistenceFinderByG_N.count(
			finderCache, new Object[] {groupId, name});
	}

	private FinderPath _finderPathWithPaginationFindByC_N;
	private FinderPath _finderPathWithoutPaginationFindByC_N;
	private FinderPath _finderPathCountByC_N;
	private CollectionPersistenceFinder<ChangesetCollection>
		_collectionPersistenceFinderByC_N;

	/**
	 * Returns all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByC_N(long companyId, String name) {
		return findByC_N(
			companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByC_N(
		long companyId, String name, int start, int end) {

		return findByC_N(companyId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByC_N(
		long companyId, String name, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return findByC_N(companyId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	@Override
	public List<ChangesetCollection> findByC_N(
		long companyId, String name, int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_N.find(
			finderCache, new Object[] {companyId, name}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection findByC_N_First(
			long companyId, String name,
			OrderByComparator<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException {

		ChangesetCollection changesetCollection = fetchByC_N_First(
			companyId, name, orderByComparator);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		throw new NoSuchCollectionException(
			_collectionPersistenceFinderByC_N.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, name}));
	}

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	@Override
	public ChangesetCollection fetchByC_N_First(
		long companyId, String name,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return _collectionPersistenceFinderByC_N.fetchFirst(
			finderCache, new Object[] {companyId, name}, orderByComparator);
	}

	/**
	 * Removes all the changeset collections where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	@Override
	public void removeByC_N(long companyId, String name) {
		_collectionPersistenceFinderByC_N.remove(
			finderCache, new Object[] {companyId, name});
	}

	/**
	 * Returns the number of changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching changeset collections
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		return _collectionPersistenceFinderByC_N.count(
			finderCache, new Object[] {companyId, name});
	}

	public ChangesetCollectionPersistenceImpl() {
		setModelClass(ChangesetCollection.class);

		setModelImplClass(ChangesetCollectionImpl.class);
		setModelPKClass(long.class);

		setTable(ChangesetCollectionTable.INSTANCE);
	}

	/**
	 * Caches the changeset collection in the entity cache if it is enabled.
	 *
	 * @param changesetCollection the changeset collection
	 */
	@Override
	public void cacheResult(ChangesetCollection changesetCollection) {
		entityCache.putResult(
			ChangesetCollectionImpl.class, changesetCollection.getPrimaryKey(),
			changesetCollection);

		finderCache.putResult(
			_finderPathFetchByG_N,
			new Object[] {
				changesetCollection.getGroupId(), changesetCollection.getName()
			},
			changesetCollection);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the changeset collections in the entity cache if it is enabled.
	 *
	 * @param changesetCollections the changeset collections
	 */
	@Override
	public void cacheResult(List<ChangesetCollection> changesetCollections) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (changesetCollections.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ChangesetCollection changesetCollection : changesetCollections) {
			if (entityCache.getResult(
					ChangesetCollectionImpl.class,
					changesetCollection.getPrimaryKey()) == null) {

				cacheResult(changesetCollection);
			}
		}
	}

	/**
	 * Clears the cache for all changeset collections.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ChangesetCollectionImpl.class);

		finderCache.clearCache(ChangesetCollectionImpl.class);
	}

	/**
	 * Clears the cache for the changeset collection.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ChangesetCollection changesetCollection) {
		entityCache.removeResult(
			ChangesetCollectionImpl.class, changesetCollection);
	}

	@Override
	public void clearCache(List<ChangesetCollection> changesetCollections) {
		for (ChangesetCollection changesetCollection : changesetCollections) {
			entityCache.removeResult(
				ChangesetCollectionImpl.class, changesetCollection);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ChangesetCollectionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ChangesetCollectionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ChangesetCollectionModelImpl changesetCollectionModelImpl) {

		Object[] args = new Object[] {
			changesetCollectionModelImpl.getGroupId(),
			changesetCollectionModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathFetchByG_N, args, changesetCollectionModelImpl);
	}

	/**
	 * Creates a new changeset collection with the primary key. Does not add the changeset collection to the database.
	 *
	 * @param changesetCollectionId the primary key for the new changeset collection
	 * @return the new changeset collection
	 */
	@Override
	public ChangesetCollection create(long changesetCollectionId) {
		ChangesetCollection changesetCollection = new ChangesetCollectionImpl();

		changesetCollection.setNew(true);
		changesetCollection.setPrimaryKey(changesetCollectionId);

		changesetCollection.setCompanyId(CompanyThreadLocal.getCompanyId());

		return changesetCollection;
	}

	/**
	 * Removes the changeset collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection that was removed
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection remove(long changesetCollectionId)
		throws NoSuchCollectionException {

		return remove((Serializable)changesetCollectionId);
	}

	/**
	 * Removes the changeset collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the changeset collection
	 * @return the changeset collection that was removed
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection remove(Serializable primaryKey)
		throws NoSuchCollectionException {

		Session session = null;

		try {
			session = openSession();

			ChangesetCollection changesetCollection =
				(ChangesetCollection)session.get(
					ChangesetCollectionImpl.class, primaryKey);

			if (changesetCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCollectionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(changesetCollection);
		}
		catch (NoSuchCollectionException noSuchEntityException) {
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
	protected ChangesetCollection removeImpl(
		ChangesetCollection changesetCollection) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(changesetCollection)) {
				changesetCollection = (ChangesetCollection)session.get(
					ChangesetCollectionImpl.class,
					changesetCollection.getPrimaryKeyObj());
			}

			if (changesetCollection != null) {
				session.delete(changesetCollection);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (changesetCollection != null) {
			clearCache(changesetCollection);
		}

		return changesetCollection;
	}

	@Override
	public ChangesetCollection updateImpl(
		ChangesetCollection changesetCollection) {

		boolean isNew = changesetCollection.isNew();

		if (!(changesetCollection instanceof ChangesetCollectionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(changesetCollection.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					changesetCollection);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in changesetCollection proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ChangesetCollection implementation " +
					changesetCollection.getClass());
		}

		ChangesetCollectionModelImpl changesetCollectionModelImpl =
			(ChangesetCollectionModelImpl)changesetCollection;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (changesetCollection.getCreateDate() == null)) {
			if (serviceContext == null) {
				changesetCollection.setCreateDate(date);
			}
			else {
				changesetCollection.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!changesetCollectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				changesetCollection.setModifiedDate(date);
			}
			else {
				changesetCollection.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(changesetCollection);
			}
			else {
				changesetCollection = (ChangesetCollection)session.merge(
					changesetCollection);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ChangesetCollectionImpl.class, changesetCollectionModelImpl, false,
			true);

		cacheUniqueFindersCache(changesetCollectionModelImpl);

		if (isNew) {
			changesetCollection.setNew(false);
		}

		changesetCollection.resetOriginalValues();

		return changesetCollection;
	}

	/**
	 * Returns the changeset collection with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the changeset collection
	 * @return the changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCollectionException {

		ChangesetCollection changesetCollection = fetchByPrimaryKey(primaryKey);

		if (changesetCollection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCollectionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return changesetCollection;
	}

	/**
	 * Returns the changeset collection with the primary key or throws a <code>NoSuchCollectionException</code> if it could not be found.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection findByPrimaryKey(long changesetCollectionId)
		throws NoSuchCollectionException {

		return findByPrimaryKey((Serializable)changesetCollectionId);
	}

	/**
	 * Returns the changeset collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection, or <code>null</code> if a changeset collection with the primary key could not be found
	 */
	@Override
	public ChangesetCollection fetchByPrimaryKey(long changesetCollectionId) {
		return fetchByPrimaryKey((Serializable)changesetCollectionId);
	}

	/**
	 * Returns all the changeset collections.
	 *
	 * @return the changeset collections
	 */
	@Override
	public List<ChangesetCollection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of changeset collections
	 */
	@Override
	public List<ChangesetCollection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of changeset collections
	 */
	@Override
	public List<ChangesetCollection> findAll(
		int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of changeset collections
	 */
	@Override
	public List<ChangesetCollection> findAll(
		int start, int end,
		OrderByComparator<ChangesetCollection> orderByComparator,
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

		List<ChangesetCollection> list = null;

		if (useFinderCache) {
			list = (List<ChangesetCollection>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CHANGESETCOLLECTION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CHANGESETCOLLECTION;

				sql = sql.concat(ChangesetCollectionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ChangesetCollection>)QueryUtil.list(
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
	 * Removes all the changeset collections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ChangesetCollection changesetCollection : findAll()) {
			remove(changesetCollection);
		}
	}

	/**
	 * Returns the number of changeset collections.
	 *
	 * @return the number of changeset collections
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
					_SQL_COUNT_CHANGESETCOLLECTION);

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
		return "changesetCollectionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CHANGESETCOLLECTION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ChangesetCollectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the changeset collection persistence.
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
				_SQL_SELECT_CHANGESETCOLLECTION_WHERE,
				_SQL_COUNT_CHANGESETCOLLECTION_WHERE,
				ChangesetCollectionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"changesetCollection.", "groupId", FinderColumn.Type.LONG,
					"=", true, true, ChangesetCollection::getGroupId));

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
				_finderPathCountByCompanyId,
				_SQL_SELECT_CHANGESETCOLLECTION_WHERE,
				_SQL_COUNT_CHANGESETCOLLECTION_WHERE,
				ChangesetCollectionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"changesetCollection.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, ChangesetCollection::getCompanyId));

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
			_SQL_SELECT_CHANGESETCOLLECTION_WHERE,
			_SQL_COUNT_CHANGESETCOLLECTION_WHERE,
			ChangesetCollectionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"changesetCollection.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, ChangesetCollection::getGroupId),
			new FinderColumn<>(
				"changesetCollection.", "userId", FinderColumn.Type.LONG, "=",
				true, true, ChangesetCollection::getUserId));

		_finderPathFetchByG_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, true);

		_uniquePersistenceFinderByG_N = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_N, _SQL_SELECT_CHANGESETCOLLECTION_WHERE,
			new FinderColumn<>(
				"changesetCollection.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, ChangesetCollection::getGroupId),
			new FinderColumn<>(
				"changesetCollection.", "name", FinderColumn.Type.STRING, "=",
				true, true, ChangesetCollection::getName));

		_finderPathWithPaginationFindByC_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "name"}, true);

		_finderPathWithoutPaginationFindByC_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, true);

		_finderPathCountByC_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, false);

		_collectionPersistenceFinderByC_N = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_N,
			_finderPathWithoutPaginationFindByC_N, _finderPathCountByC_N,
			_SQL_SELECT_CHANGESETCOLLECTION_WHERE,
			_SQL_COUNT_CHANGESETCOLLECTION_WHERE,
			ChangesetCollectionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"changesetCollection.", "companyId", FinderColumn.Type.LONG,
				"=", true, false, ChangesetCollection::getCompanyId),
			new FinderColumn<>(
				"changesetCollection.", "name", FinderColumn.Type.STRING, "=",
				true, true, ChangesetCollection::getName));

		ChangesetCollectionUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ChangesetCollectionUtil.setPersistence(null);

		entityCache.removeCache(ChangesetCollectionImpl.class.getName());
	}

	@Override
	@Reference(
		target = ChangesetPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ChangesetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ChangesetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CHANGESETCOLLECTION =
		"SELECT changesetCollection FROM ChangesetCollection changesetCollection";

	private static final String _SQL_SELECT_CHANGESETCOLLECTION_WHERE =
		"SELECT changesetCollection FROM ChangesetCollection changesetCollection WHERE ";

	private static final String _SQL_COUNT_CHANGESETCOLLECTION =
		"SELECT COUNT(changesetCollection) FROM ChangesetCollection changesetCollection";

	private static final String _SQL_COUNT_CHANGESETCOLLECTION_WHERE =
		"SELECT COUNT(changesetCollection) FROM ChangesetCollection changesetCollection WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "changesetCollection.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ChangesetCollection exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ChangesetCollection exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetCollectionPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1350835519