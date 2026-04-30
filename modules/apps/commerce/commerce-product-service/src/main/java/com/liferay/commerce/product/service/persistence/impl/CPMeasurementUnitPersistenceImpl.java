/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.DuplicateCPMeasurementUnitExternalReferenceCodeException;
import com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.model.CPMeasurementUnitTable;
import com.liferay.commerce.product.model.impl.CPMeasurementUnitImpl;
import com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl;
import com.liferay.commerce.product.service.persistence.CPMeasurementUnitPersistence;
import com.liferay.commerce.product.service.persistence.CPMeasurementUnitUtil;
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
 * The persistence implementation for the cp measurement unit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CPMeasurementUnitPersistence.class)
public class CPMeasurementUnitPersistenceImpl
	extends BasePersistenceImpl
		<CPMeasurementUnit, NoSuchCPMeasurementUnitException>
	implements CPMeasurementUnitPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPMeasurementUnitUtil</code> to access the cp measurement unit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPMeasurementUnitImpl.class.getName();

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
	private CollectionPersistenceFinder<CPMeasurementUnit>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the cp measurement units where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByUuid_First(
			String uuid, OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {

		CPMeasurementUnit cpMeasurementUnit = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		throw new NoSuchCPMeasurementUnitException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByUuid_First(
		String uuid, OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the cp measurement units where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of cp measurement units where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<CPMeasurementUnit>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPMeasurementUnitException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByUUID_G(String uuid, long groupId)
		throws NoSuchCPMeasurementUnitException {

		CPMeasurementUnit cpMeasurementUnit = fetchByUUID_G(uuid, groupId);

		if (cpMeasurementUnit == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPMeasurementUnitException(message);
		}

		return cpMeasurementUnit;
	}

	/**
	 * Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the cp measurement unit where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp measurement unit that was removed
	 */
	@Override
	public CPMeasurementUnit removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPMeasurementUnitException {

		CPMeasurementUnit cpMeasurementUnit = findByUUID_G(uuid, groupId);

		return remove(cpMeasurementUnit);
	}

	/**
	 * Returns the number of cp measurement units where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<CPMeasurementUnit>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {

		CPMeasurementUnit cpMeasurementUnit = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		throw new NoSuchCPMeasurementUnitException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the cp measurement units where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<CPMeasurementUnit>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the cp measurement units where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				finderCache, new Object[] {companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByCompanyId_First(
			long companyId,
			OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {

		CPMeasurementUnit cpMeasurementUnit = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		throw new NoSuchCPMeasurementUnitException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the cp measurement units where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of cp measurement units where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				finderCache, new Object[] {companyId});
		}
	}

	private FinderPath _finderPathFetchByC_K;

	/**
	 * Returns the cp measurement unit where companyId = &#63; and key = &#63; or throws a <code>NoSuchCPMeasurementUnitException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByC_K(long companyId, String key)
		throws NoSuchCPMeasurementUnitException {

		CPMeasurementUnit cpMeasurementUnit = fetchByC_K(companyId, key);

		if (cpMeasurementUnit == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", key=");
			sb.append(key);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPMeasurementUnitException(sb.toString());
		}

		return cpMeasurementUnit;
	}

	/**
	 * Returns the cp measurement unit where companyId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByC_K(long companyId, String key) {
		return fetchByC_K(companyId, key, true);
	}

	/**
	 * Returns the cp measurement unit where companyId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByC_K(
		long companyId, String key, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			key = Objects.toString(key, "");

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {companyId, key};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByC_K, finderArgs, this);
			}

			if (result instanceof CPMeasurementUnit) {
				CPMeasurementUnit cpMeasurementUnit = (CPMeasurementUnit)result;

				if ((companyId != cpMeasurementUnit.getCompanyId()) ||
					!Objects.equals(key, cpMeasurementUnit.getKey())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

				sb.append(_FINDER_COLUMN_C_K_COMPANYID_2);

				boolean bindKey = false;

				if (key.isEmpty()) {
					sb.append(_FINDER_COLUMN_C_K_KEY_3);
				}
				else {
					bindKey = true;

					sb.append(_FINDER_COLUMN_C_K_KEY_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					if (bindKey) {
						queryPos.add(StringUtil.toLowerCase(key));
					}

					List<CPMeasurementUnit> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByC_K, finderArgs, list);
						}
					}
					else {
						CPMeasurementUnit cpMeasurementUnit = list.get(0);

						result = cpMeasurementUnit;

						cacheResult(cpMeasurementUnit);
					}
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (CPMeasurementUnit)result;
			}
		}
	}

	/**
	 * Removes the cp measurement unit where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the cp measurement unit that was removed
	 */
	@Override
	public CPMeasurementUnit removeByC_K(long companyId, String key)
		throws NoSuchCPMeasurementUnitException {

		CPMeasurementUnit cpMeasurementUnit = findByC_K(companyId, key);

		return remove(cpMeasurementUnit);
	}

	/**
	 * Returns the number of cp measurement units where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByC_K(long companyId, String key) {
		CPMeasurementUnit cpMeasurementUnit = fetchByC_K(companyId, key);

		if (cpMeasurementUnit == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_C_K_COMPANYID_2 =
		"cpMeasurementUnit.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_K_KEY_2 =
		"lower(cpMeasurementUnit.key) = ?";

	private static final String _FINDER_COLUMN_C_K_KEY_3 =
		"(cpMeasurementUnit.key IS NULL OR cpMeasurementUnit.key = '')";

	private FinderPath _finderPathWithPaginationFindByC_T;
	private FinderPath _finderPathWithoutPaginationFindByC_T;
	private FinderPath _finderPathCountByC_T;
	private CollectionPersistenceFinder<CPMeasurementUnit>
		_collectionPersistenceFinderByC_T;

	/**
	 * Returns all the cp measurement units where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByC_T(long companyId, int type) {
		return findByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByC_T(
		long companyId, int type, int start, int end) {

		return findByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return findByC_T(companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _collectionPersistenceFinderByC_T.find(
				finderCache, new Object[] {companyId, type}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByC_T_First(
			long companyId, int type,
			OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {

		CPMeasurementUnit cpMeasurementUnit = fetchByC_T_First(
			companyId, type, orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		throw new NoSuchCPMeasurementUnitException(
			_collectionPersistenceFinderByC_T.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, type}));
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByC_T_First(
		long companyId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return _collectionPersistenceFinderByC_T.fetchFirst(
			finderCache, new Object[] {companyId, type}, orderByComparator);
	}

	/**
	 * Removes all the cp measurement units where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	@Override
	public void removeByC_T(long companyId, int type) {
		_collectionPersistenceFinderByC_T.remove(
			finderCache, new Object[] {companyId, type});
	}

	/**
	 * Returns the number of cp measurement units where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByC_T(long companyId, int type) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _collectionPersistenceFinderByC_T.count(
				finderCache, new Object[] {companyId, type});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_P_T;
	private FinderPath _finderPathWithoutPaginationFindByC_P_T;
	private FinderPath _finderPathCountByC_P_T;
	private CollectionPersistenceFinder<CPMeasurementUnit>
		_collectionPersistenceFinderByC_P_T;

	/**
	 * Returns all the cp measurement units where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByC_P_T(
		long companyId, boolean primary, int type) {

		return findByC_P_T(
			companyId, primary, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp measurement units where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByC_P_T(
		long companyId, boolean primary, int type, int start, int end) {

		return findByC_P_T(companyId, primary, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByC_P_T(
		long companyId, boolean primary, int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return findByC_P_T(
			companyId, primary, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByC_P_T(
		long companyId, boolean primary, int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _collectionPersistenceFinderByC_P_T.find(
				finderCache, new Object[] {companyId, primary, type}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByC_P_T_First(
			long companyId, boolean primary, int type,
			OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {

		CPMeasurementUnit cpMeasurementUnit = fetchByC_P_T_First(
			companyId, primary, type, orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		throw new NoSuchCPMeasurementUnitException(
			_collectionPersistenceFinderByC_P_T.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, primary, type}));
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByC_P_T_First(
		long companyId, boolean primary, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return _collectionPersistenceFinderByC_P_T.fetchFirst(
			finderCache, new Object[] {companyId, primary, type},
			orderByComparator);
	}

	/**
	 * Removes all the cp measurement units where companyId = &#63; and primary = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 */
	@Override
	public void removeByC_P_T(long companyId, boolean primary, int type) {
		_collectionPersistenceFinderByC_P_T.remove(
			finderCache, new Object[] {companyId, primary, type});
	}

	/**
	 * Returns the number of cp measurement units where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByC_P_T(long companyId, boolean primary, int type) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _collectionPersistenceFinderByC_P_T.count(
				finderCache, new Object[] {companyId, primary, type});
		}
	}

	private FinderPath _finderPathFetchByERC_C;
	private UniquePersistenceFinder<CPMeasurementUnit>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the cp measurement unit where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchCPMeasurementUnitException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCPMeasurementUnitException {

		CPMeasurementUnit cpMeasurementUnit = fetchByERC_C(
			externalReferenceCode, companyId);

		if (cpMeasurementUnit == null) {
			String message =
				_uniquePersistenceFinderByERC_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchCPMeasurementUnitException(message);
		}

		return cpMeasurementUnit;
	}

	/**
	 * Returns the cp measurement unit where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the cp measurement unit where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			return _uniquePersistenceFinderByERC_C.fetch(
				finderCache, new Object[] {externalReferenceCode, companyId},
				useFinderCache);
		}
	}

	/**
	 * Removes the cp measurement unit where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the cp measurement unit that was removed
	 */
	@Override
	public CPMeasurementUnit removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCPMeasurementUnitException {

		CPMeasurementUnit cpMeasurementUnit = findByERC_C(
			externalReferenceCode, companyId);

		return remove(cpMeasurementUnit);
	}

	/**
	 * Returns the number of cp measurement units where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public CPMeasurementUnitPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("key", "key_");
		dbColumnNames.put("primary", "primary_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPMeasurementUnit.class);

		setModelImplClass(CPMeasurementUnitImpl.class);
		setModelPKClass(long.class);

		setTable(CPMeasurementUnitTable.INSTANCE);
	}

	/**
	 * Caches the cp measurement unit in the entity cache if it is enabled.
	 *
	 * @param cpMeasurementUnit the cp measurement unit
	 */
	@Override
	public void cacheResult(CPMeasurementUnit cpMeasurementUnit) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpMeasurementUnit.getCtCollectionId())) {

			entityCache.putResult(
				CPMeasurementUnitImpl.class, cpMeasurementUnit.getPrimaryKey(),
				cpMeasurementUnit);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					cpMeasurementUnit.getUuid(), cpMeasurementUnit.getGroupId()
				},
				cpMeasurementUnit);

			finderCache.putResult(
				_finderPathFetchByC_K,
				new Object[] {
					cpMeasurementUnit.getCompanyId(), cpMeasurementUnit.getKey()
				},
				cpMeasurementUnit);

			finderCache.putResult(
				_finderPathFetchByERC_C,
				new Object[] {
					cpMeasurementUnit.getExternalReferenceCode(),
					cpMeasurementUnit.getCompanyId()
				},
				cpMeasurementUnit);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp measurement units in the entity cache if it is enabled.
	 *
	 * @param cpMeasurementUnits the cp measurement units
	 */
	@Override
	public void cacheResult(List<CPMeasurementUnit> cpMeasurementUnits) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpMeasurementUnits.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPMeasurementUnit cpMeasurementUnit : cpMeasurementUnits) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						cpMeasurementUnit.getCtCollectionId())) {

				if (entityCache.getResult(
						CPMeasurementUnitImpl.class,
						cpMeasurementUnit.getPrimaryKey()) == null) {

					cacheResult(cpMeasurementUnit);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		CPMeasurementUnitModelImpl cpMeasurementUnitModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					cpMeasurementUnitModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				cpMeasurementUnitModelImpl.getUuid(),
				cpMeasurementUnitModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, cpMeasurementUnitModelImpl);

			args = new Object[] {
				cpMeasurementUnitModelImpl.getCompanyId(),
				cpMeasurementUnitModelImpl.getKey()
			};

			finderCache.putResult(
				_finderPathFetchByC_K, args, cpMeasurementUnitModelImpl);

			args = new Object[] {
				cpMeasurementUnitModelImpl.getExternalReferenceCode(),
				cpMeasurementUnitModelImpl.getCompanyId()
			};

			finderCache.putResult(
				_finderPathFetchByERC_C, args, cpMeasurementUnitModelImpl);
		}
	}

	/**
	 * Creates a new cp measurement unit with the primary key. Does not add the cp measurement unit to the database.
	 *
	 * @param CPMeasurementUnitId the primary key for the new cp measurement unit
	 * @return the new cp measurement unit
	 */
	@Override
	public CPMeasurementUnit create(long CPMeasurementUnitId) {
		CPMeasurementUnit cpMeasurementUnit = new CPMeasurementUnitImpl();

		cpMeasurementUnit.setNew(true);
		cpMeasurementUnit.setPrimaryKey(CPMeasurementUnitId);

		String uuid = PortalUUIDUtil.generate();

		cpMeasurementUnit.setUuid(uuid);

		cpMeasurementUnit.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpMeasurementUnit;
	}

	/**
	 * Removes the cp measurement unit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPMeasurementUnitId the primary key of the cp measurement unit
	 * @return the cp measurement unit that was removed
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit remove(long CPMeasurementUnitId)
		throws NoSuchCPMeasurementUnitException {

		return remove((Serializable)CPMeasurementUnitId);
	}

	@Override
	protected CPMeasurementUnit removeImpl(
		CPMeasurementUnit cpMeasurementUnit) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpMeasurementUnit)) {
				cpMeasurementUnit = (CPMeasurementUnit)session.get(
					CPMeasurementUnitImpl.class,
					cpMeasurementUnit.getPrimaryKeyObj());
			}

			if ((cpMeasurementUnit != null) &&
				ctPersistenceHelper.isRemove(cpMeasurementUnit)) {

				session.delete(cpMeasurementUnit);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpMeasurementUnit != null) {
			clearCache(cpMeasurementUnit);
		}

		return cpMeasurementUnit;
	}

	@Override
	public CPMeasurementUnit updateImpl(CPMeasurementUnit cpMeasurementUnit) {
		boolean isNew = cpMeasurementUnit.isNew();

		if (!(cpMeasurementUnit instanceof CPMeasurementUnitModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpMeasurementUnit.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpMeasurementUnit);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpMeasurementUnit proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPMeasurementUnit implementation " +
					cpMeasurementUnit.getClass());
		}

		CPMeasurementUnitModelImpl cpMeasurementUnitModelImpl =
			(CPMeasurementUnitModelImpl)cpMeasurementUnit;

		if (Validator.isNull(cpMeasurementUnit.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpMeasurementUnit.setUuid(uuid);
		}

		if (Validator.isNull(cpMeasurementUnit.getExternalReferenceCode())) {
			cpMeasurementUnit.setExternalReferenceCode(
				cpMeasurementUnit.getUuid());
		}
		else {
			if (!Objects.equals(
					cpMeasurementUnitModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					cpMeasurementUnit.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = cpMeasurementUnit.getCompanyId();

					long groupId = cpMeasurementUnit.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = cpMeasurementUnit.getPrimaryKey();
					}

					try {
						cpMeasurementUnit.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								CPMeasurementUnit.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								cpMeasurementUnit.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			CPMeasurementUnit ercCPMeasurementUnit = fetchByERC_C(
				cpMeasurementUnit.getExternalReferenceCode(),
				cpMeasurementUnit.getCompanyId());

			if (isNew) {
				if (ercCPMeasurementUnit != null) {
					throw new DuplicateCPMeasurementUnitExternalReferenceCodeException(
						"Duplicate cp measurement unit with external reference code " +
							cpMeasurementUnit.getExternalReferenceCode() +
								" and company " +
									cpMeasurementUnit.getCompanyId());
				}
			}
			else {
				if ((ercCPMeasurementUnit != null) &&
					(cpMeasurementUnit.getCPMeasurementUnitId() !=
						ercCPMeasurementUnit.getCPMeasurementUnitId())) {

					throw new DuplicateCPMeasurementUnitExternalReferenceCodeException(
						"Duplicate cp measurement unit with external reference code " +
							cpMeasurementUnit.getExternalReferenceCode() +
								" and company " +
									cpMeasurementUnit.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpMeasurementUnit.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpMeasurementUnit.setCreateDate(date);
			}
			else {
				cpMeasurementUnit.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpMeasurementUnitModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpMeasurementUnit.setModifiedDate(date);
			}
			else {
				cpMeasurementUnit.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(cpMeasurementUnit)) {
				if (!isNew) {
					session.evict(
						CPMeasurementUnitImpl.class,
						cpMeasurementUnit.getPrimaryKeyObj());
				}

				session.save(cpMeasurementUnit);
			}
			else {
				cpMeasurementUnit = (CPMeasurementUnit)session.merge(
					cpMeasurementUnit);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPMeasurementUnitImpl.class, cpMeasurementUnitModelImpl, false,
			true);

		cacheUniqueFindersCache(cpMeasurementUnitModelImpl);

		if (isNew) {
			cpMeasurementUnit.setNew(false);
		}

		cpMeasurementUnit.resetOriginalValues();

		return cpMeasurementUnit;
	}

	/**
	 * Returns the cp measurement unit with the primary key or throws a <code>NoSuchCPMeasurementUnitException</code> if it could not be found.
	 *
	 * @param CPMeasurementUnitId the primary key of the cp measurement unit
	 * @return the cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit findByPrimaryKey(long CPMeasurementUnitId)
		throws NoSuchCPMeasurementUnitException {

		return findByPrimaryKey((Serializable)CPMeasurementUnitId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the cp measurement unit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPMeasurementUnitId the primary key of the cp measurement unit
	 * @return the cp measurement unit, or <code>null</code> if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByPrimaryKey(long CPMeasurementUnitId) {
		return fetchByPrimaryKey((Serializable)CPMeasurementUnitId);
	}

	/**
	 * Returns all the cp measurement units.
	 *
	 * @return the cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findAll(
		int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findAll(
		int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

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

			List<CPMeasurementUnit> list = null;

			if (useFinderCache) {
				list = (List<CPMeasurementUnit>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_CPMEASUREMENTUNIT);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_CPMEASUREMENTUNIT;

					sql = sql.concat(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<CPMeasurementUnit>)QueryUtil.list(
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
	 * Removes all the cp measurement units from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPMeasurementUnit cpMeasurementUnit : findAll()) {
			remove(cpMeasurementUnit);
		}
	}

	/**
	 * Returns the number of cp measurement units.
	 *
	 * @return the number of cp measurement units
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CPMeasurementUnit.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_CPMEASUREMENTUNIT);

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
		return "CPMeasurementUnitId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPMEASUREMENTUNIT;
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
		return CPMeasurementUnitModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPMeasurementUnit";
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
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("key_");
		ctMergeColumnNames.add("rate");
		ctMergeColumnNames.add("primary_");
		ctMergeColumnNames.add("priority");
		ctMergeColumnNames.add("type_");
		ctMergeColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPMeasurementUnitId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"companyId", "key_"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "companyId"});
	}

	/**
	 * Initializes the cp measurement unit persistence.
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
			_SQL_SELECT_CPMEASUREMENTUNIT_WHERE,
			_SQL_COUNT_CPMEASUREMENTUNIT_WHERE,
			CPMeasurementUnitModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpMeasurementUnit.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, CPMeasurementUnit::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_CPMEASUREMENTUNIT_WHERE,
			new FinderColumn<>(
				"cpMeasurementUnit.", "uuid", FinderColumn.Type.STRING, "=",
				true, false, CPMeasurementUnit::getUuid),
			new FinderColumn<>(
				"cpMeasurementUnit.", "groupId", FinderColumn.Type.LONG, "=",
				true, true, CPMeasurementUnit::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_CPMEASUREMENTUNIT_WHERE,
				_SQL_COUNT_CPMEASUREMENTUNIT_WHERE,
				CPMeasurementUnitModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpMeasurementUnit.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, CPMeasurementUnit::getUuid),
				new FinderColumn<>(
					"cpMeasurementUnit.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, CPMeasurementUnit::getCompanyId));

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
				_SQL_SELECT_CPMEASUREMENTUNIT_WHERE,
				_SQL_COUNT_CPMEASUREMENTUNIT_WHERE,
				CPMeasurementUnitModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"cpMeasurementUnit.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, CPMeasurementUnit::getCompanyId));

		_finderPathFetchByC_K = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "key_"}, true);

		_finderPathWithPaginationFindByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "type_"}, true);

		_finderPathWithoutPaginationFindByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "type_"}, true);

		_finderPathCountByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "type_"}, false);

		_collectionPersistenceFinderByC_T = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_T,
			_finderPathWithoutPaginationFindByC_T, _finderPathCountByC_T,
			_SQL_SELECT_CPMEASUREMENTUNIT_WHERE,
			_SQL_COUNT_CPMEASUREMENTUNIT_WHERE,
			CPMeasurementUnitModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpMeasurementUnit.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, CPMeasurementUnit::getCompanyId),
			new FinderColumn<>(
				"cpMeasurementUnit.", "type", FinderColumn.Type.INTEGER, "=",
				true, true, CPMeasurementUnit::getType));

		_finderPathWithPaginationFindByC_P_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "primary_", "type_"}, true);

		_finderPathWithoutPaginationFindByC_P_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "primary_", "type_"}, true);

		_finderPathCountByC_P_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "primary_", "type_"}, false);

		_collectionPersistenceFinderByC_P_T = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_P_T,
			_finderPathWithoutPaginationFindByC_P_T, _finderPathCountByC_P_T,
			_SQL_SELECT_CPMEASUREMENTUNIT_WHERE,
			_SQL_COUNT_CPMEASUREMENTUNIT_WHERE,
			CPMeasurementUnitModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"cpMeasurementUnit.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, CPMeasurementUnit::getCompanyId),
			new FinderColumn<>(
				"cpMeasurementUnit.", "primary", FinderColumn.Type.BOOLEAN, "=",
				true, false, CPMeasurementUnit::isPrimary),
			new FinderColumn<>(
				"cpMeasurementUnit.", "type", FinderColumn.Type.INTEGER, "=",
				true, true, CPMeasurementUnit::getType));

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_C, _SQL_SELECT_CPMEASUREMENTUNIT_WHERE,
			new FinderColumn<>(
				"cpMeasurementUnit.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				CPMeasurementUnit::getExternalReferenceCode),
			new FinderColumn<>(
				"cpMeasurementUnit.", "companyId", FinderColumn.Type.LONG, "=",
				true, true, CPMeasurementUnit::getCompanyId));

		CPMeasurementUnitUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CPMeasurementUnitUtil.setPersistence(null);

		entityCache.removeCache(CPMeasurementUnitImpl.class.getName());
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

	private static final String _SQL_SELECT_CPMEASUREMENTUNIT =
		"SELECT cpMeasurementUnit FROM CPMeasurementUnit cpMeasurementUnit";

	private static final String _SQL_SELECT_CPMEASUREMENTUNIT_WHERE =
		"SELECT cpMeasurementUnit FROM CPMeasurementUnit cpMeasurementUnit WHERE ";

	private static final String _SQL_COUNT_CPMEASUREMENTUNIT =
		"SELECT COUNT(cpMeasurementUnit) FROM CPMeasurementUnit cpMeasurementUnit";

	private static final String _SQL_COUNT_CPMEASUREMENTUNIT_WHERE =
		"SELECT COUNT(cpMeasurementUnit) FROM CPMeasurementUnit cpMeasurementUnit WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cpMeasurementUnit.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPMeasurementUnit exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPMeasurementUnitPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "key", "primary", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-913732266