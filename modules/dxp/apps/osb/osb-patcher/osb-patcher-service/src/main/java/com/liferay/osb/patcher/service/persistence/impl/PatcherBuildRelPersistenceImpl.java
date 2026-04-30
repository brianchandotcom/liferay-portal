/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.service.persistence.impl;

import com.liferay.osb.patcher.exception.NoSuchPatcherBuildRelException;
import com.liferay.osb.patcher.model.PatcherBuildRel;
import com.liferay.osb.patcher.model.PatcherBuildRelTable;
import com.liferay.osb.patcher.model.impl.PatcherBuildRelImpl;
import com.liferay.osb.patcher.model.impl.PatcherBuildRelModelImpl;
import com.liferay.osb.patcher.service.persistence.PatcherBuildRelPersistence;
import com.liferay.osb.patcher.service.persistence.PatcherBuildRelUtil;
import com.liferay.osb.patcher.service.persistence.impl.constants.OSBPatcherPersistenceConstants;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the patcher build rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = PatcherBuildRelPersistence.class)
public class PatcherBuildRelPersistenceImpl
	extends BasePersistenceImpl<PatcherBuildRel, NoSuchPatcherBuildRelException>
	implements PatcherBuildRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PatcherBuildRelUtil</code> to access the patcher build rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PatcherBuildRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByChildPatcherBuildId;
	private FinderPath _finderPathWithoutPaginationFindByChildPatcherBuildId;
	private FinderPath _finderPathCountByChildPatcherBuildId;
	private CollectionPersistenceFinder<PatcherBuildRel>
		_collectionPersistenceFinderByChildPatcherBuildId;

	/**
	 * Returns all the patcher build rels where childPatcherBuildId = &#63;.
	 *
	 * @param childPatcherBuildId the child patcher build ID
	 * @return the matching patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findByChildPatcherBuildId(
		long childPatcherBuildId) {

		return findByChildPatcherBuildId(
			childPatcherBuildId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher build rels where childPatcherBuildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherBuildRelModelImpl</code>.
	 * </p>
	 *
	 * @param childPatcherBuildId the child patcher build ID
	 * @param start the lower bound of the range of patcher build rels
	 * @param end the upper bound of the range of patcher build rels (not inclusive)
	 * @return the range of matching patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findByChildPatcherBuildId(
		long childPatcherBuildId, int start, int end) {

		return findByChildPatcherBuildId(childPatcherBuildId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher build rels where childPatcherBuildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherBuildRelModelImpl</code>.
	 * </p>
	 *
	 * @param childPatcherBuildId the child patcher build ID
	 * @param start the lower bound of the range of patcher build rels
	 * @param end the upper bound of the range of patcher build rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findByChildPatcherBuildId(
		long childPatcherBuildId, int start, int end,
		OrderByComparator<PatcherBuildRel> orderByComparator) {

		return findByChildPatcherBuildId(
			childPatcherBuildId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the patcher build rels where childPatcherBuildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherBuildRelModelImpl</code>.
	 * </p>
	 *
	 * @param childPatcherBuildId the child patcher build ID
	 * @param start the lower bound of the range of patcher build rels
	 * @param end the upper bound of the range of patcher build rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findByChildPatcherBuildId(
		long childPatcherBuildId, int start, int end,
		OrderByComparator<PatcherBuildRel> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByChildPatcherBuildId.find(
			finderCache, new Object[] {childPatcherBuildId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first patcher build rel in the ordered set where childPatcherBuildId = &#63;.
	 *
	 * @param childPatcherBuildId the child patcher build ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher build rel
	 * @throws NoSuchPatcherBuildRelException if a matching patcher build rel could not be found
	 */
	@Override
	public PatcherBuildRel findByChildPatcherBuildId_First(
			long childPatcherBuildId,
			OrderByComparator<PatcherBuildRel> orderByComparator)
		throws NoSuchPatcherBuildRelException {

		PatcherBuildRel patcherBuildRel = fetchByChildPatcherBuildId_First(
			childPatcherBuildId, orderByComparator);

		if (patcherBuildRel != null) {
			return patcherBuildRel;
		}

		throw new NoSuchPatcherBuildRelException(
			_collectionPersistenceFinderByChildPatcherBuildId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {childPatcherBuildId}));
	}

	/**
	 * Returns the first patcher build rel in the ordered set where childPatcherBuildId = &#63;.
	 *
	 * @param childPatcherBuildId the child patcher build ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher build rel, or <code>null</code> if a matching patcher build rel could not be found
	 */
	@Override
	public PatcherBuildRel fetchByChildPatcherBuildId_First(
		long childPatcherBuildId,
		OrderByComparator<PatcherBuildRel> orderByComparator) {

		return _collectionPersistenceFinderByChildPatcherBuildId.fetchFirst(
			finderCache, new Object[] {childPatcherBuildId}, orderByComparator);
	}

	/**
	 * Removes all the patcher build rels where childPatcherBuildId = &#63; from the database.
	 *
	 * @param childPatcherBuildId the child patcher build ID
	 */
	@Override
	public void removeByChildPatcherBuildId(long childPatcherBuildId) {
		_collectionPersistenceFinderByChildPatcherBuildId.remove(
			finderCache, new Object[] {childPatcherBuildId});
	}

	/**
	 * Returns the number of patcher build rels where childPatcherBuildId = &#63;.
	 *
	 * @param childPatcherBuildId the child patcher build ID
	 * @return the number of matching patcher build rels
	 */
	@Override
	public int countByChildPatcherBuildId(long childPatcherBuildId) {
		return _collectionPersistenceFinderByChildPatcherBuildId.count(
			finderCache, new Object[] {childPatcherBuildId});
	}

	private FinderPath _finderPathWithPaginationFindByParentPatcherBuildId;
	private FinderPath _finderPathWithoutPaginationFindByParentPatcherBuildId;
	private FinderPath _finderPathCountByParentPatcherBuildId;
	private CollectionPersistenceFinder<PatcherBuildRel>
		_collectionPersistenceFinderByParentPatcherBuildId;

	/**
	 * Returns all the patcher build rels where parentPatcherBuildId = &#63;.
	 *
	 * @param parentPatcherBuildId the parent patcher build ID
	 * @return the matching patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findByParentPatcherBuildId(
		long parentPatcherBuildId) {

		return findByParentPatcherBuildId(
			parentPatcherBuildId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher build rels where parentPatcherBuildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherBuildRelModelImpl</code>.
	 * </p>
	 *
	 * @param parentPatcherBuildId the parent patcher build ID
	 * @param start the lower bound of the range of patcher build rels
	 * @param end the upper bound of the range of patcher build rels (not inclusive)
	 * @return the range of matching patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findByParentPatcherBuildId(
		long parentPatcherBuildId, int start, int end) {

		return findByParentPatcherBuildId(
			parentPatcherBuildId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher build rels where parentPatcherBuildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherBuildRelModelImpl</code>.
	 * </p>
	 *
	 * @param parentPatcherBuildId the parent patcher build ID
	 * @param start the lower bound of the range of patcher build rels
	 * @param end the upper bound of the range of patcher build rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findByParentPatcherBuildId(
		long parentPatcherBuildId, int start, int end,
		OrderByComparator<PatcherBuildRel> orderByComparator) {

		return findByParentPatcherBuildId(
			parentPatcherBuildId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the patcher build rels where parentPatcherBuildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherBuildRelModelImpl</code>.
	 * </p>
	 *
	 * @param parentPatcherBuildId the parent patcher build ID
	 * @param start the lower bound of the range of patcher build rels
	 * @param end the upper bound of the range of patcher build rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findByParentPatcherBuildId(
		long parentPatcherBuildId, int start, int end,
		OrderByComparator<PatcherBuildRel> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByParentPatcherBuildId.find(
			finderCache, new Object[] {parentPatcherBuildId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first patcher build rel in the ordered set where parentPatcherBuildId = &#63;.
	 *
	 * @param parentPatcherBuildId the parent patcher build ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher build rel
	 * @throws NoSuchPatcherBuildRelException if a matching patcher build rel could not be found
	 */
	@Override
	public PatcherBuildRel findByParentPatcherBuildId_First(
			long parentPatcherBuildId,
			OrderByComparator<PatcherBuildRel> orderByComparator)
		throws NoSuchPatcherBuildRelException {

		PatcherBuildRel patcherBuildRel = fetchByParentPatcherBuildId_First(
			parentPatcherBuildId, orderByComparator);

		if (patcherBuildRel != null) {
			return patcherBuildRel;
		}

		throw new NoSuchPatcherBuildRelException(
			_collectionPersistenceFinderByParentPatcherBuildId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {parentPatcherBuildId}));
	}

	/**
	 * Returns the first patcher build rel in the ordered set where parentPatcherBuildId = &#63;.
	 *
	 * @param parentPatcherBuildId the parent patcher build ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching patcher build rel, or <code>null</code> if a matching patcher build rel could not be found
	 */
	@Override
	public PatcherBuildRel fetchByParentPatcherBuildId_First(
		long parentPatcherBuildId,
		OrderByComparator<PatcherBuildRel> orderByComparator) {

		return _collectionPersistenceFinderByParentPatcherBuildId.fetchFirst(
			finderCache, new Object[] {parentPatcherBuildId},
			orderByComparator);
	}

	/**
	 * Removes all the patcher build rels where parentPatcherBuildId = &#63; from the database.
	 *
	 * @param parentPatcherBuildId the parent patcher build ID
	 */
	@Override
	public void removeByParentPatcherBuildId(long parentPatcherBuildId) {
		_collectionPersistenceFinderByParentPatcherBuildId.remove(
			finderCache, new Object[] {parentPatcherBuildId});
	}

	/**
	 * Returns the number of patcher build rels where parentPatcherBuildId = &#63;.
	 *
	 * @param parentPatcherBuildId the parent patcher build ID
	 * @return the number of matching patcher build rels
	 */
	@Override
	public int countByParentPatcherBuildId(long parentPatcherBuildId) {
		return _collectionPersistenceFinderByParentPatcherBuildId.count(
			finderCache, new Object[] {parentPatcherBuildId});
	}

	public PatcherBuildRelPersistenceImpl() {
		setModelClass(PatcherBuildRel.class);

		setModelImplClass(PatcherBuildRelImpl.class);
		setModelPKClass(long.class);

		setTable(PatcherBuildRelTable.INSTANCE);
	}

	/**
	 * Caches the patcher build rel in the entity cache if it is enabled.
	 *
	 * @param patcherBuildRel the patcher build rel
	 */
	@Override
	public void cacheResult(PatcherBuildRel patcherBuildRel) {
		entityCache.putResult(
			PatcherBuildRelImpl.class, patcherBuildRel.getPrimaryKey(),
			patcherBuildRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the patcher build rels in the entity cache if it is enabled.
	 *
	 * @param patcherBuildRels the patcher build rels
	 */
	@Override
	public void cacheResult(List<PatcherBuildRel> patcherBuildRels) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (patcherBuildRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (PatcherBuildRel patcherBuildRel : patcherBuildRels) {
			if (entityCache.getResult(
					PatcherBuildRelImpl.class,
					patcherBuildRel.getPrimaryKey()) == null) {

				cacheResult(patcherBuildRel);
			}
		}
	}

	/**
	 * Creates a new patcher build rel with the primary key. Does not add the patcher build rel to the database.
	 *
	 * @param patcherBuildRelId the primary key for the new patcher build rel
	 * @return the new patcher build rel
	 */
	@Override
	public PatcherBuildRel create(long patcherBuildRelId) {
		PatcherBuildRel patcherBuildRel = new PatcherBuildRelImpl();

		patcherBuildRel.setNew(true);
		patcherBuildRel.setPrimaryKey(patcherBuildRelId);

		patcherBuildRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return patcherBuildRel;
	}

	/**
	 * Removes the patcher build rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param patcherBuildRelId the primary key of the patcher build rel
	 * @return the patcher build rel that was removed
	 * @throws NoSuchPatcherBuildRelException if a patcher build rel with the primary key could not be found
	 */
	@Override
	public PatcherBuildRel remove(long patcherBuildRelId)
		throws NoSuchPatcherBuildRelException {

		return remove((Serializable)patcherBuildRelId);
	}

	@Override
	protected PatcherBuildRel removeImpl(PatcherBuildRel patcherBuildRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(patcherBuildRel)) {
				patcherBuildRel = (PatcherBuildRel)session.get(
					PatcherBuildRelImpl.class,
					patcherBuildRel.getPrimaryKeyObj());
			}

			if (patcherBuildRel != null) {
				session.delete(patcherBuildRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (patcherBuildRel != null) {
			clearCache(patcherBuildRel);
		}

		return patcherBuildRel;
	}

	@Override
	public PatcherBuildRel updateImpl(PatcherBuildRel patcherBuildRel) {
		boolean isNew = patcherBuildRel.isNew();

		if (!(patcherBuildRel instanceof PatcherBuildRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(patcherBuildRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					patcherBuildRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in patcherBuildRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PatcherBuildRel implementation " +
					patcherBuildRel.getClass());
		}

		PatcherBuildRelModelImpl patcherBuildRelModelImpl =
			(PatcherBuildRelModelImpl)patcherBuildRel;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(patcherBuildRel);
			}
			else {
				patcherBuildRel = (PatcherBuildRel)session.merge(
					patcherBuildRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			PatcherBuildRelImpl.class, patcherBuildRelModelImpl, false, true);

		if (isNew) {
			patcherBuildRel.setNew(false);
		}

		patcherBuildRel.resetOriginalValues();

		return patcherBuildRel;
	}

	/**
	 * Returns the patcher build rel with the primary key or throws a <code>NoSuchPatcherBuildRelException</code> if it could not be found.
	 *
	 * @param patcherBuildRelId the primary key of the patcher build rel
	 * @return the patcher build rel
	 * @throws NoSuchPatcherBuildRelException if a patcher build rel with the primary key could not be found
	 */
	@Override
	public PatcherBuildRel findByPrimaryKey(long patcherBuildRelId)
		throws NoSuchPatcherBuildRelException {

		return findByPrimaryKey((Serializable)patcherBuildRelId);
	}

	/**
	 * Returns the patcher build rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param patcherBuildRelId the primary key of the patcher build rel
	 * @return the patcher build rel, or <code>null</code> if a patcher build rel with the primary key could not be found
	 */
	@Override
	public PatcherBuildRel fetchByPrimaryKey(long patcherBuildRelId) {
		return fetchByPrimaryKey((Serializable)patcherBuildRelId);
	}

	/**
	 * Returns all the patcher build rels.
	 *
	 * @return the patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the patcher build rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherBuildRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of patcher build rels
	 * @param end the upper bound of the range of patcher build rels (not inclusive)
	 * @return the range of patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the patcher build rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherBuildRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of patcher build rels
	 * @param end the upper bound of the range of patcher build rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findAll(
		int start, int end,
		OrderByComparator<PatcherBuildRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the patcher build rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PatcherBuildRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of patcher build rels
	 * @param end the upper bound of the range of patcher build rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of patcher build rels
	 */
	@Override
	public List<PatcherBuildRel> findAll(
		int start, int end,
		OrderByComparator<PatcherBuildRel> orderByComparator,
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

		List<PatcherBuildRel> list = null;

		if (useFinderCache) {
			list = (List<PatcherBuildRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PATCHERBUILDREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PATCHERBUILDREL;

				sql = sql.concat(PatcherBuildRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PatcherBuildRel>)QueryUtil.list(
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
	 * Removes all the patcher build rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PatcherBuildRel patcherBuildRel : findAll()) {
			remove(patcherBuildRel);
		}
	}

	/**
	 * Returns the number of patcher build rels.
	 *
	 * @return the number of patcher build rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_PATCHERBUILDREL);

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
		return "patcherBuildRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PATCHERBUILDREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PatcherBuildRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the patcher build rel persistence.
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

		_finderPathWithPaginationFindByChildPatcherBuildId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByChildPatcherBuildId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"childPatcherBuildId"}, true);

		_finderPathWithoutPaginationFindByChildPatcherBuildId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByChildPatcherBuildId", new String[] {Long.class.getName()},
			new String[] {"childPatcherBuildId"}, true);

		_finderPathCountByChildPatcherBuildId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByChildPatcherBuildId", new String[] {Long.class.getName()},
			new String[] {"childPatcherBuildId"}, false);

		_collectionPersistenceFinderByChildPatcherBuildId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByChildPatcherBuildId,
				_finderPathWithoutPaginationFindByChildPatcherBuildId,
				_finderPathCountByChildPatcherBuildId,
				_SQL_SELECT_PATCHERBUILDREL_WHERE,
				_SQL_COUNT_PATCHERBUILDREL_WHERE,
				PatcherBuildRelModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"patcherBuildRel.", "childPatcherBuildId",
					FinderColumn.Type.LONG, "=", true, true,
					PatcherBuildRel::getChildPatcherBuildId));

		_finderPathWithPaginationFindByParentPatcherBuildId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByParentPatcherBuildId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"parentPatcherBuildId"}, true);

		_finderPathWithoutPaginationFindByParentPatcherBuildId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByParentPatcherBuildId", new String[] {Long.class.getName()},
			new String[] {"parentPatcherBuildId"}, true);

		_finderPathCountByParentPatcherBuildId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByParentPatcherBuildId", new String[] {Long.class.getName()},
			new String[] {"parentPatcherBuildId"}, false);

		_collectionPersistenceFinderByParentPatcherBuildId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByParentPatcherBuildId,
				_finderPathWithoutPaginationFindByParentPatcherBuildId,
				_finderPathCountByParentPatcherBuildId,
				_SQL_SELECT_PATCHERBUILDREL_WHERE,
				_SQL_COUNT_PATCHERBUILDREL_WHERE,
				PatcherBuildRelModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"patcherBuildRel.", "parentPatcherBuildId",
					FinderColumn.Type.LONG, "=", true, true,
					PatcherBuildRel::getParentPatcherBuildId));

		PatcherBuildRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		PatcherBuildRelUtil.setPersistence(null);

		entityCache.removeCache(PatcherBuildRelImpl.class.getName());
	}

	@Override
	@Reference(
		target = OSBPatcherPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OSBPatcherPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OSBPatcherPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_PATCHERBUILDREL =
		"SELECT patcherBuildRel FROM PatcherBuildRel patcherBuildRel";

	private static final String _SQL_SELECT_PATCHERBUILDREL_WHERE =
		"SELECT patcherBuildRel FROM PatcherBuildRel patcherBuildRel WHERE ";

	private static final String _SQL_COUNT_PATCHERBUILDREL =
		"SELECT COUNT(patcherBuildRel) FROM PatcherBuildRel patcherBuildRel";

	private static final String _SQL_COUNT_PATCHERBUILDREL_WHERE =
		"SELECT COUNT(patcherBuildRel) FROM PatcherBuildRel patcherBuildRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "patcherBuildRel.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PatcherBuildRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PatcherBuildRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-555481649