/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service.persistence.impl;

import com.liferay.journal.exception.DuplicateJournalFolderExternalReferenceCodeException;
import com.liferay.journal.exception.NoSuchFolderException;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderTable;
import com.liferay.journal.model.impl.JournalFolderImpl;
import com.liferay.journal.model.impl.JournalFolderModelImpl;
import com.liferay.journal.service.persistence.JournalFolderPersistence;
import com.liferay.journal.service.persistence.JournalFolderUtil;
import com.liferay.journal.service.persistence.impl.constants.JournalPersistenceConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * The persistence implementation for the journal folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = JournalFolderPersistence.class)
public class JournalFolderPersistenceImpl
	extends BasePersistenceImpl<JournalFolder>
	implements JournalFolderPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>JournalFolderUtil</code> to access the journal folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		JournalFolderImpl.class.getName();

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
	private CollectionPersistenceFinder<JournalFolder>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the journal folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal folders
	 */
	@Override
	public List<JournalFolder> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first journal folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByUuid_First(
			String uuid, OrderByComparator<JournalFolder> orderByComparator)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByUuid_First(
			uuid, orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		throw new NoSuchFolderException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first journal folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByUuid_First(
		String uuid, OrderByComparator<JournalFolder> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the journal folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of journal folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<JournalFolder>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the journal folder where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByUUID_G(uuid, groupId);

		if (journalFolder == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFolderException(message);
		}

		return journalFolder;
	}

	/**
	 * Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the journal folder where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the journal folder that was removed
	 */
	@Override
	public JournalFolder removeByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException {

		JournalFolder journalFolder = findByUUID_G(uuid, groupId);

		return remove(journalFolder);
	}

	/**
	 * Returns the number of journal folders where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<JournalFolder>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the journal folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching journal folders
	 */
	@Override
	public List<JournalFolder> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<JournalFolder> orderByComparator)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		throw new NoSuchFolderException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<JournalFolder> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the journal folders where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of journal folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<JournalFolder>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the journal folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal folders
	 */
	@Override
	public List<JournalFolder> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				finderCache, new Object[] {groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByGroupId_First(
			long groupId, OrderByComparator<JournalFolder> orderByComparator)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByGroupId_First(
			groupId, orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		throw new NoSuchFolderException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first journal folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByGroupId_First(
		long groupId, OrderByComparator<JournalFolder> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Returns all the journal folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALFOLDER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(JournalFolderModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalFolderImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalFolderImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<JournalFolder>)QueryUtil.list(
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
	 * Removes all the journal folders where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of journal folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				finderCache, new Object[] {groupId});
		}
	}

	/**
	 * Returns the number of journal folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal folders that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalFolder> journalFolders = findByGroupId(groupId);

			journalFolders = InlineSQLHelperUtil.filter(
				journalFolders, groupId);

			return journalFolders.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_JOURNALFOLDER_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"journalFolder.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<JournalFolder>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the journal folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching journal folders
	 */
	@Override
	public List<JournalFolder> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				finderCache, new Object[] {companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByCompanyId_First(
			long companyId, OrderByComparator<JournalFolder> orderByComparator)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		throw new NoSuchFolderException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first journal folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByCompanyId_First(
		long companyId, OrderByComparator<JournalFolder> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the journal folders where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of journal folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				finderCache, new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P;
	private FinderPath _finderPathCountByG_P;
	private CollectionPersistenceFinder<JournalFolder>
		_collectionPersistenceFinderByG_P;

	/**
	 * Returns all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P(long groupId, long parentFolderId) {
		return findByG_P(
			groupId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end) {

		return findByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		return findByG_P(
			groupId, parentFolderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByG_P.find(
				finderCache, new Object[] {groupId, parentFolderId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByG_P_First(
			long groupId, long parentFolderId,
			OrderByComparator<JournalFolder> orderByComparator)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByG_P_First(
			groupId, parentFolderId, orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		throw new NoSuchFolderException(
			_collectionPersistenceFinderByG_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, parentFolderId}));
	}

	/**
	 * Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByG_P_First(
		long groupId, long parentFolderId,
		OrderByComparator<JournalFolder> orderByComparator) {

		return _collectionPersistenceFinderByG_P.fetchFirst(
			finderCache, new Object[] {groupId, parentFolderId},
			orderByComparator);
	}

	/**
	 * Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByG_P(
		long groupId, long parentFolderId) {

		return filterFindByG_P(
			groupId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end) {

		return filterFindByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(
				groupId, parentFolderId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_P(
					groupId, parentFolderId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALFOLDER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_P_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(JournalFolderModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalFolderImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalFolderImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(parentFolderId);

			return (List<JournalFolder>)QueryUtil.list(
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
	 * Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 */
	@Override
	public void removeByG_P(long groupId, long parentFolderId) {
		_collectionPersistenceFinderByG_P.remove(
			finderCache, new Object[] {groupId, parentFolderId});
	}

	/**
	 * Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByG_P(long groupId, long parentFolderId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByG_P.count(
				finderCache, new Object[] {groupId, parentFolderId});
		}
	}

	/**
	 * Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching journal folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_P(long groupId, long parentFolderId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentFolderId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalFolder> journalFolders = findByG_P(
				groupId, parentFolderId);

			journalFolders = InlineSQLHelperUtil.filter(
				journalFolders, groupId);

			return journalFolders.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_JOURNALFOLDER_WHERE);

		sb.append(_FINDER_COLUMN_G_P_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(parentFolderId);

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

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 =
		"journalFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_PARENTFOLDERID_2 =
		"journalFolder.parentFolderId = ?";

	private FinderPath _finderPathFetchByG_N;
	private UniquePersistenceFinder<JournalFolder>
		_uniquePersistenceFinderByG_N;

	/**
	 * Returns the journal folder where groupId = &#63; and name = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByG_N(long groupId, String name)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByG_N(groupId, name);

		if (journalFolder == null) {
			String message =
				_uniquePersistenceFinderByG_N.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, name});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFolderException(message);
		}

		return journalFolder;
	}

	/**
	 * Returns the journal folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the journal folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByG_N(
		long groupId, String name, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _uniquePersistenceFinderByG_N.fetch(
				finderCache, new Object[] {groupId, name}, useFinderCache);
		}
	}

	/**
	 * Removes the journal folder where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the journal folder that was removed
	 */
	@Override
	public JournalFolder removeByG_N(long groupId, String name)
		throws NoSuchFolderException {

		JournalFolder journalFolder = findByG_N(groupId, name);

		return remove(journalFolder);
	}

	/**
	 * Returns the number of journal folders where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		return _uniquePersistenceFinderByG_N.count(
			finderCache, new Object[] {groupId, name});
	}

	private FinderPath _finderPathWithPaginationFindByC_NotS;
	private FinderPath _finderPathWithPaginationCountByC_NotS;
	private CollectionPersistenceFinder<JournalFolder>
		_collectionPersistenceFinderByC_NotS;

	/**
	 * Returns all the journal folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching journal folders
	 */
	@Override
	public List<JournalFolder> findByC_NotS(long companyId, int status) {
		return findByC_NotS(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByC_NotS(
		long companyId, int status, int start, int end) {

		return findByC_NotS(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		return findByC_NotS(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByC_NotS.find(
				finderCache, new Object[] {companyId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByC_NotS_First(
			long companyId, int status,
			OrderByComparator<JournalFolder> orderByComparator)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByC_NotS_First(
			companyId, status, orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		throw new NoSuchFolderException(
			_collectionPersistenceFinderByC_NotS.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, status}));
	}

	/**
	 * Returns the first journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByC_NotS_First(
		long companyId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {

		return _collectionPersistenceFinderByC_NotS.fetchFirst(
			finderCache, new Object[] {companyId, status}, orderByComparator);
	}

	/**
	 * Removes all the journal folders where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_NotS(long companyId, int status) {
		_collectionPersistenceFinderByC_NotS.remove(
			finderCache, new Object[] {companyId, status});
	}

	/**
	 * Returns the number of journal folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByC_NotS(long companyId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByC_NotS.count(
				finderCache, new Object[] {companyId, status});
		}
	}

	private FinderPath _finderPathFetchByG_P_N;
	private UniquePersistenceFinder<JournalFolder>
		_uniquePersistenceFinderByG_P_N;

	/**
	 * Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByG_P_N(
			long groupId, long parentFolderId, String name)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByG_P_N(
			groupId, parentFolderId, name);

		if (journalFolder == null) {
			String message =
				_uniquePersistenceFinderByG_P_N.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, parentFolderId, name});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFolderException(message);
		}

		return journalFolder;
	}

	/**
	 * Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByG_P_N(
		long groupId, long parentFolderId, String name) {

		return fetchByG_P_N(groupId, parentFolderId, name, true);
	}

	/**
	 * Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByG_P_N(
		long groupId, long parentFolderId, String name,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _uniquePersistenceFinderByG_P_N.fetch(
				finderCache, new Object[] {groupId, parentFolderId, name},
				useFinderCache);
		}
	}

	/**
	 * Removes the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the journal folder that was removed
	 */
	@Override
	public JournalFolder removeByG_P_N(
			long groupId, long parentFolderId, String name)
		throws NoSuchFolderException {

		JournalFolder journalFolder = findByG_P_N(
			groupId, parentFolderId, name);

		return remove(journalFolder);
	}

	/**
	 * Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByG_P_N(long groupId, long parentFolderId, String name) {
		return _uniquePersistenceFinderByG_P_N.count(
			finderCache, new Object[] {groupId, parentFolderId, name});
	}

	private FinderPath _finderPathWithPaginationFindByG_P_S;
	private FinderPath _finderPathWithoutPaginationFindByG_P_S;
	private FinderPath _finderPathCountByG_P_S;
	private CollectionPersistenceFinder<JournalFolder>
		_collectionPersistenceFinderByG_P_S;

	/**
	 * Returns all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P_S(
		long groupId, long parentFolderId, int status) {

		return findByG_P_S(
			groupId, parentFolderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end) {

		return findByG_P_S(groupId, parentFolderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		return findByG_P_S(
			groupId, parentFolderId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByG_P_S.find(
				finderCache, new Object[] {groupId, parentFolderId, status},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByG_P_S_First(
			long groupId, long parentFolderId, int status,
			OrderByComparator<JournalFolder> orderByComparator)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByG_P_S_First(
			groupId, parentFolderId, status, orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		throw new NoSuchFolderException(
			_collectionPersistenceFinderByG_P_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, parentFolderId, status}));
	}

	/**
	 * Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByG_P_S_First(
		long groupId, long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {

		return _collectionPersistenceFinderByG_P_S.fetchFirst(
			finderCache, new Object[] {groupId, parentFolderId, status},
			orderByComparator);
	}

	/**
	 * Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByG_P_S(
		long groupId, long parentFolderId, int status) {

		return filterFindByG_P_S(
			groupId, parentFolderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end) {

		return filterFindByG_P_S(
			groupId, parentFolderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_S(
				groupId, parentFolderId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_P_S(
					groupId, parentFolderId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
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
			sb.append(_FILTER_SQL_SELECT_JOURNALFOLDER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_P_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_P_S_PARENTFOLDERID_2);

		sb.append(_FINDER_COLUMN_G_P_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(JournalFolderModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalFolderImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalFolderImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(parentFolderId);

			queryPos.add(status);

			return (List<JournalFolder>)QueryUtil.list(
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
	 * Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 */
	@Override
	public void removeByG_P_S(long groupId, long parentFolderId, int status) {
		_collectionPersistenceFinderByG_P_S.remove(
			finderCache, new Object[] {groupId, parentFolderId, status});
	}

	/**
	 * Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByG_P_S(long groupId, long parentFolderId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByG_P_S.count(
				finderCache, new Object[] {groupId, parentFolderId, status});
		}
	}

	/**
	 * Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching journal folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_P_S(
		long groupId, long parentFolderId, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_S(groupId, parentFolderId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalFolder> journalFolders = findByG_P_S(
				groupId, parentFolderId, status);

			journalFolders = InlineSQLHelperUtil.filter(
				journalFolders, groupId);

			return journalFolders.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALFOLDER_WHERE);

		sb.append(_FINDER_COLUMN_G_P_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_P_S_PARENTFOLDERID_2);

		sb.append(_FINDER_COLUMN_G_P_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(parentFolderId);

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

	private static final String _FINDER_COLUMN_G_P_S_GROUPID_2 =
		"journalFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_S_PARENTFOLDERID_2 =
		"journalFolder.parentFolderId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_S_STATUS_2 =
		"journalFolder.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_NotS;
	private FinderPath _finderPathWithPaginationCountByG_P_NotS;
	private CollectionPersistenceFinder<JournalFolder>
		_collectionPersistenceFinderByG_P_NotS;

	/**
	 * Returns all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P_NotS(
		long groupId, long parentFolderId, int status) {

		return findByG_P_NotS(
			groupId, parentFolderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end) {

		return findByG_P_NotS(
			groupId, parentFolderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		return findByG_P_NotS(
			groupId, parentFolderId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByG_P_NotS.find(
				finderCache, new Object[] {groupId, parentFolderId, status},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByG_P_NotS_First(
			long groupId, long parentFolderId, int status,
			OrderByComparator<JournalFolder> orderByComparator)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByG_P_NotS_First(
			groupId, parentFolderId, status, orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		throw new NoSuchFolderException(
			_collectionPersistenceFinderByG_P_NotS.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, parentFolderId, status}));
	}

	/**
	 * Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByG_P_NotS_First(
		long groupId, long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {

		return _collectionPersistenceFinderByG_P_NotS.fetchFirst(
			finderCache, new Object[] {groupId, parentFolderId, status},
			orderByComparator);
	}

	/**
	 * Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByG_P_NotS(
		long groupId, long parentFolderId, int status) {

		return filterFindByG_P_NotS(
			groupId, parentFolderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end) {

		return filterFindByG_P_NotS(
			groupId, parentFolderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders that the user has permission to view
	 */
	@Override
	public List<JournalFolder> filterFindByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_NotS(
				groupId, parentFolderId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_P_NotS(
					groupId, parentFolderId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
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
			sb.append(_FILTER_SQL_SELECT_JOURNALFOLDER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_P_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_P_NOTS_PARENTFOLDERID_2);

		sb.append(_FINDER_COLUMN_G_P_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(JournalFolderModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalFolderImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalFolderImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(parentFolderId);

			queryPos.add(status);

			return (List<JournalFolder>)QueryUtil.list(
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
	 * Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 */
	@Override
	public void removeByG_P_NotS(
		long groupId, long parentFolderId, int status) {

		_collectionPersistenceFinderByG_P_NotS.remove(
			finderCache, new Object[] {groupId, parentFolderId, status});
	}

	/**
	 * Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByG_P_NotS(long groupId, long parentFolderId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByG_P_NotS.count(
				finderCache, new Object[] {groupId, parentFolderId, status});
		}
	}

	/**
	 * Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching journal folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_P_NotS(
		long groupId, long parentFolderId, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_NotS(groupId, parentFolderId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalFolder> journalFolders = findByG_P_NotS(
				groupId, parentFolderId, status);

			journalFolders = InlineSQLHelperUtil.filter(
				journalFolders, groupId);

			return journalFolders.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALFOLDER_WHERE);

		sb.append(_FINDER_COLUMN_G_P_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_P_NOTS_PARENTFOLDERID_2);

		sb.append(_FINDER_COLUMN_G_P_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(parentFolderId);

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

	private static final String _FINDER_COLUMN_G_P_NOTS_GROUPID_2 =
		"journalFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_NOTS_PARENTFOLDERID_2 =
		"journalFolder.parentFolderId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_NOTS_STATUS_2 =
		"journalFolder.status != ?";

	private FinderPath _finderPathWithPaginationFindByGtF_C_P_NotS;
	private FinderPath _finderPathWithPaginationCountByGtF_C_P_NotS;
	private CollectionPersistenceFinder<JournalFolder>
		_collectionPersistenceFinderByGtF_C_P_NotS;

	/**
	 * Returns all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching journal folders
	 */
	@Override
	public List<JournalFolder> findByGtF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status) {

		return findByGtF_C_P_NotS(
			folderId, companyId, parentFolderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByGtF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status,
		int start, int end) {

		return findByGtF_C_P_NotS(
			folderId, companyId, parentFolderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByGtF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status,
		int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		return findByGtF_C_P_NotS(
			folderId, companyId, parentFolderId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal folders
	 */
	@Override
	public List<JournalFolder> findByGtF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status,
		int start, int end, OrderByComparator<JournalFolder> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByGtF_C_P_NotS.find(
				finderCache,
				new Object[] {folderId, companyId, parentFolderId, status},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByGtF_C_P_NotS_First(
			long folderId, long companyId, long parentFolderId, int status,
			OrderByComparator<JournalFolder> orderByComparator)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByGtF_C_P_NotS_First(
			folderId, companyId, parentFolderId, status, orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		throw new NoSuchFolderException(
			_collectionPersistenceFinderByGtF_C_P_NotS.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {folderId, companyId, parentFolderId, status}));
	}

	/**
	 * Returns the first journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByGtF_C_P_NotS_First(
		long folderId, long companyId, long parentFolderId, int status,
		OrderByComparator<JournalFolder> orderByComparator) {

		return _collectionPersistenceFinderByGtF_C_P_NotS.fetchFirst(
			finderCache,
			new Object[] {folderId, companyId, parentFolderId, status},
			orderByComparator);
	}

	/**
	 * Removes all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 */
	@Override
	public void removeByGtF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status) {

		_collectionPersistenceFinderByGtF_C_P_NotS.remove(
			finderCache,
			new Object[] {folderId, companyId, parentFolderId, status});
	}

	/**
	 * Returns the number of journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByGtF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _collectionPersistenceFinderByGtF_C_P_NotS.count(
				finderCache,
				new Object[] {folderId, companyId, parentFolderId, status});
		}
	}

	private FinderPath _finderPathFetchByERC_G;
	private UniquePersistenceFinder<JournalFolder>
		_uniquePersistenceFinderByERC_G;

	/**
	 * Returns the journal folder where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching journal folder
	 * @throws NoSuchFolderException if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder findByERC_G(String externalReferenceCode, long groupId)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByERC_G(
			externalReferenceCode, groupId);

		if (journalFolder == null) {
			String message =
				_uniquePersistenceFinderByERC_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchFolderException(message);
		}

		return journalFolder;
	}

	/**
	 * Returns the journal folder where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByERC_G(
		String externalReferenceCode, long groupId) {

		return fetchByERC_G(externalReferenceCode, groupId, true);
	}

	/**
	 * Returns the journal folder where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	@Override
	public JournalFolder fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			return _uniquePersistenceFinderByERC_G.fetch(
				finderCache, new Object[] {externalReferenceCode, groupId},
				useFinderCache);
		}
	}

	/**
	 * Removes the journal folder where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the journal folder that was removed
	 */
	@Override
	public JournalFolder removeByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchFolderException {

		JournalFolder journalFolder = findByERC_G(
			externalReferenceCode, groupId);

		return remove(journalFolder);
	}

	/**
	 * Returns the number of journal folders where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching journal folders
	 */
	@Override
	public int countByERC_G(String externalReferenceCode, long groupId) {
		return _uniquePersistenceFinderByERC_G.count(
			finderCache, new Object[] {externalReferenceCode, groupId});
	}

	public JournalFolderPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(JournalFolder.class);

		setModelImplClass(JournalFolderImpl.class);
		setModelPKClass(long.class);

		setTable(JournalFolderTable.INSTANCE);
	}

	/**
	 * Caches the journal folder in the entity cache if it is enabled.
	 *
	 * @param journalFolder the journal folder
	 */
	@Override
	public void cacheResult(JournalFolder journalFolder) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					journalFolder.getCtCollectionId())) {

			entityCache.putResult(
				JournalFolderImpl.class, journalFolder.getPrimaryKey(),
				journalFolder);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					journalFolder.getUuid(), journalFolder.getGroupId()
				},
				journalFolder);

			finderCache.putResult(
				_finderPathFetchByG_N,
				new Object[] {
					journalFolder.getGroupId(), journalFolder.getName()
				},
				journalFolder);

			finderCache.putResult(
				_finderPathFetchByG_P_N,
				new Object[] {
					journalFolder.getGroupId(),
					journalFolder.getParentFolderId(), journalFolder.getName()
				},
				journalFolder);

			finderCache.putResult(
				_finderPathFetchByERC_G,
				new Object[] {
					journalFolder.getExternalReferenceCode(),
					journalFolder.getGroupId()
				},
				journalFolder);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the journal folders in the entity cache if it is enabled.
	 *
	 * @param journalFolders the journal folders
	 */
	@Override
	public void cacheResult(List<JournalFolder> journalFolders) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (journalFolders.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (JournalFolder journalFolder : journalFolders) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						journalFolder.getCtCollectionId())) {

				if (entityCache.getResult(
						JournalFolderImpl.class,
						journalFolder.getPrimaryKey()) == null) {

					cacheResult(journalFolder);
				}
			}
		}
	}

	/**
	 * Clears the cache for all journal folders.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(JournalFolderImpl.class);

		finderCache.clearCache(JournalFolderImpl.class);
	}

	/**
	 * Clears the cache for the journal folder.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalFolder journalFolder) {
		entityCache.removeResult(JournalFolderImpl.class, journalFolder);
	}

	@Override
	public void clearCache(List<JournalFolder> journalFolders) {
		for (JournalFolder journalFolder : journalFolders) {
			entityCache.removeResult(JournalFolderImpl.class, journalFolder);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(JournalFolderImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(JournalFolderImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		JournalFolderModelImpl journalFolderModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					journalFolderModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				journalFolderModelImpl.getUuid(),
				journalFolderModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, journalFolderModelImpl);

			args = new Object[] {
				journalFolderModelImpl.getGroupId(),
				journalFolderModelImpl.getName()
			};

			finderCache.putResult(
				_finderPathFetchByG_N, args, journalFolderModelImpl);

			args = new Object[] {
				journalFolderModelImpl.getGroupId(),
				journalFolderModelImpl.getParentFolderId(),
				journalFolderModelImpl.getName()
			};

			finderCache.putResult(
				_finderPathFetchByG_P_N, args, journalFolderModelImpl);

			args = new Object[] {
				journalFolderModelImpl.getExternalReferenceCode(),
				journalFolderModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByERC_G, args, journalFolderModelImpl);
		}
	}

	/**
	 * Creates a new journal folder with the primary key. Does not add the journal folder to the database.
	 *
	 * @param folderId the primary key for the new journal folder
	 * @return the new journal folder
	 */
	@Override
	public JournalFolder create(long folderId) {
		JournalFolder journalFolder = new JournalFolderImpl();

		journalFolder.setNew(true);
		journalFolder.setPrimaryKey(folderId);

		String uuid = PortalUUIDUtil.generate();

		journalFolder.setUuid(uuid);

		journalFolder.setCompanyId(CompanyThreadLocal.getCompanyId());

		return journalFolder;
	}

	/**
	 * Removes the journal folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param folderId the primary key of the journal folder
	 * @return the journal folder that was removed
	 * @throws NoSuchFolderException if a journal folder with the primary key could not be found
	 */
	@Override
	public JournalFolder remove(long folderId) throws NoSuchFolderException {
		return remove((Serializable)folderId);
	}

	/**
	 * Removes the journal folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal folder
	 * @return the journal folder that was removed
	 * @throws NoSuchFolderException if a journal folder with the primary key could not be found
	 */
	@Override
	public JournalFolder remove(Serializable primaryKey)
		throws NoSuchFolderException {

		Session session = null;

		try {
			session = openSession();

			JournalFolder journalFolder = (JournalFolder)session.get(
				JournalFolderImpl.class, primaryKey);

			if (journalFolder == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFolderException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(journalFolder);
		}
		catch (NoSuchFolderException noSuchEntityException) {
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
	protected JournalFolder removeImpl(JournalFolder journalFolder) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(journalFolder)) {
				journalFolder = (JournalFolder)session.get(
					JournalFolderImpl.class, journalFolder.getPrimaryKeyObj());
			}

			if ((journalFolder != null) &&
				ctPersistenceHelper.isRemove(journalFolder)) {

				session.delete(journalFolder);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (journalFolder != null) {
			clearCache(journalFolder);
		}

		return journalFolder;
	}

	@Override
	public JournalFolder updateImpl(JournalFolder journalFolder) {
		boolean isNew = journalFolder.isNew();

		if (!(journalFolder instanceof JournalFolderModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(journalFolder.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					journalFolder);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in journalFolder proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom JournalFolder implementation " +
					journalFolder.getClass());
		}

		JournalFolderModelImpl journalFolderModelImpl =
			(JournalFolderModelImpl)journalFolder;

		if (Validator.isNull(journalFolder.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalFolder.setUuid(uuid);
		}

		if (Validator.isNull(journalFolder.getExternalReferenceCode())) {
			journalFolder.setExternalReferenceCode(journalFolder.getUuid());
		}
		else {
			if (!Objects.equals(
					journalFolderModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					journalFolder.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = journalFolder.getCompanyId();

					long groupId = journalFolder.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = journalFolder.getPrimaryKey();
					}

					try {
						journalFolder.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								JournalFolder.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								journalFolder.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			JournalFolder ercJournalFolder = fetchByERC_G(
				journalFolder.getExternalReferenceCode(),
				journalFolder.getGroupId());

			if (isNew) {
				if (ercJournalFolder != null) {
					throw new DuplicateJournalFolderExternalReferenceCodeException(
						"Duplicate journal folder with external reference code " +
							journalFolder.getExternalReferenceCode() +
								" and group " + journalFolder.getGroupId());
				}
			}
			else {
				if ((ercJournalFolder != null) &&
					(journalFolder.getFolderId() !=
						ercJournalFolder.getFolderId())) {

					throw new DuplicateJournalFolderExternalReferenceCodeException(
						"Duplicate journal folder with external reference code " +
							journalFolder.getExternalReferenceCode() +
								" and group " + journalFolder.getGroupId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (journalFolder.getCreateDate() == null)) {
			if (serviceContext == null) {
				journalFolder.setCreateDate(date);
			}
			else {
				journalFolder.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!journalFolderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				journalFolder.setModifiedDate(date);
			}
			else {
				journalFolder.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(journalFolder)) {
				if (!isNew) {
					session.evict(
						JournalFolderImpl.class,
						journalFolder.getPrimaryKeyObj());
				}

				session.save(journalFolder);
			}
			else {
				journalFolder = (JournalFolder)session.merge(journalFolder);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			JournalFolderImpl.class, journalFolderModelImpl, false, true);

		cacheUniqueFindersCache(journalFolderModelImpl);

		if (isNew) {
			journalFolder.setNew(false);
		}

		journalFolder.resetOriginalValues();

		return journalFolder;
	}

	/**
	 * Returns the journal folder with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal folder
	 * @return the journal folder
	 * @throws NoSuchFolderException if a journal folder with the primary key could not be found
	 */
	@Override
	public JournalFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFolderException {

		JournalFolder journalFolder = fetchByPrimaryKey(primaryKey);

		if (journalFolder == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFolderException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return journalFolder;
	}

	/**
	 * Returns the journal folder with the primary key or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param folderId the primary key of the journal folder
	 * @return the journal folder
	 * @throws NoSuchFolderException if a journal folder with the primary key could not be found
	 */
	@Override
	public JournalFolder findByPrimaryKey(long folderId)
		throws NoSuchFolderException {

		return findByPrimaryKey((Serializable)folderId);
	}

	/**
	 * Returns the journal folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal folder
	 * @return the journal folder, or <code>null</code> if a journal folder with the primary key could not be found
	 */
	@Override
	public JournalFolder fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				JournalFolder.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		JournalFolder journalFolder = (JournalFolder)entityCache.getResult(
			JournalFolderImpl.class, primaryKey);

		if (journalFolder != null) {
			return journalFolder;
		}

		Session session = null;

		try {
			session = openSession();

			journalFolder = (JournalFolder)session.get(
				JournalFolderImpl.class, primaryKey);

			if (journalFolder != null) {
				cacheResult(journalFolder);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return journalFolder;
	}

	/**
	 * Returns the journal folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param folderId the primary key of the journal folder
	 * @return the journal folder, or <code>null</code> if a journal folder with the primary key could not be found
	 */
	@Override
	public JournalFolder fetchByPrimaryKey(long folderId) {
		return fetchByPrimaryKey((Serializable)folderId);
	}

	@Override
	public Map<Serializable, JournalFolder> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(JournalFolder.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, JournalFolder> map =
			new HashMap<Serializable, JournalFolder>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			JournalFolder journalFolder = fetchByPrimaryKey(primaryKey);

			if (journalFolder != null) {
				map.put(primaryKey, journalFolder);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						JournalFolder.class, primaryKey)) {

				JournalFolder journalFolder =
					(JournalFolder)entityCache.getResult(
						JournalFolderImpl.class, primaryKey);

				if (journalFolder == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, journalFolder);
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

			for (JournalFolder journalFolder :
					(List<JournalFolder>)query.list()) {

				map.put(journalFolder.getPrimaryKeyObj(), journalFolder);

				cacheResult(journalFolder);
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
	 * Returns all the journal folders.
	 *
	 * @return the journal folders
	 */
	@Override
	public List<JournalFolder> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of journal folders
	 */
	@Override
	public List<JournalFolder> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal folders
	 */
	@Override
	public List<JournalFolder> findAll(
		int start, int end,
		OrderByComparator<JournalFolder> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of journal folders
	 */
	@Override
	public List<JournalFolder> findAll(
		int start, int end, OrderByComparator<JournalFolder> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

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

			List<JournalFolder> list = null;

			if (useFinderCache) {
				list = (List<JournalFolder>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_JOURNALFOLDER);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_JOURNALFOLDER;

					sql = sql.concat(JournalFolderModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<JournalFolder>)QueryUtil.list(
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
	 * Removes all the journal folders from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (JournalFolder journalFolder : findAll()) {
			remove(journalFolder);
		}
	}

	/**
	 * Returns the number of journal folders.
	 *
	 * @return the number of journal folders
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalFolder.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_JOURNALFOLDER);

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
		return "folderId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_JOURNALFOLDER;
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
		return JournalFolderModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "JournalFolder";
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
		ctMergeColumnNames.add("parentFolderId");
		ctMergeColumnNames.add("treePath");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("description");
		ctMergeColumnNames.add("restrictionType");
		ctMergeColumnNames.add("lastPublishDate");
		ctMergeColumnNames.add("status");
		ctMergeColumnNames.add("statusByUserId");
		ctMergeColumnNames.add("statusByUserName");
		ctMergeColumnNames.add("statusDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("folderId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "parentFolderId", "name"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "groupId"});
	}

	/**
	 * Initializes the journal folder persistence.
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
			_SQL_SELECT_JOURNALFOLDER_WHERE, _SQL_COUNT_JOURNALFOLDER_WHERE,
			JournalFolderModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"journalFolder.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, JournalFolder::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_JOURNALFOLDER_WHERE,
			new FinderColumn<>(
				"journalFolder.", "uuid", FinderColumn.Type.STRING, "=", true,
				false, JournalFolder::getUuid),
			new FinderColumn<>(
				"journalFolder.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalFolder::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_JOURNALFOLDER_WHERE,
				_SQL_COUNT_JOURNALFOLDER_WHERE,
				JournalFolderModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalFolder.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, JournalFolder::getUuid),
				new FinderColumn<>(
					"journalFolder.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, JournalFolder::getCompanyId));

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
				_finderPathCountByGroupId, _SQL_SELECT_JOURNALFOLDER_WHERE,
				_SQL_COUNT_JOURNALFOLDER_WHERE,
				JournalFolderModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalFolder.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, JournalFolder::getGroupId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_JOURNALFOLDER_WHERE,
				_SQL_COUNT_JOURNALFOLDER_WHERE,
				JournalFolderModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalFolder.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, JournalFolder::getCompanyId));

		_finderPathWithPaginationFindByG_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "parentFolderId"}, true);

		_finderPathWithoutPaginationFindByG_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "parentFolderId"}, true);

		_finderPathCountByG_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "parentFolderId"}, false);

		_collectionPersistenceFinderByG_P = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_P,
			_finderPathWithoutPaginationFindByG_P, _finderPathCountByG_P,
			_SQL_SELECT_JOURNALFOLDER_WHERE, _SQL_COUNT_JOURNALFOLDER_WHERE,
			JournalFolderModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"journalFolder.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, JournalFolder::getGroupId),
			new FinderColumn<>(
				"journalFolder.", "parentFolderId", FinderColumn.Type.LONG, "=",
				true, true, JournalFolder::getParentFolderId));

		_finderPathFetchByG_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, true);

		_uniquePersistenceFinderByG_N = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_N, _SQL_SELECT_JOURNALFOLDER_WHERE,
			new FinderColumn<>(
				"journalFolder.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, JournalFolder::getGroupId),
			new FinderColumn<>(
				"journalFolder.", "name", FinderColumn.Type.STRING, "=", true,
				true, JournalFolder::getName));

		_finderPathWithPaginationFindByC_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_NotS",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "status"}, true);

		_finderPathWithPaginationCountByC_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotS",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, false);

		_collectionPersistenceFinderByC_NotS =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_NotS, null,
				_finderPathWithPaginationCountByC_NotS,
				_SQL_SELECT_JOURNALFOLDER_WHERE, _SQL_COUNT_JOURNALFOLDER_WHERE,
				JournalFolderModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalFolder.", "companyId", FinderColumn.Type.LONG, "=",
					true, false, JournalFolder::getCompanyId),
				new FinderColumn<>(
					"journalFolder.", "status", FinderColumn.Type.INTEGER, "!=",
					true, true, JournalFolder::getStatus));

		_finderPathFetchByG_P_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "parentFolderId", "name"}, true);

		_uniquePersistenceFinderByG_P_N = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_P_N, _SQL_SELECT_JOURNALFOLDER_WHERE,
			new FinderColumn<>(
				"journalFolder.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, JournalFolder::getGroupId),
			new FinderColumn<>(
				"journalFolder.", "parentFolderId", FinderColumn.Type.LONG, "=",
				true, false, JournalFolder::getParentFolderId),
			new FinderColumn<>(
				"journalFolder.", "name", FinderColumn.Type.STRING, "=", true,
				true, JournalFolder::getName));

		_finderPathWithPaginationFindByG_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "parentFolderId", "status"}, true);

		_finderPathWithoutPaginationFindByG_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "parentFolderId", "status"}, true);

		_finderPathCountByG_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "parentFolderId", "status"}, false);

		_collectionPersistenceFinderByG_P_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_P_S,
			_finderPathWithoutPaginationFindByG_P_S, _finderPathCountByG_P_S,
			_SQL_SELECT_JOURNALFOLDER_WHERE, _SQL_COUNT_JOURNALFOLDER_WHERE,
			JournalFolderModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"journalFolder.", "groupId", FinderColumn.Type.LONG, "=", true,
				false, JournalFolder::getGroupId),
			new FinderColumn<>(
				"journalFolder.", "parentFolderId", FinderColumn.Type.LONG, "=",
				true, false, JournalFolder::getParentFolderId),
			new FinderColumn<>(
				"journalFolder.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, JournalFolder::getStatus));

		_finderPathWithPaginationFindByG_P_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "parentFolderId", "status"}, true);

		_finderPathWithPaginationCountByG_P_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_P_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "parentFolderId", "status"}, false);

		_collectionPersistenceFinderByG_P_NotS =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_P_NotS, null,
				_finderPathWithPaginationCountByG_P_NotS,
				_SQL_SELECT_JOURNALFOLDER_WHERE, _SQL_COUNT_JOURNALFOLDER_WHERE,
				JournalFolderModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalFolder.", "groupId", FinderColumn.Type.LONG, "=",
					true, false, JournalFolder::getGroupId),
				new FinderColumn<>(
					"journalFolder.", "parentFolderId", FinderColumn.Type.LONG,
					"=", true, false, JournalFolder::getParentFolderId),
				new FinderColumn<>(
					"journalFolder.", "status", FinderColumn.Type.INTEGER, "!=",
					true, true, JournalFolder::getStatus));

		_finderPathWithPaginationFindByGtF_C_P_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGtF_C_P_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"folderId", "companyId", "parentFolderId", "status"},
			true);

		_finderPathWithPaginationCountByGtF_C_P_NotS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGtF_C_P_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {"folderId", "companyId", "parentFolderId", "status"},
			false);

		_collectionPersistenceFinderByGtF_C_P_NotS =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByGtF_C_P_NotS, null,
				_finderPathWithPaginationCountByGtF_C_P_NotS,
				_SQL_SELECT_JOURNALFOLDER_WHERE, _SQL_COUNT_JOURNALFOLDER_WHERE,
				JournalFolderModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalFolder.", "folderId", FinderColumn.Type.LONG, ">",
					true, false, JournalFolder::getFolderId),
				new FinderColumn<>(
					"journalFolder.", "companyId", FinderColumn.Type.LONG, "=",
					true, false, JournalFolder::getCompanyId),
				new FinderColumn<>(
					"journalFolder.", "parentFolderId", FinderColumn.Type.LONG,
					"=", true, false, JournalFolder::getParentFolderId),
				new FinderColumn<>(
					"journalFolder.", "status", FinderColumn.Type.INTEGER, "!=",
					true, true, JournalFolder::getStatus));

		_finderPathFetchByERC_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "groupId"}, true);

		_uniquePersistenceFinderByERC_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_G, _SQL_SELECT_JOURNALFOLDER_WHERE,
			new FinderColumn<>(
				"journalFolder.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				JournalFolder::getExternalReferenceCode),
			new FinderColumn<>(
				"journalFolder.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalFolder::getGroupId));

		JournalFolderUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		JournalFolderUtil.setPersistence(null);

		entityCache.removeCache(JournalFolderImpl.class.getName());
	}

	@Override
	@Reference(
		target = JournalPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = JournalPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = JournalPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_JOURNALFOLDER =
		"SELECT journalFolder FROM JournalFolder journalFolder";

	private static final String _SQL_SELECT_JOURNALFOLDER_WHERE =
		"SELECT journalFolder FROM JournalFolder journalFolder WHERE ";

	private static final String _SQL_COUNT_JOURNALFOLDER =
		"SELECT COUNT(journalFolder) FROM JournalFolder journalFolder";

	private static final String _SQL_COUNT_JOURNALFOLDER_WHERE =
		"SELECT COUNT(journalFolder) FROM JournalFolder journalFolder WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"journalFolder.folderId";

	private static final String _FILTER_SQL_SELECT_JOURNALFOLDER_WHERE =
		"SELECT DISTINCT {journalFolder.*} FROM JournalFolder journalFolder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {JournalFolder.*} FROM (SELECT DISTINCT journalFolder.folderId FROM JournalFolder journalFolder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN JournalFolder ON TEMP_TABLE.folderId = JournalFolder.folderId";

	private static final String _FILTER_SQL_COUNT_JOURNALFOLDER_WHERE =
		"SELECT COUNT(DISTINCT journalFolder.folderId) AS COUNT_VALUE FROM JournalFolder journalFolder WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "journalFolder";

	private static final String _FILTER_ENTITY_TABLE = "JournalFolder";

	private static final String _ORDER_BY_ENTITY_ALIAS = "journalFolder.";

	private static final String _ORDER_BY_ENTITY_TABLE = "JournalFolder.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No JournalFolder exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No JournalFolder exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalFolderPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-856242895