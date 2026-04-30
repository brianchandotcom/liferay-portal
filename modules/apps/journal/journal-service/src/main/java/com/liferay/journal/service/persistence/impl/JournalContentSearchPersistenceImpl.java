/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service.persistence.impl;

import com.liferay.journal.exception.NoSuchContentSearchException;
import com.liferay.journal.model.JournalContentSearch;
import com.liferay.journal.model.JournalContentSearchTable;
import com.liferay.journal.model.impl.JournalContentSearchImpl;
import com.liferay.journal.model.impl.JournalContentSearchModelImpl;
import com.liferay.journal.service.persistence.JournalContentSearchPersistence;
import com.liferay.journal.service.persistence.JournalContentSearchUtil;
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

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
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
 * The persistence implementation for the journal content search service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = JournalContentSearchPersistence.class)
public class JournalContentSearchPersistenceImpl
	extends BasePersistenceImpl
		<JournalContentSearch, NoSuchContentSearchException>
	implements JournalContentSearchPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>JournalContentSearchUtil</code> to access the journal content search persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		JournalContentSearchImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;
	private CollectionPersistenceFinder<JournalContentSearch>
		_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns all the journal content searches where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByCompanyId.find(
				finderCache, new Object[] {companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal content search in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByCompanyId_First(
			long companyId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		throw new NoSuchContentSearchException(
			_collectionPersistenceFinderByCompanyId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {companyId}));
	}

	/**
	 * Returns the first journal content search in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByCompanyId_First(
		long companyId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the journal content searches where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of journal content searches where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByCompanyId.count(
				finderCache, new Object[] {companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByPortletId;
	private FinderPath _finderPathWithoutPaginationFindByPortletId;
	private FinderPath _finderPathCountByPortletId;
	private CollectionPersistenceFinder<JournalContentSearch>
		_collectionPersistenceFinderByPortletId;

	/**
	 * Returns all the journal content searches where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByPortletId(String portletId) {
		return findByPortletId(
			portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByPortletId(
		String portletId, int start, int end) {

		return findByPortletId(portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByPortletId(
		String portletId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByPortletId(portletId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByPortletId(
		String portletId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByPortletId.find(
				finderCache, new Object[] {portletId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByPortletId_First(
			String portletId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByPortletId_First(
			portletId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		throw new NoSuchContentSearchException(
			_collectionPersistenceFinderByPortletId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {portletId}));
	}

	/**
	 * Returns the first journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByPortletId_First(
		String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return _collectionPersistenceFinderByPortletId.fetchFirst(
			finderCache, new Object[] {portletId}, orderByComparator);
	}

	/**
	 * Removes all the journal content searches where portletId = &#63; from the database.
	 *
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByPortletId(String portletId) {
		_collectionPersistenceFinderByPortletId.remove(
			finderCache, new Object[] {portletId});
	}

	/**
	 * Returns the number of journal content searches where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByPortletId(String portletId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByPortletId.count(
				finderCache, new Object[] {portletId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByArticleId;
	private FinderPath _finderPathWithoutPaginationFindByArticleId;
	private FinderPath _finderPathCountByArticleId;
	private CollectionPersistenceFinder<JournalContentSearch>
		_collectionPersistenceFinderByArticleId;

	/**
	 * Returns all the journal content searches where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByArticleId(String articleId) {
		return findByArticleId(
			articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByArticleId(
		String articleId, int start, int end) {

		return findByArticleId(articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByArticleId(
		String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByArticleId(articleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByArticleId(
		String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByArticleId.find(
				finderCache, new Object[] {articleId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByArticleId_First(
			String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByArticleId_First(
			articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		throw new NoSuchContentSearchException(
			_collectionPersistenceFinderByArticleId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {articleId}));
	}

	/**
	 * Returns the first journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByArticleId_First(
		String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return _collectionPersistenceFinderByArticleId.fetchFirst(
			finderCache, new Object[] {articleId}, orderByComparator);
	}

	/**
	 * Removes all the journal content searches where articleId = &#63; from the database.
	 *
	 * @param articleId the article ID
	 */
	@Override
	public void removeByArticleId(String articleId) {
		_collectionPersistenceFinderByArticleId.remove(
			finderCache, new Object[] {articleId});
	}

	/**
	 * Returns the number of journal content searches where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByArticleId(String articleId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByArticleId.count(
				finderCache, new Object[] {articleId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P;
	private FinderPath _finderPathCountByG_P;
	private CollectionPersistenceFinder<JournalContentSearch>
		_collectionPersistenceFinderByG_P;

	/**
	 * Returns all the journal content searches where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout) {

		return findByG_P(
			groupId, privateLayout, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end) {

		return findByG_P(groupId, privateLayout, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByG_P(
			groupId, privateLayout, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByG_P.find(
				finderCache, new Object[] {groupId, privateLayout}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_First(
			long groupId, boolean privateLayout,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_First(
			groupId, privateLayout, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		throw new NoSuchContentSearchException(
			_collectionPersistenceFinderByG_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, privateLayout}));
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_First(
		long groupId, boolean privateLayout,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return _collectionPersistenceFinderByG_P.fetchFirst(
			finderCache, new Object[] {groupId, privateLayout},
			orderByComparator);
	}

	/**
	 * Removes all the journal content searches where groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 */
	@Override
	public void removeByG_P(long groupId, boolean privateLayout) {
		_collectionPersistenceFinderByG_P.remove(
			finderCache, new Object[] {groupId, privateLayout});
	}

	/**
	 * Returns the number of journal content searches where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_P(long groupId, boolean privateLayout) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByG_P.count(
				finderCache, new Object[] {groupId, privateLayout});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_A;
	private FinderPath _finderPathWithoutPaginationFindByG_A;
	private FinderPath _finderPathCountByG_A;
	private CollectionPersistenceFinder<JournalContentSearch>
		_collectionPersistenceFinderByG_A;

	/**
	 * Returns all the journal content searches where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_A(
		long groupId, String articleId) {

		return findByG_A(
			groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_A(
		long groupId, String articleId, int start, int end) {

		return findByG_A(groupId, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_A(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByG_A(
			groupId, articleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_A(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByG_A.find(
				finderCache, new Object[] {groupId, articleId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_A_First(
			long groupId, String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_A_First(
			groupId, articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		throw new NoSuchContentSearchException(
			_collectionPersistenceFinderByG_A.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, articleId}));
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_A_First(
		long groupId, String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return _collectionPersistenceFinderByG_A.fetchFirst(
			finderCache, new Object[] {groupId, articleId}, orderByComparator);
	}

	/**
	 * Removes all the journal content searches where groupId = &#63; and articleId = &#63; from the database.
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
	 * Returns the number of journal content searches where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_A(long groupId, String articleId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByG_A.count(
				finderCache, new Object[] {groupId, articleId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_P_L;
	private FinderPath _finderPathWithoutPaginationFindByG_P_L;
	private FinderPath _finderPathCountByG_P_L;
	private CollectionPersistenceFinder<JournalContentSearch>
		_collectionPersistenceFinderByG_P_L;

	/**
	 * Returns all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		return findByG_P_L(
			groupId, privateLayout, layoutId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start,
		int end) {

		return findByG_P_L(groupId, privateLayout, layoutId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByG_P_L(
			groupId, privateLayout, layoutId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByG_P_L.find(
				finderCache, new Object[] {groupId, privateLayout, layoutId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_L_First(
			long groupId, boolean privateLayout, long layoutId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_L_First(
			groupId, privateLayout, layoutId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		throw new NoSuchContentSearchException(
			_collectionPersistenceFinderByG_P_L.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, privateLayout, layoutId}));
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_L_First(
		long groupId, boolean privateLayout, long layoutId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return _collectionPersistenceFinderByG_P_L.fetchFirst(
			finderCache, new Object[] {groupId, privateLayout, layoutId},
			orderByComparator);
	}

	/**
	 * Removes all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 */
	@Override
	public void removeByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		_collectionPersistenceFinderByG_P_L.remove(
			finderCache, new Object[] {groupId, privateLayout, layoutId});
	}

	/**
	 * Returns the number of journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByG_P_L.count(
				finderCache, new Object[] {groupId, privateLayout, layoutId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_P_A;
	private FinderPath _finderPathWithoutPaginationFindByG_P_A;
	private FinderPath _finderPathCountByG_P_A;
	private CollectionPersistenceFinder<JournalContentSearch>
		_collectionPersistenceFinderByG_P_A;

	/**
	 * Returns all the journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, String articleId) {

		return findByG_P_A(
			groupId, privateLayout, articleId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, String articleId, int start,
		int end) {

		return findByG_P_A(groupId, privateLayout, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, String articleId, int start,
		int end, OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByG_P_A(
			groupId, privateLayout, articleId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, String articleId, int start,
		int end, OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByG_P_A.find(
				finderCache, new Object[] {groupId, privateLayout, articleId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_A_First(
			long groupId, boolean privateLayout, String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_A_First(
			groupId, privateLayout, articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		throw new NoSuchContentSearchException(
			_collectionPersistenceFinderByG_P_A.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, privateLayout, articleId}));
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_A_First(
		long groupId, boolean privateLayout, String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return _collectionPersistenceFinderByG_P_A.fetchFirst(
			finderCache, new Object[] {groupId, privateLayout, articleId},
			orderByComparator);
	}

	/**
	 * Removes all the journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 */
	@Override
	public void removeByG_P_A(
		long groupId, boolean privateLayout, String articleId) {

		_collectionPersistenceFinderByG_P_A.remove(
			finderCache, new Object[] {groupId, privateLayout, articleId});
	}

	/**
	 * Returns the number of journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_P_A(
		long groupId, boolean privateLayout, String articleId) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByG_P_A.count(
				finderCache, new Object[] {groupId, privateLayout, articleId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_P_L_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P_L_P;
	private FinderPath _finderPathCountByG_P_L_P;
	private CollectionPersistenceFinder<JournalContentSearch>
		_collectionPersistenceFinderByG_P_L_P;

	/**
	 * Returns all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId) {

		return findByG_P_L_P(
			groupId, privateLayout, layoutId, portletId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		int start, int end) {

		return findByG_P_L_P(
			groupId, privateLayout, layoutId, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByG_P_L_P(
			groupId, privateLayout, layoutId, portletId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByG_P_L_P.find(
				finderCache,
				new Object[] {groupId, privateLayout, layoutId, portletId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_L_P_First(
			long groupId, boolean privateLayout, long layoutId,
			String portletId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_L_P_First(
			groupId, privateLayout, layoutId, portletId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		throw new NoSuchContentSearchException(
			_collectionPersistenceFinderByG_P_L_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, privateLayout, layoutId, portletId}));
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_L_P_First(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return _collectionPersistenceFinderByG_P_L_P.fetchFirst(
			finderCache,
			new Object[] {groupId, privateLayout, layoutId, portletId},
			orderByComparator);
	}

	/**
	 * Removes all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId) {

		_collectionPersistenceFinderByG_P_L_P.remove(
			finderCache,
			new Object[] {groupId, privateLayout, layoutId, portletId});
	}

	/**
	 * Returns the number of journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _collectionPersistenceFinderByG_P_L_P.count(
				finderCache,
				new Object[] {groupId, privateLayout, layoutId, portletId});
		}
	}

	private FinderPath _finderPathFetchByG_P_L_P_A;
	private UniquePersistenceFinder<JournalContentSearch>
		_uniquePersistenceFinderByG_P_L_P_A;

	/**
	 * Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or throws a <code>NoSuchContentSearchException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_L_P_A(
			long groupId, boolean privateLayout, long layoutId,
			String portletId, String articleId)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_L_P_A(
			groupId, privateLayout, layoutId, portletId, articleId);

		if (journalContentSearch == null) {
			String message =
				_uniquePersistenceFinderByG_P_L_P_A.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						groupId, privateLayout, layoutId, portletId, articleId
					});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchContentSearchException(message);
		}

		return journalContentSearch;
	}

	/**
	 * Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		String articleId) {

		return fetchByG_P_L_P_A(
			groupId, privateLayout, layoutId, portletId, articleId, true);
	}

	/**
	 * Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		String articleId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			return _uniquePersistenceFinderByG_P_L_P_A.fetch(
				finderCache,
				new Object[] {
					groupId, privateLayout, layoutId, portletId, articleId
				},
				useFinderCache);
		}
	}

	/**
	 * Removes the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the journal content search that was removed
	 */
	@Override
	public JournalContentSearch removeByG_P_L_P_A(
			long groupId, boolean privateLayout, long layoutId,
			String portletId, String articleId)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = findByG_P_L_P_A(
			groupId, privateLayout, layoutId, portletId, articleId);

		return remove(journalContentSearch);
	}

	/**
	 * Returns the number of journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		String articleId) {

		return _uniquePersistenceFinderByG_P_L_P_A.count(
			finderCache,
			new Object[] {
				groupId, privateLayout, layoutId, portletId, articleId
			});
	}

	public JournalContentSearchPersistenceImpl() {
		setModelClass(JournalContentSearch.class);

		setModelImplClass(JournalContentSearchImpl.class);
		setModelPKClass(long.class);

		setTable(JournalContentSearchTable.INSTANCE);
	}

	/**
	 * Caches the journal content search in the entity cache if it is enabled.
	 *
	 * @param journalContentSearch the journal content search
	 */
	@Override
	public void cacheResult(JournalContentSearch journalContentSearch) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					journalContentSearch.getCtCollectionId())) {

			entityCache.putResult(
				JournalContentSearchImpl.class,
				journalContentSearch.getPrimaryKey(), journalContentSearch);

			finderCache.putResult(
				_finderPathFetchByG_P_L_P_A,
				new Object[] {
					journalContentSearch.getGroupId(),
					journalContentSearch.isPrivateLayout(),
					journalContentSearch.getLayoutId(),
					journalContentSearch.getPortletId(),
					journalContentSearch.getArticleId()
				},
				journalContentSearch);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the journal content searches in the entity cache if it is enabled.
	 *
	 * @param journalContentSearchs the journal content searches
	 */
	@Override
	public void cacheResult(List<JournalContentSearch> journalContentSearchs) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (journalContentSearchs.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (JournalContentSearch journalContentSearch :
				journalContentSearchs) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						journalContentSearch.getCtCollectionId())) {

				if (entityCache.getResult(
						JournalContentSearchImpl.class,
						journalContentSearch.getPrimaryKey()) == null) {

					cacheResult(journalContentSearch);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		JournalContentSearchModelImpl journalContentSearchModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					journalContentSearchModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				journalContentSearchModelImpl.getGroupId(),
				journalContentSearchModelImpl.isPrivateLayout(),
				journalContentSearchModelImpl.getLayoutId(),
				journalContentSearchModelImpl.getPortletId(),
				journalContentSearchModelImpl.getArticleId()
			};

			finderCache.putResult(
				_finderPathFetchByG_P_L_P_A, args,
				journalContentSearchModelImpl);
		}
	}

	/**
	 * Creates a new journal content search with the primary key. Does not add the journal content search to the database.
	 *
	 * @param contentSearchId the primary key for the new journal content search
	 * @return the new journal content search
	 */
	@Override
	public JournalContentSearch create(long contentSearchId) {
		JournalContentSearch journalContentSearch =
			new JournalContentSearchImpl();

		journalContentSearch.setNew(true);
		journalContentSearch.setPrimaryKey(contentSearchId);

		journalContentSearch.setCompanyId(CompanyThreadLocal.getCompanyId());

		return journalContentSearch;
	}

	/**
	 * Removes the journal content search with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentSearchId the primary key of the journal content search
	 * @return the journal content search that was removed
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch remove(long contentSearchId)
		throws NoSuchContentSearchException {

		return remove((Serializable)contentSearchId);
	}

	@Override
	protected JournalContentSearch removeImpl(
		JournalContentSearch journalContentSearch) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(journalContentSearch)) {
				journalContentSearch = (JournalContentSearch)session.get(
					JournalContentSearchImpl.class,
					journalContentSearch.getPrimaryKeyObj());
			}

			if ((journalContentSearch != null) &&
				ctPersistenceHelper.isRemove(journalContentSearch)) {

				session.delete(journalContentSearch);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (journalContentSearch != null) {
			clearCache(journalContentSearch);
		}

		return journalContentSearch;
	}

	@Override
	public JournalContentSearch updateImpl(
		JournalContentSearch journalContentSearch) {

		boolean isNew = journalContentSearch.isNew();

		if (!(journalContentSearch instanceof JournalContentSearchModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(journalContentSearch.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					journalContentSearch);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in journalContentSearch proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom JournalContentSearch implementation " +
					journalContentSearch.getClass());
		}

		JournalContentSearchModelImpl journalContentSearchModelImpl =
			(JournalContentSearchModelImpl)journalContentSearch;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(journalContentSearch)) {
				if (!isNew) {
					session.evict(
						JournalContentSearchImpl.class,
						journalContentSearch.getPrimaryKeyObj());
				}

				session.save(journalContentSearch);
			}
			else {
				journalContentSearch = (JournalContentSearch)session.merge(
					journalContentSearch);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			JournalContentSearchImpl.class, journalContentSearchModelImpl,
			false, true);

		cacheUniqueFindersCache(journalContentSearchModelImpl);

		if (isNew) {
			journalContentSearch.setNew(false);
		}

		journalContentSearch.resetOriginalValues();

		return journalContentSearch;
	}

	/**
	 * Returns the journal content search with the primary key or throws a <code>NoSuchContentSearchException</code> if it could not be found.
	 *
	 * @param contentSearchId the primary key of the journal content search
	 * @return the journal content search
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch findByPrimaryKey(long contentSearchId)
		throws NoSuchContentSearchException {

		return findByPrimaryKey((Serializable)contentSearchId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the journal content search with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contentSearchId the primary key of the journal content search
	 * @return the journal content search, or <code>null</code> if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch fetchByPrimaryKey(long contentSearchId) {
		return fetchByPrimaryKey((Serializable)contentSearchId);
	}

	/**
	 * Returns all the journal content searches.
	 *
	 * @return the journal content searches
	 */
	@Override
	public List<JournalContentSearch> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of journal content searches
	 */
	@Override
	public List<JournalContentSearch> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal content searches
	 */
	@Override
	public List<JournalContentSearch> findAll(
		int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of journal content searches
	 */
	@Override
	public List<JournalContentSearch> findAll(
		int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

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

			List<JournalContentSearch> list = null;

			if (useFinderCache) {
				list = (List<JournalContentSearch>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_JOURNALCONTENTSEARCH;

					sql = sql.concat(
						JournalContentSearchModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<JournalContentSearch>)QueryUtil.list(
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
	 * Removes all the journal content searches from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (JournalContentSearch journalContentSearch : findAll()) {
			remove(journalContentSearch);
		}
	}

	/**
	 * Returns the number of journal content searches.
	 *
	 * @return the number of journal content searches
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					JournalContentSearch.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_JOURNALCONTENTSEARCH);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "contentSearchId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_JOURNALCONTENTSEARCH;
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
		return JournalContentSearchModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "JournalContentSearch";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctMergeColumnNames.add("privateLayout");
		ctMergeColumnNames.add("layoutId");
		ctMergeColumnNames.add("portletId");
		ctMergeColumnNames.add("articleId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("contentSearchId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {
				"groupId", "privateLayout", "layoutId", "portletId", "articleId"
			});
	}

	/**
	 * Initializes the journal content search persistence.
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
				_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE,
				_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE,
				JournalContentSearchModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalContentSearch.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					JournalContentSearch::getCompanyId));

		_finderPathWithPaginationFindByPortletId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPortletId",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"portletId"}, true);

		_finderPathWithoutPaginationFindByPortletId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPortletId",
			new String[] {String.class.getName()}, new String[] {"portletId"},
			true);

		_finderPathCountByPortletId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPortletId",
			new String[] {String.class.getName()}, new String[] {"portletId"},
			false);

		_collectionPersistenceFinderByPortletId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByPortletId,
				_finderPathWithoutPaginationFindByPortletId,
				_finderPathCountByPortletId,
				_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE,
				_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE,
				JournalContentSearchModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalContentSearch.", "portletId",
					FinderColumn.Type.STRING, "=", true, true,
					JournalContentSearch::getPortletId));

		_finderPathWithPaginationFindByArticleId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByArticleId",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"articleId"}, true);

		_finderPathWithoutPaginationFindByArticleId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByArticleId",
			new String[] {String.class.getName()}, new String[] {"articleId"},
			true);

		_finderPathCountByArticleId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByArticleId",
			new String[] {String.class.getName()}, new String[] {"articleId"},
			false);

		_collectionPersistenceFinderByArticleId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByArticleId,
				_finderPathWithoutPaginationFindByArticleId,
				_finderPathCountByArticleId,
				_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE,
				_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE,
				JournalContentSearchModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalContentSearch.", "articleId",
					FinderColumn.Type.STRING, "=", true, true,
					JournalContentSearch::getArticleId));

		_finderPathWithPaginationFindByG_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "privateLayout"}, true);

		_finderPathWithoutPaginationFindByG_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "privateLayout"}, true);

		_finderPathCountByG_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "privateLayout"}, false);

		_collectionPersistenceFinderByG_P = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_P,
			_finderPathWithoutPaginationFindByG_P, _finderPathCountByG_P,
			_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE,
			_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE,
			JournalContentSearchModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"journalContentSearch.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, JournalContentSearch::getGroupId),
			new FinderColumn<>(
				"journalContentSearch.", "privateLayout",
				FinderColumn.Type.BOOLEAN, "=", true, true,
				JournalContentSearch::isPrivateLayout));

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
			_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE,
			_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE,
			JournalContentSearchModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"journalContentSearch.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, JournalContentSearch::getGroupId),
			new FinderColumn<>(
				"journalContentSearch.", "articleId", FinderColumn.Type.STRING,
				"=", true, true, JournalContentSearch::getArticleId));

		_finderPathWithPaginationFindByG_P_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "privateLayout", "layoutId"}, true);

		_finderPathWithoutPaginationFindByG_P_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			new String[] {"groupId", "privateLayout", "layoutId"}, true);

		_finderPathCountByG_P_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			new String[] {"groupId", "privateLayout", "layoutId"}, false);

		_collectionPersistenceFinderByG_P_L = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_P_L,
			_finderPathWithoutPaginationFindByG_P_L, _finderPathCountByG_P_L,
			_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE,
			_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE,
			JournalContentSearchModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"journalContentSearch.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, JournalContentSearch::getGroupId),
			new FinderColumn<>(
				"journalContentSearch.", "privateLayout",
				FinderColumn.Type.BOOLEAN, "=", true, false,
				JournalContentSearch::isPrivateLayout),
			new FinderColumn<>(
				"journalContentSearch.", "layoutId", FinderColumn.Type.LONG,
				"=", true, true, JournalContentSearch::getLayoutId));

		_finderPathWithPaginationFindByG_P_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "privateLayout", "articleId"}, true);

		_finderPathWithoutPaginationFindByG_P_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "privateLayout", "articleId"}, true);

		_finderPathCountByG_P_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "privateLayout", "articleId"}, false);

		_collectionPersistenceFinderByG_P_A = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_P_A,
			_finderPathWithoutPaginationFindByG_P_A, _finderPathCountByG_P_A,
			_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE,
			_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE,
			JournalContentSearchModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"journalContentSearch.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, JournalContentSearch::getGroupId),
			new FinderColumn<>(
				"journalContentSearch.", "privateLayout",
				FinderColumn.Type.BOOLEAN, "=", true, false,
				JournalContentSearch::isPrivateLayout),
			new FinderColumn<>(
				"journalContentSearch.", "articleId", FinderColumn.Type.STRING,
				"=", true, true, JournalContentSearch::getArticleId));

		_finderPathWithPaginationFindByG_P_L_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "privateLayout", "layoutId", "portletId"},
			true);

		_finderPathWithoutPaginationFindByG_P_L_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {"groupId", "privateLayout", "layoutId", "portletId"},
			true);

		_finderPathCountByG_P_L_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {"groupId", "privateLayout", "layoutId", "portletId"},
			false);

		_collectionPersistenceFinderByG_P_L_P =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_P_L_P,
				_finderPathWithoutPaginationFindByG_P_L_P,
				_finderPathCountByG_P_L_P,
				_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE,
				_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE,
				JournalContentSearchModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"journalContentSearch.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, JournalContentSearch::getGroupId),
				new FinderColumn<>(
					"journalContentSearch.", "privateLayout",
					FinderColumn.Type.BOOLEAN, "=", true, false,
					JournalContentSearch::isPrivateLayout),
				new FinderColumn<>(
					"journalContentSearch.", "layoutId", FinderColumn.Type.LONG,
					"=", true, false, JournalContentSearch::getLayoutId),
				new FinderColumn<>(
					"journalContentSearch.", "portletId",
					FinderColumn.Type.STRING, "=", true, true,
					JournalContentSearch::getPortletId));

		_finderPathFetchByG_P_L_P_A = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_P_L_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"groupId", "privateLayout", "layoutId", "portletId", "articleId"
			},
			true);

		_uniquePersistenceFinderByG_P_L_P_A = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_P_L_P_A,
			_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE,
			new FinderColumn<>(
				"journalContentSearch.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, JournalContentSearch::getGroupId),
			new FinderColumn<>(
				"journalContentSearch.", "privateLayout",
				FinderColumn.Type.BOOLEAN, "=", true, false,
				JournalContentSearch::isPrivateLayout),
			new FinderColumn<>(
				"journalContentSearch.", "layoutId", FinderColumn.Type.LONG,
				"=", true, false, JournalContentSearch::getLayoutId),
			new FinderColumn<>(
				"journalContentSearch.", "portletId", FinderColumn.Type.STRING,
				"=", true, false, JournalContentSearch::getPortletId),
			new FinderColumn<>(
				"journalContentSearch.", "articleId", FinderColumn.Type.STRING,
				"=", true, true, JournalContentSearch::getArticleId));

		JournalContentSearchUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		JournalContentSearchUtil.setPersistence(null);

		entityCache.removeCache(JournalContentSearchImpl.class.getName());
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

	private static final String _SQL_SELECT_JOURNALCONTENTSEARCH =
		"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch";

	private static final String _SQL_SELECT_JOURNALCONTENTSEARCH_WHERE =
		"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ";

	private static final String _SQL_COUNT_JOURNALCONTENTSEARCH =
		"SELECT COUNT(journalContentSearch) FROM JournalContentSearch journalContentSearch";

	private static final String _SQL_COUNT_JOURNALCONTENTSEARCH_WHERE =
		"SELECT COUNT(journalContentSearch) FROM JournalContentSearch journalContentSearch WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"journalContentSearch.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No JournalContentSearch exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentSearchPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-348023772