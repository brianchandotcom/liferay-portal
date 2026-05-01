/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.background.task.exception.NoSuchBackgroundTaskException;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.model.BackgroundTaskTable;
import com.liferay.portal.background.task.model.impl.BackgroundTaskImpl;
import com.liferay.portal.background.task.model.impl.BackgroundTaskModelImpl;
import com.liferay.portal.background.task.service.persistence.BackgroundTaskPersistence;
import com.liferay.portal.background.task.service.persistence.BackgroundTaskUtil;
import com.liferay.portal.background.task.service.persistence.impl.constants.BackgroundTaskPersistenceConstants;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the background task service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = BackgroundTaskPersistence.class)
public class BackgroundTaskPersistenceImpl
	extends BasePersistenceImpl<BackgroundTask, NoSuchBackgroundTaskException>
	implements BackgroundTaskPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BackgroundTaskUtil</code> to access the background task persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BackgroundTaskImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<BackgroundTask>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the background tasks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByGroupId.find(
			finderCache, new Object[] {groupId}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByGroupId_First(
			long groupId, OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByGroupId_First(
			groupId, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		throw new NoSuchBackgroundTaskException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByGroupId_First(
		long groupId, OrderByComparator<BackgroundTask> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Removes all the background tasks where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of background tasks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByGroupId(long groupId) {
		return _collectionPersistenceFinderByGroupId.count(
			finderCache, new Object[] {groupId});
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<BackgroundTask>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the background tasks where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first background task in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByCompanyId_First(
			long companyId, OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		throw new NoSuchBackgroundTaskException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first background task in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByCompanyId_First(
		long companyId, OrderByComparator<BackgroundTask> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the background tasks where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of background tasks where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	private FinderPath _finderPathWithPaginationFindByCompleted;
	private FinderPath _finderPathWithoutPaginationFindByCompleted;
	private FinderPath _finderPathCountByCompleted;
	private CollectionPersistenceFinder<BackgroundTask>
		_collectionPersistenceFinderByCompleted;

	/**
	 * Returns all the background tasks where completed = &#63;.
	 *
	 * @param completed the completed
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompleted(boolean completed) {
		return findByCompleted(
			completed, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompleted(
		boolean completed, int start, int end) {

		return findByCompleted(completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompleted(
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByCompleted(completed, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompleted(
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompleted.find(
			finderCache, new Object[] {completed}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first background task in the ordered set where completed = &#63;.
	 *
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByCompleted_First(
			boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByCompleted_First(
			completed, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		throw new NoSuchBackgroundTaskException(
			_collectionPersistenceFinderByCompleted.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {completed}));
	}

	/**
	 * Returns the first background task in the ordered set where completed = &#63;.
	 *
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByCompleted_First(
		boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return _collectionPersistenceFinderByCompleted.fetchFirst(
			finderCache, new Object[] {completed}, orderByComparator);
	}

	/**
	 * Removes all the background tasks where completed = &#63; from the database.
	 *
	 * @param completed the completed
	 */
	@Override
	public void removeByCompleted(boolean completed) {
		_collectionPersistenceFinderByCompleted.remove(
			finderCache, new Object[] {completed});
	}

	/**
	 * Returns the number of background tasks where completed = &#63;.
	 *
	 * @param completed the completed
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByCompleted(boolean completed) {
		return _collectionPersistenceFinderByCompleted.count(
			finderCache, new Object[] {completed});
	}

	private FinderPath _finderPathWithPaginationFindByStatus;
	private FinderPath _finderPathWithoutPaginationFindByStatus;
	private FinderPath _finderPathCountByStatus;
	private CollectionPersistenceFinder<BackgroundTask>
		_collectionPersistenceFinderByStatus;

	/**
	 * Returns all the background tasks where status = &#63;.
	 *
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByStatus(int status) {
		return findByStatus(status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByStatus(int status, int start, int end) {
		return findByStatus(status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByStatus(
		int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByStatus(status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByStatus(
		int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByStatus.find(
			finderCache, new Object[] {status}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first background task in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByStatus_First(
			int status, OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByStatus_First(
			status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		throw new NoSuchBackgroundTaskException(
			_collectionPersistenceFinderByStatus.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {status}));
	}

	/**
	 * Returns the first background task in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByStatus_First(
		int status, OrderByComparator<BackgroundTask> orderByComparator) {

		return _collectionPersistenceFinderByStatus.fetchFirst(
			finderCache, new Object[] {status}, orderByComparator);
	}

	/**
	 * Removes all the background tasks where status = &#63; from the database.
	 *
	 * @param status the status
	 */
	@Override
	public void removeByStatus(int status) {
		_collectionPersistenceFinderByStatus.remove(
			finderCache, new Object[] {status});
	}

	/**
	 * Returns the number of background tasks where status = &#63;.
	 *
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByStatus(int status) {
		return _collectionPersistenceFinderByStatus.count(
			finderCache, new Object[] {status});
	}

	private FinderPath _finderPathWithPaginationFindByG_T;
	private FinderPath _finderPathWithoutPaginationFindByG_T;
	private FinderPath _finderPathCountByG_T;
	private FinderPath _finderPathWithPaginationCountByG_T;

	/**
	 * Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long groupId, String taskExecutorClassName) {

		return findByG_T(
			groupId, taskExecutorClassName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long groupId, String taskExecutorClassName, int start, int end) {

		return findByG_T(groupId, taskExecutorClassName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long groupId, String taskExecutorClassName, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T(
			groupId, taskExecutorClassName, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long groupId, String taskExecutorClassName, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_T;
				finderArgs = new Object[] {groupId, taskExecutorClassName};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_T;
			finderArgs = new Object[] {
				groupId, taskExecutorClassName, start, end, orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName())) {

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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_T_First(
			long groupId, String taskExecutorClassName,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_T_First(
			groupId, taskExecutorClassName, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_T_First(
		long groupId, String taskExecutorClassName,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByG_T(
			groupId, taskExecutorClassName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long[] groupIds, String[] taskExecutorClassNames) {

		return findByG_T(
			groupIds, taskExecutorClassNames, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long[] groupIds, String[] taskExecutorClassNames, int start, int end) {

		return findByG_T(groupIds, taskExecutorClassNames, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long[] groupIds, String[] taskExecutorClassNames, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T(
			groupIds, taskExecutorClassNames, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long[] groupIds, String[] taskExecutorClassNames, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		if ((groupIds.length == 1) && (taskExecutorClassNames.length == 1)) {
			return findByG_T(
				groupIds[0], taskExecutorClassNames[0], start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds),
					StringUtil.merge(taskExecutorClassNames)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds),
				StringUtil.merge(taskExecutorClassNames), start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByG_T, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!ArrayUtil.contains(
							groupIds, backgroundTask.getGroupId()) ||
						!ArrayUtil.contains(
							taskExecutorClassNames,
							backgroundTask.getTaskExecutorClassName())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_T, finderArgs, list);
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
	 * Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 */
	@Override
	public void removeByG_T(long groupId, String taskExecutorClassName) {
		for (BackgroundTask backgroundTask :
				findByG_T(
					groupId, taskExecutorClassName, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T(long groupId, String taskExecutorClassName) {
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByG_T;

		Object[] finderArgs = new Object[] {groupId, taskExecutorClassName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

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

	/**
	 * Returns the number of background tasks where groupId = any &#63; and taskExecutorClassName = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T(long[] groupIds, String[] taskExecutorClassNames) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		Object[] finderArgs = new Object[] {
			StringUtil.merge(groupIds), StringUtil.merge(taskExecutorClassNames)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_T, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_T, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 =
		"backgroundTask.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_GROUPID_7 =
		"backgroundTask.groupId IN (";

	private static final String _FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ?";

	private static final String _FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '')";

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;
	private CollectionPersistenceFinder<BackgroundTask>
		_collectionPersistenceFinderByG_S;

	/**
	 * Returns all the background tasks where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_S(long groupId, int status) {
		return findByG_S(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_S(
		long groupId, int status, int start, int end) {

		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_S(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_S.find(
			finderCache, new Object[] {groupId, status}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_S_First(
			long groupId, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_S_First(
			groupId, status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		throw new NoSuchBackgroundTaskException(
			_collectionPersistenceFinderByG_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, status}));
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_S_First(
		long groupId, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return _collectionPersistenceFinderByG_S.fetchFirst(
			finderCache, new Object[] {groupId, status}, orderByComparator);
	}

	/**
	 * Removes all the background tasks where groupId = &#63; and status = &#63; from the database.
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
	 * Returns the number of background tasks where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		return _collectionPersistenceFinderByG_S.count(
			finderCache, new Object[] {groupId, status});
	}

	private FinderPath _finderPathWithPaginationFindByT_S;
	private FinderPath _finderPathWithoutPaginationFindByT_S;
	private FinderPath _finderPathCountByT_S;
	private FinderPath _finderPathWithPaginationCountByT_S;

	/**
	 * Returns all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String taskExecutorClassName, int status) {

		return findByT_S(
			taskExecutorClassName, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String taskExecutorClassName, int status, int start, int end) {

		return findByT_S(taskExecutorClassName, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String taskExecutorClassName, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByT_S(
			taskExecutorClassName, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String taskExecutorClassName, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByT_S;
				finderArgs = new Object[] {taskExecutorClassName, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByT_S;
			finderArgs = new Object[] {
				taskExecutorClassName, status, start, end, orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName()) ||
						(status != backgroundTask.getStatus())) {

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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(status);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByT_S_First(
			String taskExecutorClassName, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByT_S_First(
			taskExecutorClassName, status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByT_S_First(
		String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByT_S(
			taskExecutorClassName, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String[] taskExecutorClassNames, int status) {

		return findByT_S(
			taskExecutorClassNames, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String[] taskExecutorClassNames, int status, int start, int end) {

		return findByT_S(taskExecutorClassNames, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String[] taskExecutorClassNames, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByT_S(
			taskExecutorClassNames, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String[] taskExecutorClassNames, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		if (taskExecutorClassNames.length == 1) {
			return findByT_S(
				taskExecutorClassNames[0], status, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(taskExecutorClassNames), status
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(taskExecutorClassNames), status, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByT_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!ArrayUtil.contains(
							taskExecutorClassNames,
							backgroundTask.getTaskExecutorClassName()) ||
						(status != backgroundTask.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_T_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(status);

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByT_S, finderArgs, list);
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
	 * Removes all the background tasks where taskExecutorClassName = &#63; and status = &#63; from the database.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 */
	@Override
	public void removeByT_S(String taskExecutorClassName, int status) {
		for (BackgroundTask backgroundTask :
				findByT_S(
					taskExecutorClassName, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByT_S(String taskExecutorClassName, int status) {
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByT_S;

		Object[] finderArgs = new Object[] {taskExecutorClassName, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_T_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

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

	/**
	 * Returns the number of background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByT_S(String[] taskExecutorClassNames, int status) {
		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		Object[] finderArgs = new Object[] {
			StringUtil.merge(taskExecutorClassNames), status
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByT_S, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_T_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByT_S, finderArgs, count);
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

	private static final String _FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ? AND ";

	private static final String _FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '') AND ";

	private static final String _FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_5 =
		"(" + removeConjunction(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2) +
			")";

	private static final String _FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_6 =
		"(" + removeConjunction(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3) +
			")";

	private static final String _FINDER_COLUMN_T_S_STATUS_2 =
		"backgroundTask.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_N_T;
	private FinderPath _finderPathWithoutPaginationFindByG_N_T;
	private FinderPath _finderPathCountByG_N_T;
	private FinderPath _finderPathWithPaginationCountByG_N_T;

	/**
	 * Returns all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long groupId, String name, String taskExecutorClassName) {

		return findByG_N_T(
			groupId, name, taskExecutorClassName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long groupId, String name, String taskExecutorClassName, int start,
		int end) {

		return findByG_N_T(
			groupId, name, taskExecutorClassName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long groupId, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_N_T(
			groupId, name, taskExecutorClassName, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long groupId, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_N_T;
				finderArgs = new Object[] {
					groupId, name, taskExecutorClassName
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_N_T;
			finderArgs = new Object[] {
				groupId, name, taskExecutorClassName, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!name.equals(backgroundTask.getName()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName())) {

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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_N_T_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_N_T_First(
			long groupId, String name, String taskExecutorClassName,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_N_T_First(
			groupId, name, taskExecutorClassName, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_N_T_First(
		long groupId, String name, String taskExecutorClassName,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByG_N_T(
			groupId, name, taskExecutorClassName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassNames the task executor class names
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long[] groupIds, String name, String[] taskExecutorClassNames) {

		return findByG_N_T(
			groupIds, name, taskExecutorClassNames, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassNames the task executor class names
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long[] groupIds, String name, String[] taskExecutorClassNames,
		int start, int end) {

		return findByG_N_T(
			groupIds, name, taskExecutorClassNames, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassNames the task executor class names
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long[] groupIds, String name, String[] taskExecutorClassNames,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_N_T(
			groupIds, name, taskExecutorClassNames, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassNames the task executor class names
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long[] groupIds, String name, String[] taskExecutorClassNames,
		int start, int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		if ((groupIds.length == 1) && (taskExecutorClassNames.length == 1)) {
			return findByG_N_T(
				groupIds[0], name, taskExecutorClassNames[0], start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), name,
					StringUtil.merge(taskExecutorClassNames)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), name,
				StringUtil.merge(taskExecutorClassNames), start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByG_N_T, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!ArrayUtil.contains(
							groupIds, backgroundTask.getGroupId()) ||
						!name.equals(backgroundTask.getName()) ||
						!ArrayUtil.contains(
							taskExecutorClassNames,
							backgroundTask.getTaskExecutorClassName())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_N_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_NAME_2);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_2);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_N_T, finderArgs, list);
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
	 * Removes all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 */
	@Override
	public void removeByG_N_T(
		long groupId, String name, String taskExecutorClassName) {

		for (BackgroundTask backgroundTask :
				findByG_N_T(
					groupId, name, taskExecutorClassName, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_N_T(
		long groupId, String name, String taskExecutorClassName) {

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByG_N_T;

		Object[] finderArgs = new Object[] {
			groupId, name, taskExecutorClassName
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_N_T_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

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

	/**
	 * Returns the number of background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassNames the task executor class names
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_N_T(
		long[] groupIds, String name, String[] taskExecutorClassNames) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		Object[] finderArgs = new Object[] {
			StringUtil.merge(groupIds), name,
			StringUtil.merge(taskExecutorClassNames)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_N_T, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_N_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_NAME_2);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_2);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_N_T, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_N_T_GROUPID_2 =
		"backgroundTask.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_GROUPID_7 =
		"backgroundTask.groupId IN (";

	private static final String _FINDER_COLUMN_G_N_T_NAME_2 =
		"backgroundTask.name = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_NAME_3 =
		"(backgroundTask.name IS NULL OR backgroundTask.name = '') AND ";

	private static final String _FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ?";

	private static final String _FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '')";

	private FinderPath _finderPathWithPaginationFindByG_T_C;
	private FinderPath _finderPathWithoutPaginationFindByG_T_C;
	private FinderPath _finderPathCountByG_T_C;
	private FinderPath _finderPathWithPaginationCountByG_T_C;

	/**
	 * Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed) {

		return findByG_T_C(
			groupId, taskExecutorClassName, completed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed,
		int start, int end) {

		return findByG_T_C(
			groupId, taskExecutorClassName, completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T_C(
			groupId, taskExecutorClassName, completed, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed,
		int start, int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_T_C;
				finderArgs = new Object[] {
					groupId, taskExecutorClassName, completed
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_T_C;
			finderArgs = new Object[] {
				groupId, taskExecutorClassName, completed, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName()) ||
						(completed != backgroundTask.isCompleted())) {

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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_C_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_C_COMPLETED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_T_C_First(
			long groupId, String taskExecutorClassName, boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_T_C_First(
			groupId, taskExecutorClassName, completed, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_T_C_First(
		long groupId, String taskExecutorClassName, boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByG_T_C(
			groupId, taskExecutorClassName, completed, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param completed the completed
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed) {

		return findByG_T_C(
			groupIds, taskExecutorClassNames, completed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed,
		int start, int end) {

		return findByG_T_C(
			groupIds, taskExecutorClassNames, completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T_C(
			groupIds, taskExecutorClassNames, completed, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed,
		int start, int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		if ((groupIds.length == 1) && (taskExecutorClassNames.length == 1)) {
			return findByG_T_C(
				groupIds[0], taskExecutorClassNames[0], completed, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds),
					StringUtil.merge(taskExecutorClassNames), completed
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds),
				StringUtil.merge(taskExecutorClassNames), completed, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByG_T_C, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!ArrayUtil.contains(
							groupIds, backgroundTask.getGroupId()) ||
						!ArrayUtil.contains(
							taskExecutorClassNames,
							backgroundTask.getTaskExecutorClassName()) ||
						(completed != backgroundTask.isCompleted())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_T_C_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_T_C_COMPLETED_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(completed);

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_T_C, finderArgs, list);
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
	 * Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 */
	@Override
	public void removeByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed) {

		for (BackgroundTask backgroundTask :
				findByG_T_C(
					groupId, taskExecutorClassName, completed,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByG_T_C;

		Object[] finderArgs = new Object[] {
			groupId, taskExecutorClassName, completed
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_C_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_C_COMPLETED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

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

	/**
	 * Returns the number of background tasks where groupId = any &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param completed the completed
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T_C(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		Object[] finderArgs = new Object[] {
			StringUtil.merge(groupIds),
			StringUtil.merge(taskExecutorClassNames), completed
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_T_C, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_T_C_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_T_C_COMPLETED_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(completed);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_T_C, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_T_C_GROUPID_2 =
		"backgroundTask.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_C_GROUPID_7 =
		"backgroundTask.groupId IN (";

	private static final String _FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ? AND ";

	private static final String _FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '') AND ";

	private static final String _FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_5 =
		"(" + removeConjunction(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_2) +
			")";

	private static final String _FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_6 =
		"(" + removeConjunction(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_3) +
			")";

	private static final String _FINDER_COLUMN_G_T_C_COMPLETED_2 =
		"backgroundTask.completed = ?";

	private FinderPath _finderPathWithPaginationFindByG_T_S;
	private FinderPath _finderPathWithoutPaginationFindByG_T_S;
	private FinderPath _finderPathCountByG_T_S;
	private FinderPath _finderPathWithPaginationCountByG_T_S;

	/**
	 * Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String taskExecutorClassName, int status) {

		return findByG_T_S(
			groupId, taskExecutorClassName, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String taskExecutorClassName, int status, int start,
		int end) {

		return findByG_T_S(
			groupId, taskExecutorClassName, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String taskExecutorClassName, int status, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T_S(
			groupId, taskExecutorClassName, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String taskExecutorClassName, int status, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_T_S;
				finderArgs = new Object[] {
					groupId, taskExecutorClassName, status
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_T_S;
			finderArgs = new Object[] {
				groupId, taskExecutorClassName, status, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName()) ||
						(status != backgroundTask.getStatus())) {

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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(status);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_T_S_First(
			long groupId, String taskExecutorClassName, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_T_S_First(
			groupId, taskExecutorClassName, status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_T_S_First(
		long groupId, String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByG_T_S(
			groupId, taskExecutorClassName, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String[] taskExecutorClassNames, int status) {

		return findByG_T_S(
			groupId, taskExecutorClassNames, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String[] taskExecutorClassNames, int status, int start,
		int end) {

		return findByG_T_S(
			groupId, taskExecutorClassNames, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String[] taskExecutorClassNames, int status, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T_S(
			groupId, taskExecutorClassNames, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String[] taskExecutorClassNames, int status, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		if (taskExecutorClassNames.length == 1) {
			return findByG_T_S(
				groupId, taskExecutorClassNames[0], status, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(taskExecutorClassNames), status
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, StringUtil.merge(taskExecutorClassNames), status,
				start, end, orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByG_T_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!ArrayUtil.contains(
							taskExecutorClassNames,
							backgroundTask.getTaskExecutorClassName()) ||
						(status != backgroundTask.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(status);

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_T_S, finderArgs, list);
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
	 * Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 */
	@Override
	public void removeByG_T_S(
		long groupId, String taskExecutorClassName, int status) {

		for (BackgroundTask backgroundTask :
				findByG_T_S(
					groupId, taskExecutorClassName, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T_S(
		long groupId, String taskExecutorClassName, int status) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByG_T_S;

		Object[] finderArgs = new Object[] {
			groupId, taskExecutorClassName, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

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

	/**
	 * Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T_S(
		long groupId, String[] taskExecutorClassNames, int status) {

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		Object[] finderArgs = new Object[] {
			groupId, StringUtil.merge(taskExecutorClassNames), status
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_T_S, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_T_S, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_T_S_GROUPID_2 =
		"backgroundTask.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ? AND ";

	private static final String _FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '') AND ";

	private static final String _FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_5 =
		"(" + removeConjunction(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2) +
			")";

	private static final String _FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_6 =
		"(" + removeConjunction(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3) +
			")";

	private static final String _FINDER_COLUMN_G_T_S_STATUS_2 =
		"backgroundTask.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_N_T_C;
	private FinderPath _finderPathWithoutPaginationFindByG_N_T_C;
	private FinderPath _finderPathCountByG_N_T_C;
	private FinderPath _finderPathWithPaginationCountByG_N_T_C;

	/**
	 * Returns all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed) {

		return findByG_N_T_C(
			groupId, name, taskExecutorClassName, completed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed, int start, int end) {

		return findByG_N_T_C(
			groupId, name, taskExecutorClassName, completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_N_T_C(
			groupId, name, taskExecutorClassName, completed, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_N_T_C;
				finderArgs = new Object[] {
					groupId, name, taskExecutorClassName, completed
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_N_T_C;
			finderArgs = new Object[] {
				groupId, name, taskExecutorClassName, completed, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!name.equals(backgroundTask.getName()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName()) ||
						(completed != backgroundTask.isCompleted())) {

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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_N_T_C_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_N_T_C_COMPLETED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_N_T_C_First(
			long groupId, String name, String taskExecutorClassName,
			boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_N_T_C_First(
			groupId, name, taskExecutorClassName, completed, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_N_T_C_First(
		long groupId, String name, String taskExecutorClassName,
		boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByG_N_T_C(
			groupId, name, taskExecutorClassName, completed, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed) {

		return findByG_N_T_C(
			groupIds, name, taskExecutorClassName, completed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed, int start, int end) {

		return findByG_N_T_C(
			groupIds, name, taskExecutorClassName, completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_N_T_C(
			groupIds, name, taskExecutorClassName, completed, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		if (groupIds.length == 1) {
			return findByG_N_T_C(
				groupIds[0], name, taskExecutorClassName, completed, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), name, taskExecutorClassName,
					completed
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), name, taskExecutorClassName,
				completed, start, end, orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByG_N_T_C, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!ArrayUtil.contains(
							groupIds, backgroundTask.getGroupId()) ||
						!name.equals(backgroundTask.getName()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName()) ||
						(completed != backgroundTask.isCompleted())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_N_T_C_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_N_T_C_COMPLETED_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_N_T_C, finderArgs,
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

	/**
	 * Removes all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 */
	@Override
	public void removeByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed) {

		for (BackgroundTask backgroundTask :
				findByG_N_T_C(
					groupId, name, taskExecutorClassName, completed,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed) {

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByG_N_T_C;

		Object[] finderArgs = new Object[] {
			groupId, name, taskExecutorClassName, completed
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_N_T_C_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_N_T_C_COMPLETED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

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

	/**
	 * Returns the number of background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_N_T_C(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		Object[] finderArgs = new Object[] {
			StringUtil.merge(groupIds), name, taskExecutorClassName, completed
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_N_T_C, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_N_T_C_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_N_T_C_COMPLETED_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_N_T_C, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_N_T_C_GROUPID_2 =
		"backgroundTask.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_C_GROUPID_7 =
		"backgroundTask.groupId IN (";

	private static final String _FINDER_COLUMN_G_N_T_C_NAME_2 =
		"backgroundTask.name = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_C_NAME_3 =
		"(backgroundTask.name IS NULL OR backgroundTask.name = '') AND ";

	private static final String _FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '') AND ";

	private static final String _FINDER_COLUMN_G_N_T_C_COMPLETED_2 =
		"backgroundTask.completed = ?";

	public BackgroundTaskPersistenceImpl() {
		setModelClass(BackgroundTask.class);

		setModelImplClass(BackgroundTaskImpl.class);
		setModelPKClass(long.class);

		setTable(BackgroundTaskTable.INSTANCE);
	}

	/**
	 * Caches the background task in the entity cache if it is enabled.
	 *
	 * @param backgroundTask the background task
	 */
	@Override
	public void cacheResult(BackgroundTask backgroundTask) {
		entityCache.putResult(
			BackgroundTaskImpl.class, backgroundTask.getPrimaryKey(),
			backgroundTask);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the background tasks in the entity cache if it is enabled.
	 *
	 * @param backgroundTasks the background tasks
	 */
	@Override
	public void cacheResult(List<BackgroundTask> backgroundTasks) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (backgroundTasks.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BackgroundTask backgroundTask : backgroundTasks) {
			if (entityCache.getResult(
					BackgroundTaskImpl.class, backgroundTask.getPrimaryKey()) ==
						null) {

				cacheResult(backgroundTask);
			}
		}
	}

	/**
	 * Creates a new background task with the primary key. Does not add the background task to the database.
	 *
	 * @param backgroundTaskId the primary key for the new background task
	 * @return the new background task
	 */
	@Override
	public BackgroundTask create(long backgroundTaskId) {
		BackgroundTask backgroundTask = new BackgroundTaskImpl();

		backgroundTask.setNew(true);
		backgroundTask.setPrimaryKey(backgroundTaskId);

		backgroundTask.setCompanyId(CompanyThreadLocal.getCompanyId());

		return backgroundTask;
	}

	/**
	 * Removes the background task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param backgroundTaskId the primary key of the background task
	 * @return the background task that was removed
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask remove(long backgroundTaskId)
		throws NoSuchBackgroundTaskException {

		return remove((Serializable)backgroundTaskId);
	}

	@Override
	protected BackgroundTask removeImpl(BackgroundTask backgroundTask) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(backgroundTask)) {
				backgroundTask = (BackgroundTask)session.get(
					BackgroundTaskImpl.class,
					backgroundTask.getPrimaryKeyObj());
			}

			if (backgroundTask != null) {
				session.delete(backgroundTask);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (backgroundTask != null) {
			clearCache(backgroundTask);
		}

		return backgroundTask;
	}

	@Override
	public BackgroundTask updateImpl(BackgroundTask backgroundTask) {
		boolean isNew = backgroundTask.isNew();

		if (!(backgroundTask instanceof BackgroundTaskModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(backgroundTask.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					backgroundTask);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in backgroundTask proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BackgroundTask implementation " +
					backgroundTask.getClass());
		}

		BackgroundTaskModelImpl backgroundTaskModelImpl =
			(BackgroundTaskModelImpl)backgroundTask;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (backgroundTask.getCreateDate() == null)) {
			if (serviceContext == null) {
				backgroundTask.setCreateDate(date);
			}
			else {
				backgroundTask.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!backgroundTaskModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				backgroundTask.setModifiedDate(date);
			}
			else {
				backgroundTask.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(backgroundTask);
			}
			else {
				backgroundTask = (BackgroundTask)session.merge(backgroundTask);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			BackgroundTaskImpl.class, backgroundTaskModelImpl, false, true);

		if (isNew) {
			backgroundTask.setNew(false);
		}

		backgroundTask.resetOriginalValues();

		return backgroundTask;
	}

	/**
	 * Returns the background task with the primary key or throws a <code>NoSuchBackgroundTaskException</code> if it could not be found.
	 *
	 * @param backgroundTaskId the primary key of the background task
	 * @return the background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask findByPrimaryKey(long backgroundTaskId)
		throws NoSuchBackgroundTaskException {

		return findByPrimaryKey((Serializable)backgroundTaskId);
	}

	/**
	 * Returns the background task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param backgroundTaskId the primary key of the background task
	 * @return the background task, or <code>null</code> if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask fetchByPrimaryKey(long backgroundTaskId) {
		return fetchByPrimaryKey((Serializable)backgroundTaskId);
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "backgroundTaskId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BACKGROUNDTASK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BackgroundTaskModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the background task persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

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
				_finderPathCountByGroupId, _SQL_SELECT_BACKGROUNDTASK_WHERE,
				_SQL_COUNT_BACKGROUNDTASK_WHERE,
				BackgroundTaskModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"backgroundTask.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, BackgroundTask::getGroupId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_BACKGROUNDTASK_WHERE,
				_SQL_COUNT_BACKGROUNDTASK_WHERE,
				BackgroundTaskModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"backgroundTask.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, BackgroundTask::getCompanyId));

		_finderPathWithPaginationFindByCompleted = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompleted",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"completed"}, true);

		_finderPathWithoutPaginationFindByCompleted = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompleted",
			new String[] {Boolean.class.getName()}, new String[] {"completed"},
			true);

		_finderPathCountByCompleted = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompleted",
			new String[] {Boolean.class.getName()}, new String[] {"completed"},
			false);

		_collectionPersistenceFinderByCompleted =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCompleted,
				_finderPathWithoutPaginationFindByCompleted,
				_finderPathCountByCompleted, _SQL_SELECT_BACKGROUNDTASK_WHERE,
				_SQL_COUNT_BACKGROUNDTASK_WHERE,
				BackgroundTaskModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"backgroundTask.", "completed", FinderColumn.Type.BOOLEAN,
					"=", true, true, BackgroundTask::isCompleted));

		_finderPathWithPaginationFindByStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStatus",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"status"}, true);

		_finderPathWithoutPaginationFindByStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStatus",
			new String[] {Integer.class.getName()}, new String[] {"status"},
			true);

		_finderPathCountByStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStatus",
			new String[] {Integer.class.getName()}, new String[] {"status"},
			false);

		_collectionPersistenceFinderByStatus =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByStatus,
				_finderPathWithoutPaginationFindByStatus,
				_finderPathCountByStatus, _SQL_SELECT_BACKGROUNDTASK_WHERE,
				_SQL_COUNT_BACKGROUNDTASK_WHERE,
				BackgroundTaskModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"backgroundTask.", "status", FinderColumn.Type.INTEGER, "=",
					true, true, BackgroundTask::getStatus));

		_finderPathWithPaginationFindByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName"}, true);

		_finderPathWithoutPaginationFindByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "taskExecutorClassName"}, true);

		_finderPathCountByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "taskExecutorClassName"}, false);

		_finderPathWithPaginationCountByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "taskExecutorClassName"}, false);

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
			_SQL_SELECT_BACKGROUNDTASK_WHERE, _SQL_COUNT_BACKGROUNDTASK_WHERE,
			BackgroundTaskModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"backgroundTask.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, BackgroundTask::getGroupId),
			new FinderColumn<>(
				"backgroundTask.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, BackgroundTask::getStatus));

		_finderPathWithPaginationFindByT_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_S",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"taskExecutorClassName", "status"}, true);

		_finderPathWithoutPaginationFindByT_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_S",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"taskExecutorClassName", "status"}, true);

		_finderPathCountByT_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_S",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"taskExecutorClassName", "status"}, false);

		_finderPathWithPaginationCountByT_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByT_S",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"taskExecutorClassName", "status"}, false);

		_finderPathWithPaginationFindByG_N_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "name", "taskExecutorClassName"}, true);

		_finderPathWithoutPaginationFindByG_N_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "name", "taskExecutorClassName"}, true);

		_finderPathCountByG_N_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "name", "taskExecutorClassName"}, false);

		_finderPathWithPaginationCountByG_N_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "name", "taskExecutorClassName"}, false);

		_finderPathWithPaginationFindByG_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "completed"},
			true);

		_finderPathWithoutPaginationFindByG_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "completed"},
			true);

		_finderPathCountByG_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "completed"},
			false);

		_finderPathWithPaginationCountByG_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "completed"},
			false);

		_finderPathWithPaginationFindByG_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "status"}, true);

		_finderPathWithoutPaginationFindByG_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "status"}, true);

		_finderPathCountByG_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "status"}, false);

		_finderPathWithPaginationCountByG_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "status"}, false);

		_finderPathWithPaginationFindByG_N_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "name", "taskExecutorClassName", "completed"
			},
			true);

		_finderPathWithoutPaginationFindByG_N_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "name", "taskExecutorClassName", "completed"
			},
			true);

		_finderPathCountByG_N_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "name", "taskExecutorClassName", "completed"
			},
			false);

		_finderPathWithPaginationCountByG_N_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_N_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "name", "taskExecutorClassName", "completed"
			},
			false);

		BackgroundTaskUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		BackgroundTaskUtil.setPersistence(null);

		entityCache.removeCache(BackgroundTaskImpl.class.getName());
	}

	@Override
	@Reference(
		target = BackgroundTaskPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = BackgroundTaskPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BackgroundTaskPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _ENTITY_ALIAS_PREFIX =
		BackgroundTaskModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_BACKGROUNDTASK =
		"SELECT backgroundTask FROM BackgroundTask backgroundTask";

	private static final String _SQL_SELECT_BACKGROUNDTASK_WHERE =
		"SELECT backgroundTask FROM BackgroundTask backgroundTask WHERE ";

	private static final String _SQL_COUNT_BACKGROUNDTASK_WHERE =
		"SELECT COUNT(backgroundTask) FROM BackgroundTask backgroundTask WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BackgroundTask exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:463854229