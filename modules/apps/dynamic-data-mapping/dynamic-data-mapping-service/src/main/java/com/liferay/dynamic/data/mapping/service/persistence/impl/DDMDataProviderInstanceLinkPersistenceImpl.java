/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceLinkException;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLinkTable;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderInstanceLinkPersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderInstanceLinkUtil;
import com.liferay.dynamic.data.mapping.service.persistence.impl.constants.DDMPersistenceConstants;
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

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ddm data provider instance link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DDMDataProviderInstanceLinkPersistence.class)
public class DDMDataProviderInstanceLinkPersistenceImpl
	extends BasePersistenceImpl
		<DDMDataProviderInstanceLink, NoSuchDataProviderInstanceLinkException>
	implements DDMDataProviderInstanceLinkPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DDMDataProviderInstanceLinkUtil</code> to access the ddm data provider instance link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DDMDataProviderInstanceLinkImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByDataProviderInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByDataProviderInstanceId;
	private FinderPath _finderPathCountByDataProviderInstanceId;
	private CollectionPersistenceFinder<DDMDataProviderInstanceLink>
		_collectionPersistenceFinderByDataProviderInstanceId;

	/**
	 * Returns all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @return the matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId) {

		return findByDataProviderInstanceId(
			dataProviderInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @return the range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId, int start, int end) {

		return findByDataProviderInstanceId(
			dataProviderInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId, int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return findByDataProviderInstanceId(
			dataProviderInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId, int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDMDataProviderInstanceLink.class)) {

			return _collectionPersistenceFinderByDataProviderInstanceId.find(
				finderCache, new Object[] {dataProviderInstanceId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink findByDataProviderInstanceId_First(
			long dataProviderInstanceId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			fetchByDataProviderInstanceId_First(
				dataProviderInstanceId, orderByComparator);

		if (ddmDataProviderInstanceLink != null) {
			return ddmDataProviderInstanceLink;
		}

		throw new NoSuchDataProviderInstanceLinkException(
			_collectionPersistenceFinderByDataProviderInstanceId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {dataProviderInstanceId}));
	}

	/**
	 * Returns the first ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByDataProviderInstanceId_First(
		long dataProviderInstanceId,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return _collectionPersistenceFinderByDataProviderInstanceId.fetchFirst(
			finderCache, new Object[] {dataProviderInstanceId},
			orderByComparator);
	}

	/**
	 * Removes all the ddm data provider instance links where dataProviderInstanceId = &#63; from the database.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 */
	@Override
	public void removeByDataProviderInstanceId(long dataProviderInstanceId) {
		_collectionPersistenceFinderByDataProviderInstanceId.remove(
			finderCache, new Object[] {dataProviderInstanceId});
	}

	/**
	 * Returns the number of ddm data provider instance links where dataProviderInstanceId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @return the number of matching ddm data provider instance links
	 */
	@Override
	public int countByDataProviderInstanceId(long dataProviderInstanceId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDMDataProviderInstanceLink.class)) {

			return _collectionPersistenceFinderByDataProviderInstanceId.count(
				finderCache, new Object[] {dataProviderInstanceId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByStructureId;
	private FinderPath _finderPathWithoutPaginationFindByStructureId;
	private FinderPath _finderPathCountByStructureId;
	private CollectionPersistenceFinder<DDMDataProviderInstanceLink>
		_collectionPersistenceFinderByStructureId;

	/**
	 * Returns all the ddm data provider instance links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId) {

		return findByStructureId(
			structureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm data provider instance links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @return the range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end) {

		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return findByStructureId(
			structureId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDMDataProviderInstanceLink.class)) {

			return _collectionPersistenceFinderByStructureId.find(
				finderCache, new Object[] {structureId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink findByStructureId_First(
			long structureId,
			OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			fetchByStructureId_First(structureId, orderByComparator);

		if (ddmDataProviderInstanceLink != null) {
			return ddmDataProviderInstanceLink;
		}

		throw new NoSuchDataProviderInstanceLinkException(
			_collectionPersistenceFinderByStructureId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {structureId}));
	}

	/**
	 * Returns the first ddm data provider instance link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByStructureId_First(
		long structureId,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return _collectionPersistenceFinderByStructureId.fetchFirst(
			finderCache, new Object[] {structureId}, orderByComparator);
	}

	/**
	 * Removes all the ddm data provider instance links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 */
	@Override
	public void removeByStructureId(long structureId) {
		_collectionPersistenceFinderByStructureId.remove(
			finderCache, new Object[] {structureId});
	}

	/**
	 * Returns the number of ddm data provider instance links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching ddm data provider instance links
	 */
	@Override
	public int countByStructureId(long structureId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDMDataProviderInstanceLink.class)) {

			return _collectionPersistenceFinderByStructureId.count(
				finderCache, new Object[] {structureId});
		}
	}

	private FinderPath _finderPathFetchByD_S;
	private UniquePersistenceFinder<DDMDataProviderInstanceLink>
		_uniquePersistenceFinderByD_S;

	/**
	 * Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or throws a <code>NoSuchDataProviderInstanceLinkException</code> if it could not be found.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the matching ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink findByD_S(
			long dataProviderInstanceId, long structureId)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink = fetchByD_S(
			dataProviderInstanceId, structureId);

		if (ddmDataProviderInstanceLink == null) {
			String message =
				_uniquePersistenceFinderByD_S.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {dataProviderInstanceId, structureId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchDataProviderInstanceLinkException(message);
		}

		return ddmDataProviderInstanceLink;
	}

	/**
	 * Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByD_S(
		long dataProviderInstanceId, long structureId) {

		return fetchByD_S(dataProviderInstanceId, structureId, true);
	}

	/**
	 * Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByD_S(
		long dataProviderInstanceId, long structureId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDMDataProviderInstanceLink.class)) {

			return _uniquePersistenceFinderByD_S.fetch(
				finderCache, new Object[] {dataProviderInstanceId, structureId},
				useFinderCache);
		}
	}

	/**
	 * Removes the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; from the database.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the ddm data provider instance link that was removed
	 */
	@Override
	public DDMDataProviderInstanceLink removeByD_S(
			long dataProviderInstanceId, long structureId)
		throws NoSuchDataProviderInstanceLinkException {

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink = findByD_S(
			dataProviderInstanceId, structureId);

		return remove(ddmDataProviderInstanceLink);
	}

	/**
	 * Returns the number of ddm data provider instance links where dataProviderInstanceId = &#63; and structureId = &#63;.
	 *
	 * @param dataProviderInstanceId the data provider instance ID
	 * @param structureId the structure ID
	 * @return the number of matching ddm data provider instance links
	 */
	@Override
	public int countByD_S(long dataProviderInstanceId, long structureId) {
		return _uniquePersistenceFinderByD_S.count(
			finderCache, new Object[] {dataProviderInstanceId, structureId});
	}

	public DDMDataProviderInstanceLinkPersistenceImpl() {
		setModelClass(DDMDataProviderInstanceLink.class);

		setModelImplClass(DDMDataProviderInstanceLinkImpl.class);
		setModelPKClass(long.class);

		setTable(DDMDataProviderInstanceLinkTable.INSTANCE);
	}

	/**
	 * Caches the ddm data provider instance link in the entity cache if it is enabled.
	 *
	 * @param ddmDataProviderInstanceLink the ddm data provider instance link
	 */
	@Override
	public void cacheResult(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ddmDataProviderInstanceLink.getCtCollectionId())) {

			entityCache.putResult(
				DDMDataProviderInstanceLinkImpl.class,
				ddmDataProviderInstanceLink.getPrimaryKey(),
				ddmDataProviderInstanceLink);

			finderCache.putResult(
				_finderPathFetchByD_S,
				new Object[] {
					ddmDataProviderInstanceLink.getDataProviderInstanceId(),
					ddmDataProviderInstanceLink.getStructureId()
				},
				ddmDataProviderInstanceLink);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ddm data provider instance links in the entity cache if it is enabled.
	 *
	 * @param ddmDataProviderInstanceLinks the ddm data provider instance links
	 */
	@Override
	public void cacheResult(
		List<DDMDataProviderInstanceLink> ddmDataProviderInstanceLinks) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ddmDataProviderInstanceLinks.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
				ddmDataProviderInstanceLinks) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ddmDataProviderInstanceLink.getCtCollectionId())) {

				if (entityCache.getResult(
						DDMDataProviderInstanceLinkImpl.class,
						ddmDataProviderInstanceLink.getPrimaryKey()) == null) {

					cacheResult(ddmDataProviderInstanceLink);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		DDMDataProviderInstanceLinkModelImpl
			ddmDataProviderInstanceLinkModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ddmDataProviderInstanceLinkModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				ddmDataProviderInstanceLinkModelImpl.
					getDataProviderInstanceId(),
				ddmDataProviderInstanceLinkModelImpl.getStructureId()
			};

			finderCache.putResult(
				_finderPathFetchByD_S, args,
				ddmDataProviderInstanceLinkModelImpl);
		}
	}

	/**
	 * Creates a new ddm data provider instance link with the primary key. Does not add the ddm data provider instance link to the database.
	 *
	 * @param dataProviderInstanceLinkId the primary key for the new ddm data provider instance link
	 * @return the new ddm data provider instance link
	 */
	@Override
	public DDMDataProviderInstanceLink create(long dataProviderInstanceLinkId) {
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			new DDMDataProviderInstanceLinkImpl();

		ddmDataProviderInstanceLink.setNew(true);
		ddmDataProviderInstanceLink.setPrimaryKey(dataProviderInstanceLinkId);

		ddmDataProviderInstanceLink.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return ddmDataProviderInstanceLink;
	}

	/**
	 * Removes the ddm data provider instance link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link that was removed
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink remove(long dataProviderInstanceLinkId)
		throws NoSuchDataProviderInstanceLinkException {

		return remove((Serializable)dataProviderInstanceLinkId);
	}

	@Override
	protected DDMDataProviderInstanceLink removeImpl(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmDataProviderInstanceLink)) {
				ddmDataProviderInstanceLink =
					(DDMDataProviderInstanceLink)session.get(
						DDMDataProviderInstanceLinkImpl.class,
						ddmDataProviderInstanceLink.getPrimaryKeyObj());
			}

			if ((ddmDataProviderInstanceLink != null) &&
				ctPersistenceHelper.isRemove(ddmDataProviderInstanceLink)) {

				session.delete(ddmDataProviderInstanceLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmDataProviderInstanceLink != null) {
			clearCache(ddmDataProviderInstanceLink);
		}

		return ddmDataProviderInstanceLink;
	}

	@Override
	public DDMDataProviderInstanceLink updateImpl(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		boolean isNew = ddmDataProviderInstanceLink.isNew();

		if (!(ddmDataProviderInstanceLink instanceof
				DDMDataProviderInstanceLinkModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					ddmDataProviderInstanceLink.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					ddmDataProviderInstanceLink);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddmDataProviderInstanceLink proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDMDataProviderInstanceLink implementation " +
					ddmDataProviderInstanceLink.getClass());
		}

		DDMDataProviderInstanceLinkModelImpl
			ddmDataProviderInstanceLinkModelImpl =
				(DDMDataProviderInstanceLinkModelImpl)
					ddmDataProviderInstanceLink;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ddmDataProviderInstanceLink)) {
				if (!isNew) {
					session.evict(
						DDMDataProviderInstanceLinkImpl.class,
						ddmDataProviderInstanceLink.getPrimaryKeyObj());
				}

				session.save(ddmDataProviderInstanceLink);
			}
			else {
				ddmDataProviderInstanceLink =
					(DDMDataProviderInstanceLink)session.merge(
						ddmDataProviderInstanceLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DDMDataProviderInstanceLinkImpl.class,
			ddmDataProviderInstanceLinkModelImpl, false, true);

		cacheUniqueFindersCache(ddmDataProviderInstanceLinkModelImpl);

		if (isNew) {
			ddmDataProviderInstanceLink.setNew(false);
		}

		ddmDataProviderInstanceLink.resetOriginalValues();

		return ddmDataProviderInstanceLink;
	}

	/**
	 * Returns the ddm data provider instance link with the primary key or throws a <code>NoSuchDataProviderInstanceLinkException</code> if it could not be found.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link
	 * @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink findByPrimaryKey(
			long dataProviderInstanceLinkId)
		throws NoSuchDataProviderInstanceLinkException {

		return findByPrimaryKey((Serializable)dataProviderInstanceLinkId);
	}

	/**
	 * Returns the ddm data provider instance link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link, or <code>null</code> if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				DDMDataProviderInstanceLink.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
			(DDMDataProviderInstanceLink)entityCache.getResult(
				DDMDataProviderInstanceLinkImpl.class, primaryKey);

		if (ddmDataProviderInstanceLink != null) {
			return ddmDataProviderInstanceLink;
		}

		Session session = null;

		try {
			session = openSession();

			ddmDataProviderInstanceLink =
				(DDMDataProviderInstanceLink)session.get(
					DDMDataProviderInstanceLinkImpl.class, primaryKey);

			if (ddmDataProviderInstanceLink != null) {
				cacheResult(ddmDataProviderInstanceLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return ddmDataProviderInstanceLink;
	}

	/**
	 * Returns the ddm data provider instance link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	 * @return the ddm data provider instance link, or <code>null</code> if a ddm data provider instance link with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstanceLink fetchByPrimaryKey(
		long dataProviderInstanceLinkId) {

		return fetchByPrimaryKey((Serializable)dataProviderInstanceLinkId);
	}

	@Override
	public Map<Serializable, DDMDataProviderInstanceLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				DDMDataProviderInstanceLink.class)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMDataProviderInstanceLink> map =
			new HashMap<Serializable, DDMDataProviderInstanceLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
				fetchByPrimaryKey(primaryKey);

			if (ddmDataProviderInstanceLink != null) {
				map.put(primaryKey, ddmDataProviderInstanceLink);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						DDMDataProviderInstanceLink.class, primaryKey)) {

				DDMDataProviderInstanceLink ddmDataProviderInstanceLink =
					(DDMDataProviderInstanceLink)entityCache.getResult(
						DDMDataProviderInstanceLinkImpl.class, primaryKey);

				if (ddmDataProviderInstanceLink == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, ddmDataProviderInstanceLink);
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

			for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
					(List<DDMDataProviderInstanceLink>)query.list()) {

				map.put(
					ddmDataProviderInstanceLink.getPrimaryKeyObj(),
					ddmDataProviderInstanceLink);

				cacheResult(ddmDataProviderInstanceLink);
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
	 * Returns all the ddm data provider instance links.
	 *
	 * @return the ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm data provider instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @return the range of ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findAll(
		int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm data provider instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMDataProviderInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm data provider instance links
	 * @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm data provider instance links
	 */
	@Override
	public List<DDMDataProviderInstanceLink> findAll(
		int start, int end,
		OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDMDataProviderInstanceLink.class)) {

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

			List<DDMDataProviderInstanceLink> list = null;

			if (useFinderCache) {
				list = (List<DDMDataProviderInstanceLink>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCELINK);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_DDMDATAPROVIDERINSTANCELINK;

					sql = sql.concat(
						DDMDataProviderInstanceLinkModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<DDMDataProviderInstanceLink>)QueryUtil.list(
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
	 * Removes all the ddm data provider instance links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
				findAll()) {

			remove(ddmDataProviderInstanceLink);
		}
	}

	/**
	 * Returns the number of ddm data provider instance links.
	 *
	 * @return the number of ddm data provider instance links
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDMDataProviderInstanceLink.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_DDMDATAPROVIDERINSTANCELINK);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "dataProviderInstanceLinkId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDMDATAPROVIDERINSTANCELINK;
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
		return DDMDataProviderInstanceLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DDMDataProviderInstanceLink";
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
		ctStrictColumnNames.add("companyId");
		ctMergeColumnNames.add("dataProviderInstanceId");
		ctMergeColumnNames.add("structureId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("dataProviderInstanceLinkId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"dataProviderInstanceId", "structureId"});
	}

	/**
	 * Initializes the ddm data provider instance link persistence.
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

		_finderPathWithPaginationFindByDataProviderInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByDataProviderInstanceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"dataProviderInstanceId"}, true);

		_finderPathWithoutPaginationFindByDataProviderInstanceId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByDataProviderInstanceId",
				new String[] {Long.class.getName()},
				new String[] {"dataProviderInstanceId"}, true);

		_finderPathCountByDataProviderInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDataProviderInstanceId",
			new String[] {Long.class.getName()},
			new String[] {"dataProviderInstanceId"}, false);

		_collectionPersistenceFinderByDataProviderInstanceId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByDataProviderInstanceId,
				_finderPathWithoutPaginationFindByDataProviderInstanceId,
				_finderPathCountByDataProviderInstanceId,
				_SQL_SELECT_DDMDATAPROVIDERINSTANCELINK_WHERE,
				_SQL_COUNT_DDMDATAPROVIDERINSTANCELINK_WHERE,
				DDMDataProviderInstanceLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ddmDataProviderInstanceLink.", "dataProviderInstanceId",
					FinderColumn.Type.LONG, "=", true, true,
					DDMDataProviderInstanceLink::getDataProviderInstanceId));

		_finderPathWithPaginationFindByStructureId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStructureId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"structureId"}, true);

		_finderPathWithoutPaginationFindByStructureId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStructureId",
			new String[] {Long.class.getName()}, new String[] {"structureId"},
			true);

		_finderPathCountByStructureId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStructureId",
			new String[] {Long.class.getName()}, new String[] {"structureId"},
			false);

		_collectionPersistenceFinderByStructureId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByStructureId,
				_finderPathWithoutPaginationFindByStructureId,
				_finderPathCountByStructureId,
				_SQL_SELECT_DDMDATAPROVIDERINSTANCELINK_WHERE,
				_SQL_COUNT_DDMDATAPROVIDERINSTANCELINK_WHERE,
				DDMDataProviderInstanceLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ddmDataProviderInstanceLink.", "structureId",
					FinderColumn.Type.LONG, "=", true, true,
					DDMDataProviderInstanceLink::getStructureId));

		_finderPathFetchByD_S = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByD_S",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"dataProviderInstanceId", "structureId"}, true);

		_uniquePersistenceFinderByD_S = new UniquePersistenceFinder<>(
			this, _finderPathFetchByD_S,
			_SQL_SELECT_DDMDATAPROVIDERINSTANCELINK_WHERE,
			new FinderColumn<>(
				"ddmDataProviderInstanceLink.", "dataProviderInstanceId",
				FinderColumn.Type.LONG, "=", true, false,
				DDMDataProviderInstanceLink::getDataProviderInstanceId),
			new FinderColumn<>(
				"ddmDataProviderInstanceLink.", "structureId",
				FinderColumn.Type.LONG, "=", true, true,
				DDMDataProviderInstanceLink::getStructureId));

		DDMDataProviderInstanceLinkUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		DDMDataProviderInstanceLinkUtil.setPersistence(null);

		entityCache.removeCache(
			DDMDataProviderInstanceLinkImpl.class.getName());
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DDMDATAPROVIDERINSTANCELINK =
		"SELECT ddmDataProviderInstanceLink FROM DDMDataProviderInstanceLink ddmDataProviderInstanceLink";

	private static final String _SQL_SELECT_DDMDATAPROVIDERINSTANCELINK_WHERE =
		"SELECT ddmDataProviderInstanceLink FROM DDMDataProviderInstanceLink ddmDataProviderInstanceLink WHERE ";

	private static final String _SQL_COUNT_DDMDATAPROVIDERINSTANCELINK =
		"SELECT COUNT(ddmDataProviderInstanceLink) FROM DDMDataProviderInstanceLink ddmDataProviderInstanceLink";

	private static final String _SQL_COUNT_DDMDATAPROVIDERINSTANCELINK_WHERE =
		"SELECT COUNT(ddmDataProviderInstanceLink) FROM DDMDataProviderInstanceLink ddmDataProviderInstanceLink WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"ddmDataProviderInstanceLink.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DDMDataProviderInstanceLink exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMDataProviderInstanceLinkPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1250879092