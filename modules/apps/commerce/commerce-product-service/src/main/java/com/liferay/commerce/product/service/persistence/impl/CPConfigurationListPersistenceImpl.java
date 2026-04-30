/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.DuplicateCPConfigurationListExternalReferenceCodeException;
import com.liferay.commerce.product.exception.NoSuchCPConfigurationListException;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPConfigurationListTable;
import com.liferay.commerce.product.model.impl.CPConfigurationListImpl;
import com.liferay.commerce.product.model.impl.CPConfigurationListModelImpl;
import com.liferay.commerce.product.service.persistence.CPConfigurationListPersistence;
import com.liferay.commerce.product.service.persistence.CPConfigurationListUtil;
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
import com.liferay.portal.kernel.dao.orm.QueryPos;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
 * The persistence implementation for the cp configuration list service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CPConfigurationListPersistence.class)
public class CPConfigurationListPersistenceImpl
	extends BasePersistenceImpl
		<CPConfigurationList, NoSuchCPConfigurationListException>
	implements CPConfigurationListPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPConfigurationListUtil</code> to access the cp configuration list persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPConfigurationListImpl.class.getName();

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
	private CollectionPersistenceFinder<CPConfigurationList>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the cp configuration lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByUuid_First(
			String uuid,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		throw new NoSuchCPConfigurationListException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByUuid_First(
		String uuid, OrderByComparator<CPConfigurationList> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the cp configuration lists where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of cp configuration lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<CPConfigurationList>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the cp configuration list where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByUUID_G(String uuid, long groupId)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByUUID_G(uuid, groupId);

		if (cpConfigurationList == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPConfigurationListException(message);
		}

		return cpConfigurationList;
	}

	/**
	 * Returns the cp configuration list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp configuration list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the cp configuration list where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp configuration list that was removed
	 */
	@Override
	public CPConfigurationList removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = findByUUID_G(uuid, groupId);

		return remove(cpConfigurationList);
	}

	/**
	 * Returns the number of cp configuration lists where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<CPConfigurationList>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		throw new NoSuchCPConfigurationListException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first cp configuration list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the cp configuration lists where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of cp configuration lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<CPConfigurationList>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the cp configuration lists where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				finderCache, new Object[] {companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByCompanyId_First(
			long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		throw new NoSuchCPConfigurationListException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first cp configuration list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the cp configuration lists where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of cp configuration lists where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				finderCache, new Object[] {companyId});
		}
	}

	private FinderPath
		_finderPathWithPaginationFindByParentCPConfigurationListId;
	private FinderPath
		_finderPathWithoutPaginationFindByParentCPConfigurationListId;
	private FinderPath _finderPathCountByParentCPConfigurationListId;
	private CollectionPersistenceFinder<CPConfigurationList>
		_collectionPersistenceFinderByParentCPConfigurationListId;

	/**
	 * Returns all the cp configuration lists where parentCPConfigurationListId = &#63;.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByParentCPConfigurationListId(
		long parentCPConfigurationListId) {

		return findByParentCPConfigurationListId(
			parentCPConfigurationListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp configuration lists where parentCPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByParentCPConfigurationListId(
		long parentCPConfigurationListId, int start, int end) {

		return findByParentCPConfigurationListId(
			parentCPConfigurationListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where parentCPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByParentCPConfigurationListId(
		long parentCPConfigurationListId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByParentCPConfigurationListId(
			parentCPConfigurationListId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where parentCPConfigurationListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByParentCPConfigurationListId(
		long parentCPConfigurationListId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByParentCPConfigurationListId.
				find(
					finderCache, new Object[] {parentCPConfigurationListId},
					start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp configuration list in the ordered set where parentCPConfigurationListId = &#63;.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByParentCPConfigurationListId_First(
			long parentCPConfigurationListId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList =
			fetchByParentCPConfigurationListId_First(
				parentCPConfigurationListId, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		throw new NoSuchCPConfigurationListException(
			_collectionPersistenceFinderByParentCPConfigurationListId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {parentCPConfigurationListId}));
	}

	/**
	 * Returns the first cp configuration list in the ordered set where parentCPConfigurationListId = &#63;.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByParentCPConfigurationListId_First(
		long parentCPConfigurationListId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return _collectionPersistenceFinderByParentCPConfigurationListId.
			fetchFirst(
				finderCache, new Object[] {parentCPConfigurationListId},
				orderByComparator);
	}

	/**
	 * Removes all the cp configuration lists where parentCPConfigurationListId = &#63; from the database.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 */
	@Override
	public void removeByParentCPConfigurationListId(
		long parentCPConfigurationListId) {

		_collectionPersistenceFinderByParentCPConfigurationListId.remove(
			finderCache, new Object[] {parentCPConfigurationListId});
	}

	/**
	 * Returns the number of cp configuration lists where parentCPConfigurationListId = &#63;.
	 *
	 * @param parentCPConfigurationListId the parent cp configuration list ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByParentCPConfigurationListId(
		long parentCPConfigurationListId) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByParentCPConfigurationListId.
				count(finderCache, new Object[] {parentCPConfigurationListId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C;
	private FinderPath _finderPathCountByG_C;
	private FinderPath _finderPathWithPaginationCountByG_C;

	/**
	 * Returns all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(long groupId, long companyId) {
		return findByG_C(
			groupId, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long groupId, long companyId, int start, int end) {

		return findByG_C(groupId, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long groupId, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C(
			groupId, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long groupId, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_C;
					finderArgs = new Object[] {groupId, companyId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_C;
				finderArgs = new Object[] {
					groupId, companyId, start, end, orderByComparator
				};
			}

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if ((groupId != cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId())) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(companyId);

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByG_C_First(
			long groupId, long companyId,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByG_C_First(
			groupId, companyId, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_C_First(
		long groupId, long companyId,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		List<CPConfigurationList> list = findByG_C(
			groupId, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId) {

		return findByG_C(
			groupIds, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId, int start, int end) {

		return findByG_C(groupIds, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C(
			groupIds, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C(
		long[] groupIds, long companyId, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByG_C(
				groupIds[0], companyId, start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						StringUtil.merge(groupIds), companyId
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), companyId, start, end,
					orderByComparator
				};
			}

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					_finderPathWithPaginationFindByG_C, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if (!ArrayUtil.contains(
								groupIds, cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					list = (List<CPConfigurationList>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_C, finderArgs,
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
	}

	/**
	 * Removes all the cp configuration lists where groupId = &#63; and companyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 */
	@Override
	public void removeByG_C(long groupId, long companyId) {
		for (CPConfigurationList cpConfigurationList :
				findByG_C(
					groupId, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C(long groupId, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = _finderPathCountByG_C;

			Object[] finderArgs = new Object[] {groupId, companyId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(companyId);

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
	}

	/**
	 * Returns the number of cp configuration lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C(long[] groupIds, long companyId) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = new Object[] {
				StringUtil.merge(groupIds), companyId
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_C, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_C, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 =
		"cpConfigurationList.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_GROUPID_7 =
		"cpConfigurationList.groupId IN (";

	private static final String _FINDER_COLUMN_G_C_COMPANYID_2 =
		"cpConfigurationList.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByG_M;
	private FinderPath _finderPathWithoutPaginationFindByG_M;
	private FinderPath _finderPathCountByG_M;
	private CollectionPersistenceFinder<CPConfigurationList>
		_collectionPersistenceFinderByG_M;

	/**
	 * Returns all the cp configuration lists where groupId = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param master the master
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_M(long groupId, boolean master) {
		return findByG_M(
			groupId, master, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param master the master
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_M(
		long groupId, boolean master, int start, int end) {

		return findByG_M(groupId, master, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param master the master
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_M(
		long groupId, boolean master, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_M(groupId, master, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param master the master
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_M(
		long groupId, boolean master, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByG_M.find(
				finderCache, new Object[] {groupId, master}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByG_M_First(
			long groupId, boolean master,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByG_M_First(
			groupId, master, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		throw new NoSuchCPConfigurationListException(
			_collectionPersistenceFinderByG_M.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, master}));
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_M_First(
		long groupId, boolean master,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return _collectionPersistenceFinderByG_M.fetchFirst(
			finderCache, new Object[] {groupId, master}, orderByComparator);
	}

	/**
	 * Removes all the cp configuration lists where groupId = &#63; and master = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param master the master
	 */
	@Override
	public void removeByG_M(long groupId, boolean master) {
		_collectionPersistenceFinderByG_M.remove(
			finderCache, new Object[] {groupId, master});
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param master the master
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_M(long groupId, boolean master) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByG_M.count(
				finderCache, new Object[] {groupId, master});
		}
	}

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;
	private CollectionPersistenceFinder<CPConfigurationList>
		_collectionPersistenceFinderByLtD_S;

	/**
	 * Returns all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByLtD_S.find(
				finderCache, new Object[] {displayDate, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		throw new NoSuchCPConfigurationListException(
			_collectionPersistenceFinderByLtD_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {displayDate, status}));
	}

	/**
	 * Returns the first cp configuration list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return _collectionPersistenceFinderByLtD_S.fetchFirst(
			finderCache, new Object[] {displayDate, status}, orderByComparator);
	}

	/**
	 * Removes all the cp configuration lists where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		_collectionPersistenceFinderByLtD_S.remove(
			finderCache, new Object[] {displayDate, status});
	}

	/**
	 * Returns the number of cp configuration lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _collectionPersistenceFinderByLtD_S.count(
				finderCache, new Object[] {displayDate, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_C_S;
	private FinderPath _finderPathWithoutPaginationFindByG_C_S;
	private FinderPath _finderPathCountByG_C_S;
	private FinderPath _finderPathWithPaginationCountByG_C_S;

	/**
	 * Returns all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status) {

		return findByG_C_S(
			groupId, companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end) {

		return findByG_C_S(groupId, companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C_S(
			groupId, companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_C_S;
					finderArgs = new Object[] {groupId, companyId, status};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_C_S;
				finderArgs = new Object[] {
					groupId, companyId, status, start, end, orderByComparator
				};
			}

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if ((groupId != cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId()) ||
							(status != cpConfigurationList.getStatus())) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_S_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(companyId);

					queryPos.add(status);

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByG_C_S_First(
			long groupId, long companyId, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByG_C_S_First(
			groupId, companyId, status, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_C_S_First(
		long groupId, long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		List<CPConfigurationList> list = findByG_C_S(
			groupId, companyId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status) {

		return findByG_C_S(
			groupIds, companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end) {

		return findByG_C_S(groupIds, companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C_S(
			groupIds, companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByG_C_S(
				groupIds[0], companyId, status, start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						StringUtil.merge(groupIds), companyId, status
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), companyId, status, start, end,
					orderByComparator
				};
			}

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					_finderPathWithPaginationFindByG_C_S, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if (!ArrayUtil.contains(
								groupIds, cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId()) ||
							(status != cpConfigurationList.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_S_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_S_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(status);

					list = (List<CPConfigurationList>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_C_S, finderArgs,
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
	}

	/**
	 * Removes all the cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByG_C_S(long groupId, long companyId, int status) {
		for (CPConfigurationList cpConfigurationList :
				findByG_C_S(
					groupId, companyId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C_S(long groupId, long companyId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = _finderPathCountByG_C_S;

			Object[] finderArgs = new Object[] {groupId, companyId, status};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_S_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(companyId);

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
	}

	/**
	 * Returns the number of cp configuration lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C_S(long[] groupIds, long companyId, int status) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = new Object[] {
				StringUtil.merge(groupIds), companyId, status
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_C_S, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_S_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_S_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(status);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_C_S, finderArgs,
						count);
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

	private static final String _FINDER_COLUMN_G_C_S_GROUPID_2 =
		"cpConfigurationList.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_S_GROUPID_7 =
		"cpConfigurationList.groupId IN (";

	private static final String _FINDER_COLUMN_G_C_S_COMPANYID_2 =
		"cpConfigurationList.companyId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_S_STATUS_2 =
		"cpConfigurationList.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_NotS;
	private FinderPath _finderPathWithPaginationCountByG_C_NotS;

	/**
	 * Returns all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status) {

		return findByG_C_NotS(
			groupId, companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end) {

		return findByG_C_NotS(groupId, companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C_NotS(
			groupId, companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			finderPath = _finderPathWithPaginationFindByG_C_NotS;
			finderArgs = new Object[] {
				groupId, companyId, status, start, end, orderByComparator
			};

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if ((groupId != cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId()) ||
							(status == cpConfigurationList.getStatus())) {

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

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(companyId);

					queryPos.add(status);

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByG_C_NotS_First(
			long groupId, long companyId, int status,
			OrderByComparator<CPConfigurationList> orderByComparator)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByG_C_NotS_First(
			groupId, companyId, status, orderByComparator);

		if (cpConfigurationList != null) {
			return cpConfigurationList;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", status!=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPConfigurationListException(sb.toString());
	}

	/**
	 * Returns the first cp configuration list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByG_C_NotS_First(
		long groupId, long companyId, int status,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		List<CPConfigurationList> list = findByG_C_NotS(
			groupId, companyId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status) {

		return findByG_C_NotS(
			groupIds, companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end) {

		return findByG_C_NotS(groupIds, companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findByG_C_NotS(
			groupIds, companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByG_C_NotS(
				groupIds[0], companyId, status, start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						StringUtil.merge(groupIds), companyId, status
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), companyId, status, start, end,
					orderByComparator
				};
			}

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					_finderPathWithPaginationFindByG_C_NotS, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (CPConfigurationList cpConfigurationList : list) {
						if (!ArrayUtil.contains(
								groupIds, cpConfigurationList.getGroupId()) ||
							(companyId != cpConfigurationList.getCompanyId()) ||
							(status == cpConfigurationList.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_NOTS_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(status);

					list = (List<CPConfigurationList>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_C_NotS, finderArgs,
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
	}

	/**
	 * Removes all the cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByG_C_NotS(long groupId, long companyId, int status) {
		for (CPConfigurationList cpConfigurationList :
				findByG_C_NotS(
					groupId, companyId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C_NotS(long groupId, long companyId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			FinderPath finderPath = _finderPathWithPaginationCountByG_C_NotS;

			Object[] finderArgs = new Object[] {groupId, companyId, status};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(companyId);

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
	}

	/**
	 * Returns the number of cp configuration lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByG_C_NotS(long[] groupIds, long companyId, int status) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Object[] finderArgs = new Object[] {
				StringUtil.merge(groupIds), companyId, status
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_C_NotS, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_CPCONFIGURATIONLIST_WHERE);

				if (groupIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_7);

					sb.append(StringUtil.merge(groupIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_C_NOTS_COMPANYID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(status);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_C_NotS, finderArgs,
						count);
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

	private static final String _FINDER_COLUMN_G_C_NOTS_GROUPID_2 =
		"cpConfigurationList.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_NOTS_GROUPID_7 =
		"cpConfigurationList.groupId IN (";

	private static final String _FINDER_COLUMN_G_C_NOTS_COMPANYID_2 =
		"cpConfigurationList.companyId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_NOTS_STATUS_2 =
		"cpConfigurationList.status != ?";

	private FinderPath _finderPathFetchByERC_C;
	private UniquePersistenceFinder<CPConfigurationList>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp configuration list
	 * @throws NoSuchCPConfigurationListException if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = fetchByERC_C(
			externalReferenceCode, companyId);

		if (cpConfigurationList == null) {
			String message =
				_uniquePersistenceFinderByERC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPConfigurationListException(message);
		}

		return cpConfigurationList;
	}

	/**
	 * Returns the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration list, or <code>null</code> if a matching cp configuration list could not be found
	 */
	@Override
	public CPConfigurationList fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			return _uniquePersistenceFinderByERC_C.fetch(
				finderCache, new Object[] {externalReferenceCode, companyId},
				useFinderCache);
		}
	}

	/**
	 * Removes the cp configuration list where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the cp configuration list that was removed
	 */
	@Override
	public CPConfigurationList removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCPConfigurationListException {

		CPConfigurationList cpConfigurationList = findByERC_C(
			externalReferenceCode, companyId);

		return remove(cpConfigurationList);
	}

	/**
	 * Returns the number of cp configuration lists where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching cp configuration lists
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public CPConfigurationListPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPConfigurationList.class);

		setModelImplClass(CPConfigurationListImpl.class);
		setModelPKClass(long.class);

		setTable(CPConfigurationListTable.INSTANCE);
	}

	/**
	 * Caches the cp configuration list in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationList the cp configuration list
	 */
	@Override
	public void cacheResult(CPConfigurationList cpConfigurationList) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpConfigurationList.getCtCollectionId())) {

			entityCache.putResult(
				CPConfigurationListImpl.class,
				cpConfigurationList.getPrimaryKey(), cpConfigurationList);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					cpConfigurationList.getUuid(),
					cpConfigurationList.getGroupId()
				},
				cpConfigurationList);

			finderCache.putResult(
				_finderPathFetchByERC_C,
				new Object[] {
					cpConfigurationList.getExternalReferenceCode(),
					cpConfigurationList.getCompanyId()
				},
				cpConfigurationList);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp configuration lists in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationLists the cp configuration lists
	 */
	@Override
	public void cacheResult(List<CPConfigurationList> cpConfigurationLists) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpConfigurationLists.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPConfigurationList cpConfigurationList : cpConfigurationLists) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						cpConfigurationList.getCtCollectionId())) {

				if (entityCache.getResult(
						CPConfigurationListImpl.class,
						cpConfigurationList.getPrimaryKey()) == null) {

					cacheResult(cpConfigurationList);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		CPConfigurationListModelImpl cpConfigurationListModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpConfigurationListModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				cpConfigurationListModelImpl.getUuid(),
				cpConfigurationListModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, cpConfigurationListModelImpl);

			args = new Object[] {
				cpConfigurationListModelImpl.getExternalReferenceCode(),
				cpConfigurationListModelImpl.getCompanyId()
			};

			finderCache.putResult(
				_finderPathFetchByERC_C, args, cpConfigurationListModelImpl);
		}
	}

	/**
	 * Creates a new cp configuration list with the primary key. Does not add the cp configuration list to the database.
	 *
	 * @param CPConfigurationListId the primary key for the new cp configuration list
	 * @return the new cp configuration list
	 */
	@Override
	public CPConfigurationList create(long CPConfigurationListId) {
		CPConfigurationList cpConfigurationList = new CPConfigurationListImpl();

		cpConfigurationList.setNew(true);
		cpConfigurationList.setPrimaryKey(CPConfigurationListId);

		String uuid = PortalUUIDUtil.generate();

		cpConfigurationList.setUuid(uuid);

		cpConfigurationList.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpConfigurationList;
	}

	/**
	 * Removes the cp configuration list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPConfigurationListId the primary key of the cp configuration list
	 * @return the cp configuration list that was removed
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList remove(long CPConfigurationListId)
		throws NoSuchCPConfigurationListException {

		return remove((Serializable)CPConfigurationListId);
	}

	@Override
	protected CPConfigurationList removeImpl(
		CPConfigurationList cpConfigurationList) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpConfigurationList)) {
				cpConfigurationList = (CPConfigurationList)session.get(
					CPConfigurationListImpl.class,
					cpConfigurationList.getPrimaryKeyObj());
			}

			if ((cpConfigurationList != null) &&
				ctPersistenceHelper.isRemove(cpConfigurationList)) {

				session.delete(cpConfigurationList);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpConfigurationList != null) {
			clearCache(cpConfigurationList);
		}

		return cpConfigurationList;
	}

	@Override
	public CPConfigurationList updateImpl(
		CPConfigurationList cpConfigurationList) {

		boolean isNew = cpConfigurationList.isNew();

		if (!(cpConfigurationList instanceof CPConfigurationListModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpConfigurationList.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpConfigurationList);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpConfigurationList proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPConfigurationList implementation " +
					cpConfigurationList.getClass());
		}

		CPConfigurationListModelImpl cpConfigurationListModelImpl =
			(CPConfigurationListModelImpl)cpConfigurationList;

		if (Validator.isNull(cpConfigurationList.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpConfigurationList.setUuid(uuid);
		}

		if (Validator.isNull(cpConfigurationList.getExternalReferenceCode())) {
			cpConfigurationList.setExternalReferenceCode(
				cpConfigurationList.getUuid());
		}
		else {
			if (!Objects.equals(
					cpConfigurationListModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					cpConfigurationList.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = cpConfigurationList.getCompanyId();

					long groupId = cpConfigurationList.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = cpConfigurationList.getPrimaryKey();
					}

					try {
						cpConfigurationList.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								CPConfigurationList.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								cpConfigurationList.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			CPConfigurationList ercCPConfigurationList = fetchByERC_C(
				cpConfigurationList.getExternalReferenceCode(),
				cpConfigurationList.getCompanyId());

			if (isNew) {
				if (ercCPConfigurationList != null) {
					throw new DuplicateCPConfigurationListExternalReferenceCodeException(
						"Duplicate cp configuration list with external reference code " +
							cpConfigurationList.getExternalReferenceCode() +
								" and company " +
									cpConfigurationList.getCompanyId());
				}
			}
			else {
				if ((ercCPConfigurationList != null) &&
					(cpConfigurationList.getCPConfigurationListId() !=
						ercCPConfigurationList.getCPConfigurationListId())) {

					throw new DuplicateCPConfigurationListExternalReferenceCodeException(
						"Duplicate cp configuration list with external reference code " +
							cpConfigurationList.getExternalReferenceCode() +
								" and company " +
									cpConfigurationList.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpConfigurationList.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpConfigurationList.setCreateDate(date);
			}
			else {
				cpConfigurationList.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpConfigurationListModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpConfigurationList.setModifiedDate(date);
			}
			else {
				cpConfigurationList.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(cpConfigurationList)) {
				if (!isNew) {
					session.evict(
						CPConfigurationListImpl.class,
						cpConfigurationList.getPrimaryKeyObj());
				}

				session.save(cpConfigurationList);
			}
			else {
				cpConfigurationList = (CPConfigurationList)session.merge(
					cpConfigurationList);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPConfigurationListImpl.class, cpConfigurationListModelImpl, false,
			true);

		cacheUniqueFindersCache(cpConfigurationListModelImpl);

		if (isNew) {
			cpConfigurationList.setNew(false);
		}

		cpConfigurationList.resetOriginalValues();

		return cpConfigurationList;
	}

	/**
	 * Returns the cp configuration list with the primary key or throws a <code>NoSuchCPConfigurationListException</code> if it could not be found.
	 *
	 * @param CPConfigurationListId the primary key of the cp configuration list
	 * @return the cp configuration list
	 * @throws NoSuchCPConfigurationListException if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList findByPrimaryKey(long CPConfigurationListId)
		throws NoSuchCPConfigurationListException {

		return findByPrimaryKey((Serializable)CPConfigurationListId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the cp configuration list with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPConfigurationListId the primary key of the cp configuration list
	 * @return the cp configuration list, or <code>null</code> if a cp configuration list with the primary key could not be found
	 */
	@Override
	public CPConfigurationList fetchByPrimaryKey(long CPConfigurationListId) {
		return fetchByPrimaryKey((Serializable)CPConfigurationListId);
	}

	/**
	 * Returns all the cp configuration lists.
	 *
	 * @return the cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp configuration lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @return the range of cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp configuration lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration lists
	 * @param end the upper bound of the range of cp configuration lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp configuration lists
	 */
	@Override
	public List<CPConfigurationList> findAll(
		int start, int end,
		OrderByComparator<CPConfigurationList> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

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

			List<CPConfigurationList> list = null;

			if (useFinderCache) {
				list = (List<CPConfigurationList>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_CPCONFIGURATIONLIST);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_CPCONFIGURATIONLIST;

					sql = sql.concat(
						CPConfigurationListModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<CPConfigurationList>)QueryUtil.list(
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
	 * Removes all the cp configuration lists from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPConfigurationList cpConfigurationList : findAll()) {
			remove(cpConfigurationList);
		}
	}

	/**
	 * Returns the number of cp configuration lists.
	 *
	 * @return the number of cp configuration lists
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPConfigurationList.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_CPCONFIGURATIONLIST);

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
		return "CPConfigurationListId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPCONFIGURATIONLIST;
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
		return CPConfigurationListModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPConfigurationList";
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
		ctStrictColumnNames.add("externalReferenceCode");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("parentCPConfigurationListId");
		ctMergeColumnNames.add("master");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("priority");
		ctMergeColumnNames.add("displayDate");
		ctMergeColumnNames.add("expirationDate");
		ctMergeColumnNames.add("lastPublishDate");
		ctMergeColumnNames.add("status");
		ctMergeColumnNames.add("statusByUserId");
		ctMergeColumnNames.add("statusByUserName");
		ctMergeColumnNames.add("statusDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPConfigurationListId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "companyId"});
	}

	/**
	 * Initializes the cp configuration list persistence.
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
			_SQL_SELECT_CPCONFIGURATIONLIST_WHERE,
			_SQL_COUNT_CPCONFIGURATIONLIST_WHERE,
			CPConfigurationListModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpConfigurationList.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, CPConfigurationList::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G,
			_SQL_SELECT_CPCONFIGURATIONLIST_WHERE,
			new FinderColumn<>(
				"cpConfigurationList.", "uuid", FinderColumn.Type.STRING, "=",
				true, false, CPConfigurationList::getUuid),
			new FinderColumn<>(
				"cpConfigurationList.", "groupId", FinderColumn.Type.LONG, "=",
				true, true, CPConfigurationList::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_CPCONFIGURATIONLIST_WHERE,
				_SQL_COUNT_CPCONFIGURATIONLIST_WHERE,
				CPConfigurationListModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpConfigurationList.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, CPConfigurationList::getUuid),
				new FinderColumn<>(
					"cpConfigurationList.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, CPConfigurationList::getCompanyId));

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
				_SQL_SELECT_CPCONFIGURATIONLIST_WHERE,
				_SQL_COUNT_CPCONFIGURATIONLIST_WHERE,
				CPConfigurationListModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpConfigurationList.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, CPConfigurationList::getCompanyId));

		_finderPathWithPaginationFindByParentCPConfigurationListId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByParentCPConfigurationListId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"parentCPConfigurationListId"}, true);

		_finderPathWithoutPaginationFindByParentCPConfigurationListId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByParentCPConfigurationListId",
				new String[] {Long.class.getName()},
				new String[] {"parentCPConfigurationListId"}, true);

		_finderPathCountByParentCPConfigurationListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByParentCPConfigurationListId",
			new String[] {Long.class.getName()},
			new String[] {"parentCPConfigurationListId"}, false);

		_collectionPersistenceFinderByParentCPConfigurationListId =
			new CollectionPersistenceFinder<>(
				this,
				_finderPathWithPaginationFindByParentCPConfigurationListId,
				_finderPathWithoutPaginationFindByParentCPConfigurationListId,
				_finderPathCountByParentCPConfigurationListId,
				_SQL_SELECT_CPCONFIGURATIONLIST_WHERE,
				_SQL_COUNT_CPCONFIGURATIONLIST_WHERE,
				CPConfigurationListModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpConfigurationList.", "parentCPConfigurationListId",
					FinderColumn.Type.LONG, "=", true, true,
					CPConfigurationList::getParentCPConfigurationListId));

		_finderPathWithPaginationFindByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId"}, true);

		_finderPathWithoutPaginationFindByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "companyId"}, true);

		_finderPathCountByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "companyId"}, false);

		_finderPathWithPaginationCountByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "companyId"}, false);

		_finderPathWithPaginationFindByG_M = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_M",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "master"}, true);

		_finderPathWithoutPaginationFindByG_M = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_M",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "master"}, true);

		_finderPathCountByG_M = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_M",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "master"}, false);

		_collectionPersistenceFinderByG_M = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_M,
			_finderPathWithoutPaginationFindByG_M, _finderPathCountByG_M,
			_SQL_SELECT_CPCONFIGURATIONLIST_WHERE,
			_SQL_COUNT_CPCONFIGURATIONLIST_WHERE,
			CPConfigurationListModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpConfigurationList.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, CPConfigurationList::getGroupId),
			new FinderColumn<>(
				"cpConfigurationList.", "master", FinderColumn.Type.BOOLEAN,
				"=", true, true, CPConfigurationList::isMaster));

		_finderPathWithPaginationFindByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"displayDate", "status"}, true);

		_finderPathWithPaginationCountByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"displayDate", "status"}, false);

		_collectionPersistenceFinderByLtD_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByLtD_S, null,
			_finderPathWithPaginationCountByLtD_S,
			_SQL_SELECT_CPCONFIGURATIONLIST_WHERE,
			_SQL_COUNT_CPCONFIGURATIONLIST_WHERE,
			CPConfigurationListModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpConfigurationList.", "displayDate", FinderColumn.Type.DATE,
				"<", true, false, CPConfigurationList::getDisplayDate),
			new FinderColumn<>(
				"cpConfigurationList.", "status", FinderColumn.Type.INTEGER,
				"=", true, true, CPConfigurationList::getStatus));

		_finderPathWithPaginationFindByG_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, true);

		_finderPathWithoutPaginationFindByG_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, true);

		_finderPathCountByG_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, false);

		_finderPathWithPaginationCountByG_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, false);

		_finderPathWithPaginationFindByG_C_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, true);

		_finderPathWithPaginationCountByG_C_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "companyId", "status"}, false);

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_C,
			_SQL_SELECT_CPCONFIGURATIONLIST_WHERE,
			new FinderColumn<>(
				"cpConfigurationList.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				CPConfigurationList::getExternalReferenceCode),
			new FinderColumn<>(
				"cpConfigurationList.", "companyId", FinderColumn.Type.LONG,
				"=", true, true, CPConfigurationList::getCompanyId));

		CPConfigurationListUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPConfigurationListUtil.setPersistence(null);

		entityCache.removeCache(CPConfigurationListImpl.class.getName());
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

	private static final String _SQL_SELECT_CPCONFIGURATIONLIST =
		"SELECT cpConfigurationList FROM CPConfigurationList cpConfigurationList";

	private static final String _SQL_SELECT_CPCONFIGURATIONLIST_WHERE =
		"SELECT cpConfigurationList FROM CPConfigurationList cpConfigurationList WHERE ";

	private static final String _SQL_COUNT_CPCONFIGURATIONLIST =
		"SELECT COUNT(cpConfigurationList) FROM CPConfigurationList cpConfigurationList";

	private static final String _SQL_COUNT_CPCONFIGURATIONLIST_WHERE =
		"SELECT COUNT(cpConfigurationList) FROM CPConfigurationList cpConfigurationList WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cpConfigurationList.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPConfigurationList exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPConfigurationListPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:681467938