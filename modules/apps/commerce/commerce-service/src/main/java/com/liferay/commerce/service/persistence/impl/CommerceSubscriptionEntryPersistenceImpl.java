/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.exception.NoSuchSubscriptionEntryException;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.model.CommerceSubscriptionEntryTable;
import com.liferay.commerce.model.impl.CommerceSubscriptionEntryImpl;
import com.liferay.commerce.model.impl.CommerceSubscriptionEntryModelImpl;
import com.liferay.commerce.service.persistence.CommerceSubscriptionEntryPersistence;
import com.liferay.commerce.service.persistence.CommerceSubscriptionEntryUtil;
import com.liferay.commerce.service.persistence.impl.constants.CommercePersistenceConstants;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the commerce subscription entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = CommerceSubscriptionEntryPersistence.class)
public class CommerceSubscriptionEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceSubscriptionEntry>
	implements CommerceSubscriptionEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceSubscriptionEntryUtil</code> to access the commerce subscription entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceSubscriptionEntryImpl.class.getName();

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
	private CollectionPersistenceFinder<CommerceSubscriptionEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the commerce subscription entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByUuid_First(
			String uuid,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the commerce subscription entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<CommerceSubscriptionEntry>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the commerce subscription entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSubscriptionEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByUUID_G(
			uuid, groupId);

		if (commerceSubscriptionEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSubscriptionEntryException(sb.toString());
		}

		return commerceSubscriptionEntry;
	}

	/**
	 * Returns the commerce subscription entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce subscription entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return _uniquePersistenceFinderByUUID_G.fetch(
			finderCache, new Object[] {uuid, groupId}, useFinderCache);
	}

	/**
	 * Removes the commerce subscription entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce subscription entry that was removed
	 */
	@Override
	public CommerceSubscriptionEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = findByUUID_G(
			uuid, groupId);

		return remove(commerceSubscriptionEntry);
	}

	/**
	 * Returns the number of commerce subscription entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<CommerceSubscriptionEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the commerce subscription entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the commerce subscription entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<CommerceSubscriptionEntry>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the commerce subscription entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByGroupId.find(
			finderCache, new Object[] {groupId}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByGroupId_First(
			long groupId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByGroupId_First(groupId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the commerce subscription entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		return _collectionPersistenceFinderByGroupId.count(
			finderCache, new Object[] {groupId});
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<CommerceSubscriptionEntry>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the commerce subscription entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the commerce subscription entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	private FinderPath _finderPathFetchByCommerceOrderItemId;
	private UniquePersistenceFinder<CommerceSubscriptionEntry>
		_uniquePersistenceFinderByCommerceOrderItemId;

	/**
	 * Returns the commerce subscription entry where commerceOrderItemId = &#63; or throws a <code>NoSuchSubscriptionEntryException</code> if it could not be found.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByCommerceOrderItemId(
			long commerceOrderItemId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByCommerceOrderItemId(commerceOrderItemId);

		if (commerceSubscriptionEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceOrderItemId=");
			sb.append(commerceOrderItemId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSubscriptionEntryException(sb.toString());
		}

		return commerceSubscriptionEntry;
	}

	/**
	 * Returns the commerce subscription entry where commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByCommerceOrderItemId(
		long commerceOrderItemId) {

		return fetchByCommerceOrderItemId(commerceOrderItemId, true);
	}

	/**
	 * Returns the commerce subscription entry where commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByCommerceOrderItemId(
		long commerceOrderItemId, boolean useFinderCache) {

		return _uniquePersistenceFinderByCommerceOrderItemId.fetch(
			finderCache, new Object[] {commerceOrderItemId}, useFinderCache);
	}

	/**
	 * Removes the commerce subscription entry where commerceOrderItemId = &#63; from the database.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the commerce subscription entry that was removed
	 */
	@Override
	public CommerceSubscriptionEntry removeByCommerceOrderItemId(
			long commerceOrderItemId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			findByCommerceOrderItemId(commerceOrderItemId);

		return remove(commerceSubscriptionEntry);
	}

	/**
	 * Returns the number of commerce subscription entries where commerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByCommerceOrderItemId(long commerceOrderItemId) {
		return _uniquePersistenceFinderByCommerceOrderItemId.count(
			finderCache, new Object[] {commerceOrderItemId});
	}

	private FinderPath _finderPathWithPaginationFindBySubscriptionStatus;
	private FinderPath _finderPathWithoutPaginationFindBySubscriptionStatus;
	private FinderPath _finderPathCountBySubscriptionStatus;
	private CollectionPersistenceFinder<CommerceSubscriptionEntry>
		_collectionPersistenceFinderBySubscriptionStatus;

	/**
	 * Returns all the commerce subscription entries where subscriptionStatus = &#63;.
	 *
	 * @param subscriptionStatus the subscription status
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findBySubscriptionStatus(
		int subscriptionStatus) {

		return findBySubscriptionStatus(
			subscriptionStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where subscriptionStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param subscriptionStatus the subscription status
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findBySubscriptionStatus(
		int subscriptionStatus, int start, int end) {

		return findBySubscriptionStatus(subscriptionStatus, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where subscriptionStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param subscriptionStatus the subscription status
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findBySubscriptionStatus(
		int subscriptionStatus, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findBySubscriptionStatus(
			subscriptionStatus, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where subscriptionStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param subscriptionStatus the subscription status
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findBySubscriptionStatus(
		int subscriptionStatus, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderBySubscriptionStatus.find(
			finderCache, new Object[] {subscriptionStatus}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where subscriptionStatus = &#63;.
	 *
	 * @param subscriptionStatus the subscription status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findBySubscriptionStatus_First(
			int subscriptionStatus,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchBySubscriptionStatus_First(
				subscriptionStatus, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("subscriptionStatus=");
		sb.append(subscriptionStatus);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where subscriptionStatus = &#63;.
	 *
	 * @param subscriptionStatus the subscription status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchBySubscriptionStatus_First(
		int subscriptionStatus,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findBySubscriptionStatus(
			subscriptionStatus, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the commerce subscription entries where subscriptionStatus = &#63; from the database.
	 *
	 * @param subscriptionStatus the subscription status
	 */
	@Override
	public void removeBySubscriptionStatus(int subscriptionStatus) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findBySubscriptionStatus(
					subscriptionStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where subscriptionStatus = &#63;.
	 *
	 * @param subscriptionStatus the subscription status
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countBySubscriptionStatus(int subscriptionStatus) {
		return _collectionPersistenceFinderBySubscriptionStatus.count(
			finderCache, new Object[] {subscriptionStatus});
	}

	private FinderPath _finderPathWithPaginationFindByC_U;
	private FinderPath _finderPathWithoutPaginationFindByC_U;
	private FinderPath _finderPathCountByC_U;
	private CollectionPersistenceFinder<CommerceSubscriptionEntry>
		_collectionPersistenceFinderByC_U;

	/**
	 * Returns all the commerce subscription entries where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByC_U(
		long companyId, long userId) {

		return findByC_U(
			companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByC_U(
		long companyId, long userId, int start, int end) {

		return findByC_U(companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByC_U(
			companyId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_U.find(
			finderCache, new Object[] {companyId, userId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByC_U_First(
			long companyId, long userId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByC_U_First(
			companyId, userId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByC_U_First(
		long companyId, long userId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByC_U(
			companyId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the commerce subscription entries where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByC_U(long companyId, long userId) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByC_U(
					companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		return _collectionPersistenceFinderByC_U.count(
			finderCache, new Object[] {companyId, userId});
	}

	private FinderPath _finderPathWithPaginationFindByG_C_U;
	private FinderPath _finderPathWithoutPaginationFindByG_C_U;
	private FinderPath _finderPathCountByG_C_U;
	private CollectionPersistenceFinder<CommerceSubscriptionEntry>
		_collectionPersistenceFinderByG_C_U;

	/**
	 * Returns all the commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByG_C_U(
		long groupId, long companyId, long userId) {

		return findByG_C_U(
			groupId, companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByG_C_U(
		long groupId, long companyId, long userId, int start, int end) {

		return findByG_C_U(groupId, companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByG_C_U(
		long groupId, long companyId, long userId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByG_C_U(
			groupId, companyId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByG_C_U(
		long groupId, long companyId, long userId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_C_U.find(
			finderCache, new Object[] {groupId, companyId, userId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByG_C_U_First(
			long groupId, long companyId, long userId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByG_C_U_First(groupId, companyId, userId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByG_C_U_First(
		long groupId, long companyId, long userId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByG_C_U(
			groupId, companyId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByG_C_U(long groupId, long companyId, long userId) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByG_C_U(
					groupId, companyId, userId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByG_C_U(long groupId, long companyId, long userId) {
		return _collectionPersistenceFinderByG_C_U.count(
			finderCache, new Object[] {groupId, companyId, userId});
	}

	private FinderPath _finderPathFetchByC_C_C;
	private UniquePersistenceFinder<CommerceSubscriptionEntry>
		_uniquePersistenceFinderByC_C_C;

	/**
	 * Returns the commerce subscription entry where CPInstanceUuid = &#63; and CProductId = &#63; and commerceOrderItemId = &#63; or throws a <code>NoSuchSubscriptionEntryException</code> if it could not be found.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByC_C_C(
			String CPInstanceUuid, long CProductId, long commerceOrderItemId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByC_C_C(
			CPInstanceUuid, CProductId, commerceOrderItemId);

		if (commerceSubscriptionEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPInstanceUuid=");
			sb.append(CPInstanceUuid);

			sb.append(", CProductId=");
			sb.append(CProductId);

			sb.append(", commerceOrderItemId=");
			sb.append(commerceOrderItemId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSubscriptionEntryException(sb.toString());
		}

		return commerceSubscriptionEntry;
	}

	/**
	 * Returns the commerce subscription entry where CPInstanceUuid = &#63; and CProductId = &#63; and commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByC_C_C(
		String CPInstanceUuid, long CProductId, long commerceOrderItemId) {

		return fetchByC_C_C(
			CPInstanceUuid, CProductId, commerceOrderItemId, true);
	}

	/**
	 * Returns the commerce subscription entry where CPInstanceUuid = &#63; and CProductId = &#63; and commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByC_C_C(
		String CPInstanceUuid, long CProductId, long commerceOrderItemId,
		boolean useFinderCache) {

		return _uniquePersistenceFinderByC_C_C.fetch(
			finderCache,
			new Object[] {CPInstanceUuid, CProductId, commerceOrderItemId},
			useFinderCache);
	}

	/**
	 * Removes the commerce subscription entry where CPInstanceUuid = &#63; and CProductId = &#63; and commerceOrderItemId = &#63; from the database.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the commerce subscription entry that was removed
	 */
	@Override
	public CommerceSubscriptionEntry removeByC_C_C(
			String CPInstanceUuid, long CProductId, long commerceOrderItemId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = findByC_C_C(
			CPInstanceUuid, CProductId, commerceOrderItemId);

		return remove(commerceSubscriptionEntry);
	}

	/**
	 * Returns the number of commerce subscription entries where CPInstanceUuid = &#63; and CProductId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByC_C_C(
		String CPInstanceUuid, long CProductId, long commerceOrderItemId) {

		return _uniquePersistenceFinderByC_C_C.count(
			finderCache,
			new Object[] {CPInstanceUuid, CProductId, commerceOrderItemId});
	}

	public CommerceSubscriptionEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"deliverySubscriptionTypeSettings", "deliverySubTypeSettings");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceSubscriptionEntry.class);

		setModelImplClass(CommerceSubscriptionEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceSubscriptionEntryTable.INSTANCE);
	}

	/**
	 * Caches the commerce subscription entry in the entity cache if it is enabled.
	 *
	 * @param commerceSubscriptionEntry the commerce subscription entry
	 */
	@Override
	public void cacheResult(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		entityCache.putResult(
			CommerceSubscriptionEntryImpl.class,
			commerceSubscriptionEntry.getPrimaryKey(),
			commerceSubscriptionEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				commerceSubscriptionEntry.getUuid(),
				commerceSubscriptionEntry.getGroupId()
			},
			commerceSubscriptionEntry);

		finderCache.putResult(
			_finderPathFetchByCommerceOrderItemId,
			new Object[] {commerceSubscriptionEntry.getCommerceOrderItemId()},
			commerceSubscriptionEntry);

		finderCache.putResult(
			_finderPathFetchByC_C_C,
			new Object[] {
				commerceSubscriptionEntry.getCPInstanceUuid(),
				commerceSubscriptionEntry.getCProductId(),
				commerceSubscriptionEntry.getCommerceOrderItemId()
			},
			commerceSubscriptionEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce subscription entries in the entity cache if it is enabled.
	 *
	 * @param commerceSubscriptionEntries the commerce subscription entries
	 */
	@Override
	public void cacheResult(
		List<CommerceSubscriptionEntry> commerceSubscriptionEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceSubscriptionEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				commerceSubscriptionEntries) {

			if (entityCache.getResult(
					CommerceSubscriptionEntryImpl.class,
					commerceSubscriptionEntry.getPrimaryKey()) == null) {

				cacheResult(commerceSubscriptionEntry);
			}
		}
	}

	/**
	 * Clears the cache for all commerce subscription entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceSubscriptionEntryImpl.class);

		finderCache.clearCache(CommerceSubscriptionEntryImpl.class);
	}

	/**
	 * Clears the cache for the commerce subscription entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		entityCache.removeResult(
			CommerceSubscriptionEntryImpl.class, commerceSubscriptionEntry);
	}

	@Override
	public void clearCache(
		List<CommerceSubscriptionEntry> commerceSubscriptionEntries) {

		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				commerceSubscriptionEntries) {

			entityCache.removeResult(
				CommerceSubscriptionEntryImpl.class, commerceSubscriptionEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceSubscriptionEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceSubscriptionEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceSubscriptionEntryModelImpl commerceSubscriptionEntryModelImpl) {

		Object[] args = new Object[] {
			commerceSubscriptionEntryModelImpl.getUuid(),
			commerceSubscriptionEntryModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathFetchByUUID_G, args, commerceSubscriptionEntryModelImpl);

		args = new Object[] {
			commerceSubscriptionEntryModelImpl.getCommerceOrderItemId()
		};

		finderCache.putResult(
			_finderPathFetchByCommerceOrderItemId, args,
			commerceSubscriptionEntryModelImpl);

		args = new Object[] {
			commerceSubscriptionEntryModelImpl.getCPInstanceUuid(),
			commerceSubscriptionEntryModelImpl.getCProductId(),
			commerceSubscriptionEntryModelImpl.getCommerceOrderItemId()
		};

		finderCache.putResult(
			_finderPathFetchByC_C_C, args, commerceSubscriptionEntryModelImpl);
	}

	/**
	 * Creates a new commerce subscription entry with the primary key. Does not add the commerce subscription entry to the database.
	 *
	 * @param commerceSubscriptionEntryId the primary key for the new commerce subscription entry
	 * @return the new commerce subscription entry
	 */
	@Override
	public CommerceSubscriptionEntry create(long commerceSubscriptionEntryId) {
		CommerceSubscriptionEntry commerceSubscriptionEntry =
			new CommerceSubscriptionEntryImpl();

		commerceSubscriptionEntry.setNew(true);
		commerceSubscriptionEntry.setPrimaryKey(commerceSubscriptionEntryId);

		String uuid = PortalUUIDUtil.generate();

		commerceSubscriptionEntry.setUuid(uuid);

		commerceSubscriptionEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceSubscriptionEntry;
	}

	/**
	 * Removes the commerce subscription entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the commerce subscription entry
	 * @return the commerce subscription entry that was removed
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry remove(long commerceSubscriptionEntryId)
		throws NoSuchSubscriptionEntryException {

		return remove((Serializable)commerceSubscriptionEntryId);
	}

	/**
	 * Removes the commerce subscription entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce subscription entry
	 * @return the commerce subscription entry that was removed
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry remove(Serializable primaryKey)
		throws NoSuchSubscriptionEntryException {

		Session session = null;

		try {
			session = openSession();

			CommerceSubscriptionEntry commerceSubscriptionEntry =
				(CommerceSubscriptionEntry)session.get(
					CommerceSubscriptionEntryImpl.class, primaryKey);

			if (commerceSubscriptionEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSubscriptionEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceSubscriptionEntry);
		}
		catch (NoSuchSubscriptionEntryException noSuchEntityException) {
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
	protected CommerceSubscriptionEntry removeImpl(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceSubscriptionEntry)) {
				commerceSubscriptionEntry =
					(CommerceSubscriptionEntry)session.get(
						CommerceSubscriptionEntryImpl.class,
						commerceSubscriptionEntry.getPrimaryKeyObj());
			}

			if (commerceSubscriptionEntry != null) {
				session.delete(commerceSubscriptionEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceSubscriptionEntry != null) {
			clearCache(commerceSubscriptionEntry);
		}

		return commerceSubscriptionEntry;
	}

	@Override
	public CommerceSubscriptionEntry updateImpl(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		boolean isNew = commerceSubscriptionEntry.isNew();

		if (!(commerceSubscriptionEntry instanceof
				CommerceSubscriptionEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceSubscriptionEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceSubscriptionEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceSubscriptionEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceSubscriptionEntry implementation " +
					commerceSubscriptionEntry.getClass());
		}

		CommerceSubscriptionEntryModelImpl commerceSubscriptionEntryModelImpl =
			(CommerceSubscriptionEntryModelImpl)commerceSubscriptionEntry;

		if (Validator.isNull(commerceSubscriptionEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceSubscriptionEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceSubscriptionEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceSubscriptionEntry.setCreateDate(date);
			}
			else {
				commerceSubscriptionEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceSubscriptionEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceSubscriptionEntry.setModifiedDate(date);
			}
			else {
				commerceSubscriptionEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceSubscriptionEntry);
			}
			else {
				commerceSubscriptionEntry =
					(CommerceSubscriptionEntry)session.merge(
						commerceSubscriptionEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceSubscriptionEntryImpl.class,
			commerceSubscriptionEntryModelImpl, false, true);

		cacheUniqueFindersCache(commerceSubscriptionEntryModelImpl);

		if (isNew) {
			commerceSubscriptionEntry.setNew(false);
		}

		commerceSubscriptionEntry.resetOriginalValues();

		return commerceSubscriptionEntry;
	}

	/**
	 * Returns the commerce subscription entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce subscription entry
	 * @return the commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByPrimaryKey(
			primaryKey);

		if (commerceSubscriptionEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSubscriptionEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceSubscriptionEntry;
	}

	/**
	 * Returns the commerce subscription entry with the primary key or throws a <code>NoSuchSubscriptionEntryException</code> if it could not be found.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the commerce subscription entry
	 * @return the commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByPrimaryKey(
			long commerceSubscriptionEntryId)
		throws NoSuchSubscriptionEntryException {

		return findByPrimaryKey((Serializable)commerceSubscriptionEntryId);
	}

	/**
	 * Returns the commerce subscription entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the commerce subscription entry
	 * @return the commerce subscription entry, or <code>null</code> if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByPrimaryKey(
		long commerceSubscriptionEntryId) {

		return fetchByPrimaryKey((Serializable)commerceSubscriptionEntryId);
	}

	/**
	 * Returns all the commerce subscription entries.
	 *
	 * @return the commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		List<CommerceSubscriptionEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceSubscriptionEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESUBSCRIPTIONENTRY;

				sql = sql.concat(
					CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceSubscriptionEntry>)QueryUtil.list(
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
	 * Removes all the commerce subscription entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry : findAll()) {
			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries.
	 *
	 * @return the number of commerce subscription entries
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
					_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY);

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
		return "commerceSubscriptionEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCESUBSCRIPTIONENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceSubscriptionEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce subscription entry persistence.
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
			_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE,
			_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE,
			CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"commerceSubscriptionEntry.", "uuid", FinderColumn.Type.STRING,
				"=", true, true, CommerceSubscriptionEntry::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G,
			_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE,
			new FinderColumn<>(
				"commerceSubscriptionEntry.", "uuid", FinderColumn.Type.STRING,
				"=", true, false, CommerceSubscriptionEntry::getUuid),
			new FinderColumn<>(
				"commerceSubscriptionEntry.", "groupId", FinderColumn.Type.LONG,
				"=", true, true, CommerceSubscriptionEntry::getGroupId));

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
				_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE,
				_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE,
				CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commerceSubscriptionEntry.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					CommerceSubscriptionEntry::getUuid),
				new FinderColumn<>(
					"commerceSubscriptionEntry.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					CommerceSubscriptionEntry::getCompanyId));

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
				_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE,
				_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE,
				CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commerceSubscriptionEntry.", "groupId",
					FinderColumn.Type.LONG, "=", true, true,
					CommerceSubscriptionEntry::getGroupId));

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
				_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE,
				_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE,
				CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commerceSubscriptionEntry.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					CommerceSubscriptionEntry::getCompanyId));

		_finderPathFetchByCommerceOrderItemId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCommerceOrderItemId",
			new String[] {Long.class.getName()},
			new String[] {"commerceOrderItemId"}, true);

		_uniquePersistenceFinderByCommerceOrderItemId =
			new UniquePersistenceFinder<>(
				this, _finderPathFetchByCommerceOrderItemId,
				_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE,
				new FinderColumn<>(
					"commerceSubscriptionEntry.", "commerceOrderItemId",
					FinderColumn.Type.LONG, "=", true, true,
					CommerceSubscriptionEntry::getCommerceOrderItemId));

		_finderPathWithPaginationFindBySubscriptionStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySubscriptionStatus",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"subscriptionStatus"}, true);

		_finderPathWithoutPaginationFindBySubscriptionStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySubscriptionStatus", new String[] {Integer.class.getName()},
			new String[] {"subscriptionStatus"}, true);

		_finderPathCountBySubscriptionStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySubscriptionStatus", new String[] {Integer.class.getName()},
			new String[] {"subscriptionStatus"}, false);

		_collectionPersistenceFinderBySubscriptionStatus =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindBySubscriptionStatus,
				_finderPathWithoutPaginationFindBySubscriptionStatus,
				_finderPathCountBySubscriptionStatus,
				_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE,
				_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE,
				CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commerceSubscriptionEntry.", "subscriptionStatus",
					FinderColumn.Type.INTEGER, "=", true, true,
					CommerceSubscriptionEntry::getSubscriptionStatus));

		_finderPathWithPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "userId"}, true);

		_finderPathWithoutPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, true);

		_finderPathCountByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, false);

		_collectionPersistenceFinderByC_U = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_U,
			_finderPathWithoutPaginationFindByC_U, _finderPathCountByC_U,
			_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE,
			_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE,
			CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"commerceSubscriptionEntry.", "companyId",
				FinderColumn.Type.LONG, "=", true, false,
				CommerceSubscriptionEntry::getCompanyId),
			new FinderColumn<>(
				"commerceSubscriptionEntry.", "userId", FinderColumn.Type.LONG,
				"=", true, true, CommerceSubscriptionEntry::getUserId));

		_finderPathWithPaginationFindByG_C_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId", "userId"}, true);

		_finderPathWithoutPaginationFindByG_C_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "companyId", "userId"}, true);

		_finderPathCountByG_C_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "companyId", "userId"}, false);

		_collectionPersistenceFinderByG_C_U = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_C_U,
			_finderPathWithoutPaginationFindByG_C_U, _finderPathCountByG_C_U,
			_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE,
			_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE,
			CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"commerceSubscriptionEntry.", "groupId", FinderColumn.Type.LONG,
				"=", true, false, CommerceSubscriptionEntry::getGroupId),
			new FinderColumn<>(
				"commerceSubscriptionEntry.", "companyId",
				FinderColumn.Type.LONG, "=", true, false,
				CommerceSubscriptionEntry::getCompanyId),
			new FinderColumn<>(
				"commerceSubscriptionEntry.", "userId", FinderColumn.Type.LONG,
				"=", true, true, CommerceSubscriptionEntry::getUserId));

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"CPInstanceUuid", "CProductId", "commerceOrderItemId"
			},
			true);

		_uniquePersistenceFinderByC_C_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_C_C,
			_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE,
			new FinderColumn<>(
				"commerceSubscriptionEntry.", "CPInstanceUuid",
				FinderColumn.Type.STRING, "=", true, false,
				CommerceSubscriptionEntry::getCPInstanceUuid),
			new FinderColumn<>(
				"commerceSubscriptionEntry.", "CProductId",
				FinderColumn.Type.LONG, "=", true, false,
				CommerceSubscriptionEntry::getCProductId),
			new FinderColumn<>(
				"commerceSubscriptionEntry.", "commerceOrderItemId",
				FinderColumn.Type.LONG, "=", true, true,
				CommerceSubscriptionEntry::getCommerceOrderItemId));

		CommerceSubscriptionEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceSubscriptionEntryUtil.setPersistence(null);

		entityCache.removeCache(CommerceSubscriptionEntryImpl.class.getName());
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
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCESUBSCRIPTIONENTRY =
		"SELECT commerceSubscriptionEntry FROM CommerceSubscriptionEntry commerceSubscriptionEntry";

	private static final String _SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE =
		"SELECT commerceSubscriptionEntry FROM CommerceSubscriptionEntry commerceSubscriptionEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCESUBSCRIPTIONENTRY =
		"SELECT COUNT(commerceSubscriptionEntry) FROM CommerceSubscriptionEntry commerceSubscriptionEntry";

	private static final String _SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE =
		"SELECT COUNT(commerceSubscriptionEntry) FROM CommerceSubscriptionEntry commerceSubscriptionEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceSubscriptionEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceSubscriptionEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceSubscriptionEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceSubscriptionEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "deliverySubscriptionTypeSettings"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-2079143861