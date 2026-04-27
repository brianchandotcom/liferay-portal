/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchPortletPreferenceValueException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.model.PortletPreferenceValueTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.PortletPreferenceValuePersistence;
import com.liferay.portal.kernel.service.persistence.PortletPreferenceValueUtil;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
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
import com.liferay.portal.model.impl.PortletPreferenceValueImpl;
import com.liferay.portal.model.impl.PortletPreferenceValueModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the portlet preference value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortletPreferenceValuePersistenceImpl
	extends BasePersistenceImpl<PortletPreferenceValue>
	implements PortletPreferenceValuePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PortletPreferenceValueUtil</code> to access the portlet preference value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PortletPreferenceValueImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByPortletPreferencesId;
	private FinderPath _finderPathWithoutPaginationFindByPortletPreferencesId;
	private FinderPath _finderPathCountByPortletPreferencesId;
	private CollectionPersistenceFinder<PortletPreferenceValue>
		_collectionPersistenceFinderByPortletPreferencesId;

	/**
	 * Returns all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @return the matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId) {

		return findByPortletPreferencesId(
			portletPreferencesId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId, int start, int end) {

		return findByPortletPreferencesId(
			portletPreferencesId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return findByPortletPreferencesId(
			portletPreferencesId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					PortletPreferenceValue.class)) {

			return _collectionPersistenceFinderByPortletPreferencesId.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {portletPreferencesId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByPortletPreferencesId_First(
			long portletPreferencesId,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue =
			fetchByPortletPreferencesId_First(
				portletPreferencesId, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		throw new NoSuchPortletPreferenceValueException(
			_collectionPersistenceFinderByPortletPreferencesId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {portletPreferencesId}));
	}

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByPortletPreferencesId_First(
		long portletPreferencesId,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return _collectionPersistenceFinderByPortletPreferencesId.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {portletPreferencesId}, orderByComparator);
	}

	/**
	 * Removes all the portlet preference values where portletPreferencesId = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 */
	@Override
	public void removeByPortletPreferencesId(long portletPreferencesId) {
		_collectionPersistenceFinderByPortletPreferencesId.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {portletPreferencesId});
	}

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @return the number of matching portlet preference values
	 */
	@Override
	public int countByPortletPreferencesId(long portletPreferencesId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					PortletPreferenceValue.class)) {

			return _collectionPersistenceFinderByPortletPreferencesId.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {portletPreferencesId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByP_N;
	private FinderPath _finderPathWithoutPaginationFindByP_N;
	private FinderPath _finderPathCountByP_N;
	private CollectionPersistenceFinder<PortletPreferenceValue>
		_collectionPersistenceFinderByP_N;

	/**
	 * Returns all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @return the matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name) {

		return findByP_N(
			portletPreferencesId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name, int start, int end) {

		return findByP_N(portletPreferencesId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return findByP_N(
			portletPreferencesId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					PortletPreferenceValue.class)) {

			return _collectionPersistenceFinderByP_N.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {portletPreferencesId, name}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByP_N_First(
			long portletPreferencesId, String name,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByP_N_First(
			portletPreferencesId, name, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		throw new NoSuchPortletPreferenceValueException(
			_collectionPersistenceFinderByP_N.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {portletPreferencesId, name}));
	}

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByP_N_First(
		long portletPreferencesId, String name,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return _collectionPersistenceFinderByP_N.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {portletPreferencesId, name}, orderByComparator);
	}

	/**
	 * Removes all the portlet preference values where portletPreferencesId = &#63; and name = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 */
	@Override
	public void removeByP_N(long portletPreferencesId, String name) {
		_collectionPersistenceFinderByP_N.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {portletPreferencesId, name});
	}

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @return the number of matching portlet preference values
	 */
	@Override
	public int countByP_N(long portletPreferencesId, String name) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					PortletPreferenceValue.class)) {

			return _collectionPersistenceFinderByP_N.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {portletPreferencesId, name});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_N_SV;
	private FinderPath _finderPathWithoutPaginationFindByC_N_SV;
	private FinderPath _finderPathCountByC_N_SV;
	private CollectionPersistenceFinder<PortletPreferenceValue>
		_collectionPersistenceFinderByC_N_SV;

	/**
	 * Returns all the portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @return the matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByC_N_SV(
		long companyId, String name, String smallValue) {

		return findByC_N_SV(
			companyId, name, smallValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByC_N_SV(
		long companyId, String name, String smallValue, int start, int end) {

		return findByC_N_SV(companyId, name, smallValue, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByC_N_SV(
		long companyId, String name, String smallValue, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return findByC_N_SV(
			companyId, name, smallValue, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByC_N_SV(
		long companyId, String name, String smallValue, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					PortletPreferenceValue.class)) {

			return _collectionPersistenceFinderByC_N_SV.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, name, smallValue}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first portlet preference value in the ordered set where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByC_N_SV_First(
			long companyId, String name, String smallValue,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByC_N_SV_First(
			companyId, name, smallValue, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		throw new NoSuchPortletPreferenceValueException(
			_collectionPersistenceFinderByC_N_SV.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, name, smallValue}));
	}

	/**
	 * Returns the first portlet preference value in the ordered set where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByC_N_SV_First(
		long companyId, String name, String smallValue,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return _collectionPersistenceFinderByC_N_SV.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, name, smallValue}, orderByComparator);
	}

	/**
	 * Removes all the portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 */
	@Override
	public void removeByC_N_SV(long companyId, String name, String smallValue) {
		_collectionPersistenceFinderByC_N_SV.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {companyId, name, smallValue});
	}

	/**
	 * Returns the number of portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @return the number of matching portlet preference values
	 */
	@Override
	public int countByC_N_SV(long companyId, String name, String smallValue) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					PortletPreferenceValue.class)) {

			return _collectionPersistenceFinderByC_N_SV.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {companyId, name, smallValue});
		}
	}

	private FinderPath _finderPathFetchByP_I_N;
	private UniquePersistenceFinder<PortletPreferenceValue>
		_uniquePersistenceFinderByP_I_N;

	/**
	 * Returns the portlet preference value where portletPreferencesId = &#63; and index = &#63; and name = &#63; or throws a <code>NoSuchPortletPreferenceValueException</code> if it could not be found.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param index the index
	 * @param name the name
	 * @return the matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByP_I_N(
			long portletPreferencesId, int index, String name)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByP_I_N(
			portletPreferencesId, index, name);

		if (portletPreferenceValue == null) {
			String message =
				_uniquePersistenceFinderByP_I_N.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {portletPreferencesId, index, name});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchPortletPreferenceValueException(message);
		}

		return portletPreferenceValue;
	}

	/**
	 * Returns the portlet preference value where portletPreferencesId = &#63; and index = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param index the index
	 * @param name the name
	 * @return the matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByP_I_N(
		long portletPreferencesId, int index, String name) {

		return fetchByP_I_N(portletPreferencesId, index, name, true);
	}

	/**
	 * Returns the portlet preference value where portletPreferencesId = &#63; and index = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param index the index
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByP_I_N(
		long portletPreferencesId, int index, String name,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					PortletPreferenceValue.class)) {

			return _uniquePersistenceFinderByP_I_N.fetch(
				FinderCacheUtil.getFinderCache(),
				new Object[] {portletPreferencesId, index, name},
				useFinderCache);
		}
	}

	/**
	 * Removes the portlet preference value where portletPreferencesId = &#63; and index = &#63; and name = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param index the index
	 * @param name the name
	 * @return the portlet preference value that was removed
	 */
	@Override
	public PortletPreferenceValue removeByP_I_N(
			long portletPreferencesId, int index, String name)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = findByP_I_N(
			portletPreferencesId, index, name);

		return remove(portletPreferenceValue);
	}

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63; and index = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param index the index
	 * @param name the name
	 * @return the number of matching portlet preference values
	 */
	@Override
	public int countByP_I_N(long portletPreferencesId, int index, String name) {
		return _uniquePersistenceFinderByP_I_N.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {portletPreferencesId, index, name});
	}

	private FinderPath _finderPathWithPaginationFindByP_N_SV;
	private FinderPath _finderPathWithoutPaginationFindByP_N_SV;
	private FinderPath _finderPathCountByP_N_SV;
	private CollectionPersistenceFinder<PortletPreferenceValue>
		_collectionPersistenceFinderByP_N_SV;

	/**
	 * Returns all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @return the matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue) {

		return findByP_N_SV(
			portletPreferencesId, name, smallValue, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue, int start,
		int end) {

		return findByP_N_SV(
			portletPreferencesId, name, smallValue, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue, int start,
		int end, OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return findByP_N_SV(
			portletPreferencesId, name, smallValue, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue, int start,
		int end, OrderByComparator<PortletPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					PortletPreferenceValue.class)) {

			return _collectionPersistenceFinderByP_N_SV.find(
				FinderCacheUtil.getFinderCache(),
				new Object[] {portletPreferencesId, name, smallValue}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByP_N_SV_First(
			long portletPreferencesId, String name, String smallValue,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByP_N_SV_First(
			portletPreferencesId, name, smallValue, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		throw new NoSuchPortletPreferenceValueException(
			_collectionPersistenceFinderByP_N_SV.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {portletPreferencesId, name, smallValue}));
	}

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByP_N_SV_First(
		long portletPreferencesId, String name, String smallValue,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return _collectionPersistenceFinderByP_N_SV.fetchFirst(
			FinderCacheUtil.getFinderCache(),
			new Object[] {portletPreferencesId, name, smallValue},
			orderByComparator);
	}

	/**
	 * Removes all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 */
	@Override
	public void removeByP_N_SV(
		long portletPreferencesId, String name, String smallValue) {

		_collectionPersistenceFinderByP_N_SV.remove(
			FinderCacheUtil.getFinderCache(),
			new Object[] {portletPreferencesId, name, smallValue});
	}

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @return the number of matching portlet preference values
	 */
	@Override
	public int countByP_N_SV(
		long portletPreferencesId, String name, String smallValue) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					PortletPreferenceValue.class)) {

			return _collectionPersistenceFinderByP_N_SV.count(
				FinderCacheUtil.getFinderCache(),
				new Object[] {portletPreferencesId, name, smallValue});
		}
	}

	public PortletPreferenceValuePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("index", "index_");

		setDBColumnNames(dbColumnNames);

		setModelClass(PortletPreferenceValue.class);

		setModelImplClass(PortletPreferenceValueImpl.class);
		setModelPKClass(long.class);

		setTable(PortletPreferenceValueTable.INSTANCE);
	}

	/**
	 * Caches the portlet preference value in the entity cache if it is enabled.
	 *
	 * @param portletPreferenceValue the portlet preference value
	 */
	@Override
	public void cacheResult(PortletPreferenceValue portletPreferenceValue) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					portletPreferenceValue.getCtCollectionId())) {

			EntityCacheUtil.putResult(
				PortletPreferenceValueImpl.class,
				portletPreferenceValue.getPrimaryKey(), portletPreferenceValue);

			FinderCacheUtil.putResult(
				_finderPathFetchByP_I_N,
				new Object[] {
					portletPreferenceValue.getPortletPreferencesId(),
					portletPreferenceValue.getIndex(),
					portletPreferenceValue.getName()
				},
				portletPreferenceValue);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the portlet preference values in the entity cache if it is enabled.
	 *
	 * @param portletPreferenceValues the portlet preference values
	 */
	@Override
	public void cacheResult(
		List<PortletPreferenceValue> portletPreferenceValues) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (portletPreferenceValues.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (PortletPreferenceValue portletPreferenceValue :
				portletPreferenceValues) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						portletPreferenceValue.getCtCollectionId())) {

				if (EntityCacheUtil.getResult(
						PortletPreferenceValueImpl.class,
						portletPreferenceValue.getPrimaryKey()) == null) {

					cacheResult(portletPreferenceValue);
				}
			}
		}
	}

	/**
	 * Clears the cache for all portlet preference values.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(PortletPreferenceValueImpl.class);

		FinderCacheUtil.clearCache(PortletPreferenceValueImpl.class);
	}

	/**
	 * Clears the cache for the portlet preference value.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PortletPreferenceValue portletPreferenceValue) {
		EntityCacheUtil.removeResult(
			PortletPreferenceValueImpl.class, portletPreferenceValue);
	}

	@Override
	public void clearCache(
		List<PortletPreferenceValue> portletPreferenceValues) {

		for (PortletPreferenceValue portletPreferenceValue :
				portletPreferenceValues) {

			EntityCacheUtil.removeResult(
				PortletPreferenceValueImpl.class, portletPreferenceValue);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(PortletPreferenceValueImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				PortletPreferenceValueImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PortletPreferenceValueModelImpl portletPreferenceValueModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					portletPreferenceValueModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				portletPreferenceValueModelImpl.getPortletPreferencesId(),
				portletPreferenceValueModelImpl.getIndex(),
				portletPreferenceValueModelImpl.getName()
			};

			FinderCacheUtil.putResult(
				_finderPathFetchByP_I_N, args, portletPreferenceValueModelImpl);
		}
	}

	/**
	 * Creates a new portlet preference value with the primary key. Does not add the portlet preference value to the database.
	 *
	 * @param portletPreferenceValueId the primary key for the new portlet preference value
	 * @return the new portlet preference value
	 */
	@Override
	public PortletPreferenceValue create(long portletPreferenceValueId) {
		PortletPreferenceValue portletPreferenceValue =
			new PortletPreferenceValueImpl();

		portletPreferenceValue.setNew(true);
		portletPreferenceValue.setPrimaryKey(portletPreferenceValueId);

		portletPreferenceValue.setCompanyId(CompanyThreadLocal.getCompanyId());

		return portletPreferenceValue;
	}

	/**
	 * Removes the portlet preference value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portletPreferenceValueId the primary key of the portlet preference value
	 * @return the portlet preference value that was removed
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue remove(long portletPreferenceValueId)
		throws NoSuchPortletPreferenceValueException {

		return remove((Serializable)portletPreferenceValueId);
	}

	/**
	 * Removes the portlet preference value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portlet preference value
	 * @return the portlet preference value that was removed
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue remove(Serializable primaryKey)
		throws NoSuchPortletPreferenceValueException {

		Session session = null;

		try {
			session = openSession();

			PortletPreferenceValue portletPreferenceValue =
				(PortletPreferenceValue)session.get(
					PortletPreferenceValueImpl.class, primaryKey);

			if (portletPreferenceValue == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPortletPreferenceValueException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(portletPreferenceValue);
		}
		catch (NoSuchPortletPreferenceValueException noSuchEntityException) {
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
	protected PortletPreferenceValue removeImpl(
		PortletPreferenceValue portletPreferenceValue) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portletPreferenceValue)) {
				portletPreferenceValue = (PortletPreferenceValue)session.get(
					PortletPreferenceValueImpl.class,
					portletPreferenceValue.getPrimaryKeyObj());
			}

			if ((portletPreferenceValue != null) &&
				CTPersistenceHelperUtil.isRemove(portletPreferenceValue)) {

				session.delete(portletPreferenceValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (portletPreferenceValue != null) {
			clearCache(portletPreferenceValue);
		}

		return portletPreferenceValue;
	}

	@Override
	public PortletPreferenceValue updateImpl(
		PortletPreferenceValue portletPreferenceValue) {

		boolean isNew = portletPreferenceValue.isNew();

		if (!(portletPreferenceValue instanceof
				PortletPreferenceValueModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(portletPreferenceValue.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					portletPreferenceValue);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in portletPreferenceValue proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PortletPreferenceValue implementation " +
					portletPreferenceValue.getClass());
		}

		PortletPreferenceValueModelImpl portletPreferenceValueModelImpl =
			(PortletPreferenceValueModelImpl)portletPreferenceValue;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(portletPreferenceValue)) {
				if (!isNew) {
					session.evict(
						PortletPreferenceValueImpl.class,
						portletPreferenceValue.getPrimaryKeyObj());
				}

				session.save(portletPreferenceValue);
			}
			else {
				portletPreferenceValue = (PortletPreferenceValue)session.merge(
					portletPreferenceValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			PortletPreferenceValueImpl.class, portletPreferenceValueModelImpl,
			false, true);

		cacheUniqueFindersCache(portletPreferenceValueModelImpl);

		if (isNew) {
			portletPreferenceValue.setNew(false);
		}

		portletPreferenceValue.resetOriginalValues();

		return portletPreferenceValue;
	}

	/**
	 * Returns the portlet preference value with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet preference value
	 * @return the portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByPrimaryKey(
			primaryKey);

		if (portletPreferenceValue == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPortletPreferenceValueException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return portletPreferenceValue;
	}

	/**
	 * Returns the portlet preference value with the primary key or throws a <code>NoSuchPortletPreferenceValueException</code> if it could not be found.
	 *
	 * @param portletPreferenceValueId the primary key of the portlet preference value
	 * @return the portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue findByPrimaryKey(
			long portletPreferenceValueId)
		throws NoSuchPortletPreferenceValueException {

		return findByPrimaryKey((Serializable)portletPreferenceValueId);
	}

	/**
	 * Returns the portlet preference value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet preference value
	 * @return the portlet preference value, or <code>null</code> if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(
				PortletPreferenceValue.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		PortletPreferenceValue portletPreferenceValue =
			(PortletPreferenceValue)EntityCacheUtil.getResult(
				PortletPreferenceValueImpl.class, primaryKey);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		Session session = null;

		try {
			session = openSession();

			portletPreferenceValue = (PortletPreferenceValue)session.get(
				PortletPreferenceValueImpl.class, primaryKey);

			if (portletPreferenceValue != null) {
				cacheResult(portletPreferenceValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return portletPreferenceValue;
	}

	/**
	 * Returns the portlet preference value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portletPreferenceValueId the primary key of the portlet preference value
	 * @return the portlet preference value, or <code>null</code> if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByPrimaryKey(
		long portletPreferenceValueId) {

		return fetchByPrimaryKey((Serializable)portletPreferenceValueId);
	}

	@Override
	public Map<Serializable, PortletPreferenceValue> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(
				PortletPreferenceValue.class)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PortletPreferenceValue> map =
			new HashMap<Serializable, PortletPreferenceValue>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PortletPreferenceValue portletPreferenceValue = fetchByPrimaryKey(
				primaryKey);

			if (portletPreferenceValue != null) {
				map.put(primaryKey, portletPreferenceValue);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
						PortletPreferenceValue.class, primaryKey)) {

				PortletPreferenceValue portletPreferenceValue =
					(PortletPreferenceValue)EntityCacheUtil.getResult(
						PortletPreferenceValueImpl.class, primaryKey);

				if (portletPreferenceValue == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, portletPreferenceValue);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (PortletPreferenceValue portletPreferenceValue :
					(List<PortletPreferenceValue>)query.list()) {

				map.put(
					portletPreferenceValue.getPrimaryKeyObj(),
					portletPreferenceValue);

				cacheResult(portletPreferenceValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the portlet preference values.
	 *
	 * @return the portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preference values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preference values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findAll(
		int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlet preference values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findAll(
		int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					PortletPreferenceValue.class)) {

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

			List<PortletPreferenceValue> list = null;

			if (useFinderCache) {
				list = (List<PortletPreferenceValue>)FinderCacheUtil.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_PORTLETPREFERENCEVALUE);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_PORTLETPREFERENCEVALUE;

					sql = sql.concat(
						PortletPreferenceValueModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<PortletPreferenceValue>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the portlet preference values from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PortletPreferenceValue portletPreferenceValue : findAll()) {
			remove(portletPreferenceValue);
		}
	}

	/**
	 * Returns the number of portlet preference values.
	 *
	 * @return the number of portlet preference values
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					PortletPreferenceValue.class)) {

			Long count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_PORTLETPREFERENCEVALUE);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "portletPreferenceValueId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PORTLETPREFERENCEVALUE;
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
		return PortletPreferenceValueModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "PortletPreferenceValue";
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
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctMergeColumnNames.add("portletPreferencesId");
		ctMergeColumnNames.add("index_");
		ctMergeColumnNames.add("largeValue");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("readOnly");
		ctMergeColumnNames.add("smallValue");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("portletPreferenceValueId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"portletPreferencesId", "index_", "name"});
	}

	/**
	 * Initializes the portlet preference value persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathWithPaginationFindByPortletPreferencesId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPortletPreferencesId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"portletPreferencesId"}, true);

		_finderPathWithoutPaginationFindByPortletPreferencesId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPortletPreferencesId", new String[] {Long.class.getName()},
			new String[] {"portletPreferencesId"}, true);

		_finderPathCountByPortletPreferencesId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPortletPreferencesId", new String[] {Long.class.getName()},
			new String[] {"portletPreferencesId"}, false);

		_collectionPersistenceFinderByPortletPreferencesId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByPortletPreferencesId,
				_finderPathWithoutPaginationFindByPortletPreferencesId,
				_finderPathCountByPortletPreferencesId,
				_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE,
				_SQL_COUNT_PORTLETPREFERENCEVALUE_WHERE,
				PortletPreferenceValueModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"portletPreferenceValue.", "portletPreferencesId",
					FinderColumn.Type.LONG, "=", true, true,
					PortletPreferenceValue::getPortletPreferencesId));

		_finderPathWithPaginationFindByP_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"portletPreferencesId", "name"}, true);

		_finderPathWithoutPaginationFindByP_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"portletPreferencesId", "name"}, true);

		_finderPathCountByP_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"portletPreferencesId", "name"}, false);

		_collectionPersistenceFinderByP_N = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByP_N,
			_finderPathWithoutPaginationFindByP_N, _finderPathCountByP_N,
			_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE,
			_SQL_COUNT_PORTLETPREFERENCEVALUE_WHERE,
			PortletPreferenceValueModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"portletPreferenceValue.", "portletPreferencesId",
				FinderColumn.Type.LONG, "=", true, false,
				PortletPreferenceValue::getPortletPreferencesId),
			new FinderColumn<>(
				"portletPreferenceValue.", "name", FinderColumn.Type.STRING,
				"=", true, true, PortletPreferenceValue::getName));

		_finderPathWithPaginationFindByC_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "name", "smallValue"}, true);

		_finderPathWithoutPaginationFindByC_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "name", "smallValue"}, true);

		_finderPathCountByC_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "name", "smallValue"}, false);

		_collectionPersistenceFinderByC_N_SV =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_N_SV,
				_finderPathWithoutPaginationFindByC_N_SV,
				_finderPathCountByC_N_SV,
				_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE,
				_SQL_COUNT_PORTLETPREFERENCEVALUE_WHERE,
				PortletPreferenceValueModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"portletPreferenceValue.", "companyId",
					FinderColumn.Type.LONG, "=", true, false,
					PortletPreferenceValue::getCompanyId),
				new FinderColumn<>(
					"portletPreferenceValue.", "name", FinderColumn.Type.STRING,
					"=", true, false, PortletPreferenceValue::getName),
				new FinderColumn<>(
					"portletPreferenceValue.", "smallValue",
					FinderColumn.Type.STRING, "=", true, true,
					PortletPreferenceValue::getSmallValue));

		_finderPathFetchByP_I_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByP_I_N",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			new String[] {"portletPreferencesId", "index_", "name"}, true);

		_uniquePersistenceFinderByP_I_N = new UniquePersistenceFinder<>(
			this, _finderPathFetchByP_I_N,
			_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE,
			new FinderColumn<>(
				"portletPreferenceValue.", "portletPreferencesId",
				FinderColumn.Type.LONG, "=", true, false,
				PortletPreferenceValue::getPortletPreferencesId),
			new FinderColumn<>(
				"portletPreferenceValue.", "index", FinderColumn.Type.INTEGER,
				"=", true, false, PortletPreferenceValue::getIndex),
			new FinderColumn<>(
				"portletPreferenceValue.", "name", FinderColumn.Type.STRING,
				"=", true, true, PortletPreferenceValue::getName));

		_finderPathWithPaginationFindByP_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"portletPreferencesId", "name", "smallValue"}, true);

		_finderPathWithoutPaginationFindByP_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"portletPreferencesId", "name", "smallValue"}, true);

		_finderPathCountByP_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"portletPreferencesId", "name", "smallValue"}, false);

		_collectionPersistenceFinderByP_N_SV =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByP_N_SV,
				_finderPathWithoutPaginationFindByP_N_SV,
				_finderPathCountByP_N_SV,
				_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE,
				_SQL_COUNT_PORTLETPREFERENCEVALUE_WHERE,
				PortletPreferenceValueModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"portletPreferenceValue.", "portletPreferencesId",
					FinderColumn.Type.LONG, "=", true, false,
					PortletPreferenceValue::getPortletPreferencesId),
				new FinderColumn<>(
					"portletPreferenceValue.", "name", FinderColumn.Type.STRING,
					"=", true, false, PortletPreferenceValue::getName),
				new FinderColumn<>(
					"portletPreferenceValue.", "smallValue",
					FinderColumn.Type.STRING, "=", true, true,
					PortletPreferenceValue::getSmallValue));

		PortletPreferenceValueUtil.setPersistence(this);
	}

	public void destroy() {
		PortletPreferenceValueUtil.setPersistence(null);

		EntityCacheUtil.removeCache(PortletPreferenceValueImpl.class.getName());
	}

	private static final String _SQL_SELECT_PORTLETPREFERENCEVALUE =
		"SELECT portletPreferenceValue FROM PortletPreferenceValue portletPreferenceValue";

	private static final String _SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE =
		"SELECT portletPreferenceValue FROM PortletPreferenceValue portletPreferenceValue WHERE ";

	private static final String _SQL_COUNT_PORTLETPREFERENCEVALUE =
		"SELECT COUNT(portletPreferenceValue) FROM PortletPreferenceValue portletPreferenceValue";

	private static final String _SQL_COUNT_PORTLETPREFERENCEVALUE_WHERE =
		"SELECT COUNT(portletPreferenceValue) FROM PortletPreferenceValue portletPreferenceValue WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"portletPreferenceValue.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PortletPreferenceValue exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PortletPreferenceValue exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferenceValuePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"index"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-928230160