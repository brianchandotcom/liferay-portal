/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.exportimport.service.persistence.impl;

import com.liferay.exportimport.kernel.exception.NoSuchConfigurationException;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.model.ExportImportConfigurationTable;
import com.liferay.exportimport.kernel.service.persistence.ExportImportConfigurationPersistence;
import com.liferay.exportimport.kernel.service.persistence.ExportImportConfigurationUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationImpl;
import com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the export import configuration service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExportImportConfigurationPersistenceImpl
	extends BasePersistenceImpl<ExportImportConfiguration>
	implements ExportImportConfigurationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ExportImportConfigurationUtil</code> to access the export import configuration persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ExportImportConfigurationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<ExportImportConfiguration>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the export import configurations where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the export import configurations where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByGroupId.find(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByGroupId_First(
			long groupId,
			OrderByComparator<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException {

		ExportImportConfiguration exportImportConfiguration =
			fetchByGroupId_First(groupId, orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchConfigurationException(sb.toString());
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByGroupId_First(
		long groupId,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		List<ExportImportConfiguration> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the export import configurations where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (ExportImportConfiguration exportImportConfiguration :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching export import configurations
	 */
	@Override
	public int countByGroupId(long groupId) {
		return _collectionPersistenceFinderByGroupId.count(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId});
	}

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<ExportImportConfiguration>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the export import configurations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the export import configurations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the export import configurations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByCompanyId_First(
			long companyId,
			OrderByComparator<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException {

		ExportImportConfiguration exportImportConfiguration =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchConfigurationException(sb.toString());
	}

	/**
	 * Returns the first export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByCompanyId_First(
		long companyId,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		List<ExportImportConfiguration> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the export import configurations where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (ExportImportConfiguration exportImportConfiguration :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching export import configurations
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			FinderCacheUtil.getFinderCache(), new Object[] {companyId});
	}

	private FinderPath _finderPathWithPaginationFindByG_T;
	private FinderPath _finderPathWithoutPaginationFindByG_T;
	private FinderPath _finderPathCountByG_T;
	private CollectionPersistenceFinder<ExportImportConfiguration>
		_collectionPersistenceFinderByG_T;

	/**
	 * Returns all the export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T(long groupId, int type) {
		return findByG_T(
			groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T(
		long groupId, int type, int start, int end) {

		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_T.find(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId, type},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByG_T_First(
			long groupId, int type,
			OrderByComparator<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException {

		ExportImportConfiguration exportImportConfiguration = fetchByG_T_First(
			groupId, type, orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchConfigurationException(sb.toString());
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByG_T_First(
		long groupId, int type,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		List<ExportImportConfiguration> list = findByG_T(
			groupId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the export import configurations where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, int type) {
		for (ExportImportConfiguration exportImportConfiguration :
				findByG_T(
					groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching export import configurations
	 */
	@Override
	public int countByG_T(long groupId, int type) {
		return _collectionPersistenceFinderByG_T.count(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId, type});
	}

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;
	private CollectionPersistenceFinder<ExportImportConfiguration>
		_collectionPersistenceFinderByG_S;

	/**
	 * Returns all the export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_S(long groupId, int status) {
		return findByG_S(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_S(
		long groupId, int status, int start, int end) {

		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		return findByG_S(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_S.find(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId, status},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByG_S_First(
			long groupId, int status,
			OrderByComparator<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException {

		ExportImportConfiguration exportImportConfiguration = fetchByG_S_First(
			groupId, status, orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchConfigurationException(sb.toString());
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByG_S_First(
		long groupId, int status,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		List<ExportImportConfiguration> list = findByG_S(
			groupId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the export import configurations where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_S(long groupId, int status) {
		for (ExportImportConfiguration exportImportConfiguration :
				findByG_S(
					groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching export import configurations
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		return _collectionPersistenceFinderByG_S.count(
			FinderCacheUtil.getFinderCache(), new Object[] {groupId, status});
	}

	private FinderPath _finderPathWithPaginationFindByG_T_S;
	private FinderPath _finderPathWithoutPaginationFindByG_T_S;
	private FinderPath _finderPathCountByG_T_S;
	private CollectionPersistenceFinder<ExportImportConfiguration>
		_collectionPersistenceFinderByG_T_S;

	/**
	 * Returns all the export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @return the matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T_S(
		long groupId, int type, int status) {

		return findByG_T_S(
			groupId, type, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T_S(
		long groupId, int type, int status, int start, int end) {

		return findByG_T_S(groupId, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T_S(
		long groupId, int type, int status, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		return findByG_T_S(
			groupId, type, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findByG_T_S(
		long groupId, int type, int status, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_T_S.find(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, type, status}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration findByG_T_S_First(
			long groupId, int type, int status,
			OrderByComparator<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException {

		ExportImportConfiguration exportImportConfiguration =
			fetchByG_T_S_First(groupId, type, status, orderByComparator);

		if (exportImportConfiguration != null) {
			return exportImportConfiguration;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchConfigurationException(sb.toString());
	}

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByG_T_S_First(
		long groupId, int type, int status,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		List<ExportImportConfiguration> list = findByG_T_S(
			groupId, type, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the export import configurations where groupId = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByG_T_S(long groupId, int type, int status) {
		for (ExportImportConfiguration exportImportConfiguration :
				findByG_T_S(
					groupId, type, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching export import configurations
	 */
	@Override
	public int countByG_T_S(long groupId, int type, int status) {
		return _collectionPersistenceFinderByG_T_S.count(
			FinderCacheUtil.getFinderCache(),
			new Object[] {groupId, type, status});
	}

	public ExportImportConfigurationPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");
		dbColumnNames.put("settings", "settings_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ExportImportConfiguration.class);

		setModelImplClass(ExportImportConfigurationImpl.class);
		setModelPKClass(long.class);

		setTable(ExportImportConfigurationTable.INSTANCE);
	}

	/**
	 * Caches the export import configuration in the entity cache if it is enabled.
	 *
	 * @param exportImportConfiguration the export import configuration
	 */
	@Override
	public void cacheResult(
		ExportImportConfiguration exportImportConfiguration) {

		EntityCacheUtil.putResult(
			ExportImportConfigurationImpl.class,
			exportImportConfiguration.getPrimaryKey(),
			exportImportConfiguration);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the export import configurations in the entity cache if it is enabled.
	 *
	 * @param exportImportConfigurations the export import configurations
	 */
	@Override
	public void cacheResult(
		List<ExportImportConfiguration> exportImportConfigurations) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (exportImportConfigurations.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ExportImportConfiguration exportImportConfiguration :
				exportImportConfigurations) {

			if (EntityCacheUtil.getResult(
					ExportImportConfigurationImpl.class,
					exportImportConfiguration.getPrimaryKey()) == null) {

				cacheResult(exportImportConfiguration);
			}
		}
	}

	/**
	 * Clears the cache for all export import configurations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ExportImportConfigurationImpl.class);

		FinderCacheUtil.clearCache(ExportImportConfigurationImpl.class);
	}

	/**
	 * Clears the cache for the export import configuration.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		ExportImportConfiguration exportImportConfiguration) {

		EntityCacheUtil.removeResult(
			ExportImportConfigurationImpl.class, exportImportConfiguration);
	}

	@Override
	public void clearCache(
		List<ExportImportConfiguration> exportImportConfigurations) {

		for (ExportImportConfiguration exportImportConfiguration :
				exportImportConfigurations) {

			EntityCacheUtil.removeResult(
				ExportImportConfigurationImpl.class, exportImportConfiguration);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(ExportImportConfigurationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				ExportImportConfigurationImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new export import configuration with the primary key. Does not add the export import configuration to the database.
	 *
	 * @param exportImportConfigurationId the primary key for the new export import configuration
	 * @return the new export import configuration
	 */
	@Override
	public ExportImportConfiguration create(long exportImportConfigurationId) {
		ExportImportConfiguration exportImportConfiguration =
			new ExportImportConfigurationImpl();

		exportImportConfiguration.setNew(true);
		exportImportConfiguration.setPrimaryKey(exportImportConfigurationId);

		exportImportConfiguration.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return exportImportConfiguration;
	}

	/**
	 * Removes the export import configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param exportImportConfigurationId the primary key of the export import configuration
	 * @return the export import configuration that was removed
	 * @throws NoSuchConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration remove(long exportImportConfigurationId)
		throws NoSuchConfigurationException {

		return remove((Serializable)exportImportConfigurationId);
	}

	/**
	 * Removes the export import configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the export import configuration
	 * @return the export import configuration that was removed
	 * @throws NoSuchConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration remove(Serializable primaryKey)
		throws NoSuchConfigurationException {

		Session session = null;

		try {
			session = openSession();

			ExportImportConfiguration exportImportConfiguration =
				(ExportImportConfiguration)session.get(
					ExportImportConfigurationImpl.class, primaryKey);

			if (exportImportConfiguration == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchConfigurationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(exportImportConfiguration);
		}
		catch (NoSuchConfigurationException noSuchEntityException) {
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
	protected ExportImportConfiguration removeImpl(
		ExportImportConfiguration exportImportConfiguration) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(exportImportConfiguration)) {
				exportImportConfiguration =
					(ExportImportConfiguration)session.get(
						ExportImportConfigurationImpl.class,
						exportImportConfiguration.getPrimaryKeyObj());
			}

			if (exportImportConfiguration != null) {
				session.delete(exportImportConfiguration);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (exportImportConfiguration != null) {
			clearCache(exportImportConfiguration);
		}

		return exportImportConfiguration;
	}

	@Override
	public ExportImportConfiguration updateImpl(
		ExportImportConfiguration exportImportConfiguration) {

		boolean isNew = exportImportConfiguration.isNew();

		if (!(exportImportConfiguration instanceof
				ExportImportConfigurationModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(exportImportConfiguration.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					exportImportConfiguration);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in exportImportConfiguration proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ExportImportConfiguration implementation " +
					exportImportConfiguration.getClass());
		}

		ExportImportConfigurationModelImpl exportImportConfigurationModelImpl =
			(ExportImportConfigurationModelImpl)exportImportConfiguration;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (exportImportConfiguration.getCreateDate() == null)) {
			if (serviceContext == null) {
				exportImportConfiguration.setCreateDate(date);
			}
			else {
				exportImportConfiguration.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!exportImportConfigurationModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				exportImportConfiguration.setModifiedDate(date);
			}
			else {
				exportImportConfiguration.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(exportImportConfiguration);
			}
			else {
				exportImportConfiguration =
					(ExportImportConfiguration)session.merge(
						exportImportConfiguration);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			ExportImportConfigurationImpl.class,
			exportImportConfigurationModelImpl, false, true);

		if (isNew) {
			exportImportConfiguration.setNew(false);
		}

		exportImportConfiguration.resetOriginalValues();

		return exportImportConfiguration;
	}

	/**
	 * Returns the export import configuration with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the export import configuration
	 * @return the export import configuration
	 * @throws NoSuchConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration findByPrimaryKey(Serializable primaryKey)
		throws NoSuchConfigurationException {

		ExportImportConfiguration exportImportConfiguration = fetchByPrimaryKey(
			primaryKey);

		if (exportImportConfiguration == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchConfigurationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return exportImportConfiguration;
	}

	/**
	 * Returns the export import configuration with the primary key or throws a <code>NoSuchConfigurationException</code> if it could not be found.
	 *
	 * @param exportImportConfigurationId the primary key of the export import configuration
	 * @return the export import configuration
	 * @throws NoSuchConfigurationException if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration findByPrimaryKey(
			long exportImportConfigurationId)
		throws NoSuchConfigurationException {

		return findByPrimaryKey((Serializable)exportImportConfigurationId);
	}

	/**
	 * Returns the export import configuration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param exportImportConfigurationId the primary key of the export import configuration
	 * @return the export import configuration, or <code>null</code> if a export import configuration with the primary key could not be found
	 */
	@Override
	public ExportImportConfiguration fetchByPrimaryKey(
		long exportImportConfigurationId) {

		return fetchByPrimaryKey((Serializable)exportImportConfigurationId);
	}

	/**
	 * Returns all the export import configurations.
	 *
	 * @return the export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the export import configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the export import configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findAll(
		int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the export import configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of export import configurations
	 */
	@Override
	public List<ExportImportConfiguration> findAll(
		int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator,
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

		List<ExportImportConfiguration> list = null;

		if (useFinderCache) {
			list = (List<ExportImportConfiguration>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_EXPORTIMPORTCONFIGURATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_EXPORTIMPORTCONFIGURATION;

				sql = sql.concat(
					ExportImportConfigurationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ExportImportConfiguration>)QueryUtil.list(
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

	/**
	 * Removes all the export import configurations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ExportImportConfiguration exportImportConfiguration : findAll()) {
			remove(exportImportConfiguration);
		}
	}

	/**
	 * Returns the number of export import configurations.
	 *
	 * @return the number of export import configurations
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_EXPORTIMPORTCONFIGURATION);

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
		return "exportImportConfigurationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_EXPORTIMPORTCONFIGURATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ExportImportConfigurationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the export import configuration persistence.
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
				_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE,
				_SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE,
				ExportImportConfigurationModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"exportImportConfiguration.", "groupId",
					FinderColumn.Type.LONG, "=", true, true,
					ExportImportConfiguration::getGroupId));

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
				_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE,
				_SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE,
				ExportImportConfigurationModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"exportImportConfiguration.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					ExportImportConfiguration::getCompanyId));

		_finderPathWithPaginationFindByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "type_"}, true);

		_finderPathWithoutPaginationFindByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "type_"}, true);

		_finderPathCountByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "type_"}, false);

		_collectionPersistenceFinderByG_T = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_T,
			_finderPathWithoutPaginationFindByG_T, _finderPathCountByG_T,
			_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE,
			_SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE,
			ExportImportConfigurationModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"exportImportConfiguration.", "groupId", FinderColumn.Type.LONG,
				"=", true, false, ExportImportConfiguration::getGroupId),
			new FinderColumn<>(
				"exportImportConfiguration.", "type", FinderColumn.Type.INTEGER,
				"=", true, true, ExportImportConfiguration::getType));

		_finderPathWithPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "status"}, true);

		_finderPathWithoutPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, true);

		_finderPathCountByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, false);

		_collectionPersistenceFinderByG_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_S,
			_finderPathWithoutPaginationFindByG_S, _finderPathCountByG_S,
			_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE,
			_SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE,
			ExportImportConfigurationModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"exportImportConfiguration.", "groupId", FinderColumn.Type.LONG,
				"=", true, false, ExportImportConfiguration::getGroupId),
			new FinderColumn<>(
				"exportImportConfiguration.", "status",
				FinderColumn.Type.INTEGER, "=", true, true,
				ExportImportConfiguration::getStatus));

		_finderPathWithPaginationFindByG_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "type_", "status"}, true);

		_finderPathWithoutPaginationFindByG_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "type_", "status"}, true);

		_finderPathCountByG_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "type_", "status"}, false);

		_collectionPersistenceFinderByG_T_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_T_S,
			_finderPathWithoutPaginationFindByG_T_S, _finderPathCountByG_T_S,
			_SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE,
			_SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE,
			ExportImportConfigurationModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"exportImportConfiguration.", "groupId", FinderColumn.Type.LONG,
				"=", true, false, ExportImportConfiguration::getGroupId),
			new FinderColumn<>(
				"exportImportConfiguration.", "type", FinderColumn.Type.INTEGER,
				"=", true, false, ExportImportConfiguration::getType),
			new FinderColumn<>(
				"exportImportConfiguration.", "status",
				FinderColumn.Type.INTEGER, "=", true, true,
				ExportImportConfiguration::getStatus));

		ExportImportConfigurationUtil.setPersistence(this);
	}

	public void destroy() {
		ExportImportConfigurationUtil.setPersistence(null);

		EntityCacheUtil.removeCache(
			ExportImportConfigurationImpl.class.getName());
	}

	private static final String _SQL_SELECT_EXPORTIMPORTCONFIGURATION =
		"SELECT exportImportConfiguration FROM ExportImportConfiguration exportImportConfiguration";

	private static final String _SQL_SELECT_EXPORTIMPORTCONFIGURATION_WHERE =
		"SELECT exportImportConfiguration FROM ExportImportConfiguration exportImportConfiguration WHERE ";

	private static final String _SQL_COUNT_EXPORTIMPORTCONFIGURATION =
		"SELECT COUNT(exportImportConfiguration) FROM ExportImportConfiguration exportImportConfiguration";

	private static final String _SQL_COUNT_EXPORTIMPORTCONFIGURATION_WHERE =
		"SELECT COUNT(exportImportConfiguration) FROM ExportImportConfiguration exportImportConfiguration WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"exportImportConfiguration.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ExportImportConfiguration exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ExportImportConfiguration exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportConfigurationPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type", "settings"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1006532141