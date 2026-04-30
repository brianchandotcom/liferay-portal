/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.service.persistence.impl;

import com.liferay.batch.planner.exception.NoSuchMappingException;
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerMappingTable;
import com.liferay.batch.planner.model.impl.BatchPlannerMappingImpl;
import com.liferay.batch.planner.model.impl.BatchPlannerMappingModelImpl;
import com.liferay.batch.planner.service.persistence.BatchPlannerMappingPersistence;
import com.liferay.batch.planner.service.persistence.BatchPlannerMappingUtil;
import com.liferay.batch.planner.service.persistence.impl.constants.BatchPlannerPersistenceConstants;
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

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the batch planner mapping service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @generated
 */
@Component(service = BatchPlannerMappingPersistence.class)
public class BatchPlannerMappingPersistenceImpl
	extends BasePersistenceImpl<BatchPlannerMapping, NoSuchMappingException>
	implements BatchPlannerMappingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchPlannerMappingUtil</code> to access the batch planner mapping persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchPlannerMappingImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByBatchPlannerPlanId;
	private FinderPath _finderPathWithoutPaginationFindByBatchPlannerPlanId;
	private FinderPath _finderPathCountByBatchPlannerPlanId;
	private CollectionPersistenceFinder<BatchPlannerMapping>
		_collectionPersistenceFinderByBatchPlannerPlanId;

	/**
	 * Returns all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId) {

		return findByBatchPlannerPlanId(
			batchPlannerPlanId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @return the range of matching batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end) {

		return findByBatchPlannerPlanId(batchPlannerPlanId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		return findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByBatchPlannerPlanId.find(
			finderCache, new Object[] {batchPlannerPlanId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner mapping
	 * @throws NoSuchMappingException if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping findByBatchPlannerPlanId_First(
			long batchPlannerPlanId,
			OrderByComparator<BatchPlannerMapping> orderByComparator)
		throws NoSuchMappingException {

		BatchPlannerMapping batchPlannerMapping =
			fetchByBatchPlannerPlanId_First(
				batchPlannerPlanId, orderByComparator);

		if (batchPlannerMapping != null) {
			return batchPlannerMapping;
		}

		throw new NoSuchMappingException(
			_collectionPersistenceFinderByBatchPlannerPlanId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {batchPlannerPlanId}));
	}

	/**
	 * Returns the first batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping fetchByBatchPlannerPlanId_First(
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		return _collectionPersistenceFinderByBatchPlannerPlanId.fetchFirst(
			finderCache, new Object[] {batchPlannerPlanId}, orderByComparator);
	}

	/**
	 * Removes all the batch planner mappings where batchPlannerPlanId = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 */
	@Override
	public void removeByBatchPlannerPlanId(long batchPlannerPlanId) {
		_collectionPersistenceFinderByBatchPlannerPlanId.remove(
			finderCache, new Object[] {batchPlannerPlanId});
	}

	/**
	 * Returns the number of batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the number of matching batch planner mappings
	 */
	@Override
	public int countByBatchPlannerPlanId(long batchPlannerPlanId) {
		return _collectionPersistenceFinderByBatchPlannerPlanId.count(
			finderCache, new Object[] {batchPlannerPlanId});
	}

	private FinderPath _finderPathFetchByBPPI_EFN_IFN;
	private UniquePersistenceFinder<BatchPlannerMapping>
		_uniquePersistenceFinderByBPPI_EFN_IFN;

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or throws a <code>NoSuchMappingException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the matching batch planner mapping
	 * @throws NoSuchMappingException if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping findByBPPI_EFN_IFN(
			long batchPlannerPlanId, String externalFieldName,
			String internalFieldName)
		throws NoSuchMappingException {

		BatchPlannerMapping batchPlannerMapping = fetchByBPPI_EFN_IFN(
			batchPlannerPlanId, externalFieldName, internalFieldName);

		if (batchPlannerMapping == null) {
			String message =
				_uniquePersistenceFinderByBPPI_EFN_IFN.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						batchPlannerPlanId, externalFieldName, internalFieldName
					});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchMappingException(message);
		}

		return batchPlannerMapping;
	}

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping fetchByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName) {

		return fetchByBPPI_EFN_IFN(
			batchPlannerPlanId, externalFieldName, internalFieldName, true);
	}

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping fetchByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName, boolean useFinderCache) {

		return _uniquePersistenceFinderByBPPI_EFN_IFN.fetch(
			finderCache,
			new Object[] {
				batchPlannerPlanId, externalFieldName, internalFieldName
			},
			useFinderCache);
	}

	/**
	 * Removes the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the batch planner mapping that was removed
	 */
	@Override
	public BatchPlannerMapping removeByBPPI_EFN_IFN(
			long batchPlannerPlanId, String externalFieldName,
			String internalFieldName)
		throws NoSuchMappingException {

		BatchPlannerMapping batchPlannerMapping = findByBPPI_EFN_IFN(
			batchPlannerPlanId, externalFieldName, internalFieldName);

		return remove(batchPlannerMapping);
	}

	/**
	 * Returns the number of batch planner mappings where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the number of matching batch planner mappings
	 */
	@Override
	public int countByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName) {

		return _uniquePersistenceFinderByBPPI_EFN_IFN.count(
			finderCache,
			new Object[] {
				batchPlannerPlanId, externalFieldName, internalFieldName
			});
	}

	public BatchPlannerMappingPersistenceImpl() {
		setModelClass(BatchPlannerMapping.class);

		setModelImplClass(BatchPlannerMappingImpl.class);
		setModelPKClass(long.class);

		setTable(BatchPlannerMappingTable.INSTANCE);
	}

	/**
	 * Caches the batch planner mapping in the entity cache if it is enabled.
	 *
	 * @param batchPlannerMapping the batch planner mapping
	 */
	@Override
	public void cacheResult(BatchPlannerMapping batchPlannerMapping) {
		entityCache.putResult(
			BatchPlannerMappingImpl.class, batchPlannerMapping.getPrimaryKey(),
			batchPlannerMapping);

		finderCache.putResult(
			_finderPathFetchByBPPI_EFN_IFN,
			new Object[] {
				batchPlannerMapping.getBatchPlannerPlanId(),
				batchPlannerMapping.getExternalFieldName(),
				batchPlannerMapping.getInternalFieldName()
			},
			batchPlannerMapping);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the batch planner mappings in the entity cache if it is enabled.
	 *
	 * @param batchPlannerMappings the batch planner mappings
	 */
	@Override
	public void cacheResult(List<BatchPlannerMapping> batchPlannerMappings) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (batchPlannerMappings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			if (entityCache.getResult(
					BatchPlannerMappingImpl.class,
					batchPlannerMapping.getPrimaryKey()) == null) {

				cacheResult(batchPlannerMapping);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		BatchPlannerMappingModelImpl batchPlannerMappingModelImpl) {

		Object[] args = new Object[] {
			batchPlannerMappingModelImpl.getBatchPlannerPlanId(),
			batchPlannerMappingModelImpl.getExternalFieldName(),
			batchPlannerMappingModelImpl.getInternalFieldName()
		};

		finderCache.putResult(
			_finderPathFetchByBPPI_EFN_IFN, args, batchPlannerMappingModelImpl);
	}

	/**
	 * Creates a new batch planner mapping with the primary key. Does not add the batch planner mapping to the database.
	 *
	 * @param batchPlannerMappingId the primary key for the new batch planner mapping
	 * @return the new batch planner mapping
	 */
	@Override
	public BatchPlannerMapping create(long batchPlannerMappingId) {
		BatchPlannerMapping batchPlannerMapping = new BatchPlannerMappingImpl();

		batchPlannerMapping.setNew(true);
		batchPlannerMapping.setPrimaryKey(batchPlannerMappingId);

		batchPlannerMapping.setCompanyId(CompanyThreadLocal.getCompanyId());

		return batchPlannerMapping;
	}

	/**
	 * Removes the batch planner mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping that was removed
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	@Override
	public BatchPlannerMapping remove(long batchPlannerMappingId)
		throws NoSuchMappingException {

		return remove((Serializable)batchPlannerMappingId);
	}

	@Override
	protected BatchPlannerMapping removeImpl(
		BatchPlannerMapping batchPlannerMapping) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchPlannerMapping)) {
				batchPlannerMapping = (BatchPlannerMapping)session.get(
					BatchPlannerMappingImpl.class,
					batchPlannerMapping.getPrimaryKeyObj());
			}

			if (batchPlannerMapping != null) {
				session.delete(batchPlannerMapping);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (batchPlannerMapping != null) {
			clearCache(batchPlannerMapping);
		}

		return batchPlannerMapping;
	}

	@Override
	public BatchPlannerMapping updateImpl(
		BatchPlannerMapping batchPlannerMapping) {

		boolean isNew = batchPlannerMapping.isNew();

		if (!(batchPlannerMapping instanceof BatchPlannerMappingModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(batchPlannerMapping.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					batchPlannerMapping);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchPlannerMapping proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchPlannerMapping implementation " +
					batchPlannerMapping.getClass());
		}

		BatchPlannerMappingModelImpl batchPlannerMappingModelImpl =
			(BatchPlannerMappingModelImpl)batchPlannerMapping;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (batchPlannerMapping.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchPlannerMapping.setCreateDate(date);
			}
			else {
				batchPlannerMapping.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!batchPlannerMappingModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchPlannerMapping.setModifiedDate(date);
			}
			else {
				batchPlannerMapping.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(batchPlannerMapping);
			}
			else {
				batchPlannerMapping = (BatchPlannerMapping)session.merge(
					batchPlannerMapping);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			BatchPlannerMappingImpl.class, batchPlannerMappingModelImpl, false,
			true);

		cacheUniqueFindersCache(batchPlannerMappingModelImpl);

		if (isNew) {
			batchPlannerMapping.setNew(false);
		}

		batchPlannerMapping.resetOriginalValues();

		return batchPlannerMapping;
	}

	/**
	 * Returns the batch planner mapping with the primary key or throws a <code>NoSuchMappingException</code> if it could not be found.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	@Override
	public BatchPlannerMapping findByPrimaryKey(long batchPlannerMappingId)
		throws NoSuchMappingException {

		return findByPrimaryKey((Serializable)batchPlannerMappingId);
	}

	/**
	 * Returns the batch planner mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping, or <code>null</code> if a batch planner mapping with the primary key could not be found
	 */
	@Override
	public BatchPlannerMapping fetchByPrimaryKey(long batchPlannerMappingId) {
		return fetchByPrimaryKey((Serializable)batchPlannerMappingId);
	}

	/**
	 * Returns all the batch planner mappings.
	 *
	 * @return the batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch planner mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @return the range of batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator,
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

		List<BatchPlannerMapping> list = null;

		if (useFinderCache) {
			list = (List<BatchPlannerMapping>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BATCHPLANNERMAPPING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHPLANNERMAPPING;

				sql = sql.concat(BatchPlannerMappingModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BatchPlannerMapping>)QueryUtil.list(
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
	 * Removes all the batch planner mappings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchPlannerMapping batchPlannerMapping : findAll()) {
			remove(batchPlannerMapping);
		}
	}

	/**
	 * Returns the number of batch planner mappings.
	 *
	 * @return the number of batch planner mappings
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
					_SQL_COUNT_BATCHPLANNERMAPPING);

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
		return "batchPlannerMappingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHPLANNERMAPPING;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchPlannerMappingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch planner mapping persistence.
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

		_finderPathWithPaginationFindByBatchPlannerPlanId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByBatchPlannerPlanId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"batchPlannerPlanId"}, true);

		_finderPathWithoutPaginationFindByBatchPlannerPlanId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByBatchPlannerPlanId", new String[] {Long.class.getName()},
			new String[] {"batchPlannerPlanId"}, true);

		_finderPathCountByBatchPlannerPlanId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByBatchPlannerPlanId", new String[] {Long.class.getName()},
			new String[] {"batchPlannerPlanId"}, false);

		_collectionPersistenceFinderByBatchPlannerPlanId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByBatchPlannerPlanId,
				_finderPathWithoutPaginationFindByBatchPlannerPlanId,
				_finderPathCountByBatchPlannerPlanId,
				_SQL_SELECT_BATCHPLANNERMAPPING_WHERE,
				_SQL_COUNT_BATCHPLANNERMAPPING_WHERE,
				BatchPlannerMappingModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"batchPlannerMapping.", "batchPlannerPlanId",
					FinderColumn.Type.LONG, "=", true, true,
					BatchPlannerMapping::getBatchPlannerPlanId));

		_finderPathFetchByBPPI_EFN_IFN = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByBPPI_EFN_IFN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"batchPlannerPlanId", "externalFieldName", "internalFieldName"
			},
			true);

		_uniquePersistenceFinderByBPPI_EFN_IFN = new UniquePersistenceFinder<>(
			this, _finderPathFetchByBPPI_EFN_IFN,
			_SQL_SELECT_BATCHPLANNERMAPPING_WHERE,
			new FinderColumn<>(
				"batchPlannerMapping.", "batchPlannerPlanId",
				FinderColumn.Type.LONG, "=", true, false,
				BatchPlannerMapping::getBatchPlannerPlanId),
			new FinderColumn<>(
				"batchPlannerMapping.", "externalFieldName",
				FinderColumn.Type.STRING, "=", true, false,
				BatchPlannerMapping::getExternalFieldName),
			new FinderColumn<>(
				"batchPlannerMapping.", "internalFieldName",
				FinderColumn.Type.STRING, "=", true, true,
				BatchPlannerMapping::getInternalFieldName));

		BatchPlannerMappingUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		BatchPlannerMappingUtil.setPersistence(null);

		entityCache.removeCache(BatchPlannerMappingImpl.class.getName());
	}

	@Override
	@Reference(
		target = BatchPlannerPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = BatchPlannerPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BatchPlannerPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_BATCHPLANNERMAPPING =
		"SELECT batchPlannerMapping FROM BatchPlannerMapping batchPlannerMapping";

	private static final String _SQL_SELECT_BATCHPLANNERMAPPING_WHERE =
		"SELECT batchPlannerMapping FROM BatchPlannerMapping batchPlannerMapping WHERE ";

	private static final String _SQL_COUNT_BATCHPLANNERMAPPING =
		"SELECT COUNT(batchPlannerMapping) FROM BatchPlannerMapping batchPlannerMapping";

	private static final String _SQL_COUNT_BATCHPLANNERMAPPING_WHERE =
		"SELECT COUNT(batchPlannerMapping) FROM BatchPlannerMapping batchPlannerMapping WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "batchPlannerMapping.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchPlannerMapping exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchPlannerMappingPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:380646583