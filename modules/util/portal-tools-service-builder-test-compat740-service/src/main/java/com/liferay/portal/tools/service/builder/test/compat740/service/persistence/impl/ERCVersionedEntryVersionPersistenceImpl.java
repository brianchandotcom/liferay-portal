/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence.impl;

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
import com.liferay.portal.tools.service.builder.test.compat740.exception.NoSuchERCVersionedEntryVersionException;
import com.liferay.portal.tools.service.builder.test.compat740.model.ERCVersionedEntryVersion;
import com.liferay.portal.tools.service.builder.test.compat740.model.ERCVersionedEntryVersionTable;
import com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionImpl;
import com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.ERCVersionedEntryVersionPersistence;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.ERCVersionedEntryVersionUtil;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.impl.constants.SBCompat740PersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the erc versioned entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ERCVersionedEntryVersionPersistence.class)
public class ERCVersionedEntryVersionPersistenceImpl
	extends BasePersistenceImpl
		<ERCVersionedEntryVersion, NoSuchERCVersionedEntryVersionException>
	implements ERCVersionedEntryVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ERCVersionedEntryVersionUtil</code> to access the erc versioned entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ERCVersionedEntryVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByErcVersionedEntryId;
	private FinderPath _finderPathWithoutPaginationFindByErcVersionedEntryId;
	private FinderPath _finderPathCountByErcVersionedEntryId;
	private CollectionPersistenceFinder<ERCVersionedEntryVersion>
		_collectionPersistenceFinderByErcVersionedEntryId;

	/**
	 * Returns all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId) {

		return findByErcVersionedEntryId(
			ercVersionedEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId, int start, int end) {

		return findByErcVersionedEntryId(ercVersionedEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByErcVersionedEntryId(
			ercVersionedEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByErcVersionedEntryId.find(
			finderCache, new Object[] {ercVersionedEntryId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByErcVersionedEntryId_First(
			long ercVersionedEntryId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByErcVersionedEntryId_First(
				ercVersionedEntryId, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		throw new NoSuchERCVersionedEntryVersionException(
			_collectionPersistenceFinderByErcVersionedEntryId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {ercVersionedEntryId}));
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByErcVersionedEntryId_First(
		long ercVersionedEntryId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByErcVersionedEntryId.fetchFirst(
			finderCache, new Object[] {ercVersionedEntryId}, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where ercVersionedEntryId = &#63; from the database.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 */
	@Override
	public void removeByErcVersionedEntryId(long ercVersionedEntryId) {
		_collectionPersistenceFinderByErcVersionedEntryId.remove(
			finderCache, new Object[] {ercVersionedEntryId});
	}

	/**
	 * Returns the number of erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByErcVersionedEntryId(long ercVersionedEntryId) {
		return _collectionPersistenceFinderByErcVersionedEntryId.count(
			finderCache, new Object[] {ercVersionedEntryId});
	}

	private FinderPath _finderPathFetchByErcVersionedEntryId_Version;
	private UniquePersistenceFinder<ERCVersionedEntryVersion>
		_uniquePersistenceFinderByErcVersionedEntryId_Version;

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByErcVersionedEntryId_Version(
			long ercVersionedEntryId, int version)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByErcVersionedEntryId_Version(ercVersionedEntryId, version);

		if (ercVersionedEntryVersion == null) {
			String message =
				_uniquePersistenceFinderByErcVersionedEntryId_Version.
					buildNoSuchKeyMessage(
						_NO_SUCH_ENTITY_WITH_KEY,
						new Object[] {ercVersionedEntryId, version});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchERCVersionedEntryVersionException(message);
		}

		return ercVersionedEntryVersion;
	}

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version) {

		return fetchByErcVersionedEntryId_Version(
			ercVersionedEntryId, version, true);
	}

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version, boolean useFinderCache) {

		return _uniquePersistenceFinderByErcVersionedEntryId_Version.fetch(
			finderCache, new Object[] {ercVersionedEntryId, version},
			useFinderCache);
	}

	/**
	 * Removes the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the erc versioned entry version that was removed
	 */
	@Override
	public ERCVersionedEntryVersion removeByErcVersionedEntryId_Version(
			long ercVersionedEntryId, int version)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			findByErcVersionedEntryId_Version(ercVersionedEntryId, version);

		return remove(ercVersionedEntryVersion);
	}

	/**
	 * Returns the number of erc versioned entry versions where ercVersionedEntryId = &#63; and version = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version) {

		return _uniquePersistenceFinderByErcVersionedEntryId_Version.count(
			finderCache, new Object[] {ercVersionedEntryId, version});
	}

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<ERCVersionedEntryVersion>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_First(
			String uuid,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion = fetchByUuid_First(
			uuid, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		throw new NoSuchERCVersionedEntryVersionException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_First(
		String uuid,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_Version;
	private FinderPath _finderPathCountByUuid_Version;
	private CollectionPersistenceFinder<ERCVersionedEntryVersion>
		_collectionPersistenceFinderByUuid_Version;

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version) {

		return findByUuid_Version(
			uuid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end) {

		return findByUuid_Version(uuid, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid_Version(
			uuid, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_Version.find(
			finderCache, new Object[] {uuid, version}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_Version_First(
			String uuid, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByUuid_Version_First(uuid, version, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		throw new NoSuchERCVersionedEntryVersionException(
			_collectionPersistenceFinderByUuid_Version.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, version}));
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_Version_First(
		String uuid, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByUuid_Version.fetchFirst(
			finderCache, new Object[] {uuid, version}, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	@Override
	public void removeByUuid_Version(String uuid, int version) {
		_collectionPersistenceFinderByUuid_Version.remove(
			finderCache, new Object[] {uuid, version});
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUuid_Version(String uuid, int version) {
		return _collectionPersistenceFinderByUuid_Version.count(
			finderCache, new Object[] {uuid, version});
	}

	private FinderPath _finderPathWithPaginationFindByUUID_G;
	private FinderPath _finderPathWithoutPaginationFindByUUID_G;
	private FinderPath _finderPathCountByUUID_G;
	private CollectionPersistenceFinder<ERCVersionedEntryVersion>
		_collectionPersistenceFinderByUUID_G;

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId) {

		return findByUUID_G(
			uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return findByUUID_G(uuid, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByUUID_G(uuid, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUUID_G.find(
			finderCache, new Object[] {uuid, groupId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion = fetchByUUID_G_First(
			uuid, groupId, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		throw new NoSuchERCVersionedEntryVersionException(
			_collectionPersistenceFinderByUUID_G.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId}));
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByUUID_G.fetchFirst(
			finderCache, new Object[] {uuid, groupId}, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	@Override
	public void removeByUUID_G(String uuid, long groupId) {
		_collectionPersistenceFinderByUUID_G.remove(
			finderCache, new Object[] {uuid, groupId});
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _collectionPersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathFetchByUUID_G_Version;
	private UniquePersistenceFinder<ERCVersionedEntryVersion>
		_uniquePersistenceFinderByUUID_G_Version;

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByUUID_G_Version(uuid, groupId, version);

		if (ercVersionedEntryVersion == null) {
			String message =
				_uniquePersistenceFinderByUUID_G_Version.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {uuid, groupId, version});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchERCVersionedEntryVersionException(message);
		}

		return ercVersionedEntryVersion;
	}

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version) {

		return fetchByUUID_G_Version(uuid, groupId, version, true);
	}

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version, boolean useFinderCache) {

		return _uniquePersistenceFinderByUUID_G_Version.fetch(
			finderCache, new Object[] {uuid, groupId, version}, useFinderCache);
	}

	/**
	 * Removes the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the erc versioned entry version that was removed
	 */
	@Override
	public ERCVersionedEntryVersion removeByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			findByUUID_G_Version(uuid, groupId, version);

		return remove(ercVersionedEntryVersion);
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and groupId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUUID_G_Version(String uuid, long groupId, int version) {
		return _uniquePersistenceFinderByUUID_G_Version.count(
			finderCache, new Object[] {uuid, groupId, version});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<ERCVersionedEntryVersion>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		throw new NoSuchERCVersionedEntryVersionException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C_Version;
	private FinderPath _finderPathCountByUuid_C_Version;
	private CollectionPersistenceFinder<ERCVersionedEntryVersion>
		_collectionPersistenceFinderByUuid_C_Version;

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version) {

		return findByUuid_C_Version(
			uuid, companyId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end) {

		return findByUuid_C_Version(uuid, companyId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C_Version.find(
			finderCache, new Object[] {uuid, companyId, version}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException {

		ERCVersionedEntryVersion ercVersionedEntryVersion =
			fetchByUuid_C_Version_First(
				uuid, companyId, version, orderByComparator);

		if (ercVersionedEntryVersion != null) {
			return ercVersionedEntryVersion;
		}

		throw new NoSuchERCVersionedEntryVersionException(
			_collectionPersistenceFinderByUuid_C_Version.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {uuid, companyId, version}));
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C_Version.fetchFirst(
			finderCache, new Object[] {uuid, companyId, version},
			orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 */
	@Override
	public void removeByUuid_C_Version(
		String uuid, long companyId, int version) {

		_collectionPersistenceFinderByUuid_C_Version.remove(
			finderCache, new Object[] {uuid, companyId, version});
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	@Override
	public int countByUuid_C_Version(String uuid, long companyId, int version) {
		return _collectionPersistenceFinderByUuid_C_Version.count(
			finderCache, new Object[] {uuid, companyId, version});
	}

	public ERCVersionedEntryVersionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ERCVersionedEntryVersion.class);

		setModelImplClass(ERCVersionedEntryVersionImpl.class);
		setModelPKClass(long.class);

		setTable(ERCVersionedEntryVersionTable.INSTANCE);
	}

	/**
	 * Caches the erc versioned entry version in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntryVersion the erc versioned entry version
	 */
	@Override
	public void cacheResult(ERCVersionedEntryVersion ercVersionedEntryVersion) {
		entityCache.putResult(
			ERCVersionedEntryVersionImpl.class,
			ercVersionedEntryVersion.getPrimaryKey(), ercVersionedEntryVersion);

		finderCache.putResult(
			_finderPathFetchByErcVersionedEntryId_Version,
			new Object[] {
				ercVersionedEntryVersion.getErcVersionedEntryId(),
				ercVersionedEntryVersion.getVersion()
			},
			ercVersionedEntryVersion);

		finderCache.putResult(
			_finderPathFetchByUUID_G_Version,
			new Object[] {
				ercVersionedEntryVersion.getUuid(),
				ercVersionedEntryVersion.getGroupId(),
				ercVersionedEntryVersion.getVersion()
			},
			ercVersionedEntryVersion);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the erc versioned entry versions in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntryVersions the erc versioned entry versions
	 */
	@Override
	public void cacheResult(
		List<ERCVersionedEntryVersion> ercVersionedEntryVersions) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ercVersionedEntryVersions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ERCVersionedEntryVersion ercVersionedEntryVersion :
				ercVersionedEntryVersions) {

			if (entityCache.getResult(
					ERCVersionedEntryVersionImpl.class,
					ercVersionedEntryVersion.getPrimaryKey()) == null) {

				cacheResult(ercVersionedEntryVersion);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		ERCVersionedEntryVersionModelImpl ercVersionedEntryVersionModelImpl) {

		Object[] args = new Object[] {
			ercVersionedEntryVersionModelImpl.getErcVersionedEntryId(),
			ercVersionedEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathFetchByErcVersionedEntryId_Version, args,
			ercVersionedEntryVersionModelImpl);

		args = new Object[] {
			ercVersionedEntryVersionModelImpl.getUuid(),
			ercVersionedEntryVersionModelImpl.getGroupId(),
			ercVersionedEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathFetchByUUID_G_Version, args,
			ercVersionedEntryVersionModelImpl);
	}

	/**
	 * Creates a new erc versioned entry version with the primary key. Does not add the erc versioned entry version to the database.
	 *
	 * @param ercVersionedEntryVersionId the primary key for the new erc versioned entry version
	 * @return the new erc versioned entry version
	 */
	@Override
	public ERCVersionedEntryVersion create(long ercVersionedEntryVersionId) {
		ERCVersionedEntryVersion ercVersionedEntryVersion =
			new ERCVersionedEntryVersionImpl();

		ercVersionedEntryVersion.setNew(true);
		ercVersionedEntryVersion.setPrimaryKey(ercVersionedEntryVersionId);

		ercVersionedEntryVersion.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return ercVersionedEntryVersion;
	}

	/**
	 * Removes the erc versioned entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version that was removed
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion remove(long ercVersionedEntryVersionId)
		throws NoSuchERCVersionedEntryVersionException {

		return remove((Serializable)ercVersionedEntryVersionId);
	}

	@Override
	protected ERCVersionedEntryVersion removeImpl(
		ERCVersionedEntryVersion ercVersionedEntryVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ercVersionedEntryVersion)) {
				ercVersionedEntryVersion =
					(ERCVersionedEntryVersion)session.get(
						ERCVersionedEntryVersionImpl.class,
						ercVersionedEntryVersion.getPrimaryKeyObj());
			}

			if (ercVersionedEntryVersion != null) {
				session.delete(ercVersionedEntryVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ercVersionedEntryVersion != null) {
			clearCache(ercVersionedEntryVersion);
		}

		return ercVersionedEntryVersion;
	}

	@Override
	public ERCVersionedEntryVersion updateImpl(
		ERCVersionedEntryVersion ercVersionedEntryVersion) {

		boolean isNew = ercVersionedEntryVersion.isNew();

		if (!(ercVersionedEntryVersion instanceof
				ERCVersionedEntryVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ercVersionedEntryVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ercVersionedEntryVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ercVersionedEntryVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ERCVersionedEntryVersion implementation " +
					ercVersionedEntryVersion.getClass());
		}

		ERCVersionedEntryVersionModelImpl ercVersionedEntryVersionModelImpl =
			(ERCVersionedEntryVersionModelImpl)ercVersionedEntryVersion;

		if (Validator.isNull(
				ercVersionedEntryVersion.getExternalReferenceCode())) {

			ercVersionedEntryVersion.setExternalReferenceCode(
				String.valueOf(ercVersionedEntryVersion.getPrimaryKey()));
		}
		else {
			if (!Objects.equals(
					ercVersionedEntryVersionModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					ercVersionedEntryVersion.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = ercVersionedEntryVersion.getCompanyId();

					long groupId = ercVersionedEntryVersion.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = ercVersionedEntryVersion.getPrimaryKey();
					}

					try {
						ercVersionedEntryVersion.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								ERCVersionedEntryVersion.class.getName(),
								classPK, ContentTypes.TEXT_HTML,
								Sanitizer.MODE_ALL,
								ercVersionedEntryVersion.
									getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ercVersionedEntryVersion);
			}
			else {
				throw new IllegalArgumentException(
					"ERCVersionedEntryVersion is read only, create a new version instead");
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ERCVersionedEntryVersionImpl.class,
			ercVersionedEntryVersionModelImpl, false, true);

		cacheUniqueFindersCache(ercVersionedEntryVersionModelImpl);

		if (isNew) {
			ercVersionedEntryVersion.setNew(false);
		}

		ercVersionedEntryVersion.resetOriginalValues();

		return ercVersionedEntryVersion;
	}

	/**
	 * Returns the erc versioned entry version with the primary key or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion findByPrimaryKey(
			long ercVersionedEntryVersionId)
		throws NoSuchERCVersionedEntryVersionException {

		return findByPrimaryKey((Serializable)ercVersionedEntryVersionId);
	}

	/**
	 * Returns the erc versioned entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version, or <code>null</code> if a erc versioned entry version with the primary key could not be found
	 */
	@Override
	public ERCVersionedEntryVersion fetchByPrimaryKey(
		long ercVersionedEntryVersionId) {

		return fetchByPrimaryKey((Serializable)ercVersionedEntryVersionId);
	}

	/**
	 * Returns all the erc versioned entry versions.
	 *
	 * @return the erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findAll(
		int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc versioned entry versions
	 */
	@Override
	public List<ERCVersionedEntryVersion> findAll(
		int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
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

		List<ERCVersionedEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<ERCVersionedEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ERCVERSIONEDENTRYVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ERCVERSIONEDENTRYVERSION;

				sql = sql.concat(
					ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ERCVersionedEntryVersion>)QueryUtil.list(
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
	 * Removes all the erc versioned entry versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ERCVersionedEntryVersion ercVersionedEntryVersion : findAll()) {
			remove(ercVersionedEntryVersion);
		}
	}

	/**
	 * Returns the number of erc versioned entry versions.
	 *
	 * @return the number of erc versioned entry versions
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
					_SQL_COUNT_ERCVERSIONEDENTRYVERSION);

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
		return "ercVersionedEntryVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ERCVERSIONEDENTRYVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ERCVersionedEntryVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the erc versioned entry version persistence.
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

		_finderPathWithPaginationFindByErcVersionedEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByErcVersionedEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ercVersionedEntryId"}, true);

		_finderPathWithoutPaginationFindByErcVersionedEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByErcVersionedEntryId", new String[] {Long.class.getName()},
			new String[] {"ercVersionedEntryId"}, true);

		_finderPathCountByErcVersionedEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByErcVersionedEntryId", new String[] {Long.class.getName()},
			new String[] {"ercVersionedEntryId"}, false);

		_collectionPersistenceFinderByErcVersionedEntryId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByErcVersionedEntryId,
				_finderPathWithoutPaginationFindByErcVersionedEntryId,
				_finderPathCountByErcVersionedEntryId,
				_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE,
				_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE,
				ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "ercVersionedEntryId",
					FinderColumn.Type.LONG, "=", true, true,
					ERCVersionedEntryVersion::getErcVersionedEntryId));

		_finderPathFetchByErcVersionedEntryId_Version = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByErcVersionedEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"ercVersionedEntryId", "version"}, true);

		_uniquePersistenceFinderByErcVersionedEntryId_Version =
			new UniquePersistenceFinder<>(
				this, _finderPathFetchByErcVersionedEntryId_Version,
				_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE,
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "ercVersionedEntryId",
					FinderColumn.Type.LONG, "=", true, false,
					ERCVersionedEntryVersion::getErcVersionedEntryId),
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					ERCVersionedEntryVersion::getVersion));

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
			_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE,
			_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE,
			ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"ercVersionedEntryVersion.", "uuid", FinderColumn.Type.STRING,
				"=", true, true, ERCVersionedEntryVersion::getUuid));

		_finderPathWithPaginationFindByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_Version",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "version"}, true);

		_finderPathWithoutPaginationFindByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"uuid_", "version"}, true);

		_finderPathCountByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"uuid_", "version"}, false);

		_collectionPersistenceFinderByUuid_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_Version,
				_finderPathWithoutPaginationFindByUuid_Version,
				_finderPathCountByUuid_Version,
				_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE,
				_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE,
				ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					ERCVersionedEntryVersion::getUuid),
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					ERCVersionedEntryVersion::getVersion));

		_finderPathWithPaginationFindByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUUID_G",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathWithoutPaginationFindByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_collectionPersistenceFinderByUUID_G =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUUID_G,
				_finderPathWithoutPaginationFindByUUID_G,
				_finderPathCountByUUID_G,
				_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE,
				_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE,
				ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					ERCVersionedEntryVersion::getUuid),
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "groupId",
					FinderColumn.Type.LONG, "=", true, true,
					ERCVersionedEntryVersion::getGroupId));

		_finderPathFetchByUUID_G_Version = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "groupId", "version"}, true);

		_uniquePersistenceFinderByUUID_G_Version =
			new UniquePersistenceFinder<>(
				this, _finderPathFetchByUUID_G_Version,
				_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE,
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					ERCVersionedEntryVersion::getUuid),
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "groupId",
					FinderColumn.Type.LONG, "=", true, false,
					ERCVersionedEntryVersion::getGroupId),
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					ERCVersionedEntryVersion::getVersion));

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
				_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE,
				_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE,
				ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					ERCVersionedEntryVersion::getUuid),
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					ERCVersionedEntryVersion::getCompanyId));

		_finderPathWithPaginationFindByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, true);

		_finderPathWithoutPaginationFindByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, true);

		_finderPathCountByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, false);

		_collectionPersistenceFinderByUuid_C_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_C_Version,
				_finderPathWithoutPaginationFindByUuid_C_Version,
				_finderPathCountByUuid_C_Version,
				_SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE,
				_SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE,
				ERCVersionedEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					ERCVersionedEntryVersion::getUuid),
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "companyId",
					FinderColumn.Type.LONG, "=", true, false,
					ERCVersionedEntryVersion::getCompanyId),
				new FinderColumn<>(
					"ercVersionedEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					ERCVersionedEntryVersion::getVersion));

		ERCVersionedEntryVersionUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ERCVersionedEntryVersionUtil.setPersistence(null);

		entityCache.removeCache(ERCVersionedEntryVersionImpl.class.getName());
	}

	@Override
	@Reference(
		target = SBCompat740PersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SBCompat740PersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SBCompat740PersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ERCVERSIONEDENTRYVERSION =
		"SELECT ercVersionedEntryVersion FROM ERCVersionedEntryVersion ercVersionedEntryVersion";

	private static final String _SQL_SELECT_ERCVERSIONEDENTRYVERSION_WHERE =
		"SELECT ercVersionedEntryVersion FROM ERCVersionedEntryVersion ercVersionedEntryVersion WHERE ";

	private static final String _SQL_COUNT_ERCVERSIONEDENTRYVERSION =
		"SELECT COUNT(ercVersionedEntryVersion) FROM ERCVersionedEntryVersion ercVersionedEntryVersion";

	private static final String _SQL_COUNT_ERCVERSIONEDENTRYVERSION_WHERE =
		"SELECT COUNT(ercVersionedEntryVersion) FROM ERCVersionedEntryVersion ercVersionedEntryVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"ercVersionedEntryVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ERCVersionedEntryVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ERCVersionedEntryVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:30799319