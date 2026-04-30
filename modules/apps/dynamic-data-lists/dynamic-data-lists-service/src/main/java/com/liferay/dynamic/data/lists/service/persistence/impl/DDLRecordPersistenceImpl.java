/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.service.persistence.impl;

import com.liferay.dynamic.data.lists.exception.NoSuchRecordException;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordTable;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordImpl;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordPersistence;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordUtil;
import com.liferay.dynamic.data.lists.service.persistence.impl.constants.DDLPersistenceConstants;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ddl record service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DDLRecordPersistence.class)
public class DDLRecordPersistenceImpl
	extends BasePersistenceImpl<DDLRecord, NoSuchRecordException>
	implements DDLRecordPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DDLRecordUtil</code> to access the ddl record persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DDLRecordImpl.class.getName();

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
	private CollectionPersistenceFinder<DDLRecord>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the ddl records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByUuid_First(
			String uuid, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByUuid_First(uuid, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		throw new NoSuchRecordException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByUuid_First(
		String uuid, OrderByComparator<DDLRecord> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the ddl records where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of ddl records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<DDLRecord> _uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRecordException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByUUID_G(String uuid, long groupId)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByUUID_G(uuid, groupId);

		if (ddlRecord == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchRecordException(message);
		}

		return ddlRecord;
	}

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the ddl record where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddl record that was removed
	 */
	@Override
	public DDLRecord removeByUUID_G(String uuid, long groupId)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = findByUUID_G(uuid, groupId);

		return remove(ddlRecord);
	}

	/**
	 * Returns the number of ddl records where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<DDLRecord>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		throw new NoSuchRecordException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DDLRecord> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the ddl records where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<DDLRecord>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the ddl records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				finderCache, new Object[] {companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByCompanyId_First(
			long companyId, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		throw new NoSuchRecordException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByCompanyId_First(
		long companyId, OrderByComparator<DDLRecord> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the ddl records where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of ddl records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				finderCache, new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByRecordSetId;
	private FinderPath _finderPathWithoutPaginationFindByRecordSetId;
	private FinderPath _finderPathCountByRecordSetId;
	private CollectionPersistenceFinder<DDLRecord>
		_collectionPersistenceFinderByRecordSetId;

	/**
	 * Returns all the ddl records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(long recordSetId) {
		return findByRecordSetId(
			recordSetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end) {

		return findByRecordSetId(recordSetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByRecordSetId(
			recordSetId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByRecordSetId.find(
				finderCache, new Object[] {recordSetId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByRecordSetId_First(
			long recordSetId, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByRecordSetId_First(
			recordSetId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		throw new NoSuchRecordException(
			_collectionPersistenceFinderByRecordSetId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {recordSetId}));
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByRecordSetId_First(
		long recordSetId, OrderByComparator<DDLRecord> orderByComparator) {

		return _collectionPersistenceFinderByRecordSetId.fetchFirst(
			finderCache, new Object[] {recordSetId}, orderByComparator);
	}

	/**
	 * Removes all the ddl records where recordSetId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 */
	@Override
	public void removeByRecordSetId(long recordSetId) {
		_collectionPersistenceFinderByRecordSetId.remove(
			finderCache, new Object[] {recordSetId});
	}

	/**
	 * Returns the number of ddl records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByRecordSetId(long recordSetId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByRecordSetId.count(
				finderCache, new Object[] {recordSetId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByR_U;
	private FinderPath _finderPathWithoutPaginationFindByR_U;
	private FinderPath _finderPathCountByR_U;
	private CollectionPersistenceFinder<DDLRecord>
		_collectionPersistenceFinderByR_U;

	/**
	 * Returns all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_U(long recordSetId, long userId) {
		return findByR_U(
			recordSetId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end) {

		return findByR_U(recordSetId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByR_U(
			recordSetId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByR_U.find(
				finderCache, new Object[] {recordSetId, userId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByR_U_First(
			long recordSetId, long userId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByR_U_First(
			recordSetId, userId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		throw new NoSuchRecordException(
			_collectionPersistenceFinderByR_U.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {recordSetId, userId}));
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByR_U_First(
		long recordSetId, long userId,
		OrderByComparator<DDLRecord> orderByComparator) {

		return _collectionPersistenceFinderByR_U.fetchFirst(
			finderCache, new Object[] {recordSetId, userId}, orderByComparator);
	}

	/**
	 * Removes all the ddl records where recordSetId = &#63; and userId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByR_U(long recordSetId, long userId) {
		_collectionPersistenceFinderByR_U.remove(
			finderCache, new Object[] {recordSetId, userId});
	}

	/**
	 * Returns the number of ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByR_U(long recordSetId, long userId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByR_U.count(
				finderCache, new Object[] {recordSetId, userId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByR_R;
	private FinderPath _finderPathWithoutPaginationFindByR_R;
	private FinderPath _finderPathCountByR_R;
	private CollectionPersistenceFinder<DDLRecord>
		_collectionPersistenceFinderByR_R;

	/**
	 * Returns all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion) {

		return findByR_R(
			recordSetId, recordSetVersion, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end) {

		return findByR_R(recordSetId, recordSetVersion, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByR_R(
			recordSetId, recordSetVersion, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByR_R.find(
				finderCache, new Object[] {recordSetId, recordSetVersion},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByR_R_First(
			long recordSetId, String recordSetVersion,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByR_R_First(
			recordSetId, recordSetVersion, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		throw new NoSuchRecordException(
			_collectionPersistenceFinderByR_R.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {recordSetId, recordSetVersion}));
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByR_R_First(
		long recordSetId, String recordSetVersion,
		OrderByComparator<DDLRecord> orderByComparator) {

		return _collectionPersistenceFinderByR_R.fetchFirst(
			finderCache, new Object[] {recordSetId, recordSetVersion},
			orderByComparator);
	}

	/**
	 * Removes all the ddl records where recordSetId = &#63; and recordSetVersion = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 */
	@Override
	public void removeByR_R(long recordSetId, String recordSetVersion) {
		_collectionPersistenceFinderByR_R.remove(
			finderCache, new Object[] {recordSetId, recordSetVersion});
	}

	/**
	 * Returns the number of ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByR_R(long recordSetId, String recordSetVersion) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByR_R.count(
				finderCache, new Object[] {recordSetId, recordSetVersion});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;
	private CollectionPersistenceFinder<DDLRecord>
		_collectionPersistenceFinderByC_C;

	/**
	 * Returns all the ddl records where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByC_C(String className, long classPK) {
		return findByC_C(
			className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByC_C(
		String className, long classPK, int start, int end) {

		return findByC_C(className, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByC_C(
		String className, long classPK, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByC_C(
			className, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByC_C(
		String className, long classPK, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByC_C.find(
				finderCache, new Object[] {className, classPK}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first ddl record in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByC_C_First(
			String className, long classPK,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByC_C_First(
			className, classPK, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		throw new NoSuchRecordException(
			_collectionPersistenceFinderByC_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {className, classPK}));
	}

	/**
	 * Returns the first ddl record in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByC_C_First(
		String className, long classPK,
		OrderByComparator<DDLRecord> orderByComparator) {

		return _collectionPersistenceFinderByC_C.fetchFirst(
			finderCache, new Object[] {className, classPK}, orderByComparator);
	}

	/**
	 * Removes all the ddl records where className = &#63; and classPK = &#63; from the database.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(String className, long classPK) {
		_collectionPersistenceFinderByC_C.remove(
			finderCache, new Object[] {className, classPK});
	}

	/**
	 * Returns the number of ddl records where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByC_C(String className, long classPK) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			return _collectionPersistenceFinderByC_C.count(
				finderCache, new Object[] {className, classPK});
		}
	}

	public DDLRecordPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DDLRecord.class);

		setModelImplClass(DDLRecordImpl.class);
		setModelPKClass(long.class);

		setTable(DDLRecordTable.INSTANCE);
	}

	/**
	 * Caches the ddl record in the entity cache if it is enabled.
	 *
	 * @param ddlRecord the ddl record
	 */
	@Override
	public void cacheResult(DDLRecord ddlRecord) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ddlRecord.getCtCollectionId())) {

			entityCache.putResult(
				DDLRecordImpl.class, ddlRecord.getPrimaryKey(), ddlRecord);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {ddlRecord.getUuid(), ddlRecord.getGroupId()},
				ddlRecord);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ddl records in the entity cache if it is enabled.
	 *
	 * @param ddlRecords the ddl records
	 */
	@Override
	public void cacheResult(List<DDLRecord> ddlRecords) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ddlRecords.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DDLRecord ddlRecord : ddlRecords) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ddlRecord.getCtCollectionId())) {

				if (entityCache.getResult(
						DDLRecordImpl.class, ddlRecord.getPrimaryKey()) ==
							null) {

					cacheResult(ddlRecord);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		DDLRecordModelImpl ddlRecordModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ddlRecordModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				ddlRecordModelImpl.getUuid(), ddlRecordModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, ddlRecordModelImpl);
		}
	}

	/**
	 * Creates a new ddl record with the primary key. Does not add the ddl record to the database.
	 *
	 * @param recordId the primary key for the new ddl record
	 * @return the new ddl record
	 */
	@Override
	public DDLRecord create(long recordId) {
		DDLRecord ddlRecord = new DDLRecordImpl();

		ddlRecord.setNew(true);
		ddlRecord.setPrimaryKey(recordId);

		String uuid = PortalUUIDUtil.generate();

		ddlRecord.setUuid(uuid);

		ddlRecord.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ddlRecord;
	}

	/**
	 * Removes the ddl record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record that was removed
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord remove(long recordId) throws NoSuchRecordException {
		return remove((Serializable)recordId);
	}

	@Override
	protected DDLRecord removeImpl(DDLRecord ddlRecord) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddlRecord)) {
				ddlRecord = (DDLRecord)session.get(
					DDLRecordImpl.class, ddlRecord.getPrimaryKeyObj());
			}

			if ((ddlRecord != null) &&
				ctPersistenceHelper.isRemove(ddlRecord)) {

				session.delete(ddlRecord);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddlRecord != null) {
			clearCache(ddlRecord);
		}

		return ddlRecord;
	}

	@Override
	public DDLRecord updateImpl(DDLRecord ddlRecord) {
		boolean isNew = ddlRecord.isNew();

		if (!(ddlRecord instanceof DDLRecordModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ddlRecord.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ddlRecord);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddlRecord proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDLRecord implementation " +
					ddlRecord.getClass());
		}

		DDLRecordModelImpl ddlRecordModelImpl = (DDLRecordModelImpl)ddlRecord;

		if (Validator.isNull(ddlRecord.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddlRecord.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (ddlRecord.getCreateDate() == null)) {
			if (serviceContext == null) {
				ddlRecord.setCreateDate(date);
			}
			else {
				ddlRecord.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!ddlRecordModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ddlRecord.setModifiedDate(date);
			}
			else {
				ddlRecord.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ddlRecord)) {
				if (!isNew) {
					session.evict(
						DDLRecordImpl.class, ddlRecord.getPrimaryKeyObj());
				}

				session.save(ddlRecord);
			}
			else {
				ddlRecord = (DDLRecord)session.merge(ddlRecord);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DDLRecordImpl.class, ddlRecordModelImpl, false, true);

		cacheUniqueFindersCache(ddlRecordModelImpl);

		if (isNew) {
			ddlRecord.setNew(false);
		}

		ddlRecord.resetOriginalValues();

		return ddlRecord;
	}

	/**
	 * Returns the ddl record with the primary key or throws a <code>NoSuchRecordException</code> if it could not be found.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord findByPrimaryKey(long recordId)
		throws NoSuchRecordException {

		return findByPrimaryKey((Serializable)recordId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the ddl record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record, or <code>null</code> if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord fetchByPrimaryKey(long recordId) {
		return fetchByPrimaryKey((Serializable)recordId);
	}

	/**
	 * Returns all the ddl records.
	 *
	 * @return the ddl records
	 */
	@Override
	public List<DDLRecord> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of ddl records
	 */
	@Override
	public List<DDLRecord> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddl records
	 */
	@Override
	public List<DDLRecord> findAll(
		int start, int end, OrderByComparator<DDLRecord> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddl records
	 */
	@Override
	public List<DDLRecord> findAll(
		int start, int end, OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

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

			List<DDLRecord> list = null;

			if (useFinderCache) {
				list = (List<DDLRecord>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_DDLRECORD);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_DDLRECORD;

					sql = sql.concat(DDLRecordModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<DDLRecord>)QueryUtil.list(
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
	 * Removes all the ddl records from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDLRecord ddlRecord : findAll()) {
			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of ddl records.
	 *
	 * @return the number of ddl records
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DDLRecord.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_DDLRECORD);

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
		return "recordId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDLRECORD;
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
		return DDLRecordModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DDLRecord";
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
		ctMergeColumnNames.add("versionUserId");
		ctMergeColumnNames.add("versionUserName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("DDMStorageId");
		ctMergeColumnNames.add("recordSetId");
		ctMergeColumnNames.add("recordSetVersion");
		ctMergeColumnNames.add("className");
		ctStrictColumnNames.add("classPK");
		ctMergeColumnNames.add("version");
		ctMergeColumnNames.add("displayIndex");
		ctMergeColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("recordId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the ddl record persistence.
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
			_SQL_SELECT_DDLRECORD_WHERE, _SQL_COUNT_DDLRECORD_WHERE,
			DDLRecordModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"ddlRecord.", "uuid", FinderColumn.Type.STRING, "=", true, true,
				DDLRecord::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_DDLRECORD_WHERE,
			new FinderColumn<>(
				"ddlRecord.", "uuid", FinderColumn.Type.STRING, "=", true,
				false, DDLRecord::getUuid),
			new FinderColumn<>(
				"ddlRecord.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, DDLRecord::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_DDLRECORD_WHERE,
				_SQL_COUNT_DDLRECORD_WHERE, DDLRecordModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ddlRecord.", "uuid", FinderColumn.Type.STRING, "=", true,
					false, DDLRecord::getUuid),
				new FinderColumn<>(
					"ddlRecord.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, DDLRecord::getCompanyId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_DDLRECORD_WHERE,
				_SQL_COUNT_DDLRECORD_WHERE, DDLRecordModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ddlRecord.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, DDLRecord::getCompanyId));

		_finderPathWithPaginationFindByRecordSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRecordSetId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"recordSetId"}, true);

		_finderPathWithoutPaginationFindByRecordSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRecordSetId",
			new String[] {Long.class.getName()}, new String[] {"recordSetId"},
			true);

		_finderPathCountByRecordSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRecordSetId",
			new String[] {Long.class.getName()}, new String[] {"recordSetId"},
			false);

		_collectionPersistenceFinderByRecordSetId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByRecordSetId,
				_finderPathWithoutPaginationFindByRecordSetId,
				_finderPathCountByRecordSetId, _SQL_SELECT_DDLRECORD_WHERE,
				_SQL_COUNT_DDLRECORD_WHERE, DDLRecordModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ddlRecord.", "recordSetId", FinderColumn.Type.LONG, "=",
					true, true, DDLRecord::getRecordSetId));

		_finderPathWithPaginationFindByR_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"recordSetId", "userId"}, true);

		_finderPathWithoutPaginationFindByR_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"recordSetId", "userId"}, true);

		_finderPathCountByR_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"recordSetId", "userId"}, false);

		_collectionPersistenceFinderByR_U = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByR_U,
			_finderPathWithoutPaginationFindByR_U, _finderPathCountByR_U,
			_SQL_SELECT_DDLRECORD_WHERE, _SQL_COUNT_DDLRECORD_WHERE,
			DDLRecordModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"ddlRecord.", "recordSetId", FinderColumn.Type.LONG, "=", true,
				false, DDLRecord::getRecordSetId),
			new FinderColumn<>(
				"ddlRecord.", "userId", FinderColumn.Type.LONG, "=", true, true,
				DDLRecord::getUserId));

		_finderPathWithPaginationFindByR_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"recordSetId", "recordSetVersion"}, true);

		_finderPathWithoutPaginationFindByR_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_R",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"recordSetId", "recordSetVersion"}, true);

		_finderPathCountByR_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_R",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"recordSetId", "recordSetVersion"}, false);

		_collectionPersistenceFinderByR_R = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByR_R,
			_finderPathWithoutPaginationFindByR_R, _finderPathCountByR_R,
			_SQL_SELECT_DDLRECORD_WHERE, _SQL_COUNT_DDLRECORD_WHERE,
			DDLRecordModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"ddlRecord.", "recordSetId", FinderColumn.Type.LONG, "=", true,
				false, DDLRecord::getRecordSetId),
			new FinderColumn<>(
				"ddlRecord.", "recordSetVersion", FinderColumn.Type.STRING, "=",
				true, true, DDLRecord::getRecordSetVersion));

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"className", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"className", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"className", "classPK"}, false);

		_collectionPersistenceFinderByC_C = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_C,
			_finderPathWithoutPaginationFindByC_C, _finderPathCountByC_C,
			_SQL_SELECT_DDLRECORD_WHERE, _SQL_COUNT_DDLRECORD_WHERE,
			DDLRecordModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"ddlRecord.", "className", FinderColumn.Type.STRING, "=", true,
				false, DDLRecord::getClassName),
			new FinderColumn<>(
				"ddlRecord.", "classPK", FinderColumn.Type.LONG, "=", true,
				true, DDLRecord::getClassPK));

		DDLRecordUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		DDLRecordUtil.setPersistence(null);

		entityCache.removeCache(DDLRecordImpl.class.getName());
	}

	@Override
	@Reference(
		target = DDLPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DDLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DDLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DDLRECORD =
		"SELECT ddlRecord FROM DDLRecord ddlRecord";

	private static final String _SQL_SELECT_DDLRECORD_WHERE =
		"SELECT ddlRecord FROM DDLRecord ddlRecord WHERE ";

	private static final String _SQL_COUNT_DDLRECORD =
		"SELECT COUNT(ddlRecord) FROM DDLRecord ddlRecord";

	private static final String _SQL_COUNT_DDLRECORD_WHERE =
		"SELECT COUNT(ddlRecord) FROM DDLRecord ddlRecord WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ddlRecord.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DDLRecord exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:92579974