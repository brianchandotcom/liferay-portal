/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.forecast.alert.service.persistence.impl;

import com.liferay.commerce.machine.learning.forecast.alert.exception.NoSuchMLForecastAlertEntryException;
import com.liferay.commerce.machine.learning.forecast.alert.model.CommerceMLForecastAlertEntry;
import com.liferay.commerce.machine.learning.forecast.alert.model.CommerceMLForecastAlertEntryTable;
import com.liferay.commerce.machine.learning.forecast.alert.model.impl.CommerceMLForecastAlertEntryImpl;
import com.liferay.commerce.machine.learning.forecast.alert.model.impl.CommerceMLForecastAlertEntryModelImpl;
import com.liferay.commerce.machine.learning.forecast.alert.service.persistence.CommerceMLForecastAlertEntryPersistence;
import com.liferay.commerce.machine.learning.forecast.alert.service.persistence.CommerceMLForecastAlertEntryUtil;
import com.liferay.commerce.machine.learning.forecast.alert.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
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
import com.liferay.portal.kernel.util.ArrayUtil;
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
 * The persistence implementation for the commerce ml forecast alert entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Riccardo Ferrari
 * @generated
 */
@Component(service = CommerceMLForecastAlertEntryPersistence.class)
public class CommerceMLForecastAlertEntryPersistenceImpl
	extends BasePersistenceImpl
		<CommerceMLForecastAlertEntry, NoSuchMLForecastAlertEntryException>
	implements CommerceMLForecastAlertEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceMLForecastAlertEntryUtil</code> to access the commerce ml forecast alert entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceMLForecastAlertEntryImpl.class.getName();

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
	private CollectionPersistenceFinder<CommerceMLForecastAlertEntry>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByUuid_First(
			String uuid,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByUuid_First(uuid, orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		throw new NoSuchMLForecastAlertEntryException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the commerce ml forecast alert entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of commerce ml forecast alert entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<CommerceMLForecastAlertEntry>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		throw new NoSuchMLForecastAlertEntryException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the commerce ml forecast alert entries where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of commerce ml forecast alert entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private FinderPath _finderPathFetchByC_C_T;
	private UniquePersistenceFinder<CommerceMLForecastAlertEntry>
		_uniquePersistenceFinderByC_C_T;

	/**
	 * Returns the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; or throws a <code>NoSuchMLForecastAlertEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByC_C_T(
			long companyId, long commerceAccountId, Date timestamp)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByC_C_T(companyId, commerceAccountId, timestamp);

		if (commerceMLForecastAlertEntry == null) {
			String message =
				_uniquePersistenceFinderByC_C_T.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {companyId, commerceAccountId, timestamp});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchMLForecastAlertEntryException(message);
		}

		return commerceMLForecastAlertEntry;
	}

	/**
	 * Returns the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_T(
		long companyId, long commerceAccountId, Date timestamp) {

		return fetchByC_C_T(companyId, commerceAccountId, timestamp, true);
	}

	/**
	 * Returns the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_T(
		long companyId, long commerceAccountId, Date timestamp,
		boolean useFinderCache) {

		return _uniquePersistenceFinderByC_C_T.fetch(
			finderCache, new Object[] {companyId, commerceAccountId, timestamp},
			useFinderCache);
	}

	/**
	 * Removes the commerce ml forecast alert entry where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the commerce ml forecast alert entry that was removed
	 */
	@Override
	public CommerceMLForecastAlertEntry removeByC_C_T(
			long companyId, long commerceAccountId, Date timestamp)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry = findByC_C_T(
			companyId, commerceAccountId, timestamp);

		return remove(commerceMLForecastAlertEntry);
	}

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and timestamp = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param timestamp the timestamp
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_T(
		long companyId, long commerceAccountId, Date timestamp) {

		return _uniquePersistenceFinderByC_C_T.count(
			finderCache,
			new Object[] {companyId, commerceAccountId, timestamp});
	}

	private FinderPath _finderPathWithPaginationFindByC_C_S;
	private FinderPath _finderPathWithoutPaginationFindByC_C_S;
	private FinderPath _finderPathCountByC_C_S;
	private FinderPath _finderPathWithPaginationCountByC_C_S;

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status) {

		return findByC_C_S(
			companyId, commerceAccountId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status, int start,
		int end) {

		return findByC_C_S(
			companyId, commerceAccountId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_S(
			companyId, commerceAccountId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long commerceAccountId, int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C_S;
				finderArgs = new Object[] {
					companyId, commerceAccountId, status
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C_S;
			finderArgs = new Object[] {
				companyId, commerceAccountId, status, start, end,
				orderByComparator
			};
		}

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						(commerceAccountId !=
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
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
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByC_C_S_First(
			long companyId, long commerceAccountId, int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByC_C_S_First(
				companyId, commerceAccountId, status, orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_S_First(
		long companyId, long commerceAccountId, int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		List<CommerceMLForecastAlertEntry> list = findByC_C_S(
			companyId, commerceAccountId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status) {

		return findByC_C_S(
			companyId, commerceAccountIds, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status, int start,
		int end) {

		return findByC_C_S(
			companyId, commerceAccountIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status, int start,
		int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_S(
			companyId, commerceAccountIds, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_S(
		long companyId, long[] commerceAccountIds, int status, int start,
		int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		if (commerceAccountIds.length == 1) {
			return findByC_C_S(
				companyId, commerceAccountIds[0], status, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, StringUtil.merge(commerceAccountIds), status
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, StringUtil.merge(commerceAccountIds), status, start,
				end, orderByComparator
			};
		}

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByC_C_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						!ArrayUtil.contains(
							commerceAccountIds,
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_C_S, finderArgs, list);
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
	 * Removes all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 */
	@Override
	public void removeByC_C_S(
		long companyId, long commerceAccountId, int status) {

		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				findByC_C_S(
					companyId, commerceAccountId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceMLForecastAlertEntry);
		}
	}

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_S(
		long companyId, long commerceAccountId, int status) {

		FinderPath finderPath = _finderPathCountByC_C_S;

		Object[] finderArgs = new Object[] {
			companyId, commerceAccountId, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

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

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_S(
		long companyId, long[] commerceAccountIds, int status) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		Object[] finderArgs = new Object[] {
			companyId, StringUtil.merge(commerceAccountIds), status
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_C_S, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

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
					_finderPathWithPaginationCountByC_C_S, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_C_S_COMPANYID_2 =
		"commerceMLForecastAlertEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_2 =
		"commerceMLForecastAlertEntry.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_COMMERCEACCOUNTID_7 =
		"commerceMLForecastAlertEntry.commerceAccountId IN (";

	private static final String _FINDER_COLUMN_C_C_S_STATUS_2 =
		"commerceMLForecastAlertEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_GtRc_S;
	private FinderPath _finderPathWithPaginationCountByC_C_GtRc_S;

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountId, relativeChange, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountId, relativeChange, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountId, relativeChange, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_C_GtRc_S;
		finderArgs = new Object[] {
			companyId, commerceAccountId, relativeChange, status, start, end,
			orderByComparator
		};

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						(commerceAccountId !=
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(relativeChange >=
							commerceMLForecastAlertEntry.getRelativeChange()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
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
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByC_C_GtRc_S_First(
			long companyId, long commerceAccountId, double relativeChange,
			int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByC_C_GtRc_S_First(
				companyId, commerceAccountId, relativeChange, status,
				orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", relativeChange>");
		sb.append(relativeChange);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_GtRc_S_First(
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		List<CommerceMLForecastAlertEntry> list = findByC_C_GtRc_S(
			companyId, commerceAccountId, relativeChange, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountIds, relativeChange, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountIds, relativeChange, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_GtRc_S(
			companyId, commerceAccountIds, relativeChange, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		if (commerceAccountIds.length == 1) {
			return findByC_C_GtRc_S(
				companyId, commerceAccountIds[0], relativeChange, status, start,
				end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, StringUtil.merge(commerceAccountIds),
					relativeChange, status
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, StringUtil.merge(commerceAccountIds), relativeChange,
				status, start, end, orderByComparator
			};
		}

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByC_C_GtRc_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						!ArrayUtil.contains(
							commerceAccountIds,
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(relativeChange >=
							commerceMLForecastAlertEntry.getRelativeChange()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_C_GtRc_S, finderArgs,
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

	/**
	 * Removes all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 */
	@Override
	public void removeByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				findByC_C_GtRc_S(
					companyId, commerceAccountId, relativeChange, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceMLForecastAlertEntry);
		}
	}

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_GtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		FinderPath finderPath = _finderPathWithPaginationCountByC_C_GtRc_S;

		Object[] finderArgs = new Object[] {
			companyId, commerceAccountId, relativeChange, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				queryPos.add(relativeChange);

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

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &gt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_GtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		Object[] finderArgs = new Object[] {
			companyId, StringUtil.merge(commerceAccountIds), relativeChange,
			status
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_C_GtRc_S, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_GTRC_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByC_C_GtRc_S, finderArgs,
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

	private static final String _FINDER_COLUMN_C_C_GTRC_S_COMPANYID_2 =
		"commerceMLForecastAlertEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_2 =
		"commerceMLForecastAlertEntry.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_GTRC_S_COMMERCEACCOUNTID_7 =
		"commerceMLForecastAlertEntry.commerceAccountId IN (";

	private static final String _FINDER_COLUMN_C_C_GTRC_S_RELATIVECHANGE_2 =
		"commerceMLForecastAlertEntry.relativeChange > ? AND ";

	private static final String _FINDER_COLUMN_C_C_GTRC_S_STATUS_2 =
		"commerceMLForecastAlertEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_LtRc_S;
	private FinderPath _finderPathWithPaginationCountByC_C_LtRc_S;

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountId, relativeChange, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountId, relativeChange, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountId, relativeChange, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_C_LtRc_S;
		finderArgs = new Object[] {
			companyId, commerceAccountId, relativeChange, status, start, end,
			orderByComparator
		};

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						(commerceAccountId !=
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(relativeChange <=
							commerceMLForecastAlertEntry.getRelativeChange()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
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
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByC_C_LtRc_S_First(
			long companyId, long commerceAccountId, double relativeChange,
			int status,
			OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator)
		throws NoSuchMLForecastAlertEntryException {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			fetchByC_C_LtRc_S_First(
				companyId, commerceAccountId, relativeChange, status,
				orderByComparator);

		if (commerceMLForecastAlertEntry != null) {
			return commerceMLForecastAlertEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", relativeChange<");
		sb.append(relativeChange);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchMLForecastAlertEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce ml forecast alert entry in the ordered set where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce ml forecast alert entry, or <code>null</code> if a matching commerce ml forecast alert entry could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByC_C_LtRc_S_First(
		long companyId, long commerceAccountId, double relativeChange,
		int status,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		List<CommerceMLForecastAlertEntry> list = findByC_C_LtRc_S(
			companyId, commerceAccountId, relativeChange, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountIds, relativeChange, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountIds, relativeChange, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findByC_C_LtRc_S(
			companyId, commerceAccountIds, relativeChange, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status, int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
		boolean useFinderCache) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		if (commerceAccountIds.length == 1) {
			return findByC_C_LtRc_S(
				companyId, commerceAccountIds[0], relativeChange, status, start,
				end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, StringUtil.merge(commerceAccountIds),
					relativeChange, status
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, StringUtil.merge(commerceAccountIds), relativeChange,
				status, start, end, orderByComparator
			};
		}

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByC_C_LtRc_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
						list) {

					if ((companyId !=
							commerceMLForecastAlertEntry.getCompanyId()) ||
						!ArrayUtil.contains(
							commerceAccountIds,
							commerceMLForecastAlertEntry.
								getCommerceAccountId()) ||
						(relativeChange <=
							commerceMLForecastAlertEntry.getRelativeChange()) ||
						(status != commerceMLForecastAlertEntry.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_C_LtRc_S, finderArgs,
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

	/**
	 * Removes all the commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 */
	@Override
	public void removeByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				findByC_C_LtRc_S(
					companyId, commerceAccountId, relativeChange, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceMLForecastAlertEntry);
		}
	}

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountId the commerce account ID
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_LtRc_S(
		long companyId, long commerceAccountId, double relativeChange,
		int status) {

		FinderPath finderPath = _finderPathWithPaginationCountByC_C_LtRc_S;

		Object[] finderArgs = new Object[] {
			companyId, commerceAccountId, relativeChange, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(commerceAccountId);

				queryPos.add(relativeChange);

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

	/**
	 * Returns the number of commerce ml forecast alert entries where companyId = &#63; and commerceAccountId = any &#63; and relativeChange &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param commerceAccountIds the commerce account IDs
	 * @param relativeChange the relative change
	 * @param status the status
	 * @return the number of matching commerce ml forecast alert entries
	 */
	@Override
	public int countByC_C_LtRc_S(
		long companyId, long[] commerceAccountIds, double relativeChange,
		int status) {

		if (commerceAccountIds == null) {
			commerceAccountIds = new long[0];
		}
		else if (commerceAccountIds.length > 1) {
			commerceAccountIds = ArrayUtil.sortedUnique(commerceAccountIds);
		}

		Object[] finderArgs = new Object[] {
			companyId, StringUtil.merge(commerceAccountIds), relativeChange,
			status
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_C_LtRc_S, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMPANYID_2);

			if (commerceAccountIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_7);

				sb.append(StringUtil.merge(commerceAccountIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_RELATIVECHANGE_2);

			sb.append(_FINDER_COLUMN_C_C_LTRC_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(relativeChange);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByC_C_LtRc_S, finderArgs,
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

	private static final String _FINDER_COLUMN_C_C_LTRC_S_COMPANYID_2 =
		"commerceMLForecastAlertEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_2 =
		"commerceMLForecastAlertEntry.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_LTRC_S_COMMERCEACCOUNTID_7 =
		"commerceMLForecastAlertEntry.commerceAccountId IN (";

	private static final String _FINDER_COLUMN_C_C_LTRC_S_RELATIVECHANGE_2 =
		"commerceMLForecastAlertEntry.relativeChange < ? AND ";

	private static final String _FINDER_COLUMN_C_C_LTRC_S_STATUS_2 =
		"commerceMLForecastAlertEntry.status = ?";

	public CommerceMLForecastAlertEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceMLForecastAlertEntry.class);

		setModelImplClass(CommerceMLForecastAlertEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceMLForecastAlertEntryTable.INSTANCE);
	}

	/**
	 * Caches the commerce ml forecast alert entry in the entity cache if it is enabled.
	 *
	 * @param commerceMLForecastAlertEntry the commerce ml forecast alert entry
	 */
	@Override
	public void cacheResult(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

		entityCache.putResult(
			CommerceMLForecastAlertEntryImpl.class,
			commerceMLForecastAlertEntry.getPrimaryKey(),
			commerceMLForecastAlertEntry);

		finderCache.putResult(
			_finderPathFetchByC_C_T,
			new Object[] {
				commerceMLForecastAlertEntry.getCompanyId(),
				commerceMLForecastAlertEntry.getCommerceAccountId(),
				commerceMLForecastAlertEntry.getTimestamp()
			},
			commerceMLForecastAlertEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce ml forecast alert entries in the entity cache if it is enabled.
	 *
	 * @param commerceMLForecastAlertEntries the commerce ml forecast alert entries
	 */
	@Override
	public void cacheResult(
		List<CommerceMLForecastAlertEntry> commerceMLForecastAlertEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceMLForecastAlertEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				commerceMLForecastAlertEntries) {

			if (entityCache.getResult(
					CommerceMLForecastAlertEntryImpl.class,
					commerceMLForecastAlertEntry.getPrimaryKey()) == null) {

				cacheResult(commerceMLForecastAlertEntry);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceMLForecastAlertEntryModelImpl
			commerceMLForecastAlertEntryModelImpl) {

		Object[] args = new Object[] {
			commerceMLForecastAlertEntryModelImpl.getCompanyId(),
			commerceMLForecastAlertEntryModelImpl.getCommerceAccountId(),
			_getTime(commerceMLForecastAlertEntryModelImpl.getTimestamp())
		};

		finderCache.putResult(
			_finderPathFetchByC_C_T, args,
			commerceMLForecastAlertEntryModelImpl);
	}

	/**
	 * Creates a new commerce ml forecast alert entry with the primary key. Does not add the commerce ml forecast alert entry to the database.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key for the new commerce ml forecast alert entry
	 * @return the new commerce ml forecast alert entry
	 */
	@Override
	public CommerceMLForecastAlertEntry create(
		long commerceMLForecastAlertEntryId) {

		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry =
			new CommerceMLForecastAlertEntryImpl();

		commerceMLForecastAlertEntry.setNew(true);
		commerceMLForecastAlertEntry.setPrimaryKey(
			commerceMLForecastAlertEntryId);

		String uuid = PortalUUIDUtil.generate();

		commerceMLForecastAlertEntry.setUuid(uuid);

		commerceMLForecastAlertEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceMLForecastAlertEntry;
	}

	/**
	 * Removes the commerce ml forecast alert entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the commerce ml forecast alert entry
	 * @return the commerce ml forecast alert entry that was removed
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry remove(
			long commerceMLForecastAlertEntryId)
		throws NoSuchMLForecastAlertEntryException {

		return remove((Serializable)commerceMLForecastAlertEntryId);
	}

	@Override
	protected CommerceMLForecastAlertEntry removeImpl(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceMLForecastAlertEntry)) {
				commerceMLForecastAlertEntry =
					(CommerceMLForecastAlertEntry)session.get(
						CommerceMLForecastAlertEntryImpl.class,
						commerceMLForecastAlertEntry.getPrimaryKeyObj());
			}

			if (commerceMLForecastAlertEntry != null) {
				session.delete(commerceMLForecastAlertEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceMLForecastAlertEntry != null) {
			clearCache(commerceMLForecastAlertEntry);
		}

		return commerceMLForecastAlertEntry;
	}

	@Override
	public CommerceMLForecastAlertEntry updateImpl(
		CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

		boolean isNew = commerceMLForecastAlertEntry.isNew();

		if (!(commerceMLForecastAlertEntry instanceof
				CommerceMLForecastAlertEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceMLForecastAlertEntry.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceMLForecastAlertEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceMLForecastAlertEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceMLForecastAlertEntry implementation " +
					commerceMLForecastAlertEntry.getClass());
		}

		CommerceMLForecastAlertEntryModelImpl
			commerceMLForecastAlertEntryModelImpl =
				(CommerceMLForecastAlertEntryModelImpl)
					commerceMLForecastAlertEntry;

		if (Validator.isNull(commerceMLForecastAlertEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceMLForecastAlertEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceMLForecastAlertEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceMLForecastAlertEntry.setCreateDate(date);
			}
			else {
				commerceMLForecastAlertEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceMLForecastAlertEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceMLForecastAlertEntry.setModifiedDate(date);
			}
			else {
				commerceMLForecastAlertEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceMLForecastAlertEntry);
			}
			else {
				commerceMLForecastAlertEntry =
					(CommerceMLForecastAlertEntry)session.merge(
						commerceMLForecastAlertEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceMLForecastAlertEntryImpl.class,
			commerceMLForecastAlertEntryModelImpl, false, true);

		cacheUniqueFindersCache(commerceMLForecastAlertEntryModelImpl);

		if (isNew) {
			commerceMLForecastAlertEntry.setNew(false);
		}

		commerceMLForecastAlertEntry.resetOriginalValues();

		return commerceMLForecastAlertEntry;
	}

	/**
	 * Returns the commerce ml forecast alert entry with the primary key or throws a <code>NoSuchMLForecastAlertEntryException</code> if it could not be found.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the commerce ml forecast alert entry
	 * @return the commerce ml forecast alert entry
	 * @throws NoSuchMLForecastAlertEntryException if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry findByPrimaryKey(
			long commerceMLForecastAlertEntryId)
		throws NoSuchMLForecastAlertEntryException {

		return findByPrimaryKey((Serializable)commerceMLForecastAlertEntryId);
	}

	/**
	 * Returns the commerce ml forecast alert entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceMLForecastAlertEntryId the primary key of the commerce ml forecast alert entry
	 * @return the commerce ml forecast alert entry, or <code>null</code> if a commerce ml forecast alert entry with the primary key could not be found
	 */
	@Override
	public CommerceMLForecastAlertEntry fetchByPrimaryKey(
		long commerceMLForecastAlertEntryId) {

		return fetchByPrimaryKey((Serializable)commerceMLForecastAlertEntryId);
	}

	/**
	 * Returns all the commerce ml forecast alert entries.
	 *
	 * @return the commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce ml forecast alert entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @return the range of commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce ml forecast alert entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceMLForecastAlertEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce ml forecast alert entries
	 * @param end the upper bound of the range of commerce ml forecast alert entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce ml forecast alert entries
	 */
	@Override
	public List<CommerceMLForecastAlertEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceMLForecastAlertEntry> orderByComparator,
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

		List<CommerceMLForecastAlertEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceMLForecastAlertEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEMLFORECASTALERTENTRY;

				sql = sql.concat(
					CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceMLForecastAlertEntry>)QueryUtil.list(
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
	 * Removes all the commerce ml forecast alert entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceMLForecastAlertEntry commerceMLForecastAlertEntry :
				findAll()) {

			remove(commerceMLForecastAlertEntry);
		}
	}

	/**
	 * Returns the number of commerce ml forecast alert entries.
	 *
	 * @return the number of commerce ml forecast alert entries
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
					_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY);

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
		return "commerceMLForecastAlertEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEMLFORECASTALERTENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceMLForecastAlertEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce ml forecast alert entry persistence.
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
			_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE,
			_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE,
			CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"commerceMLForecastAlertEntry.", "uuid",
				FinderColumn.Type.STRING, "=", true, true,
				CommerceMLForecastAlertEntry::getUuid));

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
				_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE,
				_SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE,
				CommerceMLForecastAlertEntryModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"commerceMLForecastAlertEntry.", "uuid",
					FinderColumn.Type.STRING, "=", true, false,
					CommerceMLForecastAlertEntry::getUuid),
				new FinderColumn<>(
					"commerceMLForecastAlertEntry.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					CommerceMLForecastAlertEntry::getCompanyId));

		_finderPathFetchByC_C_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			},
			new String[] {"companyId", "commerceAccountId", "timestamp"}, true);

		_uniquePersistenceFinderByC_C_T = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_C_T,
			_SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE,
			new FinderColumn<>(
				"commerceMLForecastAlertEntry.", "companyId",
				FinderColumn.Type.LONG, "=", true, false,
				CommerceMLForecastAlertEntry::getCompanyId),
			new FinderColumn<>(
				"commerceMLForecastAlertEntry.", "commerceAccountId",
				FinderColumn.Type.LONG, "=", true, false,
				CommerceMLForecastAlertEntry::getCommerceAccountId),
			new FinderColumn<>(
				"commerceMLForecastAlertEntry.", "timestamp",
				FinderColumn.Type.DATE, "=", true, true,
				CommerceMLForecastAlertEntry::getTimestamp));

		_finderPathWithPaginationFindByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "commerceAccountId", "status"}, true);

		_finderPathWithoutPaginationFindByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "commerceAccountId", "status"}, true);

		_finderPathCountByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "commerceAccountId", "status"}, false);

		_finderPathWithPaginationCountByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "commerceAccountId", "status"}, false);

		_finderPathWithPaginationFindByC_C_GtRc_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_GtRc_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "commerceAccountId", "relativeChange", "status"
			},
			true);

		_finderPathWithPaginationCountByC_C_GtRc_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_GtRc_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName(), Integer.class.getName()
			},
			new String[] {
				"companyId", "commerceAccountId", "relativeChange", "status"
			},
			false);

		_finderPathWithPaginationFindByC_C_LtRc_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_LtRc_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "commerceAccountId", "relativeChange", "status"
			},
			true);

		_finderPathWithPaginationCountByC_C_LtRc_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_LtRc_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName(), Integer.class.getName()
			},
			new String[] {
				"companyId", "commerceAccountId", "relativeChange", "status"
			},
			false);

		CommerceMLForecastAlertEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceMLForecastAlertEntryUtil.setPersistence(null);

		entityCache.removeCache(
			CommerceMLForecastAlertEntryImpl.class.getName());
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

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_COMMERCEMLFORECASTALERTENTRY =
		"SELECT commerceMLForecastAlertEntry FROM CommerceMLForecastAlertEntry commerceMLForecastAlertEntry";

	private static final String _SQL_SELECT_COMMERCEMLFORECASTALERTENTRY_WHERE =
		"SELECT commerceMLForecastAlertEntry FROM CommerceMLForecastAlertEntry commerceMLForecastAlertEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCEMLFORECASTALERTENTRY =
		"SELECT COUNT(commerceMLForecastAlertEntry) FROM CommerceMLForecastAlertEntry commerceMLForecastAlertEntry";

	private static final String _SQL_COUNT_COMMERCEMLFORECASTALERTENTRY_WHERE =
		"SELECT COUNT(commerceMLForecastAlertEntry) FROM CommerceMLForecastAlertEntry commerceMLForecastAlertEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceMLForecastAlertEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceMLForecastAlertEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMLForecastAlertEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:752753741