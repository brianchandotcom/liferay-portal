/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.exception.NoSuchFaroProjectException;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.model.FaroProjectTable;
import com.liferay.osb.faro.model.impl.FaroProjectImpl;
import com.liferay.osb.faro.model.impl.FaroProjectModelImpl;
import com.liferay.osb.faro.service.persistence.FaroProjectPersistence;
import com.liferay.osb.faro.service.persistence.FaroProjectUtil;
import com.liferay.osb.faro.service.persistence.impl.constants.OSBFaroPersistenceConstants;
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

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the faro project service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @generated
 */
@Component(service = FaroProjectPersistence.class)
public class FaroProjectPersistenceImpl
	extends BasePersistenceImpl<FaroProject, NoSuchFaroProjectException>
	implements FaroProjectPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FaroProjectUtil</code> to access the faro project persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FaroProjectImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByGroupId;
	private UniquePersistenceFinder<FaroProject>
		_uniquePersistenceFinderByGroupId;

	/**
	 * Returns the faro project where groupId = &#63; or throws a <code>NoSuchFaroProjectException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @return the matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByGroupId(long groupId)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByGroupId(groupId);

		if (faroProject == null) {
			String message =
				_uniquePersistenceFinderByGroupId.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFaroProjectException(message);
		}

		return faroProject;
	}

	/**
	 * Returns the faro project where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByGroupId(long groupId) {
		return fetchByGroupId(groupId, true);
	}

	/**
	 * Returns the faro project where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByGroupId(long groupId, boolean useFinderCache) {
		return _uniquePersistenceFinderByGroupId.fetch(
			finderCache, new Object[] {groupId}, useFinderCache);
	}

	/**
	 * Removes the faro project where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @return the faro project that was removed
	 */
	@Override
	public FaroProject removeByGroupId(long groupId)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = findByGroupId(groupId);

		return remove(faroProject);
	}

	/**
	 * Returns the number of faro projects where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching faro projects
	 */
	@Override
	public int countByGroupId(long groupId) {
		return _uniquePersistenceFinderByGroupId.count(
			finderCache, new Object[] {groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;
	private CollectionPersistenceFinder<FaroProject>
		_collectionPersistenceFinderByUserId;

	/**
	 * Returns all the faro projects where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching faro projects
	 */
	@Override
	public List<FaroProject> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro projects where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @return the range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro projects where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByUserId(
		long userId, int start, int end,
		OrderByComparator<FaroProject> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro projects where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByUserId(
		long userId, int start, int end,
		OrderByComparator<FaroProject> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUserId.find(
			finderCache, new Object[] {userId}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first faro project in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByUserId_First(
			long userId, OrderByComparator<FaroProject> orderByComparator)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByUserId_First(
			userId, orderByComparator);

		if (faroProject != null) {
			return faroProject;
		}

		throw new NoSuchFaroProjectException(
			_collectionPersistenceFinderByUserId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {userId}));
	}

	/**
	 * Returns the first faro project in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByUserId_First(
		long userId, OrderByComparator<FaroProject> orderByComparator) {

		return _collectionPersistenceFinderByUserId.fetchFirst(
			finderCache, new Object[] {userId}, orderByComparator);
	}

	/**
	 * Removes all the faro projects where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		_collectionPersistenceFinderByUserId.remove(
			finderCache, new Object[] {userId});
	}

	/**
	 * Returns the number of faro projects where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching faro projects
	 */
	@Override
	public int countByUserId(long userId) {
		return _collectionPersistenceFinderByUserId.count(
			finderCache, new Object[] {userId});
	}

	private FinderPath _finderPathFetchByCorpProjectUuid;
	private UniquePersistenceFinder<FaroProject>
		_uniquePersistenceFinderByCorpProjectUuid;

	/**
	 * Returns the faro project where corpProjectUuid = &#63; or throws a <code>NoSuchFaroProjectException</code> if it could not be found.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByCorpProjectUuid(String corpProjectUuid)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByCorpProjectUuid(corpProjectUuid);

		if (faroProject == null) {
			String message =
				_uniquePersistenceFinderByCorpProjectUuid.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {corpProjectUuid});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFaroProjectException(message);
		}

		return faroProject;
	}

	/**
	 * Returns the faro project where corpProjectUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByCorpProjectUuid(String corpProjectUuid) {
		return fetchByCorpProjectUuid(corpProjectUuid, true);
	}

	/**
	 * Returns the faro project where corpProjectUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByCorpProjectUuid(
		String corpProjectUuid, boolean useFinderCache) {

		return _uniquePersistenceFinderByCorpProjectUuid.fetch(
			finderCache, new Object[] {corpProjectUuid}, useFinderCache);
	}

	/**
	 * Removes the faro project where corpProjectUuid = &#63; from the database.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the faro project that was removed
	 */
	@Override
	public FaroProject removeByCorpProjectUuid(String corpProjectUuid)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = findByCorpProjectUuid(corpProjectUuid);

		return remove(faroProject);
	}

	/**
	 * Returns the number of faro projects where corpProjectUuid = &#63;.
	 *
	 * @param corpProjectUuid the corp project uuid
	 * @return the number of matching faro projects
	 */
	@Override
	public int countByCorpProjectUuid(String corpProjectUuid) {
		return _uniquePersistenceFinderByCorpProjectUuid.count(
			finderCache, new Object[] {corpProjectUuid});
	}

	private FinderPath _finderPathWithPaginationFindByServerLocation;
	private FinderPath _finderPathWithoutPaginationFindByServerLocation;
	private FinderPath _finderPathCountByServerLocation;
	private CollectionPersistenceFinder<FaroProject>
		_collectionPersistenceFinderByServerLocation;

	/**
	 * Returns all the faro projects where serverLocation = &#63;.
	 *
	 * @param serverLocation the server location
	 * @return the matching faro projects
	 */
	@Override
	public List<FaroProject> findByServerLocation(String serverLocation) {
		return findByServerLocation(
			serverLocation, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro projects where serverLocation = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param serverLocation the server location
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @return the range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByServerLocation(
		String serverLocation, int start, int end) {

		return findByServerLocation(serverLocation, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro projects where serverLocation = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param serverLocation the server location
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByServerLocation(
		String serverLocation, int start, int end,
		OrderByComparator<FaroProject> orderByComparator) {

		return findByServerLocation(
			serverLocation, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro projects where serverLocation = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param serverLocation the server location
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro projects
	 */
	@Override
	public List<FaroProject> findByServerLocation(
		String serverLocation, int start, int end,
		OrderByComparator<FaroProject> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByServerLocation.find(
			finderCache, new Object[] {serverLocation}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first faro project in the ordered set where serverLocation = &#63;.
	 *
	 * @param serverLocation the server location
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByServerLocation_First(
			String serverLocation,
			OrderByComparator<FaroProject> orderByComparator)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByServerLocation_First(
			serverLocation, orderByComparator);

		if (faroProject != null) {
			return faroProject;
		}

		throw new NoSuchFaroProjectException(
			_collectionPersistenceFinderByServerLocation.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {serverLocation}));
	}

	/**
	 * Returns the first faro project in the ordered set where serverLocation = &#63;.
	 *
	 * @param serverLocation the server location
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByServerLocation_First(
		String serverLocation,
		OrderByComparator<FaroProject> orderByComparator) {

		return _collectionPersistenceFinderByServerLocation.fetchFirst(
			finderCache, new Object[] {serverLocation}, orderByComparator);
	}

	/**
	 * Removes all the faro projects where serverLocation = &#63; from the database.
	 *
	 * @param serverLocation the server location
	 */
	@Override
	public void removeByServerLocation(String serverLocation) {
		_collectionPersistenceFinderByServerLocation.remove(
			finderCache, new Object[] {serverLocation});
	}

	/**
	 * Returns the number of faro projects where serverLocation = &#63;.
	 *
	 * @param serverLocation the server location
	 * @return the number of matching faro projects
	 */
	@Override
	public int countByServerLocation(String serverLocation) {
		return _collectionPersistenceFinderByServerLocation.count(
			finderCache, new Object[] {serverLocation});
	}

	private FinderPath _finderPathFetchByWeDeployKey;
	private UniquePersistenceFinder<FaroProject>
		_uniquePersistenceFinderByWeDeployKey;

	/**
	 * Returns the faro project where weDeployKey = &#63; or throws a <code>NoSuchFaroProjectException</code> if it could not be found.
	 *
	 * @param weDeployKey the we deploy key
	 * @return the matching faro project
	 * @throws NoSuchFaroProjectException if a matching faro project could not be found
	 */
	@Override
	public FaroProject findByWeDeployKey(String weDeployKey)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = fetchByWeDeployKey(weDeployKey);

		if (faroProject == null) {
			String message =
				_uniquePersistenceFinderByWeDeployKey.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {weDeployKey});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFaroProjectException(message);
		}

		return faroProject;
	}

	/**
	 * Returns the faro project where weDeployKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param weDeployKey the we deploy key
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByWeDeployKey(String weDeployKey) {
		return fetchByWeDeployKey(weDeployKey, true);
	}

	/**
	 * Returns the faro project where weDeployKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param weDeployKey the we deploy key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro project, or <code>null</code> if a matching faro project could not be found
	 */
	@Override
	public FaroProject fetchByWeDeployKey(
		String weDeployKey, boolean useFinderCache) {

		return _uniquePersistenceFinderByWeDeployKey.fetch(
			finderCache, new Object[] {weDeployKey}, useFinderCache);
	}

	/**
	 * Removes the faro project where weDeployKey = &#63; from the database.
	 *
	 * @param weDeployKey the we deploy key
	 * @return the faro project that was removed
	 */
	@Override
	public FaroProject removeByWeDeployKey(String weDeployKey)
		throws NoSuchFaroProjectException {

		FaroProject faroProject = findByWeDeployKey(weDeployKey);

		return remove(faroProject);
	}

	/**
	 * Returns the number of faro projects where weDeployKey = &#63;.
	 *
	 * @param weDeployKey the we deploy key
	 * @return the number of matching faro projects
	 */
	@Override
	public int countByWeDeployKey(String weDeployKey) {
		return _uniquePersistenceFinderByWeDeployKey.count(
			finderCache, new Object[] {weDeployKey});
	}

	public FaroProjectPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("state", "state_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FaroProject.class);

		setModelImplClass(FaroProjectImpl.class);
		setModelPKClass(long.class);

		setTable(FaroProjectTable.INSTANCE);
	}

	/**
	 * Caches the faro project in the entity cache if it is enabled.
	 *
	 * @param faroProject the faro project
	 */
	@Override
	public void cacheResult(FaroProject faroProject) {
		entityCache.putResult(
			FaroProjectImpl.class, faroProject.getPrimaryKey(), faroProject);

		finderCache.putResult(
			_finderPathFetchByGroupId, new Object[] {faroProject.getGroupId()},
			faroProject);

		finderCache.putResult(
			_finderPathFetchByCorpProjectUuid,
			new Object[] {faroProject.getCorpProjectUuid()}, faroProject);

		finderCache.putResult(
			_finderPathFetchByWeDeployKey,
			new Object[] {faroProject.getWeDeployKey()}, faroProject);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the faro projects in the entity cache if it is enabled.
	 *
	 * @param faroProjects the faro projects
	 */
	@Override
	public void cacheResult(List<FaroProject> faroProjects) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (faroProjects.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FaroProject faroProject : faroProjects) {
			if (entityCache.getResult(
					FaroProjectImpl.class, faroProject.getPrimaryKey()) ==
						null) {

				cacheResult(faroProject);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		FaroProjectModelImpl faroProjectModelImpl) {

		Object[] args = new Object[] {faroProjectModelImpl.getGroupId()};

		finderCache.putResult(
			_finderPathFetchByGroupId, args, faroProjectModelImpl);

		args = new Object[] {faroProjectModelImpl.getCorpProjectUuid()};

		finderCache.putResult(
			_finderPathFetchByCorpProjectUuid, args, faroProjectModelImpl);

		args = new Object[] {faroProjectModelImpl.getWeDeployKey()};

		finderCache.putResult(
			_finderPathFetchByWeDeployKey, args, faroProjectModelImpl);
	}

	/**
	 * Creates a new faro project with the primary key. Does not add the faro project to the database.
	 *
	 * @param faroProjectId the primary key for the new faro project
	 * @return the new faro project
	 */
	@Override
	public FaroProject create(long faroProjectId) {
		FaroProject faroProject = new FaroProjectImpl();

		faroProject.setNew(true);
		faroProject.setPrimaryKey(faroProjectId);

		faroProject.setCompanyId(CompanyThreadLocal.getCompanyId());

		return faroProject;
	}

	/**
	 * Removes the faro project with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroProjectId the primary key of the faro project
	 * @return the faro project that was removed
	 * @throws NoSuchFaroProjectException if a faro project with the primary key could not be found
	 */
	@Override
	public FaroProject remove(long faroProjectId)
		throws NoSuchFaroProjectException {

		return remove((Serializable)faroProjectId);
	}

	@Override
	protected FaroProject removeImpl(FaroProject faroProject) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(faroProject)) {
				faroProject = (FaroProject)session.get(
					FaroProjectImpl.class, faroProject.getPrimaryKeyObj());
			}

			if (faroProject != null) {
				session.delete(faroProject);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (faroProject != null) {
			clearCache(faroProject);
		}

		return faroProject;
	}

	@Override
	public FaroProject updateImpl(FaroProject faroProject) {
		boolean isNew = faroProject.isNew();

		if (!(faroProject instanceof FaroProjectModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(faroProject.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(faroProject);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in faroProject proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FaroProject implementation " +
					faroProject.getClass());
		}

		FaroProjectModelImpl faroProjectModelImpl =
			(FaroProjectModelImpl)faroProject;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(faroProject);
			}
			else {
				faroProject = (FaroProject)session.merge(faroProject);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FaroProjectImpl.class, faroProjectModelImpl, false, true);

		cacheUniqueFindersCache(faroProjectModelImpl);

		if (isNew) {
			faroProject.setNew(false);
		}

		faroProject.resetOriginalValues();

		return faroProject;
	}

	/**
	 * Returns the faro project with the primary key or throws a <code>NoSuchFaroProjectException</code> if it could not be found.
	 *
	 * @param faroProjectId the primary key of the faro project
	 * @return the faro project
	 * @throws NoSuchFaroProjectException if a faro project with the primary key could not be found
	 */
	@Override
	public FaroProject findByPrimaryKey(long faroProjectId)
		throws NoSuchFaroProjectException {

		return findByPrimaryKey((Serializable)faroProjectId);
	}

	/**
	 * Returns the faro project with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroProjectId the primary key of the faro project
	 * @return the faro project, or <code>null</code> if a faro project with the primary key could not be found
	 */
	@Override
	public FaroProject fetchByPrimaryKey(long faroProjectId) {
		return fetchByPrimaryKey((Serializable)faroProjectId);
	}

	/**
	 * Returns all the faro projects.
	 *
	 * @return the faro projects
	 */
	@Override
	public List<FaroProject> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @return the range of faro projects
	 */
	@Override
	public List<FaroProject> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro projects
	 */
	@Override
	public List<FaroProject> findAll(
		int start, int end, OrderByComparator<FaroProject> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro projects
	 * @param end the upper bound of the range of faro projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro projects
	 */
	@Override
	public List<FaroProject> findAll(
		int start, int end, OrderByComparator<FaroProject> orderByComparator,
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

		List<FaroProject> list = null;

		if (useFinderCache) {
			list = (List<FaroProject>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FAROPROJECT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FAROPROJECT;

				sql = sql.concat(FaroProjectModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FaroProject>)QueryUtil.list(
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
	 * Removes all the faro projects from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FaroProject faroProject : findAll()) {
			remove(faroProject);
		}
	}

	/**
	 * Returns the number of faro projects.
	 *
	 * @return the number of faro projects
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FAROPROJECT);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "faroProjectId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FAROPROJECT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FaroProjectModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the faro project persistence.
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

		_finderPathFetchByGroupId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_uniquePersistenceFinderByGroupId = new UniquePersistenceFinder<>(
			this, _finderPathFetchByGroupId, _SQL_SELECT_FAROPROJECT_WHERE,
			new FinderColumn<>(
				"faroProject.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, FaroProject::getGroupId));

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_collectionPersistenceFinderByUserId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUserId,
				_finderPathWithoutPaginationFindByUserId,
				_finderPathCountByUserId, _SQL_SELECT_FAROPROJECT_WHERE,
				_SQL_COUNT_FAROPROJECT_WHERE,
				FaroProjectModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"faroProject.", "userId", FinderColumn.Type.LONG, "=", true,
					true, FaroProject::getUserId));

		_finderPathFetchByCorpProjectUuid = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCorpProjectUuid",
			new String[] {String.class.getName()},
			new String[] {"corpProjectUuid"}, true);

		_uniquePersistenceFinderByCorpProjectUuid =
			new UniquePersistenceFinder<>(
				this, _finderPathFetchByCorpProjectUuid,
				_SQL_SELECT_FAROPROJECT_WHERE,
				new FinderColumn<>(
					"faroProject.", "corpProjectUuid", FinderColumn.Type.STRING,
					"=", true, true, FaroProject::getCorpProjectUuid));

		_finderPathWithPaginationFindByServerLocation = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByServerLocation",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"serverLocation"}, true);

		_finderPathWithoutPaginationFindByServerLocation = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByServerLocation",
			new String[] {String.class.getName()},
			new String[] {"serverLocation"}, true);

		_finderPathCountByServerLocation = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByServerLocation",
			new String[] {String.class.getName()},
			new String[] {"serverLocation"}, false);

		_collectionPersistenceFinderByServerLocation =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByServerLocation,
				_finderPathWithoutPaginationFindByServerLocation,
				_finderPathCountByServerLocation, _SQL_SELECT_FAROPROJECT_WHERE,
				_SQL_COUNT_FAROPROJECT_WHERE,
				FaroProjectModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"faroProject.", "serverLocation", FinderColumn.Type.STRING,
					"=", true, true, FaroProject::getServerLocation));

		_finderPathFetchByWeDeployKey = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByWeDeployKey",
			new String[] {String.class.getName()}, new String[] {"weDeployKey"},
			true);

		_uniquePersistenceFinderByWeDeployKey = new UniquePersistenceFinder<>(
			this, _finderPathFetchByWeDeployKey, _SQL_SELECT_FAROPROJECT_WHERE,
			new FinderColumn<>(
				"faroProject.", "weDeployKey", FinderColumn.Type.STRING, "=",
				true, true, FaroProject::getWeDeployKey));

		FaroProjectUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FaroProjectUtil.setPersistence(null);

		entityCache.removeCache(FaroProjectImpl.class.getName());
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FAROPROJECT =
		"SELECT faroProject FROM FaroProject faroProject";

	private static final String _SQL_SELECT_FAROPROJECT_WHERE =
		"SELECT faroProject FROM FaroProject faroProject WHERE ";

	private static final String _SQL_COUNT_FAROPROJECT =
		"SELECT COUNT(faroProject) FROM FaroProject faroProject";

	private static final String _SQL_COUNT_FAROPROJECT_WHERE =
		"SELECT COUNT(faroProject) FROM FaroProject faroProject WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "faroProject.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FaroProject exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FaroProjectPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"state"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1530644714