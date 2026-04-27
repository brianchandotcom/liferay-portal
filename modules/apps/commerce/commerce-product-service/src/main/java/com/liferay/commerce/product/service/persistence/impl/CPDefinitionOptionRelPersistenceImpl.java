/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionRelTable;
import com.liferay.commerce.product.model.impl.CPDefinitionOptionRelImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionOptionRelPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionOptionRelUtil;
import com.liferay.commerce.product.service.persistence.impl.constants.CommercePersistenceConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
 * The persistence implementation for the cp definition option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CPDefinitionOptionRelPersistence.class)
public class CPDefinitionOptionRelPersistenceImpl
	extends BasePersistenceImpl<CPDefinitionOptionRel>
	implements CPDefinitionOptionRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDefinitionOptionRelUtil</code> to access the cp definition option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDefinitionOptionRelImpl.class.getName();

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
	private CollectionPersistenceFinder<CPDefinitionOptionRel>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the cp definition option rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByUuid_First(
			String uuid,
			OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		throw new NoSuchCPDefinitionOptionRelException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the cp definition option rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of cp definition option rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<CPDefinitionOptionRel>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the cp definition option rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPDefinitionOptionRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByUUID_G(
			uuid, groupId);

		if (cpDefinitionOptionRel == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPDefinitionOptionRelException(message);
		}

		return cpDefinitionOptionRel;
	}

	/**
	 * Returns the cp definition option rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition option rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the cp definition option rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition option rel that was removed
	 */
	@Override
	public CPDefinitionOptionRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = findByUUID_G(
			uuid, groupId);

		return remove(cpDefinitionOptionRel);
	}

	/**
	 * Returns the number of cp definition option rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<CPDefinitionOptionRel>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		throw new NoSuchCPDefinitionOptionRelException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the cp definition option rels where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of cp definition option rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<CPDefinitionOptionRel>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the cp definition option rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				finderCache, new Object[] {groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByGroupId_First(
			long groupId,
			OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByGroupId_First(
			groupId, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		throw new NoSuchCPDefinitionOptionRelException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByGroupId_First(
		long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Removes all the cp definition option rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of cp definition option rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				finderCache, new Object[] {groupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<CPDefinitionOptionRel>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the cp definition option rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				finderCache, new Object[] {companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByCompanyId_First(
			long companyId,
			OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		throw new NoSuchCPDefinitionOptionRelException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the cp definition option rels where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of cp definition option rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				finderCache, new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCPDefinitionId;
	private FinderPath _finderPathWithoutPaginationFindByCPDefinitionId;
	private FinderPath _finderPathCountByCPDefinitionId;
	private CollectionPersistenceFinder<CPDefinitionOptionRel>
		_collectionPersistenceFinderByCPDefinitionId;

	/**
	 * Returns all the cp definition option rels where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDefinitionId(
		long CPDefinitionId) {

		return findByCPDefinitionId(
			CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {

		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByCPDefinitionId.find(
				finderCache, new Object[] {CPDefinitionId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			fetchByCPDefinitionId_First(CPDefinitionId, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		throw new NoSuchCPDefinitionOptionRelException(
			_collectionPersistenceFinderByCPDefinitionId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {CPDefinitionId}));
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return _collectionPersistenceFinderByCPDefinitionId.fetchFirst(
			finderCache, new Object[] {CPDefinitionId}, orderByComparator);
	}

	/**
	 * Removes all the cp definition option rels where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		_collectionPersistenceFinderByCPDefinitionId.remove(
			finderCache, new Object[] {CPDefinitionId});
	}

	/**
	 * Returns the number of cp definition option rels where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByCPDefinitionId.count(
				finderCache, new Object[] {CPDefinitionId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCPOptionId;
	private FinderPath _finderPathWithoutPaginationFindByCPOptionId;
	private FinderPath _finderPathCountByCPOptionId;
	private CollectionPersistenceFinder<CPDefinitionOptionRel>
		_collectionPersistenceFinderByCPOptionId;

	/**
	 * Returns all the cp definition option rels where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPOptionId(long CPOptionId) {
		return findByCPOptionId(
			CPOptionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where CPOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionId the cp option ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPOptionId(
		long CPOptionId, int start, int end) {

		return findByCPOptionId(CPOptionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionId the cp option ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPOptionId(
		long CPOptionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return findByCPOptionId(
			CPOptionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionId the cp option ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPOptionId(
		long CPOptionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByCPOptionId.find(
				finderCache, new Object[] {CPOptionId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByCPOptionId_First(
			long CPOptionId,
			OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByCPOptionId_First(
			CPOptionId, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		throw new NoSuchCPDefinitionOptionRelException(
			_collectionPersistenceFinderByCPOptionId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {CPOptionId}));
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByCPOptionId_First(
		long CPOptionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return _collectionPersistenceFinderByCPOptionId.fetchFirst(
			finderCache, new Object[] {CPOptionId}, orderByComparator);
	}

	/**
	 * Removes all the cp definition option rels where CPOptionId = &#63; from the database.
	 *
	 * @param CPOptionId the cp option ID
	 */
	@Override
	public void removeByCPOptionId(long CPOptionId) {
		_collectionPersistenceFinderByCPOptionId.remove(
			finderCache, new Object[] {CPOptionId});
	}

	/**
	 * Returns the number of cp definition option rels where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByCPOptionId(long CPOptionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByCPOptionId.count(
				finderCache, new Object[] {CPOptionId});
		}
	}

	private FinderPath _finderPathFetchByC_C;
	private UniquePersistenceFinder<CPDefinitionOptionRel>
		_uniquePersistenceFinderByC_C;

	/**
	 * Returns the cp definition option rel where CPDefinitionId = &#63; and CPOptionId = &#63; or throws a <code>NoSuchCPDefinitionOptionRelException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionId the cp option ID
	 * @return the matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByC_C(long CPDefinitionId, long CPOptionId)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByC_C(
			CPDefinitionId, CPOptionId);

		if (cpDefinitionOptionRel == null) {
			String message =
				_uniquePersistenceFinderByC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {CPDefinitionId, CPOptionId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPDefinitionOptionRelException(message);
		}

		return cpDefinitionOptionRel;
	}

	/**
	 * Returns the cp definition option rel where CPDefinitionId = &#63; and CPOptionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionId the cp option ID
	 * @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByC_C(
		long CPDefinitionId, long CPOptionId) {

		return fetchByC_C(CPDefinitionId, CPOptionId, true);
	}

	/**
	 * Returns the cp definition option rel where CPDefinitionId = &#63; and CPOptionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionId the cp option ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByC_C(
		long CPDefinitionId, long CPOptionId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _uniquePersistenceFinderByC_C.fetch(
				finderCache, new Object[] {CPDefinitionId, CPOptionId},
				useFinderCache);
		}
	}

	/**
	 * Removes the cp definition option rel where CPDefinitionId = &#63; and CPOptionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionId the cp option ID
	 * @return the cp definition option rel that was removed
	 */
	@Override
	public CPDefinitionOptionRel removeByC_C(
			long CPDefinitionId, long CPOptionId)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = findByC_C(
			CPDefinitionId, CPOptionId);

		return remove(cpDefinitionOptionRel);
	}

	/**
	 * Returns the number of cp definition option rels where CPDefinitionId = &#63; and CPOptionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionId the cp option ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByC_C(long CPDefinitionId, long CPOptionId) {
		return _uniquePersistenceFinderByC_C.count(
			finderCache, new Object[] {CPDefinitionId, CPOptionId});
	}

	private FinderPath _finderPathWithPaginationFindByCPDI_R;
	private FinderPath _finderPathWithoutPaginationFindByCPDI_R;
	private FinderPath _finderPathCountByCPDI_R;
	private CollectionPersistenceFinder<CPDefinitionOptionRel>
		_collectionPersistenceFinderByCPDI_R;

	/**
	 * Returns all the cp definition option rels where CPDefinitionId = &#63; and required = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param required the required
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDI_R(
		long CPDefinitionId, boolean required) {

		return findByCPDI_R(
			CPDefinitionId, required, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp definition option rels where CPDefinitionId = &#63; and required = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param required the required
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDI_R(
		long CPDefinitionId, boolean required, int start, int end) {

		return findByCPDI_R(CPDefinitionId, required, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63; and required = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param required the required
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDI_R(
		long CPDefinitionId, boolean required, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return findByCPDI_R(
			CPDefinitionId, required, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63; and required = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param required the required
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDI_R(
		long CPDefinitionId, boolean required, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByCPDI_R.find(
				finderCache, new Object[] {CPDefinitionId, required}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63; and required = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param required the required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByCPDI_R_First(
			long CPDefinitionId, boolean required,
			OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByCPDI_R_First(
			CPDefinitionId, required, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		throw new NoSuchCPDefinitionOptionRelException(
			_collectionPersistenceFinderByCPDI_R.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {CPDefinitionId, required}));
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63; and required = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param required the required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByCPDI_R_First(
		long CPDefinitionId, boolean required,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return _collectionPersistenceFinderByCPDI_R.fetchFirst(
			finderCache, new Object[] {CPDefinitionId, required},
			orderByComparator);
	}

	/**
	 * Removes all the cp definition option rels where CPDefinitionId = &#63; and required = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param required the required
	 */
	@Override
	public void removeByCPDI_R(long CPDefinitionId, boolean required) {
		_collectionPersistenceFinderByCPDI_R.remove(
			finderCache, new Object[] {CPDefinitionId, required});
	}

	/**
	 * Returns the number of cp definition option rels where CPDefinitionId = &#63; and required = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param required the required
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByCPDI_R(long CPDefinitionId, boolean required) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByCPDI_R.count(
				finderCache, new Object[] {CPDefinitionId, required});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_SC;
	private FinderPath _finderPathWithoutPaginationFindByC_SC;
	private FinderPath _finderPathCountByC_SC;
	private CollectionPersistenceFinder<CPDefinitionOptionRel>
		_collectionPersistenceFinderByC_SC;

	/**
	 * Returns all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByC_SC(
		long CPDefinitionId, boolean skuContributor) {

		return findByC_SC(
			CPDefinitionId, skuContributor, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByC_SC(
		long CPDefinitionId, boolean skuContributor, int start, int end) {

		return findByC_SC(CPDefinitionId, skuContributor, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByC_SC(
		long CPDefinitionId, boolean skuContributor, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return findByC_SC(
			CPDefinitionId, skuContributor, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByC_SC(
		long CPDefinitionId, boolean skuContributor, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByC_SC.find(
				finderCache, new Object[] {CPDefinitionId, skuContributor},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByC_SC_First(
			long CPDefinitionId, boolean skuContributor,
			OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByC_SC_First(
			CPDefinitionId, skuContributor, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		throw new NoSuchCPDefinitionOptionRelException(
			_collectionPersistenceFinderByC_SC.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {CPDefinitionId, skuContributor}));
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByC_SC_First(
		long CPDefinitionId, boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return _collectionPersistenceFinderByC_SC.fetchFirst(
			finderCache, new Object[] {CPDefinitionId, skuContributor},
			orderByComparator);
	}

	/**
	 * Removes all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 */
	@Override
	public void removeByC_SC(long CPDefinitionId, boolean skuContributor) {
		_collectionPersistenceFinderByC_SC.remove(
			finderCache, new Object[] {CPDefinitionId, skuContributor});
	}

	/**
	 * Returns the number of cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByC_SC(long CPDefinitionId, boolean skuContributor) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _collectionPersistenceFinderByC_SC.count(
				finderCache, new Object[] {CPDefinitionId, skuContributor});
		}
	}

	private FinderPath _finderPathFetchByC_K;
	private UniquePersistenceFinder<CPDefinitionOptionRel>
		_uniquePersistenceFinderByC_K;

	/**
	 * Returns the cp definition option rel where CPDefinitionId = &#63; and key = &#63; or throws a <code>NoSuchCPDefinitionOptionRelException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param key the key
	 * @return the matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByC_K(long CPDefinitionId, String key)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByC_K(
			CPDefinitionId, key);

		if (cpDefinitionOptionRel == null) {
			String message =
				_uniquePersistenceFinderByC_K.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {CPDefinitionId, key});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPDefinitionOptionRelException(message);
		}

		return cpDefinitionOptionRel;
	}

	/**
	 * Returns the cp definition option rel where CPDefinitionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param key the key
	 * @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByC_K(long CPDefinitionId, String key) {
		return fetchByC_K(CPDefinitionId, key, true);
	}

	/**
	 * Returns the cp definition option rel where CPDefinitionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByC_K(
		long CPDefinitionId, String key, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			return _uniquePersistenceFinderByC_K.fetch(
				finderCache, new Object[] {CPDefinitionId, key},
				useFinderCache);
		}
	}

	/**
	 * Removes the cp definition option rel where CPDefinitionId = &#63; and key = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param key the key
	 * @return the cp definition option rel that was removed
	 */
	@Override
	public CPDefinitionOptionRel removeByC_K(long CPDefinitionId, String key)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = findByC_K(
			CPDefinitionId, key);

		return remove(cpDefinitionOptionRel);
	}

	/**
	 * Returns the number of cp definition option rels where CPDefinitionId = &#63; and key = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param key the key
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByC_K(long CPDefinitionId, String key) {
		return _uniquePersistenceFinderByC_K.count(
			finderCache, new Object[] {CPDefinitionId, key});
	}

	public CPDefinitionOptionRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("key", "key_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPDefinitionOptionRel.class);

		setModelImplClass(CPDefinitionOptionRelImpl.class);
		setModelPKClass(long.class);

		setTable(CPDefinitionOptionRelTable.INSTANCE);
	}

	/**
	 * Caches the cp definition option rel in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionOptionRel the cp definition option rel
	 */
	@Override
	public void cacheResult(CPDefinitionOptionRel cpDefinitionOptionRel) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpDefinitionOptionRel.getCtCollectionId())) {

			entityCache.putResult(
				CPDefinitionOptionRelImpl.class,
				cpDefinitionOptionRel.getPrimaryKey(), cpDefinitionOptionRel);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					cpDefinitionOptionRel.getUuid(),
					cpDefinitionOptionRel.getGroupId()
				},
				cpDefinitionOptionRel);

			finderCache.putResult(
				_finderPathFetchByC_C,
				new Object[] {
					cpDefinitionOptionRel.getCPDefinitionId(),
					cpDefinitionOptionRel.getCPOptionId()
				},
				cpDefinitionOptionRel);

			finderCache.putResult(
				_finderPathFetchByC_K,
				new Object[] {
					cpDefinitionOptionRel.getCPDefinitionId(),
					cpDefinitionOptionRel.getKey()
				},
				cpDefinitionOptionRel);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp definition option rels in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionOptionRels the cp definition option rels
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionOptionRel> cpDefinitionOptionRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpDefinitionOptionRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						cpDefinitionOptionRel.getCtCollectionId())) {

				if (entityCache.getResult(
						CPDefinitionOptionRelImpl.class,
						cpDefinitionOptionRel.getPrimaryKey()) == null) {

					cacheResult(cpDefinitionOptionRel);
				}
			}
		}
	}

	/**
	 * Clears the cache for all cp definition option rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionOptionRelImpl.class);

		finderCache.clearCache(CPDefinitionOptionRelImpl.class);
	}

	/**
	 * Clears the cache for the cp definition option rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinitionOptionRel cpDefinitionOptionRel) {
		entityCache.removeResult(
			CPDefinitionOptionRelImpl.class, cpDefinitionOptionRel);
	}

	@Override
	public void clearCache(List<CPDefinitionOptionRel> cpDefinitionOptionRels) {
		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			entityCache.removeResult(
				CPDefinitionOptionRelImpl.class, cpDefinitionOptionRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPDefinitionOptionRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPDefinitionOptionRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionOptionRelModelImpl cpDefinitionOptionRelModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpDefinitionOptionRelModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				cpDefinitionOptionRelModelImpl.getUuid(),
				cpDefinitionOptionRelModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, cpDefinitionOptionRelModelImpl);

			args = new Object[] {
				cpDefinitionOptionRelModelImpl.getCPDefinitionId(),
				cpDefinitionOptionRelModelImpl.getCPOptionId()
			};

			finderCache.putResult(
				_finderPathFetchByC_C, args, cpDefinitionOptionRelModelImpl);

			args = new Object[] {
				cpDefinitionOptionRelModelImpl.getCPDefinitionId(),
				cpDefinitionOptionRelModelImpl.getKey()
			};

			finderCache.putResult(
				_finderPathFetchByC_K, args, cpDefinitionOptionRelModelImpl);
		}
	}

	/**
	 * Creates a new cp definition option rel with the primary key. Does not add the cp definition option rel to the database.
	 *
	 * @param CPDefinitionOptionRelId the primary key for the new cp definition option rel
	 * @return the new cp definition option rel
	 */
	@Override
	public CPDefinitionOptionRel create(long CPDefinitionOptionRelId) {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			new CPDefinitionOptionRelImpl();

		cpDefinitionOptionRel.setNew(true);
		cpDefinitionOptionRel.setPrimaryKey(CPDefinitionOptionRelId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionOptionRel.setUuid(uuid);

		cpDefinitionOptionRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpDefinitionOptionRel;
	}

	/**
	 * Removes the cp definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	 * @return the cp definition option rel that was removed
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel remove(long CPDefinitionOptionRelId)
		throws NoSuchCPDefinitionOptionRelException {

		return remove((Serializable)CPDefinitionOptionRelId);
	}

	/**
	 * Removes the cp definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition option rel
	 * @return the cp definition option rel that was removed
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel remove(Serializable primaryKey)
		throws NoSuchCPDefinitionOptionRelException {

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionRel cpDefinitionOptionRel =
				(CPDefinitionOptionRel)session.get(
					CPDefinitionOptionRelImpl.class, primaryKey);

			if (cpDefinitionOptionRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionOptionRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpDefinitionOptionRel);
		}
		catch (NoSuchCPDefinitionOptionRelException noSuchEntityException) {
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
	protected CPDefinitionOptionRel removeImpl(
		CPDefinitionOptionRel cpDefinitionOptionRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionOptionRel)) {
				cpDefinitionOptionRel = (CPDefinitionOptionRel)session.get(
					CPDefinitionOptionRelImpl.class,
					cpDefinitionOptionRel.getPrimaryKeyObj());
			}

			if ((cpDefinitionOptionRel != null) &&
				ctPersistenceHelper.isRemove(cpDefinitionOptionRel)) {

				session.delete(cpDefinitionOptionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionOptionRel != null) {
			clearCache(cpDefinitionOptionRel);
		}

		return cpDefinitionOptionRel;
	}

	@Override
	public CPDefinitionOptionRel updateImpl(
		CPDefinitionOptionRel cpDefinitionOptionRel) {

		boolean isNew = cpDefinitionOptionRel.isNew();

		if (!(cpDefinitionOptionRel instanceof
				CPDefinitionOptionRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinitionOptionRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpDefinitionOptionRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionOptionRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionOptionRel implementation " +
					cpDefinitionOptionRel.getClass());
		}

		CPDefinitionOptionRelModelImpl cpDefinitionOptionRelModelImpl =
			(CPDefinitionOptionRelModelImpl)cpDefinitionOptionRel;

		if (Validator.isNull(cpDefinitionOptionRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionOptionRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpDefinitionOptionRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionOptionRel.setCreateDate(date);
			}
			else {
				cpDefinitionOptionRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpDefinitionOptionRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionOptionRel.setModifiedDate(date);
			}
			else {
				cpDefinitionOptionRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(cpDefinitionOptionRel)) {
				if (!isNew) {
					session.evict(
						CPDefinitionOptionRelImpl.class,
						cpDefinitionOptionRel.getPrimaryKeyObj());
				}

				session.save(cpDefinitionOptionRel);
			}
			else {
				cpDefinitionOptionRel = (CPDefinitionOptionRel)session.merge(
					cpDefinitionOptionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDefinitionOptionRelImpl.class, cpDefinitionOptionRelModelImpl,
			false, true);

		cacheUniqueFindersCache(cpDefinitionOptionRelModelImpl);

		if (isNew) {
			cpDefinitionOptionRel.setNew(false);
		}

		cpDefinitionOptionRel.resetOriginalValues();

		return cpDefinitionOptionRel;
	}

	/**
	 * Returns the cp definition option rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition option rel
	 * @return the cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionOptionRelException {

		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByPrimaryKey(
			primaryKey);

		if (cpDefinitionOptionRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionOptionRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpDefinitionOptionRel;
	}

	/**
	 * Returns the cp definition option rel with the primary key or throws a <code>NoSuchCPDefinitionOptionRelException</code> if it could not be found.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	 * @return the cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByPrimaryKey(long CPDefinitionOptionRelId)
		throws NoSuchCPDefinitionOptionRelException {

		return findByPrimaryKey((Serializable)CPDefinitionOptionRelId);
	}

	/**
	 * Returns the cp definition option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition option rel
	 * @return the cp definition option rel, or <code>null</code> if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				CPDefinitionOptionRel.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		CPDefinitionOptionRel cpDefinitionOptionRel =
			(CPDefinitionOptionRel)entityCache.getResult(
				CPDefinitionOptionRelImpl.class, primaryKey);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		Session session = null;

		try {
			session = openSession();

			cpDefinitionOptionRel = (CPDefinitionOptionRel)session.get(
				CPDefinitionOptionRelImpl.class, primaryKey);

			if (cpDefinitionOptionRel != null) {
				cacheResult(cpDefinitionOptionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return cpDefinitionOptionRel;
	}

	/**
	 * Returns the cp definition option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	 * @return the cp definition option rel, or <code>null</code> if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByPrimaryKey(
		long CPDefinitionOptionRelId) {

		return fetchByPrimaryKey((Serializable)CPDefinitionOptionRelId);
	}

	@Override
	public Map<Serializable, CPDefinitionOptionRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(CPDefinitionOptionRel.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDefinitionOptionRel> map =
			new HashMap<Serializable, CPDefinitionOptionRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDefinitionOptionRel cpDefinitionOptionRel = fetchByPrimaryKey(
				primaryKey);

			if (cpDefinitionOptionRel != null) {
				map.put(primaryKey, cpDefinitionOptionRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						CPDefinitionOptionRel.class, primaryKey)) {

				CPDefinitionOptionRel cpDefinitionOptionRel =
					(CPDefinitionOptionRel)entityCache.getResult(
						CPDefinitionOptionRelImpl.class, primaryKey);

				if (cpDefinitionOptionRel == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, cpDefinitionOptionRel);
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

			for (CPDefinitionOptionRel cpDefinitionOptionRel :
					(List<CPDefinitionOptionRel>)query.list()) {

				map.put(
					cpDefinitionOptionRel.getPrimaryKeyObj(),
					cpDefinitionOptionRel);

				cacheResult(cpDefinitionOptionRel);
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
	 * Returns all the cp definition option rels.
	 *
	 * @return the cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

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

			List<CPDefinitionOptionRel> list = null;

			if (useFinderCache) {
				list = (List<CPDefinitionOptionRel>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_CPDEFINITIONOPTIONREL);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_CPDEFINITIONOPTIONREL;

					sql = sql.concat(
						CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<CPDefinitionOptionRel>)QueryUtil.list(
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
	 * Removes all the cp definition option rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionOptionRel cpDefinitionOptionRel : findAll()) {
			remove(cpDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of cp definition option rels.
	 *
	 * @return the number of cp definition option rels
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionOptionRel.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_CPDEFINITIONOPTIONREL);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CPDefinitionOptionRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDEFINITIONOPTIONREL;
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
		return CPDefinitionOptionRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPDefinitionOptionRel";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("CPDefinitionId");
		ctMergeColumnNames.add("CPOptionId");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("description");
		ctMergeColumnNames.add("commerceOptionTypeKey");
		ctMergeColumnNames.add("infoItemServiceKey");
		ctMergeColumnNames.add("priority");
		ctMergeColumnNames.add("definedExternally");
		ctMergeColumnNames.add("facetable");
		ctMergeColumnNames.add("required");
		ctMergeColumnNames.add("skuContributor");
		ctMergeColumnNames.add("key_");
		ctMergeColumnNames.add("priceType");
		ctMergeColumnNames.add("typeSettings");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPDefinitionOptionRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"CPDefinitionId", "CPOptionId"});

		_uniqueIndexColumnNames.add(new String[] {"CPDefinitionId", "key_"});
	}

	/**
	 * Initializes the cp definition option rel persistence.
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
			_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE,
			_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE,
			CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpDefinitionOptionRel.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, CPDefinitionOptionRel::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G,
			_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE,
			new FinderColumn<>(
				"cpDefinitionOptionRel.", "uuid", FinderColumn.Type.STRING, "=",
				true, false, CPDefinitionOptionRel::getUuid),
			new FinderColumn<>(
				"cpDefinitionOptionRel.", "groupId", FinderColumn.Type.LONG,
				"=", true, true, CPDefinitionOptionRel::getGroupId));

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
				_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE,
				_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE,
				CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpDefinitionOptionRel.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, CPDefinitionOptionRel::getUuid),
				new FinderColumn<>(
					"cpDefinitionOptionRel.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					CPDefinitionOptionRel::getCompanyId));

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
				_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE,
				_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE,
				CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpDefinitionOptionRel.", "groupId", FinderColumn.Type.LONG,
					"=", true, true, CPDefinitionOptionRel::getGroupId));

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
				_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE,
				_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE,
				CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpDefinitionOptionRel.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					CPDefinitionOptionRel::getCompanyId));

		_finderPathWithPaginationFindByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPDefinitionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionId"}, true);

		_finderPathWithoutPaginationFindByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, true);

		_finderPathCountByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, false);

		_collectionPersistenceFinderByCPDefinitionId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCPDefinitionId,
				_finderPathWithoutPaginationFindByCPDefinitionId,
				_finderPathCountByCPDefinitionId,
				_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE,
				_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE,
				CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpDefinitionOptionRel.", "CPDefinitionId",
					FinderColumn.Type.LONG, "=", true, true,
					CPDefinitionOptionRel::getCPDefinitionId));

		_finderPathWithPaginationFindByCPOptionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPOptionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPOptionId"}, true);

		_finderPathWithoutPaginationFindByCPOptionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPOptionId",
			new String[] {Long.class.getName()}, new String[] {"CPOptionId"},
			true);

		_finderPathCountByCPOptionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPOptionId",
			new String[] {Long.class.getName()}, new String[] {"CPOptionId"},
			false);

		_collectionPersistenceFinderByCPOptionId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCPOptionId,
				_finderPathWithoutPaginationFindByCPOptionId,
				_finderPathCountByCPOptionId,
				_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE,
				_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE,
				CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpDefinitionOptionRel.", "CPOptionId",
					FinderColumn.Type.LONG, "=", true, true,
					CPDefinitionOptionRel::getCPOptionId));

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionId", "CPOptionId"}, true);

		_uniquePersistenceFinderByC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_C,
			_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE,
			new FinderColumn<>(
				"cpDefinitionOptionRel.", "CPDefinitionId",
				FinderColumn.Type.LONG, "=", true, false,
				CPDefinitionOptionRel::getCPDefinitionId),
			new FinderColumn<>(
				"cpDefinitionOptionRel.", "CPOptionId", FinderColumn.Type.LONG,
				"=", true, true, CPDefinitionOptionRel::getCPOptionId));

		_finderPathWithPaginationFindByCPDI_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPDI_R",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionId", "required"}, true);

		_finderPathWithoutPaginationFindByCPDI_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDI_R",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"CPDefinitionId", "required"}, true);

		_finderPathCountByCPDI_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDI_R",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"CPDefinitionId", "required"}, false);

		_collectionPersistenceFinderByCPDI_R =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCPDI_R,
				_finderPathWithoutPaginationFindByCPDI_R,
				_finderPathCountByCPDI_R,
				_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE,
				_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE,
				CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpDefinitionOptionRel.", "CPDefinitionId",
					FinderColumn.Type.LONG, "=", true, false,
					CPDefinitionOptionRel::getCPDefinitionId),
				new FinderColumn<>(
					"cpDefinitionOptionRel.", "required",
					FinderColumn.Type.BOOLEAN, "=", true, true,
					CPDefinitionOptionRel::isRequired));

		_finderPathWithPaginationFindByC_SC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_SC",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionId", "skuContributor"}, true);

		_finderPathWithoutPaginationFindByC_SC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_SC",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"CPDefinitionId", "skuContributor"}, true);

		_finderPathCountByC_SC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_SC",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"CPDefinitionId", "skuContributor"}, false);

		_collectionPersistenceFinderByC_SC = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_SC,
			_finderPathWithoutPaginationFindByC_SC, _finderPathCountByC_SC,
			_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE,
			_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE,
			CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpDefinitionOptionRel.", "CPDefinitionId",
				FinderColumn.Type.LONG, "=", true, false,
				CPDefinitionOptionRel::getCPDefinitionId),
			new FinderColumn<>(
				"cpDefinitionOptionRel.", "skuContributor",
				FinderColumn.Type.BOOLEAN, "=", true, true,
				CPDefinitionOptionRel::isSkuContributor));

		_finderPathFetchByC_K = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"CPDefinitionId", "key_"}, true);

		_uniquePersistenceFinderByC_K = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_K,
			_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE,
			new FinderColumn<>(
				"cpDefinitionOptionRel.", "CPDefinitionId",
				FinderColumn.Type.LONG, "=", true, false,
				CPDefinitionOptionRel::getCPDefinitionId),
			new FinderColumn<>(
				"cpDefinitionOptionRel.", "key", FinderColumn.Type.STRING, "=",
				true, true, CPDefinitionOptionRel::getKey));

		CPDefinitionOptionRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPDefinitionOptionRelUtil.setPersistence(null);

		entityCache.removeCache(CPDefinitionOptionRelImpl.class.getName());
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_CPDEFINITIONOPTIONREL =
		"SELECT cpDefinitionOptionRel FROM CPDefinitionOptionRel cpDefinitionOptionRel";

	private static final String _SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE =
		"SELECT cpDefinitionOptionRel FROM CPDefinitionOptionRel cpDefinitionOptionRel WHERE ";

	private static final String _SQL_COUNT_CPDEFINITIONOPTIONREL =
		"SELECT COUNT(cpDefinitionOptionRel) FROM CPDefinitionOptionRel cpDefinitionOptionRel";

	private static final String _SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE =
		"SELECT COUNT(cpDefinitionOptionRel) FROM CPDefinitionOptionRel cpDefinitionOptionRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpDefinitionOptionRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPDefinitionOptionRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDefinitionOptionRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionOptionRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "key"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-2057163858