/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service.persistence.impl;

import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleTable;
import com.liferay.journal.model.impl.JournalArticleImpl;
import com.liferay.journal.model.impl.JournalArticleModelImpl;
import com.liferay.journal.service.persistence.JournalArticlePersistence;
import com.liferay.journal.service.persistence.JournalArticleUtil;
import com.liferay.journal.service.persistence.impl.constants.JournalPersistenceConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
 * The persistence implementation for the journal article service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = JournalArticlePersistence.class)
public class JournalArticlePersistenceImpl
	extends BasePersistenceImpl<JournalArticle, NoSuchArticleException>
	implements JournalArticlePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>JournalArticleUtil</code> to access the journal article persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		JournalArticleImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByResourcePrimKey;
	private FinderPath _finderPathWithoutPaginationFindByResourcePrimKey;
	private FinderPath _finderPathCountByResourcePrimKey;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByResourcePrimKey;

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByResourcePrimKey(long resourcePrimKey) {
		return findByResourcePrimKey(
			resourcePrimKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end) {

		return findByResourcePrimKey(resourcePrimKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByResourcePrimKey(
			resourcePrimKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByResourcePrimKey.find(
				finderCache, new Object[] {resourcePrimKey}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByResourcePrimKey_First(
			long resourcePrimKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByResourcePrimKey_First(
			resourcePrimKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByResourcePrimKey.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {resourcePrimKey}));
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByResourcePrimKey_First(
		long resourcePrimKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByResourcePrimKey.fetchFirst(
			finderCache, new Object[] {resourcePrimKey}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 */
	@Override
	public void removeByResourcePrimKey(long resourcePrimKey) {
		_collectionPersistenceFinderByResourcePrimKey.remove(
			finderCache, new Object[] {resourcePrimKey});
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByResourcePrimKey(long resourcePrimKey) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByResourcePrimKey.count(
				finderCache, new Object[] {resourcePrimKey});
		}
	}

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the journal articles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByUuid_First(
			String uuid, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByUuid_First(
			uuid, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first journal article in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByUuid_First(
		String uuid, OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of journal articles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<JournalArticle>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the journal article where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchArticleException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByUUID_G(String uuid, long groupId)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByUUID_G(uuid, groupId);

		if (journalArticle == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchArticleException(message);
		}

		return journalArticle;
	}

	/**
	 * Returns the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the journal article where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the journal article that was removed
	 */
	@Override
	public JournalArticle removeByUUID_G(String uuid, long groupId)
		throws NoSuchArticleException {

		JournalArticle journalArticle = findByUUID_G(uuid, groupId);

		return remove(journalArticle);
	}

	/**
	 * Returns the number of journal articles where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the journal articles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				finderCache, new Object[] {groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByGroupId_First(
			long groupId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByGroupId_First(
			groupId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByGroupId_First(
		long groupId, OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				finderCache, new Object[] {groupId});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByGroupId(groupId);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
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
		"journalArticle.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the journal articles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				finderCache, new Object[] {companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByCompanyId_First(
			long companyId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByCompanyId_First(
		long companyId, OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of journal articles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				finderCache, new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByDDMStructureId;
	private FinderPath _finderPathWithoutPaginationFindByDDMStructureId;
	private FinderPath _finderPathCountByDDMStructureId;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByDDMStructureId;

	/**
	 * Returns all the journal articles where DDMStructureId = &#63;.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMStructureId(long DDMStructureId) {
		return findByDDMStructureId(
			DDMStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMStructureId(
		long DDMStructureId, int start, int end) {

		return findByDDMStructureId(DDMStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMStructureId(
		long DDMStructureId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByDDMStructureId(
			DDMStructureId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMStructureId(
		long DDMStructureId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByDDMStructureId.find(
				finderCache, new Object[] {DDMStructureId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where DDMStructureId = &#63;.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByDDMStructureId_First(
			long DDMStructureId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByDDMStructureId_First(
			DDMStructureId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByDDMStructureId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {DDMStructureId}));
	}

	/**
	 * Returns the first journal article in the ordered set where DDMStructureId = &#63;.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByDDMStructureId_First(
		long DDMStructureId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByDDMStructureId.fetchFirst(
			finderCache, new Object[] {DDMStructureId}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where DDMStructureId = &#63; from the database.
	 *
	 * @param DDMStructureId the ddm structure ID
	 */
	@Override
	public void removeByDDMStructureId(long DDMStructureId) {
		_collectionPersistenceFinderByDDMStructureId.remove(
			finderCache, new Object[] {DDMStructureId});
	}

	/**
	 * Returns the number of journal articles where DDMStructureId = &#63;.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByDDMStructureId(long DDMStructureId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByDDMStructureId.count(
				finderCache, new Object[] {DDMStructureId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByDDMTemplateKey;
	private FinderPath _finderPathWithoutPaginationFindByDDMTemplateKey;
	private FinderPath _finderPathCountByDDMTemplateKey;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByDDMTemplateKey;

	/**
	 * Returns all the journal articles where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMTemplateKey(String DDMTemplateKey) {
		return findByDDMTemplateKey(
			DDMTemplateKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMTemplateKey(
		String DDMTemplateKey, int start, int end) {

		return findByDDMTemplateKey(DDMTemplateKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMTemplateKey(
		String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByDDMTemplateKey(
			DDMTemplateKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMTemplateKey(
		String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByDDMTemplateKey.find(
				finderCache, new Object[] {DDMTemplateKey}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByDDMTemplateKey_First(
			String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByDDMTemplateKey_First(
			DDMTemplateKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByDDMTemplateKey.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {DDMTemplateKey}));
	}

	/**
	 * Returns the first journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByDDMTemplateKey_First(
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByDDMTemplateKey.fetchFirst(
			finderCache, new Object[] {DDMTemplateKey}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where DDMTemplateKey = &#63; from the database.
	 *
	 * @param DDMTemplateKey the ddm template key
	 */
	@Override
	public void removeByDDMTemplateKey(String DDMTemplateKey) {
		_collectionPersistenceFinderByDDMTemplateKey.remove(
			finderCache, new Object[] {DDMTemplateKey});
	}

	/**
	 * Returns the number of journal articles where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByDDMTemplateKey(String DDMTemplateKey) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByDDMTemplateKey.count(
				finderCache, new Object[] {DDMTemplateKey});
		}
	}

	private FinderPath _finderPathWithPaginationFindByLayoutUuid;
	private FinderPath _finderPathWithoutPaginationFindByLayoutUuid;
	private FinderPath _finderPathCountByLayoutUuid;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByLayoutUuid;

	/**
	 * Returns all the journal articles where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLayoutUuid(String layoutUuid) {
		return findByLayoutUuid(
			layoutUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLayoutUuid(
		String layoutUuid, int start, int end) {

		return findByLayoutUuid(layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLayoutUuid(
		String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByLayoutUuid(
			layoutUuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLayoutUuid(
		String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByLayoutUuid.find(
				finderCache, new Object[] {layoutUuid}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByLayoutUuid_First(
			String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByLayoutUuid_First(
			layoutUuid, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByLayoutUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {layoutUuid}));
	}

	/**
	 * Returns the first journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByLayoutUuid_First(
		String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByLayoutUuid.fetchFirst(
			finderCache, new Object[] {layoutUuid}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where layoutUuid = &#63; from the database.
	 *
	 * @param layoutUuid the layout uuid
	 */
	@Override
	public void removeByLayoutUuid(String layoutUuid) {
		_collectionPersistenceFinderByLayoutUuid.remove(
			finderCache, new Object[] {layoutUuid});
	}

	/**
	 * Returns the number of journal articles where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByLayoutUuid(String layoutUuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByLayoutUuid.count(
				finderCache, new Object[] {layoutUuid});
		}
	}

	private FinderPath _finderPathWithPaginationFindBySmallImageId;
	private FinderPath _finderPathWithoutPaginationFindBySmallImageId;
	private FinderPath _finderPathCountBySmallImageId;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderBySmallImageId;

	/**
	 * Returns all the journal articles where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findBySmallImageId(long smallImageId) {
		return findBySmallImageId(
			smallImageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end) {

		return findBySmallImageId(smallImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findBySmallImageId(
			smallImageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderBySmallImageId.find(
				finderCache, new Object[] {smallImageId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findBySmallImageId_First(
			long smallImageId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchBySmallImageId_First(
			smallImageId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderBySmallImageId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {smallImageId}));
	}

	/**
	 * Returns the first journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchBySmallImageId_First(
		long smallImageId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderBySmallImageId.fetchFirst(
			finderCache, new Object[] {smallImageId}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where smallImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 */
	@Override
	public void removeBySmallImageId(long smallImageId) {
		_collectionPersistenceFinderBySmallImageId.remove(
			finderCache, new Object[] {smallImageId});
	}

	/**
	 * Returns the number of journal articles where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countBySmallImageId(long smallImageId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderBySmallImageId.count(
				finderCache, new Object[] {smallImageId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByR_I;
	private FinderPath _finderPathWithoutPaginationFindByR_I;
	private FinderPath _finderPathCountByR_I;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByR_I;

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I(
		long resourcePrimKey, boolean indexable) {

		return findByR_I(
			resourcePrimKey, indexable, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I(
		long resourcePrimKey, boolean indexable, int start, int end) {

		return findByR_I(resourcePrimKey, indexable, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I(
		long resourcePrimKey, boolean indexable, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByR_I(
			resourcePrimKey, indexable, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I(
		long resourcePrimKey, boolean indexable, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByR_I.find(
				finderCache, new Object[] {resourcePrimKey, indexable}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByR_I_First(
			long resourcePrimKey, boolean indexable,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByR_I_First(
			resourcePrimKey, indexable, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByR_I.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {resourcePrimKey, indexable}));
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByR_I_First(
		long resourcePrimKey, boolean indexable,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByR_I.fetchFirst(
			finderCache, new Object[] {resourcePrimKey, indexable},
			orderByComparator);
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; and indexable = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 */
	@Override
	public void removeByR_I(long resourcePrimKey, boolean indexable) {
		_collectionPersistenceFinderByR_I.remove(
			finderCache, new Object[] {resourcePrimKey, indexable});
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByR_I(long resourcePrimKey, boolean indexable) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByR_I.count(
				finderCache, new Object[] {resourcePrimKey, indexable});
		}
	}

	private FinderPath _finderPathWithPaginationFindByR_ST;
	private FinderPath _finderPathWithoutPaginationFindByR_ST;
	private FinderPath _finderPathCountByR_ST;
	private FinderPath _finderPathWithPaginationCountByR_ST;

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(long resourcePrimKey, int status) {
		return findByR_ST(
			resourcePrimKey, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(
		long resourcePrimKey, int status, int start, int end) {

		return findByR_ST(resourcePrimKey, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(
		long resourcePrimKey, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByR_ST(
			resourcePrimKey, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(
		long resourcePrimKey, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByR_ST;
					finderArgs = new Object[] {resourcePrimKey, status};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByR_ST;
				finderArgs = new Object[] {
					resourcePrimKey, status, start, end, orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((resourcePrimKey !=
								journalArticle.getResourcePrimKey()) ||
							(status != journalArticle.getStatus())) {

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
						4 + (orderByComparator.getOrderByFields().length * 2));
				}
				else {
					sb = new StringBundler(4);
				}

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

				sb.append(_FINDER_COLUMN_R_ST_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(resourcePrimKey);

					queryPos.add(status);

					list = (List<JournalArticle>)QueryUtil.list(
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
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByR_ST_First(
			long resourcePrimKey, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByR_ST_First(
			resourcePrimKey, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("resourcePrimKey=");
		sb.append(resourcePrimKey);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchArticleException(sb.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByR_ST_First(
		long resourcePrimKey, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		List<JournalArticle> list = findByR_ST(
			resourcePrimKey, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(
		long resourcePrimKey, int[] statuses) {

		return findByR_ST(
			resourcePrimKey, statuses, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(
		long resourcePrimKey, int[] statuses, int start, int end) {

		return findByR_ST(resourcePrimKey, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(
		long resourcePrimKey, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByR_ST(
			resourcePrimKey, statuses, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(
		long resourcePrimKey, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		if (statuses.length == 1) {
			return findByR_ST(
				resourcePrimKey, statuses[0], start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						resourcePrimKey, StringUtil.merge(statuses)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					resourcePrimKey, StringUtil.merge(statuses), start, end,
					orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					_finderPathWithPaginationFindByR_ST, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((resourcePrimKey !=
								journalArticle.getResourcePrimKey()) ||
							!ArrayUtil.contains(
								statuses, journalArticle.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_R_ST_STATUS_7);

					sb.append(StringUtil.merge(statuses));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(resourcePrimKey);

					list = (List<JournalArticle>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByR_ST, finderArgs,
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
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; and status = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 */
	@Override
	public void removeByR_ST(long resourcePrimKey, int status) {
		for (JournalArticle journalArticle :
				findByR_ST(
					resourcePrimKey, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByR_ST(long resourcePrimKey, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			FinderPath finderPath = _finderPathCountByR_ST;

			Object[] finderArgs = new Object[] {resourcePrimKey, status};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

				sb.append(_FINDER_COLUMN_R_ST_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(resourcePrimKey);

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
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByR_ST(long resourcePrimKey, int[] statuses) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = new Object[] {
				resourcePrimKey, StringUtil.merge(statuses)
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByR_ST, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_R_ST_STATUS_7);

					sb.append(StringUtil.merge(statuses));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(resourcePrimKey);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByR_ST, finderArgs,
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
	}

	private static final String _FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2 =
		"journalArticle.resourcePrimKey = ? AND ";

	private static final String _FINDER_COLUMN_R_ST_STATUS_2 =
		"journalArticle.status = ?";

	private static final String _FINDER_COLUMN_R_ST_STATUS_7 =
		"journalArticle.status IN (";

	private FinderPath _finderPathWithPaginationFindByG_U;
	private FinderPath _finderPathWithoutPaginationFindByG_U;
	private FinderPath _finderPathCountByG_U;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_U;

	/**
	 * Returns all the journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U(long groupId, long userId) {
		return findByG_U(
			groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U(
		long groupId, long userId, int start, int end) {

		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_U(groupId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_U.find(
				finderCache, new Object[] {groupId, userId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_U_First(
			long groupId, long userId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_U_First(
			groupId, userId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_U.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, userId}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_U_First(
		long groupId, long userId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_U.fetchFirst(
			finderCache, new Object[] {groupId, userId}, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U(long groupId, long userId) {
		return filterFindByG_U(
			groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U(
		long groupId, long userId, int start, int end) {

		return filterFindByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U(groupId, userId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_U(
					groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
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
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByG_U(long groupId, long userId) {
		_collectionPersistenceFinderByG_U.remove(
			finderCache, new Object[] {groupId, userId});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_U.count(
				finderCache, new Object[] {groupId, userId});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_U(long groupId, long userId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U(groupId, userId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_U(groupId, userId);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_USERID_2 =
		"journalArticle.userId = ?";

	private FinderPath _finderPathWithPaginationFindByG_ERC;
	private FinderPath _finderPathWithoutPaginationFindByG_ERC;
	private FinderPath _finderPathCountByG_ERC;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_ERC;

	/**
	 * Returns all the journal articles where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC(
		long groupId, String externalReferenceCode) {

		return findByG_ERC(
			groupId, externalReferenceCode, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC(
		long groupId, String externalReferenceCode, int start, int end) {

		return findByG_ERC(groupId, externalReferenceCode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC(
		long groupId, String externalReferenceCode, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_ERC(
			groupId, externalReferenceCode, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC(
		long groupId, String externalReferenceCode, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_ERC.find(
				finderCache, new Object[] {groupId, externalReferenceCode},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_ERC_First(
			long groupId, String externalReferenceCode,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_ERC_First(
			groupId, externalReferenceCode, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_ERC.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, externalReferenceCode}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_ERC_First(
		long groupId, String externalReferenceCode,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_ERC.fetchFirst(
			finderCache, new Object[] {groupId, externalReferenceCode},
			orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ERC(
		long groupId, String externalReferenceCode) {

		return filterFindByG_ERC(
			groupId, externalReferenceCode, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ERC(
		long groupId, String externalReferenceCode, int start, int end) {

		return filterFindByG_ERC(
			groupId, externalReferenceCode, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ERC(
		long groupId, String externalReferenceCode, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_ERC(
				groupId, externalReferenceCode, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_ERC(
					groupId, externalReferenceCode, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_ERC_GROUPID_2);

		boolean bindExternalReferenceCode = false;

		if (externalReferenceCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_3);
		}
		else {
			bindExternalReferenceCode = true;

			sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindExternalReferenceCode) {
				queryPos.add(externalReferenceCode);
			}

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 */
	@Override
	public void removeByG_ERC(long groupId, String externalReferenceCode) {
		_collectionPersistenceFinderByG_ERC.remove(
			finderCache, new Object[] {groupId, externalReferenceCode});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_ERC(long groupId, String externalReferenceCode) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_ERC.count(
				finderCache, new Object[] {groupId, externalReferenceCode});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_ERC(long groupId, String externalReferenceCode) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_ERC(groupId, externalReferenceCode);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_ERC(
				groupId, externalReferenceCode);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_ERC_GROUPID_2);

		boolean bindExternalReferenceCode = false;

		if (externalReferenceCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_3);
		}
		else {
			bindExternalReferenceCode = true;

			sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindExternalReferenceCode) {
				queryPos.add(externalReferenceCode);
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

	private static final String _FINDER_COLUMN_G_ERC_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_2 =
		"journalArticle.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_3 =
		"(journalArticle.externalReferenceCode IS NULL OR journalArticle.externalReferenceCode = '')";

	private FinderPath _finderPathWithPaginationFindByG_F;
	private FinderPath _finderPathWithoutPaginationFindByG_F;
	private FinderPath _finderPathCountByG_F;
	private FinderPath _finderPathWithPaginationCountByG_F;

	/**
	 * Returns all the journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(long groupId, long folderId) {
		return findByG_F(
			groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(
		long groupId, long folderId, int start, int end) {

		return findByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_F(
			groupId, folderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_F;
					finderArgs = new Object[] {groupId, folderId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_F;
				finderArgs = new Object[] {
					groupId, folderId, start, end, orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((groupId != journalArticle.getGroupId()) ||
							(folderId != journalArticle.getFolderId())) {

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
						4 + (orderByComparator.getOrderByFields().length * 2));
				}
				else {
					sb = new StringBundler(4);
				}

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_FOLDERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

					list = (List<JournalArticle>)QueryUtil.list(
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_F_First(
			long groupId, long folderId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_F_First(
			groupId, folderId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", folderId=");
		sb.append(folderId);

		sb.append("}");

		throw new NoSuchArticleException(sb.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_F_First(
		long groupId, long folderId,
		OrderByComparator<JournalArticle> orderByComparator) {

		List<JournalArticle> list = findByG_F(
			groupId, folderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(long groupId, long folderId) {
		return filterFindByG_F(
			groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(
		long groupId, long folderId, int start, int end) {

		return filterFindByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F(
					groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
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
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(
		long groupId, long[] folderIds) {

		return filterFindByG_F(
			groupId, folderIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end) {

		return filterFindByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderIds, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F(
					groupId, folderIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Returns all the journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(long groupId, long[] folderIds) {
		return findByG_F(
			groupId, folderIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(
		long groupId, long[] folderIds, int start, int end) {

		return findByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_F(
			groupId, folderIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		if (folderIds.length == 1) {
			return findByG_F(
				groupId, folderIds[0], start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						groupId, StringUtil.merge(folderIds)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(folderIds), start, end,
					orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					_finderPathWithPaginationFindByG_F, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((groupId != journalArticle.getGroupId()) ||
							!ArrayUtil.contains(
								folderIds, journalArticle.getFolderId())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

				if (folderIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_F_FOLDERID_7);

					sb.append(StringUtil.merge(folderIds));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					list = (List<JournalArticle>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_F, finderArgs,
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
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByG_F(long groupId, long folderId) {
		for (JournalArticle journalArticle :
				findByG_F(
					groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_F(long groupId, long folderId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			FinderPath finderPath = _finderPathCountByG_F;

			Object[] finderArgs = new Object[] {groupId, folderId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_FOLDERID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

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
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_F(long groupId, long[] folderIds) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(folderIds)
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_F, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

				if (folderIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_F_FOLDERID_7);

					sb.append(StringUtil.merge(folderIds));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_F, finderArgs, count);
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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_F(long groupId, long folderId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_F(groupId, folderId);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_F(long groupId, long[] folderIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderIds);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = InlineSQLHelperUtil.filter(
				findByG_F(groupId, folderIds), groupId);

			return journalArticles.size();
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.sortedUnique(folderIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

		if (folderIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_FOLDERID_7);

			sb.append(StringUtil.merge(folderIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
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

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 =
		"journalArticle.folderId = ?";

	private static final String _FINDER_COLUMN_G_F_FOLDERID_7 =
		"journalArticle.folderId IN (";

	private FinderPath _finderPathWithPaginationFindByG_A;
	private FinderPath _finderPathWithoutPaginationFindByG_A;
	private FinderPath _finderPathCountByG_A;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_A;

	/**
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A(long groupId, String articleId) {
		return findByG_A(
			groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A(
		long groupId, String articleId, int start, int end) {

		return findByG_A(groupId, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_A(
			groupId, articleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_A.find(
				finderCache, new Object[] {groupId, articleId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_A_First(
			long groupId, String articleId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_A_First(
			groupId, articleId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_A.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, articleId}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_First(
		long groupId, String articleId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_A.fetchFirst(
			finderCache, new Object[] {groupId, articleId}, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A(
		long groupId, String articleId) {

		return filterFindByG_A(
			groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A(
		long groupId, String articleId, int start, int end) {

		return filterFindByG_A(groupId, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A(groupId, articleId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_A(
					groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
		}

		articleId = Objects.toString(articleId, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			sb.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindArticleId) {
				queryPos.add(articleId);
			}

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 */
	@Override
	public void removeByG_A(long groupId, String articleId) {
		_collectionPersistenceFinderByG_A.remove(
			finderCache, new Object[] {groupId, articleId});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_A(long groupId, String articleId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_A.count(
				finderCache, new Object[] {groupId, articleId});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long groupId, String articleId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A(groupId, articleId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_A(
				groupId, articleId);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		articleId = Objects.toString(articleId, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			sb.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindArticleId) {
				queryPos.add(articleId);
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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_ARTICLEID_2 =
		"journalArticle.articleId = ?";

	private static final String _FINDER_COLUMN_G_A_ARTICLEID_3 =
		"(journalArticle.articleId IS NULL OR journalArticle.articleId = '')";

	private FinderPath _finderPathWithPaginationFindByG_UT;
	private FinderPath _finderPathWithoutPaginationFindByG_UT;
	private FinderPath _finderPathCountByG_UT;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_UT;

	/**
	 * Returns all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT(long groupId, String urlTitle) {
		return findByG_UT(
			groupId, urlTitle, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT(
		long groupId, String urlTitle, int start, int end) {

		return findByG_UT(groupId, urlTitle, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT(
		long groupId, String urlTitle, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_UT(
			groupId, urlTitle, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT(
		long groupId, String urlTitle, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_UT.find(
				finderCache, new Object[] {groupId, urlTitle}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_UT_First(
			long groupId, String urlTitle,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_UT_First(
			groupId, urlTitle, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_UT.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, urlTitle}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_UT_First(
		long groupId, String urlTitle,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_UT.fetchFirst(
			finderCache, new Object[] {groupId, urlTitle}, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT(
		long groupId, String urlTitle) {

		return filterFindByG_UT(
			groupId, urlTitle, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT(
		long groupId, String urlTitle, int start, int end) {

		return filterFindByG_UT(groupId, urlTitle, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT(
		long groupId, String urlTitle, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_UT(groupId, urlTitle, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_UT(
					groupId, urlTitle, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
		}

		urlTitle = Objects.toString(urlTitle, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_UT_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			sb.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindUrlTitle) {
				queryPos.add(urlTitle);
			}

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 */
	@Override
	public void removeByG_UT(long groupId, String urlTitle) {
		_collectionPersistenceFinderByG_UT.remove(
			finderCache, new Object[] {groupId, urlTitle});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_UT(long groupId, String urlTitle) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_UT.count(
				finderCache, new Object[] {groupId, urlTitle});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_UT(long groupId, String urlTitle) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_UT(groupId, urlTitle);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_UT(
				groupId, urlTitle);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		urlTitle = Objects.toString(urlTitle, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_UT_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			sb.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindUrlTitle) {
				queryPos.add(urlTitle);
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

	private static final String _FINDER_COLUMN_G_UT_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_UT_URLTITLE_2 =
		"journalArticle.urlTitle = ?";

	private static final String _FINDER_COLUMN_G_UT_URLTITLE_3 =
		"(journalArticle.urlTitle IS NULL OR journalArticle.urlTitle = '')";

	private FinderPath _finderPathWithPaginationFindByG_DDMSI;
	private FinderPath _finderPathWithoutPaginationFindByG_DDMSI;
	private FinderPath _finderPathCountByG_DDMSI;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_DDMSI;

	/**
	 * Returns all the journal articles where groupId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMSI(
		long groupId, long DDMStructureId) {

		return findByG_DDMSI(
			groupId, DDMStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMSI(
		long groupId, long DDMStructureId, int start, int end) {

		return findByG_DDMSI(groupId, DDMStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMSI(
		long groupId, long DDMStructureId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_DDMSI(
			groupId, DDMStructureId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMSI(
		long groupId, long DDMStructureId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_DDMSI.find(
				finderCache, new Object[] {groupId, DDMStructureId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_DDMSI_First(
			long groupId, long DDMStructureId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_DDMSI_First(
			groupId, DDMStructureId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_DDMSI.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, DDMStructureId}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_DDMSI_First(
		long groupId, long DDMStructureId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_DDMSI.fetchFirst(
			finderCache, new Object[] {groupId, DDMStructureId},
			orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMSI(
		long groupId, long DDMStructureId) {

		return filterFindByG_DDMSI(
			groupId, DDMStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMSI(
		long groupId, long DDMStructureId, int start, int end) {

		return filterFindByG_DDMSI(groupId, DDMStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMSI(
		long groupId, long DDMStructureId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_DDMSI(
				groupId, DDMStructureId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_DDMSI(
					groupId, DDMStructureId, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_DDMSI_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_DDMSI_DDMSTRUCTUREID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(DDMStructureId);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and DDMStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 */
	@Override
	public void removeByG_DDMSI(long groupId, long DDMStructureId) {
		_collectionPersistenceFinderByG_DDMSI.remove(
			finderCache, new Object[] {groupId, DDMStructureId});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_DDMSI(long groupId, long DDMStructureId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_DDMSI.count(
				finderCache, new Object[] {groupId, DDMStructureId});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_DDMSI(long groupId, long DDMStructureId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_DDMSI(groupId, DDMStructureId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_DDMSI(
				groupId, DDMStructureId);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_DDMSI_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_DDMSI_DDMSTRUCTUREID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(DDMStructureId);

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

	private static final String _FINDER_COLUMN_G_DDMSI_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_DDMSI_DDMSTRUCTUREID_2 =
		"journalArticle.DDMStructureId = ?";

	private FinderPath _finderPathWithPaginationFindByG_DDMTK;
	private FinderPath _finderPathWithoutPaginationFindByG_DDMTK;
	private FinderPath _finderPathCountByG_DDMTK;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_DDMTK;

	/**
	 * Returns all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMTK(
		long groupId, String DDMTemplateKey) {

		return findByG_DDMTK(
			groupId, DDMTemplateKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMTK(
		long groupId, String DDMTemplateKey, int start, int end) {

		return findByG_DDMTK(groupId, DDMTemplateKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMTK(
		long groupId, String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_DDMTK(
			groupId, DDMTemplateKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMTK(
		long groupId, String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_DDMTK.find(
				finderCache, new Object[] {groupId, DDMTemplateKey}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_DDMTK_First(
			long groupId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_DDMTK_First(
			groupId, DDMTemplateKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_DDMTK.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, DDMTemplateKey}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_DDMTK_First(
		long groupId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_DDMTK.fetchFirst(
			finderCache, new Object[] {groupId, DDMTemplateKey},
			orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMTK(
		long groupId, String DDMTemplateKey) {

		return filterFindByG_DDMTK(
			groupId, DDMTemplateKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMTK(
		long groupId, String DDMTemplateKey, int start, int end) {

		return filterFindByG_DDMTK(groupId, DDMTemplateKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMTK(
		long groupId, String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_DDMTK(
				groupId, DDMTemplateKey, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_DDMTK(
					groupId, DDMTemplateKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		DDMTemplateKey = Objects.toString(DDMTemplateKey, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_DDMTK_GROUPID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			sb.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindDDMTemplateKey) {
				queryPos.add(DDMTemplateKey);
			}

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and DDMTemplateKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 */
	@Override
	public void removeByG_DDMTK(long groupId, String DDMTemplateKey) {
		_collectionPersistenceFinderByG_DDMTK.remove(
			finderCache, new Object[] {groupId, DDMTemplateKey});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_DDMTK(long groupId, String DDMTemplateKey) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_DDMTK.count(
				finderCache, new Object[] {groupId, DDMTemplateKey});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_DDMTK(long groupId, String DDMTemplateKey) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_DDMTK(groupId, DDMTemplateKey);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_DDMTK(
				groupId, DDMTemplateKey);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		DDMTemplateKey = Objects.toString(DDMTemplateKey, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_DDMTK_GROUPID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			sb.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindDDMTemplateKey) {
				queryPos.add(DDMTemplateKey);
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

	private static final String _FINDER_COLUMN_G_DDMTK_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_2 =
		"journalArticle.DDMTemplateKey = ?";

	private static final String _FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_3 =
		"(journalArticle.DDMTemplateKey IS NULL OR journalArticle.DDMTemplateKey = '')";

	private FinderPath _finderPathWithPaginationFindByG_L;
	private FinderPath _finderPathWithoutPaginationFindByG_L;
	private FinderPath _finderPathCountByG_L;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_L;

	/**
	 * Returns all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_L(long groupId, String layoutUuid) {
		return findByG_L(
			groupId, layoutUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_L(
		long groupId, String layoutUuid, int start, int end) {

		return findByG_L(groupId, layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_L(
		long groupId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_L(
			groupId, layoutUuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_L(
		long groupId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_L.find(
				finderCache, new Object[] {groupId, layoutUuid}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_L_First(
			long groupId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_L_First(
			groupId, layoutUuid, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_L.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, layoutUuid}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_L_First(
		long groupId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_L.fetchFirst(
			finderCache, new Object[] {groupId, layoutUuid}, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_L(
		long groupId, String layoutUuid) {

		return filterFindByG_L(
			groupId, layoutUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_L(
		long groupId, String layoutUuid, int start, int end) {

		return filterFindByG_L(groupId, layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_L(
		long groupId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L(
				groupId, layoutUuid, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_L(
					groupId, layoutUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
				groupId);
		}

		layoutUuid = Objects.toString(layoutUuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			sb.append(_FINDER_COLUMN_G_L_LAYOUTUUID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindLayoutUuid) {
				queryPos.add(layoutUuid);
			}

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and layoutUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 */
	@Override
	public void removeByG_L(long groupId, String layoutUuid) {
		_collectionPersistenceFinderByG_L.remove(
			finderCache, new Object[] {groupId, layoutUuid});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_L(long groupId, String layoutUuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_L.count(
				finderCache, new Object[] {groupId, layoutUuid});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_L(long groupId, String layoutUuid) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_L(groupId, layoutUuid);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_L(
				groupId, layoutUuid);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		layoutUuid = Objects.toString(layoutUuid, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			sb.append(_FINDER_COLUMN_G_L_LAYOUTUUID_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindLayoutUuid) {
				queryPos.add(layoutUuid);
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

	private static final String _FINDER_COLUMN_G_L_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_L_LAYOUTUUID_2 =
		"journalArticle.layoutUuid = ?";

	private static final String _FINDER_COLUMN_G_L_LAYOUTUUID_3 =
		"(journalArticle.layoutUuid IS NULL OR journalArticle.layoutUuid = '')";

	private FinderPath _finderPathWithPaginationFindByG_ST;
	private FinderPath _finderPathWithoutPaginationFindByG_ST;
	private FinderPath _finderPathCountByG_ST;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_ST;

	/**
	 * Returns all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ST(long groupId, int status) {
		return findByG_ST(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ST(
		long groupId, int status, int start, int end) {

		return findByG_ST(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ST(
		long groupId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_ST(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ST(
		long groupId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_ST.find(
				finderCache, new Object[] {groupId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_ST_First(
			long groupId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_ST_First(
			groupId, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_ST.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, status}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_ST_First(
		long groupId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_ST.fetchFirst(
			finderCache, new Object[] {groupId, status}, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ST(long groupId, int status) {
		return filterFindByG_ST(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ST(
		long groupId, int status, int start, int end) {

		return filterFindByG_ST(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ST(
		long groupId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_ST(groupId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_ST(
					groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					orderByComparator),
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
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_ST_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_ST(long groupId, int status) {
		_collectionPersistenceFinderByG_ST.remove(
			finderCache, new Object[] {groupId, status});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_ST(long groupId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_ST.count(
				finderCache, new Object[] {groupId, status});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_ST(long groupId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_ST(groupId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_ST(groupId, status);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_ST_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_ST_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_ST_STATUS_2 =
		"journalArticle.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_V;
	private FinderPath _finderPathWithoutPaginationFindByC_V;
	private FinderPath _finderPathCountByC_V;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByC_V;

	/**
	 * Returns all the journal articles where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V(long companyId, double version) {
		return findByC_V(
			companyId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V(
		long companyId, double version, int start, int end) {

		return findByC_V(companyId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V(
		long companyId, double version, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByC_V(
			companyId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V(
		long companyId, double version, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByC_V.find(
				finderCache, new Object[] {companyId, version}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_V_First(
			long companyId, double version,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByC_V_First(
			companyId, version, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByC_V.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, version}));
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_V_First(
		long companyId, double version,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByC_V.fetchFirst(
			finderCache, new Object[] {companyId, version}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 */
	@Override
	public void removeByC_V(long companyId, double version) {
		_collectionPersistenceFinderByC_V.remove(
			finderCache, new Object[] {companyId, version});
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByC_V(long companyId, double version) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByC_V.count(
				finderCache, new Object[] {companyId, version});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_ST;
	private FinderPath _finderPathWithoutPaginationFindByC_ST;
	private FinderPath _finderPathCountByC_ST;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByC_ST;

	/**
	 * Returns all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_ST(long companyId, int status) {
		return findByC_ST(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_ST(
		long companyId, int status, int start, int end) {

		return findByC_ST(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_ST(
		long companyId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByC_ST(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_ST(
		long companyId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByC_ST.find(
				finderCache, new Object[] {companyId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_ST_First(
			long companyId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByC_ST_First(
			companyId, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByC_ST.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, status}));
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_ST_First(
		long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByC_ST.fetchFirst(
			finderCache, new Object[] {companyId, status}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_ST(long companyId, int status) {
		_collectionPersistenceFinderByC_ST.remove(
			finderCache, new Object[] {companyId, status});
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByC_ST(long companyId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByC_ST.count(
				finderCache, new Object[] {companyId, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_NotST;
	private FinderPath _finderPathWithPaginationCountByC_NotST;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByC_NotST;

	/**
	 * Returns all the journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_NotST(long companyId, int status) {
		return findByC_NotST(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_NotST(
		long companyId, int status, int start, int end) {

		return findByC_NotST(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_NotST(
		long companyId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByC_NotST(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_NotST(
		long companyId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByC_NotST.find(
				finderCache, new Object[] {companyId, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_NotST_First(
			long companyId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByC_NotST_First(
			companyId, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByC_NotST.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId, status}));
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_NotST_First(
		long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByC_NotST.fetchFirst(
			finderCache, new Object[] {companyId, status}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_NotST(long companyId, int status) {
		_collectionPersistenceFinderByC_NotST.remove(
			finderCache, new Object[] {companyId, status});
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByC_NotST(long companyId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByC_NotST.count(
				finderCache, new Object[] {companyId, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByLtD_S;

	/**
	 * Returns all the journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByLtD_S.find(
				finderCache, new Object[] {displayDate, status}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByLtD_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {displayDate, status}));
	}

	/**
	 * Returns the first journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByLtD_S.fetchFirst(
			finderCache, new Object[] {displayDate, status}, orderByComparator);
	}

	/**
	 * Removes all the journal articles where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		_collectionPersistenceFinderByLtD_S.remove(
			finderCache, new Object[] {displayDate, status});
	}

	/**
	 * Returns the number of journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByLtD_S.count(
				finderCache, new Object[] {displayDate, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByR_I_S;
	private FinderPath _finderPathWithoutPaginationFindByR_I_S;
	private FinderPath _finderPathCountByR_I_S;
	private FinderPath _finderPathWithPaginationCountByR_I_S;

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int status) {

		return findByR_I_S(
			resourcePrimKey, indexable, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int status, int start,
		int end) {

		return findByR_I_S(
			resourcePrimKey, indexable, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByR_I_S(
			resourcePrimKey, indexable, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByR_I_S;
					finderArgs = new Object[] {
						resourcePrimKey, indexable, status
					};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByR_I_S;
				finderArgs = new Object[] {
					resourcePrimKey, indexable, status, start, end,
					orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((resourcePrimKey !=
								journalArticle.getResourcePrimKey()) ||
							(indexable != journalArticle.isIndexable()) ||
							(status != journalArticle.getStatus())) {

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

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_R_I_S_RESOURCEPRIMKEY_2);

				sb.append(_FINDER_COLUMN_R_I_S_INDEXABLE_2);

				sb.append(_FINDER_COLUMN_R_I_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(resourcePrimKey);

					queryPos.add(indexable);

					queryPos.add(status);

					list = (List<JournalArticle>)QueryUtil.list(
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
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByR_I_S_First(
			long resourcePrimKey, boolean indexable, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByR_I_S_First(
			resourcePrimKey, indexable, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("resourcePrimKey=");
		sb.append(resourcePrimKey);

		sb.append(", indexable=");
		sb.append(indexable);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchArticleException(sb.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByR_I_S_First(
		long resourcePrimKey, boolean indexable, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		List<JournalArticle> list = findByR_I_S(
			resourcePrimKey, indexable, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int[] statuses) {

		return findByR_I_S(
			resourcePrimKey, indexable, statuses, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int[] statuses, int start,
		int end) {

		return findByR_I_S(
			resourcePrimKey, indexable, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int[] statuses, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		return findByR_I_S(
			resourcePrimKey, indexable, statuses, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int[] statuses, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		if (statuses.length == 1) {
			return findByR_I_S(
				resourcePrimKey, indexable, statuses[0], start, end,
				orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						resourcePrimKey, indexable, StringUtil.merge(statuses)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					resourcePrimKey, indexable, StringUtil.merge(statuses),
					start, end, orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					_finderPathWithPaginationFindByR_I_S, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((resourcePrimKey !=
								journalArticle.getResourcePrimKey()) ||
							(indexable != journalArticle.isIndexable()) ||
							!ArrayUtil.contains(
								statuses, journalArticle.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_R_I_S_RESOURCEPRIMKEY_2);

				sb.append(_FINDER_COLUMN_R_I_S_INDEXABLE_2);

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_R_I_S_STATUS_7);

					sb.append(StringUtil.merge(statuses));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(resourcePrimKey);

					queryPos.add(indexable);

					list = (List<JournalArticle>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByR_I_S, finderArgs,
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
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 */
	@Override
	public void removeByR_I_S(
		long resourcePrimKey, boolean indexable, int status) {

		for (JournalArticle journalArticle :
				findByR_I_S(
					resourcePrimKey, indexable, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByR_I_S(
		long resourcePrimKey, boolean indexable, int status) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			FinderPath finderPath = _finderPathCountByR_I_S;

			Object[] finderArgs = new Object[] {
				resourcePrimKey, indexable, status
			};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_R_I_S_RESOURCEPRIMKEY_2);

				sb.append(_FINDER_COLUMN_R_I_S_INDEXABLE_2);

				sb.append(_FINDER_COLUMN_R_I_S_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(resourcePrimKey);

					queryPos.add(indexable);

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
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByR_I_S(
		long resourcePrimKey, boolean indexable, int[] statuses) {

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = new Object[] {
				resourcePrimKey, indexable, StringUtil.merge(statuses)
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByR_I_S, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_R_I_S_RESOURCEPRIMKEY_2);

				sb.append(_FINDER_COLUMN_R_I_S_INDEXABLE_2);

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_R_I_S_STATUS_7);

					sb.append(StringUtil.merge(statuses));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(resourcePrimKey);

					queryPos.add(indexable);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByR_I_S, finderArgs,
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
	}

	private static final String _FINDER_COLUMN_R_I_S_RESOURCEPRIMKEY_2 =
		"journalArticle.resourcePrimKey = ? AND ";

	private static final String _FINDER_COLUMN_R_I_S_INDEXABLE_2 =
		"journalArticle.indexable = ? AND ";

	private static final String _FINDER_COLUMN_R_I_S_STATUS_2 =
		"journalArticle.status = ?";

	private static final String _FINDER_COLUMN_R_I_S_STATUS_7 =
		"journalArticle.status IN (";

	private FinderPath _finderPathWithPaginationFindByG_U_C;
	private FinderPath _finderPathWithoutPaginationFindByG_U_C;
	private FinderPath _finderPathCountByG_U_C;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_U_C;

	/**
	 * Returns all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U_C(
		long groupId, long userId, long classNameId) {

		return findByG_U_C(
			groupId, userId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U_C(
		long groupId, long userId, long classNameId, int start, int end) {

		return findByG_U_C(groupId, userId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U_C(
		long groupId, long userId, long classNameId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_U_C(
			groupId, userId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U_C(
		long groupId, long userId, long classNameId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_U_C.find(
				finderCache, new Object[] {groupId, userId, classNameId}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_U_C_First(
			long groupId, long userId, long classNameId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_U_C_First(
			groupId, userId, classNameId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_U_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, userId, classNameId}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_U_C_First(
		long groupId, long userId, long classNameId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_U_C.fetchFirst(
			finderCache, new Object[] {groupId, userId, classNameId},
			orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U_C(
		long groupId, long userId, long classNameId) {

		return filterFindByG_U_C(
			groupId, userId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U_C(
		long groupId, long userId, long classNameId, int start, int end) {

		return filterFindByG_U_C(
			groupId, userId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U_C(
		long groupId, long userId, long classNameId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_C(
				groupId, userId, classNameId, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_U_C(
					groupId, userId, classNameId, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_U_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_C_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_C_CLASSNAMEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			queryPos.add(classNameId);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_U_C(long groupId, long userId, long classNameId) {
		_collectionPersistenceFinderByG_U_C.remove(
			finderCache, new Object[] {groupId, userId, classNameId});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_U_C(long groupId, long userId, long classNameId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_U_C.count(
				finderCache, new Object[] {groupId, userId, classNameId});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_C(long groupId, long userId, long classNameId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_C(groupId, userId, classNameId);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_U_C(
				groupId, userId, classNameId);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_U_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_C_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_C_CLASSNAMEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(userId);

			queryPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_U_C_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_C_USERID_2 =
		"journalArticle.userId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_C_CLASSNAMEID_2 =
		"journalArticle.classNameId = ?";

	private FinderPath _finderPathFetchByG_ERC_V;
	private UniquePersistenceFinder<JournalArticle>
		_uniquePersistenceFinderByG_ERC_V;

	/**
	 * Returns the journal article where groupId = &#63; and externalReferenceCode = &#63; and version = &#63; or throws a <code>NoSuchArticleException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param version the version
	 * @return the matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_ERC_V(
			long groupId, String externalReferenceCode, double version)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_ERC_V(
			groupId, externalReferenceCode, version);

		if (journalArticle == null) {
			String message =
				_uniquePersistenceFinderByG_ERC_V.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, externalReferenceCode, version});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchArticleException(message);
		}

		return journalArticle;
	}

	/**
	 * Returns the journal article where groupId = &#63; and externalReferenceCode = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param version the version
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_ERC_V(
		long groupId, String externalReferenceCode, double version) {

		return fetchByG_ERC_V(groupId, externalReferenceCode, version, true);
	}

	/**
	 * Returns the journal article where groupId = &#63; and externalReferenceCode = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_ERC_V(
		long groupId, String externalReferenceCode, double version,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _uniquePersistenceFinderByG_ERC_V.fetch(
				finderCache,
				new Object[] {groupId, externalReferenceCode, version},
				useFinderCache);
		}
	}

	/**
	 * Removes the journal article where groupId = &#63; and externalReferenceCode = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param version the version
	 * @return the journal article that was removed
	 */
	@Override
	public JournalArticle removeByG_ERC_V(
			long groupId, String externalReferenceCode, double version)
		throws NoSuchArticleException {

		JournalArticle journalArticle = findByG_ERC_V(
			groupId, externalReferenceCode, version);

		return remove(journalArticle);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and externalReferenceCode = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param version the version
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_ERC_V(
		long groupId, String externalReferenceCode, double version) {

		return _uniquePersistenceFinderByG_ERC_V.count(
			finderCache,
			new Object[] {groupId, externalReferenceCode, version});
	}

	private FinderPath _finderPathWithPaginationFindByG_ERC_ST;
	private FinderPath _finderPathWithoutPaginationFindByG_ERC_ST;
	private FinderPath _finderPathCountByG_ERC_ST;
	private FinderPath _finderPathWithPaginationCountByG_ERC_ST;

	/**
	 * Returns all the journal articles where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC_ST(
		long groupId, String externalReferenceCode, int status) {

		return findByG_ERC_ST(
			groupId, externalReferenceCode, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC_ST(
		long groupId, String externalReferenceCode, int status, int start,
		int end) {

		return findByG_ERC_ST(
			groupId, externalReferenceCode, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC_ST(
		long groupId, String externalReferenceCode, int status, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_ERC_ST(
			groupId, externalReferenceCode, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC_ST(
		long groupId, String externalReferenceCode, int status, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			externalReferenceCode = Objects.toString(externalReferenceCode, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_ERC_ST;
					finderArgs = new Object[] {
						groupId, externalReferenceCode, status
					};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_ERC_ST;
				finderArgs = new Object[] {
					groupId, externalReferenceCode, status, start, end,
					orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((groupId != journalArticle.getGroupId()) ||
							!externalReferenceCode.equals(
								journalArticle.getExternalReferenceCode()) ||
							(status != journalArticle.getStatus())) {

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

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_ERC_ST_GROUPID_2);

				boolean bindExternalReferenceCode = false;

				if (externalReferenceCode.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_3);
				}
				else {
					bindExternalReferenceCode = true;

					sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_2);
				}

				sb.append(_FINDER_COLUMN_G_ERC_ST_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					if (bindExternalReferenceCode) {
						queryPos.add(externalReferenceCode);
					}

					queryPos.add(status);

					list = (List<JournalArticle>)QueryUtil.list(
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_ERC_ST_First(
			long groupId, String externalReferenceCode, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_ERC_ST_First(
			groupId, externalReferenceCode, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchArticleException(sb.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_ERC_ST_First(
		long groupId, String externalReferenceCode, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		List<JournalArticle> list = findByG_ERC_ST(
			groupId, externalReferenceCode, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ERC_ST(
		long groupId, String externalReferenceCode, int status) {

		return filterFindByG_ERC_ST(
			groupId, externalReferenceCode, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ERC_ST(
		long groupId, String externalReferenceCode, int status, int start,
		int end) {

		return filterFindByG_ERC_ST(
			groupId, externalReferenceCode, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ERC_ST(
		long groupId, String externalReferenceCode, int status, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_ERC_ST(
				groupId, externalReferenceCode, status, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_ERC_ST(
					groupId, externalReferenceCode, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_ERC_ST_GROUPID_2);

		boolean bindExternalReferenceCode = false;

		if (externalReferenceCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_3);
		}
		else {
			bindExternalReferenceCode = true;

			sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_2);
		}

		sb.append(_FINDER_COLUMN_G_ERC_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindExternalReferenceCode) {
				queryPos.add(externalReferenceCode);
			}

			queryPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and externalReferenceCode = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param statuses the statuses
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ERC_ST(
		long groupId, String externalReferenceCode, int[] statuses) {

		return filterFindByG_ERC_ST(
			groupId, externalReferenceCode, statuses, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and externalReferenceCode = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ERC_ST(
		long groupId, String externalReferenceCode, int[] statuses, int start,
		int end) {

		return filterFindByG_ERC_ST(
			groupId, externalReferenceCode, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and externalReferenceCode = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ERC_ST(
		long groupId, String externalReferenceCode, int[] statuses, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_ERC_ST(
				groupId, externalReferenceCode, statuses, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_ERC_ST(
					groupId, externalReferenceCode, statuses, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_ERC_ST_GROUPID_2);

		boolean bindExternalReferenceCode = false;

		if (externalReferenceCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_3);
		}
		else {
			bindExternalReferenceCode = true;

			sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_2);
		}

		if (statuses.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_ERC_ST_STATUS_7);

			sb.append(StringUtil.merge(statuses));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindExternalReferenceCode) {
				queryPos.add(externalReferenceCode);
			}

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Returns all the journal articles where groupId = &#63; and externalReferenceCode = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC_ST(
		long groupId, String externalReferenceCode, int[] statuses) {

		return findByG_ERC_ST(
			groupId, externalReferenceCode, statuses, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and externalReferenceCode = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC_ST(
		long groupId, String externalReferenceCode, int[] statuses, int start,
		int end) {

		return findByG_ERC_ST(
			groupId, externalReferenceCode, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and externalReferenceCode = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC_ST(
		long groupId, String externalReferenceCode, int[] statuses, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_ERC_ST(
			groupId, externalReferenceCode, statuses, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ERC_ST(
		long groupId, String externalReferenceCode, int[] statuses, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		if (statuses.length == 1) {
			return findByG_ERC_ST(
				groupId, externalReferenceCode, statuses[0], start, end,
				orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						groupId, externalReferenceCode,
						StringUtil.merge(statuses)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, externalReferenceCode, StringUtil.merge(statuses),
					start, end, orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					_finderPathWithPaginationFindByG_ERC_ST, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((groupId != journalArticle.getGroupId()) ||
							!externalReferenceCode.equals(
								journalArticle.getExternalReferenceCode()) ||
							!ArrayUtil.contains(
								statuses, journalArticle.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_ERC_ST_GROUPID_2);

				boolean bindExternalReferenceCode = false;

				if (externalReferenceCode.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_3);
				}
				else {
					bindExternalReferenceCode = true;

					sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_2);
				}

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_ERC_ST_STATUS_7);

					sb.append(StringUtil.merge(statuses));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					if (bindExternalReferenceCode) {
						queryPos.add(externalReferenceCode);
					}

					list = (List<JournalArticle>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_ERC_ST, finderArgs,
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
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and externalReferenceCode = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 */
	@Override
	public void removeByG_ERC_ST(
		long groupId, String externalReferenceCode, int status) {

		for (JournalArticle journalArticle :
				findByG_ERC_ST(
					groupId, externalReferenceCode, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_ERC_ST(
		long groupId, String externalReferenceCode, int status) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			externalReferenceCode = Objects.toString(externalReferenceCode, "");

			FinderPath finderPath = _finderPathCountByG_ERC_ST;

			Object[] finderArgs = new Object[] {
				groupId, externalReferenceCode, status
			};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_ERC_ST_GROUPID_2);

				boolean bindExternalReferenceCode = false;

				if (externalReferenceCode.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_3);
				}
				else {
					bindExternalReferenceCode = true;

					sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_2);
				}

				sb.append(_FINDER_COLUMN_G_ERC_ST_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					if (bindExternalReferenceCode) {
						queryPos.add(externalReferenceCode);
					}

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
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and externalReferenceCode = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_ERC_ST(
		long groupId, String externalReferenceCode, int[] statuses) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = new Object[] {
				groupId, externalReferenceCode, StringUtil.merge(statuses)
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_ERC_ST, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_ERC_ST_GROUPID_2);

				boolean bindExternalReferenceCode = false;

				if (externalReferenceCode.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_3);
				}
				else {
					bindExternalReferenceCode = true;

					sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_2);
				}

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_ERC_ST_STATUS_7);

					sb.append(StringUtil.merge(statuses));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					if (bindExternalReferenceCode) {
						queryPos.add(externalReferenceCode);
					}

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_ERC_ST, finderArgs,
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
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and externalReferenceCode = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_ERC_ST(
		long groupId, String externalReferenceCode, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_ERC_ST(groupId, externalReferenceCode, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_ERC_ST(
				groupId, externalReferenceCode, status);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_ERC_ST_GROUPID_2);

		boolean bindExternalReferenceCode = false;

		if (externalReferenceCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_3);
		}
		else {
			bindExternalReferenceCode = true;

			sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_2);
		}

		sb.append(_FINDER_COLUMN_G_ERC_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindExternalReferenceCode) {
				queryPos.add(externalReferenceCode);
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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and externalReferenceCode = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param statuses the statuses
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_ERC_ST(
		long groupId, String externalReferenceCode, int[] statuses) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_ERC_ST(groupId, externalReferenceCode, statuses);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = InlineSQLHelperUtil.filter(
				findByG_ERC_ST(groupId, externalReferenceCode, statuses),
				groupId);

			return journalArticles.size();
		}

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_ERC_ST_GROUPID_2);

		boolean bindExternalReferenceCode = false;

		if (externalReferenceCode.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_3);
		}
		else {
			bindExternalReferenceCode = true;

			sb.append(_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_2);
		}

		if (statuses.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_ERC_ST_STATUS_7);

			sb.append(StringUtil.merge(statuses));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindExternalReferenceCode) {
				queryPos.add(externalReferenceCode);
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

	private static final String _FINDER_COLUMN_G_ERC_ST_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_2 =
			"journalArticle.externalReferenceCode = ? AND ";

	private static final String
		_FINDER_COLUMN_G_ERC_ST_EXTERNALREFERENCECODE_3 =
			"(journalArticle.externalReferenceCode IS NULL OR journalArticle.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_G_ERC_ST_STATUS_2 =
		"journalArticle.status = ?";

	private static final String _FINDER_COLUMN_G_ERC_ST_STATUS_7 =
		"journalArticle.status IN (";

	private FinderPath _finderPathWithPaginationFindByG_F_ST;
	private FinderPath _finderPathWithoutPaginationFindByG_F_ST;
	private FinderPath _finderPathCountByG_F_ST;
	private FinderPath _finderPathWithPaginationCountByG_F_ST;

	/**
	 * Returns all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int status) {

		return findByG_F_ST(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int status, int start, int end) {

		return findByG_F_ST(groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_F_ST(
			groupId, folderId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_F_ST;
					finderArgs = new Object[] {groupId, folderId, status};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_F_ST;
				finderArgs = new Object[] {
					groupId, folderId, status, start, end, orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((groupId != journalArticle.getGroupId()) ||
							(folderId != journalArticle.getFolderId()) ||
							(status != journalArticle.getStatus())) {

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

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

				sb.append(_FINDER_COLUMN_G_F_ST_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

					queryPos.add(status);

					list = (List<JournalArticle>)QueryUtil.list(
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_F_ST_First(
			long groupId, long folderId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_F_ST_First(
			groupId, folderId, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", folderId=");
		sb.append(folderId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchArticleException(sb.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_F_ST_First(
		long groupId, long folderId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		List<JournalArticle> list = findByG_F_ST(
			groupId, folderId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int status) {

		return filterFindByG_F_ST(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int status, int start, int end) {

		return filterFindByG_F_ST(groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_ST(
				groupId, folderId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F_ST(
					groupId, folderId, status, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_F_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			queryPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int[] statuses) {

		return filterFindByG_F_ST(
			groupId, folderId, statuses, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int[] statuses, int start, int end) {

		return filterFindByG_F_ST(
			groupId, folderId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_ST(
				groupId, folderId, statuses, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F_ST(
					groupId, folderId, statuses, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

		if (statuses.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_ST_STATUS_7);

			sb.append(StringUtil.merge(statuses));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Returns all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int[] statuses) {

		return findByG_F_ST(
			groupId, folderId, statuses, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int[] statuses, int start, int end) {

		return findByG_F_ST(groupId, folderId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_F_ST(
			groupId, folderId, statuses, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		if (statuses.length == 1) {
			return findByG_F_ST(
				groupId, folderId, statuses[0], start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						groupId, folderId, StringUtil.merge(statuses)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, folderId, StringUtil.merge(statuses), start, end,
					orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					_finderPathWithPaginationFindByG_F_ST, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((groupId != journalArticle.getGroupId()) ||
							(folderId != journalArticle.getFolderId()) ||
							!ArrayUtil.contains(
								statuses, journalArticle.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_F_ST_STATUS_7);

					sb.append(StringUtil.merge(statuses));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

					list = (List<JournalArticle>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_F_ST, finderArgs,
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
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 */
	@Override
	public void removeByG_F_ST(long groupId, long folderId, int status) {
		for (JournalArticle journalArticle :
				findByG_F_ST(
					groupId, folderId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_F_ST(long groupId, long folderId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			FinderPath finderPath = _finderPathCountByG_F_ST;

			Object[] finderArgs = new Object[] {groupId, folderId, status};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

				sb.append(_FINDER_COLUMN_G_F_ST_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

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
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_F_ST(long groupId, long folderId, int[] statuses) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = new Object[] {
				groupId, folderId, StringUtil.merge(statuses)
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_F_ST, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_F_ST_STATUS_7);

					sb.append(StringUtil.merge(statuses));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(folderId);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_F_ST, finderArgs,
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
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_ST(long groupId, long folderId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_ST(groupId, folderId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_F_ST(
				groupId, folderId, status);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_F_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_ST(
		long groupId, long folderId, int[] statuses) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_ST(groupId, folderId, statuses);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = InlineSQLHelperUtil.filter(
				findByG_F_ST(groupId, folderId, statuses), groupId);

			return journalArticles.size();
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

		if (statuses.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_F_ST_STATUS_7);

			sb.append(StringUtil.merge(statuses));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

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

	private static final String _FINDER_COLUMN_G_F_ST_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_ST_FOLDERID_2 =
		"journalArticle.folderId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_ST_STATUS_2 =
		"journalArticle.status = ?";

	private static final String _FINDER_COLUMN_G_F_ST_STATUS_7 =
		"journalArticle.status IN (";

	private FinderPath _finderPathWithPaginationFindByG_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C;
	private FinderPath _finderPathCountByG_C_C;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_C_C;

	/**
	 * Returns all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_C(
		long groupId, long classNameId, long classPK) {

		return findByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_C_C.find(
				finderCache, new Object[] {groupId, classNameId, classPK},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_C_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, classNameId, classPK}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_C_C.fetchFirst(
			finderCache, new Object[] {groupId, classNameId, classPK},
			orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_C(
		long groupId, long classNameId, long classPK) {

		return filterFindByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return filterFindByG_C_C(
			groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C(
				groupId, classNameId, classPK, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_C_C(
					groupId, classNameId, classPK, QueryUtil.ALL_POS,
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
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classPK);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		_collectionPersistenceFinderByG_C_C.remove(
			finderCache, new Object[] {groupId, classNameId, classPK});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_C_C.count(
				finderCache, new Object[] {groupId, classNameId, classPK});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C(
		long groupId, long classNameId, long classPK) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C(groupId, classNameId, classPK);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_C_C(
				groupId, classNameId, classPK);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 =
		"journalArticle.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 =
		"journalArticle.classPK = ?";

	private FinderPath _finderPathFetchByG_C_DDMSI;
	private UniquePersistenceFinder<JournalArticle>
		_uniquePersistenceFinderByG_C_DDMSI;

	/**
	 * Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureId = &#63; or throws a <code>NoSuchArticleException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_DDMSI(
			long groupId, long classNameId, long DDMStructureId)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_C_DDMSI(
			groupId, classNameId, DDMStructureId);

		if (journalArticle == null) {
			String message =
				_uniquePersistenceFinderByG_C_DDMSI.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, classNameId, DDMStructureId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchArticleException(message);
		}

		return journalArticle;
	}

	/**
	 * Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_DDMSI(
		long groupId, long classNameId, long DDMStructureId) {

		return fetchByG_C_DDMSI(groupId, classNameId, DDMStructureId, true);
	}

	/**
	 * Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureId the ddm structure ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_DDMSI(
		long groupId, long classNameId, long DDMStructureId,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _uniquePersistenceFinderByG_C_DDMSI.fetch(
				finderCache,
				new Object[] {groupId, classNameId, DDMStructureId},
				useFinderCache);
		}
	}

	/**
	 * Removes the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the journal article that was removed
	 */
	@Override
	public JournalArticle removeByG_C_DDMSI(
			long groupId, long classNameId, long DDMStructureId)
		throws NoSuchArticleException {

		JournalArticle journalArticle = findByG_C_DDMSI(
			groupId, classNameId, DDMStructureId);

		return remove(journalArticle);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_C_DDMSI(
		long groupId, long classNameId, long DDMStructureId) {

		return _uniquePersistenceFinderByG_C_DDMSI.count(
			finderCache, new Object[] {groupId, classNameId, DDMStructureId});
	}

	private FinderPath _finderPathWithPaginationFindByG_C_DDMTK;
	private FinderPath _finderPathWithoutPaginationFindByG_C_DDMTK;
	private FinderPath _finderPathCountByG_C_DDMTK;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_C_DDMTK;

	/**
	 * Returns all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey) {

		return findByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey, int start,
		int end) {

		return findByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_C_DDMTK.find(
				finderCache,
				new Object[] {groupId, classNameId, DDMTemplateKey}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_DDMTK_First(
			long groupId, long classNameId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_C_DDMTK_First(
			groupId, classNameId, DDMTemplateKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_C_DDMTK.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, classNameId, DDMTemplateKey}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_DDMTK_First(
		long groupId, long classNameId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_C_DDMTK.fetchFirst(
			finderCache, new Object[] {groupId, classNameId, DDMTemplateKey},
			orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey) {

		return filterFindByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey, int start,
		int end) {

		return filterFindByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_DDMTK(
				groupId, classNameId, DDMTemplateKey, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_C_DDMTK(
					groupId, classNameId, DDMTemplateKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		DDMTemplateKey = Objects.toString(DDMTemplateKey, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_DDMTK_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_DDMTK_CLASSNAMEID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			sb.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			if (bindDDMTemplateKey) {
				queryPos.add(DDMTemplateKey);
			}

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 */
	@Override
	public void removeByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey) {

		_collectionPersistenceFinderByG_C_DDMTK.remove(
			finderCache, new Object[] {groupId, classNameId, DDMTemplateKey});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_C_DDMTK.count(
				finderCache,
				new Object[] {groupId, classNameId, DDMTemplateKey});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_DDMTK(groupId, classNameId, DDMTemplateKey);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_C_DDMTK(
				groupId, classNameId, DDMTemplateKey);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		DDMTemplateKey = Objects.toString(DDMTemplateKey, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_C_DDMTK_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_DDMTK_CLASSNAMEID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			sb.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			if (bindDDMTemplateKey) {
				queryPos.add(DDMTemplateKey);
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

	private static final String _FINDER_COLUMN_G_C_DDMTK_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_DDMTK_CLASSNAMEID_2 =
		"journalArticle.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_2 =
		"journalArticle.DDMTemplateKey = ?";

	private static final String _FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_3 =
		"(journalArticle.DDMTemplateKey IS NULL OR journalArticle.DDMTemplateKey = '')";

	private FinderPath _finderPathWithPaginationFindByG_C_L;
	private FinderPath _finderPathWithoutPaginationFindByG_C_L;
	private FinderPath _finderPathCountByG_C_L;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_C_L;

	/**
	 * Returns all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_L(
		long groupId, long classNameId, String layoutUuid) {

		return findByG_C_L(
			groupId, classNameId, layoutUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_L(
		long groupId, long classNameId, String layoutUuid, int start, int end) {

		return findByG_C_L(groupId, classNameId, layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_L(
		long groupId, long classNameId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_C_L(
			groupId, classNameId, layoutUuid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_L(
		long groupId, long classNameId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_C_L.find(
				finderCache, new Object[] {groupId, classNameId, layoutUuid},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_L_First(
			long groupId, long classNameId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_C_L_First(
			groupId, classNameId, layoutUuid, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_C_L.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, classNameId, layoutUuid}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_L_First(
		long groupId, long classNameId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_C_L.fetchFirst(
			finderCache, new Object[] {groupId, classNameId, layoutUuid},
			orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_L(
		long groupId, long classNameId, String layoutUuid) {

		return filterFindByG_C_L(
			groupId, classNameId, layoutUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_L(
		long groupId, long classNameId, String layoutUuid, int start, int end) {

		return filterFindByG_C_L(
			groupId, classNameId, layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_L(
		long groupId, long classNameId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_L(
				groupId, classNameId, layoutUuid, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_C_L(
					groupId, classNameId, layoutUuid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		layoutUuid = Objects.toString(layoutUuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_L_CLASSNAMEID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			sb.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			if (bindLayoutUuid) {
				queryPos.add(layoutUuid);
			}

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 */
	@Override
	public void removeByG_C_L(
		long groupId, long classNameId, String layoutUuid) {

		_collectionPersistenceFinderByG_C_L.remove(
			finderCache, new Object[] {groupId, classNameId, layoutUuid});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_C_L(long groupId, long classNameId, String layoutUuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_C_L.count(
				finderCache, new Object[] {groupId, classNameId, layoutUuid});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_L(
		long groupId, long classNameId, String layoutUuid) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_L(groupId, classNameId, layoutUuid);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_C_L(
				groupId, classNameId, layoutUuid);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		layoutUuid = Objects.toString(layoutUuid, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_L_CLASSNAMEID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			sb.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			if (bindLayoutUuid) {
				queryPos.add(layoutUuid);
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

	private static final String _FINDER_COLUMN_G_C_L_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_L_CLASSNAMEID_2 =
		"journalArticle.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_L_LAYOUTUUID_2 =
		"journalArticle.layoutUuid = ?";

	private static final String _FINDER_COLUMN_G_C_L_LAYOUTUUID_3 =
		"(journalArticle.layoutUuid IS NULL OR journalArticle.layoutUuid = '')";

	private FinderPath _finderPathWithPaginationFindByG_C_NotL;
	private FinderPath _finderPathWithPaginationCountByG_C_NotL;

	/**
	 * Returns all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_NotL(
		long groupId, long classNameId, String layoutUuid) {

		return findByG_C_NotL(
			groupId, classNameId, layoutUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_NotL(
		long groupId, long classNameId, String layoutUuid, int start, int end) {

		return findByG_C_NotL(
			groupId, classNameId, layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_NotL(
		long groupId, long classNameId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_C_NotL(
			groupId, classNameId, layoutUuid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_NotL(
		long groupId, long classNameId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			layoutUuid = Objects.toString(layoutUuid, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			finderPath = _finderPathWithPaginationFindByG_C_NotL;
			finderArgs = new Object[] {
				groupId, classNameId, layoutUuid, start, end, orderByComparator
			};

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((groupId != journalArticle.getGroupId()) ||
							(classNameId != journalArticle.getClassNameId()) ||
							layoutUuid.equals(journalArticle.getLayoutUuid())) {

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

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_C_NOTL_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTL_CLASSNAMEID_2);

				boolean bindLayoutUuid = false;

				if (layoutUuid.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_3);
				}
				else {
					bindLayoutUuid = true;

					sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(classNameId);

					if (bindLayoutUuid) {
						queryPos.add(layoutUuid);
					}

					list = (List<JournalArticle>)QueryUtil.list(
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_NotL_First(
			long groupId, long classNameId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_C_NotL_First(
			groupId, classNameId, layoutUuid, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", layoutUuid!=");
		sb.append(layoutUuid);

		sb.append("}");

		throw new NoSuchArticleException(sb.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_NotL_First(
		long groupId, long classNameId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		List<JournalArticle> list = findByG_C_NotL(
			groupId, classNameId, layoutUuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_NotL(
		long groupId, long classNameId, String layoutUuid) {

		return filterFindByG_C_NotL(
			groupId, classNameId, layoutUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_NotL(
		long groupId, long classNameId, String layoutUuid, int start, int end) {

		return filterFindByG_C_NotL(
			groupId, classNameId, layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_NotL(
		long groupId, long classNameId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_NotL(
				groupId, classNameId, layoutUuid, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_C_NotL(
					groupId, classNameId, layoutUuid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		layoutUuid = Objects.toString(layoutUuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_NOTL_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTL_CLASSNAMEID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			if (bindLayoutUuid) {
				queryPos.add(layoutUuid);
			}

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuids the layout uuids
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_NotL(
		long groupId, long classNameId, String[] layoutUuids) {

		return filterFindByG_C_NotL(
			groupId, classNameId, layoutUuids, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuids the layout uuids
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_NotL(
		long groupId, long classNameId, String[] layoutUuids, int start,
		int end) {

		return filterFindByG_C_NotL(
			groupId, classNameId, layoutUuids, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuids the layout uuids
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_NotL(
		long groupId, long classNameId, String[] layoutUuids, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_NotL(
				groupId, classNameId, layoutUuids, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_C_NotL(
					groupId, classNameId, layoutUuids, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		if (layoutUuids == null) {
			layoutUuids = new String[0];
		}
		else if (layoutUuids.length > 1) {
			for (int i = 0; i < layoutUuids.length; i++) {
				layoutUuids[i] = Objects.toString(layoutUuids[i], "");
			}

			layoutUuids = ArrayUtil.sortedUnique(layoutUuids);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_NOTL_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTL_CLASSNAMEID_2);

		if (layoutUuids.length > 0) {
			sb.append("(");

			for (int i = 0; i < layoutUuids.length; i++) {
				String layoutUuid = layoutUuids[i];

				if (layoutUuid.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_3);
				}
				else {
					sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_2);
				}

				if ((i + 1) < layoutUuids.length) {
					sb.append(WHERE_AND);
				}
			}

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			for (String layoutUuid : layoutUuids) {
				if ((layoutUuid != null) && !layoutUuid.isEmpty()) {
					queryPos.add(layoutUuid);
				}
			}

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Returns all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuids the layout uuids
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_NotL(
		long groupId, long classNameId, String[] layoutUuids) {

		return findByG_C_NotL(
			groupId, classNameId, layoutUuids, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuids the layout uuids
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_NotL(
		long groupId, long classNameId, String[] layoutUuids, int start,
		int end) {

		return findByG_C_NotL(
			groupId, classNameId, layoutUuids, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuids the layout uuids
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_NotL(
		long groupId, long classNameId, String[] layoutUuids, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_C_NotL(
			groupId, classNameId, layoutUuids, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuids the layout uuids
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_NotL(
		long groupId, long classNameId, String[] layoutUuids, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		if (layoutUuids == null) {
			layoutUuids = new String[0];
		}
		else if (layoutUuids.length > 1) {
			for (int i = 0; i < layoutUuids.length; i++) {
				layoutUuids[i] = Objects.toString(layoutUuids[i], "");
			}

			layoutUuids = ArrayUtil.sortedUnique(layoutUuids);
		}

		if (layoutUuids.length == 1) {
			return findByG_C_NotL(
				groupId, classNameId, layoutUuids[0], start, end,
				orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						groupId, classNameId, StringUtil.merge(layoutUuids)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, classNameId, StringUtil.merge(layoutUuids), start,
					end, orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					_finderPathWithPaginationFindByG_C_NotL, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((groupId != journalArticle.getGroupId()) ||
							(classNameId != journalArticle.getClassNameId()) ||
							!ArrayUtil.contains(
								layoutUuids, journalArticle.getLayoutUuid())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_C_NOTL_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTL_CLASSNAMEID_2);

				if (layoutUuids.length > 0) {
					sb.append("(");

					for (int i = 0; i < layoutUuids.length; i++) {
						String layoutUuid = layoutUuids[i];

						if (layoutUuid.isEmpty()) {
							sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_3);
						}
						else {
							sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_2);
						}

						if ((i + 1) < layoutUuids.length) {
							sb.append(WHERE_AND);
						}
					}

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(classNameId);

					for (String layoutUuid : layoutUuids) {
						if ((layoutUuid != null) && !layoutUuid.isEmpty()) {
							queryPos.add(layoutUuid);
						}
					}

					list = (List<JournalArticle>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_C_NotL, finderArgs,
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
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 */
	@Override
	public void removeByG_C_NotL(
		long groupId, long classNameId, String layoutUuid) {

		for (JournalArticle journalArticle :
				findByG_C_NotL(
					groupId, classNameId, layoutUuid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_C_NotL(
		long groupId, long classNameId, String layoutUuid) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			layoutUuid = Objects.toString(layoutUuid, "");

			FinderPath finderPath = _finderPathWithPaginationCountByG_C_NotL;

			Object[] finderArgs = new Object[] {
				groupId, classNameId, layoutUuid
			};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_C_NOTL_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTL_CLASSNAMEID_2);

				boolean bindLayoutUuid = false;

				if (layoutUuid.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_3);
				}
				else {
					bindLayoutUuid = true;

					sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(classNameId);

					if (bindLayoutUuid) {
						queryPos.add(layoutUuid);
					}

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
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuids the layout uuids
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_C_NotL(
		long groupId, long classNameId, String[] layoutUuids) {

		if (layoutUuids == null) {
			layoutUuids = new String[0];
		}
		else if (layoutUuids.length > 1) {
			for (int i = 0; i < layoutUuids.length; i++) {
				layoutUuids[i] = Objects.toString(layoutUuids[i], "");
			}

			layoutUuids = ArrayUtil.sortedUnique(layoutUuids);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = new Object[] {
				groupId, classNameId, StringUtil.merge(layoutUuids)
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_C_NotL, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_C_NOTL_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_C_NOTL_CLASSNAMEID_2);

				if (layoutUuids.length > 0) {
					sb.append("(");

					for (int i = 0; i < layoutUuids.length; i++) {
						String layoutUuid = layoutUuids[i];

						if (layoutUuid.isEmpty()) {
							sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_3);
						}
						else {
							sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_2);
						}

						if ((i + 1) < layoutUuids.length) {
							sb.append(WHERE_AND);
						}
					}

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(classNameId);

					for (String layoutUuid : layoutUuids) {
						if ((layoutUuid != null) && !layoutUuid.isEmpty()) {
							queryPos.add(layoutUuid);
						}
					}

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_C_NotL, finderArgs,
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
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_NotL(
		long groupId, long classNameId, String layoutUuid) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_NotL(groupId, classNameId, layoutUuid);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_C_NotL(
				groupId, classNameId, layoutUuid);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		layoutUuid = Objects.toString(layoutUuid, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_C_NOTL_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTL_CLASSNAMEID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			if (bindLayoutUuid) {
				queryPos.add(layoutUuid);
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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuids the layout uuids
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_NotL(
		long groupId, long classNameId, String[] layoutUuids) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_NotL(groupId, classNameId, layoutUuids);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = InlineSQLHelperUtil.filter(
				findByG_C_NotL(groupId, classNameId, layoutUuids), groupId);

			return journalArticles.size();
		}

		if (layoutUuids == null) {
			layoutUuids = new String[0];
		}
		else if (layoutUuids.length > 1) {
			for (int i = 0; i < layoutUuids.length; i++) {
				layoutUuids[i] = Objects.toString(layoutUuids[i], "");
			}

			layoutUuids = ArrayUtil.sortedUnique(layoutUuids);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_C_NOTL_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTL_CLASSNAMEID_2);

		if (layoutUuids.length > 0) {
			sb.append("(");

			for (int i = 0; i < layoutUuids.length; i++) {
				String layoutUuid = layoutUuids[i];

				if (layoutUuid.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_3);
				}
				else {
					sb.append(_FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_2);
				}

				if ((i + 1) < layoutUuids.length) {
					sb.append(WHERE_AND);
				}
			}

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			for (String layoutUuid : layoutUuids) {
				if ((layoutUuid != null) && !layoutUuid.isEmpty()) {
					queryPos.add(layoutUuid);
				}
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

	private static final String _FINDER_COLUMN_G_C_NOTL_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_NOTL_CLASSNAMEID_2 =
		"journalArticle.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_2 =
		"journalArticle.layoutUuid != ?";

	private static final String _FINDER_COLUMN_G_C_NOTL_LAYOUTUUID_3 =
		"(journalArticle.layoutUuid IS NULL OR journalArticle.layoutUuid != '')";

	private FinderPath _finderPathFetchByG_A_V;
	private UniquePersistenceFinder<JournalArticle>
		_uniquePersistenceFinderByG_A_V;

	/**
	 * Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or throws a <code>NoSuchArticleException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_A_V(
			long groupId, String articleId, double version)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_A_V(
			groupId, articleId, version);

		if (journalArticle == null) {
			String message =
				_uniquePersistenceFinderByG_A_V.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, articleId, version});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchArticleException(message);
		}

		return journalArticle;
	}

	/**
	 * Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_V(
		long groupId, String articleId, double version) {

		return fetchByG_A_V(groupId, articleId, version, true);
	}

	/**
	 * Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_V(
		long groupId, String articleId, double version,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _uniquePersistenceFinderByG_A_V.fetch(
				finderCache, new Object[] {groupId, articleId, version},
				useFinderCache);
		}
	}

	/**
	 * Removes the journal article where groupId = &#63; and articleId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the journal article that was removed
	 */
	@Override
	public JournalArticle removeByG_A_V(
			long groupId, String articleId, double version)
		throws NoSuchArticleException {

		JournalArticle journalArticle = findByG_A_V(
			groupId, articleId, version);

		return remove(journalArticle);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_A_V(long groupId, String articleId, double version) {
		return _uniquePersistenceFinderByG_A_V.count(
			finderCache, new Object[] {groupId, articleId, version});
	}

	private FinderPath _finderPathWithPaginationFindByG_A_ST;
	private FinderPath _finderPathWithoutPaginationFindByG_A_ST;
	private FinderPath _finderPathCountByG_A_ST;
	private FinderPath _finderPathWithPaginationCountByG_A_ST;

	/**
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int status) {

		return findByG_A_ST(
			groupId, articleId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int status, int start, int end) {

		return findByG_A_ST(groupId, articleId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_A_ST(
			groupId, articleId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			articleId = Objects.toString(articleId, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_A_ST;
					finderArgs = new Object[] {groupId, articleId, status};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_A_ST;
				finderArgs = new Object[] {
					groupId, articleId, status, start, end, orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((groupId != journalArticle.getGroupId()) ||
							!articleId.equals(journalArticle.getArticleId()) ||
							(status != journalArticle.getStatus())) {

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

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

				boolean bindArticleId = false;

				if (articleId.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
				}
				else {
					bindArticleId = true;

					sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
				}

				sb.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					if (bindArticleId) {
						queryPos.add(articleId);
					}

					queryPos.add(status);

					list = (List<JournalArticle>)QueryUtil.list(
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_A_ST_First(
			long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_A_ST_First(
			groupId, articleId, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", articleId=");
		sb.append(articleId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchArticleException(sb.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_ST_First(
		long groupId, String articleId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		List<JournalArticle> list = findByG_A_ST(
			groupId, articleId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int status) {

		return filterFindByG_A_ST(
			groupId, articleId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int status, int start, int end) {

		return filterFindByG_A_ST(groupId, articleId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_ST(
				groupId, articleId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_A_ST(
					groupId, articleId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		articleId = Objects.toString(articleId, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
		}

		sb.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindArticleId) {
				queryPos.add(articleId);
			}

			queryPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int[] statuses) {

		return filterFindByG_A_ST(
			groupId, articleId, statuses, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int[] statuses, int start, int end) {

		return filterFindByG_A_ST(
			groupId, articleId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_ST(
				groupId, articleId, statuses, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_A_ST(
					groupId, articleId, statuses, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		articleId = Objects.toString(articleId, "");

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
		}

		if (statuses.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_A_ST_STATUS_7);

			sb.append(StringUtil.merge(statuses));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindArticleId) {
				queryPos.add(articleId);
			}

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int[] statuses) {

		return findByG_A_ST(
			groupId, articleId, statuses, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int[] statuses, int start, int end) {

		return findByG_A_ST(groupId, articleId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_A_ST(
			groupId, articleId, statuses, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		articleId = Objects.toString(articleId, "");

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		if (statuses.length == 1) {
			return findByG_A_ST(
				groupId, articleId, statuses[0], start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						groupId, articleId, StringUtil.merge(statuses)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, articleId, StringUtil.merge(statuses), start, end,
					orderByComparator
				};
			}

			List<JournalArticle> list = null;

			if (useFinderCache) {
				list = (List<JournalArticle>)finderCache.getResult(
					_finderPathWithPaginationFindByG_A_ST, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (JournalArticle journalArticle : list) {
						if ((groupId != journalArticle.getGroupId()) ||
							!articleId.equals(journalArticle.getArticleId()) ||
							!ArrayUtil.contains(
								statuses, journalArticle.getStatus())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

				boolean bindArticleId = false;

				if (articleId.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
				}
				else {
					bindArticleId = true;

					sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
				}

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_A_ST_STATUS_7);

					sb.append(StringUtil.merge(statuses));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
				}
				else {
					sb.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					if (bindArticleId) {
						queryPos.add(articleId);
					}

					list = (List<JournalArticle>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_A_ST, finderArgs,
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
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 */
	@Override
	public void removeByG_A_ST(long groupId, String articleId, int status) {
		for (JournalArticle journalArticle :
				findByG_A_ST(
					groupId, articleId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_A_ST(long groupId, String articleId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			articleId = Objects.toString(articleId, "");

			FinderPath finderPath = _finderPathCountByG_A_ST;

			Object[] finderArgs = new Object[] {groupId, articleId, status};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

				boolean bindArticleId = false;

				if (articleId.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
				}
				else {
					bindArticleId = true;

					sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
				}

				sb.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					if (bindArticleId) {
						queryPos.add(articleId);
					}

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
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_A_ST(long groupId, String articleId, int[] statuses) {
		articleId = Objects.toString(articleId, "");

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			Object[] finderArgs = new Object[] {
				groupId, articleId, StringUtil.merge(statuses)
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_A_ST, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				sb.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

				boolean bindArticleId = false;

				if (articleId.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
				}
				else {
					bindArticleId = true;

					sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
				}

				if (statuses.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_A_ST_STATUS_7);

					sb.append(StringUtil.merge(statuses));

					sb.append(")");

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					if (bindArticleId) {
						queryPos.add(articleId);
					}

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_A_ST, finderArgs,
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
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_ST(long groupId, String articleId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A_ST(groupId, articleId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_A_ST(
				groupId, articleId, status);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		articleId = Objects.toString(articleId, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
		}

		sb.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindArticleId) {
				queryPos.add(articleId);
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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_ST(
		long groupId, String articleId, int[] statuses) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A_ST(groupId, articleId, statuses);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = InlineSQLHelperUtil.filter(
				findByG_A_ST(groupId, articleId, statuses), groupId);

			return journalArticles.size();
		}

		articleId = Objects.toString(articleId, "");

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			sb.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
		}

		if (statuses.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_A_ST_STATUS_7);

			sb.append(StringUtil.merge(statuses));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindArticleId) {
				queryPos.add(articleId);
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

	private static final String _FINDER_COLUMN_G_A_ST_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_ST_ARTICLEID_2 =
		"journalArticle.articleId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_ST_ARTICLEID_3 =
		"(journalArticle.articleId IS NULL OR journalArticle.articleId = '') AND ";

	private static final String _FINDER_COLUMN_G_A_ST_STATUS_2 =
		"journalArticle.status = ?";

	private static final String _FINDER_COLUMN_G_A_ST_STATUS_7 =
		"journalArticle.status IN (";

	private FinderPath _finderPathWithPaginationFindByG_A_NotST;
	private FinderPath _finderPathWithPaginationCountByG_A_NotST;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_A_NotST;

	/**
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_NotST(
		long groupId, String articleId, int status) {

		return findByG_A_NotST(
			groupId, articleId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_NotST(
		long groupId, String articleId, int status, int start, int end) {

		return findByG_A_NotST(groupId, articleId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_NotST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_A_NotST(
			groupId, articleId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_NotST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_A_NotST.find(
				finderCache, new Object[] {groupId, articleId, status}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_A_NotST_First(
			long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_A_NotST_First(
			groupId, articleId, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_A_NotST.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, articleId, status}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_NotST_First(
		long groupId, String articleId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_A_NotST.fetchFirst(
			finderCache, new Object[] {groupId, articleId, status},
			orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_NotST(
		long groupId, String articleId, int status) {

		return filterFindByG_A_NotST(
			groupId, articleId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_NotST(
		long groupId, String articleId, int status, int start, int end) {

		return filterFindByG_A_NotST(
			groupId, articleId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_NotST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_NotST(
				groupId, articleId, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_A_NotST(
					groupId, articleId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		articleId = Objects.toString(articleId, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_NOTST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			sb.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_2);
		}

		sb.append(_FINDER_COLUMN_G_A_NOTST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindArticleId) {
				queryPos.add(articleId);
			}

			queryPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 */
	@Override
	public void removeByG_A_NotST(long groupId, String articleId, int status) {
		_collectionPersistenceFinderByG_A_NotST.remove(
			finderCache, new Object[] {groupId, articleId, status});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_A_NotST(long groupId, String articleId, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_A_NotST.count(
				finderCache, new Object[] {groupId, articleId, status});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_NotST(
		long groupId, String articleId, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A_NotST(groupId, articleId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_A_NotST(
				groupId, articleId, status);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		articleId = Objects.toString(articleId, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_A_NOTST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			sb.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_2);
		}

		sb.append(_FINDER_COLUMN_G_A_NOTST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindArticleId) {
				queryPos.add(articleId);
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

	private static final String _FINDER_COLUMN_G_A_NOTST_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_NOTST_ARTICLEID_2 =
		"journalArticle.articleId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_NOTST_ARTICLEID_3 =
		"(journalArticle.articleId IS NULL OR journalArticle.articleId = '') AND ";

	private static final String _FINDER_COLUMN_G_A_NOTST_STATUS_2 =
		"journalArticle.status != ?";

	private FinderPath _finderPathWithPaginationFindByG_UT_ST;
	private FinderPath _finderPathWithoutPaginationFindByG_UT_ST;
	private FinderPath _finderPathCountByG_UT_ST;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_UT_ST;

	/**
	 * Returns all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT_ST(
		long groupId, String urlTitle, int status) {

		return findByG_UT_ST(
			groupId, urlTitle, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT_ST(
		long groupId, String urlTitle, int status, int start, int end) {

		return findByG_UT_ST(groupId, urlTitle, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT_ST(
		long groupId, String urlTitle, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_UT_ST(
			groupId, urlTitle, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT_ST(
		long groupId, String urlTitle, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_UT_ST.find(
				finderCache, new Object[] {groupId, urlTitle, status}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_UT_ST_First(
			long groupId, String urlTitle, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_UT_ST_First(
			groupId, urlTitle, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_UT_ST.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, urlTitle, status}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_UT_ST_First(
		long groupId, String urlTitle, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_UT_ST.fetchFirst(
			finderCache, new Object[] {groupId, urlTitle, status},
			orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT_ST(
		long groupId, String urlTitle, int status) {

		return filterFindByG_UT_ST(
			groupId, urlTitle, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT_ST(
		long groupId, String urlTitle, int status, int start, int end) {

		return filterFindByG_UT_ST(groupId, urlTitle, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT_ST(
		long groupId, String urlTitle, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_UT_ST(
				groupId, urlTitle, status, start, end, orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_UT_ST(
					groupId, urlTitle, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		urlTitle = Objects.toString(urlTitle, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			sb.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
		}

		sb.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindUrlTitle) {
				queryPos.add(urlTitle);
			}

			queryPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 */
	@Override
	public void removeByG_UT_ST(long groupId, String urlTitle, int status) {
		_collectionPersistenceFinderByG_UT_ST.remove(
			finderCache, new Object[] {groupId, urlTitle, status});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_UT_ST(long groupId, String urlTitle, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_UT_ST.count(
				finderCache, new Object[] {groupId, urlTitle, status});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_UT_ST(long groupId, String urlTitle, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_UT_ST(groupId, urlTitle, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_UT_ST(
				groupId, urlTitle, status);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		urlTitle = Objects.toString(urlTitle, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			sb.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
		}

		sb.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindUrlTitle) {
				queryPos.add(urlTitle);
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

	private static final String _FINDER_COLUMN_G_UT_ST_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_UT_ST_URLTITLE_2 =
		"journalArticle.urlTitle = ? AND ";

	private static final String _FINDER_COLUMN_G_UT_ST_URLTITLE_3 =
		"(journalArticle.urlTitle IS NULL OR journalArticle.urlTitle = '') AND ";

	private static final String _FINDER_COLUMN_G_UT_ST_STATUS_2 =
		"journalArticle.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_V_ST;
	private FinderPath _finderPathWithoutPaginationFindByC_V_ST;
	private FinderPath _finderPathCountByC_V_ST;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByC_V_ST;

	/**
	 * Returns all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V_ST(
		long companyId, double version, int status) {

		return findByC_V_ST(
			companyId, version, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V_ST(
		long companyId, double version, int status, int start, int end) {

		return findByC_V_ST(companyId, version, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V_ST(
		long companyId, double version, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return findByC_V_ST(
			companyId, version, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V_ST(
		long companyId, double version, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByC_V_ST.find(
				finderCache, new Object[] {companyId, version, status}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_V_ST_First(
			long companyId, double version, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByC_V_ST_First(
			companyId, version, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByC_V_ST.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, version, status}));
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_V_ST_First(
		long companyId, double version, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByC_V_ST.fetchFirst(
			finderCache, new Object[] {companyId, version, status},
			orderByComparator);
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and version = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 */
	@Override
	public void removeByC_V_ST(long companyId, double version, int status) {
		_collectionPersistenceFinderByC_V_ST.remove(
			finderCache, new Object[] {companyId, version, status});
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByC_V_ST(long companyId, double version, int status) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByC_V_ST.count(
				finderCache, new Object[] {companyId, version, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_F_C_NotST;
	private FinderPath _finderPathWithPaginationCountByG_F_C_NotST;
	private CollectionPersistenceFinder<JournalArticle>
		_collectionPersistenceFinderByG_F_C_NotST;

	/**
	 * Returns all the journal articles where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_C_NotST(
		long groupId, long folderId, long classNameId, int status) {

		return findByG_F_C_NotST(
			groupId, folderId, classNameId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_C_NotST(
		long groupId, long folderId, long classNameId, int status, int start,
		int end) {

		return findByG_F_C_NotST(
			groupId, folderId, classNameId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_C_NotST(
		long groupId, long folderId, long classNameId, int status, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		return findByG_F_C_NotST(
			groupId, folderId, classNameId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_C_NotST(
		long groupId, long folderId, long classNameId, int status, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_F_C_NotST.find(
				finderCache,
				new Object[] {groupId, folderId, classNameId, status}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_F_C_NotST_First(
			long groupId, long folderId, long classNameId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {

		JournalArticle journalArticle = fetchByG_F_C_NotST_First(
			groupId, folderId, classNameId, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		throw new NoSuchArticleException(
			_collectionPersistenceFinderByG_F_C_NotST.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, folderId, classNameId, status}));
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_F_C_NotST_First(
		long groupId, long folderId, long classNameId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return _collectionPersistenceFinderByG_F_C_NotST.fetchFirst(
			finderCache, new Object[] {groupId, folderId, classNameId, status},
			orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_C_NotST(
		long groupId, long folderId, long classNameId, int status) {

		return filterFindByG_F_C_NotST(
			groupId, folderId, classNameId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_C_NotST(
		long groupId, long folderId, long classNameId, int status, int start,
		int end) {

		return filterFindByG_F_C_NotST(
			groupId, folderId, classNameId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_C_NotST(
		long groupId, long folderId, long classNameId, int status, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_C_NotST(
				groupId, folderId, classNameId, status, start, end,
				orderByComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			isPermissionsInMemoryFilterEnabled()) {

			return InlineSQLHelperUtil.filter(
				findByG_F_C_NotST(
					groupId, folderId, classNameId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator),
				groupId);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_F_C_NOTST_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_C_NOTST_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_F_C_NOTST_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_F_C_NOTST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL_INLINE_DISTINCT);
			}
			else {
				sb.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			queryPos.add(classNameId);

			queryPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(
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
	 * Removes all the journal articles where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 */
	@Override
	public void removeByG_F_C_NotST(
		long groupId, long folderId, long classNameId, int status) {

		_collectionPersistenceFinderByG_F_C_NotST.remove(
			finderCache, new Object[] {groupId, folderId, classNameId, status});
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_F_C_NotST(
		long groupId, long folderId, long classNameId, int status) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalArticle.class)) {

			return _collectionPersistenceFinderByG_F_C_NotST.count(
				finderCache,
				new Object[] {groupId, folderId, classNameId, status});
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and classNameId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_C_NotST(
		long groupId, long folderId, long classNameId, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_C_NotST(groupId, folderId, classNameId, status);
		}

		if (isPermissionsInMemoryFilterEnabled()) {
			List<JournalArticle> journalArticles = findByG_F_C_NotST(
				groupId, folderId, classNameId, status);

			journalArticles = InlineSQLHelperUtil.filter(
				journalArticles, groupId);

			return journalArticles.size();
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		sb.append(_FINDER_COLUMN_G_F_C_NOTST_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_C_NOTST_FOLDERID_2);

		sb.append(_FINDER_COLUMN_G_F_C_NOTST_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_F_C_NOTST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalArticle.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(folderId);

			queryPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_F_C_NOTST_GROUPID_2 =
		"journalArticle.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_C_NOTST_FOLDERID_2 =
		"journalArticle.folderId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_C_NOTST_CLASSNAMEID_2 =
		"journalArticle.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_C_NOTST_STATUS_2 =
		"journalArticle.status != ?";

	public JournalArticlePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("id", "id_");

		setDBColumnNames(dbColumnNames);

		setModelClass(JournalArticle.class);

		setModelImplClass(JournalArticleImpl.class);
		setModelPKClass(long.class);

		setTable(JournalArticleTable.INSTANCE);
	}

	/**
	 * Creates a new journal article with the primary key. Does not add the journal article to the database.
	 *
	 * @param id the primary key for the new journal article
	 * @return the new journal article
	 */
	@Override
	public JournalArticle create(long id) {
		JournalArticle journalArticle = new JournalArticleImpl();

		journalArticle.setNew(true);
		journalArticle.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalArticle.setUuid(uuid);

		journalArticle.setCompanyId(CompanyThreadLocal.getCompanyId());

		return journalArticle;
	}

	/**
	 * Removes the journal article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the journal article
	 * @return the journal article that was removed
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle remove(long id) throws NoSuchArticleException {
		return remove((Serializable)id);
	}

	@Override
	protected JournalArticle removeImpl(JournalArticle journalArticle) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(journalArticle)) {
				journalArticle = (JournalArticle)session.get(
					JournalArticleImpl.class,
					journalArticle.getPrimaryKeyObj());
			}

			if ((journalArticle != null) &&
				ctPersistenceHelper.isRemove(journalArticle)) {

				session.delete(journalArticle);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (journalArticle != null) {
			clearCache(journalArticle);
		}

		return journalArticle;
	}

	@Override
	public JournalArticle updateImpl(JournalArticle journalArticle) {
		boolean isNew = journalArticle.isNew();

		if (!(journalArticle instanceof JournalArticleModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(journalArticle.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					journalArticle);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in journalArticle proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom JournalArticle implementation " +
					journalArticle.getClass());
		}

		JournalArticleModelImpl journalArticleModelImpl =
			(JournalArticleModelImpl)journalArticle;

		if (Validator.isNull(journalArticle.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalArticle.setUuid(uuid);
		}

		if (Validator.isNull(journalArticle.getExternalReferenceCode())) {
			journalArticle.setExternalReferenceCode(journalArticle.getUuid());
		}
		else {
			if (!Objects.equals(
					journalArticleModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					journalArticle.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = journalArticle.getCompanyId();

					long groupId = journalArticle.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = journalArticle.getPrimaryKey();
					}

					try {
						journalArticle.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								JournalArticle.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								journalArticle.getExternalReferenceCode(),
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

		if (isNew && (journalArticle.getCreateDate() == null)) {
			if (serviceContext == null) {
				journalArticle.setCreateDate(date);
			}
			else {
				journalArticle.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!journalArticleModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				journalArticle.setModifiedDate(date);
			}
			else {
				journalArticle.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(journalArticle)) {
				if (!isNew) {
					session.evict(
						JournalArticleImpl.class,
						journalArticle.getPrimaryKeyObj());
				}

				session.save(journalArticle);
			}
			else {
				journalArticle = (JournalArticle)session.merge(journalArticle);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(journalArticle, false);

		if (isNew) {
			journalArticle.setNew(false);
		}

		journalArticle.resetOriginalValues();

		return journalArticle;
	}

	/**
	 * Returns the journal article with the primary key or throws a <code>NoSuchArticleException</code> if it could not be found.
	 *
	 * @param id the primary key of the journal article
	 * @return the journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle findByPrimaryKey(long id)
		throws NoSuchArticleException {

		return findByPrimaryKey((Serializable)id);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the journal article with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the journal article
	 * @return the journal article, or <code>null</code> if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle fetchByPrimaryKey(long id) {
		return fetchByPrimaryKey((Serializable)id);
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
		return "id_";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_JOURNALARTICLE;
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
		return JournalArticleModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "JournalArticle";
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
		ctMergeColumnNames.add("resourcePrimKey");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("externalReferenceCode");
		ctMergeColumnNames.add("folderId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctMergeColumnNames.add("treePath");
		ctMergeColumnNames.add("articleId");
		ctMergeColumnNames.add("version");
		ctMergeColumnNames.add("urlTitle");
		ctMergeColumnNames.add("DDMStructureId");
		ctMergeColumnNames.add("DDMTemplateKey");
		ctMergeColumnNames.add("defaultLanguageId");
		ctMergeColumnNames.add("layoutUuid");
		ctMergeColumnNames.add("displayDate");
		ctMergeColumnNames.add("expirationDate");
		ctMergeColumnNames.add("reviewDate");
		ctMergeColumnNames.add("indexable");
		ctMergeColumnNames.add("smallImage");
		ctMergeColumnNames.add("smallImageId");
		ctMergeColumnNames.add("smallImageSource");
		ctMergeColumnNames.add("smallImageURL");
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
			CTColumnResolutionType.PK, Collections.singleton("id_"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "externalReferenceCode", "version"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "articleId", "version"});
	}

	/**
	 * Initializes the journal article persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindByResourcePrimKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByResourcePrimKey",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"resourcePrimKey"}, true);

		_finderPathWithoutPaginationFindByResourcePrimKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByResourcePrimKey",
			new String[] {Long.class.getName()},
			new String[] {"resourcePrimKey"}, true);

		_finderPathCountByResourcePrimKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByResourcePrimKey",
			new String[] {Long.class.getName()},
			new String[] {"resourcePrimKey"}, false);

		_collectionPersistenceFinderByResourcePrimKey =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByResourcePrimKey,
				_finderPathWithoutPaginationFindByResourcePrimKey,
				_finderPathCountByResourcePrimKey,
				_SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "resourcePrimKey",
					FinderColumn.Type.LONG, "=", true, true,
					JournalArticle::getResourcePrimKey));

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
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, JournalArticle::getUuid));

		_finderPathFetchByUUID_G = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false,
			convertNullFunction(JournalArticle::getUuid),
			JournalArticle::getGroupId);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_JOURNALARTICLE_WHERE,
			"",
			new FinderColumn<>(
				"journalArticle.", "uuid", FinderColumn.Type.STRING, "=", true,
				true, JournalArticle::getUuid),
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "uuid", FinderColumn.Type.STRING, "=",
					true, true, JournalArticle::getUuid),
				new FinderColumn<>(
					"journalArticle.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getCompanyId));

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
				_finderPathCountByGroupId, _SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getGroupId));

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
				_finderPathCountByCompanyId, _SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getCompanyId));

		_finderPathWithPaginationFindByDDMStructureId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDDMStructureId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"DDMStructureId"}, true);

		_finderPathWithoutPaginationFindByDDMStructureId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDDMStructureId",
			new String[] {Long.class.getName()},
			new String[] {"DDMStructureId"}, true);

		_finderPathCountByDDMStructureId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDDMStructureId",
			new String[] {Long.class.getName()},
			new String[] {"DDMStructureId"}, false);

		_collectionPersistenceFinderByDDMStructureId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByDDMStructureId,
				_finderPathWithoutPaginationFindByDDMStructureId,
				_finderPathCountByDDMStructureId,
				_SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "DDMStructureId", FinderColumn.Type.LONG,
					"=", true, true, JournalArticle::getDDMStructureId));

		_finderPathWithPaginationFindByDDMTemplateKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDDMTemplateKey",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"DDMTemplateKey"}, true);

		_finderPathWithoutPaginationFindByDDMTemplateKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDDMTemplateKey",
			new String[] {String.class.getName()},
			new String[] {"DDMTemplateKey"}, true);

		_finderPathCountByDDMTemplateKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDDMTemplateKey",
			new String[] {String.class.getName()},
			new String[] {"DDMTemplateKey"}, false);

		_collectionPersistenceFinderByDDMTemplateKey =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByDDMTemplateKey,
				_finderPathWithoutPaginationFindByDDMTemplateKey,
				_finderPathCountByDDMTemplateKey,
				_SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "DDMTemplateKey",
					FinderColumn.Type.STRING, "=", true, true,
					JournalArticle::getDDMTemplateKey));

		_finderPathWithPaginationFindByLayoutUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLayoutUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"layoutUuid"}, true);

		_finderPathWithoutPaginationFindByLayoutUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLayoutUuid",
			new String[] {String.class.getName()}, new String[] {"layoutUuid"},
			true);

		_finderPathCountByLayoutUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLayoutUuid",
			new String[] {String.class.getName()}, new String[] {"layoutUuid"},
			false);

		_collectionPersistenceFinderByLayoutUuid =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByLayoutUuid,
				_finderPathWithoutPaginationFindByLayoutUuid,
				_finderPathCountByLayoutUuid, _SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "layoutUuid", FinderColumn.Type.STRING,
					"=", true, true, JournalArticle::getLayoutUuid));

		_finderPathWithPaginationFindBySmallImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySmallImageId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"smallImageId"}, true);

		_finderPathWithoutPaginationFindBySmallImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySmallImageId",
			new String[] {Long.class.getName()}, new String[] {"smallImageId"},
			true);

		_finderPathCountBySmallImageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySmallImageId",
			new String[] {Long.class.getName()}, new String[] {"smallImageId"},
			false);

		_collectionPersistenceFinderBySmallImageId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindBySmallImageId,
				_finderPathWithoutPaginationFindBySmallImageId,
				_finderPathCountBySmallImageId,
				_SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "smallImageId", FinderColumn.Type.LONG,
					"=", true, true, JournalArticle::getSmallImageId));

		_finderPathWithPaginationFindByR_I = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_I",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"resourcePrimKey", "indexable"}, true);

		_finderPathWithoutPaginationFindByR_I = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_I",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"resourcePrimKey", "indexable"}, true);

		_finderPathCountByR_I = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_I",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"resourcePrimKey", "indexable"}, false);

		_collectionPersistenceFinderByR_I = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByR_I,
			_finderPathWithoutPaginationFindByR_I, _finderPathCountByR_I,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "resourcePrimKey", FinderColumn.Type.LONG,
				"=", true, true, JournalArticle::getResourcePrimKey),
			new FinderColumn<>(
				"journalArticle.", "indexable", FinderColumn.Type.BOOLEAN, "=",
				true, true, JournalArticle::isIndexable));

		_finderPathWithPaginationFindByR_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_ST",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"resourcePrimKey", "status"}, true);

		_finderPathWithoutPaginationFindByR_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_ST",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"resourcePrimKey", "status"}, true);

		_finderPathCountByR_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_ST",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"resourcePrimKey", "status"}, false);

		_finderPathWithPaginationCountByR_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByR_ST",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"resourcePrimKey", "status"}, false);

		_finderPathWithPaginationFindByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId"}, true);

		_finderPathWithoutPaginationFindByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, true);

		_finderPathCountByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, false);

		_collectionPersistenceFinderByG_U = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_U,
			_finderPathWithoutPaginationFindByG_U, _finderPathCountByG_U,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "userId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getUserId));

		_finderPathWithPaginationFindByG_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_ERC",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "externalReferenceCode"}, true);

		_finderPathWithoutPaginationFindByG_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "externalReferenceCode"}, true);

		_finderPathCountByG_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "externalReferenceCode"}, false);

		_collectionPersistenceFinderByG_ERC = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_ERC,
			_finderPathWithoutPaginationFindByG_ERC, _finderPathCountByG_ERC,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, true,
				JournalArticle::getExternalReferenceCode));

		_finderPathWithPaginationFindByG_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "folderId"}, true);

		_finderPathWithoutPaginationFindByG_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "folderId"}, true);

		_finderPathCountByG_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "folderId"}, false);

		_finderPathWithPaginationCountByG_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "folderId"}, false);

		_finderPathWithPaginationFindByG_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "articleId"}, true);

		_finderPathWithoutPaginationFindByG_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "articleId"}, true);

		_finderPathCountByG_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "articleId"}, false);

		_collectionPersistenceFinderByG_A = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_A,
			_finderPathWithoutPaginationFindByG_A, _finderPathCountByG_A,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "articleId", FinderColumn.Type.STRING, "=",
				true, true, JournalArticle::getArticleId));

		_finderPathWithPaginationFindByG_UT = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_UT",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "urlTitle"}, true);

		_finderPathWithoutPaginationFindByG_UT = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_UT",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "urlTitle"}, true);

		_finderPathCountByG_UT = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_UT",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "urlTitle"}, false);

		_collectionPersistenceFinderByG_UT = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_UT,
			_finderPathWithoutPaginationFindByG_UT, _finderPathCountByG_UT,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "urlTitle", FinderColumn.Type.STRING, "=",
				true, true, JournalArticle::getUrlTitle));

		_finderPathWithPaginationFindByG_DDMSI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_DDMSI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "DDMStructureId"}, true);

		_finderPathWithoutPaginationFindByG_DDMSI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_DDMSI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "DDMStructureId"}, true);

		_finderPathCountByG_DDMSI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_DDMSI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "DDMStructureId"}, false);

		_collectionPersistenceFinderByG_DDMSI =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_DDMSI,
				_finderPathWithoutPaginationFindByG_DDMSI,
				_finderPathCountByG_DDMSI, _SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getGroupId),
				new FinderColumn<>(
					"journalArticle.", "DDMStructureId", FinderColumn.Type.LONG,
					"=", true, true, JournalArticle::getDDMStructureId));

		_finderPathWithPaginationFindByG_DDMTK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_DDMTK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "DDMTemplateKey"}, true);

		_finderPathWithoutPaginationFindByG_DDMTK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_DDMTK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "DDMTemplateKey"}, true);

		_finderPathCountByG_DDMTK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_DDMTK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "DDMTemplateKey"}, false);

		_collectionPersistenceFinderByG_DDMTK =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_DDMTK,
				_finderPathWithoutPaginationFindByG_DDMTK,
				_finderPathCountByG_DDMTK, _SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getGroupId),
				new FinderColumn<>(
					"journalArticle.", "DDMTemplateKey",
					FinderColumn.Type.STRING, "=", true, true,
					JournalArticle::getDDMTemplateKey));

		_finderPathWithPaginationFindByG_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "layoutUuid"}, true);

		_finderPathWithoutPaginationFindByG_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_L",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "layoutUuid"}, true);

		_finderPathCountByG_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_L",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "layoutUuid"}, false);

		_collectionPersistenceFinderByG_L = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_L,
			_finderPathWithoutPaginationFindByG_L, _finderPathCountByG_L,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "layoutUuid", FinderColumn.Type.STRING, "=",
				true, true, JournalArticle::getLayoutUuid));

		_finderPathWithPaginationFindByG_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_ST",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "status"}, true);

		_finderPathWithoutPaginationFindByG_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_ST",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, true);

		_finderPathCountByG_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_ST",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, false);

		_collectionPersistenceFinderByG_ST = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_ST,
			_finderPathWithoutPaginationFindByG_ST, _finderPathCountByG_ST,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, JournalArticle::getStatus));

		_finderPathWithPaginationFindByC_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_V",
			new String[] {
				Long.class.getName(), Double.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "version"}, true);

		_finderPathWithoutPaginationFindByC_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_V",
			new String[] {Long.class.getName(), Double.class.getName()},
			new String[] {"companyId", "version"}, true);

		_finderPathCountByC_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_V",
			new String[] {Long.class.getName(), Double.class.getName()},
			new String[] {"companyId", "version"}, false);

		_collectionPersistenceFinderByC_V = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_V,
			_finderPathWithoutPaginationFindByC_V, _finderPathCountByC_V,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "companyId", FinderColumn.Type.LONG, "=",
				true, true, JournalArticle::getCompanyId),
			new FinderColumn<>(
				"journalArticle.", "version", FinderColumn.Type.DOUBLE, "=",
				true, true, JournalArticle::getVersion));

		_finderPathWithPaginationFindByC_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_ST",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "status"}, true);

		_finderPathWithoutPaginationFindByC_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_ST",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, true);

		_finderPathCountByC_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ST",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, false);

		_collectionPersistenceFinderByC_ST = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_ST,
			_finderPathWithoutPaginationFindByC_ST, _finderPathCountByC_ST,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "companyId", FinderColumn.Type.LONG, "=",
				true, true, JournalArticle::getCompanyId),
			new FinderColumn<>(
				"journalArticle.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, JournalArticle::getStatus));

		_finderPathWithPaginationFindByC_NotST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_NotST",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "status"}, true);

		_finderPathWithPaginationCountByC_NotST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotST",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, false);

		_collectionPersistenceFinderByC_NotST =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_NotST, null,
				_finderPathWithPaginationCountByC_NotST,
				_SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getCompanyId),
				new FinderColumn<>(
					"journalArticle.", "status", FinderColumn.Type.INTEGER,
					"!=", true, true, JournalArticle::getStatus));

		_finderPathWithPaginationFindByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"displayDate", "status"}, true);

		_finderPathWithPaginationCountByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"displayDate", "status"}, false);

		_collectionPersistenceFinderByLtD_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByLtD_S, null,
			_finderPathWithPaginationCountByLtD_S,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "displayDate", FinderColumn.Type.DATE, "<",
				true, true, JournalArticle::getDisplayDate),
			new FinderColumn<>(
				"journalArticle.", "status", FinderColumn.Type.INTEGER, "=",
				true, true, JournalArticle::getStatus));

		_finderPathWithPaginationFindByR_I_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_I_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"resourcePrimKey", "indexable", "status"}, true);

		_finderPathWithoutPaginationFindByR_I_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_I_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			new String[] {"resourcePrimKey", "indexable", "status"}, true);

		_finderPathCountByR_I_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_I_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			new String[] {"resourcePrimKey", "indexable", "status"}, false);

		_finderPathWithPaginationCountByR_I_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByR_I_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			new String[] {"resourcePrimKey", "indexable", "status"}, false);

		_finderPathWithPaginationFindByG_U_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId", "classNameId"}, true);

		_finderPathWithoutPaginationFindByG_U_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "userId", "classNameId"}, true);

		_finderPathCountByG_U_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "userId", "classNameId"}, false);

		_collectionPersistenceFinderByG_U_C = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_U_C,
			_finderPathWithoutPaginationFindByG_U_C, _finderPathCountByG_U_C,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "userId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getUserId),
			new FinderColumn<>(
				"journalArticle.", "classNameId", FinderColumn.Type.LONG, "=",
				true, true, JournalArticle::getClassNameId));

		_finderPathFetchByG_ERC_V = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_ERC_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			},
			new String[] {"groupId", "externalReferenceCode", "version"}, false,
			JournalArticle::getGroupId,
			convertNullFunction(JournalArticle::getExternalReferenceCode),
			JournalArticle::getVersion);

		_uniquePersistenceFinderByG_ERC_V = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_ERC_V, _SQL_SELECT_JOURNALARTICLE_WHERE,
			"",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, true,
				JournalArticle::getExternalReferenceCode),
			new FinderColumn<>(
				"journalArticle.", "version", FinderColumn.Type.DOUBLE, "=",
				true, true, JournalArticle::getVersion));

		_finderPathWithPaginationFindByG_ERC_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_ERC_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "externalReferenceCode", "status"}, true);

		_finderPathWithoutPaginationFindByG_ERC_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_ERC_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "externalReferenceCode", "status"}, true);

		_finderPathCountByG_ERC_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_ERC_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "externalReferenceCode", "status"}, false);

		_finderPathWithPaginationCountByG_ERC_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_ERC_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "externalReferenceCode", "status"}, false);

		_finderPathWithPaginationFindByG_F_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "folderId", "status"}, true);

		_finderPathWithoutPaginationFindByG_F_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "folderId", "status"}, true);

		_finderPathCountByG_F_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "folderId", "status"}, false);

		_finderPathWithPaginationCountByG_F_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "folderId", "status"}, false);

		_finderPathWithPaginationFindByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_finderPathCountByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, false);

		_collectionPersistenceFinderByG_C_C = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_C_C,
			_finderPathWithoutPaginationFindByG_C_C, _finderPathCountByG_C_C,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "classNameId", FinderColumn.Type.LONG, "=",
				true, true, JournalArticle::getClassNameId),
			new FinderColumn<>(
				"journalArticle.", "classPK", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getClassPK));

		_finderPathFetchByG_C_DDMSI = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_DDMSI",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "DDMStructureId"}, false,
			JournalArticle::getGroupId, JournalArticle::getClassNameId,
			JournalArticle::getDDMStructureId);

		_uniquePersistenceFinderByG_C_DDMSI = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_C_DDMSI, _SQL_SELECT_JOURNALARTICLE_WHERE,
			"",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "classNameId", FinderColumn.Type.LONG, "=",
				true, true, JournalArticle::getClassNameId),
			new FinderColumn<>(
				"journalArticle.", "DDMStructureId", FinderColumn.Type.LONG,
				"=", true, true, JournalArticle::getDDMStructureId));

		_finderPathWithPaginationFindByG_C_DDMTK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_DDMTK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "DDMTemplateKey"}, true);

		_finderPathWithoutPaginationFindByG_C_DDMTK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_DDMTK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "classNameId", "DDMTemplateKey"}, true);

		_finderPathCountByG_C_DDMTK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_DDMTK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "classNameId", "DDMTemplateKey"}, false);

		_collectionPersistenceFinderByG_C_DDMTK =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_C_DDMTK,
				_finderPathWithoutPaginationFindByG_C_DDMTK,
				_finderPathCountByG_C_DDMTK, _SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getGroupId),
				new FinderColumn<>(
					"journalArticle.", "classNameId", FinderColumn.Type.LONG,
					"=", true, true, JournalArticle::getClassNameId),
				new FinderColumn<>(
					"journalArticle.", "DDMTemplateKey",
					FinderColumn.Type.STRING, "=", true, true,
					JournalArticle::getDDMTemplateKey));

		_finderPathWithPaginationFindByG_C_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "layoutUuid"}, true);

		_finderPathWithoutPaginationFindByG_C_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "classNameId", "layoutUuid"}, true);

		_finderPathCountByG_C_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "classNameId", "layoutUuid"}, false);

		_collectionPersistenceFinderByG_C_L = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_C_L,
			_finderPathWithoutPaginationFindByG_C_L, _finderPathCountByG_C_L,
			_SQL_SELECT_JOURNALARTICLE_WHERE, _SQL_COUNT_JOURNALARTICLE_WHERE,
			JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "classNameId", FinderColumn.Type.LONG, "=",
				true, true, JournalArticle::getClassNameId),
			new FinderColumn<>(
				"journalArticle.", "layoutUuid", FinderColumn.Type.STRING, "=",
				true, true, JournalArticle::getLayoutUuid));

		_finderPathWithPaginationFindByG_C_NotL = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_NotL",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "layoutUuid"}, true);

		_finderPathWithPaginationCountByG_C_NotL = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_NotL",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "classNameId", "layoutUuid"}, false);

		_finderPathFetchByG_A_V = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			},
			new String[] {"groupId", "articleId", "version"}, false,
			JournalArticle::getGroupId,
			convertNullFunction(JournalArticle::getArticleId),
			JournalArticle::getVersion);

		_uniquePersistenceFinderByG_A_V = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_A_V, _SQL_SELECT_JOURNALARTICLE_WHERE, "",
			new FinderColumn<>(
				"journalArticle.", "groupId", FinderColumn.Type.LONG, "=", true,
				true, JournalArticle::getGroupId),
			new FinderColumn<>(
				"journalArticle.", "articleId", FinderColumn.Type.STRING, "=",
				true, true, JournalArticle::getArticleId),
			new FinderColumn<>(
				"journalArticle.", "version", FinderColumn.Type.DOUBLE, "=",
				true, true, JournalArticle::getVersion));

		_finderPathWithPaginationFindByG_A_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "articleId", "status"}, true);

		_finderPathWithoutPaginationFindByG_A_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "articleId", "status"}, true);

		_finderPathCountByG_A_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "articleId", "status"}, false);

		_finderPathWithPaginationCountByG_A_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_A_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "articleId", "status"}, false);

		_finderPathWithPaginationFindByG_A_NotST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A_NotST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "articleId", "status"}, true);

		_finderPathWithPaginationCountByG_A_NotST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_A_NotST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "articleId", "status"}, false);

		_collectionPersistenceFinderByG_A_NotST =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_A_NotST, null,
				_finderPathWithPaginationCountByG_A_NotST,
				_SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getGroupId),
				new FinderColumn<>(
					"journalArticle.", "articleId", FinderColumn.Type.STRING,
					"=", true, true, JournalArticle::getArticleId),
				new FinderColumn<>(
					"journalArticle.", "status", FinderColumn.Type.INTEGER,
					"!=", true, true, JournalArticle::getStatus));

		_finderPathWithPaginationFindByG_UT_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_UT_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "urlTitle", "status"}, true);

		_finderPathWithoutPaginationFindByG_UT_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_UT_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "urlTitle", "status"}, true);

		_finderPathCountByG_UT_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_UT_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "urlTitle", "status"}, false);

		_collectionPersistenceFinderByG_UT_ST =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_UT_ST,
				_finderPathWithoutPaginationFindByG_UT_ST,
				_finderPathCountByG_UT_ST, _SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getGroupId),
				new FinderColumn<>(
					"journalArticle.", "urlTitle", FinderColumn.Type.STRING,
					"=", true, true, JournalArticle::getUrlTitle),
				new FinderColumn<>(
					"journalArticle.", "status", FinderColumn.Type.INTEGER, "=",
					true, true, JournalArticle::getStatus));

		_finderPathWithPaginationFindByC_V_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_V_ST",
			new String[] {
				Long.class.getName(), Double.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "version", "status"}, true);

		_finderPathWithoutPaginationFindByC_V_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_V_ST",
			new String[] {
				Long.class.getName(), Double.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "version", "status"}, true);

		_finderPathCountByC_V_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_V_ST",
			new String[] {
				Long.class.getName(), Double.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "version", "status"}, false);

		_collectionPersistenceFinderByC_V_ST =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_V_ST,
				_finderPathWithoutPaginationFindByC_V_ST,
				_finderPathCountByC_V_ST, _SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "companyId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getCompanyId),
				new FinderColumn<>(
					"journalArticle.", "version", FinderColumn.Type.DOUBLE, "=",
					true, true, JournalArticle::getVersion),
				new FinderColumn<>(
					"journalArticle.", "status", FinderColumn.Type.INTEGER, "=",
					true, true, JournalArticle::getStatus));

		_finderPathWithPaginationFindByG_F_C_NotST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F_C_NotST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "folderId", "classNameId", "status"},
			true);

		_finderPathWithPaginationCountByG_F_C_NotST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F_C_NotST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "folderId", "classNameId", "status"},
			false);

		_collectionPersistenceFinderByG_F_C_NotST =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_F_C_NotST, null,
				_finderPathWithPaginationCountByG_F_C_NotST,
				_SQL_SELECT_JOURNALARTICLE_WHERE,
				_SQL_COUNT_JOURNALARTICLE_WHERE,
				JournalArticleModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX, "",
				new FinderColumn<>(
					"journalArticle.", "groupId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getGroupId),
				new FinderColumn<>(
					"journalArticle.", "folderId", FinderColumn.Type.LONG, "=",
					true, true, JournalArticle::getFolderId),
				new FinderColumn<>(
					"journalArticle.", "classNameId", FinderColumn.Type.LONG,
					"=", true, true, JournalArticle::getClassNameId),
				new FinderColumn<>(
					"journalArticle.", "status", FinderColumn.Type.INTEGER,
					"!=", true, true, JournalArticle::getStatus));

		JournalArticleUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		JournalArticleUtil.setPersistence(null);

		entityCache.removeCache(JournalArticleImpl.class.getName());
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

	private static final String _ENTITY_ALIAS_PREFIX =
		JournalArticleModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_JOURNALARTICLE =
		"SELECT journalArticle FROM JournalArticle journalArticle";

	private static final String _SQL_SELECT_JOURNALARTICLE_WHERE =
		"SELECT journalArticle FROM JournalArticle journalArticle WHERE ";

	private static final String _SQL_COUNT_JOURNALARTICLE_WHERE =
		"SELECT COUNT(journalArticle) FROM JournalArticle journalArticle WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"journalArticle.resourcePrimKey";

	private static final String _FILTER_SQL_SELECT_JOURNALARTICLE_WHERE =
		"SELECT DISTINCT {journalArticle.*} FROM JournalArticle journalArticle WHERE ";

	private static final String
		_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {JournalArticle.*} FROM (SELECT DISTINCT journalArticle.id_ FROM JournalArticle journalArticle WHERE ";

	private static final String
		_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN JournalArticle ON TEMP_TABLE.id_ = JournalArticle.id_";

	private static final String _FILTER_SQL_COUNT_JOURNALARTICLE_WHERE =
		"SELECT COUNT(DISTINCT journalArticle.id_) AS COUNT_VALUE FROM JournalArticle journalArticle WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "journalArticle";

	private static final String _FILTER_ENTITY_TABLE = "JournalArticle";

	private static final String _ORDER_BY_ENTITY_TABLE = "JournalArticle.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No JournalArticle exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticlePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "id"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-738585720