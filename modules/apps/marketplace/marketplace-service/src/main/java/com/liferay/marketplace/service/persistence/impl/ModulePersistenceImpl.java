/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service.persistence.impl;

import com.liferay.marketplace.exception.NoSuchModuleException;
import com.liferay.marketplace.model.Module;
import com.liferay.marketplace.model.ModuleTable;
import com.liferay.marketplace.model.impl.ModuleImpl;
import com.liferay.marketplace.model.impl.ModuleModelImpl;
import com.liferay.marketplace.service.persistence.ModulePersistence;
import com.liferay.marketplace.service.persistence.ModuleUtil;
import com.liferay.marketplace.service.persistence.impl.constants.MarketplacePersistenceConstants;
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
 * The persistence implementation for the module service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ryan Park
 * @generated
 */
@Component(service = ModulePersistence.class)
public class ModulePersistenceImpl
	extends BasePersistenceImpl<Module> implements ModulePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ModuleUtil</code> to access the module persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ModuleImpl.class.getName();

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
	private CollectionPersistenceFinder<Module>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the modules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching modules
	 */
	@Override
	public List<Module> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the modules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @return the range of matching modules
	 */
	@Override
	public List<Module> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the modules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Module> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the modules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Module> orderByComparator, boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first module in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module
	 * @throws NoSuchModuleException if a matching module could not be found
	 */
	@Override
	public Module findByUuid_First(
			String uuid, OrderByComparator<Module> orderByComparator)
		throws NoSuchModuleException {

		Module module = fetchByUuid_First(uuid, orderByComparator);

		if (module != null) {
			return module;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchModuleException(sb.toString());
	}

	/**
	 * Returns the first module in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module, or <code>null</code> if a matching module could not be found
	 */
	@Override
	public Module fetchByUuid_First(
		String uuid, OrderByComparator<Module> orderByComparator) {

		List<Module> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the modules where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (Module module :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(module);
		}
	}

	/**
	 * Returns the number of modules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching modules
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<Module>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the modules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching modules
	 */
	@Override
	public List<Module> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the modules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @return the range of matching modules
	 */
	@Override
	public List<Module> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the modules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Module> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the modules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Module> orderByComparator, boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first module in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module
	 * @throws NoSuchModuleException if a matching module could not be found
	 */
	@Override
	public Module findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<Module> orderByComparator)
		throws NoSuchModuleException {

		Module module = fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (module != null) {
			return module;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchModuleException(sb.toString());
	}

	/**
	 * Returns the first module in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module, or <code>null</code> if a matching module could not be found
	 */
	@Override
	public Module fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<Module> orderByComparator) {

		List<Module> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the modules where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (Module module :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(module);
		}
	}

	/**
	 * Returns the number of modules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching modules
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private FinderPath _finderPathWithPaginationFindByAppId;
	private FinderPath _finderPathWithoutPaginationFindByAppId;
	private FinderPath _finderPathCountByAppId;
	private CollectionPersistenceFinder<Module>
		_collectionPersistenceFinderByAppId;

	/**
	 * Returns all the modules where appId = &#63;.
	 *
	 * @param appId the app ID
	 * @return the matching modules
	 */
	@Override
	public List<Module> findByAppId(long appId) {
		return findByAppId(appId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the modules where appId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param appId the app ID
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @return the range of matching modules
	 */
	@Override
	public List<Module> findByAppId(long appId, int start, int end) {
		return findByAppId(appId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the modules where appId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param appId the app ID
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByAppId(
		long appId, int start, int end,
		OrderByComparator<Module> orderByComparator) {

		return findByAppId(appId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the modules where appId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param appId the app ID
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByAppId(
		long appId, int start, int end,
		OrderByComparator<Module> orderByComparator, boolean useFinderCache) {

		return _collectionPersistenceFinderByAppId.find(
			finderCache, new Object[] {appId}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first module in the ordered set where appId = &#63;.
	 *
	 * @param appId the app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module
	 * @throws NoSuchModuleException if a matching module could not be found
	 */
	@Override
	public Module findByAppId_First(
			long appId, OrderByComparator<Module> orderByComparator)
		throws NoSuchModuleException {

		Module module = fetchByAppId_First(appId, orderByComparator);

		if (module != null) {
			return module;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appId=");
		sb.append(appId);

		sb.append("}");

		throw new NoSuchModuleException(sb.toString());
	}

	/**
	 * Returns the first module in the ordered set where appId = &#63;.
	 *
	 * @param appId the app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module, or <code>null</code> if a matching module could not be found
	 */
	@Override
	public Module fetchByAppId_First(
		long appId, OrderByComparator<Module> orderByComparator) {

		List<Module> list = findByAppId(appId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the modules where appId = &#63; from the database.
	 *
	 * @param appId the app ID
	 */
	@Override
	public void removeByAppId(long appId) {
		for (Module module :
				findByAppId(
					appId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(module);
		}
	}

	/**
	 * Returns the number of modules where appId = &#63;.
	 *
	 * @param appId the app ID
	 * @return the number of matching modules
	 */
	@Override
	public int countByAppId(long appId) {
		return _collectionPersistenceFinderByAppId.count(
			finderCache, new Object[] {appId});
	}

	private FinderPath _finderPathWithPaginationFindByBundleSymbolicName;
	private FinderPath _finderPathWithoutPaginationFindByBundleSymbolicName;
	private FinderPath _finderPathCountByBundleSymbolicName;
	private CollectionPersistenceFinder<Module>
		_collectionPersistenceFinderByBundleSymbolicName;

	/**
	 * Returns all the modules where bundleSymbolicName = &#63;.
	 *
	 * @param bundleSymbolicName the bundle symbolic name
	 * @return the matching modules
	 */
	@Override
	public List<Module> findByBundleSymbolicName(String bundleSymbolicName) {
		return findByBundleSymbolicName(
			bundleSymbolicName, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the modules where bundleSymbolicName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @return the range of matching modules
	 */
	@Override
	public List<Module> findByBundleSymbolicName(
		String bundleSymbolicName, int start, int end) {

		return findByBundleSymbolicName(bundleSymbolicName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the modules where bundleSymbolicName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByBundleSymbolicName(
		String bundleSymbolicName, int start, int end,
		OrderByComparator<Module> orderByComparator) {

		return findByBundleSymbolicName(
			bundleSymbolicName, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the modules where bundleSymbolicName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByBundleSymbolicName(
		String bundleSymbolicName, int start, int end,
		OrderByComparator<Module> orderByComparator, boolean useFinderCache) {

		return _collectionPersistenceFinderByBundleSymbolicName.find(
			finderCache, new Object[] {bundleSymbolicName}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first module in the ordered set where bundleSymbolicName = &#63;.
	 *
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module
	 * @throws NoSuchModuleException if a matching module could not be found
	 */
	@Override
	public Module findByBundleSymbolicName_First(
			String bundleSymbolicName,
			OrderByComparator<Module> orderByComparator)
		throws NoSuchModuleException {

		Module module = fetchByBundleSymbolicName_First(
			bundleSymbolicName, orderByComparator);

		if (module != null) {
			return module;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("bundleSymbolicName=");
		sb.append(bundleSymbolicName);

		sb.append("}");

		throw new NoSuchModuleException(sb.toString());
	}

	/**
	 * Returns the first module in the ordered set where bundleSymbolicName = &#63;.
	 *
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module, or <code>null</code> if a matching module could not be found
	 */
	@Override
	public Module fetchByBundleSymbolicName_First(
		String bundleSymbolicName,
		OrderByComparator<Module> orderByComparator) {

		List<Module> list = findByBundleSymbolicName(
			bundleSymbolicName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the modules where bundleSymbolicName = &#63; from the database.
	 *
	 * @param bundleSymbolicName the bundle symbolic name
	 */
	@Override
	public void removeByBundleSymbolicName(String bundleSymbolicName) {
		for (Module module :
				findByBundleSymbolicName(
					bundleSymbolicName, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(module);
		}
	}

	/**
	 * Returns the number of modules where bundleSymbolicName = &#63;.
	 *
	 * @param bundleSymbolicName the bundle symbolic name
	 * @return the number of matching modules
	 */
	@Override
	public int countByBundleSymbolicName(String bundleSymbolicName) {
		return _collectionPersistenceFinderByBundleSymbolicName.count(
			finderCache, new Object[] {bundleSymbolicName});
	}

	private FinderPath _finderPathWithPaginationFindByContextName;
	private FinderPath _finderPathWithoutPaginationFindByContextName;
	private FinderPath _finderPathCountByContextName;
	private CollectionPersistenceFinder<Module>
		_collectionPersistenceFinderByContextName;

	/**
	 * Returns all the modules where contextName = &#63;.
	 *
	 * @param contextName the context name
	 * @return the matching modules
	 */
	@Override
	public List<Module> findByContextName(String contextName) {
		return findByContextName(
			contextName, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the modules where contextName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param contextName the context name
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @return the range of matching modules
	 */
	@Override
	public List<Module> findByContextName(
		String contextName, int start, int end) {

		return findByContextName(contextName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the modules where contextName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param contextName the context name
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByContextName(
		String contextName, int start, int end,
		OrderByComparator<Module> orderByComparator) {

		return findByContextName(
			contextName, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the modules where contextName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param contextName the context name
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByContextName(
		String contextName, int start, int end,
		OrderByComparator<Module> orderByComparator, boolean useFinderCache) {

		return _collectionPersistenceFinderByContextName.find(
			finderCache, new Object[] {contextName}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first module in the ordered set where contextName = &#63;.
	 *
	 * @param contextName the context name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module
	 * @throws NoSuchModuleException if a matching module could not be found
	 */
	@Override
	public Module findByContextName_First(
			String contextName, OrderByComparator<Module> orderByComparator)
		throws NoSuchModuleException {

		Module module = fetchByContextName_First(
			contextName, orderByComparator);

		if (module != null) {
			return module;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("contextName=");
		sb.append(contextName);

		sb.append("}");

		throw new NoSuchModuleException(sb.toString());
	}

	/**
	 * Returns the first module in the ordered set where contextName = &#63;.
	 *
	 * @param contextName the context name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module, or <code>null</code> if a matching module could not be found
	 */
	@Override
	public Module fetchByContextName_First(
		String contextName, OrderByComparator<Module> orderByComparator) {

		List<Module> list = findByContextName(
			contextName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the modules where contextName = &#63; from the database.
	 *
	 * @param contextName the context name
	 */
	@Override
	public void removeByContextName(String contextName) {
		for (Module module :
				findByContextName(
					contextName, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(module);
		}
	}

	/**
	 * Returns the number of modules where contextName = &#63;.
	 *
	 * @param contextName the context name
	 * @return the number of matching modules
	 */
	@Override
	public int countByContextName(String contextName) {
		return _collectionPersistenceFinderByContextName.count(
			finderCache, new Object[] {contextName});
	}

	private FinderPath _finderPathWithPaginationFindByA_CN;
	private FinderPath _finderPathWithoutPaginationFindByA_CN;
	private FinderPath _finderPathCountByA_CN;
	private CollectionPersistenceFinder<Module>
		_collectionPersistenceFinderByA_CN;

	/**
	 * Returns all the modules where appId = &#63; and contextName = &#63;.
	 *
	 * @param appId the app ID
	 * @param contextName the context name
	 * @return the matching modules
	 */
	@Override
	public List<Module> findByA_CN(long appId, String contextName) {
		return findByA_CN(
			appId, contextName, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the modules where appId = &#63; and contextName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param appId the app ID
	 * @param contextName the context name
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @return the range of matching modules
	 */
	@Override
	public List<Module> findByA_CN(
		long appId, String contextName, int start, int end) {

		return findByA_CN(appId, contextName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the modules where appId = &#63; and contextName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param appId the app ID
	 * @param contextName the context name
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByA_CN(
		long appId, String contextName, int start, int end,
		OrderByComparator<Module> orderByComparator) {

		return findByA_CN(
			appId, contextName, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the modules where appId = &#63; and contextName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param appId the app ID
	 * @param contextName the context name
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching modules
	 */
	@Override
	public List<Module> findByA_CN(
		long appId, String contextName, int start, int end,
		OrderByComparator<Module> orderByComparator, boolean useFinderCache) {

		return _collectionPersistenceFinderByA_CN.find(
			finderCache, new Object[] {appId, contextName}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first module in the ordered set where appId = &#63; and contextName = &#63;.
	 *
	 * @param appId the app ID
	 * @param contextName the context name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module
	 * @throws NoSuchModuleException if a matching module could not be found
	 */
	@Override
	public Module findByA_CN_First(
			long appId, String contextName,
			OrderByComparator<Module> orderByComparator)
		throws NoSuchModuleException {

		Module module = fetchByA_CN_First(
			appId, contextName, orderByComparator);

		if (module != null) {
			return module;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appId=");
		sb.append(appId);

		sb.append(", contextName=");
		sb.append(contextName);

		sb.append("}");

		throw new NoSuchModuleException(sb.toString());
	}

	/**
	 * Returns the first module in the ordered set where appId = &#63; and contextName = &#63;.
	 *
	 * @param appId the app ID
	 * @param contextName the context name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module, or <code>null</code> if a matching module could not be found
	 */
	@Override
	public Module fetchByA_CN_First(
		long appId, String contextName,
		OrderByComparator<Module> orderByComparator) {

		List<Module> list = findByA_CN(
			appId, contextName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the modules where appId = &#63; and contextName = &#63; from the database.
	 *
	 * @param appId the app ID
	 * @param contextName the context name
	 */
	@Override
	public void removeByA_CN(long appId, String contextName) {
		for (Module module :
				findByA_CN(
					appId, contextName, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(module);
		}
	}

	/**
	 * Returns the number of modules where appId = &#63; and contextName = &#63;.
	 *
	 * @param appId the app ID
	 * @param contextName the context name
	 * @return the number of matching modules
	 */
	@Override
	public int countByA_CN(long appId, String contextName) {
		return _collectionPersistenceFinderByA_CN.count(
			finderCache, new Object[] {appId, contextName});
	}

	private FinderPath _finderPathFetchByA_BSN_BV;
	private UniquePersistenceFinder<Module> _uniquePersistenceFinderByA_BSN_BV;

	/**
	 * Returns the module where appId = &#63; and bundleSymbolicName = &#63; and bundleVersion = &#63; or throws a <code>NoSuchModuleException</code> if it could not be found.
	 *
	 * @param appId the app ID
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param bundleVersion the bundle version
	 * @return the matching module
	 * @throws NoSuchModuleException if a matching module could not be found
	 */
	@Override
	public Module findByA_BSN_BV(
			long appId, String bundleSymbolicName, String bundleVersion)
		throws NoSuchModuleException {

		Module module = fetchByA_BSN_BV(
			appId, bundleSymbolicName, bundleVersion);

		if (module == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("appId=");
			sb.append(appId);

			sb.append(", bundleSymbolicName=");
			sb.append(bundleSymbolicName);

			sb.append(", bundleVersion=");
			sb.append(bundleVersion);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchModuleException(sb.toString());
		}

		return module;
	}

	/**
	 * Returns the module where appId = &#63; and bundleSymbolicName = &#63; and bundleVersion = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param appId the app ID
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param bundleVersion the bundle version
	 * @return the matching module, or <code>null</code> if a matching module could not be found
	 */
	@Override
	public Module fetchByA_BSN_BV(
		long appId, String bundleSymbolicName, String bundleVersion) {

		return fetchByA_BSN_BV(appId, bundleSymbolicName, bundleVersion, true);
	}

	/**
	 * Returns the module where appId = &#63; and bundleSymbolicName = &#63; and bundleVersion = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param appId the app ID
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param bundleVersion the bundle version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching module, or <code>null</code> if a matching module could not be found
	 */
	@Override
	public Module fetchByA_BSN_BV(
		long appId, String bundleSymbolicName, String bundleVersion,
		boolean useFinderCache) {

		return _uniquePersistenceFinderByA_BSN_BV.fetch(
			finderCache,
			new Object[] {appId, bundleSymbolicName, bundleVersion},
			useFinderCache);
	}

	/**
	 * Removes the module where appId = &#63; and bundleSymbolicName = &#63; and bundleVersion = &#63; from the database.
	 *
	 * @param appId the app ID
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param bundleVersion the bundle version
	 * @return the module that was removed
	 */
	@Override
	public Module removeByA_BSN_BV(
			long appId, String bundleSymbolicName, String bundleVersion)
		throws NoSuchModuleException {

		Module module = findByA_BSN_BV(
			appId, bundleSymbolicName, bundleVersion);

		return remove(module);
	}

	/**
	 * Returns the number of modules where appId = &#63; and bundleSymbolicName = &#63; and bundleVersion = &#63;.
	 *
	 * @param appId the app ID
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param bundleVersion the bundle version
	 * @return the number of matching modules
	 */
	@Override
	public int countByA_BSN_BV(
		long appId, String bundleSymbolicName, String bundleVersion) {

		return _uniquePersistenceFinderByA_BSN_BV.count(
			finderCache,
			new Object[] {appId, bundleSymbolicName, bundleVersion});
	}

	public ModulePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Module.class);

		setModelImplClass(ModuleImpl.class);
		setModelPKClass(long.class);

		setTable(ModuleTable.INSTANCE);
	}

	/**
	 * Caches the module in the entity cache if it is enabled.
	 *
	 * @param module the module
	 */
	@Override
	public void cacheResult(Module module) {
		entityCache.putResult(ModuleImpl.class, module.getPrimaryKey(), module);

		finderCache.putResult(
			_finderPathFetchByA_BSN_BV,
			new Object[] {
				module.getAppId(), module.getBundleSymbolicName(),
				module.getBundleVersion()
			},
			module);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the modules in the entity cache if it is enabled.
	 *
	 * @param modules the modules
	 */
	@Override
	public void cacheResult(List<Module> modules) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (modules.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Module module : modules) {
			if (entityCache.getResult(
					ModuleImpl.class, module.getPrimaryKey()) == null) {

				cacheResult(module);
			}
		}
	}

	/**
	 * Clears the cache for all modules.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ModuleImpl.class);

		finderCache.clearCache(ModuleImpl.class);
	}

	/**
	 * Clears the cache for the module.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Module module) {
		entityCache.removeResult(ModuleImpl.class, module);
	}

	@Override
	public void clearCache(List<Module> modules) {
		for (Module module : modules) {
			entityCache.removeResult(ModuleImpl.class, module);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ModuleImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ModuleImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(ModuleModelImpl moduleModelImpl) {
		Object[] args = new Object[] {
			moduleModelImpl.getAppId(), moduleModelImpl.getBundleSymbolicName(),
			moduleModelImpl.getBundleVersion()
		};

		finderCache.putResult(
			_finderPathFetchByA_BSN_BV, args, moduleModelImpl);
	}

	/**
	 * Creates a new module with the primary key. Does not add the module to the database.
	 *
	 * @param moduleId the primary key for the new module
	 * @return the new module
	 */
	@Override
	public Module create(long moduleId) {
		Module module = new ModuleImpl();

		module.setNew(true);
		module.setPrimaryKey(moduleId);

		String uuid = PortalUUIDUtil.generate();

		module.setUuid(uuid);

		module.setCompanyId(CompanyThreadLocal.getCompanyId());

		return module;
	}

	/**
	 * Removes the module with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param moduleId the primary key of the module
	 * @return the module that was removed
	 * @throws NoSuchModuleException if a module with the primary key could not be found
	 */
	@Override
	public Module remove(long moduleId) throws NoSuchModuleException {
		return remove((Serializable)moduleId);
	}

	/**
	 * Removes the module with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the module
	 * @return the module that was removed
	 * @throws NoSuchModuleException if a module with the primary key could not be found
	 */
	@Override
	public Module remove(Serializable primaryKey) throws NoSuchModuleException {
		Session session = null;

		try {
			session = openSession();

			Module module = (Module)session.get(ModuleImpl.class, primaryKey);

			if (module == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchModuleException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(module);
		}
		catch (NoSuchModuleException noSuchEntityException) {
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
	protected Module removeImpl(Module module) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(module)) {
				module = (Module)session.get(
					ModuleImpl.class, module.getPrimaryKeyObj());
			}

			if (module != null) {
				session.delete(module);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (module != null) {
			clearCache(module);
		}

		return module;
	}

	@Override
	public Module updateImpl(Module module) {
		boolean isNew = module.isNew();

		if (!(module instanceof ModuleModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(module.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(module);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in module proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Module implementation " +
					module.getClass());
		}

		ModuleModelImpl moduleModelImpl = (ModuleModelImpl)module;

		if (Validator.isNull(module.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			module.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(module);
			}
			else {
				module = (Module)session.merge(module);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(ModuleImpl.class, moduleModelImpl, false, true);

		cacheUniqueFindersCache(moduleModelImpl);

		if (isNew) {
			module.setNew(false);
		}

		module.resetOriginalValues();

		return module;
	}

	/**
	 * Returns the module with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the module
	 * @return the module
	 * @throws NoSuchModuleException if a module with the primary key could not be found
	 */
	@Override
	public Module findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModuleException {

		Module module = fetchByPrimaryKey(primaryKey);

		if (module == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchModuleException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return module;
	}

	/**
	 * Returns the module with the primary key or throws a <code>NoSuchModuleException</code> if it could not be found.
	 *
	 * @param moduleId the primary key of the module
	 * @return the module
	 * @throws NoSuchModuleException if a module with the primary key could not be found
	 */
	@Override
	public Module findByPrimaryKey(long moduleId) throws NoSuchModuleException {
		return findByPrimaryKey((Serializable)moduleId);
	}

	/**
	 * Returns the module with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param moduleId the primary key of the module
	 * @return the module, or <code>null</code> if a module with the primary key could not be found
	 */
	@Override
	public Module fetchByPrimaryKey(long moduleId) {
		return fetchByPrimaryKey((Serializable)moduleId);
	}

	/**
	 * Returns all the modules.
	 *
	 * @return the modules
	 */
	@Override
	public List<Module> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the modules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @return the range of modules
	 */
	@Override
	public List<Module> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the modules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of modules
	 */
	@Override
	public List<Module> findAll(
		int start, int end, OrderByComparator<Module> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the modules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ModuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of modules
	 * @param end the upper bound of the range of modules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of modules
	 */
	@Override
	public List<Module> findAll(
		int start, int end, OrderByComparator<Module> orderByComparator,
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

		List<Module> list = null;

		if (useFinderCache) {
			list = (List<Module>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MODULE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MODULE;

				sql = sql.concat(ModuleModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Module>)QueryUtil.list(
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
	 * Removes all the modules from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Module module : findAll()) {
			remove(module);
		}
	}

	/**
	 * Returns the number of modules.
	 *
	 * @return the number of modules
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MODULE);

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
		return "moduleId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MODULE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ModuleModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the module persistence.
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
			_SQL_SELECT_MODULE_WHERE, _SQL_COUNT_MODULE_WHERE,
			ModuleModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"module.", "uuid", FinderColumn.Type.STRING, "=", true, true,
				Module::getUuid));

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
				_finderPathCountByUuid_C, _SQL_SELECT_MODULE_WHERE,
				_SQL_COUNT_MODULE_WHERE, ModuleModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"module.", "uuid", FinderColumn.Type.STRING, "=", true,
					false, Module::getUuid),
				new FinderColumn<>(
					"module.", "companyId", FinderColumn.Type.LONG, "=", true,
					true, Module::getCompanyId));

		_finderPathWithPaginationFindByAppId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAppId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"appId"}, true);

		_finderPathWithoutPaginationFindByAppId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAppId",
			new String[] {Long.class.getName()}, new String[] {"appId"}, true);

		_finderPathCountByAppId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAppId",
			new String[] {Long.class.getName()}, new String[] {"appId"}, false);

		_collectionPersistenceFinderByAppId = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByAppId,
			_finderPathWithoutPaginationFindByAppId, _finderPathCountByAppId,
			_SQL_SELECT_MODULE_WHERE, _SQL_COUNT_MODULE_WHERE,
			ModuleModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"module.", "appId", FinderColumn.Type.LONG, "=", true, true,
				Module::getAppId));

		_finderPathWithPaginationFindByBundleSymbolicName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByBundleSymbolicName",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"bundleSymbolicName"}, true);

		_finderPathWithoutPaginationFindByBundleSymbolicName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByBundleSymbolicName", new String[] {String.class.getName()},
			new String[] {"bundleSymbolicName"}, true);

		_finderPathCountByBundleSymbolicName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByBundleSymbolicName", new String[] {String.class.getName()},
			new String[] {"bundleSymbolicName"}, false);

		_collectionPersistenceFinderByBundleSymbolicName =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByBundleSymbolicName,
				_finderPathWithoutPaginationFindByBundleSymbolicName,
				_finderPathCountByBundleSymbolicName, _SQL_SELECT_MODULE_WHERE,
				_SQL_COUNT_MODULE_WHERE, ModuleModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"module.", "bundleSymbolicName", FinderColumn.Type.STRING,
					"=", true, true, Module::getBundleSymbolicName));

		_finderPathWithPaginationFindByContextName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByContextName",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"contextName"}, true);

		_finderPathWithoutPaginationFindByContextName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByContextName",
			new String[] {String.class.getName()}, new String[] {"contextName"},
			true);

		_finderPathCountByContextName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByContextName",
			new String[] {String.class.getName()}, new String[] {"contextName"},
			false);

		_collectionPersistenceFinderByContextName =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByContextName,
				_finderPathWithoutPaginationFindByContextName,
				_finderPathCountByContextName, _SQL_SELECT_MODULE_WHERE,
				_SQL_COUNT_MODULE_WHERE, ModuleModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"module.", "contextName", FinderColumn.Type.STRING, "=",
					true, true, Module::getContextName));

		_finderPathWithPaginationFindByA_CN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_CN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"appId", "contextName"}, true);

		_finderPathWithoutPaginationFindByA_CN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_CN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"appId", "contextName"}, true);

		_finderPathCountByA_CN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_CN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"appId", "contextName"}, false);

		_collectionPersistenceFinderByA_CN = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByA_CN,
			_finderPathWithoutPaginationFindByA_CN, _finderPathCountByA_CN,
			_SQL_SELECT_MODULE_WHERE, _SQL_COUNT_MODULE_WHERE,
			ModuleModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"module.", "appId", FinderColumn.Type.LONG, "=", true, false,
				Module::getAppId),
			new FinderColumn<>(
				"module.", "contextName", FinderColumn.Type.STRING, "=", true,
				true, Module::getContextName));

		_finderPathFetchByA_BSN_BV = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByA_BSN_BV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"appId", "bundleSymbolicName", "bundleVersion"},
			true);

		_uniquePersistenceFinderByA_BSN_BV = new UniquePersistenceFinder<>(
			this, _finderPathFetchByA_BSN_BV, _SQL_SELECT_MODULE_WHERE,
			new FinderColumn<>(
				"module.", "appId", FinderColumn.Type.LONG, "=", true, false,
				Module::getAppId),
			new FinderColumn<>(
				"module.", "bundleSymbolicName", FinderColumn.Type.STRING, "=",
				true, false, Module::getBundleSymbolicName),
			new FinderColumn<>(
				"module.", "bundleVersion", FinderColumn.Type.STRING, "=", true,
				true, Module::getBundleVersion));

		ModuleUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ModuleUtil.setPersistence(null);

		entityCache.removeCache(ModuleImpl.class.getName());
	}

	@Override
	@Reference(
		target = MarketplacePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MarketplacePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MarketplacePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_MODULE =
		"SELECT module FROM Module module";

	private static final String _SQL_SELECT_MODULE_WHERE =
		"SELECT module FROM Module module WHERE ";

	private static final String _SQL_COUNT_MODULE =
		"SELECT COUNT(module) FROM Module module";

	private static final String _SQL_COUNT_MODULE_WHERE =
		"SELECT COUNT(module) FROM Module module WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "module.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Module exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Module exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ModulePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-918102706