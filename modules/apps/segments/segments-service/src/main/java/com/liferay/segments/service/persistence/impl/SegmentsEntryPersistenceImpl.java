/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.ArrayableFinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FilterCollectionPersistenceFinder;
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
import com.liferay.segments.exception.DuplicateSegmentsEntryExternalReferenceCodeException;
import com.liferay.segments.exception.NoSuchEntryException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryTable;
import com.liferay.segments.model.impl.SegmentsEntryImpl;
import com.liferay.segments.model.impl.SegmentsEntryModelImpl;
import com.liferay.segments.service.persistence.SegmentsEntryPersistence;
import com.liferay.segments.service.persistence.SegmentsEntryUtil;
import com.liferay.segments.service.persistence.impl.constants.SegmentsPersistenceConstants;

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
 * The persistence implementation for the segments entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @generated
 */
@Component(service = SegmentsEntryPersistence.class)
public class SegmentsEntryPersistenceImpl
	extends BasePersistenceImpl<SegmentsEntry, NoSuchEntryException>
	implements SegmentsEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SegmentsEntryUtil</code> to access the segments entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SegmentsEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<SegmentsEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the segments entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first segments entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByUuid_First(
			String uuid, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first segments entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByUuid_First(
		String uuid, OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the segments entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of segments entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<SegmentsEntry>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the segments entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByUUID_G(uuid, groupId);

		if (segmentsEntry == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryException(message);
		}

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the segments entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return _uniquePersistenceFinderByUUID_G.fetch(
			finderCache, new Object[] {uuid, groupId}, useFinderCache);
	}

	/**
	 * Removes the segments entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the segments entry that was removed
	 */
	@Override
	public SegmentsEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = findByUUID_G(uuid, groupId);

		return remove(segmentsEntry);
	}

	/**
	 * Returns the number of segments entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<SegmentsEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the segments entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first segments entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first segments entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the segments entries where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of segments entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private FinderPath _finderPathWithPaginationFindBySegmentsEntryId;
	private FinderPath _finderPathWithoutPaginationFindBySegmentsEntryId;
	private FinderPath _finderPathCountBySegmentsEntryId;
	private CollectionPersistenceFinder<SegmentsEntry>
		_collectionPersistenceFinderBySegmentsEntryId;

	/**
	 * Returns all the segments entries where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySegmentsEntryId(long segmentsEntryId) {
		return findBySegmentsEntryId(
			segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end) {

		return findBySegmentsEntryId(segmentsEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderBySegmentsEntryId.find(
			finderCache, new Object[] {new long[] {segmentsEntryId}}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first segments entry in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findBySegmentsEntryId_First(
			long segmentsEntryId,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("segmentsEntryId=");
		sb.append(segmentsEntryId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderBySegmentsEntryId.fetchFirst(
			finderCache, new Object[] {new long[] {segmentsEntryId}},
			orderByComparator);
	}

	/**
	 * Returns all the segments entries where segmentsEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryIds the segments entry IDs
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySegmentsEntryId(long[] segmentsEntryIds) {
		return findBySegmentsEntryId(
			segmentsEntryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where segmentsEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryIds the segments entry IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySegmentsEntryId(
		long[] segmentsEntryIds, int start, int end) {

		return findBySegmentsEntryId(segmentsEntryIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where segmentsEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryIds the segments entry IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySegmentsEntryId(
		long[] segmentsEntryIds, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findBySegmentsEntryId(
			segmentsEntryIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where segmentsEntryId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryIds the segments entry IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySegmentsEntryId(
		long[] segmentsEntryIds, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderBySegmentsEntryId.find(
			finderCache,
			new Object[] {ArrayUtil.sortedUnique(segmentsEntryIds)}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the segments entries where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	@Override
	public void removeBySegmentsEntryId(long segmentsEntryId) {
		_collectionPersistenceFinderBySegmentsEntryId.remove(
			finderCache, new Object[] {new long[] {segmentsEntryId}});
	}

	/**
	 * Returns the number of segments entries where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching segments entries
	 */
	@Override
	public int countBySegmentsEntryId(long segmentsEntryId) {
		return _collectionPersistenceFinderBySegmentsEntryId.count(
			finderCache, new Object[] {new long[] {segmentsEntryId}});
	}

	/**
	 * Returns the number of segments entries where segmentsEntryId = any &#63;.
	 *
	 * @param segmentsEntryIds the segments entry IDs
	 * @return the number of matching segments entries
	 */
	@Override
	public int countBySegmentsEntryId(long[] segmentsEntryIds) {
		return _collectionPersistenceFinderBySegmentsEntryId.count(
			finderCache,
			new Object[] {ArrayUtil.sortedUnique(segmentsEntryIds)});
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private FilterCollectionPersistenceFinder<SegmentsEntry>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the segments entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByGroupId.find(
			finderCache, new Object[] {new long[] {groupId}}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByGroupId_First(
			long groupId, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByGroupId_First(
		long groupId, OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {new long[] {groupId}},
			orderByComparator);
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.filterFind(
			finderCache, new Object[] {new long[] {groupId}}, start, end,
			orderByComparator, groupId);
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(long[] groupIds) {
		return filterFindByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(
		long[] groupIds, int start, int end) {

		return filterFindByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		groupIds = ArrayUtil.sortedUnique(groupIds);

		return _collectionPersistenceFinderByGroupId.filterFind(
			finderCache, new Object[] {groupIds}, start, end, orderByComparator,
			groupIds);
	}

	/**
	 * Returns all the segments entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(long[] groupIds) {
		return findByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(
		long[] groupIds, int start, int end) {

		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByGroupId(groupIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByGroupId.find(
			finderCache, new Object[] {ArrayUtil.sortedUnique(groupIds)}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the segments entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {new long[] {groupId}});
	}

	/**
	 * Returns the number of segments entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		return _collectionPersistenceFinderByGroupId.count(
			finderCache, new Object[] {new long[] {groupId}});
	}

	/**
	 * Returns the number of segments entries where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByGroupId(long[] groupIds) {
		return _collectionPersistenceFinderByGroupId.count(
			finderCache, new Object[] {ArrayUtil.sortedUnique(groupIds)});
	}

	/**
	 * Returns the number of segments entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		return _collectionPersistenceFinderByGroupId.filterCount(
			finderCache, new Object[] {new long[] {groupId}}, groupId);
	}

	/**
	 * Returns the number of segments entries that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long[] groupIds) {
		groupIds = ArrayUtil.sortedUnique(groupIds);

		return _collectionPersistenceFinderByGroupId.filterCount(
			finderCache, new Object[] {groupIds}, groupIds);
	}

	private FinderPath _finderPathWithPaginationFindByActive;
	private FinderPath _finderPathWithoutPaginationFindByActive;
	private FinderPath _finderPathCountByActive;
	private CollectionPersistenceFinder<SegmentsEntry>
		_collectionPersistenceFinderByActive;

	/**
	 * Returns all the segments entries where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByActive(boolean active) {
		return findByActive(active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByActive(
		boolean active, int start, int end) {

		return findByActive(active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByActive(
		boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByActive(active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByActive(
		boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByActive.find(
			finderCache, new Object[] {active}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first segments entry in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByActive_First(
			boolean active, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByActive_First(
			active, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderByActive.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {active}));
	}

	/**
	 * Returns the first segments entry in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByActive_First(
		boolean active, OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderByActive.fetchFirst(
			finderCache, new Object[] {active}, orderByComparator);
	}

	/**
	 * Removes all the segments entries where active = &#63; from the database.
	 *
	 * @param active the active
	 */
	@Override
	public void removeByActive(boolean active) {
		_collectionPersistenceFinderByActive.remove(
			finderCache, new Object[] {active});
	}

	/**
	 * Returns the number of segments entries where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByActive(boolean active) {
		return _collectionPersistenceFinderByActive.count(
			finderCache, new Object[] {active});
	}

	private FinderPath _finderPathWithPaginationFindBySource;
	private FinderPath _finderPathWithoutPaginationFindBySource;
	private FinderPath _finderPathCountBySource;
	private CollectionPersistenceFinder<SegmentsEntry>
		_collectionPersistenceFinderBySource;

	/**
	 * Returns all the segments entries where source = &#63;.
	 *
	 * @param source the source
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(String source) {
		return findBySource(source, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(String source, int start, int end) {
		return findBySource(source, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(
		String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findBySource(source, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(
		String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderBySource.find(
			finderCache, new Object[] {source}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first segments entry in the ordered set where source = &#63;.
	 *
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findBySource_First(
			String source, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchBySource_First(
			source, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		throw new NoSuchEntryException(
			_collectionPersistenceFinderBySource.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {source}));
	}

	/**
	 * Returns the first segments entry in the ordered set where source = &#63;.
	 *
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchBySource_First(
		String source, OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderBySource.fetchFirst(
			finderCache, new Object[] {source}, orderByComparator);
	}

	/**
	 * Removes all the segments entries where source = &#63; from the database.
	 *
	 * @param source the source
	 */
	@Override
	public void removeBySource(String source) {
		_collectionPersistenceFinderBySource.remove(
			finderCache, new Object[] {source});
	}

	/**
	 * Returns the number of segments entries where source = &#63;.
	 *
	 * @param source the source
	 * @return the number of matching segments entries
	 */
	@Override
	public int countBySource(String source) {
		return _collectionPersistenceFinderBySource.count(
			finderCache, new Object[] {source});
	}

	private FinderPath _finderPathFetchByG_S;
	private UniquePersistenceFinder<SegmentsEntry>
		_uniquePersistenceFinderByG_S;

	/**
	 * Returns the segments entry where groupId = &#63; and segmentsEntryKey = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryKey the segments entry key
	 * @return the matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_S(long groupId, String segmentsEntryKey)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByG_S(groupId, segmentsEntryKey);

		if (segmentsEntry == null) {
			String message =
				_uniquePersistenceFinderByG_S.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, segmentsEntryKey});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryException(message);
		}

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry where groupId = &#63; and segmentsEntryKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryKey the segments entry key
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_S(long groupId, String segmentsEntryKey) {
		return fetchByG_S(groupId, segmentsEntryKey, true);
	}

	/**
	 * Returns the segments entry where groupId = &#63; and segmentsEntryKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryKey the segments entry key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_S(
		long groupId, String segmentsEntryKey, boolean useFinderCache) {

		return _uniquePersistenceFinderByG_S.fetch(
			finderCache, new Object[] {groupId, segmentsEntryKey},
			useFinderCache);
	}

	/**
	 * Removes the segments entry where groupId = &#63; and segmentsEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryKey the segments entry key
	 * @return the segments entry that was removed
	 */
	@Override
	public SegmentsEntry removeByG_S(long groupId, String segmentsEntryKey)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = findByG_S(groupId, segmentsEntryKey);

		return remove(segmentsEntry);
	}

	/**
	 * Returns the number of segments entries where groupId = &#63; and segmentsEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryKey the segments entry key
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_S(long groupId, String segmentsEntryKey) {
		return _uniquePersistenceFinderByG_S.count(
			finderCache, new Object[] {groupId, segmentsEntryKey});
	}

	private FinderPath _finderPathWithPaginationFindByG_A;
	private FinderPath _finderPathWithoutPaginationFindByG_A;
	private FinderPath _finderPathCountByG_A;
	private FilterCollectionPersistenceFinder<SegmentsEntry>
		_collectionPersistenceFinderByG_A;

	/**
	 * Returns all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(long groupId, boolean active) {
		return findByG_A(
			groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long groupId, boolean active, int start, int end) {

		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_A.find(
			finderCache, new Object[] {new long[] {groupId}, active}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_First(
			long groupId, boolean active,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByG_A_First(
			groupId, active, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_First(
		long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_A.fetchFirst(
			finderCache, new Object[] {new long[] {groupId}, active},
			orderByComparator);
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(long groupId, boolean active) {
		return filterFindByG_A(
			groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(
		long groupId, boolean active, int start, int end) {

		return filterFindByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_A.filterFind(
			finderCache, new Object[] {new long[] {groupId}, active}, start,
			end, orderByComparator, groupId);
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(
		long[] groupIds, boolean active) {

		return filterFindByG_A(
			groupIds, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(
		long[] groupIds, boolean active, int start, int end) {

		return filterFindByG_A(groupIds, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(
		long[] groupIds, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		groupIds = ArrayUtil.sortedUnique(groupIds);

		return _collectionPersistenceFinderByG_A.filterFind(
			finderCache, new Object[] {groupIds, active}, start, end,
			orderByComparator, groupIds);
	}

	/**
	 * Returns all the segments entries where groupId = any &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(long[] groupIds, boolean active) {
		return findByG_A(
			groupIds, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = any &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long[] groupIds, boolean active, int start, int end) {

		return findByG_A(groupIds, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = any &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long[] groupIds, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_A(groupIds, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long[] groupIds, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_A.find(
			finderCache,
			new Object[] {ArrayUtil.sortedUnique(groupIds), active}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the segments entries where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 */
	@Override
	public void removeByG_A(long groupId, boolean active) {
		_collectionPersistenceFinderByG_A.remove(
			finderCache, new Object[] {new long[] {groupId}, active});
	}

	/**
	 * Returns the number of segments entries where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A(long groupId, boolean active) {
		return _collectionPersistenceFinderByG_A.count(
			finderCache, new Object[] {new long[] {groupId}, active});
	}

	/**
	 * Returns the number of segments entries where groupId = any &#63; and active = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A(long[] groupIds, boolean active) {
		return _collectionPersistenceFinderByG_A.count(
			finderCache,
			new Object[] {ArrayUtil.sortedUnique(groupIds), active});
	}

	/**
	 * Returns the number of segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long groupId, boolean active) {
		return _collectionPersistenceFinderByG_A.filterCount(
			finderCache, new Object[] {new long[] {groupId}, active}, groupId);
	}

	/**
	 * Returns the number of segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long[] groupIds, boolean active) {
		groupIds = ArrayUtil.sortedUnique(groupIds);

		return _collectionPersistenceFinderByG_A.filterCount(
			finderCache, new Object[] {groupIds, active}, groupIds);
	}

	private FinderPath _finderPathWithPaginationFindByG_SRC;
	private FinderPath _finderPathWithoutPaginationFindByG_SRC;
	private FinderPath _finderPathCountByG_SRC;
	private FilterCollectionPersistenceFinder<SegmentsEntry>
		_collectionPersistenceFinderByG_SRC;

	/**
	 * Returns all the segments entries where groupId = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param source the source
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_SRC(long groupId, String source) {
		return findByG_SRC(
			groupId, source, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_SRC(
		long groupId, String source, int start, int end) {

		return findByG_SRC(groupId, source, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_SRC(
		long groupId, String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_SRC(
			groupId, source, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_SRC(
		long groupId, String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_SRC.find(
			finderCache, new Object[] {new long[] {groupId}, source}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_SRC_First(
			long groupId, String source,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByG_SRC_First(
			groupId, source, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", source=");
		sb.append(source);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_SRC_First(
		long groupId, String source,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_SRC.fetchFirst(
			finderCache, new Object[] {new long[] {groupId}, source},
			orderByComparator);
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param source the source
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_SRC(long groupId, String source) {
		return filterFindByG_SRC(
			groupId, source, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_SRC(
		long groupId, String source, int start, int end) {

		return filterFindByG_SRC(groupId, source, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_SRC(
		long groupId, String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_SRC.filterFind(
			finderCache, new Object[] {new long[] {groupId}, source}, start,
			end, orderByComparator, groupId);
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = any &#63; and source = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param source the source
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_SRC(
		long[] groupIds, String source) {

		return filterFindByG_SRC(
			groupIds, source, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = any &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_SRC(
		long[] groupIds, String source, int start, int end) {

		return filterFindByG_SRC(groupIds, source, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_SRC(
		long[] groupIds, String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		groupIds = ArrayUtil.sortedUnique(groupIds);

		return _collectionPersistenceFinderByG_SRC.filterFind(
			finderCache, new Object[] {groupIds, source}, start, end,
			orderByComparator, groupIds);
	}

	/**
	 * Returns all the segments entries where groupId = any &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param source the source
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_SRC(long[] groupIds, String source) {
		return findByG_SRC(
			groupIds, source, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = any &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_SRC(
		long[] groupIds, String source, int start, int end) {

		return findByG_SRC(groupIds, source, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = any &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_SRC(
		long[] groupIds, String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_SRC(
			groupIds, source, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and source = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_SRC(
		long[] groupIds, String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_SRC.find(
			finderCache,
			new Object[] {ArrayUtil.sortedUnique(groupIds), source}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the segments entries where groupId = &#63; and source = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param source the source
	 */
	@Override
	public void removeByG_SRC(long groupId, String source) {
		_collectionPersistenceFinderByG_SRC.remove(
			finderCache, new Object[] {new long[] {groupId}, source});
	}

	/**
	 * Returns the number of segments entries where groupId = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param source the source
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_SRC(long groupId, String source) {
		return _collectionPersistenceFinderByG_SRC.count(
			finderCache, new Object[] {new long[] {groupId}, source});
	}

	/**
	 * Returns the number of segments entries where groupId = any &#63; and source = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param source the source
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_SRC(long[] groupIds, String source) {
		return _collectionPersistenceFinderByG_SRC.count(
			finderCache,
			new Object[] {ArrayUtil.sortedUnique(groupIds), source});
	}

	/**
	 * Returns the number of segments entries that the user has permission to view where groupId = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param source the source
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_SRC(long groupId, String source) {
		return _collectionPersistenceFinderByG_SRC.filterCount(
			finderCache, new Object[] {new long[] {groupId}, source}, groupId);
	}

	/**
	 * Returns the number of segments entries that the user has permission to view where groupId = any &#63; and source = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param source the source
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_SRC(long[] groupIds, String source) {
		groupIds = ArrayUtil.sortedUnique(groupIds);

		return _collectionPersistenceFinderByG_SRC.filterCount(
			finderCache, new Object[] {groupIds, source}, groupIds);
	}

	private FinderPath _finderPathWithPaginationFindByG_A_SRC;
	private FinderPath _finderPathWithoutPaginationFindByG_A_SRC;
	private FinderPath _finderPathCountByG_A_SRC;
	private FilterCollectionPersistenceFinder<SegmentsEntry>
		_collectionPersistenceFinderByG_A_SRC;

	/**
	 * Returns all the segments entries where groupId = &#63; and active = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_SRC(
		long groupId, boolean active, String source) {

		return findByG_A_SRC(
			groupId, active, source, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = &#63; and active = &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_SRC(
		long groupId, boolean active, String source, int start, int end) {

		return findByG_A_SRC(groupId, active, source, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_SRC(
		long groupId, boolean active, String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_A_SRC(
			groupId, active, source, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_SRC(
		long groupId, boolean active, String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_A_SRC.find(
			finderCache,
			new Object[] {new long[] {groupId}, active, new String[] {source}},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_SRC_First(
			long groupId, boolean active, String source,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByG_A_SRC_First(
			groupId, active, source, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", source=");
		sb.append(source);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_SRC_First(
		long groupId, boolean active, String source,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_A_SRC.fetchFirst(
			finderCache,
			new Object[] {new long[] {groupId}, active, new String[] {source}},
			orderByComparator);
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_SRC(
		long groupId, boolean active, String source) {

		return filterFindByG_A_SRC(
			groupId, active, source, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_SRC(
		long groupId, boolean active, String source, int start, int end) {

		return filterFindByG_A_SRC(groupId, active, source, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and active = &#63; and source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_SRC(
		long groupId, boolean active, String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_A_SRC.filterFind(
			finderCache,
			new Object[] {new long[] {groupId}, active, new String[] {source}},
			start, end, orderByComparator, groupId);
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and source = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param sources the sources
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_SRC(
		long[] groupIds, boolean active, String[] sources) {

		return filterFindByG_A_SRC(
			groupIds, active, sources, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and source = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param sources the sources
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_SRC(
		long[] groupIds, boolean active, String[] sources, int start, int end) {

		return filterFindByG_A_SRC(groupIds, active, sources, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and source = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param sources the sources
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_SRC(
		long[] groupIds, boolean active, String[] sources, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		groupIds = ArrayUtil.sortedUnique(groupIds);

		return _collectionPersistenceFinderByG_A_SRC.filterFind(
			finderCache,
			new Object[] {groupIds, active, ArrayUtil.sortedUnique(sources)},
			start, end, orderByComparator, groupIds);
	}

	/**
	 * Returns all the segments entries where groupId = any &#63; and active = &#63; and source = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param sources the sources
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_SRC(
		long[] groupIds, boolean active, String[] sources) {

		return findByG_A_SRC(
			groupIds, active, sources, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = any &#63; and active = &#63; and source = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param sources the sources
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_SRC(
		long[] groupIds, boolean active, String[] sources, int start, int end) {

		return findByG_A_SRC(groupIds, active, sources, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = any &#63; and active = &#63; and source = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param sources the sources
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_SRC(
		long[] groupIds, boolean active, String[] sources, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_A_SRC(
			groupIds, active, sources, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and source = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param sources the sources
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_SRC(
		long[] groupIds, boolean active, String[] sources, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_A_SRC.find(
			finderCache,
			new Object[] {
				ArrayUtil.sortedUnique(groupIds), active,
				ArrayUtil.sortedUnique(sources)
			},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the segments entries where groupId = &#63; and active = &#63; and source = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 */
	@Override
	public void removeByG_A_SRC(long groupId, boolean active, String source) {
		_collectionPersistenceFinderByG_A_SRC.remove(
			finderCache,
			new Object[] {new long[] {groupId}, active, new String[] {source}});
	}

	/**
	 * Returns the number of segments entries where groupId = &#63; and active = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A_SRC(long groupId, boolean active, String source) {
		return _collectionPersistenceFinderByG_A_SRC.count(
			finderCache,
			new Object[] {new long[] {groupId}, active, new String[] {source}});
	}

	/**
	 * Returns the number of segments entries where groupId = any &#63; and active = &#63; and source = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param sources the sources
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A_SRC(
		long[] groupIds, boolean active, String[] sources) {

		return _collectionPersistenceFinderByG_A_SRC.count(
			finderCache,
			new Object[] {
				ArrayUtil.sortedUnique(groupIds), active,
				ArrayUtil.sortedUnique(sources)
			});
	}

	/**
	 * Returns the number of segments entries that the user has permission to view where groupId = &#63; and active = &#63; and source = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_SRC(
		long groupId, boolean active, String source) {

		return _collectionPersistenceFinderByG_A_SRC.filterCount(
			finderCache,
			new Object[] {new long[] {groupId}, active, new String[] {source}},
			groupId);
	}

	/**
	 * Returns the number of segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and source = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param sources the sources
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_SRC(
		long[] groupIds, boolean active, String[] sources) {

		groupIds = ArrayUtil.sortedUnique(groupIds);

		return _collectionPersistenceFinderByG_A_SRC.filterCount(
			finderCache,
			new Object[] {groupIds, active, ArrayUtil.sortedUnique(sources)},
			groupIds);
	}

	private FinderPath _finderPathFetchByERC_G;
	private UniquePersistenceFinder<SegmentsEntry>
		_uniquePersistenceFinderByERC_G;

	/**
	 * Returns the segments entry where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByERC_G(String externalReferenceCode, long groupId)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByERC_G(
			externalReferenceCode, groupId);

		if (segmentsEntry == null) {
			String message =
				_uniquePersistenceFinderByERC_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryException(message);
		}

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByERC_G(
		String externalReferenceCode, long groupId) {

		return fetchByERC_G(externalReferenceCode, groupId, true);
	}

	/**
	 * Returns the segments entry where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_G.fetch(
			finderCache, new Object[] {externalReferenceCode, groupId},
			useFinderCache);
	}

	/**
	 * Removes the segments entry where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the segments entry that was removed
	 */
	@Override
	public SegmentsEntry removeByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = findByERC_G(
			externalReferenceCode, groupId);

		return remove(segmentsEntry);
	}

	/**
	 * Returns the number of segments entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByERC_G(String externalReferenceCode, long groupId) {
		return _uniquePersistenceFinderByERC_G.count(
			finderCache, new Object[] {externalReferenceCode, groupId});
	}

	public SegmentsEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SegmentsEntry.class);

		setModelImplClass(SegmentsEntryImpl.class);
		setModelPKClass(long.class);

		setTable(SegmentsEntryTable.INSTANCE);
	}

	/**
	 * Creates a new segments entry with the primary key. Does not add the segments entry to the database.
	 *
	 * @param segmentsEntryId the primary key for the new segments entry
	 * @return the new segments entry
	 */
	@Override
	public SegmentsEntry create(long segmentsEntryId) {
		SegmentsEntry segmentsEntry = new SegmentsEntryImpl();

		segmentsEntry.setNew(true);
		segmentsEntry.setPrimaryKey(segmentsEntryId);

		String uuid = PortalUUIDUtil.generate();

		segmentsEntry.setUuid(uuid);

		segmentsEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return segmentsEntry;
	}

	/**
	 * Removes the segments entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryId the primary key of the segments entry
	 * @return the segments entry that was removed
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry remove(long segmentsEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)segmentsEntryId);
	}

	@Override
	protected SegmentsEntry removeImpl(SegmentsEntry segmentsEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(segmentsEntry)) {
				segmentsEntry = (SegmentsEntry)session.get(
					SegmentsEntryImpl.class, segmentsEntry.getPrimaryKeyObj());
			}

			if ((segmentsEntry != null) &&
				ctPersistenceHelper.isRemove(segmentsEntry)) {

				session.delete(segmentsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (segmentsEntry != null) {
			clearCache(segmentsEntry);
		}

		return segmentsEntry;
	}

	@Override
	public SegmentsEntry updateImpl(SegmentsEntry segmentsEntry) {
		boolean isNew = segmentsEntry.isNew();

		if (!(segmentsEntry instanceof SegmentsEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(segmentsEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					segmentsEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in segmentsEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SegmentsEntry implementation " +
					segmentsEntry.getClass());
		}

		SegmentsEntryModelImpl segmentsEntryModelImpl =
			(SegmentsEntryModelImpl)segmentsEntry;

		if (Validator.isNull(segmentsEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			segmentsEntry.setUuid(uuid);
		}

		if (Validator.isNull(segmentsEntry.getExternalReferenceCode())) {
			segmentsEntry.setExternalReferenceCode(segmentsEntry.getUuid());
		}
		else {
			if (!Objects.equals(
					segmentsEntryModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					segmentsEntry.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = segmentsEntry.getCompanyId();

					long groupId = segmentsEntry.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = segmentsEntry.getPrimaryKey();
					}

					try {
						segmentsEntry.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								SegmentsEntry.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								segmentsEntry.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			SegmentsEntry ercSegmentsEntry = fetchByERC_G(
				segmentsEntry.getExternalReferenceCode(),
				segmentsEntry.getGroupId());

			if (isNew) {
				if (ercSegmentsEntry != null) {
					throw new DuplicateSegmentsEntryExternalReferenceCodeException(
						"Duplicate segments entry with external reference code " +
							segmentsEntry.getExternalReferenceCode() +
								" and group " + segmentsEntry.getGroupId());
				}
			}
			else {
				if ((ercSegmentsEntry != null) &&
					(segmentsEntry.getSegmentsEntryId() !=
						ercSegmentsEntry.getSegmentsEntryId())) {

					throw new DuplicateSegmentsEntryExternalReferenceCodeException(
						"Duplicate segments entry with external reference code " +
							segmentsEntry.getExternalReferenceCode() +
								" and group " + segmentsEntry.getGroupId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (segmentsEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				segmentsEntry.setCreateDate(date);
			}
			else {
				segmentsEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!segmentsEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				segmentsEntry.setModifiedDate(date);
			}
			else {
				segmentsEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(segmentsEntry)) {
				if (!isNew) {
					session.evict(
						SegmentsEntryImpl.class,
						segmentsEntry.getPrimaryKeyObj());
				}

				session.save(segmentsEntry);
			}
			else {
				segmentsEntry = (SegmentsEntry)session.merge(segmentsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(segmentsEntry, false);

		if (isNew) {
			segmentsEntry.setNew(false);
		}

		segmentsEntry.resetOriginalValues();

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param segmentsEntryId the primary key of the segments entry
	 * @return the segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry findByPrimaryKey(long segmentsEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)segmentsEntryId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the segments entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsEntryId the primary key of the segments entry
	 * @return the segments entry, or <code>null</code> if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry fetchByPrimaryKey(long segmentsEntryId) {
		return fetchByPrimaryKey((Serializable)segmentsEntryId);
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
		return "segmentsEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SEGMENTSENTRY;
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
		return SegmentsEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "SegmentsEntry";
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
		ctMergeColumnNames.add("segmentsEntryKey");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("description");
		ctMergeColumnNames.add("active_");
		ctMergeColumnNames.add("criteria");
		ctMergeColumnNames.add("source");
		ctMergeColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("segmentsEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "segmentsEntryKey"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "groupId"});
	}

	/**
	 * Initializes the segments entry persistence.
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
			new String[] {String.class.getName()}, new String[] {"uuid_"}, 0, 1,
			true, null);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"}, 0, 1,
			false, null);

		_collectionPersistenceFinderByUuid = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByUuid,
			_finderPathWithoutPaginationFindByUuid, _finderPathCountByUuid,
			_SQL_SELECT_SEGMENTSENTRY_WHERE, _SQL_COUNT_SEGMENTSENTRY_WHERE,
			SegmentsEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"segmentsEntry.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, SegmentsEntry::getUuid));

		_finderPathFetchByUUID_G = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, 0, 1, false,
			convertNullFunction(SegmentsEntry::getUuid),
			SegmentsEntry::getGroupId);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_SEGMENTSENTRY_WHERE, "",
			new FinderColumn<>(
				"segmentsEntry.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, SegmentsEntry::getUuid),
			new FinderColumn<>(
				"segmentsEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, SegmentsEntry::getGroupId));

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
			new String[] {"uuid_", "companyId"}, 0, 1, true, null);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, 0, 1, false, null);

		_collectionPersistenceFinderByUuid_C =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_C,
				_finderPathWithoutPaginationFindByUuid_C,
				_finderPathCountByUuid_C, _SQL_SELECT_SEGMENTSENTRY_WHERE,
				_SQL_COUNT_SEGMENTSENTRY_WHERE,
				SegmentsEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"segmentsEntry.", "uuid", FinderColumn.Type.STRING, "=",
					true, true, SegmentsEntry::getUuid),
				new FinderColumn<>(
					"segmentsEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, SegmentsEntry::getCompanyId));

		_finderPathWithPaginationFindBySegmentsEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySegmentsEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"segmentsEntryId"}, true);

		_finderPathWithoutPaginationFindBySegmentsEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySegmentsEntryId",
			new String[] {Long.class.getName()},
			new String[] {"segmentsEntryId"}, true);

		_finderPathCountBySegmentsEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countBySegmentsEntryId",
			new String[] {Long.class.getName()},
			new String[] {"segmentsEntryId"}, false);

		_collectionPersistenceFinderBySegmentsEntryId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindBySegmentsEntryId,
				_finderPathWithoutPaginationFindBySegmentsEntryId,
				_finderPathCountBySegmentsEntryId,
				_SQL_SELECT_SEGMENTSENTRY_WHERE, _SQL_COUNT_SEGMENTSENTRY_WHERE,
				SegmentsEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new ArrayableFinderColumn<>(
					"segmentsEntry.", "segmentsEntryId", FinderColumn.Type.LONG,
					"=", false, true, true, SegmentsEntry::getSegmentsEntryId));

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
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_collectionPersistenceFinderByGroupId =
			new FilterCollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByGroupId,
				_finderPathWithoutPaginationFindByGroupId,
				_finderPathCountByGroupId, _SQL_SELECT_SEGMENTSENTRY_WHERE,
				_SQL_COUNT_SEGMENTSENTRY_WHERE,
				SegmentsEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FilterCollectionPersistenceFinder.FilterMetadata<>(
					SegmentsEntryImpl.class, SegmentsEntry.class,
					_FILTER_ENTITY_ALIAS, _FILTER_ENTITY_TABLE,
					_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2,
					_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE,
					SegmentsEntryModelImpl.ORDER_BY_SQL,
					SegmentsEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT),
				new ArrayableFinderColumn<>(
					"segmentsEntry.", "groupId", FinderColumn.Type.LONG, "=",
					false, true, true, SegmentsEntry::getGroupId));

		_finderPathWithPaginationFindByActive = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByActive",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"active_"}, true);

		_finderPathWithoutPaginationFindByActive = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActive",
			new String[] {Boolean.class.getName()}, new String[] {"active_"},
			true);

		_finderPathCountByActive = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActive",
			new String[] {Boolean.class.getName()}, new String[] {"active_"},
			false);

		_collectionPersistenceFinderByActive =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByActive,
				_finderPathWithoutPaginationFindByActive,
				_finderPathCountByActive, _SQL_SELECT_SEGMENTSENTRY_WHERE,
				_SQL_COUNT_SEGMENTSENTRY_WHERE,
				SegmentsEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"segmentsEntry.", "active", FinderColumn.Type.BOOLEAN, "=",
					true, true, SegmentsEntry::isActive));

		_finderPathWithPaginationFindBySource = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySource",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"source"}, true);

		_finderPathWithoutPaginationFindBySource = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySource",
			new String[] {String.class.getName()}, new String[] {"source"}, 0,
			1, true, null);

		_finderPathCountBySource = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySource",
			new String[] {String.class.getName()}, new String[] {"source"}, 0,
			1, false, null);

		_collectionPersistenceFinderBySource =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindBySource,
				_finderPathWithoutPaginationFindBySource,
				_finderPathCountBySource, _SQL_SELECT_SEGMENTSENTRY_WHERE,
				_SQL_COUNT_SEGMENTSENTRY_WHERE,
				SegmentsEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"segmentsEntry.", "source", FinderColumn.Type.STRING, "=",
					true, true, SegmentsEntry::getSource));

		_finderPathFetchByG_S = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_S",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "segmentsEntryKey"}, 0, 2, false,
			SegmentsEntry::getGroupId,
			convertNullFunction(SegmentsEntry::getSegmentsEntryKey));

		_uniquePersistenceFinderByG_S = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_S, _SQL_SELECT_SEGMENTSENTRY_WHERE, "",
			new FinderColumn<>(
				"segmentsEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, SegmentsEntry::getGroupId),
			new FinderColumn<>(
				"segmentsEntry.", "segmentsEntryKey", FinderColumn.Type.STRING,
				"=", true, true, SegmentsEntry::getSegmentsEntryKey));

		_finderPathWithPaginationFindByG_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "active_"}, true);

		_finderPathWithoutPaginationFindByG_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "active_"}, true);

		_finderPathCountByG_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "active_"}, false);

		_collectionPersistenceFinderByG_A =
			new FilterCollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_A,
				_finderPathWithoutPaginationFindByG_A, _finderPathCountByG_A,
				_SQL_SELECT_SEGMENTSENTRY_WHERE, _SQL_COUNT_SEGMENTSENTRY_WHERE,
				SegmentsEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FilterCollectionPersistenceFinder.FilterMetadata<>(
					SegmentsEntryImpl.class, SegmentsEntry.class,
					_FILTER_ENTITY_ALIAS, _FILTER_ENTITY_TABLE,
					_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2,
					_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE,
					SegmentsEntryModelImpl.ORDER_BY_SQL,
					SegmentsEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT),
				new ArrayableFinderColumn<>(
					"segmentsEntry.", "groupId", FinderColumn.Type.LONG, "=",
					false, true, true, SegmentsEntry::getGroupId),
				new FinderColumn<>(
					"segmentsEntry.", "active", FinderColumn.Type.BOOLEAN, "=",
					true, true, SegmentsEntry::isActive));

		_finderPathWithPaginationFindByG_SRC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_SRC",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "source"}, true);

		_finderPathWithoutPaginationFindByG_SRC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_SRC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "source"}, 0, 2, true, null);

		_finderPathCountByG_SRC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_SRC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "source"}, 0, 2, false, null);

		_collectionPersistenceFinderByG_SRC =
			new FilterCollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_SRC,
				_finderPathWithoutPaginationFindByG_SRC,
				_finderPathCountByG_SRC, _SQL_SELECT_SEGMENTSENTRY_WHERE,
				_SQL_COUNT_SEGMENTSENTRY_WHERE,
				SegmentsEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FilterCollectionPersistenceFinder.FilterMetadata<>(
					SegmentsEntryImpl.class, SegmentsEntry.class,
					_FILTER_ENTITY_ALIAS, _FILTER_ENTITY_TABLE,
					_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2,
					_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE,
					SegmentsEntryModelImpl.ORDER_BY_SQL,
					SegmentsEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT),
				new ArrayableFinderColumn<>(
					"segmentsEntry.", "groupId", FinderColumn.Type.LONG, "=",
					false, true, true, SegmentsEntry::getGroupId),
				new FinderColumn<>(
					"segmentsEntry.", "source", FinderColumn.Type.STRING, "=",
					true, true, SegmentsEntry::getSource));

		_finderPathWithPaginationFindByG_A_SRC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A_SRC",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "active_", "source"}, true);

		_finderPathWithoutPaginationFindByG_A_SRC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A_SRC",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "active_", "source"}, 0, 4, true, null);

		_finderPathCountByG_A_SRC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_A_SRC",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "active_", "source"}, 0, 4, false, null);

		_collectionPersistenceFinderByG_A_SRC =
			new FilterCollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_A_SRC,
				_finderPathWithoutPaginationFindByG_A_SRC,
				_finderPathCountByG_A_SRC, _SQL_SELECT_SEGMENTSENTRY_WHERE,
				_SQL_COUNT_SEGMENTSENTRY_WHERE,
				SegmentsEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FilterCollectionPersistenceFinder.FilterMetadata<>(
					SegmentsEntryImpl.class, SegmentsEntry.class,
					_FILTER_ENTITY_ALIAS, _FILTER_ENTITY_TABLE,
					_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1,
					_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2,
					_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE,
					SegmentsEntryModelImpl.ORDER_BY_SQL,
					SegmentsEntryModelImpl.ORDER_BY_SQL_INLINE_DISTINCT),
				new ArrayableFinderColumn<>(
					"segmentsEntry.", "groupId", FinderColumn.Type.LONG, "=",
					false, true, true, SegmentsEntry::getGroupId),
				new FinderColumn<>(
					"segmentsEntry.", "active", FinderColumn.Type.BOOLEAN, "=",
					true, true, SegmentsEntry::isActive),
				new ArrayableFinderColumn<>(
					"segmentsEntry.", "source", FinderColumn.Type.STRING, "=",
					false, true, true, SegmentsEntry::getSource));

		_finderPathFetchByERC_G = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "groupId"}, 0, 1, false,
			convertNullFunction(SegmentsEntry::getExternalReferenceCode),
			SegmentsEntry::getGroupId);

		_uniquePersistenceFinderByERC_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_G, _SQL_SELECT_SEGMENTSENTRY_WHERE, "",
			new FinderColumn<>(
				"segmentsEntry.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, true,
				SegmentsEntry::getExternalReferenceCode),
			new FinderColumn<>(
				"segmentsEntry.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, SegmentsEntry::getGroupId));

		SegmentsEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		SegmentsEntryUtil.setPersistence(null);

		entityCache.removeCache(SegmentsEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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
		SegmentsEntryModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_SEGMENTSENTRY =
		"SELECT segmentsEntry FROM SegmentsEntry segmentsEntry";

	private static final String _SQL_SELECT_SEGMENTSENTRY_WHERE =
		"SELECT segmentsEntry FROM SegmentsEntry segmentsEntry WHERE ";

	private static final String _SQL_COUNT_SEGMENTSENTRY_WHERE =
		"SELECT COUNT(segmentsEntry) FROM SegmentsEntry segmentsEntry WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"segmentsEntry.segmentsEntryId";

	private static final String _FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE =
		"SELECT DISTINCT {segmentsEntry.*} FROM SegmentsEntry segmentsEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {SegmentsEntry.*} FROM (SELECT DISTINCT segmentsEntry.segmentsEntryId FROM SegmentsEntry segmentsEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN SegmentsEntry ON TEMP_TABLE.segmentsEntryId = SegmentsEntry.segmentsEntryId";

	private static final String _FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE =
		"SELECT COUNT(DISTINCT segmentsEntry.segmentsEntryId) AS COUNT_VALUE FROM SegmentsEntry segmentsEntry WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "segmentsEntry";

	private static final String _FILTER_ENTITY_TABLE = "SegmentsEntry";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SegmentsEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1349458489