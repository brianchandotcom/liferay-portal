/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.content.service.persistence.impl;

import com.liferay.document.library.content.exception.NoSuchContentException;
import com.liferay.document.library.content.model.DLContent;
import com.liferay.document.library.content.model.DLContentTable;
import com.liferay.document.library.content.model.impl.DLContentImpl;
import com.liferay.document.library.content.model.impl.DLContentModelImpl;
import com.liferay.document.library.content.service.persistence.DLContentPersistence;
import com.liferay.document.library.content.service.persistence.DLContentUtil;
import com.liferay.document.library.content.service.persistence.impl.constants.DLPersistenceConstants;
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
 * The persistence implementation for the document library content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DLContentPersistence.class)
public class DLContentPersistenceImpl
	extends BasePersistenceImpl<DLContent, NoSuchContentException>
	implements DLContentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DLContentUtil</code> to access the document library content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DLContentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByC_R;
	private FinderPath _finderPathWithoutPaginationFindByC_R;
	private FinderPath _finderPathCountByC_R;
	private CollectionPersistenceFinder<DLContent>
		_collectionPersistenceFinderByC_R;

	/**
	 * Returns all the document library contents where companyId = &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @return the matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R(long companyId, long repositoryId) {
		return findByC_R(
			companyId, repositoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @return the range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R(
		long companyId, long repositoryId, int start, int end) {

		return findByC_R(companyId, repositoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R(
		long companyId, long repositoryId, int start, int end,
		OrderByComparator<DLContent> orderByComparator) {

		return findByC_R(
			companyId, repositoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R(
		long companyId, long repositoryId, int start, int end,
		OrderByComparator<DLContent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DLContent.class)) {

			return _collectionPersistenceFinderByC_R.find(
				finderCache, new Object[] {companyId, repositoryId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content
	 * @throws NoSuchContentException if a matching document library content could not be found
	 */
	@Override
	public DLContent findByC_R_First(
			long companyId, long repositoryId,
			OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {

		DLContent dlContent = fetchByC_R_First(
			companyId, repositoryId, orderByComparator);

		if (dlContent != null) {
			return dlContent;
		}

		throw new NoSuchContentException(
			_collectionPersistenceFinderByC_R.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, repositoryId}));
	}

	/**
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_First(
		long companyId, long repositoryId,
		OrderByComparator<DLContent> orderByComparator) {

		return _collectionPersistenceFinderByC_R.fetchFirst(
			finderCache, new Object[] {companyId, repositoryId},
			orderByComparator);
	}

	/**
	 * Removes all the document library contents where companyId = &#63; and repositoryId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 */
	@Override
	public void removeByC_R(long companyId, long repositoryId) {
		_collectionPersistenceFinderByC_R.remove(
			finderCache, new Object[] {companyId, repositoryId});
	}

	/**
	 * Returns the number of document library contents where companyId = &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @return the number of matching document library contents
	 */
	@Override
	public int countByC_R(long companyId, long repositoryId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DLContent.class)) {

			return _collectionPersistenceFinderByC_R.count(
				finderCache, new Object[] {companyId, repositoryId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_R_P;
	private FinderPath _finderPathWithoutPaginationFindByC_R_P;
	private FinderPath _finderPathCountByC_R_P;
	private CollectionPersistenceFinder<DLContent>
		_collectionPersistenceFinderByC_R_P;

	/**
	 * Returns all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_P(
		long companyId, long repositoryId, String path) {

		return findByC_R_P(
			companyId, repositoryId, path, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @return the range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_P(
		long companyId, long repositoryId, String path, int start, int end) {

		return findByC_R_P(companyId, repositoryId, path, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_P(
		long companyId, long repositoryId, String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator) {

		return findByC_R_P(
			companyId, repositoryId, path, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_P(
		long companyId, long repositoryId, String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DLContent.class)) {

			return _collectionPersistenceFinderByC_R_P.find(
				finderCache, new Object[] {companyId, repositoryId, path},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content
	 * @throws NoSuchContentException if a matching document library content could not be found
	 */
	@Override
	public DLContent findByC_R_P_First(
			long companyId, long repositoryId, String path,
			OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {

		DLContent dlContent = fetchByC_R_P_First(
			companyId, repositoryId, path, orderByComparator);

		if (dlContent != null) {
			return dlContent;
		}

		throw new NoSuchContentException(
			_collectionPersistenceFinderByC_R_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, repositoryId, path}));
	}

	/**
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_P_First(
		long companyId, long repositoryId, String path,
		OrderByComparator<DLContent> orderByComparator) {

		return _collectionPersistenceFinderByC_R_P.fetchFirst(
			finderCache, new Object[] {companyId, repositoryId, path},
			orderByComparator);
	}

	/**
	 * Removes all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 */
	@Override
	public void removeByC_R_P(long companyId, long repositoryId, String path) {
		_collectionPersistenceFinderByC_R_P.remove(
			finderCache, new Object[] {companyId, repositoryId, path});
	}

	/**
	 * Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the number of matching document library contents
	 */
	@Override
	public int countByC_R_P(long companyId, long repositoryId, String path) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DLContent.class)) {

			return _collectionPersistenceFinderByC_R_P.count(
				finderCache, new Object[] {companyId, repositoryId, path});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_R_LikeP;
	private FinderPath _finderPathWithPaginationCountByC_R_LikeP;
	private CollectionPersistenceFinder<DLContent>
		_collectionPersistenceFinderByC_R_LikeP;

	/**
	 * Returns all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_LikeP(
		long companyId, long repositoryId, String path) {

		return findByC_R_LikeP(
			companyId, repositoryId, path, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @return the range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_LikeP(
		long companyId, long repositoryId, String path, int start, int end) {

		return findByC_R_LikeP(companyId, repositoryId, path, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_LikeP(
		long companyId, long repositoryId, String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator) {

		return findByC_R_LikeP(
			companyId, repositoryId, path, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library contents
	 */
	@Override
	public List<DLContent> findByC_R_LikeP(
		long companyId, long repositoryId, String path, int start, int end,
		OrderByComparator<DLContent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DLContent.class)) {

			return _collectionPersistenceFinderByC_R_LikeP.find(
				finderCache, new Object[] {companyId, repositoryId, path},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content
	 * @throws NoSuchContentException if a matching document library content could not be found
	 */
	@Override
	public DLContent findByC_R_LikeP_First(
			long companyId, long repositoryId, String path,
			OrderByComparator<DLContent> orderByComparator)
		throws NoSuchContentException {

		DLContent dlContent = fetchByC_R_LikeP_First(
			companyId, repositoryId, path, orderByComparator);

		if (dlContent != null) {
			return dlContent;
		}

		throw new NoSuchContentException(
			_collectionPersistenceFinderByC_R_LikeP.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {companyId, repositoryId, path}));
	}

	/**
	 * Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_LikeP_First(
		long companyId, long repositoryId, String path,
		OrderByComparator<DLContent> orderByComparator) {

		return _collectionPersistenceFinderByC_R_LikeP.fetchFirst(
			finderCache, new Object[] {companyId, repositoryId, path},
			orderByComparator);
	}

	/**
	 * Removes all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 */
	@Override
	public void removeByC_R_LikeP(
		long companyId, long repositoryId, String path) {

		_collectionPersistenceFinderByC_R_LikeP.remove(
			finderCache, new Object[] {companyId, repositoryId, path});
	}

	/**
	 * Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @return the number of matching document library contents
	 */
	@Override
	public int countByC_R_LikeP(
		long companyId, long repositoryId, String path) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DLContent.class)) {

			return _collectionPersistenceFinderByC_R_LikeP.count(
				finderCache, new Object[] {companyId, repositoryId, path});
		}
	}

	private FinderPath _finderPathFetchByC_R_P_V;
	private UniquePersistenceFinder<DLContent>
		_uniquePersistenceFinderByC_R_P_V;

	/**
	 * Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or throws a <code>NoSuchContentException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @return the matching document library content
	 * @throws NoSuchContentException if a matching document library content could not be found
	 */
	@Override
	public DLContent findByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws NoSuchContentException {

		DLContent dlContent = fetchByC_R_P_V(
			companyId, repositoryId, path, version);

		if (dlContent == null) {
			String message =
				_uniquePersistenceFinderByC_R_P_V.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {companyId, repositoryId, path, version});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchContentException(message);
		}

		return dlContent;
	}

	/**
	 * Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @return the matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_P_V(
		long companyId, long repositoryId, String path, String version) {

		return fetchByC_R_P_V(companyId, repositoryId, path, version, true);
	}

	/**
	 * Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library content, or <code>null</code> if a matching document library content could not be found
	 */
	@Override
	public DLContent fetchByC_R_P_V(
		long companyId, long repositoryId, String path, String version,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DLContent.class)) {

			return _uniquePersistenceFinderByC_R_P_V.fetch(
				finderCache,
				new Object[] {companyId, repositoryId, path, version},
				useFinderCache);
		}
	}

	/**
	 * Removes the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @return the document library content that was removed
	 */
	@Override
	public DLContent removeByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws NoSuchContentException {

		DLContent dlContent = findByC_R_P_V(
			companyId, repositoryId, path, version);

		return remove(dlContent);
	}

	/**
	 * Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @return the number of matching document library contents
	 */
	@Override
	public int countByC_R_P_V(
		long companyId, long repositoryId, String path, String version) {

		return _uniquePersistenceFinderByC_R_P_V.count(
			finderCache, new Object[] {companyId, repositoryId, path, version});
	}

	public DLContentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("path", "path_");
		dbColumnNames.put("data", "data_");
		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DLContent.class);

		setModelImplClass(DLContentImpl.class);
		setModelPKClass(long.class);

		setTable(DLContentTable.INSTANCE);
	}

	/**
	 * Caches the document library content in the entity cache if it is enabled.
	 *
	 * @param dlContent the document library content
	 */
	@Override
	public void cacheResult(DLContent dlContent) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					dlContent.getCtCollectionId())) {

			entityCache.putResult(
				DLContentImpl.class, dlContent.getPrimaryKey(), dlContent);

			finderCache.putResult(
				_finderPathFetchByC_R_P_V,
				new Object[] {
					dlContent.getCompanyId(), dlContent.getRepositoryId(),
					dlContent.getPath(), dlContent.getVersion()
				},
				dlContent);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the document library contents in the entity cache if it is enabled.
	 *
	 * @param dlContents the document library contents
	 */
	@Override
	public void cacheResult(List<DLContent> dlContents) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (dlContents.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DLContent dlContent : dlContents) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						dlContent.getCtCollectionId())) {

				if (entityCache.getResult(
						DLContentImpl.class, dlContent.getPrimaryKey()) ==
							null) {

					cacheResult(dlContent);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		DLContentModelImpl dlContentModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					dlContentModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				dlContentModelImpl.getCompanyId(),
				dlContentModelImpl.getRepositoryId(),
				dlContentModelImpl.getPath(), dlContentModelImpl.getVersion()
			};

			finderCache.putResult(
				_finderPathFetchByC_R_P_V, args, dlContentModelImpl);
		}
	}

	/**
	 * Creates a new document library content with the primary key. Does not add the document library content to the database.
	 *
	 * @param contentId the primary key for the new document library content
	 * @return the new document library content
	 */
	@Override
	public DLContent create(long contentId) {
		DLContent dlContent = new DLContentImpl();

		dlContent.setNew(true);
		dlContent.setPrimaryKey(contentId);

		dlContent.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dlContent;
	}

	/**
	 * Removes the document library content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentId the primary key of the document library content
	 * @return the document library content that was removed
	 * @throws NoSuchContentException if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent remove(long contentId) throws NoSuchContentException {
		return remove((Serializable)contentId);
	}

	@Override
	protected DLContent removeImpl(DLContent dlContent) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlContent)) {
				dlContent = (DLContent)session.get(
					DLContentImpl.class, dlContent.getPrimaryKeyObj());
			}

			if ((dlContent != null) &&
				ctPersistenceHelper.isRemove(dlContent)) {

				session.delete(dlContent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dlContent != null) {
			clearCache(dlContent);
		}

		return dlContent;
	}

	@Override
	public DLContent updateImpl(DLContent dlContent) {
		boolean isNew = dlContent.isNew();

		if (!(dlContent instanceof DLContentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlContent.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(dlContent);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlContent proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLContent implementation " +
					dlContent.getClass());
		}

		DLContentModelImpl dlContentModelImpl = (DLContentModelImpl)dlContent;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(dlContent)) {
				if (!isNew) {
					session.evict(
						DLContentImpl.class, dlContent.getPrimaryKeyObj());
				}

				session.save(dlContent);
			}
			else {
				session.evict(
					DLContentImpl.class, dlContent.getPrimaryKeyObj());

				session.saveOrUpdate(dlContent);
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
			DLContentImpl.class, dlContentModelImpl, false, true);

		cacheUniqueFindersCache(dlContentModelImpl);

		if (isNew) {
			dlContent.setNew(false);
		}

		dlContent.resetOriginalValues();

		return dlContent;
	}

	/**
	 * Returns the document library content with the primary key or throws a <code>NoSuchContentException</code> if it could not be found.
	 *
	 * @param contentId the primary key of the document library content
	 * @return the document library content
	 * @throws NoSuchContentException if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent findByPrimaryKey(long contentId)
		throws NoSuchContentException {

		return findByPrimaryKey((Serializable)contentId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the document library content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contentId the primary key of the document library content
	 * @return the document library content, or <code>null</code> if a document library content with the primary key could not be found
	 */
	@Override
	public DLContent fetchByPrimaryKey(long contentId) {
		return fetchByPrimaryKey((Serializable)contentId);
	}

	/**
	 * Returns all the document library contents.
	 *
	 * @return the document library contents
	 */
	@Override
	public List<DLContent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @return the range of document library contents
	 */
	@Override
	public List<DLContent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library contents
	 */
	@Override
	public List<DLContent> findAll(
		int start, int end, OrderByComparator<DLContent> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of document library contents
	 */
	@Override
	public List<DLContent> findAll(
		int start, int end, OrderByComparator<DLContent> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DLContent.class)) {

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

			List<DLContent> list = null;

			if (useFinderCache) {
				list = (List<DLContent>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_DLCONTENT);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_DLCONTENT;

					sql = sql.concat(DLContentModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<DLContent>)QueryUtil.list(
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
	 * Removes all the document library contents from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLContent dlContent : findAll()) {
			remove(dlContent);
		}
	}

	/**
	 * Returns the number of document library contents.
	 *
	 * @return the number of document library contents
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					DLContent.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_DLCONTENT);

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
		return "contentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLCONTENT;
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
		return DLContentModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DLContent";
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
		ctMergeColumnNames.add("repositoryId");
		ctMergeColumnNames.add("path_");
		ctMergeColumnNames.add("version");
		ctMergeColumnNames.add("data_");
		ctMergeColumnNames.add("size_");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("contentId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"companyId", "repositoryId", "path_", "version"});
	}

	/**
	 * Initializes the document library content persistence.
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

		_finderPathWithPaginationFindByC_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "repositoryId"}, true);

		_finderPathWithoutPaginationFindByC_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "repositoryId"}, true);

		_finderPathCountByC_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "repositoryId"}, false);

		_collectionPersistenceFinderByC_R = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_R,
			_finderPathWithoutPaginationFindByC_R, _finderPathCountByC_R,
			_SQL_SELECT_DLCONTENT_WHERE, _SQL_COUNT_DLCONTENT_WHERE,
			DLContentModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"dlContent.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, DLContent::getCompanyId),
			new FinderColumn<>(
				"dlContent.", "repositoryId", FinderColumn.Type.LONG, "=", true,
				true, DLContent::getRepositoryId));

		_finderPathWithPaginationFindByC_R_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "repositoryId", "path_"}, true);

		_finderPathWithoutPaginationFindByC_R_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_R_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "repositoryId", "path_"}, true);

		_finderPathCountByC_R_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "repositoryId", "path_"}, false);

		_collectionPersistenceFinderByC_R_P = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByC_R_P,
			_finderPathWithoutPaginationFindByC_R_P, _finderPathCountByC_R_P,
			_SQL_SELECT_DLCONTENT_WHERE, _SQL_COUNT_DLCONTENT_WHERE,
			DLContentModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"dlContent.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, DLContent::getCompanyId),
			new FinderColumn<>(
				"dlContent.", "repositoryId", FinderColumn.Type.LONG, "=", true,
				false, DLContent::getRepositoryId),
			new FinderColumn<>(
				"dlContent.", "path", FinderColumn.Type.STRING, "=", true, true,
				DLContent::getPath));

		_finderPathWithPaginationFindByC_R_LikeP = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R_LikeP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "repositoryId", "path_"}, true);

		_finderPathWithPaginationCountByC_R_LikeP = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_R_LikeP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "repositoryId", "path_"}, false);

		_collectionPersistenceFinderByC_R_LikeP =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByC_R_LikeP, null,
				_finderPathWithPaginationCountByC_R_LikeP,
				_SQL_SELECT_DLCONTENT_WHERE, _SQL_COUNT_DLCONTENT_WHERE,
				DLContentModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"dlContent.", "companyId", FinderColumn.Type.LONG, "=",
					true, false, DLContent::getCompanyId),
				new FinderColumn<>(
					"dlContent.", "repositoryId", FinderColumn.Type.LONG, "=",
					true, false, DLContent::getRepositoryId),
				new FinderColumn<>(
					"dlContent.", "path", FinderColumn.Type.STRING, "LIKE",
					true, true, DLContent::getPath));

		_finderPathFetchByC_R_P_V = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_R_P_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {"companyId", "repositoryId", "path_", "version"},
			true);

		_uniquePersistenceFinderByC_R_P_V = new UniquePersistenceFinder<>(
			this, _finderPathFetchByC_R_P_V, _SQL_SELECT_DLCONTENT_WHERE,
			new FinderColumn<>(
				"dlContent.", "companyId", FinderColumn.Type.LONG, "=", true,
				false, DLContent::getCompanyId),
			new FinderColumn<>(
				"dlContent.", "repositoryId", FinderColumn.Type.LONG, "=", true,
				false, DLContent::getRepositoryId),
			new FinderColumn<>(
				"dlContent.", "path", FinderColumn.Type.STRING, "=", true,
				false, DLContent::getPath),
			new FinderColumn<>(
				"dlContent.", "version", FinderColumn.Type.STRING, "=", true,
				true, DLContent::getVersion));

		DLContentUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		DLContentUtil.setPersistence(null);

		entityCache.removeCache(DLContentImpl.class.getName());
	}

	@Override
	@Reference(
		target = DLPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DLCONTENT =
		"SELECT dlContent FROM DLContent dlContent";

	private static final String _SQL_SELECT_DLCONTENT_WHERE =
		"SELECT dlContent FROM DLContent dlContent WHERE ";

	private static final String _SQL_COUNT_DLCONTENT =
		"SELECT COUNT(dlContent) FROM DLContent dlContent";

	private static final String _SQL_COUNT_DLCONTENT_WHERE =
		"SELECT COUNT(dlContent) FROM DLContent dlContent WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "dlContent.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DLContent exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DLContentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"path", "data", "size"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:664347016