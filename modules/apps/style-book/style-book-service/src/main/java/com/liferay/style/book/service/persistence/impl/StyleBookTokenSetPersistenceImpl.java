/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.persistence.impl;

import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
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
import com.liferay.style.book.exception.DuplicateStyleBookTokenSetExternalReferenceCodeException;
import com.liferay.style.book.exception.NoSuchTokenSetException;
import com.liferay.style.book.model.StyleBookTokenSet;
import com.liferay.style.book.model.StyleBookTokenSetTable;
import com.liferay.style.book.model.impl.StyleBookTokenSetImpl;
import com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl;
import com.liferay.style.book.service.persistence.StyleBookTokenSetPersistence;
import com.liferay.style.book.service.persistence.StyleBookTokenSetUtil;
import com.liferay.style.book.service.persistence.impl.constants.StyleBookPersistenceConstants;

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
 * The persistence implementation for the style book token set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = StyleBookTokenSetPersistence.class)
public class StyleBookTokenSetPersistenceImpl
	extends BasePersistenceImpl<StyleBookTokenSet, NoSuchTokenSetException>
	implements StyleBookTokenSetPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>StyleBookTokenSetUtil</code> to access the style book token set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		StyleBookTokenSetImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private CollectionPersistenceFinder
		<StyleBookTokenSet, NoSuchTokenSetException>
			_collectionPersistenceFinderByUuid;

	/**
	 * Returns an ordered range of all the style book token sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book token sets
	 */
	@Override
	public List<StyleBookTokenSet> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<StyleBookTokenSet> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first style book token set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet findByUuid_First(
			String uuid, OrderByComparator<StyleBookTokenSet> orderByComparator)
		throws NoSuchTokenSetException {

		return _collectionPersistenceFinderByUuid.findFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Returns the first style book token set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet fetchByUuid_First(
		String uuid, OrderByComparator<StyleBookTokenSet> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the style book token sets where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of style book token sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching style book token sets
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private UniquePersistenceFinder<StyleBookTokenSet, NoSuchTokenSetException>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the style book token set where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTokenSetException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet findByUUID_G(String uuid, long groupId)
		throws NoSuchTokenSetException {

		return _uniquePersistenceFinderByUUID_G.find(
			finderCache, new Object[] {uuid, groupId});
	}

	/**
	 * Returns the style book token set where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return _uniquePersistenceFinderByUUID_G.fetch(
			finderCache, new Object[] {uuid, groupId}, useFinderCache);
	}

	/**
	 * Removes the style book token set where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the style book token set that was removed
	 */
	@Override
	public StyleBookTokenSet removeByUUID_G(String uuid, long groupId)
		throws NoSuchTokenSetException {

		StyleBookTokenSet styleBookTokenSet = findByUUID_G(uuid, groupId);

		return remove(styleBookTokenSet);
	}

	/**
	 * Returns the number of style book token sets where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching style book token sets
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private CollectionPersistenceFinder
		<StyleBookTokenSet, NoSuchTokenSetException>
			_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns an ordered range of all the style book token sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book token sets
	 */
	@Override
	public List<StyleBookTokenSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<StyleBookTokenSet> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first style book token set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<StyleBookTokenSet> orderByComparator)
		throws NoSuchTokenSetException {

		return _collectionPersistenceFinderByUuid_C.findFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Returns the first style book token set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<StyleBookTokenSet> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the style book token sets where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of style book token sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching style book token sets
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private CollectionPersistenceFinder
		<StyleBookTokenSet, NoSuchTokenSetException>
			_collectionPersistenceFinderByStyleBookEntryId;

	/**
	 * Returns an ordered range of all the style book token sets where styleBookEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book token sets
	 */
	@Override
	public List<StyleBookTokenSet> findByStyleBookEntryId(
		long styleBookEntryId, int start, int end,
		OrderByComparator<StyleBookTokenSet> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByStyleBookEntryId.find(
			finderCache, new Object[] {styleBookEntryId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first style book token set in the ordered set where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet findByStyleBookEntryId_First(
			long styleBookEntryId,
			OrderByComparator<StyleBookTokenSet> orderByComparator)
		throws NoSuchTokenSetException {

		return _collectionPersistenceFinderByStyleBookEntryId.findFirst(
			finderCache, new Object[] {styleBookEntryId}, orderByComparator);
	}

	/**
	 * Returns the first style book token set in the ordered set where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet fetchByStyleBookEntryId_First(
		long styleBookEntryId,
		OrderByComparator<StyleBookTokenSet> orderByComparator) {

		return _collectionPersistenceFinderByStyleBookEntryId.fetchFirst(
			finderCache, new Object[] {styleBookEntryId}, orderByComparator);
	}

	/**
	 * Removes all the style book token sets where styleBookEntryId = &#63; from the database.
	 *
	 * @param styleBookEntryId the style book entry ID
	 */
	@Override
	public void removeByStyleBookEntryId(long styleBookEntryId) {
		_collectionPersistenceFinderByStyleBookEntryId.remove(
			finderCache, new Object[] {styleBookEntryId});
	}

	/**
	 * Returns the number of style book token sets where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @return the number of matching style book token sets
	 */
	@Override
	public int countByStyleBookEntryId(long styleBookEntryId) {
		return _collectionPersistenceFinderByStyleBookEntryId.count(
			finderCache, new Object[] {styleBookEntryId});
	}

	private UniquePersistenceFinder<StyleBookTokenSet, NoSuchTokenSetException>
		_uniquePersistenceFinderByFTCN_FTDI_N_SBEI;

	/**
	 * Returns the style book token set where frontendTokenCategoryName = &#63; and frontendTokenDefinitionId = &#63; and name = &#63; and styleBookEntryId = &#63; or throws a <code>NoSuchTokenSetException</code> if it could not be found.
	 *
	 * @param frontendTokenCategoryName the frontend token category name
	 * @param frontendTokenDefinitionId the frontend token definition ID
	 * @param name the name
	 * @param styleBookEntryId the style book entry ID
	 * @return the matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet findByFTCN_FTDI_N_SBEI(
			String frontendTokenCategoryName, String frontendTokenDefinitionId,
			String name, long styleBookEntryId)
		throws NoSuchTokenSetException {

		return _uniquePersistenceFinderByFTCN_FTDI_N_SBEI.find(
			finderCache,
			new Object[] {
				frontendTokenCategoryName, frontendTokenDefinitionId, name,
				styleBookEntryId
			});
	}

	/**
	 * Returns the style book token set where frontendTokenCategoryName = &#63; and frontendTokenDefinitionId = &#63; and name = &#63; and styleBookEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param frontendTokenCategoryName the frontend token category name
	 * @param frontendTokenDefinitionId the frontend token definition ID
	 * @param name the name
	 * @param styleBookEntryId the style book entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet fetchByFTCN_FTDI_N_SBEI(
		String frontendTokenCategoryName, String frontendTokenDefinitionId,
		String name, long styleBookEntryId, boolean useFinderCache) {

		return _uniquePersistenceFinderByFTCN_FTDI_N_SBEI.fetch(
			finderCache,
			new Object[] {
				frontendTokenCategoryName, frontendTokenDefinitionId, name,
				styleBookEntryId
			},
			useFinderCache);
	}

	/**
	 * Removes the style book token set where frontendTokenCategoryName = &#63; and frontendTokenDefinitionId = &#63; and name = &#63; and styleBookEntryId = &#63; from the database.
	 *
	 * @param frontendTokenCategoryName the frontend token category name
	 * @param frontendTokenDefinitionId the frontend token definition ID
	 * @param name the name
	 * @param styleBookEntryId the style book entry ID
	 * @return the style book token set that was removed
	 */
	@Override
	public StyleBookTokenSet removeByFTCN_FTDI_N_SBEI(
			String frontendTokenCategoryName, String frontendTokenDefinitionId,
			String name, long styleBookEntryId)
		throws NoSuchTokenSetException {

		StyleBookTokenSet styleBookTokenSet = findByFTCN_FTDI_N_SBEI(
			frontendTokenCategoryName, frontendTokenDefinitionId, name,
			styleBookEntryId);

		return remove(styleBookTokenSet);
	}

	/**
	 * Returns the number of style book token sets where frontendTokenCategoryName = &#63; and frontendTokenDefinitionId = &#63; and name = &#63; and styleBookEntryId = &#63;.
	 *
	 * @param frontendTokenCategoryName the frontend token category name
	 * @param frontendTokenDefinitionId the frontend token definition ID
	 * @param name the name
	 * @param styleBookEntryId the style book entry ID
	 * @return the number of matching style book token sets
	 */
	@Override
	public int countByFTCN_FTDI_N_SBEI(
		String frontendTokenCategoryName, String frontendTokenDefinitionId,
		String name, long styleBookEntryId) {

		return _uniquePersistenceFinderByFTCN_FTDI_N_SBEI.count(
			finderCache,
			new Object[] {
				frontendTokenCategoryName, frontendTokenDefinitionId, name,
				styleBookEntryId
			});
	}

	private UniquePersistenceFinder<StyleBookTokenSet, NoSuchTokenSetException>
		_uniquePersistenceFinderByERC_G;

	/**
	 * Returns the style book token set where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchTokenSetException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet findByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchTokenSetException {

		return _uniquePersistenceFinderByERC_G.find(
			finderCache, new Object[] {externalReferenceCode, groupId});
	}

	/**
	 * Returns the style book token set where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	@Override
	public StyleBookTokenSet fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_G.fetch(
			finderCache, new Object[] {externalReferenceCode, groupId},
			useFinderCache);
	}

	/**
	 * Removes the style book token set where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the style book token set that was removed
	 */
	@Override
	public StyleBookTokenSet removeByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchTokenSetException {

		StyleBookTokenSet styleBookTokenSet = findByERC_G(
			externalReferenceCode, groupId);

		return remove(styleBookTokenSet);
	}

	/**
	 * Returns the number of style book token sets where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching style book token sets
	 */
	@Override
	public int countByERC_G(String externalReferenceCode, long groupId) {
		return _uniquePersistenceFinderByERC_G.count(
			finderCache, new Object[] {externalReferenceCode, groupId});
	}

	public StyleBookTokenSetPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(StyleBookTokenSet.class);

		setModelImplClass(StyleBookTokenSetImpl.class);
		setModelPKClass(long.class);

		setTable(StyleBookTokenSetTable.INSTANCE);
	}

	/**
	 * Creates a new style book token set with the primary key. Does not add the style book token set to the database.
	 *
	 * @param styleBookTokenSetId the primary key for the new style book token set
	 * @return the new style book token set
	 */
	@Override
	public StyleBookTokenSet create(long styleBookTokenSetId) {
		StyleBookTokenSet styleBookTokenSet = new StyleBookTokenSetImpl();

		styleBookTokenSet.setNew(true);
		styleBookTokenSet.setPrimaryKey(styleBookTokenSetId);

		String uuid = PortalUUIDUtil.generate();

		styleBookTokenSet.setUuid(uuid);

		styleBookTokenSet.setCompanyId(CompanyThreadLocal.getCompanyId());

		return styleBookTokenSet;
	}

	/**
	 * Removes the style book token set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set that was removed
	 * @throws NoSuchTokenSetException if a style book token set with the primary key could not be found
	 */
	@Override
	public StyleBookTokenSet remove(long styleBookTokenSetId)
		throws NoSuchTokenSetException {

		return remove((Serializable)styleBookTokenSetId);
	}

	@Override
	protected StyleBookTokenSet removeImpl(
		StyleBookTokenSet styleBookTokenSet) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(styleBookTokenSet)) {
				styleBookTokenSet = (StyleBookTokenSet)session.get(
					StyleBookTokenSetImpl.class,
					styleBookTokenSet.getPrimaryKeyObj());
			}

			if ((styleBookTokenSet != null) &&
				ctPersistenceHelper.isRemove(styleBookTokenSet)) {

				session.delete(styleBookTokenSet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (styleBookTokenSet != null) {
			clearCache(styleBookTokenSet);
		}

		return styleBookTokenSet;
	}

	@Override
	public StyleBookTokenSet updateImpl(StyleBookTokenSet styleBookTokenSet) {
		boolean isNew = styleBookTokenSet.isNew();

		if (!(styleBookTokenSet instanceof StyleBookTokenSetModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(styleBookTokenSet.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					styleBookTokenSet);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in styleBookTokenSet proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom StyleBookTokenSet implementation " +
					styleBookTokenSet.getClass());
		}

		StyleBookTokenSetModelImpl styleBookTokenSetModelImpl =
			(StyleBookTokenSetModelImpl)styleBookTokenSet;

		if (Validator.isNull(styleBookTokenSet.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			styleBookTokenSet.setUuid(uuid);
		}

		if (Validator.isNull(styleBookTokenSet.getExternalReferenceCode())) {
			styleBookTokenSet.setExternalReferenceCode(
				styleBookTokenSet.getUuid());
		}
		else {
			if (!Objects.equals(
					styleBookTokenSetModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					styleBookTokenSet.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = styleBookTokenSet.getCompanyId();

					long groupId = styleBookTokenSet.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = styleBookTokenSet.getPrimaryKey();
					}

					try {
						styleBookTokenSet.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								StyleBookTokenSet.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								styleBookTokenSet.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			StyleBookTokenSet ercStyleBookTokenSet = fetchByERC_G(
				styleBookTokenSet.getExternalReferenceCode(),
				styleBookTokenSet.getGroupId());

			if (isNew) {
				if (ercStyleBookTokenSet != null) {
					throw new DuplicateStyleBookTokenSetExternalReferenceCodeException(
						"Duplicate style book token set with external reference code " +
							styleBookTokenSet.getExternalReferenceCode() +
								" and group " + styleBookTokenSet.getGroupId());
				}
			}
			else {
				if ((ercStyleBookTokenSet != null) &&
					(styleBookTokenSet.getStyleBookTokenSetId() !=
						ercStyleBookTokenSet.getStyleBookTokenSetId())) {

					throw new DuplicateStyleBookTokenSetExternalReferenceCodeException(
						"Duplicate style book token set with external reference code " +
							styleBookTokenSet.getExternalReferenceCode() +
								" and group " + styleBookTokenSet.getGroupId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (styleBookTokenSet.getCreateDate() == null)) {
			if (serviceContext == null) {
				styleBookTokenSet.setCreateDate(date);
			}
			else {
				styleBookTokenSet.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!styleBookTokenSetModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				styleBookTokenSet.setModifiedDate(date);
			}
			else {
				styleBookTokenSet.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(styleBookTokenSet)) {
				if (!isNew) {
					session.evict(
						StyleBookTokenSetImpl.class,
						styleBookTokenSet.getPrimaryKeyObj());
				}

				session.save(styleBookTokenSet);
			}
			else {
				styleBookTokenSet = (StyleBookTokenSet)session.merge(
					styleBookTokenSet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(styleBookTokenSet, false);

		if (isNew) {
			styleBookTokenSet.setNew(false);
		}

		styleBookTokenSet.resetOriginalValues();

		return styleBookTokenSet;
	}

	/**
	 * Returns the style book token set with the primary key or throws a <code>NoSuchTokenSetException</code> if it could not be found.
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set
	 * @throws NoSuchTokenSetException if a style book token set with the primary key could not be found
	 */
	@Override
	public StyleBookTokenSet findByPrimaryKey(long styleBookTokenSetId)
		throws NoSuchTokenSetException {

		return findByPrimaryKey((Serializable)styleBookTokenSetId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the style book token set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set, or <code>null</code> if a style book token set with the primary key could not be found
	 */
	@Override
	public StyleBookTokenSet fetchByPrimaryKey(long styleBookTokenSetId) {
		return fetchByPrimaryKey((Serializable)styleBookTokenSetId);
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
		return "styleBookTokenSetId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_STYLEBOOKTOKENSET;
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
		return StyleBookTokenSetModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "StyleBookTokenSet";
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
		ctMergeColumnNames.add("description");
		ctMergeColumnNames.add("frontendTokenCategoryName");
		ctMergeColumnNames.add("frontendTokenDefinitionId");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("styleBookEntryId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("styleBookTokenSetId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {
				"frontendTokenCategoryName", "frontendTokenDefinitionId",
				"name", "styleBookEntryId"
			});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "groupId"});
	}

	/**
	 * Initializes the style book token set persistence.
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
			_SQL_SELECT_STYLEBOOKTOKENSET_WHERE,
			_SQL_COUNT_STYLEBOOKTOKENSET_WHERE,
			StyleBookTokenSetModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"styleBookTokenSet.", "uuid", "uuid_", FinderColumn.Type.STRING,
				"=", true, true, StyleBookTokenSet::getUuid));

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this,
			createUniqueFinderPath(
				FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
				new String[] {String.class.getName(), Long.class.getName()},
				new String[] {"uuid_", "groupId"}, 0, 1, false,
				convertNullFunction(StyleBookTokenSet::getUuid),
				StyleBookTokenSet::getGroupId),
			_SQL_SELECT_STYLEBOOKTOKENSET_WHERE, "",
			new FinderColumn<>(
				"styleBookTokenSet.", "uuid", "uuid_", FinderColumn.Type.STRING,
				"=", true, true, StyleBookTokenSet::getUuid),
			new FinderColumn<>(
				"styleBookTokenSet.", "groupId", FinderColumn.Type.LONG, "=",
				true, true, StyleBookTokenSet::getGroupId));

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
				_SQL_SELECT_STYLEBOOKTOKENSET_WHERE,
				_SQL_COUNT_STYLEBOOKTOKENSET_WHERE,
				StyleBookTokenSetModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				"",
				new FinderColumn<>(
					"styleBookTokenSet.", "uuid", "uuid_",
					FinderColumn.Type.STRING, "=", true, true,
					StyleBookTokenSet::getUuid),
				new FinderColumn<>(
					"styleBookTokenSet.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, StyleBookTokenSet::getCompanyId));

		_collectionPersistenceFinderByStyleBookEntryId =
			new CollectionPersistenceFinder<>(
				this,
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
					"findByStyleBookEntryId",
					new String[] {
						Long.class.getName(), Integer.class.getName(),
						Integer.class.getName(),
						OrderByComparator.class.getName()
					},
					new String[] {"styleBookEntryId"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"findByStyleBookEntryId",
					new String[] {Long.class.getName()},
					new String[] {"styleBookEntryId"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"countByStyleBookEntryId",
					new String[] {Long.class.getName()},
					new String[] {"styleBookEntryId"}, false),
				_SQL_SELECT_STYLEBOOKTOKENSET_WHERE,
				_SQL_COUNT_STYLEBOOKTOKENSET_WHERE,
				StyleBookTokenSetModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
				"",
				new FinderColumn<>(
					"styleBookTokenSet.", "styleBookEntryId",
					FinderColumn.Type.LONG, "=", true, true,
					StyleBookTokenSet::getStyleBookEntryId));

		_uniquePersistenceFinderByFTCN_FTDI_N_SBEI =
			new UniquePersistenceFinder<>(
				this,
				createUniqueFinderPath(
					FINDER_CLASS_NAME_ENTITY, "fetchByFTCN_FTDI_N_SBEI",
					new String[] {
						String.class.getName(), String.class.getName(),
						String.class.getName(), Long.class.getName()
					},
					new String[] {
						"frontendTokenCategoryName",
						"frontendTokenDefinitionId", "name", "styleBookEntryId"
					},
					0, 7, false,
					convertNullFunction(
						StyleBookTokenSet::getFrontendTokenCategoryName),
					convertNullFunction(
						StyleBookTokenSet::getFrontendTokenDefinitionId),
					convertNullFunction(StyleBookTokenSet::getName),
					StyleBookTokenSet::getStyleBookEntryId),
				_SQL_SELECT_STYLEBOOKTOKENSET_WHERE, "",
				new FinderColumn<>(
					"styleBookTokenSet.", "frontendTokenCategoryName",
					FinderColumn.Type.STRING, "=", true, true,
					StyleBookTokenSet::getFrontendTokenCategoryName),
				new FinderColumn<>(
					"styleBookTokenSet.", "frontendTokenDefinitionId",
					FinderColumn.Type.STRING, "=", true, true,
					StyleBookTokenSet::getFrontendTokenDefinitionId),
				new FinderColumn<>(
					"styleBookTokenSet.", "name", FinderColumn.Type.STRING, "=",
					true, true, StyleBookTokenSet::getName),
				new FinderColumn<>(
					"styleBookTokenSet.", "styleBookEntryId",
					FinderColumn.Type.LONG, "=", true, true,
					StyleBookTokenSet::getStyleBookEntryId));

		_uniquePersistenceFinderByERC_G = new UniquePersistenceFinder<>(
			this,
			createUniqueFinderPath(
				FINDER_CLASS_NAME_ENTITY, "fetchByERC_G",
				new String[] {String.class.getName(), Long.class.getName()},
				new String[] {"externalReferenceCode", "groupId"}, 0, 1, false,
				convertNullFunction(
					StyleBookTokenSet::getExternalReferenceCode),
				StyleBookTokenSet::getGroupId),
			_SQL_SELECT_STYLEBOOKTOKENSET_WHERE, "",
			new FinderColumn<>(
				"styleBookTokenSet.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, true,
				StyleBookTokenSet::getExternalReferenceCode),
			new FinderColumn<>(
				"styleBookTokenSet.", "groupId", FinderColumn.Type.LONG, "=",
				true, true, StyleBookTokenSet::getGroupId));

		StyleBookTokenSetUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		StyleBookTokenSetUtil.setPersistence(null);

		entityCache.removeCache(StyleBookTokenSetImpl.class.getName());
	}

	@Override
	@Reference(
		target = StyleBookPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = StyleBookPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = StyleBookPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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
		StyleBookTokenSetModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_STYLEBOOKTOKENSET =
		"SELECT styleBookTokenSet FROM StyleBookTokenSet styleBookTokenSet";

	private static final String _SQL_SELECT_STYLEBOOKTOKENSET_WHERE =
		"SELECT styleBookTokenSet FROM StyleBookTokenSet styleBookTokenSet WHERE ";

	private static final String _SQL_COUNT_STYLEBOOKTOKENSET_WHERE =
		"SELECT COUNT(styleBookTokenSet) FROM StyleBookTokenSet styleBookTokenSet WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No StyleBookTokenSet exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		StyleBookTokenSetPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:2011580972