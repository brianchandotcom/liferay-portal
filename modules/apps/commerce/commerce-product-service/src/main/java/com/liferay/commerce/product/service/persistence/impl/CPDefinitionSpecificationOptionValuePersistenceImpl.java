/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.DuplicateCPDefinitionSpecificationOptionValueExternalReferenceCodeException;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueTable;
import com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionSpecificationOptionValuePersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionSpecificationOptionValueUtil;
import com.liferay.commerce.product.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
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
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the cp definition specification option value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CPDefinitionSpecificationOptionValuePersistence.class)
public class CPDefinitionSpecificationOptionValuePersistenceImpl
	extends BasePersistenceImpl
		<CPDefinitionSpecificationOptionValue,
		 NoSuchCPDefinitionSpecificationOptionValueException>
	implements CPDefinitionSpecificationOptionValuePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDefinitionSpecificationOptionValueUtil</code> to access the cp definition specification option value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDefinitionSpecificationOptionValueImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<CPDefinitionSpecificationOptionValue>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the cp definition specification option values where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByUuid_First(
			String uuid,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByUuid_First(
				uuid, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByUuid_First(
		String uuid,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the cp definition specification option values where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of cp definition specification option values where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<CPDefinitionSpecificationOptionValue>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPDefinitionSpecificationOptionValueException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByUUID_G(
			String uuid, long groupId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByUUID_G(uuid, groupId);

		if (cpDefinitionSpecificationOptionValue == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPDefinitionSpecificationOptionValueException(
				message);
		}

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the cp definition specification option value where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition specification option value that was removed
	 */
	@Override
	public CPDefinitionSpecificationOptionValue removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByUUID_G(uuid, groupId);

		return remove(cpDefinitionSpecificationOptionValue);
	}

	/**
	 * Returns the number of cp definition specification option values where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<CPDefinitionSpecificationOptionValue>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByUuid_C_First(
				uuid, companyId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the cp definition specification option values where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of cp definition specification option values where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<CPDefinitionSpecificationOptionValue>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the cp definition specification option values where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId) {

		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				finderCache, new Object[] {groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByGroupId_First(
			long groupId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByGroupId_First(
				groupId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByGroupId_First(
		long groupId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Removes all the cp definition specification option values where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of cp definition specification option values where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				finderCache, new Object[] {groupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCPDefinitionId;
	private FinderPath _finderPathWithoutPaginationFindByCPDefinitionId;
	private FinderPath _finderPathCountByCPDefinitionId;
	private CollectionPersistenceFinder<CPDefinitionSpecificationOptionValue>
		_collectionPersistenceFinderByCPDefinitionId;

	/**
	 * Returns all the cp definition specification option values where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId) {

		return findByCPDefinitionId(
			CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {

		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByCPDefinitionId.find(
				finderCache, new Object[] {CPDefinitionId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByCPDefinitionId_First(
				CPDefinitionId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			_collectionPersistenceFinderByCPDefinitionId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {CPDefinitionId}));
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return _collectionPersistenceFinderByCPDefinitionId.fetchFirst(
			finderCache, new Object[] {CPDefinitionId}, orderByComparator);
	}

	/**
	 * Removes all the cp definition specification option values where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		_collectionPersistenceFinderByCPDefinitionId.remove(
			finderCache, new Object[] {CPDefinitionId});
	}

	/**
	 * Returns the number of cp definition specification option values where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByCPDefinitionId.count(
				finderCache, new Object[] {CPDefinitionId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCPSpecificationOptionId;
	private FinderPath
		_finderPathWithoutPaginationFindByCPSpecificationOptionId;
	private FinderPath _finderPathCountByCPSpecificationOptionId;
	private CollectionPersistenceFinder<CPDefinitionSpecificationOptionValue>
		_collectionPersistenceFinderByCPSpecificationOptionId;

	/**
	 * Returns all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue>
		findByCPSpecificationOptionId(long CPSpecificationOptionId) {

		return findByCPSpecificationOptionId(
			CPSpecificationOptionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end) {

		return findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator) {

		return findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator,
			boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByCPSpecificationOptionId.find(
				finderCache, new Object[] {CPSpecificationOptionId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue
			findByCPSpecificationOptionId_First(
				long CPSpecificationOptionId,
				OrderByComparator<CPDefinitionSpecificationOptionValue>
					orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				fetchByCPSpecificationOptionId_First(
					CPSpecificationOptionId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			_collectionPersistenceFinderByCPSpecificationOptionId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {CPSpecificationOptionId}));
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue
		fetchByCPSpecificationOptionId_First(
			long CPSpecificationOptionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator) {

		return _collectionPersistenceFinderByCPSpecificationOptionId.fetchFirst(
			finderCache, new Object[] {CPSpecificationOptionId},
			orderByComparator);
	}

	/**
	 * Removes all the cp definition specification option values where CPSpecificationOptionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 */
	@Override
	public void removeByCPSpecificationOptionId(long CPSpecificationOptionId) {
		_collectionPersistenceFinderByCPSpecificationOptionId.remove(
			finderCache, new Object[] {CPSpecificationOptionId});
	}

	/**
	 * Returns the number of cp definition specification option values where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByCPSpecificationOptionId(long CPSpecificationOptionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByCPSpecificationOptionId.count(
				finderCache, new Object[] {CPSpecificationOptionId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCPOptionCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByCPOptionCategoryId;
	private FinderPath _finderPathCountByCPOptionCategoryId;
	private CollectionPersistenceFinder<CPDefinitionSpecificationOptionValue>
		_collectionPersistenceFinderByCPOptionCategoryId;

	/**
	 * Returns all the cp definition specification option values where CPOptionCategoryId = &#63;.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId) {

		return findByCPOptionCategoryId(
			CPOptionCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end) {

		return findByCPOptionCategoryId(CPOptionCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByCPOptionCategoryId(
			CPOptionCategoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByCPOptionCategoryId.find(
				finderCache, new Object[] {CPOptionCategoryId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByCPOptionCategoryId_First(
			long CPOptionCategoryId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				fetchByCPOptionCategoryId_First(
					CPOptionCategoryId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			_collectionPersistenceFinderByCPOptionCategoryId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {CPOptionCategoryId}));
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByCPOptionCategoryId_First(
		long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return _collectionPersistenceFinderByCPOptionCategoryId.fetchFirst(
			finderCache, new Object[] {CPOptionCategoryId}, orderByComparator);
	}

	/**
	 * Removes all the cp definition specification option values where CPOptionCategoryId = &#63; from the database.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 */
	@Override
	public void removeByCPOptionCategoryId(long CPOptionCategoryId) {
		_collectionPersistenceFinderByCPOptionCategoryId.remove(
			finderCache, new Object[] {CPOptionCategoryId});
	}

	/**
	 * Returns the number of cp definition specification option values where CPOptionCategoryId = &#63;.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByCPOptionCategoryId(long CPOptionCategoryId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByCPOptionCategoryId.count(
				finderCache, new Object[] {CPOptionCategoryId});
		}
	}

	private FinderPath _finderPathFetchByC_CSOVI;
	private UniquePersistenceFinder<CPDefinitionSpecificationOptionValue>
		_uniquePersistenceFinderByC_CSOVI;

	/**
	 * Returns the cp definition specification option value where CPDefinitionSpecificationOptionValueId = &#63; and CPDefinitionId = &#63; or throws a <code>NoSuchCPDefinitionSpecificationOptionValueException</code> if it could not be found.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByC_CSOVI(
			long CPDefinitionSpecificationOptionValueId, long CPDefinitionId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByC_CSOVI(
				CPDefinitionSpecificationOptionValueId, CPDefinitionId);

		if (cpDefinitionSpecificationOptionValue == null) {
			String message =
				_uniquePersistenceFinderByC_CSOVI.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						CPDefinitionSpecificationOptionValueId, CPDefinitionId
					});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPDefinitionSpecificationOptionValueException(
				message);
		}

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Returns the cp definition specification option value where CPDefinitionSpecificationOptionValueId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_CSOVI(
		long CPDefinitionSpecificationOptionValueId, long CPDefinitionId) {

		return fetchByC_CSOVI(
			CPDefinitionSpecificationOptionValueId, CPDefinitionId, true);
	}

	/**
	 * Returns the cp definition specification option value where CPDefinitionSpecificationOptionValueId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_CSOVI(
		long CPDefinitionSpecificationOptionValueId, long CPDefinitionId,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _uniquePersistenceFinderByC_CSOVI.fetch(
				finderCache,
				new Object[] {
					CPDefinitionSpecificationOptionValueId, CPDefinitionId
				},
				useFinderCache);
		}
	}

	/**
	 * Removes the cp definition specification option value where CPDefinitionSpecificationOptionValueId = &#63; and CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the cp definition specification option value that was removed
	 */
	@Override
	public CPDefinitionSpecificationOptionValue removeByC_CSOVI(
			long CPDefinitionSpecificationOptionValueId, long CPDefinitionId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByC_CSOVI(
				CPDefinitionSpecificationOptionValueId, CPDefinitionId);

		return remove(cpDefinitionSpecificationOptionValue);
	}

	/**
	 * Returns the number of cp definition specification option values where CPDefinitionSpecificationOptionValueId = &#63; and CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByC_CSOVI(
		long CPDefinitionSpecificationOptionValueId, long CPDefinitionId) {

		return _uniquePersistenceFinderByC_CSOVI.count(
			finderCache,
			new Object[] {
				CPDefinitionSpecificationOptionValueId, CPDefinitionId
			});
	}

	private FinderPath _finderPathWithPaginationFindByC_CSO;
	private FinderPath _finderPathWithoutPaginationFindByC_CSO;
	private FinderPath _finderPathCountByC_CSO;
	private CollectionPersistenceFinder<CPDefinitionSpecificationOptionValue>
		_collectionPersistenceFinderByC_CSO;

	/**
	 * Returns all the cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId) {

		return findByC_CSO(
			CPDefinitionId, CPSpecificationOptionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId, int start, int end) {

		return findByC_CSO(
			CPDefinitionId, CPSpecificationOptionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByC_CSO(
			CPDefinitionId, CPSpecificationOptionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByC_CSO.find(
				finderCache,
				new Object[] {CPDefinitionId, CPSpecificationOptionId}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByC_CSO_First(
			long CPDefinitionId, long CPSpecificationOptionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByC_CSO_First(
				CPDefinitionId, CPSpecificationOptionId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			_collectionPersistenceFinderByC_CSO.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {CPDefinitionId, CPSpecificationOptionId}));
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_CSO_First(
		long CPDefinitionId, long CPSpecificationOptionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return _collectionPersistenceFinderByC_CSO.fetchFirst(
			finderCache, new Object[] {CPDefinitionId, CPSpecificationOptionId},
			orderByComparator);
	}

	/**
	 * Removes all the cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 */
	@Override
	public void removeByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId) {

		_collectionPersistenceFinderByC_CSO.remove(
			finderCache,
			new Object[] {CPDefinitionId, CPSpecificationOptionId});
	}

	/**
	 * Returns the number of cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByC_CSO(long CPDefinitionId, long CPSpecificationOptionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByC_CSO.count(
				finderCache,
				new Object[] {CPDefinitionId, CPSpecificationOptionId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_COC;
	private FinderPath _finderPathWithoutPaginationFindByC_COC;
	private FinderPath _finderPathCountByC_COC;
	private CollectionPersistenceFinder<CPDefinitionSpecificationOptionValue>
		_collectionPersistenceFinderByC_COC;

	/**
	 * Returns all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId) {

		return findByC_COC(
			CPDefinitionId, CPOptionCategoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end) {

		return findByC_COC(
			CPDefinitionId, CPOptionCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByC_COC(
			CPDefinitionId, CPOptionCategoryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByC_COC.find(
				finderCache, new Object[] {CPDefinitionId, CPOptionCategoryId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByC_COC_First(
			long CPDefinitionId, long CPOptionCategoryId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByC_COC_First(
				CPDefinitionId, CPOptionCategoryId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			_collectionPersistenceFinderByC_COC.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {CPDefinitionId, CPOptionCategoryId}));
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_COC_First(
		long CPDefinitionId, long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return _collectionPersistenceFinderByC_COC.fetchFirst(
			finderCache, new Object[] {CPDefinitionId, CPOptionCategoryId},
			orderByComparator);
	}

	/**
	 * Removes all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 */
	@Override
	public void removeByC_COC(long CPDefinitionId, long CPOptionCategoryId) {
		_collectionPersistenceFinderByC_COC.remove(
			finderCache, new Object[] {CPDefinitionId, CPOptionCategoryId});
	}

	/**
	 * Returns the number of cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByC_COC(long CPDefinitionId, long CPOptionCategoryId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _collectionPersistenceFinderByC_COC.count(
				finderCache, new Object[] {CPDefinitionId, CPOptionCategoryId});
		}
	}

	private FinderPath _finderPathFetchByC_K;
	private UniquePersistenceFinder<CPDefinitionSpecificationOptionValue>
		_uniquePersistenceFinderByC_K;

	/**
	 * Returns the cp definition specification option value where CPDefinitionId = &#63; and key = &#63; or throws a <code>NoSuchCPDefinitionSpecificationOptionValueException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param key the key
	 * @return the matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByC_K(
			long CPDefinitionId, String key)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByC_K(
				CPDefinitionId, key);

		if (cpDefinitionSpecificationOptionValue == null) {
			String message =
				_uniquePersistenceFinderByC_K.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {CPDefinitionId, key});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPDefinitionSpecificationOptionValueException(
				message);
		}

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Returns the cp definition specification option value where CPDefinitionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param key the key
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_K(
		long CPDefinitionId, String key) {

		return fetchByC_K(CPDefinitionId, key, true);
	}

	/**
	 * Returns the cp definition specification option value where CPDefinitionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_K(
		long CPDefinitionId, String key, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _uniquePersistenceFinderByC_K.fetch(
				finderCache, new Object[] {CPDefinitionId, key},
				useFinderCache);
		}
	}

	/**
	 * Removes the cp definition specification option value where CPDefinitionId = &#63; and key = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param key the key
	 * @return the cp definition specification option value that was removed
	 */
	@Override
	public CPDefinitionSpecificationOptionValue removeByC_K(
			long CPDefinitionId, String key)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByC_K(
				CPDefinitionId, key);

		return remove(cpDefinitionSpecificationOptionValue);
	}

	/**
	 * Returns the number of cp definition specification option values where CPDefinitionId = &#63; and key = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param key the key
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByC_K(long CPDefinitionId, String key) {
		return _uniquePersistenceFinderByC_K.count(
			finderCache, new Object[] {CPDefinitionId, key});
	}

	private FinderPath _finderPathFetchByERC_C;
	private UniquePersistenceFinder<CPDefinitionSpecificationOptionValue>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the cp definition specification option value where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchCPDefinitionSpecificationOptionValueException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByERC_C(
				externalReferenceCode, companyId);

		if (cpDefinitionSpecificationOptionValue == null) {
			String message =
				_uniquePersistenceFinderByERC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPDefinitionSpecificationOptionValueException(
				message);
		}

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Returns the cp definition specification option value where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the cp definition specification option value where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPDefinitionSpecificationOptionValue.class)) {

			return _uniquePersistenceFinderByERC_C.fetch(
				finderCache, new Object[] {externalReferenceCode, companyId},
				useFinderCache);
		}
	}

	/**
	 * Removes the cp definition specification option value where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the cp definition specification option value that was removed
	 */
	@Override
	public CPDefinitionSpecificationOptionValue removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByERC_C(
				externalReferenceCode, companyId);

		return remove(cpDefinitionSpecificationOptionValue);
	}

	/**
	 * Returns the number of cp definition specification option values where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public CPDefinitionSpecificationOptionValuePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"CPDefinitionSpecificationOptionValueId",
			"CPDSpecificationOptionValueId");
		dbColumnNames.put("key", "key_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPDefinitionSpecificationOptionValue.class);

		setModelImplClass(CPDefinitionSpecificationOptionValueImpl.class);
		setModelPKClass(long.class);

		setTable(CPDefinitionSpecificationOptionValueTable.INSTANCE);
	}

	/**
	 * Creates a new cp definition specification option value with the primary key. Does not add the cp definition specification option value to the database.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key for the new cp definition specification option value
	 * @return the new cp definition specification option value
	 */
	@Override
	public CPDefinitionSpecificationOptionValue create(
		long CPDefinitionSpecificationOptionValueId) {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				new CPDefinitionSpecificationOptionValueImpl();

		cpDefinitionSpecificationOptionValue.setNew(true);
		cpDefinitionSpecificationOptionValue.setPrimaryKey(
			CPDefinitionSpecificationOptionValueId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionSpecificationOptionValue.setUuid(uuid);

		cpDefinitionSpecificationOptionValue.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Removes the cp definition specification option value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	 * @return the cp definition specification option value that was removed
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue remove(
			long CPDefinitionSpecificationOptionValueId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		return remove((Serializable)CPDefinitionSpecificationOptionValueId);
	}

	@Override
	protected CPDefinitionSpecificationOptionValue removeImpl(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionSpecificationOptionValue)) {
				cpDefinitionSpecificationOptionValue =
					(CPDefinitionSpecificationOptionValue)session.get(
						CPDefinitionSpecificationOptionValueImpl.class,
						cpDefinitionSpecificationOptionValue.
							getPrimaryKeyObj());
			}

			if ((cpDefinitionSpecificationOptionValue != null) &&
				ctPersistenceHelper.isRemove(
					cpDefinitionSpecificationOptionValue)) {

				session.delete(cpDefinitionSpecificationOptionValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionSpecificationOptionValue != null) {
			clearCache(cpDefinitionSpecificationOptionValue);
		}

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	public CPDefinitionSpecificationOptionValue updateImpl(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue) {

		boolean isNew = cpDefinitionSpecificationOptionValue.isNew();

		if (!(cpDefinitionSpecificationOptionValue instanceof
				CPDefinitionSpecificationOptionValueModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					cpDefinitionSpecificationOptionValue.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					cpDefinitionSpecificationOptionValue);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionSpecificationOptionValue proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionSpecificationOptionValue implementation " +
					cpDefinitionSpecificationOptionValue.getClass());
		}

		CPDefinitionSpecificationOptionValueModelImpl
			cpDefinitionSpecificationOptionValueModelImpl =
				(CPDefinitionSpecificationOptionValueModelImpl)
					cpDefinitionSpecificationOptionValue;

		if (Validator.isNull(cpDefinitionSpecificationOptionValue.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionSpecificationOptionValue.setUuid(uuid);
		}

		if (Validator.isNull(
				cpDefinitionSpecificationOptionValue.
					getExternalReferenceCode())) {

			cpDefinitionSpecificationOptionValue.setExternalReferenceCode(
				cpDefinitionSpecificationOptionValue.getUuid());
		}
		else {
			if (!Objects.equals(
					cpDefinitionSpecificationOptionValueModelImpl.
						getColumnOriginalValue("externalReferenceCode"),
					cpDefinitionSpecificationOptionValue.
						getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId =
						cpDefinitionSpecificationOptionValue.getCompanyId();

					long groupId =
						cpDefinitionSpecificationOptionValue.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK =
							cpDefinitionSpecificationOptionValue.
								getPrimaryKey();
					}

					try {
						cpDefinitionSpecificationOptionValue.
							setExternalReferenceCode(
								SanitizerUtil.sanitize(
									companyId, groupId, userId,
									CPDefinitionSpecificationOptionValue.class.
										getName(),
									classPK, ContentTypes.TEXT_HTML,
									Sanitizer.MODE_ALL,
									cpDefinitionSpecificationOptionValue.
										getExternalReferenceCode(),
									null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			CPDefinitionSpecificationOptionValue
				ercCPDefinitionSpecificationOptionValue = fetchByERC_C(
					cpDefinitionSpecificationOptionValue.
						getExternalReferenceCode(),
					cpDefinitionSpecificationOptionValue.getCompanyId());

			if (isNew) {
				if (ercCPDefinitionSpecificationOptionValue != null) {
					throw new DuplicateCPDefinitionSpecificationOptionValueExternalReferenceCodeException(
						"Duplicate cp definition specification option value with external reference code " +
							cpDefinitionSpecificationOptionValue.
								getExternalReferenceCode() + " and company " +
									cpDefinitionSpecificationOptionValue.
										getCompanyId());
				}
			}
			else {
				if ((ercCPDefinitionSpecificationOptionValue != null) &&
					(cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId() !=
							ercCPDefinitionSpecificationOptionValue.
								getCPDefinitionSpecificationOptionValueId())) {

					throw new DuplicateCPDefinitionSpecificationOptionValueExternalReferenceCodeException(
						"Duplicate cp definition specification option value with external reference code " +
							cpDefinitionSpecificationOptionValue.
								getExternalReferenceCode() + " and company " +
									cpDefinitionSpecificationOptionValue.
										getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(cpDefinitionSpecificationOptionValue.getCreateDate() == null)) {

			if (serviceContext == null) {
				cpDefinitionSpecificationOptionValue.setCreateDate(date);
			}
			else {
				cpDefinitionSpecificationOptionValue.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpDefinitionSpecificationOptionValueModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				cpDefinitionSpecificationOptionValue.setModifiedDate(date);
			}
			else {
				cpDefinitionSpecificationOptionValue.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(
					cpDefinitionSpecificationOptionValue)) {

				if (!isNew) {
					session.evict(
						CPDefinitionSpecificationOptionValueImpl.class,
						cpDefinitionSpecificationOptionValue.
							getPrimaryKeyObj());
				}

				session.save(cpDefinitionSpecificationOptionValue);
			}
			else {
				cpDefinitionSpecificationOptionValue =
					(CPDefinitionSpecificationOptionValue)session.merge(
						cpDefinitionSpecificationOptionValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(cpDefinitionSpecificationOptionValue, false);

		if (isNew) {
			cpDefinitionSpecificationOptionValue.setNew(false);
		}

		cpDefinitionSpecificationOptionValue.resetOriginalValues();

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Returns the cp definition specification option value with the primary key or throws a <code>NoSuchCPDefinitionSpecificationOptionValueException</code> if it could not be found.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	 * @return the cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByPrimaryKey(
			long CPDefinitionSpecificationOptionValueId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		return findByPrimaryKey(
			(Serializable)CPDefinitionSpecificationOptionValueId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the cp definition specification option value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	 * @return the cp definition specification option value, or <code>null</code> if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByPrimaryKey(
		long CPDefinitionSpecificationOptionValueId) {

		return fetchByPrimaryKey(
			(Serializable)CPDefinitionSpecificationOptionValueId);
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
		return "CPDSpecificationOptionValueId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE;
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
		return CPDefinitionSpecificationOptionValueModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPDSpecificationOptionValue";
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
		ctMergeColumnNames.add("CPDefinitionId");
		ctMergeColumnNames.add("CPSpecificationOptionId");
		ctMergeColumnNames.add("CPOptionCategoryId");
		ctMergeColumnNames.add("key_");
		ctMergeColumnNames.add("priority");
		ctMergeColumnNames.add("value");
		ctMergeColumnNames.add("visible");
		ctMergeColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPDSpecificationOptionValueId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"CPDSpecificationOptionValueId", "CPDefinitionId"});

		_uniqueIndexColumnNames.add(new String[] {"CPDefinitionId", "key_"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "companyId"});
	}

	/**
	 * Initializes the cp definition specification option value persistence.
	 */
	@Activate
	public void activate() {
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
			_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
			_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
			CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL,
			_ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.", "uuid",
				FinderColumn.Type.STRING, "=", true, true,
				CPDefinitionSpecificationOptionValue::getUuid));

		_finderPathFetchByUUID_G = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false,
			CPDefinitionSpecificationOptionValue::getUuid,
			CPDefinitionSpecificationOptionValue::getGroupId);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G,
			_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.", "uuid",
				FinderColumn.Type.STRING, "=", true, false,
				CPDefinitionSpecificationOptionValue::getUuid),
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.", "groupId",
				FinderColumn.Type.LONG, "=", true, true,
				CPDefinitionSpecificationOptionValue::getGroupId));

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
				_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
				_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"cpDefinitionSpecificationOptionValue.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					CPDefinitionSpecificationOptionValue::getUuid),
				new FinderColumn<>(
					"cpDefinitionSpecificationOptionValue.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					CPDefinitionSpecificationOptionValue::getCompanyId));

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
				_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
				_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"cpDefinitionSpecificationOptionValue.", "groupId",
					FinderColumn.Type.LONG, "=", true, true,
					CPDefinitionSpecificationOptionValue::getGroupId));

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
				_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
				_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"cpDefinitionSpecificationOptionValue.", "CPDefinitionId",
					FinderColumn.Type.LONG, "=", true, true,
					CPDefinitionSpecificationOptionValue::getCPDefinitionId));

		_finderPathWithPaginationFindByCPSpecificationOptionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPSpecificationOptionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPSpecificationOptionId"}, true);

		_finderPathWithoutPaginationFindByCPSpecificationOptionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCPSpecificationOptionId",
				new String[] {Long.class.getName()},
				new String[] {"CPSpecificationOptionId"}, true);

		_finderPathCountByCPSpecificationOptionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPSpecificationOptionId",
			new String[] {Long.class.getName()},
			new String[] {"CPSpecificationOptionId"}, false);

		_collectionPersistenceFinderByCPSpecificationOptionId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCPSpecificationOptionId,
				_finderPathWithoutPaginationFindByCPSpecificationOptionId,
				_finderPathCountByCPSpecificationOptionId,
				_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
				_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"cpDefinitionSpecificationOptionValue.",
					"CPSpecificationOptionId", FinderColumn.Type.LONG, "=",
					true, true,
					CPDefinitionSpecificationOptionValue::
						getCPSpecificationOptionId));

		_finderPathWithPaginationFindByCPOptionCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPOptionCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPOptionCategoryId"}, true);

		_finderPathWithoutPaginationFindByCPOptionCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCPOptionCategoryId", new String[] {Long.class.getName()},
			new String[] {"CPOptionCategoryId"}, true);

		_finderPathCountByCPOptionCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPOptionCategoryId", new String[] {Long.class.getName()},
			new String[] {"CPOptionCategoryId"}, false);

		_collectionPersistenceFinderByCPOptionCategoryId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCPOptionCategoryId,
				_finderPathWithoutPaginationFindByCPOptionCategoryId,
				_finderPathCountByCPOptionCategoryId,
				_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
				_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"cpDefinitionSpecificationOptionValue.",
					"CPOptionCategoryId", FinderColumn.Type.LONG, "=", true,
					true,
					CPDefinitionSpecificationOptionValue::
						getCPOptionCategoryId));

		_finderPathFetchByC_CSOVI = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_CSOVI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDSpecificationOptionValueId", "CPDefinitionId"},
			false,
			CPDefinitionSpecificationOptionValue::
				getCPDefinitionSpecificationOptionValueId,
			CPDefinitionSpecificationOptionValue::getCPDefinitionId);

		_uniquePersistenceFinderByC_CSOVI = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_CSOVI,
			_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.",
				"CPDefinitionSpecificationOptionValueId",
				FinderColumn.Type.LONG, "=", true, false,
				CPDefinitionSpecificationOptionValue::
					getCPDefinitionSpecificationOptionValueId),
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.", "CPDefinitionId",
				FinderColumn.Type.LONG, "=", true, true,
				CPDefinitionSpecificationOptionValue::getCPDefinitionId));

		_finderPathWithPaginationFindByC_CSO = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CSO",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionId", "CPSpecificationOptionId"}, true);

		_finderPathWithoutPaginationFindByC_CSO = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CSO",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionId", "CPSpecificationOptionId"}, true);

		_finderPathCountByC_CSO = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CSO",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionId", "CPSpecificationOptionId"}, false);

		_collectionPersistenceFinderByC_CSO = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_CSO,
			_finderPathWithoutPaginationFindByC_CSO, _finderPathCountByC_CSO,
			_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
			_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
			CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL,
			_ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.", "CPDefinitionId",
				FinderColumn.Type.LONG, "=", true, false,
				CPDefinitionSpecificationOptionValue::getCPDefinitionId),
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.",
				"CPSpecificationOptionId", FinderColumn.Type.LONG, "=", true,
				true,
				CPDefinitionSpecificationOptionValue::
					getCPSpecificationOptionId));

		_finderPathWithPaginationFindByC_COC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_COC",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionId", "CPOptionCategoryId"}, true);

		_finderPathWithoutPaginationFindByC_COC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_COC",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionId", "CPOptionCategoryId"}, true);

		_finderPathCountByC_COC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_COC",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionId", "CPOptionCategoryId"}, false);

		_collectionPersistenceFinderByC_COC = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_COC,
			_finderPathWithoutPaginationFindByC_COC, _finderPathCountByC_COC,
			_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
			_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
			CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL,
			_ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.", "CPDefinitionId",
				FinderColumn.Type.LONG, "=", true, false,
				CPDefinitionSpecificationOptionValue::getCPDefinitionId),
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.", "CPOptionCategoryId",
				FinderColumn.Type.LONG, "=", true, true,
				CPDefinitionSpecificationOptionValue::getCPOptionCategoryId));

		_finderPathFetchByC_K = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"CPDefinitionId", "key_"}, false,
			CPDefinitionSpecificationOptionValue::getCPDefinitionId,
			CPDefinitionSpecificationOptionValue::getKey);

		_uniquePersistenceFinderByC_K = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_K,
			_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.", "CPDefinitionId",
				FinderColumn.Type.LONG, "=", true, false,
				CPDefinitionSpecificationOptionValue::getCPDefinitionId),
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.", "key",
				FinderColumn.Type.STRING, "=", true, true,
				CPDefinitionSpecificationOptionValue::getKey));

		_finderPathFetchByERC_C = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, false,
			CPDefinitionSpecificationOptionValue::getExternalReferenceCode,
			CPDefinitionSpecificationOptionValue::getCompanyId);

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_C,
			_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE,
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.",
				"externalReferenceCode", FinderColumn.Type.STRING, "=", true,
				false,
				CPDefinitionSpecificationOptionValue::getExternalReferenceCode),
			new FinderColumn<>(
				"cpDefinitionSpecificationOptionValue.", "companyId",
				FinderColumn.Type.LONG, "=", true, true,
				CPDefinitionSpecificationOptionValue::getCompanyId));

		CPDefinitionSpecificationOptionValueUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPDefinitionSpecificationOptionValueUtil.setPersistence(null);

		entityCache.removeCache(
			CPDefinitionSpecificationOptionValueImpl.class.getName());
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

	private static final String _ENTITY_ALIAS_PREFIX =
		CPDefinitionSpecificationOptionValueModelImpl.ENTITY_ALIAS + ".";

	private static final String
		_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE =
			"SELECT cpDefinitionSpecificationOptionValue FROM CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue";

	private static final String
		_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE =
			"SELECT cpDefinitionSpecificationOptionValue FROM CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue WHERE ";

	private static final String
		_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE =
			"SELECT COUNT(cpDefinitionSpecificationOptionValue) FROM CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDefinitionSpecificationOptionValue exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionSpecificationOptionValuePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "CPDefinitionSpecificationOptionValueId", "key"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1813350670