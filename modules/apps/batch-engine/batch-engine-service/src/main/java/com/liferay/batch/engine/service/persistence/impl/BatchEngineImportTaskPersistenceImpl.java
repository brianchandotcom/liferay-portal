/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.persistence.impl;

import com.liferay.batch.engine.exception.DuplicateBatchEngineImportTaskExternalReferenceCodeException;
import com.liferay.batch.engine.exception.NoSuchImportTaskException;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.model.BatchEngineImportTaskTable;
import com.liferay.batch.engine.model.impl.BatchEngineImportTaskImpl;
import com.liferay.batch.engine.model.impl.BatchEngineImportTaskModelImpl;
import com.liferay.batch.engine.service.persistence.BatchEngineImportTaskPersistence;
import com.liferay.batch.engine.service.persistence.BatchEngineImportTaskUtil;
import com.liferay.batch.engine.service.persistence.impl.constants.BatchEnginePersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.ContentTypes;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the batch engine import task service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @generated
 */
@Component(service = BatchEngineImportTaskPersistence.class)
public class BatchEngineImportTaskPersistenceImpl
	extends BasePersistenceImpl
		<BatchEngineImportTask, NoSuchImportTaskException>
	implements BatchEngineImportTaskPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchEngineImportTaskUtil</code> to access the batch engine import task persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchEngineImportTaskImpl.class.getName();

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
	private CollectionPersistenceFinder<BatchEngineImportTask>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the batch engine import tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine import tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @return the range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first batch engine import task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task
	 * @throws NoSuchImportTaskException if a matching batch engine import task could not be found
	 */
	@Override
	public BatchEngineImportTask findByUuid_First(
			String uuid,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws NoSuchImportTaskException {

		BatchEngineImportTask batchEngineImportTask = fetchByUuid_First(
			uuid, orderByComparator);

		if (batchEngineImportTask != null) {
			return batchEngineImportTask;
		}

		throw new NoSuchImportTaskException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first batch engine import task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	@Override
	public BatchEngineImportTask fetchByUuid_First(
		String uuid,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the batch engine import tasks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of batch engine import tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch engine import tasks
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<BatchEngineImportTask>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the batch engine import tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine import tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @return the range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch engine import task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task
	 * @throws NoSuchImportTaskException if a matching batch engine import task could not be found
	 */
	@Override
	public BatchEngineImportTask findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws NoSuchImportTaskException {

		BatchEngineImportTask batchEngineImportTask = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (batchEngineImportTask != null) {
			return batchEngineImportTask;
		}

		throw new NoSuchImportTaskException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first batch engine import task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	@Override
	public BatchEngineImportTask fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the batch engine import tasks where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of batch engine import tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch engine import tasks
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<BatchEngineImportTask>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the batch engine import tasks where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine import tasks where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @return the range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch engine import task in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task
	 * @throws NoSuchImportTaskException if a matching batch engine import task could not be found
	 */
	@Override
	public BatchEngineImportTask findByCompanyId_First(
			long companyId,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws NoSuchImportTaskException {

		BatchEngineImportTask batchEngineImportTask = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (batchEngineImportTask != null) {
			return batchEngineImportTask;
		}

		throw new NoSuchImportTaskException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first batch engine import task in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	@Override
	public BatchEngineImportTask fetchByCompanyId_First(
		long companyId,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the batch engine import tasks where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of batch engine import tasks where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching batch engine import tasks
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	private FinderPath _finderPathWithPaginationFindByExecuteStatus;
	private FinderPath _finderPathWithoutPaginationFindByExecuteStatus;
	private FinderPath _finderPathCountByExecuteStatus;
	private CollectionPersistenceFinder<BatchEngineImportTask>
		_collectionPersistenceFinderByExecuteStatus;

	/**
	 * Returns all the batch engine import tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByExecuteStatus(
		String executeStatus) {

		return findByExecuteStatus(
			executeStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine import tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @return the range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByExecuteStatus(
		String executeStatus, int start, int end) {

		return findByExecuteStatus(executeStatus, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return findByExecuteStatus(
			executeStatus, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByExecuteStatus.find(
			finderCache, new Object[] {executeStatus}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch engine import task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task
	 * @throws NoSuchImportTaskException if a matching batch engine import task could not be found
	 */
	@Override
	public BatchEngineImportTask findByExecuteStatus_First(
			String executeStatus,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws NoSuchImportTaskException {

		BatchEngineImportTask batchEngineImportTask =
			fetchByExecuteStatus_First(executeStatus, orderByComparator);

		if (batchEngineImportTask != null) {
			return batchEngineImportTask;
		}

		throw new NoSuchImportTaskException(
			_collectionPersistenceFinderByExecuteStatus.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {executeStatus}));
	}

	/**
	 * Returns the first batch engine import task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	@Override
	public BatchEngineImportTask fetchByExecuteStatus_First(
		String executeStatus,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return _collectionPersistenceFinderByExecuteStatus.fetchFirst(
			finderCache, new Object[] {executeStatus}, orderByComparator);
	}

	/**
	 * Removes all the batch engine import tasks where executeStatus = &#63; from the database.
	 *
	 * @param executeStatus the execute status
	 */
	@Override
	public void removeByExecuteStatus(String executeStatus) {
		_collectionPersistenceFinderByExecuteStatus.remove(
			finderCache, new Object[] {executeStatus});
	}

	/**
	 * Returns the number of batch engine import tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the number of matching batch engine import tasks
	 */
	@Override
	public int countByExecuteStatus(String executeStatus) {
		return _collectionPersistenceFinderByExecuteStatus.count(
			finderCache, new Object[] {executeStatus});
	}

	private FinderPath _finderPathFetchByERC_C;
	private UniquePersistenceFinder<BatchEngineImportTask>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the batch engine import task where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchImportTaskException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching batch engine import task
	 * @throws NoSuchImportTaskException if a matching batch engine import task could not be found
	 */
	@Override
	public BatchEngineImportTask findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchImportTaskException {

		BatchEngineImportTask batchEngineImportTask = fetchByERC_C(
			externalReferenceCode, companyId);

		if (batchEngineImportTask == null) {
			String message =
				_uniquePersistenceFinderByERC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchImportTaskException(message);
		}

		return batchEngineImportTask;
	}

	/**
	 * Returns the batch engine import task where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	@Override
	public BatchEngineImportTask fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the batch engine import task where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	@Override
	public BatchEngineImportTask fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_C.fetch(
			finderCache, new Object[] {externalReferenceCode, companyId},
			useFinderCache);
	}

	/**
	 * Removes the batch engine import task where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the batch engine import task that was removed
	 */
	@Override
	public BatchEngineImportTask removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchImportTaskException {

		BatchEngineImportTask batchEngineImportTask = findByERC_C(
			externalReferenceCode, companyId);

		return remove(batchEngineImportTask);
	}

	/**
	 * Returns the number of batch engine import tasks where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching batch engine import tasks
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public BatchEngineImportTaskPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(BatchEngineImportTask.class);

		setModelImplClass(BatchEngineImportTaskImpl.class);
		setModelPKClass(long.class);

		setTable(BatchEngineImportTaskTable.INSTANCE);
	}

	/**
	 * Caches the batch engine import task in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportTask the batch engine import task
	 */
	@Override
	public void cacheResult(BatchEngineImportTask batchEngineImportTask) {
		entityCache.putResult(
			BatchEngineImportTaskImpl.class,
			batchEngineImportTask.getPrimaryKey(), batchEngineImportTask);

		finderCache.putResult(
			_finderPathFetchByERC_C,
			new Object[] {
				batchEngineImportTask.getExternalReferenceCode(),
				batchEngineImportTask.getCompanyId()
			},
			batchEngineImportTask);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the batch engine import tasks in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportTasks the batch engine import tasks
	 */
	@Override
	public void cacheResult(
		List<BatchEngineImportTask> batchEngineImportTasks) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (batchEngineImportTasks.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BatchEngineImportTask batchEngineImportTask :
				batchEngineImportTasks) {

			if (entityCache.getResult(
					BatchEngineImportTaskImpl.class,
					batchEngineImportTask.getPrimaryKey()) == null) {

				cacheResult(batchEngineImportTask);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		BatchEngineImportTaskModelImpl batchEngineImportTaskModelImpl) {

		Object[] args = new Object[] {
			batchEngineImportTaskModelImpl.getExternalReferenceCode(),
			batchEngineImportTaskModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C, args, batchEngineImportTaskModelImpl);
	}

	/**
	 * Creates a new batch engine import task with the primary key. Does not add the batch engine import task to the database.
	 *
	 * @param batchEngineImportTaskId the primary key for the new batch engine import task
	 * @return the new batch engine import task
	 */
	@Override
	public BatchEngineImportTask create(long batchEngineImportTaskId) {
		BatchEngineImportTask batchEngineImportTask =
			new BatchEngineImportTaskImpl();

		batchEngineImportTask.setNew(true);
		batchEngineImportTask.setPrimaryKey(batchEngineImportTaskId);

		String uuid = PortalUUIDUtil.generate();

		batchEngineImportTask.setUuid(uuid);

		batchEngineImportTask.setCompanyId(CompanyThreadLocal.getCompanyId());

		return batchEngineImportTask;
	}

	/**
	 * Removes the batch engine import task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTaskId the primary key of the batch engine import task
	 * @return the batch engine import task that was removed
	 * @throws NoSuchImportTaskException if a batch engine import task with the primary key could not be found
	 */
	@Override
	public BatchEngineImportTask remove(long batchEngineImportTaskId)
		throws NoSuchImportTaskException {

		return remove((Serializable)batchEngineImportTaskId);
	}

	@Override
	protected BatchEngineImportTask removeImpl(
		BatchEngineImportTask batchEngineImportTask) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchEngineImportTask)) {
				batchEngineImportTask = (BatchEngineImportTask)session.get(
					BatchEngineImportTaskImpl.class,
					batchEngineImportTask.getPrimaryKeyObj());
			}

			if (batchEngineImportTask != null) {
				session.delete(batchEngineImportTask);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (batchEngineImportTask != null) {
			clearCache(batchEngineImportTask);
		}

		return batchEngineImportTask;
	}

	@Override
	public BatchEngineImportTask updateImpl(
		BatchEngineImportTask batchEngineImportTask) {

		boolean isNew = batchEngineImportTask.isNew();

		if (!(batchEngineImportTask instanceof
				BatchEngineImportTaskModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(batchEngineImportTask.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					batchEngineImportTask);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchEngineImportTask proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchEngineImportTask implementation " +
					batchEngineImportTask.getClass());
		}

		BatchEngineImportTaskModelImpl batchEngineImportTaskModelImpl =
			(BatchEngineImportTaskModelImpl)batchEngineImportTask;

		if (Validator.isNull(batchEngineImportTask.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			batchEngineImportTask.setUuid(uuid);
		}

		if (Validator.isNull(
				batchEngineImportTask.getExternalReferenceCode())) {

			batchEngineImportTask.setExternalReferenceCode(
				batchEngineImportTask.getUuid());
		}
		else {
			if (!Objects.equals(
					batchEngineImportTaskModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					batchEngineImportTask.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = batchEngineImportTask.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = batchEngineImportTask.getPrimaryKey();
					}

					try {
						batchEngineImportTask.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								BatchEngineImportTask.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								batchEngineImportTask.
									getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			BatchEngineImportTask ercBatchEngineImportTask = fetchByERC_C(
				batchEngineImportTask.getExternalReferenceCode(),
				batchEngineImportTask.getCompanyId());

			if (isNew) {
				if (ercBatchEngineImportTask != null) {
					throw new DuplicateBatchEngineImportTaskExternalReferenceCodeException(
						"Duplicate batch engine import task with external reference code " +
							batchEngineImportTask.getExternalReferenceCode() +
								" and company " +
									batchEngineImportTask.getCompanyId());
				}
			}
			else {
				if ((ercBatchEngineImportTask != null) &&
					(batchEngineImportTask.getBatchEngineImportTaskId() !=
						ercBatchEngineImportTask.
							getBatchEngineImportTaskId())) {

					throw new DuplicateBatchEngineImportTaskExternalReferenceCodeException(
						"Duplicate batch engine import task with external reference code " +
							batchEngineImportTask.getExternalReferenceCode() +
								" and company " +
									batchEngineImportTask.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (batchEngineImportTask.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchEngineImportTask.setCreateDate(date);
			}
			else {
				batchEngineImportTask.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!batchEngineImportTaskModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchEngineImportTask.setModifiedDate(date);
			}
			else {
				batchEngineImportTask.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(batchEngineImportTask);
			}
			else {
				session.evict(
					BatchEngineImportTaskImpl.class,
					batchEngineImportTask.getPrimaryKeyObj());

				session.saveOrUpdate(batchEngineImportTask);
			}

			session.flush();
			session.clear();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			BatchEngineImportTaskImpl.class, batchEngineImportTaskModelImpl,
			false, true);

		cacheUniqueFindersCache(batchEngineImportTaskModelImpl);

		if (isNew) {
			batchEngineImportTask.setNew(false);
		}

		batchEngineImportTask.resetOriginalValues();

		return batchEngineImportTask;
	}

	/**
	 * Returns the batch engine import task with the primary key or throws a <code>NoSuchImportTaskException</code> if it could not be found.
	 *
	 * @param batchEngineImportTaskId the primary key of the batch engine import task
	 * @return the batch engine import task
	 * @throws NoSuchImportTaskException if a batch engine import task with the primary key could not be found
	 */
	@Override
	public BatchEngineImportTask findByPrimaryKey(long batchEngineImportTaskId)
		throws NoSuchImportTaskException {

		return findByPrimaryKey((Serializable)batchEngineImportTaskId);
	}

	/**
	 * Returns the batch engine import task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineImportTaskId the primary key of the batch engine import task
	 * @return the batch engine import task, or <code>null</code> if a batch engine import task with the primary key could not be found
	 */
	@Override
	public BatchEngineImportTask fetchByPrimaryKey(
		long batchEngineImportTaskId) {

		return fetchByPrimaryKey((Serializable)batchEngineImportTaskId);
	}

	/**
	 * Returns all the batch engine import tasks.
	 *
	 * @return the batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine import tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @return the range of batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch engine import tasks
	 */
	@Override
	public List<BatchEngineImportTask> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator,
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

		List<BatchEngineImportTask> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineImportTask>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BATCHENGINEIMPORTTASK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHENGINEIMPORTTASK;

				sql = sql.concat(BatchEngineImportTaskModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BatchEngineImportTask>)QueryUtil.list(
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
	 * Removes all the batch engine import tasks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchEngineImportTask batchEngineImportTask : findAll()) {
			remove(batchEngineImportTask);
		}
	}

	/**
	 * Returns the number of batch engine import tasks.
	 *
	 * @return the number of batch engine import tasks
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
					_SQL_COUNT_BATCHENGINEIMPORTTASK);

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
		return "batchEngineImportTaskId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHENGINEIMPORTTASK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchEngineImportTaskModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch engine import task persistence.
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
			_SQL_SELECT_BATCHENGINEIMPORTTASK_WHERE,
			_SQL_COUNT_BATCHENGINEIMPORTTASK_WHERE,
			BatchEngineImportTaskModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"batchEngineImportTask.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, BatchEngineImportTask::getUuid));

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
				_SQL_SELECT_BATCHENGINEIMPORTTASK_WHERE,
				_SQL_COUNT_BATCHENGINEIMPORTTASK_WHERE,
				BatchEngineImportTaskModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"batchEngineImportTask.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, BatchEngineImportTask::getUuid),
				new FinderColumn<>(
					"batchEngineImportTask.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					BatchEngineImportTask::getCompanyId));

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
				_SQL_SELECT_BATCHENGINEIMPORTTASK_WHERE,
				_SQL_COUNT_BATCHENGINEIMPORTTASK_WHERE,
				BatchEngineImportTaskModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"batchEngineImportTask.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					BatchEngineImportTask::getCompanyId));

		_finderPathWithPaginationFindByExecuteStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByExecuteStatus",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"executeStatus"}, true);

		_finderPathWithoutPaginationFindByExecuteStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByExecuteStatus",
			new String[] {String.class.getName()},
			new String[] {"executeStatus"}, true);

		_finderPathCountByExecuteStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByExecuteStatus",
			new String[] {String.class.getName()},
			new String[] {"executeStatus"}, false);

		_collectionPersistenceFinderByExecuteStatus =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByExecuteStatus,
				_finderPathWithoutPaginationFindByExecuteStatus,
				_finderPathCountByExecuteStatus,
				_SQL_SELECT_BATCHENGINEIMPORTTASK_WHERE,
				_SQL_COUNT_BATCHENGINEIMPORTTASK_WHERE,
				BatchEngineImportTaskModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"batchEngineImportTask.", "executeStatus",
					FinderColumn.Type.STRING, "=", true, true,
					BatchEngineImportTask::getExecuteStatus));

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_C,
			_SQL_SELECT_BATCHENGINEIMPORTTASK_WHERE,
			new FinderColumn<>(
				"batchEngineImportTask.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				BatchEngineImportTask::getExternalReferenceCode),
			new FinderColumn<>(
				"batchEngineImportTask.", "companyId", FinderColumn.Type.LONG,
				"=", true, true, BatchEngineImportTask::getCompanyId));

		BatchEngineImportTaskUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		BatchEngineImportTaskUtil.setPersistence(null);

		entityCache.removeCache(BatchEngineImportTaskImpl.class.getName());
	}

	@Override
	@Reference(
		target = BatchEnginePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = BatchEnginePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BatchEnginePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_BATCHENGINEIMPORTTASK =
		"SELECT batchEngineImportTask FROM BatchEngineImportTask batchEngineImportTask";

	private static final String _SQL_SELECT_BATCHENGINEIMPORTTASK_WHERE =
		"SELECT batchEngineImportTask FROM BatchEngineImportTask batchEngineImportTask WHERE ";

	private static final String _SQL_COUNT_BATCHENGINEIMPORTTASK =
		"SELECT COUNT(batchEngineImportTask) FROM BatchEngineImportTask batchEngineImportTask";

	private static final String _SQL_COUNT_BATCHENGINEIMPORTTASK_WHERE =
		"SELECT COUNT(batchEngineImportTask) FROM BatchEngineImportTask batchEngineImportTask WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"batchEngineImportTask.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchEngineImportTask exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineImportTaskPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-202684190