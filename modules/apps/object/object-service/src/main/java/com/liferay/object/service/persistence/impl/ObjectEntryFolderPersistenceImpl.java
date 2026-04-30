/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence.impl;

import com.liferay.object.exception.NoSuchObjectEntryFolderException;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.model.ObjectEntryFolderTable;
import com.liferay.object.model.impl.ObjectEntryFolderImpl;
import com.liferay.object.model.impl.ObjectEntryFolderModelImpl;
import com.liferay.object.service.persistence.ObjectEntryFolderPersistence;
import com.liferay.object.service.persistence.ObjectEntryFolderUtil;
import com.liferay.object.service.persistence.impl.constants.ObjectPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.ContentTypes;
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
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the object entry folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = ObjectEntryFolderPersistence.class)
public class ObjectEntryFolderPersistenceImpl
	extends BasePersistenceImpl
		<ObjectEntryFolder, NoSuchObjectEntryFolderException>
	implements ObjectEntryFolderPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectEntryFolderUtil</code> to access the object entry folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectEntryFolderImpl.class.getName();

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
	private CollectionPersistenceFinder<ObjectEntryFolder>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the object entry folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object entry folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object entry folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object entry folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid.find(
			finderCache, new Object[] {uuid}, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first object entry folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder findByUuid_First(
			String uuid, OrderByComparator<ObjectEntryFolder> orderByComparator)
		throws NoSuchObjectEntryFolderException {

		ObjectEntryFolder objectEntryFolder = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectEntryFolder != null) {
			return objectEntryFolder;
		}

		throw new NoSuchObjectEntryFolderException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first object entry folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder fetchByUuid_First(
		String uuid, OrderByComparator<ObjectEntryFolder> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the object entry folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of object entry folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object entry folders
	 */
	@Override
	public int countByUuid(String uuid) {
		return _collectionPersistenceFinderByUuid.count(
			finderCache, new Object[] {uuid});
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<ObjectEntryFolder>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the object entry folder where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchObjectEntryFolderException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchObjectEntryFolderException {

		ObjectEntryFolder objectEntryFolder = fetchByUUID_G(uuid, groupId);

		if (objectEntryFolder == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchObjectEntryFolderException(message);
		}

		return objectEntryFolder;
	}

	/**
	 * Returns the object entry folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the object entry folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return _uniquePersistenceFinderByUUID_G.fetch(
			finderCache, new Object[] {uuid, groupId}, useFinderCache);
	}

	/**
	 * Removes the object entry folder where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the object entry folder that was removed
	 */
	@Override
	public ObjectEntryFolder removeByUUID_G(String uuid, long groupId)
		throws NoSuchObjectEntryFolderException {

		ObjectEntryFolder objectEntryFolder = findByUUID_G(uuid, groupId);

		return remove(objectEntryFolder);
	}

	/**
	 * Returns the number of object entry folders where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching object entry folders
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<ObjectEntryFolder>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the object entry folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object entry folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object entry folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object entry folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByUuid_C.find(
			finderCache, new Object[] {uuid, companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object entry folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectEntryFolder> orderByComparator)
		throws NoSuchObjectEntryFolderException {

		ObjectEntryFolder objectEntryFolder = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectEntryFolder != null) {
			return objectEntryFolder;
		}

		throw new NoSuchObjectEntryFolderException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first object entry folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectEntryFolder> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the object entry folders where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of object entry folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object entry folders
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		return _collectionPersistenceFinderByUuid_C.count(
			finderCache, new Object[] {uuid, companyId});
	}

	private FinderPath _finderPathFetchByERC_G_C;
	private UniquePersistenceFinder<ObjectEntryFolder>
		_uniquePersistenceFinderByERC_G_C;

	/**
	 * Returns the object entry folder where externalReferenceCode = &#63; and groupId = &#63; and companyId = &#63; or throws a <code>NoSuchObjectEntryFolderException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder findByERC_G_C(
			String externalReferenceCode, long groupId, long companyId)
		throws NoSuchObjectEntryFolderException {

		ObjectEntryFolder objectEntryFolder = fetchByERC_G_C(
			externalReferenceCode, groupId, companyId);

		if (objectEntryFolder == null) {
			String message =
				_uniquePersistenceFinderByERC_G_C.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, groupId, companyId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchObjectEntryFolderException(message);
		}

		return objectEntryFolder;
	}

	/**
	 * Returns the object entry folder where externalReferenceCode = &#63; and groupId = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder fetchByERC_G_C(
		String externalReferenceCode, long groupId, long companyId) {

		return fetchByERC_G_C(externalReferenceCode, groupId, companyId, true);
	}

	/**
	 * Returns the object entry folder where externalReferenceCode = &#63; and groupId = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder fetchByERC_G_C(
		String externalReferenceCode, long groupId, long companyId,
		boolean useFinderCache) {

		return _uniquePersistenceFinderByERC_G_C.fetch(
			finderCache,
			new Object[] {externalReferenceCode, groupId, companyId},
			useFinderCache);
	}

	/**
	 * Removes the object entry folder where externalReferenceCode = &#63; and groupId = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the object entry folder that was removed
	 */
	@Override
	public ObjectEntryFolder removeByERC_G_C(
			String externalReferenceCode, long groupId, long companyId)
		throws NoSuchObjectEntryFolderException {

		ObjectEntryFolder objectEntryFolder = findByERC_G_C(
			externalReferenceCode, groupId, companyId);

		return remove(objectEntryFolder);
	}

	/**
	 * Returns the number of object entry folders where externalReferenceCode = &#63; and groupId = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the number of matching object entry folders
	 */
	@Override
	public int countByERC_G_C(
		String externalReferenceCode, long groupId, long companyId) {

		return _uniquePersistenceFinderByERC_G_C.count(
			finderCache,
			new Object[] {externalReferenceCode, groupId, companyId});
	}

	private FinderPath _finderPathWithPaginationFindByG_C_P;
	private FinderPath _finderPathWithoutPaginationFindByG_C_P;
	private FinderPath _finderPathCountByG_C_P;
	private CollectionPersistenceFinder<ObjectEntryFolder>
		_collectionPersistenceFinderByG_C_P;

	/**
	 * Returns all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId) {

		return findByG_C_P(
			groupId, companyId, parentObjectEntryFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId, int start,
		int end) {

		return findByG_C_P(
			groupId, companyId, parentObjectEntryFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId, int start,
		int end, OrderByComparator<ObjectEntryFolder> orderByComparator) {

		return findByG_C_P(
			groupId, companyId, parentObjectEntryFolderId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId, int start,
		int end, OrderByComparator<ObjectEntryFolder> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_C_P.find(
			finderCache,
			new Object[] {groupId, companyId, parentObjectEntryFolderId}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder findByG_C_P_First(
			long groupId, long companyId, long parentObjectEntryFolderId,
			OrderByComparator<ObjectEntryFolder> orderByComparator)
		throws NoSuchObjectEntryFolderException {

		ObjectEntryFolder objectEntryFolder = fetchByG_C_P_First(
			groupId, companyId, parentObjectEntryFolderId, orderByComparator);

		if (objectEntryFolder != null) {
			return objectEntryFolder;
		}

		throw new NoSuchObjectEntryFolderException(
			_collectionPersistenceFinderByG_C_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, companyId, parentObjectEntryFolderId}));
	}

	/**
	 * Returns the first object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder fetchByG_C_P_First(
		long groupId, long companyId, long parentObjectEntryFolderId,
		OrderByComparator<ObjectEntryFolder> orderByComparator) {

		return _collectionPersistenceFinderByG_C_P.fetchFirst(
			finderCache,
			new Object[] {groupId, companyId, parentObjectEntryFolderId},
			orderByComparator);
	}

	/**
	 * Returns all the object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the matching object entry folders that the user has permission to view
	 */
	@Override
	public List<ObjectEntryFolder> filterFindByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId) {

		return filterFindByG_C_P(
			groupId, companyId, parentObjectEntryFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders that the user has permission to view
	 */
	@Override
	public List<ObjectEntryFolder> filterFindByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId, int start,
		int end) {

		return filterFindByG_C_P(
			groupId, companyId, parentObjectEntryFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object entry folders that the user has permissions to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders that the user has permission to view
	 */
	@Override
	public List<ObjectEntryFolder> filterFindByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId, int start,
		int end, OrderByComparator<ObjectEntryFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_P(
				groupId, companyId, parentObjectEntryFolderId, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_C_P(
					groupId, companyId, parentObjectEntryFolderId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTENTRYFOLDER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTENTRYFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_P_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_P_PARENTOBJECTENTRYFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTENTRYFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					ObjectEntryFolderModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectEntryFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectEntryFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectEntryFolderImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectEntryFolderImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(companyId);

			queryPos.add(parentObjectEntryFolderId);

			return (List<ObjectEntryFolder>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 */
	@Override
	public void removeByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId) {

		_collectionPersistenceFinderByG_C_P.remove(
			finderCache,
			new Object[] {groupId, companyId, parentObjectEntryFolderId});
	}

	/**
	 * Returns the number of object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the number of matching object entry folders
	 */
	@Override
	public int countByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId) {

		return _collectionPersistenceFinderByG_C_P.count(
			finderCache,
			new Object[] {groupId, companyId, parentObjectEntryFolderId});
	}

	/**
	 * Returns the number of object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @return the number of matching object entry folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_P(
		long groupId, long companyId, long parentObjectEntryFolderId) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_P(groupId, companyId, parentObjectEntryFolderId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectEntryFolder> objectEntryFolders = findByG_C_P(
				groupId, companyId, parentObjectEntryFolderId);

			objectEntryFolders = InlineSQLHelperUtil.filter(
				objectEntryFolders, groupId);

			return objectEntryFolders.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_OBJECTENTRYFOLDER_WHERE);

		sb.append(_FINDER_COLUMN_G_C_P_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_P_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_P_PARENTOBJECTENTRYFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectEntryFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(companyId);

			queryPos.add(parentObjectEntryFolderId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_C_P_GROUPID_2 =
		"objectEntryFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_P_COMPANYID_2 =
		"objectEntryFolder.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_C_P_PARENTOBJECTENTRYFOLDERID_2 =
			"objectEntryFolder.parentObjectEntryFolderId = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_LikeT;
	private FinderPath _finderPathWithPaginationCountByG_C_LikeT;
	private CollectionPersistenceFinder<ObjectEntryFolder>
		_collectionPersistenceFinderByG_C_LikeT;

	/**
	 * Returns all the object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_LikeT(
		long groupId, long companyId, String treePath) {

		return findByG_C_LikeT(
			groupId, companyId, treePath, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_LikeT(
		long groupId, long companyId, String treePath, int start, int end) {

		return findByG_C_LikeT(groupId, companyId, treePath, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_LikeT(
		long groupId, long companyId, String treePath, int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator) {

		return findByG_C_LikeT(
			groupId, companyId, treePath, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_LikeT(
		long groupId, long companyId, String treePath, int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_C_LikeT.find(
			finderCache, new Object[] {groupId, companyId, treePath}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder findByG_C_LikeT_First(
			long groupId, long companyId, String treePath,
			OrderByComparator<ObjectEntryFolder> orderByComparator)
		throws NoSuchObjectEntryFolderException {

		ObjectEntryFolder objectEntryFolder = fetchByG_C_LikeT_First(
			groupId, companyId, treePath, orderByComparator);

		if (objectEntryFolder != null) {
			return objectEntryFolder;
		}

		throw new NoSuchObjectEntryFolderException(
			_collectionPersistenceFinderByG_C_LikeT.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, companyId, treePath}));
	}

	/**
	 * Returns the first object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder fetchByG_C_LikeT_First(
		long groupId, long companyId, String treePath,
		OrderByComparator<ObjectEntryFolder> orderByComparator) {

		return _collectionPersistenceFinderByG_C_LikeT.fetchFirst(
			finderCache, new Object[] {groupId, companyId, treePath},
			orderByComparator);
	}

	/**
	 * Returns all the object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the matching object entry folders that the user has permission to view
	 */
	@Override
	public List<ObjectEntryFolder> filterFindByG_C_LikeT(
		long groupId, long companyId, String treePath) {

		return filterFindByG_C_LikeT(
			groupId, companyId, treePath, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders that the user has permission to view
	 */
	@Override
	public List<ObjectEntryFolder> filterFindByG_C_LikeT(
		long groupId, long companyId, String treePath, int start, int end) {

		return filterFindByG_C_LikeT(
			groupId, companyId, treePath, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object entry folders that the user has permissions to view where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders that the user has permission to view
	 */
	@Override
	public List<ObjectEntryFolder> filterFindByG_C_LikeT(
		long groupId, long companyId, String treePath, int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_LikeT(
				groupId, companyId, treePath, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_C_LikeT(
					groupId, companyId, treePath, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		treePath = Objects.toString(treePath, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTENTRYFOLDER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTENTRYFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_LIKET_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_LIKET_COMPANYID_2);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_LIKET_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			sb.append(_FINDER_COLUMN_G_C_LIKET_TREEPATH_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTENTRYFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					ObjectEntryFolderModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectEntryFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectEntryFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectEntryFolderImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectEntryFolderImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(companyId);

			if (bindTreePath) {
				queryPos.add(treePath);
			}

			return (List<ObjectEntryFolder>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 */
	@Override
	public void removeByG_C_LikeT(
		long groupId, long companyId, String treePath) {

		_collectionPersistenceFinderByG_C_LikeT.remove(
			finderCache, new Object[] {groupId, companyId, treePath});
	}

	/**
	 * Returns the number of object entry folders where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the number of matching object entry folders
	 */
	@Override
	public int countByG_C_LikeT(long groupId, long companyId, String treePath) {
		return _collectionPersistenceFinderByG_C_LikeT.count(
			finderCache, new Object[] {groupId, companyId, treePath});
	}

	/**
	 * Returns the number of object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and treePath LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param treePath the tree path
	 * @return the number of matching object entry folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_LikeT(
		long groupId, long companyId, String treePath) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_LikeT(groupId, companyId, treePath);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectEntryFolder> objectEntryFolders = findByG_C_LikeT(
				groupId, companyId, treePath);

			objectEntryFolders = InlineSQLHelperUtil.filter(
				objectEntryFolders, groupId);

			return objectEntryFolders.size();
		}

		treePath = Objects.toString(treePath, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_OBJECTENTRYFOLDER_WHERE);

		sb.append(_FINDER_COLUMN_G_C_LIKET_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_LIKET_COMPANYID_2);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_LIKET_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			sb.append(_FINDER_COLUMN_G_C_LIKET_TREEPATH_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectEntryFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(companyId);

			if (bindTreePath) {
				queryPos.add(treePath);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_C_LIKET_GROUPID_2 =
		"objectEntryFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_LIKET_COMPANYID_2 =
		"objectEntryFolder.companyId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_LIKET_TREEPATH_2 =
		"objectEntryFolder.treePath LIKE ?";

	private static final String _FINDER_COLUMN_G_C_LIKET_TREEPATH_3 =
		"(objectEntryFolder.treePath IS NULL OR objectEntryFolder.treePath LIKE '')";

	private FinderPath _finderPathWithPaginationFindByG_C_P_N_NotS;
	private FinderPath _finderPathWithPaginationCountByG_C_P_N_NotS;
	private CollectionPersistenceFinder<ObjectEntryFolder>
		_collectionPersistenceFinderByG_C_P_N_NotS;

	/**
	 * Returns all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 * @return the matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_P_N_NotS(
		long groupId, long companyId, long parentObjectEntryFolderId,
		String name, int status) {

		return findByG_C_P_N_NotS(
			groupId, companyId, parentObjectEntryFolderId, name, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_P_N_NotS(
		long groupId, long companyId, long parentObjectEntryFolderId,
		String name, int status, int start, int end) {

		return findByG_C_P_N_NotS(
			groupId, companyId, parentObjectEntryFolderId, name, status, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_P_N_NotS(
		long groupId, long companyId, long parentObjectEntryFolderId,
		String name, int status, int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator) {

		return findByG_C_P_N_NotS(
			groupId, companyId, parentObjectEntryFolderId, name, status, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findByG_C_P_N_NotS(
		long groupId, long companyId, long parentObjectEntryFolderId,
		String name, int status, int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_C_P_N_NotS.find(
			finderCache,
			new Object[] {
				groupId, companyId, parentObjectEntryFolderId, name, status
			},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder
	 * @throws NoSuchObjectEntryFolderException if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder findByG_C_P_N_NotS_First(
			long groupId, long companyId, long parentObjectEntryFolderId,
			String name, int status,
			OrderByComparator<ObjectEntryFolder> orderByComparator)
		throws NoSuchObjectEntryFolderException {

		ObjectEntryFolder objectEntryFolder = fetchByG_C_P_N_NotS_First(
			groupId, companyId, parentObjectEntryFolderId, name, status,
			orderByComparator);

		if (objectEntryFolder != null) {
			return objectEntryFolder;
		}

		throw new NoSuchObjectEntryFolderException(
			_collectionPersistenceFinderByG_C_P_N_NotS.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {
					groupId, companyId, parentObjectEntryFolderId, name, status
				}));
	}

	/**
	 * Returns the first object entry folder in the ordered set where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object entry folder, or <code>null</code> if a matching object entry folder could not be found
	 */
	@Override
	public ObjectEntryFolder fetchByG_C_P_N_NotS_First(
		long groupId, long companyId, long parentObjectEntryFolderId,
		String name, int status,
		OrderByComparator<ObjectEntryFolder> orderByComparator) {

		return _collectionPersistenceFinderByG_C_P_N_NotS.fetchFirst(
			finderCache,
			new Object[] {
				groupId, companyId, parentObjectEntryFolderId, name, status
			},
			orderByComparator);
	}

	/**
	 * Returns all the object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 * @return the matching object entry folders that the user has permission to view
	 */
	@Override
	public List<ObjectEntryFolder> filterFindByG_C_P_N_NotS(
		long groupId, long companyId, long parentObjectEntryFolderId,
		String name, int status) {

		return filterFindByG_C_P_N_NotS(
			groupId, companyId, parentObjectEntryFolderId, name, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of matching object entry folders that the user has permission to view
	 */
	@Override
	public List<ObjectEntryFolder> filterFindByG_C_P_N_NotS(
		long groupId, long companyId, long parentObjectEntryFolderId,
		String name, int status, int start, int end) {

		return filterFindByG_C_P_N_NotS(
			groupId, companyId, parentObjectEntryFolderId, name, status, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the object entry folders that the user has permissions to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object entry folders that the user has permission to view
	 */
	@Override
	public List<ObjectEntryFolder> filterFindByG_C_P_N_NotS(
		long groupId, long companyId, long parentObjectEntryFolderId,
		String name, int status, int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_P_N_NotS(
				groupId, companyId, parentObjectEntryFolderId, name, status,
				start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_C_P_N_NotS(
					groupId, companyId, parentObjectEntryFolderId, name, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTENTRYFOLDER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTENTRYFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_PARENTOBJECTENTRYFOLDERID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTENTRYFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					ObjectEntryFolderModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(ObjectEntryFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectEntryFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectEntryFolderImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectEntryFolderImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(companyId);

			queryPos.add(parentObjectEntryFolderId);

			if (bindName) {
				queryPos.add(name);
			}

			queryPos.add(status);

			return (List<ObjectEntryFolder>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes all the object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 */
	@Override
	public void removeByG_C_P_N_NotS(
		long groupId, long companyId, long parentObjectEntryFolderId,
		String name, int status) {

		_collectionPersistenceFinderByG_C_P_N_NotS.remove(
			finderCache,
			new Object[] {
				groupId, companyId, parentObjectEntryFolderId, name, status
			});
	}

	/**
	 * Returns the number of object entry folders where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 * @return the number of matching object entry folders
	 */
	@Override
	public int countByG_C_P_N_NotS(
		long groupId, long companyId, long parentObjectEntryFolderId,
		String name, int status) {

		return _collectionPersistenceFinderByG_C_P_N_NotS.count(
			finderCache,
			new Object[] {
				groupId, companyId, parentObjectEntryFolderId, name, status
			});
	}

	/**
	 * Returns the number of object entry folders that the user has permission to view where groupId = &#63; and companyId = &#63; and parentObjectEntryFolderId = &#63; and name = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentObjectEntryFolderId the parent object entry folder ID
	 * @param name the name
	 * @param status the status
	 * @return the number of matching object entry folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_P_N_NotS(
		long groupId, long companyId, long parentObjectEntryFolderId,
		String name, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_P_N_NotS(
				groupId, companyId, parentObjectEntryFolderId, name, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<ObjectEntryFolder> objectEntryFolders = findByG_C_P_N_NotS(
				groupId, companyId, parentObjectEntryFolderId, name, status);

			objectEntryFolders = InlineSQLHelperUtil.filter(
				objectEntryFolders, groupId);

			return objectEntryFolders.size();
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(6);

		sb.append(_FILTER_SQL_COUNT_OBJECTENTRYFOLDER_WHERE);

		sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_PARENTOBJECTENTRYFOLDERID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_C_P_N_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectEntryFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(companyId);

			queryPos.add(parentObjectEntryFolderId);

			if (bindName) {
				queryPos.add(name);
			}

			queryPos.add(status);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_C_P_N_NOTS_GROUPID_2 =
		"objectEntryFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_P_N_NOTS_COMPANYID_2 =
		"objectEntryFolder.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_C_P_N_NOTS_PARENTOBJECTENTRYFOLDERID_2 =
			"objectEntryFolder.parentObjectEntryFolderId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_P_N_NOTS_NAME_2 =
		"objectEntryFolder.name = ? AND ";

	private static final String _FINDER_COLUMN_G_C_P_N_NOTS_NAME_3 =
		"(objectEntryFolder.name IS NULL OR objectEntryFolder.name = '') AND ";

	private static final String _FINDER_COLUMN_G_C_P_N_NOTS_STATUS_2 =
		"objectEntryFolder.status != ?";

	public ObjectEntryFolderPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectEntryFolder.class);

		setModelImplClass(ObjectEntryFolderImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectEntryFolderTable.INSTANCE);
	}

	/**
	 * Caches the object entry folder in the entity cache if it is enabled.
	 *
	 * @param objectEntryFolder the object entry folder
	 */
	@Override
	public void cacheResult(ObjectEntryFolder objectEntryFolder) {
		entityCache.putResult(
			ObjectEntryFolderImpl.class, objectEntryFolder.getPrimaryKey(),
			objectEntryFolder);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				objectEntryFolder.getUuid(), objectEntryFolder.getGroupId()
			},
			objectEntryFolder);

		finderCache.putResult(
			_finderPathFetchByERC_G_C,
			new Object[] {
				objectEntryFolder.getExternalReferenceCode(),
				objectEntryFolder.getGroupId(), objectEntryFolder.getCompanyId()
			},
			objectEntryFolder);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object entry folders in the entity cache if it is enabled.
	 *
	 * @param objectEntryFolders the object entry folders
	 */
	@Override
	public void cacheResult(List<ObjectEntryFolder> objectEntryFolders) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectEntryFolders.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectEntryFolder objectEntryFolder : objectEntryFolders) {
			if (entityCache.getResult(
					ObjectEntryFolderImpl.class,
					objectEntryFolder.getPrimaryKey()) == null) {

				cacheResult(objectEntryFolder);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		ObjectEntryFolderModelImpl objectEntryFolderModelImpl) {

		Object[] args = new Object[] {
			objectEntryFolderModelImpl.getUuid(),
			objectEntryFolderModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathFetchByUUID_G, args, objectEntryFolderModelImpl);

		args = new Object[] {
			objectEntryFolderModelImpl.getExternalReferenceCode(),
			objectEntryFolderModelImpl.getGroupId(),
			objectEntryFolderModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_G_C, args, objectEntryFolderModelImpl);
	}

	/**
	 * Creates a new object entry folder with the primary key. Does not add the object entry folder to the database.
	 *
	 * @param objectEntryFolderId the primary key for the new object entry folder
	 * @return the new object entry folder
	 */
	@Override
	public ObjectEntryFolder create(long objectEntryFolderId) {
		ObjectEntryFolder objectEntryFolder = new ObjectEntryFolderImpl();

		objectEntryFolder.setNew(true);
		objectEntryFolder.setPrimaryKey(objectEntryFolderId);

		String uuid = PortalUUIDUtil.generate();

		objectEntryFolder.setUuid(uuid);

		objectEntryFolder.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectEntryFolder;
	}

	/**
	 * Removes the object entry folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectEntryFolderId the primary key of the object entry folder
	 * @return the object entry folder that was removed
	 * @throws NoSuchObjectEntryFolderException if a object entry folder with the primary key could not be found
	 */
	@Override
	public ObjectEntryFolder remove(long objectEntryFolderId)
		throws NoSuchObjectEntryFolderException {

		return remove((Serializable)objectEntryFolderId);
	}

	@Override
	protected ObjectEntryFolder removeImpl(
		ObjectEntryFolder objectEntryFolder) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectEntryFolder)) {
				objectEntryFolder = (ObjectEntryFolder)session.get(
					ObjectEntryFolderImpl.class,
					objectEntryFolder.getPrimaryKeyObj());
			}

			if (objectEntryFolder != null) {
				session.delete(objectEntryFolder);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectEntryFolder != null) {
			clearCache(objectEntryFolder);
		}

		return objectEntryFolder;
	}

	@Override
	public ObjectEntryFolder updateImpl(ObjectEntryFolder objectEntryFolder) {
		boolean isNew = objectEntryFolder.isNew();

		if (!(objectEntryFolder instanceof ObjectEntryFolderModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectEntryFolder.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectEntryFolder);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectEntryFolder proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectEntryFolder implementation " +
					objectEntryFolder.getClass());
		}

		ObjectEntryFolderModelImpl objectEntryFolderModelImpl =
			(ObjectEntryFolderModelImpl)objectEntryFolder;

		if (Validator.isNull(objectEntryFolder.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectEntryFolder.setUuid(uuid);
		}

		if (Validator.isNull(objectEntryFolder.getExternalReferenceCode())) {
			objectEntryFolder.setExternalReferenceCode(
				objectEntryFolder.getUuid());
		}
		else {
			if (!Objects.equals(
					objectEntryFolderModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					objectEntryFolder.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = objectEntryFolder.getCompanyId();

					long groupId = objectEntryFolder.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = objectEntryFolder.getPrimaryKey();
					}

					try {
						objectEntryFolder.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								ObjectEntryFolder.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								objectEntryFolder.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectEntryFolder.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectEntryFolder.setCreateDate(date);
			}
			else {
				objectEntryFolder.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectEntryFolderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectEntryFolder.setModifiedDate(date);
			}
			else {
				objectEntryFolder.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectEntryFolder);
			}
			else {
				objectEntryFolder = (ObjectEntryFolder)session.merge(
					objectEntryFolder);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectEntryFolderImpl.class, objectEntryFolderModelImpl, false,
			true);

		cacheUniqueFindersCache(objectEntryFolderModelImpl);

		if (isNew) {
			objectEntryFolder.setNew(false);
		}

		objectEntryFolder.resetOriginalValues();

		return objectEntryFolder;
	}

	/**
	 * Returns the object entry folder with the primary key or throws a <code>NoSuchObjectEntryFolderException</code> if it could not be found.
	 *
	 * @param objectEntryFolderId the primary key of the object entry folder
	 * @return the object entry folder
	 * @throws NoSuchObjectEntryFolderException if a object entry folder with the primary key could not be found
	 */
	@Override
	public ObjectEntryFolder findByPrimaryKey(long objectEntryFolderId)
		throws NoSuchObjectEntryFolderException {

		return findByPrimaryKey((Serializable)objectEntryFolderId);
	}

	/**
	 * Returns the object entry folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectEntryFolderId the primary key of the object entry folder
	 * @return the object entry folder, or <code>null</code> if a object entry folder with the primary key could not be found
	 */
	@Override
	public ObjectEntryFolder fetchByPrimaryKey(long objectEntryFolderId) {
		return fetchByPrimaryKey((Serializable)objectEntryFolderId);
	}

	/**
	 * Returns all the object entry folders.
	 *
	 * @return the object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object entry folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @return the range of object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object entry folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findAll(
		int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object entry folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectEntryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object entry folders
	 * @param end the upper bound of the range of object entry folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object entry folders
	 */
	@Override
	public List<ObjectEntryFolder> findAll(
		int start, int end,
		OrderByComparator<ObjectEntryFolder> orderByComparator,
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

		List<ObjectEntryFolder> list = null;

		if (useFinderCache) {
			list = (List<ObjectEntryFolder>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTENTRYFOLDER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTENTRYFOLDER;

				sql = sql.concat(ObjectEntryFolderModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectEntryFolder>)QueryUtil.list(
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
	 * Removes all the object entry folders from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectEntryFolder objectEntryFolder : findAll()) {
			remove(objectEntryFolder);
		}
	}

	/**
	 * Returns the number of object entry folders.
	 *
	 * @return the number of object entry folders
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OBJECTENTRYFOLDER);

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
		return "objectEntryFolderId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTENTRYFOLDER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectEntryFolderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object entry folder persistence.
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
			_SQL_SELECT_OBJECTENTRYFOLDER_WHERE,
			_SQL_COUNT_OBJECTENTRYFOLDER_WHERE,
			ObjectEntryFolderModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"objectEntryFolder.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, ObjectEntryFolder::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_OBJECTENTRYFOLDER_WHERE,
			new FinderColumn<>(
				"objectEntryFolder.", "uuid", FinderColumn.Type.STRING, "=",
				true, false, ObjectEntryFolder::getUuid),
			new FinderColumn<>(
				"objectEntryFolder.", "groupId", FinderColumn.Type.LONG, "=",
				true, true, ObjectEntryFolder::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_OBJECTENTRYFOLDER_WHERE,
				_SQL_COUNT_OBJECTENTRYFOLDER_WHERE,
				ObjectEntryFolderModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"objectEntryFolder.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, ObjectEntryFolder::getUuid),
				new FinderColumn<>(
					"objectEntryFolder.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, ObjectEntryFolder::getCompanyId));

		_finderPathFetchByERC_G_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_G_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			new String[] {"externalReferenceCode", "groupId", "companyId"},
			true);

		_uniquePersistenceFinderByERC_G_C = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_G_C,
			_SQL_SELECT_OBJECTENTRYFOLDER_WHERE,
			new FinderColumn<>(
				"objectEntryFolder.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				ObjectEntryFolder::getExternalReferenceCode),
			new FinderColumn<>(
				"objectEntryFolder.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, ObjectEntryFolder::getGroupId),
			new FinderColumn<>(
				"objectEntryFolder.", "companyId", FinderColumn.Type.LONG, "=",
				true, true, ObjectEntryFolder::getCompanyId));

		_finderPathWithPaginationFindByG_C_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId", "parentObjectEntryFolderId"},
			true);

		_finderPathWithoutPaginationFindByG_C_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "companyId", "parentObjectEntryFolderId"},
			true);

		_finderPathCountByG_C_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "companyId", "parentObjectEntryFolderId"},
			false);

		_collectionPersistenceFinderByG_C_P = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_C_P,
			_finderPathWithoutPaginationFindByG_C_P, _finderPathCountByG_C_P,
			_SQL_SELECT_OBJECTENTRYFOLDER_WHERE,
			_SQL_COUNT_OBJECTENTRYFOLDER_WHERE,
			ObjectEntryFolderModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"objectEntryFolder.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, ObjectEntryFolder::getGroupId),
			new FinderColumn<>(
				"objectEntryFolder.", "companyId", FinderColumn.Type.LONG, "=",
				true, false, ObjectEntryFolder::getCompanyId),
			new FinderColumn<>(
				"objectEntryFolder.", "parentObjectEntryFolderId",
				FinderColumn.Type.LONG, "=", true, true,
				ObjectEntryFolder::getParentObjectEntryFolderId));

		_finderPathWithPaginationFindByG_C_LikeT = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_LikeT",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId", "treePath"}, true);

		_finderPathWithPaginationCountByG_C_LikeT = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_LikeT",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "companyId", "treePath"}, false);

		_collectionPersistenceFinderByG_C_LikeT =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_C_LikeT, null,
				_finderPathWithPaginationCountByG_C_LikeT,
				_SQL_SELECT_OBJECTENTRYFOLDER_WHERE,
				_SQL_COUNT_OBJECTENTRYFOLDER_WHERE,
				ObjectEntryFolderModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"objectEntryFolder.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, ObjectEntryFolder::getGroupId),
				new FinderColumn<>(
					"objectEntryFolder.", "companyId", FinderColumn.Type.LONG,
					"=", true, false, ObjectEntryFolder::getCompanyId),
				new FinderColumn<>(
					"objectEntryFolder.", "treePath", FinderColumn.Type.STRING,
					"LIKE", true, true, ObjectEntryFolder::getTreePath));

		_finderPathWithPaginationFindByG_C_P_N_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_P_N_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "companyId", "parentObjectEntryFolderId", "name",
				"status"
			},
			true);

		_finderPathWithPaginationCountByG_C_P_N_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_P_N_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {
				"groupId", "companyId", "parentObjectEntryFolderId", "name",
				"status"
			},
			false);

		_collectionPersistenceFinderByG_C_P_N_NotS =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_C_P_N_NotS, null,
				_finderPathWithPaginationCountByG_C_P_N_NotS,
				_SQL_SELECT_OBJECTENTRYFOLDER_WHERE,
				_SQL_COUNT_OBJECTENTRYFOLDER_WHERE,
				ObjectEntryFolderModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"objectEntryFolder.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, ObjectEntryFolder::getGroupId),
				new FinderColumn<>(
					"objectEntryFolder.", "companyId", FinderColumn.Type.LONG,
					"=", true, false, ObjectEntryFolder::getCompanyId),
				new FinderColumn<>(
					"objectEntryFolder.", "parentObjectEntryFolderId",
					FinderColumn.Type.LONG, "=", true, false,
					ObjectEntryFolder::getParentObjectEntryFolderId),
				new FinderColumn<>(
					"objectEntryFolder.", "name", FinderColumn.Type.STRING, "=",
					true, false, ObjectEntryFolder::getName),
				new FinderColumn<>(
					"objectEntryFolder.", "status", FinderColumn.Type.INTEGER,
					"!=", true, true, ObjectEntryFolder::getStatus));

		ObjectEntryFolderUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ObjectEntryFolderUtil.setPersistence(null);

		entityCache.removeCache(ObjectEntryFolderImpl.class.getName());
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OBJECTENTRYFOLDER =
		"SELECT objectEntryFolder FROM ObjectEntryFolder objectEntryFolder";

	private static final String _SQL_SELECT_OBJECTENTRYFOLDER_WHERE =
		"SELECT objectEntryFolder FROM ObjectEntryFolder objectEntryFolder WHERE ";

	private static final String _SQL_COUNT_OBJECTENTRYFOLDER =
		"SELECT COUNT(objectEntryFolder) FROM ObjectEntryFolder objectEntryFolder";

	private static final String _SQL_COUNT_OBJECTENTRYFOLDER_WHERE =
		"SELECT COUNT(objectEntryFolder) FROM ObjectEntryFolder objectEntryFolder WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"objectEntryFolder.objectEntryFolderId";

	private static final String _FILTER_SQL_SELECT_OBJECTENTRYFOLDER_WHERE =
		"SELECT DISTINCT {objectEntryFolder.*} FROM ObjectEntryFolder objectEntryFolder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OBJECTENTRYFOLDER_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {ObjectEntryFolder.*} FROM (SELECT DISTINCT objectEntryFolder.objectEntryFolderId FROM ObjectEntryFolder objectEntryFolder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OBJECTENTRYFOLDER_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN ObjectEntryFolder ON TEMP_TABLE.objectEntryFolderId = ObjectEntryFolder.objectEntryFolderId";

	private static final String _FILTER_SQL_COUNT_OBJECTENTRYFOLDER_WHERE =
		"SELECT COUNT(DISTINCT objectEntryFolder.objectEntryFolderId) AS COUNT_VALUE FROM ObjectEntryFolder objectEntryFolder WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "objectEntryFolder";

	private static final String _FILTER_ENTITY_TABLE = "ObjectEntryFolder";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectEntryFolder.";

	private static final String _ORDER_BY_ENTITY_TABLE = "ObjectEntryFolder.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectEntryFolder exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryFolderPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1100410535