/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.persistence.impl;

import com.liferay.launch.exception.DuplicateLaunchEntryExternalReferenceCodeException;
import com.liferay.launch.exception.NoSuchLaunchEntryException;
import com.liferay.launch.model.LaunchEntry;
import com.liferay.launch.model.LaunchEntryTable;
import com.liferay.launch.model.impl.LaunchEntryImpl;
import com.liferay.launch.model.impl.LaunchEntryModelImpl;
import com.liferay.launch.service.persistence.LaunchEntryPersistence;
import com.liferay.launch.service.persistence.LaunchEntryUtil;
import com.liferay.launch.service.persistence.impl.constants.LaunchPersistenceConstants;
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
 * The persistence implementation for the launch entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = LaunchEntryPersistence.class)
public class LaunchEntryPersistenceImpl
	extends BasePersistenceImpl<LaunchEntry, NoSuchLaunchEntryException>
	implements LaunchEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LaunchEntryUtil</code> to access the launch entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LaunchEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private CollectionPersistenceFinder<LaunchEntry, NoSuchLaunchEntryException>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns an ordered range of all the launch entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry findByUuid_First(
			String uuid, OrderByComparator<LaunchEntry> orderByComparator)
		throws NoSuchLaunchEntryException {

		return _collectionPersistenceFinderByUuid.findFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByUuid_First(
		String uuid, OrderByComparator<LaunchEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the launch entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of launch entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching launch entries
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private CollectionPersistenceFinder<LaunchEntry, NoSuchLaunchEntryException>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns an ordered range of all the launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LaunchEntry> orderByComparator)
		throws NoSuchLaunchEntryException {

		return _collectionPersistenceFinderByUuid_C.findFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LaunchEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the launch entries where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching launch entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private CollectionPersistenceFinder<LaunchEntry, NoSuchLaunchEntryException>
		_collectionPersistenceFinderByLaunchSetId;

	/**
	 * Returns an ordered range of all the launch entries where launchSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param launchSetId the launch set ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch entries
	 */
	@Override
	public List<LaunchEntry> findByLaunchSetId(
		long launchSetId, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByLaunchSetId.find(
			finderCache, new Object[] {launchSetId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry findByLaunchSetId_First(
			long launchSetId, OrderByComparator<LaunchEntry> orderByComparator)
		throws NoSuchLaunchEntryException {

		return _collectionPersistenceFinderByLaunchSetId.findFirst(
			finderCache, new Object[] {launchSetId}, orderByComparator);
	}

	/**
	 * Returns the first launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByLaunchSetId_First(
		long launchSetId, OrderByComparator<LaunchEntry> orderByComparator) {

		return _collectionPersistenceFinderByLaunchSetId.fetchFirst(
			finderCache, new Object[] {launchSetId}, orderByComparator);
	}

	/**
	 * Removes all the launch entries where launchSetId = &#63; from the database.
	 *
	 * @param launchSetId the launch set ID
	 */
	@Override
	public void removeByLaunchSetId(long launchSetId) {
		_collectionPersistenceFinderByLaunchSetId.remove(
			finderCache, new Object[] {launchSetId});
	}

	/**
	 * Returns the number of launch entries where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @return the number of matching launch entries
	 */
	@Override
	public int countByLaunchSetId(long launchSetId) {
		return _collectionPersistenceFinderByLaunchSetId.count(
			finderCache, new Object[] {launchSetId});
	}

	private UniquePersistenceFinder<LaunchEntry, NoSuchLaunchEntryException>
		_uniquePersistenceFinderByC_C_C;

	/**
	 * Returns the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry findByC_C_C(
			long classNameId, long classPK, String classVersion)
		throws NoSuchLaunchEntryException {

		return _uniquePersistenceFinderByC_C_C.find(
			finderCache, new Object[] {classNameId, classPK, classVersion});
	}

	/**
	 * Returns the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByC_C_C(
		long classNameId, long classPK, String classVersion,
		boolean useFinderCache) {

		return _uniquePersistenceFinderByC_C_C.fetch(
			finderCache, new Object[] {classNameId, classPK, classVersion},
			useFinderCache);
	}

	/**
	 * Removes the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the launch entry that was removed
	 */
	@Override
	public LaunchEntry removeByC_C_C(
			long classNameId, long classPK, String classVersion)
		throws NoSuchLaunchEntryException {

		LaunchEntry launchEntry = findByC_C_C(
			classNameId, classPK, classVersion);

		return remove(launchEntry);
	}

	/**
	 * Returns the number of launch entries where classNameId = &#63; and classPK = &#63; and classVersion = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the number of matching launch entries
	 */
	@Override
	public int countByC_C_C(
		long classNameId, long classPK, String classVersion) {

		return _uniquePersistenceFinderByC_C_C.count(
			finderCache, new Object[] {classNameId, classPK, classVersion});
	}

	private UniquePersistenceFinder<LaunchEntry, NoSuchLaunchEntryException>
		_uniquePersistenceFinderByERC_C;

	/**
	 * Returns the launch entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry findByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchLaunchEntryException {

		return _uniquePersistenceFinderByERC_C.find(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	/**
	 * Returns the launch entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	@Override
	public LaunchEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_C.fetch(
			finderCache, new Object[] {externalReferenceCode, companyId},
			useFinderCache);
	}

	/**
	 * Removes the launch entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the launch entry that was removed
	 */
	@Override
	public LaunchEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchLaunchEntryException {

		LaunchEntry launchEntry = findByERC_C(externalReferenceCode, companyId);

		return remove(launchEntry);
	}

	/**
	 * Returns the number of launch entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching launch entries
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		return _uniquePersistenceFinderByERC_C.count(
			finderCache, new Object[] {externalReferenceCode, companyId});
	}

	public LaunchEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(LaunchEntry.class);

		setModelImplClass(LaunchEntryImpl.class);
		setModelPKClass(long.class);

		setTable(LaunchEntryTable.INSTANCE);
	}

	/**
	 * Creates a new launch entry with the primary key. Does not add the launch entry to the database.
	 *
	 * @param launchEntryId the primary key for the new launch entry
	 * @return the new launch entry
	 */
	@Override
	public LaunchEntry create(long launchEntryId) {
		LaunchEntry launchEntry = new LaunchEntryImpl();

		launchEntry.setNew(true);
		launchEntry.setPrimaryKey(launchEntryId);

		String uuid = PortalUUIDUtil.generate();

		launchEntry.setUuid(uuid);

		launchEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return launchEntry;
	}

	/**
	 * Removes the launch entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry that was removed
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	@Override
	public LaunchEntry remove(long launchEntryId)
		throws NoSuchLaunchEntryException {

		return remove((Serializable)launchEntryId);
	}

	@Override
	protected LaunchEntry removeImpl(LaunchEntry launchEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(launchEntry)) {
				launchEntry = (LaunchEntry)session.get(
					LaunchEntryImpl.class, launchEntry.getPrimaryKeyObj());
			}

			if (launchEntry != null) {
				session.delete(launchEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (launchEntry != null) {
			clearCache(launchEntry);
		}

		return launchEntry;
	}

	@Override
	public LaunchEntry updateImpl(LaunchEntry launchEntry) {
		boolean isNew = launchEntry.isNew();

		if (!(launchEntry instanceof LaunchEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(launchEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(launchEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in launchEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LaunchEntry implementation " +
					launchEntry.getClass());
		}

		LaunchEntryModelImpl launchEntryModelImpl =
			(LaunchEntryModelImpl)launchEntry;

		if (Validator.isNull(launchEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			launchEntry.setUuid(uuid);
		}

		if (Validator.isNull(launchEntry.getExternalReferenceCode())) {
			launchEntry.setExternalReferenceCode(launchEntry.getUuid());
		}
		else {
			if (!Objects.equals(
					launchEntryModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					launchEntry.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = launchEntry.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = launchEntry.getPrimaryKey();
					}

					try {
						launchEntry.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								LaunchEntry.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								launchEntry.getExternalReferenceCode(), null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			LaunchEntry ercLaunchEntry = fetchByERC_C(
				launchEntry.getExternalReferenceCode(),
				launchEntry.getCompanyId());

			if (isNew) {
				if (ercLaunchEntry != null) {
					throw new DuplicateLaunchEntryExternalReferenceCodeException(
						"Duplicate launch entry with external reference code " +
							launchEntry.getExternalReferenceCode() +
								" and company " + launchEntry.getCompanyId());
				}
			}
			else {
				if ((ercLaunchEntry != null) &&
					(launchEntry.getLaunchEntryId() !=
						ercLaunchEntry.getLaunchEntryId())) {

					throw new DuplicateLaunchEntryExternalReferenceCodeException(
						"Duplicate launch entry with external reference code " +
							launchEntry.getExternalReferenceCode() +
								" and company " + launchEntry.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (launchEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				launchEntry.setCreateDate(date);
			}
			else {
				launchEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!launchEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				launchEntry.setModifiedDate(date);
			}
			else {
				launchEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(launchEntry);
			}
			else {
				launchEntry = (LaunchEntry)session.merge(launchEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(launchEntry, false);

		if (isNew) {
			launchEntry.setNew(false);
		}

		launchEntry.resetOriginalValues();

		return launchEntry;
	}

	/**
	 * Returns the launch entry with the primary key or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	@Override
	public LaunchEntry findByPrimaryKey(long launchEntryId)
		throws NoSuchLaunchEntryException {

		return findByPrimaryKey((Serializable)launchEntryId);
	}

	/**
	 * Returns the launch entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry, or <code>null</code> if a launch entry with the primary key could not be found
	 */
	@Override
	public LaunchEntry fetchByPrimaryKey(long launchEntryId) {
		return fetchByPrimaryKey((Serializable)launchEntryId);
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
		return "launchEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAUNCHENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LaunchEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the launch entry persistence.
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
			_SQL_SELECT_LAUNCHENTRY_WHERE, _SQL_COUNT_LAUNCHENTRY_WHERE,
			LaunchEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"launchEntry.", "uuid", "uuid_", FinderColumn.Type.STRING, "=",
				true, true, LaunchEntry::getUuid));

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
				_SQL_SELECT_LAUNCHENTRY_WHERE, _SQL_COUNT_LAUNCHENTRY_WHERE,
				LaunchEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"launchEntry.", "uuid", "uuid_", FinderColumn.Type.STRING,
					"=", true, true, LaunchEntry::getUuid),
				new FinderColumn<>(
					"launchEntry.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, LaunchEntry::getCompanyId));

		_collectionPersistenceFinderByLaunchSetId =
			new CollectionPersistenceFinder<>(
				this,
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLaunchSetId",
					new String[] {
						Long.class.getName(), Integer.class.getName(),
						Integer.class.getName(),
						OrderByComparator.class.getName()
					},
					new String[] {"launchSetId"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"findByLaunchSetId", new String[] {Long.class.getName()},
					new String[] {"launchSetId"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"countByLaunchSetId", new String[] {Long.class.getName()},
					new String[] {"launchSetId"}, false),
				_SQL_SELECT_LAUNCHENTRY_WHERE, _SQL_COUNT_LAUNCHENTRY_WHERE,
				LaunchEntryModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"launchEntry.", "launchSetId", FinderColumn.Type.LONG, "=",
					true, true, LaunchEntry::getLaunchSetId));

		_uniquePersistenceFinderByC_C_C = new UniquePersistenceFinder<>(
			this,
			createUniqueFinderPath(
				FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName()
				},
				new String[] {"classNameId", "classPK", "classVersion"}, 0, 4,
				false, LaunchEntry::getClassNameId, LaunchEntry::getClassPK,
				convertNullFunction(LaunchEntry::getClassVersion)),
			_SQL_SELECT_LAUNCHENTRY_WHERE, "",
			new FinderColumn<>(
				"launchEntry.", "classNameId", FinderColumn.Type.LONG, "=",
				true, true, LaunchEntry::getClassNameId),
			new FinderColumn<>(
				"launchEntry.", "classPK", FinderColumn.Type.LONG, "=", true,
				true, LaunchEntry::getClassPK),
			new FinderColumn<>(
				"launchEntry.", "classVersion", FinderColumn.Type.STRING, "=",
				true, true, LaunchEntry::getClassVersion));

		_uniquePersistenceFinderByERC_C = new UniquePersistenceFinder<>(
			this,
			createUniqueFinderPath(
				FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
				new String[] {String.class.getName(), Long.class.getName()},
				new String[] {"externalReferenceCode", "companyId"}, 0, 1,
				false,
				convertNullFunction(LaunchEntry::getExternalReferenceCode),
				LaunchEntry::getCompanyId),
			_SQL_SELECT_LAUNCHENTRY_WHERE, "",
			new FinderColumn<>(
				"launchEntry.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, true,
				LaunchEntry::getExternalReferenceCode),
			new FinderColumn<>(
				"launchEntry.", "companyId", FinderColumn.Type.LONG, "=", true,
				true, LaunchEntry::getCompanyId));

		LaunchEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		LaunchEntryUtil.setPersistence(null);

		entityCache.removeCache(LaunchEntryImpl.class.getName());
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
		LaunchEntryModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_LAUNCHENTRY =
		"SELECT launchEntry FROM LaunchEntry launchEntry";

	private static final String _SQL_SELECT_LAUNCHENTRY_WHERE =
		"SELECT launchEntry FROM LaunchEntry launchEntry WHERE ";

	private static final String _SQL_COUNT_LAUNCHENTRY_WHERE =
		"SELECT COUNT(launchEntry) FROM LaunchEntry launchEntry WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LaunchEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LaunchEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1223057405