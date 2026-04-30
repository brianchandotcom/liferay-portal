/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service.persistence.impl;

import com.liferay.journal.exception.NoSuchArticleResourceException;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.model.JournalArticleResourceTable;
import com.liferay.journal.model.impl.JournalArticleResourceImpl;
import com.liferay.journal.model.impl.JournalArticleResourceModelImpl;
import com.liferay.journal.service.persistence.JournalArticleResourcePersistence;
import com.liferay.journal.service.persistence.JournalArticleResourceUtil;
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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the journal article resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = JournalArticleResourcePersistence.class)
public class JournalArticleResourcePersistenceImpl
	extends BasePersistenceImpl
		<JournalArticleResource, NoSuchArticleResourceException>
	implements JournalArticleResourcePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>JournalArticleResourceUtil</code> to access the journal article resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		JournalArticleResourceImpl.class.getName();

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
	private CollectionPersistenceFinder<JournalArticleResource>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the journal article resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal article resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @return the range of matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal article resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticleResource.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first journal article resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article resource
	 * @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource findByUuid_First(
			String uuid,
			OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException {

		JournalArticleResource journalArticleResource = fetchByUuid_First(
			uuid, orderByComparator);

		if (journalArticleResource != null) {
			return journalArticleResource;
		}

		throw new NoSuchArticleResourceException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first journal article resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource fetchByUuid_First(
		String uuid,
		OrderByComparator<JournalArticleResource> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the journal article resources where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of journal article resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal article resources
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticleResource.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<JournalArticleResource>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the journal article resource where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchArticleResourceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal article resource
	 * @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource findByUUID_G(String uuid, long groupId)
		throws NoSuchArticleResourceException {

		JournalArticleResource journalArticleResource = fetchByUUID_G(
			uuid, groupId);

		if (journalArticleResource == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchArticleResourceException(message);
		}

		return journalArticleResource;
	}

	/**
	 * Returns the journal article resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the journal article resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticleResource.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the journal article resource where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the journal article resource that was removed
	 */
	@Override
	public JournalArticleResource removeByUUID_G(String uuid, long groupId)
		throws NoSuchArticleResourceException {

		JournalArticleResource journalArticleResource = findByUUID_G(
			uuid, groupId);

		return remove(journalArticleResource);
	}

	/**
	 * Returns the number of journal article resources where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal article resources
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<JournalArticleResource>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the journal article resources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal article resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @return the range of matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal article resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticleResource.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article resource
	 * @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException {

		JournalArticleResource journalArticleResource = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (journalArticleResource != null) {
			return journalArticleResource;
		}

		throw new NoSuchArticleResourceException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<JournalArticleResource> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the journal article resources where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of journal article resources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching journal article resources
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticleResource.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<JournalArticleResource>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the journal article resources where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal article resources where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @return the range of matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article resources where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal article resources where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal article resources
	 */
	@Override
	public List<JournalArticleResource> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticleResource.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				finderCache, new Object[] {groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article resource
	 * @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource findByGroupId_First(
			long groupId,
			OrderByComparator<JournalArticleResource> orderByComparator)
		throws NoSuchArticleResourceException {

		JournalArticleResource journalArticleResource = fetchByGroupId_First(
			groupId, orderByComparator);

		if (journalArticleResource != null) {
			return journalArticleResource;
		}

		throw new NoSuchArticleResourceException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first journal article resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource fetchByGroupId_First(
		long groupId,
		OrderByComparator<JournalArticleResource> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Removes all the journal article resources where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of journal article resources where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal article resources
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticleResource.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				finderCache, new Object[] {groupId});
		}
	}

	private FinderPath _finderPathFetchByG_A;
	private UniquePersistenceFinder<JournalArticleResource>
		_uniquePersistenceFinderByG_A;

	/**
	 * Returns the journal article resource where groupId = &#63; and articleId = &#63; or throws a <code>NoSuchArticleResourceException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal article resource
	 * @throws NoSuchArticleResourceException if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource findByG_A(long groupId, String articleId)
		throws NoSuchArticleResourceException {

		JournalArticleResource journalArticleResource = fetchByG_A(
			groupId, articleId);

		if (journalArticleResource == null) {
			String message =
				_uniquePersistenceFinderByG_A.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, articleId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchArticleResourceException(message);
		}

		return journalArticleResource;
	}

	/**
	 * Returns the journal article resource where groupId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource fetchByG_A(long groupId, String articleId) {
		return fetchByG_A(groupId, articleId, true);
	}

	/**
	 * Returns the journal article resource where groupId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource fetchByG_A(
		long groupId, String articleId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticleResource.class)) {

			return _uniquePersistenceFinderByG_A.fetch(
				finderCache, new Object[] {groupId, articleId}, useFinderCache);
		}
	}

	/**
	 * Removes the journal article resource where groupId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the journal article resource that was removed
	 */
	@Override
	public JournalArticleResource removeByG_A(long groupId, String articleId)
		throws NoSuchArticleResourceException {

		JournalArticleResource journalArticleResource = findByG_A(
			groupId, articleId);

		return remove(journalArticleResource);
	}

	/**
	 * Returns the number of journal article resources where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching journal article resources
	 */
	@Override
	public int countByG_A(long groupId, String articleId) {
		return _uniquePersistenceFinderByG_A.count(
			finderCache, new Object[] {groupId, articleId});
	}

	public JournalArticleResourcePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(JournalArticleResource.class);

		setModelImplClass(JournalArticleResourceImpl.class);
		setModelPKClass(long.class);

		setTable(JournalArticleResourceTable.INSTANCE);
	}

	/**
	 * Caches the journal article resource in the entity cache if it is enabled.
	 *
	 * @param journalArticleResource the journal article resource
	 */
	@Override
	public void cacheResult(JournalArticleResource journalArticleResource) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					journalArticleResource.getCtCollectionId())) {

			entityCache.putResult(
				JournalArticleResourceImpl.class,
				journalArticleResource.getPrimaryKey(), journalArticleResource);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					journalArticleResource.getUuid(),
					journalArticleResource.getGroupId()
				},
				journalArticleResource);

			finderCache.putResult(
				_finderPathFetchByG_A,
				new Object[] {
					journalArticleResource.getGroupId(),
					journalArticleResource.getArticleId()
				},
				journalArticleResource);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the journal article resources in the entity cache if it is enabled.
	 *
	 * @param journalArticleResources the journal article resources
	 */
	@Override
	public void cacheResult(
		List<JournalArticleResource> journalArticleResources) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (journalArticleResources.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (JournalArticleResource journalArticleResource :
				journalArticleResources) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						journalArticleResource.getCtCollectionId())) {

				if (entityCache.getResult(
						JournalArticleResourceImpl.class,
						journalArticleResource.getPrimaryKey()) == null) {

					cacheResult(journalArticleResource);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		JournalArticleResourceModelImpl journalArticleResourceModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					journalArticleResourceModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				journalArticleResourceModelImpl.getUuid(),
				journalArticleResourceModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args,
				journalArticleResourceModelImpl);

			args = new Object[] {
				journalArticleResourceModelImpl.getGroupId(),
				journalArticleResourceModelImpl.getArticleId()
			};

			finderCache.putResult(
				_finderPathFetchByG_A, args, journalArticleResourceModelImpl);
		}
	}

	/**
	 * Creates a new journal article resource with the primary key. Does not add the journal article resource to the database.
	 *
	 * @param resourcePrimKey the primary key for the new journal article resource
	 * @return the new journal article resource
	 */
	@Override
	public JournalArticleResource create(long resourcePrimKey) {
		JournalArticleResource journalArticleResource =
			new JournalArticleResourceImpl();

		journalArticleResource.setNew(true);
		journalArticleResource.setPrimaryKey(resourcePrimKey);

		String uuid = PortalUUIDUtil.generate();

		journalArticleResource.setUuid(uuid);

		journalArticleResource.setCompanyId(CompanyThreadLocal.getCompanyId());

		return journalArticleResource;
	}

	/**
	 * Removes the journal article resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourcePrimKey the primary key of the journal article resource
	 * @return the journal article resource that was removed
	 * @throws NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	 */
	@Override
	public JournalArticleResource remove(long resourcePrimKey)
		throws NoSuchArticleResourceException {

		return remove((Serializable)resourcePrimKey);
	}

	@Override
	protected JournalArticleResource removeImpl(
		JournalArticleResource journalArticleResource) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(journalArticleResource)) {
				journalArticleResource = (JournalArticleResource)session.get(
					JournalArticleResourceImpl.class,
					journalArticleResource.getPrimaryKeyObj());
			}

			if ((journalArticleResource != null) &&
				ctPersistenceHelper.isRemove(journalArticleResource)) {

				session.delete(journalArticleResource);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (journalArticleResource != null) {
			clearCache(journalArticleResource);
		}

		return journalArticleResource;
	}

	@Override
	public JournalArticleResource updateImpl(
		JournalArticleResource journalArticleResource) {

		boolean isNew = journalArticleResource.isNew();

		if (!(journalArticleResource instanceof
				JournalArticleResourceModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(journalArticleResource.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					journalArticleResource);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in journalArticleResource proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom JournalArticleResource implementation " +
					journalArticleResource.getClass());
		}

		JournalArticleResourceModelImpl journalArticleResourceModelImpl =
			(JournalArticleResourceModelImpl)journalArticleResource;

		if (Validator.isNull(journalArticleResource.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalArticleResource.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(journalArticleResource)) {
				if (!isNew) {
					session.evict(
						JournalArticleResourceImpl.class,
						journalArticleResource.getPrimaryKeyObj());
				}

				session.save(journalArticleResource);
			}
			else {
				journalArticleResource = (JournalArticleResource)session.merge(
					journalArticleResource);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			JournalArticleResourceImpl.class, journalArticleResourceModelImpl,
			false, true);

		cacheUniqueFindersCache(journalArticleResourceModelImpl);

		if (isNew) {
			journalArticleResource.setNew(false);
		}

		journalArticleResource.resetOriginalValues();

		return journalArticleResource;
	}

	/**
	 * Returns the journal article resource with the primary key or throws a <code>NoSuchArticleResourceException</code> if it could not be found.
	 *
	 * @param resourcePrimKey the primary key of the journal article resource
	 * @return the journal article resource
	 * @throws NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	 */
	@Override
	public JournalArticleResource findByPrimaryKey(long resourcePrimKey)
		throws NoSuchArticleResourceException {

		return findByPrimaryKey((Serializable)resourcePrimKey);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the journal article resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourcePrimKey the primary key of the journal article resource
	 * @return the journal article resource, or <code>null</code> if a journal article resource with the primary key could not be found
	 */
	@Override
	public JournalArticleResource fetchByPrimaryKey(long resourcePrimKey) {
		return fetchByPrimaryKey((Serializable)resourcePrimKey);
	}

	/**
	 * Returns all the journal article resources.
	 *
	 * @return the journal article resources
	 */
	@Override
	public List<JournalArticleResource> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal article resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @return the range of journal article resources
	 */
	@Override
	public List<JournalArticleResource> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal article resources
	 */
	@Override
	public List<JournalArticleResource> findAll(
		int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal article resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleResourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of journal article resources
	 */
	@Override
	public List<JournalArticleResource> findAll(
		int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticleResource.class)) {

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

			List<JournalArticleResource> list = null;

			if (useFinderCache) {
				list = (List<JournalArticleResource>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_JOURNALARTICLERESOURCE);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_JOURNALARTICLERESOURCE;

					sql = sql.concat(
						JournalArticleResourceModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<JournalArticleResource>)QueryUtil.list(
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
	 * Removes all the journal article resources from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (JournalArticleResource journalArticleResource : findAll()) {
			remove(journalArticleResource);
		}
	}

	/**
	 * Returns the number of journal article resources.
	 *
	 * @return the number of journal article resources
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticleResource.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_JOURNALARTICLERESOURCE);

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
		return "resourcePrimKey";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_JOURNALARTICLERESOURCE;
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
		return JournalArticleResourceModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "JournalArticleResource";
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
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctMergeColumnNames.add("articleId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("resourcePrimKey"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"groupId", "articleId"});
	}

	/**
	 * Initializes the journal article resource persistence.
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
			_SQL_SELECT_JOURNALARTICLERESOURCE_WHERE,
			_SQL_COUNT_JOURNALARTICLERESOURCE_WHERE,
			JournalArticleResourceModelImpl.ORDER_BY_JPQL,
			_ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"journalArticleResource.", "uuid", FinderColumn.Type.STRING,
				"=", true, true, JournalArticleResource::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G,
			_SQL_SELECT_JOURNALARTICLERESOURCE_WHERE,
			new FinderColumn<>(
				"journalArticleResource.", "uuid", FinderColumn.Type.STRING,
				"=", true, false, JournalArticleResource::getUuid),
			new FinderColumn<>(
				"journalArticleResource.", "groupId", FinderColumn.Type.LONG,
				"=", true, true, JournalArticleResource::getGroupId));

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
				_SQL_SELECT_JOURNALARTICLERESOURCE_WHERE,
				_SQL_COUNT_JOURNALARTICLERESOURCE_WHERE,
				JournalArticleResourceModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalArticleResource.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, JournalArticleResource::getUuid),
				new FinderColumn<>(
					"journalArticleResource.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					JournalArticleResource::getCompanyId));

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
				_SQL_SELECT_JOURNALARTICLERESOURCE_WHERE,
				_SQL_COUNT_JOURNALARTICLERESOURCE_WHERE,
				JournalArticleResourceModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalArticleResource.", "groupId",
					FinderColumn.Type.LONG, "=", true, true,
					JournalArticleResource::getGroupId));

		_finderPathFetchByG_A = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_A",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "articleId"}, true);

		_uniquePersistenceFinderByG_A = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_A,
			_SQL_SELECT_JOURNALARTICLERESOURCE_WHERE,
			new FinderColumn<>(
				"journalArticleResource.", "groupId", FinderColumn.Type.LONG,
				"=", true, false, JournalArticleResource::getGroupId),
			new FinderColumn<>(
				"journalArticleResource.", "articleId",
				FinderColumn.Type.STRING, "=", true, true,
				JournalArticleResource::getArticleId));

		JournalArticleResourceUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		JournalArticleResourceUtil.setPersistence(null);

		entityCache.removeCache(JournalArticleResourceImpl.class.getName());
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

	private static final String _SQL_SELECT_JOURNALARTICLERESOURCE =
		"SELECT journalArticleResource FROM JournalArticleResource journalArticleResource";

	private static final String _SQL_SELECT_JOURNALARTICLERESOURCE_WHERE =
		"SELECT journalArticleResource FROM JournalArticleResource journalArticleResource WHERE ";

	private static final String _SQL_COUNT_JOURNALARTICLERESOURCE =
		"SELECT COUNT(journalArticleResource) FROM JournalArticleResource journalArticleResource";

	private static final String _SQL_COUNT_JOURNALARTICLERESOURCE_WHERE =
		"SELECT COUNT(journalArticleResource) FROM JournalArticleResource journalArticleResource WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"journalArticleResource.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No JournalArticleResource exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleResourcePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1397891448