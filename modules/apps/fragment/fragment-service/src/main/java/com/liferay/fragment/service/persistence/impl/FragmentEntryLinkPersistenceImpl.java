/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.exception.DuplicateFragmentEntryLinkExternalReferenceCodeException;
import com.liferay.fragment.exception.NoSuchEntryLinkException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.model.FragmentEntryLinkTable;
import com.liferay.fragment.model.impl.FragmentEntryLinkImpl;
import com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl;
import com.liferay.fragment.service.persistence.FragmentEntryLinkPersistence;
import com.liferay.fragment.service.persistence.FragmentEntryLinkUtil;
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
import com.liferay.portal.kernel.dao.orm.QueryPos;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
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
 * The persistence implementation for the fragment entry link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = FragmentEntryLinkPersistence.class)
public class FragmentEntryLinkPersistenceImpl
	extends BasePersistenceImpl<FragmentEntryLink, NoSuchEntryLinkException>
	implements FragmentEntryLinkPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FragmentEntryLinkUtil</code> to access the fragment entry link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FragmentEntryLinkImpl.class.getName();

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
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByUuid;

	/**
	 * Returns all the fragment entry links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByUuid.find(
				finderCache, new Object[] {uuid}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByUuid_First(
			String uuid, OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByUuid_First(
			uuid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByUuid.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByUuid_First(
		String uuid, OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByUuid.fetchFirst(
			finderCache, new Object[] {uuid}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		_collectionPersistenceFinderByUuid.remove(
			finderCache, new Object[] {uuid});
	}

	/**
	 * Returns the number of fragment entry links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByUuid.count(
				finderCache, new Object[] {uuid});
		}
	}

	private FinderPath _finderPathFetchByUUID_G;
	private UniquePersistenceFinder<FragmentEntryLink>
		_uniquePersistenceFinderByUUID_G;

	/**
	 * Returns the fragment entry link where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryLinkException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByUUID_G(uuid, groupId);

		if (fragmentEntryLink == null) {
			String message =
				_uniquePersistenceFinderByUUID_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryLinkException(message);
		}

		return fragmentEntryLink;
	}

	/**
	 * Returns the fragment entry link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the fragment entry link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _uniquePersistenceFinderByUUID_G.fetch(
				finderCache, new Object[] {uuid, groupId}, useFinderCache);
		}
	}

	/**
	 * Removes the fragment entry link where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the fragment entry link that was removed
	 */
	@Override
	public FragmentEntryLink removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByUUID_G(uuid, groupId);

		return remove(fragmentEntryLink);
	}

	/**
	 * Returns the number of fragment entry links where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		return _uniquePersistenceFinderByUUID_G.count(
			finderCache, new Object[] {uuid, groupId});
	}

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByUuid_C;

	/**
	 * Returns all the fragment entry links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByUuid_C.find(
				finderCache, new Object[] {uuid, companyId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByUuid_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {uuid, companyId}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByUuid_C.fetchFirst(
			finderCache, new Object[] {uuid, companyId}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where uuid = &#63; and companyId = &#63; from the database.
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
	 * Returns the number of fragment entry links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByUuid_C.count(
				finderCache, new Object[] {uuid, companyId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByGroupId;

	/**
	 * Returns all the fragment entry links where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByGroupId.find(
				finderCache, new Object[] {groupId}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByGroupId_First(
			long groupId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByGroupId_First(
			groupId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByGroupId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByGroupId_First(
		long groupId, OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByGroupId.fetchFirst(
			finderCache, new Object[] {groupId}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		_collectionPersistenceFinderByGroupId.remove(
			finderCache, new Object[] {groupId});
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByGroupId(long groupId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByGroupId.count(
				finderCache, new Object[] {groupId});
		}
	}

	private FinderPath _finderPathWithPaginationFindByRendererKey;
	private FinderPath _finderPathWithoutPaginationFindByRendererKey;
	private FinderPath _finderPathCountByRendererKey;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByRendererKey;

	/**
	 * Returns all the fragment entry links where rendererKey = &#63;.
	 *
	 * @param rendererKey the renderer key
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByRendererKey(String rendererKey) {
		return findByRendererKey(
			rendererKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByRendererKey(
		String rendererKey, int start, int end) {

		return findByRendererKey(rendererKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByRendererKey(
		String rendererKey, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByRendererKey(
			rendererKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByRendererKey(
		String rendererKey, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByRendererKey.find(
				finderCache, new Object[] {rendererKey}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where rendererKey = &#63;.
	 *
	 * @param rendererKey the renderer key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByRendererKey_First(
			String rendererKey,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByRendererKey_First(
			rendererKey, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByRendererKey.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {rendererKey}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where rendererKey = &#63;.
	 *
	 * @param rendererKey the renderer key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByRendererKey_First(
		String rendererKey,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByRendererKey.fetchFirst(
			finderCache, new Object[] {rendererKey}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where rendererKey = &#63; from the database.
	 *
	 * @param rendererKey the renderer key
	 */
	@Override
	public void removeByRendererKey(String rendererKey) {
		_collectionPersistenceFinderByRendererKey.remove(
			finderCache, new Object[] {rendererKey});
	}

	/**
	 * Returns the number of fragment entry links where rendererKey = &#63;.
	 *
	 * @param rendererKey the renderer key
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByRendererKey(String rendererKey) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByRendererKey.count(
				finderCache, new Object[] {rendererKey});
		}
	}

	private FinderPath _finderPathWithPaginationFindByType;
	private FinderPath _finderPathWithoutPaginationFindByType;
	private FinderPath _finderPathCountByType;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByType;

	/**
	 * Returns all the fragment entry links where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByType(int type) {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByType(int type, int start, int end) {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByType(
		int type, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByType(type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByType(
		int type, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByType.find(
				finderCache, new Object[] {type}, start, end, orderByComparator,
				useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByType_First(
			int type, OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByType_First(
			type, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByType.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {type}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByType_First(
		int type, OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByType.fetchFirst(
			finderCache, new Object[] {type}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where type = &#63; from the database.
	 *
	 * @param type the type
	 */
	@Override
	public void removeByType(int type) {
		_collectionPersistenceFinderByType.remove(
			finderCache, new Object[] {type});
	}

	/**
	 * Returns the number of fragment entry links where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByType(int type) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByType.count(
				finderCache, new Object[] {type});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P;
	private FinderPath _finderPathCountByG_P;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByG_P;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P(long groupId, long plid) {
		return findByG_P(
			groupId, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P(
		long groupId, long plid, int start, int end) {

		return findByG_P(groupId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P(
		long groupId, long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_P(groupId, plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P(
		long groupId, long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_P.find(
				finderCache, new Object[] {groupId, plid}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_P_First(
			long groupId, long plid,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_P_First(
			groupId, plid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByG_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {groupId, plid}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_P_First(
		long groupId, long plid,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByG_P.fetchFirst(
			finderCache, new Object[] {groupId, plid}, orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and plid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 */
	@Override
	public void removeByG_P(long groupId, long plid) {
		_collectionPersistenceFinderByG_P.remove(
			finderCache, new Object[] {groupId, plid});
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_P(long groupId, long plid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_P.count(
				finderCache, new Object[] {groupId, plid});
		}
	}

	private FinderPath _finderPathWithPaginationFindByC_R;
	private FinderPath _finderPathWithoutPaginationFindByC_R;
	private FinderPath _finderPathCountByC_R;
	private FinderPath _finderPathWithPaginationCountByC_R;

	/**
	 * Returns all the fragment entry links where companyId = &#63; and rendererKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param rendererKey the renderer key
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByC_R(
		long companyId, String rendererKey) {

		return findByC_R(
			companyId, rendererKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where companyId = &#63; and rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByC_R(
		long companyId, String rendererKey, int start, int end) {

		return findByC_R(companyId, rendererKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where companyId = &#63; and rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByC_R(
		long companyId, String rendererKey, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByC_R(
			companyId, rendererKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where companyId = &#63; and rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByC_R(
		long companyId, String rendererKey, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			rendererKey = Objects.toString(rendererKey, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByC_R;
					finderArgs = new Object[] {companyId, rendererKey};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByC_R;
				finderArgs = new Object[] {
					companyId, rendererKey, start, end, orderByComparator
				};
			}

			List<FragmentEntryLink> list = null;

			if (useFinderCache) {
				list = (List<FragmentEntryLink>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (FragmentEntryLink fragmentEntryLink : list) {
						if ((companyId != fragmentEntryLink.getCompanyId()) ||
							!rendererKey.equals(
								fragmentEntryLink.getRendererKey())) {

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

				sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_C_R_COMPANYID_2);

				boolean bindRendererKey = false;

				if (rendererKey.isEmpty()) {
					sb.append(_FINDER_COLUMN_C_R_RENDERERKEY_3);
				}
				else {
					bindRendererKey = true;

					sb.append(_FINDER_COLUMN_C_R_RENDERERKEY_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					if (bindRendererKey) {
						queryPos.add(rendererKey);
					}

					list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where companyId = &#63; and rendererKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param rendererKey the renderer key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByC_R_First(
			long companyId, String rendererKey,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByC_R_First(
			companyId, rendererKey, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", rendererKey=");
		sb.append(rendererKey);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where companyId = &#63; and rendererKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param rendererKey the renderer key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByC_R_First(
		long companyId, String rendererKey,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByC_R(
			companyId, rendererKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the fragment entry links where companyId = &#63; and rendererKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param rendererKeys the renderer keys
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByC_R(
		long companyId, String[] rendererKeys) {

		return findByC_R(
			companyId, rendererKeys, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry links where companyId = &#63; and rendererKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param rendererKeys the renderer keys
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByC_R(
		long companyId, String[] rendererKeys, int start, int end) {

		return findByC_R(companyId, rendererKeys, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where companyId = &#63; and rendererKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param rendererKeys the renderer keys
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByC_R(
		long companyId, String[] rendererKeys, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByC_R(
			companyId, rendererKeys, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where companyId = &#63; and rendererKey = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param rendererKeys the renderer keys
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByC_R(
		long companyId, String[] rendererKeys, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		if (rendererKeys == null) {
			rendererKeys = new String[0];
		}
		else if (rendererKeys.length > 1) {
			for (int i = 0; i < rendererKeys.length; i++) {
				rendererKeys[i] = Objects.toString(rendererKeys[i], "");
			}

			rendererKeys = ArrayUtil.sortedUnique(rendererKeys);
		}

		if (rendererKeys.length == 1) {
			return findByC_R(
				companyId, rendererKeys[0], start, end, orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						companyId, StringUtil.merge(rendererKeys)
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, StringUtil.merge(rendererKeys), start, end,
					orderByComparator
				};
			}

			List<FragmentEntryLink> list = null;

			if (useFinderCache) {
				list = (List<FragmentEntryLink>)finderCache.getResult(
					_finderPathWithPaginationFindByC_R, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (FragmentEntryLink fragmentEntryLink : list) {
						if ((companyId != fragmentEntryLink.getCompanyId()) ||
							!ArrayUtil.contains(
								rendererKeys,
								fragmentEntryLink.getRendererKey())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_C_R_COMPANYID_2);

				if (rendererKeys.length > 0) {
					sb.append("(");

					for (int i = 0; i < rendererKeys.length; i++) {
						String rendererKey = rendererKeys[i];

						if (rendererKey.isEmpty()) {
							sb.append(_FINDER_COLUMN_C_R_RENDERERKEY_3);
						}
						else {
							sb.append(_FINDER_COLUMN_C_R_RENDERERKEY_2);
						}

						if ((i + 1) < rendererKeys.length) {
							sb.append(WHERE_OR);
						}
					}

					sb.append(")");
				}

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					for (String rendererKey : rendererKeys) {
						if ((rendererKey != null) && !rendererKey.isEmpty()) {
							queryPos.add(rendererKey);
						}
					}

					list = (List<FragmentEntryLink>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByC_R, finderArgs,
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
	 * Removes all the fragment entry links where companyId = &#63; and rendererKey = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param rendererKey the renderer key
	 */
	@Override
	public void removeByC_R(long companyId, String rendererKey) {
		for (FragmentEntryLink fragmentEntryLink :
				findByC_R(
					companyId, rendererKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where companyId = &#63; and rendererKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param rendererKey the renderer key
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByC_R(long companyId, String rendererKey) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			rendererKey = Objects.toString(rendererKey, "");

			FinderPath finderPath = _finderPathCountByC_R;

			Object[] finderArgs = new Object[] {companyId, rendererKey};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_C_R_COMPANYID_2);

				boolean bindRendererKey = false;

				if (rendererKey.isEmpty()) {
					sb.append(_FINDER_COLUMN_C_R_RENDERERKEY_3);
				}
				else {
					bindRendererKey = true;

					sb.append(_FINDER_COLUMN_C_R_RENDERERKEY_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					if (bindRendererKey) {
						queryPos.add(rendererKey);
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
	 * Returns the number of fragment entry links where companyId = &#63; and rendererKey = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param rendererKeys the renderer keys
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByC_R(long companyId, String[] rendererKeys) {
		if (rendererKeys == null) {
			rendererKeys = new String[0];
		}
		else if (rendererKeys.length > 1) {
			for (int i = 0; i < rendererKeys.length; i++) {
				rendererKeys[i] = Objects.toString(rendererKeys[i], "");
			}

			rendererKeys = ArrayUtil.sortedUnique(rendererKeys);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			Object[] finderArgs = new Object[] {
				companyId, StringUtil.merge(rendererKeys)
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByC_R, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_C_R_COMPANYID_2);

				if (rendererKeys.length > 0) {
					sb.append("(");

					for (int i = 0; i < rendererKeys.length; i++) {
						String rendererKey = rendererKeys[i];

						if (rendererKey.isEmpty()) {
							sb.append(_FINDER_COLUMN_C_R_RENDERERKEY_3);
						}
						else {
							sb.append(_FINDER_COLUMN_C_R_RENDERERKEY_2);
						}

						if ((i + 1) < rendererKeys.length) {
							sb.append(WHERE_OR);
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

					queryPos.add(companyId);

					for (String rendererKey : rendererKeys) {
						if ((rendererKey != null) && !rendererKey.isEmpty()) {
							queryPos.add(rendererKey);
						}
					}

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByC_R, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_R_COMPANYID_2 =
		"fragmentEntryLink.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_R_RENDERERKEY_2 =
		"fragmentEntryLink.rendererKey = ?";

	private static final String _FINDER_COLUMN_C_R_RENDERERKEY_3 =
		"(fragmentEntryLink.rendererKey IS NULL OR fragmentEntryLink.rendererKey = '')";

	private FinderPath _finderPathWithPaginationFindByFEERC_FESERC;
	private FinderPath _finderPathWithoutPaginationFindByFEERC_FESERC;
	private FinderPath _finderPathCountByFEERC_FESERC;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByFEERC_FESERC;

	/**
	 * Returns all the fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFEERC_FESERC(
		String fragmentEntryERC, String fragmentEntryScopeERC) {

		return findByFEERC_FESERC(
			fragmentEntryERC, fragmentEntryScopeERC, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFEERC_FESERC(
		String fragmentEntryERC, String fragmentEntryScopeERC, int start,
		int end) {

		return findByFEERC_FESERC(
			fragmentEntryERC, fragmentEntryScopeERC, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFEERC_FESERC(
		String fragmentEntryERC, String fragmentEntryScopeERC, int start,
		int end, OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByFEERC_FESERC(
			fragmentEntryERC, fragmentEntryScopeERC, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFEERC_FESERC(
		String fragmentEntryERC, String fragmentEntryScopeERC, int start,
		int end, OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByFEERC_FESERC.find(
				finderCache,
				new Object[] {fragmentEntryERC, fragmentEntryScopeERC}, start,
				end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByFEERC_FESERC_First(
			String fragmentEntryERC, String fragmentEntryScopeERC,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByFEERC_FESERC_First(
			fragmentEntryERC, fragmentEntryScopeERC, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByFEERC_FESERC.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {fragmentEntryERC, fragmentEntryScopeERC}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByFEERC_FESERC_First(
		String fragmentEntryERC, String fragmentEntryScopeERC,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByFEERC_FESERC.fetchFirst(
			finderCache, new Object[] {fragmentEntryERC, fragmentEntryScopeERC},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; from the database.
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 */
	@Override
	public void removeByFEERC_FESERC(
		String fragmentEntryERC, String fragmentEntryScopeERC) {

		_collectionPersistenceFinderByFEERC_FESERC.remove(
			finderCache,
			new Object[] {fragmentEntryERC, fragmentEntryScopeERC});
	}

	/**
	 * Returns the number of fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByFEERC_FESERC(
		String fragmentEntryERC, String fragmentEntryScopeERC) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByFEERC_FESERC.count(
				finderCache,
				new Object[] {fragmentEntryERC, fragmentEntryScopeERC});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_OFELERC_P;
	private FinderPath _finderPathWithoutPaginationFindByG_OFELERC_P;
	private FinderPath _finderPathCountByG_OFELERC_P;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByG_OFELERC_P;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and originalFragmentEntryLinkERC = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param originalFragmentEntryLinkERC the original fragment entry link erc
	 * @param plid the plid
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_OFELERC_P(
		long groupId, String originalFragmentEntryLinkERC, long plid) {

		return findByG_OFELERC_P(
			groupId, originalFragmentEntryLinkERC, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and originalFragmentEntryLinkERC = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param originalFragmentEntryLinkERC the original fragment entry link erc
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_OFELERC_P(
		long groupId, String originalFragmentEntryLinkERC, long plid, int start,
		int end) {

		return findByG_OFELERC_P(
			groupId, originalFragmentEntryLinkERC, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and originalFragmentEntryLinkERC = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param originalFragmentEntryLinkERC the original fragment entry link erc
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_OFELERC_P(
		long groupId, String originalFragmentEntryLinkERC, long plid, int start,
		int end, OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_OFELERC_P(
			groupId, originalFragmentEntryLinkERC, plid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and originalFragmentEntryLinkERC = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param originalFragmentEntryLinkERC the original fragment entry link erc
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_OFELERC_P(
		long groupId, String originalFragmentEntryLinkERC, long plid, int start,
		int end, OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_OFELERC_P.find(
				finderCache,
				new Object[] {groupId, originalFragmentEntryLinkERC, plid},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and originalFragmentEntryLinkERC = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param originalFragmentEntryLinkERC the original fragment entry link erc
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_OFELERC_P_First(
			long groupId, String originalFragmentEntryLinkERC, long plid,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_OFELERC_P_First(
			groupId, originalFragmentEntryLinkERC, plid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByG_OFELERC_P.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, originalFragmentEntryLinkERC, plid}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and originalFragmentEntryLinkERC = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param originalFragmentEntryLinkERC the original fragment entry link erc
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_OFELERC_P_First(
		long groupId, String originalFragmentEntryLinkERC, long plid,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByG_OFELERC_P.fetchFirst(
			finderCache,
			new Object[] {groupId, originalFragmentEntryLinkERC, plid},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and originalFragmentEntryLinkERC = &#63; and plid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param originalFragmentEntryLinkERC the original fragment entry link erc
	 * @param plid the plid
	 */
	@Override
	public void removeByG_OFELERC_P(
		long groupId, String originalFragmentEntryLinkERC, long plid) {

		_collectionPersistenceFinderByG_OFELERC_P.remove(
			finderCache,
			new Object[] {groupId, originalFragmentEntryLinkERC, plid});
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and originalFragmentEntryLinkERC = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param originalFragmentEntryLinkERC the original fragment entry link erc
	 * @param plid the plid
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_OFELERC_P(
		long groupId, String originalFragmentEntryLinkERC, long plid) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_OFELERC_P.count(
				finderCache,
				new Object[] {groupId, originalFragmentEntryLinkERC, plid});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FEERC_FESERC;
	private FinderPath _finderPathWithoutPaginationFindByG_FEERC_FESERC;
	private FinderPath _finderPathCountByG_FEERC_FESERC;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByG_FEERC_FESERC;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC) {

		return findByG_FEERC_FESERC(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		int start, int end) {

		return findByG_FEERC_FESERC(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_FEERC_FESERC(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_FEERC_FESERC.find(
				finderCache,
				new Object[] {groupId, fragmentEntryERC, fragmentEntryScopeERC},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_FEERC_FESERC_First(
			long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_FEERC_FESERC_First(
			groupId, fragmentEntryERC, fragmentEntryScopeERC,
			orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByG_FEERC_FESERC.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {
					groupId, fragmentEntryERC, fragmentEntryScopeERC
				}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_FEERC_FESERC_First(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByG_FEERC_FESERC.fetchFirst(
			finderCache,
			new Object[] {groupId, fragmentEntryERC, fragmentEntryScopeERC},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 */
	@Override
	public void removeByG_FEERC_FESERC(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC) {

		_collectionPersistenceFinderByG_FEERC_FESERC.remove(
			finderCache,
			new Object[] {groupId, fragmentEntryERC, fragmentEntryScopeERC});
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_FEERC_FESERC(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_FEERC_FESERC.count(
				finderCache,
				new Object[] {
					groupId, fragmentEntryERC, fragmentEntryScopeERC
				});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_S_P;
	private FinderPath _finderPathWithoutPaginationFindByG_S_P;
	private FinderPath _finderPathCountByG_S_P;
	private FinderPath _finderPathWithPaginationCountByG_S_P;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long segmentsExperienceId, long plid) {

		return findByG_S_P(
			groupId, segmentsExperienceId, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long segmentsExperienceId, long plid, int start,
		int end) {

		return findByG_S_P(
			groupId, segmentsExperienceId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long segmentsExperienceId, long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_S_P(
			groupId, segmentsExperienceId, plid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long segmentsExperienceId, long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_S_P;
					finderArgs = new Object[] {
						groupId, segmentsExperienceId, plid
					};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_S_P;
				finderArgs = new Object[] {
					groupId, segmentsExperienceId, plid, start, end,
					orderByComparator
				};
			}

			List<FragmentEntryLink> list = null;

			if (useFinderCache) {
				list = (List<FragmentEntryLink>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (FragmentEntryLink fragmentEntryLink : list) {
						if ((groupId != fragmentEntryLink.getGroupId()) ||
							(segmentsExperienceId !=
								fragmentEntryLink.getSegmentsExperienceId()) ||
							(plid != fragmentEntryLink.getPlid())) {

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

				sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_G_S_P_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_S_P_SEGMENTSEXPERIENCEID_2);

				sb.append(_FINDER_COLUMN_G_S_P_PLID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(segmentsExperienceId);

					queryPos.add(plid);

					list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_S_P_First(
			long groupId, long segmentsExperienceId, long plid,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_S_P_First(
			groupId, segmentsExperienceId, plid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", segmentsExperienceId=");
		sb.append(segmentsExperienceId);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_S_P_First(
		long groupId, long segmentsExperienceId, long plid,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByG_S_P(
			groupId, segmentsExperienceId, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the fragment entry links where groupId = &#63; and segmentsExperienceId = any &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param plid the plid
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long[] segmentsExperienceIds, long plid) {

		return findByG_S_P(
			groupId, segmentsExperienceIds, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = any &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long[] segmentsExperienceIds, long plid, int start,
		int end) {

		return findByG_S_P(
			groupId, segmentsExperienceIds, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = any &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long[] segmentsExperienceIds, long plid, int start,
		int end, OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_S_P(
			groupId, segmentsExperienceIds, plid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long[] segmentsExperienceIds, long plid, int start,
		int end, OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		if (segmentsExperienceIds == null) {
			segmentsExperienceIds = new long[0];
		}
		else if (segmentsExperienceIds.length > 1) {
			segmentsExperienceIds = ArrayUtil.sortedUnique(
				segmentsExperienceIds);
		}

		if (segmentsExperienceIds.length == 1) {
			return findByG_S_P(
				groupId, segmentsExperienceIds[0], plid, start, end,
				orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						groupId, StringUtil.merge(segmentsExperienceIds), plid
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(segmentsExperienceIds), plid,
					start, end, orderByComparator
				};
			}

			List<FragmentEntryLink> list = null;

			if (useFinderCache) {
				list = (List<FragmentEntryLink>)finderCache.getResult(
					_finderPathWithPaginationFindByG_S_P, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (FragmentEntryLink fragmentEntryLink : list) {
						if ((groupId != fragmentEntryLink.getGroupId()) ||
							!ArrayUtil.contains(
								segmentsExperienceIds,
								fragmentEntryLink.getSegmentsExperienceId()) ||
							(plid != fragmentEntryLink.getPlid())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_G_S_P_GROUPID_2);

				if (segmentsExperienceIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_S_P_SEGMENTSEXPERIENCEID_7);

					sb.append(StringUtil.merge(segmentsExperienceIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_S_P_PLID_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(plid);

					list = (List<FragmentEntryLink>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_S_P, finderArgs,
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
	 * Removes all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 */
	@Override
	public void removeByG_S_P(
		long groupId, long segmentsExperienceId, long plid) {

		for (FragmentEntryLink fragmentEntryLink :
				findByG_S_P(
					groupId, segmentsExperienceId, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_S_P(
		long groupId, long segmentsExperienceId, long plid) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			FinderPath finderPath = _finderPathCountByG_S_P;

			Object[] finderArgs = new Object[] {
				groupId, segmentsExperienceId, plid
			};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_G_S_P_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_S_P_SEGMENTSEXPERIENCEID_2);

				sb.append(_FINDER_COLUMN_G_S_P_PLID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(segmentsExperienceId);

					queryPos.add(plid);

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
	 * Returns the number of fragment entry links where groupId = &#63; and segmentsExperienceId = any &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param plid the plid
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_S_P(
		long groupId, long[] segmentsExperienceIds, long plid) {

		if (segmentsExperienceIds == null) {
			segmentsExperienceIds = new long[0];
		}
		else if (segmentsExperienceIds.length > 1) {
			segmentsExperienceIds = ArrayUtil.sortedUnique(
				segmentsExperienceIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(segmentsExperienceIds), plid
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_S_P, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_G_S_P_GROUPID_2);

				if (segmentsExperienceIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_S_P_SEGMENTSEXPERIENCEID_7);

					sb.append(StringUtil.merge(segmentsExperienceIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_S_P_PLID_2);

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

					queryPos.add(plid);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_S_P, finderArgs,
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

	private static final String _FINDER_COLUMN_G_S_P_GROUPID_2 =
		"fragmentEntryLink.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_P_SEGMENTSEXPERIENCEID_2 =
		"fragmentEntryLink.segmentsExperienceId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_P_SEGMENTSEXPERIENCEID_7 =
		"fragmentEntryLink.segmentsExperienceId IN (";

	private static final String _FINDER_COLUMN_G_S_P_PLID_2 =
		"fragmentEntryLink.plid = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C;
	private FinderPath _finderPathCountByG_C_C;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByG_C_C;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_C_C(
		long groupId, long classNameId, long classPK) {

		return findByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_C_C.find(
				finderCache, new Object[] {groupId, classNameId, classPK},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByG_C_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, classNameId, classPK}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByG_C_C.fetchFirst(
			finderCache, new Object[] {groupId, classNameId, classPK},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
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
	 * Returns the number of fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_C_C.count(
				finderCache, new Object[] {groupId, classNameId, classPK});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_P_D;
	private FinderPath _finderPathWithoutPaginationFindByG_P_D;
	private FinderPath _finderPathCountByG_P_D;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByG_P_D;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P_D(
		long groupId, long plid, boolean deleted) {

		return findByG_P_D(
			groupId, plid, deleted, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P_D(
		long groupId, long plid, boolean deleted, int start, int end) {

		return findByG_P_D(groupId, plid, deleted, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P_D(
		long groupId, long plid, boolean deleted, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_P_D(
			groupId, plid, deleted, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P_D(
		long groupId, long plid, boolean deleted, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_P_D.find(
				finderCache, new Object[] {groupId, plid, deleted}, start, end,
				orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_P_D_First(
			long groupId, long plid, boolean deleted,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_P_D_First(
			groupId, plid, deleted, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByG_P_D.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {groupId, plid, deleted}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_P_D_First(
		long groupId, long plid, boolean deleted,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByG_P_D.fetchFirst(
			finderCache, new Object[] {groupId, plid, deleted},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and plid = &#63; and deleted = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param deleted the deleted
	 */
	@Override
	public void removeByG_P_D(long groupId, long plid, boolean deleted) {
		_collectionPersistenceFinderByG_P_D.remove(
			finderCache, new Object[] {groupId, plid, deleted});
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_P_D(long groupId, long plid, boolean deleted) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_P_D.count(
				finderCache, new Object[] {groupId, plid, deleted});
		}
	}

	private FinderPath _finderPathWithPaginationFindByFEERC_FESERC_D;
	private FinderPath _finderPathWithoutPaginationFindByFEERC_FESERC_D;
	private FinderPath _finderPathCountByFEERC_FESERC_D;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByFEERC_FESERC_D;

	/**
	 * Returns all the fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFEERC_FESERC_D(
		String fragmentEntryERC, String fragmentEntryScopeERC,
		boolean deleted) {

		return findByFEERC_FESERC_D(
			fragmentEntryERC, fragmentEntryScopeERC, deleted, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFEERC_FESERC_D(
		String fragmentEntryERC, String fragmentEntryScopeERC, boolean deleted,
		int start, int end) {

		return findByFEERC_FESERC_D(
			fragmentEntryERC, fragmentEntryScopeERC, deleted, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFEERC_FESERC_D(
		String fragmentEntryERC, String fragmentEntryScopeERC, boolean deleted,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByFEERC_FESERC_D(
			fragmentEntryERC, fragmentEntryScopeERC, deleted, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFEERC_FESERC_D(
		String fragmentEntryERC, String fragmentEntryScopeERC, boolean deleted,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByFEERC_FESERC_D.find(
				finderCache,
				new Object[] {fragmentEntryERC, fragmentEntryScopeERC, deleted},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByFEERC_FESERC_D_First(
			String fragmentEntryERC, String fragmentEntryScopeERC,
			boolean deleted,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByFEERC_FESERC_D_First(
			fragmentEntryERC, fragmentEntryScopeERC, deleted,
			orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByFEERC_FESERC_D.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {
					fragmentEntryERC, fragmentEntryScopeERC, deleted
				}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByFEERC_FESERC_D_First(
		String fragmentEntryERC, String fragmentEntryScopeERC, boolean deleted,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByFEERC_FESERC_D.fetchFirst(
			finderCache,
			new Object[] {fragmentEntryERC, fragmentEntryScopeERC, deleted},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63; from the database.
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 */
	@Override
	public void removeByFEERC_FESERC_D(
		String fragmentEntryERC, String fragmentEntryScopeERC,
		boolean deleted) {

		_collectionPersistenceFinderByFEERC_FESERC_D.remove(
			finderCache,
			new Object[] {fragmentEntryERC, fragmentEntryScopeERC, deleted});
	}

	/**
	 * Returns the number of fragment entry links where fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByFEERC_FESERC_D(
		String fragmentEntryERC, String fragmentEntryScopeERC,
		boolean deleted) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByFEERC_FESERC_D.count(
				finderCache,
				new Object[] {
					fragmentEntryERC, fragmentEntryScopeERC, deleted
				});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FEERC_FESERC_C;
	private FinderPath _finderPathWithoutPaginationFindByG_FEERC_FESERC_C;
	private FinderPath _finderPathCountByG_FEERC_FESERC_C;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByG_FEERC_FESERC_C;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId) {

		return findByG_FEERC_FESERC_C(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId, int start, int end) {

		return findByG_FEERC_FESERC_C(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_FEERC_FESERC_C(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_FEERC_FESERC_C.find(
				finderCache,
				new Object[] {
					groupId, fragmentEntryERC, fragmentEntryScopeERC,
					classNameId
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_FEERC_FESERC_C_First(
			long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
			long classNameId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_FEERC_FESERC_C_First(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId,
			orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByG_FEERC_FESERC_C.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						groupId, fragmentEntryERC, fragmentEntryScopeERC,
						classNameId
					}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_FEERC_FESERC_C_First(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByG_FEERC_FESERC_C.fetchFirst(
			finderCache,
			new Object[] {
				groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId
			},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_FEERC_FESERC_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId) {

		_collectionPersistenceFinderByG_FEERC_FESERC_C.remove(
			finderCache,
			new Object[] {
				groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId
			});
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_FEERC_FESERC_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_FEERC_FESERC_C.count(
				finderCache,
				new Object[] {
					groupId, fragmentEntryERC, fragmentEntryScopeERC,
					classNameId
				});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FEERC_FESERC_P;
	private FinderPath _finderPathWithoutPaginationFindByG_FEERC_FESERC_P;
	private FinderPath _finderPathCountByG_FEERC_FESERC_P;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByG_FEERC_FESERC_P;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param plid the plid
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_P(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long plid) {

		return findByG_FEERC_FESERC_P(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, plid,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_P(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long plid, int start, int end) {

		return findByG_FEERC_FESERC_P(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, plid, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_P(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_FEERC_FESERC_P(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, plid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_P(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_FEERC_FESERC_P.find(
				finderCache,
				new Object[] {
					groupId, fragmentEntryERC, fragmentEntryScopeERC, plid
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_FEERC_FESERC_P_First(
			long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
			long plid, OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_FEERC_FESERC_P_First(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, plid,
			orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByG_FEERC_FESERC_P.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						groupId, fragmentEntryERC, fragmentEntryScopeERC, plid
					}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_FEERC_FESERC_P_First(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long plid, OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByG_FEERC_FESERC_P.fetchFirst(
			finderCache,
			new Object[] {
				groupId, fragmentEntryERC, fragmentEntryScopeERC, plid
			},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and plid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param plid the plid
	 */
	@Override
	public void removeByG_FEERC_FESERC_P(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long plid) {

		_collectionPersistenceFinderByG_FEERC_FESERC_P.remove(
			finderCache,
			new Object[] {
				groupId, fragmentEntryERC, fragmentEntryScopeERC, plid
			});
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param plid the plid
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_FEERC_FESERC_P(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long plid) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_FEERC_FESERC_P.count(
				finderCache,
				new Object[] {
					groupId, fragmentEntryERC, fragmentEntryScopeERC, plid
				});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FEERC_FESERC_D;
	private FinderPath _finderPathWithoutPaginationFindByG_FEERC_FESERC_D;
	private FinderPath _finderPathCountByG_FEERC_FESERC_D;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByG_FEERC_FESERC_D;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_D(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		boolean deleted) {

		return findByG_FEERC_FESERC_D(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, deleted,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_D(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		boolean deleted, int start, int end) {

		return findByG_FEERC_FESERC_D(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, deleted, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_D(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		boolean deleted, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_FEERC_FESERC_D(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, deleted, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_D(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		boolean deleted, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_FEERC_FESERC_D.find(
				finderCache,
				new Object[] {
					groupId, fragmentEntryERC, fragmentEntryScopeERC, deleted
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_FEERC_FESERC_D_First(
			long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
			boolean deleted,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_FEERC_FESERC_D_First(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, deleted,
			orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByG_FEERC_FESERC_D.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						groupId, fragmentEntryERC, fragmentEntryScopeERC,
						deleted
					}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_FEERC_FESERC_D_First(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		boolean deleted,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByG_FEERC_FESERC_D.fetchFirst(
			finderCache,
			new Object[] {
				groupId, fragmentEntryERC, fragmentEntryScopeERC, deleted
			},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 */
	@Override
	public void removeByG_FEERC_FESERC_D(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		boolean deleted) {

		_collectionPersistenceFinderByG_FEERC_FESERC_D.remove(
			finderCache,
			new Object[] {
				groupId, fragmentEntryERC, fragmentEntryScopeERC, deleted
			});
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param deleted the deleted
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_FEERC_FESERC_D(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		boolean deleted) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_FEERC_FESERC_D.count(
				finderCache,
				new Object[] {
					groupId, fragmentEntryERC, fragmentEntryScopeERC, deleted
				});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_S_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_S_C_C;
	private FinderPath _finderPathCountByG_S_C_C;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByG_S_C_C;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId,
		long classPK) {

		return findByG_S_C_C(
			groupId, segmentsExperienceId, classNameId, classPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId, long classPK,
		int start, int end) {

		return findByG_S_C_C(
			groupId, segmentsExperienceId, classNameId, classPK, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId, long classPK,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_S_C_C(
			groupId, segmentsExperienceId, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId, long classPK,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_S_C_C.find(
				finderCache,
				new Object[] {
					groupId, segmentsExperienceId, classNameId, classPK
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_S_C_C_First(
			long groupId, long segmentsExperienceId, long classNameId,
			long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_S_C_C_First(
			groupId, segmentsExperienceId, classNameId, classPK,
			orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByG_S_C_C.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {
					groupId, segmentsExperienceId, classNameId, classPK
				}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_S_C_C_First(
		long groupId, long segmentsExperienceId, long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByG_S_C_C.fetchFirst(
			finderCache,
			new Object[] {groupId, segmentsExperienceId, classNameId, classPK},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId,
		long classPK) {

		_collectionPersistenceFinderByG_S_C_C.remove(
			finderCache,
			new Object[] {groupId, segmentsExperienceId, classNameId, classPK});
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId,
		long classPK) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_S_C_C.count(
				finderCache,
				new Object[] {
					groupId, segmentsExperienceId, classNameId, classPK
				});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_S_P_D;
	private FinderPath _finderPathWithoutPaginationFindByG_S_P_D;
	private FinderPath _finderPathCountByG_S_P_D;
	private FinderPath _finderPathWithPaginationCountByG_S_P_D;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_D(
		long groupId, long segmentsExperienceId, long plid, boolean deleted) {

		return findByG_S_P_D(
			groupId, segmentsExperienceId, plid, deleted, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_D(
		long groupId, long segmentsExperienceId, long plid, boolean deleted,
		int start, int end) {

		return findByG_S_P_D(
			groupId, segmentsExperienceId, plid, deleted, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_D(
		long groupId, long segmentsExperienceId, long plid, boolean deleted,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_S_P_D(
			groupId, segmentsExperienceId, plid, deleted, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_D(
		long groupId, long segmentsExperienceId, long plid, boolean deleted,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByG_S_P_D;
					finderArgs = new Object[] {
						groupId, segmentsExperienceId, plid, deleted
					};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByG_S_P_D;
				finderArgs = new Object[] {
					groupId, segmentsExperienceId, plid, deleted, start, end,
					orderByComparator
				};
			}

			List<FragmentEntryLink> list = null;

			if (useFinderCache) {
				list = (List<FragmentEntryLink>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (FragmentEntryLink fragmentEntryLink : list) {
						if ((groupId != fragmentEntryLink.getGroupId()) ||
							(segmentsExperienceId !=
								fragmentEntryLink.getSegmentsExperienceId()) ||
							(plid != fragmentEntryLink.getPlid()) ||
							(deleted != fragmentEntryLink.isDeleted())) {

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
						6 + (orderByComparator.getOrderByFields().length * 2));
				}
				else {
					sb = new StringBundler(6);
				}

				sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_G_S_P_D_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_S_P_D_SEGMENTSEXPERIENCEID_2);

				sb.append(_FINDER_COLUMN_G_S_P_D_PLID_2);

				sb.append(_FINDER_COLUMN_G_S_P_D_DELETED_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(segmentsExperienceId);

					queryPos.add(plid);

					queryPos.add(deleted);

					list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_S_P_D_First(
			long groupId, long segmentsExperienceId, long plid, boolean deleted,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_S_P_D_First(
			groupId, segmentsExperienceId, plid, deleted, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", segmentsExperienceId=");
		sb.append(segmentsExperienceId);

		sb.append(", plid=");
		sb.append(plid);

		sb.append(", deleted=");
		sb.append(deleted);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_S_P_D_First(
		long groupId, long segmentsExperienceId, long plid, boolean deleted,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByG_S_P_D(
			groupId, segmentsExperienceId, plid, deleted, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the fragment entry links where groupId = &#63; and segmentsExperienceId = any &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param plid the plid
	 * @param deleted the deleted
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_D(
		long groupId, long[] segmentsExperienceIds, long plid,
		boolean deleted) {

		return findByG_S_P_D(
			groupId, segmentsExperienceIds, plid, deleted, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = any &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_D(
		long groupId, long[] segmentsExperienceIds, long plid, boolean deleted,
		int start, int end) {

		return findByG_S_P_D(
			groupId, segmentsExperienceIds, plid, deleted, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = any &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_D(
		long groupId, long[] segmentsExperienceIds, long plid, boolean deleted,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_S_P_D(
			groupId, segmentsExperienceIds, plid, deleted, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and deleted = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param plid the plid
	 * @param deleted the deleted
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_D(
		long groupId, long[] segmentsExperienceIds, long plid, boolean deleted,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		if (segmentsExperienceIds == null) {
			segmentsExperienceIds = new long[0];
		}
		else if (segmentsExperienceIds.length > 1) {
			segmentsExperienceIds = ArrayUtil.sortedUnique(
				segmentsExperienceIds);
		}

		if (segmentsExperienceIds.length == 1) {
			return findByG_S_P_D(
				groupId, segmentsExperienceIds[0], plid, deleted, start, end,
				orderByComparator);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderArgs = new Object[] {
						groupId, StringUtil.merge(segmentsExperienceIds), plid,
						deleted
					};
				}
			}
			else if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(segmentsExperienceIds), plid,
					deleted, start, end, orderByComparator
				};
			}

			List<FragmentEntryLink> list = null;

			if (useFinderCache) {
				list = (List<FragmentEntryLink>)finderCache.getResult(
					_finderPathWithPaginationFindByG_S_P_D, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (FragmentEntryLink fragmentEntryLink : list) {
						if ((groupId != fragmentEntryLink.getGroupId()) ||
							!ArrayUtil.contains(
								segmentsExperienceIds,
								fragmentEntryLink.getSegmentsExperienceId()) ||
							(plid != fragmentEntryLink.getPlid()) ||
							(deleted != fragmentEntryLink.isDeleted())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_G_S_P_D_GROUPID_2);

				if (segmentsExperienceIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_S_P_D_SEGMENTSEXPERIENCEID_7);

					sb.append(StringUtil.merge(segmentsExperienceIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_S_P_D_PLID_2);

				sb.append(_FINDER_COLUMN_G_S_P_D_DELETED_2);

				sb.setStringAt(
					removeConjunction(sb.stringAt(sb.index() - 1)),
					sb.index() - 1);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(plid);

					queryPos.add(deleted);

					list = (List<FragmentEntryLink>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(
							_finderPathWithPaginationFindByG_S_P_D, finderArgs,
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
	 * Removes all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and deleted = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param deleted the deleted
	 */
	@Override
	public void removeByG_S_P_D(
		long groupId, long segmentsExperienceId, long plid, boolean deleted) {

		for (FragmentEntryLink fragmentEntryLink :
				findByG_S_P_D(
					groupId, segmentsExperienceId, plid, deleted,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param deleted the deleted
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_S_P_D(
		long groupId, long segmentsExperienceId, long plid, boolean deleted) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			FinderPath finderPath = _finderPathCountByG_S_P_D;

			Object[] finderArgs = new Object[] {
				groupId, segmentsExperienceId, plid, deleted
			};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(5);

				sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_G_S_P_D_GROUPID_2);

				sb.append(_FINDER_COLUMN_G_S_P_D_SEGMENTSEXPERIENCEID_2);

				sb.append(_FINDER_COLUMN_G_S_P_D_PLID_2);

				sb.append(_FINDER_COLUMN_G_S_P_D_DELETED_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(groupId);

					queryPos.add(segmentsExperienceId);

					queryPos.add(plid);

					queryPos.add(deleted);

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
	 * Returns the number of fragment entry links where groupId = &#63; and segmentsExperienceId = any &#63; and plid = &#63; and deleted = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param plid the plid
	 * @param deleted the deleted
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_S_P_D(
		long groupId, long[] segmentsExperienceIds, long plid,
		boolean deleted) {

		if (segmentsExperienceIds == null) {
			segmentsExperienceIds = new long[0];
		}
		else if (segmentsExperienceIds.length > 1) {
			segmentsExperienceIds = ArrayUtil.sortedUnique(
				segmentsExperienceIds);
		}

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(segmentsExperienceIds), plid, deleted
			};

			Long count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_S_P_D, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler();

				sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

				sb.append(_FINDER_COLUMN_G_S_P_D_GROUPID_2);

				if (segmentsExperienceIds.length > 0) {
					sb.append("(");

					sb.append(_FINDER_COLUMN_G_S_P_D_SEGMENTSEXPERIENCEID_7);

					sb.append(StringUtil.merge(segmentsExperienceIds));

					sb.append(")");

					sb.append(")");

					sb.append(WHERE_AND);
				}

				sb.append(_FINDER_COLUMN_G_S_P_D_PLID_2);

				sb.append(_FINDER_COLUMN_G_S_P_D_DELETED_2);

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

					queryPos.add(plid);

					queryPos.add(deleted);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
						_finderPathWithPaginationCountByG_S_P_D, finderArgs,
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

	private static final String _FINDER_COLUMN_G_S_P_D_GROUPID_2 =
		"fragmentEntryLink.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_P_D_SEGMENTSEXPERIENCEID_2 =
		"fragmentEntryLink.segmentsExperienceId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_P_D_SEGMENTSEXPERIENCEID_7 =
		"fragmentEntryLink.segmentsExperienceId IN (";

	private static final String _FINDER_COLUMN_G_S_P_D_PLID_2 =
		"fragmentEntryLink.plid = ? AND ";

	private static final String _FINDER_COLUMN_G_S_P_D_DELETED_2 =
		"fragmentEntryLink.deleted = ?";

	private FinderPath _finderPathWithPaginationFindByG_S_P_R;
	private FinderPath _finderPathWithoutPaginationFindByG_S_P_R;
	private FinderPath _finderPathCountByG_S_P_R;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByG_S_P_R;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and rendererKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param rendererKey the renderer key
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_R(
		long groupId, long segmentsExperienceId, long plid,
		String rendererKey) {

		return findByG_S_P_R(
			groupId, segmentsExperienceId, plid, rendererKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_R(
		long groupId, long segmentsExperienceId, long plid, String rendererKey,
		int start, int end) {

		return findByG_S_P_R(
			groupId, segmentsExperienceId, plid, rendererKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_R(
		long groupId, long segmentsExperienceId, long plid, String rendererKey,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_S_P_R(
			groupId, segmentsExperienceId, plid, rendererKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P_R(
		long groupId, long segmentsExperienceId, long plid, String rendererKey,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_S_P_R.find(
				finderCache,
				new Object[] {groupId, segmentsExperienceId, plid, rendererKey},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and rendererKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param rendererKey the renderer key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_S_P_R_First(
			long groupId, long segmentsExperienceId, long plid,
			String rendererKey,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_S_P_R_First(
			groupId, segmentsExperienceId, plid, rendererKey,
			orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByG_S_P_R.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {
					groupId, segmentsExperienceId, plid, rendererKey
				}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and rendererKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param rendererKey the renderer key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_S_P_R_First(
		long groupId, long segmentsExperienceId, long plid, String rendererKey,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByG_S_P_R.fetchFirst(
			finderCache,
			new Object[] {groupId, segmentsExperienceId, plid, rendererKey},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and rendererKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param rendererKey the renderer key
	 */
	@Override
	public void removeByG_S_P_R(
		long groupId, long segmentsExperienceId, long plid,
		String rendererKey) {

		_collectionPersistenceFinderByG_S_P_R.remove(
			finderCache,
			new Object[] {groupId, segmentsExperienceId, plid, rendererKey});
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; and rendererKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param rendererKey the renderer key
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_S_P_R(
		long groupId, long segmentsExperienceId, long plid,
		String rendererKey) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_S_P_R.count(
				finderCache,
				new Object[] {
					groupId, segmentsExperienceId, plid, rendererKey
				});
		}
	}

	private FinderPath _finderPathWithPaginationFindByG_FEERC_FESERC_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_FEERC_FESERC_C_C;
	private FinderPath _finderPathCountByG_FEERC_FESERC_C_C;
	private CollectionPersistenceFinder<FragmentEntryLink>
		_collectionPersistenceFinderByG_FEERC_FESERC_C_C;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_C_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId, long classPK) {

		return findByG_FEERC_FESERC_C_C(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId,
			classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_C_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId, long classPK, int start, int end) {

		return findByG_FEERC_FESERC_C_C(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId,
			classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_C_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_FEERC_FESERC_C_C(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId,
			classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_FEERC_FESERC_C_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_FEERC_FESERC_C_C.find(
				finderCache,
				new Object[] {
					groupId, fragmentEntryERC, fragmentEntryScopeERC,
					classNameId, classPK
				},
				start, end, orderByComparator, useFinderCache);
		}
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_FEERC_FESERC_C_C_First(
			long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
			long classNameId, long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_FEERC_FESERC_C_C_First(
			groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId,
			classPK, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		throw new NoSuchEntryLinkException(
			_collectionPersistenceFinderByG_FEERC_FESERC_C_C.
				buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {
						groupId, fragmentEntryERC, fragmentEntryScopeERC,
						classNameId, classPK
					}));
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_FEERC_FESERC_C_C_First(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return _collectionPersistenceFinderByG_FEERC_FESERC_C_C.fetchFirst(
			finderCache,
			new Object[] {
				groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId,
				classPK
			},
			orderByComparator);
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_FEERC_FESERC_C_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId, long classPK) {

		_collectionPersistenceFinderByG_FEERC_FESERC_C_C.remove(
			finderCache,
			new Object[] {
				groupId, fragmentEntryERC, fragmentEntryScopeERC, classNameId,
				classPK
			});
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and fragmentEntryERC = &#63; and fragmentEntryScopeERC = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryERC the fragment entry erc
	 * @param fragmentEntryScopeERC the fragment entry scope erc
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_FEERC_FESERC_C_C(
		long groupId, String fragmentEntryERC, String fragmentEntryScopeERC,
		long classNameId, long classPK) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _collectionPersistenceFinderByG_FEERC_FESERC_C_C.count(
				finderCache,
				new Object[] {
					groupId, fragmentEntryERC, fragmentEntryScopeERC,
					classNameId, classPK
				});
		}
	}

	private FinderPath _finderPathFetchByERC_G;
	private UniquePersistenceFinder<FragmentEntryLink>
		_uniquePersistenceFinderByERC_G;

	/**
	 * Returns the fragment entry link where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchEntryLinkException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByERC_G(
			externalReferenceCode, groupId);

		if (fragmentEntryLink == null) {
			String message =
				_uniquePersistenceFinderByERC_G.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {externalReferenceCode, groupId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchEntryLinkException(message);
		}

		return fragmentEntryLink;
	}

	/**
	 * Returns the fragment entry link where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByERC_G(
		String externalReferenceCode, long groupId) {

		return fetchByERC_G(externalReferenceCode, groupId, true);
	}

	/**
	 * Returns the fragment entry link where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			return _uniquePersistenceFinderByERC_G.fetch(
				finderCache, new Object[] {externalReferenceCode, groupId},
				useFinderCache);
		}
	}

	/**
	 * Removes the fragment entry link where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the fragment entry link that was removed
	 */
	@Override
	public FragmentEntryLink removeByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByERC_G(
			externalReferenceCode, groupId);

		return remove(fragmentEntryLink);
	}

	/**
	 * Returns the number of fragment entry links where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByERC_G(String externalReferenceCode, long groupId) {
		return _uniquePersistenceFinderByERC_G.count(
			finderCache, new Object[] {externalReferenceCode, groupId});
	}

	public FragmentEntryLinkPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FragmentEntryLink.class);

		setModelImplClass(FragmentEntryLinkImpl.class);
		setModelPKClass(long.class);

		setTable(FragmentEntryLinkTable.INSTANCE);
	}

	/**
	 * Caches the fragment entry link in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryLink the fragment entry link
	 */
	@Override
	public void cacheResult(FragmentEntryLink fragmentEntryLink) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					fragmentEntryLink.getCtCollectionId())) {

			entityCache.putResult(
				FragmentEntryLinkImpl.class, fragmentEntryLink.getPrimaryKey(),
				fragmentEntryLink);

			finderCache.putResult(
				_finderPathFetchByUUID_G,
				new Object[] {
					fragmentEntryLink.getUuid(), fragmentEntryLink.getGroupId()
				},
				fragmentEntryLink);

			finderCache.putResult(
				_finderPathFetchByERC_G,
				new Object[] {
					fragmentEntryLink.getExternalReferenceCode(),
					fragmentEntryLink.getGroupId()
				},
				fragmentEntryLink);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the fragment entry links in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryLinks the fragment entry links
	 */
	@Override
	public void cacheResult(List<FragmentEntryLink> fragmentEntryLinks) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (fragmentEntryLinks.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						fragmentEntryLink.getCtCollectionId())) {

				FragmentEntryLink cachedFragmentEntryLink =
					(FragmentEntryLink)entityCache.getResult(
						FragmentEntryLinkImpl.class,
						fragmentEntryLink.getPrimaryKey());

				if (cachedFragmentEntryLink == null) {
					cacheResult(fragmentEntryLink);
				}
				else {
					FragmentEntryLinkModelImpl fragmentEntryLinkModelImpl =
						(FragmentEntryLinkModelImpl)fragmentEntryLink;
					FragmentEntryLinkModelImpl
						cachedFragmentEntryLinkModelImpl =
							(FragmentEntryLinkModelImpl)cachedFragmentEntryLink;

					fragmentEntryLinkModelImpl.setConfigurationJSONObject(
						cachedFragmentEntryLinkModelImpl.
							getConfigurationJSONObject());

					fragmentEntryLinkModelImpl.setEditableValuesJSONObject(
						cachedFragmentEntryLinkModelImpl.
							getEditableValuesJSONObject());

					fragmentEntryLinkModelImpl.setFragmentEntry(
						cachedFragmentEntryLinkModelImpl.getFragmentEntry());
				}
			}
		}
	}

	protected void cacheUniqueFindersCache(
		FragmentEntryLinkModelImpl fragmentEntryLinkModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					fragmentEntryLinkModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				fragmentEntryLinkModelImpl.getUuid(),
				fragmentEntryLinkModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByUUID_G, args, fragmentEntryLinkModelImpl);

			args = new Object[] {
				fragmentEntryLinkModelImpl.getExternalReferenceCode(),
				fragmentEntryLinkModelImpl.getGroupId()
			};

			finderCache.putResult(
				_finderPathFetchByERC_G, args, fragmentEntryLinkModelImpl);
		}
	}

	/**
	 * Creates a new fragment entry link with the primary key. Does not add the fragment entry link to the database.
	 *
	 * @param fragmentEntryLinkId the primary key for the new fragment entry link
	 * @return the new fragment entry link
	 */
	@Override
	public FragmentEntryLink create(long fragmentEntryLinkId) {
		FragmentEntryLink fragmentEntryLink = new FragmentEntryLinkImpl();

		fragmentEntryLink.setNew(true);
		fragmentEntryLink.setPrimaryKey(fragmentEntryLinkId);

		String uuid = PortalUUIDUtil.generate();

		fragmentEntryLink.setUuid(uuid);

		fragmentEntryLink.setCompanyId(CompanyThreadLocal.getCompanyId());

		return fragmentEntryLink;
	}

	/**
	 * Removes the fragment entry link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryLinkId the primary key of the fragment entry link
	 * @return the fragment entry link that was removed
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink remove(long fragmentEntryLinkId)
		throws NoSuchEntryLinkException {

		return remove((Serializable)fragmentEntryLinkId);
	}

	@Override
	protected FragmentEntryLink removeImpl(
		FragmentEntryLink fragmentEntryLink) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fragmentEntryLink)) {
				fragmentEntryLink = (FragmentEntryLink)session.get(
					FragmentEntryLinkImpl.class,
					fragmentEntryLink.getPrimaryKeyObj());
			}

			if ((fragmentEntryLink != null) &&
				ctPersistenceHelper.isRemove(fragmentEntryLink)) {

				session.delete(fragmentEntryLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntryLink != null) {
			clearCache(fragmentEntryLink);
		}

		return fragmentEntryLink;
	}

	@Override
	public FragmentEntryLink updateImpl(FragmentEntryLink fragmentEntryLink) {
		boolean isNew = fragmentEntryLink.isNew();

		if (!(fragmentEntryLink instanceof FragmentEntryLinkModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fragmentEntryLink.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					fragmentEntryLink);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fragmentEntryLink proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FragmentEntryLink implementation " +
					fragmentEntryLink.getClass());
		}

		FragmentEntryLinkModelImpl fragmentEntryLinkModelImpl =
			(FragmentEntryLinkModelImpl)fragmentEntryLink;

		if (Validator.isNull(fragmentEntryLink.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			fragmentEntryLink.setUuid(uuid);
		}

		if (Validator.isNull(fragmentEntryLink.getExternalReferenceCode())) {
			fragmentEntryLink.setExternalReferenceCode(
				fragmentEntryLink.getUuid());
		}
		else {
			if (!Objects.equals(
					fragmentEntryLinkModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					fragmentEntryLink.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = fragmentEntryLink.getCompanyId();

					long groupId = fragmentEntryLink.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = fragmentEntryLink.getPrimaryKey();
					}

					try {
						fragmentEntryLink.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								FragmentEntryLink.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								fragmentEntryLink.getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			FragmentEntryLink ercFragmentEntryLink = fetchByERC_G(
				fragmentEntryLink.getExternalReferenceCode(),
				fragmentEntryLink.getGroupId());

			if (isNew) {
				if (ercFragmentEntryLink != null) {
					throw new DuplicateFragmentEntryLinkExternalReferenceCodeException(
						"Duplicate fragment entry link with external reference code " +
							fragmentEntryLink.getExternalReferenceCode() +
								" and group " + fragmentEntryLink.getGroupId());
				}
			}
			else {
				if ((ercFragmentEntryLink != null) &&
					(fragmentEntryLink.getFragmentEntryLinkId() !=
						ercFragmentEntryLink.getFragmentEntryLinkId())) {

					throw new DuplicateFragmentEntryLinkExternalReferenceCodeException(
						"Duplicate fragment entry link with external reference code " +
							fragmentEntryLink.getExternalReferenceCode() +
								" and group " + fragmentEntryLink.getGroupId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (fragmentEntryLink.getCreateDate() == null)) {
			if (serviceContext == null) {
				fragmentEntryLink.setCreateDate(date);
			}
			else {
				fragmentEntryLink.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!fragmentEntryLinkModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fragmentEntryLink.setModifiedDate(date);
			}
			else {
				fragmentEntryLink.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(fragmentEntryLink)) {
				if (!isNew) {
					session.evict(
						FragmentEntryLinkImpl.class,
						fragmentEntryLink.getPrimaryKeyObj());
				}

				session.save(fragmentEntryLink);
			}
			else {
				fragmentEntryLink = (FragmentEntryLink)session.merge(
					fragmentEntryLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FragmentEntryLinkImpl.class, fragmentEntryLinkModelImpl, false,
			true);

		cacheUniqueFindersCache(fragmentEntryLinkModelImpl);

		if (isNew) {
			fragmentEntryLink.setNew(false);
		}

		fragmentEntryLink.resetOriginalValues();

		return fragmentEntryLink;
	}

	/**
	 * Returns the fragment entry link with the primary key or throws a <code>NoSuchEntryLinkException</code> if it could not be found.
	 *
	 * @param fragmentEntryLinkId the primary key of the fragment entry link
	 * @return the fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink findByPrimaryKey(long fragmentEntryLinkId)
		throws NoSuchEntryLinkException {

		return findByPrimaryKey((Serializable)fragmentEntryLinkId);
	}

	@Override
	protected CTPersistenceHelper getCTPersistenceHelper() {
		return ctPersistenceHelper;
	}

	/**
	 * Returns the fragment entry link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryLinkId the primary key of the fragment entry link
	 * @return the fragment entry link, or <code>null</code> if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink fetchByPrimaryKey(long fragmentEntryLinkId) {
		return fetchByPrimaryKey((Serializable)fragmentEntryLinkId);
	}

	/**
	 * Returns all the fragment entry links.
	 *
	 * @return the fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

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

			List<FragmentEntryLink> list = null;

			if (useFinderCache) {
				list = (List<FragmentEntryLink>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_FRAGMENTENTRYLINK);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_FRAGMENTENTRYLINK;

					sql = sql.concat(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Removes all the fragment entry links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FragmentEntryLink fragmentEntryLink : findAll()) {
			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links.
	 *
	 * @return the number of fragment entry links
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					FragmentEntryLink.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_FRAGMENTENTRYLINK);

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
		return "fragmentEntryLinkId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FRAGMENTENTRYLINK;
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
		return FragmentEntryLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "FragmentEntryLink";
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
		ctMergeColumnNames.add("originalFragmentEntryLinkERC");
		ctMergeColumnNames.add("fragmentEntryERC");
		ctMergeColumnNames.add("fragmentEntryScopeERC");
		ctMergeColumnNames.add("segmentsExperienceId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctMergeColumnNames.add("plid");
		ctMergeColumnNames.add("css");
		ctMergeColumnNames.add("html");
		ctMergeColumnNames.add("js");
		ctMergeColumnNames.add("configuration");
		ctMergeColumnNames.add("deleted");
		ctMergeColumnNames.add("editableValues");
		ctMergeColumnNames.add("namespace");
		ctMergeColumnNames.add("position");
		ctMergeColumnNames.add("rendererKey");
		ctMergeColumnNames.add("type_");
		ctMergeColumnNames.add("lastPropagationDate");
		ctMergeColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("fragmentEntryLinkId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "groupId"});
	}

	/**
	 * Initializes the fragment entry link persistence.
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
			_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
			_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
			FragmentEntryLinkModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"fragmentEntryLink.", "uuid", FinderColumn.Type.STRING, "=",
				true, true, FragmentEntryLink::getUuid));

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_uniquePersistenceFinderByUUID_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByUUID_G, _SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
			new FinderColumn<>(
				"fragmentEntryLink.", "uuid", FinderColumn.Type.STRING, "=",
				true, false, FragmentEntryLink::getUuid),
			new FinderColumn<>(
				"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG, "=",
				true, true, FragmentEntryLink::getGroupId));

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
				_finderPathCountByUuid_C, _SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "uuid", FinderColumn.Type.STRING, "=",
					true, false, FragmentEntryLink::getUuid),
				new FinderColumn<>(
					"fragmentEntryLink.", "companyId", FinderColumn.Type.LONG,
					"=", true, true, FragmentEntryLink::getCompanyId));

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
				_finderPathCountByGroupId, _SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG,
					"=", true, true, FragmentEntryLink::getGroupId));

		_finderPathWithPaginationFindByRendererKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRendererKey",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"rendererKey"}, true);

		_finderPathWithoutPaginationFindByRendererKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRendererKey",
			new String[] {String.class.getName()}, new String[] {"rendererKey"},
			true);

		_finderPathCountByRendererKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRendererKey",
			new String[] {String.class.getName()}, new String[] {"rendererKey"},
			false);

		_collectionPersistenceFinderByRendererKey =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByRendererKey,
				_finderPathWithoutPaginationFindByRendererKey,
				_finderPathCountByRendererKey,
				_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "rendererKey",
					FinderColumn.Type.STRING, "=", true, true,
					FragmentEntryLink::getRendererKey));

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
			_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
			_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
			FragmentEntryLinkModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"fragmentEntryLink.", "type", FinderColumn.Type.INTEGER, "=",
				true, true, FragmentEntryLink::getType));

		_finderPathWithPaginationFindByG_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "plid"}, true);

		_finderPathWithoutPaginationFindByG_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "plid"}, true);

		_finderPathCountByG_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "plid"}, false);

		_collectionPersistenceFinderByG_P = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_P,
			_finderPathWithoutPaginationFindByG_P, _finderPathCountByG_P,
			_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
			_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
			FragmentEntryLinkModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, FragmentEntryLink::getGroupId),
			new FinderColumn<>(
				"fragmentEntryLink.", "plid", FinderColumn.Type.LONG, "=", true,
				true, FragmentEntryLink::getPlid));

		_finderPathWithPaginationFindByC_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "rendererKey"}, true);

		_finderPathWithoutPaginationFindByC_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_R",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "rendererKey"}, true);

		_finderPathCountByC_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "rendererKey"}, false);

		_finderPathWithPaginationCountByC_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_R",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "rendererKey"}, false);

		_finderPathWithPaginationFindByFEERC_FESERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFEERC_FESERC",
			new String[] {
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"fragmentEntryERC", "fragmentEntryScopeERC"}, true);

		_finderPathWithoutPaginationFindByFEERC_FESERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFEERC_FESERC",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"fragmentEntryERC", "fragmentEntryScopeERC"}, true);

		_finderPathCountByFEERC_FESERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFEERC_FESERC",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"fragmentEntryERC", "fragmentEntryScopeERC"}, false);

		_collectionPersistenceFinderByFEERC_FESERC =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByFEERC_FESERC,
				_finderPathWithoutPaginationFindByFEERC_FESERC,
				_finderPathCountByFEERC_FESERC,
				_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryScopeERC",
					FinderColumn.Type.STRING, "=", true, true,
					FragmentEntryLink::getFragmentEntryScopeERC));

		_finderPathWithPaginationFindByG_OFELERC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_OFELERC_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "originalFragmentEntryLinkERC", "plid"},
			true);

		_finderPathWithoutPaginationFindByG_OFELERC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_OFELERC_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			new String[] {"groupId", "originalFragmentEntryLinkERC", "plid"},
			true);

		_finderPathCountByG_OFELERC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_OFELERC_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			new String[] {"groupId", "originalFragmentEntryLinkERC", "plid"},
			false);

		_collectionPersistenceFinderByG_OFELERC_P =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_OFELERC_P,
				_finderPathWithoutPaginationFindByG_OFELERC_P,
				_finderPathCountByG_OFELERC_P,
				_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryLink::getGroupId),
				new FinderColumn<>(
					"fragmentEntryLink.", "originalFragmentEntryLinkERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getOriginalFragmentEntryLinkERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "plid", FinderColumn.Type.LONG, "=",
					true, true, FragmentEntryLink::getPlid));

		_finderPathWithPaginationFindByG_FEERC_FESERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FEERC_FESERC",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC"
			},
			true);

		_finderPathWithoutPaginationFindByG_FEERC_FESERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FEERC_FESERC",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC"
			},
			true);

		_finderPathCountByG_FEERC_FESERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_FEERC_FESERC",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC"
			},
			false);

		_collectionPersistenceFinderByG_FEERC_FESERC =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FEERC_FESERC,
				_finderPathWithoutPaginationFindByG_FEERC_FESERC,
				_finderPathCountByG_FEERC_FESERC,
				_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryLink::getGroupId),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryScopeERC",
					FinderColumn.Type.STRING, "=", true, true,
					FragmentEntryLink::getFragmentEntryScopeERC));

		_finderPathWithPaginationFindByG_S_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "segmentsExperienceId", "plid"}, true);

		_finderPathWithoutPaginationFindByG_S_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "segmentsExperienceId", "plid"}, true);

		_finderPathCountByG_S_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "segmentsExperienceId", "plid"}, false);

		_finderPathWithPaginationCountByG_S_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_S_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "segmentsExperienceId", "plid"}, false);

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
			_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
			_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
			FragmentEntryLinkModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, FragmentEntryLink::getGroupId),
			new FinderColumn<>(
				"fragmentEntryLink.", "classNameId", FinderColumn.Type.LONG,
				"=", true, false, FragmentEntryLink::getClassNameId),
			new FinderColumn<>(
				"fragmentEntryLink.", "classPK", FinderColumn.Type.LONG, "=",
				true, true, FragmentEntryLink::getClassPK));

		_finderPathWithPaginationFindByG_P_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "plid", "deleted"}, true);

		_finderPathWithoutPaginationFindByG_P_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "plid", "deleted"}, true);

		_finderPathCountByG_P_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "plid", "deleted"}, false);

		_collectionPersistenceFinderByG_P_D = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByG_P_D,
			_finderPathWithoutPaginationFindByG_P_D, _finderPathCountByG_P_D,
			_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
			_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
			FragmentEntryLinkModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG, "=",
				true, false, FragmentEntryLink::getGroupId),
			new FinderColumn<>(
				"fragmentEntryLink.", "plid", FinderColumn.Type.LONG, "=", true,
				false, FragmentEntryLink::getPlid),
			new FinderColumn<>(
				"fragmentEntryLink.", "deleted", FinderColumn.Type.BOOLEAN, "=",
				true, true, FragmentEntryLink::isDeleted));

		_finderPathWithPaginationFindByFEERC_FESERC_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFEERC_FESERC_D",
			new String[] {
				String.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"fragmentEntryERC", "fragmentEntryScopeERC", "deleted"
			},
			true);

		_finderPathWithoutPaginationFindByFEERC_FESERC_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFEERC_FESERC_D",
			new String[] {
				String.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {
				"fragmentEntryERC", "fragmentEntryScopeERC", "deleted"
			},
			true);

		_finderPathCountByFEERC_FESERC_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFEERC_FESERC_D",
			new String[] {
				String.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {
				"fragmentEntryERC", "fragmentEntryScopeERC", "deleted"
			},
			false);

		_collectionPersistenceFinderByFEERC_FESERC_D =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByFEERC_FESERC_D,
				_finderPathWithoutPaginationFindByFEERC_FESERC_D,
				_finderPathCountByFEERC_FESERC_D,
				_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryScopeERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryScopeERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "deleted", FinderColumn.Type.BOOLEAN,
					"=", true, true, FragmentEntryLink::isDeleted));

		_finderPathWithPaginationFindByG_FEERC_FESERC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FEERC_FESERC_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC",
				"classNameId"
			},
			true);

		_finderPathWithoutPaginationFindByG_FEERC_FESERC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FEERC_FESERC_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC",
				"classNameId"
			},
			true);

		_finderPathCountByG_FEERC_FESERC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FEERC_FESERC_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC",
				"classNameId"
			},
			false);

		_collectionPersistenceFinderByG_FEERC_FESERC_C =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FEERC_FESERC_C,
				_finderPathWithoutPaginationFindByG_FEERC_FESERC_C,
				_finderPathCountByG_FEERC_FESERC_C,
				_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryLink::getGroupId),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryScopeERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryScopeERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "classNameId", FinderColumn.Type.LONG,
					"=", true, true, FragmentEntryLink::getClassNameId));

		_finderPathWithPaginationFindByG_FEERC_FESERC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FEERC_FESERC_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC", "plid"
			},
			true);

		_finderPathWithoutPaginationFindByG_FEERC_FESERC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FEERC_FESERC_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC", "plid"
			},
			true);

		_finderPathCountByG_FEERC_FESERC_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FEERC_FESERC_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC", "plid"
			},
			false);

		_collectionPersistenceFinderByG_FEERC_FESERC_P =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FEERC_FESERC_P,
				_finderPathWithoutPaginationFindByG_FEERC_FESERC_P,
				_finderPathCountByG_FEERC_FESERC_P,
				_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryLink::getGroupId),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryScopeERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryScopeERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "plid", FinderColumn.Type.LONG, "=",
					true, true, FragmentEntryLink::getPlid));

		_finderPathWithPaginationFindByG_FEERC_FESERC_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FEERC_FESERC_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC",
				"deleted"
			},
			true);

		_finderPathWithoutPaginationFindByG_FEERC_FESERC_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_FEERC_FESERC_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC",
				"deleted"
			},
			true);

		_finderPathCountByG_FEERC_FESERC_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FEERC_FESERC_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC",
				"deleted"
			},
			false);

		_collectionPersistenceFinderByG_FEERC_FESERC_D =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FEERC_FESERC_D,
				_finderPathWithoutPaginationFindByG_FEERC_FESERC_D,
				_finderPathCountByG_FEERC_FESERC_D,
				_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryLink::getGroupId),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryScopeERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryScopeERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "deleted", FinderColumn.Type.BOOLEAN,
					"=", true, true, FragmentEntryLink::isDeleted));

		_finderPathWithPaginationFindByG_S_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "segmentsExperienceId", "classNameId", "classPK"
			},
			true);

		_finderPathWithoutPaginationFindByG_S_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"groupId", "segmentsExperienceId", "classNameId", "classPK"
			},
			true);

		_finderPathCountByG_S_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"groupId", "segmentsExperienceId", "classNameId", "classPK"
			},
			false);

		_collectionPersistenceFinderByG_S_C_C =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_S_C_C,
				_finderPathWithoutPaginationFindByG_S_C_C,
				_finderPathCountByG_S_C_C, _SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryLink::getGroupId),
				new FinderColumn<>(
					"fragmentEntryLink.", "segmentsExperienceId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryLink::getSegmentsExperienceId),
				new FinderColumn<>(
					"fragmentEntryLink.", "classNameId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryLink::getClassNameId),
				new FinderColumn<>(
					"fragmentEntryLink.", "classPK", FinderColumn.Type.LONG,
					"=", true, true, FragmentEntryLink::getClassPK));

		_finderPathWithPaginationFindByG_S_P_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S_P_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "segmentsExperienceId", "plid", "deleted"},
			true);

		_finderPathWithoutPaginationFindByG_S_P_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S_P_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "segmentsExperienceId", "plid", "deleted"},
			true);

		_finderPathCountByG_S_P_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S_P_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "segmentsExperienceId", "plid", "deleted"},
			false);

		_finderPathWithPaginationCountByG_S_P_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_S_P_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "segmentsExperienceId", "plid", "deleted"},
			false);

		_finderPathWithPaginationFindByG_S_P_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S_P_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "segmentsExperienceId", "plid", "rendererKey"
			},
			true);

		_finderPathWithoutPaginationFindByG_S_P_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S_P_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {
				"groupId", "segmentsExperienceId", "plid", "rendererKey"
			},
			true);

		_finderPathCountByG_S_P_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S_P_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {
				"groupId", "segmentsExperienceId", "plid", "rendererKey"
			},
			false);

		_collectionPersistenceFinderByG_S_P_R =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_S_P_R,
				_finderPathWithoutPaginationFindByG_S_P_R,
				_finderPathCountByG_S_P_R, _SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryLink::getGroupId),
				new FinderColumn<>(
					"fragmentEntryLink.", "segmentsExperienceId",
					FinderColumn.Type.LONG, "=", true, false,
					FragmentEntryLink::getSegmentsExperienceId),
				new FinderColumn<>(
					"fragmentEntryLink.", "plid", FinderColumn.Type.LONG, "=",
					true, false, FragmentEntryLink::getPlid),
				new FinderColumn<>(
					"fragmentEntryLink.", "rendererKey",
					FinderColumn.Type.STRING, "=", true, true,
					FragmentEntryLink::getRendererKey));

		_finderPathWithPaginationFindByG_FEERC_FESERC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_FEERC_FESERC_C_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC",
				"classNameId", "classPK"
			},
			true);

		_finderPathWithoutPaginationFindByG_FEERC_FESERC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FEERC_FESERC_C_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC",
				"classNameId", "classPK"
			},
			true);

		_finderPathCountByG_FEERC_FESERC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FEERC_FESERC_C_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"groupId", "fragmentEntryERC", "fragmentEntryScopeERC",
				"classNameId", "classPK"
			},
			false);

		_collectionPersistenceFinderByG_FEERC_FESERC_C_C =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByG_FEERC_FESERC_C_C,
				_finderPathWithoutPaginationFindByG_FEERC_FESERC_C_C,
				_finderPathCountByG_FEERC_FESERC_C_C,
				_SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
				_SQL_COUNT_FRAGMENTENTRYLINK_WHERE,
				FragmentEntryLinkModelImpl.ORDER_BY_JPQL,
				_ORDER_BY_ENTITY_ALIAS,
				new FinderColumn<>(
					"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryLink::getGroupId),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "fragmentEntryScopeERC",
					FinderColumn.Type.STRING, "=", true, false,
					FragmentEntryLink::getFragmentEntryScopeERC),
				new FinderColumn<>(
					"fragmentEntryLink.", "classNameId", FinderColumn.Type.LONG,
					"=", true, false, FragmentEntryLink::getClassNameId),
				new FinderColumn<>(
					"fragmentEntryLink.", "classPK", FinderColumn.Type.LONG,
					"=", true, true, FragmentEntryLink::getClassPK));

		_finderPathFetchByERC_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "groupId"}, true);

		_uniquePersistenceFinderByERC_G = new UniquePersistenceFinder<>(
			this, _finderPathFetchByERC_G, _SQL_SELECT_FRAGMENTENTRYLINK_WHERE,
			new FinderColumn<>(
				"fragmentEntryLink.", "externalReferenceCode",
				FinderColumn.Type.STRING, "=", true, false,
				FragmentEntryLink::getExternalReferenceCode),
			new FinderColumn<>(
				"fragmentEntryLink.", "groupId", FinderColumn.Type.LONG, "=",
				true, true, FragmentEntryLink::getGroupId));

		FragmentEntryLinkUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FragmentEntryLinkUtil.setPersistence(null);

		entityCache.removeCache(FragmentEntryLinkImpl.class.getName());
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

	private static final String _SQL_SELECT_FRAGMENTENTRYLINK =
		"SELECT fragmentEntryLink FROM FragmentEntryLink fragmentEntryLink";

	private static final String _SQL_SELECT_FRAGMENTENTRYLINK_WHERE =
		"SELECT fragmentEntryLink FROM FragmentEntryLink fragmentEntryLink WHERE ";

	private static final String _SQL_COUNT_FRAGMENTENTRYLINK =
		"SELECT COUNT(fragmentEntryLink) FROM FragmentEntryLink fragmentEntryLink";

	private static final String _SQL_COUNT_FRAGMENTENTRYLINK_WHERE =
		"SELECT COUNT(fragmentEntryLink) FROM FragmentEntryLink fragmentEntryLink WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "fragmentEntryLink.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FragmentEntryLink exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1442628866