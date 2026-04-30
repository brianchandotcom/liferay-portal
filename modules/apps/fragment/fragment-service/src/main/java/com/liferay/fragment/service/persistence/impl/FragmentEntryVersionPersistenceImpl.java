/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.exception.NoSuchEntryVersionException;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.model.FragmentEntryVersionTable;
import com.liferay.fragment.model.impl.FragmentEntryVersionImpl;
import com.liferay.fragment.model.impl.FragmentEntryVersionModelImpl;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.fragment.service.persistence.FragmentEntryVersionUtil;
import com.liferay.fragment.service.persistence.impl.constants.FragmentPersistenceConstants;
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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

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
 * The persistence implementation for the fragment entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = FragmentEntryVersionPersistence.class)
public class FragmentEntryVersionPersistenceImpl
	extends BasePersistenceImpl
		<FragmentEntryVersion, NoSuchEntryVersionException>
	implements FragmentEntryVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FragmentEntryVersionUtil</code> to access the fragment entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FragmentEntryVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByFragmentEntryId;
	private FinderPath _finderPathWithoutPaginationFindByFragmentEntryId;
	private FinderPath _finderPathCountByFragmentEntryId;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByFragmentEntryId;

	/**
	 * Returns all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId) {

		return findByFragmentEntryId(
			fragmentEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId, int start, int end) {

		return findByFragmentEntryId(fragmentEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByFragmentEntryId(
			fragmentEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByFragmentEntryId.find(
				finderCache, new Object[] {fragmentEntryId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByFragmentEntryId_First(
			long fragmentEntryId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByFragmentEntryId_First(fragmentEntryId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByFragmentEntryId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {fragmentEntryId}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentEntryId_First(
		long fragmentEntryId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByFragmentEntryId.fetchFirst(
			finderCache, new Object[] {fragmentEntryId}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where fragmentEntryId = &#63; from the database.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 */
	@Override
	public void removeByFragmentEntryId(long fragmentEntryId) {
		_collectionPersistenceFinderByFragmentEntryId.remove(
			finderCache, new Object[] {fragmentEntryId});
	}

	/**
	 * Returns the number of fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByFragmentEntryId(long fragmentEntryId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByFragmentEntryId.count(
				finderCache, new Object[] {fragmentEntryId});
		}
	}

	private FinderPath _finderPathFetchByFragmentEntryId_Version;
	private UniquePersistenceFinder<FragmentEntryVersion>
		_uniquePersistenceFinderByFragmentEntryId_Version;

	/**
	 * Returns the fragment entry version where fragmentEntryId = &#63; and version = &#63; or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByFragmentEntryId_Version(
			long fragmentEntryId, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByFragmentEntryId_Version(fragmentEntryId, version);

		if (fragmentEntryVersion == null) {
			String message =
				_uniquePersistenceFinderByFragmentEntryId_Version.
					buildNoSuchKeyMessage(
						_NO_SUCH_ENTITY_WITH_KEY,
						new Object[] {fragmentEntryId, version});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryVersionException(message);
		}

		return fragmentEntryVersion;
	}

	/**
	 * Returns the fragment entry version where fragmentEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentEntryId_Version(
		long fragmentEntryId, int version) {

		return fetchByFragmentEntryId_Version(fragmentEntryId, version, true);
	}

	/**
	 * Returns the fragment entry version where fragmentEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentEntryId_Version(
		long fragmentEntryId, int version, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _uniquePersistenceFinderByFragmentEntryId_Version.fetch(
				finderCache, new Object[] {fragmentEntryId, version},
				useFinderCache);
		}
	}

	/**
	 * Removes the fragment entry version where fragmentEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the fragment entry version that was removed
	 */
	@Override
	public FragmentEntryVersion removeByFragmentEntryId_Version(
			long fragmentEntryId, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			findByFragmentEntryId_Version(fragmentEntryId, version);

		return remove(fragmentEntryVersion);
	}

	/**
	 * Returns the number of fragment entry versions where fragmentEntryId = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByFragmentEntryId_Version(
		long fragmentEntryId, int version) {

		return _uniquePersistenceFinderByFragmentEntryId_Version.count(
			finderCache, new Object[] {fragmentEntryId, version});
	}

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the fragment entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_First(
			String uuid,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_First(
			uuid, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_First(
		String uuid,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of fragment entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathWithPaginationFindByUuid_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_Version;
	private FinderPath _finderPathCountByUuid_Version;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByUuid_Version;

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version) {

		return findByUuid_Version(
			uuid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end) {

		return findByUuid_Version(uuid, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByUuid_Version(
			uuid, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByUuid_Version.find(
				finderCache, new Object[] {uuid, version}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_Version_First(
			String uuid, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_Version_First(
			uuid, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByUuid_Version.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, version}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_Version_First(
		String uuid, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByUuid_Version.fetchFirst(
			finderCache, new Object[] {uuid, version}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	@Override
	public void removeByUuid_Version(String uuid, int version) {
		_collectionPersistenceFinderByUuid_Version.remove(
			finderCache, new Object[] {uuid, version});
	}

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUuid_Version(String uuid, int version) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByUuid_Version.count(
				finderCache, new Object[] {uuid, version});
		}
	}

	private FinderPath _finderPathWithPaginationFindByUUID_G;
	private FinderPath _finderPathWithoutPaginationFindByUUID_G;
	private FinderPath _finderPathCountByUUID_G;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByUUID_G;

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUUID_G(String uuid, long groupId) {
		return findByUUID_G(
			uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return findByUUID_G(uuid, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByUUID_G(uuid, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByUUID_G.find(
				finderCache, new Object[] {uuid, groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUUID_G_First(
			uuid, groupId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByUUID_G.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByUUID_G.fetchFirst(
			finderCache, new Object[] {uuid, groupId}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	@Override
	public void removeByUUID_G(String uuid, long groupId) {
		_collectionPersistenceFinderByUUID_G.remove(
			finderCache, new Object[] {uuid, groupId});
	}

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByUUID_G.count(
				finderCache, new Object[] {uuid, groupId});
		}
	}

	private FinderPath _finderPathFetchByUUID_G_Version;
	private UniquePersistenceFinder<FragmentEntryVersion>
		_uniquePersistenceFinderByUUID_G_Version;

	/**
	 * Returns the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUUID_G_Version(
			uuid, groupId, version);

		if (fragmentEntryVersion == null) {
			String message =
				_uniquePersistenceFinderByUUID_G_Version.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {uuid, groupId, version});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryVersionException(message);
		}

		return fragmentEntryVersion;
	}

	/**
	 * Returns the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version) {

		return fetchByUUID_G_Version(uuid, groupId, version, true);
	}

	/**
	 * Returns the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _uniquePersistenceFinderByUUID_G_Version.fetch(
				finderCache, new Object[] {uuid, groupId, version},
				useFinderCache);
		}
	}

	/**
	 * Removes the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the fragment entry version that was removed
	 */
	@Override
	public FragmentEntryVersion removeByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByUUID_G_Version(
			uuid, groupId, version);

		return remove(fragmentEntryVersion);
	}

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and groupId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUUID_G_Version(String uuid, long groupId, int version) {
		return _uniquePersistenceFinderByUUID_G_Version.count(
			finderCache, new Object[] {uuid, groupId, version});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C_Version;
	private FinderPath _finderPathCountByUuid_C_Version;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByUuid_C_Version;

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version) {

		return findByUuid_C_Version(
			uuid, companyId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end) {

		return findByUuid_C_Version(uuid, companyId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByUuid_C_Version.find(
				finderCache, new Object[] {uuid, companyId, version}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByUuid_C_Version_First(
			uuid, companyId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByUuid_C_Version.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {uuid, companyId, version}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C_Version.fetchFirst(
			finderCache, new Object[] {uuid, companyId, version},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 */
	@Override
	public void removeByUuid_C_Version(
		String uuid, long companyId, int version) {

		_collectionPersistenceFinderByUuid_C_Version.remove(
			finderCache, new Object[] {uuid, companyId, version});
	}

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByUuid_C_Version(String uuid, long companyId, int version) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByUuid_C_Version.count(
				finderCache, new Object[] {uuid, companyId, version});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the fragment entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				finderCache, new Object[] {groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByGroupId_First(
			long groupId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByGroupId_First(
			groupId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByGroupId_First(
		long groupId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				finderCache, new Object[] {groupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId_Version;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Version;
	private FinderPath _finderPathCountByGroupId_Version;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByGroupId_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version) {

		return findByGroupId_Version(
			groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end) {

		return findByGroupId_Version(groupId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByGroupId_Version(
			groupId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByGroupId_Version.find(
				finderCache, new Object[] {groupId, version}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByGroupId_Version_First(
			long groupId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByGroupId_Version_First(groupId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByGroupId_Version.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, version}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByGroupId_Version_First(
		long groupId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByGroupId_Version.fetchFirst(
			finderCache, new Object[] {groupId, version}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	@Override
	public void removeByGroupId_Version(long groupId, int version) {
		_collectionPersistenceFinderByGroupId_Version.remove(
			finderCache, new Object[] {groupId, version});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByGroupId_Version(long groupId, int version) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByGroupId_Version.count(
				finderCache, new Object[] {groupId, version});
		}
	}

	private FinderPath _finderPathWithPaginationFindByFragmentCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByFragmentCollectionId;
	private FinderPath _finderPathCountByFragmentCollectionId;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByFragmentCollectionId;

	/**
	 * Returns all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId) {

		return findByFragmentCollectionId(
			fragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end) {

		return findByFragmentCollectionId(
			fragmentCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByFragmentCollectionId(
			fragmentCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByFragmentCollectionId.find(
				finderCache, new Object[] {fragmentCollectionId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByFragmentCollectionId_First(
			long fragmentCollectionId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByFragmentCollectionId_First(
				fragmentCollectionId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByFragmentCollectionId.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {fragmentCollectionId}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentCollectionId_First(
		long fragmentCollectionId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByFragmentCollectionId.fetchFirst(
			finderCache, new Object[] {fragmentCollectionId},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where fragmentCollectionId = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 */
	@Override
	public void removeByFragmentCollectionId(long fragmentCollectionId) {
		_collectionPersistenceFinderByFragmentCollectionId.remove(
			finderCache, new Object[] {fragmentCollectionId});
	}

	/**
	 * Returns the number of fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByFragmentCollectionId(long fragmentCollectionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByFragmentCollectionId.count(
				finderCache, new Object[] {fragmentCollectionId});
		}
	}

	private FinderPath
		_finderPathWithPaginationFindByFragmentCollectionId_Version;
	private FinderPath
		_finderPathWithoutPaginationFindByFragmentCollectionId_Version;
	private FinderPath _finderPathCountByFragmentCollectionId_Version;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByFragmentCollectionId_Version;

	/**
	 * Returns all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId_Version(
		long fragmentCollectionId, int version) {

		return findByFragmentCollectionId_Version(
			fragmentCollectionId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId_Version(
		long fragmentCollectionId, int version, int start, int end) {

		return findByFragmentCollectionId_Version(
			fragmentCollectionId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId_Version(
		long fragmentCollectionId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByFragmentCollectionId_Version(
			fragmentCollectionId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByFragmentCollectionId_Version(
		long fragmentCollectionId, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByFragmentCollectionId_Version.
				find(
					finderCache, new Object[] {fragmentCollectionId, version},
					start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByFragmentCollectionId_Version_First(
			long fragmentCollectionId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByFragmentCollectionId_Version_First(
				fragmentCollectionId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByFragmentCollectionId_Version.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {fragmentCollectionId, version}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByFragmentCollectionId_Version_First(
		long fragmentCollectionId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByFragmentCollectionId_Version.
			fetchFirst(
				finderCache, new Object[] {fragmentCollectionId, version},
				orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 */
	@Override
	public void removeByFragmentCollectionId_Version(
		long fragmentCollectionId, int version) {

		_collectionPersistenceFinderByFragmentCollectionId_Version.remove(
			finderCache, new Object[] {fragmentCollectionId, version});
	}

	/**
	 * Returns the number of fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByFragmentCollectionId_Version(
		long fragmentCollectionId, int version) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByFragmentCollectionId_Version.
				count(
					finderCache, new Object[] {fragmentCollectionId, version});
		}
	}

	private FinderPath _finderPathWithPaginationFindByType;
	private FinderPath _finderPathWithoutPaginationFindByType;
	private FinderPath _finderPathCountByType;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByType;

	/**
	 * Returns all the fragment entry versions where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByType(int type) {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByType(int type, int start, int end) {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByType(
		int type, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByType(type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByType(
		int type, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByType.find(
				finderCache, new Object[] {type}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByType_First(
			int type, OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByType_First(
			type, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByType.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {type}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByType_First(
		int type, OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByType.fetchFirst(
			finderCache, new Object[] {type}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where type = &#63; from the database.
	 *
	 * @param type the type
	 */
	@Override
	public void removeByType(int type) {
		_collectionPersistenceFinderByType.remove(
			finderCache, new Object[] {type});
	}

	/**
	 * Returns the number of fragment entry versions where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByType(int type) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByType.count(
				finderCache, new Object[] {type});
		}
	}

	private FinderPath _finderPathWithPaginationFindByType_Version;
	private FinderPath _finderPathWithoutPaginationFindByType_Version;
	private FinderPath _finderPathCountByType_Version;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByType_Version;

	/**
	 * Returns all the fragment entry versions where type = &#63; and version = &#63;.
	 *
	 * @param type the type
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByType_Version(
		int type, int version) {

		return findByType_Version(
			type, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByType_Version(
		int type, int version, int start, int end) {

		return findByType_Version(type, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByType_Version(
		int type, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByType_Version(
			type, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByType_Version(
		int type, int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByType_Version.find(
				finderCache, new Object[] {type, version}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where type = &#63; and version = &#63;.
	 *
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByType_Version_First(
			int type, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByType_Version_First(
			type, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByType_Version.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {type, version}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where type = &#63; and version = &#63;.
	 *
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByType_Version_First(
		int type, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByType_Version.fetchFirst(
			finderCache, new Object[] {type, version}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where type = &#63; and version = &#63; from the database.
	 *
	 * @param type the type
	 * @param version the version
	 */
	@Override
	public void removeByType_Version(int type, int version) {
		_collectionPersistenceFinderByType_Version.remove(
			finderCache, new Object[] {type, version});
	}

	/**
	 * Returns the number of fragment entry versions where type = &#63; and version = &#63;.
	 *
	 * @param type the type
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByType_Version(int type, int version) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByType_Version.count(
				finderCache, new Object[] {type, version});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI;
	private FinderPath _finderPathCountByG_FCI;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId) {

		return findByG_FCI(
			groupId, fragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end) {

		return findByG_FCI(groupId, fragmentCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI(
			groupId, fragmentCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI.find(
				finderCache, new Object[] {groupId, fragmentCollectionId},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_First(
			long groupId, long fragmentCollectionId,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_First(
			groupId, fragmentCollectionId, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_First(
		long groupId, long fragmentCollectionId,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI.fetchFirst(
			finderCache, new Object[] {groupId, fragmentCollectionId},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 */
	@Override
	public void removeByG_FCI(long groupId, long fragmentCollectionId) {
		_collectionPersistenceFinderByG_FCI.remove(
			finderCache, new Object[] {groupId, fragmentCollectionId});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI(long groupId, long fragmentCollectionId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI.count(
				finderCache, new Object[] {groupId, fragmentCollectionId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_Version;
	private FinderPath _finderPathCountByG_FCI_Version;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version) {

		return findByG_FCI_Version(
			groupId, fragmentCollectionId, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version, int start,
		int end) {

		return findByG_FCI_Version(
			groupId, fragmentCollectionId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version, int start,
		int end, OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_Version(
			groupId, fragmentCollectionId, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version, int start,
		int end, OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_Version.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, version}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_Version_First(
			long groupId, long fragmentCollectionId, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_Version_First(
			groupId, fragmentCollectionId, version, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI_Version.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, version}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_Version_First(
		long groupId, long fragmentCollectionId, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_Version.fetchFirst(
			finderCache, new Object[] {groupId, fragmentCollectionId, version},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version) {

		_collectionPersistenceFinderByG_FCI_Version.remove(
			finderCache, new Object[] {groupId, fragmentCollectionId, version});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_Version.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, version});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FEK;
	private FinderPath _finderPathWithoutPaginationFindByG_FEK;
	private FinderPath _finderPathCountByG_FEK;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FEK;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey) {

		return findByG_FEK(
			groupId, fragmentEntryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end) {

		return findByG_FEK(groupId, fragmentEntryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FEK(
			groupId, fragmentEntryKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FEK.find(
				finderCache, new Object[] {groupId, fragmentEntryKey}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FEK_First(
			long groupId, String fragmentEntryKey,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FEK_First(
			groupId, fragmentEntryKey, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FEK.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentEntryKey}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FEK_First(
		long groupId, String fragmentEntryKey,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FEK.fetchFirst(
			finderCache, new Object[] {groupId, fragmentEntryKey},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 */
	@Override
	public void removeByG_FEK(long groupId, String fragmentEntryKey) {
		_collectionPersistenceFinderByG_FEK.remove(
			finderCache, new Object[] {groupId, fragmentEntryKey});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FEK(long groupId, String fragmentEntryKey) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FEK.count(
				finderCache, new Object[] {groupId, fragmentEntryKey});
		}
	}

	private FinderPath _finderPathFetchByG_FEK_Version;
	private UniquePersistenceFinder<FragmentEntryVersion>
		_uniquePersistenceFinderByG_FEK_Version;

	/**
	 * Returns the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FEK_Version(
			long groupId, String fragmentEntryKey, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FEK_Version(
			groupId, fragmentEntryKey, version);

		if (fragmentEntryVersion == null) {
			String message =
				_uniquePersistenceFinderByG_FEK_Version.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {groupId, fragmentEntryKey, version});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryVersionException(message);
		}

		return fragmentEntryVersion;
	}

	/**
	 * Returns the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FEK_Version(
		long groupId, String fragmentEntryKey, int version) {

		return fetchByG_FEK_Version(groupId, fragmentEntryKey, version, true);
	}

	/**
	 * Returns the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FEK_Version(
		long groupId, String fragmentEntryKey, int version,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _uniquePersistenceFinderByG_FEK_Version.fetch(
				finderCache, new Object[] {groupId, fragmentEntryKey, version},
				useFinderCache);
		}
	}

	/**
	 * Removes the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the fragment entry version that was removed
	 */
	@Override
	public FragmentEntryVersion removeByG_FEK_Version(
			long groupId, String fragmentEntryKey, int version)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = findByG_FEK_Version(
			groupId, fragmentEntryKey, version);

		return remove(fragmentEntryVersion);
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FEK_Version(
		long groupId, String fragmentEntryKey, int version) {

		return _uniquePersistenceFinderByG_FEK_Version.count(
			finderCache, new Object[] {groupId, fragmentEntryKey, version});
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_LikeN;
	private FinderPath _finderPathCountByG_FCI_LikeN;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI_LikeN;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN.find(
				finderCache, new Object[] {groupId, fragmentCollectionId, name},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_First(
			long groupId, long fragmentCollectionId, String name,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_LikeN_First(
			groupId, fragmentCollectionId, name, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI_LikeN.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, name}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_First(
		long groupId, long fragmentCollectionId, String name,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_LikeN.fetchFirst(
			finderCache, new Object[] {groupId, fragmentCollectionId, name},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 */
	@Override
	public void removeByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name) {

		_collectionPersistenceFinderByG_FCI_LikeN.remove(
			finderCache, new Object[] {groupId, fragmentCollectionId, name});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, name});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_LikeN_Version;
	private FinderPath _finderPathCountByG_FCI_LikeN_Version;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI_LikeN_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version) {

		return findByG_FCI_LikeN_Version(
			groupId, fragmentCollectionId, name, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version,
		int start, int end) {

		return findByG_FCI_LikeN_Version(
			groupId, fragmentCollectionId, name, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_LikeN_Version(
			groupId, fragmentCollectionId, name, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_Version.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, name, version},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_Version_First(
			long groupId, long fragmentCollectionId, String name, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_LikeN_Version_First(
				groupId, fragmentCollectionId, name, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI_LikeN_Version.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						groupId, fragmentCollectionId, name, version
					}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_Version_First(
		long groupId, long fragmentCollectionId, String name, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_LikeN_Version.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, name, version},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version) {

		_collectionPersistenceFinderByG_FCI_LikeN_Version.remove(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, name, version});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_Version.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, name, version});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_T;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T;
	private FinderPath _finderPathCountByG_FCI_T;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI_T;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_T.find(
				finderCache, new Object[] {groupId, fragmentCollectionId, type},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_First(
			long groupId, long fragmentCollectionId, int type,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_T_First(
			groupId, fragmentCollectionId, type, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI_T.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, type}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_First(
		long groupId, long fragmentCollectionId, int type,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_T.fetchFirst(
			finderCache, new Object[] {groupId, fragmentCollectionId, type},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 */
	@Override
	public void removeByG_FCI_T(
		long groupId, long fragmentCollectionId, int type) {

		_collectionPersistenceFinderByG_FCI_T.remove(
			finderCache, new Object[] {groupId, fragmentCollectionId, type});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_T(
		long groupId, long fragmentCollectionId, int type) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_T.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, type});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_Version;
	private FinderPath _finderPathCountByG_FCI_T_Version;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI_T_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version) {

		return findByG_FCI_T_Version(
			groupId, fragmentCollectionId, type, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version,
		int start, int end) {

		return findByG_FCI_T_Version(
			groupId, fragmentCollectionId, type, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_T_Version(
			groupId, fragmentCollectionId, type, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_T_Version.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, type, version},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_Version_First(
			long groupId, long fragmentCollectionId, int type, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_T_Version_First(
				groupId, fragmentCollectionId, type, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI_T_Version.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, type, version}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_Version_First(
		long groupId, long fragmentCollectionId, int type, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_T_Version.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, type, version},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version) {

		_collectionPersistenceFinderByG_FCI_T_Version.remove(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, type, version});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_T_Version.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, type, version});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_S;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_S;
	private FinderPath _finderPathCountByG_FCI_S;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI_S;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start,
		int end) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_S.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, status}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_S_First(
			long groupId, long fragmentCollectionId, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_S_First(
			groupId, fragmentCollectionId, status, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, status}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_S_First(
		long groupId, long fragmentCollectionId, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_S.fetchFirst(
			finderCache, new Object[] {groupId, fragmentCollectionId, status},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 */
	@Override
	public void removeByG_FCI_S(
		long groupId, long fragmentCollectionId, int status) {

		_collectionPersistenceFinderByG_FCI_S.remove(
			finderCache, new Object[] {groupId, fragmentCollectionId, status});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_S(
		long groupId, long fragmentCollectionId, int status) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_S.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_S_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_S_Version;
	private FinderPath _finderPathCountByG_FCI_S_Version;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI_S_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version) {

		return findByG_FCI_S_Version(
			groupId, fragmentCollectionId, status, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version,
		int start, int end) {

		return findByG_FCI_S_Version(
			groupId, fragmentCollectionId, status, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_S_Version(
			groupId, fragmentCollectionId, status, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_S_Version.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, status, version},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_S_Version_First(
			long groupId, long fragmentCollectionId, int status, int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_S_Version_First(
				groupId, fragmentCollectionId, status, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI_S_Version.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, status, version}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_S_Version_First(
		long groupId, long fragmentCollectionId, int status, int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_S_Version.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, status, version},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version) {

		_collectionPersistenceFinderByG_FCI_S_Version.remove(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, status, version});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_S_Version.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, status, version});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_S;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_LikeN_S;
	private FinderPath _finderPathCountByG_FCI_LikeN_S;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI_LikeN_S;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_S.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, name, status},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_S_First(
			long groupId, long fragmentCollectionId, String name, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_LikeN_S_First(
			groupId, fragmentCollectionId, name, status, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI_LikeN_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, name, status}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_S_First(
		long groupId, long fragmentCollectionId, String name, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_LikeN_S.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, name, status},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 */
	@Override
	public void removeByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status) {

		_collectionPersistenceFinderByG_FCI_LikeN_S.remove(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, name, status});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_S.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, name, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_S_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_LikeN_S_Version;
	private FinderPath _finderPathCountByG_FCI_LikeN_S_Version;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI_LikeN_S_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version) {

		return findByG_FCI_LikeN_S_Version(
			groupId, fragmentCollectionId, name, status, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version, int start, int end) {

		return findByG_FCI_LikeN_S_Version(
			groupId, fragmentCollectionId, name, status, version, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_LikeN_S_Version(
			groupId, fragmentCollectionId, name, status, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_S_Version.find(
				finderCache,
				new Object[] {
					groupId, fragmentCollectionId, name, status, version
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_LikeN_S_Version_First(
			long groupId, long fragmentCollectionId, String name, int status,
			int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_LikeN_S_Version_First(
				groupId, fragmentCollectionId, name, status, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI_LikeN_S_Version.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						groupId, fragmentCollectionId, name, status, version
					}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_LikeN_S_Version_First(
		long groupId, long fragmentCollectionId, String name, int status,
		int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_LikeN_S_Version.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, name, status, version},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version) {

		_collectionPersistenceFinderByG_FCI_LikeN_S_Version.remove(
			finderCache,
			new Object[] {
				groupId, fragmentCollectionId, name, status, version
			});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_LikeN_S_Version.count(
				finderCache,
				new Object[] {
					groupId, fragmentCollectionId, name, status, version
				});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_S;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_S;
	private FinderPath _finderPathCountByG_FCI_T_S;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI_T_S;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_T_S.find(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, type, status},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_S_First(
			long groupId, long fragmentCollectionId, int type, int status,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion = fetchByG_FCI_T_S_First(
			groupId, fragmentCollectionId, type, status, orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI_T_S.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, fragmentCollectionId, type, status}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_S_First(
		long groupId, long fragmentCollectionId, int type, int status,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_T_S.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, type, status},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status) {

		_collectionPersistenceFinderByG_FCI_T_S.remove(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, type, status});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_T_S.count(
				finderCache,
				new Object[] {groupId, fragmentCollectionId, type, status});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_S_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_S_Version;
	private FinderPath _finderPathCountByG_FCI_T_S_Version;
	private CollectionPersistenceFinder<FragmentEntryVersion>
		_collectionPersistenceFinderByG_FCI_T_S_Version;

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version) {

		return findByG_FCI_T_S_Version(
			groupId, fragmentCollectionId, type, status, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version, int start, int end) {

		return findByG_FCI_T_S_Version(
			groupId, fragmentCollectionId, type, status, version, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findByG_FCI_T_S_Version(
			groupId, fragmentCollectionId, type, status, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version, int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_T_S_Version.find(
				finderCache,
				new Object[] {
					groupId, fragmentCollectionId, type, status, version
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion findByG_FCI_T_S_Version_First(
			long groupId, long fragmentCollectionId, int type, int status,
			int version,
			OrderByComparator<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		FragmentEntryVersion fragmentEntryVersion =
			fetchByG_FCI_T_S_Version_First(
				groupId, fragmentCollectionId, type, status, version,
				orderByComparator);

		if (fragmentEntryVersion != null) {
			return fragmentEntryVersion;
		}

		throw new NoSuchEntryVersionException(
			_collectionPersistenceFinderByG_FCI_T_S_Version.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						groupId, fragmentCollectionId, type, status, version
					}));
	}

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByG_FCI_T_S_Version_First(
		long groupId, long fragmentCollectionId, int type, int status,
		int version,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return _collectionPersistenceFinderByG_FCI_T_S_Version.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentCollectionId, type, status, version},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 */
	@Override
	public void removeByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version) {

		_collectionPersistenceFinderByG_FCI_T_S_Version.remove(
			finderCache,
			new Object[] {
				groupId, fragmentCollectionId, type, status, version
			});
	}

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	@Override
	public int countByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			return _collectionPersistenceFinderByG_FCI_T_S_Version.count(
				finderCache,
				new Object[] {
					groupId, fragmentCollectionId, type, status, version
				});
		}
	}

	public FragmentEntryVersionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FragmentEntryVersion.class);

		setModelImplClass(FragmentEntryVersionImpl.class);
		setModelPKClass(long.class);

		setTable(FragmentEntryVersionTable.INSTANCE);
	}

	/**
	 * Caches the fragment entry version in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryVersion the fragment entry version
	 */
	@Override
	public void cacheResult(FragmentEntryVersion fragmentEntryVersion) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					fragmentEntryVersion.getCtCollectionId())) {

			entityCache.putResult(
				FragmentEntryVersionImpl.class,
				fragmentEntryVersion.getPrimaryKey(), fragmentEntryVersion);

			finderCache.putResult(
				_finderPathFetchByFragmentEntryId_Version,
				new Object[] {
					fragmentEntryVersion.getFragmentEntryId(),
					fragmentEntryVersion.getVersion()
				},
				fragmentEntryVersion);

			finderCache.putResult(
				_finderPathFetchByUUID_G_Version,
				new Object[] {
					fragmentEntryVersion.getUuid(),
					fragmentEntryVersion.getGroupId(),
					fragmentEntryVersion.getVersion()
				},
				fragmentEntryVersion);

			finderCache.putResult(
				_finderPathFetchByG_FEK_Version,
				new Object[] {
					fragmentEntryVersion.getGroupId(),
					fragmentEntryVersion.getFragmentEntryKey(),
					fragmentEntryVersion.getVersion()
				},
				fragmentEntryVersion);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the fragment entry versions in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryVersions the fragment entry versions
	 */
	@Override
	public void cacheResult(List<FragmentEntryVersion> fragmentEntryVersions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (fragmentEntryVersions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FragmentEntryVersion fragmentEntryVersion :
				fragmentEntryVersions) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						fragmentEntryVersion.getCtCollectionId())) {

				if (entityCache.getResult(
						FragmentEntryVersionImpl.class,
						fragmentEntryVersion.getPrimaryKey()) == null) {

					cacheResult(fragmentEntryVersion);
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		FragmentEntryVersionModelImpl fragmentEntryVersionModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					fragmentEntryVersionModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				fragmentEntryVersionModelImpl.getFragmentEntryId(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.putResult(
				_finderPathFetchByFragmentEntryId_Version, args,
				fragmentEntryVersionModelImpl);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getUuid(),
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G_Version, args,
				fragmentEntryVersionModelImpl);

			args = new Object[] {
				fragmentEntryVersionModelImpl.getGroupId(),
				fragmentEntryVersionModelImpl.getFragmentEntryKey(),
				fragmentEntryVersionModelImpl.getVersion()
			};

			finderCache.putResult(
				_finderPathFetchByG_FEK_Version, args,
				fragmentEntryVersionModelImpl);
		}
	}

	/**
	 * Creates a new fragment entry version with the primary key. Does not add the fragment entry version to the database.
	 *
	 * @param fragmentEntryVersionId the primary key for the new fragment entry version
	 * @return the new fragment entry version
	 */
	@Override
	public FragmentEntryVersion create(long fragmentEntryVersionId) {
		FragmentEntryVersion fragmentEntryVersion =
			new FragmentEntryVersionImpl();

		fragmentEntryVersion.setNew(true);
		fragmentEntryVersion.setPrimaryKey(fragmentEntryVersionId);

		fragmentEntryVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return fragmentEntryVersion;
	}

	/**
	 * Removes the fragment entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryVersionId the primary key of the fragment entry version
	 * @return the fragment entry version that was removed
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion remove(long fragmentEntryVersionId)
		throws NoSuchEntryVersionException {

		return remove((Serializable)fragmentEntryVersionId);
	}

	@Override
	protected FragmentEntryVersion removeImpl(
		FragmentEntryVersion fragmentEntryVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fragmentEntryVersion)) {
				fragmentEntryVersion = (FragmentEntryVersion)session.get(
					FragmentEntryVersionImpl.class,
					fragmentEntryVersion.getPrimaryKeyObj());
			}

			if ((fragmentEntryVersion != null) &&
				ctPersistenceHelper.isRemove(fragmentEntryVersion)) {

				session.delete(fragmentEntryVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntryVersion != null) {
			clearCache(fragmentEntryVersion);
		}

		return fragmentEntryVersion;
	}

	@Override
	public FragmentEntryVersion updateImpl(
		FragmentEntryVersion fragmentEntryVersion) {

		boolean isNew = fragmentEntryVersion.isNew();

		if (!(fragmentEntryVersion instanceof FragmentEntryVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fragmentEntryVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					fragmentEntryVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fragmentEntryVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FragmentEntryVersion implementation " +
					fragmentEntryVersion.getClass());
		}

		FragmentEntryVersionModelImpl fragmentEntryVersionModelImpl =
			(FragmentEntryVersionModelImpl)fragmentEntryVersion;

		if (Validator.isNull(fragmentEntryVersion.getExternalReferenceCode())) {
			fragmentEntryVersion.setExternalReferenceCode(
				String.valueOf(fragmentEntryVersion.getPrimaryKey()));
		}
		else {
			if (!Objects.equals(
					fragmentEntryVersionModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					fragmentEntryVersion.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = fragmentEntryVersion.getCompanyId();

					long groupId = fragmentEntryVersion.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = fragmentEntryVersion.getPrimaryKey();
					}

					try {
						fragmentEntryVersion.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								FragmentEntryVersion.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								fragmentEntryVersion.getExternalReferenceCode(),
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

		if (isNew && (fragmentEntryVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				fragmentEntryVersion.setCreateDate(date);
			}
			else {
				fragmentEntryVersion.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!fragmentEntryVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fragmentEntryVersion.setModifiedDate(date);
			}
			else {
				fragmentEntryVersion.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(fragmentEntryVersion)) {
				if (!isNew) {
					session.evict(
						FragmentEntryVersionImpl.class,
						fragmentEntryVersion.getPrimaryKeyObj());
				}

				session.save(fragmentEntryVersion);
			}
			else {
				throw new IllegalArgumentException(
					"FragmentEntryVersion is read only, create a new version instead");
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FragmentEntryVersionImpl.class, fragmentEntryVersionModelImpl,
			false, true);

		cacheUniqueFindersCache(fragmentEntryVersionModelImpl);

		if (isNew) {
			fragmentEntryVersion.setNew(false);
		}

		fragmentEntryVersion.resetOriginalValues();

		return fragmentEntryVersion;
	}

	/**
	 * Returns the fragment entry version with the primary key or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param fragmentEntryVersionId the primary key of the fragment entry version
	 * @return the fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion findByPrimaryKey(long fragmentEntryVersionId)
		throws NoSuchEntryVersionException {

		return findByPrimaryKey((Serializable)fragmentEntryVersionId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the fragment entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryVersionId the primary key of the fragment entry version
	 * @return the fragment entry version, or <code>null</code> if a fragment entry version with the primary key could not be found
	 */
	@Override
	public FragmentEntryVersion fetchByPrimaryKey(long fragmentEntryVersionId) {
		return fetchByPrimaryKey((Serializable)fragmentEntryVersionId);
	}

	/**
	 * Returns all the fragment entry versions.
	 *
	 * @return the fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment entry versions
	 */
	@Override
	public List<FragmentEntryVersion> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryVersion> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

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

			List<FragmentEntryVersion> list = null;

			if (useFinderCache) {
				list = (List<FragmentEntryVersion>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_FRAGMENTENTRYVERSION);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_FRAGMENTENTRYVERSION;

					sql = sql.concat(
						FragmentEntryVersionModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<FragmentEntryVersion>)QueryUtil.list(
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
	 * Removes all the fragment entry versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FragmentEntryVersion fragmentEntryVersion : findAll()) {
			remove(fragmentEntryVersion);
		}
	}

	/**
	 * Returns the number of fragment entry versions.
	 *
	 * @return the number of fragment entry versions
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryVersion.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_FRAGMENTENTRYVERSION);

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
		return "fragmentEntryVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FRAGMENTENTRYVERSION;
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
		return FragmentEntryVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "FragmentEntryVersion";
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
		ctMergeColumnNames.add("version");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("externalReferenceCode");
		ctMergeColumnNames.add("fragmentEntryId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("fragmentCollectionId");
		ctMergeColumnNames.add("fragmentEntryKey");
		ctMergeColumnNames.add("name");
		ctMergeColumnNames.add("css");
		ctMergeColumnNames.add("html");
		ctMergeColumnNames.add("js");
		ctMergeColumnNames.add("cacheable");
		ctMergeColumnNames.add("configuration");
		ctMergeColumnNames.add("icon");
		ctMergeColumnNames.add("previewFileEntryId");
		ctMergeColumnNames.add("marketplace");
		ctMergeColumnNames.add("readOnly");
		ctMergeColumnNames.add("type_");
		ctMergeColumnNames.add("typeOptions");
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
			CTColumnResolutionType.PK,
			Collections.singleton("fragmentEntryVersionId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"fragmentEntryId", "version"});

		_uniqueIndexColumnNames.add(
			new String[] {"uuid_", "groupId", "version"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "fragmentEntryKey", "version"});
	}

	/**
	 * Initializes the fragment entry version persistence.
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

		_finderPathWithPaginationFindByFragmentEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFragmentEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"fragmentEntryId"}, true);

		_finderPathWithoutPaginationFindByFragmentEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFragmentEntryId",
			new String[] {Long.class.getName()},
			new String[] {"fragmentEntryId"}, true);

		_finderPathCountByFragmentEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFragmentEntryId",
			new String[] {Long.class.getName()},
			new String[] {"fragmentEntryId"}, false);

		_collectionPersistenceFinderByFragmentEntryId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByFragmentEntryId,
				_finderPathWithoutPaginationFindByFragmentEntryId,
				_finderPathCountByFragmentEntryId,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentEntryId",
					FinderColumn.Type.LONG, "=", true, true,
					FragmentEntryVersion::getFragmentEntryId));

		_finderPathFetchByFragmentEntryId_Version = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByFragmentEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"fragmentEntryId", "version"}, true);

		_uniquePersistenceFinderByFragmentEntryId_Version =
			new UniquePersistenceFinder<>(
				this, _finderPathFetchByFragmentEntryId_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentEntryId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentEntryId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

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
			_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
			_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
			FragmentEntryVersionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"fragmentEntryVersion.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, FragmentEntryVersion::getUuid));

		_finderPathWithPaginationFindByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_Version",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "version"}, true);

		_finderPathWithoutPaginationFindByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"uuid_", "version"}, true);

		_finderPathCountByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"uuid_", "version"}, false);

		_collectionPersistenceFinderByUuid_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_Version,
				_finderPathWithoutPaginationFindByUuid_Version,
				_finderPathCountByUuid_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, FragmentEntryVersion::getUuid),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

		_finderPathWithPaginationFindByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUUID_G",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathWithoutPaginationFindByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_collectionPersistenceFinderByUUID_G =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUUID_G,
				_finderPathWithoutPaginationFindByUUID_G,
				_finderPathCountByUUID_G,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, FragmentEntryVersion::getUuid),
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, true, FragmentEntryVersion::getGroupId));

		_finderPathFetchByUUID_G_Version = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "groupId", "version"}, true);

		_uniquePersistenceFinderByUUID_G_Version =
			new UniquePersistenceFinder<>(
				this, _finderPathFetchByUUID_G_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				new FinderColumn<>(
					"fragmentEntryVersion.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, FragmentEntryVersion::getUuid),
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

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
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, FragmentEntryVersion::getUuid),
				new FinderColumn<>(
					"fragmentEntryVersion.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					FragmentEntryVersion::getCompanyId));

		_finderPathWithPaginationFindByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, true);

		_finderPathWithoutPaginationFindByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, true);

		_finderPathCountByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, false);

		_collectionPersistenceFinderByUuid_C_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByUuid_C_Version,
				_finderPathWithoutPaginationFindByUuid_C_Version,
				_finderPathCountByUuid_C_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "uuid", FinderColumn.Type.STRING,
					"=", true, false, FragmentEntryVersion::getUuid),
				new FinderColumn<>(
					"fragmentEntryVersion.", "companyId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getCompanyId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

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
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, true, FragmentEntryVersion::getGroupId));

		_finderPathWithPaginationFindByGroupId_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "version"}, true);

		_finderPathWithoutPaginationFindByGroupId_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "version"}, true);

		_finderPathCountByGroupId_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "version"}, false);

		_collectionPersistenceFinderByGroupId_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByGroupId_Version,
				_finderPathWithoutPaginationFindByGroupId_Version,
				_finderPathCountByGroupId_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

		_finderPathWithPaginationFindByFragmentCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByFragmentCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"fragmentCollectionId"}, true);

		_finderPathWithoutPaginationFindByFragmentCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByFragmentCollectionId", new String[] {Long.class.getName()},
			new String[] {"fragmentCollectionId"}, true);

		_finderPathCountByFragmentCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFragmentCollectionId", new String[] {Long.class.getName()},
			new String[] {"fragmentCollectionId"}, false);

		_collectionPersistenceFinderByFragmentCollectionId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByFragmentCollectionId,
				_finderPathWithoutPaginationFindByFragmentCollectionId,
				_finderPathCountByFragmentCollectionId,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, true,
					FragmentEntryVersion::getFragmentCollectionId));

		_finderPathWithPaginationFindByFragmentCollectionId_Version =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByFragmentCollectionId_Version",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				},
				new String[] {"fragmentCollectionId", "version"}, true);

		_finderPathWithoutPaginationFindByFragmentCollectionId_Version =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByFragmentCollectionId_Version",
				new String[] {Long.class.getName(), Integer.class.getName()},
				new String[] {"fragmentCollectionId", "version"}, true);

		_finderPathCountByFragmentCollectionId_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFragmentCollectionId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"fragmentCollectionId", "version"}, false);

		_collectionPersistenceFinderByFragmentCollectionId_Version =
			new CollectionPersistenceFinder<>(
				this,
				_finderPathWithPaginationFindByFragmentCollectionId_Version,
				_finderPathWithoutPaginationFindByFragmentCollectionId_Version,
				_finderPathCountByFragmentCollectionId_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

		_finderPathWithPaginationFindByType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByType",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"type_"}, true);

		_finderPathWithoutPaginationFindByType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByType",
			new String[] {Integer.class.getName()}, new String[] {"type_"},
			true);

		_finderPathCountByType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByType",
			new String[] {Integer.class.getName()}, new String[] {"type_"},
			false);

		_collectionPersistenceFinderByType = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByType,
			_finderPathWithoutPaginationFindByType, _finderPathCountByType,
			_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
			_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
			FragmentEntryVersionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"fragmentEntryVersion.", "type", FinderColumn.Type.INTEGER, "=",
				true, true, FragmentEntryVersion::getType));

		_finderPathWithPaginationFindByType_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByType_Version",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"type_", "version"}, true);

		_finderPathWithoutPaginationFindByType_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByType_Version",
			new String[] {Integer.class.getName(), Integer.class.getName()},
			new String[] {"type_", "version"}, true);

		_finderPathCountByType_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByType_Version",
			new String[] {Integer.class.getName(), Integer.class.getName()},
			new String[] {"type_", "version"}, false);

		_collectionPersistenceFinderByType_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByType_Version,
				_finderPathWithoutPaginationFindByType_Version,
				_finderPathCountByType_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "type", FinderColumn.Type.INTEGER,
					"=", true, false, FragmentEntryVersion::getType),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

		_finderPathWithPaginationFindByG_FCI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId"}, true);

		_finderPathWithoutPaginationFindByG_FCI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "fragmentCollectionId"}, true);

		_finderPathCountByG_FCI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "fragmentCollectionId"}, false);

		_collectionPersistenceFinderByG_FCI = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_FCI,
			_finderPathWithoutPaginationFindByG_FCI, _finderPathCountByG_FCI,
			_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
			_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
			FragmentEntryVersionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, FragmentEntryVersion::getGroupId),
			new FinderColumn<>(
				"fragmentEntryVersion.", "fragmentCollectionId",
				FinderColumn.Type.LONG, "=", true, true,
				FragmentEntryVersion::getFragmentCollectionId));

		_finderPathWithPaginationFindByG_FCI_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "version"}, true);

		_finderPathWithoutPaginationFindByG_FCI_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "version"}, true);

		_finderPathCountByG_FCI_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "version"}, false);

		_collectionPersistenceFinderByG_FCI_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_Version,
				_finderPathWithoutPaginationFindByG_FCI_Version,
				_finderPathCountByG_FCI_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

		_finderPathWithPaginationFindByG_FEK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FEK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentEntryKey"}, true);

		_finderPathWithoutPaginationFindByG_FEK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FEK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "fragmentEntryKey"}, true);

		_finderPathCountByG_FEK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FEK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "fragmentEntryKey"}, false);

		_collectionPersistenceFinderByG_FEK = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_FEK,
			_finderPathWithoutPaginationFindByG_FEK, _finderPathCountByG_FEK,
			_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
			_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
			FragmentEntryVersionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, FragmentEntryVersion::getGroupId),
			new FinderColumn<>(
				"fragmentEntryVersion.", "fragmentEntryKey",
				FinderColumn.Type.STRING, "=", true, true,
				FragmentEntryVersion::getFragmentEntryKey));

		_finderPathFetchByG_FEK_Version = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_FEK_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "fragmentEntryKey", "version"}, true);

		_uniquePersistenceFinderByG_FEK_Version = new UniquePersistenceFinder<>(
			this, _finderPathFetchByG_FEK_Version,
			_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
			new FinderColumn<>(
				"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, FragmentEntryVersion::getGroupId),
			new FinderColumn<>(
				"fragmentEntryVersion.", "fragmentEntryKey",
				FinderColumn.Type.STRING, "=", true, false,
				FragmentEntryVersion::getFragmentEntryKey),
			new FinderColumn<>(
				"fragmentEntryVersion.", "version", FinderColumn.Type.INTEGER,
				"=", true, true, FragmentEntryVersion::getVersion));

		_finderPathWithPaginationFindByG_FCI_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name"}, true);

		_finderPathWithoutPaginationFindByG_FCI_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name"}, true);

		_finderPathCountByG_FCI_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name"}, false);

		_collectionPersistenceFinderByG_FCI_LikeN =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_LikeN,
				_finderPathWithoutPaginationFindByG_FCI_LikeN,
				_finderPathCountByG_FCI_LikeN,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "name", FinderColumn.Type.STRING,
					"=", true, true, FragmentEntryVersion::getName));

		_finderPathWithPaginationFindByG_FCI_LikeN_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_LikeN_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name", "version"},
			true);

		_finderPathWithoutPaginationFindByG_FCI_LikeN_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI_LikeN_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name", "version"},
			true);

		_finderPathCountByG_FCI_LikeN_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_LikeN_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name", "version"},
			false);

		_collectionPersistenceFinderByG_FCI_LikeN_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_LikeN_Version,
				_finderPathWithoutPaginationFindByG_FCI_LikeN_Version,
				_finderPathCountByG_FCI_LikeN_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "name", FinderColumn.Type.STRING,
					"=", true, false, FragmentEntryVersion::getName),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

		_finderPathWithPaginationFindByG_FCI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "type_"}, true);

		_finderPathWithoutPaginationFindByG_FCI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "type_"}, true);

		_finderPathCountByG_FCI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "type_"}, false);

		_collectionPersistenceFinderByG_FCI_T =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_T,
				_finderPathWithoutPaginationFindByG_FCI_T,
				_finderPathCountByG_FCI_T,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "type", FinderColumn.Type.INTEGER,
					"=", true, true, FragmentEntryVersion::getType));

		_finderPathWithPaginationFindByG_FCI_T_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_T_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "type_", "version"
			},
			true);

		_finderPathWithoutPaginationFindByG_FCI_T_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_T_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "type_", "version"
			},
			true);

		_finderPathCountByG_FCI_T_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_T_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "type_", "version"
			},
			false);

		_collectionPersistenceFinderByG_FCI_T_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_T_Version,
				_finderPathWithoutPaginationFindByG_FCI_T_Version,
				_finderPathCountByG_FCI_T_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "type", FinderColumn.Type.INTEGER,
					"=", true, false, FragmentEntryVersion::getType),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

		_finderPathWithPaginationFindByG_FCI_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "status"}, true);

		_finderPathWithoutPaginationFindByG_FCI_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "status"}, true);

		_finderPathCountByG_FCI_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "status"}, false);

		_collectionPersistenceFinderByG_FCI_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_S,
				_finderPathWithoutPaginationFindByG_FCI_S,
				_finderPathCountByG_FCI_S,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "status",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getStatus));

		_finderPathWithPaginationFindByG_FCI_S_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "status", "version"
			},
			true);

		_finderPathWithoutPaginationFindByG_FCI_S_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "status", "version"
			},
			true);

		_finderPathCountByG_FCI_S_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "status", "version"
			},
			false);

		_collectionPersistenceFinderByG_FCI_S_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_S_Version,
				_finderPathWithoutPaginationFindByG_FCI_S_Version,
				_finderPathCountByG_FCI_S_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "status",
					FinderColumn.Type.INTEGER, "=", true, false,
					FragmentEntryVersion::getStatus),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

		_finderPathWithPaginationFindByG_FCI_LikeN_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name", "status"},
			true);

		_finderPathWithoutPaginationFindByG_FCI_LikeN_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name", "status"},
			true);

		_finderPathCountByG_FCI_LikeN_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "name", "status"},
			false);

		_collectionPersistenceFinderByG_FCI_LikeN_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_LikeN_S,
				_finderPathWithoutPaginationFindByG_FCI_LikeN_S,
				_finderPathCountByG_FCI_LikeN_S,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "name", FinderColumn.Type.STRING,
					"=", true, false, FragmentEntryVersion::getName),
				new FinderColumn<>(
					"fragmentEntryVersion.", "status",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getStatus));

		_finderPathWithPaginationFindByG_FCI_LikeN_S_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_LikeN_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "name", "status", "version"
			},
			true);

		_finderPathWithoutPaginationFindByG_FCI_LikeN_S_Version =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByG_FCI_LikeN_S_Version",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName(), Integer.class.getName(),
					Integer.class.getName()
				},
				new String[] {
					"groupId", "fragmentCollectionId", "name", "status",
					"version"
				},
				true);

		_finderPathCountByG_FCI_LikeN_S_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_LikeN_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "name", "status", "version"
			},
			false);

		_collectionPersistenceFinderByG_FCI_LikeN_S_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_LikeN_S_Version,
				_finderPathWithoutPaginationFindByG_FCI_LikeN_S_Version,
				_finderPathCountByG_FCI_LikeN_S_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "name", FinderColumn.Type.STRING,
					"=", true, false, FragmentEntryVersion::getName),
				new FinderColumn<>(
					"fragmentEntryVersion.", "status",
					FinderColumn.Type.INTEGER, "=", true, false,
					FragmentEntryVersion::getStatus),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

		_finderPathWithPaginationFindByG_FCI_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "type_", "status"},
			true);

		_finderPathWithoutPaginationFindByG_FCI_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FCI_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "type_", "status"},
			true);

		_finderPathCountByG_FCI_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FCI_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			new String[] {"groupId", "fragmentCollectionId", "type_", "status"},
			false);

		_collectionPersistenceFinderByG_FCI_T_S =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_T_S,
				_finderPathWithoutPaginationFindByG_FCI_T_S,
				_finderPathCountByG_FCI_T_S,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "type", FinderColumn.Type.INTEGER,
					"=", true, false, FragmentEntryVersion::getType),
				new FinderColumn<>(
					"fragmentEntryVersion.", "status",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getStatus));

		_finderPathWithPaginationFindByG_FCI_T_S_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FCI_T_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "type_", "status", "version"
			},
			true);

		_finderPathWithoutPaginationFindByG_FCI_T_S_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI_T_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "type_", "status", "version"
			},
			true);

		_finderPathCountByG_FCI_T_S_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_T_S_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			new String[] {
				"groupId", "fragmentCollectionId", "type_", "status", "version"
			},
			false);

		_collectionPersistenceFinderByG_FCI_T_S_Version =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FCI_T_S_Version,
				_finderPathWithoutPaginationFindByG_FCI_T_S_Version,
				_finderPathCountByG_FCI_T_S_Version,
				_SQL_SELECT_FRAGMENTENTRYVERSION_WHERE,
				_SQL_COUNT_FRAGMENTENTRYVERSION_WHERE,
				FragmentEntryVersionModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryVersion.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryVersion::getGroupId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "fragmentCollectionId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryVersion::getFragmentCollectionId),
				new FinderColumn<>(
					"fragmentEntryVersion.", "type", FinderColumn.Type.INTEGER,
					"=", true, false, FragmentEntryVersion::getType),
				new FinderColumn<>(
					"fragmentEntryVersion.", "status",
					FinderColumn.Type.INTEGER, "=", true, false,
					FragmentEntryVersion::getStatus),
				new FinderColumn<>(
					"fragmentEntryVersion.", "version",
					FinderColumn.Type.INTEGER, "=", true, true,
					FragmentEntryVersion::getVersion));

		FragmentEntryVersionUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FragmentEntryVersionUtil.setPersistence(null);

		entityCache.removeCache(FragmentEntryVersionImpl.class.getName());
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_FRAGMENTENTRYVERSION =
		"SELECT fragmentEntryVersion FROM FragmentEntryVersion fragmentEntryVersion";

	private static final String _SQL_SELECT_FRAGMENTENTRYVERSION_WHERE =
		"SELECT fragmentEntryVersion FROM FragmentEntryVersion fragmentEntryVersion WHERE ";

	private static final String _SQL_COUNT_FRAGMENTENTRYVERSION =
		"SELECT COUNT(fragmentEntryVersion) FROM FragmentEntryVersion fragmentEntryVersion";

	private static final String _SQL_COUNT_FRAGMENTENTRYVERSION_WHERE =
		"SELECT COUNT(fragmentEntryVersion) FROM FragmentEntryVersion fragmentEntryVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"fragmentEntryVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FragmentEntryVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:6443994