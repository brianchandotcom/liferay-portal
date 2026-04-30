/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.store.service.persistence.impl;

import com.liferay.change.tracking.store.exception.NoSuchContentException;
import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.change.tracking.store.model.CTSContentTable;
import com.liferay.change.tracking.store.model.impl.CTSContentImpl;
import com.liferay.change.tracking.store.model.impl.CTSContentModelImpl;
import com.liferay.change.tracking.store.service.persistence.CTSContentPersistence;
import com.liferay.change.tracking.store.service.persistence.CTSContentUtil;
import com.liferay.change.tracking.store.service.persistence.impl.constants.CTSPersistenceConstants;
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
 * The persistence implementation for the cts content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @generated
 */
@Component(service = CTSContentPersistence.class)
public class CTSContentPersistenceImpl
	extends BasePersistenceImpl<CTSContent, NoSuchContentException>
	implements CTSContentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTSContentUtil</code> to access the cts content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTSContentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByR_P;
	private FinderPath _finderPathWithoutPaginationFindByR_P;
	private FinderPath _finderPathCountByR_P;
	private CollectionPersistenceFinder<CTSContent>
		_collectionPersistenceFinderByR_P;

	/**
	 * Returns all the cts contents where repositoryId = &#63; and path = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the matching cts contents
	 */
	@Override
	public List<CTSContent> findByR_P(long repositoryId, String path) {
		return findByR_P(
			repositoryId, path, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cts contents where repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByR_P(
		long repositoryId, String path, int start, int end) {

		return findByR_P(repositoryId, path, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cts contents where repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByR_P(
		long repositoryId, String path, int start, int end,
		OrderByComparator<CTSContent> orderByComparator) {

		return findByR_P(
			repositoryId, path, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cts contents where repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByR_P(
		long repositoryId, String path, int start, int end,
		OrderByComparator<CTSContent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTSContent.class)) {

			return _collectionPersistenceFinderByR_P.find(
				finderCache, new Object[] {repositoryId, path}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cts content in the ordered set where repositoryId = &#63; and path = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByR_P_First(
			long repositoryId, String path,
			OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByR_P_First(
			repositoryId, path, orderByComparator);

		if (ctsContent != null) {
			return ctsContent;
		}

		throw new NoSuchContentException(
			_collectionPersistenceFinderByR_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {repositoryId, path}));
	}

	/**
	 * Returns the first cts content in the ordered set where repositoryId = &#63; and path = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByR_P_First(
		long repositoryId, String path,
		OrderByComparator<CTSContent> orderByComparator) {

		return _collectionPersistenceFinderByR_P.fetchFirst(
			finderCache, new Object[] {repositoryId, path}, orderByComparator);
	}

	/**
	 * Removes all the cts contents where repositoryId = &#63; and path = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param path the path
	 */
	@Override
	public void removeByR_P(long repositoryId, String path) {
		_collectionPersistenceFinderByR_P.remove(
			finderCache, new Object[] {repositoryId, path});
	}

	/**
	 * Returns the number of cts contents where repositoryId = &#63; and path = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the number of matching cts contents
	 */
	@Override
	public int countByR_P(long repositoryId, String path) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTSContent.class)) {

			return _collectionPersistenceFinderByR_P.count(
				finderCache, new Object[] {repositoryId, path});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_R_S;
	private FinderPath _finderPathWithoutPaginationFindByC_R_S;
	private FinderPath _finderPathCountByC_R_S;
	private CollectionPersistenceFinder<CTSContent>
		_collectionPersistenceFinderByC_R_S;

	/**
	 * Returns all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @return the matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType) {

		return findByC_R_S(
			companyId, repositoryId, storeType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType, int start,
		int end) {

		return findByC_R_S(
			companyId, repositoryId, storeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType, int start, int end,
		OrderByComparator<CTSContent> orderByComparator) {

		return findByC_R_S(
			companyId, repositoryId, storeType, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType, int start, int end,
		OrderByComparator<CTSContent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTSContent.class)) {

			return _collectionPersistenceFinderByC_R_S.find(
				finderCache, new Object[] {companyId, repositoryId, storeType},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByC_R_S_First(
			long companyId, long repositoryId, String storeType,
			OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByC_R_S_First(
			companyId, repositoryId, storeType, orderByComparator);

		if (ctsContent != null) {
			return ctsContent;
		}

		throw new NoSuchContentException(
			_collectionPersistenceFinderByC_R_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, repositoryId, storeType}));
	}

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_S_First(
		long companyId, long repositoryId, String storeType,
		OrderByComparator<CTSContent> orderByComparator) {

		return _collectionPersistenceFinderByC_R_S.fetchFirst(
			finderCache, new Object[] {companyId, repositoryId, storeType},
			orderByComparator);
	}

	/**
	 * Removes all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 */
	@Override
	public void removeByC_R_S(
		long companyId, long repositoryId, String storeType) {

		_collectionPersistenceFinderByC_R_S.remove(
			finderCache, new Object[] {companyId, repositoryId, storeType});
	}

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	@Override
	public int countByC_R_S(
		long companyId, long repositoryId, String storeType) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTSContent.class)) {

			return _collectionPersistenceFinderByC_R_S.count(
				finderCache, new Object[] {companyId, repositoryId, storeType});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_R_P_S;
	private FinderPath _finderPathWithoutPaginationFindByC_R_P_S;
	private FinderPath _finderPathCountByC_R_P_S;
	private CollectionPersistenceFinder<CTSContent>
		_collectionPersistenceFinderByC_R_P_S;

	/**
	 * Returns all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType) {

		return findByC_R_P_S(
			companyId, repositoryId, path, storeType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end) {

		return findByC_R_P_S(
			companyId, repositoryId, path, storeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end, OrderByComparator<CTSContent> orderByComparator) {

		return findByC_R_P_S(
			companyId, repositoryId, path, storeType, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end, OrderByComparator<CTSContent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTSContent.class)) {

			return _collectionPersistenceFinderByC_R_P_S.find(
				finderCache,
				new Object[] {companyId, repositoryId, path, storeType}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByC_R_P_S_First(
			long companyId, long repositoryId, String path, String storeType,
			OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByC_R_P_S_First(
			companyId, repositoryId, path, storeType, orderByComparator);

		if (ctsContent != null) {
			return ctsContent;
		}

		throw new NoSuchContentException(
			_collectionPersistenceFinderByC_R_P_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, repositoryId, path, storeType}));
	}

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_P_S_First(
		long companyId, long repositoryId, String path, String storeType,
		OrderByComparator<CTSContent> orderByComparator) {

		return _collectionPersistenceFinderByC_R_P_S.fetchFirst(
			finderCache,
			new Object[] {companyId, repositoryId, path, storeType},
			orderByComparator);
	}

	/**
	 * Removes all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 */
	@Override
	public void removeByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType) {

		_collectionPersistenceFinderByC_R_P_S.remove(
			finderCache,
			new Object[] {companyId, repositoryId, path, storeType});
	}

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	@Override
	public int countByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTSContent.class)) {

			return _collectionPersistenceFinderByC_R_P_S.count(
				finderCache,
				new Object[] {companyId, repositoryId, path, storeType});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_R_LikeP_S;
	private FinderPath _finderPathWithPaginationCountByC_R_LikeP_S;
	private CollectionPersistenceFinder<CTSContent>
		_collectionPersistenceFinderByC_R_LikeP_S;

	/**
	 * Returns all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType) {

		return findByC_R_LikeP_S(
			companyId, repositoryId, path, storeType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end) {

		return findByC_R_LikeP_S(
			companyId, repositoryId, path, storeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end, OrderByComparator<CTSContent> orderByComparator) {

		return findByC_R_LikeP_S(
			companyId, repositoryId, path, storeType, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cts contents
	 */
	@Override
	public List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end, OrderByComparator<CTSContent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTSContent.class)) {

			return _collectionPersistenceFinderByC_R_LikeP_S.find(
				finderCache,
				new Object[] {companyId, repositoryId, path, storeType}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByC_R_LikeP_S_First(
			long companyId, long repositoryId, String path, String storeType,
			OrderByComparator<CTSContent> orderByComparator)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByC_R_LikeP_S_First(
			companyId, repositoryId, path, storeType, orderByComparator);

		if (ctsContent != null) {
			return ctsContent;
		}

		throw new NoSuchContentException(
			_collectionPersistenceFinderByC_R_LikeP_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, repositoryId, path, storeType}));
	}

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_LikeP_S_First(
		long companyId, long repositoryId, String path, String storeType,
		OrderByComparator<CTSContent> orderByComparator) {

		return _collectionPersistenceFinderByC_R_LikeP_S.fetchFirst(
			finderCache,
			new Object[] {companyId, repositoryId, path, storeType},
			orderByComparator);
	}

	/**
	 * Removes all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 */
	@Override
	public void removeByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType) {

		_collectionPersistenceFinderByC_R_LikeP_S.remove(
			finderCache,
			new Object[] {companyId, repositoryId, path, storeType});
	}

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	@Override
	public int countByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTSContent.class)) {

			return _collectionPersistenceFinderByC_R_LikeP_S.count(
				finderCache,
				new Object[] {companyId, repositoryId, path, storeType});
		}
	}

	private FinderPath _finderPathFetchByC_R_P_V_S;
	private UniquePersistenceFinder<CTSContent>
		_uniquePersistenceFinderByC_R_P_V_S;

	/**
	 * Returns the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; or throws a <code>NoSuchContentException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	@Override
	public CTSContent findByC_R_P_V_S(
			long companyId, long repositoryId, String path, String version,
			String storeType)
		throws NoSuchContentException {

		CTSContent ctsContent = fetchByC_R_P_V_S(
			companyId, repositoryId, path, version, storeType);

		if (ctsContent == null) {
			String message =
				_uniquePersistenceFinderByC_R_P_V_S.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						companyId, repositoryId, path, version, storeType
					});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchContentException(message);
		}

		return ctsContent;
	}

	/**
	 * Returns the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_P_V_S(
		long companyId, long repositoryId, String path, String version,
		String storeType) {

		return fetchByC_R_P_V_S(
			companyId, repositoryId, path, version, storeType, true);
	}

	/**
	 * Returns the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	@Override
	public CTSContent fetchByC_R_P_V_S(
		long companyId, long repositoryId, String path, String version,
		String storeType, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTSContent.class)) {

			return _uniquePersistenceFinderByC_R_P_V_S.fetch(
				finderCache,
				new Object[] {
					companyId, repositoryId, path, version, storeType
				},
				useFinderCache);
		}
	}

	/**
	 * Removes the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the cts content that was removed
	 */
	@Override
	public CTSContent removeByC_R_P_V_S(
			long companyId, long repositoryId, String path, String version,
			String storeType)
		throws NoSuchContentException {

		CTSContent ctsContent = findByC_R_P_V_S(
			companyId, repositoryId, path, version, storeType);

		return remove(ctsContent);
	}

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	@Override
	public int countByC_R_P_V_S(
		long companyId, long repositoryId, String path, String version,
		String storeType) {

		return _uniquePersistenceFinderByC_R_P_V_S.count(
			finderCache,
			new Object[] {companyId, repositoryId, path, version, storeType});
	}

	public CTSContentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("path", "path_");
		dbColumnNames.put("data", "data_");
		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CTSContent.class);

		setModelImplClass(CTSContentImpl.class);
		setModelPKClass(long.class);

		setTable(CTSContentTable.INSTANCE);
	}

	/**
	 * Caches the cts content in the entity cache if it is enabled.
	 *
	 * @param ctsContent the cts content
	 */
	@Override
	public void cacheResult(CTSContent ctsContent) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctsContent.getCtCollectionId())) {

			entityCache.putResult(
				CTSContentImpl.class, ctsContent.getPrimaryKey(), ctsContent);

			finderCache.putResult(
				_finderPathFetchByC_R_P_V_S,
				new Object[] {
					ctsContent.getCompanyId(), ctsContent.getRepositoryId(),
					ctsContent.getPath(), ctsContent.getVersion(),
					ctsContent.getStoreType()
				},
				ctsContent);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cts contents in the entity cache if it is enabled.
	 *
	 * @param ctsContents the cts contents
	 */
	@Override
	public void cacheResult(List<CTSContent> ctsContents) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ctsContents.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CTSContent ctsContent : ctsContents) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctsContent.getCtCollectionId())) {

				if (entityCache.getResult(
						CTSContentImpl.class, ctsContent.getPrimaryKey()) ==
							null) {

					cacheResult(ctsContent);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		CTSContentModelImpl ctsContentModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctsContentModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				ctsContentModelImpl.getCompanyId(),
				ctsContentModelImpl.getRepositoryId(),
				ctsContentModelImpl.getPath(), ctsContentModelImpl.getVersion(),
				ctsContentModelImpl.getStoreType()
			};

			finderCache.putResult(
				_finderPathFetchByC_R_P_V_S, args, ctsContentModelImpl);
		}
	}

	/**
	 * Creates a new cts content with the primary key. Does not add the cts content to the database.
	 *
	 * @param ctsContentId the primary key for the new cts content
	 * @return the new cts content
	 */
	@Override
	public CTSContent create(long ctsContentId) {
		CTSContent ctsContent = new CTSContentImpl();

		ctsContent.setNew(true);
		ctsContent.setPrimaryKey(ctsContentId);

		ctsContent.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctsContent;
	}

	/**
	 * Removes the cts content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctsContentId the primary key of the cts content
	 * @return the cts content that was removed
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent remove(long ctsContentId) throws NoSuchContentException {
		return remove((Serializable)ctsContentId);
	}

	@Override
	protected CTSContent removeImpl(CTSContent ctsContent) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctsContent)) {
				ctsContent = (CTSContent)session.get(
					CTSContentImpl.class, ctsContent.getPrimaryKeyObj());
			}

			if ((ctsContent != null) &&
				ctPersistenceHelper.isRemove(ctsContent)) {

				session.delete(ctsContent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctsContent != null) {
			clearCache(ctsContent);
		}

		return ctsContent;
	}

	@Override
	public CTSContent updateImpl(CTSContent ctsContent) {
		boolean isNew = ctsContent.isNew();

		if (!(ctsContent instanceof CTSContentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctsContent.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctsContent);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctsContent proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTSContent implementation " +
					ctsContent.getClass());
		}

		CTSContentModelImpl ctsContentModelImpl =
			(CTSContentModelImpl)ctsContent;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ctsContent)) {
				if (!isNew) {
					session.evict(
						CTSContentImpl.class, ctsContent.getPrimaryKeyObj());
				}

				session.save(ctsContent);
			}
			else {
				session.evict(
					CTSContentImpl.class, ctsContent.getPrimaryKeyObj());

				session.saveOrUpdate(ctsContent);
			}

			session.flush();
			session.clear();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CTSContentImpl.class, ctsContentModelImpl, false, true);

		cacheUniqueFindersCache(ctsContentModelImpl);

		if (isNew) {
			ctsContent.setNew(false);
		}

		ctsContent.resetOriginalValues();

		return ctsContent;
	}

	/**
	 * Returns the cts content with the primary key or throws a <code>NoSuchContentException</code> if it could not be found.
	 *
	 * @param ctsContentId the primary key of the cts content
	 * @return the cts content
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent findByPrimaryKey(long ctsContentId)
		throws NoSuchContentException {

		return findByPrimaryKey((Serializable)ctsContentId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the cts content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctsContentId the primary key of the cts content
	 * @return the cts content, or <code>null</code> if a cts content with the primary key could not be found
	 */
	@Override
	public CTSContent fetchByPrimaryKey(long ctsContentId) {
		return fetchByPrimaryKey((Serializable)ctsContentId);
	}

	/**
	 * Returns all the cts contents.
	 *
	 * @return the cts contents
	 */
	@Override
	public List<CTSContent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cts contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of cts contents
	 */
	@Override
	public List<CTSContent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cts contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cts contents
	 */
	@Override
	public List<CTSContent> findAll(
		int start, int end, OrderByComparator<CTSContent> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cts contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cts contents
	 */
	@Override
	public List<CTSContent> findAll(
		int start, int end, OrderByComparator<CTSContent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTSContent.class)) {

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

			List<CTSContent> list = null;

			if (useFinderCache) {
				list = (List<CTSContent>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_CTSCONTENT);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_CTSCONTENT;

					sql = sql.concat(CTSContentModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<CTSContent>)QueryUtil.list(
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
	 * Removes all the cts contents from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTSContent ctsContent : findAll()) {
			remove(ctsContent);
		}
	}

	/**
	 * Returns the number of cts contents.
	 *
	 * @return the number of cts contents
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					CTSContent.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_CTSCONTENT);

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
		return "ctsContentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTSCONTENT;
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
		return CTSContentModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CTSContent";
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
		ctMergeColumnNames.add("repositoryId");
		ctMergeColumnNames.add("path_");
		ctMergeColumnNames.add("version");
		ctMergeColumnNames.add("data_");
		ctMergeColumnNames.add("size_");
		ctMergeColumnNames.add("storeType");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("ctsContentId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {
				"companyId", "repositoryId", "path_", "version", "storeType"
			});
	}

	/**
	 * Initializes the cts content persistence.
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

		_finderPathWithPaginationFindByR_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"repositoryId", "path_"}, true);

		_finderPathWithoutPaginationFindByR_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_P",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"repositoryId", "path_"}, true);

		_finderPathCountByR_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_P",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"repositoryId", "path_"}, false);

		_collectionPersistenceFinderByR_P = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByR_P,
			_finderPathWithoutPaginationFindByR_P, _finderPathCountByR_P,
			_SQL_SELECT_CTSCONTENT_WHERE, _SQL_COUNT_CTSCONTENT_WHERE,
			CTSContentModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"ctsContent.", "repositoryId", FinderColumn.Type.LONG, "=",
				true, false, CTSContent::getRepositoryId),
			new FinderColumn<>(
				"ctsContent.", "path", FinderColumn.Type.STRING, "=", true,
				true, CTSContent::getPath));

		_finderPathWithPaginationFindByC_R_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "repositoryId", "storeType"}, true);

		_finderPathWithoutPaginationFindByC_R_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "repositoryId", "storeType"}, true);

		_finderPathCountByC_R_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "repositoryId", "storeType"}, false);

		_collectionPersistenceFinderByC_R_S = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_R_S,
			_finderPathWithoutPaginationFindByC_R_S, _finderPathCountByC_R_S,
			_SQL_SELECT_CTSCONTENT_WHERE, _SQL_COUNT_CTSCONTENT_WHERE,
			CTSContentModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"ctsContent.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, CTSContent::getCompanyId),
			new FinderColumn<>(
				"ctsContent.", "repositoryId", FinderColumn.Type.LONG, "=",
				true, false, CTSContent::getRepositoryId),
			new FinderColumn<>(
				"ctsContent.", "storeType", FinderColumn.Type.STRING, "=", true,
				true, CTSContent::getStoreType));

		_finderPathWithPaginationFindByC_R_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "repositoryId", "path_", "storeType"},
			true);

		_finderPathWithoutPaginationFindByC_R_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_R_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {"companyId", "repositoryId", "path_", "storeType"},
			true);

		_finderPathCountByC_R_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {"companyId", "repositoryId", "path_", "storeType"},
			false);

		_collectionPersistenceFinderByC_R_P_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_R_P_S,
				_finderPathWithoutPaginationFindByC_R_P_S,
				_finderPathCountByC_R_P_S, _SQL_SELECT_CTSCONTENT_WHERE,
				_SQL_COUNT_CTSCONTENT_WHERE, CTSContentModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ctsContent.", "companyId", FinderColumn.Type.LONG, "=",
					true, false, CTSContent::getCompanyId),
				new FinderColumn<>(
					"ctsContent.", "repositoryId", FinderColumn.Type.LONG, "=",
					true, false, CTSContent::getRepositoryId),
				new FinderColumn<>(
					"ctsContent.", "path", FinderColumn.Type.STRING, "=", true,
					false, CTSContent::getPath),
				new FinderColumn<>(
					"ctsContent.", "storeType", FinderColumn.Type.STRING, "=",
					true, true, CTSContent::getStoreType));

		_finderPathWithPaginationFindByC_R_LikeP_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R_LikeP_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "repositoryId", "path_", "storeType"},
			true);

		_finderPathWithPaginationCountByC_R_LikeP_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_R_LikeP_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {"companyId", "repositoryId", "path_", "storeType"},
			false);

		_collectionPersistenceFinderByC_R_LikeP_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_R_LikeP_S, null,
				_finderPathWithPaginationCountByC_R_LikeP_S,
				_SQL_SELECT_CTSCONTENT_WHERE, _SQL_COUNT_CTSCONTENT_WHERE,
				CTSContentModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"ctsContent.", "companyId", FinderColumn.Type.LONG, "=",
					true, false, CTSContent::getCompanyId),
				new FinderColumn<>(
					"ctsContent.", "repositoryId", FinderColumn.Type.LONG, "=",
					true, false, CTSContent::getRepositoryId),
				new FinderColumn<>(
					"ctsContent.", "path", FinderColumn.Type.STRING, "LIKE",
					true, false, CTSContent::getPath),
				new FinderColumn<>(
					"ctsContent.", "storeType", FinderColumn.Type.STRING, "=",
					true, true, CTSContent::getStoreType));

		_finderPathFetchByC_R_P_V_S = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_R_P_V_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"companyId", "repositoryId", "path_", "version", "storeType"
			},
			true);

		_uniquePersistenceFinderByC_R_P_V_S = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_R_P_V_S, _SQL_SELECT_CTSCONTENT_WHERE,
			new FinderColumn<>(
				"ctsContent.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, CTSContent::getCompanyId),
			new FinderColumn<>(
				"ctsContent.", "repositoryId", FinderColumn.Type.LONG, "=",
				true, false, CTSContent::getRepositoryId),
			new FinderColumn<>(
				"ctsContent.", "path", FinderColumn.Type.STRING, "=", true,
				false, CTSContent::getPath),
			new FinderColumn<>(
				"ctsContent.", "version", FinderColumn.Type.STRING, "=", true,
				false, CTSContent::getVersion),
			new FinderColumn<>(
				"ctsContent.", "storeType", FinderColumn.Type.STRING, "=", true,
				true, CTSContent::getStoreType));

		CTSContentUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CTSContentUtil.setPersistence(null);

		entityCache.removeCache(CTSContentImpl.class.getName());
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_CTSCONTENT =
		"SELECT ctsContent FROM CTSContent ctsContent";

	private static final String _SQL_SELECT_CTSCONTENT_WHERE =
		"SELECT ctsContent FROM CTSContent ctsContent WHERE ";

	private static final String _SQL_COUNT_CTSCONTENT =
		"SELECT COUNT(ctsContent) FROM CTSContent ctsContent";

	private static final String _SQL_COUNT_CTSCONTENT_WHERE =
		"SELECT COUNT(ctsContent) FROM CTSContent ctsContent WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctsContent.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTSContent exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTSContentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"path", "data", "size"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:811361746