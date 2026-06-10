/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service.persistence.impl;

import com.liferay.audience.exception.DuplicateAudienceEntryExternalReferenceCodeException;
import com.liferay.audience.exception.NoSuchAudienceEntryException;
import com.liferay.audience.model.AudienceEntry;
import com.liferay.audience.model.AudienceEntryTable;
import com.liferay.audience.model.impl.AudienceEntryImpl;
import com.liferay.audience.model.impl.AudienceEntryModelImpl;
import com.liferay.audience.service.persistence.AudienceEntryPersistence;
import com.liferay.audience.service.persistence.AudienceEntryUtil;
import com.liferay.audience.service.persistence.impl.constants.AudiencePersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.FilterCollectionPersistenceFinder;
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
 * The persistence implementation for the audience entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AudienceEntryPersistence.class)
public class AudienceEntryPersistenceImpl
	extends BasePersistenceImpl<AudienceEntry, NoSuchAudienceEntryException>
	implements AudienceEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AudienceEntryUtil</code> to access the audience entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AudienceEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FilterCollectionPersistenceFinder
		<AudienceEntry, NoSuchAudienceEntryException>
			_collectionPersistenceFinderByUuid;

	/**
	 * Returns an ordered range of all the audience entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audience entries
	 */
	@Override
	public List<AudienceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first audience entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry
	 * @throws NoSuchAudienceEntryException if a matching audience entry could not be found
	 */
	@Override
	public AudienceEntry findByUuid_First(
			String uuid, OrderByComparator<AudienceEntry> orderByComparator)
		throws NoSuchAudienceEntryException {

		return _collectionPersistenceFinderByUuid.findFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Returns the first audience entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	@Override
	public AudienceEntry fetchByUuid_First(
		String uuid, OrderByComparator<AudienceEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the audience entries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries that the user has permission to view
	 */
	@Override
	public List<AudienceEntry> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.filterFind(
			finderCache, new Object[] {uuid}, start, end, orderByComparator);
	}

	/**
	 * Removes all the audience entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of audience entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching audience entries
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of audience entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching audience entries that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.filterCount(
			finderCache, new Object[] {uuid});
	}

	private FilterCollectionPersistenceFinder
		<AudienceEntry, NoSuchAudienceEntryException>
			_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns an ordered range of all the audience entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audience entries
	 */
	@Override
	public List<AudienceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first audience entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry
	 * @throws NoSuchAudienceEntryException if a matching audience entry could not be found
	 */
	@Override
	public AudienceEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AudienceEntry> orderByComparator)
		throws NoSuchAudienceEntryException {

		return _collectionPersistenceFinderByUuid_C.findFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Returns the first audience entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	@Override
	public AudienceEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the audience entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries that the user has permission to view
	 */
	@Override
	public List<AudienceEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.filterFind(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, companyId, 0);
	}

	/**
	 * Removes all the audience entries where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of audience entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching audience entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	/**
	 * Returns the number of audience entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching audience entries that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.filterCount(
			finderCache, new Object[] {uuid, companyId}, companyId, 0);
	}

	private FilterCollectionPersistenceFinder
		<AudienceEntry, NoSuchAudienceEntryException>
			_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns an ordered range of all the audience entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audience entries
	 */
	@Override
	public List<AudienceEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry
	 * @throws NoSuchAudienceEntryException if a matching audience entry could not be found
	 */
	@Override
	public AudienceEntry findByCompanyId_First(
			long companyId, OrderByComparator<AudienceEntry> orderByComparator)
		throws NoSuchAudienceEntryException {

		return _collectionPersistenceFinderByCompanyId.findFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	@Override
	public AudienceEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<AudienceEntry> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the audience entries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries that the user has permission to view
	 */
	@Override
	public List<AudienceEntry> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.filterFind(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, companyId, 0);
	}

	/**
	 * Removes all the audience entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of audience entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching audience entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of audience entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching audience entries that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.filterCount(
			finderCache, new Object[] {companyId}, companyId, 0);
	}

	private FilterCollectionPersistenceFinder
		<AudienceEntry, NoSuchAudienceEntryException>
			_collectionPersistenceFinderByC_LikeN;

	/**
	 * Returns all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching audience entries
	 */
	@Override
	public List<AudienceEntry> findByC_LikeN(long companyId, String name) {
		return findByC_LikeN(
			companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of matching audience entries
	 */
	@Override
	public List<AudienceEntry> findByC_LikeN(
		long companyId, String name, int start, int end) {

		return findByC_LikeN(companyId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries
	 */
	@Override
	public List<AudienceEntry> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return findByC_LikeN(
			companyId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audience entries
	 */
	@Override
	public List<AudienceEntry> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByC_LikeN.find(
			finderCache, new Object[] {companyId, name}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry
	 * @throws NoSuchAudienceEntryException if a matching audience entry could not be found
	 */
	@Override
	public AudienceEntry findByC_LikeN_First(
			long companyId, String name,
			OrderByComparator<AudienceEntry> orderByComparator)
		throws NoSuchAudienceEntryException {

		return _collectionPersistenceFinderByC_LikeN.findFirst(
			finderCache, new Object[] {companyId, name}, orderByComparator);
	}

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	@Override
	public AudienceEntry fetchByC_LikeN_First(
		long companyId, String name,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return _collectionPersistenceFinderByC_LikeN.fetchFirst(
			finderCache, new Object[] {companyId, name}, orderByComparator);
	}

	/**
	 * Returns all the audience entries that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching audience entries that the user has permission to view
	 */
	@Override
	public List<AudienceEntry> filterFindByC_LikeN(
		long companyId, String name) {

		return filterFindByC_LikeN(
			companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the audience entries that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of matching audience entries that the user has permission to view
	 */
	@Override
	public List<AudienceEntry> filterFindByC_LikeN(
		long companyId, String name, int start, int end) {

		return filterFindByC_LikeN(companyId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the audience entries that the user has permissions to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries that the user has permission to view
	 */
	@Override
	public List<AudienceEntry> filterFindByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return _collectionPersistenceFinderByC_LikeN.filterFind(
			finderCache, new Object[] {companyId, name}, start, end,
			orderByComparator, companyId, 0);
	}

	/**
	 * Removes all the audience entries where companyId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	@Override
	public void removeByC_LikeN(long companyId, String name) {
		_collectionPersistenceFinderByC_LikeN.remove(
			finderCache, new Object[] {companyId, name});
	}

	/**
	 * Returns the number of audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching audience entries
	 */
	@Override
	public int countByC_LikeN(long companyId, String name) {
		return _collectionPersistenceFinderByC_LikeN.count(
			finderCache, new Object[] {companyId, name});
	}

	/**
	 * Returns the number of audience entries that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching audience entries that the user has permission to view
	 */
	@Override
	public int filterCountByC_LikeN(long companyId, String name) {
		return _collectionPersistenceFinderByC_LikeN.filterCount(
			finderCache, new Object[] {companyId, name}, companyId, 0);
	}

	private UniquePersistenceFinder<AudienceEntry, NoSuchAudienceEntryException>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the audience entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchAudienceEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching audience entry
	 * @throws NoSuchAudienceEntryException if a matching audience entry could not be found
	 */
	@Override
	public AudienceEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchAudienceEntryException {

		return _uniquePersistenceFinderByERC_C.find(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	/**
	 * Returns the audience entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	@Override
	public AudienceEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_C.fetch(
			finderCache, new Object[] {externalReferenceCode, companyId},
			useFinderCache);
	}

	/**
	 * Removes the audience entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the audience entry that was removed
	 */
	@Override
	public AudienceEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchAudienceEntryException {

		AudienceEntry audienceEntry = findByERC_C(
			externalReferenceCode, companyId);

		return remove(audienceEntry);
	}

	/**
	 * Returns the number of audience entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching audience entries
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public AudienceEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AudienceEntry.class);

		setModelImplClass(AudienceEntryImpl.class);
		setModelPKClass(long.class);

		setTable(AudienceEntryTable.INSTANCE);
	}

	/**
	 * Creates a new audience entry with the primary key. Does not add the audience entry to the database.
	 *
	 * @param audienceEntryId the primary key for the new audience entry
	 * @return the new audience entry
	 */
	@Override
	public AudienceEntry create(long audienceEntryId) {
		AudienceEntry audienceEntry = new AudienceEntryImpl();

		audienceEntry.setNew(true);
		audienceEntry.setPrimaryKey(audienceEntryId);

		String uuid = PortalUUIDUtil.generate();

		audienceEntry.setUuid(uuid);

		audienceEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return audienceEntry;
	}

	/**
	 * Removes the audience entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry that was removed
	 * @throws NoSuchAudienceEntryException if a audience entry with the primary key could not be found
	 */
	@Override
	public AudienceEntry remove(long audienceEntryId)
		throws NoSuchAudienceEntryException {

		return remove((Serializable)audienceEntryId);
	}

	@Override
	protected AudienceEntry removeImpl(AudienceEntry audienceEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(audienceEntry)) {
				audienceEntry = (AudienceEntry)session.get(
					AudienceEntryImpl.class, audienceEntry.getPrimaryKeyObj());
			}

			if (audienceEntry != null) {
				session.delete(audienceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (audienceEntry != null) {
			clearCache(audienceEntry);
		}

		return audienceEntry;
	}

	@Override
	public AudienceEntry updateImpl(AudienceEntry audienceEntry) {
		boolean isNew = audienceEntry.isNew();

		if (!(audienceEntry instanceof AudienceEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(audienceEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					audienceEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in audienceEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AudienceEntry implementation " +
					audienceEntry.getClass());
		}

		AudienceEntryModelImpl audienceEntryModelImpl =
			(AudienceEntryModelImpl)audienceEntry;

		if (Validator.isNull(audienceEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			audienceEntry.setUuid(uuid);
		}

		if (Validator.isNull(audienceEntry.getExternalReferenceCode())) {
			audienceEntry.setExternalReferenceCode(audienceEntry.getUuid());
		}
		else {
			if (!Objects.equals(
					audienceEntryModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					audienceEntry.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = audienceEntry.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = audienceEntry.getPrimaryKey();
					}

					try {
						audienceEntry.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								AudienceEntry.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								audienceEntry.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			AudienceEntry ercAudienceEntry = fetchByERC_C(
				audienceEntry.getExternalReferenceCode(),
				audienceEntry.getCompanyId());

			if (isNew) {
				if (ercAudienceEntry != null) {
					throw new DuplicateAudienceEntryExternalReferenceCodeException(
						"Duplicate audience entry with external reference code " +
							audienceEntry.getExternalReferenceCode() +
								" and company " + audienceEntry.getCompanyId());
				}
			}
			else {
				if ((ercAudienceEntry != null) &&
					(audienceEntry.getAudienceEntryId() !=
						ercAudienceEntry.getAudienceEntryId())) {

					throw new DuplicateAudienceEntryExternalReferenceCodeException(
						"Duplicate audience entry with external reference code " +
							audienceEntry.getExternalReferenceCode() +
								" and company " + audienceEntry.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (audienceEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				audienceEntry.setCreateDate(date);
			}
			else {
				audienceEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!audienceEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				audienceEntry.setModifiedDate(date);
			}
			else {
				audienceEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(audienceEntry);
			}
			else {
				audienceEntry = (AudienceEntry)session.merge(audienceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(audienceEntry, false);

		if (isNew) {
			audienceEntry.setNew(false);
		}

		audienceEntry.resetOriginalValues();

		return audienceEntry;
	}

	/**
	 * Returns the audience entry with the primary key or throws a <code>NoSuchAudienceEntryException</code> if it could not be found.
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry
	 * @throws NoSuchAudienceEntryException if a audience entry with the primary key could not be found
	 */
	@Override
	public AudienceEntry findByPrimaryKey(long audienceEntryId)
		throws NoSuchAudienceEntryException {

		return findByPrimaryKey((Serializable)audienceEntryId);
	}

	/**
	 * Returns the audience entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry, or <code>null</code> if a audience entry with the primary key could not be found
	 */
	@Override
	public AudienceEntry fetchByPrimaryKey(long audienceEntryId) {
		return fetchByPrimaryKey((Serializable)audienceEntryId);
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
		return "audienceEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_AUDIENCEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AudienceEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the audience entry persistence.
	 */
	@Activate
	public void activate() {
		_collectionPersistenceFinderByUuid =
			new FilterCollectionPersistenceFinder<>(
				this,
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
					new String[] {
						String.class.getName(), Integer.class.getName(),
						Integer.class.getName(),
						OrderByComparator.class.getName()
					},
					new String[] {"uuid_"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
					new String[] {String.class.getName()},
					new String[] {"uuid_"}, 0, 1, true, null),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
					new String[] {String.class.getName()},
					new String[] {"uuid_"}, 0, 1, false, null),
				_SQL_SELECT_AUDIENCEENTRY_WHERE, _SQL_COUNT_AUDIENCEENTRY_WHERE,
				AudienceEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"audienceEntry.", "uuid", "uuid_", FinderColumn.Type.STRING,
					"=", true, true, AudienceEntry::getUuid));

		_collectionPersistenceFinderByUuid_C =
			new FilterCollectionPersistenceFinder<>(
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
				_SQL_SELECT_AUDIENCEENTRY_WHERE, _SQL_COUNT_AUDIENCEENTRY_WHERE,
				AudienceEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"audienceEntry.", "uuid", "uuid_", FinderColumn.Type.STRING,
					"=", true, true, AudienceEntry::getUuid),
				new FinderColumn<>(
					"audienceEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, AudienceEntry::getCompanyId));

		_collectionPersistenceFinderByCompanyId =
			new FilterCollectionPersistenceFinder<>(
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
				_SQL_SELECT_AUDIENCEENTRY_WHERE, _SQL_COUNT_AUDIENCEENTRY_WHERE,
				AudienceEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"audienceEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, AudienceEntry::getCompanyId));

		_collectionPersistenceFinderByC_LikeN =
			new FilterCollectionPersistenceFinder<>(
				this,
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LikeN",
					new String[] {
						Long.class.getName(), String.class.getName(),
						Integer.class.getName(), Integer.class.getName(),
						OrderByComparator.class.getName()
					},
					new String[] {"companyId", "name"}, true),
				null,
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LikeN",
					new String[] {Long.class.getName(), String.class.getName()},
					new String[] {"companyId", "name"}, false),
				_SQL_SELECT_AUDIENCEENTRY_WHERE, _SQL_COUNT_AUDIENCEENTRY_WHERE,
				AudienceEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"audienceEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, AudienceEntry::getCompanyId),
				new FinderColumn<>(
					"audienceEntry.", "name", FinderColumn.Type.STRING, "LIKE",
					false, true, AudienceEntry::getName));

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this,
			createUniqueFinderPath(
				FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
				new String[] {String.class.getName(), Long.class.getName()},
				new String[] {"externalReferenceCode", "companyId"}, 0, 1,
				false,
				convertNullFunction(AudienceEntry::getExternalReferenceCode),
				AudienceEntry::getCompanyId),
			_SQL_SELECT_AUDIENCEENTRY_WHERE, "",
			new FinderColumn<>(
				"audienceEntry.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, true,
				AudienceEntry::getExternalReferenceCode),
			new FinderColumn<>(
				"audienceEntry.", "companyId", FinderColumn.Type.LONG, "=",
				true, true, AudienceEntry::getCompanyId));

		AudienceEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		AudienceEntryUtil.setPersistence(null);

		entityCache.removeCache(AudienceEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = AudiencePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AudiencePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AudiencePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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
		AudienceEntryModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_AUDIENCEENTRY =
		"SELECT audienceEntry FROM AudienceEntry audienceEntry";

	private static final String _SQL_SELECT_AUDIENCEENTRY_WHERE =
		"SELECT audienceEntry FROM AudienceEntry audienceEntry WHERE ";

	private static final String _SQL_COUNT_AUDIENCEENTRY_WHERE =
		"SELECT COUNT(audienceEntry) FROM AudienceEntry audienceEntry WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AudienceEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AudienceEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-425107667