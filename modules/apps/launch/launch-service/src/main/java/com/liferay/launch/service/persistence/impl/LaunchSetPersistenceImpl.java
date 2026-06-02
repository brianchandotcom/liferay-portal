/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.persistence.impl;

import com.liferay.launch.exception.DuplicateLaunchSetExternalReferenceCodeException;
import com.liferay.launch.exception.NoSuchLaunchSetException;
import com.liferay.launch.model.LaunchSet;
import com.liferay.launch.model.LaunchSetTable;
import com.liferay.launch.model.impl.LaunchSetImpl;
import com.liferay.launch.model.impl.LaunchSetModelImpl;
import com.liferay.launch.service.persistence.LaunchSetPersistence;
import com.liferay.launch.service.persistence.LaunchSetUtil;
import com.liferay.launch.service.persistence.impl.constants.LaunchPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
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
import com.liferay.portal.kernel.service.persistence.impl.ArrayableFinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
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
 * The persistence implementation for the launch set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = LaunchSetPersistence.class)
public class LaunchSetPersistenceImpl
	extends BasePersistenceImpl<LaunchSet, NoSuchLaunchSetException>
	implements LaunchSetPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LaunchSetUtil</code> to access the launch set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LaunchSetImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private CollectionPersistenceFinder<LaunchSet, NoSuchLaunchSetException>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns an ordered range of all the launch sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first launch set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByUuid_First(
			String uuid, OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		return _collectionPersistenceFinderByUuid.findFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Returns the first launch set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByUuid_First(
		String uuid, OrderByComparator<LaunchSet> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the launch sets where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of launch sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private CollectionPersistenceFinder<LaunchSet, NoSuchLaunchSetException>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns an ordered range of all the launch sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first launch set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		return _collectionPersistenceFinderByUuid_C.findFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Returns the first launch set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LaunchSet> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the launch sets where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of launch sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private CollectionPersistenceFinder<LaunchSet, NoSuchLaunchSetException>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first launch set in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByCompanyId_First(
			long companyId, OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		return _collectionPersistenceFinderByCompanyId.findFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Returns the first launch set in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByCompanyId_First(
		long companyId, OrderByComparator<LaunchSet> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the launch sets where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of launch sets where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	private CollectionPersistenceFinder<LaunchSet, NoSuchLaunchSetException>
		_collectionPersistenceFinderByC_U;

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_U.find(
			finderCache, new Object[] {companyId, userId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first launch set in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByC_U_First(
			long companyId, long userId,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		return _collectionPersistenceFinderByC_U.findFirst(
			finderCache, new Object[] {companyId, userId}, orderByComparator);
	}

	/**
	 * Returns the first launch set in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByC_U_First(
		long companyId, long userId,
		OrderByComparator<LaunchSet> orderByComparator) {

		return _collectionPersistenceFinderByC_U.fetchFirst(
			finderCache, new Object[] {companyId, userId}, orderByComparator);
	}

	/**
	 * Removes all the launch sets where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByC_U(long companyId, long userId) {
		_collectionPersistenceFinderByC_U.remove(
			finderCache, new Object[] {companyId, userId});
	}

	/**
	 * Returns the number of launch sets where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		return _collectionPersistenceFinderByC_U.count(
			finderCache, new Object[] {companyId, userId});
	}

	private CollectionPersistenceFinder<LaunchSet, NoSuchLaunchSetException>
		_collectionPersistenceFinderByC_S;

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_S.find(
			finderCache, new Object[] {companyId, new int[] {status}}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first launch set in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByC_S_First(
			long companyId, int status,
			OrderByComparator<LaunchSet> orderByComparator)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = fetchByC_S_First(
			companyId, status, orderByComparator);

		if (launchSet != null) {
			return launchSet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchLaunchSetException(sb.toString());
	}

	/**
	 * Returns the first launch set in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByC_S_First(
		long companyId, int status,
		OrderByComparator<LaunchSet> orderByComparator) {

		return _collectionPersistenceFinderByC_S.fetchFirst(
			finderCache, new Object[] {companyId, new int[] {status}},
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the launch sets where companyId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch sets
	 */
	@Override
	public List<LaunchSet> findByC_S(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<LaunchSet> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_S.find(
			finderCache,
			new Object[] {companyId, ArrayUtil.sortedUnique(statuses)}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the launch sets where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long companyId, int status) {
		_collectionPersistenceFinderByC_S.remove(
			finderCache, new Object[] {companyId, new int[] {status}});
	}

	/**
	 * Returns the number of launch sets where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByC_S(long companyId, int status) {
		return _collectionPersistenceFinderByC_S.count(
			finderCache, new Object[] {companyId, new int[] {status}});
	}

	/**
	 * Returns the number of launch sets where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByC_S(long companyId, int[] statuses) {
		return _collectionPersistenceFinderByC_S.count(
			finderCache,
			new Object[] {companyId, ArrayUtil.sortedUnique(statuses)});
	}

	private UniquePersistenceFinder<LaunchSet, NoSuchLaunchSetException>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the launch set where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchLaunchSetException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching launch set
	 * @throws NoSuchLaunchSetException if a matching launch set could not be found
	 */
	@Override
	public LaunchSet findByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchLaunchSetException {

		return _uniquePersistenceFinderByERC_C.find(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	/**
	 * Returns the launch set where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Override
	public LaunchSet fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_C.fetch(
			finderCache, new Object[] {externalReferenceCode, companyId},
			useFinderCache);
	}

	/**
	 * Removes the launch set where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the launch set that was removed
	 */
	@Override
	public LaunchSet removeByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchLaunchSetException {

		LaunchSet launchSet = findByERC_C(externalReferenceCode, companyId);

		return remove(launchSet);
	}

	/**
	 * Returns the number of launch sets where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching launch sets
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public LaunchSetPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(LaunchSet.class);

		setModelImplClass(LaunchSetImpl.class);
		setModelPKClass(long.class);

		setTable(LaunchSetTable.INSTANCE);
	}

	/**
	 * Creates a new launch set with the primary key. Does not add the launch set to the database.
	 *
	 * @param launchSetId the primary key for the new launch set
	 * @return the new launch set
	 */
	@Override
	public LaunchSet create(long launchSetId) {
		LaunchSet launchSet = new LaunchSetImpl();

		launchSet.setNew(true);
		launchSet.setPrimaryKey(launchSetId);

		String uuid = PortalUUIDUtil.generate();

		launchSet.setUuid(uuid);

		launchSet.setCompanyId(CompanyThreadLocal.getCompanyId());

		return launchSet;
	}

	/**
	 * Removes the launch set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set that was removed
	 * @throws NoSuchLaunchSetException if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet remove(long launchSetId) throws NoSuchLaunchSetException {
		return remove((Serializable)launchSetId);
	}

	@Override
	protected LaunchSet removeImpl(LaunchSet launchSet) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(launchSet)) {
				launchSet = (LaunchSet)session.get(
					LaunchSetImpl.class, launchSet.getPrimaryKeyObj());
			}

			if (launchSet != null) {
				session.delete(launchSet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (launchSet != null) {
			clearCache(launchSet);
		}

		return launchSet;
	}

	@Override
	public LaunchSet updateImpl(LaunchSet launchSet) {
		boolean isNew = launchSet.isNew();

		if (!(launchSet instanceof LaunchSetModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(launchSet.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(launchSet);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in launchSet proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LaunchSet implementation " +
					launchSet.getClass());
		}

		LaunchSetModelImpl launchSetModelImpl = (LaunchSetModelImpl)launchSet;

		if (Validator.isNull(launchSet.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			launchSet.setUuid(uuid);
		}

		if (Validator.isNull(launchSet.getExternalReferenceCode())) {
			launchSet.setExternalReferenceCode(launchSet.getUuid());
		}
		else {
			if (!Objects.equals(
					launchSetModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					launchSet.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = launchSet.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = launchSet.getPrimaryKey();
					}

					try {
						launchSet.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								LaunchSet.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								launchSet.getExternalReferenceCode(), null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			LaunchSet ercLaunchSet = fetchByERC_C(
				launchSet.getExternalReferenceCode(), launchSet.getCompanyId());

			if (isNew) {
				if (ercLaunchSet != null) {
					throw new DuplicateLaunchSetExternalReferenceCodeException(
						"Duplicate launch set with external reference code " +
							launchSet.getExternalReferenceCode() +
								" and company " + launchSet.getCompanyId());
				}
			}
			else {
				if ((ercLaunchSet != null) &&
					(launchSet.getLaunchSetId() !=
						ercLaunchSet.getLaunchSetId())) {

					throw new DuplicateLaunchSetExternalReferenceCodeException(
						"Duplicate launch set with external reference code " +
							launchSet.getExternalReferenceCode() +
								" and company " + launchSet.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (launchSet.getCreateDate() == null)) {
			if (serviceContext == null) {
				launchSet.setCreateDate(date);
			}
			else {
				launchSet.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!launchSetModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				launchSet.setModifiedDate(date);
			}
			else {
				launchSet.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(launchSet);
			}
			else {
				launchSet = (LaunchSet)session.merge(launchSet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(launchSet, false);

		if (isNew) {
			launchSet.setNew(false);
		}

		launchSet.resetOriginalValues();

		return launchSet;
	}

	/**
	 * Returns the launch set with the primary key or throws a <code>NoSuchLaunchSetException</code> if it could not be found.
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set
	 * @throws NoSuchLaunchSetException if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet findByPrimaryKey(long launchSetId)
		throws NoSuchLaunchSetException {

		return findByPrimaryKey((Serializable)launchSetId);
	}

	/**
	 * Returns the launch set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set, or <code>null</code> if a launch set with the primary key could not be found
	 */
	@Override
	public LaunchSet fetchByPrimaryKey(long launchSetId) {
		return fetchByPrimaryKey((Serializable)launchSetId);
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
		return "launchSetId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAUNCHSET;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LaunchSetModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the launch set persistence.
	 */
	@Activate
	public void activate() {
		_collectionPersistenceFinderByUuid = new CollectionPersistenceFinder<>(
			this,
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
				new String[] {
					String.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"uuid_"}, true),
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
				new String[] {String.class.getName()}, new String[] {"uuid_"},
				0, 1, true, null),
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
				new String[] {String.class.getName()}, new String[] {"uuid_"},
				0, 1, false, null),
			_SQL_SELECT_LAUNCHSET_WHERE, _SQL_COUNT_LAUNCHSET_WHERE,
			LaunchSetModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"launchSet.", "uuid", "uuid_", FinderColumn.Type.STRING, "=",
				true, true, LaunchSet::getUuid));

		_collectionPersistenceFinderByUuid_C =
			new CollectionPersistenceFinder<>(
				this,
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
					new String[] {
						String.class.getName(), Long.class.getName(),
						Integer.class.getName(), Integer.class.getName(),
						OrderByComparator.class.getName()
					},
					new String[] {"uuid_", "companyId"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
					new String[] {String.class.getName(), Long.class.getName()},
					new String[] {"uuid_", "companyId"}, 0, 1, true, null),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
					new String[] {String.class.getName(), Long.class.getName()},
					new String[] {"uuid_", "companyId"}, 0, 1, false, null),
				_SQL_SELECT_LAUNCHSET_WHERE, _SQL_COUNT_LAUNCHSET_WHERE,
				LaunchSetModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"launchSet.", "uuid", "uuid_", FinderColumn.Type.STRING,
					"=", true, true, LaunchSet::getUuid),
				new FinderColumn<>(
					"launchSet.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, LaunchSet::getCompanyId));

		_collectionPersistenceFinderByCompanyId =
			new CollectionPersistenceFinder<>(
				this,
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
					new String[] {
						Long.class.getName(), Integer.class.getName(),
						Integer.class.getName(),
						OrderByComparator.class.getName()
					},
					new String[] {"companyId"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"findByCompanyId", new String[] {Long.class.getName()},
					new String[] {"companyId"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"countByCompanyId", new String[] {Long.class.getName()},
					new String[] {"companyId"}, false),
				_SQL_SELECT_LAUNCHSET_WHERE, _SQL_COUNT_LAUNCHSET_WHERE,
				LaunchSetModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"launchSet.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, LaunchSet::getCompanyId));

		_collectionPersistenceFinderByC_U = new CollectionPersistenceFinder<>(
			this,
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				},
				new String[] {"companyId", "userId"}, true),
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_U",
				new String[] {Long.class.getName(), Long.class.getName()},
				new String[] {"companyId", "userId"}, true),
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
				new String[] {Long.class.getName(), Long.class.getName()},
				new String[] {"companyId", "userId"}, false),
			_SQL_SELECT_LAUNCHSET_WHERE, _SQL_COUNT_LAUNCHSET_WHERE,
			LaunchSetModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"launchSet.", "companyId", FinderColumn.Type.LONG, "=", true,
				true, LaunchSet::getCompanyId),
			new FinderColumn<>(
				"launchSet.", "userId", FinderColumn.Type.LONG, "=", true, true,
				LaunchSet::getUserId));

		_collectionPersistenceFinderByC_S = new CollectionPersistenceFinder<>(
			this,
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				},
				new String[] {"companyId", "status"}, true),
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
				new String[] {Long.class.getName(), Integer.class.getName()},
				new String[] {"companyId", "status"}, true),
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_S",
				new String[] {Long.class.getName(), Integer.class.getName()},
				new String[] {"companyId", "status"}, false),
			_SQL_SELECT_LAUNCHSET_WHERE, _SQL_COUNT_LAUNCHSET_WHERE,
			LaunchSetModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"launchSet.", "companyId", FinderColumn.Type.LONG, "=", true,
				true, LaunchSet::getCompanyId),
			new ArrayableFinderColumn<>(
				"launchSet.", "status", FinderColumn.Type.INTEGER, "=", false,
				true, true, LaunchSet::getStatus));

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this,
			createUniqueFinderPath(
				FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
				new String[] {String.class.getName(), Long.class.getName()},
				new String[] {"externalReferenceCode", "companyId"}, 0, 1,
				false, convertNullFunction(LaunchSet::getExternalReferenceCode),
				LaunchSet::getCompanyId),
			_SQL_SELECT_LAUNCHSET_WHERE, "",
			new FinderColumn<>(
				"launchSet.", "externalReferenceCode", FinderColumn.Type.STRING,
				"=", true, true, LaunchSet::getExternalReferenceCode),
			new FinderColumn<>(
				"launchSet.", "companyId", FinderColumn.Type.LONG, "=", true,
				true, LaunchSet::getCompanyId));

		LaunchSetUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		LaunchSetUtil.setPersistence(null);

		entityCache.removeCache(LaunchSetImpl.class.getName());
	}

	@Override
	@Reference(
		target = LaunchPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = LaunchPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = LaunchPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _ENTITY_ALIAS_PREFIX =
		LaunchSetModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_LAUNCHSET =
		"SELECT launchSet FROM LaunchSet launchSet";

	private static final String _SQL_SELECT_LAUNCHSET_WHERE =
		"SELECT launchSet FROM LaunchSet launchSet WHERE ";

	private static final String _SQL_COUNT_LAUNCHSET_WHERE =
		"SELECT COUNT(launchSet) FROM LaunchSet launchSet WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LaunchSet exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LaunchSetPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-859064190