/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.model.impl.CPDefinitionImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionModelImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionLocalizationPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionUtil;
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
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the cp definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CPDefinitionPersistence.class)
public class CPDefinitionPersistenceImpl
	extends BasePersistenceImpl<CPDefinition, NoSuchCPDefinitionException>
	implements CPDefinitionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDefinitionUtil</code> to access the cp definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDefinitionImpl.class.getName();

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
	private CollectionPersistenceFinder<CPDefinition>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the cp definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByUuid_First(
			String uuid, OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByUuid_First(uuid, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		throw new NoSuchCPDefinitionException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByUuid_First(
		String uuid, OrderByComparator<CPDefinition> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of cp definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<CPDefinition>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the cp definition where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPDefinitionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByUUID_G(uuid, groupId);

		if (cpDefinition == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPDefinitionException(message);
		}

		return cpDefinition;
	}

	/**
	 * Returns the cp definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the cp definition where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition that was removed
	 */
	@Override
	public CPDefinition removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = findByUUID_G(uuid, groupId);

		return remove(cpDefinition);
	}

	/**
	 * Returns the number of cp definitions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<CPDefinition>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		throw new NoSuchCPDefinitionException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinition> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<CPDefinition>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the cp definitions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				finderCache, new Object[] {groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByGroupId_First(
			long groupId, OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByGroupId_First(
			groupId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		throw new NoSuchCPDefinitionException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByGroupId_First(
		long groupId, OrderByComparator<CPDefinition> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Returns all the cp definitions that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp definitions that the user has permission to view
	 */
	@Override
	public List<CPDefinition> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions that the user has permission to view
	 */
	@Override
	public List<CPDefinition> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions that the user has permission to view
	 */
	@Override
	public List<CPDefinition> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_CPDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_CPDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_CPDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CPDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CPDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CPDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CPDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<CPDefinition>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the cp definitions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of cp definitions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				finderCache, new Object[] {groupId});
		}
	}

	/**
	 * Returns the number of cp definitions that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp definitions that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CPDefinition> cpDefinitions = findByGroupId(groupId);

			cpDefinitions = InlineSQLHelperUtil.filter(cpDefinitions, groupId);

			return cpDefinitions.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_CPDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CPDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"cpDefinition.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<CPDefinition>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the cp definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				finderCache, new Object[] {companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByCompanyId_First(
			long companyId, OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		throw new NoSuchCPDefinitionException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByCompanyId_First(
		long companyId, OrderByComparator<CPDefinition> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of cp definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				finderCache, new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCProductId;
	private FinderPath _finderPathWithoutPaginationFindByCProductId;
	private FinderPath _finderPathCountByCProductId;
	private CollectionPersistenceFinder<CPDefinition>
		_collectionPersistenceFinderByCProductId;

	/**
	 * Returns all the cp definitions where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCProductId(long CProductId) {
		return findByCProductId(
			CProductId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCProductId(
		long CProductId, int start, int end) {

		return findByCProductId(CProductId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByCProductId(
			CProductId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByCProductId.find(
				finderCache, new Object[] {CProductId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByCProductId_First(
			long CProductId, OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByCProductId_First(
			CProductId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		throw new NoSuchCPDefinitionException(
			_collectionPersistenceFinderByCProductId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {CProductId}));
	}

	/**
	 * Returns the first cp definition in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByCProductId_First(
		long CProductId, OrderByComparator<CPDefinition> orderByComparator) {

		return _collectionPersistenceFinderByCProductId.fetchFirst(
			finderCache, new Object[] {CProductId}, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where CProductId = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 */
	@Override
	public void removeByCProductId(long CProductId) {
		_collectionPersistenceFinderByCProductId.remove(
			finderCache, new Object[] {CProductId});
	}

	/**
	 * Returns the number of cp definitions where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByCProductId(long CProductId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByCProductId.count(
				finderCache, new Object[] {CProductId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCPTaxCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByCPTaxCategoryId;
	private FinderPath _finderPathCountByCPTaxCategoryId;
	private CollectionPersistenceFinder<CPDefinition>
		_collectionPersistenceFinderByCPTaxCategoryId;

	/**
	 * Returns all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCPTaxCategoryId(long CPTaxCategoryId) {
		return findByCPTaxCategoryId(
			CPTaxCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end) {

		return findByCPTaxCategoryId(CPTaxCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByCPTaxCategoryId(
			CPTaxCategoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByCPTaxCategoryId.find(
				finderCache, new Object[] {CPTaxCategoryId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByCPTaxCategoryId_First(
			long CPTaxCategoryId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByCPTaxCategoryId_First(
			CPTaxCategoryId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		throw new NoSuchCPDefinitionException(
			_collectionPersistenceFinderByCPTaxCategoryId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {CPTaxCategoryId}));
	}

	/**
	 * Returns the first cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByCPTaxCategoryId_First(
		long CPTaxCategoryId,
		OrderByComparator<CPDefinition> orderByComparator) {

		return _collectionPersistenceFinderByCPTaxCategoryId.fetchFirst(
			finderCache, new Object[] {CPTaxCategoryId}, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where CPTaxCategoryId = &#63; from the database.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 */
	@Override
	public void removeByCPTaxCategoryId(long CPTaxCategoryId) {
		_collectionPersistenceFinderByCPTaxCategoryId.remove(
			finderCache, new Object[] {CPTaxCategoryId});
	}

	/**
	 * Returns the number of cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByCPTaxCategoryId(long CPTaxCategoryId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByCPTaxCategoryId.count(
				finderCache, new Object[] {CPTaxCategoryId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_SE;
	private FinderPath _finderPathWithoutPaginationFindByG_SE;
	private FinderPath _finderPathCountByG_SE;
	private CollectionPersistenceFinder<CPDefinition>
		_collectionPersistenceFinderByG_SE;

	/**
	 * Returns all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled) {

		return findByG_SE(
			groupId, subscriptionEnabled, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled, int start, int end) {

		return findByG_SE(groupId, subscriptionEnabled, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByG_SE(
			groupId, subscriptionEnabled, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByG_SE.find(
				finderCache, new Object[] {groupId, subscriptionEnabled}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByG_SE_First(
			long groupId, boolean subscriptionEnabled,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByG_SE_First(
			groupId, subscriptionEnabled, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		throw new NoSuchCPDefinitionException(
			_collectionPersistenceFinderByG_SE.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, subscriptionEnabled}));
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByG_SE_First(
		long groupId, boolean subscriptionEnabled,
		OrderByComparator<CPDefinition> orderByComparator) {

		return _collectionPersistenceFinderByG_SE.fetchFirst(
			finderCache, new Object[] {groupId, subscriptionEnabled},
			orderByComparator);
	}

	/**
	 * Returns all the cp definitions that the user has permission to view where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @return the matching cp definitions that the user has permission to view
	 */
	@Override
	public List<CPDefinition> filterFindByG_SE(
		long groupId, boolean subscriptionEnabled) {

		return filterFindByG_SE(
			groupId, subscriptionEnabled, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp definitions that the user has permission to view where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions that the user has permission to view
	 */
	@Override
	public List<CPDefinition> filterFindByG_SE(
		long groupId, boolean subscriptionEnabled, int start, int end) {

		return filterFindByG_SE(groupId, subscriptionEnabled, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions that the user has permissions to view where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions that the user has permission to view
	 */
	@Override
	public List<CPDefinition> filterFindByG_SE(
		long groupId, boolean subscriptionEnabled, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_SE(
				groupId, subscriptionEnabled, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_SE(
					groupId, subscriptionEnabled, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_CPDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_CPDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_SE_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_SE_SUBSCRIPTIONENABLED_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_CPDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CPDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CPDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CPDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CPDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(subscriptionEnabled);

			return (List<CPDefinition>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 */
	@Override
	public void removeByG_SE(long groupId, boolean subscriptionEnabled) {
		_collectionPersistenceFinderByG_SE.remove(
			finderCache, new Object[] {groupId, subscriptionEnabled});
	}

	/**
	 * Returns the number of cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByG_SE(long groupId, boolean subscriptionEnabled) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByG_SE.count(
				finderCache, new Object[] {groupId, subscriptionEnabled});
		}
	}

	/**
	 * Returns the number of cp definitions that the user has permission to view where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @return the number of matching cp definitions that the user has permission to view
	 */
	@Override
	public int filterCountByG_SE(long groupId, boolean subscriptionEnabled) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_SE(groupId, subscriptionEnabled);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CPDefinition> cpDefinitions = findByG_SE(
				groupId, subscriptionEnabled);

			cpDefinitions = InlineSQLHelperUtil.filter(cpDefinitions, groupId);

			return cpDefinitions.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_CPDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_G_SE_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_SE_SUBSCRIPTIONENABLED_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CPDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(subscriptionEnabled);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_SE_GROUPID_2 =
		"cpDefinition.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_SE_SUBSCRIPTIONENABLED_2 =
		"cpDefinition.subscriptionEnabled = ?";

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;
	private CollectionPersistenceFinder<CPDefinition>
		_collectionPersistenceFinderByG_S;

	/**
	 * Returns all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_S(long groupId, int status) {
		return findByG_S(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_S(
		long groupId, int status, int start, int end) {

		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByG_S(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByG_S.find(
				finderCache, new Object[] {groupId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByG_S_First(
			long groupId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByG_S_First(
			groupId, status, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		throw new NoSuchCPDefinitionException(
			_collectionPersistenceFinderByG_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, status}));
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByG_S_First(
		long groupId, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		return _collectionPersistenceFinderByG_S.fetchFirst(
			finderCache, new Object[] {groupId, status}, orderByComparator);
	}

	/**
	 * Returns all the cp definitions that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching cp definitions that the user has permission to view
	 */
	@Override
	public List<CPDefinition> filterFindByG_S(long groupId, int status) {
		return filterFindByG_S(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions that the user has permission to view
	 */
	@Override
	public List<CPDefinition> filterFindByG_S(
		long groupId, int status, int start, int end) {

		return filterFindByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions that the user has permission to view
	 */
	@Override
	public List<CPDefinition> filterFindByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S(groupId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_S(
					groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_CPDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_CPDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_CPDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CPDefinitionModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CPDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CPDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CPDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

			return (List<CPDefinition>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the cp definitions where groupId = &#63; and status = &#63; from the database.
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
	 * Returns the number of cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByG_S.count(
				finderCache, new Object[] {groupId, status});
		}
	}

	/**
	 * Returns the number of cp definitions that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching cp definitions that the user has permission to view
	 */
	@Override
	public int filterCountByG_S(long groupId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<CPDefinition> cpDefinitions = findByG_S(groupId, status);

			cpDefinitions = InlineSQLHelperUtil.filter(cpDefinitions, groupId);

			return cpDefinitions.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_CPDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CPDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"cpDefinition.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_STATUS_2 =
		"cpDefinition.status = ?";

	private FinderPath _finderPathFetchByC_V;
	private UniquePersistenceFinder<CPDefinition> _uniquePersistenceFinderByC_V;

	/**
	 * Returns the cp definition where CProductId = &#63; and version = &#63; or throws a <code>NoSuchCPDefinitionException</code> if it could not be found.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByC_V(long CProductId, int version)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByC_V(CProductId, version);

		if (cpDefinition == null) {
			String message =
				_uniquePersistenceFinderByC_V.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {CProductId, version});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPDefinitionException(message);
		}

		return cpDefinition;
	}

	/**
	 * Returns the cp definition where CProductId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByC_V(long CProductId, int version) {
		return fetchByC_V(CProductId, version, true);
	}

	/**
	 * Returns the cp definition where CProductId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByC_V(
		long CProductId, int version, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _uniquePersistenceFinderByC_V.fetch(
				finderCache, new Object[] {CProductId, version},
				useFinderCache);
		}
	}

	/**
	 * Removes the cp definition where CProductId = &#63; and version = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the cp definition that was removed
	 */
	@Override
	public CPDefinition removeByC_V(long CProductId, int version)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = findByC_V(CProductId, version);

		return remove(cpDefinition);
	}

	/**
	 * Returns the number of cp definitions where CProductId = &#63; and version = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByC_V(long CProductId, int version) {
		return _uniquePersistenceFinderByC_V.count(
			finderCache, new Object[] {CProductId, version});
	}

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;
	private CollectionPersistenceFinder<CPDefinition>
		_collectionPersistenceFinderByC_S;

	/**
	 * Returns all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByC_S(long CProductId, int status) {
		return findByC_S(
			CProductId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByC_S(
		long CProductId, int status, int start, int end) {

		return findByC_S(CProductId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByC_S(
		long CProductId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByC_S(
			CProductId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByC_S(
		long CProductId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByC_S.find(
				finderCache, new Object[] {CProductId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByC_S_First(
			long CProductId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByC_S_First(
			CProductId, status, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		throw new NoSuchCPDefinitionException(
			_collectionPersistenceFinderByC_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {CProductId, status}));
	}

	/**
	 * Returns the first cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByC_S_First(
		long CProductId, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		return _collectionPersistenceFinderByC_S.fetchFirst(
			finderCache, new Object[] {CProductId, status}, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where CProductId = &#63; and status = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long CProductId, int status) {
		_collectionPersistenceFinderByC_S.remove(
			finderCache, new Object[] {CProductId, status});
	}

	/**
	 * Returns the number of cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByC_S(long CProductId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByC_S.count(
				finderCache, new Object[] {CProductId, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;
	private CollectionPersistenceFinder<CPDefinition>
		_collectionPersistenceFinderByLtD_S;

	/**
	 * Returns all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByLtD_S.find(
				finderCache, new Object[] {displayDate, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		throw new NoSuchCPDefinitionException(
			_collectionPersistenceFinderByLtD_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {displayDate, status}));
	}

	/**
	 * Returns the first cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		return _collectionPersistenceFinderByLtD_S.fetchFirst(
			finderCache, new Object[] {displayDate, status}, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where displayDate &lt; &#63; and status = &#63; from the database.
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
	 * Returns the number of cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			return _collectionPersistenceFinderByLtD_S.count(
				finderCache, new Object[] {displayDate, status});
		}
	}

	public CPDefinitionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"deliverySubscriptionTypeSettings", "deliverySubTypeSettings");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPDefinition.class);

		setModelImplClass(CPDefinitionImpl.class);
		setModelPKClass(long.class);

		setTable(CPDefinitionTable.INSTANCE);
	}

	/**
	 * Caches the cp definition in the entity cache if it is enabled.
	 *
	 * @param cpDefinition the cp definition
	 */
	@Override
	public void cacheResult(CPDefinition cpDefinition) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpDefinition.getCtCollectionId())) {

			entityCache.putResult(
				CPDefinitionImpl.class, cpDefinition.getPrimaryKey(),
				cpDefinition);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					cpDefinition.getUuid(), cpDefinition.getGroupId()
				},
				cpDefinition);

			finderCache.putResult(
				_finderPathFetchByC_V,
				new Object[] {
					cpDefinition.getCProductId(), cpDefinition.getVersion()
				},
				cpDefinition);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp definitions in the entity cache if it is enabled.
	 *
	 * @param cpDefinitions the cp definitions
	 */
	@Override
	public void cacheResult(List<CPDefinition> cpDefinitions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpDefinitions.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPDefinition cpDefinition : cpDefinitions) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						cpDefinition.getCtCollectionId())) {

				if (entityCache.getResult(
						CPDefinitionImpl.class, cpDefinition.getPrimaryKey()) ==
							null) {

					cacheResult(cpDefinition);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionModelImpl cpDefinitionModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpDefinitionModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				cpDefinitionModelImpl.getUuid(),
				cpDefinitionModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, cpDefinitionModelImpl);

			args = new Object[] {
				cpDefinitionModelImpl.getCProductId(),
				cpDefinitionModelImpl.getVersion()
			};

			finderCache.putResult(
				_finderPathFetchByC_V, args, cpDefinitionModelImpl);
		}
	}

	/**
	 * Creates a new cp definition with the primary key. Does not add the cp definition to the database.
	 *
	 * @param CPDefinitionId the primary key for the new cp definition
	 * @return the new cp definition
	 */
	@Override
	public CPDefinition create(long CPDefinitionId) {
		CPDefinition cpDefinition = new CPDefinitionImpl();

		cpDefinition.setNew(true);
		cpDefinition.setPrimaryKey(CPDefinitionId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinition.setUuid(uuid);

		cpDefinition.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpDefinition;
	}

	/**
	 * Removes the cp definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionId the primary key of the cp definition
	 * @return the cp definition that was removed
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition remove(long CPDefinitionId)
		throws NoSuchCPDefinitionException {

		return remove((Serializable)CPDefinitionId);
	}

	@Override
	protected CPDefinition removeImpl(CPDefinition cpDefinition) {
		cpDefinitionLocalizationPersistence.removeByCPDefinitionId(
			cpDefinition.getCPDefinitionId());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinition)) {
				cpDefinition = (CPDefinition)session.get(
					CPDefinitionImpl.class, cpDefinition.getPrimaryKeyObj());
			}

			if ((cpDefinition != null) &&
				ctPersistenceHelper.isRemove(cpDefinition)) {

				session.delete(cpDefinition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinition != null) {
			clearCache(cpDefinition);
		}

		return cpDefinition;
	}

	@Override
	public CPDefinition updateImpl(CPDefinition cpDefinition) {
		boolean isNew = cpDefinition.isNew();

		if (!(cpDefinition instanceof CPDefinitionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinition.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpDefinition);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinition proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinition implementation " +
					cpDefinition.getClass());
		}

		CPDefinitionModelImpl cpDefinitionModelImpl =
			(CPDefinitionModelImpl)cpDefinition;

		if (Validator.isNull(cpDefinition.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinition.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpDefinition.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinition.setCreateDate(date);
			}
			else {
				cpDefinition.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!cpDefinitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinition.setModifiedDate(date);
			}
			else {
				cpDefinition.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(cpDefinition)) {
				if (!isNew) {
					session.evict(
						CPDefinitionImpl.class,
						cpDefinition.getPrimaryKeyObj());
				}

				session.save(cpDefinition);
			}
			else {
				cpDefinition = (CPDefinition)session.merge(cpDefinition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDefinitionImpl.class, cpDefinitionModelImpl, false, true);

		cacheUniqueFindersCache(cpDefinitionModelImpl);

		if (isNew) {
			cpDefinition.setNew(false);
		}

		cpDefinition.resetOriginalValues();

		return cpDefinition;
	}

	/**
	 * Returns the cp definition with the primary key or throws a <code>NoSuchCPDefinitionException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the primary key of the cp definition
	 * @return the cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition findByPrimaryKey(long CPDefinitionId)
		throws NoSuchCPDefinitionException {

		return findByPrimaryKey((Serializable)CPDefinitionId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the cp definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionId the primary key of the cp definition
	 * @return the cp definition, or <code>null</code> if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition fetchByPrimaryKey(long CPDefinitionId) {
		return fetchByPrimaryKey((Serializable)CPDefinitionId);
	}

	/**
	 * Returns all the cp definitions.
	 *
	 * @return the cp definitions
	 */
	@Override
	public List<CPDefinition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of cp definitions
	 */
	@Override
	public List<CPDefinition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definitions
	 */
	@Override
	public List<CPDefinition> findAll(
		int start, int end, OrderByComparator<CPDefinition> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definitions
	 */
	@Override
	public List<CPDefinition> findAll(
		int start, int end, OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

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

			List<CPDefinition> list = null;

			if (useFinderCache) {
				list = (List<CPDefinition>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_CPDEFINITION);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_CPDEFINITION;

					sql = sql.concat(CPDefinitionModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<CPDefinition>)QueryUtil.list(
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
	 * Removes all the cp definitions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinition cpDefinition : findAll()) {
			remove(cpDefinition);
		}
	}

	/**
	 * Returns the number of cp definitions.
	 *
	 * @return the number of cp definitions
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinition.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_CPDEFINITION);

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
		return "CPDefinitionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDEFINITION;
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
		return CPDefinitionModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPDefinition";
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
		ctStrictColumnNames.add("defaultLanguageId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("CProductId");
		ctMergeColumnNames.add("CPTaxCategoryId");
		ctMergeColumnNames.add("accountGroupFilterEnabled");
		ctMergeColumnNames.add("availableIndividually");
		ctMergeColumnNames.add("channelFilterEnabled");
		ctMergeColumnNames.add("DDMStructureKey");
		ctMergeColumnNames.add("deliveryMaxSubscriptionCycles");
		ctMergeColumnNames.add("deliverySubscriptionEnabled");
		ctMergeColumnNames.add("deliverySubscriptionLength");
		ctMergeColumnNames.add("deliverySubscriptionType");
		ctMergeColumnNames.add("deliverySubTypeSettings");
		ctMergeColumnNames.add("depth");
		ctMergeColumnNames.add("displayDate");
		ctMergeColumnNames.add("expirationDate");
		ctMergeColumnNames.add("freeShipping");
		ctMergeColumnNames.add("height");
		ctMergeColumnNames.add("ignoreSKUCombinations");
		ctMergeColumnNames.add("maxSubscriptionCycles");
		ctMergeColumnNames.add("productTypeName");
		ctMergeColumnNames.add("published");
		ctMergeColumnNames.add("shipSeparately");
		ctMergeColumnNames.add("shippable");
		ctMergeColumnNames.add("shippingExtraPrice");
		ctMergeColumnNames.add("subscriptionEnabled");
		ctMergeColumnNames.add("subscriptionLength");
		ctMergeColumnNames.add("subscriptionType");
		ctMergeColumnNames.add("subscriptionTypeSettings");
		ctMergeColumnNames.add("taxExempt");
		ctMergeColumnNames.add("telcoOrElectronics");
		ctMergeColumnNames.add("version");
		ctMergeColumnNames.add("weight");
		ctMergeColumnNames.add("width");
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
			CTColumnResolutionType.PK, Collections.singleton("CPDefinitionId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"CProductId", "version"});
	}

	/**
	 * Initializes the cp definition persistence.
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
			_SQL_SELECT_CPDEFINITION_WHERE, _SQL_COUNT_CPDEFINITION_WHERE,
			CPDefinitionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpDefinition.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, CPDefinition::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_CPDEFINITION_WHERE,
			new FinderColumn<>(
				"cpDefinition.", "uuid", FinderColumn.Type.STRING, "=", true,
				false, CPDefinition::getUuid),
			new FinderColumn<>(
				"cpDefinition.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, CPDefinition::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_CPDEFINITION_WHERE,
				_SQL_COUNT_CPDEFINITION_WHERE,
				CPDefinitionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpDefinition.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, CPDefinition::getUuid),
				new FinderColumn<>(
					"cpDefinition.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, CPDefinition::getCompanyId));

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
				_finderPathCountByGroupId, _SQL_SELECT_CPDEFINITION_WHERE,
				_SQL_COUNT_CPDEFINITION_WHERE,
				CPDefinitionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpDefinition.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, CPDefinition::getGroupId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_CPDEFINITION_WHERE,
				_SQL_COUNT_CPDEFINITION_WHERE,
				CPDefinitionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpDefinition.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, CPDefinition::getCompanyId));

		_finderPathWithPaginationFindByCProductId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCProductId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CProductId"}, true);

		_finderPathWithoutPaginationFindByCProductId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCProductId",
			new String[] {Long.class.getName()}, new String[] {"CProductId"},
			true);

		_finderPathCountByCProductId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCProductId",
			new String[] {Long.class.getName()}, new String[] {"CProductId"},
			false);

		_collectionPersistenceFinderByCProductId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCProductId,
				_finderPathWithoutPaginationFindByCProductId,
				_finderPathCountByCProductId, _SQL_SELECT_CPDEFINITION_WHERE,
				_SQL_COUNT_CPDEFINITION_WHERE,
				CPDefinitionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpDefinition.", "CProductId", FinderColumn.Type.LONG, "=",
					true, true, CPDefinition::getCProductId));

		_finderPathWithPaginationFindByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPTaxCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPTaxCategoryId"}, true);

		_finderPathWithoutPaginationFindByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPTaxCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"CPTaxCategoryId"}, true);

		_finderPathCountByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPTaxCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"CPTaxCategoryId"}, false);

		_collectionPersistenceFinderByCPTaxCategoryId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCPTaxCategoryId,
				_finderPathWithoutPaginationFindByCPTaxCategoryId,
				_finderPathCountByCPTaxCategoryId,
				_SQL_SELECT_CPDEFINITION_WHERE, _SQL_COUNT_CPDEFINITION_WHERE,
				CPDefinitionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpDefinition.", "CPTaxCategoryId", FinderColumn.Type.LONG,
					"=", true, true, CPDefinition::getCPTaxCategoryId));

		_finderPathWithPaginationFindByG_SE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_SE",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "subscriptionEnabled"}, true);

		_finderPathWithoutPaginationFindByG_SE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_SE",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "subscriptionEnabled"}, true);

		_finderPathCountByG_SE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_SE",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "subscriptionEnabled"}, false);

		_collectionPersistenceFinderByG_SE = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_SE,
			_finderPathWithoutPaginationFindByG_SE, _finderPathCountByG_SE,
			_SQL_SELECT_CPDEFINITION_WHERE, _SQL_COUNT_CPDEFINITION_WHERE,
			CPDefinitionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpDefinition.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, CPDefinition::getGroupId),
			new FinderColumn<>(
				"cpDefinition.", "subscriptionEnabled",
				FinderColumn.Type.BOOLEAN, "=", true, true,
				CPDefinition::isSubscriptionEnabled));

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
			_SQL_SELECT_CPDEFINITION_WHERE, _SQL_COUNT_CPDEFINITION_WHERE,
			CPDefinitionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpDefinition.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, CPDefinition::getGroupId),
			new FinderColumn<>(
				"cpDefinition.", "status", FinderColumn.Type.INTEGER, "=", true,
				true, CPDefinition::getStatus));

		_finderPathFetchByC_V = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_V",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"CProductId", "version"}, true);

		_uniquePersistenceFinderByC_V = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_V, _SQL_SELECT_CPDEFINITION_WHERE,
			new FinderColumn<>(
				"cpDefinition.", "CProductId", FinderColumn.Type.LONG, "=",
				true, false, CPDefinition::getCProductId),
			new FinderColumn<>(
				"cpDefinition.", "version", FinderColumn.Type.INTEGER, "=",
				true, true, CPDefinition::getVersion));

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CProductId", "status"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"CProductId", "status"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"CProductId", "status"}, false);

		_collectionPersistenceFinderByC_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_S,
			_finderPathWithoutPaginationFindByC_S, _finderPathCountByC_S,
			_SQL_SELECT_CPDEFINITION_WHERE, _SQL_COUNT_CPDEFINITION_WHERE,
			CPDefinitionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpDefinition.", "CProductId", FinderColumn.Type.LONG, "=",
				true, false, CPDefinition::getCProductId),
			new FinderColumn<>(
				"cpDefinition.", "status", FinderColumn.Type.INTEGER, "=", true,
				true, CPDefinition::getStatus));

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
			_SQL_SELECT_CPDEFINITION_WHERE, _SQL_COUNT_CPDEFINITION_WHERE,
			CPDefinitionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpDefinition.", "displayDate", FinderColumn.Type.DATE, "<",
				true, false, CPDefinition::getDisplayDate),
			new FinderColumn<>(
				"cpDefinition.", "status", FinderColumn.Type.INTEGER, "=", true,
				true, CPDefinition::getStatus));

		CPDefinitionUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPDefinitionUtil.setPersistence(null);

		entityCache.removeCache(CPDefinitionImpl.class.getName());
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

	@Reference
	protected CPDefinitionLocalizationPersistence
		cpDefinitionLocalizationPersistence;

	private static final String _SQL_SELECT_CPDEFINITION =
		"SELECT cpDefinition FROM CPDefinition cpDefinition";

	private static final String _SQL_SELECT_CPDEFINITION_WHERE =
		"SELECT cpDefinition FROM CPDefinition cpDefinition WHERE ";

	private static final String _SQL_COUNT_CPDEFINITION =
		"SELECT COUNT(cpDefinition) FROM CPDefinition cpDefinition";

	private static final String _SQL_COUNT_CPDEFINITION_WHERE =
		"SELECT COUNT(cpDefinition) FROM CPDefinition cpDefinition WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"cpDefinition.CPDefinitionId";

	private static final String _FILTER_SQL_SELECT_CPDEFINITION_WHERE =
		"SELECT DISTINCT {cpDefinition.*} FROM CPDefinition cpDefinition WHERE ";

	private static final String
		_FILTER_SQL_SELECT_CPDEFINITION_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CPDefinition.*} FROM (SELECT DISTINCT cpDefinition.CPDefinitionId FROM CPDefinition cpDefinition WHERE ";

	private static final String
		_FILTER_SQL_SELECT_CPDEFINITION_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CPDefinition ON TEMP_TABLE.CPDefinitionId = CPDefinition.CPDefinitionId";

	private static final String _FILTER_SQL_COUNT_CPDEFINITION_WHERE =
		"SELECT COUNT(DISTINCT cpDefinition.CPDefinitionId) AS COUNT_VALUE FROM CPDefinition cpDefinition WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "cpDefinition";

	private static final String _FILTER_ENTITY_TABLE = "CPDefinition";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDefinition.";

	private static final String _ORDER_BY_ENTITY_TABLE = "CPDefinition.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDefinition exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "deliverySubscriptionTypeSettings"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-863284287